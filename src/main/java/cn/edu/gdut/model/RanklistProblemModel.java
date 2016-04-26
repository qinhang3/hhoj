package cn.edu.gdut.model;

public class RanklistProblemModel {
	private Long acTime = null;
	private int noAcCnt = 0;
	private Boolean fb = false;
	private int pdCnt = 0;

	public Long getAcTime() {
		return acTime;
	}

	public void addNoAcCnt() {
		noAcCnt++;
		
	}

	public void addPdCnt() {
		pdCnt++;
		
	}

	public void setAcTime(Long acTime) {
		this.acTime = acTime;
	}

	public int getNoAcCnt() {
		return noAcCnt;
	}

	public Boolean getFb() {
		return fb;
	}

	public void setFb(Boolean fb) {
		this.fb = fb;
	}

	public int getPdCnt() {
		return pdCnt;
	}
	
	public String getPrintStr(){
		StringBuilder stringBuilder = new StringBuilder();		
		if (acTime != null) stringBuilder.append(msToHMS(acTime));
		if (noAcCnt>0) {
			stringBuilder.append("(-");
			stringBuilder.append(noAcCnt);
			stringBuilder.append(")");
			
		}
		if(pdCnt>0){
			stringBuilder.append("[");
			stringBuilder.append(pdCnt);
			stringBuilder.append("]");
		}
		return stringBuilder.toString();
	}
	
	public String getColor(){
		if (acTime != null){
			return "success";
		} else if (noAcCnt > 0){
			return "danger";
		}
		return "";
	}
	
	private String msToHMS(long ms){
		ms /= 1000;
		long h = ms/60/60;
		long m = ms/60%60;
		long s = ms%60;
		return String.format("%02d:%02d:%02d", h,m,s);
	}
}
