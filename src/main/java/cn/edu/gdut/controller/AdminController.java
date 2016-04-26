package cn.edu.gdut.controller;

import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.edu.gdut.mapper.BugMapper;
import cn.edu.gdut.model.BugModel;
import cn.edu.gdut.model.ContestEntiry;
import cn.edu.gdut.model.ContestModel;
import cn.edu.gdut.model.DBLoggerModel;
import cn.edu.gdut.model.DBLoggerQuery;
import cn.edu.gdut.model.JudgeModel;
import cn.edu.gdut.model.ProblemModel;
import cn.edu.gdut.model.SystemInfoModel;
import cn.edu.gdut.service.ConstantService;
import cn.edu.gdut.service.ContestService;
import cn.edu.gdut.service.DBLoggerService;
import cn.edu.gdut.service.ProblemService;
import cn.edu.gdut.service.StatusService;
import cn.edu.gdut.timetask.PageviewCount;
import cn.edu.gdut.util.JSonUtil;
import cn.edu.gdut.util.JudgeServer;
import cn.edu.gdut.util.JudgeServerRoot;
import cn.edu.gdut.util.OJConstant;
import cn.edu.gdut.util.ResultBase;

@Controller
@RequestMapping("/admin")
public class AdminController extends ControllerBase{
	@Autowired
	private DBLoggerService dbLoggerService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private ProblemService problemService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private StatusService statusService;
	@Autowired
	private BugMapper bugMapper;
	
	private final List<Integer> isPublicList = Arrays.asList(0,1,2,3);
	
	private static JudgeServerRoot judgeServerRoot;
	public static LinkedBlockingQueue<JudgeModel> judgeQueue = new LinkedBlockingQueue<JudgeModel>(10);
	public static Map<Integer,JudgeServer> servers = new ConcurrentHashMap<Integer,JudgeServer>();

	@RequestMapping(value = "/logger")
	public String logger(String type, String topic,@RequestParam("page") Integer page, Model model) {
		DBLoggerQuery dbLoggerQuery = new DBLoggerQuery(constantService.getAsInteger(OJConstant.Query.PAGESIZE, 20));
		dbLoggerQuery.setPage(page);
		if (StringUtils.isNotEmpty(type))
			dbLoggerQuery.setType(type);
		if (StringUtils.isNotEmpty(topic))
			dbLoggerQuery.setTopic(topic);
		
		ResultBase<List<DBLoggerModel>> result = dbLoggerService.selectByQuery(dbLoggerQuery);
		if (!result.isSuccess()) {
			addAttribute(model,"errmsg", result.getMsg());
		} else {
			addAttribute(model,"list", result.getValue());
			addAttribute(model, "page", page);
			addAttribute(model, "type", type);
			addAttribute(model, "topic", topic);
		}
		return "admin/listLog";
	}

	@RequestMapping(value = "/viewLog")
	public String viewLog(@RequestParam("id") Long Id, Model model) {
		ResultBase<DBLoggerModel> result = dbLoggerService.selectById(Id);
		if (!result.isSuccess()) {
			model.addAttribute("errmsg", result.getMsg());
			return "error";
		} else {
			model.addAttribute("node", result.getValue());
			return "admin/viewLog";
		}
	}
	
	@RequestMapping("index")
	public String index(){
		return "admin/index";
	}
	
	@RequestMapping("info")
	public void info(HttpServletResponse response){
		SystemInfoModel systemInfoModel = new SystemInfoModel().init();
		JSonUtil.writeToResponse(new Gson().toJson(systemInfoModel), response);
		return ;
	}
	
	@RequestMapping("gcNow")
	public void gcNow(HttpServletResponse response){
		try{
			System.gc();
			JSonUtil.writeToResponse(new Gson().toJson(null), response);
		} catch (Exception e){
			JSonUtil.writeToResponse(new Gson().toJson(e), response);
		}
	}
	
	@RequestMapping("problem")
	public String problem(Model model,@RequestParam("page")Integer page){
		ResultBase<List<ProblemModel>> result = problemService.selectList(null, page, true);
		if (!result.isSuccess()){
			addErrorMsg(model, result.getMsg());
			return "error";
		} else {
			addAttribute(model, "list", result.getValue());
			addAttribute(model, "page", page);
			return "admin/problem";
		}
	}
	
	@RequestMapping("/addProblem")
	public String addProblem(Model model){
		ProblemModel problemModel = new ProblemModel();
		addAttribute(model, "model", problemModel);
		return "admin/editProblem";
	}
	
	@RequestMapping("/editProblem")
	public String editProblem(@RequestParam("pid")Integer pid,Model model){
		ResultBase<ProblemModel> result = problemService.selectById(pid);
		if (!result.isSuccess()){
			addErrorMsg(model, result.getMsg());
			return "error";
		}
		if (result.getValue() == null){
			addErrorMsg(model, "problem not found");
			return "error";
		}
		model.addAttribute("model",result.getValue());
		return "admin/editProblem";
	}
	
