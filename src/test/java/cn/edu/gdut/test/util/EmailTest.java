package cn.edu.gdut.test.util;

import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class EmailTest {
	@Autowired
	private JavaMailSender javaMailSender;

	@Test
	public void msgTest() throws Exception {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("qinhang3@qq.com");
		mail.setFrom("admin@hhoj.top");
		mail.setSubject("title");
		mail.setText("text text");
		javaMailSender.send(mail);
	}

	@Test
	public void htmlText() throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		mimeMessageHelper.setTo("qinhang3@qq.com");
		mimeMessageHelper.setFrom("admin@hhoj.top");
		mimeMessageHelper.setSubject("Reset Pasword");
		String text ="<html><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><link rel=\"stylesheet\" href=\"http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css\"></head>"
				+ "<body><div class=\"jumbotron\"><div class=\"container\"><div class=\"col-md-8 col-md-offset-2\" role=\"main\"><h1>Reset Password <small>At HHOJ</small></h1><p>Your Token Is</p>"
				+ "<h1 class=\"text-center\">${TOKEN}</h1>"
				+ "<p>Please input your token in 30 minute</p></div></div></div></body></html>";
		text = text.replace("${TOKEN}", "123456");
		mimeMessageHelper.setText(text,true);
		javaMailSender.send(mimeMessage);
	}

}
