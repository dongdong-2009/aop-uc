package com.casic.security.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.security.model.SecretKeyBean;
import com.casic.security.service.SecretKeyService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.service.system.SubSystemUtil;

@Controller
@RequestMapping("/secretKey")
public class SecretKeyController extends BaseController{
	
	@Resource
	private SecretKeyService secretKeyService;
	
	/**
	 * 取得秘钥管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/secretKey/secretKeyList.jsp");
		List<SecretKeyBean> list=secretKeyService.getAll(new QueryFilter(request,"SecretKeyBeanItem"));
		mv.addObject("secretKeyBeanList",list);
		return mv;
	}
	
	/**
	 * 为系统添加秘钥
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		ModelAndView mv = new ModelAndView("/secretKey/secretKeyEdit.jsp");
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		SecretKeyBean secretKeyBean=null;
		if(id!=0){
			secretKeyBean= secretKeyService.getById(id);
			
		}else{
			secretKeyBean=new SecretKeyBean();
		}
		return mv.addObject("secretKeyBean",secretKeyBean).addObject("returnUrl", returnUrl);
	}
	
	
	@RequestMapping("createSecretKey")
	@ResponseBody
	public String createSecretKey(HttpServletRequest request) throws Exception
	{
		String secretKey ="";
		String secretKeyStr = UUID.randomUUID().toString(); 
        //去掉“-”符号 
		secretKey = secretKeyStr.substring(0,8)+
				 secretKeyStr.substring(9,13)+
				 secretKeyStr.substring(14,18)+
				 secretKeyStr.substring(19,23)+
				 secretKeyStr.substring(24); 
		return secretKey;
	}
	
	/**
	 * 删除秘钥
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			secretKeyService.delByIds(lAryId);
			
			message=new ResultMessage(ResultMessage.Success,"删除秘钥成功");
		}catch (Exception e) {
			message=new ResultMessage(ResultMessage.Fail,"删除秘钥失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

}
