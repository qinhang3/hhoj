package cn.edu.gdut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.util.JudgeServerRoot;

@Controller
@RequestMapping("/judge")
public class JudgeController {
	@Autowired
	private StatusService statusService;
		
	@RequestMapping("/start")
	public void start(){
		Thread thread = new Thread(new JudgeServerRoot(2333));
		thread.start();
	}
}
