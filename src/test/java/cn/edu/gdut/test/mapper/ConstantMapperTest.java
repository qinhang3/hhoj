package cn.edu.gdut.test.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.gdut.mapper.ConstantMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class ConstantMapperTest {
	@Autowired
	private ConstantMapper constantMapper; 
	@Test
	public void test(){
		constantMapper.insert("test1", "test2");
		constantMapper.update("test1", "test3");
		Assert.assertEquals("test3",constantMapper.select("test1"));
		constantMapper.delete("test1");
		Assert.assertEquals(null,constantMapper.select("test1"));
	}

}
