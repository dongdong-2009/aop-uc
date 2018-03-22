package com.casic.oauth.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.oauth.model.OauthParam;
import com.casic.util.CacheUtil;
import com.casic.util.HttpClientUtil;
import com.casic.util.MobileUtil;
import com.casic.util.OpenIdUtil;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;

/***
 * oauth 认证controller
 * @author think
 * 20161102
 */
@Controller
@RequestMapping("/oauth")
public class OauthController extends BaseController{
	
	@Resource
	private SysUserService sysUserService;
	
	//展示页面
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response){
		String response_type = request.getParameter("response_type");
		String client_id = request.getParameter("client_id");//此处要判断client_id 是否已经创建qq登录
		String redirect_uri = request.getParameter("redirect_uri");
		String state = request.getParameter("state");
		//String scope = request.getParameter("scope");
		String display = request.getParameter("display");
		ModelAndView mv = new ModelAndView("/oauth/login.jsp");
		mv.addObject("response_type", response_type)
		  .addObject("client_id", client_id)
		  .addObject("redirect_uri", redirect_uri)
		  .addObject("state", state)
		  .addObject("display", display);
		return mv;
	}
	
	/***
	 * 验证用户名密码等信息，然后返回到指定的地址并返回code
	 * response_type	必须	授权类型，此值固定为“code”。
	 * client_id	必须	申请QQ登录成功后，分配给应用的appid。
	 * redirect_uri	必须	成功授权后的回调地址，必须是注册appid时填写的主域名下的地址，建议设置为网站首页或网站的用户中心。注意需要将url进行URLEncode。
	 * state	必须	client端的状态值。用于第三方应用防止CSRF攻击，成功授权后回调时会原样带回。请务必严格按照流程检查用户与state参数状态的绑定。
	 * scope	可选	请求用户授权时向用户显示的可进行授权的列表。
				可填写的值是API文档中列出的接口，以及一些动作型的授权（目前仅有：do_like），如果要填写多个接口名称，请用逗号隔开。
				例如：scope=get_user_info,list_album,upload_pic,do_like
				不传则默认请求对接口get_user_info进行授权。
				建议控制授权项的数量，只传入必要的接口名称，因为授权项越多，用户越可能拒绝进行任何授权。
	 * display	可选	仅PC网站接入时使用。
				用于展示的样式。不传则默认展示为PC下的样式。
				如果传入“mobile”，则展示为mobile端下的样式。
	 * @param request
	 * @param response
	 * @return "code"
	 * @throws IOException 
	 */
	@RequestMapping(value = "authorize", method = RequestMethod.POST)
	public void authorize(OauthParam op,HttpServletRequest request, HttpServletResponse response) throws IOException{
		String redirect_uri = op.getRedirect_uri();//用户验证成功后要返回到该地址（要进行decode）
		String[] scopes = new String[5];
		String scope[] = request.getParameterValues("scope");//获取前台用户授权的url
		//此处要校验用户名和密码
		String userName = request.getParameter("account");
		String password = request.getParameter("password");
		password = EncryptUtil.encryptSha256(password);
		List<ISysUser> users = new ArrayList<ISysUser>();
		ISysUser user = null;
		String openId = "";
		if(userName!=null){
			if(userName.indexOf("@")>0){
				users = sysUserService.getByEmail(userName, password);
			}
			else if(MobileUtil.telCheck(userName)){
				users = sysUserService.getByMobile(userName, password);
			}
			
			else{
				users = sysUserService.getByVip(userName, password);
			}
		}
		if(users.size()>1){
			System.out.println("该数据有问题");
		}
		if(users.size()==0){
			System.out.println("用户名或密码错误");
		}
		else{
			user = users.get(0);
			openId = user.getOpenId();//用户唯一标示
			if(openId==null||"".equals(openId)){//老数据用户很可能不存在openId信息
				openId = OpenIdUtil.getOpenId();
				user.setOpenId(openId);
				sysUserService.update(user);//如果用户没有openId则生成一个
			}
			op.setOpenId(openId);
		}
		
		String code = OpenIdUtil.getOpenId();
		CacheUtil.getUserCache().add(code, op);//设置缓存
		redirect_uri = redirect_uri+"?code="+code;//此地址为返回第三方系统配置的地址同时要将code设置到缓存中
		response.sendRedirect(redirect_uri);
	}
	
	/***
	 * 通过code等信息获得token
	 * grant_type	必须	授权类型，此值固定为“authorization_code”
	 * client_id	必须	申请QQ登录成功后，分配给网站的appid
	 * client_secret	必须	申请QQ登录成功后，分配给网站的appkey
	 * code	必须	上一步返回的authorization code。
	 * redirect_uri	必须	与上面一步中传入的redirect_uri保持一致。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "token", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> token(@RequestBody OauthParam op ,HttpServletRequest request, HttpServletResponse response){
		Map<String, String> dataMap = new HashMap<String, String>();
		String response_type = op.getResponse_type();//此处为固定值“code”
		String client_secret = op.getClient_id();
		String code = op.getCode();
		String redirect_uri =op.getRedirect_uri();
		//验证没问题后返回access_token、expires_in是该access_token的有效期，单位为秒。
		OauthParam cacheOp = new OauthParam();
		cacheOp = (OauthParam) CacheUtil.getUserCache().getByKey(code);
		String openId = cacheOp.getOpenId();
		if(openId!=null&&!"".equals(openId)){
			CacheUtil.getUserCache().delByKey(code);//删掉code缓存信息
			String access_token = OpenIdUtil.getOpenId();
			CacheUtil.getUserCache().add(access_token, cacheOp);
			dataMap.put("access_token", access_token);
			dataMap.put("expires_in", "7776000");
		}
		else{
			dataMap.put("access_token", "不存在");
		}
		return dataMap;
	}
	
	
	/***
	 * 这个为子系统的回调地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getToken", method = RequestMethod.GET)
	public String getToken(HttpServletRequest request, HttpServletResponse response){
		String code = request.getParameter("code");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> meparams = new HashMap<String, Object>();
		params.put("response_type", "code");
		params.put("client_id", "myid");
		params.put("client_secret", "mysecret");
		params.put("code", code);
		params.put("redirect_uri", "myredirect_uri");
		String result = "";
		try {
			//获得token
			result = HttpClientUtil.JsonPostInvoke("http://172.17.11.154:1027/oauth/token.ht", params);
			System.out.println("result="+result);
			JSONObject json = JSONObject.fromObject(result);
			String access_token = json.getString("access_token");
			String expires_in = json.getString("expires_in");
			System.out.println("access_token="+access_token);
			System.out.println("expires_in="+expires_in);
			//根据token获得openId
			meparams.put("access_token", access_token);
			result = HttpClientUtil.JsonPostInvoke("http://172.17.11.154:1027/oauth/me.ht", meparams);
			JSONObject me = JSONObject.fromObject(result);
			String openid = me.getString("openid");
			System.out.println("openid="+openid);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//验证没问题后返回token
		return "token";
	}
	/**
	 * 根据上一步获得的token信息来获取openId(用户在系统的唯一标识)
	 * access_token
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "me", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> me(@RequestBody OauthParam op,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
		String access_token = op.getAccess_token();
		OauthParam cacheOp = new OauthParam();
		cacheOp =(OauthParam) CacheUtil.getUserCache().getByKey(access_token);
		String openId = cacheOp.getOpenId();
		params.put("client_id", cacheOp.getClient_id());
		params.put("openId", openId);
		return params;
	}
	
}
