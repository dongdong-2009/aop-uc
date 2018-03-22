package com.casic.console.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;

@Controller
@RequestMapping("/resource")
public class ResourcesManagerController extends BaseController {

	@Resource
	private SubSystemService subSystemService;
	@Resource
	private ResourcesService resourcesService;

	@RequestMapping("index")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SubSystem> subSystemList = this.subSystemService.getByUser(ContextUtil.getCurrentUser());
		SubSystem currentSystem = null;
		if (subSystemList != null && !subSystemList.isEmpty()) {
			if (subSystemList.size() > 0)
				currentSystem = subSystemList.get(0);
		}

		List<Resources> resourcesList = this.resourcesService.getSysMenuAndResourceName(currentSystem,
				ContextUtil.getCurrentUser(), "资源管理");
		ResourcesService.addIconCtxPath(resourcesList, request.getContextPath());
		ModelAndView mv = new ModelAndView("/resource/resourceIndex.jsp");
		Resources resources = null;
		if (resourcesList != null && resourcesList.size() > 0) {
			resources = resourcesList.get(0);
		}
		return mv.addObject("resource", resources);
	}

	@RequestMapping("roleManager")
	public ModelAndView roleManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SubSystem> subSystemList = this.subSystemService.getByUser(ContextUtil.getCurrentUser());
		SubSystem currentSystem = null;
		if (subSystemList != null && !subSystemList.isEmpty()) {
			if (subSystemList.size() > 0)
				currentSystem = subSystemList.get(0);
		}

		List<Resources> resourcesList = this.resourcesService.getSysMenuAndResourceName(currentSystem,
				ContextUtil.getCurrentUser(), "角色管理");
		ResourcesService.addIconCtxPath(resourcesList, request.getContextPath());
		ModelAndView mv = new ModelAndView("/resource/roleManager.jsp");
		Resources resources = null;
		if (resourcesList != null && resourcesList.size() > 0) {
			resources = resourcesList.get(0);
		}
		return mv.addObject("resource", resources);
	}

}
