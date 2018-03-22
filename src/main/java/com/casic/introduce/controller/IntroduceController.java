package com.casic.introduce.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.TenantInfoService;
import com.casic.util.HtmlUtil;
import com.casic.util.HttpClientUtil;
import com.casic.util.SecreptUtil;
import com.casic.util.StringUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;

@Controller
@RequestMapping("")
public class IntroduceController extends BaseController{
	@Resource
	private TenantInfoService tenantInfoService;
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user = ContextUtil.getCurrentUser();
		TenantInfo info=null;
		if(user!=null)
			info=  tenantInfoService.getById((user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId());
		ModelAndView mv = new ModelAndView("/aopUcIndex.jsp");
		return mv.addObject("info", info);
	}
	
	@RequestMapping("/joinStandard")
	public ModelAndView joinStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/joinStandard.jsp");
		return mv;
	}
	/**
	 * 用户中心api
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ucAPI")
	public ModelAndView ucAPI(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI.jsp");
		return mv;
	}
	
	@RequestMapping("/ucAPIIntroduction1")
	public ModelAndView ucAPIIntroduction1(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction1.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction2")
	public ModelAndView ucAPIIntroduction2(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction2.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction3")
	public ModelAndView ucAPIIntroduction3(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction3.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction4")
	public ModelAndView ucAPIIntroduction4(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction4.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction5")
	public ModelAndView ucAPIIntroduction5(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction5.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction6")
	public ModelAndView ucAPIIntroduction6(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction6.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction7")
	public ModelAndView ucAPIIntroduction7(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction7.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction8")
	public ModelAndView ucAPIIntroduction8(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction8.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction9")
	public ModelAndView ucAPIIntroduction9(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction9.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction10")
	public ModelAndView ucAPIIntroduction10(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction10.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction11")
	public ModelAndView ucAPIIntroduction11(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction11.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro1")
	public ModelAndView ucAPIIntro1(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro1.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro2")
	public ModelAndView ucAPIIntro2(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro2.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro3")
	public ModelAndView ucAPIIntro3(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro3.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro4")
	public ModelAndView ucAPIIntro4(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro4.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro5")
	public ModelAndView ucAPIIntro5(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro5.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro6")
	public ModelAndView ucAPIIntro6(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro6.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro7")
	public ModelAndView ucAPIIntro7(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro7.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro8")
	public ModelAndView ucAPIIntro8(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro8.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro9")
	public ModelAndView ucAPIIntro9(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro9.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntro10")
	public ModelAndView ucAPIIntro10(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntro10.jsp");
		return mv;
	}
	@RequestMapping("/ucAPIIntroduction12")
	public ModelAndView ucAPIIntroduction12(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/ucAPIIntroduction12.jsp");
		return mv;
	}
	@RequestMapping("/errorCodeInstruction")
	public ModelAndView errorCodeInstruction(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/ucAPI/errorCodeInstruction.jsp");
		return mv;
	}

	/**
	 * 用户指南
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userGuide")
	public ModelAndView userGuide(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/introduce/userGuide.jsp");
		return mv;
	}
	
	@RequestMapping("/userStandard")
	public ModelAndView userStand(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView("/standard/userStandard.jsp");
		return mv.addObject("type", type);
	}
	
	@RequestMapping("/userStandardAdd")
	public ModelAndView userStandardAdd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/userStandardAdd.jsp");
		return mv;
	}
	
	@RequestMapping("/userStandardUpdate")
	public ModelAndView userStandardUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/userStandardUpdate.jsp");
		return mv;
	}
	
	@RequestMapping("/userStandardDelete")
	public ModelAndView userStandardDelete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/userStandardDelete.jsp");
		return mv;
	}
	
	@RequestMapping("/enterpriseStandard")
	public ModelAndView enterpriseStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView("/standard/enterpriseStandard.jsp");
		return mv.addObject("type", type);
	}
	
	@RequestMapping("/enterpriseStandardAdd")
	public ModelAndView enterpriseStandardAdd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/enterpriseStandardAdd.jsp");
		return mv;
	}
	
	@RequestMapping("/enterpriseStandardUpdate")
	public ModelAndView enterpriseStandardUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/enterpriseStandardUpdate.jsp");
		return mv;
	}
	
	@RequestMapping("/enterpriseStandardDelete")
	public ModelAndView enterpriseStandardDelete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/enterpriseStandardDelete.jsp");
		return mv;
	}
	
	@RequestMapping("/authenticationStandard")
	public ModelAndView authenticationStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView("/standard/authenticationStandard.jsp");
		return mv.addObject("type", type);
	}
	
	@RequestMapping("/authenticationStandardAdd")
	public ModelAndView authenticationStandardAdd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/authenticationStandardAdd.jsp");
		return mv;
	}
	
	@RequestMapping("/authenticationStandardUpdate")
	public ModelAndView authenticationStandardUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/authenticationStandardUpdate.jsp");
		return mv;
	}
	
	@RequestMapping("/authenticationStandardDelete")
	public ModelAndView authenticationStandardDelete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/authenticationStandardDelete.jsp");
		return mv;
	}
	
	@RequestMapping("/branchStandard")
	public ModelAndView branchStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView("/standard/branchStandard.jsp");
		return mv.addObject("type", type);
	}
	
	@RequestMapping("/branchStandardAdd")
	public ModelAndView branchStandardAdd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/branchStandardAdd.jsp");
		return mv;
	}
	
	@RequestMapping("/branchStandardUpdate")
	public ModelAndView branchStandardUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/branchStandardUpdate.jsp");
		return mv;
	}
	
	@RequestMapping("/branchStandardDelete")
	public ModelAndView branchStandardDelete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/branchStandardDelete.jsp");
		return mv;
	}
	
	@RequestMapping("/goodsStandard")
	public ModelAndView goodsStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView("/standard/goodsStandard.jsp");
		return mv.addObject("type", type);
	}
	
	@RequestMapping("/goodsStandardAdd")
	public ModelAndView goodsStandardAdd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/goodsStandardAdd.jsp");
		return mv;
	}
	
	@RequestMapping("/goodsStandardUpdate")
	public ModelAndView goodsStandardUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/goodsStandardUpdate.jsp");
		return mv;
	}
	
	@RequestMapping("/goodsStandardDelete")
	public ModelAndView goodsStandardDelete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/goodsStandardDelete.jsp");
		return mv;
	}
	
	@RequestMapping("/passwordStandard")
	public ModelAndView passwordStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView("/standard/passwordStandard.jsp");
		return mv.addObject("type", type);
	}
	
	@RequestMapping("/passwordStandardAdd")
	public ModelAndView passwordStandardAdd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/passwordStandardAdd.jsp");
		return mv;
	}
	
	@RequestMapping("/passwordStandardUpdate")
	public ModelAndView passwordStandardUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/passwordStandardUpdate.jsp");
		return mv;
	}
	
	@RequestMapping("/passwordStandardDelete")
	public ModelAndView passwordStandardDelete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/passwordStandardDelete.jsp");
		return mv;
	}
	@RequestMapping("/openIdStandard")
	public ModelAndView openIdStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView("/standard/openIdStandard.jsp");
		return mv.addObject("type", type);
	}
	
	@RequestMapping("/openIdStandardAdd")
	public ModelAndView openIdStandardAdd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/openIdStandardAdd.jsp");
		return mv;
	}
	
	@RequestMapping("/openIdStandardUpdate")
	public ModelAndView openIdStandardUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/openIdStandardUpdate.jsp");
		return mv;
	}
	
	@RequestMapping("/openIdStandardDelete")
	public ModelAndView openIdStandardDelete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/standard/openIdStandardDelete.jsp");
		return mv;
	}
	
	
	/**
	 * 常见问题
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/commonProblems")
	public ModelAndView commonProblems(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/introduce/commonProblem.jsp");
		return mv;
	}
	
	/**
	 * 数据加密
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/encryptData")
	@ResponseBody
	public String encryptData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String unencryptedData = request.getParameter("unencryptedData");
		String secretKey = request.getParameter("secretKey");
		String encryptData =null;
		if(secretKey!=null){
			SecreptUtil des = new SecreptUtil(secretKey);
			if(unencryptedData!=null){
				encryptData = des.encrypt(unencryptedData);
			}
		}
		return encryptData;
	}
	/**
	 * 错误码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/errorCode")
	public ModelAndView errorCod(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/introduce/errorCode.jsp");
		return mv;
	}
	
	
	
	/**
	 * 用户中心社区建设
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/community")
	public ModelAndView community(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user = ContextUtil.getCurrentUser();
		ModelAndView mv = new ModelAndView("/community/community.jsp");
		return mv.addObject("user", user);
	}
	@RequestMapping("/loadData")
	@ResponseBody
	public ResultMessage loadData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ResultMessage resultMessage=null;
		String Msg="";
	   String url=RequestUtil.getString(request, "url");
	   url=URLDecoder.decode(url,"UTF-8");
       boolean method=true;
	   StringBuffer buffer=new StringBuffer(url);
	   //请求方式
	   String seltype=RequestUtil.getString(request, "seltype");
	   if(!StringUtil.isEmpty(seltype)){
		   if(seltype.equals("get")){
			   method=false;
		    }
		   }
	   //请求参数
	   String parms=RequestUtil.getString(request, "parms");
	   parms=URLDecoder.decode(parms,"UTF-8");
	   parms=HtmlUtil.Html2Text(parms);
	   parms=HtmlUtil.replaceBlank(parms);
	   //请求的编码方式
	   String code=RequestUtil.getString(request, "code");
	   Map<String, Object> params = new HashMap<String, Object>();
	  
	   String charsets=HttpClientUtil.CHARSET_UTF8;
	   if(url.contains("?")){
		   //表明有拼接的参数列表
		   //表明有参数拼接
		   if(url.lastIndexOf('?')<url.length()-1){
			   String param=url.substring(url.lastIndexOf('?')+1);
			   param=HtmlUtil.Html2Text(param);
			   param=HtmlUtil.replaceBlank(param);
			   String paras[]=  param.split("&");
			   if(paras!=null&& paras.length>0){
				   for (String param1 : paras) {
					   
				    if(param1.contains("=")){
					   String key=param1.substring(0, param1.lastIndexOf("="));
					   String value=param1.substring(param1.lastIndexOf("=")+1);
					   params.put(key, value);
				    }else{
				    	Msg="你传的参数类型有误";
				    }
				}
			   }
		   }
		   if(method){
		   url=url.substring(0, url.lastIndexOf('?'));
		   }else{
			   buffer.append("&"+parms);
			   url=buffer.toString();
		   }
	   }
	   String []parameters=  parms.split("&");
	   if(parameters!=null&& parameters.length>0){
		   for (String param1 : parameters) {
			   
		    if(param1.contains("=")){
			   String key=param1.substring(0, param1.lastIndexOf("="));
			   String value=param1.substring(param1.lastIndexOf("=")+1);
			   params.put(key, value);
		    }
		    else{
		    	Msg="你传的参数类型有误";
		    }
		}
		   
		   if(!StringUtil.isEmpty(code)){
			   if(code.equals("gbk")){
				   charsets=HttpClientUtil.CHARSET_GBK;
			   }
		   }
	}
	   String result=null;
	   Map<String, Object> dataMap=new HashMap<String, Object>();
	   try { 
			   if(method){
				   result=HttpClientUtil.JsonPostInvoke(url, params, charsets);
			   }else {
				   result=HttpClientUtil.JsonGetInvoke(url, charsets);
		     }
		dataMap.put("success",true);
		dataMap.put("message",HtmlUtil.Html2Text(result));
		resultMessage=new ResultMessage(ResultMessage.Success, JSONObject.fromObject(dataMap).toString());
	} catch (Exception e) {
		e.printStackTrace();
		
		dataMap.put("success",false);
		dataMap.put("message", "系统请求出错");
		resultMessage=new ResultMessage(ResultMessage.Fail, JSONObject.fromObject(dataMap).toString());
	}
	   
	   
	   return resultMessage;
	

 }
}
