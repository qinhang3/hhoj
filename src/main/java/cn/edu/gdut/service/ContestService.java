package cn.edu.gdut.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.mapper.ContestMapper;
import cn.edu.gdut.model.ContestEntiry;
import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.model.ContestQuery;
import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.util.LocalCache;
import cn.edu.gdut.util.OJConstant;
import cn.edu.gdut.util.ResultBase;

@Service
public class ContestService implements DbService{
	@Autowired
	private ContestMapper contestMapper;
	@Autowired
	private ProblemService ProblemService;
	@Autowired
	private DBLoggerService dbLoggerService;
	@Autowired
	private ConstantService constantService;
	
	private static LocalCache<Integer, ContestEntiry> lc = new LocalCache<Integer, ContestEntiry>("ContestEntiry",60*1000L);
	
	public ResultBase<Integer> add(ContestModel contestModel) {
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			int cnt = contestMapper.insert(contestModel);
			return result.setRightValueReturn(cnt);
		} catch (Exception e){
			dbLoggerService.error("INSERT CONTEST", e);
			return result.setErrorMsgReturn("add contest error");
		}
	}
	
	public ResultBase<Integer> update(ContestModel contestModel){
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			Integer cnt = contestMapper.update(contestModel);
			lc.del(contestModel.getId());
			return result.setRightValueReturn(cnt);
		} catch (Exception e){
			dbLoggerService.error("UPDATE CONTEST", e);
			return result.setErrorMsgReturn("edit contest error");
		}
	}
	
	public ResultBase<ContestModel> selectById(Integer cid) {
		ResultBase<ContestModel> result = new ResultBase<ContestModel>();
		try{
			ContestModel contestModel = contestMapper.selectById(cid);
			return result.setRightValueReturn(contestModel);
		} catch (Exception e){
			dbLoggerService.error("SELECT CONTEST", e);
			return result.setErrorMsgReturn("select contest error");
		}
	}
	
	public ResultBase<Integer> setContestIsPublic(Integer cid,Integer isPublic){
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			ContestModel contestModel = new ContestModel();
			contestModel.setId(cid);
			contestModel.setIsPublic(isPublic);
			Integer cnt = contestMapper.setIsPublicById(contestModel);
			return result.setRightValueReturn(cnt);
		} catch (Exception e) {
			dbLoggerService.error("UPDATE CONTEST", e);
			return result.setErrorMsgReturn("edit contest error");
		}
	}
	
	public ResultBase<ContestEntiry> getContestEntity(Integer cid){
		ResultBase<ContestEntiry> result = new ResultBase<ContestEntiry>();
		ContestEntiry contestEntiry = lc.get(cid);
		if (contestEntiry!=null) {
			return result.setRightValueReturn(contestEntiry);
		}
		ResultBase<ContestModel> baseResult = selectById(cid);
		if (!baseResult.isSuccess()){
			return result.setErrorMsgReturn(baseResult.getMsg());
		}
		if (baseResult.getValue() == null){
			return result.setRightValueReturn(null);
		}
		contestEntiry = ContestEntiry.getFromModel(baseResult.getValue());
		for(Integer pid : baseResult.getValue().getProblemList()){
			ResultBase<ProblemModel> problemResult = ProblemService.selectById(pid);
			if (problemResult.isSuccess()){
				ProblemModel problemModel = problemResult.getValue();
				contestEntiry.addProblemModel(problemModel);
			} else {
				return result.setErrorMsgReturn(problemResult.getMsg());
			}
		}
		lc.put(cid, contestEntiry);
		return result.setRightValueReturn(contestEntiry);
	}
	
	public ResultBase<List<ContestModel>> getContestList(Integer page, List<Integer> isPublicList){
		ResultBase<List<ContestModel>> result = new ResultBase<List<ContestModel>>();
		try{
			ContestQuery query = new ContestQuery(constantService.getAsInteger(OJConstant.Query.PAGESIZE, 20));
			query.setPage(page);
			query.setIsPublicList(isPublicList);
			List<ContestModel> list = contestMapper.selectByQuery(query);
			return result.setRightValueReturn(list);
		} catch (Exception e) {
			dbLoggerService.error("SELECT CONTEST", e);
			return result.setErrorMsgReturn("select contest error");
		}
	}
}
