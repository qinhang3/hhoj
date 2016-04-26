
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinhang.qh on 15/4/8.
 */
public class ScheduleMonitorEntity {
    @Expose
    private List <String> fullTime;

    @Expose
    private String routerKey;

    @Expose
    private String tairIndexKey;

    @Expose
    private List<String> solver;

    @Expose
    private String status;

    @Expose
    private Integer checkDay;

    @Expose
    private ScheduleTimeCheckRule timeCheckRule;

    @Expose
    private EntityCheckRule entityCheckRule;

    public ScheduleTimeCheckRule getTimeCheckRule() {
        return timeCheckRule;
    }

    public void setTimeCheckRule(ScheduleTimeCheckRule timeCheckRule) {
        this.timeCheckRule = timeCheckRule;
    }

    public EntityCheckRule getEntityCheckRule() {
        return entityCheckRule;
    }

    public void setEntityCheckRule(EntityCheckRule entityCheckRule) {
        this.entityCheckRule = entityCheckRule;
    }

    
    

    public List<String> getFullTime() {
        return fullTime;
    }

    public void setFullTime(List<String> fullTime) {
        this.fullTime = fullTime;
    }

    public String getRouterKey() {
        return routerKey;
    }

    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

    public String getTairIndexKey() {
        return tairIndexKey;
    }

    public void setTairIndexKey(String tairIndexKey) {
        this.tairIndexKey = tairIndexKey;
    }

    public List<String> getSolver() {
        return solver;
    }

    public void setSolver(List<String> solver) {
        this.solver = solver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCheckDay() {
        return checkDay;
    }

    public void setCheckDay(Integer checkDay) {
        this.checkDay = checkDay;
    }

    public static void main(String[] args){
        ScheduleMonitorEntity sme = new ScheduleMonitorEntity();
        sme.fullTime = new ArrayList<String>();
        String def = new String("090000_240000");
        sme.fullTime.add(def);
        
        sme.setRouterKey("ald");
        sme.setTairIndexKey("testmiao");
        
        sme.solver = new ArrayList<String>();
        sme.solver.add("077810");
        sme.solver.add("073947");
        
        sme.status = "ON";
        sme.checkDay = 3;
       
        sme.timeCheckRule = new ScheduleTimeCheckRule();
        sme.timeCheckRule.setSmsText("瞄一眼未来三天存在未排期的时间段");
        sme.timeCheckRule.setTimeFormat("yyyy-MM-dd HH:mm:ss");
        sme.timeCheckRule.setSplit(" ");
        sme.timeCheckRule.setSectionFormat("$startTime～～$endTime");
        
        sme.entityCheckRule = new EntityCheckRule();
        sme.entityCheckRule.setSmsText("排期$startTime~$endTime存在异常");
        sme.entityCheckRule.setTimeFormat("yyyy-MM-dd HH:mm:ss");
        sme.entityCheckRule.setRowFormat("[第$row个商品，第$columns列异常]");
        sme.entityCheckRule.setSplitIn("&");
        sme.entityCheckRule.setSplitOut(",");
        ArrayList<String> regexs = new ArrayList<String>();
        regexs.add("^([1-9][0-9]*)$");
        regexs.add("^(0\\.[0-9]*[1-9][0-9]*)$");
        sme.entityCheckRule.setRegexs(regexs);
       
        Gson gson = new Gson();
        System.out.println(gson.toJson(sme));


    }
}
