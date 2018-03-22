package com.casic.tenant.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.appleframe.dbexchange.web.entity.Org;
import com.appleframe.utils.date.DateUtils;
import com.casic.auditLog.service.tenantAudit.tenantInfoAuditLogService;
import com.casic.cloud.service.config.AptitudeService;
import com.casic.msgLog.model.SysMsgLog;
import com.casic.msgLog.service.SysMsgLogService;
import com.casic.platform.saas.role.SaasRole;
import com.casic.platform.saas.role.SaasRoleService;
import com.casic.service.UcSysAuditService;
import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.casic.tenant.dto.BranchDto;
import com.casic.tenant.dto.ResultDto;
import com.casic.tenant.model.Aptitude;
import com.casic.tenant.model.BranchBean;
import com.casic.tenant.model.TenantAddress;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.BranchBeanService;
import com.casic.tenant.service.TenantAddressService;
import com.casic.tenant.service.TenantInfoService;
import com.casic.url.model.UrlBean;
import com.casic.url.service.UrlMonitorService;
import com.casic.user.controller.JMSRunableThread;
import com.casic.util.CallableThread;
import com.casic.util.DateUtil;
import com.casic.util.HttpClientUtil;
import com.casic.util.HttpFactory;
import com.casic.util.MessageUtil;
import com.casic.util.PropertiesUtils;
import com.casic.util.SmsUtil;
import com.casic.util.StringUtil;
import com.fr.report.core.A.I;
import com.fr.script.function.UPPER;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.ICache;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.dao.system.SysUserOrgDao;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysOrgTypeService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;
import com.ixinnuo.credit.ws.model.ResponseData;

/**
 * 企业控制器
 * @author think
 * 2016 07 05
 */
@Controller
@RequestMapping("/tenant")
public class TenantInfoController extends BaseController{
	
	private final long ywOrgId =2350300l;
	
	
	@Resource
	private SysMsgLogService sysMsgLogService;
	
	@Resource
	private UrlMonitorService urlMonitorService;
	
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private SubSystemService service;
	
	@Resource
	private HttpFactory httpFactory;
	
	
	@Resource
	private SaasRoleService saasRoleService;
	
	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
	
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private SysUserOrgDao sysUserOrgDao;
	@Resource
	private SysUserDao sysUserDao;
	
	@Resource
	private SysOrgTypeService sysOrgTypeService;
	
	@Resource
	private IAuthenticate iAuthenticate;
	
	@Resource
	private TenantInfoService tenantInfoService;
	
	@Resource
	private UcSysAuditService ucSysAuditService;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private BranchBeanService branchBeanService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private ICache iCache;
	
	@Resource
	private TenantAddressService tenantAddressService;
	
	@Resource
	private AptitudeService aptitudeService;
	
	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private tenantInfoAuditLogService tenantInfoAuditLogService;
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	Logger logger = Logger.getLogger(TenantInfoController.class);
	
	private static String openAccount_url ="";
	static {
		openAccount_url = PropertiesUtils.getProperty("openAccount_url");
	}
	/**
	 * 编辑企业认证信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	/*@RequestMapping("index")
	@Action(description = "编辑企业认证信息")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantInfoIndex.jsp");
		TenantInfo info = new TenantInfo();
		SysUser user = new SysUser();
		String returnUrl = "";
		String openId = request.getParameter("openId");
		if(openId!=null&&!"".equals(openId)){
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("access_token", "f60df954-8429-449c-9943-263f873dd8b4");
			Map<String, Object> map = new HashMap();
			map.put("openid", "123");
			params.put("data", map);
			// 获取企业信息
			String tenantdata = HttpClientUtil.JsonPostInvoke("http://10.142.15.18:8080/aop-api-web/api/1.0/sys/get_tenant_by_openid", params, HttpClientUtil.CHARSET_UTF8);
			
			JSONObject tenantjson = JSONObject.fromObject(tenantdata);
			info = (TenantInfo) JSONObject.toBean(tenantjson.getJSONObject("results"), TenantInfo.class);
			if(info!=null){
				info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
			}
			
			//获取用户新消息
			String userdata = HttpClientUtil.JsonPostInvoke("http://10.142.15.18:8080/aop-api-web/api/1.0/sys/get_user_by_openid", params, HttpClientUtil.CHARSET_UTF8);
			
			JSONObject userjson = JSONObject.fromObject(userdata);
			user = (SysUser) JSONObject.toBean(userjson.getJSONObject("results"), SysUser.class);
			returnUrl = request.getHeader("Referer");
		}
		
		else{
			return new ModelAndView("/tenant/error.jsp");
		}
		
		
		
		return mv.addObject("info", info)
				//.addObject("branch", branchs.size()>0?branchs.get(0):null)
				.addObject("returnUrl", returnUrl);
	}*/
	
	
	
	/**
	 * 编辑企业认证信息
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	@RequestMapping("index")
	@Action(description = "编辑企业认证信息")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantInfoIndex.jsp");
		TenantInfo info = new TenantInfo();
		String returnUrl = "";
		String userId = request.getParameter("openId");
		String name="";
		if(userId!=null&&!"".equals(userId)){
			long id = Long.parseLong(userId);
			ISysUser user = sysUserService.getById(id);
			//ContextUtil.setCurrentUserAccount(user.getAccount());
			request.getSession().setAttribute("user", user);
			name = user.getFullname();
			info = tenantInfoService.getById(user.getOrgId());
		}
		
		return mv.addObject("info", info)
				.addObject("name",name)
				//.addObject("branch", branchs.size()>0?branchs.get(0):null)
				.addObject("returnUrl", returnUrl);
	}
	*/
	
	
	
