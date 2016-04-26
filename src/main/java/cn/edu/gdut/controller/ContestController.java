package cn.edu.gdut.controller;


import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.gdut.form.ProblemSubmitForm;
import cn.edu.gdut.model.ContestEntiry;
import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.model.RanklistModel;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.service.ContestService;
import cn.edu.gdut.service.ProblemService;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.util.CookieUtil;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.UserSession;

@Controller
@RequestMapping("/contest")
public class ContestController extends ControllerBase{
	@Autowired
	private ContestService contestService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private ProblemService problemService;
	
	private final List<Integer> isPublicList = Arrays.asList(0,1,2);

	@RequestMapping("/problems")
	public String problems(Model model,@RequestParam("cid")Integer cid){
		if (!buildDefaultModel(model, cid)) return "error";
		return "contest/problems";
	}
	
	@RequestMapping("/show")
	public String show(Model model,@RequestParam("cid") String cid,@RequestParam("index") String index,HttpServletRequest request){
		Integer cidInt = Integer.parseInt(cid);
		Integer indexInt = Integer.parseInt(index);
		
		ResultBase<ContestEntiry> result = contestService.getContestEntity(cidInt);
		if (!result.isSuccess()){
			addErrorMsg(model, result.getMsg());
			return "error";
		}
		if (result.getValue() == null){
			addErrorMsg(model, "contest not found");
			return "error";
		}
		if (result.getValue().getProblemModels().size() <= indexInt){
			addErrorMsg(model, "no such problem");
			return "error";
		}
		addAttribute(model, "model", result.getValue());
		addAttribute(model, "problemModel", result.getValue().getProblemModels().get(indexInt));
		addAttribute(model, "pid", (char)('A'+indexInt));
		addAttribute(model, "cid", cidInt);
		addAttribute(model, "allLanguage", StatusModel.getAllLanguageList());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastLanguage",CookieUtil.getValueByName(request, "lastLanguage"));
		addAttributeMap(model, map);
		return "contest/show";		
	}
	
	@RequestMapping("list")
	public String list(Model model,@RequestParam("page")Integer page){
		ResultBase<List<ContestModel>> result = contestService.getContestList(page,isPublicList);
		if (result.isSuccess()){
			addAttribute(model, "list", result.getValue());
			addAttribute(model, "page", page);
			return "contest/list";
		} else {
			addErrorMsg(model, "error");
			return "error";
		}
	}
	
	@RequestMapping(value = "doSubmit", method = RequestMethod.POST)
	public String doSubmit(Model model,@ModelAttribute("submitForm")ProblemSubmitForm submitForm,HttpServletResponse response){
		if (UserSession.get() == null || UserSession.get().getEmailStatus() != UserinfoModel.EmailStatus.PASS){
			return "error";
		}
		Integer pidInt = Integer.parseInt(submitForm.getPid());
		Integer cidInt = Integer.parseInt(submitForm.getCid());
		Integer indexInt = submitForm.getIndex().charAt(0)-'A';
		
		ResultBase<ContestEntiry> result = contestService.getContestEntity(cidInt);
		if (!result.isSuccess()){
			addErrorMsg(model, result.getMsg());
			return "error";
		}
		if (result.getValue() == null){
			addErrorMsg(model, "contest not found");
			return "error";
		}
		if (result.getValue().getProblemModels().size() <= indexInt){
			addErrorMsg(model, "no such problem");
			return "error";
		}
		if (!result.getValue().getProblemModels().get(indexInt).getId().equals(pidInt)){
			addErrorMsg(model, "param error,reflash and retry");
			return "error";
		}
		if (result.getValue().getEndTime().before(new Date())){
			addErrorMsg(model, "contest is end");
			return "error";
		}
		StatusModel statusModel = new StatusModel();
		statusModel.setCode(submitForm.getCode());
		statusModel.setPid(pidInt);
		statusModel.setUsername(UserSession.getUsername());
		statusModel.setLanguage(submitForm.getLanguage());
		statusModel.setCid(cidInt);
		
		ResultBase<Integer> r = statusService.submit(submitForm.getLanguage(), submitForm.getCode(), pidInt, cidInt);
		if (r.isSuccess()){
			if (r.getValue()==0){
				addAttribute(model, "model", submitForm);
				addErrorMsg(model, r.getMsg());
				return "status/reSubmit";
			} else {
				CookieUtil.addCookie(response, "lastLanguage", submitForm.getLanguage(), 365*24*60*60);
				return "redirect:/status/contest.htm?cid="+submitForm.getCid();
			}
		} else {
			addErrorMsg(model, r.getMsg());
			return "error";
		}
	}
	
	@RequestMapping("index")
	public String index(Model model,@RequestParam("cid")Integer cid){
		if (!buildDefaultModel(model, cid)) return "error";
		return "contest/index";
	}
	
	@RequestMapping("standing")
	public String stantding(Model model,@RequestParam("cid")Integer cid){
		if (!buildDefaultModel(model, cid)) return "error";
		ResultBase<RanklistModel> result = statusService.getRanklistModel(cid);
		if (result.isSuccess()){
			addAttribute(model, "standingModel", result.getValue());
			return "contest/standing";
		} else {
			addErrorMsg(model, result.getMsg());
			return "error";
		}
	}
	
	private boolean buildDefaultModel(Model model,Integer cid) {
		ResultBase<ContestEntiry> result = contestService.getContestEntity(cid);
		if(!result.isSuccess() || result.getValue() == null){
			addErrorMsg(model, "contest not found");
			return false;
		}
		addAttribute(model, "model", result.getValue());
		addAttribute(model, "cid", cid);
		return true;
	}
}
