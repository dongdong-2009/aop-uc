package com.casic.core.security.cas.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.casic.util.StringUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.auth.ISysUser;
/**
 * 作为session拦截判断的过滤器
 * @author ypchenga
 *
 */
public class SystemFilter implements Filter {
	/**在登录之前，有些URL是没有Session的，查找到这些URL，统一进行控制，相当于过滤器中不包含这些方法和连接*/
	List<String> list = new ArrayList<String>();
	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String ctx=request.getContextPath();
		String queryString=request.getQueryString();
		String requestURI=request.getRequestURI();
		requestURI=requestURI.substring(requestURI.indexOf(ctx)+ctx.length(),requestURI.length());
		//如果请求是去登录或者登出放行
		if(requestURI.indexOf("/login")>-1||requestURI.indexOf("/logout")>-1||requestURI.indexOf("/commons/sessionOut.jsp")>-1||requestURI.indexOf("/index")>-1||requestURI.indexOf("/user/doLogin")>-1){
			chain.doFilter(request, response);
			return;
		}
	   if(list.contains(requestURI)){
		   chain.doFilter(request, response);
		   return;
	   }
		ISysUser user=ContextUtil.getCurrentUser();
		if(user!=null){
			//表示session存在
			chain.doFilter(request, response);
			return;
		}else{
			//如果Session中不存在数据，重定向到error.jsp的页面上
		
			response.sendRedirect(request.getContextPath()+"/commons/sessionOut.jsp");
			
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		addXmltoList(list);
		
	}

	private static void addXmltoList(List<String> list) {
		SAXReader reader = new SAXReader(); 
		 // 通过read方法读取一个文件 转换成Document对象  
		Document document;
		String result = "";  
		   
	    ClassLoader classLoader =SystemFilter.class.getClassLoader();  
	    try {  
	    	result = classLoader.getResource("/conf/app-security-cas.xml").getPath();
	       // result = IOUtils.toString(classLoader.getResourceAsStream("/conf/app-security-cas.xml"));  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	   
	 
        try {
        	document = reader.read(new File(result));
        	 //获取根节点元素对象  
        	  Element root =  document.getRootElement();
        	  List<Element> elements=root.elements("bean");
        	 
           for (Element node : elements) {
        	
			List<Attribute> attributes=node.attributes();
			for (Attribute attr : attributes) {
				 if(!StringUtil.isEmpty(attr.getValue())&&attr.getValue().equals("securityMetadataSource")){
					 listNodes(node,list);
				 }
			} 
		}
         //listNodes((Element)node);
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		
	}

	private static void listNodes(Element node, List<String> list2) {
		  // 获取当前节点的所有属性节点 
          if(node.getName().equals("value")){
        	  list2.add(node.getText());
          }
	   
	  
	    
	  
	        // 当前节点下面子节点迭代器  
	        Iterator<Element> it = node.elementIterator();  
	        // 遍历  
	        while (it.hasNext()) {  
	            // 获取某个子节点对象  
	            Element e = it.next();  
	            // 对子节点进行遍历  
	            listNodes(e,list2);  
	        } 
	        
		 }
	  
		
	

}
