package com.casic.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.http.client.ClientProtocolException;

public class CallableThread implements Callable<String>{
	private Map<String, Object> params=new HashMap<String, Object>(); 
	private String url;//请求的url
     /**
      * 
      * @param systemId子系统Id
      * @param params请求参数
      * @param url请求地址
      */
	  public CallableThread(Map<String, Object> params,String url){
		  this.params=params;
		  this.url=url;
	  }

	   public synchronized String method()
      {
           String result=null;
           
           try {
			result=HttpClientUtil.JsonPostInvoke(url, params);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
           return result;
      }

	

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	   
	   



		@Override
		public String call() throws Exception {
		
			String result=method();
			
			return result;
		}
		
	}
