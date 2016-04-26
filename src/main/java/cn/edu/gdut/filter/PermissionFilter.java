package cn.edu.gdut.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.gdut.permission.PermissionCheckService;
import cn.edu.gdut.util.SpringUtil;

public class PermissionFilter extends FilterBase{
	
	private PermissionCheckService permissionCheckService;	
	
	public PermissionFilter() {
		super();
//		constantService = (ConstantService)SpringUtil.getContext().getBean("constantService");
		permissionCheckService = (PermissionCheckService)SpringUtil.getContext().getBean("permissionCheckService");
	}
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (permissionCheckService.excute(request, response)){
			chain.doFilter(request, response);
		} else {
			return;
		}
	}

}
