package com.hotent.platform.controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.msgLog.model.SysMsgLog;
import com.casic.msgLog.service.SysMsgLogService;
import com.casic.platform.saas.role.SaasRole;
import com.casic.platform.saas.role.SaasRoleService;
import com.casic.user.controller.JMSRunableThread;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.dao.system.SubSystemDao;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;
import com.hotent.platform.service.system.SysRoleService;

import net.sf.json.JSONObject;
import sun.java2d.pipe.AATextRenderer;

/**
 * 对象功能:系统角色表 控制器类 开发公司:北京航天软件有限公司 开发人员:csx 创建时间:2011-11-28 11:39:03
 */
@Controller
@RequestMapping("/platform/system/sysRole/")
public class SysRoleController extends BaseController {
	
    static  Logger  log =  Logger.getLogger(SysRoleController.class.getName());
    
    @Resource
    private SysMsgLogService sysMsgLogService;
    
	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private SaasRoleService saasRoleService;
	
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private IAuthenticate iAuthenticate;
    
	@Resource
	private SubSystemDao subSystemDao;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private JmsTemplate jmsTemplate;
	 
	@Resource(name="queueDestination")
	private Destination destination;
	/**
	 * 角色对话框的展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "sysRoleItem");
		queryFilter.getPageBean().setPagesize(10);
		List<ISysRole> list = sysRoleService.getAll(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("sysRoleList", list);
		return mv;
	}

	/**
	 * 取得系统角色表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看系统角色表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "sysRoleItem");
		
		long usersystemId = RequestUtil.getLong(request, "usersystemId",0L);
		if(usersystemId!=0L){
			filter.addFilter("systemId", usersystemId);
		}
		//filter.addFilter("systemId", SubSystemUtil.getCurrentSystemId());
		List<ISysRole> list = sysRoleService.getRoleList(filter);
		ModelAndView mv = this.getAutoView().addObject("sysRoleList", list);
		return mv;
	}

	/**
	 * 删除系统角色表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除系统角色表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			//Long[] lAryId = RequestUtil.getLongAryByStr(request, "roleId");
			//sysRoleService.delByIds(lAryId);
			long lAryId = RequestUtil.getLong(request, "roleId");
			ISysRole sysRole = sysRoleService.getById(lAryId);
			Long systemId = sysRole.getSystemId();
			sysRoleService.delById(lAryId);
		    JSONObject json=new JSONObject();
			Map<String,Object> OperationMarked = new HashMap<String,Object>();
			OperationMarked.put("操作说明","分配角色：1,删除角色：3");
			OperationMarked.put("operationSatus", 3);//操作标示
			json.put("OperationMarked", OperationMarked);//操作标示：分配角色：1,删除角色：3
			json.put("roleId", lAryId);
			json.put("systemId", systemId);
			new JMSRunableThread("role_del",json.toString());
			message = new ResultMessage(ResultMessage.Success, "删除系统角色成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除系统角色失败:" + e.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	
	
	/**
	 * 删除系统角色表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("dels")
	@Action(description = "删除批量角")
	public void dels(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "roleId");
			sysRoleService.delByIds(lAryId);
		/*	long lAryId = RequestUtil.getLong(request, "roleId");
			ISysRole sysRole = sysRoleService.getById(lAryId);
			Long systemId = sysRole.getSystemId();
			sysRoleService.delById(lAryId);
		    JSONObject json=new JSONObject();
			Map<String,Object> OperationMarked = new HashMap<String,Object>();
			OperationMarked.put("操作说明","分配角色：1,删除角色：3");
			OperationMarked.put("operationSatus", 3);//操作标示
			json.put("OperationMarked", OperationMarked);//操作标示：分配角色：1,删除角色：3
			json.put("roleId", lAryId);
			json.put("systemId", systemId);
			new JMSRunableThread("role_del",json.toString());*/
			message = new ResultMessage(ResultMessage.Success, "删除系统角色成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除系统角色失败:" + e.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	@RequestMapping("copy")
	@Action(description = "编辑系统角色表")
	public ModelAndView copy(HttpServletRequest request) throws Exception {
		Long roleId = RequestUtil.getLong(request, "roleId");

		ISysRole sysRole = sysRoleService.getById(roleId);
		Long systemId = sysRole.getSystemId();
		// 如果系统id不为空，则将角色别名系统前缀替换掉。
		if (systemId != null) {
			SubSystem subSystem = subSystemService.getById(systemId);
			String sysAlias = subSystem.getAlias();
			String roleAlias = sysRole.getAlias().replaceFirst(sysAlias + "_", "");
			sysRole.setAlias(roleAlias);
		}
		return getAutoView().addObject("sysRole", sysRole);
	}

	/**
	 * 编辑系统角色
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑系统角色表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long roleId = RequestUtil.getLong(request, "roleId");
		String returnUrl = RequestUtil.getPrePage(request);
		ISysRole sysRole = null;
		if (roleId != 0) {
			sysRole = sysRoleService.getById(roleId);
			Long systemId = sysRole.getSystemId();
			// 如果系统id不为空，则将角色别名系统前缀替换掉。
			if (systemId != null) {
				SubSystem subSystem = subSystemService.getById(systemId);
				String sysAlias = subSystem.getAlias();
				String roleAlias = sysRole.getAlias().replaceFirst(sysAlias + "_", "");
				sysRole.setAlias(roleAlias);
			}
		} else {
			sysRole = iAuthenticate.getNewSysRole();
		}
		List<SubSystem> list = subSystemService.getActiveSystem();
		return getAutoView().addObject("sysRole", sysRole).addObject("subsystemList", list).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得系统角色表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看系统角色表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "roleId");
		ISysRole sysRole = sysRoleService.getById(id);
		SubSystem subsystem = null;
		if (sysRole.getSystemId() != null) {
			subsystem = subSystemService.getById(sysRole.getSystemId());
		}

		return getAutoView().addObject("sysRole", sysRole).addObject("subsystem", subsystem);
	}

	/**
	 * 复制角色
	 * 
	 * @param request
	 * @param response
	 * @param po
	 * @throws Exception
	 */
	@RequestMapping("copyRole")
	@Action(description = "复制角色")
	public void copyRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		long roleId = RequestUtil.getLong(request, "roleId");

		String roleName = RequestUtil.getString(request, "newRoleName");
		String alias = RequestUtil.getString(request, "newAlias");
		long newRoleId = UniqueIdUtil.genId();

		ISysRole sysRole = sysRoleService.getById(roleId);
		Long systemId = sysRole.getSystemId();
		if (systemId != null) {
			SubSystem subSystem = subSystemService.getById(systemId);
			alias = subSystem.getAlias() + "_" + alias;
		}

		boolean rtn = sysRoleService.isExistRoleAlias(alias);
		if (rtn) {
			writeResultMessage(writer, "输入的别名已存在", ResultMessage.Fail);
			return;
		}

		ISysRole newRole = (ISysRole) sysRole.clone();
		newRole.setRoleId(newRoleId);
		newRole.setAlias(alias);
		newRole.setRoleName(roleName);

		try {
			sysRoleService.copyRole(newRole, roleId);
			writeResultMessage(writer, "复制角色成功!", ResultMessage.Success);
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"复制角色失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 获取角色树。
	 * 
	 * <pre>
	 * ISysRole 的systemId 
	 * 1. 0代表是子系统。
	 * 2. 1代表全局角色。
	 * 3. 其他为子系统的角色。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public List  getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		
		
		//子系统标识
		Long systemId = RequestUtil.getLong(request, "fromSysId", 0L);
		//获取当前登录用户对象
		SysUser user = (SysUser) ContextUtil.getCurrentUser();
		String account = user.getAccount();
		Long fromSysId = user.getFromSysId();
		List  treeList=new ArrayList();
		//【HT】注释
//		//全局   父节点 {0,0，全局角色,true}
//		rol=iAuthenticate.getNewSysRole();
//		rol.setRoleId(new Long(0));
//		rol.setSystemId(new Long(0));//相当于pid
//		rol.setRoleName("全局角色");
//		rol.setIsParent("true");//是否父节点,不加这个，无自己点的时候，父节点不是文件夹样式
//		treeList.add(rol);
//	
//		//全局   子节点
//		for(ISysRole sysRole:rolList){
//			//全局变量
//			if( sysRole.getSystemId()==null ||sysRole.getSystemId().equals("")){
//				sysRole.setSystemId(0L);
//				//{sysRole.getRoleId().toString(),"0",sysRole.getRoleName()}
//				treeList.add(sysRole);
//			}
//		}
		// 子系统集合
		List<Map<String,Object>> sublist1 =null;
		ArrayList<SubSystem> SubSystemList = new ArrayList<SubSystem>();
		ArrayList<SubSystem> sublist = new ArrayList<SubSystem>();
	/*	if("confadmin".contains(account)){//超级管理员
		if("confadmin".contains(account)){*/
		if(account!=null){
			if(account!=null){
			//循环子系统，{子系统角色id,"-1","子系统1"}	
		    //sublist = subSystemService.getActiveSystem();
			StringBuffer sqlroleSubSystem = new  StringBuffer();
			sqlroleSubSystem.append(" select sub.*  from  sys_subsystem  sub    ");
			//sqlroleSubSystem.append(" SELECT  DISTINCT sub.*  FROM  sys_role b , sys_subsystem sub  WHERE  sub.systemId = b.systemId   ");
		    sublist1 = jdbcTemplate.queryForList(sqlroleSubSystem.toString());
		    if(sublist1!=null&&sublist1.size()>0){
				for (Map<String, Object> m : sublist1) {
					SubSystem SubSystem = SubSystem.class.newInstance();
					SubSystem.setSystemId((Long.parseLong(m.get("systemId").toString())));
					SubSystem.setSysName(m.get("sysName").toString());
					SubSystemList.add(SubSystem);
				}
			}
		}/*else{
				SubSystem sub = subSystemService.getById(fromSysId);
					//sublist.add(sub);
				SubSystemList.add(sub);
		}*/
		
		
		//获取系统下的角色
		StringBuffer roleSubSystem = new  StringBuffer();
		/*roleSubSystem.append("  SELECT  DISTINCT  b.roleId,sub.systemId,sub.sysName,b.roleName  ");
		roleSubSystem.append("  FROM sys_user c,sys_user_role a,sys_role b, sys_org_info info, sys_subsystem sub WHERE   ");
		roleSubSystem.append("  a.userId  = c.userId   AND a.roleId = b.roleId  AND info.SYS_ORG_INFO_ID = c.orgId AND sub.systemId = b.systemId   ORDER BY sub.systemId  ASC   ");*/
		roleSubSystem.append(" select   b.roleId,sub.systemId,sub.sysName,b.roleName from  sys_role b ,  sys_subsystem sub where  sub.systemId = b.systemId    ORDER BY  sub.createtime DESC ");
		List<Map<String, Object>> rolList2 = jdbcTemplate.queryForList(roleSubSystem.toString());
		List<SysRole> rolList22 = new  ArrayList();
	    if(rolList2!=null&&rolList2.size()>0){
			for (Map<String, Object> m : rolList2) {
				SysRole SysRole = SysRole.class.newInstance();
				SysRole.setSystemId((Long.parseLong(m.get("systemId").toString())));
				SysRole.setRoleName(m.get("roleName").toString());
				SysRole.setRoleId((Long.parseLong(m.get("roleId").toString())));
				SysRole.setSystemName(m.get("sysName").toString());
				rolList22.add(SysRole);
			}
		}
		SysRole sysRole = null;
		Long i=-1L;
		for(SubSystem subSystem:SubSystemList){
			 i--;//子系统 角色 pid，-1,-2，-3，
			 sysRole = SysRole.class.newInstance();
			 sysRole.setRoleId(i);
			 sysRole.setSystemId(-9999999999L);//子系统pid
			 sysRole.setRoleName(subSystem.getSysName());
			 //子系统父节点
			 treeList.add(sysRole);
			//{String.valueOf(i),  "-9999999999",subSys.getSysName()}
			/**
			 * 如果租户有自己的角色体系，读取角色，否则读取平台角色
			 */
			Long tenantId = RequestUtil.getLong(request, "tenantId", 0);
			if(tenantId==0){
				tenantId = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
			}
			for(SysRole sysRole2:rolList22){//子系统添加 角色子节点
				if(sysRole2.getSystemId()!=null &&subSystem.getSystemId()==sysRole2.getSystemId()){
					sysRole2.setSystemId(i);
					//所有子系统的子节点
					treeList.add(sysRole2);
					//{String.valueOf(sysRole2.getSystemId()),  String.valueOf(i),sysRole2.getRoleName()}	
				}
			}
		}
     }else{////运营管理员的继承自己的子系统角色 但是现在没有这样做留着以后修改
    	 SubSystem sub = subSystemService.getById(fromSysId);
    	 SubSystemList.add(sub);
    	//获取系统下的角色
 		StringBuffer roleSubSystem = new  StringBuffer();
 		/*roleSubSystem.append("  SELECT  DISTINCT  b.roleId,sub.systemId,sub.sysName,b.roleName  ");
 		roleSubSystem.append("  FROM sys_user c,sys_user_role a,sys_role b, sys_org_info info, sys_subsystem sub WHERE   ");
 		roleSubSystem.append("  a.userId  = c.userId   AND a.roleId = b.roleId  AND info.SYS_ORG_INFO_ID = c.orgId AND sub.systemId = b.systemId   ORDER BY sub.systemId  ASC   ");*/
 		roleSubSystem.append(" select   b.roleId,sub.systemId,sub.sysName,b.roleName from  sys_role b ,  sys_subsystem sub where  sub.systemId = b.systemId    ORDER BY  sub.createtime DESC ");
 		List<Map<String, Object>> rolList2 = jdbcTemplate.queryForList(roleSubSystem.toString());
 		List<SysRole> rolList22 = new  ArrayList();
 	    if(rolList2!=null&&rolList2.size()>0){
 			for (Map<String, Object> m : rolList2) {
 				SysRole SysRole = SysRole.class.newInstance();
 				SysRole.setSystemId((Long.parseLong(m.get("systemId").toString())));
 				SysRole.setRoleName(m.get("roleName").toString());
 				SysRole.setRoleId((Long.parseLong(m.get("roleId").toString())));
 				SysRole.setSystemName(m.get("sysName").toString());
 				rolList22.add(SysRole);
 			}
 		}
 		SysRole sysRole = null;
 		Long i=-1L;
 		for(SubSystem subSystem:SubSystemList){
 			 i--;//子系统 角色 pid，-1,-2，-3，
 			 sysRole = SysRole.class.newInstance();
 			 sysRole.setRoleId(i);
 			 sysRole.setSystemId(-9999999999L);//子系统pid
 			 sysRole.setRoleName(subSystem.getSysName());
 			 //子系统父节点
 			 treeList.add(sysRole);
 			//{String.valueOf(i),  "-9999999999",subSys.getSysName()}
 			/**
 			 * 如果租户有自己的角色体系，读取角色，否则读取平台角色
 			 */
 			Long tenantId = RequestUtil.getLong(request, "tenantId", 0);
 			if(tenantId==0){
 				tenantId = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
 			}
 			for(SysRole sysRole2:rolList22){//子系统添加 角色子节点
 				if(sysRole2.getSystemId()!=null &&subSystem.getSystemId()==sysRole2.getSystemId()){
 					sysRole2.setSystemId(i);
 					//所有子系统的子节点
 					treeList.add(sysRole2);
 					//{String.valueOf(sysRole2.getSystemId()),  String.valueOf(i),sysRole2.getRoleName()}	
 				}
 			}
 		}
    	 
     
     
       }
		return treeList;
	}
	
	
	/**
	 * 获取子系统角色树。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeBySubSytem")
	@ResponseBody
	public List  getTreeBySubSytem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ISysRole> rolList =new ArrayList<ISysRole>();
		List<ISysRole> trolList=sysRoleService.getAll();		
		//子系统标识
		Long systemId = RequestUtil.getLong(request, "systemId", 0l);
		
		for(ISysRole role:trolList){
			if(role.getMemo()!=null && role.getMemo().equals("内置角色")){
				continue;
			}else{
				rolList.add(role);
			}
		}
		ISysRole rol = null;
		
		List  treeList=new ArrayList();
		// 子系统集合
		List<SubSystem> sublist = new ArrayList<SubSystem>();
		if(systemId.longValue() == 0l){
			//循环子系统，{子系统角色id,"-1","子系统1"}	
			sublist = subSystemService.getActiveSystem();
		}else{
			SubSystem sub = subSystemService.getById(systemId);
			sublist.add(sub);
		}
		
		Long i=-1L;
		for(SubSystem subSys:sublist){
			 i--;//子系统 角色 pid，-1,-2，-3，
			 rol=iAuthenticate.getNewSysRole();
			 rol.setRoleId(i);
			 rol.setSystemId(-9999999999L);//子系统pid
			 rol.setRoleName(subSys.getSysName());
			 //子系统父节点
			 treeList.add(rol);
			//{String.valueOf(i),  "-9999999999",subSys.getSysName()}
			/**
			 * 如果租户有自己的角色体系，读取角色，否则读取平台角色
			 */
			Long tenantId = RequestUtil.getLong(request, "tenantId", 0);
			if(tenantId==0){
				tenantId = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
			}
			List<SaasRole> saasRoles = saasRoleService.getByTenantId(tenantId);
			List<ISysRole> rolList2 = new ArrayList<ISysRole>();
			if(!saasRoles.isEmpty()){
				for(SaasRole saasRole : saasRoles){
					rolList2.add(saasRole.toSysRole());
				}
			}else{
				rolList2 = trolList;
			}
			for(ISysRole sysRole2:rolList2){//子系统添加 角色子节点
				if(sysRole2.getMemo()!=null && sysRole2.getMemo().equals("内置角色")){
						continue;
				}
				if(sysRole2.getSystemId()!=null &&subSys.getSystemId()==sysRole2.getSystemId()){
					sysRole2.setSystemId(i);
					//所有子系统的子节点
					treeList.add(sysRole2);
					//{String.valueOf(sysRole2.getSystemId()),  String.valueOf(i),sysRole2.getRoleName()}	
				}
			}
		}
		return treeList;
	}
	
	/**
	 * 获取子系统角色树
	 * 会将子系统角色和平台公共角色读取进来
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeByTenant")
	@ResponseBody
	public List  getTreeByTenant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 角色集
		List  treeList = new ArrayList();
		//子系统标识
		Long systemId = RequestUtil.getLong(request, "systemId", 0l);
		// 企业标识
		Long tenantId= RequestUtil.getLong(request, "tenantId", 0l);
		
		ISysRole rol = null;
		if(systemId.longValue() == 0l || tenantId.longValue() == 0l){
			rol = iAuthenticate.getNewSysRole();
			rol.setRoleId(-1l);
			rol.setSystemId(-1L);//子系统pid
			rol.setRoleName("平台未分配角色");
			//子系统父节点
			treeList.add(rol);
		}else{
			// 子系统和企业会员
			if(systemId.longValue() != 0l && tenantId.longValue() != 0l){
				SubSystem subSystem = subSystemService.getById(systemId);
				
				//构造根节点
				Long i = -1L;
				i--;//子系统 角色 pid，-1,-2，-3，
				rol=iAuthenticate.getNewSysRole();
				rol.setRoleId(i);
				rol.setSystemId(-9999999999L);//子系统pid
				rol.setRoleName(subSystem.getSysName());
				//子系统父节点
				treeList.add(rol);
				
				// 获取企业自身角色和平台公共角色
				List<SaasRole> saasRoles = saasRoleService.getByTenantIdAndPlatformPublicRole(tenantId);
				if(!saasRoles.isEmpty()){
					for(SaasRole saasRole : saasRoles){
						SysRole role = saasRole.toSysRole();
						role.setSystemId(i);
						role.setSubSystem(subSystem);
						treeList.add(role);
					}
				}else{
					//如果企业未分配角色则启用系统角色
					List<ISysRole> subRoles = sysRoleService.getInnerSysRole(systemId);
					if(!subRoles.isEmpty()){
						for(ISysRole role : subRoles){
							SysRole sysRole = (SysRole)role;
							sysRole.setSystemId(i);
							sysRole.setAlias(subSystem.getSysName());
							sysRole.setSubSystem(subSystem);
							treeList.add(sysRole);
						}
					}
				}
				
			}
		}
		return treeList;
	}
	
	/**
	 * 根据租户获取角色树
	 * <pre>
	 * ISysRole 的systemId 
	 * 1. 0代表是子系统。
	 * 2. 1代表全局角色。
	 * 3. 其他为子系统的角色。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeDataByTenant")
	@ResponseBody
	public List  getTreeDataByTenant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//List<ISysRole> rolList = sysRoleService.getRoleTree(new QueryFilter(request, "sysRole", false));
		List<ISysRole> rolList =new ArrayList<ISysRole>();
		List<ISysRole> trolList=sysRoleService.getAll();
		//子系统标识
		Long systemId = RequestUtil.getLong(request, "systemId", 0l);
		
		for(ISysRole role:trolList){
			//内置角色不显示
			if(role.getMemo()!=null && role.getMemo().equals("内置角色")){
				continue;
			}else if(role.getIsTenant()==1){//一级菜单不显示
				continue;
			}else{
				rolList.add(role);
			}
		}
		ISysRole rol = null;
		
		List  treeList=new ArrayList();
		//【HT】注释
//		//全局   父节点 {0,0，全局角色,true}
//		rol=iAuthenticate.getNewSysRole();
//		rol.setRoleId(new Long(0));
//		rol.setSystemId(new Long(0));//相当于pid
//		rol.setRoleName("全局角色");
//		rol.setIsParent("true");//是否父节点,不加这个，无自己点的时候，父节点不是文件夹样式
//		treeList.add(rol);
//	
//		//全局   子节点
//		for(ISysRole sysRole:rolList){
//			//全局变量
//			if( sysRole.getSystemId()==null ||sysRole.getSystemId().equals("")){
//				sysRole.setSystemId(0L);
//				//{sysRole.getRoleId().toString(),"0",sysRole.getRoleName()}
//				treeList.add(sysRole);
//			}
//		}
		
		// 子系统集合
		List<SubSystem> sublist = new ArrayList<SubSystem>();
		if(systemId.longValue() == 0l){
			//循环子系统，{子系统角色id,"-1","子系统1"}	
			sublist = subSystemService.getActiveSystem();
		}else{
			SubSystem sub = subSystemService.getById(systemId);
			sublist.add(sub);
		}
		
		Long i=-1L;
		for(SubSystem subSys:sublist){
			 i--;//子系统 角色 pid，-1,-2，-3，
			 rol=iAuthenticate.getNewSysRole();
			 rol.setRoleId(i);
			 rol.setSystemId(-9999999999L);//子系统pid
			 rol.setRoleName(subSys.getSysName());
			 //子系统父节点
			 treeList.add(rol);
			//{String.valueOf(i),  "-9999999999",subSys.getSysName()}
			/**
			 * 如果租户有自己的角色体系，读取角色，否则读取平台角色
			 */
			Long tenantId = RequestUtil.getLong(request, "tenantId", 0);
			if(tenantId==0){
				tenantId = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
			}
			List<SaasRole> saasRoles = saasRoleService.getByTenantId(tenantId);
			List<ISysRole> rolList2 = new ArrayList<ISysRole>();
			if(!saasRoles.isEmpty()){
				for(SaasRole saasRole : saasRoles){
					rolList2.add(saasRole.toSysRole());
				}
			}else{
				rolList2 = trolList;
			}
			/**
			List<ISysRole> rolList2=sysRoleService.getAllSaas(tenantId);
			if(rolList2.isEmpty()){
				rolList2=sysRoleService.getAll();
			}**/
			
			for(ISysRole sysRole2:rolList2){//子系统添加 角色子节点
				if(sysRole2.getMemo()!=null && sysRole2.getMemo().equals("内置角色")){
						continue;
				}else if(sysRole2.getIsTenant()==1){//一级菜单不显示
					continue;
				}
				if(sysRole2.getSystemId()!=null &&subSys.getSystemId()==sysRole2.getSystemId()){
					sysRole2.setSystemId( i);
					//所有子系统的子节点
					treeList.add(sysRole2);
					//{String.valueOf(sysRole2.getSystemId()),  String.valueOf(i),sysRole2.getRoleName()}	
				}
			}
		}
		return treeList;
	}
	
	/**
	 * 根据角色类型获取角色树。
	 * 
	 * <pre>
	 * ISysRole 的systemId 
	 * 1. 0代表是子系统。
	 * 2. 1代表全局角色。
	 * 3. 其他为子系统的角色。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeDataByType")
	@ResponseBody
	public List  getTreeDataByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//List<ISysRole> rolList = sysRoleService.getRoleTree(new QueryFilter(request, "sysRole", false));
		List<ISysRole> rolList =new ArrayList<ISysRole>();
		String roleTypeName = RequestUtil.getString(request, "roleTypeName","");
		QueryFilter filter = new QueryFilter(request, "sysRole", false);
		filter.addFilter("roleType", roleTypeName);
		List<ISysRole> trolList=sysRoleService.getAll(filter);		
		for(ISysRole role:trolList){
			if(role.getMemo().equals("内置角色")){
				continue;
			}else{
				rolList.add(role);
			}
		}
		ISysRole rol = null;
		
		List  treeList=new ArrayList();
		//循环子系统，{子系统角色id,"-1","子系统1"}
		List<SubSystem> sublist = subSystemService.getActiveSystem();
		Long i=-1L;
		for(SubSystem subSys:sublist){
			 i--;//子系统 角色 pid，-1,-2，-3，
			 rol=iAuthenticate.getNewSysRole();
			 rol.setRoleId(i);
			 rol.setSystemId(-9999999999L);//子系统pid
			 rol.setRoleName(subSys.getSysName());
			 //子系统父节点
			 treeList.add(rol);
			//{String.valueOf(i),  "-9999999999",subSys.getSysName()}
			
			/**
			 * 如果租户有自己的角色体系，读取角色，否则读取平台角色
			 */
			long tenantId = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
			List<SaasRole> saasRoles = saasRoleService.getByTenantId(tenantId);
			List<ISysRole> rolList2 = new ArrayList<ISysRole>();
			if(!saasRoles.isEmpty()){
				for(SaasRole saasRole : saasRoles){
					rolList2.add(saasRole.toSysRole());
				}
			}else{
				rolList2 = trolList;
			}
			
			for(ISysRole sysRole2:rolList2){//子系统添加 角色子节点
						if(sysRole2.getMemo().equals("内置角色")){
								continue;
						}
				if(sysRole2.getSystemId()!=null &&subSys.getSystemId()==sysRole2.getSystemId()){
					sysRole2.setSystemId( i);
					//所有子系统的子节点
					treeList.add(sysRole2);
					//{String.valueOf(sysRole2.getSystemId()),  String.valueOf(i),sysRole2.getRoleName()}	
				}
			}
		}
		return treeList;
	}

	/**
	 * 取得系统角色表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAll")
	@ResponseBody
	public List<ISysRole> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ISysRole> list = sysRoleService.getAll(new QueryFilter(request, false));
		return list;
	}

	/**
	 * 禁用或启用角色
	 * 
	 * @param request
	 * @param response
	 * @param po
	 * @throws Exception
	 */
	@RequestMapping("runEnable")
	@Action(description = "禁用或启用角色")
	public void runEnableRole(HttpServletRequest request, HttpServletResponse response) throws Exception {

		long roleId = RequestUtil.getLong(request, "roleId");
	
		ISysRole sysRole = sysRoleService.getById(roleId);
		if(sysRole.getEnabled().equals((short)1)){
			sysRole.setEnabled((short) 0);
		}
		else{
			sysRole.setEnabled((short) 1);
		}
		sysRoleService.update(sysRole);
		//删除所有的缓存。
		SecurityUtil.removeAll();
		response.sendRedirect(RequestUtil.getPrePage(request));
	}
	
	
	/**
	 * 添加或更新系统角色表。
	 * @param request
	 * @param response
	 * @param sysRole 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveRole")
	public void save(HttpServletRequest request, HttpServletResponse response, final SysRole sysRole,BindingResult bindResult) throws Exception
	{
		Long systemId=RequestUtil.getLong(request, "systemId");
		String systemAlias="";
		if(systemId>0){
			SubSystem subSystem=subSystemDao.getById(systemId);
			systemAlias=subSystem.getAlias() +"_";
		}
		ResultMessage resultMessage=validForm("sysRole", sysRole, bindResult, request);
		if(resultMessage.getResult()==ResultMessage.Fail){
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(sysRole.getRoleId()==null){
			String tmpAlias=sysRole.getAlias();
			String alias=systemAlias +tmpAlias;
			//判断别名是否存在。
			boolean isExist=sysRoleService.isExistRoleAlias(alias);
			if(!isExist){
				sysRole.setRoleId(UniqueIdUtil.genId());
				sysRole.setAlias(alias);
				sysRoleService.add(sysRole);
			 ///	List<SubSystem> all = subSystemService.getAll();
				//log.setLevel(Level.DEBUG ); //开启最低级别的，以上的严重的级别的都可以打印  
				final JSONObject json=new JSONObject();
				Map<String,Object> OperationMarked = new HashMap<String,Object>();
				OperationMarked.put("操作说明","增加：1,修改：2,删除：3");
				OperationMarked.put("operationSatus", 1);//操作标示
				json.put("OperationMarked", OperationMarked);//操作标示：1：增加,2：修改,删除3
				json.put("sysRole", sysRole);
				new  JMSRunableThread("role_add",json.toString());
		/*		for (final SubSystem subSystem : all) {
					jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public MapMessage createMessage(Session session) throws JMSException {
							MapMessage mapMsg=session.createMapMessage();
						  if(log.isDebugEnabled()){
							    log.debug("debug -----字符串：{ }-------"+json.toString()+"}"); 
						  }
						    mapMsg.setString("role_add",json.toString());
						    mapMsg.setStringProperty("role_add_Property", "1");//1：增加
							return mapMsg;
						}
					});
					SysMsgLog msgLog = new SysMsgLog();
					msgLog.setId(UniqueIdUtil.genId());
					msgLog.setOperation("角色添加");
					msgLog.setReceiverersonal(subSystem.getSystemId()+"");
					msgLog.setSendcontent(json.toString());
					sysMsgLogService.add(msgLog);
				}*/
				resultMsg=getText("角色添加成功","系统角色");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}else{
				resultMsg=getText("角色别名：["+tmpAlias+"]已存在");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			}
		}else{
			String tmpAlias=sysRole.getAlias();
			String alias=systemAlias +tmpAlias;
			Long roleId=sysRole.getRoleId();
			boolean isExist=sysRoleService.isExistRoleAliasForUpd(alias, roleId);
			if(isExist){
				resultMsg=getText("角色别名：["+tmpAlias+"]已存在");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			}
			else{
				sysRole.setAlias(alias);
				//点击编辑初始化全量角色数据 一般不用的
				//initRoles();
				
				try {
				//log.setLevel(Level.DEBUG ); //开启最低级别的，以上的严重的级别的都可以打印  
				sysRoleService.update(sysRole);
				///List<SubSystem> all = subSystemService.getAll();
				final JSONObject json=new JSONObject();
				Map<String,Object> OperationMarked = new HashMap<String,Object>();
				OperationMarked.put("操作说明","增加：1,修改：2,删除：3");
				OperationMarked.put("operationSatus", 2);//操作标示
				json.put("OperationMarked", OperationMarked);//操作标示：1：增加,2：修改,删除3
				json.put("sysRole", sysRole);
				new  JMSRunableThread("role_update",json.toString());
				/*for (final SubSystem subSystem : all) {
					jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public MapMessage createMessage(Session session) throws JMSException {
							MapMessage mapMsg=session.createMapMessage();
						  if(log.isDebugEnabled()){
							    log.debug("debug -----字符串：{ }-------"+json.toString()+"}"); 
						  }
						    mapMsg.setString("role_update",json.toString());
						   // mapMsg.setJMSMessageID(UniqueIdUtil.genId()+"");
						    Destination ReplyToDestination = session.createQueue("role");
						    mapMsg.setJMSReplyTo(ReplyToDestination);
						    //mapMsg.setStringProperty("JMSXGroupID", "roleGroup");//一个组对于一个消费者
						    mapMsg.setStringProperty("role_update_Property", "2");//2：修改
							return mapMsg;
						}
					});
					SysMsgLog msgLog = new SysMsgLog();
					msgLog.setId(UniqueIdUtil.genId());
					msgLog.setOperation("角色修改");
					msgLog.setReceiverersonal(subSystem.getSystemId()+"");
					msgLog.setSendcontent(json.toString());
					sysMsgLogService.add(msgLog);
				
				}*/
				} catch (Exception e) {
						log.error("error -----字符串：{ }-------"+e.getMessage()+"}");
				}
				resultMsg=getText("角色修改成功","系统角色");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}
		}
	}

	
}

