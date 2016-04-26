package cn.edu.gdut.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.gdut.form.ProblemSubmitForm;
import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.service.ConstantService;
import cn.edu.gdut.service.ProblemService;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.util.CookieUtil;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.UserSession;

@Service
@RequestMapping("/problem")
public class ProblemController extends ControllerBase {
	@Autowired
	private ProblemService problemService;
	@Autowired
	private StatusService statusService;
	@Autowired
	private ConstantService constantService;

	@RequestMapping("/show")
	public String show(@RequestParam("pid") Integer pid, Model model,HttpServletRequest request) {
		ResultBase<ProblemModel> result = problemService.selectById(pid);
		if (!result.isSuccess()) {
			addErrorMsg(model, result.getMsg());
			return "error";
		}
		ProblemModel problemModel = result.getValue();
		addAttribute(model, "model", problemModel);
		addAttribute(model, "allLanguage", StatusModel.getAllLanguageList());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastLanguage",CookieUtil.getValueByName(request, "lastLanguage"));
		addAttributeMap(model, map);
		return "problem/show";
	}

	@RequestMapping("/list")
	public String list(Model model,@RequestParam("page")Integer page) {
		ResultBase<List<ProblemModel>> result = problemService.selectList(1, page, false);
		if (result.isSuccess()){
			addAttribute(model, "list", result.getValue());
			addAttribute(model, "page", page);
			return "problem/list";
		} else {
			addErrorMsg(model, result.getMsg());
			return "error";
		}
	}

	@RequestMapping(value = "/doSubmit", method = RequestMethod.POST)
	public String doSubmit(Model model, @ModelAttribute("submitForm") ProblemSubmitForm submitForm,HttpServletResponse response) {
		if (UserSession.get() == null || UserSession.get().getEmailStatus() != UserinfoModel.EmailStatus.PASS){
			return "error";
		}
		ResultBase<Integer> result = statusService.submit(submitForm.getLanguage(), submitForm.getCode(), Integer.parseInt(submitForm.getPid()), null);
		if (result.isSuccess()) {
			if (result.getValue() == 0){
				addAttribute(model, "model", submitForm);
				addErrorMsg(model, result.getMsg());
				return "status/reSubmit";
			} else {
				CookieUtil.addCookie(response, "lastLanguage", submitForm.getLanguage(), 365*24*60*60);
				return "redirect:/status/problem.htm?page=0";
			}
		} else {
			addErrorMsg(model, result.getMsg());
			return "error";
		}
	}
}
