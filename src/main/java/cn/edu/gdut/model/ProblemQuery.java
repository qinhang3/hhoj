package cn.edu.gdut.model;

public class ProblemQuery extends PageQueryBase {
	public ProblemQuery(int pageSize) {
		super(pageSize);
	}
	private Integer isPublic;
	private Boolean desc;
	private Integer isDelete;
	
	public Integer getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Boolean getDesc() {
		return desc;
	}
	public void setDesc(Boolean desc) {
		this.desc = desc;
	}
}
