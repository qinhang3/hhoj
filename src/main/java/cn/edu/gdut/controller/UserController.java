package cn.edu.gdut.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;

import cn.edu.gdut.form.UserForm;
import cn.edu.gdut.model.PermissionModel;
import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.service.ConstantService;
import cn.edu.gdut.service.PermissionService;
import cn.edu.gdut.service.UserinfoService;
import cn.edu.gdut.util.EmailUtil;
import cn.edu.gdut.util.JSonUtil;
import cn.edu.gdut.util.OJConstant;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.SessionUtil;
import cn.edu.gdut.util.UserSession;
import cn.edu.gdut.util.UserinfoUtil;

@Controller
@RequestMapping("/user")
public class UserController extends ControllerBase {
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private UserinfoUtil userinfoUtil;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/info")
	public String info(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String username = request.getParameter("username");
		String nowUser = UserSession.getUsername();
		if (StringUtils.isNotEmpty(nowUser)) {
			if (StringUtils.isEmpty(username) || StringUtils.equals(username, nowUser)) {
				// 已登陆 无用户名 ==> 获取自己 允许隐私信息
				model.addAttribute("owner", "true");
				username = nowUser;
			} else {
				// 已登陆 查看他人 ==> 不允许隐私信息
				model.addAttribute("owner", "false");
			}
		} else {
			if (StringUtils.isNotEmpty(username)) {
				// 未登录 查看他人 ==> 不允许隐私信息
				model.addAttribute("owner", "false");
			} else {
				// 未登录 无参数 ==> 非法访问
				addErrorMsg(model, "Please Login First");
				return "redirect:/user/login.htm";
			}
		}

		ResultBase<UserinfoModel> result = userinfoService.selectByUsername(username);
		if (result.isSuccess() && result.getValue() != null) {
			model.addAttribute("userinfoModel", result.getValue());
		} else {
			model.addAttribute("errmsg", "No Such User!");
			model.addAttribute("userinfoModel", null);
		}

		return "user/info";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute("loginForm") UserForm loginForm, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msg = userinfoService.checkForm(request, UserForm.Type.LOGIN, loginForm);
		if (StringUtils.isNotEmpty(msg)) {
			model.addAttribute("errmsg", msg);
			return "user/login";
		} else {
			String url = loginForm.getUrl();
			if (StringUtils.isEmpty(url)){
				return "redirect:/index.htm";
			} else {
				response.sendRedirect(url);
				return null;
			}
		}
	}

	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public String register(@ModelAttribute("registerForm") UserForm registerForm, Model model, HttpServletRequest request) {
		model.addAttribute("form", registerForm);
		String msg = userinfoService.checkForm(request, UserForm.Type.REGISTER, registerForm);
		if (StringUtils.isEmpty(msg)) {
			UserinfoModel userinfoModel = new UserinfoModel();
			userinfoModel.setUsername(registerForm.getUsername());
			userinfoModel.setPassword(userinfoUtil.doEncrypt(registerForm.getPassword1()));
			userinfoModel.setEmail(registerForm.getEmail());
			ResultBase<Integer> result = userinfoService.insert(userinfoModel);
			if (result.isSuccess()) {
				PermissionModel permissionModel = new PermissionModel();
				permissionModel.setUsername(registerForm.getUsername());
				permissionModel.setGroup("user");
				permissionService.add(permissionModel);
				model.addAttribute("sucmsg", "Register Success");
				return "redirect:/user/login.htm";
			} else {
				model.addAttribute("errmsg", result.getMsg());
				return registerPage(model);
			}
		} else {
			model.addAttribute("errmsg", msg);
			return registerPage(model);
		}
	}

	@RequestMapping(value = "/doModify", method = RequestMethod.POST)
	public String doModify(@ModelAttribute("modifyForm") UserForm modifyForm, HttpServletRequest request, Model model) {
		modifyForm.setUsername(UserSession.getUsername());
		
		if (StringUtils.equals(modifyForm.getEmail(),UserSession.get().getEmail())){
			modifyForm.setEmail(null);
		}
	
		String msg = userinfoService.checkForm(request, UserForm.Type.MODIFY, modifyForm);
		if (StringUtils.isEmpty(msg)) {
			UserinfoModel userinfoModel = new UserinfoModel();
			userinfoModel.setUsername(UserSession.getUsername());
			if (StringUtils.isNotBlank(modifyForm.getPassword1())){
				userinfoModel.setPassword(userinfoUtil.doEncrypt(modifyForm.getPassword1()));
			}
			if (StringUtils.isNotBlank(modifyForm.getEmail())){
				userinfoModel.setEmail(modifyForm.getEmail());
				userinfoModel.setEmailStatus(UserinfoModel.EmailStatus.NOPASS);
			}
			ResultBase<Boolean> result = userinfoService.update(userinfoModel);
			if (result.isSuccess()) {
				msg = "Modify userinfo Success";
				model.addAttribute("sucmsg", msg);
				return "redirect:/user/login.htm";
			} else {
				model.addAttribute("errmsg", result.getMsg());
				return modifyPage(model);
			}
		} else {
			// model.addAttribute("username",UserSession.getUsername());
			model.addAttribute("errmsg", msg);
			return modifyPage(model);
		}
	}

