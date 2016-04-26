package cn.edu.gdut.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	private static ThreadLocal<Map<String, Cookie>> tl = new ThreadLocal<Map<String,Cookie>>();
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	    if (tl.get() != null){
	    	tl.get().put(name, cookie);
	    }
	}
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		if (tl.get()!=null) return tl.get();
		Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	public static String getValueByName(HttpServletRequest request, String name) {
		Cookie cookie = getCookieByName(request, name);
		if (cookie == null) return null;
		return cookie.getValue();
		
	}
	public static void reset(){
		tl.remove();
	}
}
