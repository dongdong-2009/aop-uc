package com.casic.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.util.SendMessage;
import com.casic.util.SmsCache;
import com.casic.util.StringUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.ICache;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

/***
 * 安全设置首页
 * 
 * @author think 2016 08 03
 *
 */
@Controller
@RequestMapping("/security")
public class SecurityController extends BaseController {

	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private ICache iCache;
	
	@RequestMapping("index")
	@Action(description = "安全设置首页")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/securityIndex.jsp");
		//ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		//用户注册完成后，触发账号安全验证的通知。
		String userName = user.getUsername();
		Map<String,String> params = new HashMap<String,String>();
		params.put("${userName}", userName);
		String templateCode = "ywmb015";
		String systemId = "10000059380000";
		String userId =String.valueOf(user.getUserId());
		SendMessage.sendMessage(params, templateCode, systemId, userId);
		return mv.addObject("user", user);
	} 
	
	@RequestMapping("changeMobile")
	@Action(description = "更换手机")
	public ModelAndView changeMobile(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/changeMobile.jsp");
		//ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		return mv.addObject("user", user);
	} 
	
	@RequestMapping("changeMobileStep2")
	@Action(description = "更换手机")
	public ModelAndView changeMobileStep2(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/changeMobileStep2.jsp");
		return mv;
	} 
	
	@RequestMapping("changeMobileStep3")
	@Action(description = "更换手机")
	public ModelAndView changeMobileStep3(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/changeMobileStep3.jsp");
		String mobile = request.getParameter("mobile");
		return mv.addObject("mobile", mobile);
	} 
	
	@RequestMapping("checkVerifyCode")
	@Action(description = "校验手机验证码")
	@ResponseBody
	public String checkVerifyCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String mobile = request.getParameter("mobile");
		String verifycode = request.getParameter("verifycode");
		String resultMessage = "校验手机验证码成功";
		if(!SmsCache.verify(mobile, verifycode)){
			resultMessage = "验证码有误";
		}
		return resultMessage;
	}
	
	@RequestMapping("updateMobile")
	@Action(description = "更新手机号")
	@ResponseBody
	public String updateMobile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String mobile = request.getParameter("mobile");
		//ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		String resultMessage = "更换手机成功";
		user.setMobile(mobile);
		sysUserService.update(user);
		return resultMessage;
	}
	
	@RequestMapping("bindingMobile")
	@Action(description = "绑定手机")
	public ModelAndView bindingMobile(HttpServletRequest request) throws Exception {
		//ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		ModelAndView mv = new ModelAndView("/security/bindingMobile.jsp");
		return mv.addObject("user", user);
	} 
	
	@RequestMapping("bindingMobileSuccess")
	@Action(description = "绑定手机号成功")
	public ModelAndView bindingMobileSuccess(HttpServletRequest request) throws Exception {
		String mobile = request.getParameter("mobile");
		//ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		user.setMobile(mobile);
		user.setIsMobailTrue("1");
		sysUserService.update(user);
		ModelAndView mv = new ModelAndView("/security/bindingMobileSuccess.jsp");
		return mv;
	} 
	
	@RequestMapping("changeEmail")
	@Action(description = "更换邮箱")
	public ModelAndView changeEmail(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/changeEmail.jsp");
//		ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		return mv.addObject("user", user);
	} 
	
	@RequestMapping("changeEmailStep2")
	@Action(description = "更换邮箱")
	public ModelAndView changeEmailStep2(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/changeEmailStep2.jsp");
		return mv;
	} 
	
	@RequestMapping("changeEmailStep3")
	@Action(description = "更换邮箱")
	public ModelAndView changeEmailStep3(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/changeEmailStep3.jsp");
		String email = request.getParameter("email");
		return mv.addObject("email", email);
	} 
	
	@RequestMapping("checkEmailVerifyCode")
	@Action(description = "校验邮箱验证码")
	@ResponseBody
	public String checkEmailVerifyCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String email = request.getParameter("email");
		String verifycode = request.getParameter("verifycode");
		response.setContentType("application/json;charset=UTF-8");
		String resultMessage = "校验邮箱验证码成功";
		if(!SmsCache.verify(email, verifycode)){
			resultMessage = "验证码有误";
		}
		return resultMessage;
	}
	
	@RequestMapping("updateEmail")
	@Action(description = "更新邮箱")
	@ResponseBody
	public String updateEmail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String email = request.getParameter("email");
		//ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		response.setContentType("application/json;charset=UTF-8");
		String resultMessage = "更换邮箱成功";
		user.setEmail(email);
		sysUserService.update(user);
		return resultMessage;
	}
	
	@RequestMapping("validatingEmail")
	@Action(description = "验证邮箱")
	public ModelAndView validatingEmail(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/validatingEmail.jsp");
		//ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		return mv.addObject("user", user);
	} 
	
	@RequestMapping("validatingEmailSuccess")
	@Action(description = "验证邮箱成功")
	public ModelAndView validatingEmailSuccess(HttpServletRequest request) throws Exception {
		String email = request.getParameter("email");
//		ISysUser user = (ISysUser)(request.getSession().getAttribute("user"));
		ISysUser user = ContextUtil.getCurrentUser();
		user.setEmail(email);
		user.setIsEmailTrue("1");
		sysUserService.update(user);
		ModelAndView mv = new ModelAndView("/security/validatingEmailSuccess.jsp");
		return mv;
	} 
	
	@RequestMapping("updatePwd")
	@Action(description = "修改密码")
	public ModelAndView updatePwd(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/security/updatePwd.jsp");
		ISysUser user = ContextUtil.getCurrentUser();
		return mv.addObject("user", user);
	   /* if(user!=null){
		  mv.addObject("user", user);}
	    else{
	    	mv.addObject("user", sysUserService.getById((Long)iCache.getByKey("userId")));
	    }
		return mv;*/
	} 
}
