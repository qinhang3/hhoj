package cn.edu.gdut.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.gdut.model.ContestEntiry;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.model.UserContestEntity;
import cn.edu.gdut.service.ConstantService;
import cn.edu.gdut.service.ContestService;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.util.ResultBase;

@Controller
@RequestMapping("/status")
public class StatusController extends ControllerBase {
	@Autowired
	private StatusService statusService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private ContestService contestService;
	
	@RequestMapping("/problem")
	public String problem(Integer pid, String username, String language, String status, @RequestParam(value = "page") Integer page,Model model) {
		Map<String,Object> map = new HashMap<String, Object>();
		if (pid != null){
			map.put("pid", pid);
		}
		if (username != null){
			map.put("username", username);
		}
		if (language != null){
			map.put("language", language);
		}
		if (status != null){
			map.put("status", status);
		}
		if (page != null){
			map.put("page", page);
		}
		addAttributeMap(model, map);
		
		addAttribute(model, "allStatus", StatusModel.getAllStatusList());
		addAttribute(model, "allLanguage", StatusModel.getAllLanguageList());
		return "status/problem";
	}
	
	@RequestMapping("ajaxProblem")
	public String ajaxProblem(Integer pid, String username, String language, String status, @RequestParam(value = "page") Integer page,Model model) {
		ResultBase<List<StatusModel>> result = statusService.selectByQuery(username, pid, language, null, page, status);
		addAttribute(model, "list", result.getValue());
		return "status/ajaxProblem";
	}
	
	@RequestMapping("code")
	public String code(Integer rid,Model model){
		ResultBase<StatusModel> result = statusService.getCode(rid);
		if (result.isSuccess()){
			addAttribute(model, "model", result.getValue());
			return "status/code";
		} else {
			addErrorMsg(model, result.getMsg());
			return "error";
		}
	}

	@RequestMapping("/contest")
	public String contest(Model model, @RequestParam("cid")Integer cid) {
		ResultBase<ContestEntiry> contestEntityResult = contestService.getContestEntity(cid);
		if (!contestEntityResult.isSuccess()){
			addErrorMsg(model, contestEntityResult.getMsg());
			return "error";
		}
		addAttribute(model, "model", contestEntityResult.getValue());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		addAttributeMap(model, map);
		return "status/contest";
	}

	@RequestMapping("/ajaxContest")
	public String statusUpdate(@RequestParam("cid") Integer cid,Model model,HttpServletResponse response) {
		ResultBase<UserContestEntity> userContestEntityResult = statusService.getUserContestEntity(cid);
		if (!userContestEntityResult.isSuccess()){
			addErrorMsg(model, userContestEntityResult.getMsg());
			return "error";
		}
		ResultBase<ContestEntiry> contestEntityResult = contestService.getContestEntity(cid);
		if (!contestEntityResult.isSuccess()){
			addErrorMsg(model, contestEntityResult.getMsg());
			return "error";
		}
		addAttribute(model, "statusModel", userContestEntityResult.getValue());
		addAttribute(model, "model", contestEntityResult.getValue());
		return "status/ajaxContest";
	}
}
