package com.casic.core.security.cas.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class LoginFilter
  extends GenericFilterBean
{
  private String filterProcessesUrl;
  private ServiceProperties serviceProperties;
  private String loginUrl;
  
  public LoginFilter()
  {
    this.filterProcessesUrl = "/login";
  }
  
  public String getFilterProcessesUrl()
  {
    return this.filterProcessesUrl;
  }
  
  public void setFilterProcessesUrl(String filterProcessesUrl)
  {
    Assert.isTrue(UrlUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl + " isn't a valid value for" + " 'filterProcessesUrl'");
    
    this.filterProcessesUrl = filterProcessesUrl;
  }
  
  public ServiceProperties getServiceProperties()
  {
    return this.serviceProperties;
  }
  
  public void setServiceProperties(ServiceProperties serviceProperties)
  {
    this.serviceProperties = serviceProperties;
  }
  
  public String getLoginUrl()
  {
    return this.loginUrl;
  }
  
  public void setLoginUrl(String loginUrl)
  {
    this.loginUrl = loginUrl;
  }
  
  public void afterPropertiesSet()
  {
    Assert.hasLength(this.loginUrl, "loginUrl must be specified");
    Assert.notNull(this.serviceProperties, "serviceProperties must be specified");
  }
  
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)res;
    if (requireLogin(request, response))
    {
      String urlEncodedService = createServiceUrl(request, response);
      String redirectUrl = createRedirectUrl(urlEncodedService);
      response.sendRedirect(redirectUrl);
      return;
    }
    chain.doFilter(request, response);
  }
  
  protected String createServiceUrl(HttpServletRequest request, HttpServletResponse response)
  {
    return CommonUtils.constructServiceUrl(null, response, this.serviceProperties.getService(), null, this.serviceProperties.getArtifactParameter(), true);
  }
  
  protected String createRedirectUrl(String serviceUrl)
  {
    return CommonUtils.constructRedirectUrl(this.loginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
  }
  
  protected boolean requiresLogin(HttpServletRequest request, HttpServletResponse response)
  {
    String uri = request.getRequestURI();
    int pathParamIndex = uri.indexOf(';');
    if (pathParamIndex > 0) {
      uri = uri.substring(0, pathParamIndex);
    }
    int queryParamIndex = uri.indexOf('?');
    if (queryParamIndex > 0) {
      uri = uri.substring(0, queryParamIndex);
    }
    if ("".equals(request.getContextPath())) {
      return uri.endsWith(this.filterProcessesUrl);
    }
    return uri.endsWith(request.getContextPath() + this.filterProcessesUrl);
  }
  protected boolean requireLogin(HttpServletRequest request, HttpServletResponse response)
  {
	  SecurityContext securityContext = SecurityContextHolder.getContext();
	  HttpSession session=request.getSession();
	    if (securityContext != null)
	    {
	      Authentication auth = securityContext.getAuthentication();
	      if(auth==null){
	    	 session.invalidate();
	    	//清除客户端所有的cookie
	    	 Cookie[] cookies=request.getCookies();
	    	 if(cookies!=null){
	    		 for (Cookie cookie : cookies) {
	    			 Cookie cooki = new Cookie(cookie.getName(), null);
	    			 cooki.setMaxAge(0);
	    			 cooki.setPath("/");
				}
	    	 }
	    	  return true;
	      }else{
	    	  return false;
	      }
    
    }else{
    	session.invalidate();
    	return true;
    }
  }
	    
}
