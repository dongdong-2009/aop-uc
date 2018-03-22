package com.casic.subsysInterface.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.casic.subsysInterface.model.InterfaceClassifyBean;
import com.casic.subsysInterface.service.SubSystemInterfaceService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;

/**
 * 对象功能:子系统管理 控制器类 开发公司:北京航天软件有限公司 开发人员:csx 创建时间:2011-11-21 12:22:06
 */
@Controller
@RequestMapping("/interface")
public class SubSystemInterfaceFormController extends BaseFormController {
	@Resource
	private SubSystemInterfaceService subSystemInterfaceService;

	@RequestMapping("save")
	@Action(description = "添加或更新子系统")
	public void save(HttpServletRequest request, HttpServletResponse response, InterfaceClassifyBean interfaceClassifyBean, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("interfaceClassifyBean", interfaceClassifyBean, bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		try {
			if (interfaceClassifyBean.getId()==null||interfaceClassifyBean.getId() == 0L) {
				long id = UniqueIdUtil.genId();
				interfaceClassifyBean.setId(id);
				//ISysUser currentUser = ContextUtil.getCurrentUser();
				interfaceClassifyBean.setCreateTime(new Date());
				subSystemInterfaceService.add(interfaceClassifyBean);
				resultMsg = getText("添加成功", "接口分类");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);

			} else {
				interfaceClassifyBean.setCreateTime(new Date());
				subSystemInterfaceService.update(interfaceClassifyBean);
				resultMsg = getText("更新成功", "接口分类");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
			
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"操作接口分类失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

}
