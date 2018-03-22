package com.casic.cloud.filter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.auth.ISysUser;

/**
 * 框架权限的控制	
 * @author shenjb
 *
 */
public class UserAuthorityInterceptor extends HandlerInterceptorAdapter {
		
	
	    @Override    
	    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {  
	    	HttpSession session = ((HttpServletRequest) request).getSession();
	    	Map mapReg = new HashMap();
	    	mapReg.put("script", ".*<.*script.*>.*");
	    	mapReg.put("alert", ".*alert\\(.*?\\).*");
//	    	mapReg.put("href=", ".*<.*href=.*>.*");
	    	mapReg.put("textarea", ".*<.*textarea.*>.*");
	    	mapReg.put("onmouseover", ".*onmouseover.*");
	    	mapReg.put("iframe", ".*<.*iframe.*>.*");
	    	mapReg.put("textarea", ".*<.*textarea.*>.*");
	    	mapReg.put("onmouseover", ".*onmouseover.*");
	    	mapReg.put("insert", ".*insert.*");
	    	mapReg.put("update", ".*update.*");
	    	mapReg.put("delete", ".*delete.*");
	    	mapReg.put("where", ".*where.*");
	    	mapReg.put("select", ".*select.*");
	    	mapReg.put("object data=data:text/html", ".*object data=data:text/html.*");
	        Map<String, Object> map =getRequestParamMap(request);
	        Iterator<String> itfilter=  mapReg.keySet().iterator();
	        ISysUser user = ContextUtil.getCurrentUser();
	        if(user!=null){
	        	session.setAttribute("userId", user.getUserId());
	        	session.setAttribute("orgId", user.getOrgSn());
	        }
	        
	        for(String key:map.keySet()){
	        	String parString=(String) map.get(key);
	        	
	        	if(parString==null){
	        		return true;
	        	}
	        		String b=parString.toString();
	        		itfilter=  mapReg.keySet().iterator();
	    				  if(user==null){
	    					  String regex=".*<a.*";
	    					  if(b.matches(regex)){
	    	    				  response.setContentType("text/html;charset=UTF-8");
	    		    			  response.getWriter().write("<html><body><script type=\"text/javascript\">alert('请勿进行非法操作！非法的参数是："+regex+"')</script></body></html>");
	    		    			   return false;
	    					  }
	    				  }
	    		   while(itfilter.hasNext()){
	    			   String a=(String)mapReg.get(itfilter.next());
	    			   if(b.matches(a)){
	    				  System.out.println(a.toUpperCase());
	    				  System.out.println(b.toUpperCase());
	    				  response.setContentType("text/html;charset=UTF-8");
		    			  response.getWriter().write("<html><body><script type=\"text/javascript\">alert('请勿进行非法操作！非法的参数是："+a+"')</script></body></html>");
		    			   return false;
	    			  } 
	    		   
	    	   }
	       }
            return true;
	    }    
	    /**
	     * 从请求中获取所有参数（当参数名重复时，用后者覆盖前者）
	     */
//		public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
//	        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
//	        try {
//	            String method = request.getMethod();
//	            if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete")) {
//	                String queryString = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
//	                if (StringUtil.isNotEmpty(queryString)) {
//	                    String[] qsArray = StringUtil.splitString(queryString, "&");
//	                    if (ArrayUtil.isNotEmpty(qsArray)) {
//	                        for (String qs : qsArray) {
//	                            String[] array = StringUtil.splitString(qs, "=");
//	                            if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
//	                                String paramName = array[0].trim();
//	                                String paramValue = array[1].trim();
//	                                if (checkParamName(paramName)) {
//	                                    if (paramMap.containsKey(paramName)) {
//	                                        paramValue = paramMap.get(paramName) + StringUtil.SEPARATOR + paramValue;
//	                                    }
//	                                    paramMap.put(paramName, paramValue);
//	                                }
//	                            }
//	                        }
//	                    }
//	                }
//	            } else {
//	                Enumeration<String> paramNames = request.getParameterNames();
//	                while (paramNames.hasMoreElements()) {
//	                    String paramName = paramNames.nextElement();
//	                    if (checkParamName(paramName)) {
//	                        String[] paramValues = request.getParameterValues(paramName);
//	                        if (ArrayUtil.isNotEmpty(paramValues)) {
//	                            if (paramValues.length == 1) {
//	                                paramMap.put(paramName, paramValues[0]);
//	                            } else {
//	                                StringBuilder paramValue = new StringBuilder("");
//	                                for (int i = 0; i < paramValues.length; i++) {
//	                                    paramValue.append(paramValues[i]);
//	                                    if (i != paramValues.length - 1) {
//	                                        paramValue.append(StringUtil.SEPARATOR);
//	                                    }
//	                                }
//	                                paramMap.put(paramName, paramValue.toString());
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        } catch (Exception e) {
//	            logger.error("获取请求参数出错！", e);
//	            throw new RuntimeException(e);
//	        }
//	        return paramMap;
//	    }
	    
	    /**
	     * 从请求中获取所有参数（当参数名重复时，用后者覆盖前者）
	     */
		public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
	        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
	        try {
	            String method = request.getMethod();
	            if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete")) {
	                String queryString = URLDecoder.decode(getString(request.getInputStream()),"UTF-8");
	                if ("".equals(queryString)) {
	                    String[] qsArray = queryString.split("&") ;//StringUtil.splitString(queryString, "&");
	                    if (qsArray.length>0) {
	                        for (String qs : qsArray) {
	                            String[] array = queryString.split("=") ;//StringUtil.splitString(qs, "=");
	                            if (array.length>0 && array.length == 2) {
	                                String paramName = array[0].trim();
	                                String paramValue = array[1].trim();
	                                if (checkParamName(paramName)) {
	                                    if (paramMap.containsKey(paramName)) {
	                                        paramValue = paramMap.get(paramName) + String.valueOf((char) 29) + paramValue;
	                                    }
	                                    paramMap.put(paramName, paramValue);
	                                }
	                            }
	                        }
	                    }
	                }
	            } else {
	                Enumeration<String> paramNames = request.getParameterNames();
	                while (paramNames.hasMoreElements()) {
	                    String paramName = paramNames.nextElement();
	                    if (checkParamName(paramName)) {
	                        String[] paramValues = request.getParameterValues(paramName);
	                        if (paramValues.length>0) {
	                            if (paramValues.length == 1) {
	                                paramMap.put(paramName, paramValues[0]);
	                            } else {
	                                StringBuilder paramValue = new StringBuilder("");
	                                for (int i = 0; i < paramValues.length; i++) {
	                                    paramValue.append(paramValues[i]);
	                                    if (i != paramValues.length - 1) {
	                                        paramValue.append(String.valueOf((char) 29));
	                                    }
	                                }
	                                paramMap.put(paramName, paramValue.toString());
	                            }
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	        return paramMap;
	    }

	    /**
	     * 从输入流中获取字符串
	     */
	    public static String getString(InputStream is) {
	        StringBuilder sb = new StringBuilder();
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new RuntimeException(e);
	        }
	        return sb.toString();
	    }
	    private static boolean checkParamName(String paramName) {
	        return !paramName.equals("_"); // 忽略 jQuery 缓存参数
	    }
	    public static void main(String[] args) {
			String a="88888 onmouseover=prompt(42873) bad=";
			String b=".*onmouseover.*";
			System.out.println(a.matches(b));
			
		}
}
