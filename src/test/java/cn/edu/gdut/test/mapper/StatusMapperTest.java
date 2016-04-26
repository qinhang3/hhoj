package cn.edu.gdut.test.mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.gdut.mapper.StatusMapper;
import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.model.RejudgeQuery;
import cn.edu.gdut.model.StatusCountModel;
import cn.edu.gdut.model.StatusCountQuery;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.model.StatusQuery;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class StatusMapperTest {
	@Autowired
	private StatusMapper statusMapper;
	
	@Test
	public void insert(){
		for (int i=0;i<10;i++){
			StatusModel statusModel = new StatusModel();
			statusModel.setCode("#include<iostream>\";;sdfasljflasjfdl"+i+i+i);
			statusModel.setLanguage("CPP");
			statusModel.setUsername("qinhang3"+i);
			statusModel.setCid(22);
			statusModel.setPid(i+2);
			statusMapper.insert(statusModel);
		}
	}
	
	@Test
	public void selectById(){
		System.out.println(statusMapper.selectById(14));
	}
	
	@Test
	public void judged(){
		StatusModel statusModel = statusMapper.selectById(15);
		statusModel.setStatus(StatusModel.StatusCode.AC);
		statusModel.setRunMemory(1024);
		statusModel.setRunTime(999);
		statusMapper.judged(statusModel);
	}
	
	@Test
	public void selectByQuery(){
		StatusQuery query = new StatusQuery(20);
		query.setPid(14);
		System.out.println(statusMapper.selectByQuery(query));
	}
	
	@Test
	public void selectForContent(){
		System.out.println(statusMapper.selectForRanklist(22));
	}
	
	private int now = 0;
	@Test
	public void readFromFile() throws Exception{
		File file = new File("/Users/hang/Downloads/solution 2.csv");
		Scanner fin = new Scanner(file);
		StringBuilder sb = new StringBuilder();
		while(fin.hasNext()){
			sb.append(fin.nextLine());
			sb.append("\n");
		}
		while(true){
			StatusModel statusModel = next(sb.toString());
			if (statusModel == null){
				break;
			}
			statusMapper.mork(statusModel);
		}
	}

	private StatusModel next(String string) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
		String pidStr = nextString(string);
		if (pidStr == null) return null;
		String username = nextString(string);
		String timeStr = nextString(string);
		String lanStr = nextString(string);
		String code = nextString(string);
		StatusModel statusModel = new StatusModel();
		statusModel.setPid(Integer.parseInt(pidStr));
		statusModel.setUsername(username);
		statusModel.setCreateTime(sdf.parse(timeStr));
		statusModel.setLanguage(getLan(lanStr));
		statusModel.setCode(code);
		statusModel.setCid(1000);
		return statusModel;
	}

	private String getLan(String lanStr) {
		if (lanStr.equals("0")){
			return "C";
		}
		if (lanStr.equals("1")){
			return "CPP";
		}
		if (lanStr.equals("3")){
			return "JAVA";
		}
		return "ERROR";
	}

	private String nextString(String string) {
		StringBuilder sb = new StringBuilder();
		boolean flag = false;
		now = now + 1;
		while(true){
			char c = string.charAt(now++);
			if (flag){
				sb.append(c);
				flag = false;
			} else {
				if (c == '\\') {
					flag = true;
				} else{
					if (c=='"') break;
					sb.append(c);
				}
			}
		}
		now = now + 1;
		return sb.toString();
	}
	
	@Test
	public void reJudge(){
		RejudgeQuery rejudgeQuery = new RejudgeQuery();
		rejudgeQuery.setPid(1001);
		rejudgeQuery.setCid(2000);
		rejudgeQuery.setIncludeAc(false);
		statusMapper.reJudge(rejudgeQuery);
	}
	
	@Test
	public void count(){
		StatusCountQuery query = new StatusCountQuery();
		query.addPid(9);
		query.addPid(8);
		List<StatusCountModel> list = statusMapper.count(query);
		System.out.println(list);
	}
}
