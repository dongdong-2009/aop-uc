package com.casic.subsysInterface.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.service.system.SubSystemUtil;

/***
 * 子系统管理类，包括子系统注册、编辑、删除以及子系统接口注册
 * @author think
 * 2016 07 22
 */
@Controller
@RequestMapping("/interfaceUrl")
public class SubSystemInterfaceUrlController extends BaseController{
	
	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
	
	/**
	 * 取得接口地址分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/subsystem/subSystemInterfaceUrlList.jsp");
		List<InterfaceUrlBean> list=subSystemInterfaceUrlService.getAll(new QueryFilter(request,"InterfaceUrlBeanItem"));
		mv.addObject("interfaceUrlBeanList",list);
		return mv;
	}
	
	
	
	@RequestMapping("edit")
	@Action(description="编辑接口地址情况")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		ModelAndView mv = new ModelAndView("/subsystem/subSystemInterfaceUrlEdit.jsp");
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		InterfaceUrlBean interfaceUrlBean=null;
		if(id!=0){
			interfaceUrlBean= subSystemInterfaceUrlService.getById(id);
			
		}else{
			interfaceUrlBean=new InterfaceUrlBean();
		}
		return mv.addObject("interfaceUrlBean",interfaceUrlBean).addObject("returnUrl", returnUrl);
	}
	

	/**
	 * 删除接口地址管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			subSystemInterfaceUrlService.delByIds(lAryId);
			
			//从session清除当前系统。
			SubSystemUtil.removeSystem();
			
			message=new ResultMessage(ResultMessage.Success,"删除接口地址成功");
		}catch (Exception e) {
			message=new ResultMessage(ResultMessage.Fail,"删除接口地址失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	
}
