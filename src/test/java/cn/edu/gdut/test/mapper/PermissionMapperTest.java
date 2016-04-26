package cn.edu.gdut.test.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.mapper.PermissionMapper;
import cn.edu.gdut.model.PermissionModel;
import cn.edu.gdut.test.TestBase;

public class PermissionMapperTest extends TestBase{
	@Autowired
	private PermissionMapper permissionMapper;
	@Test
	public void test(){
//		PermissionModel permissionModel = new PermissionModel();
//		permissionModel.setUsername("qinhang3");
//		permissionModel.setGroup("god");
//		int cnt = permissionMapper.insert(permissionModel);
//		Assert.assertEquals(cnt, 1);
//		permissionModel.setUsername("qinhang3");
//		permissionModel.setGroup("admin");
//		permissionMapper.insert(permissionModel);
//		
//		List<String> list = permissionMapper.selectUsernameByGroup("god");
//		Assert.assertEquals(list.size(), 1);
//		Assert.assertEquals(list.get(0), "qinhang3");
//		list = permissionMapper.selectGroupByUsername("qinhang3");
//		Assert.assertEquals(list.size(), 2);
	}
}
