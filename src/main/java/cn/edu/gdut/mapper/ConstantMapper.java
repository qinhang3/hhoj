package cn.edu.gdut.mapper;

public interface ConstantMapper {
	void insert(String key,String value);
	void delete(String key);
	void update(String key,String value);
	String select(String key);
	
	public interface Namespace{
		public final String CheckCodeLength = "CheckCodeLength";
	}
}