	@RequestMapping(value="/doEditProblem",method=RequestMethod.POST)
	public String doEditProblem(@ModelAttribute("problemModel")ProblemModel problemModel,Model model){
		//System.out.println(new Gson().toJson(problemModel));
		ResultBase<Integer> result = null;
		if (problemModel.getId() != null){
			result = problemService.modify(problemModel);
		} else {
			result = problemService.add(problemModel);
		}
		if (result.isSuccess() && result.getValue()==1){
			addSuccessMsg(model, "edit problem success");
			return "redirect:/admin/problem.htm?page=0";
		} else {
			if (!result.isSuccess()){
				addErrorMsg(model, result.getMsg());
			} else {
				addErrorMsg(model, "no problem change");
			}
			addAttribute(model, "model", problemModel);
			return "admin/editProblem";
		}
	}

	@RequestMapping(value = "deleteProblem",method=RequestMethod.POST)
	public void doDeleteProblem(@RequestParam("pid")Integer pid,HttpServletResponse response){
		ResultBase<Integer> result = problemService.delete(pid);
		JSonUtil.writeToResponse(result.toJson(Integer.class), response);
	}
	
	@RequestMapping(value= "publicProblem",method=RequestMethod.POST)
	public void publicProblem(@RequestParam("pid")Integer pid,@RequestParam("isPublic")Integer isPublic,HttpServletResponse response){
		ResultBase<Integer> result = problemService.modifyPubic(pid,isPublic);
		JSonUtil.writeToResponse(result.toJson(Integer.class), response);
	}
	
	@RequestMapping("contest")
	public String contest(Model model,@RequestParam("page")Integer page){
		ResultBase<List<ContestModel>> result = contestService.getContestList(page,isPublicList);
		if(result.isSuccess()){
			addAttribute(model, "list", result.getValue());
			return "admin/contest";
		} else {
			addErrorMsg(model, result.getMsg());
			return "error";
		}
	}
	
	@RequestMapping("publicContest")
	public void publicContest(@RequestParam("cid")Integer cid,@RequestParam("isPublic")Integer isPublic,HttpServletResponse response){
		ResultBase<Integer> result = contestService.setContestIsPublic(cid, isPublic);
		JSonUtil.writeToResponse(result.toJson(Integer.class), response);
	}
	
	@RequestMapping("editContest")
	public String editContest(@RequestParam("cid")Integer cid,Model model){
		ResultBase<ContestEntiry> result = contestService.getContestEntity(cid);
		if (!result.isSuccess()){
			addErrorMsg(model, result.getMsg());
			return "error";
		}
		if(result.getValue() == null){
			addErrorMsg(model, "contest not found");
			return "error";
		}
		addAttribute(model, "model", result.getValue());
		return "admin/editContest";
	}
	
	@RequestMapping("getNewProblems")
	public String getNewProblems(@RequestParam("list")String list,Integer del,String add,Integer moveUp,Integer cid,Model model){
		List<Integer> problems = new Gson().fromJson(list, new TypeToken<List<Integer>>() {}.getType());
		if (problems==null) {
			problems = new ArrayList<Integer>();
		}
		if (del != null){
			problems.remove(del.intValue());
		} else if (add != null){
			List<Integer> newList = getPids(add);
			for (Integer pid: newList){
				if (problems.contains(pid)){
					continue;
				} else {
					problems.add(pid);
				}
			}
		} else if (moveUp != null){
			int last = moveUp -1;
			if (last >= 0){
				int save = problems.get(moveUp);
				problems.remove(moveUp.intValue());
				problems.add(last, save);
			}
		}
		while(problems.size() > 26){
			problems.remove(26);
		}
		
		List<ProblemModel> problemModels = new ArrayList<ProblemModel>();
		for (Integer pid : problems){
			ResultBase<ProblemModel> result = problemService.selectById(pid);
			ProblemModel problemModel;
			if (result.isSuccess()){
				problemModel = result.getValue();
				if (problemModel == null){
					problemModel = new ProblemModel();
					problemModel.setTitle("Problem not found");
					problemModel.setTimeLimit(null);
					problemModel.setMemLimit(null);
					problemModel.setId(pid);
				}
			} else {
				problemModel = new ProblemModel();
				problemModel.setTitle(result.getMsg());
			}
			problemModels.add(problemModel);
		}
		addAttribute(model, "list", problemModels);
		addAttribute(model, "pids", new Gson().toJson(problems));
		addAttribute(model, "cid", cid);
		return "admin/getNewProblems";
	}

	private List<Integer> getPids(String add) {
		String[] pids = add.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (String pid:pids){
			String[] cut = pid.split("-");
			if (cut.length == 1){
				list.add(Integer.parseInt(cut[0]));
			} else {
				int beg = Integer.parseInt(cut[0]);
				int end = Integer.parseInt(cut[1]);
				if (beg > end) continue;
				for (int i=beg;i<=end;i++){
					list.add(i);
					if (list.size() > 26) break;
				}
			}
		}
		return list;
	}

