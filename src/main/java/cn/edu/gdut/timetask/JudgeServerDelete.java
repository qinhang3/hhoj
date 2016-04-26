package cn.edu.gdut.timetask;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.edu.gdut.controller.AdminController;
import cn.edu.gdut.util.JudgeServer;

@Component("judgeServerDelete")
public class JudgeServerDelete {
	private LinkedBlockingQueue<Integer> queue = JudgeServer.getDeleteQueue();
	private Map<Integer, JudgeServer> map = AdminController.servers;
	
	@Scheduled(cron = "0/30 * * * * ?")
	public void deleteNow(){	
		while(!queue.isEmpty()){
			Integer tid = queue.peek();
			JudgeServer judgeServer = map.get(tid);
			if(judgeServer.getEndTime() + 30*1000L < System.currentTimeMillis()){
				map.remove(tid);
				queue.poll();
			} else {
				break;
			}
		}
	}
}
