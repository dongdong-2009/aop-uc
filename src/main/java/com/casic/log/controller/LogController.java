package com.casic.log.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




import com.hotent.core.annotion.Action;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.CookieUitl;
@Controller
@RequestMapping("/log")
public class LogController extends BaseController{
	private Logger logger=Logger.getLogger(LogController.class);
	@RequestMapping(value = "save", method = RequestMethod.GET)
	@Action(description = "航天云网调用")
	public void toLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";
	    Date time=new Date();   
	    java.text.SimpleDateFormat  formatter= new  java.text.SimpleDateFormat("yyyyMMddHHmmss");
	    
	    String system = request.getParameter("system");
	    String ip = request.getRemoteAddr();
	    String comeTime = formatter.format(time);
		String domain=request.getParameter("domain");
		String url=request.getParameter("url");
		//String title = java.net.URLDecoder.decode(request.getParameter("title"));//用request获取URL传递的中文参数;
		String referrer=request.getParameter("referrer");
		if(referrer==null||"".equals(referrer)){
			referrer = "0";
		}
		String cookie = CookieUitl.getValueByName("CASTGC", request);
		if(cookie==null||"".equals(cookie)){
			cookie ="0";
		}
		/*String sh=request.getParameter("sh");
		String sw=request.getParameter("sw");
		String cd=request.getParameter("cd");
		String lang=request.getParameter("lang");
		String system=request.getParameter("system");*/
		result =comeTime+" "+ip+" "+system+" "+domain+" "+ url+" "+referrer+" "+cookie;
		logger.debug(result);
	}

}
