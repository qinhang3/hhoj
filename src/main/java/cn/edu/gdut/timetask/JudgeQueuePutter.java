package cn.edu.gdut.timetask;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.edu.gdut.controller.AdminController;
import cn.edu.gdut.model.JudgeModel;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.util.ResultBase;

@Component("judgeQueuePutter")
public class JudgeQueuePutter{

	private LinkedBlockingQueue<JudgeModel> queue = AdminController.judgeQueue;
	@Autowired
	private StatusService statusService;
	
	@Scheduled(fixedDelay = 500L)
	public void putPendingQueue() throws InterruptedException{
		if (queue.isEmpty()) {
			ResultBase<List<JudgeModel>> result = statusService.getJudgeModel();
			if (result.getValue() != null) {
				for (JudgeModel judgeModel : result.getValue()) {
					queue.put(judgeModel);
				}
				if (result.getValue().size() == 0){
					Thread.sleep(5000L);
				}
			}
		}
	}
}
