package cn.edu.gdut.model;

public class DBLoggerQuery extends PageQueryBase{
	public DBLoggerQuery(int pageSize) {
		super(pageSize);
	}

	private String topic;
	private String type;
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
}
