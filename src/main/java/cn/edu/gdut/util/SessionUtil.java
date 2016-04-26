package cn.edu.gdut.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
	public static void setAttribute(HttpServletRequest request,String key,Object value){
		request.getSession().setAttribute(key, value);
	}
	public static Object getAttrbute(HttpServletRequest request,String key){
		return request.getSession().getAttribute(key);
	}
	public static void removeAttribute(HttpServletRequest request,String key){
		request.getSession().removeAttribute(key);
	}
	public static void invalidate(HttpServletRequest request){
		request.getSession().invalidate();
	}
}
