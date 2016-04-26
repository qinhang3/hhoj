package cn.edu.gdut.permission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.service.PermissionService;

public class CheckerBase {
	
	@Autowired
	private PermissionService permissionService;
	
	private List<String> needGroupSet;
	public List<String> getNeedGroupSet() {
		return needGroupSet;
	}
	public void setNeedGroupSet(List<String> needGroupSet) {
		this.needGroupSet = needGroupSet;
	}
	public boolean run(HttpServletRequest request,HttpServletResponse response){
		for (String group : needGroupSet){
			if (request.getAttribute("IN"+group)!=null) return true;
		}
		return false;
	}
}
