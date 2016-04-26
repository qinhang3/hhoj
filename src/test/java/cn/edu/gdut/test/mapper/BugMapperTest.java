package cn.edu.gdut.test.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.mapper.BugMapper;
import cn.edu.gdut.model.BugModel;
import cn.edu.gdut.test.TestBase;
import cn.edu.gdut.util.HtmlEncodeUtil;

public class BugMapperTest extends TestBase{
	@Autowired
	private BugMapper bugMapper;
	@Test
	public void update(){
		List<BugModel> list = bugMapper.select();
		for (BugModel bugModel:list){
			bugModel.setDescription(HtmlEncodeUtil.toHtml(bugModel.getDescription()));
			bugModel.setUrl(HtmlEncodeUtil.toHtml(bugModel.getUrl()));
			bugMapper.update(bugModel);
		}
	}
}
