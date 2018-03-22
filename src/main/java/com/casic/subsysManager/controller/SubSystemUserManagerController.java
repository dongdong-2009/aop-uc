     package com.casic.subsysManager.controller;
	import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.msgLog.model.SysMsgLog;
import com.casic.msgLog.service.SysMsgLogService;
import com.casic.platform.saas.userRole.SaasUserRole;
import com.casic.platform.saas.userRole.SaasUserRoleService;
import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.TenantInfoService;
import com.casic.user.controller.JMSRunableThread;
import com.casic.util.HttpClientUtil;
import com.casic.util.OpenIdUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.ICache;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.model.OnlineUser;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.controller.system.SysRoleController;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.UserPositionDao;
import com.hotent.platform.dao.system.UserRoleDao;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.SysUserParam;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.RoleResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserParamService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

import net.sf.json.JSONObject;



	/***
	 * 子系统账号管理类，编辑、删除以及子系角色的查询 修改、删除
	 * @author wangshumin
	 * 2017 10 17
	 */
	@Controller
	@RequestMapping("/platform/subSystem/sysUser/")
	public class SubSystemUserManagerController extends BaseController {
		 static  Logger  log =  Logger.getLogger(SubSystemUserManagerController.class.getName());
		
		 
		@Resource
		private SysMsgLogService sysMsgLogService;
		@Resource
		private JmsTemplate jmsTemplate;
		@Resource
		private ICache iCache; 
		@Resource
		private SysUserService sysUserService;
		@Resource
		private SysRoleService sysRoleService;
		@Resource
		private UserRoleDao userRoleDao;
		
		@Resource
		private DemensionService demensionService;
		@Resource
		private SubSystemService subSystemService;
		@Resource
		private SysUserParamService sysUserParamService;
		@Resource
		private SysUserOrgService sysUserOrgService;
		@Resource
		private UserRoleService userRoleService;
		@Resource
		private UserPositionDao userPositionDao;
		@Resource
		private SysOrgDao sysOrgDao;
		@Resource
		private IAuthenticate iAuthenticate;	
		@Resource
		Properties configproperties;
		private final String defaultUserImage = "commons/image/default_image_male.jpg";

		@Resource
		private SaasUserRoleService saasUserRoleService;
		
		@Resource
		private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
		
		
		@Resource
		private TenantInfoService tenantInfoService;
		
		/**
		 * 取得用户表分页列表
		 * 
		 * @param request
		 * @param response
		 * @param page
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("list")
		@Action(description = "查看用户表分页列表",operateType="用户操作")
		public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Long userId = RequestUtil.getLong(request, "userId");
			ModelAndView mv = new ModelAndView("/subsystem/subsystemUserManagement.jsp");
		/*	Long usersystemId = RequestUtil.getLong(request, "usersystemId",0L);
			long longValue = usersystemId.longValue();
	        if(longValue==0L){
	    	    QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
				queryFilter.addFilter("fromSysId", usersystemId);
				List<ISysUser> list = sysUserService.getUserByQuery(queryFilter);
				mv.addObject("sysUserList", list);
			}else{
				//查询子系统下面的所有企业下的用户
				//List<ISysUser> listUser=	sysUserService.getUserBySystemId(usersystemId);
				QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
 				queryFilter.addFilter("SystemId", usersystemId);
				List<ISysUser> listUser = sysUserService.getUserBySystemId(queryFilter);
				mv.addObject("sysUserList", listUser);
			}*/
	        QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
			//queryFilter.addFilter("fromSysId", usersystemId);
			List<ISysUser> list = sysUserService.getUserByQuery(queryFilter);
			mv.addObject("sysUserList", list);
            
	        mv.addObject("userId", userId);

			return mv;
		}

		/**
		 * 删除用户表
		 * 
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping("del")
		@Action(description = "删除用户表")
		public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ResultMessage message = null;
			String preUrl = RequestUtil.getPrePage(request);
			try {
				Long[] lAryId = RequestUtil.getLongAryByStr(request, "userId");
				//sysUserService.delByIds(lAryId);
				
				ISysUser byId = sysUserService.getById(lAryId[0]);
				sysUserService.delById(lAryId[0]);
			    JSONObject json=new JSONObject();
				Map<String,Object> OperationMarked = new HashMap<String,Object>();
				OperationMarked.put("操作说明","修改用户：1,删除用户：3");
				OperationMarked.put("operationSatus", 3);//操作标示
				json.put("OperationMarked", OperationMarked);//操作标示：分配角色：1,删除角色：3
				json.put("userId", lAryId[0]);
				new JMSRunableThread("user_del",json.toString());
				message = new ResultMessage(ResultMessage.Success, "删除用户成功");
			} catch (Exception e) {
				message = new ResultMessage(ResultMessage.Fail, "删除用户失败");
			}
			addMessage(message, request);
			response.sendRedirect(preUrl);
		}

		@RequestMapping("edit")
		@Action(description = "编辑用户表")
		public ModelAndView edit(HttpServletRequest request) throws Exception {
			long fromSysId = RequestUtil.getLong(request, "fromSysId",0L);
		
			/*QueryFilter filter = new QueryFilter(request, false);
			if(fromSysId!=0L){
				filter.addFilter("systemId", fromSysId);
			}
			 // List<UserRole> all = userRoleService.getAll(filter);
			*/
			ModelAndView mv= new ModelAndView("/subsystem/sysUserEdit.jsp");
			mv.addObject("action", "global");
			long userId = RequestUtil.getLong(request, "userId");
			SysUser sysUser = null;
			if (userId != 0) {
				sysUser = (SysUser) sysUserService.getById(userId);
			}
			//ISysUser iSysUser = sysUserService.getById(userId);
			//Long orgsn=(iSysUser.getOrgSn()!=null&& iSysUser.getOrgSn()!=0)?iSysUser.getOrgSn():iSysUser.getOrgId();
			Long orgsn=(sysUser.getOrgSn()!=null&& sysUser.getOrgSn()!=0)?sysUser.getOrgSn():sysUser.getOrgId();
			mv.addObject("orgId", sysUser.getOrgId());
			mv.addObject("orgsn", orgsn);
			mv.addObject("fromSysId", fromSysId);
			mv.addObject("userId", userId);
			List<UserRole> roleList = userRoleService.getByUserId(userId);
			List<Demension>demensionList=demensionService.getAll();
			String returnUrl=RequestUtil.getPrePage(request);
			return getEditMv(request,mv).addObject("returnUrl", returnUrl).addObject("demensionList", demensionList).addObject("roleList", roleList);
		}
		@RequestMapping("editSubManager")
		@Action(description = "编辑")
		public ModelAndView editSubManager(HttpServletRequest request,HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView();
			Long id = RequestUtil.getLong(request, "userId");
			String returnUrl = RequestUtil.getPrePage(request);
			ISysUser sysUser = null;
			Boolean flag=false;
			if (id != 0) {
				sysUser = sysUserService.getById(id);
				if (sysUser.getFromSysId()!=null&&"100".equals(sysUser.getOrgSn().toString())) {
					flag=true;//系统管理员修改
					List<SubSystem> list = subSystemService.getActiveSystem();
					mv.addObject("subsystemList", list);
				}
			} else {
				sysUser = new SysUser();
				/*flag=true;//系统管理员增加
				List<SubSystem> list = subSystemService.getActiveSystem();
				mv.addObject("subsystemList", list);*/
			}
			mv.addObject("systemId", sysUser.getFromSysId());
			mv.addObject("flag",flag);//用来区别子系统管理员的和普通用户得编辑的，现在没有使用统一使用Boolean flag=false;
			mv.setViewName("/subsystem/subManager.jsp");
			return 	mv.addObject("sysUser", sysUser).addObject("returnUrl", returnUrl);

			
		}
