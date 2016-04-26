package cn.edu.gdut.permission;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.gdut.model.ContestEntiry;
import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.model.StatusModel;
import cn.edu.gdut.service.ContestService;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.util.ResultBase;
import cn.edu.gdut.util.UserSession;

public class StatusChecker extends CheckerBase{
	@Autowired
	private StatusService statusService;
	@Autowired
	private ContestService contestService;
	
	@Override
	public boolean run(HttpServletRequest request, HttpServletResponse response) {
		if (super.run(request, response)){
			return true;
		}
		if (request.getAttribute("METHODNAME")==null){
			return false;
		}
		String METHODNAME = request.getAttribute("METHODNAME").toString();
		if(StringUtils.equals(METHODNAME, "ajaxProblem") || StringUtils.equals(METHODNAME, "problem")){
			return true;
		}
		if(StringUtils.equals(METHODNAME, "code")){
			return statusBelongUser(Integer.parseInt(request.getParameter("rid")), UserSession.getUsername());
		}
		if(StringUtils.equals(METHODNAME, "contest") || StringUtils.equals(METHODNAME, "ajaxContest")){
			if (UserSession.getUsername() == null){
				return false;
			}
			Integer cid = Integer.parseInt(request.getParameter("cid"));
			ResultBase<ContestEntiry> result = contestService.getContestEntity(cid);
			if (result.getValue() == null) {
				return false;
			}
			ContestEntiry contestEntiry = result.getValue();
			if (contestEntiry.getStartTime().after(new Date())){
				return false;
			}
			if (contestEntiry.getIsPublic().equals(ContestModel.PublicMode.PUBLIC)){
				return true;
			}
			return request.getAttribute("INCID"+cid)!=null;
		}
		return true;
	}
	
	private boolean statusBelongUser(Integer rid,String username){
		if (username == null){
			return false;
		}
		ResultBase<StatusModel> result = statusService.getCode(rid);
		if (result.getValue() == null){
			return false;
		}
		if (result.getValue().getCid() == null){
			ResultBase<Map<Integer,Boolean>> acList = statusService.getAcByUsername(username);
			Map<Integer,Boolean> map = acList.getValue();
			Integer pid = result.getValue().getPid();
			if (map.get(pid)!=null){
				return true;
			}
		}
		return StringUtils.equals(result.getValue().getUsername(), username);
	}
}
