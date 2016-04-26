package cn.edu.gdut.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public class QueryUrlUtil {
	public static String buildQueryUrl(HttpServletRequest request) {
		//String context = request.getContextPath();
		String uri = request.getRequestURI();
		String param = request.getQueryString();
		if (param != null){
			return uri + "?" + param;
		} else {
			return uri;
		}
	}
	public static String buildQueryUrlEncode(HttpServletRequest request,String code){
		try {
			return URLEncoder.encode(buildQueryUrl(request),code);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	public static String getFullUrl(HttpServletRequest request){
		String uri = request.getScheme() + "://" +
	             request.getServerName() + 
	             ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
	             request.getRequestURI() +
	            (request.getQueryString() != null ? "?" + request.getQueryString() : "");
		return uri;
	}
}
