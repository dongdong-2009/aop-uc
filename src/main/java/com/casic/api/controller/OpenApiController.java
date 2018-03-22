package com.casic.api.controller;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.appleframe.utils.reflect.BeanUtils;
import com.casic.base.model.config.UserRelationShip;
import com.casic.base.service.config.OrgRelationShipService;
import com.casic.base.service.config.UserRelationShipService;
import com.casic.core.util.StringUtil;
import com.casic.log.model.config.AhRecord;
import com.casic.log.service.config.AhRecordService;
import com.casic.model.DataParam;
import com.casic.model.DataParamNoBack;
import com.casic.model.NotNullAnnotation;
import com.casic.model.ReturnCode;
import com.casic.model.TenantData;
import com.casic.model.UcDataParam;
import com.casic.model.UcTenant;
import com.casic.model.UserData;
import com.casic.oauth.model.OauthParam;
import com.casic.security.service.SecretKeyService;
import com.casic.service.UcSysAuditService;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.casic.tenant.dao.SysOrgInfoRes;
import com.casic.tenant.model.BranchBean;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.BranchBeanService;
import com.casic.tenant.service.TenantInfoService;
import com.casic.url.service.UrlMonitorService;
import com.casic.util.CacheUtil;
import com.casic.util.DES;
import com.casic.util.DateUtil;
import com.casic.util.EnumUtils;
import com.casic.util.HttpClientUtil;
import com.casic.util.OpenIdUtil;
import com.casic.util.PropertiesUtils;
import com.casic.util.RegexValidateUtil;
import com.casic.util.SecreptUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.NewSysOrgInfo;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.RoleResources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysRoleRes;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.SysUserRole;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.NewSysOrgInfoService;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.RoleResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleResService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserRoleService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

import net.sf.json.JSONObject;

/***
 * 验证码控制器
 * 
 * @author think 2016 07 04
 *
 */
@Controller
@RequestMapping("/api")
public class OpenApiController extends BaseController {

	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SubSystemService subSystemService;
	
	
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	@Resource
	private NewSysOrgInfoService newSysOrgInfoService;

	@Resource
	private SysRoleResService sysRoleResService;
	@Resource
	private IAuthenticate iAuthenticate;
	
	@Resource
	private SysUserOrgService sysUserOrgService;
	
	@Resource
	private SecretKeyService secretKeyService;

	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysUserRoleService sysUserRoleService;
	
	@Resource
	private ResourcesService resourcesService;
	
	@Resource
	private RoleResourcesService roleResService;
	
	@Resource
	private BranchBeanService branService;
	
	@Resource
	private TenantInfoService tenantInfoService;
	
	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
	
	@Resource
	private UrlMonitorService urlMonitorService;
	
	@Resource
	private UcSysAuditService ucSysAuditService;
	@Resource
	private UserRelationShipService userRelationShipService;
	@Resource
	private OrgRelationShipService orgRelationShipService;
	@Resource
	private AhRecordService ahRecordService;
	
	@Resource
	private DictionaryService dicService;
	
