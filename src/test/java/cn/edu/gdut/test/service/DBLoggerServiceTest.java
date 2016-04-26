package cn.edu.gdut.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.gdut.model.DBLoggerModel;
import cn.edu.gdut.model.DBLoggerQuery;
import cn.edu.gdut.service.DBLoggerService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class DBLoggerServiceTest {
	@Autowired
	private DBLoggerService dbLoggerService;

	@Test
	public void test(){
		dbLoggerService.info("<html>","<p>");	
	}
	
	@Test
	public void select(){
		//List<DBLoggerModel> list = dbLoggerService.select();
		//System.out.println(list);
		//dbLoggerService.select(new DBLoggerModel());
	}
	
	@Test
	public void selectByQuery(){
		DBLoggerQuery dbLoggerQuery = new DBLoggerQuery(20);
//		dbLoggerQuery.setNowPage(1);
		dbLoggerQuery.setTopic("REGISTER");
		List<DBLoggerModel> list = dbLoggerService.selectByQuery(dbLoggerQuery).getValue();
		System.out.println(list);
	}
}
