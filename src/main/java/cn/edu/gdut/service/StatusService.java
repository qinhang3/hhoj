package cn.edu.gdut.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.mapper.StatusMapper;
import cn.edu.gdut.model.ContestEntiry;
import cn.edu.gdut.model.JudgeModel;
import cn.edu.gdut.model.RanklistModel;
import cn.edu.gdut.model.RejudgeQuery;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.model.StatusQuery;
import cn.edu.gdut.model.UserContestEntity;
import cn.edu.gdut.timetask.PageviewCount;
import cn.edu.gdut.util.LocalCache;
import cn.edu.gdut.util.OJConstant;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.UserSession;

@Service
public class StatusService {
	@Autowired
	private StatusMapper statusMapper;
	@Autowired
	private DBLoggerService dbLoggerService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private ContestService contestService;

	private static LocalCache<String, UserContestEntity> lc = new LocalCache<String,UserContestEntity>("UserContestEntity",30*1000L);
	private static LocalCache<Integer, RanklistModel> rankLc = new LocalCache<Integer, RanklistModel>("Ranklist",60*1000L);
	private static LocalCache<String, Map<Integer,Boolean>> acPidLc = new LocalCache<String, Map<Integer,Boolean>>("AcPidList",30*1000L);
	private static LocalCache<String, Long> lastSubmitTime = new LocalCache<String, Long>("LastSubmitTime",30*1000L);
	
	public ResultBase<Integer> submit(String language,String code,Integer pid, Integer cid){
		synchronized (UserSession.get()) {
			ResultBase<Integer> result = new ResultBase<Integer>();
			Long nowTime = System.currentTimeMillis();
			Long lastTime = lastSubmitTime.get(UserSession.getUsername());
			if (lastTime != null){
				int calmDownTime = constantService.getAsInteger(OJConstant.Query.CALMDOWNTIME, 1000);
				if (lastTime + 1L * calmDownTime > nowTime){
					result.setMsg("You can't submit twice in "+calmDownTime + " ms.");
					return result.setRightValueReturn(0);
				}
			}
			
			StatusModel statusModel = new StatusModel();
			statusModel.setLanguage(language);
			statusModel.setUsername(UserSession.getUsername());
			statusModel.setCode(code);
			statusModel.setPid(pid);
			statusModel.setCid(cid);
			try{
				int cnt = statusMapper.insert(statusModel);
				if (cid != null){
					//竞赛中的提交 需要维护cache
					String lcKey = localCacheKey(cid); 
					lc.del(lcKey);
				}
				lastSubmitTime.put(UserSession.getUsername(), System.currentTimeMillis());
				return result.setRightValueReturn(cnt);
			} catch (Exception e){
				dbLoggerService.error("INSERT STATUS", e);
				return result.setErrorMsgReturn("submit error");
			}
		}
	}
	
	public ResultBase<StatusModel> selectById(Integer rid){
		ResultBase<StatusModel> result = new ResultBase<StatusModel>();
		try{
			StatusModel statusModel = statusMapper.selectById(rid);
			if (statusModel == null || statusModel.getCid()!=null) {
				return result.setErrorMsgReturn("status not found");
			} else {
				return result.setRightValueReturn(statusModel);
			}
		} catch (Exception e){
			dbLoggerService.error("SELECT STATUS", e);
			return result.setErrorMsgReturn("select status error");
		}
	}
	
	public ResultBase<List<StatusModel>> selectByQuery(String username,Integer pid,String language,Integer cid,Integer page,String status){
		Integer pageSize = constantService.getAsInteger(OJConstant.Query.PAGESIZE, 20);
		return selectByQuery(username, pid, language, cid, page, status, pageSize);
	}
	
