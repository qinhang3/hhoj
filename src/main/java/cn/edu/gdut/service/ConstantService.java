package cn.edu.gdut.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.gdut.mapper.ConstantMapper;
import cn.edu.gdut.util.LocalCache;

@Service
public class ConstantService{
	@Autowired
	private ConstantMapper constantMapper;
	@Autowired
	private DBLoggerService dbLoggerService;

	private static LocalCache<String, String> lcs = new LocalCache<String, String>("String Constant");
	private static LocalCache<String, Integer> lci = new LocalCache<String, Integer>("Integer Constant");
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 如果key不存在 返回def
	 * 
	 * @param key
	 * @param def
	 * @return
	 */
	public String getAsString(String key, String def) {
		String rul = lcs.get(key);
		if (null != rul) {
			return rul;
		}
		rul = get(key);
		if (StringUtils.isEmpty(rul)) {
			logger.error("USE DEFAULT VALUE!!! key = " + key);
			return def;
		} else {
			lcs.put(key, rul);
			return rul;
		}
	}

	/**
	 * 如果key不存在或NumberFormatException 返回def
	 * 
	 * @param key
	 * @param def
	 * @return
	 */
	public Integer getAsInteger(String key, Integer def) {
		Integer rul = lci.get(key);
		if (null != rul) {
			return rul;
		}
		try {
			rul = Integer.parseInt(get(key));
			lci.put(key, rul);
			return rul;
		} catch (NumberFormatException e) {
			logger.error("USE DEFAULT VALUE!!! key = " + key);
			return def;
		}
	}

	public void update(String key, String value) {
		constantMapper.update(key, value);
		lcs.del(key);
		lci.del(key);
	}

	public void update(String key, int value) {
		update(key, String.valueOf(value));
	}

	private String get(String key) {
		try {
			String rul = constantMapper.select(key);
			return StringUtils.isEmpty(rul) ? null : rul;
		} catch (Exception e) {
			dbLoggerService.error("SELECT CONSTANT", e);
		}
		return null;
	}

}
