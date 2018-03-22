package com.casic.util;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect 
public class AspectBean {
	
	
	  //日志管理方法  
	@Before("execution(* com.casic.util.HttpFactory.*(..))")
    public  void beforeLog(){  
        System.out.println("开始执行");  
    }  
	@After("execution(* com.casic.util.HttpFactory.*(..))") 
    public void afterLog(){  
        System.out.println("执行完毕");  
    }  
}
