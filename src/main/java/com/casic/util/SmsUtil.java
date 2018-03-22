package com.casic.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SmsUtil {
    // 查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    //通用发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // 模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    //状态报告获取
    private static String URI_PULL_STATUS= "http://yunpian.com/v1/sms/pull_status.json";

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    public static void main(String[] args) throws IOException, URISyntaxException {
        //修改为您的apikey
        String apikey = "7b923753548b33f9c9a803fad8710a4f";
        //修改为您要发送的手机号
        String mobile = "13811708055";

        /**************** 查账户信息调用示例 *****************/
//        System.out.println(SmsUtil.getUserInfo(apikey));

        /**************** 使用通用接口发短信(推荐) *****************/
        //设置您要发送的内容 (内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【航天云网】您的邀请验证码是：740117，如非本人操作，请忽略！";
        SmsUtil.sendSms(apikey, text, mobile);
        //发短信调用示例
//        System.out.println(SmsUtil.sendSms(apikey, text, mobile));
//        System.out.println(sendSms("【天智云】你的天智云验证码是：1234。请尽快登录激活！",mobile).get("msg"));

        /**************** 使用模板接口发短信(不推荐) *****************/
        //设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
        long tpl_id = 1549404;
        //设置对应的模板变量值
        //如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
        //如code值是#1234#,需作如下编码转换
        String codeValue = URLEncoder.encode("740117", ENCODING);
        String tpl_value = "#code#=" + codeValue;
        //模板发送的调用示例
     //   System.out.println(SmsUtil.tplSendSms(apikey, tpl_id, tpl_value, "13552438955"));
//        System.out.println(sendVerifySms(mobile));
      //  System.out.println((int)(Math.random()*9000+1000));
        String resp = tplSendSms(apikey,tpl_id,tpl_value,mobile);
      // sendZhuceXXSms("13552438955", codeValue, "123456", "ypcheng");
    }

    /**
     * 取账户信息
     *
     * @return json格式字符串
     * @throws java.io.IOException
     */
    public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return post(URI_GET_USER_INFO, params);
    }
    
    public static JSONObject sendSms( String text, String mobile){
    	String apikey = PropertiesUtils.getProperty("sms.apikey");
    	JSONArray arr;
		try {
			arr = JSONArray.fromObject("["+sendSms(apikey,text,mobile)+"]");
			JSONObject jsonObject = arr.getJSONObject(0);
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    
    /**
     * 验证用户输入的验证码正确性
     * @param mobile
     * @param verifyCode
     * @return
     */
    public static boolean verifyCode(String mobile,String verifyCode){
    	return verifyCode.equals(PropertiesUtils.getProperty("sms.codeValue"));
    }
    
    /**
     * 发送验证码
     * @param mobile
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public static boolean sendVerifySms(String mobile) throws NumberFormatException, IOException{
    	String tpl_id = PropertiesUtils.getProperty("sms.tpl_id");
    	String apikey = PropertiesUtils.getProperty("sms.apikey");
//    	String codeValue = PropertiesUtils.getProperty("sms.codeValue");
    	String codeValue = String.valueOf((int)(Math.random()*9000+1000));
    	SmsCache.add(mobile, codeValue);
    	String tpl_value = "#code#=" + codeValue;
    	String resp = tplSendSms(apikey,Long.parseLong(tpl_id),tpl_value,mobile);
    	JSONArray arr = JSONArray.fromObject("["+resp+"]");
    	JSONObject jsonObject = arr.getJSONObject(0);
    	int code =  (Integer) jsonObject.get("code");
    	if(code == 0){
    		System.out.println("发送信息成功，手机号【"+mobile+"】"+codeValue);
    		return true;
    	}else{
    		System.out.println("发送信息失败，手机号【"+mobile+"】，失败原因【"+jsonObject.get("msg")+"】");
    		return false;
    	}
    }
    /**
     * 发送验证码dataMap
     * @param mobile
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public static Map<String, Object> sendVerifySmsdataMap(String mobile) throws NumberFormatException, IOException{
    	String tpl_id = PropertiesUtils.getProperty("sms.tpl_id");
    	String apikey = PropertiesUtils.getProperty("sms.apikey");
//    	String codeValue = PropertiesUtils.getProperty("sms.codeValue");
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	String codeValue = String.valueOf((int)(Math.random()*9000+1000));
    	SmsCache.add(mobile, codeValue);
    	String tpl_value = "#code#=" + codeValue;
    	String resp = tplSendSms(apikey,Long.parseLong(tpl_id),tpl_value,mobile);
    	JSONArray arr = JSONArray.fromObject("["+resp+"]");
    	JSONObject jsonObject = arr.getJSONObject(0);
    	int code =  (Integer) jsonObject.get("code");
    	if(code == 0){
    		System.out.println("发送信息成功，手机号【"+mobile+"】"+codeValue);
    		//return true;
    		dataMap.put("flagYESorNO", true);
    		
    	}else{
    		System.out.println("发送信息失败，手机号【"+mobile+"】，失败原因【"+jsonObject.get("msg")+"】");
    		//return false;
    		dataMap.put("flagYESorNO", false);
    	}
    	dataMap.put("mobile", mobile);
		dataMap.put("codeValue", codeValue);
    	return dataMap;
    }
    
    
    public static void sendZhuceXXSms(String mobile,String code,String password,String userName) throws NumberFormatException, IOException{
    	String tpl_id = PropertiesUtils.getProperty("zhuce.tpl_id");
    	String apikey = PropertiesUtils.getProperty("sms.apikey");
//    	String codeValue = PropertiesUtils.getProperty("sms.codeValue");
    	//String codeValue = String.valueOf((int)(Math.random()*9000+1000));
    	//SmsCache.add(mobile, codeValue);
    	String tpl_value = "#code#="+code+"&#password#="+password+"&#userName#="+userName;
    //	tpl_value=urlencode("#code#=1234&#company#=云片网") 
    	String resp = tplSendSms(apikey,Long.parseLong(tpl_id),tpl_value,mobile);
    //	JSONArray arr = JSONArray.fromObject("["+resp+"]");
    //	JSONObject jsonObject = arr.getJSONObject(0);
    /*	int code =  (Integer) jsonObject.get("code");
    	if(code == 0){
    		System.out.println("发送信息成功，手机号【"+mobile+"】");
    		return true;
    	}else{
    		System.out.println("发送信息失败，手机号【"+mobile+"】，失败原因【"+jsonObject.get("msg")+"】");
    		return false;
    	}*/
    }
    

    /**
     * 通用接口发短信(推荐)
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String sendSms(String apikey, String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }

    /**
     * 通过模板号发送短信(推荐)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }


    /**
     * 基于HttpClient 3.1的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        HttpClient client = new HttpClient();
        try {
            PostMethod method = new PostMethod(url);
            if (paramsMap != null) {
                NameValuePair[] namePairs = new NameValuePair[paramsMap.size()];
                int i = 0;
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new NameValuePair(param.getKey(), param.getValue());
                    namePairs[i++] = pair;
                }
                method.setRequestBody(namePairs);
                HttpMethodParams param = method.getParams();
                param.setContentCharset(ENCODING);
            }
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}