package cn.edu.gdut.permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.service.ProblemService;
import cn.edu.gdut.util.ResultBase;

public class ProblemChecker extends CheckerBase {
	@Autowired
	private ProblemService problemService;
	
	@Override
	public boolean run(HttpServletRequest request, HttpServletResponse response) {
		if (super.run(request, response)){
			return true;
		}
		String pid = request.getParameter("pid");
		if (pid == null){
			return true;
		}
		Integer pidInt;
		try{
			pidInt = Integer.parseInt(pid);
		} catch (NumberFormatException e){
			return false;
		}
		
		ResultBase<ProblemModel> problemResult = problemService.selectById(pidInt);
		if (!problemResult.isSuccess() || problemResult.getValue() == null){
			return false;
		}
		
		ProblemModel problemModel = problemResult.getValue();
		return problemModel.getIsPublic() == 1;
	}
}
