package cn.edu.gdut.util;

import cn.edu.gdut.model.UserinfoModel;

public class UserSession {
	private static ThreadLocal<UserinfoModel> TL = new ThreadLocal<UserinfoModel>();
	public static void put(UserinfoModel userinfoModel){
		TL.set(userinfoModel);
	}
	public static UserinfoModel get(){
		return TL.get();
	}
	public static String getUsername(){
		if (get()==null){
			return null;
		} else {
			return get().getUsername();
		}
	}
	public static void del(){
		TL.remove();
	}
}
