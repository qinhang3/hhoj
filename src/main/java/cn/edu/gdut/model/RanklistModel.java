package cn.edu.gdut.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RanklistModel {
	private long updateTime = 0;
	private List<RanklistUserModel> rank;
	private Map<Integer,Long> fbTime;

	public Map<Integer, Long> getFbTime() {
		return fbTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public List<RanklistUserModel> getRank() {
		return rank;
	}

	public static RanklistModel buildRanklistModel(ContestEntiry contestEntiry, List<StatusModel> statusModels) {
		RanklistModel ranklistModel = new RanklistModel();
		ranklistModel.updateTime = System.currentTimeMillis() - contestEntiry.getStartTime().getTime();
		ranklistModel.rank = new ArrayList<RanklistUserModel>();
		ranklistModel.fbTime = new HashMap<Integer, Long>();

		RanklistUserModel ranklistUserModel = null;
		for (StatusModel statusModel : statusModels) {
			if (ranklistUserModel == null || !ranklistUserModel.getUsername().equals(statusModel.getUsername())) {
				if (ranklistUserModel != null) {
					ranklistModel.rank.add(ranklistUserModel);
				}
				ranklistUserModel = new RanklistUserModel(statusModel.getUsername());
			}
			if (statusModel.getStatus().equals(StatusModel.StatusCode.AC)){
				Long nowTime = ranklistModel.fbTime.get(statusModel.getPid());
				Long newTime = statusModel.getCreateTime().getTime()-contestEntiry.getStartTime().getTime();
				if (nowTime == null || nowTime > newTime){
					ranklistModel.fbTime.put(statusModel.getPid(), newTime);
				}
			}
			ranklistUserModel.add(statusModel, contestEntiry.getStartTime());
		}
		if (ranklistUserModel != null) {
			ranklistModel.rank.add(ranklistUserModel);
		}
		
		Collections.sort(ranklistModel.rank);
		return ranklistModel;
	}
}