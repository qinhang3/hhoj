package cn.edu.gdut.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.gdut.mapper.ConstantMapper;
import cn.edu.gdut.service.ConstantService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class ConstantServiceTest {
	
	
	@Autowired
	private ConstantService constantService;
	
	@Test
	public void test(){
		Assert.assertEquals(constantService.getAsString("key","aha"),"value");		
		constantService.update("key", "111");
		Assert.assertEquals(constantService.getAsInteger("key", 123), Integer.valueOf(111));
		constantService.update("key", "value");
		
	}
}
