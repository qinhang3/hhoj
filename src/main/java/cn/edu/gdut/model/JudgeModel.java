package cn.edu.gdut.model;

import com.google.gson.Gson;

public class JudgeModel {
	private Integer pid;
	private Integer memLimit;
	private Integer timeLimit;
	private Integer isSpj;
	private Integer rid;
	private String code;
	private String language;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getMemLimit() {
		return memLimit;
	}
	public void setMemLimit(Integer memLimit) {
		this.memLimit = memLimit;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public Integer getIsSpj() {
		return isSpj;
	}
	public void setIsSpj(Integer isSpj) {
		this.isSpj = isSpj;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