	private ResultBase<List<StatusModel>> selectByQuery(String username,Integer pid,String language,Integer cid,Integer page,String status,Integer pageSize){
		StatusQuery statusQuery = new StatusQuery(pageSize);
		statusQuery.setCid(cid);
		statusQuery.setLanguage(language);
		statusQuery.setPage(page);
		statusQuery.setStatus(status);
		statusQuery.setUsername(username);
		statusQuery.setPid(pid);
		ResultBase<List<StatusModel>> result = new ResultBase<List<StatusModel>>();
		try{
			List<StatusModel> list = statusMapper.selectByQuery(statusQuery);
			return result.setRightValueReturn(list);
		} catch (Exception e){
			dbLoggerService.error("SELECT STATUS", e);
			return result.setErrorMsgReturn("select status error");
		}
	}
	
	public ResultBase<String> judge(Integer id,String status,Integer time,Integer memory,String comInfo,String judgeName){
		PageviewCount.judge();
		ResultBase<String> result = new ResultBase<String>();
		try{
			if(id == null){
				return result.setErrorMsgReturn("rid is required");
			}
			StatusModel statusModel = statusMapper.selectById(id);
			if (
				!StatusModel.StatusCode.PD.equals(statusModel.getStatus()) &&
				!StatusModel.StatusCode.RN.equals(statusModel.getStatus()) &&
				!StatusModel.StatusCode.RJ.equals(statusModel.getStatus()) 
			){
				return result.setRightValueReturn("solution has been judged");
			}
			statusModel.setStatus(status);
			statusModel.setRunMemory(memory);
			statusModel.setRunTime(time);
			statusModel.setCompileInfo(comInfo);
			statusModel.setJudgeName(judgeName);
			int cnt = statusMapper.judged(statusModel);
			if (cnt != 1){
				return result.setErrorMsgReturn("update failed");
			}
			if (statusModel.getCid() != null){
				String lcKey = localCacheKey(statusModel.getCid(),statusModel.getUsername());
				UserContestEntity userContestEntity = lc.get(lcKey);
				if (userContestEntity!=null){
					List<StatusModel> list = userContestEntity.getStatusList().get(statusModel.getPid());
					for (int i=0;i<list.size();i++){
						if (list.get(i).getId().equals(statusModel.getId())){
							list.set(i, statusModel);
							return result.setRightValueReturn("update success");
						}
					}
					//如果走到这里，一定是cache悲剧
					lc.del(lcKey);
				}
			} else {
				if (StringUtils.equals(statusModel.getStatus(),StatusModel.StatusCode.AC)){
					acPidLc.del(statusModel.getUsername());
				}
			}
			return result.setRightValueReturn("update success");
		} catch (Exception e){
			dbLoggerService.error("JUDGE STATUS",e);
			return result.setErrorMsgReturn("judge error");
		}
	}

