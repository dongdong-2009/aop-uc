package com.casic.third.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.casic.util.CacheUtil;
import com.casic.util.OpenIdUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.impl.MemoryCache;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.platform.auth.ISysUser;



/***
 * 航天云网跳转到晨光
 * 
 * @author think 2016 10 31
 *
 */
@Controller
@RequestMapping("/")
public class ThirdLoginController extends BaseController {

	/**
	 * 航天云网跳转到用户中心，传递url然后用户中心设置缓存
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "cg/toLogin", method = RequestMethod.GET)
	@Action(description = "航天云网调用")
	public void toLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String url = request.getParameter("url");
		if (url != null && !"".equals(url)) {
			// 获得当前用户
			ISysUser sysUser = ContextUtil.getCurrentUser();
			String token = OpenIdUtil.getOpenId();
			CacheUtil.getUserCache().add(token, sysUser);
			System.out.println(token);
			if (sysUser != null) {
				response.sendRedirect(url + "?token=" + token);
			}
		}
	}
	
	/**
	 * 航天云网跳转到航天云宏先跳转到用户中心
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "htyh/toLogin", method = RequestMethod.GET)
	@Action(description = "航天云网调用")
	public void htyhToLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String url = request.getParameter("url");
		if (url != null && !"".equals(url)) {
			// 获得当前用户
			ISysUser sysUser = ContextUtil.getCurrentUser();
			String token = OpenIdUtil.getOpenId();
			CacheUtil.getUserCache().add(token, sysUser);
			System.out.println(token);
			if (sysUser != null) {
				response.sendRedirect("https://"+url + "?token=" + token+"&userID="+sysUser.getAccount());
			}
		}
	}

}
