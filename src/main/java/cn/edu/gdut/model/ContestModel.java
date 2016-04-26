package cn.edu.gdut.model;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContestModel {
	private Integer id;
	private String title;
	private Date startTime;
	private Date endTime;
	private List<Integer> problems;
	private Integer isPublic;
	private Date createTime;
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public String getProblems() {
		Gson gson = new Gson();
		return gson.toJson(problems);
	}

	public void setProblems(String problems) {
		Gson gson = new Gson();
		this.problems = gson.fromJson(problems, new TypeToken<List<Integer>>() {  
        }.getType());
	}
	
	public List<Integer> getProblemList(){
		return problems;
	}
	
	public void setProblemList(List<Integer> problems){
		this.problems = problems;
	}
	
	public interface PublicMode{
		public final Integer PRIVATE = 0;
		public final Integer PUBLIC = 1;
		public final Integer REGISTER = 2;
		public final Integer HIDDEN = 3;
	}
	
	public String getStatusNow(){
		if (this.getStartTime().after(new Date())) return "Not Start";
		if (this.getEndTime().before(new Date())) return "End";
		return "Running";
	}

}
