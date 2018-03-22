package com.casic.xiaon.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.appleframe.utils.web.RequestUtils;
import com.casic.xiaon.model.SysOrgXiaonGroup;
import com.casic.xiaon.service.SysOrgXiaonGroupService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


	/**
	 * 对象功能:sys_org_xiaon_pwd Model对象
	 * 开发公司:航天科工集团
	 * 开发人员:LZY
	 * 创建时间:2016-07-27
	 */
	@Controller
	@RequestMapping("/b2b/xiaon/sysOrgXiaonGroup/")
	public class SysOrgXiaonGroupController extends BaseController{
		
		@Resource
		private SysOrgXiaonGroupService  sysOrgXiaonGroupService;
		
		/**
		* 我的客服   列表  sys_org_xiaon
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		@RequestMapping("xiaonGroupList")
		public ModelAndView xiaonList(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String name = RequestUtil.getString(request, "name");
			ModelAndView mav = new ModelAndView("/b2b/xiaon/sysOrgXiaonGroupList.jsp");
			QueryFilter queryFilter= new QueryFilter(request,"sysOrgXiaoItem");
			name="%"+name+"%";	
			queryFilter.getFilters().put("name", name);
			List<SysOrgXiaonGroup> groupList = sysOrgXiaonGroupService.getAll(queryFilter);
			mav.addObject("xiaonGroup",groupList);
			return mav;
		}
		
		
		/**
		* 我的客服   列表  sys_org_xiaon
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		@ResponseBody
		@RequestMapping("ajaxList")
		public List<SysOrgXiaonGroup> ajaxList(HttpServletRequest request,HttpServletResponse response) throws Exception{
			QueryFilter queryFilter= new QueryFilter(request,"sysOrgXiaoItem");
			queryFilter.addFilter("type", 0);
			List<SysOrgXiaonGroup> groupList = sysOrgXiaonGroupService.getAll(queryFilter);
			return groupList;
		}
		
		/**
		 *  转到Add页面
		 * @param request
		 * @return
		 * @throws Exception
		 * */ 
		@RequestMapping("edit")
		public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
			ModelAndView mv = new ModelAndView("/b2b/xiaon/sysOrgXiaonGroupAdd.jsp");
			long id = RequestUtil.getLong(request,"id");
			if(!"".equals(id)){
				SysOrgXiaonGroup sysOrgXiaon = sysOrgXiaonGroupService.getById(Long.valueOf(id));
				return mv.addObject("sysOrgXiaon", sysOrgXiaon);
			}
			return mv;
		}
		
		/**
		 * 删除
		 * @param request
		 * @return
		 * @throws Exception
		 * */ 
		@RequestMapping("del")
		public void del(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String preUrl= RequestUtil.getPrePage(request);
			ResultMessage message=null;
			try{
				Long[] id =RequestUtil.getLongAryByStr(request, "id");
				sysOrgXiaonGroupService.delByIds(id);
				message=new ResultMessage(ResultMessage.Success,"删除zone_brand及其从表成功!");
			}catch(Exception ex){
				message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
			}
			addMessage(message, request);
			response.sendRedirect(preUrl);
			
		}
		
		/**
		 *  取得sys_org_xiaonIM   实体
		 * @param request
		 * @return
		 * @throws Exception
		 * */
		public SysOrgXiaonGroup getFormObject(HttpServletRequest request) throws Exception{
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] {"yyyy-MM-dd"})));
			String json = RequestUtils.getString(request, "json");
			JSONObject obj = JSONObject.fromObject(json);
			SysOrgXiaonGroup SysOrgXiaon = (SysOrgXiaonGroup)JSONObject.toBean(obj,SysOrgXiaonGroup.class);
			return SysOrgXiaon;
		}
		
		/**
		 * @description add  and  update
		 * @param id
		 * @param type
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("add")
		public void save(HttpServletRequest request,HttpServletResponse response) throws Exception {
			String resultMsg = null;
			SysOrgXiaonGroup sysOrgXiaon = null;
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
			String id = request.getParameter("id");	
			if(id!= null && !"".equals(id)){
				sysOrgXiaon = sysOrgXiaonGroupService.getById(Long.valueOf(id));
			}
				try {
					if(sysOrgXiaon == null){
						SysOrgXiaonGroup sysOrgXiao = new SysOrgXiaonGroup();
						sysOrgXiao.setId(UniqueIdUtil.genId());
						sysOrgXiao.setName(request.getParameter("name"));
						sysOrgXiao.setSettingId(request.getParameter("settingId"));
						sysOrgXiao.setType(0);
						sysOrgXiao.setSorting(RequestUtil.getInt(request, "sorting"));
						sysOrgXiao.setAddTime(time.parse(time.format(new Date())));
							System.out.println(sysOrgXiao);
							sysOrgXiaonGroupService.add(sysOrgXiao);
							resultMsg=getText("record.added","sys_org_xiaon_group");
						
					}else{
						SysOrgXiaonGroup sysOrgXiao = sysOrgXiaonGroupService.getById(Long.valueOf(id));
						sysOrgXiao.setName(request.getParameter("name"));
						sysOrgXiao.setSettingId(request.getParameter("settingId"));
						if(sysOrgXiao.getType()==0){
							sysOrgXiao.setType(1);
						}else{
							sysOrgXiao.setType(0);
						}
						sysOrgXiao.setSorting(RequestUtil.getInt(request, "sorting"));
						sysOrgXiaonGroupService.getUpdate(sysOrgXiao);
						System.out.println(sysOrgXiao);
						resultMsg=getText("record.updated","sys_org_xiaon_group");

					}
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}catch(Exception e){
				writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
			}
		}
	}
