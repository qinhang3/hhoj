package cn.edu.gdut.test.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.service.ContestService;
import cn.edu.gdut.test.TestBase;

public class ContestServiceTest extends TestBase {
	@Autowired
	private ContestService contestService;
	
	@Test
	public void insert(){
		ContestModel contestModel = new ContestModel();
		contestModel.setTitle("1234556");
		contestModel.setStartTime(new Date());
		contestModel.setEndTime(new Date());
		contestModel.setProblems("[8,9,10,11,12]");
		System.out.println(contestService.add(contestModel));
	}
	@Test
	public void getEntity(){
		System.out.println(contestService.getContestEntity(22).getValue());
	}

}
