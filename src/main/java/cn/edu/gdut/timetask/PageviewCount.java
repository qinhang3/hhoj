package cn.edu.gdut.timetask;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("pageviewCount")
public class PageviewCount {
	private static int lastMin = 0;
	private static int lastHour = 0;
	private static int lastJudge = 0;
	private static AtomicInteger minCnt = new AtomicInteger();
	private static AtomicInteger hourCnt = new AtomicInteger();
	private static AtomicInteger judgeCnt = new AtomicInteger();
	@Scheduled(cron = "0 0/1 * * * ?")
	public void lastMin(){
		lastMin = minCnt.intValue();
		minCnt.set(0);
	}
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void lastHour(){
		lastHour = hourCnt.intValue();
		hourCnt.set(0);
	}
	@Scheduled(cron = "0/10 * * * * ?")
	public void judgeCnt(){
		lastJudge = judgeCnt.intValue();
		judgeCnt.set(0);
	}
	
	public static void add(){
		minCnt.incrementAndGet();
		hourCnt.incrementAndGet();
	}
	
	public static void judge(){
		judgeCnt.incrementAndGet();
	}
	
	public static int getLastMin(){
		return Math.max(lastMin, minCnt.intValue());
	}
	public static int getLastHour(){
		return Math.max(lastHour, hourCnt.intValue());
	}
	public static int getJudgeCnt(){
		return Math.max(lastJudge, judgeCnt.intValue());
	}
}
