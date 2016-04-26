package cn.edu.gdut.permission;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.service.DBLoggerService;
import cn.edu.gdut.service.PermissionService;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.util.QueryUrlUtil;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.UserSession;

public class PermissionCheckService {

	private Map<String, CheckerBase> map;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private DBLoggerService dbLoggerService;
	@Autowired
	private StatusService statusService;

	public boolean excute(HttpServletRequest request, HttpServletResponse response) {
		String REQUESTURI = request.getRequestURI();
		String CONTEXT = request.getContextPath();
		REQUESTURI = REQUESTURI.substring(CONTEXT.length());
		String SERVLETNAME = null;
		String METHODNAME = null;
		try {
			SERVLETNAME = REQUESTURI.split("/")[1];
			METHODNAME = REQUESTURI.split("/")[2].split("\\.")[0];
		} catch (Exception e) {

		}
		addAttribute(request, "CONTEXTPATH", CONTEXT);
		addAttribute(request, "SERVLETNAME", SERVLETNAME);
		addAttribute(request, "METHODNAME", METHODNAME);
		addAttribute(request, "ENGINDEX", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		addAttribute(request, "QUERYURL", QueryUrlUtil.buildQueryUrlEncode(request, "utf-8"));
		addAttribute(request, "FULLURL", QueryUrlUtil.getFullUrl(request));
		addAttribute(request, "USERMODEL", UserSession.get());
		
		String username = UserSession.getUsername();
		if (StringUtils.isEmpty(username)){
			addAttribute(request, "INguest", Boolean.TRUE);
		} else {
			ResultBase<List<String>> permissionResult = permissionService.getGroupByUsername(username);
			if (permissionResult.isSuccess() && permissionResult.getValue() != null){
				for (String str : permissionResult.getValue()) {
					addAttribute(request, "IN"+str, Boolean.TRUE);
				}
			}
			ResultBase<Map<Integer,Boolean>> acPidResult = statusService.getAcByUsername(username);
			if (acPidResult.isSuccess() && acPidResult.getValue() != null){
				addAttribute(request, "acPidList", acPidResult.getValue());
			}
		}

		CheckerBase checker = map.get(SERVLETNAME);
		if (checker == null) {
			return true;
		}
		
		try{
			Boolean hasPermission = checker.run(request, response);
			if (hasPermission){
				return true;
			} else {
				noPermission(request, response);
				return false;
			}
		} catch (Exception e) {
			dbLoggerService.error("PERMISSION CHECK", e);
			return false;
		}
	}

	private void noPermission(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		response.sendRedirect(request.getContextPath()+"/index/noPermission.htm?url=" + request.getAttribute("QUERYURL"));
	}

	public void setMap(Map<String, CheckerBase> map) {
		this.map = map;
	}

	private void addAttribute(HttpServletRequest request, String key, Object value) {
		request.setAttribute(key, value);
	}

}
