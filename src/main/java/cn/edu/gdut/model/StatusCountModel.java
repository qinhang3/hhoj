package cn.edu.gdut.model;

import com.google.gson.Gson;

public class StatusCountModel {
	private Integer pid;
	private String status;
	private Integer cnt;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
