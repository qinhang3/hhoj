package cn.edu.gdut.timetask;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.edu.gdut.util.LocalCache;

@Component("localCacheCleaner")
public class LocalCacheCleaner {
	@SuppressWarnings("rawtypes")
	private List<LocalCache> list = LocalCache.list;
	
	@Scheduled(fixedDelay = 5000L)
	public void cleanerNow(){
		int size = list.size();
		for (int i=0;i<size;i++) {
			list.get(i).timeOutClean();
		}
	}
}
