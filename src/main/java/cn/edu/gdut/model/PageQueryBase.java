package cn.edu.gdut.model;

public class PageQueryBase {
	private Integer page;
	private Integer pageSize;
	private Integer beginIndex;
	public PageQueryBase(int pageSize){
		setPage(0);
		setPageSize(pageSize);
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
		if (pageSize != null){
			setBeginIndex(page * pageSize);
		}
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		if (page != null) {
			setBeginIndex(page * pageSize);
		}
	}
	public Integer getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}
}
