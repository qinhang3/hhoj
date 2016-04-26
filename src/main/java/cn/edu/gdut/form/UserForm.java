package cn.edu.gdut.form;

public class UserForm {

	static public interface Type{
		int REGISTER = 1;
		int LOGIN = 2;
		int RESETPW = 3;
		int MODIFY = 4;
		int CHECKEMAIL = 5;
	}
	private String username;
	private String password;
	private String password1;
	private String password2;
	private String code;
	private String email;
	private String step;
	private String topic;
	private String token;
	private String url;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isNeedCode(int formType) {
		return formType == Type.REGISTER || formType == Type.RESETPW || formType == Type.CHECKEMAIL;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
