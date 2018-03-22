package com.casic.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

public class HttpFactory {
	
	 public String postHttp(String url,Map<String, Object> params) throws ClientProtocolException, IOException{
		 
		String result= HttpClientUtil.JsonPostInvoke(url, params);
		return result;
	 } 

}
