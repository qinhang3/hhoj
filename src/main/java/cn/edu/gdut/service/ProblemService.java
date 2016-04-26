package cn.edu.gdut.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.mapper.ProblemMapper;
import cn.edu.gdut.mapper.StatusMapper;
import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.model.ProblemQuery;
import cn.edu.gdut.model.StatusCountModel;
import cn.edu.gdut.model.StatusCountQuery;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.util.LocalCache;
import cn.edu.gdut.util.OJConstant;
import cn.edu.gdut.util.ResultBase;

/**
 * @see DbService
 * @author qinhang
 *
 */
@Service
public class ProblemService implements DbService {
	
	@Autowired
	private ProblemMapper problemMapper;
	@Autowired
	private DBLoggerService dbLoggerService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private StatusMapper statusMapper;
	
	private static LocalCache<Integer, ProblemModel> lc = new LocalCache<Integer, ProblemModel>("Problem",60*1000L);
	
	public ResultBase<ProblemModel> selectById(Integer Id){
		ResultBase<ProblemModel> result = new ResultBase<ProblemModel>();
		if (Id == null || Id < 0) {
			return result.setErrorMsgReturn("wrong problem id");
		}
		ProblemModel problemModel = lc.get(Id);
		if (null != problemModel) {
			return result.setRightValueReturn(problemModel);
		}
		try{
			problemModel = problemMapper.selectById(Id);
			if (problemModel != null){
				lc.put(Id, problemModel);
			}
			return result.setRightValueReturn(problemModel);
		} catch (Exception e){
			dbLoggerService.error("SELECT PROBLEM", e);
			return result.setErrorMsgReturn("select problem error");
		}
	}
	
	public ResultBase<Integer> modify(ProblemModel problemModel){
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			Integer cnt = problemMapper.updateById(problemModel);
			lc.del(problemModel.getId());
			return result.setRightValueReturn(cnt);
		} catch (Exception e){
			dbLoggerService.error("UPDATE PROBLEM", e);
			return result.setErrorMsgReturn("update problem error");
		}
	}

	public ResultBase<Integer> add(ProblemModel problemModel) {
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			Integer cnt = problemMapper.insert(problemModel);
			dbLoggerService.info("ADD PROBLEM", "ADD PROBLEM ");
			return result.setRightValueReturn(cnt);
		} catch (Exception e){
			dbLoggerService.error("INSERT PROBLEM", e);
			return result.setErrorMsgReturn("insert problem error");
		}
	}
	
	public ResultBase<List<ProblemModel>> selectList(Integer isPublic,Integer page,Boolean orderDesc){
		ProblemQuery problemQuery = new ProblemQuery(constantService.getAsInteger(OJConstant.Query.PAGESIZE, 20));
		problemQuery.setIsPublic(isPublic);
		problemQuery.setPage(page);
		problemQuery.setDesc(orderDesc);
		ResultBase<List<ProblemModel>> result = new ResultBase<List<ProblemModel>>();
		try{
			List<ProblemModel> list = problemMapper.selectByQuery(problemQuery);
			StatusCountQuery statusCountQuery = new StatusCountQuery();
			for (ProblemModel problemModel : list){
				statusCountQuery.addPid(problemModel.getId());
			}
			statusCountQuery.setOrderDesc(orderDesc);
			List<StatusCountModel> countList = statusMapper.count(statusCountQuery);
			Iterator<StatusCountModel> listIterator = countList.iterator();
			StatusCountModel statusCountModel = null;
			if (listIterator.hasNext()){
				statusCountModel = listIterator.next();
			}
			for (ProblemModel problemModel : list){
				while(statusCountModel != null && statusCountModel.getPid().equals(problemModel.getId())){
					if (StringUtils.equals(statusCountModel.getStatus(), StatusModel.StatusCode.AC)){
						problemModel.setAcCnt(statusCountModel.getCnt());
					}
					problemModel.setTryCnt(problemModel.getTryCnt()+statusCountModel.getCnt());
					if (listIterator.hasNext()){
						statusCountModel = listIterator.next();
					} else {
						statusCountModel = null;
					}
				}
			}
			return result.setRightValueReturn(list);
		} catch (Exception e){
			dbLoggerService.error("SELECT PROBLEM",e);
			return result.setErrorMsgReturn("select problem error");
		}
	}
	
	public ResultBase<Integer> delete(Integer pid) {
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			int cnt = problemMapper.delete(pid);
			dbLoggerService.info("DELETE PROBLEM", "DELETE PROBLEM "+pid);
			lc.del(pid);
			return result.setRightValueReturn(cnt);
		}catch (Exception e){
			dbLoggerService.error("UPDATE PROBLEM", e);
			return result.setErrorMsgReturn("delete problem error");
		}
	}

	public ResultBase<Integer> modifyPubic(Integer pid, Integer isPublic) {
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			ProblemModel problemModel = new ProblemModel();
			problemModel.setId(pid);
			problemModel.setIsPublic(isPublic);;
			int cnt = problemMapper.updatePublicByPid(problemModel);
			lc.del(pid);
			if(isPublic == 1){
				dbLoggerService.info("UPDATE PROBLEM", "PUBLIC PROBLEM "+pid);
			} else {
				dbLoggerService.info("UPDATE PROBLEM", "PRIVATE PROBLEM "+pid);
			}
			return result.setRightValueReturn(cnt);
		} catch (Exception e){
			dbLoggerService.error("UPDATE PROBLEM", e);
			return result.setErrorMsgReturn("update problem error");
		}
	}
}
