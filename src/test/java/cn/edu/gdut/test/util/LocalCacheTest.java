package cn.edu.gdut.test.util;

import java.util.Scanner;

import org.junit.Test;

import cn.edu.gdut.test.TestBase;
import cn.edu.gdut.timetask.LocalCacheCleaner;
import cn.edu.gdut.util.LocalCache;

public class LocalCacheTest extends TestBase{
	@Test
	public void test(){
		LocalCache<String, Object> lc = new LocalCache<String, Object>("Test");
		int cnt = 0;
		Scanner cin = new Scanner(System.in);
		
		while (true) {
//			break;//
			int num = cin.nextInt();
			if (num < 0){
				LocalCacheCleaner localCacheCleaner = new LocalCacheCleaner();
				localCacheCleaner.cleanerNow();
			} else {				
				for (int i=0;i<=1000000;i++) lc.put(""+(cnt+i), new Object());
				cnt += 1000000;
				System.out.println(cnt);
			}
		}
	}
}
