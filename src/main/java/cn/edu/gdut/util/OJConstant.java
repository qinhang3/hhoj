package cn.edu.gdut.util;

public class OJConstant {
	public interface Username{
		public final static String minLenUsername = "Username_minLenUsername";
		public final static String maxLenUsername = "Username_maxLenUsername";
	}

	
	public interface CheckCode{
		public final static String length = "Checkcode_length";
	}
	
	public interface CheckCodeKey{
		public final static String register = "Checkcodekey_register";
		public final static String resetpw = "Checkcodekey_resetpw";
	}
	
	public interface SessionKey{
		public static final String RESETPASSTOKEN = "resetPw"; 
		public static final String CHECKCODEKEY = "checkcode";// 放到session中的key
		public static final String RESETPASSWORDUSERNAME = "resetPwUsername";
		public static final String RESETPASSTIME = "resetPwTime";
		public static final String RESETPASSKEY = "resetPwKey";
		public static final String CERTIFYEMAILTIME = "certifyEmailTime";
		public static final String CERTIFYEMAILTOKEN = "certifyEmailToken";
	}
	
	public interface Index{
		public static final String picList = "Pic_list";
	}
	
	public interface Query{
		public static final String PAGESIZE = "Page_size";
		public static final String CALMDOWNTIME = "Calmdown_time";
	}
}
