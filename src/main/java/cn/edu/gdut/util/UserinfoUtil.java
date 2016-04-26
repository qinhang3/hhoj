package cn.edu.gdut.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.service.ConstantService;
import cn.edu.gdut.service.UserinfoService;

@Service
public class UserinfoUtil {
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private ConstantService constantService;

	public String doEncrypt(String password) {
		password = password + encrypt(password, "MD5");
		return encrypt(password, "SHA-256").toUpperCase();
	}

	/**
	 * 判断用户名是否合法
	 * 
	 * @param userinfoService
	 * @param username
	 * @param isRegister
	 *            是否是注册场景
	 * @return
	 */
	public String checkUsername(String username, boolean isRegister) {
		if (StringUtils.isEmpty(username)) {
			return "Username is Null";
		}
		if (username.length() < constantService.getAsInteger(
				OJConstant.Username.minLenUsername, 4)) {
			return "Username Too short";
		}
		if (username.length() > constantService.getAsInteger(OJConstant.Username.maxLenUsername, 64)) {
			return "Username Too long";
		}
		if (StringUtils.isEmpty(username)) {
			return "Username can't be NULL";
		}
		if (StringUtils.contains(username.toLowerCase(), "admin")
				|| unSafe(username)) {
			return "Bad Username";
		}
		if (username.toLowerCase().startsWith("team")) {
			return "Can't Start With 'team'";
		}
		if (isRegister && userinfoService.hasUsername(username)) {
			return "User Exist";
		}
		if (!isRegister && !userinfoService.hasUsername(username)) {
			return "User Not Exist";
		}
		return "";
	}

	private boolean unSafe(String username) {
		String safe1 = "abcdefghijklmnopqrstuvwxyz";
		String safe2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String safe3 = "0123456789_";
		String safe = safe1 + safe2 + safe3;
		for (int i = 0; i < username.length(); i++) {
			if (!StringUtils.contains(safe, username.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private String encrypt(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		if (StringUtils.isEmpty(encName)) {
			encName = "SHA-256";
		}
		try {
			md = MessageDigest.getInstance(encName);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		md.update(bt);
		strDes = bytes2Hex(md.digest()); // to HexString
		return strDes;
	}

	private String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public boolean checkPassword(String password, String stdPassword) {
		return StringUtils.equals(stdPassword, doEncrypt(password));
	}

	public String checkEmail(String email, boolean isRegister) {
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		if (!p.matcher(email).matches()) {
			return "E-mail is not corrett";
		}
		if (isRegister && userinfoService.hasEmail(email)) {
			return "E-mail is Exist";
		}
		return null;
	}

	/**
	 * 检查用户名与邮箱是否匹配
	 * 
	 * @param userinfoService
	 * @param username
	 * @param email
	 * @return
	 */
	public String checkMatch(String username, String email) {
		UserinfoModel userinfoModel = userinfoService
				.selectByUsername(username).getValue();
		if (userinfoModel == null) {
			return "User not Exist";
		} else if (StringUtils.equals(userinfoModel.getEmail(), email)) {
			return "";
		} else {
			return "Username or E-mail not Match";
		}
	}
}
