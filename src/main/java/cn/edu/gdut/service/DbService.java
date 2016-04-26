package cn.edu.gdut.service;

/**
 * 数据库service层<br/>
 * 向下调用Mapper，向上为应用层服务<br/>
 * 返回均为<code>ResultBase<code>
 * 若成功，其中value值为<code>Model/List/null</code> <br/>
 * 若失败，会记录DbLogger，<code> isSuccess = false </code>
 * 
 * @author qinhang3
 */
public interface DbService {

}
