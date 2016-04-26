package cn.edu.gdut.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.StringUtils;

import cn.edu.gdut.model.JudgeModel;
import cn.edu.gdut.model.JudgeQuery;
import cn.edu.gdut.service.StatusService;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class JudgeServer extends Thread{
	private static String noPermission = "{\"success\":false,\"msg\":\"LoginFirst\"}";
	private static Integer uniqueIdSeed = 1000;
	private static LinkedBlockingQueue<Integer> deleteQueue = new LinkedBlockingQueue<Integer>();

	private Socket socket;
	private StatusService statusService;
	private boolean isLogin = false;
	private LinkedBlockingQueue<JudgeModel> pendingQueue;
	private String judgeName;
	private String status;
	private Date lastQuery;
	private int uniqueId;
	private Long endTime;
	
	public String getStatus(){
		return status;
	}
	public String getJudgeName() {
		return judgeName;
	}
	public Socket getSocket(){
		return socket;
	}
	public int getUniqueId(){
		return uniqueId;
	}
	public Long getEndTime(){
		return endTime;
	}

	public JudgeServer(Socket socket,LinkedBlockingQueue<JudgeModel> pendingQueue) {
		this.socket = socket;
		if(statusService == null){
			statusService = (StatusService)SpringUtil.getBean("statusService");
		}
		this.pendingQueue = pendingQueue;
		synchronized (uniqueIdSeed) {
			uniqueId = uniqueIdSeed++;
		}
	}
	
	@Override
	public void run() {
		status = "Not Login";
		Scanner sin = null;
		PrintWriter sout = null;
		try {
			sin = new Scanner(socket.getInputStream());
			sout = new PrintWriter(socket.getOutputStream());
			while(sin.hasNext()){
				lastQuery = new Date();
				String next = sin.nextLine();
				String result = getResult(next);
				sout.println(result);
				sout.flush();
			}
		} catch (IOException e) {
			
		} finally {
			if (sin!=null){
				sin.close();
			}
			if (sout!=null){
				sout.close();
			}
			if (socket !=null){
				try {
					socket.close();
				} catch (IOException e) {
					
				}
				status = "Stop";
				endTime = System.currentTimeMillis();
				deleteQueue.add(uniqueId);
			}
		}
	}

	private String getResult(String query) {
		JudgeQuery judgeQuery = null;
		try{
			judgeQuery = new Gson().fromJson(query, JudgeQuery.class);
		} catch (JsonParseException e){
			return "{\"success\":false,\"msg\":\"JsonParseException\"}";
		}
		if (StringUtils.equals(judgeQuery.getCommand(), JudgeQuery.Command.doLogin)){
			return doLogin(judgeQuery);
		} else if (StringUtils.equals(judgeQuery.getCommand(), JudgeQuery.Command.nextSolution)){
			return nextSolution(judgeQuery);
		} else if (StringUtils.equals(judgeQuery.getCommand(), JudgeQuery.Command.statusChange)){
			return statusChange(judgeQuery);
		} else {
			return "{\"success\":false,\"msg\":\"CommandNotSupport\"}";
		}
	}

	private String doLogin(JudgeQuery judgeQuery) {
		// TODO Auto-generated method stub
		isLogin = true;
		judgeName = judgeQuery.getJudgeName();
		status = "Running";
		return "{\"success\":true,\"msg\":\"LoginSuccess\"}";
	}

	private String statusChange(JudgeQuery judgeQuery) {
		if (!isLogin){
			return noPermission;
		}
		return statusService.judge(
				judgeQuery.getRid(), 
				judgeQuery.getStatus(), 
				judgeQuery.getRunTime(), 
				judgeQuery.getRunMemory(),
				judgeQuery.getCompileInfo(),
				judgeName
				).toJson(String.class);
	}

	private String nextSolution(JudgeQuery judgeQuery) {
		if (!isLogin){
			return noPermission;
		}
		ResultBase<JudgeModel> result = new ResultBase<JudgeModel>();
		return result.setRightValueReturn(pendingQueue.poll()).toJson(JudgeModel.class);
	}
	public void tryToStop(){
		if (!socket.isClosed()){
			try {
				socket.close();
			} catch (IOException e) {
				status = "Excepetion : "+e.toString();
			}
		}
	}
	public Date getLastQuery() {
		return lastQuery;
	}
	public static LinkedBlockingQueue<Integer> getDeleteQueue() {
		return deleteQueue;
	}
}
