package cn.edu.gdut.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext=applicationContext;
    }
    
    public static ApplicationContext getContext(){
        return SpringUtil.applicationContext;
    }
    
    public static Object getBean(String name){
    	return getContext().getBean(name);
    }
}