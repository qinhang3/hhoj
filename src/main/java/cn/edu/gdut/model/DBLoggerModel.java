package cn.edu.gdut.model;

import java.util.Date;

import com.google.gson.Gson;

import cn.edu.gdut.util.UserSession;

public class DBLoggerModel {
	private Long id;
	private Date time;
	private String topic;
	private String type;
	private String info;
	private String detail;
	private String username;
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public DBLoggerModel(){
		
	}
	
	public DBLoggerModel(String topic,String type,String info,String detail){
		setTopic(topic);
		setType(type);
		setInfo(info);
		setDetail(detail);
		setUsername(UserSession.getUsername());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
