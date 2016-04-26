package cn.edu.gdut.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.gdut.service.ConstantService;
import cn.edu.gdut.util.CheckCodeUtil;
import cn.edu.gdut.util.JSonUtil;
import cn.edu.gdut.util.OJConstant;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("/checkcode")
public class CheckCodeController {
	
	@Autowired
	private ConstantService constantService;
	
	@RequestMapping(value = "/getPic")
	public void getPic(@RequestParam("topic") String topic,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		CheckCodeUtil.getPic(topic, httpServletRequest, httpServletResponse,constantService.getAsInteger(OJConstant.CheckCode.length, 4));
	}

	@RequestMapping(value = "/checkPic")
	public void checkPic(HttpServletRequest request, HttpServletResponse response,@RequestParam("topic") String topic) {
		JsonObject jo = new JsonObject();
		if (CheckCodeUtil.checkCode(topic, request, false)) {
			jo.addProperty("value", "true");
		} else {
			jo.addProperty("value", "false");
		}
		JSonUtil.writeToResponse(jo, response);
	}
}
