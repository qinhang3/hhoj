package cn.edu.gdut.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class StatusModel {
	private Integer id;
	private Integer pid;
	private String code;
	private String language;
	private String status;
	private String username;
	private Date createTime;
	private Date judgeTime;
	private Integer runMemory;
	private Integer runTime;
	private Integer codeLength;
	private Integer cid;
	private String compileInfo;
	private String judgeName;
	public String getCompileInfo() {
		return compileInfo;
	}
	public void setCompileInfo(String compileInfo) {
		this.compileInfo = compileInfo;
	}
	public String getJudgeName() {
		return judgeName;
	}
	public void setJudgeName(String judgeName) {
		this.judgeName = judgeName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
		setCodeLength(code.length());
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusShort(){
		switch (status) {
		case StatusCode.AC:
			return "AC";
		case StatusCode.TLE:
			return "TLE";
		case StatusCode.WA:
			return "WA";
		case StatusCode.RE:
			return "RE";
		case StatusCode.CE:
			return "CE";
		case StatusCode.MLE:
			return "MLE";
		case StatusCode.PE:
			return "PE";
		case StatusCode.OLE:
			return "OLE";
		case StatusCode.JE:
			return "JE";
		case StatusCode.SE:
			return "SE";
		case StatusCode.PD:
			return "PD";
		case StatusCode.RN:
			return "RN";
		case StatusCode.RJ:
			return "RJ";
		default:
			return status;
		}
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getJudgeTime() {
		return judgeTime;
	}
	public void setJudgeTime(Date judgeTime) {
		this.judgeTime = judgeTime;
	}
	public Integer getRunMemory() {
		return runMemory;
	}
	public void setRunMemory(Integer runMemory) {
		this.runMemory = runMemory;
	}
	public Integer getRunTime() {
		return runTime;
	}
	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}
	public Integer getCodeLength() {
		return codeLength;
	}
	public void setCodeLength(Integer codeLength) {
		this.codeLength = codeLength;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public interface StatusCode{
		public static String CE = "Compile Error";
		public static String TLE = "Time Limit Exceeded";
		public static String MLE = "Memory Limit Exceeded";
		public static String OLE = "Output Limit Exceeded";
		public static String RE = "Runtime Error";
		public static String WA = "Wrong Answer";
		public static String AC = "Accepted";
		public static String PE = "Presentation Error";
		public static String SE = "System Error";
		public static String PD = "Pending";
		public static String RN = "Running";
		public static String RJ = "Repending";
		public static String JE = "Judge Error";
	}
	public interface LanguageCode{
		public static String CPP = "CPP";
		public static String C = "C";
		public static String JAVA = "JAVA";
		//public static String CPP11 = "CPP11";
	}
	public static List<String> getAllStatusList(){
		List<String> list = new ArrayList<String>();
		list.add(StatusCode.CE);
		list.add(StatusCode.TLE);
		list.add(StatusCode.MLE);
		list.add(StatusCode.OLE);
		list.add(StatusCode.RE);
		list.add(StatusCode.WA);
		list.add(StatusCode.AC);
		list.add(StatusCode.PE);
		list.add(StatusCode.SE);
		list.add(StatusCode.PD);
		list.add(StatusCode.RN);
		list.add(StatusCode.RJ);
		list.add(StatusCode.JE);
		return list;
	}
	public static List<String> getAllLanguageList(){
		List<String> list = new ArrayList<String>();
		list.add(LanguageCode.C);
		list.add(LanguageCode.CPP);
		list.add(LanguageCode.JAVA);
		//list.add(LanguageCode.CPP11);
		return list;
	}
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public String getCodeHtml(){
		if (code == null) return "";
		return toHtml(code);
	}
	
	public String getCompileInfoHtml(){
		if(compileInfo == null) return "";
		return toHtml(compileInfo);
		
	}
	private String toHtml(String code){
		return code.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
