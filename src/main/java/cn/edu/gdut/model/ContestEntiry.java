package cn.edu.gdut.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ContestEntiry extends ContestModel {
	private List<ProblemModel> problemModels;
	private Set<String> member;

	public List<ProblemModel> getProblemModels() {
		return problemModels;
	}
	public void setProblemModels(List<ProblemModel> problemModels) {
		this.problemModels = problemModels;
	}
	public Set<String> getMember() {
		return member;
	}
	public void setMember(Set<String> member) {
		this.member = member;
	}
	public boolean hasMenber(String username){
		return getMember().contains(username); 
	}
	public static ContestEntiry getFromModel(ContestModel model) {
		ContestEntiry contestEntiry = new ContestEntiry();
		contestEntiry.setCreateTime(model.getCreateTime());
		contestEntiry.setEndTime(model.getEndTime());
		contestEntiry.setId(model.getId());
		contestEntiry.setIsPublic(model.getIsPublic());
		contestEntiry.setModifyTime(model.getModifyTime());
		contestEntiry.setProblemList(model.getProblemList());
		contestEntiry.setStartTime(model.getStartTime());
		contestEntiry.setTitle(model.getTitle());
		contestEntiry.setProblemModels(new ArrayList<ProblemModel>());
		return contestEntiry;
	}
	
	public Integer getPidOnIndex(int index){
		if (problemModels.size()<=index) return null;
		return problemModels.get(index).getId();
		
	}
	
	public String getTimePercent(){
		Long allTime = this.getEndTime().getTime() - this.getStartTime().getTime();
		Long nowTime = new Date().getTime() - this.getStartTime().getTime();
		if (nowTime > allTime) return "100";
		if (nowTime < 0) return "0";
		return ""+(nowTime*100/allTime);
	}
	public void addProblemModel(ProblemModel problemModel) {
		getProblemModels().add(problemModel);
	}
}
