package cn.edu.gdut.test.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.mapper.ContestMapper;
import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.model.ContestQuery;
import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.test.TestBase;

public class ContestMapperTest extends TestBase{
	@Autowired
	private ContestMapper contestMapper;
	
	@Test
	public void insert(){
		for (int i=0;i<10;i++){
			ContestModel contestModel = new ContestModel();
			contestModel.setTitle("contest title"+i+i);
			contestModel.setStartTime(new Date());
			contestModel.setEndTime(new Date());
			List<Integer> list = new ArrayList<Integer>();
			list.add(1000);
			list.add(1004);
			list.add(1003);
			list.add(1002);
			list.add(1001);
			contestModel.setProblemList(list);
			contestMapper.insert(contestModel);
		}
	}
	
	@Test
	public void select(){
		System.out.println(contestMapper.selectById(21));
//		ContestModel contestModel = new ContestModel();
//		contestModel.setProblems("[1000,1004,1003,1002,1001]");
	}
	
	@Test
	public void update(){
		ContestModel contestModel = contestMapper.selectById(7);
		contestModel.setIsPublic(1);
		contestMapper.update(contestModel);
	}
	
	@Test
	public void delete(){
		contestMapper.delete(8);
	}
	
	@Test
	public void selectByQuery(){
		ContestQuery query = new ContestQuery(20);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		query.setIsPublicList(list);
		query.setPage(0);
		System.out.println(contestMapper.selectByQuery(query));
		
	}
}
