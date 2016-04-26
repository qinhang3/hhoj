package cn.edu.gdut.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.gdut.service.ProblemService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class ProblemServiceTest {
	@Autowired
	private ProblemService problemService;
	
	@Test
	public void selectById(){
		System.out.println(problemService.selectById(12));
	}
}
