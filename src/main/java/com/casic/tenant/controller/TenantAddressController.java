package com.casic.tenant.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.tenant.model.TenantAddress;
import com.casic.tenant.service.TenantAddressService;
import com.casic.tenant.service.TenantInfoService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
/**
 * 企业地址的控制器
 * @author ypchenga
 *
 */
@Controller
@RequestMapping("/tenantAddress")
public class TenantAddressController extends BaseController {
	
	@Resource
	private TenantAddressService tenantAddressService;
	/**
	 * 添加收发货地址信息
	 * @param tenantAddress
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加收发货地址信息")
	@ResponseBody
	public ResultMessage save(TenantAddress tenantAddress,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultMessage resultMsg = null;
		String Msg=null;
		try{
			if(tenantAddress.getId()==null||tenantAddress.getId()==0){
				tenantAddress.setId(UniqueIdUtil.genId());
				ISysUser user = ContextUtil.getCurrentUser();
				tenantAddress.setUserId(user.getUserId());
				tenantAddressService.addTenantAddress(tenantAddress);
				Msg=JSONObject.fromObject(tenantAddress).toString();
			}else{
				tenantAddressService.updateTenantAddress(tenantAddress);
			    Msg=JSONObject.fromObject(tenantAddress).toString();
			}
			 if(tenantAddress.getIsDefault()!=null&&tenantAddress.getIsDefault().equals("1")){
				 tenantAddressService.updateTenantAddressUnisDefault(tenantAddress.getId(), tenantAddress.getUserId(),tenantAddress.getIsReceviced());
			 }
			resultMsg=new ResultMessage(ResultMessage.Success, Msg);
		}catch(Exception e){
			e.printStackTrace();
			resultMsg=new ResultMessage(ResultMessage.Fail, Msg);
		}
		return resultMsg;
	}
	
 /**
 * 查询收发货的地址信息
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@RequestMapping("getTenantAddressByType")
	@Action(description="查询收发货地址信息")
	@ResponseBody
	public ResultMessage getTenantAddressByType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultMessage resultMsg = null;
		String Msg=null;
		String isReceviced=RequestUtil.getString(request, "isReceviced");
		ISysUser user = ContextUtil.getCurrentUser();
		try{
			List<TenantAddress> orderAddressInfo = tenantAddressService.getTenantAddressByType(user.getUserId(),isReceviced);
			 Msg=JSONArray.fromObject(orderAddressInfo).toString();
			resultMsg=new ResultMessage(ResultMessage.Success, Msg);
		}catch(Exception e){
			e.printStackTrace();
			resultMsg=new ResultMessage(ResultMessage.Fail, Msg);
		}
		return resultMsg;
	}
	@RequestMapping("updateIsDefault")
	@Action(description="更新默认地址信息")
	@ResponseBody
	public ResultMessage updateIsDefault(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultMessage resultMsg = null;
		String Msg=null;	
		Long id = RequestUtil.getLong(request, "id");
		String isReceviced = RequestUtil.getString(request, "isReceviced");
		ISysUser user = ContextUtil.getCurrentUser();
		try{
			tenantAddressService.updateTenantAddressUnisDefault(id,user.getUserId(),isReceviced);
			resultMsg=new ResultMessage(ResultMessage.Success, "更新成功");
		}catch(Exception e){
			e.printStackTrace();
			resultMsg=new ResultMessage(ResultMessage.Fail, "更新失败");
		}
		return resultMsg;
	}
	@RequestMapping("delAddress")
	@Action(description="更新默认地址信息")
	@ResponseBody
	public ResultMessage delAddress(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultMessage resultMsg = null;
		String Msg=null;	
		Long id = RequestUtil.getLong(request, "id");
		ISysUser user = ContextUtil.getCurrentUser();
		try{
			tenantAddressService.delAddress(id,user.getUserId());
			resultMsg=new ResultMessage(ResultMessage.Success, "更新成功");
		}catch(Exception e){
			e.printStackTrace();
			resultMsg=new ResultMessage(ResultMessage.Fail, "更新失败");
		}
		return resultMsg;
	}
	@RequestMapping("editAddress")
	@Action(description="更改地址信息")
	public ModelAndView editAddress(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view=new ModelAndView("/tenant/editAddress.jsp");
		Long id = RequestUtil.getLong(request, "id");
		ISysUser user = ContextUtil.getCurrentUser();
		try{
			TenantAddress address=tenantAddressService.getAddressById(id,user.getUserId());
		    view.addObject("address", address);
		}catch(Exception e){
			e.printStackTrace();
		}
		return view;
	}
	@RequestMapping("showAddress")
	@Action(description="更改地址信息")
	public ModelAndView showAddress(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view=new ModelAndView("/tenant/showAddress.jsp");
		Long id = RequestUtil.getLong(request, "id");
		ISysUser user = ContextUtil.getCurrentUser();
		try{
			TenantAddress address=tenantAddressService.getAddressById(id,user.getUserId());
			view.addObject("address", address);
		}catch(Exception e){
			e.printStackTrace();
		}
		return view;
	}
	

}
