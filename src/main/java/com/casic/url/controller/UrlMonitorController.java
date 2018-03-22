package com.casic.url.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.compass.core.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.casic.url.model.UrlBean;
import com.casic.url.service.UrlMonitorService;
import com.casic.util.DateUtil;
import com.casic.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.SysOrgInfo;

/***
 * 子系统管理类，包括子系统注册、编辑、删除以及子系统接口注册
 * @author think
 * 2016 07 22
 */
@Controller
@RequestMapping("/urlMonitor")
public class UrlMonitorController extends BaseController{
	
	@Resource
	private UrlMonitorService urlMonitorService;
	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
	
	/***
	 * 获得url监控信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/monitor/urlMonitorList.jsp");
		//List<UrlBean> list=urlMonitorService.getAll(new QueryFilter(request,"UrlBeanItem"));
		List<UrlBean> list=urlMonitorService.getAllUrls();
		mv.addObject("urlBeanList",list);
		return mv;
	}
	
	/*@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mv = new ModelAndView("/monitor/urlMonitorGet.jsp");
		long id=RequestUtil.getLong(request,"id");
		UrlBean urlBean = urlMonitorService.getById(id);		
		return mv.addObject("urlBean", urlBean);
		
	}*/
	
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ModelAndView mv = new ModelAndView("/monitor/urlMonitorGet.jsp");
		String url=RequestUtil.getString(request,"url");
		UrlBean urlBean = urlMonitorService.getByUrl(url);
		if(urlBean!=null){
			String endTime = urlBean.getEndTime();
			endTime = DateUtil.getTimeStr(endTime);
			urlBean.setEndTime(endTime);
		}
		return mv.addObject("urlBean", urlBean);
		
	}
	
	@RequestMapping("getDetail")
	public ModelAndView getDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ModelAndView mv = new ModelAndView("/monitor/urlMonitorGetDetail.jsp");
		String url=RequestUtil.getString(request,"url");
		String flag=RequestUtil.getString(request,"flag");
		QueryFilter filter=new QueryFilter(request,"UrlBeanItem");
		if(flag!=null&&!("").equals(flag)){
			if(flag.equals("now")){
				//当天的url监管情况
				filter.addFilter("now", "now");
				filter.addFilter("url", url);
				
			}
			else{
				filter.addFilter("now", null);
				filter.addFilter("url", url);
				
			}
				
		}
		List<UrlBean> urlList=urlMonitorService.getAllUrlDetails(filter);
		for (UrlBean urlBean : urlList) {
			
			if(urlBean!=null&&urlBean.getStartTime()!=null&&!("").equals(urlBean.getStartTime())){
				String endTime = urlBean.getEndTime();
			
				String startTime=urlBean.getStartTime();
				endTime = DateUtil.getTimeStr(endTime);
				startTime=DateUtil.getTimeStr(startTime);
				
				urlBean.setStartTime(startTime);
				urlBean.setEndTime(endTime);
				
			}
		}
		
	
		return mv.addObject("urlBeanList",urlList);
		
	}
	
	
	@RequestMapping("graphDisplay")
	public ModelAndView graphDisplay(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ModelAndView mv = new ModelAndView("/monitor/urlGraphDisplay.jsp");
		String url=RequestUtil.getString(request,"url");
		
		return mv.addObject("url", url);
		
	}
	
	@RequestMapping("echarts")
	public ModelAndView echarts(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ModelAndView mv = new ModelAndView("/monitor/echarts.jsp");
		String url=RequestUtil.getString(request,"url");
		
		return mv.addObject("url", url);
		
	}
	/**
	 * 加载数据文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadData")
	@ResponseBody
	public ResultMessage loadData(HttpServletRequest request) throws Exception{
		ResultMessage resultMessage=null;
		String url = RequestUtil.getString(request,"url");
		try {
			UrlBean urlBean = urlMonitorService.loadDataByUrl(url);
	      
			resultMessage=new ResultMessage(ResultMessage.Success, (JSONObject.fromObject(urlBean)).toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage=new ResultMessage(ResultMessage.Fail, e.getMessage());
		}
		return resultMessage;
	}
	
}
