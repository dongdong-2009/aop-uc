package com.casic.tenant.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.casic.platform.saas.role.SaasRole;
import com.casic.service.UcSysAuditService;
import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.casic.tenant.dto.ResultDto;
import com.casic.tenant.model.Aptitude;
import com.casic.tenant.model.BranchBean;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.BranchBeanService;
import com.casic.tenant.service.TenantInfoService;
import com.casic.util.HttpClientUtil;
import com.casic.util.HttpFactory;
import com.casic.util.OpenIdUtil;
import com.casic.util.PropertiesUtils;
import com.casic.util.SmsUtil;
import com.casic.util.StringUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.bpmn20.entity.activiti.Out;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysOrgTypeService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

/**
 * 企业控制器
 * @author think
 * 2016 07 05
 */
@Controller
@RequestMapping("/tenant")
public class TenantInfoFormController extends BaseFormController{
	
	
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private HttpFactory httpFactory;
	
	@Resource
	private SysUserOrgService sysUserOrgService;
	
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
	private UserRoleService userRoleService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
 
	
	private Logger logger=Logger.getLogger(TenantInfoFormController.class);
	
	private static String onLine_url ="";
	static {
		onLine_url = PropertiesUtils.getProperty("onLine_url");
	}
	/**
	 * 添加或更新组织架构。
	 * 
	 * @param request
	 * @param response
	 * @param sysOrg
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新组织架构")
	public void save(HttpServletRequest request, HttpServletResponse response, SysOrg sysOrg, BindingResult bindResult) throws Exception {
		
		ResultMessage resultMessage = validForm("sysOrg", sysOrg, bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		
		
		if (sysOrg.getOrgId() == null) {
			Long orgId=UniqueIdUtil.genId();
			sysOrg.setOrgId(orgId);
			sysOrg.setCreatorId(ContextUtil.getCurrentUserId());
			Long supOrgId=RequestUtil.getLong(request, "orgSupId");
			ISysOrg supOrg = sysOrgService.getById(supOrgId);
			// 若以维度为父节点新建，则设置其Path为维度ID+该组织ID
			if (supOrg == null) {
				sysOrg.setPath(supOrgId + "." + orgId +".");
			} else {
				sysOrg.setPath(supOrg.getPath() + sysOrg.getOrgId() +".");
			}
			sysOrg.setOrgSupId(supOrgId);
			sysOrg.setSn(supOrg.getSn());
			sysOrg.setOpenId(OpenIdUtil.getOpenId());
			sysOrgService.addOrg(sysOrg);
			
			writeResultMessage(response.getWriter(), "{\"orgId\":\""+ orgId +"\",\"action\":\"add\"}", ResultMessage.Success);
		} else {
			sysOrg.setUpdateId(ContextUtil.getCurrentUserId());
			sysOrgService.updOrg(sysOrg);
			
			writeResultMessage(response.getWriter(), "{\"orgId\":\""+ sysOrg.getOrgId() +"\",\"action\":\"upd\"}", ResultMessage.Success);
		}
		
	}
	@RequestMapping("saveSettlementAccount")
	@Action(description = "账户更新")
	@ResponseBody
	public Map<String,String> saveSettlementAccount(BranchBean branchBean, HttpServletRequest request, HttpServletResponse response, SysOrg sysOrg, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = null;
		Map<String,String> map=new HashMap<String,String>();
		String resultMsg="";
		boolean flag=false;
		//先查询是否中金开户
		boolean fag=false;
		List<BranchBean> branchList=branchBeanService.getByOrgid(branchBean.getOrgid());
		if(branchList!=null&&branchList.size()>0){
			if(("1").equals(branchList.get(0).getAccstate())||("3").equals(branchList.get(0).getAccstate())){
				fag=true;//表示已中金开户
			}
		}
		if (fag){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("submerid", branchBean.getOrgid() + "");
				params.put("sysid", "uc");
				params.put("bkacc_bkid", branchBean.getBankId());
				params.put("bkacc_accno", branchBean.getBankaccount());
				params.put("bkacc_accnm", branchBean.getBranchaccountname());
				params.put("bkacc_acctp", branchBean.getAccountType());
				params.put("bkacc_cdtp", branchBean.getCredentialsType());
				params.put("bkacc_cdno", branchBean.getCredentialsNumber());
				params.put("bkacc_openbkcd", branchBean.getOpenbkcd());
				params.put("bkacc_openbknm", branchBean.getBranchname());
				params.put("bkacc_prccd", branchBean.getProvCode());
				params.put("bkacc_prcnm", branchBean.getProvince());
				params.put("bkacc_citycd", branchBean.getCityCode());
				params.put("bkacc_citynm", branchBean.getCity());
				params.put("fcflg", branchBean.getFcflg());
				String result = "";
				ResultDto reulDto = null;
				try {
					System.out.println("请求start====" + params + "---"
							+ onLine_url);
					result = HttpClientUtil.JsonPostInvoke(onLine_url, params);

					System.out.println("请求end====" + result);
					if (!StringUtil.isEmpty(result) && !result.equals("请求错误")) {
						System.out.println("result=" + result);
						reulDto = JSON.parseObject(result, ResultDto.class);
					}
				} catch (Exception e) {
					System.out.println("请求Exception====" + e);
					e.printStackTrace();
				}
				System.out.println("请求end===finally" + result);
				if (reulDto != null) {
					if (reulDto.getRETCODE().equals("000000")) {
						flag = true;
						//更新企业的认证状态 
					} else {
						resultMsg = "中金接口提示您:" + reulDto.getRETMSG();
					}
				}
				if (!flag) {
					if (!StringUtil.isEmpty(branchBean.getStlstate())) {
						if ("2".equals(branchBean.getStlstate())) {
							branchBean.setStlstate("2");
						} else if ("4".equals(branchBean.getStlstate())) {
							branchBean.setStlstate("4");
						}
					} else {

						branchBean.setStlstate("2");
					}
					branchBeanService.updateBranchAccount(branchBean);
					map.put("code", "0");
					if (StringUtil.isEmpty(resultMsg)) {
						resultMsg = "中金接口调用失败!请联系网络管理人员";
					}
					map.put("msg", resultMsg);
					return map;
				} else {
					resultMsg = "中金账号绑定成功";
					logger.info("账户更新start");

					if (!StringUtil.isEmpty(branchBean.getStlstate())) {
						if ("2".equals(branchBean.getStlstate())) {
							branchBean.setStlstate("1");
						} else if ("4".equals(branchBean.getStlstate())) {
							branchBean.setStlstate("3");
						}
					} else {
						branchBean.setStlstate("1");
					}
					branchBeanService.updateBranchAccount(branchBean);
					logger.info("账户更新end");
					map.put("code", "1");
					map.put("msg", "账户更新成功，" + resultMsg);
					return map;
				}
			}else{
				map.put("code", "0");
				map.put("msg", "请您先进行中金开户!");
				return map;
			}
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
	@RequestMapping("saveNewRole")
	@Action(description = "添加或更新用户表")
	public void saveNewRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String systemId="100";
		Long sysOrgInfoId=ContextUtil.getCurrentTenantId();
	  	ISysUser user=ContextUtil.getCurrentUser();
		if(StringUtil.isEmpty(sysOrgInfoId)){
			sysOrgInfoId =(user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}
		TenantInfo info = tenantInfoService.getById(sysOrgInfoId);
		if(info!=null){
			systemId = info.getSystemId();//企业属于哪个子系统
		}
		String status ="";
		String resultMsg = null;
        String userId=RequestUtil.getString(request, "userId");
        String openId=RequestUtil.getString(request, "openId");//用户的openId
        boolean flag=RequestUtil.getBoolean(request, "flag");
		String[] aryRoleId =RequestUtil.getStringAry(request,"roleId");// 角色Id数组
		ISysUser sysUser=null;
		if(aryRoleId==null || aryRoleId.length==0){
			resultMsg = getText("用户角色添加失败", "请选择所属角色！");
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;					
		}
		if(!StringUtil.isEmpty(userId)){
			sysUser =sysUserService.getById(Long.parseLong(userId));
		}
		if(!"".equals(userId)||aryRoleId!=null&&sysUser!=null){
			sysUserService.updateUser(sysUser,aryRoleId,flag);
			resultMsg = getText("用户角色更新成功", "用户表");
			status = "200";
		}
		String result="";
		int i=1;
		List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassifyWithSys(i,"userRole",systemId+"");
		for(InterfaceUrlBean urlBean:urls){
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("openId", openId);
			data.put("roleIds", aryRoleId);
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
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");  
			PrintWriter printWriter = response.getWriter();
			ResultMessage resultObj=new ResultMessage(ResultMessage.Success,resultMsg);
			printWriter.print(resultObj);
			printWriter.close();  
			//writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		}
		
		
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveOrg")
	@Action(description = "添加或更新用户组织信息")
	public void saveOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String userId=RequestUtil.getString(request, "userId");
		Long[] aryOrgId = RequestUtil.getLongAry(request, "orgIds") ;// 组织Id数组
		Long orgIdPrimary = RequestUtil.getLong(request, "orgIdPrimary");// 主要组织Id
		Long[] aryChargeOrg = RequestUtil.getLongAry(request, "chargeOrgId") ;// 组织Id数组
		ISysUser sysUser=null;
		if(aryOrgId==null || aryOrgId.length==0){
			resultMsg ="用户组织添加失败,失败原因:请选择所属组织！";
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;			
		}
		if (BeanUtils.isNotEmpty(aryOrgId)&& orgIdPrimary==0){
			resultMsg ="用户组织添加失败,失败原因:请选择主组织！";
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
			return;
		}
		if(!StringUtil.isEmpty(userId)){
			sysUser =sysUserService.getById(Long.parseLong(userId));
		}
		
		sysUserService.updateUser(sysUser,aryOrgId,aryChargeOrg,orgIdPrimary);
		sysUserService.updateOrgId(sysUser,orgIdPrimary);
		resultMsg ="用户组织更新失败";
		
		
		
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}
	
	@RequestMapping("joinNow")
	public void joinNow(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ISysUser currentUser = ContextUtil.getCurrentUser();
		String resultMsg = null;
		//获取加入的组织信息
		String sysOrgInfoId=request.getParameter("sysOrgInfoId");
		if(StringUtil.isEmpty(sysOrgInfoId)){
			resultMsg = getText("加入企业失败", "请填写加入的企业名称！");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			return ;
		}
		Long sysOrgId=Long.parseLong(sysOrgInfoId);
		//Long[] aryRoleId =new Long[]{10000045050018L,10000045050019L,10000045050020L,10000045050021L,10000045050022L};// 角色Id数组
		Long [] aryRoleId=new Long[]{10000043250001L};
		
		Long[] aryOrgId = {sysOrgId};// 组织Id数组
		 Long[] aryOrgCharge=new Long[1];
		  aryOrgCharge[0]=SysUserOrg.CHARRGE_YES.longValue();
			
			
		  currentUser.setOrgId(sysOrgId);
		  currentUser.setOrgSn(sysOrgId);
		  currentUser.setState("0");//用户审核状态
		  sysUserService.update(currentUser);
			// 添加用户和组织关系。
		  sysUserOrgService.saveUserOrg(currentUser.getUserId(), new Long[]{sysOrgId}, 1l, aryOrgCharge);
		  //添加用户和角色的关系
		  userRoleService.saveUserRole(currentUser.getUserId(), aryRoleId);
		  /**
		   * 更新是否还是个人用户的表
		   */
		  String sqlUpdate = "update sys_user_extence  set state = 1  where user_id = '"+currentUser.getUserId()+"'";
			int flag = jdbcTemplate.update(sqlUpdate);
			
			if(flag == 0){
				String sql = "insert into sys_user_extence  set state = 1 ,user_id = '"+currentUser.getUserId()+"'";
				jdbcTemplate.execute(sql);
			}
			resultMsg = getText("加入企业成功", "加入企业成功");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			//调用子系统接口
			try{
				//遍历所有接口地址，将数据同步到各子系统
				String result = "";
				int i = 2;
				List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i,"user");
				for(InterfaceUrlBean urlBean:urls){
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, Object> data = new HashMap<String, Object>();
					// 子系统标识
					long systemId = urlBean.getSubId();
					data.put("sysUser", currentUser);
					//data.put("aptitudes", aptitudes);
					params.put("systemId", systemId);
					params.put("data", data);
					result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl()+urlBean.getUrl(), params);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
		
	
	}
}
