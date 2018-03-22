package com.casic.subsysManager.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.TenantInfoService;
import com.casic.url.model.UrlBean;
import com.casic.url.service.UrlMonitorService;
import com.casic.util.HttpClientUtil;
import com.casic.util.PropertiesUtils;
import com.casic.util.SmsUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;

/***
 * 子系统账号管理类，包括子系统用户账号、增加、编辑、删除以及子系角色的查询、增加、修改、删除
 * @author wangshumin
 * 2017 10 17
 */
@Controller
@RequestMapping("/subSystem")
public class SubSystemManagerController extends BaseController{
	
	
	@Resource
	private SubSystemService service;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
	@Resource
	private UrlMonitorService urlMonitorService;
	@Resource
	private TenantInfoService tenantInfoService;
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 编辑企业认证信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("index")
	@Action(description = "编辑企业认证信息")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/subsystem/subSystemIndex.jsp");
		//获取当前登录用户对象
		SysUser user = (SysUser) ContextUtil.getCurrentUser();
		String account = user.getAccount();	
	/*	String name = user.getFullname();
		Long userId = user.getUserId();*/
		ArrayList<String> supper=new ArrayList<String>();
		//注释掉的是可以从properties文件中获取账户的代码然后添加到用户管理
	/*    String propertys = PropertiesUtils.getProperty("useraccount");
		if(propertys!=null){
			String[] accounts = propertys.split(",");
			for (int i = 0; i < accounts.length; i++) {
				String acc = accounts[i];				
		        supper.add(acc);
				System.out.println("添加的账户有：+++++"+acc);
			}			
		}*/
	    supper.add("5551548_meepo");
		supper.add("600225_hlgsjgw");
		supper.add("3580409_hwjAccount");
		supper.add("600632_system");
		supper.add("600606_system");
		supper.add("6130584_system");
        /*supper.add("sysadmin");
		supper.add("confadmin");*/
		supper.add("100");
		//获取当前登录用户的企业
		TenantInfo info=null;
		if(user!=null){
		  info=  tenantInfoService.getById((user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId());
		}
		//二级运营管理员登录权限
		boolean flag=false;
		if(supper.contains(account)){//配置用户有的二级运营管理员登录权限；导入批量显示
			flag=false;
			return mv.addObject("user", user).addObject("flag",flag).addObject("bl",false);
		}else if(user.getFromSysId()!=null&&"100".equals(user.getOrgSn().toString())&&!"confadmin".equals(account)){//二级运营管理员登录权限
			flag=false;
			return mv.addObject("user", user).addObject("flag",flag).addObject("bl",false);
		}
		else if("confadmin".equals(account)){//超级管理员权限
			flag=true;
			return mv.addObject("flag",flag).addObject("bl",false);
		}else{
			if(!"confadmin".equals(account)&& !supper.contains(account) && !"100".equals(user.getOrgSn().toString())){//没有权限登录运营平台和普通人员
				mv = new ModelAndView("/commons/noPower.jsp");
				//response.sendRedirect("/tenant/maintain.ht");
				return mv.addObject("info", info);
			}	
		}	
		return null;
	}
	
	

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
		request.setCharacterEncoding("utf-8");;
		String compType=RequestUtil.getString(request,"compType");
		
		ModelAndView mv = new ModelAndView("/subsystem/subSystemList.jsp");
		QueryFilter queryFilter=new QueryFilter(request, "subSystemItem");
		List<SubSystem> list=service.getAll(queryFilter);
		mv.addObject("subSystemList",list).addObject("compType",compType);
		
		return mv;
	}
	
	//begin
	@RequestMapping("synchronizeForSystem")
	public ModelAndView synchronizeForSystem(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String userIds = request.getParameter("userIds");
		ModelAndView mv = new ModelAndView("/subsystem/synchronizeForSystem.jsp");
		List<SubSystem> list=service.getAll(new QueryFilter(request,"synchronizeForSystemItem"));
		return mv.addObject("synchronizeForSystemList",list).addObject("userIds",userIds);
	}
	
	@RequestMapping("synchronizeForTenant")
	public ModelAndView synchronizeForTenant(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String tenantIds = request.getParameter("tenantIds");
		ModelAndView mv = new ModelAndView("/subsystem/synchronizeForTenant.jsp");
		List<SubSystem> list=service.getAll(new QueryFilter(request,"synchronizeForTenantItem"));
		return mv.addObject("tenantList",list).addObject("tenantIds",tenantIds);
	}
	
	@RequestMapping("userDataToSystem")
	@ResponseBody
	public ResultMessage userDataToSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//boolean isExist = false;
		ResultMessage res = null;
		String userIds = request.getParameter("userIds");
		String systemIds = request.getParameter("systemIds");
		String[] users = userIds.split(",");
		String[] systems = systemIds.split(",");
		for (String systemId : systems) {
			SubSystem subSystem = service.getById(Long.parseLong(systemId));
			for (String userId : users) {
				ISysUser sysUser = sysUserService.getById(Long.parseLong(userId));
				int i = 1;
				List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassifyWithSys(i,"user", systemId);
				for(InterfaceUrlBean urlBean:urls){
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("sysUser", sysUser);
					params.put("data", data);
					params.put("systemId", urlBean.getSubId());
					String result = "";
				    Date start=new Date();   
				    java.text.SimpleDateFormat  formatter= new  java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
				    String startTime = formatter.format(start);
				    UrlBean urlMonitor = new UrlBean();
				    long id = UniqueIdUtil.genId();
					urlMonitor.setId(id);
					urlMonitor.setUrl(urlBean.getSubIndexUrl()+urlBean.getUrl());
					urlMonitor.setStartTime(startTime);
					urlMonitor.setSubSysId(urlBean.getSubId()+"");
					int isSuccess =1;
				    try{
				    	System.out.println("同步开始------"+urlBean.getSubIndexUrl()+urlBean.getUrl());
				    	result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
				    	System.out.println("同步结束------"+result);
				    	res=new ResultMessage(ResultMessage.Success, "接口同步成功");
				    }
				    catch (Exception e){
				    	isSuccess = 0;
				    	System.out.println("同步失败------"+e.getMessage());
				    	res=new ResultMessage(ResultMessage.Fail, "接口同步失败");
				    	e.printStackTrace();
				    }
				    finally {
				    	
				    	System.out.println("调用子系统接口结果="+result);
				    	Date end=new Date();  
						String endTime = formatter.format(end);
						urlMonitor.setEndTime(endTime);
						urlMonitor.setIsSuccess(isSuccess);
						urlMonitorService.add(urlMonitor);
				    }
				}
				
			}
		}
		return res;
	}
	
	
	//end
	
	@RequestMapping("edit")
	@Action(description="编辑加班情况")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		ModelAndView mv = new ModelAndView("/subsystem/subSystemEdit.jsp");
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		SubSystem subSystem=null;
		if(id!=0){
			subSystem= service.getById(id);
			
		}else{
			subSystem=new SubSystem();
		}
		return mv.addObject("subSystem",subSystem).addObject("returnUrl", returnUrl);
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
			service.delByIds(lAryId);
			
			//从session清除当前系统。
			SubSystemUtil.removeSystem();
			
			message=new ResultMessage(ResultMessage.Success,"删除子系统成功");
		}catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.Fail,"删除子系统失败");
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
