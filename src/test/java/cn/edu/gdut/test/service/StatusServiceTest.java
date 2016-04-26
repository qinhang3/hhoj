package cn.edu.gdut.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.model.UserContestEntity;
import cn.edu.gdut.model.UserinfoModel;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.test.TestBase;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.UserSession;

public class StatusServiceTest extends TestBase{
	@Autowired
	private StatusService statusService;
	
	@Test
	public void judge(){
		System.out.println(statusService.judge(17, StatusModel.StatusCode.WA, 10, 208,null,null));
	}
	
	@Test
	public void selectNum(){
		System.out.println(statusService.selectNumByQuery("qinhang3", null, null, null, null));
	}
	
	@Test
	public void getUserContestEntity(){
		UserinfoModel userinfoModel = new UserinfoModel();
		userinfoModel.setUsername("qinhang3");
		UserSession.put(userinfoModel);
		ResultBase<UserContestEntity> result = statusService.getUserContestEntity(22);
		System.out.println(result.toJson(UserContestEntity.class));
		System.out.println(new Gson().toJson(result, new TypeToken<ResultBase<UserContestEntity>>(){}.getType()));
	}
	@Test
	public void getRanklist(){
		System.out.println(new Gson().toJson(statusService.getRanklistModel(22).getValue()));
	}
}