/*		@RequestMapping("editSubManager")
		@Action(description = "编辑")
		public ModelAndView editSubManager(HttpServletRequest request,HttpServletResponse response) throws Exception {
			ModelAndView mv = new ModelAndView();
			Long id = RequestUtil.getLong(request, "userId");
			String returnUrl = RequestUtil.getPrePage(request);
			ISysUser sysUser = null;
			if (id != 0) {
				sysUser = sysUserService.getById(id);
				if (sysUser.getFromSysId()==null) {
					String resultMsg1 = getText("友情提示", "该用户不是子系统管理员！");
					writeResultMessage(response.getWriter(), resultMsg1, ResultMessage.Fail);
					Long orgsn=(sysUser.getOrgSn()!=null&& sysUser.getOrgSn()!=0)?sysUser.getOrgSn():sysUser.getOrgId();
					mv.addObject("orgId", sysUser.getOrgId());
					mv.addObject("orgsn", orgsn);
					List<UserRole> roleList = userRoleService.getByUserId(id);
					List<Demension>demensionList=demensionService.getAll();
					mv.setViewName("/subsystem/sysUserEdit.jsp");
					return  getEditMv(request,mv).addObject("returnUrl", returnUrl).addObject("demensionList", demensionList).addObject("roleList", roleList);
				}else{
					
					mv.setViewName("/subsystem/subManager.jsp");
					
				}
				
			} else {
				sysUser = new SysUser();
				mv.setViewName("/subsystem/subManager.jsp");
			}
			List<SubSystem> list = subSystemService.getActiveSystem();
			return 	mv.addObject("sysUser", sysUser).addObject("returnUrl", returnUrl).addObject("subsystemList", list).addObject("systemId", sysUser.getFromSysId());
			
			
		}
*/         
		
		@RequestMapping("saveSubManager")
		public void saveSubManager(HttpServletRequest request, HttpServletResponse response, final SysUser sysUser,
				BindingResult bindResult) throws Exception {
			ResultMessage resultMessage = validForm("sysUser", sysUser, bindResult, request);
			ISysUser currentUser = ContextUtil.getCurrentUser();
			String resultMsg = null;
			Long[] aryOrgId = new Long[1];// 组织Id数组
			if (currentUser != null) {
				aryOrgId[0] = currentUser.getOrgId();
			} else {
				aryOrgId[0] = (Long) iCache.getByKey("orgInfoId");
			}
			Long[] aryOrgCharge = new Long[1];
			aryOrgCharge[0] = SysUserOrg.CHARRGE_NO.longValue();
			if (sysUser.getUserId() == null) {
				boolean isExist = sysUserService.isAccountExist(sysUser.getAccount());
				if (isExist) {
					resultMsg = getText("子系统管理员添加失败", "该子系统管理员已经存在！");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				}
				Long userId = UniqueIdUtil.genId();

				String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());
				sysUser.setPassword(enPassword);
				sysUser.setUserId(userId);
				sysUser.setOrgId(currentUser.getOrgId());
				sysUser.setOrgSn(currentUser.getOrgId());
				sysUser.setCreatetime(new Date());
				/*sysUser.setIsLock((short) 0);
				sysUser.setIsExpired((short) 0);
				sysUser.setStatus((short) 1);*/
				sysUser.setOpenId(OpenIdUtil.getOpenId());
				sysUserService.add(sysUser);
				// 添加用户和组织关系。
				sysUserOrgService.saveUserOrg(userId, aryOrgId, 1l, aryOrgCharge);
				resultMsg = getText("恭喜您，您创建的用户已成功！", "用户表");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			    JSONObject json = new JSONObject();
			    JSONObject userJson = json.fromObject(sysUser);
				new JMSRunableThread("user_add",userJson.toString());
			/*	for (SubSystem subSystem : all) {
					jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							MapMessage ms1=session.createMapMessage();
							ms1.setString("user_add",userJson.toString());
							return ms1;
						}
					});
					SysMsgLog msgLog = new SysMsgLog();
					msgLog.setId(UniqueIdUtil.genId());
					msgLog.setOperation("用户添加");
					msgLog.setReceiverersonal(subSystem.getSystemId()+"");
					msgLog.setSendcontent(userJson.toString());
					sysMsgLogService.add(msgLog);
				}*/
		     } else {
				boolean isExist = sysUserService.isAccountExistForUpd(sysUser.getUserId(), sysUser.getAccount());
				if (isExist) {
					resultMsg = getText("账号添加失败", "该账号已经存在！");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				}
				SysUser user = (SysUser) sysUserService.getById(sysUser.getUserId());
				if (!StringUtil.isEmpty(sysUser.getPassword())) {
					String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());
					sysUser.setPassword(enPassword);
				} else {
					sysUser.setPassword(user.getPassword());
				}

				sysUser.setOrgId(user.getOrgId());
				sysUser.setOrgSn(user.getOrgSn());
				sysUser.setCreatetime(user.getCreatetime());
			/*	sysUser.setIsLock(user.getIsLock());
				sysUser.setIsExpired(user.getIsExpired());
				sysUser.setStatus(user.getStatus());*/
				sysUser.setOpenId(user.getOpenId());
				sysUserService.update(sysUser);
				com.casic.util.MessageUtil.sendSysTopic("updateUser", sysUser);
				// 添加用户和组织关系。
				sysUserOrgService.saveUserOrg(user.getUserId(), new Long[] { user.getOrgId() }, 1l, aryOrgCharge);
				resultMsg = getText("用户更新成功", "用户表");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
				// 调用子系统接口
				List<SubSystem> all = subSystemService.getAll();
				JSONObject json = new JSONObject();
				final JSONObject userJson = json.fromObject(sysUser);
				new JMSRunableThread("user_update",userJson.toString());
		/*		for (SubSystem subSystem : all) {
					jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							MapMessage ms1=session.createMapMessage();
							ms1.setString("user_update",userJson.toString());
							return ms1;
						}
					});
					SysMsgLog msgLog = new SysMsgLog();
					msgLog.setId(UniqueIdUtil.genId());
					msgLog.setOperation("用户修改");
					msgLog.setReceiverersonal(subSystem.getSystemId()+"");
					msgLog.setSendcontent(userJson.toString());
					sysMsgLogService.add(msgLog);
				} */
				} 
				/*try {
					// 遍历所有接口地址，将数据同步到各子系统
					String result = "";
					int i = 2;
					List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "user");
					for (InterfaceUrlBean urlBean : urls) {
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Object> data = new HashMap<String, Object>();
						// 子系统标识
						long systemId = urlBean.getSubId();
						data.put("sysUser", sysUser);
						// data.put("aptitudes", aptitudes);
						params.put("systemId", systemId);
						params.put("data", data);
						result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() + urlBean.getUrl(), params);
						System.out.println(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}

	/*	@RequestMapping("userEdit")
		@Action(description = "编辑用户")
		public ModelAndView useredit(HttpServletRequest request,SysUser user) throws Exception {
			Long[] roleIds = RequestUtil.getLongAry(request, "roleId");
			Long[] orgIds = RequestUtil.getLongAry(request, "orgIds");
			ModelAndView mv= new ModelAndView("/subsystem/sysUserEdit.jsp");
			long userId = RequestUtil.getLong(request, "userId");
			List<UserRole> roleList = userRoleService.getByUserId(userId);
			ResultMessage message = null;
			try {
				sysUserService.update(user);
				userRoleService.saveUserRole(user.getUserId(), roleIds);
				message = new ResultMessage(ResultMessage.Success, "保存成功");
			} catch (Exception e) {
				message = new ResultMessage(ResultMessage.Fail, "保存失败");
			}
			addMessage(message, request);
			String msg = message.toString();
			mv.addObject("message1",msg);
			mv.addObject("msg",msg);
			mv.addObject("action", "global");
			List<Demension>demensionList=demensionService.getAll();
			String returnUrl=RequestUtil.getPrePage(request);
			return getEditMv(request,mv).addObject("demensionList", demensionList).addObject("roleList", roleList).addObject("returnUrl", "/platform/subSystem/sysUser/list.ht").addObject("sysUser", user);
			return mv;
		}
		*/
		@RequestMapping("userRoleEdit")
		@ResponseBody
		@Action(description = "编辑分配角色")

		public void  useredit(HttpServletRequest request,HttpServletResponse response,SysUser user) throws Exception {
			final Long[] roleIds = RequestUtil.getLongAry(request, "roleId");
			Long orgId=RequestUtil.getLong(request, "orgSn");
			final SysUser sysUser = (com.hotent.platform.model.system.SysUser) sysUserService.getById(user.getUserId());
			/***********************************************************/
			String systemId="100";
			String openId="" ;
			TenantInfo info = tenantInfoService.getById(orgId);
			if(info!=null){
				systemId = info.getSystemId();//企业属于哪个子系统
				openId = info.getOpenId();
			}
			
			/***********************************************************/
			/*SysUser.setSex(user.getSex());
			SysUser.setIsLock((short) user.getIsLock());
			SysUser.setIsExpired((short) user.getIsExpired());
			SysUser.setStatus((short) user.getStatus());
			SysUser.setAccount(user.getAccount());
			SysUser.setEmail(user.getEmail());
			SysUser.setFullname(user.getFullname());
			SysUser.setMobile(user.getMobile());
			SysUser.setPhone(user.getPhone());*/
			
			//用户角色分配子系统的集合
			Map<Long,Object> mapsystemIdList =  new HashMap<Long,Object>();
			//Map<Long,Long> maproleidsystemIdList =  new HashMap<Long,Long>();
			String status="";
			int i=1;
			String resultMsg;
			List<SubSystem> systemIdsList= new ArrayList<SubSystem>();
			if(roleIds!=null){
				for (Long roleId : roleIds) {
					ISysRole isysrole = sysRoleService.getById(roleId);
					//maproleidsystemIdList.put(roleId, isysrole.getSystemId());
					
					SubSystem subSystem = subSystemService.getById(isysrole.getSystemId());
					
					Object object = mapsystemIdList.get(subSystem.getSystemId());
					if(object==null){
						mapsystemIdList.put(subSystem.getSystemId(),subSystem);
					    systemIdsList.add(subSystem);
					}
				}
				
				
			}else{
				userRoleDao.delByUserId(user.getUserId());
				resultMsg="保存成功";
				status = "200";
			}
			try {
				sysUserService.update(sysUser);
				userRoleService.saveUserRole(sysUser.getUserId(), roleIds);
				final List<UserRole> userrole = userRoleService.getByUserId(sysUser.getUserId());
				final Long userId = sysUser.getUserId();
				//log.setLevel(Level.DEBUG);
				//List<SubSystem> all = subSystemService.getAll();
				JSONObject json=new JSONObject();
				JSONObject json2=new JSONObject();
				Map<String,Object> OperationMarked = new HashMap<String,Object>();
				OperationMarked.put("操作说明","分配角色：1,删除角色：3");
				OperationMarked.put("operationSatus", 1);//操作标示
				json.put("OperationMarked", OperationMarked);//操作标示：分配角色：1,删除角色：3
				json.put("userId", userId);
				json.put("sysRoleIdList", roleIds); 
				json.put("userrole", userrole);
				json.put("systemIdsList", systemIdsList);
			    JSONObject userJson = json2.fromObject(sysUser);
				new JMSRunableThread("user_update",userJson.toString());
				new JMSRunableThread("userRole_update",json.toString(),systemIdsList);
			/*	for (final SubSystem subSystem : all) {
					jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public MapMessage createMessage(Session session) throws JMSException {
							MapMessage mapMsg=session.createMapMessage();
						    mapMsg.setString("user_update",userJson.toString());
						  if(log.isDebugEnabled()){
							    log.debug("debug -----操作标示：分配角色：1,删除角色：3：{ }-------"+json.toString()+"}"); 
						  }
						    mapMsg.setString("userRole_update",json.toString());
						    mapMsg.setStringProperty("userRole_update_Property", "1");//分配角色：1
							return mapMsg;
						}
					});
					
					SysMsgLog msgLog = new SysMsgLog();
					msgLog.setId(UniqueIdUtil.genId());
					msgLog.setOperation("用户修改+分配角色");
					msgLog.setReceiverersonal(subSystem.getSystemId()+"");
					msgLog.setSendcontent(userJson.toString()+"--------------"+json.toString());
					sysMsgLogService.add(msgLog);
				}*/
				resultMsg="保存成功";
				status = "200";
			} catch (Exception e) {
				e.printStackTrace();
				 resultMsg="保存失败";
			}
			String result="";
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassifyWithSys(i,"userRole",systemId+"");
			for(InterfaceUrlBean urlBean:urls){
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("openId", openId);
				data.put("roleIds", roleIds);
				//data.put("aptitudes", aptitudes);
				params.put("systemId", systemId);
				params.put("data", data);
				result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
				System.out.print(urlBean.getSubSystemName()+"返回的结果是"+result);
				JSONObject jsonObject = JSONObject.fromObject(result);
				status = jsonObject.getString("status");
				String msg = jsonObject.getString("msg");
				resultMsg = msg;
				
			}
			if(!"200".equals(status)){
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			}
			else{
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
			
		}
	/*	@RequestMapping("userSave")
		@Action(description = "保存用户")
		public ModelAndView userSave(HttpServletRequest request,SysUser user) throws Exception {
			String preUrl = RequestUtil.getPrePage(request);
			
			//ModelAndView mv= new ModelAndView("redirect:/platform/subSystem/sysUser/list.ht");调到list页面
			
			user.setUserId(UniqueIdUtil.genId());
			request.setAttribute("userId", UniqueIdUtil.genId());
			ResultMessage message = null;
			try {
				sysUserService.add(user);		
				message = new ResultMessage(ResultMessage.Success, "保存用户成功");
				
			} catch (Exception e) {
				message = new ResultMessage(ResultMessage.Fail, "保存用户失败");
			}
			SysUser sysUser = (SysUser) sysUserService.getById(user.getUserId());
			addMessage(message, request);
			List<Demension>demensionList=demensionService.getAll();
			ModelAndView mv= new ModelAndView("/subsystem/sysUserSave.jsp").addObject("sysUser", sysUser);
			mv.addObject("action", "global");
			//String returnUrl=RequestUtil.getPrePage(request);addObject("returnUrl", returnUrl).
			return getEditMv(request,mv).addObject("demensionList", demensionList);
		}
		
		@RequestMapping("save")
		@Action(description = "保存用户")
		public ModelAndView save(HttpServletRequest request,SysUser user) throws Exception {
			
			ModelAndView mv= new ModelAndView("/subsystem/sysUserSave.jsp");
			mv.addObject("action", "global");
			List<Demension>demensionList=demensionService.getAll();
			String returnUrl=RequestUtil.getPrePage(request);
			return getEditMv(request,mv).addObject("returnUrl", returnUrl).addObject("demensionList", demensionList);
		}
		*/
		@RequestMapping("editGrade")
		@Action(description = "编辑用户表")
		public ModelAndView editGrade(HttpServletRequest request) throws Exception {
			ModelAndView mv= new ModelAndView();
			mv.setViewName("/subsystem/sysUserEdit.jsp");
			mv.addObject("action", "grade");
			return getEditMv(request,mv);
		}
		
		public ModelAndView getEditMv(HttpServletRequest request,ModelAndView mv){
 			Long orgId=RequestUtil.getLong(request, "orgId");
 			String returnUrl = RequestUtil.getPrePage(request);
			Long userId = RequestUtil.getLong(request, "userId");
			int bySelf = RequestUtil.getInt(request, "bySelf");
			
			ISysUser sysUser = null;
			if (userId != 0) {
				sysUser = sysUserService.getById(userId);
				List<UserRole> roleList = userRoleService.getByUserIdAndTenantId(userId, ContextUtil.getCurrentTenantId());
				List<UserPosition> posList = userPositionDao.getByUserId(userId);
				List<SysUserOrg> orgList = sysUserOrgService.getOrgByUserId(userId);
				mv.addObject("roleList", roleList).addObject("posList", posList).addObject("orgList", orgList);
			} else {
				sysUser = iAuthenticate.getNewSysUser();
				if(orgId>0){
					List<SysUserOrg> orgList=new ArrayList<SysUserOrg>();
					SysUserOrg userOrg=new SysUserOrg();
					ISysOrg sysOrg=  sysOrgDao.getById(orgId);
					userOrg.setOrgId(orgId);
					userOrg.setOrgName(sysOrg.getOrgName());
					userOrg.setIsPrimary(SysUserOrg.PRIMARY_YES);
					userOrg.setIsCharge(SysUserOrg.CHARRGE_NO);
					orgList.add(userOrg);
					mv.addObject("orgList", orgList);
				}
			}
			String pictureLoad = defaultUserImage;
			if (sysUser != null) {
				if (StringUtil.isNotEmpty(sysUser.getPicture())) {
					pictureLoad = sysUser.getPicture();
				}
			}
  			return mv.addObject("sysUser", sysUser)
					.addObject("userId", userId)
					.addObject("returnUrl", returnUrl)
					.addObject("pictureLoad", pictureLoad)
					.addObject("bySelf",bySelf);
		}

		@RequestMapping("modifyPwdView")
		@Action(description = "修改密码")
		public ModelAndView modifyPwdView(HttpServletRequest request, HttpServletResponse response) throws Exception {
			long userId = RequestUtil.getLong(request, "userId",ContextUtil.getCurrentUserId());
			ISysUser sysUser = sysUserService.getById(userId);
			return getAutoView().addObject("sysUser", sysUser).addObject("userId",userId);
		}
		
		@RequestMapping("modifyPwd")
		@Action(description = "修改密码")
		public void modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String primitivePassword =  RequestUtil.getString(request, "primitivePassword");
			String enPassword=EncryptUtil.encryptSha256(primitivePassword);
			String newPassword = RequestUtil.getString(request, "newPassword");
			Long userId = RequestUtil.getLong(request, "userId");
			ISysUser sysUser = sysUserService.getById(userId);
			String password=sysUser.getPassword();
			if(StringUtil.isEmpty( newPassword)||StringUtil.isEmpty( primitivePassword)){
				writeResultMessage(response.getWriter(), "输入的密码不能为空", ResultMessage.Fail);	
			}
			else if(!enPassword.equals(password)){
				writeResultMessage(response.getWriter(), "你输入的原始密码不正确", ResultMessage.Fail);		
			}

			else if(primitivePassword.equals(newPassword)){
				writeResultMessage(response.getWriter(), "你修改的密码和原始密码相同", ResultMessage.Fail);
			}
			else{
				try {
					sysUserService.updPwd(userId, newPassword);
					writeResultMessage(response.getWriter(),"修改密码成功", ResultMessage.Success);
				} catch (Exception ex) {
					String str = MessageUtil.getMessage();
					if (StringUtil.isNotEmpty(str)) {
						ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"修改密码失败:" + str);
						response.getWriter().print(resultMessage);
					} else {
						String message = ExceptionUtil.getExceptionMessage(ex);
						ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
						response.getWriter().print(resultMessage);
					}
				}
			}
		}

		
		@RequestMapping("resetPwdView")
		@Action(description = "重置密码")
		public ModelAndView resetPwdView(HttpServletRequest request) throws Exception {
			String returnUrl = RequestUtil.getPrePage(request);
			Long userId = RequestUtil.getLong(request, "userId");
			ISysUser sysUser = sysUserService.getById(userId);
			return getAutoView().addObject("sysUser", sysUser).addObject("userId", userId).addObject("returnUrl", returnUrl);
		}

		@RequestMapping("resetPwd")
		@Action(description = "重置密码")
		public void resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String password = RequestUtil.getString(request, "password");
			Long userId = RequestUtil.getLong(request, "userId");
			try {
				sysUserService.updPwd(userId, password);
				writeResultMessage(response.getWriter(), "重置密码成功!", ResultMessage.Success);
			} catch (Exception ex) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"重置密码失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}

		@RequestMapping("editStatusView")
		@Action(description = "设置用户状态")
		public ModelAndView editStatusView(HttpServletRequest request) throws Exception {
			String returnUrl = RequestUtil.getPrePage(request);
			Long userId = RequestUtil.getLong(request, "userId");
			ISysUser sysUser = sysUserService.getById(userId);
			return getAutoView().addObject("sysUser", sysUser).addObject("userId", userId).addObject("returnUrl", returnUrl);
		}

		@RequestMapping("editStatus")
		@Action(description = "设置用户状态")
		public void editStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Long userId = RequestUtil.getLong(request, "userId");
			int isLock = RequestUtil.getInt(request, "isLock");
			int status = RequestUtil.getInt(request, "status");
			try {
				sysUserService.updStatus(userId, (short) status, (short) isLock);
				writeResultMessage(response.getWriter(), "修改用户状态成功!", ResultMessage.Success);
			} catch (Exception ex) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"修改用户状态失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}

		/**
		 * 取得用户表明细
		 * 
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("get")
		@Action(description = "查看用户表明细")
		public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
			long userId = RequestUtil.getLong(request, "userId");
			String canReturn=RequestUtil.getString(request, "canReturn");
			ISysUser sysUser = sysUserService.getById(userId);
			String pictureLoad = defaultUserImage;
			if (sysUser != null) {
				if (StringUtil.isNotEmpty(sysUser.getPicture())) {
					pictureLoad = sysUser.getPicture();
				}
			}
			//多租户角色,ziapple,2015-04-20
			List<UserRole> roleList = new ArrayList<UserRole>();
		/*	List<SaasUserRole> saasRoleList = saasUserRoleService.getByUserIdAndTenantId(userId, ContextUtil.getCurrentTenantId());
			if(!BeanUtils.isEmpty(saasRoleList)){
				for(SaasUserRole saasUserRole : saasRoleList){
					roleList.add(saasUserRole.toUserRole());
				}
			}else{*/
				roleList = userRoleService.getByUserId(userId);
			/*}*/
