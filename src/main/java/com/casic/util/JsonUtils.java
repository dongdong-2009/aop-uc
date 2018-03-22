package com.casic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	/**
	 * json str 转Map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getMap(String json){
		return (Map)JSONObject.parse(json);
	}
	
	public static List<Map> getListMap(String str){
		List<Map> lists = new ArrayList<Map>();
		net.sf.json.JSONArray arr = net.sf.json.JSONArray.fromObject(str);
		return (List<Map>)net.sf.json.JSONArray.toCollection(arr, Map.class);
//		JSONArray arr = JSONArray.parseArray(str);
//		for(Object o : arr){
//			lists.add(getMap(o.toString()));
//		}
//		return lists;
	}
	
	/**
	 * 转化为集合
	 * @param arr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(String arr,  Class<T> clazz){
		return JSONObject.parseArray(arr, clazz);
	}
	/**
	 * str 转 json
	 * @param str
	 * @return
	 */
	public static JSONObject parseJson(String str){
		return JSONObject.parseObject(str);
	}
	/**
	 * 转化为bean
	 * @param str
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(String str,  Class<T> clazz){
		return JSONObject.parseObject(str, clazz);
	}
	
	public static void main(String[] args) {
//		String str = "{\"results\":[{\"inquiryTheme\":\"WXJ-1708-009\",\"endTime\":1506096000000},{\"inquiryTheme\":\"WXJ-1708-008\",\"endTime\":1505923200000}],\"status\":200,\"msg\":\"success\"}";
//		Map maps = getMap(str);
//		String results = maps.get("results").toString();
//		System.out.println("results...." + results);
//		List<InquiryBean> ins = parseArray(maps.get("results").toString(), InquiryBean.class);
//		System.out.println("ins..." + ins);
		String strs = "[{\"funName\":\"立即处理\", \"url\":\"http://localhost:8080/inquiry/end.ht?flag=p\"}, {\"funName\":\"查看详情\", \"url\":\"http://localhost:8080inquiry/getQuoteDetails.ht?id=10000080730000\"}]";
		List<Map> lists = getListMap(strs);
		System.out.println("lists..." + lists);
	}
}
