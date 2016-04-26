package cn.edu.gdut.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.mapper.PermissionMapper;
import cn.edu.gdut.model.PermissionModel;
import cn.edu.gdut.util.LocalCache;
import cn.edu.gdut.util.ResultBase;

@Service
public class PermissionService implements DbService{
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private DBLoggerService dbLoggerService;
	
	private static LocalCache<String, List<String>> lc = new LocalCache<String, List<String>>("Permission",60*1000L);
	
	public ResultBase<Integer> add(PermissionModel permissionModel){
		ResultBase<Integer> result = new ResultBase<Integer>();
		try{
			int cnt = permissionMapper.insert(permissionModel);
			dbLoggerService.info("PERMISSION", 
					String.format("user %s add to group %s",permissionModel.getUsername(),permissionModel.getGroup()));
			return result.setRightValueReturn(cnt);
		} catch (Exception e){
			dbLoggerService.error("INSERT PERMISSION", e);
			return result.setErrorMsgReturn("add permission error");
		}
	}
	
	public ResultBase<List<String>> getGroupByUsername(String username){
		ResultBase<List<String>> result = new ResultBase<List<String>>();
		List<String> list = lc.get(username);
		if (list != null){
			return result.setRightValueReturn(list);
		}
		try{
			list = permissionMapper.selectGroupByUsername(username);
			lc.put(username, list);
			return result.setRightValueReturn(list);
		} catch (Exception e){
			dbLoggerService.error("SELECT PERMISSION", e);
			return result.setErrorMsgReturn("select permission error");
		}
	}
}
