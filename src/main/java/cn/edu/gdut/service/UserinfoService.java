package cn.edu.gdut.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.form.UserForm;
import cn.edu.gdut.form.UserForm.Type;
import cn.edu.gdut.mapper.UserinfoMapper;
import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.util.CheckCodeUtil;
import cn.edu.gdut.util.LocalCache;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.UserSession;
import cn.edu.gdut.util.UserinfoUtil;

@Service
public class UserinfoService {
	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private DBLoggerService dbLoggerService;
	@Autowired
	private UserinfoUtil userinfoUtil;

	private static LocalCache<String, UserinfoModel> lc = new LocalCache<String, UserinfoModel>("UserinfoModel",60*1000L);

	public ResultBase<Integer> insert(UserinfoModel userinfoModel) {
		ResultBase<Integer> result = new ResultBase<Integer>();
		try {
			int rul = userinfoMapper.insertUser(userinfoModel);
			if (rul != 0) {
				return result.setRightValueReturn(rul);
			} else {
				return result.setErrorMsgReturn("insert user error");
			}
		} catch (Exception e) {
			dbLoggerService.error("INSERT USERINFO", e);
			return result.setErrorMsgReturn("insert user error");
		}
	}

	public ResultBase<UserinfoModel> select(UserinfoModel userinfoModel) {
		ResultBase<UserinfoModel> result = new ResultBase<UserinfoModel>();
		try {
			UserinfoModel userinfoModel2 = userinfoMapper.selectUser(userinfoModel);
			if (userinfoModel2 != null){
				lc.put(userinfoModel2.getUsername(), userinfoModel2);
			}
			return result.setRightValueReturn(userinfoModel2);
		} catch (Exception e) {
			dbLoggerService.error("SELECT USERINFO", e);
			return result.setErrorMsgReturn("select user error");
		}
	}
	
	/**
	 * use cache
	 * @param username
	 * @return
	 */
	public ResultBase<UserinfoModel> selectByUsername(String username){
		ResultBase<UserinfoModel> result = new ResultBase<UserinfoModel>();
		if (StringUtils.isEmpty(username)){
			return result.setErrorMsgReturn("username can't be null");
		}
		UserinfoModel userinfoModel2 = lc.get(username);
		if (userinfoModel2 != null){
			return result.setRightValueReturn(userinfoModel2);
		}
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setUsername(username);
		return select(userinfoModel);
	}

	public ResultBase<Boolean> update(UserinfoModel userinfoModel) {
		ResultBase<Boolean> result = new ResultBase<Boolean>();
		try {
			userinfoMapper.updateUser(userinfoModel);
			lc.del(userinfoModel.getUsername());
			return result.setRightValueReturn(true);
		} catch (Exception e) {
			dbLoggerService.error("MODIFY USERINFO", e);
			return result.setErrorMsgReturn("update user error");
		}
	}

	public boolean hasUsername(String username) {
		ResultBase<UserinfoModel> result = this.selectByUsername(username);
		return (result.isSuccess()) && (result.getValue() != null);
	}

	public boolean hasEmail(String email) {
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setEmail(email);
		ResultBase<UserinfoModel> result = this.select(userinfoModel);
		return (result.isSuccess()) && (result.getValue() != null);
	}

	public ResultBase<Boolean> resetPw(String username, String password) {
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setUsername(username);
		userinfoModel.setPassword(password);
		return update(userinfoModel);
	}
	
	public String checkForm(HttpServletRequest request, int formType,UserForm form) {
		/**
		 * 验证码校验
		 */
		if (form.isNeedCode(formType)){
			if (StringUtils.isEmpty(form.getTopic())){
				return "CheckCode error";
			}
			if (StringUtils.isNotEmpty(form.getTopic()) && !CheckCodeUtil.checkCode(form.getTopic(), request, true)){
				return "CheckCode error";
			}
		}
		/**
		 * 注册逻辑
		 */
		if(formType == Type.REGISTER){
			String usernameMsg = userinfoUtil.checkUsername(form.getUsername(), true);
			if (StringUtils.isNotEmpty(usernameMsg)) {
				return usernameMsg;
			}
	
			if (StringUtils.isEmpty(form.getPassword1())) {
				return "Password can't be NULL";
			}
	
			if (!StringUtils.equals(form.getPassword1(), form.getPassword2())) {
				return "Password not same";
			}
			if (form.getPassword1().length() < 6){
				return "Password too short";
			}
			String emailMsg = userinfoUtil.checkEmail(form.getEmail(), true);
			if (StringUtils.isNotEmpty(emailMsg)){
				return emailMsg;
			}
		} else
		/**
		 * 登陆逻辑
		 */
		if (formType == Type.LOGIN){
			String usernameMsg = userinfoUtil.checkUsername(form.getUsername(), false);
			if (StringUtils.isNotEmpty(usernameMsg)) {
				return usernameMsg;
			}
			ResultBase<UserinfoModel> rul = selectByUsername(form.getUsername());
			if (rul.getValue() == null) {
				return "Username or Password is Wrong";
			}
			if (!userinfoUtil.checkPassword(form.getPassword(), rul.getValue().getPassword())) {
				return "Username or Password is Wrong";
			} 
			UserSession.put(rul.getValue());
		}
		/**
		 * 重置密码逻辑
		 */
		if (formType == Type.RESETPW){
			String usernameMsg = userinfoUtil.checkUsername(form.getUsername(), false);
			if (StringUtils.isNotEmpty(usernameMsg)) {
				return usernameMsg;
			}
			if (StringUtils.isEmpty(form.getPassword1())) {
				return "Password can't be NULL";
			}
			if (!StringUtils.equals(form.getPassword1(), form.getPassword2())) {
				return "Password not same";
			}
			if (form.getPassword1().length() < 6){
				return "Password too short";
			}
			String emailMsg = userinfoUtil.checkEmail(form.getEmail(), false);
			if (StringUtils.isNotEmpty(emailMsg)){
				return emailMsg;
			}
			String matchMsg = userinfoUtil.checkMatch(form.getUsername(), form.getEmail());
			if (StringUtils.isNotEmpty(matchMsg)){
				return matchMsg;
			}
		}
		
		/**
		 * 资料修改逻辑
		 */
		if(formType == Type.MODIFY){
			String usernameMsg = userinfoUtil.checkUsername(form.getUsername(), false);
			if (StringUtils.isNotEmpty(usernameMsg)) {
				return usernameMsg;
			}
			ResultBase<UserinfoModel> rul = selectByUsername(form.getUsername());
			if (rul.getValue() == null) {
				return "Username or Password is Wrong";
			}
			if (!userinfoUtil.checkPassword(form.getPassword(), rul.getValue().getPassword())) {
				return "Username or Password is Wrong";
			} 
			if (StringUtils.isNotEmpty(form.getPassword1()) || StringUtils.isNotEmpty(form.getPassword2())){
				if (!StringUtils.equals(form.getPassword1(), form.getPassword2())) {
					return "Password not same";
				}
				if (form.getPassword1().length() < 6){
					return "Password too short";
				}
			}
			if (StringUtils.isNotEmpty(form.getEmail())){
				String emailMsg = userinfoUtil.checkEmail(form.getEmail(), true);
				if (StringUtils.isNotEmpty(emailMsg)){
					return emailMsg;
				}
			}
		}
		
		/**
		 * 邮箱验证逻辑
		 */
		if (formType == Type.CHECKEMAIL){
			//do nothing
		}
		return "";
	}
}
