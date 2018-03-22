package com.casic.api.controller;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.casic.model.DataParam;
import com.casic.model.NotNullAnnotation;
import com.casic.model.TenantForCasic;
import com.casic.model.UcDataForCasic;
import com.casic.model.UserData;
import com.casic.model.UserForCasic;
import com.casic.service.UcSysAuditService;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.TenantInfoService;
import com.casic.util.RegexValidateUtil;
import com.casic.util.SmsCache;
import com.casic.util.SmsUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysUserService;
/**
 * 提供短信验证码与注册服务的Api
 * @author ypchenga
 *
 */
@Controller
@RequestMapping("/api")
public class ApiController extends BaseFormController {
	
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private UcSysAuditService ucSysAuditService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private TenantInfoService tenantInfoService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	/**
	 * 获取短信验证码的服务
	 * @throws Exception
	 */
	@RequestMapping(value="getVerfifyCode",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getVerfifyCode(@RequestBody DataParam dataParam,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String reqParams= JSON.toJSONString(dataParam);
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			if(dataParam == null || "".equals(dataParam)){
				dataMap=result("230", "请输入有效的参数",reqParams,1);
				return dataMap;
			}
			String sysId = dataParam.getSystemId();
			Map<String, Object> data = dataParam.getData();
			// 首先判断是否传入子系统id未传直接返回请传入子系统id
			if (sysId == null || "".equals(sysId)) {
				dataMap=result("500", "请传入子系统唯一标识",reqParams,1);
				return dataMap;
			}
			if (data == null || "".equals(data)) {
				dataMap=result("502", "请传入子系统所需参数",reqParams,1);
				return dataMap;
			}
			SubSystem subSystem = subSystemService.getById(Long.parseLong(sysId));
			if (subSystem == null) {
				dataMap=result("503", "未查询到子系统",reqParams,1);
				return dataMap;
			}
			String mobile= data.get("mobile")+"";//获取手机号
			if(StringUtil.isEmpty(mobile)){
				dataMap=result("310", "手机号不能为空",reqParams,1);
				return dataMap;
			}
			if(!RegexValidateUtil.checkMobileNumber(mobile.trim())){
				dataMap=result("311", "手机号验证不合法",reqParams,1);
				return dataMap;
			}
			boolean flag = SmsUtil.sendVerifySms(mobile);
			if(flag){
				dataMap=result("200", "发送成功",reqParams,1);
			}else{
				dataMap=result("312", "短信服务调用失败",reqParams,1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap=result("312", e.getMessage(),reqParams,1);
		}
		return dataMap;
	}
	/**
	 * 校验短信验证码的服务
	 * @throws Exception
	 */
	@RequestMapping(value="checkVerfifyCode",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkVerfifyCode(String mobile,String verifycode,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String reqParams= mobile+"---"+verifycode;
		Map<String, String> dataMap = new HashMap<String, String>();
		if(StringUtil.isEmpty(mobile)||StringUtil.isEmpty(verifycode)){
			dataMap=result("322", "手机号或校验码为空",reqParams,3);
			return dataMap;
		}
		try {
			
			boolean flag =SmsCache.verify(mobile, verifycode);
			if(flag){
				dataMap=result("200", "短信验证码校验成功",reqParams,3);
			}else{
				dataMap=result("321", "短信验证码校验失败",reqParams,3);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap=result("312", e.getMessage(),reqParams,3);
		}
		return dataMap;
	}
	private Map<String, String> result(String status,String msg, String reqParams,Integer type) {
		Map<String, String> dataMap=new HashMap<String, String>();
		dataMap.put("status", status);
		dataMap.put("msg", msg);
		try {
			if(type==1){
				ucSysAuditService.addLog("短信验证码接口", "getVerfifyCode", reqParams, StringUtil.isNotEmpty(msg)?(msg.length()>20?msg.substring(0, 20):msg):"");
			}else
			if(type==2){
				ucSysAuditService.addLog("云网快速注册接口", "registerForCasic", reqParams, StringUtil.isNotEmpty(msg)?(msg.length()>20?msg.substring(0, 20):msg):"");	
			}else{
				ucSysAuditService.addLog("短信校验接口", "checkVerfifyCode", reqParams, StringUtil.isNotEmpty(msg)?(msg.length()>20?msg.substring(0, 20):msg):"");	
			}
		} catch (Exception e) {
			if(type==1){
				ucSysAuditService.addLog("短信验证码接口", "getVerfifyCode", reqParams, StringUtil.isNotEmpty(e.getMessage())?(e.getMessage().length()>14?(e.getMessage().substring(0, 14)):e.getMessage()):"日志记录异常"+"】");
			}else
				if(type==2){
				ucSysAuditService.addLog("云网快速注册接口", "registerForCasic", reqParams, StringUtil.isNotEmpty(e.getMessage())?(e.getMessage().length()>14?(e.getMessage().substring(0, 14)):e.getMessage()):"日志记录异常"+"】");
			}else{
				ucSysAuditService.addLog("短信校验接口", "checkVerfifyCode", reqParams, StringUtil.isNotEmpty(e.getMessage())?(e.getMessage().length()>14?(e.getMessage().substring(0, 14)):e.getMessage()):"日志记录异常"+"】");
			}
		}
		return dataMap;
	}
	
	
	/**
	 * 为航天云网提供快速注册的服务
	 * @throws Exception
	 */
	@RequestMapping(value="registerForCasic",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> registerForCasic(@RequestBody UcDataForCasic ucDataForCasic,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String reqParams= JSON.toJSONString(ucDataForCasic);
		Map<String, String> dataMap= new HashMap<String, String>();;
		try {
			
			Map<String, Object> map = isSubsysExist(ucDataForCasic);
			if(!map.isEmpty()){
				dataMap=result(map.get("errorCode")+"", map.get("error")+"",reqParams,2);
				return dataMap;
			}	
			UserForCasic userForCasic=new UserForCasic(); 
			if(ucDataForCasic.getUser()!=null){
				userForCasic=ucDataForCasic.getUser();
			}
			TenantForCasic tenantForCasic=new TenantForCasic();
			if(ucDataForCasic.getTenant()!=null){
				tenantForCasic=ucDataForCasic.getTenant();//得到企业信息
			}
			Map<String, Object> mapUser=new HashMap<String, Object>();//存放用户信息的map
			Map<String, Object> mapTenant=new HashMap<String, Object>();//存放企业信息的map
			mapUser= validateAnnotationObject(userForCasic);
			if(!mapUser.isEmpty()){
				dataMap=result(mapUser.get("errorCode")+"", mapUser.get("error")+"",reqParams,2);
				return dataMap;
			}
			mapUser.clear();
			mapTenant= validateAnnotationObject(tenantForCasic);
			if(!mapTenant.isEmpty()){
				dataMap=result(mapTenant.get("errorCode")+"", mapTenant.get("error")+"",reqParams,2);
				return dataMap;
			}
			mapTenant.clear();
			//必填校验完成
			mapUser=validateIsLegalForUser(userForCasic);
			if(!mapUser.isEmpty()){
				dataMap=result(mapUser.get("errorCode")+"", mapUser.get("error")+"",reqParams,2);
				return dataMap;
			}
			mapUser.clear();
			mapTenant=validateIsLegalForTenant(tenantForCasic);
			if(!mapTenant.isEmpty()){
				dataMap=result(mapTenant.get("errorCode")+"", mapTenant.get("error")+"",reqParams,2);
				return dataMap;
			}
			mapTenant.clear();
			//校验用户电话的唯一性
			boolean isExist=checkMobileRepeat(userForCasic.getMobile(), request);
			if(!isExist){
				dataMap=result("313","手机号已存在",reqParams,2);
				return dataMap;
			}
			//校验用户账号存在
			boolean isAccount=checkAccount(userForCasic.getAccount().trim());
			if(isAccount){
				dataMap=result("314","该账号已经存在",reqParams,2);
				return dataMap;	
			}
			//查询公司名称唯一性
			boolean isNameExist=checkOrgNameRepeat(request, tenantForCasic.getTenantName());
			if(!isNameExist){
				dataMap=result("315","公司名称已存在",reqParams,2);
				return dataMap;
			}
			SysUser sysUser=new SysUser();
			sysUser=convertUserDataToSysUser(sysUser, userForCasic);
			TenantInfo info=new TenantInfo();
			info=convertTenantDataToTennatInfo(info,tenantForCasic,ucDataForCasic.getSystemId());
			dataMap= saveOrgAndUserInfo(info, sysUser);
			if(!dataMap.isEmpty()){
				dataMap=result("200",JSONArray.toJSONString(dataMap),reqParams,2);
			}
		} catch (Exception e) {
			dataMap=result("319",e.getMessage(),reqParams,2);
			return dataMap;
		}
		return dataMap;
	}
	
	private TenantInfo convertTenantDataToTennatInfo(TenantInfo info,
			TenantForCasic tenantForCasic,String systemId) {
		info.setName(tenantForCasic.getTenantName());
		info.setMark("1");//标记来源航天云网
		info.setState(0);
		info.setConnecter(tenantForCasic.getConnecter());
		info.setSystemId(systemId);
		if(StringUtil.isNotEmpty(tenantForCasic.getEmail())){
			info.setEmail(tenantForCasic.getEmail());
		}
		if(StringUtil.isNotEmpty(tenantForCasic.getYyzz())){
			info.setYyzz(tenantForCasic.getYyzz());
		}
		if(StringUtil.isNotEmpty(tenantForCasic.getYyzzPic())){
			info.setYyzzPic(tenantForCasic.getYyzzPic());
		}
		if(StringUtil.isNotEmpty(tenantForCasic.getProvince())){
			info.setProvince(tenantForCasic.getProvince());
		}
		if(StringUtil.isNotEmpty(tenantForCasic.getCity())){
			info.setCity(tenantForCasic.getCity());
		}
		if(StringUtil.isNotEmpty(tenantForCasic.getInfo())){
			info.setInfo(tenantForCasic.getInfo());
		}
		return info;
	}
	private SysUser convertUserDataToSysUser(SysUser sysUser, UserForCasic userForCasic) {
		try {
			sysUser.setAccount(userForCasic.getAccount());
			sysUser.setMobile(userForCasic.getMobile());
			sysUser.setPassword(userForCasic.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysUser;
	}
	
    public boolean checkOrgNameRepeat(HttpServletRequest request, String name) throws Exception{
		boolean isExist = false;//返回true是已经存在，false是不存在
		if(StringUtil.isEmpty(name)){
			return true;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("name", name);
		List<SysOrgInfo> sysOrgInfos = sysOrgInfoService.getAll(queryFilter);
		if(sysOrgInfos!=null){
			if(sysOrgInfos.size()>0){
				isExist= false;
			}else{
				isExist= true;
			}
		}else{
			isExist= true;
		}
		return isExist;
	}
	/**
	 * 校验企业信息的合法性
	 * @param tenantForCasic
	 * @return
	 */
	private Map<String, Object> validateIsLegalForTenant(
			TenantForCasic tenantForCasic) {
		  Map<String, Object> map=new HashMap<String, Object>();
		  String tenantName=tenantForCasic.getTenantName();//获取企业的名称
			if(!isIllegalName(tenantName.trim()).matches()){
			    map.put("success", false);
				map.put("errorCode", "452");
				map.put("error", "tenantName验证不合法");
			 }
			 //企业联系人
		    String connecter=tenantForCasic.getConnecter();
		    if(!isIllegal(connecter.trim()).matches()){
			    map.put("success", false);
				map.put("errorCode", "454");
				map.put("error", "联系人验证不合法");
			 }
		   String email=tenantForCasic.getEmail();
		   if(StringUtil.isNotEmpty(email)){
			   if(!RegexValidateUtil.checkEmail(email)){
				   map.put("success", false);
					map.put("errorCode", "455");
					map.put("error", "企业邮箱验证不合法");
			   }
		   }  
		    //营业执照注册号
		   String yyzz=tenantForCasic.getYyzz();
		   if(StringUtil.isNotEmpty(yyzz)){
			   if(!isIllegal(yyzz).matches()){
				   map.put("success", false);
					map.put("errorCode", "456");
					map.put("error", "营业执照注册号验证不合法");
			   }
		   }  
		   //校验省
		   String province=tenantForCasic.getProvince();
		   //因为涉及到省统计,所以
		   if(StringUtil.isNotEmpty(province)){
			   if(!RegexValidateUtil.checkChinese(province)){
				   map.put("success", false);
				   map.put("errorCode", "458");
				   map.put("error", "省份验证不合法"); 
			   }
		   }
		 //校验省
		   String city=tenantForCasic.getCity();
		   //因为涉及到省统计,所以
		   if(StringUtil.isNotEmpty(city)){
			   if(!RegexValidateUtil.checkChinese(city)){
				   map.put("success", false);
				   map.put("errorCode", "459");
				   map.put("error", "市验证不合法"); 
			   }
		   }
		return map;
	}
	public boolean checkAccount(String account) throws Exception{
		ISysUser user = sysUserService.getByAccount(account);
		if(user!=null){
			//用户存在
			return true;
		}else{
			return false;
		}
	}
	
	  public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	  public static boolean isUrl(String url) {
	        return Pattern.matches(REGEX_URL, url);
	    }
	  
	  public static void main(String[] args) {
		System.out.println(isUrl("http://"));
	}
	  /**
	 * 企业名称是否合法
	 * @param name
	 * @return
	 */
	private Matcher isIllegalName(String name) {
		Pattern pattern = Pattern.compile("[\\（\\）|\u4e00-\u9fa5\\w|\\(\\)]+"); 
		Matcher matcher = pattern.matcher(name);
		return matcher;
	  }
	
	/**
	 * 校验用户信息的合法性
	 * @param userForCasic
	 * @return
	 */
	private Map<String, Object> validateIsLegalForUser(UserForCasic userForCasic) {
		 Map<String, Object> map=new HashMap<String, Object>();
		 String account=userForCasic.getAccount();
		    if(!isIllegal(account.trim()).matches()){
		    	map.put("success", false);
				map.put("errorCode", "446");
				map.put("error", "账号信息中还有特殊字符");
		    }
		    //校验手机号
		    String mobile=userForCasic.getMobile();//手机号
		    if(!RegexValidateUtil.checkMobileNumber(mobile.trim())){
		    	map.put("success", false);
				map.put("errorCode", "447");
				map.put("error", "手机号验证不合法");
		    }
		    
		return map;
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
	 private static Matcher isIllegal(String name) {
		  Pattern pattern = Pattern.compile("[\u4e00-\u9fa5\\w\\.]+"); 
		  Matcher matcher = pattern.matcher(name);
		  return matcher;
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
	private Map<String,Object> isSubsysExist(UcDataForCasic data) throws Exception{
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
	
	private Map<String, String> saveOrgAndUserInfo(TenantInfo info, SysUser sysUser) throws Exception {
		//构造返回信息
		 Map<String,Object> map = new HashMap<String,Object>();
		 Map<String,String> userAndOrgMap = new HashMap<String,String>();
			try {
				map = tenantInfoService.registerSysOrg(info, sysUser);
				info = (TenantInfo)map.get("sysOrgInfo");
				sysUser = (SysUser)map.get("sysUser");
				userAndOrgMap.put("orgId", info.getSysOrgInfoId()+"");
				userAndOrgMap.put("orgName", info.getName()+"");
				userAndOrgMap.put("userId", sysUser.getUserId()+"");
				userAndOrgMap.put("fullName", sysUser.getFullname()+"");
				//userAndOrgMap.put("sysUser", JSON.toJSONString(sysUser));
				//企业添加成功后同步到子系统
				ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");//**addLog
				return userAndOrgMap;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
			
	 }	
	
}
