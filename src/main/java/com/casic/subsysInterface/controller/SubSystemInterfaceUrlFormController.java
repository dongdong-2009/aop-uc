package com.casic.subsysInterface.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.subsysInterface.model.InterfaceClassifyBean;
import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.subsysInterface.service.SubSystemInterfaceUrlService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SubSystemUtil;

/***
 * 子系统管理类，包括子系统注册、编辑、删除以及子系统接口注册
 * @author think
 * 2016 07 22
 */
@Controller
@RequestMapping("/interfaceUrl")
public class SubSystemInterfaceUrlFormController extends BaseFormController{
	
	@Resource
	private SubSystemInterfaceUrlService subSystemInterfaceUrlService;
	
	@RequestMapping("save")
	@Action(description = "添加或更新子系统")
	public void save(HttpServletRequest request, HttpServletResponse response, InterfaceUrlBean interfaceUrlBean, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("interfaceUrlBean", interfaceUrlBean, bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		try {
			if (interfaceUrlBean.getId()==null||interfaceUrlBean.getId() == 0L) {
				long id = UniqueIdUtil.genId();
				interfaceUrlBean.setId(id);
				//ISysUser currentUser = ContextUtil.getCurrentUser();
				interfaceUrlBean.setCreateTime(new Date());
				subSystemInterfaceUrlService.add(interfaceUrlBean);
				resultMsg = getText("添加成功", "接口地址");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);

			} else {
				interfaceUrlBean.setCreateTime(new Date());
				subSystemInterfaceUrlService.update(interfaceUrlBean);
				resultMsg = getText("更新成功", "接口地址");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
			
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"操作接口地址失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	
}
