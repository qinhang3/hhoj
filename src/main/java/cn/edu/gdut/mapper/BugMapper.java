package cn.edu.gdut.mapper;

import java.util.List;

import cn.edu.gdut.model.BugModel;

public interface BugMapper {
	public Integer insert(BugModel bugModel);
	public List<BugModel> select();
	public Integer update(BugModel bugModel);
	
}
