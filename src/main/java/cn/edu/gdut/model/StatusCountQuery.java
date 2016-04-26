package cn.edu.gdut.model;

import java.util.ArrayList;
import java.util.List;

public class StatusCountQuery {
	private List<Integer> pidList;
	private String username;
	private Boolean orderDesc;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Integer> getPidList() {
		return pidList;
	}
	public void addPid(Integer pid){
		if (pidList == null){
			pidList = new ArrayList<Integer>();
		}
		pidList.add(pid);
	}
	public Boolean getOrderDesc() {
		if (orderDesc == null){
			return false;
		}
		return orderDesc;
	}
	public void setOrderDesc(Boolean orderDesc) {
		this.orderDesc = orderDesc;
	}
}
