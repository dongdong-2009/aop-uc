package com.casic.subsysInterface.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.casic.subsysInterface.model.InterfaceClassifyBean;
import com.casic.subsysInterface.service.SubSystemInterfaceService;
import com.casic.tenant.model.TenantInfo;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;

/***
 * 子系统管理类，包括子系统注册、编辑、删除以及子系统接口注册
 * @author think
 * 2016 07 22
 */
@Controller
@RequestMapping("/interface")
public class SubSystemInterfaceController extends BaseController{
	
	
	@Resource
	private SubSystemService service;
	@Resource
	private ResourcesService resourcesService;
	
	@Resource
	private SubSystemInterfaceService subSystemInterfaceService;
	
	

	@RequestMapping("tree")
	@ResponseBody
	public List<SubSystem> tree(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		List<SubSystem> list=service.getAll();
		SubSystem root=new SubSystem();
		root.setSystemId(0l);
		root.setParentId(-1l);
		root.setSysName("所有系统");
		list.add(root);
		
		return list;
	}
	
	/**
	 * 取得子系统管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String compType=RequestUtil.getString(request,"compType");
		ModelAndView mv = new ModelAndView("/subsystem/subSystemInterfaceClassifyList.jsp");
		List<InterfaceClassifyBean> list=subSystemInterfaceService.getAll(new QueryFilter(request,"InterfaceClassifyBeanItem"));
		mv.addObject("interfaceClassifyList",list).addObject("compType", compType);
		return mv;
	}
	
	
	
	@RequestMapping("edit")
	@Action(description="编辑加班情况")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		ModelAndView mv = new ModelAndView("/subsystem/subSystemInterfaceClassifyEdit.jsp");
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		InterfaceClassifyBean interfaceClassifyBean=null;
		if(id!=0){
			interfaceClassifyBean= subSystemInterfaceService.getById(id);
			
		}else{
			interfaceClassifyBean=new InterfaceClassifyBean();
		}
		return mv.addObject("interfaceClassifyBean",interfaceClassifyBean).addObject("returnUrl", returnUrl);
	}
	
	


	/**
	 * 删除子系统管理
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
			subSystemInterfaceService.delByIds(lAryId);
			
			//从session清除当前系统。
			SubSystemUtil.removeSystem();
			
			message=new ResultMessage(ResultMessage.Success,"删除接口分类成功");
		}catch (Exception e) {
			message=new ResultMessage(ResultMessage.Fail,"删除接口分类失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}


	/**
	 * 取得子系统管理明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		SubSystem po = service.getById(id);		
		return this.getAutoView().addObject("subSystem", po);
		
	}
	
	/**
	 * 导出子系统资源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportXml")
	public void exportXml(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		long id=RequestUtil.getLong(request, "systemId");
		if(id!=0){
			String strXml=service.exportXml(id);
			SubSystem subSystem=service.getById(id);
			String fileName=subSystem.getAlias();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(fileName,"UTF-8") + ".xml");
			response.getWriter().write(strXml);
			response.getWriter().flush();
			response.getWriter().close();
		}
		
	}
	
	/**
	 * 导入子系统资源。
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("importXml")
	@Action(description="导入子系统资源")
	public void importXml(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException
	{
		long systemId=RequestUtil.getLong(request, "systemId");
		MultipartFile fileLoad = request.getFile("xmlFile");
		ResultMessage resultMessage = null;
		try {
			service.importXml(fileLoad.getInputStream(),systemId);
			resultMessage = new ResultMessage(ResultMessage.Success, "导入成功!");
			writeResultMessage(response.getWriter(), resultMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"导入失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
}