	public ResultBase<Integer> selectNumByQuery(String username, Integer pid, String language, Integer cid, String status) {
		StatusQuery statusQuery = new StatusQuery(constantService.getAsInteger(OJConstant.Query.PAGESIZE, 20));
		statusQuery.setCid(cid);
		statusQuery.setLanguage(language);
		statusQuery.setStatus(status);
		statusQuery.setUsername(username);
		statusQuery.setPid(pid);
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			Integer num = statusMapper.selectNumberByQuery(statusQuery);
			return result.setRightValueReturn(num);
		} catch (Exception e){
			dbLoggerService.error("SELECT STATUS", e);
			return result.setErrorMsgReturn("select status error");
		}
	}
	
	public ResultBase<UserContestEntity> getUserContestEntity(Integer cid){
		String username = UserSession.getUsername();
		ResultBase<UserContestEntity> result = new ResultBase<UserContestEntity>();
		String lcKey = localCacheKey(cid);
		UserContestEntity userContestEntity = lc.get(lcKey);
		if (userContestEntity != null){
			return result.setRightValueReturn(userContestEntity);
		}
		synchronized (UserSession.get()) {
			userContestEntity = lc.get(lcKey);
			if (userContestEntity != null){
				return result.setRightValueReturn(userContestEntity);
			}
			userContestEntity = new UserContestEntity(username, cid);
			ResultBase<List<StatusModel>> statusResult = selectByQuery(username, null, null, cid, 0, null,/*INF*/9999);
			if (!statusResult.isSuccess()){
				return result.setErrorMsgReturn(statusResult.getMsg());
			}
			for(StatusModel statusModel:statusResult.getValue()){
				userContestEntity.addStatusModel(statusModel);
			}
			lc.put(lcKey, userContestEntity);
			return result.setRightValueReturn(userContestEntity);
		}
	}
	
	
	
	public ResultBase<RanklistModel> getRanklistModel(Integer cid){
		ResultBase<RanklistModel> result = new ResultBase<RanklistModel>();
		ResultBase<ContestEntiry> contestResult = contestService.getContestEntity(cid);
		if (!contestResult.isSuccess()){
			return result.setErrorMsgReturn(contestResult.getMsg());
		}
		RanklistModel ranklistModel = rankLc.get(cid);
		if (ranklistModel != null){
			return result.setRightValueReturn(ranklistModel);
		}
		synchronized (contestResult.getValue()) {
			ranklistModel = rankLc.get(cid);
			if (ranklistModel != null){
				return result.setRightValueReturn(ranklistModel);
			}
			try{
				List<StatusModel> list = statusMapper.selectForRanklist(cid);
				ranklistModel = RanklistModel.buildRanklistModel(contestResult.getValue(), list);
				rankLc.put(cid, ranklistModel);
				return result.setRightValueReturn(ranklistModel);
			} catch (Exception e){
				dbLoggerService.error("SELECT ERROR", e);
				return result.setErrorMsgReturn("select status error");
			}			
		}
	}
	
	public ResultBase<List<JudgeModel>> getJudgeModel(){
		ResultBase<List<JudgeModel>> result = new ResultBase<List<JudgeModel>>();
		try{
			List<JudgeModel> judgeModels = statusMapper.getForJudge();
			return result.setRightValueReturn(judgeModels);
		} catch (Exception e){
			dbLoggerService.error("SELECT ERROR", e);
			return result.setErrorMsgReturn("select status error");
		}
	}

	public ResultBase<StatusModel> getCode(Integer rid) {
		ResultBase<StatusModel> result = new ResultBase<StatusModel>();
		try{
			StatusModel statusModel = statusMapper.getCode(rid);
			if (statusModel == null){
				return result.setErrorMsgReturn("code not found");
			}
			return result.setRightValueReturn(statusModel);
		} catch (Exception e){
			dbLoggerService.error("SELECT STATUS", e);
			return result.setErrorMsgReturn("get code error");
		}
	}
	
	public ResultBase<Integer> rejudge(Integer rid,Integer pid,Integer cid,Boolean includeAc){
		ResultBase<Integer> result = new ResultBase<Integer>();
		RejudgeQuery query = new RejudgeQuery();
		query.setRid(rid);
		query.setPid(pid);
		query.setCid(cid);
		query.setIncludeAc(includeAc);
		try{
			int cnt = statusMapper.reJudge(query);
			if (cid != null){
				rankLc.del(cid);
			}
			return result.setRightValueReturn(cnt);
		} catch (Exception e){
			dbLoggerService.error("UPDATE STATUS", e);
			return result.setErrorMsgReturn("update status error");
		}
	}
	
	public ResultBase<Map<Integer,Boolean>> getAcByUsername(String username){
		ResultBase<Map<Integer,Boolean>> result = new ResultBase<Map<Integer,Boolean>>();
		Map<Integer,Boolean> map = acPidLc.get(username);
		if (map == null){
			try{
				List<Integer> list = statusMapper.getAcByUsername(username);
				map = new HashMap<Integer, Boolean>();
				if (list != null){
					for(Integer pid:list){
						map.put(pid, Boolean.TRUE);
					}
				}
				acPidLc.put(username, map);
			} catch (Exception e){
				dbLoggerService.error("SELECT STATUS", e);
				return result.setRightValueReturn(new HashMap<Integer,Boolean>());
			}
		}
		return result.setRightValueReturn(map);
	}
		
	private String localCacheKey(Integer cid){
		return cid+"|"+UserSession.getUsername();
	}
	private String localCacheKey(Integer cid,String username){
		return cid+"|"+username;
	}
}
