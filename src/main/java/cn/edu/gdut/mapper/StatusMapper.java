package cn.edu.gdut.mapper;

import java.util.List;

import cn.edu.gdut.model.JudgeModel;
import cn.edu.gdut.model.RejudgeQuery;
import cn.edu.gdut.model.StatusCountModel;
import cn.edu.gdut.model.StatusCountQuery;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.model.StatusQuery;

public interface StatusMapper {
	public int insert(StatusModel statusModel);
	public void mork(StatusModel statusModel);
	public int judged(StatusModel statusModel);
	public StatusModel selectById(Integer rid);
	public List<StatusModel> selectByQuery(StatusQuery query);
	public Integer selectNumberByQuery(StatusQuery query);
	public List<StatusModel> selectForRanklist(Integer cid);
	public List<JudgeModel> getForJudge();
	public StatusModel getCode(Integer rid);
	public int reJudge(RejudgeQuery query);
	public List<StatusCountModel> count(StatusCountQuery query);
	public List<Integer> getAcByUsername(String username);
}
