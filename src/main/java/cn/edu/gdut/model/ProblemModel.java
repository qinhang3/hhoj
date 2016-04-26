package cn.edu.gdut.model;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class ProblemModel {
	@Expose
	private Integer id;
	private String title;
	private String description;
	private String input;
	private String output;
	private String sampleInput;
	private String sampleOutput;
	private String author;
	private Integer memLimit;
	private Integer timeLimit;
	private Integer isPublic;
	private Integer isSpj;
	private Date createTime;
	private Date modifyTime;
	private Integer acCnt;
	private Integer tryCnt;
	public ProblemModel() {
		setTitle("Title");
		setTimeLimit(1000);
		setMemLimit(65536);
		setIsPublic(0);
		setIsSpj(0);
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getSampleInput() {
		return sampleInput;
	}
	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}
	public String getSampleOutput() {
		return sampleOutput;
	}
	public void setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getMemLimit() {
		return memLimit;
	}
	public void setMemLimit(Integer menLimit) {
		this.memLimit = menLimit;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public Integer getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	public Integer getIsSpj() {
		return isSpj;
	}
	public void setIsSpj(Integer isSpj) {
		this.isSpj = isSpj;
	}
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
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
	public Integer getTryCnt() {
		if (tryCnt == null) return 0;
		return tryCnt;
	}
	public void setTryCnt(Integer tryCnt) {
		this.tryCnt = tryCnt;
	}
	public Integer getAcCnt() {
		if (acCnt == null) return 0;
		return acCnt;
	}
	public void setAcCnt(Integer acCnt) {
		this.acCnt = acCnt;
	}
}
