package cn.edu.gdut.mapper;

import java.util.List;

import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.model.ProblemQuery;

public interface ProblemMapper {
	public int insert(ProblemModel problemModel);
	public ProblemModel selectById(Integer pid);
	public int updateById(ProblemModel problemModel);
	public List<ProblemModel> selectByQuery(ProblemQuery problemQuery);
	public int delete(Integer pid);
	public int updatePublicByPid(ProblemModel problemModel);
}
