package cn.edu.gdut.mapper;

import java.util.List;

import cn.edu.gdut.model.DBLoggerModel;
import cn.edu.gdut.model.DBLoggerQuery;

public interface DBLoggerMapper {
	public void insert(DBLoggerModel dbLoggerModel);
	public List<DBLoggerModel> selectByQuery(DBLoggerQuery query);
	public DBLoggerModel selectById(Long id);
}
