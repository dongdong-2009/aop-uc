package com.casic.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

import com.casic.user.model.TemplateDto;
import com.alibaba.druid.support.json.JSONUtils;

/**
 * 获取接口
 * @author my
 *
 */
@Component
public class SendMessage {
	

	
	/**
	 * 获取模板
	 * @param templateCode
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static TemplateDto getTemplate(final String templateCode) throws Exception{
		TemplateDto dto = new TemplateDto();
		Map<String, Object> maps = new HashMap<String, Object>();
		String apiurl = "http://172.17.70.221:8080/";
		String url = apiurl + "/api/1.0/unifyMessage/template";
		maps.put("app_key", "wenp");
		maps.put("templateCode", templateCode);
		HashMap<String, Object> httpArg = new HashMap<String,Object>();
		httpArg.put("data", maps);
		String jsonResult = JSONUtils.toJSONString(httpArg);
		//String result = HttpUtils.urlPost(url, jsonResult);
		String result  = HttpClientUtil.JsonPostInvoke(url, jsonResult);
		if(!StringUtil.isEmpty(result)){
			Map map = JsonUtils.getMap(result);
			if(!"".equals(result) && map.get("status").toString().equals("200") && null != map.get("results")){
				dto = JsonUtils.toBean(map.get("results").toString(), TemplateDto.class);
			}
		}
		return dto;
	}
	
	/**
	 * 替换字符串中的占位符 ${xxx}
	 * @param params
	 * @param message
	 * @return
	 */
	public static String replaceMessage(Map<String, String> params, String message){
		Set<String> keys = params.keySet();
		for(String key : keys){
			//包含对应的占位符
			if(message.indexOf(key) != -1){
				message = message.replace(key, params.get(key));
			}
		}
		return message;
	}
	
	/**
	 * 发送消息接口
	 * @param templateCode	模板id 如:ywmb001
	 * @param systemId	系统id
	 * @param userId	发送人的用户ID
	 * @param operatorId	接收人ID
	 * @param bussinessKey	业务主键
	 * @param params 如:key:${inquireObject} value:对象名称
	 * 				    key:${tenantName} value:航天智造有限公司
	 */
	public static void sendMessage(final Map<String, String> params, final String templateCode, final String systemId,
								final String userId){
		//新开线程
		new Thread(new Runnable() {
			@Override
		    public void run() {
				//获取模板
				TemplateDto dto;
				try {
					//dto = getTemplate(templateCode);
					//String message = dto.getTemplateValue();
					//String messageType = dto.getTypeId();
					//模板信息不为空，替换里面的占位符
					/*if(!StringUtil.isEmpty(message)){
						message = replaceMessage(params, message);
					}*/
					//获取接口地址
				   String apiurl = PropertiesUtils.getProperty("api.url");
					//String apiurl = "http://172.17.70.221:8080/";
					String url = apiurl + "/api/1.0/unifyMessage/repPublish";
					//组织参数
					Map<String, Object> maps = new HashMap<String, Object>();
					//maps.put("messageType", messageType);
					//maps.put("messageValue", message);
					maps.put("systemId", systemId);
					maps.put("sender", systemId);
					maps.put("type", "0");
					//maps.put("topic", message);
					maps.put("receiver", userId);
					//maps.put("bussinessKey", bussinessKey);
					maps.put("app_key", "wenp");
					maps.put("templateCode",templateCode);
					maps.put("values",params);
					HashMap<String, Object> httpArg = new HashMap<String,Object>();
					httpArg.put("data", maps);
					String jsonResult = JSONUtils.toJSONString(httpArg);
					//String result = HttpUtils.urlPost(url, jsonResult);
					String result  = HttpClientUtil.JsonPostInvoke(url, jsonResult);
					System.out.println("result...." + result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	
}
