package com.casic.util;


import java.util.HashMap;
import java.util.Map;

public class SmsCache {
	private static Map<String,String> smsMap;
	static{
		smsMap = new HashMap<String, String>();
	}
	public static void add(String mobile,String verifyCode){
		smsMap.put(mobile, verifyCode);
	}
	
	
	public static boolean verify(String mobile,String verifyCode){
		if(smsMap.get(mobile) != null && smsMap.get(mobile).equals(verifyCode)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static void remove(String mobile){
		smsMap.remove(mobile);
	}
}
