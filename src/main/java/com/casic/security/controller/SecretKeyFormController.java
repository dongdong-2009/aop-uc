package com.casic.security.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.security.model.SecretKeyBean;
import com.casic.security.service.SecretKeyService;
import com.casic.subsysInterface.model.InterfaceClassifyBean;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrg;

@Controller
@RequestMapping("/secretKey")
public class SecretKeyFormController extends BaseFormController{
	
	@Resource
	private SecretKeyService secretKeyService;
	
	
	
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, SecretKeyBean secretKeyBean, BindingResult bindResult) throws Exception {
		
		ISysUser user = ContextUtil.getCurrentUser();
		ResultMessage resultMessage = validForm("secretKeyBean", secretKeyBean, bindResult, request);
		if (resultMessage.getResult() == ResultMessage.Fail) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		if (secretKeyBean.getId()== null) {
			Long id=UniqueIdUtil.genId();
			secretKeyBean.setId(id);
			secretKeyBean.setCreateById(user.getUserId()+"");
			secretKeyBean.setCreateByName(user.getFullname());
			secretKeyBean.setDate(new Date());
			
			secretKeyService.add(secretKeyBean);
			
			writeResultMessage(response.getWriter(), "秘钥保存成功", ResultMessage.Success);
		} else {
			secretKeyBean.setDate(new Date());
			secretKeyService.update(secretKeyBean);
			writeResultMessage(response.getWriter(), "秘钥更新成功", ResultMessage.Success);
		}
		
	}
	
}