	@RequestMapping(value = "/checkUser")
	public void checkuser(HttpServletResponse httpServletResponse,@RequestParam(value = "username",required = true) String username) {
		JsonObject jo = new JsonObject();
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setUsername(username);
		String msg = userinfoUtil.checkUsername(username, true);
		if (!StringUtils.isEmpty(msg)) {
			jo.addProperty("value", msg);
		} else {
			jo.addProperty("value", "");
		}
		JSonUtil.writeToResponse(jo, httpServletResponse);
	}

	@RequestMapping(value = "/checkEmail")
	public void checkEmail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String email = httpServletRequest.getParameter("email");
		JsonObject jo = new JsonObject();
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setEmail(email);
		String msg = userinfoUtil.checkEmail(email, true);
		if (!StringUtils.isEmpty(msg)) {
			jo.addProperty("value", msg);
		} else {
			jo.addProperty("value", "");
		}
		JSonUtil.writeToResponse(jo, httpServletResponse);
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		UserSession.del();
		addAttribute(model, "USERMODEL", null);
		return "redirect:/user/login.htm";
	}
	
	@RequestMapping(value = "certifyEmail", method = RequestMethod.POST)
	public String certifyEmail(@ModelAttribute("certifyForm") UserForm certifyForm, HttpServletRequest request, Model model){
		String errmsg = userinfoService.checkForm(request,UserForm.Type.CHECKEMAIL,certifyForm);
		if (StringUtils.isNotBlank(errmsg)){
			addErrorMsg(model, errmsg);
			return "error";
		}
		certifyForm.setEmail(UserSession.get().getEmail());
		certifyForm.setUsername(UserSession.getUsername());
		ResultBase<String> result = emailUtil.sendCertifyEmail(certifyForm);
		if (result.isSuccess()){
			addAttribute(model, "model", UserSession.get());
			SessionUtil.setAttribute(request, OJConstant.SessionKey.CERTIFYEMAILTIME, System.currentTimeMillis());
			SessionUtil.setAttribute(request, OJConstant.SessionKey.CERTIFYEMAILTOKEN, result.getValue());
			return "user/certifyEmail";
		} else {
			addErrorMsg(model, result.getMsg());
			return "error";
		}
	}
	
	@RequestMapping(value = "certifyEmailFinal", method = RequestMethod.POST)
	public String certifyEmailFinal(@ModelAttribute("certifyForm") UserForm certifyForm, HttpServletRequest request, Model model){
		boolean isTimeout = checkTimeOut(request,OJConstant.SessionKey.CERTIFYEMAILTIME);
		if (isTimeout){
			addErrorMsg(model, "Time Limit Error,Please Retry!");
		} else {
			String stdToken = SessionUtil.getAttrbute(request, OJConstant.SessionKey.CERTIFYEMAILTOKEN).toString();
			SessionUtil.removeAttribute(request, OJConstant.SessionKey.CERTIFYEMAILTOKEN);
			if (StringUtils.equals(stdToken, certifyForm.getToken())){
				UserinfoModel userinfoModel = UserSession.get();
				if (userinfoModel.getEmailStatus().intValue() == UserinfoModel.EmailStatus.NOPASS){
					userinfoModel.setEmailStatus(UserinfoModel.EmailStatus.PASS);
					ResultBase<Boolean> result = userinfoService.update(userinfoModel);
					if (result.isSuccess()){
						addSuccessMsg(model, "Certify Email Success!");
						return "redirect:/index.htm";
					} else {
						addErrorMsg(model, result.getMsg());
					}
				}
			} else {
				addErrorMsg(model, "Token Error,Please Retry!");
			}
		}
		return "error";
	}

	@RequestMapping(value = "/reset")
	public String reset(@ModelAttribute("loginForm") UserForm postForm, HttpServletRequest request, Model model) {
		if (UserSession.get() != null){
			UserSession.del();
			return "redirect:/user/reset.htm";
		}
		if (StringUtils.equals(postForm.getStep(), "1")) {
			postForm.setStep(null);
			String errmsg = userinfoService.checkForm(request, UserForm.Type.RESETPW, postForm);
			if (StringUtils.isNotEmpty(errmsg)) {
				// 表单校验不通过
				model.addAttribute("errmsg", errmsg);
				return reset(postForm, request, model);
			} else {
				ResultBase<String> result = emailUtil.sendResetPasswordEmail(postForm);
				if (result.isSuccess()) {
					// 发送email成功
					request.getSession().setAttribute(OJConstant.SessionKey.RESETPASSTOKEN, result.getValue());
					request.getSession().setAttribute(OJConstant.SessionKey.RESETPASSWORDUSERNAME, postForm.getUsername());
					request.getSession().setAttribute(OJConstant.SessionKey.RESETPASSTIME, System.currentTimeMillis());
					request.getSession().setAttribute(OJConstant.SessionKey.RESETPASSKEY, userinfoUtil.doEncrypt(postForm.getPassword1()));
					addAttribute(model, "model", postForm);
					return "user/reset2";
				} else {
					// 发送email失败
					model.addAttribute("errmsg", result.getMsg());
					return reset(postForm, request, model);
				}
			}
		} else if (StringUtils.equals(postForm.getStep(), "2")) {
			postForm.setStep(null);
			boolean isTimeOut = checkTimeOut(request,OJConstant.SessionKey.RESETPASSTIME);
			if (!isTimeOut) {
				// 未超时
				String stdToken = request.getSession().getAttribute(OJConstant.SessionKey.RESETPASSTOKEN).toString();
				SessionUtil.removeAttribute(request, OJConstant.SessionKey.RESETPASSTOKEN);
				if (StringUtils.equals(stdToken, postForm.getToken())) {
					// token正确
					UserinfoModel newModel = new UserinfoModel();
					newModel.setUsername(request.getSession().getAttribute(OJConstant.SessionKey.RESETPASSWORDUSERNAME).toString());
					newModel.setPassword(request.getSession().getAttribute(OJConstant.SessionKey.RESETPASSKEY).toString());
					ResultBase<Boolean> result = userinfoService.update(newModel);
					if (result.isSuccess()) {
						model.addAttribute("sucmsg", "Reset Password Success!");
						return "redirect:/user/login.htm";
					} else {
						model.addAttribute("model", postForm);
						model.addAttribute("errmsg", result.getMsg());
						return reset(postForm, request, model);
					}
				} else {
					// token错误
					model.addAttribute("errmsg", "Token Error,Please Retry!");
					return reset(postForm, request, model);
				}
			} else {
				// 超时
				model.addAttribute("errmsg", "Time Limit Error.Please Retry!");
				return reset(postForm, request, model);
			}
		} else {
			HashMap<String, Object> param = new HashMap<String, Object>();
			getAndPutValue(param, OJConstant.CheckCodeKey.resetpw, "RESETPW");
			addAttributeMap(model, param);
			model.addAttribute("model", postForm);
			return "user/reset1";
		}
	}

	private boolean checkTimeOut(HttpServletRequest request,String sessionKey) {
		Long lastTime = (Long) SessionUtil.getAttrbute(request, sessionKey);
		if (lastTime == null || lastTime + 30*60*1000L < System.currentTimeMillis()){
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/login")
	public String loginPage(Model model, @RequestParam(value="url",required=false)String url) {
		if (UserSession.get() != null){
			UserSession.del();
			return "redirect:/user/login.htm";
		}
		addAttribute(model, "url", url);
		return "user/login";
	}

	@RequestMapping(value = "/register")
	public String registerPage(Model model) {
		if (UserSession.get() != null){
			UserSession.del();
			return "redirect:/user/register.htm";
		}
		HashMap<String, Object> filed = new HashMap<String, Object>();
		getAndPutValue(filed, OJConstant.Username.minLenUsername, 4);
		getAndPutValue(filed, OJConstant.Username.maxLenUsername, 64);
		getAndPutValue(filed, OJConstant.CheckCodeKey.register, "REGISTER");
		addAttributeMap(model, filed);
		return "user/register";
	}

	@RequestMapping(value = "/modify")
	public String modifyPage(Model model) {
		if (StringUtils.isEmpty(UserSession.getUsername())) {
			model.addAttribute("errmsg", "Please Login First");
			return "redirect:/user/login.htm";
		} else {
			model.addAttribute("username", UserSession.getUsername());
			return "user/modify";
		}
	}
}
