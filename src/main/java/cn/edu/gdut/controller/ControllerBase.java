package cn.edu.gdut.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import cn.edu.gdut.service.ConstantService;

@Controller
public class ControllerBase {
	@Autowired
	private ConstantService constantService;
	
	protected void addAttributeMap(Model model,Map<String, Object> map) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Entry<String, Object> entry: map.entrySet()){
			model.addAttribute(entry.getKey(),entry.getValue());
			stringBuffer.append("<input id=\""+entry.getKey()+"\" value=\""+entry.getValue()+"\" type = \"hidden\">");
		}
		model.addAttribute("hiddenFiled",stringBuffer.toString());
	}
	
	protected void getAndPutValue(Map<String,Object> map, String paramName, Object def) {
		if (def instanceof Integer) {
			Integer defInteger = (Integer) def;
			map.put(paramName, constantService.getAsInteger(paramName, defInteger));			
		} else if (def instanceof String) {
			String defString = (String) def;
			map.put(paramName, constantService.getAsString(paramName, defString));
		}
	}
	
	protected void addAttribute(Model model,String k,Object v){
		model.addAttribute(k, v);
	}
	
	protected void addErrorMsg(Model model,String errmsg){
		addAttribute(model, "errmsg", errmsg);
	}
	protected void addSuccessMsg(Model model,String sucmsg){
		addAttribute(model, "sucmsg", sucmsg);
	}
}
