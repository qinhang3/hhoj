package cn.edu.gdut.test.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.gdut.mapper.ProblemMapper;
import cn.edu.gdut.model.ProblemModel;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class ProblemMapperTest {
	@Autowired
	private ProblemMapper problemMapper;
	
	@Test
	public void insertProblem(){
		for (int i=0;i<10;i++){
			ProblemModel problemModel = new ProblemModel();
			problemModel.setTitle("title"+i+i);
			problemModel.setDescription("desc");
			problemModel.setInput("input");
			problemModel.setOutput("output");
			problemModel.setSampleInput("sample_input");
			problemModel.setSampleOutput("sample_output");
			problemModel.setAuthor("author");
			problemModel.setMemLimit(8023);
			problemModel.setTimeLimit(1024);
			problemModel.setIsPublic(1);
			problemModel.setIsSpj(1);
			
			problemMapper.insert(problemModel);
		}
	}
	
	@Test
	public void mork(){
		for (int i=0;i<8;i++){
			ProblemModel problemModel = new ProblemModel();
			problemModel.setMemLimit(131072);
			problemModel.setTimeLimit(1000);
			problemModel.setIsPublic(0);
			problemModel.setIsSpj(0);
			problemMapper.insert(problemModel);
		}
		
	}
	
	@Test
	public void selectById(){
		System.out.println(problemMapper.selectById(1));
	}
	
	@Test
	public void updateById(){
		ProblemModel problemModel = new ProblemModel();
		problemModel.setTitle("title11");
		problemModel.setDescription("desc");
		problemModel.setInput("input");
		problemModel.setOutput("output");
		problemModel.setSampleInput("sample_input");
		problemModel.setSampleOutput("sample_output");
		problemModel.setAuthor("author");
		problemModel.setMemLimit(8023);
		problemModel.setTimeLimit(1024);
		problemModel.setIsPublic(1);
		problemModel.setIsSpj(1);
		problemModel.setId(1);
		
		problemMapper.updateById(problemModel);
	}
	
	@Test
	public void selectList(){
	}

}
