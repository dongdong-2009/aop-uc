package com.casic.cas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;

public class CasAuthenticationRedirect  extends CasAuthenticationEntryPoint{

private String serviceUrlBak=null;
	
	@Override
	protected String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
		if(serviceUrlBak==null)
			serviceUrlBak=getServiceProperties().getService();
		if(serviceUrlBak!=null){
			String ctx=request.getContextPath();
			String queryString=request.getQueryString();
			String requestURI=request.getRequestURI();
			requestURI=requestURI.substring(requestURI.indexOf(ctx)+ctx.length(),requestURI.length());
			String serviceUrl="";
			if(!requestURI.equals("/") && requestURI.length()>0){
				serviceUrl="?"+AbstractAuthenticationTargetUrlRequestHandler.DEFAULT_TARGET_PARAMETER;
				serviceUrl+="="+requestURI;
				if(StringUtils.isNotBlank(queryString)){
					serviceUrl+="?"+queryString;
				}
			}
			getServiceProperties().setService(serviceUrlBak+serviceUrl);
		}
		return super.createServiceUrl(request, response);
    }	
}
