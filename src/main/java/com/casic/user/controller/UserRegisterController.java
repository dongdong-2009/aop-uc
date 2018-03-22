package com.casic.user.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.appleframe.common.SpringContextHolder;
import com.casic.model.ChargeSet;
import com.casic.service.UcSysAuditService;
import com.casic.service.config.ChargeSetService;
import com.casic.service.pub.CloudMailService;
import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.casic.tenant.dao.SysOrgInfoRes;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.TenantInfoService;
import com.casic.url.model.UrlBean;
import com.casic.url.service.UrlMonitorService;
import com.casic.user.model.Authorize;
import com.casic.util.EnumUtils;
import com.casic.util.HttpClientUtil;
import com.casic.util.OpenIdUtil;
import com.casic.util.POIExcelUtil;
import com.casic.util.PropertiesUtils;
import com.casic.util.RegexValidateUtil;
import com.casic.util.SendMessage;
import com.casic.util.SmsCache;
import com.casic.util.StringUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.ICache;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 用户注册
 * 
 * @author think
 *
 */
@Controller
@RequestMapping("/user/")
public class UserRegisterController extends BaseController {
	private int isChenkCount = 0;
	private String premobileRandom = null;
	private Map map = new HashMap();
	private Map mobileCodeMap = new HashMap();
	private String premobileCodeValue = null;
	/*
	 * @Resource private SysMsgLogService sysMsgLogService;
	 */
	ThreadLocal<HashMap> regMobileMap = new ThreadLocal<HashMap>();
	@Resource
	private UrlMonitorService urlMonitorService;

	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private SysOrgInfoService sysOrgInfoService;

	@Resource
	private ICache iCache;

	/*
	 * @Resource private JmsTemplate jmsTemplate;
	 */
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private CloudMailService cloudMailService;

	@Resource
	private ChargeSetService chargeSetService;

	@Resource
	private TenantInfoService tenantInfoService;

	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	@Resource
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

	@Resource
	private UcSysAuditService ucSysAuditService;

	@Resource
	private SubSystemService subSystemService;

	private static Long yun_ent_id = 2350300L;

	@Resource
	private SysUserOrgService sysUserOrgService;

	@Resource
	private SubSystemService service;

	private Logger logger = Logger.getLogger(UserRegisterController.class);

	/**
	 * 跳转到授权登录页面
	 */

