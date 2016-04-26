package cn.edu.gdut.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.omg.CORBA.LocalObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 原型：<code> ConcurrentMap&lt;K, SoftReference&lt;V&gt;&gt; </code><br>
 * 内存满的时候会自动回收，无须手动处理。<br>
 * 默认超时时间2000ms，可通过构造函数设置。 
 * @author qinhang
 *
 */
@SuppressWarnings("rawtypes")
public class LocalCache<K, V> {
	public static ArrayList<LocalCache> list = new ArrayList<LocalCache>();
	
	private long activeTime = 2*1000L;
	
	private String name;
	
	public LocalCache(String name) {
		super();
		this.name = name;
		list.add(this);
	}
	
	public LocalCache(String name,long activeTime){
		super();
		this.activeTime = activeTime;
		this.name = name;
		list.add(this);
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ConcurrentMap<K, CacheObject<V>> map = new ConcurrentHashMap<K, CacheObject<V>>();

	public void put(K k, V v) {
		map.put(k, new CacheObject<V>(v));
	}

	public V get(K k) {
		CacheObject<V> v = map.get(k);
		if (v == null || v.get() == null){
			return null;
		}
		if (v.getBirth() + activeTime < System.currentTimeMillis()){
			return null;
		}
		V value = v.get();
		if (value != null){
			logger.debug("CACHE SUCCESS~");
		}
		return value;
	}

	public void del(K k) {
		map.remove(k);
	}

	public void cls() {
		map.clear();
	}

	public void timeOutClean() {
		long deadLine = System.currentTimeMillis() - activeTime;
		List<K> list = new ArrayList<K>();
		for (Entry<K, CacheObject<V>> entry:map.entrySet()){
			if (activeTime!=-1 && entry.getValue().getBirth() < deadLine){
				list.add(entry.getKey());
			} else if (entry.getValue().get() == null){
				list.add(entry.getKey());
			}
		}
		for (K k : list) {
			map.remove(k);
		}
		logger.debug("Cache "+name+" Time Out Clean End  size = "+ map.size());
	}
}

class CacheObject<T> extends SoftReference<T>{

	private long birth = System.currentTimeMillis();
	public CacheObject(T referent) {
		super(referent);
	}
	
	public long getBirth() {
		return birth;
	}
}
