package com.casic.subsysManager.controller;

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
@RequestMapping("/subSystem")
public class SubSystemManagerFormController extends BaseFormController {
	@Resource
	private SubSystemService subSystemService;

	@RequestMapping("save")
	@Action(description = "添加或更新子系统")
	public void save(HttpServletRequest request, HttpServletResponse response, SubSystem subSystem, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("subSystem", subSystem, bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String logo = subSystem.getLogo();
		if (StringUtils.startsWith(logo, request.getContextPath()))
			logo = logo.replaceFirst(request.getContextPath(), "");
		String resultMsg = null;
		try {
			if (subSystem.getSystemId() == 0L) {
				// 判断别名是否存在。
				int rtn = subSystemService.isAliasExist(subSystem.getAlias());
				if (rtn > 0) {
					writeResultMessage(response.getWriter(), "输入的系统别名系统中已存在!", ResultMessage.Fail);
					return;
				}

				long systemId = UniqueIdUtil.genId();
				subSystem.setSystemId(systemId);
				ISysUser currentUser = ContextUtil.getCurrentUser();
				subSystem.setCreator(currentUser.getUsername());
				subSystem.setCreateBy(currentUser.getUserId());
				subSystem.setCreatetime(new Date());

				subSystem.setLogo(logo);
				subSystemService.add(subSystem);
				resultMsg = getText("添加成功", "子系统管理");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);

			} else {
				int rtn = subSystemService.isAliasExistForUpd(subSystem.getAlias(), subSystem.getSystemId());
				if (rtn > 0) {
					writeResultMessage(response.getWriter(), "输入的系统别名系统中已存在!", ResultMessage.Fail);
					return;
				}
				subSystem.setLogo(logo);
				subSystemService.update(subSystem);
				//将当前系统
				SubSystemUtil.removeSystem();

				resultMsg = getText("更新成功", "子系统管理");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
			
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"操作子系统失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	protected SubSystem getFormObject(@RequestParam("systemId") Long systemId, Model model) throws Exception {
		logger.debug("enter subSystem getFormObject here....");
		SubSystem subSystem = null;
		if (systemId > 0) {
			subSystem = subSystemService.getById(systemId);
		} else {
			subSystem = new SubSystem();
		}
		return subSystem;
	}

}
