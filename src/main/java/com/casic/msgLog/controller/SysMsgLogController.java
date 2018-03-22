package com.casic.msgLog.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.casic.msgLog.model.SysMsgLog;
import com.casic.msgLog.service.SysMsgLogService;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:sys_msg_log 控制器类
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-27 18:08:38
 *</pre>
 */
@Controller
@RequestMapping("/message/sysmsglog/sysmsglog/")
public class SysMsgLogController extends BaseController
{
	@Resource
	private SysMsgLogService sysmsglogService;
	
	
	/**
	 * 添加或更新sys_msg_log。
	 * @param request
	 * @param response
	 * @param sysmsglog 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新sys_msg_log")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		SysMsgLog sysmsglog=getFormObject(request);
		try{
			if(sysmsglog.getId()==null||sysmsglog.getId()==0){
				sysmsglog.setId(UniqueIdUtil.genId());
				sysmsglogService.add(sysmsglog);
				resultMsg=getText("record.added","sys_msg_log");
			}else{
			    sysmsglogService.update(sysmsglog);
				resultMsg=getText("record.updated","sys_msg_log");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 SysMsgLog 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected SysMsgLog getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		SysMsgLog sysmsglog = (SysMsgLog)JSONObject.toBean(obj, SysMsgLog.class);
		
		return sysmsglog;
    }
	
	/**
	 * 取得sys_msg_log分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看sys_msg_log分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysMsgLog> list=sysmsglogService.getAll(new QueryFilter(request,"sysmsglogItem"));
		ModelAndView mv=this.getAutoView().addObject("sysmsglogList",list);
		
		return mv;
	}
	
	/**
	 * 删除sys_msg_log
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除sys_msg_log")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			sysmsglogService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除sys_msg_log成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑sys_msg_log
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑sys_msg_log")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		SysMsgLog sysmsglog=sysmsglogService.getById(id);
		
		return getAutoView().addObject("sysmsglog",sysmsglog).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得sys_msg_log明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看sys_msg_log明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		SysMsgLog sysmsglog = sysmsglogService.getById(id);	
		return getAutoView().addObject("sysmsglog", sysmsglog);
	}
	
}
