package com.casic.util;

import java.util.UUID;

public class OpenIdUtil {
	public static String getOpenId(){
		String openId ="";
		String openStr = UUID.randomUUID().toString(); 
        //去掉“-”符号 
		openId = openStr.substring(0,8)+
				 openStr.substring(9,13)+
				 openStr.substring(14,18)+
				 openStr.substring(19,23)+
				 openStr.substring(24); 
		return openId;
	}

}
