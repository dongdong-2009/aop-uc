package com.casic.cas.controller;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.util.PropertiesUtils;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.ICache;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;

@Controller
@RequestMapping("/cas/")
public class CasController extends BaseController{
	@Resource
	private ICache iCache;
	private static String casUrl ="";
	static {
		casUrl = PropertiesUtils.getProperty("cas.url");
	}
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	@Resource
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

	@Resource(name = "systemproperties")
	private Properties systemproperties;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private SysOrgInfoService sysOrgInfoService;

	private String rememberPrivateKey = "cloudPrivateKey";
	
	
	@RequestMapping("logout")
	@Action(description = "单点退出")
	public ModelAndView casLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String service_url = RequestUtil.getString(request, "url");
		if(service_url==null||service_url.equals("")){
			System.out.println("没有传url不知道怎么跳转");
		}

		String url = casUrl + "/logout?service=" + service_url;
		// 从session中删除组织数据。
		ContextUtil.removeCurrentOrg(request, response);
		iCache.clearAll();
		// 将用户设置到Session中
	
		// 删除cookie。
		CookieUitl.delCookie("loginAction", request, response);
		CookieUitl.delCookie("JSESSIONID", request, response);
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(url);
		System.out.println("++++++++++++++++++++++++++++++++url="+url);
		return null;
	}
	
	@RequestMapping("casLogin")
	@Action(description = "单点登录")
	public ModelAndView casLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = RequestUtil.getString(request, "url");
		if (url == null||url.equals("")) {
			String deal = request.getScheme();
			String serverName = request.getServerName();
			int port = request.getServerPort();
			String contextPath = request.getContextPath();
			String path = deal + "://" + serverName + ":" + port + contextPath
					+ "/";
			url = path;
		}
		HttpSession session = ((HttpServletRequest) request).getSession();
		Object se = session.getAttribute("_const_cas_assertion_");
		String account = "", shortAccount = "", password = "", e_id = "";

		if (se instanceof Assertion) {
			Assertion assertion = (Assertion) se;
			Map<String, Object> attrs = assertion.getPrincipal()
					.getAttributes();
			e_id = attrs.get("e_id").toString();
			account = attrs.get("account").toString();
			shortAccount = attrs.get("username").toString();
			password = attrs.get("password").toString();
			Long userId = Long.parseLong(attrs.get("userId").toString());
			// String enPassword = EncryptUtil.encryptSha256(password);
//			account = URLDecoder.decode(account, "UTF-8");
//			shortAccount = URLDecoder.decode(shortAccount, "UTF-8");
			String hanggouOrgId="2350300";
			//判断username是云网account还是航购shortAccount
			if(shortAccount.matches(".+@@.+")){
				//云网
				String strs[] = shortAccount.split("@@", 0);
				account = strs[0]+"_"+strs[1];
			}else{
				//航购
				account = hanggouOrgId+"_"+shortAccount;
			}
			String enPassword = EncryptUtil.encryptSha256(password);
			//ISysUser user = sysUserService.getByAccount(account);
			ISysUser user = sysUserService.getById(userId);
			if (user!=null) {
				// 设置到session中
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getAccount(), password);
				authRequest.setDetails(new WebAuthenticationDetails(request));
				SecurityContext securityContext = SecurityContextHolder.getContext();
				Authentication auth = authenticationManager.authenticate(authRequest);
				securityContext.setAuthentication(auth);
				request.getSession().setAttribute(WebAttributes.LAST_USERNAME,user.getAccount());
				
				sessionStrategy.onAuthentication(auth, request, response);
				// 从session中删除组织数据。
				ContextUtil.removeCurrentOrg(request, response);
				
				String sql = "select count(id) from jituan_danwei_guanxi where id = "+e_id;
				int i = jdbcTemplate.queryForInt(sql);
				boolean flag = false;
				if(i == 1){
					flag = true;
				}
				request.getSession().setAttribute("jituan", flag);

				// 将用户设置到Session中
				ContextUtil.setCurrentUserAccount(user.getAccount());
				request.getSession().setAttribute("fullname",user.getFullname());
				request.getSession().setAttribute("principal", user);
				// 删除cookie。
				CookieUitl.delCookie("loginAction", request, response);

				writeRememberMeCookie(request, response, user.getAccount(),user.getPassword());
				CookieUitl.addCookie("loginAction", "cloud", request, response);
			}
		}

		// 重定向到请求的页面
		response.sendRedirect(url);
		return null;
	}
	
	
	@RequestMapping("ajaxCasLogin")
	@Action(description="验证")
	@ResponseBody
	public void ajaxCasLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		Object se = session.getAttribute("_const_cas_assertion_");
		String userName = "";
		System.out.println("ajaxCasLogin begin");
		if (se instanceof Assertion) {
			System.out.println("ajaxCasLogin has user");
			Assertion assertion = (Assertion) se;
			Map<String, Object> attrs = assertion.getPrincipal()
					.getAttributes();
			userName = attrs.get("fullname").toString();
			System.out.println("ajaxCasLogin userName="+userName);
		}
		response.getWriter().println("success_jsonpCallback(['"+userName+"'])");
	}
	
	/**
	 * 加上用户登录的remember me 的cookie
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param enPassword
	 * @throws Exception
	 */
	private void writeRememberMeCookie(HttpServletRequest request,
			HttpServletResponse response, String username, String enPassword)
			throws Exception {
		String rememberMe = request
				.getParameter("_spring_security_remember_me");
		String autoLogin = RequestUtil.getString(request, "autoLogin", "0");

		String host = request.getServerName();
		int expire = 60 * 60 * 24 * 14; // 2 weeks
		String path = org.springframework.util.StringUtils.hasLength(request
				.getContextPath()) ? request.getContextPath() : "/";

		if (autoLogin.equals("1")) {// 记住账户名
			// 账号
			String orgSn = RequestUtil.getString(request, "orgSn");
			CookieUitl.addCookie("orgSn", orgSn, expire, request, response);

			// 用户名
			CookieUitl.addCookie("shortAccount",
					URLEncoder.encode(username, "utf-8"), expire, request,
					response);
		}

		if ("1".equals(rememberMe)) {// 自动登录
			long tokenValiditySeconds = 1209600; // 14 days
			long tokenExpiryTime = System.currentTimeMillis()
					+ (tokenValiditySeconds * 1000);
			String signatureValue = DigestUtils.md5Hex(username + ":"
					+ tokenExpiryTime + ":" + enPassword + ":"
					+ rememberPrivateKey);
			String tokenValue = username + ":" + tokenExpiryTime + ":"
					+ signatureValue;
			String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue
					.getBytes()));
			CookieUitl
					.addCookie(
							TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY,
							tokenValueBase64, expire, request, response);

			// 密码
			CookieUitl.addCookie("password", EncryptUtil.encrypt(enPassword),
					expire, request, response);
		}
	}

}
