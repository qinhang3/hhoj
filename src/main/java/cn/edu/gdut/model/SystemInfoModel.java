package cn.edu.gdut.model;

import java.text.DecimalFormat;

public class SystemInfoModel {
	private Long totalMemory;
	private Long freeMemory;
	private Double freePercent;
	public Long getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(Long totalMemory) {
		this.totalMemory = totalMemory;
	}
	public Long getFreeMemory() {
		return freeMemory;
	}
	public void setFreeMemory(Long freeMemory) {
		this.freeMemory = freeMemory;
	}
	
	
	public SystemInfoModel init(){
		Runtime r = Runtime.getRuntime();
		totalMemory = r.totalMemory()/1024;
		freeMemory = r.freeMemory() / 1024;
		DecimalFormat df = new DecimalFormat("0.000");
		setFreePercent(Double.parseDouble(df.format(r.freeMemory()*100.00/r.totalMemory())));
		return this;
	}
	public Double getFreePercent() {
		return freePercent;
	}
	public void setFreePercent(Double freePercent) {
		this.freePercent = freePercent;
	}

}
