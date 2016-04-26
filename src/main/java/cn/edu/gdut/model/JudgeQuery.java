package cn.edu.gdut.model;

public class JudgeQuery {
	private String command;
	private Integer rid;
	private String status;
	private Integer runTime;
	private Integer runMemory;
	private String loginToken;
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

	public interface Command{
		public static String nextSolution = "nextSolution";
		public static String statusChange = "statusChange";
		public static String doLogin = "doLogin";
	}
	
	public interface StatusCode{
		public static String PD = "Pending";
		public static String RN = "Running";
		public static String AC = "Accepted";
		public static String WA = "Wrong Answer";
		public static String RE = "Runtime Error";
		public static String TLE = "Time Limit Error";
		public static String RJ = "Repending";
		public static String CE = "Compile Error";
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public Integer getRunMemory() {
		return runMemory;
	}

	public void setRunMemory(Integer runMemory) {
		this.runMemory = runMemory;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
}