	/**
	 * 统一维护页面首页
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("maintain")
	@Action(description = "统一维护页面")
	public ModelAndView maintain(HttpServletRequest request) throws Exception {
		String menu = request.getParameter("menu");
		String systemId = request.getParameter("systemId");
		String sys_url=null;
		//根据子系统来源去查询子系统的首页地址 
		if(!StringUtil.isEmpty(systemId)){
			SubSystem sub= subSystemService.getById(Long.parseLong(systemId));
			if(sub!=null){
				sys_url=sub.getDefaultUrl();
			}
		}
		boolean isManager = false;//判断是为企业管理员角色，若是的话，为1，不是为0
		ISysUser user = ContextUtil.getCurrentUser();
		user.setOrgSn(user.getOrgId());
		ModelAndView mv = new ModelAndView("/tenant/tenantInfoIndex.jsp");
		TenantInfo info = new TenantInfo();
		String returnUrl = "";
	    List<UserRole>	roleList = userRoleService.getByUserIdAndTenantId(
				user.getUserId(), (!StringUtil.isEmpty(user.getOrgSn()))?(user.getOrgSn()):(user.getOrgId()));
		
		if(roleList!=null){
	 for (UserRole userRole : roleList) {
		/* if(("航天云网-企业管理员").equals(userRole.getRoleName())){
			 isManager=true;
		 }*/
		 if(userRole.getRoleName().contains("企业管理员")){
			 isManager=true;
		 }
	  }
		}
		Long tenantInfoId=(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId();
		info = tenantInfoService.getById(tenantInfoId);
		return mv.addObject("info", info)
				.addObject("returnUrl", returnUrl).addObject("isManager", isManager).addObject("user", user).addObject("menu", menu).addObject("systemId", systemId)
				.addObject("sys_url", sys_url);
	}
	
	
	
	
	/**
	 * 编辑企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("editNew1")
	@Action(description = "新注册个人用户编辑企业")
	public ModelAndView editNew1(HttpServletRequest request) throws Exception {
		Long sysOrgInfoId = 2350300l;
		if(ContextUtil.getCurrentOrgInfoFromSession()!=null){
			sysOrgInfoId  = ContextUtil.getCurrentOrgInfoFromSession()
					.getSysOrgInfoId();
		}
		String returnUrl = RequestUtil.getPrePage(request);
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		if(info!=null){
			info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
		}
		ModelAndView mv = new ModelAndView("/tenant/tenantEditNew1.jsp");
		mv.addObject("info", info)
		.addObject("returnUrl", returnUrl);
		return mv;
	}
	
	
	/**
	 * 编辑企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("baseInfo")
	@Action(description = "新注册个人用户编辑企业")
	public ModelAndView baseInfo(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantBaseInfo.jsp");
		ISysUser user = ContextUtil.getCurrentUser();
		long sysOrgInfoId1 = RequestUtil.getLong(request, "sysOrgInfoId");
		Long sysOrgInfoId;
		if (user!=null) {
			sysOrgInfoId = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}else{
			sysOrgInfoId=(Long)iCache.getByKey("orgInfoId");
		}
		QueryFilter queryFilter = new QueryFilter(request, "aptitudeItem");
		queryFilter.addFilter("infoId", sysOrgInfoId);
		List<Aptitude> aptitudeList = aptitudeService.getAll(queryFilter);
		//如果是个人用户
		String reason=null;
		if(sysOrgInfoId!=this.ywOrgId){
			String returnUrl = RequestUtil.getPrePage(request);
			TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
			//根据企业的状态来判断是否展示原因
			if(1==info.getState()){
				//查询本次实名认证未通过原因
				reason=tenantInfoAuditLogService.getReasonbyTenantId(info.getSysOrgInfoId());
			}
			if(info!=null){
				info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
			}
			mv.addObject("info", info)
			.addObject("returnUrl", returnUrl).addObject("aptitudeList", aptitudeList).addObject("reason", reason);
		}
		return mv;
	}
	/**
	 * 编辑企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("baseInfoSubsystem")
	@Action(description = "新注册个人用户编辑企业")
	public ModelAndView baseInfoSubsystem(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantBaseInfo.jsp");
		ISysUser user = ContextUtil.getCurrentUser();
		long sysOrgInfoId = RequestUtil.getLong(request, "sysOrgInfoId");
		String returnUrl = RequestUtil.getPrePage(request);
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		mv.addObject("info", info).addObject("returnUrl", returnUrl);
		return mv;
	}
	
	@RequestMapping("updateInvitedCode")
	@ResponseBody
	public Map<String,String> updateInvitedCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		String invitedCode = request.getParameter("invitedCode");
		String sysOrgInfoId = request.getParameter("sysOrgInfoId");
		
		long id=Long.parseLong(invitedCode);
		TenantInfo tenant = tenantInfoService.getById(id);
		
		if(tenant != null){
			Date date = new Date();
			System.out.println(date);
			tenantInfoService.updateInvitedCode(invitedCode,Long.parseLong(sysOrgInfoId));	
			//邀请码补录
			dataMap.put("flag", "2");
			return dataMap;
		}
		//邀请码对应的企业不存在
		dataMap.put("flag", "1");
		
		return dataMap;
	}
	@RequestMapping("invitedCodeMakeup")
	@ResponseBody
	public ResultMessage invitedCodeMakeup(TenantInfo info,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage res=null;
		
		// TenantInfo tenant = tenantInfoService.getById(info.getSysOrgInfoId());
		try {
			Date date = new Date();
			System.out.println(date);
			tenantInfoService.updateInvitedCode(info.getSysOrgInfoId().toString(),Long.parseLong(info.getInvititedCode()));
			res=new ResultMessage(ResultMessage.Success, "邀请码补录成功");
		} catch (Exception e) {
			res=new ResultMessage(ResultMessage.Success, "邀请码补录失败!");
			e.printStackTrace();
		}	
	
		return res;
	}
	
	
	/**
	 * 编辑企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("orgInfo")
	@Action(description = "新注册个人用户编辑企业")
	public ModelAndView orgInfo(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantOrgInfo.jsp");
		ISysUser user = ContextUtil.getCurrentUser();
		Long sysOrgInfoId;
		if (user!=null) {
			sysOrgInfoId = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}else{
			sysOrgInfoId=(Long)iCache.getByKey("orgInfoId");
		}
		if(sysOrgInfoId==this.ywOrgId){
			mv = new ModelAndView("/tenant/hasNoTenant.jsp");
			return mv;
		}
		return mv;
	}
	
	
	/**
	 * 编辑企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("security")
	@Action(description = "新注册个人用户编辑企业")
	public ModelAndView security(HttpServletRequest request) throws Exception {
		Long sysOrgInfoId = 2350300l;
		if(ContextUtil.getCurrentOrgInfoFromSession()!=null){
			sysOrgInfoId  = ContextUtil.getCurrentOrgInfoFromSession()
					.getSysOrgInfoId();
		}
		String returnUrl = RequestUtil.getPrePage(request);
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		if(info!=null){
			info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
		}
		ModelAndView mv = new ModelAndView("/tenant/tenantSecurity.jsp");
		mv.addObject("info", info)
		.addObject("returnUrl", returnUrl);
		return mv;
	}
	
	
	/**
	 * ajax获取级联数组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCascadeJsonData")
	@Action(description = "查询物品分类")
	@ResponseBody
	public List<Dictionary> getCascadeJsonData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String optvalue = request.getParameter("value");

		List<Map<String, Object>> list = jdbcTemplate
				.queryForList(
						"select c.* from sys_dic c inner join sys_dic p where c.parentId=p.dicId and p.itemValue=?",
						optvalue);
		List<Dictionary> dics = new ArrayList();

		for (Map<String, Object> m : list) {
			Dictionary dic = new Dictionary();
			dic.setDicId(Long.parseLong(m.get("dicId").toString()));
			dic.setItemKey(m.get("itemKey").toString());
			dic.setItemName(m.get("itemName").toString());
			dic.setItemValue(m.get("itemValue").toString());
			dics.add(dic);
		}

		return dics;
	}
	/**
	 * 更新企业
	 * @param request
	 * @param response
	 * @param company
	 * @param bindResult
	 * @param viewName
	 * @throws Exception
	 */
	@RequestMapping("saveTenantBase")
	@Action(description = "更新企业")
	public void saveTenantBase(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String resultMsg = "操作成功";
		TenantInfo info = getFormObject(request);
		info.setFlaglogo(sysUserService.getFlagLogoByCountry(info.getCountry()));
		info.setInfo(RequestUtil.getString(request, "info"));
		//说明是个人用户
		if(info.getSysOrgInfoId()==null||"".equals(info.getSysOrgInfoId())||"0".equals(info.getSysOrgInfoId())){
			ISysUser user = ContextUtil.getCurrentUser();
			info.setRegistertime(new Date());
			
			Long[] aryRoleId =new Long[]{10000045050018L,10000045050019L,10000045050020L,10000045050021L,10000045050022L};// 角色Id数组
			
			TenantInfo tenantinfo = tenantInfoService.registerSysOrg(info, "123456", aryRoleId, user);
			//添加资源资质信息
			List<Aptitude> aptitudes = tenantinfo.getAptitudeList();
			//删除老数据
			String sql = "delete from sys_org_info_aptitude where info_id = "+tenantinfo.getSysOrgInfoId() ;
			jdbcTemplate.execute(sql);
			for(Aptitude aptitude :aptitudes){
				aptitude.setId(UniqueIdUtil.genId());
				aptitude.setInfoId(tenantinfo.getSysOrgInfoId());
				aptitudeService.add(aptitude);
				ucSysAuditService.addLog("添加企业资质信息", "TenantInfoController.saveSucess.ht", JSON.toJSONString(aptitude), "成功");
			}
			SmsUtil.sendZhuceXXSms(user.getMobile(), String.valueOf(tenantinfo.getSysOrgInfoId()), "123456", "system");
			ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(tenantinfo), "成功");
			List<SubSystem> all = subSystemService.getAll();

			
		

			JSONObject json = new JSONObject();
			final JSONObject tenantJson = json.fromObject(tenantinfo);
			new JMSRunableThread("tenant_add",tenantJson.toString());
			/*for (SubSystem subSystem : all) {
				jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						MapMessage ms1=session.createMapMessage();
						ms1.setString("tenant_add",tenantJson.toString());
						return ms1;
					}
				});
			SysMsgLog msgLog = new SysMsgLog();
				msgLog.setId(UniqueIdUtil.genId());
				msgLog.setOperation("企业添加");
				msgLog.setReceiverersonal(subSystem.getSystemId()+"");
				msgLog.setSendcontent(tenantJson.toString());
				sysMsgLogService.add(msgLog);
			}*/

		
		int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i,"tenant");
			for(InterfaceUrlBean urlBean:urls){
				Map<String, Object> params = new HashMap<String, Object>();
				// 子系统标识
				long systemId = urlBean.getSubId();
				// 企业标识（统一开发标识）(暂时没有)
				String topenId = info.getOpenId();
				// 企业账号
				long tsn = info.getSysOrgInfoId();
				// 企业名称
				String tname = info.getName();
				// 会员标识（统一开发标识）
				String uopenId = user.getOpenId();
				// 会员名称
				String uname = user.getAccount();
				// 登录密码
				String pwd = user.getPassword();
				// 手机号
				String mobile = user.getMobile();
				// 邮箱
				String email = user.getEmail();
				// 企业状态
				// 默认为未完善"0"
				Short state = 0;
				params.put("topenId", topenId);
				params.put("tsn", tsn);
				params.put("tname", tname);
				params.put("uopenId", uopenId);
				params.put("uname", uname);
				params.put("pwd", pwd);
				params.put("mobile", mobile);
				params.put("email", email);
				params.put("state", state);
				params.put("systemId", systemId);
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
				urlMonitor.setRemark1(user.getUserId()+"");
				urlMonitor.setRemark2(user.getOrgId()+"");
				int isSuccess =1;
			    try{
			    	result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
			    }
			    catch (Exception e){
			    	isSuccess = 0;
			    	result=e.getMessage();
			    	e.printStackTrace();
			    }
			    finally {
			    	System.out.println("调用子系统接口结果="+result+"---------"+result.length());
			    	if(result.length()>4000){
			    		String subResult = result.substring(0,4000);
			    		urlMonitor.setRemark3(subResult);
			    	}
			    	urlMonitor.setRemark3(result);
			    	Date end=new Date();  
					String endTime = formatter.format(end);
					urlMonitor.setEndTime(endTime);
					urlMonitor.setIsSuccess(isSuccess);
					urlMonitorService.add(urlMonitor);
			    }
			   
			}
			 writeResultMessage(response.getWriter(), resultMsg,
						ResultMessage.Success); 
		}
		
		else{
			try {
				info.setRegistertime(new Date());
				
				//添加资源资质信息
				List<Aptitude> aptitudes = info.getAptitudeList();
				//删除老数据
				String sql = "delete from sys_org_info_aptitude where info_id = "+info.getSysOrgInfoId() ;
				jdbcTemplate.execute(sql);
				ucSysAuditService.addLog("删除企业资质信息", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info.getSysOrgInfoId()), "成功");
				for(Aptitude aptitude :aptitudes){
					aptitude.setId(UniqueIdUtil.genId());
					aptitude.setInfoId(info.getSysOrgInfoId());
					aptitudeService.add(aptitude);
					ucSysAuditService.addLog("添加企业资质信息", "TenantInfoController.saveSucess.ht", JSON.toJSONString(aptitude), "成功");
				}
				
				if(info.getSysOrgInfoId()!=null&&!"".equals(info.getSysOrgInfoId())&&info.getSysOrgInfoId()!=0l){
					//修改企业信息
					tenantInfoService.updateAllInfo(info);
					MessageUtil.sendSysTopic("updateTenant", info);
					JSONObject json = new JSONObject();
					final JSONObject tenantJson = json.fromObject(info);
					new JMSRunableThread("tenant_update",tenantJson.toString());
				}
				/*else{
//					map = tenantInfoService.registerSysOrgNoUser(info, "123456");
//				    TenantInfo tnantinfo = (TenantInfo)map.get("sysOrgInfo");
				    for (SubSystem subSystem : all) {
						jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
							public Message createMessage(Session session) throws JMSException {
								MapMessage ms1=session.createMapMessage();
								ms1.setString("tenant_update",tenantJson.toString());
								return ms1;
							}
						});

					

						SysMsgLog msgLog = new SysMsgLog();
						msgLog.setId(UniqueIdUtil.genId());
						msgLog.setOperation("企业修改");
						msgLog.setReceiverersonal(subSystem.getSystemId()+"");
						msgLog.setSendcontent(tenantJson.toString());
						sysMsgLogService.add(msgLog);
					}
			
				}*/
				try{
					//遍历所有接口地址，将数据同步到各子系统
					String result = "";
					int i = 2;
					List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i,"tenant");
					for(InterfaceUrlBean urlBean:urls){
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Object> data = new HashMap<String, Object>();
						// 子系统标识
						Date start=new Date();   
					    java.text.SimpleDateFormat  formatter= new  java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
					    String startTime = formatter.format(start);
					    UrlBean urlMonitor = new UrlBean();
					    long id = UniqueIdUtil.genId();
						urlMonitor.setId(id);
						urlMonitor.setUrl(urlBean.getSubIndexUrl()+urlBean.getUrl());
						urlMonitor.setStartTime(startTime);
						urlMonitor.setSubSysId(urlBean.getSubId()+"");
						Long orgId = ContextUtil.getCurrentUser().getOrgId();
						Long userId = ContextUtil.getCurrentUser().getUserId();
						urlMonitor.setRemark1(orgId+"");
						urlMonitor.setRemark2(userId+"");
						long systemId = urlBean.getSubId();
						data.put("tenant", info);
						//data.put("aptitudes", aptitudes);
						params.put("systemId", systemId);
						params.put("data", data);
						int isSuccess =1;
						try {
							//result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
						} catch (Exception e) {
							// TODO: handle exception
							isSuccess = 0;
					    	result=e.getMessage();
					    	e.printStackTrace();
						}
						finally {
					    	System.out.println("调用子系统接口结果="+result+"---------"+result.length());
					    	if(result.length()>4000){
					    		String subResult = result.substring(0,4000);
					    		urlMonitor.setRemark3(subResult);
					    	}
					    	urlMonitor.setRemark3(result);
					    	Date end=new Date();  
							String endTime = formatter.format(end);
							urlMonitor.setEndTime(endTime);
							urlMonitor.setIsSuccess(isSuccess);
							urlMonitorService.add(urlMonitor);
					    }
					}
				}
				catch(Exception e){
					e.printStackTrace();
				} 
				writeResultMessage(response.getWriter(), resultMsg,
						ResultMessage.Success);
			} catch (Exception e) {
				writeResultMessage(response.getWriter(),
						resultMsg + "," + e.getMessage(), ResultMessage.Fail);
			}
		}
	
}

	
	
	
	/**
	 * 添加企业有当前操作人信息
	 * @param request
	 * @param response
	 * @param company
	 * @param bindResult
	 * @param viewName
	 * @throws Exception
	 */
	@RequestMapping("saveSucess1")
	@Action(description = "添加企业")
	public ModelAndView saveSucess1(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = "成功";
		TenantInfo info = getFormObject(request);
		ModelAndView mv = new ModelAndView("/tenant/regSuccess.jsp");
		ISysUser user = ContextUtil.getCurrentUser();
		//先用一个默认人进行默认登录
		//ISysUser user = sysUserService.getById(10000031112984l);
		try {
			info.setRegistertime(new Date());
			
			Long[] aryRoleId =new Long[]{10000045050018L,10000045050019L,10000045050020L,10000045050021L,10000045050022L};// 角色Id数组
			
			info = tenantInfoService.registerSysOrg(info, "123456", aryRoleId, user);
			SmsUtil.sendZhuceXXSms(user.getMobile(), String.valueOf(info.getSysOrgInfoId()), "123456", "system");
			ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
		} catch (Exception e) {
			return mv.addObject("info", info)
					.addObject("pwd", "123456")
					.addObject("mobile", user.getMobile())
					.addObject("resultMsg", resultMsg);
		}
		return mv.addObject("info", info)
				.addObject("pwd", "123456")
				.addObject("mobile", user.getMobile())
				.addObject("resultMsg", resultMsg);
	   }
	
	
	/**
	 * 取得 Info 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected TenantInfo getFormObject(HttpServletRequest request)
			throws Exception {

		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher((new String[] { "yyyy-MM-dd" })));

		String json = request.getParameter("json");
		JSONObject obj = JSONObject.fromObject(json);

		Map<String, Class> map = new HashMap<String, Class>();
		map.put("aptitudeList", Aptitude.class);

		TenantInfo info = (TenantInfo) JSONObject.toBean(obj, TenantInfo.class,
				map);

		return info;
	}
	
	/**
	 * ajax获取name
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("nameValid")
	@Action(description="查询注册企业名称是否重名")
	@ResponseBody
	public String nameValid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String istf = "";
		
		String name = request.getParameter("name");
		
		QueryFilter queryFilter = new QueryFilter(request,"InfoItem");
		
		
		queryFilter.addFilter("name", name);
		
		String sqlKey = "getAllbyname";
		
		List<TenantInfo> infos= null;
		
		infos = tenantInfoService.getAllInfos(sqlKey, queryFilter);
		
			if(infos == null || infos.size() == 0){
				istf = "true";
			}else {
				istf = "false";
			}
		
		
		
		
		return istf;

	}
	
	
	/**
	 * 编辑企业认证信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("certiInformation")
	@Action(description = "编辑企业认证信息")
	public ModelAndView certiInformation(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantInfoCertiInformation.jsp");
		//Long sysOrgInfoId = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();

		ISysUser user = ContextUtil.getCurrentUser();
		Long sysOrgInfoId;
		if (user!=null) {
			sysOrgInfoId = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}else{
			sysOrgInfoId=(Long)iCache.getByKey("orgInfoId");
		}
		
		if(sysOrgInfoId==this.ywOrgId){
			mv = new ModelAndView("/tenant/hasNoTenant.jsp");
			return mv;
		}
		//String returnUrl = RequestUtil.getPrePage(request);
		String returnUrl = request.getHeader("Referer");
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		//info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.addFilter("orgid", sysOrgInfoId);
		queryFilter.addFilter("flag", "1");
	
		List<BranchBean> branchs = branchBeanService.getAll(queryFilter);
		
		return mv.addObject("info", info)
				.addObject("branch", branchs.size()>0?branchs.get(0):null)
				.addObject("returnUrl", returnUrl);
	}
	
	/***
	 * 跳转到协议页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("paymentxieyi")
	public ModelAndView paymentxieyi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/xieyi.jsp");
		return mv;
	}
	
	
	/**
	 * 开户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("openAccount")
	@Action(description = "编辑企业认证信息")
	public ModelAndView openAccount(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantInfoOpenAccount.jsp");

		ISysUser user = ContextUtil.getCurrentUser();
		Long sysOrgInfoId;
		if (com.hotent.core.util.BeanUtils.isNotEmpty(user)) {
			sysOrgInfoId = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}else{
			sysOrgInfoId=(Long)iCache.getByKey("orgInfoId");
		}
		if(sysOrgInfoId==this.ywOrgId){
			mv = new ModelAndView("/tenant/hasNoTenant.jsp");
			return mv;
		}
		
		
		//String returnUrl = RequestUtil.getPrePage(request);
		String returnUrl = request.getHeader("Referer");
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.addFilter("orgid", sysOrgInfoId);
		List<BranchBean> branchs = branchBeanService.getAll(queryFilter);
		
		BranchBean branchBean = new BranchBean();
		branchBean.setOrgid(sysOrgInfoId);
		return mv
				.addObject("branch", branchs.size()>0?branchs.get(0):branchBean)
				.addObject("returnUrl", returnUrl);
	}
	
	@RequestMapping("openCloseAccount")
	public ModelAndView openCloseAccount(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/openCloseAccount.jsp");
		
		ISysUser user = ContextUtil.getCurrentUser();
		Long sysOrgInfoId;
		if (com.hotent.core.util.BeanUtils.isNotEmpty(user)) {
			sysOrgInfoId = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}else{
			sysOrgInfoId=(Long)iCache.getByKey("orgInfoId");
		}
		if(sysOrgInfoId==this.ywOrgId){
			mv = new ModelAndView("/tenant/hasNoTenant.jsp");
			return mv;
		}
		
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		
		
		//String returnUrl = RequestUtil.getPrePage(request);
		String returnUrl = request.getHeader("Referer");
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.addFilter("orgid", sysOrgInfoId);
		queryFilter.addFilter("flag", "1");
	
		List<BranchBean> branchs = branchBeanService.getAll(queryFilter);
		
		BranchBean branchBean = new BranchBean();
		branchBean.setOrgid(sysOrgInfoId);
		return mv
				.addObject("branch", branchs.size()>0?branchs.get(0):branchBean)
				.addObject("info", info)
				.addObject("returnUrl", returnUrl);
	}
	
	
	
	/**
	 * 更新企业认证信息
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveCerti")
	@Action(description = "更新企业认证信息")
	public void saveCerti(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = null;
		String isChange = "";
		TenantInfo info = getFormObject(request);
		TenantInfo info1 = tenantInfoService.getById(info.getSysOrgInfoId());
		info = synchronize(info,info1);
		try {
			info.setCreatetime(new Date());
			//判断认证信息是否发生变化
			boolean flag = isAuthChange(info.getSysOrgInfoId(),info);
			if(!flag){
				isChange = "变化";
			}
			//查看中间表是否为空
			info.setYlStatus(1);
			tenantInfoService.update(info);
			int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i,"oauth");
			for(InterfaceUrlBean urlBean:urls){
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> data = new HashMap<String, Object>();
				// 子系统标识
				long systemId = urlBean.getSubId();
				data.put("tenant", info);
				params.put("systemId", systemId);
				params.put("data", data);
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
			    	result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
			    }
			    catch (Exception e){
			    	isSuccess = 0;
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
			ucSysAuditService.addLog("更新企业", "TenantInfoController.saveCerti.ht", JSON.toJSONString(info), "成功");
			//企业商户信息
			/*BranchBean branchBean = getBranchBeanFormObject(request);
			if(branchBean.getId() == null){
				branchBean.setId(UniqueIdUtil.genId());
			}
			branchBean.setBranchname(info.getName());
			branchBean.setChannelId("umpay");
			branchBean.setState("3");
			branchBean.setAccountType("0");
			branchBean.setOrgid(info.getSysOrgInfoId());
			branchService.updateBranch(branchBean);
			cloudSysAuditService.addLog("更新企业商户信息", "TenantInfoController.saveCerti.ht", JSON.toJSONString(branchBean), "成功");*/
			resultMsg = getText("更新成功", "企业信息");
			writeResultMessage(response.getWriter(), resultMsg+isChange,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	/**
	 * 认证企业信息
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("certified")
	@Action(description = "认证企业认证信息")
	public void certified(HttpServletRequest request, HttpServletResponse response,BranchDto dto ) throws Exception
	 {
		ModelAndView mv = new ModelAndView("/tenant/certifiedFeedback.jsp");
		
		TenantInfo info = getFormObject(request);
		//数据库中企业的真实信息
		TenantInfo info1 = tenantInfoService.getById(info.getSysOrgInfoId());
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		Map<String, String> map=new HashMap<String, String>();
		ResponseData responseData = new ResponseData();
		JSONObject json=new JSONObject();
		boolean flag=false;
		Integer status=0;//状态码
		String resultMsg="";
		
		try {
			if(info1.compare(info)){
				//表示没有修改爱心诺相关信息，只需要保存到数据库中
				info.setRegAdd(info.getProvince()+info.getCity()+info.getCounty());
				info.setAccountTime(DateUtils.getCurrentDate());
				tenantInfoService.update(info);
				ucSysAuditService.addLog("更新企业认证信息", "AuditTenantInfoController.certifiedFeedback.ht", JSON.toJSONString(info), "成功");
				flag=true;
				json.put("flag", "update");
				json.put("Msg", "更新企业认证信息");
			}else{	
				boolean flg=false;
				if("1".equals(info1.getAccountStatsus())){
					flg=true;
				}
				info1.setName(info.getName());
				info1.setGszch(info.getGszch());
				info1.setCode(info.getCode());
				info1.setNsrsbh(info.getNsrsbh());
				info1.setIncorporator(info.getIncorporator());
				info1.setFrsfzhm(info.getFrsfzhm());
				info1.setFrsjh(info.getFrsjh());
				info1.setHomephone(info.getHomephone());
				info1.setYyzzPic(info.getYyzzPic());
				info1.setFrPic(info.getFrPic());
				responseData = tenantInfoService.isRealEnterprise(info,basePath+"tenant/asynServlet.ht");
				BeanUtils.copyProperties(info, info1);
				info1.setAxnStatus(3);
				if(flg){
					List<BranchBean> branchs = branchBeanService.getByOrgid(info1.getSysOrgInfoId());
					
					if(!branchs.isEmpty()&&branchs.size()>0){
						String accstate = branchs.get(0).getAccstate();
						//开户成功冻结
						if("1".equals(accstate) || "3".equals(accstate)){
							info1.setState(6);
							Long id = branchs.get(0).getId();
							//5代表需要重新开户							
							branchBeanService.updateOpenCloseAccstate(id, "5");							
						}					
					}				
				}
				info1.setRegAdd(info.getProvince()+info.getCity()+info.getCounty());				
				if (responseData!=null) {
					if (responseData.getStatus().equals("0")) {
						//返回成功
						map = responseData.getInfo();
						if (map.get("code").equals("0")) {
							boolean openFlag=false;
							//完全匹配
							info1.setAxnStatus(2);
						}
					} else if (responseData.getStatus().equals("1")) {
						info1.setAxnStatus(1);
					} else if (responseData.getStatus().equals("2")) {
						info1.setAxnStatus(1);
					} else if (responseData.getStatus().equals("3")) {
						info1.setAxnStatus(1);
					}
					json.put("flag", "audit");
					json.put("info", info1);
					json.put("map", map);
					json.put("status",responseData.getStatus());
				}else{
					json.put("flag", "updateAfter");
					json.put("Msg", "企业信息更新成功");
				}
				info1.setAccountTime(DateUtils.getCurrentDate());
				tenantInfoService.update(info1);
				ucSysAuditService.addLog("更新企业认证信息", "AuditTenantInfoController.certifiedFeedback.ht", JSON.toJSONString(info1), "成功");	
			} 
			try{
				//遍历所有接口地址，将数据同步到各子系统
				String result = "";
				int i = 2;
				List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i,"oauth");
				for(InterfaceUrlBean urlBean:urls){
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, Object> data = new HashMap<String, Object>();
					// 子系统标识
					long systemId = urlBean.getSubId();
					data.put("tenant", info1);
					params.put("systemId", systemId);
					params.put("data", data);
					result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
		} catch (Exception e) {
			json.put("Msg", e.getMessage());
			status=1;
			writeResultMessage(response.getWriter(), json.toString(), ResultMessage.Fail);
			e.printStackTrace();
		}
	
		writeResultMessage(response.getWriter(), json.toString(), ResultMessage.Success);
	}
	@RequestMapping("saveInfo")
	@Action(description = "认证企业认证信息")
	public void saveInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		TenantInfo info = getFormObject(request);

		JSONObject json=new JSONObject();
		try{
		info.setRegAdd(info.getProvince()+info.getCity()+info.getCounty());
		tenantInfoService.update(info);
		ucSysAuditService.addLog("更新企业认证信息", "AuditTenantInfoController.certifiedFeedback.ht", JSON.toJSONString(info), "成功");
		json.put("flag", "update");
		json.put("Msg", "企业信息保存成功");
		
		} catch (Exception e) {
			json.put("Msg", e.getMessage());
			writeResultMessage(response.getWriter(), json.toString(), ResultMessage.Fail);
			e.printStackTrace();
		}
		
		writeResultMessage(response.getWriter(), json.toString(), ResultMessage.Success);
	}
	
	@RequestMapping("saveDraft")
	@Action(description = "认证企业认证信息")
	public void saveDraft(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TenantInfo info = getFormObject(request);
		
		JSONObject json=new JSONObject();
		try{
			info.setRegAdd(info.getProvince()+info.getCity()+info.getCounty());
			tenantInfoService.updateDraft(info);
			ucSysAuditService.addLog("更新企业认证信息", "AuditTenantInfoController.certifiedFeedback.ht", JSON.toJSONString(info), "成功");
			json.put("flag", "update");
			json.put("Msg", "企业信息保存成功");
			
		} catch (Exception e) {
			json.put("Msg", e.getMessage());
			writeResultMessage(response.getWriter(), json.toString(), ResultMessage.Fail);
			e.printStackTrace();
		}
		
		writeResultMessage(response.getWriter(), json.toString(), ResultMessage.Success);
	}
	
	/**
	 * 异步设置验证企业查询结果
	 * @return
	 */
	@RequestMapping("asynServlet")
	@ResponseBody
	public int  asynServlet(HttpServletRequest request, HttpServletResponse response){
		String requestSerialNumber = RequestUtil.getString(request, "requestSerialNumber");
		String responseSerialNumber = RequestUtil.getString(request, "responseSerialNumber");
		String isReal = RequestUtil.getString(request, "isReal");
		
		String sql = "update sys_org_info set ylStatus = 2 where SYS_ORG_INFO_ID = "+requestSerialNumber;
		//是否为真，0为真
		if(isReal.equals("0")){
			jdbcTemplate.execute(sql);
		}else{
			sql = "update sys_org_info set ylStatus = 3 where SYS_ORG_INFO_ID = "+requestSerialNumber;
			jdbcTemplate.execute(sql);
		}
		return 0;
	}
	
	/**
	 * 更新企业开户信息
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveOpenAccount")
	@Action(description = "更新企业开户信息")
	public void saveOpenAccount(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*String resultMsg = null;
		String isChange = "";
		try {
			//企业商户信息
			BranchBean branchBean = getBranchBeanFormObject(request);
			if(branchBean.getId() == null){
				branchBean.setId(UniqueIdUtil.genId());
			}
			branchBean.setChannelId("umpay");
			branchBean.setState("3");
			branchBean.setAccountType("0");
			branchBeanService.updateBranch(branchBean);
			ucSysAuditService.addLog("更新企业商户信息", "TenantInfoController.saveCerti.ht", JSON.toJSONString(branchBean), "成功");
			resultMsg = getText("record.updated", "企业信息");
			writeResultMessage(response.getWriter(), resultMsg+isChange,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}*/
		
		String resultMsg = null;
		String isChange = "";
		try {
			//企业商户信息
			BranchBean branchBean = getBranchBeanFormObject(request);
			if(branchBean.getId() == null){
				branchBean.setId(UniqueIdUtil.genId());
			}
			branchBean.setChannelId("umpay");
			branchBean.setState("3");
			branchBean.setAccountType("0");
			branchBeanService.updateBranch(branchBean);
			
			int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i,"bank");
			for(InterfaceUrlBean urlBean:urls){
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> data = new HashMap<String, Object>();
				// 子系统标识
				long systemId = urlBean.getSubId();
				data.put("branch", branchBean);
				params.put("systemId", systemId);
				params.put("data", data);
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
			    	result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
			    }
			    catch (Exception e){
			    	isSuccess = 0;
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
			
			ucSysAuditService.addLog("更新企业商户信息", "TenantInfoController.saveOpenAccount.ht", JSON.toJSONString(branchBean), "成功");
			resultMsg = getText("更新成功", "企业商户信息");
			writeResultMessage(response.getWriter(), resultMsg+isChange,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	
	@RequestMapping("saveOpenCloseAccount")
	public void saveOpenCloseAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		try {
			//企业商户信息
			BranchBean branchBean = getBranchBeanFormObject(request);
			if(branchBean.getId() == null){
				branchBean.setId(UniqueIdUtil.genId());
			}
			branchBean.setChannelId("umpay");
			branchBean.setState("3");
			//branchBean.setAccstate("0");
			branchBean.setAccountType("0");
			branchBeanService.updateOpenCloseAccount(branchBean);
			/*String orgCode = request.getParameter("orgCode");
			String businessLicense = request.getParameter("businessLicense");
			String taxId = request.getParameter("taxId");
			String incorporator=request.getParameter("incorporator");
			TenantInfo info = new TenantInfo();
			info.setSysOrgInfoId(branchBean.getOrgid());
			info.setCode(orgCode);
			info.setYyzz(businessLicense);
			info.setNsrsbh(taxId);
			info.setIncorporator(incorporator);*/
			/*if(branchBean.getCredentialsType().equals("A")){
				info.setFrsfzhm(branchBean.getCredentialsNumber());
			}
			tenantInfoService.updateOpenCloseAccount(info);*/
			TenantInfo info = tenantInfoService.getById(branchBean.getOrgid());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sysid", "uc");
			params.put("submerid", branchBean.getOrgid()+"");
			params.put("cltacc_cltnm", branchBean.getBranchaccountname());
			params.put("clt_nm", info.getIncorporator());
			params.put("clt_kd", branchBean.getClientProperty());
			params.put("clt_cdtp", branchBean.getCredentialsType());
			params.put("clt_cdno", branchBean.getCredentialsNumber());
			params.put("clt_orgcd", info.getCode());
			params.put("clt_bslic", info.getYyzz());
			params.put("clt_swdjh", info.getNsrsbh());
			params.put("fcflg", branchBean.getBusinessFlag());
			params.put("acctp", branchBean.getAccountType1());
			
			try {
				System.out.println("接口调用start++"+openAccount_url+"请求参数:"+params);
				resultMsg=HttpClientUtil.JsonPostInvoke(openAccount_url,params);
				System.out.println("接口调用end++"+resultMsg);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("接口调用end++Exception"+e.getLocalizedMessage());
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.Fail);
			}
			com.alibaba.fastjson.JSONObject json=JSON.parseObject(resultMsg);
			String msg = json.getString("RETCODE");
			if(!StringUtil.isEmpty(branchBean.getAccstate())&&branchBean.getAccstate().equals("1")){
				if(msg.equals("000000")){
					branchBean.setAccstate("3");
					branchBeanService.updateOpenCloseAccstate(branchBean.getId(),branchBean.getAccstate());
					 writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
				}else{
					branchBean.setAccstate("4");
					/*if(branchBean.getBusinessFlag()=="2"){
						branchBeanService.updateOpenCloseAccount(branchBean);
					}*/
					branchBeanService.updateOpenCloseAccstate(branchBean.getId(),branchBean.getAccstate());
					writeResultMessage(response.getWriter(), json.getString("RETMSG"), ResultMessage.Fail);
				
				}
			}else{
			if(msg.equals("000000")){
				branchBean.setAccstate("1");
				branchBeanService.updateOpenCloseAccstate(branchBean.getId(),branchBean.getAccstate());
				 writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}else{
				branchBean.setAccstate("2");
				/*if(branchBean.getBusinessFlag()=="2"){
					branchBeanService.updateOpenCloseAccount(branchBean);
				}*/
				branchBeanService.updateOpenCloseAccstate(branchBean.getId(),branchBean.getAccstate());
				writeResultMessage(response.getWriter(), json.getString("RETMSG"), ResultMessage.Fail);
			
			}
		}
			
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg , ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 取得 branchBean 实体
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected BranchBean getBranchBeanFormObject(HttpServletRequest request)throws Exception {
		String branchname = request.getParameter("branchname");
		String bankaccount = request.getParameter("bankaccount");
		String branchaccountname = request.getParameter("branchaccountname");
		String city = request.getParameter("city");
		String bankfullname = request.getParameter("bankfullname");
		String branchAbbr = request.getParameter("branchAbbr");
		String province = request.getParameter("province");
		String str = request.getParameter("id");
		String str1 = request.getParameter("orgid");
		String accstate = request.getParameter("accstate");
		
		long id =UniqueIdUtil.genId();
		long ordid =0l;
		if(str!=null&&!str.equals("")){
			id = Long.parseLong(str);
		}
		if(str1!=null&&!str1.equals("")){
			ordid = Long.parseLong(str1);
		}
		
		String clientProperty = request.getParameter("clientProperty");
		String credentialsType = request.getParameter("credentialsType");
		String credentialsNumber = request.getParameter("credentialsNumber");
		String businessFlag = request.getParameter("businessFlag");
		String accountType1 = request.getParameter("accountType1");
		String flag = request.getParameter("flag");
		
		
		BranchBean branchBean = new BranchBean();
		branchBean.setId(id);
		branchBean.setOrgid(ordid);
		branchBean.setBankfullname(bankfullname);
		branchBean.setCity(city);
		branchBean.setBranchAbbr(branchAbbr);
		branchBean.setBankaccount(bankaccount);
		branchBean.setBranchname(branchname);
		branchBean.setProvince(province);
		branchBean.setCreatetime(new Date());
		branchBean.setBranchaccountname(branchaccountname);
		branchBean.setAccstate(accstate);
		
		branchBean.setClientProperty(clientProperty);
		branchBean.setCredentialsType(credentialsType);
		branchBean.setCredentialsNumber(credentialsNumber);
		branchBean.setBusinessFlag(businessFlag);
		branchBean.setAccountType1(accountType1);
		branchBean.setFlag(flag);
		
		return branchBean;
	}
	
	
	
	/**
	 * 获取组织架构树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public List<ISysOrg> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取维度ID
		Long demId = RequestUtil.getLong(request, "demId", 0);
		// 当前维度
		List<Demension> demens = null;
		long orgId=RequestUtil.getLong(request, "orgId",0);
		
		/////ht b
		//当前企业
		ISysUser curUser = ContextUtil.getCurrentUser();
		
		ISysOrg curComp;
		if (curUser!=null) {
			curComp = sysOrgService.getById(curUser.getOrgId());
		}else{
			curComp=sysOrgService.getById((Long)iCache.getByKey("orgInfoId"));
		}
		
		boolean compFlag=false;
		if(orgId==0){
			compFlag=true;
			orgId=curComp.getOrgId();
		}
		/////ht e
		
		
		List<ISysOrg> orgList = new ArrayList<ISysOrg>();
		if(orgId==0){		
			if (demId != 0) {
				demens = new ArrayList<Demension>();
				demens.add(demensionService.getById(demId));
				orgList = sysOrgService.getOrgByOrgSupIdAndLevel(demId);
			} else {
				demens = demensionService.getAll();
				if(demens.size()>0){
					for(int i=0;i<demens.size();i++){
						orgList.addAll(sysOrgService.getOrgByOrgSupIdAndLevel(demens.get(i).getDemId()));
					}
				}
				
			}
			for (Demension demension : demens) {
				orgList.add(getRootSysOrg(demension.getDemId(), demension.getDemName()));
			}
		}else{
			orgList = sysOrgService.getOrgByOrgSupId(orgId);
			if(compFlag){
				orgList.add(0, curComp);
			}
		}
		for(ISysOrg sysOrg:orgList){
			//changed by yanglu ,fix the root org of org tree can be delete , fix method is set the root node isRoot==1 
			if(sysOrg.getDemName()!=null && sysOrg.getDemName().length()>0){
				sysOrg.setIsRoot(Short.valueOf("1"));
				//对组织信息公司名称做截串处理  超过10个字用*代替
				String orgName = sysOrg.getOrgName();
				String replaceName =null;
				if(orgName.length()>10){
					replaceName = orgName.replace(orgName, orgName.substring(0,10)+"*");
				}else{
					replaceName=orgName;
				}
				sysOrg.setOrgName(replaceName);
			}
			//
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(sysOrg.getOrgId());
			sysOrg.setOwnUser(charge.getOwnUser());
			sysOrg.setOwnUserName(charge.getOwnUserName());
			if(sysOrg.getOrgType()==null) continue;
			SysOrgType  tempSysOrgType= sysOrgTypeService.getById(sysOrg.getOrgType());
			if(tempSysOrgType==null ) continue;
			if(tempSysOrgType.getIcon()==null) continue;
			sysOrg.setIconPath(tempSysOrgType.getIcon());
		}
		return orgList;
	}
	
	/**
	 * 取得组织架构明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantOrgGet.jsp");
		mv.addObject("action", "global");
		return getByOrgId(request,mv);

	}
	
	
	@RequestMapping("getGrade")
	public ModelAndView getGrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=new ModelAndView();
		mv.addObject("action", "grade");
		mv.setViewName("/tenant/tenantOrgGetGrade.jsp");
		return getByOrgId(request,mv);
	}
	
	@RequestMapping("edit")
	@Action(description = "编辑组织架构")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantOrgEdit.jsp");
		mv.addObject("scope", "global");
		return getEditMv(request,mv);
	}
	
	
	
	/**
	 * 删除组织及其所有子组织
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("orgdel")
	public void orgdel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long orgId = RequestUtil.getLong(request, "orgId");
		Long userId=ContextUtil.getCurrentUserId();
			sysOrgService.delById(orgId,userId);
			//删除单位的缓存数据。
			iCache.delByKey(SecurityUtil.OrgRole + orgId);
			message = new ResultMessage(ResultMessage.Success, "删除组织及其所有子组织成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除组织及其所有子组织失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	
	/**
	 * 获取编辑界面的modelandview
	 * @param request
	 * @param mv
	 * @return
	 */
	private ModelAndView getEditMv(HttpServletRequest request,ModelAndView mv){
		Long demId = RequestUtil.getLong(request, "demId", 0);
		Long orgId = RequestUtil.getLong(request, "orgId");
		String action = RequestUtil.getString(request, "action");
		ISysOrg sysOrg = null;
		Long parentOrgId=0L;
		// 当前维度
		Demension demension = demensionService.getById(demId);
		List<SysOrgType> sysOrgTypelist=sysOrgTypeService.getByDemId(demId);
		List<SysOrgType> returnSysOrgTypelist=new LinkedList<SysOrgType>();
		SysOrgType subSysOrgType=null;
		if ("add".equals(action)) {// 新增
			sysOrg = iAuthenticate.getNewSysOrg();
			ISysOrg supSysOrg = sysOrgService.getById(orgId);
			
			if (supSysOrg == null) { // 从维度上新建组织
				sysOrg.setOrgSupId(demId);
				returnSysOrgTypelist=sysOrgTypelist;
			} else {
				supSysOrg = sysOrgService.getById(orgId);
				sysOrg.setOrgSupId(supSysOrg.getOrgId());
				sysOrg.setOrgSupName(supSysOrg.getOrgName());
				Long supSysOrgId=supSysOrg.getOrgType();
				if(supSysOrgId!=null){
					 subSysOrgType=sysOrgTypeService.getById(supSysOrg.getOrgType());
				}		
			}
		} else {// 编辑
			sysOrg = sysOrgService.getById(orgId);
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(orgId);
			sysOrg.setOwnUser(charge.getOwnUser());
			sysOrg.setOwnUserName(charge.getOwnUserName());
			 parentOrgId=sysOrg.getOrgSupId()==null?0L:sysOrg.getOrgSupId();
			
			if(sysOrg.getOrgType()!=null)
				subSysOrgType=sysOrgTypeService.getById(sysOrg.getOrgType());
		
			 if(subSysOrgType==null){
				 
				 ISysOrg parentOrg= sysOrgService.getParentWithType(sysOrg);  //取得有分类的父极节点
				 if(parentOrg!=null)
					 subSysOrgType=sysOrgTypeService.getById(parentOrg.getOrgType());
				 else
					 returnSysOrgTypelist=sysOrgTypelist;
			 }
		}
		if(subSysOrgType!=null && !parentOrgId.equals(1L)){
			for(int i=0;i<sysOrgTypelist.size();i++){
				if(subSysOrgType.getLevels()<=sysOrgTypelist.get(i).getLevels())
					returnSysOrgTypelist.add(sysOrgTypelist.get(i));
			}
		}
		else if(parentOrgId.equals(1L)){
			returnSysOrgTypelist=sysOrgTypelist;
		}
			
		return mv.addObject("sysOrg", sysOrg)
				.addObject("demension", demension)
				.addObject("action", action)
				.addObject("sysOrgTypelist",returnSysOrgTypelist);
	}
	
	private ModelAndView getByOrgId(HttpServletRequest request,ModelAndView mv) throws Exception{
		long orgId = RequestUtil.getLong(request, "orgId");
		List<SysOrgType> sysOrgTypelist=null;
		String ownerName = "";
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		if (sysOrg != null) {
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(orgId);
			Long demId = sysOrg.getDemId();
			sysOrgTypelist=sysOrgTypeService.getByDemId(demId);
			if (sysOrg.getDemId() != 0) {
				sysOrg.setDemName(demensionService.getById(demId).getDemName());
				ownerName = charge.getOwnUserName();
			
			}
		}

		return mv.addObject("sysOrg", sysOrg)
				.addObject("userNameCharge", ownerName)
				.addObject("orgId", orgId)
				.addObject("sysOrgTypelist",sysOrgTypelist);
	}
	
	
	/**
	 * 将维度或全部、未分配组织等添加为组织树的一个节点
	 * 
	 * @param demId
	 * @return
	 * @throws Exception
	 */
	private ISysOrg getRootSysOrg(Long demId, String orgName) throws Exception {
		
		ISysOrg org = iAuthenticate.getNewSysOrg();
		org.setOrgId(demId);
		org.setOrgSupId(0L);
		org.setPath(demId.toString());
		org.setDemId(demId);
		org.setIsRoot((short) 1);
		org.setOrgName(orgName);
		return org;
	}
	
	

	/**
	 * 将修改信息放入
	 * @param info
	 * @param info1
	 * @return
	 */
	private TenantInfo synchronize(TenantInfo info, TenantInfo info1) {
		info1.setGszch(info.getGszch());
		info1.setFrsfzhm(info.getFrsfzhm());
		info1.setCode(info.getCode());
		info1.setNsrsbh(info.getNsrsbh());
		info1.setIncorporator(info.getIncorporator());
		info1.setFrsjh(info.getFrsjh());
		info1.setFrPic(info.getFrPic());
		info1.setYyzzPic(info.getYyzzPic());
		return info1;
	}
	
	/**
	 * 查看认证信息字段们是否被修改，是则返回false，不是则返回true
	 * @param sysOrgId
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	private Boolean isAuthChange(Long sysOrgId,TenantInfo info) throws Exception{
		//获取当前企业信息
		TenantInfo infoCopy = tenantInfoService.getById(sysOrgId);
		TenantInfo a1 = new TenantInfo();
		TenantInfo a2 = new TenantInfo();
		
		//将需要对比的信息分别放入两个对象中
		a1.setGszch(infoCopy.getGszch());
		a1.setNsrsbh(infoCopy.getNsrsbh());
		a1.setCode(infoCopy.getCode());
		a1.setIncorporator(infoCopy.getIncorporator());
		a1.setFrsjh(infoCopy.getFrsjh());
		a1.setYyzzPic(infoCopy.getYyzzPic());
		a1.setFrsfzhm(infoCopy.getFrsfzhm());
		a1.setFrPic(infoCopy.getFrPic());
		
		a2.setGszch(info.getGszch());
		a2.setNsrsbh(info.getNsrsbh());
		a2.setCode(info.getCode());
		a2.setIncorporator(info.getIncorporator());
		a2.setFrsjh(info.getFrsjh());
		a2.setYyzzPic(info.getYyzzPic());
		a2.setFrsfzhm(info.getFrsfzhm());
		a2.setFrPic(info.getFrPic());
		
		return Compare.compare(a1, a2);
	}
	static class Compare{
		 public static boolean compare(Object src,Object target) throws Exception{
			 boolean flag = true;
			  Class<?> srcClass = src.getClass();
			  Class<?> targetClass = target.getClass();
			  Field[] e1Field = srcClass.getDeclaredFields();
			  Field[] e2Field = targetClass.getDeclaredFields();
			  for(int i=0;i<e1Field.length;i++){
				 Field e1F = e1Field[i];
			     Object o1 = srcClass.getMethod(getMethodName(e1F.getName())).invoke(src);
			     for(int j=0;j<e2Field.length;j++){
			    	 Field e2F = e2Field[j];
			    	 if(e1F.getName().equals(e2F.getName())){
			    		 Class<?> type1 = e1F.getType();
			    		 Class<?> type2 = e2F.getType();
			    		 
					     Object o2 = targetClass.getMethod(getMethodName(e2F.getName())).invoke(target);
					     if(o1 == null && o2 != null ){
					    	 srcClass.getMethod(setMethodName(e1F.getName()),type1).invoke(src, o2);
					     }
					     if(o1 != null && o2 == null){
					    	 targetClass.getMethod(setMethodName(e2F.getName()),type2).invoke(target,o1);
					     }
					     if(o1 != null && o2 != null & !o1.equals(o2)){
					    	 flag = false;
					    	 System.out.println(o1 +" 不等于 "+ o2);
					     }
			    	 }
			     }
			}
			return flag;
		 }
		 private static String getMethodName(String fieldName){
		    return "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1,fieldName.length());
		 }
		 private static String setMethodName(String fieldName){
		   	return "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1,fieldName.length());
		 }
	}
	/**
	 * 编辑企业的收货地址
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addressEdit")
	@Action(description = "查看企业的收发货地址")
	public ModelAndView addressEdit(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/tenantAddressEditInfo.jsp");
		ISysUser user=ContextUtil.getCurrentUser();
		Long sysOrgInfoId =ContextUtil.getCurrentTenantId();
		SysOrgService sysOrgService = (SysOrgService)AppUtil.getBean("sysOrgService");
		System.out.println(iCache.getByKey("userId")+"---"+"--"+sysOrgService.getOrgIdsByUserId((Long)iCache.getByKey("userId")));
		//如果是个人用户
		String isReceviced="1";
		
		List<TenantAddress> orderAddressInfo = null;
		if (user!=null) {
			orderAddressInfo = tenantAddressService.getTenantAddressByType(
					user.getUserId(), isReceviced);
		}else{
			orderAddressInfo = tenantAddressService.getTenantAddressByType(
					(Long)iCache.getByKey("userId"), isReceviced);
		}
		mv.addObject("orderAddressInfo", orderAddressInfo);
		if(sysOrgInfoId==null){
			sysOrgInfoId=(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}
		if(sysOrgInfoId!=this.ywOrgId){
			String returnUrl = RequestUtil.getPrePage(request);
			TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
			if(info!=null){
				info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
			}
			mv.addObject("info", info)
			.addObject("returnUrl", returnUrl).addObject("orderAddressInfo", orderAddressInfo);
		}
		
		return mv;
	}
	
	
	@RequestMapping("/address")
	@Action(description = "新版收发货地址")
	public ModelAndView address(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/address/address.jsp");
		
		return mv;
	}
	@RequestMapping("/personalInfo")
	@Action(description = "新版收发货地址")
	public ModelAndView personalInfo(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/personalInfo/personalInfo.jsp");
		
		return mv;
	}
	@RequestMapping("/showImage")
	@Action(description = "新版收发货地址")
	public ModelAndView showImage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/personalInfo/showImage.jsp");
		
		return mv;
	}
	

	/**
	 * 企业审核
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/audit")
	public ModelAndView audit(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/tenant/tenantAudit.jsp");
		List<TenantInfo> infos=tenantInfoService.getAllCounts();
 		QueryFilter queryFilter=new QueryFilter(request,"InfoItem");
		if(!infos.isEmpty()){
              if(infos.get(0).getTotal()>0){
			queryFilter.addFilter("orderField", "accountTime");
           }
		}
		String systemid = request.getParameter("temid");
		if(!StringUtil.isEmpty(systemid)){
			queryFilter.addFilter("fromSysId", systemid);
			mv.addObject("temid", systemid).addObject("flag", "1");
		}else{
			mv.addObject("flag", "0");
		}
		List<TenantInfo> tenantList = tenantInfoService.getAll(queryFilter);		
		return mv.addObject("tenantList", tenantList);
	}
	/**
	 * 企业管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/auditSubsystem")
	public ModelAndView auditSubsystem(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		long userId = RequestUtil.getLong(request, "userId");
		long fromSysId = RequestUtil.getLong(request, "fromSysId");
		ModelAndView mv = new ModelAndView("/tenant/tenantAuditSubsystem.jsp");
		mv.addObject("userId", userId);
		mv.addObject("fromSysId", fromSysId);
		List<TenantInfo> infos=tenantInfoService.getAllCounts();
		QueryFilter queryFilter=new QueryFilter(request,"InfoItem");
		if(!infos.isEmpty()){
			if(infos.get(0).getTotal()>0){
				queryFilter.addFilter("orderField", "accountTime");
			}
		}
		//String systemid = request.getParameter("temid");
		if(!StringUtil.isEmpty(fromSysId)){
			queryFilter.addFilter("fromSysId", fromSysId);
			mv.addObject("temid", fromSysId).addObject("flag", "1");
		}else{
			mv.addObject("flag", "0");
		}
		List<TenantInfo> tenantList = tenantInfoService.getAll(queryFilter);		
		return mv.addObject("tenantList", tenantList);
	}
	
	/**
	 * 取得企业明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@Action(description = "查看企业明细")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long sysOrgInfoId = RequestUtil.getLong(request, "sysOrgInfoId");
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		ModelAndView view=new ModelAndView("/tenant/tenantInfoDetail.jsp");
		return view.addObject("info", info);
	}
	
	
	/**
	 * 异步审核企业信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("depTenant")
	@ResponseBody
	public boolean depTenant(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String state = RequestUtil.getString(request, "state");
		String sysId = RequestUtil.getString(request, "sysOrgInfoId");
		boolean flag = true;
		try {
			TenantInfo info = tenantInfoService.getById(Long.parseLong(sysId));
			if(!StringUtil.isEmpty(state) && !StringUtil.isEmpty(sysId) ){
				try {
					/*if(state.equals("1")){
						String sql = "update sys_org_info_copy set state = 1 where SYS_ORG_INFO_ID ="+sysId;
						int a = jdbcTemplate.update(sql);
					}else if(state.equals("2")){
						String sql = "update sys_org_info_copy set state = 2 where SYS_ORG_INFO_ID ="+sysId;
						int a = jdbcTemplate.update(sql);
					}*/
					if(info != null){
						info.setState(Integer.parseInt(state));
						tenantInfoService.updateState(info.getState(),info.getSysOrgInfoId());
					}
					ucSysAuditService.addLog("审核", "AuditTenantInfoController.dep.ht", JSON.toJSONString(info), "成功");
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	
	
	@RequestMapping("certifiedFeedback")
	@Action(description = "查看企业明细")
	public ModelAndView certifiedFeedback(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    String id=RequestUtil.getString(request, "id");
	    String code=RequestUtil.getString(request, "code");
	    String message=RequestUtil.getString(request, "message");
	    message=URLDecoder.decode(message, "UTF-8");
		String status=RequestUtil.getString(request, "status");
		ModelAndView view=new ModelAndView("/tenant/certifiedFeedback.jsp");
		return view.addObject("id", id).addObject("code", code).addObject("message", message).addObject("status", status);
	}

	/**
	 * 异步批量审核企业信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("batchPass")
	@ResponseBody
	public boolean batchPass(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String state = RequestUtil.getString(request, "state");
		String sysOrgInfoIds = request.getParameter("sysOrgInfoIds");
		String[] ids = sysOrgInfoIds.split(",");
		boolean flag = true;
		try {
			for (String id : ids) {
				TenantInfo info = tenantInfoService.getById(Long.parseLong(id));
				if(!StringUtil.isEmpty(state) && !StringUtil.isEmpty(id) ){
					try {
						if(info != null){
							info.setState(Integer.parseInt(state));
							tenantInfoService.updateState(info.getState(), info.getSysOrgInfoId());
						}
						ucSysAuditService.addLog("审核", "TenantInfoController.batchPass.ht", JSON.toJSONString(info), "成功");
					} catch (Exception e) {
						e.printStackTrace();
						flag = false;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 批量驳回
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("batchReject")
	@ResponseBody
	public boolean batchReject(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String state = RequestUtil.getString(request, "state");
		String sysOrgInfoIds = request.getParameter("sysOrgInfoIds");
		String[] ids = sysOrgInfoIds.split(",");
		boolean flag = true;
		try {
			for (String id : ids) {
				TenantInfo info = tenantInfoService.getById(Long.parseLong(id));
				if(info.getState()==2){
					flag = false;
				}
				if(!StringUtil.isEmpty(state) && !StringUtil.isEmpty(id) ){
					try {
						if(info != null&&flag){
							info.setState(Integer.parseInt(state));
							tenantInfoService.updateState(info.getState(), info.getSysOrgInfoId());
							ucSysAuditService.addLog("驳回", "TenantInfoController.batchReject.ht", JSON.toJSONString(info), "成功");
						}else{
							ucSysAuditService.addLog("驳回", "TenantInfoController.batchReject.ht", JSON.toJSONString(info), "失败");
						}
					} catch (Exception e) {
						e.printStackTrace();
						flag = false;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	
	@RequestMapping("/assignRole")
	public ModelAndView assignRole(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//获取当前人的企业id
		//然后根据企业id获取企业的系统来源
		//根据系统来源获得系统调用角色接口
		boolean flag=false;
		ModelAndView mv = new ModelAndView("/tenant/role.jsp");
		String userId=RequestUtil.getString(request, "userId");
		Long sysOrgInfoId=ContextUtil.getCurrentTenantId();
		ISysUser user=ContextUtil.getCurrentUser();
		if (StringUtil.isEmpty(sysOrgInfoId)) {
			sysOrgInfoId =(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}
		String systemId="100";
		String openId = "";
		openId = RequestUtil.getString(request, "openId");
		String orgopenId = "";
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		if(info!=null){
			systemId = info.getSystemId();//该systemid为系统来源  fromSysId
			orgopenId= info.getOpenId();
		}
		
		List<UserRole> roleList = new ArrayList<UserRole>();
		/********************************************调用接口显示数据选中角色*************************************************************/
		//如果systemId为空或者为100那么为航天云网的角色管理不需要去调用接口直接查询数据库
	/*	if (!StringUtil.isEmpty(systemId) && !"100".equals(systemId)) {
			int i = 4;
			String result = "";
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService
					.getAllUrlByTypeAndClassifyWithSys(i, "userRole", systemId
							+ "");
			SubSystem subSystem = subSystemService.getById(Long
					.parseLong(systemId));
			for (InterfaceUrlBean urlBean : urls) {
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("openId", openId);
				// data.put("aptitudes", aptitudes);
				params.put("systemId", systemId);
				params.put("data", data);
				result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()
						+ urlBean.getUrl(), params);
				if ("请求错误".equals(result)) {
					String jieguo = urlBean.getSubSystemName() + "地址连接失败!";
					mv = new ModelAndView("/net/netError.jsp");
					return mv.addObject("jieguo", jieguo);
				}
				System.out.print(urlBean.getSubSystemName() + "返回的结果是" + result);
				
				JSONObject jsonObject = JSONObject.fromObject(result);
				String status = jsonObject.getString("status");
				String msg = jsonObject.getString("msg");
				if (!"200".equals(status)) {
					System.out.print(urlBean.getSubSystemName()
							+ "服务调用失败,失败原因:" + msg);
				}
				Object object = jsonObject.get("results");
				JSONArray array=null;
				if(object.toString()=="null"&&"500".equals(status)){
			              System.out.println(msg);
				}else{
					array = jsonObject.getJSONArray("results");
					for (int a = 0; a < array.size(); a++) {
						JSONObject json = array.getJSONObject(a);
						UserRole userRole = (UserRole) JSONObject.toBean(json,
								UserRole.class);
						userRole.setSystemName(subSystem.getSysName());
						roleList.add(userRole);
					}
				}
				
			}
		}*/
		/********************************************调用接口显示数据选中角色*************************************************************/
		/*else */if (!StringUtil.isEmpty(userId)) {
			StringBuffer sqlroleSelected = new  StringBuffer();
			sqlroleSelected.append("   SELECT  sub.systemId, sub.sysName, b.roleId, b.roleName, info.SYS_ORG_INFO_ID, info.name ,c.fullname  ");
			sqlroleSelected.append("   FROM sys_user c,sys_user_role a,sys_role b, sys_org_info info, sys_subsystem sub WHERE  ");
			sqlroleSelected.append("   a.userId  = c.userId   AND a.roleId = b.roleId  AND info.SYS_ORG_INFO_ID = c.orgId AND sub.systemId = b.systemId    AND c.userId = '"+userId+"' ");
			 // List<Map<String, Object>> roleListmap = jdbcTemplate.queryForList(sqlroleSelected.toString(),userId,sysOrgInfoId,sysOrgInfoId,userId,sysOrgInfoId,userId);
			 List<Map<String, Object>> roleListSelectedmap = jdbcTemplate.queryForList(sqlroleSelected.toString());
			   if(roleListSelectedmap!=null&&roleListSelectedmap.size()>0){
					for (Map<String, Object> m : roleListSelectedmap) {
					UserRole userrole = UserRole.class.newInstance();
					userrole.setRoleId(Long.parseLong(m.get("roleId").toString()));
					userrole.setFullname(m.get("fullname").toString());
					userrole.setRoleName(m.get("roleName").toString());
					userrole.setSystemName(m.get("sysName").toString()); 
					roleList.add(userrole);
					}
				}

		}
		return mv.addObject("roleList", roleList).addObject("systemId", systemId).addObject("orgopenId", orgopenId).addObject("openId", openId).addObject("userId", userId).addObject("flag", flag);
	}
	/**
	 * 
	 * 中金开户信息
	 * 
	 * 
	 * */
	@RequestMapping("/getBranchAccount")
	public ModelAndView getBranch(){
		ModelAndView mv=new ModelAndView("/tenant/accBranch.jsp");			
		Long sysOrgInfoId = null;		
		//尝试获取当前用户			
		ISysUser user = ContextUtil.getCurrentUser();
		if(user != null){
			sysOrgInfoId =(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());			
		}
		if(sysOrgInfoId==null){
			sysOrgInfoId=(Long) iCache.getByKey("orgInfoId");					
		}
		if(sysOrgInfoId==this.ywOrgId){
			mv = new ModelAndView("/tenant/hasNoTenant.jsp");
			return mv;
		}
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);		
		List<BranchBean> branchs = branchBeanService.getByOrgid(sysOrgInfoId);
		String msg="一键开户";
		String types="";
		if(!branchs.isEmpty()){
			String type = branchs.get(0).getCredentialsType();
			if("A".equals(type)){
				types="身份证";
			}else if("B".equals(type)){
				types="户口簿";				
			}else if("C".equals(type)){
				types="军官证";				
			}else if("D".equals(type)){
				types="警官证";				
			}else if("E".equals(type)){
				types="护照";				
			}else if("F".equals(type)){
				types="港澳通行证";				
			}else if("L".equals(type)){
				types="其他";				
			}else if(type==null){
				types="其他";
			}			
		}else{
			types="身份证";
		}
		BranchBean branch=null;
		boolean fl=false;
		String flag="0";
		String statsus = info.getAccountStatsus();
		if(!"1".equals(statsus)){
			//未实名认证
			fl=true;
		}
		String cod="";
		if(!branchs.isEmpty() && !fl){
			//已有中金账户
			branch = branchs.get(0);
			String accstate = branch.getAccstate();
			if("2".equals(accstate) || "4".equals(accstate) || "0".equals(accstate) || "5".equals(accstate)){
				 //2开户失败   
				 msg="一键开户";
				 if("5".equals(accstate)){
					 cod="您的实名认证信息已修改，请重新开户~";
				 }if("2".equals(accstate) || "4".equals(accstate)){
					 cod="开户失败，请重新开户~";
				 }
			 }else if("1".equals(accstate)||"3".equals(accstate)){
				 msg="已开户";
				 flag="1";
			 }	
		}else if(branchs.isEmpty() && !fl){
			msg="一键开户";
		}
		if(!"".equals(cod)){
			mv.addObject("orginfo", info).addObject("msg", msg).addObject("type", types).addObject("flag", flag).addObject("cod",cod);
			return mv;
		}
		mv.addObject("orginfo", info).addObject("msg", msg).addObject("type", types).addObject("flag", flag);		
		return mv;
	}
	@RequestMapping("/valiBranchAndRealNameState")
	@ResponseBody
	public Map<String,Object> valiBranchAndRealNameState(HttpServletRequest request){
		String orgId = RequestUtil.getString(request, "id");
		String resultMsg=null;
		String msg="";
		String flag="0";
		Map<String,Object> nmap=new HashMap<String, Object>();
		TenantInfo tenantInfo = tenantInfoService.getById(Long.parseLong(orgId));
		if(tenantInfo.getState() != 5){
			nmap.put("msg", "还未通过实名审核！");
			nmap.put("flag", flag);
			return nmap;
		}
		BranchBean branchBean=new BranchBean();
		long id =0L;
		List<BranchBean> branchs = branchBeanService.getByOrgid(Long.parseLong(orgId));
		if(!branchs.isEmpty()){
			branchBean=branchs.get(0);
			id=branchs.get(0).getId();
			if(branchs.get(0).getAccstate() != null){
				if(branchs.get(0).getAccstate().equals("1")||branchs.get(0).getAccstate().equals("3") || branchs.get(0).getAccstate().equals("4") || branchs.get(0).getAccstate().equals("5")){
					branchBean.setBusinessFlag("2");
				}else{
					branchBean.setBusinessFlag("1");
				}
			}else{
				branchBean.setBusinessFlag("1");
			}
		}else{
			id =UniqueIdUtil.genId();
			branchBean.setBusinessFlag("1");
		}	
		branchBean.setId(id);
 		branchBean.setOrgid(Long.parseLong(orgId));
		branchBean.setCreatetime(new Date());
		branchBean.setBranchaccountname(tenantInfo.getName());
//		branchBean.setAccstate("0");//默认账户待审核
		branchBean.setClientProperty("1");//默认客户为公司
		branchBean.setCredentialsType("A");//默认证件为身份证
		branchBean.setCredentialsNumber(tenantInfo.getFrsfzhm());
		branchBean.setAccountType1("1");//账户类型？
		branchBean.setFlag("1");//标识为1
		branchBean.setState("0");//待审核
		branchBean.setChannelId("umpay");		
		branchBean.setAccountType("0");//对公
		branchBean.setMerchants(orgId);		
		JSONObject json=new JSONObject();
		Map<String, Object> paramss=getParams(branchBean,tenantInfo);
		String result="";
		boolean openFlag=false;
		ResultDto	reulDto=null;
		System.out.println("sadsdasdasdas+"+openAccount_url+"----"+paramss);
		try {
			json.put("flag", "zj");
			result=HttpClientUtil.JsonPostInvoke(openAccount_url,paramss);
			System.out.println("中金调用end"+result);
			 if(!StringUtil.isEmpty(result)&&!result.equals("请求错误")){
        		 System.out.println("result="+result);
        		 reulDto= JSON.parseObject(result, ResultDto.class);
        	 }
		} catch (Exception e) {
			System.out.println("result="+result); 
			json.put("Msg", "中金接口调用失败");
			e.printStackTrace();
		}
			
		if(reulDto!=null){
			 if(reulDto.getRETCODE().equals("000000")){
				 openFlag=true;
	    		 //更新企业的认证状态 
	    	 }else{
	    		 resultMsg="中金接口提示您:"+ reulDto.getRETMSG();
	    	 }
		}else{
			openFlag=false;
			resultMsg="接口调用失败";
		}		
		if(!openFlag){
		    //中金接口调用失败
			if(!StringUtil.isEmpty(branchBean.getAccstate())){
				if(branchBean.getAccstate().equals("2")){
					branchBean.setAccstate("2");//开户未成功
				}else if(branchBean.getAccstate().equals("4")||branchBean.getAccstate().equals("1")||branchBean.getAccstate().equals("3"))
				{
						branchBean.setAccstate("4");//开户未成功
				}else if(branchBean.getAccstate().equals("5")){
						branchBean.setAccstate("4");//开户未成功
					}
				System.out.println("----+"+branchBean.getAccstate());
					
			}else{
				branchBean.setAccstate("2");//开户未成功
				System.out.println("---+执行了");
			}
			
			branchBeanService.updateOpenCloseAccount(branchBean);
			flag="0";
			nmap.put("msg", resultMsg);
			nmap.put("flag", flag);
		}else{
		    //中金接口调用成功则开户	
			if(!StringUtil.isEmpty(branchBean.getAccstate())){
				if(branchBean.getAccstate().equals("2")){
					branchBean.setAccstate("1");//开户未成功
				}else
					if(branchBean.getAccstate().equals("4")&&branchBean.getAccstate().equals("5"))
				{
						branchBean.setAccstate("3");//开户未成功
				}
			}else{
				branchBean.setAccstate("1");//开户未成功
			}
			
			branchBean.setAccstate("1");
		   	branchBeanService.updateOpenCloseAccount(branchBean);
		   	msg="已开户";
			flag="2";
			nmap.put("msg", msg);
			nmap.put("flag", flag);
		}	
		return nmap;
	}
	
//	public ModelAndView bindCard(){
//		
//	}

	@RequestMapping("/getData")
	@ResponseBody
	public List<ISysRole> getData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysOrgInfo sysOrgInfo=ContextUtil.getCurrentOrgInfoFromSession();
		String  systemId=sysOrgInfo.getSystemId();//获得子系统的来源
		String topenId=sysOrgInfo.getOpenId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemId", "100");
		params.put("topenId", topenId);
		String url="http://10.142.15.34:8080/platform/system/api/roleBySystem.ht";
		String data=HttpClientUtil.JsonPostInvoke(url,params);
		SubSystem subSystem=null;
		if(!StringUtil.isEmpty(systemId)){
			subSystem = service.getById(Long.parseLong(systemId));
		}else{
			subSystem = service.getById(100L);
		}
		ISysRole rol = null;
		List<ISysRole>  treeList = new ArrayList<ISysRole>();
		//构造根节点
		Long i = -1L;
		i--;//子系统 角色 pid，-1,-2，-3，
		rol=iAuthenticate.getNewSysRole();
		rol.setRoleId(i);
		rol.setSystemId(-9999999999L);//子系统pid
		rol.setRoleName(subSystem.getSysName());
		treeList.add(rol);
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
     
	   Object jsonArray = jsonObject.get("results");  
	   List<ISysRole> sysRoles=JSON.parseArray(jsonArray+"", ISysRole.class);
	   for (ISysRole iSysRole : sysRoles) {
			//子系统父节点

			SysRole sysRole = new SysRole();
			sysRole.setSystemId(i);
			sysRole.setAlias(subSystem.getSysName());
			sysRole.setSubSystem(subSystem);
			sysRole.setRoleName(iSysRole.getRoleName());
			sysRole.setRoleId(iSysRole.getRoleId());
			
			treeList.add(sysRole);
	}
		return treeList;
	}
	/**
	 * 添加或更新用户表。
	 * 
	 * @param request
	 * @param response
	 * @param sysUser
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveRole")
	@Action(description = "添加或更新用户表")
	public void saveRole(HttpServletRequest request, HttpServletResponse response) throws Exception {


		String resultMsg = null;

		Long[] aryRoleId =RequestUtil.getLongAry(request,"roleId");// 角色Id数组
	
		
		if(aryRoleId==null || aryRoleId.length==0){
			resultMsg = getText("用户角色更新失败", "请选择所属角色！");
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;					
		}
		StringBuffer roleIds=new StringBuffer();
		for (Long roleId : aryRoleId) {
			roleIds.append(roleId+"@");
		}
		String roles=roleIds.substring(0, roleIds.length()-1);
		SysOrgInfo sysOrgInfo=ContextUtil.getCurrentOrgInfoFromSession();
		String  systemId=sysOrgInfo.getSystemId();//获得子系统的来源
		ISysUser sysUser=ContextUtil.getCurrentUser();
		String uopenId=sysUser.getOpenId();
        Map<String, Object> datas=new HashMap<String, Object>();
        datas.put("systemId", "100");
        datas.put("uopenId", uopenId);
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("roles", roles);
		datas.put("data", data);
		String url="http://10.142.15.34:8080/platform/system/api/userRoleAdd.ht";
		try {
			resultMsg=HttpClientUtil.JsonPostInvoke(url,datas);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.Fail);
		}
		com.alibaba.fastjson.JSONObject json=JSON.parseObject(resultMsg);
		int status=json.getIntValue("status");
		if(status==500){
			writeResultMessage(response.getWriter(), json.getString("msg"), ResultMessage.Fail);
		}else{
		 writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		}
	}
	
	@RequestMapping("formList")
	@Action(description = "查询物品分类")
	public ModelAndView formList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = RequestUtil.getString(request, "name");
		if(name!=null){
			name=URLDecoder.decode(name, "UTF-8");
		}
		ModelAndView mv=new ModelAndView("/tenant/tenantInfoFormList.jsp");
		return mv.addObject("name", name);
	}
	
	/**
	 * 加载角色数据 (角色分配栏目，只显示用户所属管理员的角色)
	 * */
	@SuppressWarnings("unused")
	@RequestMapping("getTreeByTenantByOrgId")
	@ResponseBody
	public List  getTreeByTenantByOrgId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		        // 角色集
				//子系统标识
				Long systemId1 = RequestUtil.getLong(request, "systemId", 0l);
				//String orgopenId1 = RequestUtil.getString(request, "orgopenId");
				String result="";
				Long tenantId = ContextUtil.getCurrentTenantId();
				ISysUser user=ContextUtil.getCurrentUser();
				user.setOrgSn(user.getOrgId());
				String account = user.getAccount();
				//String account=user.getOrgSn()+"_system";
				//user.setOrgSn(user.getOrgId());
				//String account=user.getOrgSn()+"_system";
				ISysUser userSystem=sysUserService.getByAccount(account); 
				userSystem.setOrgSn(user.getOrgId());
				/*if (StringUtil.isEmpty(tenantId)) {
					tenantId=(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
				}*/ 
				 List<SysRole> SysRoleList = new ArrayList<SysRole>();
				/*********************加的其他企业的角色信息 展示在企业管理管理加载角色列表中*******************************/
			/*	if (StringUtil.isEmpty(tenantId)) {
					tenantId =(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
				}
				String systemId="100";
				String orgopenId = "";
				TenantInfo info = tenantInfoService.getById(tenantId);
				if(info!=null){
					systemId = info.getSystemId();//该systemid为系统来源  fromSysId
					orgopenId= info.getOpenId();
				}
				//如果systemId为空或者为100那么为航天云网的角色管理不需要去调用接口直接查询数据库
				if (!StringUtil.isEmpty(systemId) && !"100".equals(systemId)) {
					int i = 4;
					String result1 = "";
					List<InterfaceUrlBean> urls = subSystemInterfaceUrlService
							.getAllUrlByTypeAndClassifyWithSys(i, "role", systemId
									+ "");
					SubSystem subSystem = subSystemService.getById(Long
							.parseLong(systemId));
					for (InterfaceUrlBean urlBean : urls) {
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("openId", orgopenId);
						params.put("systemId", systemId);
						params.put("data", data);
						result1 = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()
								+ urlBean.getUrl(), params);
						if ("请求错误".equals(result1)) {
							String jieguo = urlBean.getSubSystemName() + "地址连接失败!";
							mv = new ModelAndView("/net/netError.jsp");
							return mv.addObject("jieguo", jieguo);
							System.out.print(jieguo);
						}
						System.out.print(urlBean.getSubSystemName() + "返回的结果是" + result1);
						JSONObject jsonObject = JSONObject.fromObject(result1);
						String status = jsonObject.getString("status");
						String msg = jsonObject.getString("msg");
						if (!"200".equals(status)) {
							System.out.print(urlBean.getSubSystemName()
									+ "服务调用失败,失败原因:" + msg);
						}
						Object object = jsonObject.get("results");
						JSONArray array=null;
						if(object.toString()=="null"&&"500".equals(status)){
					              System.out.println(msg);
						}else{
							array = jsonObject.getJSONArray("results");
							for (int a = 0; a < array.size(); a++) {
								JSONObject json = array.getJSONObject(a);
								JSONObject jsonRole = json.getJSONObject("role");
								SysRole SysRole = (SysRole) JSONObject.toBean(jsonRole,
										SysRole.class);
								SysRoleList.add(SysRole);
							}
						}
						
					}
				}*/
				/*********************加的其他企业的角色信息 展示在企业管理管理加载角色列表中*******************************/
				List<SysRole> roleList = new ArrayList<SysRole>();
				List<UserRole> roleList1 = new ArrayList<UserRole>();
				Long userId= userSystem.getUserId();
				Long sysOrgInfoId=userSystem.getOrgSn();
				boolean isManager = false;//判断是为企业管理员角色
				SubSystem subsystem = null;
			  if (!StringUtil.isEmpty(userId)) {
				  /*roleList = userRoleService.getByUserIdAndTenantId(
						  userId, sysOrgInfoId);*/
				  
				   String  sql =" SELECT   DISTINCT  sub.systemId, sub.sysName, b.roleId, b.roleName, info.SYS_ORG_INFO_ID,info.name  "
						     +"  FROM  sys_user c,sys_user_role a,sys_role b, sys_org_info info,  sys_subsystem sub  "
						     +"  WHERE  a.userId = c.userId  AND a.roleId = b.roleId  AND info.SYS_ORG_INFO_ID  = c.orgId  "
						     +" AND sub.systemId = b.systemId  and c.orgId= ?  AND c.userId = ? ";
				   List<Map<String, Object>> roleListmap1 = jdbcTemplate.queryForList(sql,sysOrgInfoId,userId);
				   //List<SysRole> SysRoleList = new ArrayList<SysRole>();
				   if(roleListmap1!=null&&roleListmap1.size()>0){
						for (Map<String, Object> m : roleListmap1) {
							SysRole SysRole = SysRole.class.newInstance();
//							SysRole.setRoleId(Long.parseLong(m.get("roleId").toString()));
//							SysRole.setSystemId(Long.parseLong(m.get("systemId").toString()));
                            SysRole.setRoleName(m.get("roleName").toString());
//							SysRole.setSystemName(m.get("sysName").toString());
//							SysRoleList.add(SysRole);
							if(SysRole.getRoleName().contains("企业管理员")){//只要登录用户的一个角色是企业管理员，可以显示全部角色
								 isManager=true;
							 } 
						}
					}
			  }
			
			  //查询子系统下的所有人员的角色和企业
			  StringBuffer sqlrole = new StringBuffer();
			  //sqlrole.append(" SELECT  DISTINCT sub.systemId, sub.sysName, b.roleId, b.roleName, info.SYS_ORG_INFO_ID,info.name  ");
			  sqlrole.append(" SELECT  sub.systemId, sub.sysName, b.roleId, b.roleName, info.SYS_ORG_INFO_ID,info.name  ");
			  sqlrole.append("  FROM   sys_user c,sys_user_role a,sys_role b, sys_org_info info,  sys_subsystem sub  ");
			  sqlrole.append("  WHERE  a.userId = c.userId  AND a.roleId = b.roleId  AND info.SYS_ORG_INFO_ID  = c.orgId  ");
			//  sqlrole.append("  AND   sub.systemId = b.systemId  and c.orgId= ?  ");
			  //查询子系统下的管理员的角色和企业
			  sqlrole.append("  AND   sub.systemId = b.systemId  and c.orgId= ? AND c.userId = ? ");
			  List<Map<String, Object>> roleListmap = jdbcTemplate.queryForList(sqlrole.toString(),sysOrgInfoId,userId);
			  if(roleListmap!=null&&roleListmap.size()>0){
					for (Map<String, Object> m : roleListmap) {
						SysRole SysRole = SysRole.class.newInstance();
						SysRole.setRoleId(Long.parseLong(m.get("roleId").toString()));
						SysRole.setSystemId(Long.parseLong(m.get("systemId").toString()));
						SysRole.setRoleName(m.get("roleName").toString());
						SysRole.setSystemName(m.get("sysName").toString());
						SysRoleList.add(SysRole);
						}   
					}
			    List  treeList=new ArrayList();
			    SysRole sysrole = null;
			    Long i=-1L;
			    SubSystem subsystem1 =null;
			  //查询子系统
			  String  subsystemsql ="  SELECT   DISTINCT sub.systemId, sub.sysName,info.SYS_ORG_INFO_ID,info.name   "
					     +"  FROM  sys_user c,sys_user_role a,sys_role b, sys_org_info info, sys_subsystem sub   WHERE a.userId  = c.userId  AND a.roleId = b.roleId   "
					     +"  AND info.SYS_ORG_INFO_ID = c.orgId   AND sub.systemId = b.systemId   and c.orgId= ?   " ;
			  ArrayList<SubSystem> sublist = new ArrayList<SubSystem>();
			  List<Map<String, Object>> subsystemListmap = jdbcTemplate.queryForList(subsystemsql.toString(),sysOrgInfoId);
		      if(subsystemListmap!=null&&subsystemListmap.size()>0){
					for (Map<String, Object> m : subsystemListmap) {
						subsystem1 = SubSystem.class.newInstance();
						subsystem1.setSystemId(Long.parseLong(m.get("systemId").toString()));
						subsystem1.setSysName(m.get("sysName").toString());
						sysrole = SysRole.class.newInstance();
						i--;//子系统 角色 pid，-1,-2，-3，
						sysrole.setRoleId(i);
						sysrole.setSystemId(-9999999999L);//子系统pid
						sysrole.setRoleName(subsystem1.getSysName());
						//子系统父节点
						treeList.add(sysrole);
						sublist.add(subsystem1);
						for(SysRole SysRole1:SysRoleList){//子系统添加 角色子节点
						if(SysRole1.getSystemId()!=null &&subsystem1.getSystemId()==SysRole1.getSystemId()){
							SysRole1.setSystemId(i);
							//所有子系统的子节点
							treeList.add(SysRole1);
						}
						
				       }
					}	
			   }	      

		   //企业管理员
			  if(isManager){
				  treeList=treeList;
		       }
			   
			    /*else{
                                                    非企业管理员
		    	   sqlrole.append(" AND c.userId = ? ");
		    	   List<Map<String, Object>> roleListmap = jdbcTemplate.queryForList(sqlrole.toString(),sysOrgInfoId,userId);
				   if(roleListmap!=null&&roleListmap.size()>0){
						for (Map<String, Object> m : roleListmap) {
							UserRole userrole1 = new UserRole();
							userrole1.setRoleId(Long.parseLong(m.get("roleId").toString()));
							userrole1.setRoleName(m.get("roleName").toString());
							userrole1.setSystemName(m.get("sysName").toString()); 
							roleList1.add(userrole1);
						}
			      }
		       }
			  }*/
			 return treeList;
		       
		       }
	
	//加载角色数据
	
	@RequestMapping("getTreeByTenant")
	@ResponseBody
	public List  getTreeByTenant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		        // 角色集
				List  treeList = new ArrayList();
				//子系统标识
				Long systemId = RequestUtil.getLong(request, "systemId", 0l);
				String orgopenId = RequestUtil.getString(request, "orgopenId");
				String result="";
				Long tenantId = ContextUtil.getCurrentTenantId();
				ISysUser user=ContextUtil.getCurrentUser();
				if (StringUtil.isEmpty(tenantId)) {
					tenantId=(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
				}
				ISysRole rol = null;
				if(tenantId.longValue() == 0l){
					rol = iAuthenticate.getNewSysRole();
					rol.setRoleId(-1l);
					rol.setSystemId(-1L);//子系统pid
					rol.setRoleName("平台未分配角色");
					//子系统父节点
					treeList.add(rol);
				}
				//云网单独处理
				if(systemId.longValue() == 0l || systemId.longValue() == 100l ){
					SubSystem subSystem = service.getById(100l);
					//构造根节点
					Long i=-1L;
					i--;//子系统 角色 pid，-1,-2，-3
					// 获取企业自身角色和平台公共角色
					rol=iAuthenticate.getNewSysRole();
					rol.setRoleId(i);
					rol.setSystemId(-9999999999L);//子系统pid
					rol.setRoleName(subSystem.getSysName());
					//子系统的父节点
					treeList.add(rol);
					List<SaasRole> saasRoles=saasRoleService.getByTenantIdAndPlatformPublicRole(tenantId);
					if(!saasRoles.isEmpty()){
						for (SaasRole saasRole : saasRoles) {
							SysRole role=saasRole.getRole();
							if (isNotEmpty(role)) {
								role.setSystemId(i);
								role.setRoleType(saasRole.getStatus());//将角色来源存取到角色类型中
								role.setSubSystem(subSystem);
								treeList.add(role);
							}
						}
					}else{
						//如果企业未分配角色，则启用系统角色
						List<ISysRole> subRoles = sysRoleService.getInnerSysRole(systemId);
						if(!subRoles.isEmpty()){
							for(ISysRole role : subRoles){
								SysRole sysRole = (SysRole)role;
								sysRole.setSystemId(i);
								//sysRole.setAlias(subSystem.getSysName());
								sysRole.setSubSystem(subSystem);
								treeList.add(sysRole);
							}
						}
					}
				}
				else{
					// 子系统和企业会员
					if(systemId.longValue() != 0l && tenantId.longValue() != 100l){
						SubSystem subSystem = service.getById(systemId);
						int j=4;
						//构造根节点
						Long i=-1L;
						i--;//子系统 角色 pid，-1,-2，-3
						rol=iAuthenticate.getNewSysRole();
						rol.setRoleId(i);
						rol.setSystemId(-9999999999L);//子系统pid
						rol.setRoleName(subSystem.getSysName());
						//子系统的父节点
						//子系统父节点
						treeList.add(rol);
						//调用子系统查询所有角色接口
						List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassifyWithSys(j,"role",systemId+"");
						for(InterfaceUrlBean urlBean:urls){
							Map<String, Object> params = new HashMap<String, Object>();
							Map<String, Object> data = new HashMap<String, Object>();
							data.put("openId", orgopenId);
							//data.put("aptitudes", aptitudes);
							params.put("systemId", systemId);
							params.put("data", data);
							result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
							System.out.print(urlBean.getSubSystemName()+"返回的结果是"+result);
							JSONObject jsonObject = JSONObject.fromObject(result);
							String status = jsonObject.getString("status");
							String msg = jsonObject.getString("msg");
							if(!"200".equals(status)){
								System.out.print(urlBean.getSubSystemName()+"服务调用失败,失败原因:"+msg);
							}
							
							//String _data = jsonObject.getString("results");
							JSONArray array = jsonObject.getJSONArray("results");
							for(int a=0;a<array.size();a++){
								JSONObject json = array.getJSONObject(a);
								SaasRole saasRole = (SaasRole) JSONObject.toBean(json, SaasRole.class);
								SysRole role=saasRole.getRole();
								role.setSystemId(i);
								role.setSubSystem(subSystem);
								treeList.add(role);
								//saasRoleService.add(saasRole);
							}
						}
						
					}
					
				}
		return treeList;
	}
	
	
	@RequestMapping("/assignDepartMent")
	public ModelAndView assignDepartMent(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/tenant/assignDepartment.jsp");
		String userId=RequestUtil.getString(request, "userId");
		List<SysUserOrg> orgList = new ArrayList<SysUserOrg>();
		if(!StringUtil.isEmpty(userId)){
			 orgList = sysUserOrgService.getOrgByUserId(Long.parseLong(userId));
		}
		List<Demension>demensionList=demensionService.getAll();
		return mv.addObject("orgList", orgList).addObject("userId", userId).addObject("demensionList", demensionList);
	}
	
	
	@RequestMapping("/editInfo")
	public ModelAndView editInfo(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/tenant/tenantEditInfo.jsp");
		String userId=RequestUtil.getString(request, "userId");
		List<UserRole> roleList = new ArrayList<UserRole>();
		if(!StringUtil.isEmpty(userId)){
			roleList =userRoleService.getByUserId(Long.parseLong(userId));
		}
		return mv.addObject("roleList", roleList).addObject("userId", userId);
	}
	
	@RequestMapping("/base/json")  
    public void exchangeJson(HttpServletRequest request,HttpServletResponse response) {  
       try {  
        response.setContentType("text/plain");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        Map<String,String> map = new HashMap<String,String>();   
        map.put("result", "content");  
        map.put("results", "contents");  
        PrintWriter out = response.getWriter();       
        JSONObject resultJSON = JSONObject.fromObject(map); //根据需要拼装json  
        String jsonpCallback = request.getParameter("jsonpCallback");//客户端请求参数  
        out.println(jsonpCallback+"("+resultJSON.toString()+")");  
       
        out.flush();  
        out.close();  
      } catch (IOException e) {  
       e.printStackTrace();  
      }  
    }  
	
	
	/**
	 * 开户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("settlementAccount")
	@Action(description = "编辑企业认证信息")
	public ModelAndView settlementAccount(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/tenant/settlementAccount.jsp");
        String flag=RequestUtil.getString(request, "flag","1");
		ISysUser user = ContextUtil.getCurrentUser();
		Long sysOrgInfoId;
		if (com.hotent.core.util.BeanUtils.isNotEmpty(user)) {
			sysOrgInfoId = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}else{
			sysOrgInfoId=(Long)iCache.getByKey("orgInfoId");
		}
		if(sysOrgInfoId==this.ywOrgId){
			mv = new ModelAndView("/tenant/hasNoTenant.jsp");
			return mv;
		}
		String returnUrl = request.getHeader("Referer");
//		Long userId=ContextUtil.getCurrentUserId();
//		QueryFilter queryFilter = new QueryFilter(request);
//		queryFilter.addFilter("orgid", sysOrgInfoId);
//		queryFilter.addFilter("flag", flag);
		
//		List<BranchBean> branchs = branchBeanService.getAll(queryFilter);
		List<BranchBean> branchs = branchBeanService.getByOrgid(sysOrgInfoId);		
		TenantInfo tenantInfo=tenantInfoService.getById(sysOrgInfoId);
		BranchBean branchBean = new BranchBean();
		branchBean.setOrgid(sysOrgInfoId);
		BranchBean branch=null;
		String msg="";
		String flagk="";
		boolean fl=false;
		int infostate = tenantInfo.getState();
		if(6==infostate){
			msg="已冻结";
			flagk="1";
			fl=true;
		}
		if(!fl){
			if(!branchs.isEmpty()){
				//有账户
				branch = branchs.get(0);
				String accstate = branch.getAccstate();
				String stlstate = branch.getStlstate();
				if("1".equals(accstate) ||"3".equals(accstate)){
					//开户成功
					if("1".equals(stlstate) || "3".equals(stlstate)){
						//账号已经绑定成功
						msg="已绑定";
						flagk="3";
					}else if("2".equals(stlstate) || "4".equals(stlstate) || "0".equals(stlstate)){
						//0待绑定   2绑定失败   4 绑定修改失败(以前的数据)
						msg="去绑定";
						flagk="4";					
					}				
				}else{
					//开户不成功
					flagk="0";
				}
			}else{
				//未开户
				flagk="0";
			}
		}
		return mv
				.addObject("branch", branchs.size()>0?branchs.get(0):branchBean)
				.addObject("returnUrl", returnUrl).addObject("info", tenantInfo).addObject("msg", msg).addObject("flag", flagk);
	}
	
	/**
	 * ajax获取级联数组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getProvinceId")
	@Action(description = "查询物品分类")
	@ResponseBody
	public List<Dictionary> getProvinceId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String optvalue = request.getParameter("value");

		List<Map<String, Object>> list = jdbcTemplate
				.queryForList(
						"select c.* from BF_ZD_CITY c inner join BF_ZD_PROVINCE p where c.PROVID=p.PROVID and p.PROVCODE=?",
						optvalue);
		List<Dictionary> dics = new ArrayList();

		for (Map<String, Object> m : list) {
			Dictionary dic = new Dictionary();
			dic.setDicId(Long.parseLong(m.get("CITYID").toString()));
			dic.setItemName(m.get("CITYNAME").toString());
			dic.setItemValue(m.get("CITYCODE").toString());
			dics.add(dic);
		}

		return dics;
	}
	
	@RequestMapping("getBankId")
	@Action(description = "查询物品分类")
	@ResponseBody
	public List<Dictionary> getBankId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String optvalue = request.getParameter("value");
        //缓存一份到缓存中
		//把行别代码作为key生成一份缓存
	   List<Dictionary> dics = new ArrayList();
       if(iCache.getByKey(optvalue)!=null){
    	   //表示已经有缓存
    	   dics=(List<Dictionary>) iCache.getByKey(optvalue);
       }else{
		List<Map<String, Object>> list = jdbcTemplate
				.queryForList(
						"select p.FQHHO2,p.HANBDM,p.FKHMC1 from BF_ZD_PAYBANK p where p.HANBDM=?",
						optvalue);
		

		for (Map<String, Object> m : list) {
			Dictionary dic = new Dictionary();
			dic.setDicId(Long.parseLong(m.get("FQHHO2").toString()));
			dic.setItemName(m.get("FKHMC1").toString());
			dic.setItemValue(m.get("FQHHO2").toString());
			dics.add(dic);
		}
       }
       //更新缓存信息
       iCache.add(optvalue, dics);
       
		return dics;
	}
	
	/**
	 * 实体类的转化
	 * @param dto
	 * @param branchBean
	 * @return
	 */
	
	public BranchBean  convert(BranchDto dto,BranchBean branchBean) {
		long id =UniqueIdUtil.genId();
		if(dto!=null&&dto.getBranchId()!=null&&dto.getBranchId()!=0){
			branchBean.setId(dto.getBranchId());
		}else{
			branchBean.setId(id);
		}
		branchBean.setOrgid(dto.getOrgid());
		branchBean.setCreatetime(new Date());
		branchBean.setBranchaccountname(dto.getBranchaccountname());
		branchBean.setAccstate(dto.getAccstate());
		branchBean.setClientProperty(dto.getClientProperty());
		branchBean.setCredentialsType(dto.getCredentialsType());
		branchBean.setCredentialsNumber(dto.getCredentialsNumber());
		branchBean.setBusinessFlag(dto.getBusinessFlag());
		branchBean.setAccountType1(dto.getAccountType1());
		branchBean.setFlag(dto.getFlag());
		branchBean.setChannelId("umpay");
		branchBean.setState("3");
		branchBean.setAccountType("0");
		return branchBean;
		
	}	
	public Map<String, Object> getParams(BranchBean branchBean,TenantInfo info){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sysid", "uc");
		params.put("submerid", branchBean.getOrgid()+"");
		params.put("cltacc_cltnm", branchBean.getBranchaccountname());
		params.put("clt_nm", info.getIncorporator());
		params.put("clt_kd", branchBean.getClientProperty());
		params.put("clt_cdtp", branchBean.getCredentialsType());
		params.put("clt_cdno", branchBean.getCredentialsNumber());
		if (("1").equals(info.getIsThreeInOne())) {
			//三证合一
			params.put("clt_orgcd", info.getCreditCode());
			params.put("clt_bslic", info.getCreditCode());
			params.put("clt_swdjh", info.getCreditCode());
		}else{
			//非三证合一
			params.put("clt_orgcd", info.getCode());
			params.put("clt_bslic", info.getYyzz());
			params.put("clt_swdjh", info.getNsrsbh());
		}
		params.put("fcflg", branchBean.getBusinessFlag());
		params.put("acctp", branchBean.getAccountType1());
		return params;
	} 
	
	
	/**
	 * 编辑企业
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("editNew")
	@Action(description = "新注册个人用户编辑企业")
	public ModelAndView editNew(HttpServletRequest request) throws Exception {
		ISysUser user=ContextUtil.getCurrentUser();
		Long sysOrgInfoId=(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId();
		String returnUrl = RequestUtil.getPrePage(request);
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		if(info!=null){
			info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
		}
		ModelAndView view=new ModelAndView("/tenant/tenantInfoEditNew.jsp");
		return view.addObject("info", info)
				.addObject("returnUrl", returnUrl);
	}
	/**
	 * 加入其它企业成为其它企业的子账号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("joinCompany")
	@Action(description = "加入企业")
	public ModelAndView joinCompany(HttpServletRequest request) throws Exception {
		ISysUser user=ContextUtil.getCurrentUser();
		Long sysOrgInfoId=(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId();
		String returnUrl = RequestUtil.getPrePage(request);
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		if(info!=null){
			info.setInfo(StringUtil.formatStr2UrlStr(info.getInfo()));
		}
		ModelAndView view=new ModelAndView("/tenant/joinCompany.jsp");
		return view.addObject("info", info)
				.addObject("returnUrl", returnUrl);
	}
	/**
	 * 添加或更新企业成功提示页面
	 * @param request
	 * @param response
	 * @param company
	 * @param bindResult
	 * @param viewName
	 * @throws Exception
	 */
	@RequestMapping("saveSucess")
	@Action(description = "添加企业")
	public ModelAndView saveSucess(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = "成功";
		TenantInfo info = getFormObject(request);
		ModelAndView mv = new ModelAndView("/tenant/regSuccess.jsp");
		ISysUser user = ContextUtil.getCurrentUser();
		Long[] aryRoleId =new Long[]{10000045050018L,10000045050019L,10000045050020L,10000045050021L,10000045050022L};// 角色Id数组
		Map<String, Object> map = tenantInfoService.registerSysOrgInfo(info, "123456", aryRoleId, (SysUser)user);
		  final SysUser	sysUser = (SysUser)map.get("sysUser");
		try {
			info.setRegistertime(new Date());
		   TenantInfo infos = (TenantInfo)map.get("sysOrgInfo");
			//更新下
			String sqlUpdate = "update sys_user_extence  set state = 1  where user_id = '"+user.getUserId()+"'";
			int flag = jdbcTemplate.update(sqlUpdate);
			
			if(flag == 0){
				String sql = "insert into sys_user_extence  set state = 1 ,user_id = '"+user.getUserId()+"'";
				jdbcTemplate.execute(sql);
			}
			info = (TenantInfo)map.get("sysOrgInfo");
			JSONObject json = new JSONObject();
			JSONObject tenantJson = json.fromObject(infos);
			JSONObject userJson = json.fromObject(sysUser);
			new JMSRunableThread("tenant_add",tenantJson.toString());
			new JMSRunableThread("user_add",userJson.toString());
			/*List<SubSystem> all = subSystemService.getAll();
			for (SubSystem subSystem : all) {
				jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						MapMessage ms1=session.createMapMessage();
						ms1.setString("tenant_add",tenantJson.toString());
						ms1.setString("user_add",tenantJson.toString());
						return ms1;
					}
				});
			}*/
			//个人用户创建企业
			/*ExecutorService exs = Executors.newCachedThreadPool();
			int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i,"tenant");
			for(InterfaceUrlBean urlBean:urls){
				Map<String, Object> params = new HashMap<String, Object>();
				// 子系统标识
				long systemId = urlBean.getSubId();
				// 企业标识（统一开发标识）(暂时没有)
				String topenId = info.getOpenId();
				// 企业账号
				long tsn = info.getSysOrgInfoId();
				// 企业名称
				String tname = info.getName();
				String fromSysId = info.getSystemId();
				if(fromSysId==null || "".equals(fromSysId)){
					fromSysId="100";
				}
				// 会员标识（统一开发标识）
				String uopenId = sysUser.getOpenId();
				// 会员名称
				String uname = sysUser.getAccount();
				// 登录密码
				String pwd = sysUser.getPassword();
				// 手机号
				String mobile = sysUser.getMobile();
				// 邮箱
				String email = sysUser.getEmail();
				//用户名
				String fullName=sysUser.getFullname();
				// 企业状态
				// 默认为未完善"0"
				Short state = 0;
				params.put("topenId", topenId);
				params.put("tsn", tsn);
				params.put("tname", tname);
				params.put("uopenId", uopenId);
				params.put("uname", uname);
				params.put("pwd", pwd);
				params.put("mobile", mobile);
				params.put("email", email);
				params.put("state", state);
				params.put("fullName", fullName);
				params.put("systemId", systemId);
				params.put("fromSysId", fromSysId);
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
			    	//result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
			    	//使用多线程同步到子系统

			    	CallableThread ct = new CallableThread(params,urlBean.getSubIndexUrl()+urlBean.getUrl());//实例化任务对象
			    	//大家对Future对象如果陌生，说明你用带返回值的线程用的比较少，要多加练习
					Future<String> future = exs.submit(ct);//使用线程池对象执行任务并获取返回对象
					try {
						result = future.get();//当调用了future的get方法获取返回的值得时候
						//如果线程没有计算完成，那么这里就会一直阻塞等待线程执行完成拿到返回值
						System.out.println(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
					exs.shutdown();
			    }
			    catch (Exception e){
			    	isSuccess = 0;
			    	System.out.println("调用子系统接口结果end="+e.getMessage());
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
			}*/
						
		
			SmsUtil.sendZhuceXXSms(user.getMobile(), String.valueOf(info.getSysOrgInfoId()), "为原密码", sysUser.getAccount());
			ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
		} catch (Exception e) {
			return mv.addObject("info", info)
					.addObject("pwd", "123456")
					.addObject("account",sysUser.getAccount())
					.addObject("mobile", user.getMobile())
					.addObject("resultMsg", resultMsg);
		}
		return mv.addObject("info", info)
				.addObject("pwd", "123456")
				.addObject("account",sysUser.getAccount())
				.addObject("mobile", user.getMobile())
				.addObject("resultMsg", resultMsg);
	}
	
	public static boolean isEmpty(Object o) {
		if (o == null)
			return true;
		if (o instanceof String) {
			if (((String) o).trim().length() == 0) {
				return true;
			}
		} else if (o instanceof Collection) {
			if (((Collection) o).isEmpty()) {
				return true;
			}
		} else if (o.getClass().isArray()) {
			if (((Object[]) (Object[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).isEmpty()) {
				return true;
			}
		} else if (o instanceof Long) {
			if ((Long) o == null) {
				return true;
			}
		} else if (o instanceof Short) {
			if ((Short) o == null) {
				return true;
			}

		} else {
			return false;
		}
		return false;
	}

	public static boolean isNotEmpty(Object o) {
		return (!(isEmpty(o)));
	}
}
