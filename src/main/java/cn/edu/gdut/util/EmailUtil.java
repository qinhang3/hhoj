package cn.edu.gdut.util;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import cn.edu.gdut.form.UserForm;
import cn.edu.gdut.service.DBLoggerService;

@Service
public class EmailUtil {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private DBLoggerService dbLoggerService;
		
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public ResultBase<String> sendCertifyEmail(UserForm postForm){
		ResultBase<String> result = new ResultBase<String>();
		String sendTo = postForm.getEmail();
		String title = "CERTIFY YOUR E-MAIL";
		String token = getToken();
		String text ="<html><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link rel=\"stylesheet\" href=\"http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css\"></head>"
				+ "<body><div class=\"jumbotron\"><div class=\"container\"><div class=\"col-md-8 col-md-offset-2\" role=\"main\"><h1>Certify Your E-mail <small>At HHOJ</small></h1><p>Your Token Is</p>"
				+ "<h1 class=\"text-center\">"+token+"</h1>"
				+ "<p>Please input your token in 30 minute</p></div></div></div></body></html>";
		if (sendHtmlEmail(title, text, sendTo)){
			return result.setRightValueReturn(token);
		} else {
			return result.setErrorMsgReturn("Send E-mail Error!");
		}
	}
	
	public ResultBase<String> sendResetPasswordEmail(UserForm postForm){
		ResultBase<String> result = new ResultBase<String>();
		String sendTo = postForm.getEmail();
		String title = "RESET YOUR PASSWORD";
		String token = getToken();
		String text ="<html><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link rel=\"stylesheet\" href=\"http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css\"></head>"
				+ "<body><div class=\"jumbotron\"><div class=\"container\"><div class=\"col-md-8 col-md-offset-2\" role=\"main\"><h1>Reset Password <small>At HHOJ</small></h1><p>Your Token Is</p>"
				+ "<h1 class=\"text-center\">"+token+"</h1>"
				+ "<p>Please input your token in 30 minute</p></div></div></div></body></html>";
		
		if (sendHtmlEmail(title, text, sendTo)){
			return result.setRightValueReturn(token);
		} else {
			return result.setErrorMsgReturn("Send E-mail Error!");
		}
	}
	
	private String getToken(){
		StringBuilder builder = new StringBuilder();
		for (int i=0;i<6;i++){
			builder.append((int)(Math.random()*10));
		}
		return builder.toString();
	}
	
	private boolean sendHtmlEmail(String title,String text,String sendTo){
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			mimeMessageHelper.setTo(sendTo);
			mimeMessageHelper.setFrom("userinfo@hhoj.top");
			mimeMessageHelper.setSubject(title);
			mimeMessageHelper.setText(text,true);
		} catch (MessagingException e) {
			dbLoggerService.error("SEND EMAIL", e);
			return false;
		}
		try{
			javaMailSender.send(mimeMessage);
		} catch (Throwable t){
			dbLoggerService.error("SEND EMAIL", t);
			return false;
		}
		return true;
	}

	public void test(){
		if (javaMailSender==null) throw new RuntimeException();
	}
}
