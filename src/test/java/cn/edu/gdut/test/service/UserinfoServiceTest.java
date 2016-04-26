package cn.edu.gdut.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.service.UserinfoService;
import cn.edu.gdut.util.UserinfoUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class UserinfoServiceTest {
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private UserinfoUtil userinfoUtil;
	@Test
	public void testInsertUserinfo(){
		for (int i=1;i<=10;i++){
			UserinfoModel userinfoModel = new UserinfoModel();
			userinfoModel.setUsername(""+i);
			userinfoModel.setPassword("test");
			userinfoModel.setEmail(""+i+"@test.com");
			Assert.assertTrue(userinfoService.insert(userinfoModel).isSuccess());
		}
	}
	@Test
	public void testSelectUserinfo(){
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setUsername("11");
		System.out.println(userinfoService.select(userinfoModel));
	}
	@Test
	public void testChangePassword(){
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setUsername("qinhang3z");
		System.out.println(userinfoUtil.doEncrypt("123456789"));
		userinfoModel.setPassword(userinfoUtil.doEncrypt("123456789"));
		userinfoService.update(userinfoModel);
	}
	
	@Test
	public void testUpdate(){
		UserinfoModel model = new UserinfoModel();
		model.setUsername("qinhang3");
		model.setPassword(userinfoUtil.doEncrypt("newnew"));
		model.setEmail("qinhang3@hotmail.com");
		userinfoService.update(model);
	}
}
