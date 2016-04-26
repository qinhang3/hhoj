package cn.edu.gdut.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.service.UserinfoService;
import cn.edu.gdut.timetask.PageviewCount;
import cn.edu.gdut.util.CookieUtil;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.SessionUtil;
import cn.edu.gdut.util.SpringUtil;
import cn.edu.gdut.util.UserSession;

public class LoginFilter extends FilterBase{
	
	private UserinfoService userinfoService = (UserinfoService) SpringUtil.getBean("userinfoService");
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		PageviewCount.add();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		request.setAttribute("SESSIONID", request.getSession().getId());
		String username = (String)request.getSession().getAttribute("username");
		if (username != null){
			ResultBase<UserinfoModel> result = userinfoService.selectByUsername(username);
			if (result.isSuccess()){
				UserSession.put(result.getValue());
			}
		}
		request.setAttribute("errmsg",request.getParameter("errmsg"));
		request.setAttribute("sucmsg",request.getParameter("sucmsg"));
		chain.doFilter(request, response);
		UserinfoModel userinfoModel = UserSession.get();
		if (userinfoModel != null){
			SessionUtil.setAttribute(request, "username", userinfoModel.getUsername());
		} else {
			SessionUtil.removeAttribute(request, "username");
		}
		request.getSession().setMaxInactiveInterval(-1);
		UserSession.del();
		CookieUtil.reset();
	}
}
