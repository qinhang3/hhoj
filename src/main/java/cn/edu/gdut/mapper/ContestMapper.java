package cn.edu.gdut.mapper;

import java.util.List;

import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.model.ContestQuery;

public interface ContestMapper {
	public Integer insert(ContestModel contestModel);
	public Integer update(ContestModel contestModel);
	public Integer setIsPublicById(ContestModel contestModel);
	public void delete(Integer cid);
	public ContestModel selectById(Integer cid);
	public List<ContestModel> selectByQuery(ContestQuery query);
}