	private static String cas_url ="";
	static {
		cas_url = PropertiesUtils.getProperty("cas.url");
	}
	/**
	 * 子系统注册时调用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	@RequestMapping(value = "subContact", method = RequestMethod.POST)
	@Action(description = "各子系统调用的")
	@ResponseBody
	public Map<String, String> subContact(@RequestBody DataParam dataParam,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		String sysId = dataParam.getSystemId();
		String url = dataParam.getUri();
		Map<String, Object> data = dataParam.getData();
		// 首先判断是否传入子系统id未传直接返回请传入子系统id
		if (sysId == null || "".equals(sysId)) {
			dataMap.put("status", "500");
			dataMap.put("msg", "请传入子系统唯一标识");
			return dataMap;
		}
		if (url == null || "".equals(url)) {
			dataMap.put("status", "501");
			dataMap.put("msg", "请传入子系统获取数据地址");
			return dataMap;
		}
		if (data == null || "".equals(data)) {
			dataMap.put("status", "502");
			dataMap.put("msg", "请传入子系统所需参数");
			return dataMap;
		}
		SubSystem subSystem = subSystemService.getById(Long.parseLong(sysId));
		if (subSystem == null) {
			dataMap.put("status", "503");
			dataMap.put("msg", "未查询到子系统");
			return dataMap;
		}
		String res = HttpClientUtil.JsonPostInvoke(url, data);
		if ("".equals(res) || res == null) {
			dataMap.put("status", "504");
			dataMap.put("msg", "通过子系统url未获取到数据");
			return dataMap;
		}
		JSONObject jsonObject = JSONObject.fromObject(res);
		String status = jsonObject.getString("status");
		if("500".equals(status)){
			dataMap.put("status", "505");
			dataMap.put("msg", "子系统服务调用失败");
			return dataMap;
		}
		
		String _data = jsonObject.getString("results");
		JSONObject _data_obj = JSONObject.fromObject(_data);
		//获得子系统的用户	
		SysUser user = (SysUser) JSONObject.toBean(_data_obj, SysUser.class);
		long subUserId = user.getUserId();
		//获得组织id
		long orgId = user.getOrgId();
		//判断组织是否存在如果不存在则报错
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		//判断传入的组织是否已经存在于用户中心
		if(sysOrg==null){
			dataMap.put("status", "506");
			dataMap.put("msg", "传入的企业不存在");
			return dataMap;
		}
		String openId = user.getOpenId();
		ISysUser ucUser = new SysUser();
		ucUser = sysUserService.getByOpenId(openId);
		if(ucUser==null){//用户中心没有该数据
			//用户入库（userId）会有问题
			long ucUserId = UniqueIdUtil.genId();
			user.setUserId(ucUserId);
			String ucOpenId = "";
			String s = UUID.randomUUID().toString(); 
	        //去掉“-”符号 
			ucOpenId = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
			user.setOpenId(ucOpenId);
			iAuthenticate.add(user);
			String [] orgIds =  new String[]{orgId+""};
			//增加用户组织关系表
			sysUserOrgService.addUserOrg(orgIds, "1", ucUserId);
			
			//回调子系统将openId传回
			String subUri = "http://10.142.15.34:8080/cloud/system/auth/api/syncUserByUserId.ht";
			Map<String, Object> dataOpen = new HashMap<String, Object>();
			Map<String, Object> dataSub = new HashMap<String, Object>();
			dataSub.put("userId", subUserId);
			dataSub.put("openId", ucOpenId);
			dataOpen.put("systemId", sysId);
			dataOpen.put("data", dataSub);
			String resOpen = HttpClientUtil.JsonPostInvoke(subUri, dataOpen);
			System.out.println(resOpen);
			dataMap.put("status", "200");
			dataMap.put("msg", "接口调用成功,openId为"+dataOpen);
			return dataMap;
		}
		
		System.out.println(res);
		// 入库逻辑
		return dataMap;
	}
	
	
	
	
	
	
	
	
	/**
	 * 子系统注册时调用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@Action(description = "各子系统调用的")
	@ResponseBody
	public Map<String, String> register(@RequestBody DataParam dataParam,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		String sysId = dataParam.getSystemId();
		String url = dataParam.getUri();
		Map<String, Object> data = dataParam.getData();
		// 首先判断是否传入子系统id未传直接返回请传入子系统id
		if (sysId == null || "".equals(sysId)) {
			dataMap.put("status", "500");
			dataMap.put("msg", "请传入子系统唯一标识");
			return dataMap;
		}
		if (url == null || "".equals(url)) {
			dataMap.put("status", "501");
			dataMap.put("msg", "请传入子系统获取数据地址");
			return dataMap;
		}
		if (data == null || "".equals(data)) {
			dataMap.put("status", "502");
			dataMap.put("msg", "请传入子系统所需参数");
			return dataMap;
		}
		SubSystem subSystem = subSystemService.getById(Long.parseLong(sysId));
		if (subSystem == null) {
			dataMap.put("status", "503");
			dataMap.put("msg", "未查询到子系统");
			return dataMap;
		}
		String res = HttpClientUtil.JsonPostInvoke(url, data);
		if ("".equals(res) || res == null) {
			dataMap.put("status", "504");
			dataMap.put("msg", "通过子系统url未获取到数据");
			return dataMap;
		}
		JSONObject jsonObject = JSONObject.fromObject(res);
		String status = jsonObject.getString("status");
		if("500".equals(status)){
			dataMap.put("status", "505");
			dataMap.put("msg", "子系统服务调用失败");
			return dataMap;
		}
		
		String openId ="";
		ISysUser ucUser = new SysUser();
		ucUser = sysUserService.getByOpenId(openId);
		// 将数据同步到子系统
		return dataMap;
	}
	
	/**
	 * 子系统需注册成会员，并注册子系统才可使用该接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "registerNoBack", method = RequestMethod.POST)
	@Action(description = "各子系统调用的")
	@ResponseBody
	public Map<String, Object> registerNoBack(@RequestBody DataParamNoBack dataParam,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("success",true);
		dataMap.put("error", null);
		dataMap.put("errorCode", "0");
		String sysId = dataParam.getSystemId();
		String data = dataParam.getData();
		// 首先判断是否传入子系统id未传直接返回请传入子系统id
		if (sysId == null || "".equals(sysId)) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "500");
			dataMap.put("error", "请传入子系统唯一标识");
			return dataMap;
		}
		SubSystem subSystem = subSystemService.getById(Long.parseLong(sysId));
		if (subSystem == null) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "502");
			dataMap.put("error", "未查询到子系统");
			return dataMap;
		}
		if (data == null || "".equals(data)) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "501");
			dataMap.put("error", "请传入子系统所需参数");
			return dataMap;
		}
		//根据sysId得到秘钥
		String secretKey = secretKeyService.getSecretKeyBySysId(Long.parseLong(sysId));
		if (secretKey == null || "".equals(secretKey)) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "504");
			dataMap.put("error", "系统无对应秘钥，请联系管理员");
			return dataMap;
		}
		SecreptUtil des = new SecreptUtil(secretKey); 
		try{
			data=des.decrypt(data);
			JSONObject jsonObject = JSONObject.fromObject(data);
			ISysUser user = (SysUser) JSONObject.toBean(jsonObject, SysUser.class);
			user.setOpenId(OpenIdUtil.getOpenId());
			user.setUserId(UniqueIdUtil.genId());
			String account = user.getAccount();
			String encryptSha256 = EncryptUtil.encryptSha256(user.getPassword());
			user.setPassword(encryptSha256);
			ISysUser user2 = sysUserService.getByAccount(account);
			//String account2 = user2.getAccount();
			if(user2!=null){
				if(account.equals(user2.getAccount())){
					dataMap.put("success",false);
					dataMap.put("errorCode", "235");
					dataMap.put("error", "此账号已存在");
					return dataMap;
				}
			}
			sysUserService.add(user);
			Long[] aryOrgId = new Long[1];// 组织Id数组
			//根据企业名称获取企业的详细信息
			aryOrgId[0]=user.getOrgId();
			Long[] aryOrgCharge=new Long[1];
			aryOrgCharge[0]=SysUserOrg.CHARRGE_NO.longValue();
			sysUserOrgService.saveUserOrg(user.getUserId(), aryOrgId, 1l, aryOrgCharge);
			dataMap.put("data", user);
			dataMap.put("message", "用户添加成功");
		}
		catch(Exception e){
			e.printStackTrace();
			dataMap.put("success",false);
			dataMap.put("errorCode", "503");
			dataMap.put("error", "用户保存失败");
			return dataMap;
		}
		
		return dataMap;
	}
	/**
	 * 子系统在注册后，据systemid查询企业 返回多个企业
	 * Service 空实现，需要改动dao
	 * */
	@RequestMapping(value="getOrgInfoBySysid",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getOrgInfoBySysid(@RequestBody DataParam data)throws Exception{		
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}	
		String systemId = data.getSystemId();
		List<SysOrgInfo> syss=sysOrgService.getOrgInfoBySysid(systemId);
		if(syss!=null){
			map.put("success", true);
			map.put(systemId, syss);
		}else{
			map.put("success", false);
			map.put("errorCode", "503");
			map.put("error", "未查询到企业");
		}		
		return map;
	
	}
	
	
	/**
	 * 子系统在注册后，根据openid查询企业
	 * */
	@RequestMapping(value="getOrgInfoByOid",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getOrgInfoByOid(@RequestBody DataParam data) throws Exception{				
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}		
		Map<String, Object> dmap = data.getData();
		if(dmap==null || !dmap.containsKey("openId")){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		String openId=StringUtil.getString(dmap.get("openId"));
		if(openId == null || "".equals(openId.trim())){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		SysOrgInfo sysOrgInfo=sysOrgService.getOrgByOId(openId);					
		if(sysOrgInfo != null){
			map.put("success", true);
			map.put(openId, sysOrgInfo);
			return map;
		}
		map.put("success", false);
		map.put("errorCode", "503");
		map.put("error", "未查询到子系统");
		return map;
	}
	
	/**
	 * 子系统在注册后，根据用户account查询用户信息
	 * {systemId:100,data:{account:"740116_system"}}
	 **/
	@RequestMapping(value="getUserInfoByAcc",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserInfoByAcc(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}		
		Map<String, Object> dmap = data.getData();
		if(dmap==null || !dmap.containsKey("account")){
			map.put("success", false);
			map.put("errorCode", "501"); 
			map.put("error", "请传入所需参数");
			return map;
		}
		String account = StringUtil.getString(dmap.get("account"));
		if(com.casic.util.StringUtil.isEmpty(account)){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}else{
			ISysUser sysUser = (SysUser) sysUserService.getByAccount(account);
			if(sysUser != null){
				map.put("success", true);
				map.put("account", sysUser);
			}else{
				map.put("success", false);
				map.put("errorCode", "235");
				map.put("error", "用户不存在");
			}
			return map;
		}
	}
	
	/**
	 * 子系统在注册后，根据用户account查询用户组织信息
	 * {systemId:100,data:{account:"740116_system"}}
	 **/
	@RequestMapping(value="getUserOrgByAccount",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserOrgByAccount(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}		
		Map<String, Object> dmap = data.getData();
		if(dmap==null || !dmap.containsKey("account")){
			map.put("success", false);
			map.put("errorCode", "501"); 
			map.put("error", "请传入所需参数");
			return map;
		}
		String account = StringUtil.getString(dmap.get("account"));
		if(com.casic.util.StringUtil.isEmpty(account)){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}else{
			ISysUser sysUser = (SysUser) sysUserService.getByAccount(account);
			if(sysUser != null){
				List<SysUserOrg> orgByUserId = sysUserOrgService.getOrgByUserId(sysUser.getUserId());
				map.put("success", true);
				map.put("sysUserOrg", orgByUserId);
			}else{
				map.put("success", false);
				map.put("errorCode", "235");
				map.put("error", "用户不存在");
			}
			return map;
		}
	}
	
	
	
	/**
	 * 检查用户手机号是否重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="checkMobileIsExist",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkMobileRepeat(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		QueryFilter queryFilter = new QueryFilter(request, true);
		String mobile = RequestUtil.getString(request, "mobile");
		if(StringUtils.isEmpty(mobile)){
			map.put("success", false);
			map.put("errorCode", "501"); 
			map.put("error", "请传入手机号");
			return map;
		}
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("mobile", mobile);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if (sysUsers != null) {
			if (sysUsers.size() > 0) {
				map.put("success", false);
				map.put("errorCode", "502"); 
				map.put("error", "手机号已存在请输入新的手机号");
				return map;
			} else {
				map.put("success", true);
				map.put("msg", "手机号可以使用");
				return map;
			}
		} else {
			map.put("success", true);
			map.put("msg", "手机号可以使用");
			return map;
		}
	}
	

	
	
	/**
	 * 子系统在注册后，根据openId查询用户信息
	 * */
	@RequestMapping(value="getUserInfoByOid",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserInfoByOid(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}		
		Map<String, Object> dmap = data.getData();
		if(dmap==null || !dmap.containsKey("openId")){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		String openId = StringUtil.getString(dmap.get("openId")) ;
		if(openId == null || "".equals(openId.trim())){ 
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		ISysUser sysUser = (SysUser)sysUserService.getByOpenId(openId);
		if(sysUser != null){
			map.put("success", true);
			map.put(openId, sysUser);
		}else{
			map.put("success", false);
			map.put("errorCode", "235");
			map.put("error", "用户不存在");
		}		
		return map;
	}
	
	/**
	 * 子系统注册后，可以通过orgId查询子系统的组织信息
	 * */
	@RequestMapping(value="getOrgByOrgId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getOrgByOrgId(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(dmap==null || !dmap.containsKey("orgId")){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		String orgId =StringUtil.getString(dmap.get("orgId"));
		if(orgId == null || "".equals(orgId.trim())){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		ISysOrg sysOrg = sysOrgService.getById(Long.parseLong(orgId));
		if(sysOrg != null){
			map.put("success", true);
			map.put(orgId, sysOrg);
		}else{
			map.put("success", false);
			map.put("errorCode", "235");
			map.put("error", "组织不存在");
		}		
		return map;	
	}
	
	/**
	 * 需求：根据企业id查询该企业的信息并返回一个管理员
	 * */
	@RequestMapping(value="getOrgInfoAndManagerByOrgId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getOrgInfoAndManagerByOrgId(@RequestBody DataParam data) throws Exception{ 
		Map<String, Object> map = getOrgInfoByOrgId(data);
		if(map.get("error") != null){
			//未查询到企业信息
			return map;
		}
		String orgId = StringUtil.getString(data.getData().get("orgId"));
		List<ISysUser> users = sysUserService.getUserByOrgid(orgId);
		if(users.isEmpty()){
			map.put("success", false);
			map.put("manager", null);
			return map;
		}
		for (ISysUser user : users) {
			Long userid = user.getUserId();
			List<ISysRole> rolse = sysRoleService.getByUserId(userid);
			if(!rolse.isEmpty()){
				for (ISysRole role : rolse) {
					if("航天云网-企业管理员".equals(role.getRoleName())){
						map.put("success", true);
						map.put("manager", user);
						return map;
					}
				}
			}		
		}
		map.put("success", false);
		map.put("manager", null);
		return map;
	}
	/**
	 * 子系统注册后，可以通过orgId查询子系统的企业信息
	 * */
	@RequestMapping(value="getOrgInfoByOrgId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getOrgInfoByOrgId(@RequestBody DataParam data){
		Map<String,Object> map=new HashMap<String, Object>();
		if(data == null || "".equals(data)){
			map.put("success", false);
			map.put("errorCode", "230");
			map.put("error", "请输入有效的参数");
			return map;
		}
		String systemId = data.getSystemId();
		if(systemId == null || "".equals(systemId.trim())){
			map.put("success", false);
			map.put("errorCode", "500");
			map.put("error", "请传入系统唯一标识");
			return map;
		}else{
			SubSystem subSystem = subSystemService.getById(Long.parseLong(systemId));
			if(subSystem == null){
				map.put("success", false);
				map.put("errorCode", "503");
				map.put("error", "未查询到子系统");
				return map;
			}
		}
		Map<String, Object> dmap = data.getData();
		if(dmap==null || !dmap.containsKey("orgId")){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		String orgId = StringUtil.getString(dmap.get("orgId"));
		if(orgId == null || "".equals(orgId.trim())){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}
		SysOrgInfo sysOrg = sysOrgInfoService.getById(Long.parseLong(orgId.trim()));
		if(sysOrg != null){
			map.put("success", true);
			map.put("orgId", sysOrg);
		}else{
			map.put("success", false);
			map.put("errorCode", "235");
			map.put("error", "企业不存在");
		}		
		return map;	
	}
	
	
	
	
	/**
	 *@Title: getRoleByUser 
	 *@Description: 根据用户查询角色
	 **/
	@RequestMapping(value="getRoleByUser",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getRoleByUser(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(!dmap.isEmpty() && dmap.containsKey("userId")){
			String uid=StringUtil.getString(dmap.get("userId"));
			if(uid==null || "".equals(uid.trim())){
				map.put("success", false);
				map.put("errorCode", "501");
				map.put("error", "请输入有效的参数");	
				return map;
			}
			String userId = uid.trim();
			List<ISysRole> roles = sysRoleService.getByUserId(Long.parseLong(userId));
			if(!roles.isEmpty()){
				for (ISysRole iSysRole : roles) {
					Long systemId = iSysRole.getSystemId();
					// 如果系统id不为空，则将角色别名系统前缀替换掉。
					if (systemId != null) {
						SubSystem subSystem = subSystemService.getById(systemId);
						String sysAlias = subSystem.getAlias();
						String roleAlias = iSysRole.getAlias().replaceFirst(sysAlias + "_", "");
						iSysRole.setAlias(roleAlias);
				  }
				}
				map.put("success", true);
				map.put(userId, roles);
			}else{
				map.put("success", false);
				map.put("errorCode", "502");
				map.put("error", "该用户未拥有角色");
			}			
		}else{
			map.put("success", false);
			map.put("errorCode", "502");
			map.put("error", "该用户未拥有角色");
		}
		return map;
	}
	
	/**
	 *@Title: getRuleByRole 
	 *@Description: 根据角色查询权限
	 **/
	@RequestMapping(value="getRuleByRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getRuleByRole(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}		
		Map<String, Object> dmap = data.getData();
		if(!dmap.isEmpty() && dmap.containsKey("roleId")){
			String rid=StringUtil.getString(dmap.get("roleId"));
			if(rid==null || "".equals(rid.trim())){
				map.put("success", false);
				map.put("errorCode", "501");
				map.put("error", "请输入有效的参数");	
				return map;
			}  
			String roleId = rid.trim();
			List<Resources> ress = resourcesService.getBySysRolResChecked(Long.parseLong(data.getSystemId()), Long.parseLong(roleId));
			if(!ress.isEmpty()){
				map.put("success", true);
				map.put(roleId, ress);
			}else{
				map.put("success", false);
				map.put("errorCode", "237");
				map.put("error", "该角色未拥有访问资源的权限");
			}			
		}else{
			map.put("success", false);
			map.put("errorCode", "237");
			map.put("error", "该角色未拥有访问资源的权限");
		}		
		return map;
	}
	/**
	 *@Title: Role 
	 *@Description: 根据角色查询用户
	 **/
	@RequestMapping(value="getUserByRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserByRole(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(!dmap.isEmpty() && dmap.containsKey("roleId")){
			String rid= StringUtil.getString(dmap.get("roleId"));
			if(rid==null || "".equals(rid.trim())){
				map.put("success", false);
				map.put("errorCode", "501");
				map.put("error", "请输入有效的参数");	
				return map;
			}
			String roleId = rid.trim();
			List<Long> uids = userRoleService.getUserIdsByRoleId(Long.parseLong(roleId));
			List<SysUser> sysUsers=new ArrayList<SysUser>();
			if(uids.isEmpty()){
				map.put("success", false);
				map.put("errorCode", "501");
				map.put("error", "请输入有效的参数");	
				return map;
			}
			for (Long uid : uids) {
				SysUser sysUser = (SysUser) sysUserService.getById(uid);
				sysUsers.add(sysUser);
			}
			if(!sysUsers.isEmpty()){
				map.put("success", true);
				map.put(roleId, sysUsers);
			}else{
				map.put("success", false);
				map.put("errorCode", "509");
				map.put("error", "未查询到该用户");	
			}
			
		}else{
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请输入有效的参数");		
		}	
		return map;
	}
	@RequestMapping(value="isAdmin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isAdmin(@RequestBody DataParam data) throws Exception{
 		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(!dmap.isEmpty() && dmap.containsKey("userId")){
			String uid= StringUtil.getString(dmap.get("userId"));
			if(uid==null || "".equals(uid.trim())){
				map.put("success", false);
				map.put("errorCode", "501");
				map.put("error", "请输入有效的参数");	
				return map;
			}
			String userid = uid.trim();
			List<ISysRole> UserRoles = sysRoleService.getByUserId(Long.parseLong(userid));
			if(UserRoles.isEmpty()){
				map.put("success", false);
				map.put("errorCode", "502");
				map.put("error", "该用户未拥有角色");	
				return map;
			}
			for (ISysRole iSysRole : UserRoles) {
				String name = iSysRole.getRoleName();				
				if("航天云网-企业管理员".equals(name)){
					/*Long id = iSysRole.getSystemId();
					Long _id = Long.parseLong(data.getSystemId().trim());
					if(id==_id){
									
					}	*/
					map.put("success", true);
					map.put(userid, iSysRole);
					return map;
				}
			}					
				map.put("success", false);
				map.put("errorCode", "507");
				map.put("error", "该用户不是管理员");
				return map;
		}
		map.put("success", false);
		map.put("errorCode", "501");
		map.put("error", "请输入有效的参数");
		return map;
	}
	/**
	 * 通过企业的orgId获得企业的开户信息
	 * */
	@RequestMapping(value="getBranchByOri",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBranchByOri(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(!dmap.isEmpty() && dmap.containsKey("orgId")){
			String orgId= StringUtil.getString(dmap.get("orgId"));
			if(orgId==null || "".equals(orgId.trim())){
				map.put("success", false);
				map.put("errorCode", "501");
				map.put("error", "请输入有效的参数");	
				return map;
			}
			String oid=orgId.trim();
			List<BranchBean> branchs = branService.getByOrgid(Long.parseLong(oid));
			if(!branchs.isEmpty()){
				map.put("success", true);
				map.put(oid, branchs);
			}else{
				map.put("success", false);
				map.put("errorCode", "509");
				map.put("error", "该企业未开户");					
			}
			return map;
		}else{
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请输入有效的参数");	
			return map;
		}	
	}
	
	private Map<String,Object> isSubsysExist(DataParam data) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		if(data == null || "".equals(data)){
			map.put("success", false);
			map.put("errorCode", "230");
			map.put("error", "请输入有效的参数");
			return map;
		}
		String systemId = data.getSystemId();
		if(systemId == null || "".equals(systemId.trim())){
			map.put("success", false);
			map.put("errorCode", "500");
			map.put("error", "请传入系统唯一标识");
			return map;
		}else{
			SubSystem subSystem = subSystemService.getById(Long.parseLong(systemId));
			if(subSystem == null){
				map.put("success", false);
				map.put("errorCode", "503");
				map.put("error", "未查询到子系统");
				return map;
			}
		}
		return map;
	}
	private Map<String,Object> isSubsysExist(String data) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		if(data == null || "".equals(data)){
			map.put("success", false);
			map.put("errorCode", "230");
			map.put("error", "请输入有效的参数");
			return map;
		}
		if(data == null || "".equals(data.trim())){
			map.put("success", false);
			map.put("errorCode", "500");
			map.put("error", "请传入系统唯一标识");
			return map;
		}else{
			SubSystem subSystem = subSystemService.getById(Long.parseLong(data));
			if(subSystem == null){
				map.put("success", false);
				map.put("errorCode", "503");
				map.put("error", "未查询到子系统");
				return map;
			}
		}
		return map;
	}
	private Map<String,Object> isSubsysExist(UcDataParam data) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		if(data == null || "".equals(data)){
			map.put("success", false);
			map.put("errorCode", "230");
			map.put("error", "请输入有效的参数");
			return map;
		}
		String systemId = data.getSystemId();
		if(systemId == null || "".equals(systemId.trim())){
			map.put("success", false);
			map.put("errorCode", "500");
			map.put("error", "请传入系统唯一标识");
			return map;
		}else{
			SubSystem subSystem = subSystemService.getById(Long.parseLong(systemId));
			if(subSystem == null){
				map.put("success", false);
				map.put("errorCode", "503");
				map.put("error", "未查询到子系统");
				return map;
			}
		}
		return map;
	}
	
	//根据子系统id查询子系统所具有的角色
	@RequestMapping(value="getSysRoleBySysId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSysRoleBySysId(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		String systemId = data.getSystemId();
		if(systemId!=null && !"".equals(systemId)){
			systemId=systemId.trim();
			List<ISysRole> roles = sysRoleService.getBySystemId(Long.parseLong(systemId));
			if(!roles.isEmpty()){
				//查询到系统所具有的角色
				map.put("success", true);
				map.put("code", "100001");
				map.put("msg", roles);				
			}else{
				//未查询到系统所具有的角色
				map.put("success",false);
				map.put("errorCode", "100051");
				map.put("error", "此系统未查询到角色信息");
			}
			return map;			
		}else{
			map.put("success",false);
			map.put("errorCode", "100009");
			map.put("error", "参数错误");
			return map;
		}
		
	}
	
	//根据子系统id查询子系统所具有的资源
	@RequestMapping(value="getSysResBySysId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSysResBySysId(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		String systemId = data.getSystemId();
		if(systemId != null){
			systemId=systemId.trim();
			List<Resources> resourcess = resourcesService.getBySystemIdAndIsHidden(Long.parseLong(systemId));
			if(!resourcess.isEmpty()){
				map.put("success", true);
				map.put("code","100001");
				map.put("msg", resourcess);
			}else{
				map.put("success", false);
				map.put("errorCode", "100052");
				map.put("error", "此系统未查询到资源信息");	
			}
			return map;
		}else{
			map.put("success",false);
			map.put("errorCode", "100009");
			map.put("error", "参数错误");
			return map;			
		}	
	}
	
	/**
	 * 给角色分配资源
	 * */
	@RequestMapping(value="addResForRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResForRole(@RequestBody DataParam data,HttpServletRequest request) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}		
		if(data.getData().isEmpty() &&!(data.getData().containsKey("roleId") && !data.getData().containsKey("resId"))){
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;
		}
		RoleResources entity=new RoleResources();
		String systemId = data.getSystemId().toString();
		String roleId = StringUtil.getString(data.getData().get("roleId"));
		String resId = StringUtil.getString(data.getData().get("resId"));
		if(roleId!=null && resId!=null && !"".equals(roleId) && !"".equals(resId)){
			boolean flag = isResAndRoleExist(request, resId, roleId);
			if(flag){
				//存在不能分配
				map.put("success", false);
				map.put("errorCode", "100015");
				map.put("error", "此资源已分配给该角色");
				return map;
			}
			entity.setRoleResId(UniqueIdUtil.genId());
			entity.setResId(Long.parseLong(resId));
			entity.setRoleId(Long.parseLong(roleId));
			entity.setSystemId(Long.parseLong(systemId));
		}else{
			map.put("success", false);
			map.put("errorCode", "100009");
			map.put("error", "请填写正确的参数");
			return map;
		}		
		try {
			roleResService.add(entity);
			map.put("success", true);
			map.put("code", "100001");
			map.put("msg", "角色分配资源成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("errorCode", "100011");
			map.put("error", "角色分配资源失败");			
			e.printStackTrace();
		}
		return map;		
	}
	/**
	 * 根据Id获取角色—资源
	 * */
	@RequestMapping(value="getResRolById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getResRolById(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		if(data.getData().isEmpty() || !data.getData().containsKey("roleResId")){
			map.put("success", false);	
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;
		}
		String roleResId = StringUtil.getString(data.getData().get("roleResId"));
		RoleResources resources = roleResService.getById(Long.parseLong(roleResId));
		if(resources != null){
			ISysRole sysRole = sysRoleService.getById(resources.getRoleId());
			Resources res = resourcesService.getById(resources.getResId());
			Map<String, Object> rmap=new HashMap<String, Object>();
			rmap.put("role", sysRole);
			rmap.put("res", res);			
			map.put("success", true);
			map.put("code", "100001");
			map.put("msg", rmap);
		}else{
			map.put("success", false);
			map.put("errorCode", "100052");
			map.put("error", "未查询到该资源信息");
		}
		return map;
	}
	
	
	/**
	 * 角色修改资源
	 * */
	@RequestMapping(value="updateResoById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResoById(@RequestBody DataParam data,HttpServletRequest request) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		if(data.getData().isEmpty() || !data.getData().containsKey("roleResId") || !data.getData().containsKey("resId") || !data.getData().containsKey("roleId") ){
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;			
		}
		String roleResId = StringUtil.getString(data.getData().get("roleResId"));
		String resId = StringUtil.getString(data.getData().get("resId"));
		String roleId = StringUtil.getString(data.getData().get("roleId"));
		String systemId = data.getSystemId();
		RoleResources entity=new RoleResources();
		if(roleId!=null && resId!=null && !"".equals(roleId) && !"".equals(resId) && roleResId!=null && !"".equals(roleResId)){
			entity.setRoleResId(Long.parseLong(roleResId));
			entity.setResId(Long.parseLong(resId));
			entity.setRoleId(Long.parseLong(roleId));
			entity.setSystemId(Long.parseLong(systemId));
		}else{
			map.put("success", false);
			map.put("errorCode", "100009");
			map.put("error", "请填写正确的参数");
			return map;
		}
		try {
			boolean flag = isResAndRoleExist(request, resId, roleId);
			if(flag){
				//资源已存在
				map.put("success", false);
				map.put("code", "100016");
				map.put("msg", "角色资源已存在！请重新分配");
				return map;
			}
			roleResService.update(entity);
			map.put("success", true);
			map.put("code", "100001");
			map.put("msg", "角色资源更新成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("success", false);
			map.put("errorCode", "100012");
			map.put("error", "角色资源更新失败");
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 根据角色查询所有资源
	 * */
	@RequestMapping(value="getRessByRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRessByRole(HttpServletRequest request,@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(dmap.isEmpty() && !dmap.containsKey("roleId")){
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数可以key错误");
			return map;	
		}
		String roleId = StringUtil.getString(dmap.get("roleId"));
		if(roleId!=null && !"".equals(roleId)){
			QueryFilter queryFilter=new QueryFilter(request,false);
			queryFilter.addFilter("roleId", roleId);
			queryFilter.addFilter("systemId", data.getSystemId());
			List<RoleResources> rrs = roleResService.getAll(queryFilter);
			if(!rrs.isEmpty()){
				List<Resources> sos=new ArrayList<Resources>();
				for (RoleResources rs : rrs) {
					Long resId = rs.getResId();
					Resources resources = resourcesService.getById(resId);
					sos.add(resources);					
				}
				if(!sos.isEmpty()){
					map.put("success", true);
					map.put("code", "100001");
					map.put("msg", sos);
					return map;
				}else{
					map.put("success", false);
					map.put("errorCode", "100052");
					map.put("error", "未查询到该资源信息");
					return map;
				}				
			}else{
				map.put("success", true);
				map.put("code", "100001");
				map.put("msg", "该角色没有资源");
				return map;
			}
			
		}else{
			map.put("success", false);
			map.put("errorCode", "100052");
			map.put("error", "请填写正确的参数");
			return map;			
		}		
	} 	
	/**
	 * 给角色删除资源
	 * */
	@RequestMapping(value="delRes",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delRes(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(dmap.isEmpty() || dmap.containsKey("roleResId")){		
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;
		}
		String roleResId = StringUtil.getString(dmap.get("roleResId"));
		if(roleResId!=null && !"".equals(roleResId)){
			try {
				RoleResources roleRes = roleResService.getById(Long.parseLong(roleResId));
				if(roleRes==null){
					map.put("success", false);
					map.put("code", "100055");
					map.put("msg", "该角色下无此资源,删除失败");
					return map;
				}				
				roleResService.delById(Long.parseLong(roleResId));
				map.put("success", true);
				map.put("code", "100001");
				map.put("msg", "角色资源删除成功");
				return map;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				map.put("success", false);
				map.put("errorCode", "100053");
				map.put("error", "角色资源删除失败");
				e.printStackTrace();
			}
		}
		return map;	
	}
	/**
	 * 是否禁用
	 * */
	@RequestMapping(value="isOpen",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> isOpen(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(dmap.isEmpty() || dmap.containsKey("resId") || dmap.containsKey("isOpen")){		
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;
		}
		String resId = StringUtil.getString(dmap.get("resId"));
		String isOpen = StringUtil.getString(dmap.get("isOpen"));
		if(resId == null || "".equals(resId) || isOpen==null || "".equals(isOpen)){
			map.put("success", false);
			map.put("errorCode", "100009");
			map.put("error", "参数错误");
			return map;
		}
		Resources entity = resourcesService.getById(Long.parseLong(resId));
		if(entity!=null){
			if("0".equals(isOpen)){
				//设置成禁用
				entity.setIsOpen(Short.valueOf("0"));
			}else if("1".equals(isOpen)){
				//设置成启用
				entity.setIsOpen(Short.valueOf("1"));
			}else{
				map.put("success", false);
				map.put("errorCode", "100009");
				map.put("error", "参数错误");
				return map;				
			}
			
		}
		try {
			resourcesService.update(entity);
			map.put("success", true);
			map.put("code", "100001");
			map.put("msg", "设置成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("code", "100051");
			map.put("msg", "设置失败");
			e.printStackTrace();
		}
		
		return map;		
	}
	/**
	 * 根据父Id查询该父id下所具有的资源信息
	 * @throws Exception 
	 * */
	@RequestMapping(value="getResByParentId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getResByParentId(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(dmap.isEmpty() || !dmap.containsKey("parentId")){
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;
		}
		String parentId = StringUtil.getString(dmap.get("parentId"));
		if(parentId==null || "".equals(parentId)){
			//parentId为空或者为“”，则查询一级菜单
			parentId="0";
		}
		//显示到菜单的数据
		List<Resources> parResou = resourcesService.getByParentId(Long.parseLong(parentId));
		if(!parResou.isEmpty()){
			map.put("success", true);
			map.put("code", "100001");
			map.put("msg", parResou);
			return map;
		}else{
			map.put("success", false);
			map.put("code", "100054");
			map.put("msg", "该父Id下无资源");
			return map;
		}	
	}
	
	/**
	 * 查询所有企业列表接口
	 */
	
	@RequestMapping(value="getAllSysOrgInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getAllSysOrgInfo(HttpServletRequest request,NewSysOrgInfo newSysOrgInfo){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int cpage = (Integer.parseInt(request.getParameter("cpage")));
		int pageSize = (Integer.parseInt(request.getParameter("pageSize")));
		if(cpage==0){
			cpage = 1;
		}
		if(newSysOrgInfo.getCreatetime()!=null&&newSysOrgInfo.getUpdatetime()!=null){
			dataMap.put("errorCode", "500");
			dataMap.put("errorMsg", "只能传入一个参数");
			return dataMap;
		}
		if(StringUtils.isEmpty(newSysOrgInfo.getCreatetime())&&StringUtils.isEmpty(newSysOrgInfo.getUpdatetime())){
			newSysOrgInfo.setCreatetime("1");
		}
		int lmt = (cpage-1)*pageSize;
		List<NewSysOrgInfo> list = newSysOrgInfoService.getAllSysOrgInfo(lmt,pageSize,newSysOrgInfo);
		int size = newSysOrgInfoService.getCount();
		int pages = (size%pageSize==0?size/pageSize:size/pageSize+1);//总页数
		dataMap.put("list", list);
		dataMap.put("total", size);
		dataMap.put("pages", pages);
    	dataMap.put("cpage", cpage);
		return dataMap;
		
	}
	
	/**
	 * 根据资源Id获取资源信息
	 * @throws Exception 
	 * */
	@RequestMapping(value="getResById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getResById(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(dmap.isEmpty() || !dmap.containsKey("resId")){
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;
		}
		String resId = StringUtil.getString(dmap.get("resId"));
		if(resId != null && !"".equals(resId)){
			Resources resources = resourcesService.getById(Long.parseLong(resId));
			if(resources!=null){
				map.put("success", true);
				map.put("code", "100001");
				map.put("msg", resources);
			}else{
				map.put("success", false);
				map.put("code", "100052");
				map.put("msg", "未查询到该资源信息");
			}
			return map;
		}else{
			map.put("success", false);
			map.put("errorCode", "100009");
			map.put("error", "请填写正确的参数");
			return map;
		}	
	}
	
	public static Map<String, Object> getMapFromJsonObjStr(String jsonObjStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}
	
	
	/**
	 * 通过token获得用户信息
	 * 
	 * */
	@RequestMapping(value="getUserByToken",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserByToken(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		String token = "";
		if(!dmap.isEmpty() && dmap.containsKey("token")){
			token= StringUtil.getString(dmap.get("token"));
			if(token == null || "".equals(token.trim())){
				map.put("success", false);
				map.put("errorCode", "510");
				map.put("error", "token值不能为空!");
				return map;
			}
			else{
				ISysUser sysUser = (ISysUser) CacheUtil.getUserCache().getByKey(token);
				if(sysUser==null){
					map.put("success", false);
					map.put("errorCode", "511");
					map.put("error", "token错误，未查询到该用户");
				}else{
					map.put("success", true);
					map.put("code", 200);
					map.put("user", sysUser);
				}
				CacheUtil.getUserCache().delByKey(token);
			}
		}
		return map;
		
	}
	
	
	@RequestMapping(value="getUserByNameAndPas",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserByNameAndPas(@RequestBody DataParam data) throws Exception{
		Map<String, Object> map = isSubsysExist(data);
		if(!map.isEmpty()){
			return map;
		}
		Map<String, Object> dmap = data.getData();
		if(!dmap.containsKey("userName") || !dmap.containsKey("passWord")){
			map.put("success", false);
			map.put("errorCode", "100010");
			map.put("error", "参数key错误");
			return map;
		}		
		String username = StringUtil.getString(dmap.get("userName"));
		String pw = StringUtil.getString(dmap.get("passWord"));
		if(username==null || "".equals(username) || pw==null || "".equals(pw)){
			map.put("success", false);
			map.put("errorCode", "100009");
			map.put("error", "请填写正确的参数");
			return map;
		}
		List<ISysUser> users = sysUserService.getByVip(username, EncryptUtil.encryptSha256(pw));
		if(users.isEmpty()){
			map.put("success", false);
			map.put("errorCode", "100031");
			map.put("error", "用户不存在");
			return map;		
		}
		ISysUser sysUser = users.get(0);
		if(sysUser==null){
			map.put("success", false);
			map.put("errorCode", "100031");
			map.put("error", "用户不存在");
			return map;
		}
		map.put("success", true);
		map.put("code","100001");
		map.put("msg",sysUser);
		return map;
	
	}
	
	public  Map<String,Object> getUserFromMapping(String st) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		JSONObject jb=new JSONObject();
		JSONObject json = jb.fromObject(st);

		//创建用户
		SysUser sysuser=new SysUser();
		TenantInfo tf=new TenantInfo();
		//校验st
		if(json.containsKey("url")){
			String url = StringUtil.getString(json.get("url"));
			if(!RegexValidateUtil.checkURL(url)){
				map.put("success", false);
				map.put("msg", "url形式错误");
				return map;
			}
			//校验systemid
			if(url==null &&url.trim()==""){
				map.put("success", false);
				map.put("msg", "url参数错误");
				return map;
			}	
		}
		if(json.containsKey("systemId")){
			String systemId = StringUtil.getString(json.get("systemId"));
			if(!RegexValidateUtil.isNumeric(systemId)){
				map.put("success", false);
				map.put("msg", "systemId为数字");
				return map;
			}
			//校验特殊字符
		
			//校验systemid
			if(systemId!=null &&systemId.trim()!=""){
				SubSystem system = subSystemService.getById(Long.parseLong(systemId));
				if(system==null){
					map.put("success", false);
					map.put("msg", "系统标识不存在");
				}
			}else{
				map.put("success", false);
				map.put("msg", "系统标识错误");
			}	
		}
		UserData ud=new UserData();
		TenantData td=new TenantData();
		if(json.containsKey("data")){
			String data = StringUtil.getString(json.get("data"));
			if(data!=null && data != ""){
				JSONObject jsonObject = jb.fromObject(data);
				if(jsonObject.containsKey("tenant") && jsonObject.containsKey("user")){
					String user = StringUtil.getString(jsonObject.get("user"));
					String tenant = StringUtil.getString(jsonObject.get("tenant"));
					JSONObject jbj = jb.fromObject(user);
					//拿到用户信息
					if(jbj.containsKey("userId") && jbj.containsKey("mobile") && jbj.containsKey("fullName") && jbj.containsKey("account")){
						String userId = StringUtil.getString(jbj.get("userId"));
						String mobile = StringUtil.getString(jbj.get("mobile"));
						String fullName = StringUtil.getString(jbj.get("fullName"));
						String account = StringUtil.getString(jbj.get("account"));
						if(userId!=null && userId!=""){
							if(RegexValidateUtil.isNumberOrEn(userId)){
								ud.setUserId(userId);
							}else{
								map.put("success", false);
								map.put("msg", "userId为数字");
								return map;
							}							
						}else{
							map.put("success", false);
							map.put("msg", "请传入平台的用户Id");
							return map;
						}
						if(mobile != null && mobile!=""){
							if(!RegexValidateUtil.checkMobileNumber(mobile)){
								map.put("success", false);
								map.put("msg", "请输入正确的手机号码");
								return map;
							}
							List<ISysUser> moblies = sysUserService.getByMobile(mobile);
							if(!moblies.isEmpty() && moblies.size()>0){
								map.put("success", false);
								map.put("msg", "手机号已存在！请重新输入");
								return map;
							}
							sysuser.setMobile(mobile);
							ud.setMobile(mobile);
						}else{
							map.put("success", false);
							map.put("msg", "请传入手机号码");
							return map;
						}
						if(fullName != null && fullName!=""){
							if(!RegexValidateUtil.checkChinaEnAndNum(fullName)){
								map.put("success", false);
								map.put("msg", "fullname只支持中文、数字、英文、下划线组合");
								return map;
							}	
							sysuser.setFullname(fullName);
							ud.setFullName(fullName);
						}else{
							map.put("success", false);
							map.put("msg", "请传入用户姓名");
							return map;
						}
						
						if(account != null && account!=""){
							if(!RegexValidateUtil.checkEnAndNum(account)){
								map.put("success", false);
								map.put("msg", "账号只支持数字、英文、下划线组合");
								return map;
							}
							ISysUser iSysUser = sysUserService.getByAccount("ahjxw_"+account);
							if(iSysUser==null){
								sysuser.setAccount("ahjxw_"+account);
							}else{
								sysuser.setAccount("ahjxw_"+account+"_2");
							}							
							ud.setAccount("ahjxw_"+account);
						}else{
							map.put("success", false);
							map.put("msg", "请传入用户平台登录账户");
							return map;
						}						
					}else{
						map.put("success", false);
						map.put("msg", "请填写完整的用户参数");
						return map;
					}					
					//拿到传过来的企业信息
					if(jsonObject.containsKey("tenantId") && jsonObject.containsKey("tenantName")){
						String tenantId = StringUtil.getString(jsonObject.get("tenantId"));
						String tenantName = StringUtil.getString(jsonObject.get("tenantName"));
						String address = StringUtil.getString(jsonObject.get("address"));
						String connecter = StringUtil.getString(jsonObject.get("connecter"));
						String tel = StringUtil.getString(jsonObject.get("tel"));
						String city = StringUtil.getString(jsonObject.get("city"));
						String code = StringUtil.getString(jsonObject.get("code"));
						String registertime = StringUtil.getString(jsonObject.get("registertime"));
						String regCapital = StringUtil.getString(jsonObject.get("regCapital"));
						String incorporator = StringUtil.getString(jsonObject.get("incorporator"));
						String gszch = StringUtil.getString(jsonObject.get("gszch"));
						String nsrsbh = StringUtil.getString(jsonObject.get("nsrsbh"));
						if(tenantId!=null && tenantId!=""){
							if(!RegexValidateUtil.isNumeric(tenantId)){
								map.put("success", false);
								map.put("msg", "tenantId只支持数字");
								return map;
							}
							td.setTenantId(tenantId);
						}else{
							map.put("success", false);
							map.put("msg", "请填写tenantId信息");
							return map;
						}
						if(tenantName!=null && tenantName!=""){
							if(!RegexValidateUtil.checkChinese(tenantName)){
								map.put("success", false);
								map.put("msg", "tenantName只支持汉字");
								return map;
							}
							td.setTenantName(tenantName);
							tf.setName(tenantName);
						}else{
							map.put("success", false);
							map.put("msg", "请填写tenantName信息");
							return map;
						}
						if(address!=null && address!=""){
							if(!RegexValidateUtil.checkChinaEnAndNum(address)){
								map.put("success", false);
								map.put("msg", "address只支持汉字、数字、英文字母");
								return map;
							}
							td.setAddress(address);
							tf.setAddress(address);
						}else{
							map.put("success", false);
							map.put("msg", "请填写address信息");
							return map;
						}
						if(connecter!=null && connecter!=""){
							if(!RegexValidateUtil.checkChinaEnAndNum(connecter)){
								map.put("success", false);
								map.put("msg", "connecter只支持汉字、数字、英文字母");
								return map;
							}
							td.setConnecter(connecter);
							tf.setConnecter(connecter);
						}else{
							map.put("success", false);
							map.put("msg", "请填写connecter信息");
							return map;
						}
						if(tel!= null && tel!=""){
							if(!RegexValidateUtil.checkMobileNumber(tel)){
								map.put("success", false);
								map.put("msg", "请正确的tel号码");
								return map;
							}
							td.setTel(tel);
							tf.setTel(tel);
						}else{
							map.put("success", false);
							map.put("msg", "请填写tel信息");
							return map;
						}
						if(city!=null && city!=""){
							if(!RegexValidateUtil.checkChinaEnAndNum(city)){
								map.put("success", false);
								map.put("msg", "city只支持汉字");
								return map;
							}
							
							td.setCity(city);
							tf.setCity(city);
						}else{
							map.put("success", false);
							map.put("msg", "请填写city信息");
							return map;
						}
						if(code!=null && code!=""){
							if(!RegexValidateUtil.isNumeric(code)){
								map.put("success", false);
								map.put("msg", "code只支持数字");
								return map;
							}
							td.setCode(code);
							tf.setCode(code);
						}else{
							map.put("success", false);
							map.put("msg", "请填写code信息");
							return map;
						}
						if(registertime!=null && registertime!=""){
							td.setRegistertime(registertime);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");	
							tf.setRegistertime(sdf.parse(registertime));
						}else{
							map.put("success", false);
							map.put("msg", "请填写registertime信息");
							return map;
						}
						if(regCapital!=null && regCapital!=""){
							if(!RegexValidateUtil.isNumeric(regCapital)){
								map.put("success", false);
								map.put("msg", "regCapital只支持数字");
								return map;
							}
							td.setRegCapital(regCapital);
							tf.setRegCapital(regCapital);
						}else{
							map.put("success", false);
							map.put("msg", "请填写regCapital信息");
							return map;
						}
						if(incorporator!=null && incorporator!=""){
							if(!RegexValidateUtil.checkChinese(incorporator)){
								map.put("success", false);
								map.put("msg", "incorporator只支持汉字");
								return map;
							}
							td.setIncorporator(incorporator);
							tf.setIncorporator(incorporator);
						}else{
							map.put("success", false);
							map.put("msg", "请填写incorporator信息");
							return map;
						}
						if(gszch!=null && gszch!=""){
							if(!RegexValidateUtil.checkEnAndNum(gszch)){
								map.put("success", false);
								map.put("msg", "gszch只支持数字和英文");
								return map;
							}
							td.setGszch(gszch);
							tf.setGszch(gszch);
						}else{
							map.put("success", false);
							map.put("msg", "请填写gszch信息");
							return map;
						}
						if(nsrsbh!=null && nsrsbh!=""){
							if(!RegexValidateUtil.checkEnAndNum(nsrsbh)){
								map.put("success", false);
								map.put("msg", "nsrsbh只支持数字和英文");
								return map;
							}
							td.setNsrsbh(nsrsbh);
							tf.setNsrsbh(nsrsbh);
						}else{
							map.put("success", false);
							map.put("msg", "请填写nsrsbh信息");
							return map;
						}			
					}else{
						map.put("success", false);
						map.put("msg", "企业信息缺少必要的数据");
						return map;						
					}										
				}else{
					map.put("success", false);
					map.put("msg", "未含有tenant或者user参数");
					return map;
				}			
			}else{
				map.put("success", false);
				map.put("msg", "data参数错误");
				return map;
			}		
		}else{
			map.put("success", false);
			map.put("msg", "未含有data参数");
			return map;
		}		
		//已拿到用户信息和映射用户信息
		if(sysuser!=null && ud!=null && td!=null && tf!=null){
			map.put("success", true);
			map.put("user", sysuser);
			map.put("userData", ud);
			map.put("tenantData", td);
			map.put("tenantInfo", tf);
			
		}
		return map;
	}

	

	/**
	 * 通过token获得用户信息
	 * 
	 * */
	@RequestMapping(value="getUser",method=RequestMethod.POST)
	@ResponseBody
	public ISysUser getUserByToken(@RequestBody OauthParam op) throws Exception{
		String access_token = op.getAccess_token();
		String openId = op.getOpenId();
		OauthParam cacheOp = new OauthParam();
		cacheOp = (OauthParam) CacheUtil.getUserCache().getByKey(access_token);
		ISysUser user =null;
		String cacheOpenId =cacheOp.getOpenId();
		//用户传的openId与缓存的openId一致则返回用户信息
		if(openId.equals(cacheOpenId)){
			user = sysUserService.getByOpenId(openId);
		}
		return user;
		
	}	
	
	public Map<String,Object> checkTenant(String name,HttpServletRequest request){
		Map<String,Object> map=new HashMap<String, Object>();
		
		QueryFilter qf=new QueryFilter(request);
		qf.addFilter("NAME", name);
		List<TenantInfo> tenants = tenantInfoService.getAll(qf);
		if(!tenants.isEmpty() && tenants.size()>0){
			map.put("success", false);
			map.put("msg", "企业信息已存在");
			return map;
		}else{
			map.put("success", true);
			map.put("msg", "企业信息不存在");
			return map;
		}		
	}	
	  /**
	   * 开发用户注册的接口，供用户批量导入时调用
	   */
	
	@RequestMapping(value = "userBatchRegistercheck", method = RequestMethod.POST)
	@Action(description = "各子系统调用的")
	@ResponseBody
	public void userBatchRegister(@RequestBody List<UcTenant> datas,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//如果参数缺省，默认返回
		if(BeanUtils.isEmpty(datas)||datas.size()==0){
			 result(response, ReturnCode.REQUEST_PARAMETER_IS_EMPTY, new Object());
		     return;
		}
		//遍历循环信息list里面的
		List<SysOrgInfoRes> infoRes=new ArrayList<SysOrgInfoRes>();
		Integer total=0;//总记录数
		Integer successTotal=0;//成功记录数
		List<TenantInfo> list = new ArrayList<TenantInfo>(); // 存放未能导入的主数据
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < datas.size(); i++) {
			 total++;
			Map<String, Object> map=(Map<String, Object>) datas.get(i);
			JSONObject obj=  JSONObject.fromObject(map);
			UcTenant data=  (UcTenant) JSONObject.toBean(obj, UcTenant.class);
			ResultMessage msg = validData(data);
			
			if(msg.getResult() == 0){
				//如果校验未通过
				result(response, ReturnCode.REQUEST_PARAMETER_EXCEPTION, new Object());
				return;
			}else{
				//校验每一个字段
				
				SysOrgInfoRes res=new SysOrgInfoRes();
				TenantInfo tenantInfo=new TenantInfo();
				res.setNumber(i);
				res.setResult(true);
				String tname=data.getTname();//获取企业名称
				String mobile=data.getMobile();//获取管理人员手机号
				String email=data.getEmail();//获取邮箱信息
				String industry=data.getIndustry();//获取行业信息
				String province=data.getProvince();//获取省
				String city=data.getCity();//获取市
				String connector=data.getConnecter();//获取企业的联系人
				String address=data.getAddress();//获取企业的地址
				String fax=data.getFax();//获取企业的传真
				String invitedCode=data.getInvitedCode();//获取企业的邀请码
				String systemId=data.getSystemId();//获取企业的系统来源
				String topenId=data.getTopenId();//获取企业的openId
				String uopenId=data.getUopenId();//获取用户的uopenId
				checkTenantName(request, res, tenantInfo, tname);
				checkTenantMobile(request, res, tenantInfo,mobile);
				checkEmail(res, tenantInfo, email);
				checkIndustry(res, tenantInfo, industry);
				checkProvince(res, tenantInfo, province);
				checkCity(res, tenantInfo, city);
				checkConnector(res, tenantInfo, connector);
				checkAddress(res, tenantInfo, address);
				checkFax(res, tenantInfo, fax);
				checkInviticode(request, res, tenantInfo, invitedCode);
				checkSystemId(request, res, tenantInfo, systemId);
				checkTopenId(request, res, tenantInfo, topenId);
				checkUopenId(request, res, tenantInfo, uopenId);
				infoRes.add(res);
				if(res.isResult()){
					 successTotal++;
					 tenantInfo.setMark("3");//批量导入接口									 
				     list.add(tenantInfo);
				  }
				
			}
			//保存企业信息
			
			if(successTotal>0){
				SysUser sysUser=new SysUser();
				boolean isRequired=false;
			  for (TenantInfo  tenant : list) {
				  //保存数据
					Map<String, Object> resultMap= saveOrgAndUserInfo(tenant,sysUser,isRequired);
					resultList.add(resultMap);
			  }
			}
		}
		StringBuffer msg=new StringBuffer();
		msg.append("共"+total+"条记录,成功"+successTotal+"条<br>");
		for (SysOrgInfoRes info : infoRes) {
			//构造回调信息
			returnMsg(msg, info);
		}
		result(response, ReturnCode.CALLBACK_INFORMATION, msg.toString(),resultList);		
	}
	








	private void returnMsg(StringBuffer msg, SysOrgInfoRes info) {
		if(!info.isResult()){
			//导入出现失败info.getNumber
			msg.append("第"+info.getNumber()+"行:");
			//公司名称验证
			if(("0000").equals(info.getNameCode())){
				msg.append("公司名称只能是由数字、英文字母、汉字或者下划线组成的字符串,");
			}
			if(("0002").equals(info.getNameCode())){
				msg.append(EnumUtils.NAMECHEAPT.getName()+",");
			}
			if(("0004").equals(info.getNameCode())){
				msg.append(EnumUtils.NameEmpty.getName()+",");
			}
			//手机号校验
			if(("0000").equals(info.getMobileCode())){
				msg.append("手机验证不合法,");
			}
			if(("0003").equals(info.getMobileCode())){
				msg.append(EnumUtils.MOBILECHEAPT.getName()+",");
			}
			if(("0006").equals(info.getMobileCode())){
				msg.append("手机号含有"+EnumUtils.SPECIAL.getName()+",");
			}
			if(("0008").equals(info.getMobileCode())){
				msg.append("手机号"+EnumUtils.NotEmpty.getName()+",");
			}
			if(("0006").equals(info.getFaxCode())){
				msg.append("传真中含有"+EnumUtils.SPECIAL.getName()+",");
			}
			if(("0000").equals(info.getEmailCode())){
				msg.append("邮箱"+EnumUtils.NOVALID.getName()+",");
			}
			if(("0001").equals(info.getEmailCode())){
				msg.append(EnumUtils.OVERlENGTH.getName()+",");
			}
			//行业验证
			if(("0000").equals(info.getIndustryCode())){
				msg.append("行业只能是由数字、英文字母、汉字或者下划线组成的字符串,");
			}
			//行业验证
			if(("0008").equals(info.getIndustryCode())){
				msg.append("行业"+EnumUtils.NotEmpty.getName()+",");
			}
			//省份验证
			if(("0000").equals(info.getProvinceCode())){
				msg.append("省份中含有特殊字符,");
			}
			//省份验证
			if(("0008").equals(info.getProvinceCode())){
				msg.append("省份"+EnumUtils.NotEmpty.getName()+",");
			}
			//城市验证
			if(("0000").equals(info.getCityCode())){
				msg.append("城市中含有特殊字符,");
			}
			//城市验证
			if(("0008").equals(info.getCityCode())){
				msg.append("城市"+EnumUtils.NotEmpty.getName()+",");
			}
			//企业联系人验证
			if(("0000").equals(info.getConnectorCode())){
				msg.append("联系人只能是由数字、英文字母、汉字或者下划线组成的字符串,");
			}
			//企业联系人验证
			if(("0008").equals(info.getConnectorCode())){
				msg.append("联系人"+EnumUtils.NotEmpty.getName()+",");
			}
			//地址验证
			if(("0000").equals(info.getAddressCode())){
				msg.append("地址只能是由数字、英文字母、汉字或者下划线组成的字符串,");
			}
			//地址验证
			if(("0008").equals(info.getAddressCode())){
				msg.append("地址"+EnumUtils.NotEmpty.getName()+",");
			}
			
			//传真验证
			if(("0000").equals(info.getFaxCode())){
				msg.append("传真验证不合法,");
			}
			//邀请码验证
			if(("0006").equals(info.getInviteCode())){
				msg.append("邀请码含有非数字,");
			}
			//邀请码验证
			if(("0007").equals(info.getInviteCode())){
				msg.append("邀请码不存在,");
			}
			//系统来源验证
			if(("0006").equals(info.getSystemIdCode())){
				msg.append("系统来源中含有非数字,");
			}
			//系统来源验证
			if(("0009").equals(info.getSystemIdCode())){
				msg.append("系统来源不存在,");
			}
			//企业openId验证
			if(("0000").equals(info.getTopenIdCode())){
				msg.append("企业openId中含有不合法字符,");
			}
			//企业openId验证
			if(("00010").equals(info.getTopenIdCode())){
				msg.append("企业openId已存在,");
			}
			//用户openId验证
			if(("0006").equals(info.getUopenIdCode())){
				msg.append("用户openId中含有不合法字符,");
			}
			//用户openId验证
			if(("00010").equals(info.getUopenIdCode())){
				msg.append("用户openId已存在,");
			}
			
		   if(msg.toString().endsWith(","))
			msg.deleteCharAt(msg.lastIndexOf(","));
				
		   msg.append("<br>");
		}
	}

	private void checkUopenId(HttpServletRequest request, SysOrgInfoRes res,
			TenantInfo tenantInfo, String uopenId) throws Exception {
		if(!com.casic.util.StringUtil.isEmpty(uopenId)){
			 if(RegexValidateUtil.isNumberOrEn(uopenId)){
				 boolean flag2=checkUopenId(request,uopenId);
				 if(flag2){
					 tenantInfo.setUopenId(uopenId);}
				 else{
					 res.setResult(false);
					 res.setUopenIdCode((EnumUtils.EXIST).getCode());
				 }
			 }
			 else {
				 res.setResult(false);
				 res.setUopenIdCode((EnumUtils.NOVALID).getCode());
			 }
		}
	}

	private void checkTopenId(HttpServletRequest request, SysOrgInfoRes res,
			TenantInfo tenantInfo, String topenId) throws Exception {
		if(!com.casic.util.StringUtil.isEmpty(topenId)){
			 if(RegexValidateUtil.isNumberOrEn(topenId)){
				 boolean flag2=checkTopenId(request,topenId);
				 if(flag2){
					 tenantInfo.setOpenId(topenId);
					 }
				 else{
					 res.setResult(false);
					 res.setTopenIdCode((EnumUtils.EXIST).getCode());
				 }
			 }
			 else {
				 res.setResult(false);
				 res.setTopenIdCode((EnumUtils.NOVALID).getCode());
			 }
		 }
	}








	private void checkSystemId(HttpServletRequest request, SysOrgInfoRes res,
			TenantInfo tenantInfo, String systemId) throws Exception {
		if(!com.casic.util.StringUtil.isEmpty(systemId)){
			 //邀请码非空
			 if(RegexValidateUtil.isNumeric(systemId)){
					boolean flag2=checkSystemId(request,systemId);
				 if(flag2){
				    tenantInfo.setSystemId(systemId);}
				 else{
					 res.setResult(false);
					 res.setSystemIdCode((EnumUtils.NOTEXIST).getCode());
				 }
			 }
			 else {
				 res.setResult(false);
				 res.setSystemIdCode((EnumUtils.SPECIAL).getCode());
			}
		 }
	}
	private void checkInviticode(HttpServletRequest request, SysOrgInfoRes res,
			TenantInfo tenantInfo, String invitedCode) throws Exception {
		if(!com.casic.util.StringUtil.isEmpty(invitedCode)){
			 //邀请码非空
			 if(RegexValidateUtil.isNumeric(invitedCode)){
					boolean flag2=checkOrgId(request,invitedCode);
				 if(flag2){
				    tenantInfo.setInvititedCode(invitedCode);}
				 else{
					 res.setResult(false);
					 res.setInviteCode((EnumUtils.INVICODE).getCode());
				 }
			 }
			 else {
				 res.setResult(false);
				 res.setInviteCode((EnumUtils.SPECIAL).getCode());
			}
		 }
	}

	private void checkFax(SysOrgInfoRes res, TenantInfo tenantInfo, String fax) {
		if(!com.casic.util.StringUtil.isEmpty(fax)){
		     if(RegexValidateUtil.isNumeric(fax)){
		     tenantInfo.setFax(fax);
		   }else{
			 res.setResult(false);
			 res.setFaxCode((EnumUtils.SPECIAL).getCode());
		   }
		 }
	}
	private void checkAddress(SysOrgInfoRes res, TenantInfo tenantInfo,
			String address) {
		if(!(com.casic.util.StringUtil.isEmpty(address))){
			 Matcher matcher = isIllegalAddress(address);
			 if(!matcher.matches()){
				 res.setResult(false);
				 res.setAddressCode((EnumUtils.NOVALID).getCode());
			 }
			 
		 }else{
			 res.setResult(false);
			 res.setAddressCode((EnumUtils.NotEmpty).getCode());
		 }
		 if(com.casic.util.StringUtil.isEmpty(res.getAddressCode())||!("0000").equals(res.getAddressCode())){
			 tenantInfo.setAddress(address);
		 }
	}
	private void checkConnector(SysOrgInfoRes res, TenantInfo tenantInfo,
			String connector) {
		if(!com.casic.util.StringUtil.isEmpty(connector)){
			 Matcher matcher = isIllegal(connector);
			 if(!matcher.matches()){
				 res.setResult(false);
				 res.setConnectorCode((EnumUtils.NOVALID).getCode());
			 }
			 
		 }else{
			 res.setResult(false);
			 res.setConnectorCode((EnumUtils.NotEmpty).getCode());
		 }
		 if(com.casic.util.StringUtil.isEmpty(connector)||!("0000").equals(res.getConnectorCode())){
			 tenantInfo.setConnecter(connector);
		 }
	}

	private void checkCity(SysOrgInfoRes res, TenantInfo tenantInfo, String city) {
		if(!com.casic.util.StringUtil.isEmpty(city)){
				
			 if(!RegexValidateUtil.checkChinese(city)){
				 res.setResult(false);
				 res.setCityCode((EnumUtils.NOVALID).getCode());
			 }
			 
		 }else{
			 res.setResult(false);
			 res.setCityCode((EnumUtils.NotEmpty).getCode());
		 }
		 if(com.casic.util.StringUtil.isEmpty(res.getCityCode())||!("0000").equals(res.getCityCode())){
			 tenantInfo.setCity(city);
		 }
	}
	private void checkProvince(SysOrgInfoRes res, TenantInfo tenantInfo,
			String province) {
		if(!com.casic.util.StringUtil.isEmpty(province)){

			 if(!RegexValidateUtil.checkChinese(province)){
				 res.setResult(false);
				 res.setProvinceCode((EnumUtils.NOVALID).getCode());
			 }
			 
		 }else{
			 res.setResult(false);
			 res.setProvinceCode((EnumUtils.NotEmpty).getCode());
		 }
		 if(com.casic.util.StringUtil.isEmpty(res.getProvinceCode())||!("0000").equals(res.getProvinceCode())){
			 tenantInfo.setProvince(province);
		 }
	}

	private void checkIndustry(SysOrgInfoRes res, TenantInfo tenantInfo,
			String industry) {
		if(!com.casic.util.StringUtil.isEmpty(industry)){
			 Matcher matcher = isIllegalIndustry(industry);
			 if(!matcher.matches()){
				 res.setResult(false);
				 res.setIndustryCode((EnumUtils.NOVALID).getCode());
			 }
			 
		 }else{
			 res.setResult(false);
			 res.setIndustryCode((EnumUtils.NotEmpty).getCode());
		 }
		 if(com.casic.util.StringUtil.isEmpty(res.getIndustryCode())||!("0000").equals(res.getIndustryCode())){
			 tenantInfo.setIndustry(industry);
		 }
	}
	private void checkEmail(SysOrgInfoRes res, TenantInfo tenantInfo,
			String email) {
			 if(!com.casic.util.StringUtil.isEmpty(email)){
				 if((email.length() >=5) && (email.length() <=30)){
				 if(RegexValidateUtil.checkEmail(email)){
					 res.setEmailCode((EnumUtils.SUCCESS).getCode());
				 }else{
					 res.setResult(false);
					 res.setEmailCode((EnumUtils.NOVALID).getCode());
				 }
			 }
			 else{
				 res.setResult(false);
				 res.setEmailCode((EnumUtils.OVERlENGTH).getCode());
			 }
			
			 }
			 if(com.casic.util.StringUtil.isEmpty(email)||(!("0000").equals(res.getEmailCode())&&!("0001").equals(res.getEmailCode()))){
				 tenantInfo.setEmail(email);
			 }
	}
	private void checkTenantMobile(HttpServletRequest request,
			SysOrgInfoRes res,TenantInfo tenantInfo, String mobile) {
		if(!com.casic.util.StringUtil.isEmpty(mobile)){
			 if(RegexValidateUtil.isNumeric(mobile)){
		 
		if(RegexValidateUtil.checkMobileNumber(mobile))
		{ 
			boolean flag2=checkMobileRepeat(mobile,request);
		    if(flag2){
		    	  res.setMobileCode((EnumUtils.SUCCESS).getCode());
		             }
		     else{
			     res.setResult(false);
			     res.setMobileCode((EnumUtils.MOBILECHEAPT).getCode());
		                  }
												
		  }
		else 
		{
			res.setResult(false);
		    res.setMobileCode((EnumUtils.SPECIAL).getCode());
		}
		     
		 }else{
			  res.setResult(false);
			  res.setMobileCode((EnumUtils.SPECIAL).getCode()); 
		 }
		 }else{
			 res.setResult(false);
			 res.setMobileCode((EnumUtils.NotEmpty).getCode()); 
		}
		 if(com.casic.util.StringUtil.isEmpty(res.getMobileCode())||(!("0000").equals(res.getMobileCode())&&!("0003").equals(res.getMobileCode()))){
			 tenantInfo.setTel(mobile);
		 }
	}
	private void checkTenantName(HttpServletRequest request, SysOrgInfoRes res,
			TenantInfo tenantInfo, String tname) {
		if(!com.casic.util.StringUtil.isEmpty(tname)){
		    Matcher matcher = isIllegalName(tname);
			if(!matcher.matches()){
				 res.setResult(false);
				 res.setNameCode((EnumUtils.NOVALID).getCode());
			}else{
			boolean flag= checkNameRepeat(tname,request);
			if(flag){
				//说明不存在
				tenantInfo.setName(tname);
			}else{
				res.setResult(false);
			    res.setNameCode((EnumUtils.NAMECHEAPT).getCode());
			  }
			}
			 }else {
				 res.setResult(false);
				 res.setNameCode((EnumUtils.NameEmpty).getCode());
			}
	}
	private boolean checkNameRepeat(String name,HttpServletRequest request) {
		 boolean notExist = false;//返回true是已经存在，false是不存在
		 Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
	    List<TenantInfo> sysOrgInfos = tenantInfoService.checkOrgNameExist(map);
		if(sysOrgInfos!=null){
		if(sysOrgInfos.size()>0){
			notExist= false;
		}else{
			notExist= true;
		}
		}else{
			notExist= true;
		}
			
	  return notExist;
  }
	/**
     * 验证数据的
     * @param data
     * @return
     */
    private ResultMessage validData(Object obj) {
    	ResultMessage resultMsgMessage=new ResultMessage(ResultMessage.Success, "");
    	boolean flag=validateAnnotation(obj);
    	if(flag){
    		resultMsgMessage.setResult(ResultMessage.Fail);
    		resultMsgMessage.setMessage("参数异常");
    		
    	}
		return resultMsgMessage;
	}
	private boolean validateAnnotation(Object obj) {
		Class entity=obj.getClass();
		boolean tag=false;
		if(entity!=null){
			Field fields[]=entity.getDeclaredFields();
			if(fields!=null&&fields.length>0){
			   //遍历所有的field,使用
				for (Field field : fields) {
					PropertyDescriptor propertyDescriptor = null;
					//查看某个字段是否有非空的注解
					if(field.isAnnotationPresent(NotNullAnnotation.class)){
						//如果有这个注解，则获取注解类  
						NotNullAnnotation fieldAnnotations = (NotNullAnnotation) field.getAnnotation(NotNullAnnotation.class);  
						if(fieldAnnotations!=null){
							try {
								propertyDescriptor=new PropertyDescriptor(field.getName(), entity);
							} catch (IntrospectionException e) {
								tag=true;
								e.printStackTrace();
							}
							//得到get方法
							if(propertyDescriptor!=null){
							 Method getmethod=propertyDescriptor.getReadMethod();
							 Object value = null;
							 try {
								 value=getmethod.invoke(obj, new Object[]{});
							} catch (IllegalAccessException e) {
								tag=true;
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								tag=true;
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								tag=true;
								e.printStackTrace();
							}
								 if(com.casic.util.StringUtil.isEmpty(value)){
									 tag=true;
								 }
							}
						}
					}
				}
				
			}
			
		}
		return tag;
	}
	
	/**
	 * 通过用户account查询用户是否存在
	 * true：存在
	 * false：不存在
	 * */

	public boolean checkAccount(String account) throws Exception{
		ISysUser user = sysUserService.getByAccount(account);
		if(user!=null){
			//用户存在
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 通过用户,moblie查询用户是否存在
	 * true：存在
	 * false：不存在
	 * */
	
	public boolean checkMoblie(String moblie,HttpServletRequest request) throws Exception{		
		QueryFilter qf=new QueryFilter(request);
		if(moblie!=null && moblie!=""){
			qf.addFilter("moblie", moblie);
		}		
		List<ISysUser> users = sysUserService.getAll(qf);
		if(!users.isEmpty() && users.size()>0){
			//用户存在
			return true;
		}else{
			return false;
		}
		
	}
	private TenantInfo checkTenantName(String tenantName,HttpServletRequest request) throws Exception{		
		QueryFilter qf=new QueryFilter(request);
		if(tenantName!=null && tenantName!=""){
			qf.addFilter("name", tenantName);
		}		
		List<TenantInfo> tenants = tenantInfoService.getAll(qf);
		if(!tenants.isEmpty() && tenants.size()>0){
			//用户存在
			return tenants.get(0);
		}else{
			return null;
		}
		
	}

	/**@author ypchenga
     * 将结果返回以json串的形式
     * @param response
     * @param requestParameterIsEmpty
     * @param object
     */
	private void result(HttpServletResponse response,
			ReturnCode requestParameterIsEmpty,Object data) {
		PrintWriter pin;
		try {
			response.setHeader("Content-type", "text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("status", requestParameterIsEmpty.getCode());//错误码
			map.put("msg", requestParameterIsEmpty.getMsg());//错误信息
			map.put("results", data);
			String json=JSONObject.fromObject(map).toString();
			pin=response.getWriter();
			pin.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**@author ypchenga
	 * 将结果返回以json串的形式
	 * @param response
	 * @param requestParameterIsEmpty
	 * @param object
	 */
	private void result(HttpServletResponse response,
			Map<String, Object> data) {
		PrintWriter pin = null;
		try {
			response.setHeader("Content-type", "text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			String json=JSONObject.fromObject(data).toString();
			AhRecord axAhRecord=new AhRecord();
			axAhRecord.setId(UniqueIdUtil.genId());
			axAhRecord.setCalltime(new Date());
			axAhRecord.setFailreason(json);
			axAhRecord.setIssuccess("0");
			ahRecordService.add(axAhRecord);
		
			pin=response.getWriter();
			pin.write(json);
		
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pin!=null){
				try {
					pin.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					pin.close();
				}
			}
		}
		
		
	}
	private void result(HttpServletRequest request,
			Map<String, Object> data) {
		try {
			String json=JSONObject.fromObject(data).toString();
			AhRecord axAhRecord=new AhRecord();
			axAhRecord.setId(UniqueIdUtil.genId());
			axAhRecord.setCalltime(new Date());
			axAhRecord.setFailreason(json);
			String result=(String) data.get("errorCode");
			if(("200").equals(result)){
				axAhRecord.setIssuccess("1");
			}else{
				axAhRecord.setIssuccess("0");
			}
			axAhRecord.setAddr(request.getRemoteAddr());
			ahRecordService.add(axAhRecord);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
		
	}
		/**@author ypchenga
		 * 将结果返回以json串的形式
		 * @param response
		 * @param requestParameterIsEmpty
		 * @param object
		 */
		private void result(HttpServletResponse response,
				ReturnCode requestParameterIsEmpty,String msg, Object data) {
			PrintWriter pin;
			try {
				response.setHeader("Content-type", "text/json;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("status", requestParameterIsEmpty.getCode());//错误码
				map.put("msg", msg);//错误信息
				map.put("results", data);
				String json=JSONObject.fromObject(map).toString();
				pin=response.getWriter();
				pin.write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
	}
	private Matcher isIllegalName(String name) {
		Pattern pattern = Pattern.compile("[\\（\\）|\u4e00-\u9fa5\\w|\\(\\)]+"); 
		Matcher matcher = pattern.matcher(name);
		return matcher;
	  }
	  private static Matcher isIllegal(String name) {
		  Pattern pattern = Pattern.compile("[\u4e00-\u9fa5\\w\\.]+"); 
		  Matcher matcher = pattern.matcher(name);
		  return matcher;
	  }
	  private Matcher isIllegalAddress(String name) {
		  Pattern pattern = Pattern.compile("[#、|\\（\\）|\u4e00-\u9fa5\\w|\\(\\)|-]+"); 
		  Matcher matcher = pattern.matcher(name);
		  return matcher;
	  }
	  private Matcher isIllegalIndustry(String name) {
		  Pattern pattern = Pattern.compile("[\u4e00-\u9fa5\\w|、]+"); 
		  Matcher matcher = pattern.matcher(name);
		  return matcher;
	  }
	  private  Matcher isIllegalEn(String name) {
		  Pattern pattern = Pattern.compile("[\\w-_]+"); 
		  Matcher matcher = pattern.matcher(name);
		  return matcher;
	  }

	  private boolean isIllegalDate(String str) {
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
		  	date.parse(str);
		  	return true;
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return false;
	  }
	private boolean checkMobileRepeat(String mobile, HttpServletRequest request) {
		boolean isExist = false;//返回true是已经存在，false是不存在
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("mobile", mobile);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if(sysUsers!=null){
			if(sysUsers.size()>0){
				isExist= false;
			}else{
				isExist= true;
			}
		}else{
			isExist= true;
		}
		
		return isExist;
	
  }
	private boolean checkOrgId(HttpServletRequest request,String orgId) throws Exception{
		ResultMessage res=null;
		boolean isExist = false;//返回true是已经存在，false是不存在
		
		if(com.casic.util.StringUtil.isEmpty(orgId)){
			isExist=false;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("sysOrgInfoId", orgId);
		List<SysOrgInfo> sysOrgInfos = sysOrgInfoService.getAll(queryFilter);
		if(sysOrgInfos!=null){
			if(sysOrgInfos.size()>0){
				isExist= true;
			}else{
				isExist= false;
			}
		}else{
			isExist= false;
		}
		return isExist;
	}
	
	private boolean checkSystemId(HttpServletRequest request,String systemId) throws Exception{
		ResultMessage res=null;
		boolean isExist = false;//返回true是已经存在，false是不存在
		
		if(com.casic.util.StringUtil.isEmpty(systemId)){
			isExist=false;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("systemId", systemId);
		List<SubSystem> subSystems = subSystemService.getAll(queryFilter);
		if(subSystems!=null){
			if(subSystems.size()>0){
				isExist= true;
			}else{
				isExist= false;
			}
		}else{
			isExist= false;
		}
		return isExist;
	}
	  //查询系统的企业openId
		private boolean checkTopenId(HttpServletRequest request,String openId) throws Exception{
			ResultMessage res=null;
			boolean notExist = true;//返回true是已经存在，false是不存在
			
			if(com.casic.util.StringUtil.isEmpty(openId)){
				notExist=true;
			}
			QueryFilter queryFilter = new QueryFilter(request, true);
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("openId", openId);
			List<TenantInfo> tenantInfos = tenantInfoService.getAll(queryFilter);
			if(tenantInfos!=null){
				if(tenantInfos.size()>0){
					notExist= false;
				}else{
					notExist= true;
				}
			}else{
				notExist= true;
			}
			return notExist;
		}
		
		private boolean checkUopenId(HttpServletRequest request,String openId) throws Exception{
			ResultMessage res=null;
			boolean notExist = true;//返回true是已经存在，false是不存在
			
			if(com.casic.util.StringUtil.isEmpty(openId)){
				notExist=true;
			}
			QueryFilter queryFilter = new QueryFilter(request, true);
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("openId", openId);
			List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
			if(sysUsers!=null){
				if(sysUsers.size()>0){
					notExist= false;
				}else{
					notExist= true;
				}
			}else{
				notExist= true;
			}
			return notExist;
		}
		private Map<String, Object> saveOrgAndUserInfo(TenantInfo info, SysUser sysUser, boolean isRequired) {
			//构造返回信息
			Map<String, Object> resultMap=new HashMap<String, Object>();
			 Map<String,Object> map = new HashMap<String,Object>();
				String resultMsg = "成功";
				sysUser.setPassword("123456");
				sysUser.setOpenId(info.getUopenId());
				sysUser.setMobile(info.getTel());
				try {
					map = tenantInfoService.registerSysOrg(info, sysUser);
					info = (TenantInfo)map.get("sysOrgInfo");
					sysUser = (SysUser)map.get("sysUser");
					if(!isRequired){
					resultMap.put("tName", info.getName());
					resultMap.put("tSysOrgInfoId", info.getSysOrgInfoId());
					}else{
						resultMap=map;
					}
					int i = 1;
					//企业添加成功后同步到子系统
					ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return resultMap;
		 }		
		private boolean isResAndRoleExist(HttpServletRequest request,String resId,String roleId){
			QueryFilter queryFilter=new QueryFilter(request);
			queryFilter.addFilter("resId", resId);
			queryFilter.addFilter("roleId", roleId);
			List<RoleResources> lists = roleResService.getAll(queryFilter);
			if(!lists.isEmpty()){
				return true;
			}
			return false;
		}
	/**
		 * 得到用户和企业信息的接口页面
		 * 
		 * */
		@RequestMapping(value="getUserAndOrgInfoNew",method=RequestMethod.POST)
		@ResponseBody
		public void getUserAndOrgInfoNew(@RequestBody UcDataParam data,HttpServletResponse response,HttpServletRequest request) throws Exception{
			Map<String, Object> backMap=new HashMap<String, Object>();
			Map<String, Object> map = isSubsysExist(data);
			if(!map.isEmpty()){
				result(response,map);
				return;
			}
			//获取登录地址url
		  String url=request.getParameter("uri");
		  Map<String, Object> mapUrl=validateUrl(url);
		  if(!mapUrl.isEmpty()){
			  result(response,mapUrl);
			  return;
		  }
		
		  //获取用户和企业信息
		  LinkedHashMap<String, Object> user=null;
		  LinkedHashMap<String, Object> tenant=null;
		  UserData userData=new UserData();
		  TenantData tenantData=new TenantData();
		  String account="";
		  String password="";
		  Map<String, Object> mapUser=new HashMap<String, Object>();
		  Map<String, Object> mapOrg=new HashMap<String, Object>();
		  if(data.getUser()!=null){
			  //user=data;
			  //org.apache.commons.beanutils.BeanUtils.populate(userData, user);
			  userData=data.getUser();
		      //校验用户非空
			  mapUser= validateAnnotationObject(userData);
			  if(!mapUser.isEmpty()){
				  result(response,mapUser);
				  return;
			  }
		  }
		  mapUser.clear();//清空mapUser集合
		  if(data.getTenant()!=null){
			  /*tenant=(LinkedHashMap<String, Object>) dmap.get("tenant");
			  org.apache.commons.beanutils.BeanUtils.populate(tenantData, tenant);*/
			  tenantData=data.getTenant();
			  //校验用户非空
			  mapOrg= validateAnnotationObject(tenantData);
			  if(!mapOrg.isEmpty()){
				  result(response,mapOrg);
				  return;
			  }
		  }
		  mapOrg.clear();//清空mapOrg集合
		  //校验用户信息的合法性(是否还有特殊字符)
		  mapUser=validateIsLegalForUser(userData);
		  if(!mapUser.isEmpty()){
			  result(response,mapUser);
			  return;
		  }     
		  mapUser.clear();//清空mapUser集合
		  //校验企业信息的合法性
		  mapOrg=validateIsLegalForOrg(tenantData);
		  if(!mapOrg.isEmpty()){
			  result(response,mapOrg);
			  return;
		  }
		  mapOrg.clear();
		  //合法性校验完毕
		 //根据用户Id去查询关系表  
		 UserRelationShip userRelationShip=userRelationShipService.loadUserRelationShipByUserId(userData.getUserId().trim(),tenantData.getTenantId().trim());
		 if(userRelationShip!=null){
			 //表示已经申请了合作
			account=userRelationShip.getAccount();//从中间表得到该申请用户的账号
			Long userId=userRelationShip.getUserId();
			//查询密码
			password=sysUserService.getPasswordByUserIdAndAccount(userId,account);
		   //构造跳转的url
		  String casLogin=cas_url+"/registerLogin?userName="+account+"&password="+password+"&isEncrypt=yes&service="+url;
		  response.setCharacterEncoding("utf-8");
		  response.setHeader("Content-Type", "text/html;charset=utf-8");
		  backMap.put("success", true);
		  backMap.put("errorCode", "200");
		  backMap.put("casLogin",casLogin);
		  result(response, backMap);
		  return;
		 }else{
			 //表示没有安徽企业云的用户
			 //根据传递的企业名称来获取
			 String tenantName=tenantData.getTenantName();//获取企业名称
			 tenantName=tenantName.trim();
			 //查询该企业是否存在
			 TenantInfo info= checkTenantName(tenantName, request);
			 //如果不存在
			 SysUser sysUser=new SysUser();
			//转化用户实体关系
			mapUser=validateUnique(userData,request);
			if(!mapUser.isEmpty()){
					result(response,mapUser);
					return;
			}
			mapUser.clear();
			sysUser=convertUserDataToSysUser(sysUser,userData);
			 if(info==null){
				 //创建企业
				//转化企业实体关系
				try {
					info=new TenantInfo();
					info=convertTenantDataToTennatInfo(info,tenantData);
					boolean isRequired=true;
					Map<String, Object> resultMap=	saveOrgAndUserInfo(info,sysUser,isRequired);
					info = (TenantInfo)resultMap.get("sysOrgInfo");
					sysUser = (SysUser)resultMap.get("sysUser");
					account=sysUser.getAccount();
					userRelationShipService.saveUserRelationAndOrgRelation(userRelationShip,account,sysUser.getUserId(),tenantData,userData,info);
					password=sysUser.getPassword();
				} catch (Exception e) {
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("success", false);
					map1.put("errorCode", "271");
					map1.put("error", "创建企业失败");
					result(response, map1);
					e.printStackTrace();
				}
			 }else{
				 //否则创建一个子账号用户
				 try {
					Long[] aryOrgId = new Long[1];// 组织Id数组
					 //根据企业名称获取企业的详细信息
					 aryOrgId[0]=info.getSysOrgInfoId();
					 Long[] aryOrgCharge=new Long[1];
					 aryOrgCharge[0]=SysUserOrg.CHARRGE_NO.longValue();
					 Long userId=UniqueIdUtil.genId();
					 String enPassword = EncryptUtil.encryptSha256("123456");
					 sysUser.setPassword(enPassword);
					 sysUser.setUserId(userId);
					 sysUser.setOrgId(info.getSysOrgInfoId());
					 sysUser.setOrgSn(info.getSysOrgInfoId());
					 sysUser.setCreatetime(new Date());
					 sysUser.setIsLock((short)0);
					 sysUser.setIsExpired((short)0);
					 sysUser.setStatus((short)1);
					 sysUser.setOpenId(OpenIdUtil.getOpenId());
					 sysUserService.add(sysUser);
					// 添加用户和组织关系。
					 sysUserOrgService.saveUserOrg(userId, aryOrgId, 1l, aryOrgCharge);
					 account=sysUser.getAccount();
					 password=sysUser.getPassword();
					 userRelationShipService.saveUserRelationShip(userRelationShip,account,sysUser,tenantData,userData);
				} catch (Exception e) {
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("success", false);
					map1.put("errorCode", "270");
					map1.put("error", "创建子账号失败");
					result(response, map1);
					e.printStackTrace();
				}
			 }
			 //注册后直接登录
				//构造跳转的url
			String casLogin=cas_url+"/registerLogin?userName="+account+"&password="+password+"&isEncrypt=yes&service="+url;
			backMap.put("success", true);
			backMap.put("errorCode", "200");
			backMap.put("casLogin",casLogin);
			result(response, backMap);
			return ;
		 }

		}
		/**
		 * 得到用户和企业信息的接口页面
		 * 
		 * */
		@RequestMapping(value="getUserAndOrgInfo",method=RequestMethod.POST)
		public void getUserAndOrgInfo(String systemId,String userId,String account,String mobile,String email,String fullName,String tenantId,String tenantName,String address,String connecter,String tel,String city,String code,String registertime,String regCapital,String incorporator,String gszch,String nsrsbh,HttpServletResponse response,HttpServletRequest request) throws Exception{
			System.out.println(systemId+"----userId="+userId+"----mobile="+mobile+"----email="+email+"----fullName="+fullName+"---tenantId="+tenantId+"----tenantName="+tenantName+"-----address="+address+"----connecter="+connecter+"---tel="+tel+"------city="+city+"----code="+code+"---registertime="+registertime+"----regCapital="+regCapital+"----incorporator="+incorporator+"---gszch="+gszch+"---nsrsbh="+nsrsbh);
		    
			Map<String, Object> backMap=new HashMap<String, Object>();
			Map<String, Object> map = isSubsysExist(systemId);
			if(!map.isEmpty()){
				result(request,map);
				response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
				return;
			}
			//获取登录地址url
			String url=request.getParameter("uri");
			Map<String, Object> mapUrl=validateUrl(url);
			if(!mapUrl.isEmpty()){
				result(request,mapUrl);
				response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
				return;
			}
			
			//获取用户和企业信息email
			UserData userData=new UserData();
			TenantData tenantData=new TenantData();
			String password="";
			String account1="";
			Map<String, Object> mapUser=new HashMap<String, Object>();
			Map<String, Object> mapOrg=new HashMap<String, Object>();
				//user=data;
				//org.apache.commons.beanutils.BeanUtils.populate(userData, user);
				userData.setUserId(userId);
				userData.setAccount(account);
				userData.setEmail(email);
				userData.setFullName(fullName);
				userData.setMobile(mobile);
				//校验用户非空
				mapUser= validateAnnotationObject(userData);
				if(!mapUser.isEmpty()){
					result(request,mapUser);
					response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
					return;
				}
			   mapUser.clear();//清空mapUser集合
				/*tenant=(LinkedHashMap<String, Object>) dmap.get("tenant");
			  org.apache.commons.beanutils.BeanUtils.populate(tenantData, tenant);*/
				tenantData.setAddress(address);
				tenantData.setCity(city);
				tenantData.setCode(code);
				tenantData.setConnecter(connecter);
				tenantData.setGszch(gszch);
				tenantData.setIncorporator(incorporator);
				tenantData.setNsrsbh(nsrsbh);
				tenantData.setRegCapital(regCapital);
				tenantData.setRegistertime(registertime);
				tenantData.setTel(tel);
				tenantData.setTenantId(tenantId);
				tenantData.setTenantName(tenantName);
				//校验用户非空
				mapOrg= validateAnnotationObject(tenantData);
				if(!mapOrg.isEmpty()){
					result(request,mapOrg);
					response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
					return;
				}
			mapOrg.clear();//清空mapOrg集合
			//校验用户信息的合法性(是否还有特殊字符)
			mapUser=validateIsLegalForUser(userData);
			if(!mapUser.isEmpty()){
				result(request,mapUser);
				response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
				return;
			}
			mapUser.clear();//清空mapUser集合
			//校验企业信息的合法性
			String regx[]=code.split("\\/");
			if(regx.length==1){
				//只是组织机构代码
				tenantData.setCode(regx[0]);
			}else
				if(regx.length==2){
				tenantData.setCode(regx[0]);
				tenantData.setCreditCode(regx[1]);
			}else{
				mapOrg.put("success", false);
				mapOrg.put("errorCode", "479");
				mapOrg.put("error", "组织机构代码格式不正确");
				result(request,mapOrg);
				response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
				return;
			}
			mapOrg.clear();
			mapOrg=validateIsLegalForOrg(tenantData);
			if(!mapOrg.isEmpty()){
				result(request,mapOrg);
				response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
				return;
			}
			mapOrg.clear();
			//合法性校验完毕
			//根据用户Id去查询关系表  
			UserRelationShip userRelationShip=userRelationShipService.loadUserRelationShipByUserId(userData.getUserId().trim(),tenantData.getTenantId().trim());
			if(userRelationShip!=null){
				//表示已经申请了合作
				account1=userRelationShip.getAccount();//从中间表得到该申请用户的账号
				//查询密码
				password=sysUserService.getPasswordByUserIdAndAccount(userRelationShip.getUserId(),account1);
				//构造跳转的url
				String casLogin=cas_url+"/registerLogin?userName="+account1+"&password="+password+"&isEncrypt=yes&service="+url;
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Type", "text/html;charset=utf-8");
				backMap.put("success", true);
				backMap.put("errorCode", "200");
				backMap.put("casLogin",casLogin);
				result(request, backMap);
				response.sendRedirect(casLogin);
				return;
			}else{
				//表示没有安徽企业云的用户
				//根据传递的企业名称来获取
				tenantName=tenantName.trim();
				//查询该企业是否存在
				TenantInfo info= checkTenantName(tenantName, request);
				//如果不存在
				SysUser sysUser=new SysUser();
				//转化用户实体关系
				mapUser=validateUnique(userData,request);
				if(!mapUser.isEmpty()){
					result(request,mapUser);
					return;
				}
				mapUser.clear();
				sysUser=convertUserDataToSysUser(sysUser,userData);
				if(info==null){
					//创建企业
					//转化企业实体关系
					try {
						info=new TenantInfo();
						info=convertTenantDataToTennatInfo(info,tenantData);
						boolean isRequired=true;
						Map<String, Object> resultMap=	saveOrgAndUserInfo(info,sysUser,isRequired);
						info = (TenantInfo)resultMap.get("sysOrgInfo");
						sysUser = (SysUser)resultMap.get("sysUser");
						account1=sysUser.getAccount();
						userRelationShipService.saveUserRelationAndOrgRelation(userRelationShip,account1,sysUser.getUserId(),tenantData,userData,info);
						password=sysUser.getPassword();
					} catch (Exception e) {
						Map<String, Object> map1=new HashMap<String, Object>();
						map1.put("success", false);
						map1.put("errorCode", "271");
						map1.put("error", "创建企业失败");
						result(request, map1);
						response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
						e.printStackTrace();
					}
				}else{
					//否则创建一个子账号用户
					try {
						Long[] aryOrgId = new Long[1];// 组织Id数组
						//根据企业名称获取企业的详细信息
						aryOrgId[0]=info.getSysOrgInfoId();
						Long[] aryOrgCharge=new Long[1];
						aryOrgCharge[0]=SysUserOrg.CHARRGE_NO.longValue();
						Long uId=UniqueIdUtil.genId();
						String enPassword = EncryptUtil.encryptSha256("123456");
						sysUser.setPassword(enPassword);
						sysUser.setUserId(uId);
						sysUser.setOrgId(info.getSysOrgInfoId());
						sysUser.setOrgSn(info.getSysOrgInfoId());
						sysUser.setCreatetime(new Date());
						sysUser.setIsLock((short)0);
						sysUser.setIsExpired((short)0);
						sysUser.setStatus((short)1);
						sysUser.setOpenId(OpenIdUtil.getOpenId());
						sysUserService.add(sysUser);
						// 添加用户和组织关系。
						sysUserOrgService.saveUserOrg(uId, aryOrgId, 1l, aryOrgCharge);
						account1=sysUser.getAccount();
						password=sysUser.getPassword();
						userRelationShipService.saveUserRelationShip(userRelationShip,account1,sysUser,tenantData,userData);
					} catch (Exception e) {
						Map<String, Object> map1=new HashMap<String, Object>();
						map1.put("success", false);
						map1.put("errorCode", "270");
						map1.put("error", "创建子账号失败");
						result(request, map1);
						response.sendRedirect(request.getContextPath()+"/commons/404.jsp");
						e.printStackTrace();
					}
				}
				//注册后直接登录
				//构造跳转的url
				String casLogin=cas_url+"/registerLogin?userName="+account1+"&password="+password+"&isEncrypt=yes&service="+url;
				backMap.put("success", true);
				backMap.put("errorCode", "200");
				backMap.put("casLogin",casLogin);
				result(request, backMap);
				response.sendRedirect(casLogin);
				return ;
			}
			
		}

    //校验用户信息的唯一性(包含用户账号和手机)
	private Map<String, Object> validateUnique(UserData userData,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		String mobile=userData.getMobile();
		if(!checkMobileRepeat(mobile.trim(),request)){
			map.put("success", false);
			map.put("errorCode", "463");
			map.put("error", "手机号已存在");
		}
		return map;
	}








	private SysUser convertUserDataToSysUser(SysUser sysUser, UserData userData) {
		 try {
				org.apache.commons.beanutils.BeanUtils.copyProperties(sysUser, userData);
				 String account=userData.getAccount();
				  ISysUser iSysUser = sysUserService.getByAccount("ahjxw_"+account);
				  if(iSysUser==null){
					 sysUser.setAccount("ahjxw_"+account);
				  }else{
						String prefix="ahjxw_"+account+"_";
						account=generateAccount(prefix);
						sysUser=getAccount(sysUser,account);
					}	
				  sysUser.setFullname(userData.getFullName());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		 sysUser.setUserId(null);
		return sysUser;
	}








	private SysUser getAccount(SysUser sysUser, String account) {
		ISysUser iSysUser = sysUserService.getByAccount("ahjxw_"+account);
		if(iSysUser==null){
			 sysUser.setAccount(account);
		}else{
			 String prefix=account.substring(0,(account.lastIndexOf("_"))+1);
			 account=generateAccount(prefix);
			 getAccount(sysUser,account);
		}
		return sysUser;
	}

    private  String generateAccount(String str){
    	char[] ziMu={'A','B','C','D','E','F','G','H','I','J','K','L','M',
				'N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0'};
    	StringBuffer buffer=new StringBuffer(str);
    	for (int i=0;i<2;i++) {
    		buffer.append(ziMu[(int)(Math.random()*(ziMu.length))]);
		}
    	return buffer.toString();
    }

    

    







	private TenantInfo convertTenantDataToTennatInfo(TenantInfo info,
			TenantData tenantData) {
		 info.setName(tenantData.getTenantName());
		 info.setAddress(tenantData.getAddress());
		 info.setConnecter(tenantData.getConnecter());
		 info.setTel(tenantData.getTel());
		 info.setCity(tenantData.getCity());
		 info.setCode(tenantData.getCode());
		 String registertime=tenantData.getRegistertime();
		 info.setRegistertime(DateUtil.formatDate(registertime));
		 info.setRegCapital(tenantData.getRegCapital());
		 info.setIncorporator(tenantData.getIncorporator());
		 info.setGszch(tenantData.getGszch());
		 info.setNsrsbh(tenantData.getNsrsbh());
		 info.setCreditCode(tenantData.getCreditCode());
		 info.setMark("4");//标记来源为安徽企业云平台
		 return info;
	}

	private Map<String, Object> validateIsLegalForOrg(TenantData tenantData) {
	  Map<String, Object> map=new HashMap<String, Object>();
	  String tenantId=tenantData.getTenantId();//获取企业的Id
		//校验tenantId的合法性
	    if(!RegexValidateUtil.checkEn(tenantId.trim())){
	    	map.put("success", false);
			map.put("errorCode", "451");
			map.put("error", "tenantId非数值");
	    }
		String tenantName=tenantData.getTenantName();//获取企业的名称
		if(!isIllegalName(tenantName.trim()).matches()){
		    map.put("success", false);
			map.put("errorCode", "452");
			map.put("error", "tenantName验证不合法");
		 }
	    //校验企业的地址
		String address=tenantData.getAddress();
		if(!com.casic.util.StringUtil.isEmpty(address)){
		    if(!isIllegalAddress(address.trim()).matches()){
			    map.put("success", false);
				map.put("errorCode", "453");
				map.put("error", "地址验证不合法");
			 }
		}
	    //企业联系人
	    String connecter=tenantData.getConnecter();
	    if(!isIllegal(connecter.trim()).matches()){
		    map.put("success", false);
			map.put("errorCode", "454");
			map.put("error", "联系人验证不合法");
		 }
	    //手机号
	    String tel=tenantData.getTel();
	    if(!RegexValidateUtil.checkMobileNumber(tel.trim())){
	    	map.put("success", false);
	    	map.put("errorCode", "455");
	    	map.put("error", "手机号验证不合法");
	    }
	   //城市
	    String city=tenantData.getCity();
	    if(!com.casic.util.StringUtil.isEmpty(city)){
		    if(!RegexValidateUtil.checkChinese(city.trim())){
		    	map.put("success", false);
		    	map.put("errorCode", "456");
		    	map.put("error", "城市验证不合法");
		    }
	    }
	   //组织机构代码
	    String code=tenantData.getCode();
	    if(!isIllegalEn(code.trim()).matches()){
		  map.put("success", false);
	      map.put("errorCode", "457");
	      map.put("error", "组织机构代码验证不合法");
	    }
	    //注册时间
	    String registertime=tenantData.getRegistertime();
	    if(!com.casic.util.StringUtil.isEmpty(registertime)){
		    if(!isIllegalDate(registertime.trim())){
		    	map.put("success", false);
		    	map.put("errorCode", "458");
		    	map.put("error", "时间格式不正确");
		    }
	    }
	    //注册资本
	    String regCapital=tenantData.getRegCapital();
	    if(!com.casic.util.StringUtil.isEmpty(regCapital)){
		    if(!isIllegal(regCapital.trim()).matches()){
		    	map.put("success", false);
		    	map.put("errorCode", "459");
		    	map.put("error", "注册资本格式不正确");
		    }
	    }
	    //法人
	    String incorporator=tenantData.getIncorporator();
	    if(!com.casic.util.StringUtil.isEmpty(incorporator)){
		    if(!isIllegal(incorporator.trim()).matches()){
		    	map.put("success", false);
		    	map.put("errorCode", "460");
		    	map.put("error", "法人格式不正确");
		    }
	    }
	    //工商注册号校验
	    String gszch=tenantData.getGszch();
	    if(!com.casic.util.StringUtil.isEmpty(gszch)){
		    if(!isIllegalEn(gszch.trim()).matches()){
				  map.put("success", false);
			      map.put("errorCode", "461");
			      map.put("error", "工商注册号格式不正确");
			 }
	    }
	    //纳税人识别号校验
	    String nsrsbh=tenantData.getNsrsbh();
	    if(!com.casic.util.StringUtil.isEmpty(gszch)){
		    if(!isIllegalEn(nsrsbh.trim()).matches()){
		    	map.put("success", false);
		    	map.put("errorCode", "462");
		    	map.put("error", "纳税人识别号格式不正确");
		    }
	    }
	    //统一社会信用代码
	    String creditCode=tenantData.getCreditCode();
	    if(!com.casic.util.StringUtil.isEmpty(creditCode)){
	    	if(!isIllegalEn(creditCode.trim()).matches()){
	    		map.put("success", false);
	    		map.put("errorCode", "464");
	    		map.put("error", "统一社会信用代码格式不正确");
	    	}
	    }
		return map;
	}
	//校验用户信息的合法性
	private Map<String, Object> validateIsLegalForUser(UserData userData) {
		Map<String, Object> map=new HashMap<String, Object>();
		String userId=userData.getUserId();
		//校验userId的合法性
	    if(!RegexValidateUtil.checkEn(userId.trim())){
	    	map.put("success", false);
			map.put("errorCode", "445");
			map.put("error", "userId非数值");
	    }
	    String account=	userData.getAccount();
	    if(!isIllegal(account.trim()).matches()){
	    	map.put("success", false);
			map.put("errorCode", "446");
			map.put("error", "账号信息中还有特殊字符");
	    }
	    //校验手机号
	    String mobile=userData.getMobile();//手机号
	    if(!RegexValidateUtil.checkMobileNumber(mobile.trim())){
	    	map.put("success", false);
			map.put("errorCode", "447");
			map.put("error", "手机号验证不合法");
	    }
	    //校验邮箱(非空才校验)
	    String email=userData.getEmail();
	    if(!com.casic.util.StringUtil.isEmpty(email)){
	    	//邮箱长度不在5-30位
	    	 if(!((email.trim().length() >=5) && (email.trim().length() <=30))){
	    		map.put("success", false);
	 			map.put("errorCode", "448");
	 			map.put("error", "邮箱长度不在5-30位之间");
	    	 }
	    	 if(!RegexValidateUtil.checkEmail(email.trim())){
	    		 map.put("success", false);
		 		 map.put("errorCode", "449");
		 		 map.put("error", "邮箱验证不合法");
	    	 }
	    	 
	    }
	    //用户名的校验
	    String fullName=userData.getFullName();
	    if(!isIllegal(fullName.trim()).matches()){
	    	map.put("success", false);
			map.put("errorCode", "450");
			map.put("error", "用户姓名中还有特殊字符");
	    }
		return map;
	}
	//通过反射注解校验非空
	private Map<String, Object> validateAnnotationObject(Object obj) {
		Map<String, Object> map=new HashMap<String, Object>();
			Class entity=obj.getClass();
			if(entity!=null){
				Field fields[]=entity.getDeclaredFields();
				if(fields!=null&&fields.length>0){
				   //遍历所有的field,使用
					for (Field field : fields) {
						PropertyDescriptor propertyDescriptor = null;
						//查看某个字段是否有非空的注解
						if(field.isAnnotationPresent(NotNullAnnotation.class)){
							//如果有这个注解，则获取注解类  
							NotNullAnnotation fieldAnnotations = (NotNullAnnotation) field.getAnnotation(NotNullAnnotation.class);  
							if(fieldAnnotations!=null){
								try {
									propertyDescriptor=new PropertyDescriptor(field.getName(), entity);
								} catch (IntrospectionException e) {
									map.put("success", false);
									map.put("errorCode", "440");
									map.put("error", "IntrospectionException异常!");
									e.printStackTrace();
								}
								//得到get方法
								if(propertyDescriptor!=null){
								 Method getmethod=propertyDescriptor.getReadMethod();
								 Object value = null;
								 try {
									 value=getmethod.invoke(obj, new Object[]{});
								} catch (IllegalAccessException e) {
									map.put("success", false);
									map.put("errorCode", "441");
									map.put("error", "IllegalAccessException异常!");
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									map.put("success", false);
									map.put("errorCode", "442");
									map.put("error", "IllegalArgumentException异常!");
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									map.put("success", false);
									map.put("errorCode", "443");
									map.put("error", "InvocationTargetException异常!");
									e.printStackTrace();
								}
									 if(com.casic.util.StringUtil.isEmpty(value)){
										map.put("success", false);
										map.put("errorCode", "444");
										map.put("error", field.getName()+"不能为空");
									 }
								}
							}
						}
					}
					
				}
				
			}
			return map;
	}
	
	
	
	private Map<String, Object> validateUrl(String url) {
		Map<String,Object> map=new HashMap<String, Object>();
		if(url==null||("").equals(url)){
			map.put("success", false);
			map.put("errorCode", "230");
			map.put("error", "请输入有效的参数");
		}
		return map;
	}
	
	
	
	
	/**
	 * 子系统需注册成会员，并注册子系统才可使用该接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "subRegisterUser", method = RequestMethod.POST)
	@Action(description = "各子系统调用的")
	@ResponseBody
	public Map<String, Object> subRegisterUser(@RequestBody DataParamNoBack dataParam,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("success",true);
		dataMap.put("error", null);
		dataMap.put("errorCode", "0");
		String sysId = dataParam.getSystemId();
		String data = dataParam.getData();
		System.out.println("sysId="+sysId);
		System.out.println("data="+data);
		// 首先判断是否传入子系统id未传直接返回请传入子系统id
		if (sysId == null || "".equals(sysId)) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "500");
			dataMap.put("error", "请传入子系统唯一标识");
			return dataMap;
		}
		SubSystem subSystem = subSystemService.getById(Long.parseLong(sysId));
		if (subSystem == null) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "502");
			dataMap.put("error", "未查询到子系统");
			return dataMap;
		}
		if (data == null || "".equals(data)) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "501");
			dataMap.put("error", "请传入子系统所需参数");
			return dataMap;
		}
		//根据sysId得到秘钥
		String secretKey = secretKeyService.getSecretKeyBySysId(Long.parseLong(sysId));
		if (secretKey == null || "".equals(secretKey)) {
			dataMap.put("success",false);
			dataMap.put("errorCode", "504");
			dataMap.put("error", "系统无对应秘钥，请联系管理员");
			return dataMap;
		}
		SecreptUtil des = new SecreptUtil(secretKey); 
		try{
			data=des.decrypt(data);
			JSONObject jsonObject = JSONObject.fromObject(data);
			ISysUser user = (SysUser) JSONObject.toBean(jsonObject, SysUser.class);
			List<ISysUser> sysUsers=sysUserService.getByMobile(user.getMobile());//校验电话
			if(sysUsers.size()>0){
				dataMap.put("success",false);
				dataMap.put("errorCode", "505");
				dataMap.put("error", "用户手机号码已被注册请更换手机号码");
				return dataMap; 
			}
			if(sysUserService.isAccountExist(user.getAccount())){//校验用户名
				dataMap.put("success",false);
				dataMap.put("errorCode", "506");
				dataMap.put("error", "该账户名已被注册请更换用户名");
				return dataMap; 
			}
			user.setOpenId(OpenIdUtil.getOpenId());
			user.setUserId(UniqueIdUtil.genId());
			sysUserService.add(user);
			dataMap.put("data", user.getUserId());
			dataMap.put("message", "用户添加成功");
		}
		catch(Exception e){
			e.printStackTrace();
			dataMap.put("success",false);
			dataMap.put("errorCode", "503");
			dataMap.put("error", "用户保存失败");
			return dataMap;
		}
		
		return dataMap;
	}
	
	/**
	 * 根据子系统Id查询子系统的信息
	 * @return
	 */
	@RequestMapping(value="getSubSystemBysysId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSubSystem(@RequestBody DataParamNoBack dataParam,HttpServletRequest request){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String systemId = dataParam.getSystemId();
		if(systemId == null || "".equals(systemId)){
			systemId = "100";
		}
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.addFilter("systemId",systemId);
		List<SubSystem> list = subSystemService.getAll(queryFilter);
		SubSystem subSystem = list.get(0);
		dataMap.put("success",true);
		dataMap.put("data", subSystem);
		dataMap.put("successCode", "200");
		return dataMap;
	}
	
	/**
	 * 查询子系统中带有后台地址的子系统信息
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value="getSubSystemInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSubSystemInfo(HttpServletRequest  request ) throws ClientProtocolException, IOException{
		/*String userId = RequestUtil.getString(request, "userId");
	    ISysUser User=null;
	    try {
	    	 User = sysUserService.getById(Long.parseLong(userId));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		String userName = User.getAccount();
		//String userName = ContextUtil.getCurrentUser().getUsername();
		String timestamp = String.valueOf(new Date().getTime());
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String password = md5.encodePassword(userName, timestamp.substring(timestamp.length()-4));
		String secretKey = password;
		String url = "http://developer.casicloud.com/api/getDevInfo.ht";//判登录用户是是开发人员显示开发者中心，不是将移除开发者中心功能
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("secretKey", secretKey);
		params.put("timestamp",timestamp);
		String result = "";
	    result = HttpClientUtil.JsonPostInvoke(url, params);
	    System.out.println(params.toString());
		System.out.println(result);
		JSONObject json = new JSONObject();
		json = JSONObject.fromObject(result);
		Boolean isnotTrue = (Boolean)json.get("success");
		System.out.println("isnotTrue-------："+isnotTrue);*/
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<SubSystem> systemList = subSystemService.getAll();
		List<SubSystem> list1 = new ArrayList<SubSystem>();
		for (SubSystem subSystem : systemList) {
			if(subSystem.getIsShow()==1){
				list1.add(subSystem);
			}
		}
		/*List<SubSystem> list = new ArrayList<SubSystem>();
		for (SubSystem subSystem : list1) 
		{
			if(null != subSystem.getHomePage() && !subSystem.getHomePage().isEmpty())
			{
				// false 不是开发者  移除  "开发者中心"
				if(!isnotTrue)
				{
					if(!"开发者中心".equals(subSystem.getSysName()))
					{
						list.add(subSystem);
					}
			    }
				else
				{
			    	list.add(subSystem);
			    }
			}
		}*/
		Collections.reverse(list1);
		//System.out.println(list.size());
		//getInfoByHomePage()
		dataMap.put("success",true);
		dataMap.put("data", list1);
		dataMap.put("successCode", "200");
		return dataMap;
	}

	
	/**
	 * 子系统在注册后，根据用户account和password查询用户所有信息
	 * 
	 **/
	@RequestMapping(value="getInfoByAccountAndPassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getInfoByAccountAndPassword(@RequestBody DataParam data,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		if(!map.isEmpty()){
			return map;
		}		
		Map<String, Object> dmap = data.getData();
		if(dmap==null || !dmap.containsKey("account")){
			map.put("success", false);
			map.put("errorCode", "501"); 
			map.put("error", "请传入所需参数");
			return map;
		}
		if(dmap==null || !dmap.containsKey("password")){	
			map.put("success", false);
			map.put("errorCode", "501"); 
			map.put("error", "请传入所需参数");
			return map;
		}
		String account1 = StringUtil.getString(dmap.get("account"));
		String account = URLDecoder.decode(account1, "utf-8");
		String password1 = StringUtil.getString(dmap.get("password"));
	     DES d = new DES();
		String firstKey = PropertiesUtils.getProperty("firstKey");
		String secondKey = PropertiesUtils.getProperty("secondKey");
		String thirdKey = PropertiesUtils.getProperty("thirdKey");
		String password = d.strDec(password1, firstKey, secondKey, thirdKey);
		//String encryptSha256 = EncryptUtil.encryptSha256(password);
		if(com.casic.util.StringUtil.isEmpty(account)||com.casic.util.StringUtil.isEmpty(password)){
			map.put("success", false);
			map.put("errorCode", "501");
			map.put("error", "请传入所需参数");
			return map;
		}else{
			List<ISysUser> byVip = sysUserService.getByVip(account, password);
			if(byVip == null){
				map.put("success", false);
				map.put("errorCode", "501"); 
				map.put("error", "该用户没在云网注册");
				return map;
			}
			//用户对象
			ISysUser sysUser = byVip.get(0);
			//企业对象
			SysOrgInfo sysOrgInfo = sysOrgInfoService.getById(sysUser.getOrgSn());
			List<SysUserOrg> orgByUserId = sysUserOrgService.getOrgByUserId(sysUser.getUserId());
			//用户组织对象
			SysUserOrg sysUserOrg = orgByUserId.get(0);
			//组织对象
			SysOrg orgBySn = sysOrgService.getOrgBySn(sysUser.getOrgSn());
			//用户角色关系
			List<SysUserRole> all = sysUserRoleService.getSysUserRoleByUserId(sysUser.getUserId());
			//角色资源
			 List<List<SysRoleRes>> list = new ArrayList<List<SysRoleRes>>();
			// List<SysRoleRes> roleRes = new ArrayList<SysRoleRes>();
			 for (SysUserRole sysUserRole : all) {
				List<SysRoleRes> roleRes = sysRoleResService.getSysRoleResByRoleId(sysUserRole.getRoleid());
				list.add(roleRes);
			 }
			if(sysUser != null){
				map.put("success", true);
				map.put("account", sysUser);
			}else{
				map.put("success", false);
				map.put("errorCode", "235");
				map.put("error", "用户不存在");
			}
			 map.put("sysUser", sysUser);
			 map.put("sysOrgInfo", sysOrgInfo);
			 map.put("sysUserOrg", sysUserOrg);
			 map.put("sysOrg", orgBySn);
			 map.put("userRole", all);
			 map.put("roleRes", list);
			return map;
		}
	}
	
	
public static void main(String[] args) {
	String url = "http://127.0.0.1:8080/api/addTenant.ht";	
	Map<String, Object> params = new HashMap<String, Object>();
	//Map<String, Object> data = new HashMap<String, Object>();
//	data.put("password", "123456");
//	data.put("aryRoleId", "[123,123,123]");
	TenantInfo a = new TenantInfo();
	a.setName("阿打算");
//	ISysUser sysUser = new SysUser();
//	sysUser.setOrgName("dasd");
//	data.put("info", a);
	//data.put("user", sysUser);
	params.put("systemId", "10000052900648");
	params.put("data", a.toString());
	String result = "";
	try {
		result = HttpClientUtil.JsonPostInvoke(url, params);
		JSONObject json = new JSONObject();
		json = JSONObject.fromObject(result);
		System.out.println(json.get("success"));
		
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
	
}
