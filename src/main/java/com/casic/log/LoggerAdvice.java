package com.casic.log;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.MethodBeforeAdvice;

import com.casic.log.model.InterfaceInvokeLog;
import com.casic.log.service.InterfaceInvokeLogService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;

//@Aspect
public class LoggerAdvice {
	
	 private Logger logger = Logger.getLogger(LoggerAdvice.class);
	 
	 @Resource
	 private InterfaceInvokeLogService service;
	 
	  //日志管理方法  
		@Around("execution(* com.casic.util.HttpFactory.*(..))")
	    public  Object beforeLog(ProceedingJoinPoint point) throws Throwable{  
	        System.out.println("开始执行");
	        logger.debug("enetr log beforeLog method============================");
	        
	        String requestUrl=null;
	        String requestParams=null;
	        Object[] params = point.getArgs();
	        if(params != null && params.length>0){
	        	requestUrl=params[0].toString();
	        	requestParams=params[1].toString();
	        }
	        
	        //执行目标方法
	        Object responseResult = point.proceed();
	        
	        Signature sig = point.getSignature();
	        MethodSignature msig = null;
	        if (!(sig instanceof MethodSignature)) {
	            throw new IllegalArgumentException("该注解只能用于方法");
	        }
	        msig = (MethodSignature) sig;
	        Object target = point.getTarget();
	        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	        
	        // 获取当前的操作用户
	     	ISysUser curUser = ContextUtil.getCurrentUser();
	     	try {
				InterfaceInvokeLog interfaceLog=new InterfaceInvokeLog();
				interfaceLog.setId(UniqueIdUtil.genId());
				if(curUser != null){
					interfaceLog.setExecutorId(curUser.getUserId());
				}
				interfaceLog.setExecuteTime(new Date());
				interfaceLog.setExecuteMethod(currentMethod.getName());
				HttpServletRequest request = RequestUtil.getHttpServletRequest();
				if (request != null){
					interfaceLog.setFromIp(request.getRemoteAddr());
				}
				
				interfaceLog.setRequestUrl(requestUrl);
				interfaceLog.setRequestParams(requestParams);
				interfaceLog.setResponseResult(responseResult.toString());
				service.add(interfaceLog);
//				System.out.println("结束执行");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return responseResult;
	    } 
		
		
	 
}
