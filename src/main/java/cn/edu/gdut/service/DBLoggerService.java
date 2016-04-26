package cn.edu.gdut.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.mapper.DBLoggerMapper;
import cn.edu.gdut.model.DBLoggerModel;
import cn.edu.gdut.model.DBLoggerQuery;
import cn.edu.gdut.util.ResultBase;

/**
 * @see DbService
 * @author qinhang.qh
 *
 */
@Service
public class DBLoggerService implements DbService{
	@Autowired
	private DBLoggerMapper dbLoggerMapper;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ResultBase<List<DBLoggerModel>> selectByQuery(DBLoggerQuery dbLoggerQuery) {
		ResultBase<List<DBLoggerModel>> result = new ResultBase<List<DBLoggerModel>>();
		try {
			List<DBLoggerModel> list = dbLoggerMapper.selectByQuery(dbLoggerQuery);
			return result.setRightValueReturn(list);
		} catch (Exception e) {
			logger.error("Select Logger Error", e);
			return result.setErrorMsgReturn("Select Logger Error");
		}
	}

	public ResultBase<DBLoggerModel> selectById(Long id) {
		ResultBase<DBLoggerModel> result = new ResultBase<DBLoggerModel>();
		try {
			DBLoggerModel dbLoggerModel = dbLoggerMapper.selectById(id);
			return result.setRightValueReturn(dbLoggerModel);
		} catch (Exception e) {
			logger.error("Select Logger Error", e);
			return result.setErrorMsgReturn("Select Logger Error");
		}
	}

	public void info(String topic, String info) {
		insert(new DBLoggerModel(topic, "INFO", info, null));
	}

	public void error(String topic, String info, String detail) {
		insert(new DBLoggerModel(topic, "ERROR", info, detail));
	}

	public void error(String topic, Throwable t) {
		error(topic, t.getMessage(), formatStackTrace(t));
	}

	private String formatStackTrace(Throwable throwable) {
		if (throwable == null)
			return "";
		String rtn = throwable.getStackTrace().toString();
		try {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			throwable.printStackTrace(printWriter);
			printWriter.flush();
			writer.flush();
			rtn = writer.toString();
			printWriter.close();
			writer.close();
		} catch (Throwable t) {
			return "Error In formatStackTrace.Exception is " + throwable.toString();
		}
		return rtn;
	}

	private void insert(DBLoggerModel dbLoggerModel) {
		try {
			dbLoggerMapper.insert(dbLoggerModel);
		} catch (Exception e) {
			logger.error("Insert Logger Error: dbLogerModel = [" + dbLoggerModel + "]", e);
		}
	}
}