	@RequestMapping("authorize")
	public ModelAndView authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		ISysUser currentUser = ContextUtil.getCurrentUser();
		if (null == currentUser) {
			mv.setViewName("/authorizeLogin.jsp");
		} else {
			mv.setViewName("/fastLogin.jsp");
			mv.addObject("account", currentUser.getAccount());
			String password = currentUser.getPassword();
			mv.addObject("password", password);
		}
		String authorizeUrl = RequestUtil.getString(request, "url");
		mv.addObject("authorizeUrl", authorizeUrl);
		return mv;
	}

	/**
	 * 跳转到授权快速登录页面
	 */

	@RequestMapping("authorizeFast")
	public ModelAndView authorizeFast(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String authorizeUrl = RequestUtil.getString(request, "url");
		String account = ContextUtil.getCurrentUser().getAccount();
		ModelAndView mv = new ModelAndView("/fastLogin.jsp");
		mv.addObject("authorizeUrl", authorizeUrl).addObject("account", account);
		return mv;
	}

	/**
	 * 快速登录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("fastLogin")
	public void fastLogin(Authorize authorize, HttpServletResponse response) throws Exception {
		String personalAccount = authorize.getPersonalAccount();
		String personalPassword = authorize.getPersonalPassword();
		List<ISysUser> byVip = sysUserService.getByVip(personalAccount, personalPassword);
		ISysUser iSysUser = byVip.get(0);
		SysOrgInfo sysOrgInfo = sysOrgInfoService.getById(iSysUser.getOrgSn());
		String authorizeUrl = authorize.getAuthorizeUrl();
		String account = iSysUser.getAccount();
		String name = sysOrgInfo.getName();
		String email = iSysUser.getEmail();
		String mobile = iSysUser.getMobile();
		Long sysOrgInfoId = sysOrgInfo.getSysOrgInfoId();
		String connecter = sysOrgInfo.getConnecter();
		String returnNewUrl = "http://" + authorizeUrl + "?account=" + account + "&name=" + name + "&email=" + email
				+ "&mobile=" + mobile + "&sysOrgInfoId=" + sysOrgInfoId + "&connecter=" + connecter;
		response.sendRedirect(returnNewUrl);
	}

	/**
	 * 跳转授权页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openProtocol")
	public ModelAndView openProtocol(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/protocol.jsp");
		return mv;
	}

	@RequestMapping("ajaxauthorizeLogin")
	@ResponseBody
	public Map<String, Object> authorizeLogin(Authorize authorize) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<ISysUser> byMobile = sysUserService.getByMobile(authorize.getPersonalAccount(),
				EncryptUtil.encryptSha256(authorize.getPersonalPassword()));
		if (byMobile.size() == 0) {
			List<ISysUser> byVip = sysUserService.getByVip(authorize.getPersonalAccount(),
					EncryptUtil.encryptSha256(authorize.getPersonalPassword()));
			if (byVip.size() == 0) {
				List<ISysUser> byEmail = sysUserService.getByEmail(authorize.getPersonalAccount(),
						EncryptUtil.encryptSha256(authorize.getPersonalPassword()));
				if (byEmail.size() == 0) {
					String orgId = authorize.getOrgId();
					String orgName = authorize.getOrgName();
					List<ISysUser> byOrgIdAndorgName = sysUserService.getByVip(orgId + "_" + orgName,
							EncryptUtil.encryptSha256(authorize.getOrgPassword()));
					if (byOrgIdAndorgName.size() == 0) {
						dataMap.put("status", 2);
						dataMap.put("message", "请输入正确的用户名和密码！！！");
						return dataMap;
					} else {
						ISysUser iSysUser = byOrgIdAndorgName.get(0);
						SysOrgInfo byId = sysOrgInfoService.getById(iSysUser.getOrgSn());
						dataMap.put("status", 1);
						dataMap.put("user", iSysUser);
						dataMap.put("sysOrgInfo", byId);
						dataMap.put("authorizeUrl", authorize.getAuthorizeUrl());
						return dataMap;
					}
				}
				ISysUser iSysUser = byEmail.get(0);
				SysOrgInfo byId = sysOrgInfoService.getById(iSysUser.getOrgSn());
				dataMap.put("status", 1);
				dataMap.put("user", iSysUser);
				dataMap.put("sysOrgInfo", byId);
				dataMap.put("authorizeUrl", authorize.getAuthorizeUrl());
				return dataMap;
			}
			ISysUser iSysUser = byVip.get(0);
			SysOrgInfo byId = sysOrgInfoService.getById(iSysUser.getOrgSn());
			dataMap.put("status", 1);
			dataMap.put("user", iSysUser);
			dataMap.put("sysOrgInfo", byId);
			dataMap.put("authorizeUrl", authorize.getAuthorizeUrl());
			return dataMap;
		} else {
			ISysUser iSysUser = byMobile.get(0);
			SysOrgInfo byId = sysOrgInfoService.getById(iSysUser.getOrgSn());
			dataMap.put("status", 1);
			dataMap.put("user", iSysUser);
			dataMap.put("sysOrgInfo", byId);
			dataMap.put("authorizeUrl", authorize.getAuthorizeUrl());
			return dataMap;
		}
	}

	/***
	 * 跳转到注册页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("reg_cloud")
	public ModelAndView reg_cloud(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemId = RequestUtil.getString(request, "systemId");
		if(systemId==""){
			systemId= "100";
		}
		String serviceUrl = RequestUtil.getString(request, "url");
		String returnUrl = RequestUtil.getUrl(request);
		ISysUser sysUser = ContextUtil.getCurrentUser();
		HttpSession session = request.getSession();
		session.setAttribute("identification", UUID.randomUUID().toString());
		session.setAttribute("mobileRandom", UUID.randomUUID().toString());
		ModelAndView mv = new ModelAndView("/reg_cloud_step1.jsp");
		mv.addObject("systemId", systemId).addObject("returnUrl", returnUrl).addObject("serviceUrl", serviceUrl)
				.addObject("user", sysUser).addObject("isPer", false);
		return mv;
	}

	/**
	 * 跳转到个人注册页面reg_personal_2
	 */
	@RequestMapping("reg_personal_cloud")
	public ModelAndView reg_personal_cloud(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemId = RequestUtil.getString(request, "systemId");
		String serviceUrl = RequestUtil.getString(request, "url");
		String returnUrl = RequestUtil.getUrl(request);
		ISysUser sysUser = ContextUtil.getCurrentUser();
		String AccountRandom = UUID.randomUUID().toString();
		String identification = UUID.randomUUID().toString();
		String televerifycode = UUID.randomUUID().toString();
		iCache.add("AccountRandom", AccountRandom);
		iCache.add("identification", identification);
		iCache.add("televerifycode", televerifycode);
		ModelAndView mv = new ModelAndView("/reg_personal_step1.jsp");
		mv.addObject("systemId", systemId).addObject("returnUrl", returnUrl).addObject("user", sysUser)
				.addObject("isPer", true).addObject("serviceUrl", serviceUrl).addObject("regMobileMap", map)
				.addObject("AccountRandom", AccountRandom).addObject("identification", identification)
				.addObject("televerifycode", televerifycode);
		return mv;
	}

	@RequestMapping("reg_personal_2")
	public ModelAndView reg_personal_2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mobile = RequestUtil.getString(request, "mobile");
		String serviceUrl = RequestUtil.getString(request, "serviceUrl");
		if (mobile == null || "".equals(mobile.trim())) {
			ModelAndView mv = new ModelAndView("/reg_personal_step1.jsp");
			return mv;
		}
		String AccountRandom = UUID.randomUUID().toString();
		String identification = UUID.randomUUID().toString();
		String televerifycode = UUID.randomUUID().toString();
		iCache.add("AccountRandom", AccountRandom);
		iCache.add("identification", identification);
		iCache.add("televerifycode", televerifycode);
		ISysUser sysUser = ContextUtil.getCurrentUser();
		String systemId = RequestUtil.getString(request, "systemId");
		String returnUrl = RequestUtil.getUrl(request);
		ModelAndView mv = new ModelAndView("/reg_personal_step2.jsp");
		mv.addObject("systemId", systemId).addObject("returnUrl", returnUrl).addObject("user", sysUser)
				.addObject("isPer", true).addObject("mobile", mobile).addObject("serviceUrl", serviceUrl)
				.addObject("AccountRandom", AccountRandom).addObject("identification", identification)
				.addObject("televerifycode", televerifycode);
		return mv;
	}

	@RequestMapping("reg_personal_saveuser")
	@ResponseBody
	public ResultMessage reg_personal_saveuser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String systemId = RequestUtil.getSecureString(request, "systemId");
		String account = RequestUtil.getString(request, "account");
		String fullname = RequestUtil.getString(request, "fullname");
		String password = RequestUtil.getString(request, "password");
		String mobile = RequestUtil.getString(request, "mobile");
		Long[] aryRoleId = new Long[] { 10000000270000L };// 角色Id数组(采购主管)
		SysUser sysUser = new SysUser();
		sysUser.setOrgId(2350300l);
		sysUser.setOrgSn(2350300l);
		if (fullname != null) {
			sysUser.setFullname(fullname);
		}
		if (account != null) {
			sysUser.setAccount(account);
			sysUser.setShortAccount(account);
		}
		if (mobile != null) {
			sysUser.setMobile(mobile);
		}
		if (password != null) {
			String enPassword = EncryptUtil.encryptSha256(password);
			sysUser.setPassword(enPassword);
		}
		// if(systemId != null){
		// sysUser.setFromSysId(systemId);
		// }
		sysUser.setIsExpired(Short.valueOf("0"));
		sysUser.setIsLock(Short.valueOf("0"));
		sysUser.setStatus(Short.valueOf("1"));
		sysUser.setSecurityLevel(52);
		sysUser.setIsMobailTrue("1");
		String openId = "";
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		openId = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
		sysUser.setOpenId(openId);
		Date date = new Date();
		sysUser.setCreatetime(date);
		// 创建管理员用户（关联到该企业下，并授予默认的角色）
		ResultMessage rm = null;
		try {
			sysUserService.saveUser(sysUser, yun_ent_id, Short.valueOf("0"), aryRoleId);
			com.casic.util.MessageUtil.sendSysTopic("savePersonal", sysUser.toString());
			rm = new ResultMessage(ResultMessage.Success, "用户添加成功");

			TenantInfo info = new TenantInfo();
			SysOrgInfo byName = sysOrgInfoService.getByName("个人用户");
			System.out.println(byName);
			info.setSysOrgInfoId(byName.getSysOrgInfoId());
			info.setEmail(byName.getEmail());
			info.setName(byName.getName());
			info.setIndustry(byName.getIndustry());
			info.setScale(byName.getScale());
			info.setAddress(byName.getAddress());
			info.setPostcode(byName.getPostcode());
			info.setConnecter(byName.getConnecter());
			info.setTel(byName.getTel());
			info.setFax(byName.getFax());
			info.setHomephone(byName.getHomephone());
			info.setState(byName.getState());
			info.setOpenId(byName.getOpenId());
			info.setSystemId(systemId);

			// info.setFromSysId(systemId);
			// subsystemidJoiningQueue()
			JSONObject json = new JSONObject();
			final JSONObject userJson = json.fromObject(sysUser);
			final JSONObject tenantJson = json.fromObject(info);
			new JMSRunableThread("tenant_add", tenantJson.toString());
			new JMSRunableThread("user_add", userJson.toString());

			rm = new ResultMessage(ResultMessage.Success, "用户添加成功");

			com.casic.util.MessageUtil.sendSysTopic("savePersonal", sysUser.toString());
			// info = (TenantInfo)map.get("sysOrgInfo");
			sysUser = (SysUser) sysUserService.getByAccount(sysUser.getAccount());
			int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "tenant");
			for (InterfaceUrlBean urlBean : urls) {
				Map<String, Object> params = new HashMap<String, Object>();
				// 子系统标识
				long systemId1 = urlBean.getSubId();
				// 企业标识（统一开发标识）(暂时没有)
				String topenId = info.getOpenId();
				// 企业账号
				long tsn = info.getSysOrgInfoId();
				// 企业名称
				String tname = info.getName();
				String fromSysId = info.getSystemId();
				if (fromSysId == null || "".equals(fromSysId)) {
					fromSysId = "100";
				}
				// 会员标识（统一开发标识）
				String uopenId = sysUser.getOpenId();
				// 会员名称
				String uname = sysUser.getAccount();
				// 登录密码
				String pwd = sysUser.getPassword();
				// 手机号
				String mobile1 = sysUser.getMobile();
				// 邮箱
				String email = sysUser.getEmail();
				// 用户名
				String fullName = sysUser.getFullname();
				// 企业状态
				// 默认为未完善"0"
				Short state = 0;
				params.put("topenId", topenId);
				params.put("tsn", tsn);
				params.put("tname", tname);
				params.put("uopenId", uopenId);
				params.put("uname", uname);
				params.put("pwd", pwd);
				params.put("mobile", mobile1);
				params.put("email", email);
				params.put("state", state);
				params.put("fullName", fullName);
				params.put("systemId", systemId1);
				params.put("fromSysId", fromSysId);
				String result = "";
				Date start = new Date();
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
				String startTime = formatter.format(start);
				UrlBean urlMonitor = new UrlBean();
				long id = UniqueIdUtil.genId();
				urlMonitor.setId(id);
				urlMonitor.setUrl(urlBean.getSubIndexUrl() + urlBean.getUrl());

				urlMonitor.setStartTime(startTime);
				urlMonitor.setSubSysId(urlBean.getSubId() + "");
				int isSuccess = 1;
				System.out.println("调用子系统接口结果start地址=" + urlBean.getSubIndexUrl() + urlBean.getUrl());
				try {
					logger.warn("接口调用statrt+++" + urlMonitor.getUrl());
					System.out.println("调用子系统接口结果start地址=" + params);
					System.out.println(urlBean.getSubIndexUrl() + urlBean.getUrl());
					System.out.println(params);
					result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() + urlBean.getUrl(), params);
					// result =
					// HttpClientUtil.JsonPostInvoke("http://10.0.0.3:8080/platform/system/api/tenantAdd.ht",
					// params);
					logger.warn("接口调用end+++" + result);
					System.out.println("调用子系统接口结果end=" + result);
					// result =
					// HttpClientUtil.JsonPostInvoke("http://182.50.1.8:15380/platform/system/api/tenantAdd.ht",
					// params);
				} catch (Exception e) {
					isSuccess = 0;
					System.out.println("调用子系统接口结果end=" + e.getMessage());
					logger.warn("接口调用异常+++" + e.getMessage());
					e.printStackTrace();
				} finally {
					System.out.println("调用子系统接口结果=" + result);
					logger.warn("接口调用异常+++" + result);
					Date end = new Date();
					String endTime = formatter.format(end);
					urlMonitor.setEndTime(endTime);
					urlMonitor.setIsSuccess(isSuccess);
					urlMonitorService.add(urlMonitor);
				}
			}

		} catch (Exception e) {
			rm = new ResultMessage(ResultMessage.Fail, "用户添加失败");
			e.printStackTrace();
		}
		return rm;
	}

	@RequestMapping("perregSuccess")
	public ModelAndView perregSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 各系统注册时传过来的url
		String url = request.getParameter("url");
		String returnUrl = RequestUtil.getUrl(request);
		String systemId = RequestUtil.getString(request, "systemId");
		String account = RequestUtil.getString(request, "account");
		String serviceUrl = RequestUtil.getString(request, "serviceUrl");
		Map<String, String> params = new HashMap<String, String>();
		params.put("${account}", account);
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("account", account);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		Long userid = sysUsers.get(0).getUserId();
		String password = sysUsers.get(0).getPassword();
		String templateCode = "ywmb016";
		String systemid = "10000059380000";
		String userId = String.valueOf(userid);
		SendMessage.sendMessage(params, templateCode, systemid, userId);

		ModelAndView mv = new ModelAndView("/reg_personal_step3.jsp");
		mv.addObject("url", url).addObject("systemId", systemId).addObject("password", password)
				.addObject("account", account).addObject("serviceUrl", serviceUrl).addObject("returnUrl", returnUrl)
				.addObject("isPer", true);
		return mv;
	}

	/***
	 * 跳转到注册页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userManage")
	public ModelAndView userManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ISysUser user =
		// (ISysUser)(request.getSession().getAttribute("user"));
		int state = 1; // 判断是否新注册用户，为1表示非新注册用户，0表示为新注册个人用户
		int isNew = 2; // 判断是否新注册用户，为1表示企业用户 ，0表示为新注册个人用户
		ISysUser user = ContextUtil.getCurrentUser();
		Long tenantInfoId = (user.getOrgSn() != null && user.getOrgSn() != 0L) ? user.getOrgSn() : user.getOrgId();
		SysOrgInfo sysOrgInfo = sysOrgInfoService.getById(tenantInfoId);
		// 各系统注册时传过来的url
		String url = request.getParameter("url");
		ModelAndView mv = new ModelAndView("/userManage.jsp");
		/**
		 * 增加个人用户创建企业的逻辑
		 */

		if (("2350300").equalsIgnoreCase(tenantInfoId + "")) {
			// 如果是个人用户
			// 查看该用户是否为新注册个人用户
			String sql = "select count(id) from sys_user_extence where user_id = " + user.getUserId();
			state = jdbcTemplate.queryForInt(sql);
			if (state == 1) {
				// 查看该用户是否为新注册个人用户
				String sqlStr = "select state from sys_user_extence where user_id = " + user.getUserId();
				isNew = jdbcTemplate.queryForInt(sqlStr);
			} else if (state == 0) {
				// 当中间表中不存在，且为个人用户时，则此为新注册个人用户
				isNew = 0;
			} else if (state > 1) {
				// 若是超过一条，则删剩为一条
				sql = "DELETE FROM sys_user_extence WHERE user_id = " + user.getUserId() + " ORDER BY id asc"
						+ " LIMIT " + (state - 1);
				jdbcTemplate.execute(sql);
				// 查看该用户是否为新注册个人用户
				String sqlStr = "select state from sys_user_extence where user_id = " + user.getUserId();
				isNew = jdbcTemplate.queryForInt(sqlStr);
			}
		}
		if (user != null) {
			mv.addObject("url", url).addObject("user", user).addObject("sysOrgInfo", sysOrgInfo)
					.addObject("state", state).addObject("isNew", isNew);
		} else {
			mv.addObject("url", url).addObject("user", sysUserService.getById((Long) iCache.getByKey("userId")))
					.addObject("sysOrgInfo", sysOrgInfo).addObject("state", state).addObject("isNew", isNew);
		}
		return mv;
	}

	/**
	 * @param request
	 * @param response
	 * @param sysOrgInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("regPostCloud_v2")
	@Action(description = "云网企业注册")
	public ModelAndView regPostCloud_v2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String userName, @RequestParam String email, @RequestParam String mobile,
			@RequestParam String repeatPassword, @RequestParam String verifycode) throws Exception {

		String url = request.getParameter("url");
		String localurl = request.getParameter("localurl");
		ModelAndView mav = new ModelAndView("/regSuccess_v3.jsp").addObject("localurl", localurl);

		SysUser user = new SysUser();

		try {
			user = regUser(userName, repeatPassword, email, mobile, verifycode, mav);
			// 用户注册成功后推送到子系统
			int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "user");
			for (InterfaceUrlBean urlBean : urls) {
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("sysUser", user);
				params.put("data", data);
				params.put("systemId", urlBean.getSubId());
				String result = "";
				Date start = new Date();
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
				String startTime = formatter.format(start);
				UrlBean urlMonitor = new UrlBean();
				long id = UniqueIdUtil.genId();
				urlMonitor.setId(id);
				urlMonitor.setUrl(urlBean.getSubIndexUrl() + urlBean.getUrl());
				urlMonitor.setStartTime(startTime);
				urlMonitor.setSubSysId(urlBean.getSubId() + "");
				int isSuccess = 1;
				try {
					result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() + urlBean.getUrl(), params);
				} catch (Exception e) {
					isSuccess = 0;
					e.printStackTrace();
				} finally {
					System.out.println("调用子系统接口结果=" + result);
					Date end = new Date();
					String endTime = formatter.format(end);
					urlMonitor.setEndTime(endTime);
					urlMonitor.setIsSuccess(isSuccess);
					urlMonitorService.add(urlMonitor);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("dePassword", repeatPassword).addObject("mobile", mobile).addObject("email", email)
				.addObject("userName", userName).addObject("url", url).addObject("openId", user.getOpenId());
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param sysOrgInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateUser")
	@Action(description = "云网企业注册")
	public void updateUser(HttpServletRequest request, HttpServletResponse response, @RequestParam String account,
			@RequestParam String mobile, @RequestParam String verifycode, @RequestParam String userId)
					throws Exception {
		// 获得当前登录人
		// ISysUser user =
		// (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		long orgId = user.getOrgId();
		String resultMsg = null;
		String url = request.getParameter("url");
		String localurl = request.getParameter("localurl");
		ModelAndView mav = new ModelAndView("/regSuccess_v3.jsp").addObject("localurl", localurl);
		if (!SmsCache.verify(mobile, verifycode)) {
			resultMsg = "验证码有误";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
		} else {
			SmsCache.remove(mobile);
			// 判断account是否已经存在
			if (!account.equals(user.getShortAccount())) {
				boolean isExist = sysUserService.isAccountInCompanyExist(account, orgId);
				if (isExist) {
					resultMsg = "该账号已经存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				}
			}
			user.setAccount(account);
			user.setMobile(mobile);
			user.setIsMobailTrue("1");
			user.setShortAccount(account);
			user.setUpdateTimes(1L);
			sysUserService.update(user);
			JSONObject json = new JSONObject();
			final JSONObject userJson = json.fromObject(user);
			new JMSRunableThread("user_update", userJson.toString());
			/*
			 * Map<String, Object> mqparams = new HashMap<String, Object>();
			 * mqparams.put("mqName", "updateUser"); mqparams.put("mqValue",
			 * user.toString()); mqparams.put("systemId", "100"); String res1 =
			 * ""; res1 = HttpClientUtil.JsonPostInvoke(
			 * "http://mqt.casicloud.com/api/sysTopicSender.ht", mqparams);
			 * System.out.println("调用子系统接口结果=" + res1);
			 */
			resultMsg = "更新成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);

		}
	}

	@RequestMapping("saveFullName")
	@ResponseBody
	public Map<String, String> saveFullName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		String userFullName = request.getParameter("userFullName");
		String userId = request.getParameter("userId");
		ISysUser sysUser = ContextUtil.getCurrentUser();
		// ISysUser sysUser = sysUserService.getById(Long.parseLong(userId));
		if (sysUser != null) {
			sysUser.setFullname(userFullName);
			sysUserService.updateFullName(userFullName, Long.parseLong(userId));
			com.casic.util.MessageUtil.sendSysTopic("updateUser", sysUser);
			// 会员用户姓名更新
			dataMap.put("flag", "2");
			return dataMap;
		}
		// 用户不存在
		dataMap.put("flag", "1");
		return dataMap;
	}

	public SysUser regUser(String userName, String userPwd, String email, String mobile, String verifycode,
			ModelAndView mav) throws NumberFormatException, Exception {
		String resultMsg = null;
		Long[] aryRoleId = new Long[] { 10000000270000L };// 角色Id数组(采购主管)
		String fullName = userName;
		String sex = "1";
		Long entId = yun_ent_id;
		String enPassword = "";
		SysUser sysUser = new SysUser();
		mav.addObject("result", "1");
		// TODO 待修改为数据库验证方式
		if (!SmsCache.verify(mobile, verifycode)) {
			resultMsg = "验证码有误";
			mav.addObject("resultMessage", resultMsg);
		} else {
			SmsCache.remove(mobile);
			if (userName != null && entId != null) {
				boolean isExist = sysUserService.isAccountInCompanyExist(userName, entId);
				if (isExist) {
					resultMsg = "该账号已经存在";
					mav.addObject("resultMessage", resultMsg);
				} else {
					enPassword = EncryptUtil.encryptSha256(userPwd);
					sysUser.setPassword(enPassword);
					// 【TIANZHI】
					ISysOrg sysOrg = sysOrgService.getById(entId);
					// 登录账号
					sysUser.setShortAccount(userName);
					sysUser.setFullname(fullName);
					sysUser.setSex(sex);
					sysUser.setMobile(mobile);
					sysUser.setOrgId(sysOrg.getOrgId());
					sysUser.setOrgSn(sysOrg.getSn());
					// X3原来的账号,为了保证X3账号唯一性，加上企业账号
					sysUser.setAccount(sysOrg.getSn() + "_" + userName);
					sysUser.setIsLock(Short.valueOf("0"));
					sysUser.setIsExpired(Short.valueOf("0"));
					sysUser.setStatus(Short.valueOf("1"));
					sysUser.setEmail(email);
					// 新增加openId
					String openId = "";
					String s = UUID.randomUUID().toString();
					// 去掉“-”符号
					openId = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23)
							+ s.substring(24);
					sysUser.setOpenId(openId);
					sysUserService.saveUser(sysUser, entId, Short.valueOf("0"), aryRoleId);
					// resultMsg = "添加用户成功";
					resultMsg = openId;
				}
				priThread pth = new priThread(email, mobile, userName, userPwd);
				Thread th = new Thread(pth);
				th.start();
			}
		}

		return sysUser;
	}

	/**
	 * 通过用户id将登录信息放入cookie
	 * 
	 * @param request
	 * @param response
	 * @param hlUserId
	 * @param password
	 * @throws Exception
	 */
	public void addUserToCookie(HttpServletRequest request, HttpServletResponse response, Long hlUserId,
			String password) throws Exception {
		String enPassword = EncryptUtil.encryptSha256(password);

		ISysUser user = sysUserService.getById(hlUserId);
		if (user != null && user.getPassword().equalsIgnoreCase(enPassword)) {
			// 设置到session中
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getAccount(),
					password);

			authRequest.setDetails(new WebAuthenticationDetails(request));

			SecurityContext securityContext = SecurityContextHolder.getContext();

			Authentication auth = authenticationManager.authenticate(authRequest);

			securityContext.setAuthentication(auth);

			request.getSession().setAttribute(WebAttributes.LAST_USERNAME, user.getFullname());

			sessionStrategy.onAuthentication(auth, request, response);

			// 从session中删除组织数据。
			ContextUtil.removeCurrentOrg(request, response);

			// 将用户设置到Session中
			ContextUtil.setCurrentUserAccount(user.getAccount());
			request.getSession().setAttribute("fullname", user.getFullname());
			request.getSession().setAttribute("Principal", user);
			// 删除cookie。
			CookieUitl.delCookie("loginAction", request, response);

			/*
			 * writeRememberMeCookie(request, response, user.getAccount(),
			 * user.getPassword());
			 */

			CookieUitl.addCookie("loginAction", "cloud", request, response);
		}
	}

	/***
	 * 修改密码页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("updatePwd")
	@Action(description = "修改密码")
	public void modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 获得当前登录人
		// ISysUser user =
		// (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		String password = request.getParameter("password");
		String enPassword = EncryptUtil.encryptSha256(password);
		String newPassword = request.getParameter("newpassword");
		String newPwd = EncryptUtil.encryptSha256(newPassword);
		// ISysUser sysUser = sysUserService.getById(userId);
		// ISysUser sysUser =
		// (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser sysUser = ContextUtil.getCurrentUser();
		String truepassword = sysUser.getPassword();
		if (StringUtil.isEmpty(newPassword) || StringUtil.isEmpty(password)) {
			writeResultMessage(response.getWriter(), "输入的密码不能为空", ResultMessage.Fail);
		} else if (!truepassword.equals(enPassword)) {
			writeResultMessage(response.getWriter(), "你输入的原始密码不正确", ResultMessage.Fail);
		}

		else if (password.equals(newPassword)) {
			writeResultMessage(response.getWriter(), "你修改的密码和原始密码相同", ResultMessage.Fail);
		} else {
			try {
				sysUserService.updPwd(sysUser.getUserId(), newPassword);
				sysUser.setPassword(newPwd);
				/*
				 * Map<String, Object> mqparams = new HashMap<String, Object>();
				 * mqparams.put("mqName", "updateUser"); mqparams.put("mqValue",
				 * sysUser.toString()); mqparams.put("systemId", "100"); String
				 * res1 = ""; res1 = HttpClientUtil.JsonPostInvoke(
				 * "http://mqt.casicloud.com/api/sysTopicSender.ht", mqparams);
				 * System.out.println("调用子系统接口结果=" + res1);
				 */
				com.casic.util.MessageUtil.sendSysTopic("updateUser", sysUser);

				JSONObject json = new JSONObject();
				final JSONObject userJson = json.fromObject(sysUser);

				new JMSRunableThread("user_update", userJson.toString());

				sysUser.setPassword(EncryptUtil.encryptSha256(newPassword));
				request.getSession().setAttribute("user", sysUser);
				// 密码修改后推送到各子系统

				int i = 2;
				List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "pwd");
				for (InterfaceUrlBean urlBean : urls) {
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("openId", user.getOpenId());
					data.put("pwd", newPassword);
					params.put("data", data);
					params.put("systemId", urlBean.getSubId());
					String result = "";
					Date start = new Date();
					java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
					String startTime = formatter.format(start);
					UrlBean urlMonitor = new UrlBean();
					long id = UniqueIdUtil.genId();
					urlMonitor.setId(id);
					urlMonitor.setUrl(urlBean.getSubIndexUrl() + urlBean.getUrl());
					urlMonitor.setStartTime(startTime);
					urlMonitor.setSubSysId(urlBean.getSubId() + "");
					int isSuccess = 1;
					try {
						result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() + urlBean.getUrl(), params);
					} catch (Exception e) {
						isSuccess = 0;
						e.printStackTrace();
					} finally {
						System.out.println("调用子系统接口结果=" + result);
						Date end = new Date();
						String endTime = formatter.format(end);
						urlMonitor.setEndTime(endTime);
						urlMonitor.setIsSuccess(isSuccess);
						urlMonitorService.add(urlMonitor);
					}
				}
				// 推送完成
				writeResultMessage(response.getWriter(), "修改密码成功", ResultMessage.Success);
			} catch (Exception ex) {
				String str = MessageUtil.getMessage();
				if (str != null && !"".equals(str)) {
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, "修改密码失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}
	}

	/**
	 * 检查用户帐户是否重复,isExist= "true":无重复，isExist= "false":有重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkUserNameRepeat")
	@ResponseBody
	public boolean checkUserNameRepeat(HttpServletRequest request) throws Exception {

		boolean isExist = false;// 返回true是已经存在，false是不存在

		String userName = RequestUtil.getString(request, "account");

		if (StringUtil.isEmpty(userName)) {

			return true;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("orgId", 2350300);
		queryFilter.getFilters().put("shortAccount", userName);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if (sysUsers != null) {
			if (sysUsers.size() > 0) {
				isExist = false;
			} else {
				isExist = true;
			}
		} else {
			isExist = true;
		}

		return isExist;
	}

	/**
	 * 检查用户邮箱是否重复,isExist= "true":无重复，isExist= "false":有重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkEmailRepeat")
	@ResponseBody
	public boolean checkEmailRepeat(HttpServletRequest request) throws Exception {

		boolean isExist = false;// 返回true是已经存在，false是不存在
		String email = RequestUtil.getString(request, "email");
		if (StringUtil.isEmpty(email)) {
			return false;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("email", email);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if (sysUsers != null) {
			if (sysUsers.size() > 0) {
				isExist = false;
			} else {
				isExist = true;
			}
		} else {
			isExist = true;
		}

		return isExist;
	}

	/**
	 * 检查用户电话是否重复,isExist= "true":无重复，isExist= "false":有重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * //为true的时候验证通过  为false验证不通过
	 */
	private Map checkPhoneRepeatMap = new HashMap();

	@RequestMapping("checkPhoneRepeat")
	@ResponseBody
	public boolean checkPhoneRepeat(HttpServletRequest request) throws Exception {

		boolean isExist = false;// 为true的时候验证通过  为false验证不通过
		HttpSession session = request.getSession();
		String mobileRandom = RequestUtil.getString(request, "mobileRandom");
		String oldValue = (String) session.getAttribute("mobileRandom");
		System.out.println(oldValue + "---");
		if (!mobileRandom.equals(oldValue)) {
			// session.removeAttribute("mobileRandom");
			return false;
		}
		// 限制三次请求
		int i = (int) (checkPhoneRepeatMap.get(mobileRandom) == null ? 0 : mobileCodeMap.get(mobileRandom));
		if (i > 2) {
			// mobileCodeMap =new HashMap();
			return false;
		}
		i = i + 1;
		mobileCodeMap.put(mobileRandom, i);

		String mobile = RequestUtil.getString(request, "mobile");
		if (StringUtil.isEmpty(mobile)) {
			return false;
		} else {
			QueryFilter queryFilter = new QueryFilter(request, true);
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("mobile", mobile);
			List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
			if (sysUsers != null) {
				if (sysUsers.size() > 0) {
					isExist = false;
				} else {
					isExist = true;
				}
			} else {
				isExist = true;
			}
			return isExist;
		}
	}

	/**
	 * 企业管理员创建子账号时校验手机号是否重复
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("isPhoneRepeat")
	@ResponseBody
	public boolean isPhoneRepeat(HttpServletRequest request) throws Exception {
		boolean isExist = false;// 前台传过来的手机号在数据库是否重复，true是重复，false是不重复
		String mobile = RequestUtil.getString(request, "mobile");
		if (StringUtil.isEmpty(mobile)) {
			return false;
		} else {
			QueryFilter queryFilter = new QueryFilter(request, true);
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("mobile", mobile);
			List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
			if (sysUsers != null) {
				if (sysUsers.size() > 0) {
					isExist = true;//手机号重复
				} else {
					isExist = false;//手机号不重复
				}
			} else {
				isExist = false;//手机号不重复
			}
			return isExist;
		}
	}
	private Map map2 =new HashMap();

	@RequestMapping("checkPhoneRepeatString")
	@ResponseBody
	public String checkPhoneRepeatString(HttpServletRequest request) throws Exception {

		String isExist = "false";// 返回true是已经存在，false是不存在 false绑定
		HttpSession session = request.getSession();
		String mobileRandom = RequestUtil.getString(request, "mobileRandom");
		String oldValue = (String) session.getAttribute("mobileRandom");
		System.out.println(oldValue + "---" + mobileRandom);
		if (!mobileRandom.equals(oldValue)) {
			// session.removeAttribute("mobileRandom");
			return "fasle";
		}

		// 限制三次请求
		int i = (int) (map2.get(mobileRandom) == null ? 0 : map2.get(mobileRandom));
		if (i > 2) {
			// map2 =new HashMap();
			// return "请关闭浏览器，刷新再试！";
			return "请关闭浏览器，刷新再试！";
		}
		i = i + 1;
		map2.put(mobileRandom, i);
		String mobile = RequestUtil.getString(request, "mobile");
		if (StringUtil.isEmpty(mobile)) {
			return "false";
		} else {
			QueryFilter queryFilter = new QueryFilter(request, true);
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("mobile", mobile);
			List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
			if (sysUsers != null) {
				if (sysUsers.size() > 0) {
					isExist = "false";
				} else {
					isExist = "true";
				}
			} else {
				isExist = "true";
			}
			return isExist;
		}
	}

	/**
	 * 密码找回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findPwd")
	public ModelAndView findPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemId = RequestUtil.getString(request, "systemId");
		HttpSession session = request.getSession();
		session.setAttribute("identification", UUID.randomUUID().toString());
		session.setAttribute("mobileRandom", UUID.randomUUID().toString());
		ModelAndView mv = new ModelAndView("/findPwd.jsp");
		mv.addObject("systemId", systemId);
		return mv;
	}

	/**
	 * 检查用户邮箱或手机号是否存在
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("isParamExist")
	@ResponseBody
	public boolean isParamExist(HttpServletRequest request) throws Exception {
		boolean isExist = true;
		HttpSession session = request.getSession();
		String mobileRandom = RequestUtil.getString(request, "mobileRandom");
		if (StringUtil.isEmpty(mobileRandom)) {
			return false;
		}
		int i = (int) (map.get(mobileRandom) == null ? 0 : map.get(mobileRandom));
		if (i > 2) {
			return false;
		}
		i = i + 1;
		map.put(mobileRandom, i);
		String oldValue = (String) session.getAttribute("mobileRandom");
		if (!mobileRandom.toString().equals(oldValue.toString())) {

			return false;
		}
		System.out.println(oldValue + "****" + mobileRandom);
		String mailmobile = RequestUtil.getString(request, "mailmobile");

		if (StringUtil.isEmpty(mailmobile)) {
			return true;
		}
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		QueryFilter queryFilter = new QueryFilter(request, true);
		// 判断是否为手机号
		if (!StringUtil.isMobileNO(mailmobile)) {
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("email", mailmobile.trim());
			sysUsers = sysUserService.getAll(queryFilter);
			if (sysUsers != null) {
				if (sysUsers.size() > 0) {
					isExist = true;
				} else {
					isExist = false;
				}
			} else {
				isExist = false;
			}
		} else {
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("mobile", mailmobile.trim());
			sysUsers = sysUserService.getAll(queryFilter);
			if (sysUsers != null) {
				if (sysUsers.size() > 0) {
					isExist = true;
				} else {
					isExist = false;
				}
			} else {
				isExist = false;
			}
		}
		return isExist;
	}

	/**
	 * 检查用户邮箱或手机号是否存在
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("isParamExistReturnString")
	@ResponseBody
	public String isParamExistReturnString(HttpServletRequest request) throws Exception {
		String isExist = "true";
		HttpSession session = request.getSession();

		// 页面请求
		String mobileRandom = RequestUtil.getString(request, "mobileRandom");
		if (StringUtil.isEmpty(mobileRandom)) {
			return "false";
		}

		String oldValue = (String) session.getAttribute("mobileRandom");
		if (!mobileRandom.toString().equals(oldValue.toString())) {

			return "false";
		}

		// 限制三次请求
		int i = (int) (map.get(mobileRandom) == null ? 0 : map.get(mobileRandom));
		if (i > 2) {
			// map =new HashMap();
			return "请关闭浏览器，刷新再试！";
		}
		i = i + 1;
		map.put(mobileRandom, i);

		System.out.println(oldValue + "**" + i + "**" + mobileRandom);
		String mailmobile = RequestUtil.getString(request, "mailmobile");

		if (StringUtil.isEmpty(mailmobile)) {
			return "false";
		}
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		QueryFilter queryFilter = new QueryFilter(request, true);
		// 判断是否为手机号
		if (!StringUtil.isMobileNO(mailmobile)) {
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("email", mailmobile.trim());
			sysUsers = sysUserService.getAll(queryFilter);
			if (sysUsers != null) {
				if (sysUsers.size() > 0) {
					isExist = "true";
				} else {
					isExist = "false";
				}
			} else {
				isExist = "false";
			}
		} else {
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("mobile", mailmobile.trim());
			sysUsers = sysUserService.getAll(queryFilter);
			if (sysUsers != null) {
				if (sysUsers.size() > 0) {
					isExist = "true";
				} else {
					isExist = "false";
				}
			} else {
				isExist = "false";
			}
		}
		return isExist;
	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private Map map6 = new HashMap();

	@RequestMapping("resetPwd")
	@ResponseBody
	public String resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mailmobile = RequestUtil.getString(request, "mailmobile");
		String repeatPassword = RequestUtil.getString(request, "password");
		String verifycode = RequestUtil.getString(request, "verifycode");
		String identification = RequestUtil.getString(request, "identification");
		String resultMessage = "重置密码成功";

		// 限制三次请求
		int i = (int) (map6.get(identification) == null ? 0 : map6.get(identification));
		if (i > 2) {
			return "请关闭浏览器，刷新再试！";
		}
		i = i + 1;
		map6.put(identification, i);

		QueryFilter queryFilter = new QueryFilter(request, false);
		String path = request.getContextPath();
		String basePath;
		if (request.getServerPort() != 80) {
			basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
					+ "/";
		} else {
			basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
		}
		if (!SmsCache.verify(mailmobile, verifycode)) {
			resultMessage = "验证码有误";
		} else {
			List<ISysUser> sysUserList = new ArrayList<ISysUser>();
			String password = EncryptUtil.encryptSha256(repeatPassword);
			SysUser sysUser = new SysUser();
			// 校验修改号码为邮箱时
			if (!StringUtil.isMobileNO(mailmobile)) {
				queryFilter.getFilters().clear();
				queryFilter.addFilter("email", mailmobile);
				sysUserList = sysUserService.getAll(queryFilter);
				if (null != sysUserList && sysUserList.size() == 1) {
					// 重置密码
					sysUser = (SysUser) sysUserList.get(0);
					sysUser.setPassword(password);
					sysUserService.update(sysUser);
					JSONObject json1 = new JSONObject();
					final JSONObject userJson = json1.fromObject(sysUser);
					new JMSRunableThread("user_update", userJson.toString());
					SmsCache.remove(mailmobile);
				} else {
					resultMessage = "用户不存在或邮箱错误！";
				}
			} else {// 修改时输入的为手机号时
				queryFilter.getFilters().clear();
				queryFilter.addFilter("mobile", mailmobile);
				sysUserList = sysUserService.getAll(queryFilter);
				if (null != sysUserList && sysUserList.size() == 1) {
					// 重置密码
					sysUser = (SysUser) sysUserList.get(0);
					sysUser.setPassword(password);
					sysUserService.update(sysUser);
					JSONObject json1 = new JSONObject();
					final JSONObject userJson = json1.fromObject(sysUser);
					new JMSRunableThread("user_update", userJson.toString());
					SmsCache.remove(mailmobile);
				} else {
					resultMessage = "用户不存在或手机号错误！";
				}
			}
		}
		return resultMessage;

	}

	/**
	 * 用户同步
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("synchronize")
	public ModelAndView synchronize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = ContextUtil.getCurrentUserId();
		ModelAndView mv = new ModelAndView("/user/userSynchronize.jsp");
		QueryFilter queryFilter = new QueryFilter(request, "userSynchronizeItem");
		String systemid = request.getParameter("temid");
		if (!StringUtil.isEmpty(systemid)) {
			queryFilter.addFilter("fromSysId", systemid);
		}

		List<ISysUser> userSynchronizeList = sysUserService.querySynchronizeList(queryFilter);
		if (!StringUtil.isEmpty(systemid)) {
			mv.addObject("systemid", systemid);
		}
		return mv.addObject("userSynchronizeList", userSynchronizeList).addObject("userId", userId);
	}

	// excel导出
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

		QueryFilter queryFilter = new QueryFilter(request, "userSynchronizeItem");
		queryFilter.getFilters().clear();
		try {
			queryFilter.getPageBean().setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
			queryFilter.getPageBean().setPagesize(Integer.parseInt(request.getParameter("pageSize")));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<ISysUser> list = sysUserService.querySynchronizeList(queryFilter);
		// 构造页面数据
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();

		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("用户数据");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();

		// 设置单元格垂直居中对齐
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		style.setWrapText(true);
		// 设置单元格字体样式
		HSSFFont font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		style.setFont(font);
		// 设置单元格边框为细线条

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("企业名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("账户");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("联系人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("手机号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("邮箱");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("注册时间");
		cell.setCellStyle(style);

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			SysUser sysUser = (SysUser) list.get((i));
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue((double) (i + 1));
			row.createCell((short) 1).setCellValue(sysUser.getOrgName());
			row.createCell((short) 2).setCellValue(sysUser.getAccount());
			row.createCell((short) 3).setCellValue(sysUser.getConnecter());
			row.createCell((short) 4).setCellValue(sysUser.getMobile());
			row.createCell((short) 5).setCellValue(sysUser.getEmail());
			row.createCell((short) 6).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(sysUser.getCreatetime()));
			cell.setCellValue("注册时间");
		}
		// 第六步，将文件存到指定位置
		String fileName = new String(("用户数据").getBytes("gb2312"), "iso8859-1") + ".xls";
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/vnd.ms-excel;charset=gb2312");

		try {

			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("management")
	public ModelAndView management(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISysUser user = ContextUtil.getCurrentUser();
		Long orgSn = (!StringUtil.isEmpty(user.getOrgSn()) ? user.getOrgSn() : user.getOrgId());
		QueryFilter queryFilter = new QueryFilter(request, "userManagementItem");
		queryFilter.getFilters().put("orgSn", orgSn);
		List<ISysUser> userManagementList = sysUserService.getUserByOrgSn(queryFilter);
		for (ISysUser iSysUser : userManagementList) {
			// 去个人用户表中查询是否由个人用户申请加入
			String sql = "select count(*) from  sys_user_extence  where state = 1 and user_id=" + iSysUser.getUserId();
			int result = jdbcTemplate.queryForInt(sql);
			if (result > 0) {
				iSysUser.setIsIndividual("1");
			} else {
				iSysUser.setIsIndividual("0");
			}
			List<UserRole> roleList = userRoleService.getByUserIdAndTenantId(iSysUser.getUserId(), orgSn);
			boolean isManager = false;
			if (roleList != null) {
				for (UserRole userRole : roleList) {
					if ((userRole.getRoleName()).contains("企业管理员")) {
						isManager = true;
					}
				}
			}

			if (isManager) {
				iSysUser.setIsCharge(1);//是企业管理员
			} else {
				iSysUser.setIsCharge(0);
			}

		}
		ModelAndView mv = new ModelAndView("/user/userManagement.jsp");
		return mv.addObject("userManagementList", userManagementList).addObject("user", user).addObject("userNum",
				userManagementList.size());
	}

	@RequestMapping("edit")
	@Action(description = "编辑")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/user/userEdit.jsp");
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
	    ISysUser currentUser = ContextUtil.getCurrentUser();
		ISysUser sysUser = null;
		Boolean flag=false;
		if (id != 0) {
			sysUser = sysUserService.getById(id);
			if ("confadmin".equals(currentUser.getAccount())) {
				flag=false;//现在子系统管理员添加和修改
				List<SubSystem> list = subSystemService.getActiveSystem();
				mv.addObject("subsystemList", list);
			}
		} else {
			  sysUser = new SysUser();
				if ("confadmin".equals(currentUser.getAccount())) {
					 flag=true;
					 List<SubSystem> list = subSystemService.getActiveSystem();
					 mv.addObject("subsystemList", list);
				}
		}
		mv.addObject("flag",flag).addObject("currentUser", currentUser);
		return mv.addObject("sysUser", sysUser).addObject("returnUrl", returnUrl);
	}

/*	ModelAndView mv = new ModelAndView();
	Long id = RequestUtil.getLong(request, "userId");
	String returnUrl = RequestUtil.getPrePage(request);
	ISysUser sysUser = null;
	Boolean flag=false;
	if (id != 0) {
		sysUser = sysUserService.getById(id);
		if (sysUser.getFromSysId()!=null) {
			flag=true;//系统管理员
			List<SubSystem> list = subSystemService.getActiveSystem();
			mv.addObject("subsystemList", list);
		}
	} else {
		
		sysUser = new SysUser();
		flag=true;
		List<SubSystem> list = subSystemService.getActiveSystem();
		mv.addObject("subsystemList", list);
	}
	mv.addObject("systemId", sysUser.getFromSysId());
	flag=false;
	mv.addObject("flag",flag);//用来区别子系统管理员的和普通用户得编辑的，现在没有使用统一使用Boolean flag=false;
	mv.setViewName("/subsystem/subManager.jsp");
	return 	mv.addObject("sysUser", sysUser).addObject("returnUrl", returnUrl);
*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("del")
	@Action(description = "删除")

	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			for (Long uid : lAryId) {
				ISysUser user = sysUserService.getById(uid);
				Long[] id1 = new Long[1];
				id1[0] = uid;
				try {
					String openId = user.getOpenId();
					String result = "";
					int i = 3;
					List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "user");
					for (InterfaceUrlBean urlBean : urls) {
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Object> data = new HashMap<String, Object>();
						// 子系统标识
						long systemId = urlBean.getSubId();
						data.put("openId", openId);
						// data.put("aptitudes", aptitudes);
						params.put("systemId", systemId);
						params.put("data", data);
						result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() + urlBean.getUrl(), params);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// sysUserService.delByIds(id1);
				ISysUser byId = sysUserService.getById(uid);
				sysUserService.delById(uid);
				JSONObject json = new JSONObject();
				Map<String, Object> OperationMarked = new HashMap<String, Object>();
				OperationMarked.put("操作说明", "修改用户：1,删除用户：3");
				OperationMarked.put("operationSatus", 3);// 操作标示
				json.put("OperationMarked", OperationMarked);// 操作标示：分配角色：1,删除角色：3
				json.put("userId", uid);
				new JMSRunableThread("user_del", json.toString());
				message = new ResultMessage(ResultMessage.Success, "删除用户成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.Fail, "删除用户失败");
		} finally {
			addMessage(message, request);
			response.sendRedirect(preUrl);
		}
	}

	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, SysUser sysUser,
			BindingResult bindResult) throws Exception {
	
		ResultMessage resultMessage = validForm("sysUser", sysUser, bindResult, request);
		
		  if (resultMessage.getResult() == ResultMessage.Fail) {
		  writeResultMessage(response.getWriter(), resultMessage); return; }
		 
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
				resultMsg = getText("账号添加失败,该账号已经存在!","用户表");
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
			sysUser.setIsLock((short) 0);
			sysUser.setIsExpired((short) 0);
			sysUser.setStatus((short) 1);
			sysUser.setOpenId(OpenIdUtil.getOpenId());
			sysUserService.add(sysUser);
			// 添加用户和组织关系。
			sysUserOrgService.saveUserOrg(userId, aryOrgId, 1l, aryOrgCharge);
			resultMsg = getText("恭喜您，您创建的子账号已成功开通，请尽快完成验证", "用户表");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			JSONObject json = new JSONObject();
			final JSONObject userJson = json.fromObject(sysUser);

			new JMSRunableThread("user_add", userJson.toString());
			// 调用子系统接口
			try {
				// 遍历所有接口地址，将数据同步到各子系统
				String result = "";
				int i = 1;
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
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			boolean isExist = sysUserService.isAccountExistForUpd(sysUser.getUserId(), sysUser.getAccount());
			if (isExist) {
				resultMsg = getText("账号添加失败,该账号已经存在!", "用户表");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return;
			}
			ISysUser user = sysUserService.getById(sysUser.getUserId());
			if (!StringUtil.isEmpty(sysUser.getPassword())) {
				String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());
				sysUser.setPassword(enPassword);
			} else {
				sysUser.setPassword(user.getPassword());
			}

			sysUser.setOrgId(user.getOrgId());
			sysUser.setOrgSn(user.getOrgSn());
			sysUser.setCreatetime(user.getCreatetime());
			sysUser.setIsLock(user.getIsLock());
			sysUser.setIsExpired(user.getIsExpired());
			sysUser.setStatus(user.getStatus());
			sysUser.setOpenId(user.getOpenId());
			sysUserService.update(sysUser);
			JSONObject json = new JSONObject();
			final JSONObject userJson = json.fromObject(sysUser);
			new JMSRunableThread("user_update", userJson.toString());
			com.casic.util.MessageUtil.sendSysTopic("updateUser", sysUser);
			// 添加用户和组织关系。
			sysUserOrgService.saveUserOrg(user.getUserId(), new Long[] { user.getOrgId() }, 1l, aryOrgCharge);
			resultMsg = getText("账号更新成功", "用户表");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			// 调用子系统接口
		}
	}

	/*
	 * try { // 遍历所有接口地址，将数据同步到各子系统 String result = ""; int i = 2;
	 * List<InterfaceUrlBean> urls =
	 * subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "user"); for
	 * (InterfaceUrlBean urlBean : urls) { Map<String, Object> params = new
	 * HashMap<String, Object>(); Map<String, Object> data = new HashMap<String,
	 * Object>(); // 子系统标识 long systemId = urlBean.getSubId();
	 * data.put("sysUser", sysUser); // data.put("aptitudes", aptitudes);
	 * params.put("systemId", systemId); params.put("data", data); result =
	 * HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() +
	 * urlBean.getUrl(), params); System.out.println(result); } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 * 
	 * }
	 */

	class priThread extends Thread {
		private String emaill;
		String mobile;
		String userName;
		String userPwd;

		public priThread(String emaill, String mobile, String userName, String userPwd) {
			this.emaill = emaill;
			this.mobile = mobile;
			this.userName = userName;
			this.userPwd = userPwd;
		}

		public void run() {
			cloudMailService.sendRegMessagePersonal(emaill, mobile, userName, userPwd);
		}

		public String getEmaill() {
			return emaill;
		}

		public void setEmaill(String emaill) {
			this.emaill = emaill;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserPwd() {
			return userPwd;
		}

		public void setUserPwd(String userPwd) {
			this.userPwd = userPwd;
		}

	}

	/**
	 * 检查企业邀请号是否重复,isExist= "true":无重复，isExist= "false":有重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkOrgId")
	@ResponseBody
	public ResultMessage checkOrgId(HttpServletRequest request) throws Exception {
		ResultMessage res = null;
		boolean isExist = false;// 返回true是已经存在，false是不存在
		String invititedCode = RequestUtil.getString(request, "invititedCode");
		if (StringUtil.isEmpty(invititedCode)) {
			isExist = false;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("sysOrgInfoId", invititedCode);
		List<SysOrgInfo> sysOrgInfos = sysOrgInfoService.getAll(queryFilter);
		if (sysOrgInfos != null) {
			if (sysOrgInfos.size() > 0) {
				isExist = true;
			} else {
				isExist = false;
			}
		} else {
			isExist = false;
		}
		if (isExist) {
			res = new ResultMessage(ResultMessage.Success, JSONArray.fromObject(sysOrgInfos).toString());
		} else {
			res = new ResultMessage(ResultMessage.Fail, "你输入的邀请码不存在");
		}
		return res;
	}

	private boolean checkOrgId(HttpServletRequest request, String orgId) throws Exception {
		ResultMessage res = null;
		boolean isExist = false;// 返回true是已经存在，false是不存在

		if (StringUtil.isEmpty(orgId)) {
			isExist = false;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("sysOrgInfoId", orgId);
		List<SysOrgInfo> sysOrgInfos = sysOrgInfoService.getAll(queryFilter);
		if (sysOrgInfos != null) {
			if (sysOrgInfos.size() > 0) {
				isExist = true;
			} else {
				isExist = false;
			}
		} else {
			isExist = false;
		}
		return isExist;
	}

	private boolean checkSystemId(HttpServletRequest request, String systemId) throws Exception {
		ResultMessage res = null;
		boolean isExist = false;// 返回true是已经存在，false是不存在

		if (StringUtil.isEmpty(systemId)) {
			isExist = false;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("systemId", systemId);
		List<SubSystem> subSystems = subSystemService.getAll(queryFilter);
		if (subSystems != null) {
			if (subSystems.size() > 0) {
				isExist = true;
			} else {
				isExist = false;
			}
		} else {
			isExist = false;
		}
		return isExist;
	}

	// 查询系统的企业openId
	private boolean checkTopenId(HttpServletRequest request, String openId) throws Exception {
		ResultMessage res = null;
		boolean notExist = true;// 返回true是已经存在，false是不存在

		if (StringUtil.isEmpty(openId)) {
			notExist = true;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("openId", openId);
		List<TenantInfo> tenantInfos = tenantInfoService.getAll(queryFilter);
		if (tenantInfos != null) {
			if (tenantInfos.size() > 0) {
				notExist = false;
			} else {
				notExist = true;
			}
		} else {
			notExist = true;
		}
		return notExist;
	}

	// 查询系统的企业openId
	private boolean checkUopenId(HttpServletRequest request, String openId) throws Exception {
		ResultMessage res = null;
		boolean notExist = true;// 返回true是已经存在，false是不存在

		if (StringUtil.isEmpty(openId)) {
			notExist = true;
		}
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("openId", openId);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if (sysUsers != null) {
			if (sysUsers.size() > 0) {
				notExist = false;
			} else {
				notExist = true;
			}
		} else {
			notExist = true;
		}
		return notExist;
	}

	/**
	 * 校验手机验证码的正误
	 * 
	 * @param request
	 * @param response
	 * @param mobile
	 * @param verifycode
	 * @return
	 */
	private Map map3 = new HashMap();

	@RequestMapping("isChenk")
	public @ResponseBody String isChenk(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String mobile, @RequestParam String verifycode) {
		String flag = "fail";
		if (!SmsCache.verify(mobile, verifycode)) {
			flag = "fail";
		} else {
			flag = "ok";
			SmsCache.remove(mobile);
		}
		return flag;
	}

	private Map map4 = new HashMap();

	@RequestMapping("isChenkString")
	public @ResponseBody String isChenkString(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String mobile, @RequestParam String verifycode) {
		String pageIsChenkCount = RequestUtil.getString(request, "pageIsChenkCount");
		String flag = "fail";
		String mobileCodeValue = RequestUtil.getString(request, "mobileCodeValue");
		String mobileNo = RequestUtil.getString(request, "mobileNo");
		HttpSession session = request.getSession();
		String mobileRandom = RequestUtil.getString(request, "mobileRandom");

		if (StringUtil.isEmpty(mobileRandom)) {
			return "fail";
		}
		String oldValue = (String) session.getAttribute("mobileRandom");
		System.out.println(oldValue + "---" + mobileRandom);
		if (!mobileRandom.equals(oldValue)) {
			// session.removeAttribute("mobileRandom");
			return "fail";
		}
		System.out.println(oldValue + "---" + mobileRandom + "---" + premobileRandom + "---" + pageIsChenkCount
				+ "------" + verifycode);
		// 限制三次请求
		int i = (int) (map4.get(mobileRandom) == null ? 0 : map4.get(mobileRandom));
		if (i > 2) {
			// map4 =new HashMap();
			return "请关闭浏览器，刷新再试！";
		}
		i = i + 1;
		System.out.println(i);
		map4.put(mobileRandom, i);
		if (!SmsCache.verify(mobile, verifycode)) {
			flag = "fail";
		} else {
			flag = "ok";
		}
		return flag;
	}

	/**
	 * 校验手机验证码的正误
	 * 
	 * @param request
	 * @param response
	 * @param mobile
	 * @param verifycode
	 * @return
	 */
	@RequestMapping("isCheckCode")
	public @ResponseBody String isCheckCode(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String mobile, @RequestParam String verifycode) {
		HttpSession session = request.getSession();
		String flag;
		// 页面请求
		String mobileRandom = RequestUtil.getString(request, "mobileRandom");
		if (StringUtil.isEmpty(mobileRandom)) {
			flag = "fail";
		}

		String oldValue = (String) session.getAttribute("mobileRandom");
		if (!mobileRandom.toString().equals(oldValue.toString())) {

			flag = "fail";
		}

		// 限制三次请求
		int i = (int) (mobileCodeMap.get(mobileRandom) == null ? 0 : mobileCodeMap.get(mobileRandom));
		if (i > 2) {
			// mobileCodeMap =new HashMap();
			return "请关闭浏览器，刷新再试！";
		}
		i = i + 1;
		mobileCodeMap.put(mobileRandom, i);
		if (!SmsCache.verify(mobile, verifycode)) {
			flag = "fail";
		} else {
			flag = "ok";
		}
		return flag;
	}

	/***
	 * 跳转到注册第二步页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("reg_cloud_2")
	public ModelAndView reg_cloud_2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl = RequestUtil.getUrl(request);
		response.setDateHeader("Expires", 0);   
		response.addHeader( "Cache-Control", "no-cache" );//浏览器和缓存服务器都不应该缓存页面信息
		response.addHeader( "Cache-Control", "no-store" );
		String mobile = RequestUtil.getString(request, "mobile");
		String invititedCode = RequestUtil.getString(request, "invititedCode");
		String systemId = RequestUtil.getString(request, "systemId");
		String serviceUrl = RequestUtil.getString(request, "serviceUrl");
		String AccountRandom = UUID.randomUUID().toString();
		String identification = UUID.randomUUID().toString();
		String televerifycode = UUID.randomUUID().toString();
		iCache.add("AccountRandom", AccountRandom);
		iCache.add("identification", identification);
		iCache.add("televerifycode", televerifycode);

		ISysUser Iuser = ContextUtil.getCurrentUser();
		if (mobile == null || ("").equals(mobile)) {
			ModelAndView mv = new ModelAndView("/reg_cloud_step1.jsp");
			return mv;
		}
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.getFilters().put("state", "正常");
		List<ChargeSet> chargeSetList = chargeSetService.getAll(queryFilter);
		ModelAndView mv = new ModelAndView("/reg_cloud_step2.jsp");
		mv.addObject("chargeSetList", chargeSetList).addObject("mobile", mobile).addObject("isPer", false)
				.addObject("invititedCode", invititedCode).addObject("systemId", systemId)
				.addObject("returnUrl", returnUrl).addObject("user", Iuser).addObject("serviceUrl", serviceUrl)
				.addObject("AccountRandom", AccountRandom).addObject("identification", identification);
		return mv;
	}

	/**
	 * 检查用户名是否重复,isExist= "true":无重复，isExist= "false":有重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception//为true的时候验证通过  为false验证不通过
	 */
	@RequestMapping("checkAccountRepeat")
	@ResponseBody
	public boolean checkAccountRepeat(HttpServletRequest request) throws Exception {
		String account = RequestUtil.getString(request, "account");
		boolean isExist = false;// true的时候验证通过  为false验证不通过
		String AccountRandom = RequestUtil.getString(request, "AccountRandom");
		System.out.println("---" + AccountRandom);
		
		String oldValue = (String) iCache.getByKey("AccountRandom");
		System.out.println("---验证码比较----" + oldValue + "---" + AccountRandom);
		if (!AccountRandom.equals(oldValue)) {
			// session.removeAttribute("AccountRandom");
			iCache.delByKey("AccountRandom");
			return false;
		}

		if (StringUtil.isEmpty(account)) {
			return false;
		} else {
			// QueryFilter queryFilter = new QueryFilter(request, true);
			// queryFilter.getFilters().clear();
			// queryFilter.getFilters().put("account", account);
			// List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);

		 /*	ISysUser sysUser =null;
			try {
				sysUser = sysUserService.getByAccount(account);//查询有多个异常处理
			} catch (Exception e) {
				sysUser =new SysUser();//设置让用户不为空走下面的判断
			}
			if (sysUser != null) {*/

			String sql = "select count(*) from sys_user where account = ?";
			int queryForInt = jdbcTemplate.queryForInt(sql, account);
			if (queryForInt > 0) {
				isExist = false;
			} else {
				isExist = true;
			}
			return isExist;
		}
	}

	private Map map5 = new HashMap();

	@RequestMapping("checkAccountRepeatString")
	@ResponseBody
	public String checkAccountRepeatString(HttpServletRequest request) throws Exception {
		String account = RequestUtil.getString(request, "account");
		String isExist = "false";// 返回true是已经存在，false是不存在
		String AccountRandom = RequestUtil.getString(request, "AccountRandom");
		System.out.println("---" + AccountRandom);
		String oldValue = (String) iCache.getByKey("AccountRandom");
		System.out.println("---验证码比较----" + oldValue + "---" + AccountRandom);
		if (!AccountRandom.equals(oldValue)) {
			// session.removeAttribute("AccountRandom");
			iCache.delByKey("AccountRandom");
			return "false";
		}
		// 限制三次请求
		int i = (int) (map5.get(AccountRandom) == null ? 0 : map5.get(AccountRandom));
		if (i > 2) {
			// map5 =new HashMap();
			// return "请关闭浏览器，刷新再试！";
			return "请关闭浏览器，刷新再试！";
		}
		i = i + 1;
		map5.put(AccountRandom, i);

		if (StringUtil.isEmpty(account)) {
			return "false";
		} else {
			// QueryFilter queryFilter = new QueryFilter(request, true);
			// queryFilter.getFilters().clear();
			// queryFilter.getFilters().put("account", account);
			// List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
			String sql = "select count(*) from sys_user where account = ?";
			int queryForInt = jdbcTemplate.queryForInt(sql, account);
			//ISysUser sysUser = sysUserService.getByAccount(account);
			if (queryForInt > 0) {
				isExist = "false";
			} else {
				isExist = "true";
			}
			return isExist;
		}
	}

	/**
	 * 检查企业名是否重复,isExist= "true":无重复，isExist= "false":有重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkOrgNameRepeat")
	@ResponseBody
	public boolean checkOrgNameRepeat(HttpServletRequest request) throws Exception {

		boolean isExist = false;// 返回false是已经存在，true是不存在
		String name = RequestUtil.getString(request, "name");
		if (StringUtil.isEmpty(name)) {
			return false;
		}
		// QueryFilter queryFilter = new QueryFilter(request, true);
		// queryFilter.getFilters().clear();
		// queryFilter.getFilters().put("name", name);
		// List<SysOrgInfo> sysOrgInfos = sysOrgInfoService.getAll(queryFilter);
		String sql = "select count(*) from  SYS_ORG_INFO where name = ?";
		int queryForInt = jdbcTemplate.queryForInt(sql, name);
		if (queryForInt > 0) {
			isExist = false;
		} else {
			isExist = true;
		}

		return isExist;
	}

	/**
	 * 检查企业名是否存在,isExist= "true":无重复，isExist= "false":有重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkOrgNameExist")
	@ResponseBody
	public Map<String, Object> checkOrgNameExist(HttpServletRequest request) throws Exception {
		Map<String, Object> map2 = new HashMap<String, Object>();
		boolean isExist = false;// 返回true是已经存在，false是不存在
		String name = RequestUtil.getString(request, "name");
		Long currentId = 0L;
		Long sysOrgInfoId = null;
		ISysUser user = ContextUtil.getCurrentUser();
		if (ContextUtil.getCurrentUser() != null) {

			sysOrgInfoId = (user.getOrgSn() != null && user.getOrgSn() != 0L) ? user.getOrgSn() : (user.getOrgId());
		}
		if (StringUtil.isEmpty(name)) {
			isExist = true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("sysOrgInfoId", sysOrgInfoId);
		List<TenantInfo> sysOrgInfos = tenantInfoService.checkOrgNameExist(map);
		if (sysOrgInfos != null) {
			if (sysOrgInfos.size() > 0) {
				isExist = true;
				currentId = sysOrgInfos.get(0).getSysOrgInfoId();
			} else {
				isExist = false;
			}
		} else {
			isExist = false;
		}
		map2.put("isExist", isExist);
		map2.put("currentId", currentId);
		return map2;
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 *             企业注册保存
	 */
	@RequestMapping("saveUserAndOrgInfo")
	@ResponseBody
	public ResultMessage saveUserAndOrgInfo(TenantInfo info, HttpServletRequest request) throws Exception {
		ResultMessage res = null;
		Map<String, Object> map = new HashMap<String, Object>();
		String resultMsg = "成功";
		SysUser sysUser = new SysUser();
		String relMes = RequestUtil.getString(request, "relMes");
		try {
			info.setRegistertime(new Date());
			map = tenantInfoService.registerSysOrgNoUser(info, relMes);
			info = (TenantInfo) map.get("sysOrgInfo");
			sysUser = (SysUser) map.get("sysUser");
			TenantInfo tnantInfo = (TenantInfo) map.get("sysOrgInfo");
			SysUser sysUser1 = (SysUser) map.get("sysUser");
			JSONObject json = new JSONObject();
			final JSONObject userJson = json.fromObject(sysUser1);
			final JSONObject tenantJson = json.fromObject(tnantInfo);
			new JMSRunableThread("tenant_add", tenantJson.toString());
			new JMSRunableThread("user_add", userJson.toString());
			com.casic.util.MessageUtil.sendSysTopic("saveinfo", info.toString());
			com.casic.util.MessageUtil.sendSysTopic("savesysUser", sysUser.toString());
			int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "tenant");
			for (InterfaceUrlBean urlBean : urls) {
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
				if (fromSysId == null || "".equals(fromSysId)) {
					fromSysId = "100";
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
				// 用户名
				String fullName = sysUser.getFullname();
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
				params.put("userId", sysUser.getUserId());
				String result = "";
				Date start = new Date();
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
				String startTime = formatter.format(start);
				UrlBean urlMonitor = new UrlBean();
				long id = UniqueIdUtil.genId();
				urlMonitor.setId(id);
				urlMonitor.setUrl(urlBean.getSubIndexUrl() + urlBean.getUrl());

				urlMonitor.setStartTime(startTime);
				urlMonitor.setSubSysId(urlBean.getSubId() + "");
				urlMonitor.setRemark1(sysUser.getOrgId() + "");
				urlMonitor.setRemark2(sysUser.getUserId() + "");
				int isSuccess = 1;
				System.out.println("调用子系统接口结果start地址=" + urlBean.getSubIndexUrl() + urlBean.getUrl());
				try {
					logger.warn("接口调用statrt+++" + urlMonitor.getUrl());
					System.out.println("调用子系统接口结果start地址=" + params);
					result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() + urlBean.getUrl(), params);
					logger.warn("接口调用end+++" + result);
					System.out.println("调用子系统接口结果end=" + result);
					// result =
					// HttpClientUtil.JsonPostInvoke("http://182.50.1.8:15380/platform/system/api/tenantAdd.ht",
					// params);
				} catch (Exception e) {
					isSuccess = 0;
					System.out.println("调用子系统接口结果end=" + e.getMessage());
					logger.warn("接口调用异常+++" + e.getMessage());
					result = e.getMessage();
					e.printStackTrace();
				} finally {
					System.out.println("调用子系统接口结果=" + result);
					logger.warn("接口调用异常+++" + result);
					Date end = new Date();
					String endTime = formatter.format(end);
					urlMonitor.setEndTime(endTime);
					urlMonitor.setIsSuccess(isSuccess);
					urlMonitor.setRemark3(result);
					urlMonitorService.add(urlMonitor);
				}
			}

	        	// 企业添加成功后同步到子系统
	  			ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
		} catch (Exception e) {
			e.printStackTrace();
			res = new ResultMessage(ResultMessage.Fail, resultMsg);
		}
		res = new ResultMessage(ResultMessage.Success, resultMsg);
		return res;
	}

	@RequestMapping("tenantDataToSystem")
	@ResponseBody
	public ResultMessage tenantDataToSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage res = null;
		String tenantIds = request.getParameter("tenantIds");
		String systemIds = request.getParameter("systemIds");
		String[] tenants = tenantIds.split(",");
		String[] systems = systemIds.split(",");
		List<InterfaceUrlBean> urls = null;
		for (String systemId : systems) {
			urls = subSystemInterfaceUrlService.getAllUrlWithTypeAndClassify(1, "tenant", Long.parseLong(systemId));
		}
		if (urls != null && urls.size() > 0) {
			for (InterfaceUrlBean urlBean : urls) {
				for (String tenantId : tenants) {
					TenantInfo tenantInfo = tenantInfoService.getById(Long.parseLong(tenantId));
					Long sysOrgInfoId = tenantInfo.getSysOrgInfoId();
					SysUser sysUser = sysUserService.getUserIsCharge(sysOrgInfoId);
					if (sysUser != null) {
						res = saveUserAndOrgInfo(tenantInfo, sysUser, urlBean);
					}
				}
			}
		}

		return res;
	}

	private ResultMessage saveUserAndOrgInfo(TenantInfo info, SysUser sysUser, InterfaceUrlBean urlBean) {
		ResultMessage res = null;
		String resultMsg = "成功";
		int i = 1;
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
		String uopenId = sysUser.getOpenId();
		// 会员名称
		String uname = sysUser.getAccount();
		// 登录密码
		String pwd = sysUser.getPassword();
		// 手机号
		String mobile = sysUser.getMobile();
		// 邮箱
		String email = sysUser.getEmail();
		// 用户名
		String fullName = sysUser.getFullname();
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
		String result = "";
		Date start = new Date();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
		String startTime = formatter.format(start);
		UrlBean urlMonitor = new UrlBean();
		long id = UniqueIdUtil.genId();
		urlMonitor.setId(id);
		urlMonitor.setUrl(urlBean.getSubIndexUrl() + urlBean.getUrl());
		urlMonitor.setStartTime(startTime);
		urlMonitor.setSubSysId(urlBean.getSubId() + "");
		int isSuccess = 1;
		try {
			// result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() +
			// urlBean.getUrl(), params);
			// result =
			// HttpClientUtil.JsonPostInvoke("http://182.50.1.8:15380/platform/system/api/tenantAdd.ht",
			// params);
		} catch (Exception e) {
			isSuccess = 0;
			resultMsg = "失败";
			e.printStackTrace();
		} finally {
			System.out.println("调用子系统接口结果=" + result);
			Date end = new Date();
			String endTime = formatter.format(end);
			urlMonitor.setEndTime(endTime);
			urlMonitor.setIsSuccess(isSuccess);
			urlMonitorService.add(urlMonitor);
		}

		// 企业添加成功后同步到子系统
		ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
		res = new ResultMessage(ResultMessage.Success, resultMsg);
		return res;
	}

	/***
	 * 跳转到注册第三步页面,注册成功
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("regSuccess")
	public ModelAndView regSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 各系统注册时传过来的url
		String url = request.getParameter("url");
		String returnUrl = RequestUtil.getUrl(request);
		String systemId = RequestUtil.getString(request, "systemId");
		String account = RequestUtil.getString(request, "account");
		String serviceUrl = RequestUtil.getString(request, "serviceUrl");
		// System.out.println("----------------");
		// System.out.println(systemId);
		// System.out.println(account);
		// System.out.println("+++++++++++++++++");
		Map<String, String> params = new HashMap<String, String>();
		params.put("${account}", account);
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("account", account);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		Long userid = sysUsers.get(0).getUserId();
		String password = sysUsers.get(0).getPassword();
		String templateCode = "ywmb016";
		String systemid = "10000059380000";
		String userId = String.valueOf(userid);
		SendMessage.sendMessage(params, templateCode, systemid, userId);
		ModelAndView mv = new ModelAndView("/reg_cloud_step3.jsp");
		mv.addObject("url", url).addObject("systemId", systemId).addObject("account", account)
				.addObject("serviceUrl", serviceUrl).addObject("password", password).addObject("returnUrl", returnUrl)
				.addObject("isPer", false);
		return mv;
	}

	/***
	 * 跳转到协议页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("xieyi")
	public ModelAndView xieyi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 各系统注册时传过来的url
		String url = request.getParameter("url");

		ModelAndView mv = new ModelAndView("/xieyi.jsp");
		mv.addObject("url", url);
		return mv;
	}

	
	/***
	 * 跳转到登录协议页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("loginXieYi")
	public ModelAndView loginXieYi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 各系统注册时传过来的url
		String url = request.getParameter("url");

		ModelAndView mv = new ModelAndView("/loginXieYi.jsp");
		mv.addObject("url", url);
		return mv;
	}
	
	/***
	 * 跳转到中金支付协议页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("zjpaymentxieyi")
	public ModelAndView zjpaymentxieyi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 各系统注册时传过来的url
		// String url = request.getParameter("url");

		ModelAndView mv = new ModelAndView("/zjpaymentxieyi.jsp");
		// mv.addObject("url",url);
		return mv;
	}

	/**
	 * 统一用户登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("doLogin")
	public void doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnNewUrl = "";
		/*
		 * StringBuffer casserverLoginUrl = new
		 * StringBuffer(PropertiesUtils.getProperty("cas.url"));
		 * casserverLoginUrl.append("/login"); String returnUrl =
		 * RequestUtil.getString(request, "returnUrl");
		 * 
		 * StringBuffer serviceBark = new
		 * StringBuffer(PropertiesUtils.getProperty("platform.url"));
		 * serviceBark.append("/j_spring_cas_security_check"); String
		 * contextPath = request.getContextPath(); String queryString =
		 * request.getQueryString(); String requestURI =
		 * request.getRequestURI(); requestURI =
		 * requestURI.substring(requestURI.indexOf(contextPath) +
		 * contextPath.length(), requestURI.length());
		 */
		String system = RequestUtil.getString(request, "systemId");
		String userName = RequestUtil.getString(request, "userName");
		String password = RequestUtil.getString(request, "password");
		String casUrl = PropertiesUtils.getProperty("cas.url");
		String serviceUrl = casUrl + "/registerLogin?userName=" + userName + "&password=" + password
				+ "&isEncrypt=yes&service=";
		if (system != null && !("").equals(system)) {
			// 根据子系统Id,去取子系统的主页
			Long systemId = Long.parseLong(system);
			SubSystem sub = subSystemService.getById(systemId);
			// casserverLoginUrl.append("?systemId="+systemId);
			if (sub != null) {
				/// StringBuffer defaultUrl=new
				/// StringBuffer(sub.getDefaultUrl());
				// defaultUrl.append("/j_spring_cas_security_check?spring-security-redirect="+sub.getDefaultUrl());
				// returnUrl=defaultUrl.toString();
				// returnUrl=
				/// CommonUtils.constructRedirectUrl(casserverLoginUrl.toString(),
				/// "service", returnUrl, false, false);
				// new
				returnNewUrl = serviceUrl + sub.getDefaultUrl().toString();
			} else {
				// serviceUrl="?"+AbstractAuthenticationTargetUrlRequestHandler.DEFAULT_TARGET_PARAMETER;
				// serviceUrl+="=/index.ht";//返回社区
				// returnUrl=serviceBark.toString()+serviceUrl;
				// returnUrl=
				// CommonUtils.constructRedirectUrl(casserverLoginUrl.toString(),
				// "service", returnUrl, false, false);
				returnNewUrl = serviceUrl + "http://www.casicloud.com";
			}
		} else {

			/*
			 * if(!requestURI.equals("/")&&requestURI.length()>0&&!requestURI.
			 * equals("/user/doLogin.ht")){
			 * serviceUrl="?"+AbstractAuthenticationTargetUrlRequestHandler.
			 * DEFAULT_TARGET_PARAMETER; serviceUrl+="="+requestURI;
			 * if(org.apache.commons.lang.StringUtils.isNotBlank(queryString)){
			 * serviceUrl+="?"+queryString; }
			 * 
			 * }else{
			 * serviceUrl="?"+AbstractAuthenticationTargetUrlRequestHandler.
			 * DEFAULT_TARGET_PARAMETER; serviceUrl+="=/index.ht";//返回社区 }
			 * returnUrl=serviceBark.toString()+serviceUrl; returnUrl=
			 * CommonUtils.constructRedirectUrl(casserverLoginUrl.toString(),
			 * "service", returnUrl, false, false);
			 */
			returnNewUrl = serviceUrl + "http://www.casicloud.com";
		}

		// response.sendRedirect(returnUrl);
		response.sendRedirect(returnNewUrl);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("importCompany") // 使用线程进行存储
	// =========
	public void importCompany(@RequestParam("excel") MultipartFile excel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("进入importCompany方法");
		// logger.setLevel(Level.DEBUG);
		long start = System.currentTimeMillis();
		System.out.println("开始时间" + start);
		String resultMsg = "结果信息：";
		StringBuffer msg = new StringBuffer("");
		boolean result = true;
		int cpuNum = Runtime.getRuntime().availableProcessors();// 电脑核数
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
		/// ExecutorService fixedThreadPool = Executors.newCachedThreadPool();
		// ExecutorService fixedThreadPool =
		/// Executors.newSingleThreadExecutor();
		// 增加记录当前的操作人
		final Long openratorId = ContextUtil.getCurrentUserId();

		if (StringUtil.isEmpty(openratorId)) {
			writeResultMessage(response.getWriter(), "导入失败,当前用户session已失效,请重新登录导入!", ResultMessage.Fail);
			return;
		} else {
			try {
				InputStream is = excel.getInputStream();
				Workbook workbook = POIExcelUtil.getWorkBookByStream(is);

				// SysOrgInfoRes res=null;//创建导入提示信息的对象
				List<Future<SysOrgInfoRes>> infoRes = new ArrayList<Future<SysOrgInfoRes>>();
				List<SysOrgInfoRes> infores = new ArrayList<SysOrgInfoRes>();
				// List<TenantInfo> list = new ArrayList<TenantInfo>(); //
				// 存放未能导入的主数据
				Integer total = 0;// 总记录数
				Integer successTotal = 0;// 成功记录数
				Integer falseTotal = 0;// false记录数
				Integer checkfalse = 0;// false校验失败
				// // 循环工作表Sheet
				final Sheet sheet = POIExcelUtil.getSheetByNum(workbook, 1);
				final Map<String, String> mapTenantInfo = new HashMap();
				// 循环列
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					final int num1 = rowNum;
					final Row hasrow = sheet.getRow(num1);
					if (hasrow == null) {
						break;
					}
					if (sheet.getLastRowNum() >= 500) {
						break;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("----hasrow----{   }:" + hasrow);
					}

					final HttpServletRequest request1 = request;
					Future<SysOrgInfoRes> a = fixedThreadPool.submit(new Callable() {

						@Override
						public SysOrgInfoRes call() throws Exception {

							TenantInfo tenantInfo = null;
							SysOrgInfoRes res = null;// 创建导入提示信息的对象

							tenantInfo = new TenantInfo();
							res = new SysOrgInfoRes();// 只是用来记录提示的错误信息
							// 取行数据(编号不解析)
							// 企业名称解析
							Cell cell0 = hasrow.getCell(0);
							if (cell0 == null) {
								return null;
							}
							res.setNumber(num1);
							res.setResult(true);
							if (logger.isDebugEnabled()) {
								logger.debug(
										"--企业名称解析-SysOrgInfoRes-res----{   }:" + JSONObject.fromObject(res).toString());
							}
							// 对企业名称进行查重
							String name = POIExcelUtil.getCellData(cell0);
							// 验证企业名称
							validateName(request1, tenantInfo, res, name, mapTenantInfo);
							// 对企业的手机号进行查重与校验
							DecimalFormat df = new DecimalFormat("#");
							Cell cell1 = hasrow.getCell(1);
							Integer mobilePhoneType = -1;
							String mobile = null;
							if (cell1 != null && !("").equals(cell1))
								mobilePhoneType = cell1.getCellType();
							if (HSSFCell.CELL_TYPE_STRING == mobilePhoneType) {
								mobile = cell1.getStringCellValue().trim();
							} else if (HSSFCell.CELL_TYPE_NUMERIC == mobilePhoneType) {
								mobile = df.format(cell1.getNumericCellValue());
							}
							validateMobile(request1, tenantInfo, res, mobile, mapTenantInfo);
							// 解析邮箱
							Cell cell2 = hasrow.getCell(2);

							String email = POIExcelUtil.getCellData(cell2);
							validateEmail(tenantInfo, res, email, mapTenantInfo);
							// 对企业行业进行解析
							Cell cell3 = hasrow.getCell(3);
							String industry = POIExcelUtil.getCellData(cell3);
							validateIndustry(tenantInfo, res, industry);
							// 对企业的省进行解析
							Cell cell4 = hasrow.getCell(4);
							String province = POIExcelUtil.getCellData(cell4);
							validateProvince(tenantInfo, res, province);
							// 对企业的市进行解析
							Cell cell5 = hasrow.getCell(5);
							String city = POIExcelUtil.getCellData(cell5);
							validateCity(tenantInfo, res, city);
							// 对企业联系人进行解析
							Cell cell6 = hasrow.getCell(6);
							String connecter = POIExcelUtil.getCellData(cell6);
							validateConnecter(tenantInfo, res, connecter);
							// 对企业的地址进行解析
							Cell cell7 = hasrow.getCell(7);
							String address = POIExcelUtil.getCellData(cell7);
							validateAddress(tenantInfo, res, address);
							// 对企业的传真进行校验
							Cell cell8 = hasrow.getCell(8);
							Integer faxType = -1;
							String fax = null;
							if (cell8 != null && !("").equals(cell8))
								faxType = cell8.getCellType();
							if (HSSFCell.CELL_TYPE_STRING == faxType) {
								fax = cell8.getStringCellValue().trim();
							} else if (HSSFCell.CELL_TYPE_NUMERIC == faxType) {
								fax = df.format(cell8.getNumericCellValue());
							}
							validateFax(tenantInfo, res, fax);
							// 对邀请码进行解析
							Cell cell9 = hasrow.getCell(9);
							Integer invitedCodeType = -1;
							String invitedCode = null;
							if (cell9 != null && !("").equals(cell9))
								invitedCodeType = cell9.getCellType();
							if (HSSFCell.CELL_TYPE_STRING == invitedCodeType) {
								invitedCode = cell9.getStringCellValue().trim();
							} else if (HSSFCell.CELL_TYPE_NUMERIC == invitedCodeType) {
								invitedCode = df.format(cell9.getNumericCellValue());
							}
							validateInvitedCode(request1, tenantInfo, res, invitedCode);
							// 对系统来源进行导入
							Cell cell10 = hasrow.getCell(10);
							Integer systemIdType = -1;
							String systemId = null;
							if (cell10 != null && !("").equals(cell10))
								systemIdType = cell10.getCellType();
							if (HSSFCell.CELL_TYPE_STRING == systemIdType) {
								systemId = cell10.getStringCellValue().trim();
							} else if (HSSFCell.CELL_TYPE_NUMERIC == systemIdType) {
								systemId = df.format(cell10.getNumericCellValue());
							}
							validateSystemId(request1, tenantInfo, res, systemId);
							Cell cell11 = hasrow.getCell(11);
							Integer topenIdType = -1;
							String topenId = null;
							if (cell11 != null && !("").equals(cell11))
								topenIdType = cell11.getCellType();
							if (HSSFCell.CELL_TYPE_STRING == topenIdType) {
								topenId = cell11.getStringCellValue().trim();
							} else if (HSSFCell.CELL_TYPE_NUMERIC == topenIdType) {
								topenId = df.format(cell11.getNumericCellValue());
							}
							validateTopenId(request1, tenantInfo, res, topenId);
							Cell cell12 = hasrow.getCell(12);
							Integer uopenIdType = -1;
							String uopenId = null;
							if (cell12 != null && !("").equals(cell12))
								uopenIdType = cell12.getCellType();
							if (HSSFCell.CELL_TYPE_STRING == uopenIdType) {
								uopenId = cell12.getStringCellValue().trim();
							} else if (HSSFCell.CELL_TYPE_NUMERIC == uopenIdType) {
								uopenId = df.format(cell12.getNumericCellValue());
							}
							validateUopenId(request1, tenantInfo, res, uopenId);
							if (StringUtil.isEmpty(name) && StringUtil.isEmpty(mobile) && StringUtil.isEmpty(industry)
									&& StringUtil.isEmpty(province)) {
								return res;
							}
							if (res.isResult()) {
								tenantInfo.setMark("2");
								tenantInfo.setOperator(openratorId);// 添加操作人
								if (logger.isDebugEnabled()) {
									logger.debug("--添加操作人--tenantInfo----{   }:"
											+ JSONObject.fromObject(tenantInfo).toString());
								}
								synchronized ("b") {
									try {
										saveOrgAndUserInfoByThread(tenantInfo);
										/*
										 * Map map =
										 * saveOrgAndUserInfoByThread(tenantInfo
										 * ); SysOrgInfo OrgInfo=
										 * (com.hotent.platform.model.system.
										 * SysOrgInfo) map.get("false");
										 * if(OrgInfo!=null){ res.setNum(1); }
										 */
										if (logger.isDebugEnabled()) {
											logger.debug("--saveOrgAndUserInfoByThread--tenantInfo--{ "
													+ Thread.currentThread().getName() + "  }:"
													+ JSONObject.fromObject(tenantInfo).toString());
										}
									} catch (Exception e) {
										if (logger.isDebugEnabled()) {
											logger.debug("--res.setResult(false);--{   }:"
													+ JSONObject.fromObject(res).toString());
										}
										// res.setResult(false);
										System.out.println("插入sql失败");
										e.printStackTrace();

									}
								}

							}
							return res;
						}
					});
					if (a != null) {
						infoRes.add(a);
						if (logger.isDebugEnabled()) {
							logger.debug("--infoRes--infoRes.size() --{ " + infoRes.size() + "放进去一个" + " }:"
									+ JSON.toJSONString(infoRes).toString());
						}
					}
					total++;
				}
				while (true) {
					int threadCount = ((ThreadPoolExecutor) fixedThreadPool).getActiveCount();
					if (threadCount == 0) {
						break;
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("--infoRes.size() --{  }:" + JSON.toJSONString(infoRes).toString());
				}
				/// 一下注释代码不要删除
				/*
				 * for (Future<SysOrgInfoRes> future : infoRes) { SysOrgInfoRes
				 * res = future.get(); if(logger.isDebugEnabled()){
				 * logger.debug("--SysOrgInfoRes--res.isResult() --{ "
				 * +res.isResult()+" }:"+res.isResult()+res ); } if
				 * (res.isResult() == true) { successTotal++;
				 * if(logger.isDebugEnabled()){ logger.debug(
				 * "--successTotal --{   }:"+successTotal ); } } else {
				 * infores.add(res); if(logger.isDebugEnabled()){ logger.debug(
				 * "--infores --{   }:"+JSON.toJSONString(infores).toString());
				 * } } }
				 */
				// isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
				// isDone方法表示任务是否已经完成，若任务完成，则返回true；
				// get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
				// get(long timeout, TimeUnit
				// unit)用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。
				// 也就是说Future提供了三种功能：
				// 1）判断任务是否完成；
				// 2）能够中断任务；
				// 3）能够获取任务执行结果。
				// 线程处理失败结果
				ArrayList<SysOrgInfoRes> FailresultresList = new ArrayList<SysOrgInfoRes>();
				for (Future<SysOrgInfoRes> future : infoRes) {
					SysOrgInfoRes res = future.get();
					boolean cancelled = future.isCancelled();//
					System.out.println("cancelled:" + cancelled);
					boolean done = future.isDone();
					if (!done) {
						// logger.setLevel(Level.DEBUG);
						if (logger.isInfoEnabled()) {
							try {
								Object object = future.get();
								SysOrgInfoRes r = (SysOrgInfoRes) object;
								FailresultresList.add(r);
								logger.info(done ? "已完成" : "未完成" + "线程返回future结果SysOrgInfoRes： " + object);// 从结果的打印顺序可以看到，即使未完成，也会阻塞等待
							} catch (InterruptedException | ExecutionException e) {
								e.printStackTrace();
							}

						}
					} else if (done && res.isResult() == true) {// 返回成功且校验成功
						successTotal++;
						if (logger.isDebugEnabled()) {
							logger.debug(
									"-" + (done ? "已完成" : "未完成") + "-successTotal这不使用有重叠错误 --{ 第几个  }:" + successTotal);// 这不使用有重叠错误
						}
					} else if ("1".equals(String.valueOf(res.getNum()))) {// insert
																			// 失败的
						falseTotal++;
						if (logger.isDebugEnabled()) {
							logger.debug(
									"-" + (done ? "已完成" : "未完成") + "-insert 企业失败的falseTotal --{ 第几个  }:" + falseTotal);
						}
					} else {
						infores.add(res);// 校验失败
						checkfalse++;
						if (logger.isDebugEnabled()) {
							logger.debug("--infores --{   }:" + JSON.toJSONString(infores).toString());
						}
					}

				}

				// 重新赋值
				successTotal = total - falseTotal - checkfalse;
				System.out.println("total: " + total + ",falseTotal: " + falseTotal + ",checkfalse: " + checkfalse);
				// 准备回调信息如下
				msg.append("共" + total + "条记录,成功" + successTotal + "条<br>");
				if (logger.isDebugEnabled()) {
					logger.debug("--msg--{  }:" + "共" + total + "条记录,成功" + successTotal + "条<br>");
				}
				for (SysOrgInfoRes info : infores) {
					if (!info.isResult()) {
						if (logger.isDebugEnabled()) {
							logger.debug("--准备回调信息如下info --{   }:" + JSON.toJSONString(info).toString());
						}
						// 导入出现失败info.getNumber
						msg.append("第" + info.getNumber() + "行:");
						// 公司名称验证
						if (("0000").equals(info.getNameCode())) {
							msg.append("公司名称只能是由数字、英文字母、汉字或者下划线组成的字符串,");
						}
						if (("0002").equals(info.getNameCode())) {
							msg.append(EnumUtils.NAMECHEAPT.getName() + ",");
						}
						if (("0004").equals(info.getNameCode())) {
							msg.append(EnumUtils.NameEmpty.getName() + ",");
						}
						// 手机号校验
						if (("0000").equals(info.getMobileCode())) {
							msg.append("手机验证不合法,");
						}
						if (("0003").equals(info.getMobileCode())) {
							msg.append(EnumUtils.MOBILECHEAPT.getName() + ",");
						}
						if (("0006").equals(info.getMobileCode())) {
							msg.append("手机号含有" + EnumUtils.SPECIAL.getName() + ",");
						}
						if (("0008").equals(info.getMobileCode())) {
							msg.append("手机号" + EnumUtils.NotEmpty.getName() + ",");
						}
						if (("0006").equals(info.getFaxCode())) {
							msg.append("传真中含有" + EnumUtils.SPECIAL.getName() + ",");
						}
						if (("0000").equals(info.getEmailCode())) {
							msg.append("邮箱" + EnumUtils.NOVALID.getName() + ",");
						}
						if (("0001").equals(info.getEmailCode())) {
							msg.append(EnumUtils.OVERlENGTH.getName() + ",");
						}
						if (("00011").equals(info.getEmailCode())) {
							msg.append(EnumUtils.OVERlENGTH.getName() + ",");
						}
						// 行业验证
						if (("0000").equals(info.getIndustryCode())) {
							msg.append("行业只能是由数字、英文字母、汉字或者下划线组成的字符串,");
						}
						// 行业验证
						if (("0008").equals(info.getIndustryCode())) {
							msg.append("行业" + EnumUtils.NotEmpty.getName() + ",");
						}
						// 省份验证
						if (("0000").equals(info.getProvinceCode())) {
							msg.append("省份中含有特殊字符,");
						}
						// 省份验证
						if (("0008").equals(info.getProvinceCode())) {
							msg.append("省份" + EnumUtils.NotEmpty.getName() + ",");
						}
						// 城市验证
						if (("0000").equals(info.getCityCode())) {
							msg.append("城市中含有特殊字符,");
						}
						// 城市验证
						if (("0008").equals(info.getCityCode())) {
							msg.append("城市" + EnumUtils.NotEmpty.getName() + ",");
						}
						// 企业联系人验证
						if (("0000").equals(info.getConnectorCode())) {
							msg.append("联系人只能是由数字、英文字母、汉字或者下划线组成的字符串,");
						}
						// 企业联系人验证
						if (("0008").equals(info.getConnectorCode())) {
							msg.append("联系人" + EnumUtils.NotEmpty.getName() + ",");
						}
						// 地址验证
						if (("0000").equals(info.getAddressCode())) {
							msg.append("地址只能是由数字、英文字母、汉字或者下划线组成的字符串,");
						}
						// 地址验证
						if (("0008").equals(info.getAddressCode())) {
							msg.append("地址" + EnumUtils.NotEmpty.getName() + ",");
						}

						// 传真验证
						if (("0000").equals(info.getFaxCode())) {
							msg.append("传真验证不合法,");
						}
						// 邀请码验证
						if (("0006").equals(info.getInviteCode())) {
							msg.append("邀请码含有非数字,");
						}
						// 邀请码验证
						if (("0007").equals(info.getInviteCode())) {
							msg.append("邀请码不存在,");
						}
						// 系统来源验证
						if (("0006").equals(info.getSystemIdCode())) {
							msg.append("系统来源中含有非数字,");
						}
						// 系统来源验证
						if (("0009").equals(info.getSystemIdCode())) {
							msg.append("系统来源不存在,");
						}
						// 企业openId验证
						if (("0000").equals(info.getTopenIdCode())) {
							msg.append("企业openId中含有不合法字符,");
						}
						// 企业openId验证
						if (("00010").equals(info.getTopenIdCode())) {
							msg.append("企业openId已存在,");
						}
						// 用户openId验证
						if (("0006").equals(info.getUopenIdCode())) {
							msg.append("用户openId中含有不合法字符,");
						}
						// 用户openId验证
						if (("00010").equals(info.getUopenIdCode())) {
							msg.append("用户openId已存在,");
						}

						if (msg.toString().endsWith(","))
							msg.deleteCharAt(msg.lastIndexOf(","));

						msg.append("<br>");
						if (logger.isDebugEnabled()) {
							logger.debug("--msg-- " + "准备回调信息如下" + "--{  }:" + msg);
						}
					}
				}
				if (sheet.getLastRowNum() >= 500) {
					msg = msg.replace(0, msg.length(), "导入数据超过500");
					result = false;
				}
				if (successTotal < total) {
					result = false;
				}
				if (!result) {
					resultMsg = resultMsg + msg.toString();

					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				} else {
					resultMsg = "全部成功！" + msg.toString();
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
					long end = System.currentTimeMillis();
					System.out.println("结束时间" + end);
					System.out.println("导入用时 : " + (end - start) / 1000f + " 秒 ");
				}

			} catch (Exception e) {
				msg.append("系统出现异常,请检查数据模板数据格式是否正确");
				writeResultMessage(response.getWriter(), msg.toString(), ResultMessage.Fail);
				e.printStackTrace();
			}
		}

	}
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping("importCompany") // 使用线程进行存储 // ========= public void
	 * importCompany(@RequestParam("excel") MultipartFile excel,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { System.out.println("进入importCompany方法");
	 * 
	 * long start = System.currentTimeMillis(); System.out.println("开始时间" +
	 * start); String resultMsg = "结果信息："; StringBuffer msg = new
	 * StringBuffer(""); boolean result = true; int cpuNum =
	 * Runtime.getRuntime().availableProcessors();//电脑核数 ExecutorService
	 * fixedThreadPool = Executors.newFixedThreadPool(4); ///ExecutorService
	 * fixedThreadPool = Executors.newCachedThreadPool(); //ExecutorService
	 * fixedThreadPool = Executors.newSingleThreadExecutor(); // 增加记录当前的操作人
	 * final Long openratorId = ContextUtil.getCurrentUserId(); if
	 * (StringUtil.isEmpty(openratorId)) {
	 * writeResultMessage(response.getWriter(), "导入失败,当前用户session已失效,请重新登录导入!",
	 * ResultMessage.Fail); return; } else { try { InputStream is =
	 * excel.getInputStream(); Workbook workbook =
	 * POIExcelUtil.getWorkBookByStream(is);
	 * 
	 * // SysOrgInfoRes res=null;//创建导入提示信息的对象 List<Future<SysOrgInfoRes>>
	 * infoRes = new ArrayList<Future<SysOrgInfoRes>>(); List<SysOrgInfoRes>
	 * infores = new ArrayList<SysOrgInfoRes>(); // List<TenantInfo> list = new
	 * ArrayList<TenantInfo>(); // // 存放未能导入的主数据 Integer total = 0;// 总记录数
	 * Integer successTotal = 0;// 成功记录数 Integer falseTotal = 0;// false记录数
	 * Integer checkfalse = 0;// false校验失败 // // 循环工作表Sheet final Sheet sheet =
	 * POIExcelUtil.getSheetByNum(workbook, 1); // 循环列 for (int rowNum = 1;
	 * rowNum <= sheet.getLastRowNum(); rowNum++) { final int num1 = rowNum;
	 * final Row hasrow = sheet.getRow(num1); if (hasrow == null) { break; }
	 * 
	 * final HttpServletRequest request1 = request; Future<SysOrgInfoRes> a =
	 * fixedThreadPool.submit(new Callable() {
	 * 
	 * @Override public SysOrgInfoRes call() throws Exception {
	 * 
	 * TenantInfo tenantInfo = null; SysOrgInfoRes res = null;// 创建导入提示信息的对象
	 * 
	 * tenantInfo = new TenantInfo(); res = new SysOrgInfoRes();// 只是用来记录提示的错误信息
	 * // 取行数据(编号不解析) // 企业名称解析 Cell cell0 = hasrow.getCell(0); if (cell0 ==
	 * null) { return null; } res.setNumber(num1); res.setResult(true);
	 * 
	 * // 对企业名称进行查重 String name = POIExcelUtil.getCellData(cell0); // 验证企业名称
	 * validateName(request1, tenantInfo, res, name); // 对企业的手机号进行查重与校验
	 * DecimalFormat df = new DecimalFormat("#"); Cell cell1 =
	 * hasrow.getCell(1); Integer mobilePhoneType = -1; String mobile = null; if
	 * (cell1 != null && !("").equals(cell1)) mobilePhoneType =
	 * cell1.getCellType(); if (HSSFCell.CELL_TYPE_STRING == mobilePhoneType) {
	 * mobile = cell1.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == mobilePhoneType) { mobile =
	 * df.format(cell1.getNumericCellValue()); } validateMobile(request1,
	 * tenantInfo, res, mobile); // 解析邮箱 Cell cell2 = hasrow.getCell(2);
	 * 
	 * String email = POIExcelUtil.getCellData(cell2); validateEmail(tenantInfo,
	 * res, email); // 对企业行业进行解析 Cell cell3 = hasrow.getCell(3); String industry
	 * = POIExcelUtil.getCellData(cell3); validateIndustry(tenantInfo, res,
	 * industry); // 对企业的省进行解析 Cell cell4 = hasrow.getCell(4); String province =
	 * POIExcelUtil.getCellData(cell4); validateProvince(tenantInfo, res,
	 * province); // 对企业的市进行解析 Cell cell5 = hasrow.getCell(5); String city =
	 * POIExcelUtil.getCellData(cell5); validateCity(tenantInfo, res, city); //
	 * 对企业联系人进行解析 Cell cell6 = hasrow.getCell(6); String connecter =
	 * POIExcelUtil.getCellData(cell6); validateConnecter(tenantInfo, res,
	 * connecter); // 对企业的地址进行解析 Cell cell7 = hasrow.getCell(7); String address
	 * = POIExcelUtil.getCellData(cell7); validateAddress(tenantInfo, res,
	 * address); // 对企业的传真进行校验 Cell cell8 = hasrow.getCell(8); Integer faxType =
	 * -1; String fax = null; if (cell8 != null && !("").equals(cell8)) faxType
	 * = cell8.getCellType(); if (HSSFCell.CELL_TYPE_STRING == faxType) { fax =
	 * cell8.getStringCellValue().trim(); } else if (HSSFCell.CELL_TYPE_NUMERIC
	 * == faxType) { fax = df.format(cell8.getNumericCellValue()); }
	 * validateFax(tenantInfo, res, fax); // 对邀请码进行解析 Cell cell9 =
	 * hasrow.getCell(9); Integer invitedCodeType = -1; String invitedCode =
	 * null; if (cell9 != null && !("").equals(cell9)) invitedCodeType =
	 * cell9.getCellType(); if (HSSFCell.CELL_TYPE_STRING == invitedCodeType) {
	 * invitedCode = cell9.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == invitedCodeType) { invitedCode =
	 * df.format(cell9.getNumericCellValue()); } validateInvitedCode(request1,
	 * tenantInfo, res, invitedCode); // 对系统来源进行导入 Cell cell10 =
	 * hasrow.getCell(10); Integer systemIdType = -1; String systemId = null; if
	 * (cell10 != null && !("").equals(cell10)) systemIdType =
	 * cell10.getCellType(); if (HSSFCell.CELL_TYPE_STRING == systemIdType) {
	 * systemId = cell10.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == systemIdType) { systemId =
	 * df.format(cell10.getNumericCellValue()); } validateSystemId(request1,
	 * tenantInfo, res, systemId); Cell cell11 = hasrow.getCell(11); Integer
	 * topenIdType = -1; String topenId = null; if (cell11 != null &&
	 * !("").equals(cell11)) topenIdType = cell11.getCellType(); if
	 * (HSSFCell.CELL_TYPE_STRING == topenIdType) { topenId =
	 * cell11.getStringCellValue().trim(); } else if (HSSFCell.CELL_TYPE_NUMERIC
	 * == topenIdType) { topenId = df.format(cell11.getNumericCellValue()); }
	 * validateTopenId(request1, tenantInfo, res, topenId); Cell cell12 =
	 * hasrow.getCell(12); Integer uopenIdType = -1; String uopenId = null; if
	 * (cell12 != null && !("").equals(cell12)) uopenIdType =
	 * cell12.getCellType(); if (HSSFCell.CELL_TYPE_STRING == uopenIdType) {
	 * uopenId = cell12.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == uopenIdType) { uopenId =
	 * df.format(cell12.getNumericCellValue()); } validateUopenId(request1,
	 * tenantInfo, res, uopenId); if (StringUtil.isEmpty(name) &&
	 * StringUtil.isEmpty(mobile) && StringUtil.isEmpty(industry) &&
	 * StringUtil.isEmpty(province)) { return res; } if (res.isResult()) {
	 * tenantInfo.setMark("2"); tenantInfo.setOperator(openratorId);// 添加操作人
	 * 
	 * synchronized ("b") { try { Map map =
	 * saveOrgAndUserInfoByThread(tenantInfo); SysOrgInfo OrgInfo=
	 * (com.hotent.platform.model.system.SysOrgInfo) map.get("false");
	 * if(OrgInfo!=null){ res.setNum(1); }
	 * 
	 * } catch (Exception e) {
	 * 
	 * //res.setResult(false); System.out.println("插入sql失败");
	 * e.printStackTrace();
	 * 
	 * } }
	 * 
	 * } return res; } }); if (a != null) { infoRes.add(a); } total++; } while
	 * (true) { int threadCount = ((ThreadPoolExecutor)
	 * fixedThreadPool).getActiveCount(); if (threadCount == 0) { break; } }
	 * System.out.println("++++++++++++++++++++++++"); // 准备回调信息如下
	 * msg.append("共" + total + "条记录,成功" + successTotal + "条<br>"); for
	 * (SysOrgInfoRes info : infores) { if (!info.isResult()) { //
	 * 导入出现失败info.getNumber msg.append("第" + info.getNumber() + "行:"); // 公司名称验证
	 * if (("0000").equals(info.getNameCode())) {
	 * msg.append("公司名称只能是由数字、英文字母、汉字或者下划线组成的字符串,"); } if
	 * (("0002").equals(info.getNameCode())) {
	 * msg.append(EnumUtils.NAMECHEAPT.getName() + ","); } if
	 * (("0004").equals(info.getNameCode())) {
	 * msg.append(EnumUtils.NameEmpty.getName() + ","); } // 手机号校验 if
	 * (("0000").equals(info.getMobileCode())) { msg.append("手机验证不合法,"); } if
	 * (("0003").equals(info.getMobileCode())) {
	 * msg.append(EnumUtils.MOBILECHEAPT.getName() + ","); } if
	 * (("0006").equals(info.getMobileCode())) { msg.append("手机号含有" +
	 * EnumUtils.SPECIAL.getName() + ","); } if
	 * (("0008").equals(info.getMobileCode())) { msg.append("手机号" +
	 * EnumUtils.NotEmpty.getName() + ","); } if
	 * (("0006").equals(info.getFaxCode())) { msg.append("传真中含有" +
	 * EnumUtils.SPECIAL.getName() + ","); } if
	 * (("0000").equals(info.getEmailCode())) { msg.append("邮箱" +
	 * EnumUtils.NOVALID.getName() + ","); } if
	 * (("0001").equals(info.getEmailCode())) {
	 * msg.append(EnumUtils.OVERlENGTH.getName() + ","); } // 行业验证 if
	 * (("0000").equals(info.getIndustryCode())) {
	 * msg.append("行业只能是由数字、英文字母、汉字或者下划线组成的字符串,"); } // 行业验证 if
	 * (("0008").equals(info.getIndustryCode())) { msg.append("行业" +
	 * EnumUtils.NotEmpty.getName() + ","); } // 省份验证 if
	 * (("0000").equals(info.getProvinceCode())) { msg.append("省份中含有特殊字符,"); }
	 * // 省份验证 if (("0008").equals(info.getProvinceCode())) { msg.append("省份" +
	 * EnumUtils.NotEmpty.getName() + ","); } // 城市验证 if
	 * (("0000").equals(info.getCityCode())) { msg.append("城市中含有特殊字符,"); } //
	 * 城市验证 if (("0008").equals(info.getCityCode())) { msg.append("城市" +
	 * EnumUtils.NotEmpty.getName() + ","); } // 企业联系人验证 if
	 * (("0000").equals(info.getConnectorCode())) {
	 * msg.append("联系人只能是由数字、英文字母、汉字或者下划线组成的字符串,"); } // 企业联系人验证 if
	 * (("0008").equals(info.getConnectorCode())) { msg.append("联系人" +
	 * EnumUtils.NotEmpty.getName() + ","); } // 地址验证 if
	 * (("0000").equals(info.getAddressCode())) {
	 * msg.append("地址只能是由数字、英文字母、汉字或者下划线组成的字符串,"); } // 地址验证 if
	 * (("0008").equals(info.getAddressCode())) { msg.append("地址" +
	 * EnumUtils.NotEmpty.getName() + ","); }
	 * 
	 * // 传真验证 if (("0000").equals(info.getFaxCode())) { msg.append("传真验证不合法,");
	 * } // 邀请码验证 if (("0006").equals(info.getInviteCode())) {
	 * msg.append("邀请码含有非数字,"); } // 邀请码验证 if
	 * (("0007").equals(info.getInviteCode())) { msg.append("邀请码不存在,"); } //
	 * 系统来源验证 if (("0006").equals(info.getSystemIdCode())) {
	 * msg.append("系统来源中含有非数字,"); } // 系统来源验证 if
	 * (("0009").equals(info.getSystemIdCode())) { msg.append("系统来源不存在,"); } //
	 * 企业openId验证 if (("0000").equals(info.getTopenIdCode())) {
	 * msg.append("企业openId中含有不合法字符,"); } // 企业openId验证 if
	 * (("00010").equals(info.getTopenIdCode())) { msg.append("企业openId已存在,"); }
	 * // 用户openId验证 if (("0006").equals(info.getUopenIdCode())) {
	 * msg.append("用户openId中含有不合法字符,"); } // 用户openId验证 if
	 * (("00010").equals(info.getUopenIdCode())) { msg.append("用户openId已存在,"); }
	 * 
	 * if (msg.toString().endsWith(",")) msg.deleteCharAt(msg.lastIndexOf(","));
	 * 
	 * msg.append("<br>"); } }
	 * 
	 * if (successTotal < total) { result = false; } if (!result) { resultMsg =
	 * resultMsg + msg.toString(); writeResultMessage(response.getWriter(),
	 * resultMsg, ResultMessage.Fail); } else { resultMsg =
	 * "全部成功！"+msg.toString(); writeResultMessage(response.getWriter(),
	 * resultMsg, ResultMessage.Success); long end = System.currentTimeMillis();
	 * System.out.println("结束时间" + end); System.out.println("导入用时 : " + (end -
	 * start) / 1000f + " 秒 "); }
	 * 
	 * } catch (Exception e) { msg.append("系统出现异常,请检查数据模板数据格式是否正确");
	 * writeResultMessage(response.getWriter(), msg.toString(),
	 * ResultMessage.Fail); e.printStackTrace(); } }
	 * 
	 * }
	 */
	/*
	 * @RequestMapping("importCompany") public void
	 * importCompany(@RequestParam("excel") MultipartFile
	 * excel,HttpServletRequest request, HttpServletResponse response)throws
	 * Exception { String resultMsg = "结果信息："; StringBuffer msg = new
	 * StringBuffer(""); boolean result=true; //增加记录当前的操作人 Long
	 * openratorId=ContextUtil.getCurrentUserId();
	 * if(StringUtil.isEmpty(openratorId)){
	 * writeResultMessage(response.getWriter(),"导入失败,当前用户session已失效,请重新登录导入!",
	 * ResultMessage.Fail); return; }else{ try { InputStream is =
	 * excel.getInputStream(); Workbook workbook =
	 * POIExcelUtil.getWorkBookByStream(is); TenantInfo tenantInfo=null;
	 * SysOrgInfoRes res=null;//创建导入提示信息的对象 List<SysOrgInfoRes> infoRes=new
	 * ArrayList<SysOrgInfoRes>(); List<TenantInfo> list = new
	 * ArrayList<TenantInfo>(); // 存放未能导入的主数据 Integer total=0;//总记录数 Integer
	 * successTotal=0;//成功记录数 // // 循环工作表Sheet Sheet sheet =
	 * POIExcelUtil.getSheetByNum(workbook, 1); //循环列 for (int rowNum = 1;
	 * rowNum <= sheet.getLastRowNum(); rowNum++) { Row
	 * hasrow=sheet.getRow(rowNum); if(hasrow==null){ continue; } tenantInfo=new
	 * TenantInfo(); res=new SysOrgInfoRes();//只是用来记录提示的错误信息 //取行数据(编号不解析)
	 * //企业名称解析 Cell cell0=hasrow.getCell(0); if (cell0 == null) { continue; }
	 * res.setNumber(rowNum); res.setResult(true); //对企业名称进行查重 String
	 * name=POIExcelUtil.getCellData(cell0); //验证企业名称 validateName(request,
	 * tenantInfo, res, name); //对企业的手机号进行查重与校验 DecimalFormat df = new
	 * DecimalFormat("#"); Cell cell1=hasrow.getCell(1); Integer
	 * mobilePhoneType=-1; String mobile=null;
	 * if(cell1!=null&&!("").equals(cell1)) mobilePhoneType=
	 * cell1.getCellType(); if (HSSFCell.CELL_TYPE_STRING == mobilePhoneType) {
	 * mobile=cell1.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == mobilePhoneType) {
	 * mobile=df.format(cell1.getNumericCellValue()); } validateMobile(request,
	 * tenantInfo, res, mobile); //解析邮箱 Cell cell2=hasrow.getCell(2);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String email=POIExcelUtil.getCellData(cell2); validateEmail(tenantInfo,
	 * res, email); //对企业行业进行解析 Cell cell3=hasrow.getCell(3); String
	 * industry=POIExcelUtil.getCellData(cell3); validateIndustry(tenantInfo,
	 * res, industry); //对企业的省进行解析 Cell cell4=hasrow.getCell(4); String
	 * province=POIExcelUtil.getCellData(cell4); validateProvince(tenantInfo,
	 * res, province); //对企业的市进行解析 Cell cell5=hasrow.getCell(5); String
	 * city=POIExcelUtil.getCellData(cell5); validateCity(tenantInfo, res,
	 * city); //对企业联系人进行解析 Cell cell6=hasrow.getCell(6); String
	 * connecter=POIExcelUtil.getCellData(cell6); validateConnecter(tenantInfo,
	 * res, connecter); //对企业的地址进行解析 Cell cell7=hasrow.getCell(7); String
	 * address=POIExcelUtil.getCellData(cell7); validateAddress(tenantInfo, res,
	 * address); //对企业的传真进行校验 Cell cell8=hasrow.getCell(8); Integer faxType=-1;
	 * String fax=null; if(cell8!=null&&!("").equals(cell8)) faxType=
	 * cell8.getCellType(); if (HSSFCell.CELL_TYPE_STRING == faxType) {
	 * fax=cell8.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == faxType) {
	 * fax=df.format(cell8.getNumericCellValue()); } validateFax(tenantInfo,
	 * res, fax); //对邀请码进行解析 Cell cell9=hasrow.getCell(9); Integer
	 * invitedCodeType=-1; String invitedCode=null;
	 * if(cell9!=null&&!("").equals(cell9)) invitedCodeType=
	 * cell9.getCellType(); if (HSSFCell.CELL_TYPE_STRING == invitedCodeType) {
	 * invitedCode=cell9.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == invitedCodeType) {
	 * invitedCode=df.format(cell9.getNumericCellValue()); }
	 * validateInvitedCode(request, tenantInfo, res, invitedCode); //对系统来源进行导入
	 * Cell cell10=hasrow.getCell(10); Integer systemIdType=-1; String
	 * systemId=null; if(cell10!=null&&!("").equals(cell10)) systemIdType=
	 * cell10.getCellType(); if (HSSFCell.CELL_TYPE_STRING == systemIdType) {
	 * systemId=cell10.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == systemIdType) {
	 * systemId=df.format(cell10.getNumericCellValue()); }
	 * validateSystemId(request, tenantInfo, res, systemId); Cell
	 * cell11=hasrow.getCell(11); Integer topenIdType=-1; String topenId=null;
	 * if(cell11!=null&&!("").equals(cell11)) topenIdType= cell11.getCellType();
	 * if (HSSFCell.CELL_TYPE_STRING == topenIdType) {
	 * topenId=cell11.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == topenIdType) {
	 * topenId=df.format(cell11.getNumericCellValue()); }
	 * validateTopenId(request, tenantInfo, res, topenId); Cell
	 * cell12=hasrow.getCell(12); Integer uopenIdType=-1; String uopenId=null;
	 * if(cell12!=null&&!("").equals(cell12)) uopenIdType= cell12.getCellType();
	 * if (HSSFCell.CELL_TYPE_STRING == uopenIdType) {
	 * uopenId=cell12.getStringCellValue().trim(); } else if
	 * (HSSFCell.CELL_TYPE_NUMERIC == uopenIdType) {
	 * uopenId=df.format(cell12.getNumericCellValue()); }
	 * validateUopenId(request, tenantInfo, res, uopenId);
	 * if(StringUtil.isEmpty(name)&&StringUtil.isEmpty(mobile)&&StringUtil.
	 * isEmpty(industry)&&StringUtil.isEmpty(province)){ continue; }
	 * infoRes.add(res); if(res.isResult()){ successTotal++;
	 * tenantInfo.setMark("2"); tenantInfo.setOperator(openratorId);//添加操作人
	 * list.add(tenantInfo); } total++; } //保存企业信息 if(successTotal>0){ for
	 * (TenantInfo tenant : list) { //保存数据 saveOrgAndUserInfo(tenant); } }
	 * //准备回调信息如下 msg.append("共"+total+"条记录,成功"+successTotal+"条<br>"); for
	 * (SysOrgInfoRes info : infoRes) { if(!info.isResult()){
	 * //导入出现失败info.getNumber msg.append("第"+info.getNumber()+"行:"); //公司名称验证
	 * if(("0000").equals(info.getNameCode())){
	 * msg.append("公司名称只能是由数字、英文字母、汉字或者下划线组成的字符串,"); }
	 * if(("0002").equals(info.getNameCode())){
	 * msg.append(EnumUtils.NAMECHEAPT.getName()+","); }
	 * if(("0004").equals(info.getNameCode())){
	 * msg.append(EnumUtils.NameEmpty.getName()+","); } //手机号校验
	 * if(("0000").equals(info.getMobileCode())){ msg.append("手机验证不合法,"); }
	 * if(("0003").equals(info.getMobileCode())){
	 * msg.append(EnumUtils.MOBILECHEAPT.getName()+","); }
	 * if(("0006").equals(info.getMobileCode())){
	 * msg.append("手机号含有"+EnumUtils.SPECIAL.getName()+","); }
	 * if(("0008").equals(info.getMobileCode())){
	 * msg.append("手机号"+EnumUtils.NotEmpty.getName()+","); }
	 * if(("0006").equals(info.getFaxCode())){
	 * msg.append("传真中含有"+EnumUtils.SPECIAL.getName()+","); }
	 * if(("0000").equals(info.getEmailCode())){
	 * msg.append("邮箱"+EnumUtils.NOVALID.getName()+","); }
	 * if(("0001").equals(info.getEmailCode())){
	 * msg.append(EnumUtils.OVERlENGTH.getName()+","); } //行业验证
	 * if(("0000").equals(info.getIndustryCode())){
	 * msg.append("行业只能是由数字、英文字母、汉字或者下划线组成的字符串,"); } //行业验证
	 * if(("0008").equals(info.getIndustryCode())){
	 * msg.append("行业"+EnumUtils.NotEmpty.getName()+","); } //省份验证
	 * if(("0000").equals(info.getProvinceCode())){ msg.append("省份中含有特殊字符,"); }
	 * //省份验证 if(("0008").equals(info.getProvinceCode())){
	 * msg.append("省份"+EnumUtils.NotEmpty.getName()+","); } //城市验证
	 * if(("0000").equals(info.getCityCode())){ msg.append("城市中含有特殊字符,"); }
	 * //城市验证 if(("0008").equals(info.getCityCode())){
	 * msg.append("城市"+EnumUtils.NotEmpty.getName()+","); } //企业联系人验证
	 * if(("0000").equals(info.getConnectorCode())){
	 * msg.append("联系人只能是由数字、英文字母、汉字或者下划线组成的字符串,"); } //企业联系人验证
	 * if(("0008").equals(info.getConnectorCode())){
	 * msg.append("联系人"+EnumUtils.NotEmpty.getName()+","); } //地址验证
	 * if(("0000").equals(info.getAddressCode())){
	 * msg.append("地址只能是由数字、英文字母、汉字或者下划线组成的字符串,"); } //地址验证
	 * if(("0008").equals(info.getAddressCode())){
	 * msg.append("地址"+EnumUtils.NotEmpty.getName()+","); }
	 * 
	 * //传真验证 if(("0000").equals(info.getFaxCode())){ msg.append("传真验证不合法,"); }
	 * //邀请码验证 if(("0006").equals(info.getInviteCode())){
	 * msg.append("邀请码含有非数字,"); } //邀请码验证
	 * if(("0007").equals(info.getInviteCode())){ msg.append("邀请码不存在,"); }
	 * //系统来源验证 if(("0006").equals(info.getSystemIdCode())){
	 * msg.append("系统来源中含有非数字,"); } //系统来源验证
	 * if(("0009").equals(info.getSystemIdCode())){ msg.append("系统来源不存在,"); }
	 * //企业openId验证 if(("0000").equals(info.getTopenIdCode())){
	 * msg.append("企业openId中含有不合法字符,"); } //企业openId验证
	 * if(("00010").equals(info.getTopenIdCode())){ msg.append("企业openId已存在,");
	 * } //用户openId验证 if(("0006").equals(info.getUopenIdCode())){
	 * msg.append("用户openId中含有不合法字符,"); } //用户openId验证
	 * if(("00010").equals(info.getUopenIdCode())){ msg.append("用户openId已存在,");
	 * }
	 * 
	 * if(msg.toString().endsWith(",")) msg.deleteCharAt(msg.lastIndexOf(","));
	 * 
	 * msg.append("<br>"); } }
	 * 
	 * if(successTotal<total){ result=false; } if(!result){ resultMsg =
	 * resultMsg + msg.toString();
	 * 
	 * writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Fail);
	 * }else{ resultMsg = "导入成功！"; writeResultMessage(response.getWriter(),
	 * resultMsg,ResultMessage.Success); }
	 * 
	 * } catch (Exception e) { msg.append("系统异常");
	 * writeResultMessage(response.getWriter(),
	 * msg.toString(),ResultMessage.Fail); e.printStackTrace(); } }
	 * 
	 * }
	 */
	// =============

	private void validateUopenId(HttpServletRequest request, TenantInfo tenantInfo, SysOrgInfoRes res, String uopenId)
			throws Exception {
		if (!StringUtil.isEmpty(uopenId)) {
			if (RegexValidateUtil.isNumberOrEn(uopenId)) {
				boolean flag2 = checkUopenId(request, uopenId);
				if (flag2) {
					tenantInfo.setUopenId(uopenId);
				} else {
					res.setResult(false);
					res.setUopenIdCode((EnumUtils.EXIST).getCode());
				}
			} else {
				res.setResult(false);
				res.setUopenIdCode((EnumUtils.NOVALID).getCode());
			}
		}
	}

	private void validateTopenId(HttpServletRequest request, TenantInfo tenantInfo, SysOrgInfoRes res, String topenId)
			throws Exception {
		if (!StringUtil.isEmpty(topenId)) {
			if (RegexValidateUtil.isNumberOrEn(topenId)) {
				boolean flag2 = checkTopenId(request, topenId);
				if (flag2) {
					tenantInfo.setOpenId(topenId);
				} else {
					res.setResult(false);
					res.setTopenIdCode((EnumUtils.EXIST).getCode());
				}
			} else {
				res.setResult(false);
				res.setTopenIdCode((EnumUtils.NOVALID).getCode());
			}
		}
	}

	private void validateSystemId(HttpServletRequest request, TenantInfo tenantInfo, SysOrgInfoRes res, String systemId)
			throws Exception {
		if (!StringUtil.isEmpty(systemId)) {
			// 邀请码非空
			if (RegexValidateUtil.isNumeric(systemId)) {
				boolean flag2 = checkSystemId(request, systemId);
				if (flag2) {
					tenantInfo.setSystemId(systemId);
				} else {
					res.setResult(false);
					res.setSystemIdCode((EnumUtils.NOTEXIST).getCode());
				}
			} else {
				res.setResult(false);
				res.setSystemIdCode((EnumUtils.SPECIAL).getCode());
			}
		}
	}

	private void validateInvitedCode(HttpServletRequest request, TenantInfo tenantInfo, SysOrgInfoRes res,
			String invitedCode) throws Exception {
		if (!StringUtil.isEmpty(invitedCode)) {
			// 邀请码非空
			if (RegexValidateUtil.isNumeric(invitedCode)) {
				boolean flag2 = checkOrgId(request, invitedCode);
				if (flag2) {
					tenantInfo.setInvititedCode(invitedCode);
				} else {
					res.setResult(false);
					res.setInviteCode((EnumUtils.INVICODE).getCode());
				}
			} else {
				res.setResult(false);
				res.setInviteCode((EnumUtils.SPECIAL).getCode());
			}
		}
	}

	private void validateFax(TenantInfo tenantInfo, SysOrgInfoRes res, String fax) {
		if (!StringUtil.isEmpty(fax)) {
			if (RegexValidateUtil.isNumeric(fax)) {
				tenantInfo.setFax(fax);
			} else {
				res.setResult(false);
				res.setFaxCode((EnumUtils.SPECIAL).getCode());
			}
		}
	}

	private void validateAddress(TenantInfo tenantInfo, SysOrgInfoRes res, String address) {
		if (!(StringUtil.isEmpty(address))) {
			// 校验地址
			Matcher matcher = isIllegalAddress(address);
			if (!matcher.matches()) {
				res.setResult(false);
				res.setAddressCode((EnumUtils.NOVALID).getCode());
			}

		} else {
			res.setResult(false);
			res.setAddressCode((EnumUtils.NotEmpty).getCode());
		}
		if (StringUtil.isEmpty(res.getAddressCode()) || !("0000").equals(res.getAddressCode())) {
			tenantInfo.setAddress(address);
		}
	}

	private void validateConnecter(TenantInfo tenantInfo, SysOrgInfoRes res, String connecter) {
		if (!StringUtil.isEmpty(connecter)) {
			Matcher matcher = isIllegal(connecter);
			if (!matcher.matches()) {
				res.setResult(false);
				res.setConnectorCode((EnumUtils.NOVALID).getCode());
			}

		} else {
			res.setResult(false);
			res.setConnectorCode((EnumUtils.NotEmpty).getCode());
		}
		if (StringUtil.isEmpty(connecter) || !("0000").equals(res.getConnectorCode())) {
			tenantInfo.setConnecter(connecter);
		}
	}

	private void validateCity(TenantInfo tenantInfo, SysOrgInfoRes res, String city) {
		if (!StringUtil.isEmpty(city)) {

			if (!RegexValidateUtil.checkChinese(city)) {
				res.setResult(false);
				res.setCityCode((EnumUtils.NOVALID).getCode());
			}

		} else {
			res.setResult(false);
			res.setCityCode((EnumUtils.NotEmpty).getCode());
		}
		if (StringUtil.isEmpty(res.getCityCode()) || !("0000").equals(res.getCityCode())) {
			tenantInfo.setCity(city);
		}
	}

	private void validateProvince(TenantInfo tenantInfo, SysOrgInfoRes res, String province) {
		if (!StringUtil.isEmpty(province)) {
			if (!RegexValidateUtil.checkChinese(province)) {
				res.setResult(false);
				res.setProvinceCode((EnumUtils.NOVALID).getCode());
			}
		} else {
			res.setResult(false);
			res.setProvinceCode((EnumUtils.NotEmpty).getCode());
		}
		if (StringUtil.isEmpty(res.getProvinceCode()) || !("0000").equals(res.getProvinceCode())) {
			tenantInfo.setProvince(province);
		}
	}

	private void validateIndustry(TenantInfo tenantInfo, SysOrgInfoRes res, String industry) {
		if (!StringUtil.isEmpty(industry)) {
			// 校验行业
			Matcher matcher = isIllegalIndustry(industry);
			if (!matcher.matches()) {
				res.setResult(false);
				res.setIndustryCode((EnumUtils.NOVALID).getCode());
			}
		} else {
			res.setResult(false);
			res.setIndustryCode((EnumUtils.NotEmpty).getCode());
		}
		if (StringUtil.isEmpty(res.getIndustryCode()) || !("0000").equals(res.getIndustryCode())) {
			tenantInfo.setIndustry(industry);
		}
	}

	private void validateEmail(TenantInfo tenantInfo, SysOrgInfoRes res, String email, Map mapTenantInfo) {
		if (!StringUtil.isEmpty(email)) {
			String a1 = (String) mapTenantInfo.get(email);
			if (a1 == null) {
				mapTenantInfo.put(email, email);
			} else {
				res.setResult(false);
				res.setEmailCode((EnumUtils.OVERlENGTH1).getCode());
				return;
			}
			if ((email.length() >= 5) && (email.length() <= 30)) {
				if (RegexValidateUtil.checkEmail(email)) {
					res.setEmailCode((EnumUtils.SUCCESS).getCode());
				} else {
					res.setResult(false);
					res.setEmailCode((EnumUtils.NOVALID).getCode());
				}
			} else {
				res.setResult(false);
				res.setEmailCode((EnumUtils.OVERlENGTH).getCode());
			}
		}

		if (StringUtil.isEmpty(email)
				|| (!("0000").equals(res.getEmailCode()) && !("0001").equals(res.getEmailCode()))) {
			tenantInfo.setEmail(email);
		}
	}

	private void validateMobile(HttpServletRequest request, TenantInfo tenantInfo, SysOrgInfoRes res, String mobile,
			Map mapTenantInfo) {
		if (!StringUtil.isEmpty(mobile)) {
			String a1 = (String) mapTenantInfo.get(mobile);
			if (a1 == null) {
				mapTenantInfo.put(mobile, mobile);
			} else {
				res.setResult(false);
				res.setMobileCode((EnumUtils.MOBILECHEAPT).getCode());
				return;
			}
			if (RegexValidateUtil.isNumeric(mobile)) {

				if (RegexValidateUtil.checkMobileNumber(mobile)) {
					boolean flag2 = checkMobileRepeat(mobile, request);
					if (flag2) {
						res.setMobileCode((EnumUtils.SUCCESS).getCode());
					} else {
						res.setResult(false);
						res.setMobileCode((EnumUtils.MOBILECHEAPT).getCode());
					}
				} else {
					res.setResult(false);
					res.setMobileCode((EnumUtils.SPECIAL).getCode());
				}

			} else {
				res.setResult(false);
				res.setMobileCode((EnumUtils.SPECIAL).getCode());
			}
		} else {
			res.setResult(false);
			res.setMobileCode((EnumUtils.NotEmpty).getCode());
		}

		if (StringUtil.isEmpty(res.getMobileCode())
				|| (!("0000").equals(res.getMobileCode()) && !("0003").equals(res.getMobileCode()))) {
			tenantInfo.setTel(mobile);
		}
	}

	private synchronized void validateName(HttpServletRequest request, TenantInfo tenantInfo, SysOrgInfoRes res,
			String name, Map mapTenantInfo) {

		if (!StringUtil.isEmpty(name)) {
			String a1 = (String) mapTenantInfo.get(name);
			if (a1 == null) {
				mapTenantInfo.put(name, name);
			} else {
				res.setResult(false);
				res.setNameCode((EnumUtils.NAMECHEAPT).getCode());
				return;
			}
			// 校验公司名称
			Matcher matcher = isIllegalName(name);
			if (!matcher.matches()) {
				res.setResult(false);
				res.setNameCode((EnumUtils.NOVALID).getCode());
			} else {
				boolean flag = checkNameRepeat(name, request);
				if (flag) {
					// 说明不存在
					tenantInfo.setName(name);
				} else {
					res.setResult(false);
					res.setNameCode((EnumUtils.NAMECHEAPT).getCode());
				}
			}
		} else {
			res.setResult(false);
			res.setNameCode((EnumUtils.NameEmpty).getCode());
		}
	}

	private Matcher isIllegalName(String name) {
		Pattern pattern = Pattern.compile("[\\（\\）|\u4e00-\u9fa5\\w|\\(\\)]+");
		Matcher matcher = pattern.matcher(name);
		return matcher;
	}

	private Matcher isIllegal(String name) {
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5\\w]+");
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

	/**
	 * 保存用户和企业信息
	 * 
	 * @param sysOrgInfo
	 */
	private void saveOrgAndUserInfo(TenantInfo info) {
		Map<String, Object> map = new HashMap<String, Object>();
		String resultMsg = "成功";
		SysUser sysUser = new SysUser();
		try {
			map = tenantInfoService.registerSysOrg(info, "123456", info.getUopenId());
			info = (TenantInfo) map.get("sysOrgInfo");
			sysUser = (SysUser) map.get("sysUser");
			int i = 1;
			List<InterfaceUrlBean> urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "tenant");
			for (InterfaceUrlBean urlBean : urls) {
				Map<String, Object> params = new HashMap<String, Object>();
				// 子系统标识
				long systemId = urlBean.getSubId();
				String fromSysId = info.getSystemId();
				if (fromSysId == null || "".equals(fromSysId)) {
					fromSysId = "100";
				}
				// 企业标识（统一开发标识）(暂时没有)
				String topenId = info.getOpenId();
				// 企业账号
				long tsn = info.getSysOrgInfoId();
				// 企业名称
				String tname = info.getName();
				// 会员标识（统一开发标识）
				String uopenId = sysUser.getOpenId();
				// 会员名称
				String uname = sysUser.getAccount();
				// 用户姓名
				String fullName = sysUser.getFullname();
				// 登录密码
				String pwd = sysUser.getPassword();
				// 手机号
				String mobile = sysUser.getMobile();
				// 邮箱
				String email = sysUser.getEmail();
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
				Date start = new Date();
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
				String startTime = formatter.format(start);
				UrlBean urlMonitor = new UrlBean();
				long id = UniqueIdUtil.genId();
				urlMonitor.setId(id);
				urlMonitor.setUrl(urlBean.getSubIndexUrl() + urlBean.getUrl());
				urlMonitor.setStartTime(startTime);
				urlMonitor.setSubSysId(urlBean.getSubId() + "");
				int isSuccess = 1;
				try {
					result = HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() + urlBean.getUrl(), params);
				} catch (Exception e) {
					isSuccess = 0;
					e.printStackTrace();
				} finally {
					System.out.println("调用子系统接口结果=" + result);
					Date end = new Date();
					String endTime = formatter.format(end);
					urlMonitor.setEndTime(endTime);
					urlMonitor.setIsSuccess(isSuccess);

					urlMonitor.setRemark1(sysUser.getOrgId() + "");
					urlMonitor.setRemark2(sysUser.getUserId() + "");
					urlMonitor.setRemark3(result);

					urlMonitor.setRemark1(sysUser.getOrgId() + "");
					urlMonitor.setRemark2(sysUser.getUserId() + "");
					urlMonitor.setRemark3(result);

					urlMonitorService.add(urlMonitor);
				}

			}

			// 企业添加成功后同步到子系统
			ucSysAuditService.addLog("添加企业", "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存用户和企业信息 线程专用
	 * 
	 * @param sysOrgInfo
	 * @throws Exception
	 */
	private void saveOrgAndUserInfoByThread(TenantInfo info) throws Exception {
		/* Map map = saveOrgAndUserInfoThreads(info); */

		try {
			saveOrgAndUserInfoThreads(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		/*
		 * SysOrgInfo sysOrgInfo = (SysOrgInfo) map.get("sysOrgInfo"); return
		 * map;
		 */
		/*
		 * ReentrantLock lock = new ReentrantLock(); boolean tryLock =
		 * lock.tryLock();
		 * System.out.println("tryLock:"+tryLock+Thread.currentThread().getName(
		 * )); if(tryLock){ try { saveOrgAndUserInfoThreads(info);
		 * Thread.sleep(2000); } catch (Exception e) { e.printStackTrace();
		 * System.out.println("数据添加异常"); }finally { lock.unlock(); }
		 * 
		 * 
		 * }
		 */
	}

	/**
	 * @param info
	 * @throws Exception
	 */
	private void saveOrgAndUserInfoThreads(TenantInfo info) {
		Map<String, Object> map = new HashMap<String, Object>();
		String resultMsg = "成功";
		SysUser sysUser = new SysUser();
		try {
			map = tenantInfoService.registerSysOrg(info, "123456", info.getUopenId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		info = (TenantInfo) map.get("sysOrgInfo");
		sysUser = (SysUser) map.get("sysUser");
		int i = 1;
		List<InterfaceUrlBean> urls = null;
		try {
			urls = subSystemInterfaceUrlService.getAllUrlByTypeAndClassify(i, "tenant");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (InterfaceUrlBean urlBean : urls) {
			Map<String, Object> params = new HashMap<String, Object>();
			// 子系统标识
			long systemId = urlBean.getSubId();
			String fromSysId = info.getSystemId();
			if (fromSysId == null || "".equals(fromSysId)) {
				fromSysId = "100";
			}
			// 企业标识（统一开发标识）(暂时没有)
			String topenId = info.getOpenId();
			// 企业账号
			long tsn = info.getSysOrgInfoId();
			// 企业名称
			String tname = info.getName();
			// 会员标识（统一开发标识）
			String uopenId = sysUser.getOpenId();
			// 会员名称
			String uname = sysUser.getAccount();
			// 用户姓名
			String fullName = sysUser.getFullname();
			// 登录密码
			String pwd = sysUser.getPassword();
			// 手机号
			String mobile = sysUser.getMobile();
			// 邮箱
			String email = sysUser.getEmail();
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
			Date start = new Date();
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
			String startTime = formatter.format(start);
			UrlBean urlMonitor = new UrlBean();
			long id = UniqueIdUtil.genId();
			urlMonitor.setId(id);
			urlMonitor.setUrl(urlBean.getSubIndexUrl() + urlBean.getUrl());
			urlMonitor.setStartTime(startTime);
			urlMonitor.setSubSysId(urlBean.getSubId() + "");
			int isSuccess = 1;

			try {
				// ##result =
				// HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() +
				// urlBean.getUrl(), params);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = 0;
			} finally {
				Date end = new Date();
				String endTime = formatter.format(end);
				urlMonitor.setEndTime(endTime);
				urlMonitor.setIsSuccess(isSuccess);
				urlMonitorService.add(urlMonitor);
			}

			try {
				// ##result =
				// HttpClientUtil.JsonPostInvoke(urlBean.getSubIndexUrl() +
				// urlBean.getUrl(), params);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = 0;
			} finally {
				System.out.println("调用子系统接口结果=" + result);
				Date end = new Date();
				String endTime = formatter.format(end);
				urlMonitor.setId(UniqueIdUtil.genId());
				urlMonitor.setEndTime(endTime);
				urlMonitor.setIsSuccess(isSuccess);
				urlMonitorService.add(urlMonitor);

			}

		}

		/*
		 * try { // 企业添加成功后同步到子系统 ucSysAuditService.addLog("添加企业",
		 * "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		/*
		 * try { // 企业添加成功后同步到子系统 ucSysAuditService.addLog("添加企业",
		 * "TenantInfoController.saveSucess.ht", JSON.toJSONString(info), "成功");
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		// return map;
	}

	// 检查手机号是否重复
	private boolean checkMobileRepeat(String mobile, HttpServletRequest request) {
		boolean isExist = false;// 返回true是已经存在，false是不存在
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("mobile", mobile);
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if (sysUsers != null) {
			if (sysUsers.size() > 0) {
				isExist = false;
			} else {
				isExist = true;
			}
		} else {
			isExist = true;
		}

		return isExist;

	}

	private boolean checkNameRepeat(String name, HttpServletRequest request) {
		boolean isExist = false;// 返回true是已经存在，false是不存在
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("name", name);
		List<SysOrgInfo> sysOrgInfos = sysOrgInfoService.getAll(queryFilter);
		if (sysOrgInfos != null) {
			if (sysOrgInfos.size() > 0) {
				isExist = false;
			} else {
				isExist = true;
			}
		} else {
			isExist = true;
		}

		return isExist;
	}

	/**
	 * 登录前做systemId的查询判断
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkSystemId")
	@ResponseBody
	public ResultMessage checkSystemId(HttpServletRequest request) throws Exception {
		ResultMessage res = null;
		boolean isExist = false;// 返回true是已经存在，false是不存在
		String systemId = RequestUtil.getString(request, "systemId");
		if (StringUtil.isEmpty(systemId)) {
			isExist = false;
		}
		SubSystem sub = null;
		if (!StringUtil.isEmpty(systemId)) {
			sub = subSystemService.getById(Long.parseLong(systemId));
		}
		if (sub != null) {
			if (sub.getSystemId() > 0) {
				isExist = true;
			} else {
				isExist = false;
			}

		} else {
			isExist = false;
		}
		if (isExist) {
			res = new ResultMessage(ResultMessage.Success, JSONArray.fromObject(sub).toString());
		} else {
			res = new ResultMessage(ResultMessage.Fail, "您的子系统Id不存在");
		}
		return res;
	}

	// 模板下载
	@RequestMapping(value = "/downLoad")
	public void memberImportDemo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取文件的路径
		String filePath = RequestUtil.getString(request, "filePath", "");
		// 文件绝对地址
		String absolutePath = "";
		// 文件名
		String fileName = "";
		ServletContext sc = request.getSession().getServletContext();
		if (!filePath.equals("")) {
			Properties configproperties = SpringContextHolder.getBean("configproperties");
			String fileDir = configproperties.getProperty("fileDir");
			String[] str = filePath.split("/");
			fileName = str[str.length - 1];

			if (fileDir != null) {
				absolutePath = fileDir + filePath;
			} else {
				absolutePath = sc.getRealPath(filePath);

			}

		} else {
			String svrPath = RequestUtil.getString(request, "svrPath", "");

			if (svrPath != null) {
				absolutePath = sc.getRealPath("/") + svrPath;
				String[] str = svrPath.split("/");
				fileName = str[str.length - 1];
			}
		}

		// 设置下载头信息
		fileName = new String(fileName.getBytes("GB2312"), "ISO_8859_1");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + "");
		response.setHeader("Connection", "close");
		response.setCharacterEncoding("utf-8");
		InputStream in = null;
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		try {
			if (absolutePath != null) {
				in = new FileInputStream(absolutePath);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					out.write(b, 0, i);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/*
	 * 用户列表
	 */
	@RequestMapping("/ajax_list")
	@ResponseBody
	public List<ISysUser> list(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize") int pagesize) {
		QueryFilter queryFilter = new QueryFilter(request, true);

		PageBean pageBean = new PageBean(page, pagesize);
		queryFilter.getFilters().clear();
		queryFilter.setPageBean(pageBean);

		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		for (ISysUser iSysUser : sysUsers) {
			iSysUser.setTotal(new Long((long) queryFilter.getPageBean().getTotalCount()));
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select *from sys_user  limit ?,? ",
				(page - 1) * pagesize, pagesize);

		Long Total = jdbcTemplate.queryForLong("select count(*) from sys_user");
		List<ISysUser> users = new ArrayList<ISysUser>();
		for (Map<String, Object> m : list) {
			SysUser sysuser = new SysUser();
			sysuser.setUserId(Long.parseLong(m.get("userId").toString()));
			sysuser.setFullname(m.get("fullname").toString());
			sysuser.setIsLock(Short.parseShort(m.get("isLock").toString()));
			sysuser.setIsExpired(Short.parseShort(m.get("isExpired").toString()));
			sysuser.setTotal(Total);
			users.add(sysuser);

		}

		return sysUsers;

	}

	@RequestMapping("/ucAPI")
	public ModelAndView ucAPI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/user/layerPageTest.jsp");
		return mv;
	}

	/***
	 * 跳转到更改用户名的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateAccount")
	public ModelAndView updateAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysUser user = ContextUtil.getCurrentUser();

		// 各系统注册时传过来的url
		String url = request.getParameter("url");
		ModelAndView mv = new ModelAndView("user/updateAccount.jsp");
		if (user != null) {
			mv.addObject("url", url).addObject("user", user);
		} else {
			mv.addObject("url", url).addObject("user", sysUserService.getById((Long) iCache.getByKey("userId")));
		}
		return mv;
	}

	/***
	 * 跳转到更改用户姓名的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateFullName")
	public ModelAndView updateFullName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysUser user = ContextUtil.getCurrentUser();

		// 各系统注册时传过来的url
		String url = request.getParameter("url");
		ModelAndView mv = new ModelAndView("user/updateFullName.jsp");
		if (user != null) {
			mv.addObject("url", url).addObject("user", user);
		} else {
			mv.addObject("url", url).addObject("user", sysUserService.getById((Long) iCache.getByKey("userId")));
		}
		return mv;
	}

	@RequestMapping("rejectUser")
	@Action(description = "rejectUser")
	@ResponseBody
	public ResultMessage rejectUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			// 驳回逻辑
			Long userId = RequestUtil.getLong(request, "userId");
			// 获取驳回用户信息
			ISysUser sysUser = sysUserService.getById(userId);
			sysUser.setState("2");// 审核不通过
			sysUser.setOrgId(yun_ent_id);
			sysUser.setOrgSn(yun_ent_id);
			// 驳回操作

			Long[] aryRoleId = new Long[] { 10000000270000L };// 角色Id数组(采购主管)
			sysUserService.saveUser(sysUser, yun_ent_id, Short.valueOf("0"), aryRoleId);
			// 更新中间表信息
			String sqlUpdate = "update sys_user_extence  set state = 0  where user_id = '" + sysUser.getUserId() + "'";
			int flag = jdbcTemplate.update(sqlUpdate);
			message = new ResultMessage(ResultMessage.Success, "驳回成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "驳回失败" + ex.getMessage());
		}
		return message;
	}

	@RequestMapping("passUser")
	@Action(description = "passUser")
	@ResponseBody
	public ResultMessage passUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			// 通过逻辑
			Long userId = RequestUtil.getLong(request, "userId");
			// 获取驳回用户信息
			ISysUser sysUser = sysUserService.getById(userId);
			sysUser.setState("1");// 审核通过
			sysUserService.update(sysUser);
			message = new ResultMessage(ResultMessage.Success, "审核通过!");
		} catch (Exception ex) {
			ex.printStackTrace();
			message = new ResultMessage(ResultMessage.Fail, "审核失败" + ex.getMessage());
		}
		return message;
	}
	
	@RequestMapping("getCityIdBankId")
	@Action(description = "查询开户行详细分类")
	@ResponseBody
	public List<Dictionary> getCityIdBankId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 城市cityId , 开户行bankId	
		String cityId = request.getParameter("cityId");
		String bankId = request.getParameter("bankId");
		String optvalue = cityId+bankId;
		// 缓存一份到缓存中
		// 把行别代码作为key生成一份缓存
		List<Dictionary> dics = new ArrayList();
		if (iCache.getByKey(optvalue) != null) {
			// 表示已经有缓存
			dics = (List<Dictionary>) iCache.getByKey(optvalue);
		} else {
			Map<String, Object> cmap = jdbcTemplate.queryForMap("SELECT t.CITYNAME from BF_ZD_CITY t where t.CITYCODE = ?",cityId);
			String cityName = (String) cmap.get("CITYNAME");
			String substring = cityName.substring(0,cityName.length()-1);
			System.out.println(substring);
			List<Map<String, Object>> list = jdbcTemplate
					.queryForList("select p.FQHHO2,p.HANBDM,p.FKHMC1 from BF_ZD_PAYBANK p where p.HANBDM= ? and p.FKHMC1 LIKE '%"+substring+"%';", bankId);

			for (Map<String, Object> m : list) {
				Dictionary dic = new Dictionary();
				dic.setDicId(Long.parseLong(m.get("FQHHO2").toString()));
				dic.setItemName(m.get("FKHMC1").toString());
				dic.setItemValue(m.get("FQHHO2").toString());
				dics.add(dic);
			}
		}
		// 更新缓存信息
		iCache.add(optvalue, dics);

		return dics;
	}
	@RequestMapping("getBankId")
	@Action(description = "查询物品分类")
	@ResponseBody
	public List<Dictionary> getBankId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String optvalue = request.getParameter("value");
		// 缓存一份到缓存中
		// 把行别代码作为key生成一份缓存
		List<Dictionary> dics = new ArrayList();
		if (iCache.getByKey(optvalue) != null) {
			// 表示已经有缓存
			dics = (List<Dictionary>) iCache.getByKey(optvalue);
		} else {
			List<Map<String, Object>> list = jdbcTemplate
					.queryForList("select p.FQHHO2,p.HANBDM,p.FKHMC1 from BF_ZD_PAYBANK p where p.HANBDM=?", optvalue);

			for (Map<String, Object> m : list) {
				Dictionary dic = new Dictionary();
				dic.setDicId(Long.parseLong(m.get("FQHHO2").toString()));
				dic.setItemName(m.get("FKHMC1").toString());
				dic.setItemValue(m.get("FQHHO2").toString());
				dics.add(dic);
			}
		}
		// 更新缓存信息
		iCache.add(optvalue, dics);

		return dics;
	}
}