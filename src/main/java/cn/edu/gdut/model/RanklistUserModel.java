package cn.edu.gdut.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RanklistUserModel implements Comparable<RanklistUserModel>{
	private String username;
	private Long time;
	private Integer acCnt;
	private Map<Integer, RanklistProblemModel> info;

	public RanklistUserModel(String username) {
		super();
		this.username = username;
		time = 0L;
		acCnt = 0;
		info = new HashMap<Integer, RanklistProblemModel>();
	}

	public void add(StatusModel statusModel, Date startTime) {
		RanklistProblemModel ranklistProblemModel = info.get(statusModel.getPid());
		if (ranklistProblemModel == null) {
			ranklistProblemModel = new RanklistProblemModel();
			info.put(statusModel.getPid(), ranklistProblemModel);
		}
		if (ranklistProblemModel.getAcTime() != null) {
			return;
		}
		if (StatusModel.StatusCode.AC.equals(statusModel.getStatus())) {
			ranklistProblemModel.setAcTime(statusModel.getCreateTime().getTime() - startTime.getTime());
			acCnt++;
			time += (ranklistProblemModel.getAcTime() + 20L * 60L * 1000L * ranklistProblemModel.getNoAcCnt());
		} else if (
				StatusModel.StatusCode.PD.equals(statusModel.getStatus()) || 
				StatusModel.StatusCode.RJ.equals(statusModel.getStatus()) ||
				StatusModel.StatusCode.RN.equals(statusModel.getStatus()) 
				) {
			ranklistProblemModel.addPdCnt();
		} else {
			ranklistProblemModel.addNoAcCnt();
		}

	}

	public String getUsername() {
		return username;
	}

	public Integer getAcCnt() {
		return acCnt;
	}

	public Map<Integer, RanklistProblemModel> getInfo() {
		return info;
	}

	public Long getTime() {
		return this.time;
	}
	
	public String getAllTime(){
		return msToHMS(time);
	}

	@Override
	public int compareTo(RanklistUserModel o) {
		if (this.acCnt.equals(o.getAcCnt())){
			if (this.time.equals(o.time)) return 0;
			return this.time<o.time?-1:1;
		} else {
			return this.acCnt<o.acCnt?1:-1;
		}
	}
	
	private String msToHMS(long ms){
		ms /= 1000;
		long h = ms/60/60;
		long m = ms/60%60;
		long s = ms%60;
		return String.format("%02d:%02d:%02d", h,m,s);
	}
}