	@RequestMapping(value = "doEditContest" , method = RequestMethod.POST)
	public String doEditContest(
			@RequestParam(value="cid",required = false)Integer cid,
			@RequestParam("title")String title,
			@RequestParam("startTime")String startTime,
			@RequestParam("endTime")String endTime,
			@RequestParam("problems")String problems,
			Model model) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ContestModel contestModel = new ContestModel();
		contestModel.setId(cid);
		contestModel.setTitle(title);
		contestModel.setStartTime(sdf.parse(startTime));
		contestModel.setEndTime(sdf.parse(endTime));
		contestModel.setProblems(problems);
		
		ResultBase<Integer> result ;
		if (cid == null){
			result = contestService.add(contestModel);
		} else {
			result = contestService.update(contestModel);
		}
		if(result.isSuccess() && result.getValue() != 0){
			return "redirect:/admin/contest.htm?page=0";
		} else {
			if (result.isSuccess()){
				addErrorMsg(model, "edit contest error");
			} else {
				addErrorMsg(model, result.getMsg());
			}
			addAttribute(model, "model", contestModel);
			return "admin/editContest";
		}
	}
	
	@RequestMapping("addContest")
	public String addContest(){
		return "admin/editContest";
	}
	
	
	private static Object lock = new Object();
	@RequestMapping(value="newJudgeServer",method=RequestMethod.POST)
	public String newJudgeServer(@RequestParam("port")Integer port,Model model){
		if (judgeServerRoot == null){
			synchronized (lock) {
				if (judgeServerRoot == null){ 
					judgeServerRoot = new JudgeServerRoot(port);
					judgeServerRoot.start();
				}
			}
		}
		return judgeStatus(model);
	}
	
	@RequestMapping(value="newJudgeClient",method=RequestMethod.POST)
	public String newJudgeClient(@RequestParam("host")String host,@RequestParam("port")Integer port,Model model){
		try {
			Socket socket = new Socket(host,port);
			JudgeServer judgeServer = new JudgeServer(socket, judgeQueue);
			judgeServer.start();
			servers.put(judgeServer.getUniqueId(), judgeServer);
		} catch (Exception e) {
			
		}
		return judgeStatus(model);
	}
	
	@RequestMapping(value="stopJudgeServer",method=RequestMethod.POST)
	public String stopJudgeServer(Model model){
		if (judgeServerRoot.getServerSocket()!=null){
			try {
				judgeServerRoot.tryToStop();
			} catch (Exception e) {
				judgeServerRoot.setStatus("Exception : "+e.toString());
			}
		}
		return judgeStatus(model);
	}
	
	@RequestMapping("judgeStatus")
	public String judgeStatus(Model model){
		if(judgeServerRoot!=null && !judgeServerRoot.isAlive()){
			judgeServerRoot = null;
		}
		addAttribute(model, "model", judgeServerRoot);
		addAttribute(model, "servers", servers);
		return "admin/judgeStatus";
	}
	
	@RequestMapping(value = "disConnectJudgeClient",method = RequestMethod.POST)
	public String disConnectJudgeClient(@RequestParam("tid")Integer tid,Model model){
		JudgeServer judgeServer = servers.get(tid);
		if (judgeServer != null){
			judgeServer.tryToStop();
		}
		return judgeStatus(model);
	}
	
	@RequestMapping("appStatus")
	public String appStatus(Model model){
		addAttribute(model, "minPv", PageviewCount.getLastMin());
		addAttribute(model, "hourPv", PageviewCount.getLastHour());
		addAttribute(model, "queueLength", judgeQueue.size());
		addAttribute(model, "judgeCnt", PageviewCount.getJudgeCnt());
		return "admin/appStatus";
	}
	
	@RequestMapping(value="rejudge",method=RequestMethod.POST)
	public void rejudge(Integer rid,Integer pid,Integer cid,Boolean includeAc,HttpServletResponse response){
		ResultBase<Integer> result = statusService.rejudge(rid, pid, cid, includeAc);
		JSonUtil.writeToResponse(result.toJson(Integer.class), response);
	}
	
	@RequestMapping("picture")
	public String picture(){
		return "admin/picture";
	}
	
	@RequestMapping("bugView")
	public String bugView(Model model){
		List<BugModel> list = bugMapper.select();
//		for (BugModel bugModel:list){
//			bugModel.setDescription(HtmlEncodeUtil.toHtml(bugModel.getDescription()));
//			bugModel.setUrl(HtmlEncodeUtil.toHtml(bugModel.getDescription()));
//			bugMapper.update(bugModel);
//			break;
//		}
		//System.out.println(list);
		addAttribute(model, "list", list);
		return "admin/bugView";
	}
}
