package com.casic.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.authentication.AuthenticationManager;

import com.alibaba.druid.support.logging.Log;
import com.casic.util.PropertiesUtils;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.SysUserService;

public class LoginFilter implements Filter {
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
   
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
        
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpSession session = request.getSession();
		String path = request.getServletPath();
		if(path.contains("/api/")){
			chain.doFilter(request, response);
			return;
		}
		if (session != null && ContextUtil.getCurrentUser() == null) {
			Object se = session.getAttribute("SPRING_SECURITY_CONTEXT");
			String returnUrl = "";
			StringBuffer casserverLoginUrl = new StringBuffer(
					PropertiesUtils.getProperty("cas.url"));
			StringBuffer defaultUrl = new StringBuffer(
					PropertiesUtils.getProperty("platform.url"));
			casserverLoginUrl.append("/login");
			defaultUrl
					.append("/j_spring_cas_security_check?spring-security-redirect="
							+ path);
			returnUrl = defaultUrl.toString();
			returnUrl = CommonUtils.constructRedirectUrl(
					casserverLoginUrl.toString(), "service", returnUrl, false,
					false);
			response.sendRedirect(returnUrl);
			return;

		} 
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
