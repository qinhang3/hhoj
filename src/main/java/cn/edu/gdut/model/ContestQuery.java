package cn.edu.gdut.model;

import java.util.List;


public class ContestQuery extends PageQueryBase {

	private List<Integer> isPublicList;
	public ContestQuery(int pageSize) {
		super(pageSize);
	}
	public List<Integer> getIsPublicList() {
		return isPublicList;
	}
	public void setIsPublicList(List<Integer> isPublicList) {
		this.isPublicList = isPublicList;
	}
}
