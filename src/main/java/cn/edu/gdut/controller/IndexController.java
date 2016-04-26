package cn.edu.gdut.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.edu.gdut.mapper.BugMapper;
import cn.edu.gdut.model.BugModel;
import cn.edu.gdut.service.ConstantService;
import cn.edu.gdut.util.CookieUtil;
import cn.edu.gdut.util.HtmlEncodeUtil;
import cn.edu.gdut.util.JSonUtil;
import cn.edu.gdut.util.LocalCache;
import cn.edu.gdut.util.OJConstant;
import cn.edu.gdut.util.UserSession;

@Controller
@RequestMapping("/index")
public class IndexController extends ControllerBase{
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private ConstantService constantService;
	@Autowired
	private BugMapper bugMapper;
	
	private static LocalCache<String, List<String>> lc = new LocalCache<String, List<String>>("Index Pic List", 60*1000L);
	@RequestMapping
	public String index(Model model){
		List<String> list = lc.get("picList");
		if (list == null){
			String listStr = constantService.getAsString(OJConstant.Index.picList, "[\"http://gdutcode-hhoj.stor.sinaapp.com/index/55751e1410e34.jpg\"]");
			list = new Gson().fromJson(listStr, new TypeToken<List<String>>() {}.getType());
			lc.put(OJConstant.Index.picList, list);
		}
		addAttribute(model, "picList", list);
		return "index/index";
	}
	@RequestMapping("noPermission")
	public String noPermission(String url,Model model,HttpServletRequest request) throws UnsupportedEncodingException{
		if (StringUtils.isEmpty(url)){
			url = request.getAttribute("CONTEXTPATH").toString();
		}
		addAttribute(model, "url", URLEncoder.encode(url, "utf-8"));
		return "index/noPermission";
	}
	@RequestMapping("bug")
	public String bug(String url,Model model){
		addAttribute(model, "url", url);
		return "index/bug";
	}
	@RequestMapping(value = "bugSubmit",method = RequestMethod.POST)
	public String bug(@ModelAttribute("bugModel")BugModel bugModel,HttpServletRequest request){
		if (UserSession.getUsername() == null){
			bugModel.setUsername("Guest");
		} else {
			bugModel.setUsername(UserSession.getUsername());
		}
		bugModel.setDescription(HtmlEncodeUtil.toHtml(bugModel.getDescription()));
		bugModel.setUrl(HtmlEncodeUtil.toHtml(bugModel.getUrl()));
//		bugModel.setIpAddress(request.getRemoteAddr());
		bugModel.setIpAddress(getIpAddr(request));
		bugMapper.insert(bugModel);
		return "redirect:/index.htm";
	}
	
	@RequestMapping("isAdmin")
	public void isAdmin(HttpServletRequest request, HttpServletResponse response,Model model){
		boolean flag = request.getAttribute("INadmin")!=null;
		if(flag){
			JSonUtil.writeToResponse("Yes", response);
		} else {
			JSonUtil.writeToResponse("No", response);
		}
	}
	
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		return ip;
	}
}
