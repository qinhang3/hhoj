package cn.edu.gdut.mapper;

import java.util.List;

import cn.edu.gdut.model.PermissionModel;

public interface PermissionMapper {
	public Integer insert(PermissionModel permissionModel);
	public Integer delete(PermissionModel permissionModel);
	public List<String> selectGroupByUsername(String username);
	public List<String> selectUsernameByGroup(String group); 
}