//			List<UserRole> roleList = userRoleService.getByUserId(userId);
			List<UserPosition> posList = userPositionDao.getByUserId(userId);
			List<SysUserOrg> orgList = sysUserOrgService.getOrgByUserId(userId);

			List<SysUserParam> userParamList = sysUserParamService.getByUserId(userId);

			return new 	ModelAndView("/subsystem/sysUserGet.jsp").addObject("sysUser", sysUser).addObject("roleList", roleList).addObject("posList", posList).addObject("orgList", orgList)
					.addObject("pictureLoad", pictureLoad).addObject("userParamList", userParamList).addObject("canReturn",canReturn);
		}

		/**
		 * 取得用户表明细
		 * 
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("getMyself")
		@Action(description = "查看用户表明细")
		public ModelAndView getMyself(HttpServletRequest request, HttpServletResponse response) throws Exception {
			long userId = ContextUtil.getCurrentUserId();
			String canReturn=RequestUtil.getString(request, "canReturn");
			ISysUser sysUser = sysUserService.getById(userId);
			String pictureLoad = defaultUserImage;
			if (sysUser != null) {
				if (StringUtil.isNotEmpty(sysUser.getPicture())) {
					pictureLoad = sysUser.getPicture();
				}
			}
			//多租户角色,ziapple,2015-04-20
			List<UserRole> roleList = new ArrayList<UserRole>();
			List<SaasUserRole> saasRoleList = saasUserRoleService.getByUserIdAndTenantId(userId, ContextUtil.getCurrentTenantId());
			if(!BeanUtils.isEmpty(saasRoleList)){
				for(SaasUserRole saasUserRole : saasRoleList){
					roleList.add(saasUserRole.toUserRole());
				}
			}else{
				roleList = userRoleService.getByUserId(userId);
			}
					
//			List<UserRole> roleList = userRoleService.getByUserId(userId);
			List<UserPosition> posList = userPositionDao.getByUserId(userId);
			List<SysUserOrg> orgList = sysUserOrgService.getOrgByUserId(userId);

			List<SysUserParam> userParamList = sysUserParamService.getByUserId(userId);

			return getAutoView().addObject("sysUser", sysUser).addObject("roleList", roleList).addObject("posList", posList).addObject("orgList", orgList)
					.addObject("pictureLoad", pictureLoad).addObject("userParamList", userParamList).addObject("canReturn",canReturn);
		}
		
		/**
		 * 取得用户表分页列表
		 * 
		 * @param request
		 * @param response
		 * @param page
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("dialog")
		public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception {

			ModelAndView mv = this.getAutoView();

			List<Demension> demensionList = demensionService.getAll();
			mv.addObject("demensionList", demensionList);
			List<SubSystem> subSystemList = subSystemService.getAll();
			mv.addObject("subSystemList", subSystemList);

			String isSingle = RequestUtil.getString(request, "isSingle", "false");
			mv.addObject("isSingle", isSingle);
			
			return mv;
		}
		
		/**
		 * 取得用户表分页列表
		 * 
		 * @param request
		 * @param response
		 * @param page
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("companyDialog")
		public ModelAndView companyDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {

			ModelAndView mv = this.getAutoView();

			List<Demension> demensionList = demensionService.getAll();
			mv.addObject("demensionList", demensionList);
			List<SubSystem> subSystemList = subSystemService.getAll();
			mv.addObject("subSystemList", subSystemList);

			String isSingle = RequestUtil.getString(request, "isSingle", "false");
			mv.addObject("isSingle", isSingle);
			
			long companyId = RequestUtil.getLong(request, "companyId", 0);
			mv.addObject("companyId", companyId);
			
			return mv;
		}
		
		@RequestMapping("flowDialog")
		public ModelAndView flowDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			ModelAndView mv = this.getAutoView();
			List<Demension> demensionList = demensionService.getAll();
			mv.addObject("demensionList", demensionList);
			List<SubSystem> subSystemList = subSystemService.getAll();
			
			mv.addObject("isSingle", "false");
			mv.addObject("subSystemList", subSystemList);
			return mv;
		}

		@RequestMapping("selector")
		public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<ISysUser> list = null;
			ModelAndView result = getAutoView();
			String searchBy = RequestUtil.getString(request, "searchBy");
			QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
			
			/**
			 * modifyBy hotent.xianggang b 添加过滤条件
			 */
			ISysUser currentUser=ContextUtil.getCurrentUser();
			Long currentUserOrgId=currentUser.getOrgId();
			queryFilter.addFilter("orgId", currentUserOrgId);
			/**
			 * modifyBy hotent.xianggang e 
			 */
			
			if (SystemConst.SEARCH_BY_ONL.equals(searchBy)) {
				String demId = RequestUtil.getString(request, "path");
				if (demId.equals("-1")) {//未分配组织的用户
					list=sysUserService.getUserNoOrg(queryFilter);
				} else{
					list = sysUserService.getDistinctUserByOrgPath(queryFilter);
				}
				list = sysUserService.getOnlineUser(list);
			} else if (SystemConst.SEARCH_BY_ORG.equals(searchBy)) {
				list = sysUserService.getDistinctUserByOrgPath(queryFilter);
			} else if (SystemConst.SEARCH_BY_POS.equals(searchBy)) {
				list = sysUserService.getDistinctUserByPosPath(queryFilter);
			} else if (SystemConst.SEARCH_BY_ROL.equals(searchBy)) {
				list = sysUserService.getUserByRoleId(queryFilter);
			} else if (SystemConst.SEARCH_BY_ENTERPRIES.equals(searchBy)){//modifyBy hotent.xianggang
				list = sysUserService.getUserByEnterprise(queryFilter);//modifyBy hotent.xianggang
			}else {
				list = sysUserService.getUserByQuery(queryFilter);
			}
			result.addObject("sysUserList", list);

			String isSingle = RequestUtil.getString(request, "isSingle", "false");
			result.addObject("isSingle", isSingle);

			return result;
		}

		/**
		 * 企业用户选择器
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("selectorCompany")
		public ModelAndView selectorCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<ISysUser> list = null;
			ModelAndView result = getAutoView();
			String searchBy = RequestUtil.getString(request, "searchBy");
			QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
			
			/**
			 * modifyBy hotent.xianggang b 添加过滤条件
			 */
			ISysUser currentUser=ContextUtil.getCurrentUser();
			long companyId = RequestUtil.getLong(request, "companyId", 0);
			if(companyId == 0)
				companyId = currentUser.getOrgId();
			
			queryFilter.addFilter("orgId", companyId);
			/**
			 * modifyBy hotent.xianggang e 
			 */
			
			if (SystemConst.SEARCH_BY_ONL.equals(searchBy)) {
				String demId = RequestUtil.getString(request, "path");
				if (demId.equals("-1")) {//未分配组织的用户
					list=sysUserService.getUserNoOrg(queryFilter);
				} else{
					list = sysUserService.getDistinctUserByOrgPath(queryFilter);
				}
				list = sysUserService.getOnlineUser(list);
			} else if (SystemConst.SEARCH_BY_ORG.equals(searchBy)) {
				list = sysUserService.getDistinctUserByOrgPath(queryFilter);
			} else if (SystemConst.SEARCH_BY_POS.equals(searchBy)) {
				list = sysUserService.getDistinctUserByPosPath(queryFilter);
			} else if (SystemConst.SEARCH_BY_ROL.equals(searchBy)) {
				list = sysUserService.getUserByRoleId(queryFilter);
			} else if (SystemConst.SEARCH_BY_ENTERPRIES.equals(searchBy)){//modifyBy hotent.xianggang
				list = sysUserService.getUserByEnterprise(queryFilter);//modifyBy hotent.xianggang
			}else {
				list = sysUserService.getUserByQuery(queryFilter);
			}
			result.addObject("sysUserList", list);

			String isSingle = RequestUtil.getString(request, "isSingle", "false");
			result.addObject("isSingle", isSingle);
			result.addObject("companyId", companyId);

			return result;
		}
		
		/**
		 * 获取在线用户树
		 * 
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping("getTreeData")
		@ResponseBody
		public List<OnlineUser> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Map<Long, OnlineUser> onlineUsers = AppUtil.getOnlineUsers();
			List<OnlineUser> onlineList = new ArrayList<OnlineUser>();
			for (Long sessionId : onlineUsers.keySet()) {
				OnlineUser onlineUser = new OnlineUser();
				onlineUser = onlineUsers.get(sessionId);
				onlineList.add(onlineUser);
			}
			return onlineList;
		}
	}
