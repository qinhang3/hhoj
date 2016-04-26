package cn.edu.gdut.mapper;

import cn.edu.gdut.model.UserinfoModel;

public interface UserinfoMapper {
	int insertUser(UserinfoModel userinfo);
	int deleteUser(UserinfoModel userinfo);
	void updateUser(UserinfoModel userinfo);
	UserinfoModel selectUser(UserinfoModel userinfo);
}
