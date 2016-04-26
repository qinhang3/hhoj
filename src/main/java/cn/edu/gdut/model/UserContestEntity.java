package cn.edu.gdut.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class UserContestEntity {
	private String username;
	private Integer cid;
	private Long lastSubmitTime;
	private Integer lastSubmitPid;
	//pid to status
	private Map<Integer,List<StatusModel>> statusList;
	public String getUsername() {
		return username;
	}
	public Integer getCid() {
		return cid;
	}
	public Long getLastSubmitTime() {
		return lastSubmitTime;
	}
	public Integer getLastSubmitPid(){
		return lastSubmitPid;
	}
	public Map<Integer,List<StatusModel>> getStatusList() {
		return statusList;
	}
	
	public UserContestEntity(String username,Integer cid) {
		this.username = username;
		this.cid = cid;
		statusList = new HashMap<Integer, List<StatusModel>>();
	}
	public void addStatusModel(StatusModel statusModel) {
		if (lastSubmitPid == null){
			lastSubmitTime = statusModel.getCreateTime().getTime();
			lastSubmitPid = statusModel.getPid();
		}
		List<StatusModel> list = statusList.get(statusModel.getPid());
		if (list == null){
			list = new ArrayList<StatusModel>();
			statusList.put(statusModel.getPid(), list);
		}
		list.add(statusModel);
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
