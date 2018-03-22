package com.casic.demo;

import java.util.HashMap;
import java.util.Map;








import net.sf.json.JSONObject;

import com.casic.util.HttpClientUtil;
import com.casic.util.SecreptUtil;

public class Demo  {
	
	public static void main(String[] args) throws Exception {
		String url="http://10.142.15.191:8080/api/registerNoBack.ht";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemId","10000046690000");
		Map<String, Object> paramsData = new HashMap<String, Object>();
		paramsData.put("fullname", "test"); 
		paramsData.put("account", "test"); 
		paramsData.put("shortAccount", "test"); 
		paramsData.put("password", "123456"); 
		paramsData.put("isExpired", 0); 
		paramsData.put("isLock", 0); 
		paramsData.put("status", 1); 
		paramsData.put("email", "test@qq.com"); 
		paramsData.put("mobile", "13265498774");
		paramsData.put("sex", "1");
		paramsData.put("orgId", 456);
		paramsData.put("isMobailTrue", "1");
		paramsData.put("isEmailTrue", "1");
		

		JSONObject jsonObject = JSONObject.fromObject(paramsData);
		//数据加密
		SecreptUtil des = new SecreptUtil("2ffbdf5e9b4044a8a100abc5a1b451b7");//自定义密钥   
		String encryptData = des.encrypt(jsonObject.toString());
		params.put("data",encryptData);
		String result = "";
		try {
			result = HttpClientUtil.JsonPostInvoke(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	    	System.out.println("调用接口结果="+result);
	    }
		
		
	}
	
	
}
