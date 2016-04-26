package cn.edu.gdut.model;

import com.google.gson.Gson;

public class RejudgeQuery {
	private Integer rid;
	private Integer pid;
	private Integer cid;
	private Boolean includeAc;
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
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Boolean getIncludeAc() {
		return includeAc;
	}
	public void setIncludeAc(Boolean includeAc) {
		this.includeAc = includeAc;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
