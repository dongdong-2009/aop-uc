package com.casic.core.security.cas.web;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;

import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.system.SystemConst;

/**
 * 多应用单点登录Filter
 * 校验CASTGC的Cookie，如果存在请求CASServer
 * 
 * @author csx
 */
public class CASTGCRememberMeFilter implements Filter{
	/**具有匿名访问权限的url*/
	private  HashSet<String> casValidateUrls=new HashSet<String>();
   
	/**
	 * 设置具有匿名访问权限的url
	 * @param anonymousUrls
	 */
	public  void setCasValidateUrls(HashSet<String> casValidateUrls) {
		this.casValidateUrls = casValidateUrls;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//if(auth!=null){//包含匿名用户
			String url = req.getRequestURI();
	    	url=removeCtx(url,req.getContextPath());
			//if(auth.getAuthorities().contains(SystemConst.ROLE_CONFIG_ANONYMOUS)){
				//需要cas验证访问的URL,支持通配符
		    	AntPathMatcher matcher = new AntPathMatcher();
		    	for(String casValidateUrl : casValidateUrls){
		    		if(matcher.match(casValidateUrl, url)){
		    			res.sendRedirect("http://10.142.14.12:1010/login?service=http%3A%2F%2F10.142.14.12%3A1027%2Fj_spring_cas_security_check");
		    		}
		    	}
			//}
		//}
		//chain.doFilter(request, response);
	}

	/**
	 * 获取当前URL
	 * @param url
	 * @param contextPath
	 * @return
	 */
	private static String removeCtx(String url,String contextPath){
		url=url.trim();
		if(StringUtil.isEmpty(contextPath)) return url;
		if(StringUtil.isEmpty(url)) return "";
		if(url.startsWith(contextPath)){
			url=url.replaceFirst(contextPath, "");
		}
		return url;
	}
	
	@Override
	public void destroy()
	{
	}
}
