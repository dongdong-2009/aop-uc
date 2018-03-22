package com.casic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidateUtil {
	/**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
    /**
     * url
     * @param rul
     * @return
     */
    public static boolean checkURL(String url){
        boolean flag = false;
        try{
                String check = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(url);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
    /**
     * 验证邮编
     * @param postcode
     * @return
     */
    public static boolean checkPostcode(String postcode){
    	boolean flag = false;
    	try{
    		String check = "^[0-9]{6}$";
    		Pattern regex = Pattern.compile(check);
    		Matcher matcher = regex.matcher(postcode);
    		flag = matcher.matches();
    	}catch(Exception e){
    		flag = false;
    	}
    	return flag;
    }
     
    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    /*public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try{
                Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
                Matcher matcher = regex.matcher(mobileNumber);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }*/
    public static boolean checkMobileNumber(String tel){     
		Pattern p = Pattern.compile("^((13\\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(18[0,1,2,3,4,5,6,7,8,9]\\d{8}$)|(147\\d{8})$|(17[0,1,2,3,4,5,6,7,8,9]\\d{8})$)");    
		Matcher m = p.matcher(tel);    
		return m.matches();  
	} 
    
    public static void main(String[] args) {
		System.out.println(checkMobileNumber("13111111111"));
	}
    /**
     * 中文数字英文下划线
     * */
    public static boolean checkChinaEnAndNum(String str){     
		Pattern p = Pattern.compile("^[\u4E00-\u9FA5A-Za-z0-9_]+$");    
		Matcher m = p.matcher(str);    
		return m.matches();  
	}
    /**
     *  英文数字下划线
     * */
    public static boolean checkEnAndNum(String str){     
		Pattern p = Pattern.compile("/[A-Za-z0-9_-]/");    
		Matcher m = p.matcher(str);    
		return m.matches();  
	}
  
    public static boolean checkEn(String str){     
    	Pattern p = Pattern.compile("^[a-zA-Z_0-9-]+$");    
    	Matcher m = p.matcher(str);    
    	return m.matches();  
    }

    /**
     * 汉字与英文组合
     * */
    public static boolean checkEnAndChina(String str){     
		Pattern p = Pattern.compile("/[A-Za-z0-9_-]/");    
		Matcher m = p.matcher(str);    
		return m.matches();  
	}
    
    public static boolean isNumeric(String str){ 
    	
    	String reg = "^[0-9]*[1-9][0-9]*$";
    	return Pattern.compile(reg).matcher(str).find();
    }
    public static boolean checkChinese(String str){ 
    	
    	String reg = "^[\u4E00-\u9FA5]+$";
    	return Pattern.compile(reg).matcher(str).find();
    }
    public static boolean isNumberOrEn(String str){ 
    	
    	String reg = "^[0-9a-zA-Z]+$";
    	return Pattern.compile(reg).matcher(str).find();
    }
    

   
}
