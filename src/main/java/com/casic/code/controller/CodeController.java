package com.casic.code.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.service.pub.CloudMailService;
import com.casic.util.HttpClientUtil;
import com.casic.util.PropertiesUtils;
import com.casic.util.SmsUtil;
import com.casic.util.StringUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONObject;

/***
 * 验证码控制器
 * @author think
 * 2016 07 04
 *
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController{
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private CloudMailService cloudMailService;

	/**
	 * 注册发送AJAX验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("ajaxSendVerifyCode")
	@Action(description = "Ajax发送验证码")
	@ResponseBody
	public Map<String,String> ajaxSendVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		String mobile = request.getParameter("mobile");
		//String mobile = RequestUtil.getString(request, "mobile");
		boolean flag = SmsUtil.sendVerifySms(mobile);
		if(flag){
			dataMap.put("flag", "1");
		}else{
			dataMap.put("flag", "0");
		}
		return dataMap;
	}
	/**
	 * 注册发送AJAX验证码改造
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("ajaxSendVerifyCodeDataMap")
	@Action(description = "Ajax发送验证码")
	@ResponseBody
	public Map<String, Object> ajaxSendVerifyCodeDataMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//	Map<String, String> dataMap = new HashMap<String, String>();
		String mobile = request.getParameter("mobile");
		//String mobile = RequestUtil.getString(request, "mobile");
		Map<String, Object> dataMap1 = SmsUtil.sendVerifySmsdataMap(mobile);
		boolean object =(boolean) dataMap1.get("flagYESorNO");
		System.out.println( object);
		if(object){
			dataMap1.put("flag", "1");
		}else{
			dataMap1.put("flag", "0");
		}
		return dataMap1;
	}
	
	/**
	 * 注册发送AJAX验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("sendEmailVerifyCode")
	@Action(description = "Ajax发送验证码")
	@ResponseBody
	public Map<String,String> sendEmailVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		String email = request.getParameter("email");
		try {
			cloudMailService.SendVerifyCode(email);
			dataMap.put("flag", "1");
		} catch (Exception e) {
			dataMap.put("flag", "0");
			e.printStackTrace();
		}
		return dataMap;
	}
	
	/**
	 * 检查同一手机号一小时内发送验证码情况
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("ajaxCheckSendNumber")
	@Action(description = "Ajax检查同一手机号一小时内发送验证码情况")
	@ResponseBody
	public Integer ajaxCheckSendNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mobile = request.getParameter("mobile");
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<String,Object>();
		params.put("isAuth", 1);
		params.put("access_token", null);
		data.put("app_key", "wenp");
		data.put("mobile", mobile);
		params.put("data", data);
		String sendCodeUrl = PropertiesUtils.getProperty("sendCodeUrl");
		String result = HttpClientUtil.JsonPostInvoke(sendCodeUrl,params);
		JSONObject json = new JSONObject();
		json = JSONObject.fromObject(result);
		JSONObject mbileCount = (JSONObject) json.get("results");
		String count = (String) mbileCount.get("mobileCount");
		int mCount = Integer.parseInt(count);
		System.out.println(mCount);
		return mCount;
	}
	
	
	
	
	/**
	 * 找回密码 手机号注册发送AJAX验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("ajaxSendVerifyCodeFind")
	@Action(description = "Ajax发送验证码")
	@ResponseBody
	public Map<String,Object> ajaxSendVerifyCodeFind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isExist = false;//返回true是已经存在，false是不存在
		String mobile = request.getParameter("mobile");
		String forgot = request.getParameter("forgot");
	
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("mobile", mobile);
		//Map<String, String> dataMap = new HashMap<String, String>();
		Map<String, Object> dataMap1=new HashMap<String, Object>();
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if(StringUtil.isEmpty(forgot)){
			if(sysUsers.size()>0){
				isExist= true;
			}else{
				isExist= false;
			}
		}else{
			if(sysUsers.size()>0){
				isExist= false;
			}else{
				isExist= true;
			}
		}
		if(isExist){
			//flag:2  该手机号未注册
			 dataMap1.put("flag", "2");
		}else{
			dataMap1 = SmsUtil.sendVerifySmsdataMap(mobile);
			boolean flag =(boolean) dataMap1.get("flagYESorNO");
			//boolean flag = SmsUtil.sendVerifySmsdataMap(mobile);
			if(flag){
				//flag:1   验证码下发成功，注意查收
				dataMap1.put("flag", "1");
			}else{
				//flag:0  验证码下发失败
				dataMap1.put("flag", "0");
			}
		}
		return dataMap1;
	}
	
	/**
	 * 找回密码 邮箱注册发送AJAX验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("sendEmailVerifyCodeFind")
	@Action(description = "Ajax发送验证码")
	@ResponseBody
	public Map<String,String> sendEmailVerifyCodeFind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isExist = false;//返回true是已经存在，false是不存在
		String email = request.getParameter("email");
		String forgot = request.getParameter("forgot");
		QueryFilter queryFilter = new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		queryFilter.getFilters().put("email", email);
		Map<String, String> dataMap = new HashMap<String, String>();
		List<ISysUser> sysUsers = sysUserService.getAll(queryFilter);
		if(StringUtil.isEmpty(forgot)){
			if(sysUsers.size()>0){
				isExist= true;
			}else{
				isExist= false;
			}
		}else{
			if(sysUsers.size()>0){
				isExist= false;
			}else{
				isExist= true;
			}
		}
		if(isExist){
			 dataMap.put("flag", "2");
		}else{
			try {
				cloudMailService.SendVerifyCode(email);
				dataMap.put("flag", "1");
			} catch (Exception e) {
				dataMap.put("flag", "0");
				e.printStackTrace();
			}
		}
		return dataMap;
	}
	
}
