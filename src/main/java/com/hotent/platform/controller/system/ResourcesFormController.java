package com.hotent.platform.controller.system;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.casic.msgLog.model.SysMsgLog;
import com.casic.msgLog.service.SysMsgLogService;
import com.casic.user.controller.JMSRunableThread;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SubSystemService;

import net.sf.json.JSONObject;

/**
 * 对象功能:子系统资源 控制器类 开发公司:北京航天软件有限公司 开发人员:csx 创建时间:2011-12-05 17:00:54
 */
@Controller
@RequestMapping({"/platform/system/resources/"})
public class ResourcesFormController extends BaseFormController {
	
	@Resource
	private SysMsgLogService sysMsgLogService;
	
	@Resource
	private ResourcesService resourcesService;

	@Resource
	private JmsTemplate jmsTemplate;
	
	@Resource
	private SubSystemService subSystemService;
	
	

	/**
	 * 添加或更新子系统资源。
	 * 
	 * @param request
	 * @param response
	 * @param resources
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新子系统资源")
	public void save(HttpServletRequest request, HttpServletResponse response,final Resources resources, BindingResult bindResult) throws Exception {
		PrintWriter out=response.getWriter();
		ResultMessage resultMessage = validForm("resources", resources,bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		
		String icon = resources.getIcon();

		icon = icon.replace(request.getContextPath(), "");
		resources.setIcon(icon);

		String defaultUrl = resources.getDefaultUrl();
		if (defaultUrl != null) {
			defaultUrl = defaultUrl.trim();
			if(defaultUrl.equals("")){
				defaultUrl=null;
			}
			resources.setDefaultUrl(defaultUrl);
		}

		final String[] aryName = request.getParameterValues("name");
		final String[] aryUrl = request.getParameterValues("url");

		if (resources.getResId() == null) {
			Integer rtn = resourcesService.isAliasExists(resources);
			if (rtn > 0) {
				writeResultMessage(response.getWriter(), "别名在系统中已存在!",ResultMessage.Fail);
				return;
			}
			try {
				long resId= resourcesService.addRes(resources, aryName, aryUrl);
				// List<SubSystem> all = subSystemService.getAll();
				final JSONObject json=new JSONObject();
				json.put("resources", resources);
				json.put("aryName", aryName);
				json.put("aryUrl", aryUrl);
				new JMSRunableThread("resources_add",json.toString());
				/*for (final SubSystem subSystem : all) {
					jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public MapMessage createMessage(Session session) throws JMSException {
							MapMessage mapMsg=session.createMapMessage();
						    mapMsg.setString("resources_add",json.toString());
							return mapMsg;
						}
					});
					SysMsgLog msgLog = new SysMsgLog();
					msgLog.setId(UniqueIdUtil.genId());
					msgLog.setOperation("资源添加");
					msgLog.setReceiverersonal(subSystem.getSystemId()+"");
					msgLog.setSendcontent(json.toString());
					sysMsgLogService.add(msgLog);
				}*/
				//删除缓存。
				SecurityUtil.removeCacheBySystemId( resources.getSystemId());
				String result="{result:1,resId:"+resId+",operate:'add'}";
				out.print(result);
			}
			catch (Exception e) {
				e.printStackTrace();
				writeResultMessage(response.getWriter(), "添加资源失败!",ResultMessage.Fail);
			}
		} 
		else {
			Integer rtn = resourcesService.isAliasExistsForUpd(resources);
			if (rtn > 0) {
				writeResultMessage(response.getWriter(), "别名在系统中已存在!",ResultMessage.Fail);
				return;
			}
			try {
				resourcesService.updRes(resources, aryName, aryUrl);
				final JSONObject json=new JSONObject();
				json.put("resources", resources);
				json.put("aryName", aryName);
				json.put("aryUrl", aryUrl);
				new JMSRunableThread("resources_update",json.toString());
				/*for (final SubSystem subSystem : all) {
					jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public MapMessage createMessage(Session session) throws JMSException {
							MapMessage mapMsg=session.createMapMessage();
						    mapMsg.setString("resources_update",json.toString());
							return mapMsg;
						}
					});
					SysMsgLog msgLog = new SysMsgLog();
					msgLog.setId(UniqueIdUtil.genId());
					msgLog.setOperation("资源修改");
					msgLog.setReceiverersonal(subSystem.getSystemId()+"");
					msgLog.setSendcontent(json.toString());
					sysMsgLogService.add(msgLog);
				}*/
				
				//更新子级所有是否隐藏
				if(resources.getIsHidden()==1){
					resourcesService.updateIsHidden(resources);
				}
				
				//删除缓存。
				SecurityUtil.removeCacheBySystemId( resources.getSystemId());
				
				String result="{result:1,operate:'edit'}";
				out.print(result);
			} catch (Exception e) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					resultMessage = new ResultMessage(ResultMessage.Fail,"更新资源失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(e);
					resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}

	}

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param resId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected Resources getFormObject(@RequestParam("resId") Long resId,
			Model model) throws Exception {
		logger.debug("enter Resources getFormObject here....");
		Resources resources = null;
		if (resId != null) {
			resources = resourcesService.getById(resId);
		} 
		else {
			resources = new Resources();
		}
		return resources;
	}

}
