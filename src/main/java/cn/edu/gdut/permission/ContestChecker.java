package cn.edu.gdut.permission;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.model.ContestEntiry;
import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.service.ContestService;
import cn.edu.gdut.util.ResultBase;

public class ContestChecker extends CheckerBase {
	@Autowired
	private ContestService contestService;
	
	@Override
	public boolean run(HttpServletRequest request, HttpServletResponse response) {
		if (request.getAttribute("METHODNAME")==null){
			return false;
		}
		if (StringUtils.equals(request.getAttribute("METHODNAME").toString(),"list")){
			return true;
		}
		if (super.run(request, response)){
			return true;
		}
		Integer cid = null;
		try{
			cid = Integer.parseInt(request.getParameter("cid"));
		} catch (Exception e){
			return false;
		}
		ResultBase<ContestEntiry> entityResult = contestService.getContestEntity(cid);
		if (!entityResult.isSuccess() || entityResult.getValue()==null){
			return false;
		}
		Date startTime = entityResult.getValue().getStartTime();
		if (startTime.after(new Date())){
			if (!StringUtils.equals(request.getAttribute("METHODNAME").toString(),"index")){
				return false;
			} else {
				request.setAttribute("notStart", Boolean.TRUE);
			}
		}
		Integer isPublic = entityResult.getValue().getIsPublic();
		if (ContestModel.PublicMode.PUBLIC.equals(isPublic)){
			return true;
		}
		if (ContestModel.PublicMode.HIDDEN.equals(isPublic)){
			return false;
		}
		if (request.getAttribute("INCID"+cid)!=null){
			return true;
		}
		return false;
	}
}
