package com.hotent.platform.controller.system;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.casic.msgLog.service.SysMsgLogService;
import com.casic.user.controller.JMSRunableThread;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.ResourceFolder;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.ResourcesUrl;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.ResourcesUrlService;
import com.hotent.platform.service.system.RoleResourcesService;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping({"/platform/system/resources/"})
public class ResourcesController
  extends BaseController
{
	
  static  Logger  log =  Logger.getLogger(ResourcesController.class.getName());
public static Object subsystemidJoiningQueue;

  @Resource
  private SysMsgLogService sysMsgLogService;
  @Resource
  private JmsTemplate jmsTemplate;
  @Resource
  private ResourcesUrlService resourcesUrlService;
  @Resource
  private ResourcesService resourcesService;
  @Resource
  private SubSystemService subSystemService;
  @Resource
  private RoleResourcesService roleResourcesService;
  
  @RequestMapping({"tree"})
  public ModelAndView tree(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
	  
	  
    ModelAndView mv = getAutoView();
    QueryFilter filter = new QueryFilter(request, "resourcesItem");
	long usersystemId = RequestUtil.getLong(request, "usersystemId", 0L);
	List<SubSystem> subSystemList=null;
	if (usersystemId != 0L) {
		filter.addFilter("systemId", usersystemId);
		subSystemList = this.subSystemService.getAll(filter);
	}else{
	    subSystemList = this.subSystemService.getAll();
	}
   
    Long currentSystemId = SubSystemUtil.getCurrentSystemId(request);
    mv.addObject("subSystemList", subSystemList).addObject("currentSystemId", currentSystemId);
    return mv;
  }
  
	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "resourcesItem");
		long usersystemId = RequestUtil.getLong(request, "usersystemId", 0L);
		List<Resources> list = null;
		if (usersystemId != 0L) {
			filter.addFilter("systemId", usersystemId);
			 list = this.resourcesService.getAll(filter);
		}else{
			 list = this.resourcesService.getAll(filter);
		}
		
		if ((list != null) && (list.size() > 0)) {
			for (Resources res : list) {
				res.setIcon(request.getContextPath() + res.getIcon());
			}
		}
		ModelAndView mv = getAutoView().addObject("resourcesList", list);
		return mv;
	}
  
  @RequestMapping({"del"})
  @Action(description="删除子系统资源")
  public void del(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ResultMessage resultMessage = new ResultMessage(1, "删除资源成功!");
    final Long resId = Long.valueOf(RequestUtil.getLong(request, "resId"));
    Resources res = (Resources)this.resourcesService.getById(resId);
    try
    {
      this.resourcesService.delById(resId);
      JSONObject json=new JSONObject();
     
		Map<String,Object> OperationMarked = new HashMap<String,Object>();
		OperationMarked.put("操作说明","增加：1,修改：2,删除：3");
		OperationMarked.put("operationSatus", 3);//操作标示
		json.put("OperationMarked", OperationMarked);//操作标示：1：增加,2：修改,删除3
		json.put("resId", resId);
		json.put("systemId", res.getSystemId());
        new JMSRunableThread("resources_delete",json.toString());
      /*List<SubSystem> all = subSystemService.getAll();
		for (final SubSystem subSystem : all) {
			jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
				public MapMessage createMessage(Session session) throws JMSException {
					MapMessage mapMsg=session.createMapMessage();
					JSONObject json=new JSONObject();
					json.put("resId", resId);
				    mapMsg.setString("resources_delete",json.toString());
					return mapMsg;
				}
			});
		}*/
		

      SecurityUtil.removeCacheBySystemId(res.getSystemId());
    }
    catch (Exception ex)
    {
      resultMessage = new ResultMessage(0, "删除资源失败!");
    }
    response.getWriter().print(resultMessage);
  }
  
  @RequestMapping({"edit"})
  @Action(description="编辑子系统资源")
  public ModelAndView edit(HttpServletRequest request)
    throws Exception
  {
    ModelAndView mv = getAutoView();
    long systemId = RequestUtil.getLong(request, "systemId", 0L);
    long parentId = RequestUtil.getLong(request, "parentId", 0L);
    long resId = RequestUtil.getLong(request, "resId", 0L);
    

    Resources resources = null;
    if (resId != 0L)
    {
      resources = (Resources)this.resourcesService.getById(Long.valueOf(resId));
      if ((resources != null) && (resources.getIsFolder() != null) && (resources.getIsFolder().equals(Resources.IS_FOLDER_N)))
      {
        List<ResourcesUrl> resourcesUrlList = this.resourcesUrlService.getByResId(resources.getResId().longValue());
        mv.addObject("resourcesUrlList", resourcesUrlList);
      }
    }
    else
    {
      Resources parent = this.resourcesService.getParentResourcesByParentId(systemId, parentId);
      resources = new Resources();
      
      resources.setIsFolder(Resources.IS_FOLDER_N);
      resources.setIsDisplayInMenu(Resources.IS_DISPLAY_IN_MENU_Y);
      resources.setIsOpen(Resources.IS_OPEN_Y);
      
      resources.setSystemId(Long.valueOf(systemId));
      resources.setParentId(parent.getResId());
      resources.setSn(Integer.valueOf(1));
    }
    resources.setIcon(request.getContextPath() + resources.getIcon());
    
    return mv.addObject("resources", resources);
  }
  
  @RequestMapping({"get"})
  public ModelAndView get(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    long id = RequestUtil.getLong(request, "resId");
    Resources resources = (Resources)this.resourcesService.getById(Long.valueOf(id));
    if (resources != null) {
      resources.setIcon(request.getContextPath() + resources.getIcon());
    }
    List<ResourcesUrl> resourcesUrlList = this.resourcesUrlService.getByResId(id);
    return getAutoView().addObject("resources", resources).addObject("resourcesUrlList", resourcesUrlList);
  }
  
  @RequestMapping({"getSystemTreeData"})
  @ResponseBody
  public List<Resources> getSystemTreeData(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    long systemId = RequestUtil.getLong(request, "systemId", 0L);
    List<Resources> list = this.resourcesService.getBySystemIdAndIsHidden(systemId);
    Resources parent = this.resourcesService.getParentResourcesByParentId(systemId, 0L);
    list.add(parent);
    
    ResourcesService.addIconCtxPath(list, request.getContextPath());
    return list;
  }
  
  @RequestMapping({"getChildrenResource"})
  @ResponseBody
  public List<Resources> getChildrenResource(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    long resId = RequestUtil.getLong(request, "resId", 0L);
    List<Resources> list = this.resourcesService.getByParentId(Long.valueOf(resId));
    for (Resources res : list) {
      res.getChildren().addAll(this.resourcesService.getByParentId(res.getParentId()));
    }
    return list;
  }
  
  @RequestMapping({"getSysRolResTreeChecked"})
  @ResponseBody
  public List<Resources> getSysRolResTreeChecked(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    long systemId = RequestUtil.getLong(request, "systemId", 0L);
    long roleId = RequestUtil.getLong(request, "roleId", 0L);
    
    List<Resources> resourcesList = this.resourcesService.getBySysRolResChecked(Long.valueOf(systemId), Long.valueOf(roleId));
    
    Resources parent = this.resourcesService.getParentResourcesByParentId(systemId, 0L);
    resourcesList.add(parent);
    
    ResourcesService.addIconCtxPath(resourcesList, request.getContextPath());
    return resourcesList;
  }
  
  @RequestMapping({"move"})
  @Action(description="转移子系统资源")
  public void move(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ResultMessage resultObj = null;
    PrintWriter out = response.getWriter();
    long targetId = RequestUtil.getLong(request, "targetId", 0L);
    long sourceId = RequestUtil.getLong(request, "sourceId");
    String moveType = RequestUtil.getString(request, "moveType", "inner");
    this.resourcesService.move(Long.valueOf(sourceId), Long.valueOf(targetId), moveType);
    resultObj = new ResultMessage(1, "转移子系统资源完成");
    out.print(resultObj);
  }
  
  @RequestMapping({"/icons"})
  public ModelAndView icons(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = getAutoView();
    int iconType = RequestUtil.getInt(request, "iconType");
    String path = RequestUtil.getString(request, "path");
    String iconTypeStr = "";
    if ((path != null) && (path != ""))
    {
      iconTypeStr = path;
    }
    else
    {
      iconTypeStr = "/styles/default/images/resicon";
      if (iconType == 1) {
        iconTypeStr = "/styles/default/images/logo";
      }
    }
    String iconPath = request.getSession().getServletContext().getRealPath(iconTypeStr);
    String[] iconList = getIconList(iconPath);
    mv.addObject("iconList", iconList);
    mv.addObject("iconPath", iconTypeStr);
    return mv;
  }
  
  @RequestMapping({"getFolderData"})
  @ResponseBody
  public List<ResourceFolder> getFolderData(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    List<ResourceFolder> list = new ArrayList();
    String rootPath = "/styles/default/images/resicon";
    ResourceFolder folder = new ResourceFolder();
    folder.setFolderId(Long.valueOf(0L));
    folder.setFolderName("全部");
    folder.setFolderPath(rootPath);
    list.add(folder);
    getFolderList(request, folder, list);
    return list;
  }
  
  public void getFolderList(HttpServletRequest request, ResourceFolder resFolder, List<ResourceFolder> list)
  {
    String path = resFolder.getFolderPath();
    String realPath = request.getSession().getServletContext().getRealPath(path);
    File file = new File(realPath);
    File[] files = file.listFiles();
    if ((files != null) && (files.length != 0)) {
      for (File f : files) {
        if (f.isDirectory())
        {
          ResourceFolder folder = new ResourceFolder();
          folder.setFolderId(Long.valueOf(UniqueIdUtil.genId()));
          folder.setFolderName(f.getName());
          folder.setFolderPath(path + "/" + f.getName());
          folder.setParentId(resFolder.getFolderId());
          list.add(folder);
          getFolderList(request, folder, list);
        }
      }
    }
  }
  
  @RequestMapping({"createFolder"})
  public void createFolder(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String iconPath = RequestUtil.getString(request, "path");
    String folderName = RequestUtil.getString(request, "folderName");
    String path = request.getSession().getServletContext().getRealPath(iconPath);
    try
    {
      FileUtil.createFolder(path, folderName);
      writeResultMessage(response.getWriter(), new ResultMessage(1, "新建文件夹成功！"));
    }
    catch (Exception e)
    {
      String str = MessageUtil.getMessage();
      if (StringUtil.isNotEmpty(str))
      {
        ResultMessage resultMessage = new ResultMessage(0, "新建文件夹失败:" + str);
        response.getWriter().print(resultMessage);
      }
      else
      {
        String message = ExceptionUtil.getExceptionMessage(e);
        ResultMessage resultMessage = new ResultMessage(0, message);
        response.getWriter().print(resultMessage);
      }
    }
  }
  
  @RequestMapping({"uploadIcon"})
  public void uploadIcon(MultipartHttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String iconPath = RequestUtil.getString(request, "path");
    Map<String, MultipartFile> files = request.getFileMap();
    Iterator<MultipartFile> it = files.values().iterator();
    if (it.hasNext())
    {
      MultipartFile f = (MultipartFile)it.next();
      String fileName = f.getOriginalFilename();
      String extName = FileUtil.getFileExt(fileName);
      if ((!extName.equals("gif")) && (!extName.equals("png")) && (!extName.equals("jpg")))
      {
        writeResultMessage(response.getWriter(), new ResultMessage(0, "上传的文件格式必须为图片类型"));
        return;
      }
      String filePath = request.getSession().getServletContext().getRealPath(iconPath + "/" + fileName);
      File file = new File(filePath);
      file.createNewFile();
      FileUtil.writeByte(filePath, f.getBytes());
      writeResultMessage(response.getWriter(), new ResultMessage(1, "上传的文件成功！"));
    }
  }
  
  private String[] getIconList(String iconPath)
  {
    File iconFolder = new File(iconPath);
    String[] fileNameList = iconFolder.list(new FilenameFilter()
    {
      public boolean accept(File dir, String name)
      {
        String ext = name.substring(name.lastIndexOf(".") + 1).toUpperCase();
        if ("PNG|JPG|JPEG|GIF".contains(ext)) {
          return true;
        }
        return false;
      }
    });
    return fileNameList;
  }
  
  @RequestMapping({"delFile"})
  public void delFile(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String folderPath = RequestUtil.getString(request, "path");
    String path = request.getSession().getServletContext().getRealPath(folderPath);
    try
    {
      FileUtil.deleteDir(new File(path));
      writeResultMessage(response.getWriter(), new ResultMessage(1, "删除文件夹成功！"));
    }
    catch (Exception e)
    {
      String str = MessageUtil.getMessage();
      if (StringUtil.isNotEmpty(str))
      {
        ResultMessage resultMessage = new ResultMessage(0, "删除文件夹失败:" + str);
        response.getWriter().print(resultMessage);
      }
      else
      {
        String message = ExceptionUtil.getExceptionMessage(e);
        ResultMessage resultMessage = new ResultMessage(0, message);
        response.getWriter().print(resultMessage);
      }
    }
  }
  
  @RequestMapping({"getExtendIcons"})
  @Action(description="从样式文件中获取扩展功能图标")
  public void getExtendIcons(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ResultMessage resultObj = null;
    PrintWriter out = response.getWriter();
    try
    {
      String fileName = FileUtil.getRootPath() + "/styles/default/css/form.css";
      String cssStr = FileUtil.readFile(fileName);
      int start = cssStr.indexOf("/*--extend start--*/");
      int end = cssStr.indexOf("/*--extend end--*/");
      String subStr = cssStr.substring(start, end);
      String icons = "";
      Pattern regex = Pattern.compile("a\\.extend\\.([\\w]+)\\s*\\{", 66);
      Matcher regexMatcher = regex.matcher(subStr);
      while (regexMatcher.find()) {
        icons = icons + regexMatcher.group(1) + ",";
      }
      resultObj = new ResultMessage(1, icons);
    }
    catch (Exception ex)
    {
      resultObj = new ResultMessage(0, ex.getMessage());
    }
    out.print(resultObj);
  }
  
  private String getNewIconsStr()
  {
    String iconPath = FileUtil.getRootPath() + "/styles/default/images/extendIcon".replace("/", File.separator);
    String[] icons = getIconList(iconPath);
    String returnStr = "/*--extend start--*/";
    for (String str : icons)
    {
      returnStr = returnStr + "\n";
      returnStr = returnStr + "a.extend." + str.substring(0, str.lastIndexOf("."));
      returnStr = returnStr + "{\n";
      returnStr = returnStr + "    background:url(../images/extendIcon/" + str + ") 0px -1px no-repeat;\n}\n";
    }
    return returnStr;
  }
  
  @RequestMapping({"refreshExtendCss"})
  @Action(description="刷新扩展功能样式文件")
  public void refreshExtendCss(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    PrintWriter out = response.getWriter();
    ResultMessage resultMessage = null;
    try
    {
      String fileName = FileUtil.getRootPath() + "/styles/default/css/form.css".replace("/", File.separator);
      String cssStr = FileUtil.readFile(fileName);
      String newSubStr = getNewIconsStr();
      int length = cssStr.length();
      int start = cssStr.indexOf("/*--extend start--*/");
      int end = cssStr.indexOf("/*--extend end--*/");
      String bottomStr = cssStr.substring(end, length);
      String headStr = cssStr.substring(0, start);
      String newStr = headStr + newSubStr + bottomStr;
      FileUtil.writeFile(fileName, newStr);
      resultMessage = new ResultMessage(1, "刷新成功");
    }
    catch (Exception ex)
    {
      String str = MessageUtil.getMessage();
      if (StringUtil.isNotEmpty(str))
      {
        resultMessage = new ResultMessage(0, "刷新扩展功能样式文件失败:" + str);
      }
      else
      {
        String message = ExceptionUtil.getExceptionMessage(ex);
        resultMessage = new ResultMessage(0, message);
      }
    }
    out.print(resultMessage);
  }
  
  @RequestMapping({"exportXml"})
  @Action(description="导出资源")
  public void exportXml(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    long resId = RequestUtil.getLong(request, "resId");
    if (resId != 0L)
    {
      Resources resources = (Resources)this.resourcesService.getById(Long.valueOf(resId));
      String fileName = resources.getAlias();
      String strXml = this.resourcesService.exportXml(resId);
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xml");
      response.getWriter().write(strXml);
      response.getWriter().flush();
      response.getWriter().close();
    }
  }
  
  @RequestMapping({"importXml"})
  @Action(description="导出资源")
  public void importXml(MultipartHttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    long systemId = RequestUtil.getLong(request, "systemId");
    long resId = RequestUtil.getLong(request, "resId");
    MultipartFile fileLoad = request.getFile("xmlFile");
    ResultMessage resultMessage = null;
    try
    {
      this.resourcesService.importXml(fileLoad.getInputStream(), resId, systemId);
      
      SecurityUtil.removeCacheBySystemId(Long.valueOf(systemId));
      resultMessage = new ResultMessage(1, "导入成功!");
      writeResultMessage(response.getWriter(), resultMessage);
    }
    catch (Exception ex)
    {
      String str = MessageUtil.getMessage();
      if (StringUtil.isNotEmpty(str))
      {
        resultMessage = new ResultMessage(0, "导入失败:" + str);
        response.getWriter().print(resultMessage);
      }
      else
      {
        String message = ExceptionUtil.getExceptionMessage(ex);
        resultMessage = new ResultMessage(0, message);
        response.getWriter().print(resultMessage);
      }
    }
  }
  
  @RequestMapping({"sortList"})
  @Action(description="资源树排序列表")
  public ModelAndView sortList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Long resId = Long.valueOf(RequestUtil.getLong(request, "resId", -1L));
    List<Resources> list = this.resourcesService.getByParentId(resId);
    if ((resId.longValue() == 0L) && (list.size() > 0))
    {
      Long currentSystemId = SubSystemUtil.getCurrentSystemId(request);
      List<Resources> relist = new ArrayList();
      for (Resources resources : list) {
        if (resources.getSystemId() == currentSystemId) {
          relist.add(resources);
        }
      }
      list.removeAll(list);
      list.addAll(relist);
    }
    return getAutoView().addObject("ResourcesList", list);
  }
  
  @RequestMapping({"sort"})
  @Action(description="资源树排序")
  public void sort(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ResultMessage resultObj = null;
    PrintWriter out = response.getWriter();
    Long[] lAryId = RequestUtil.getLongAryByStr(request, "resIds");
    if (BeanUtils.isNotEmpty(lAryId)) {
      for (int i = 0; i < lAryId.length; i++)
      {
        Long resId = lAryId[i];
        long sn = i + 1;
        this.resourcesService.updSn(resId, sn);
      }
    }
    resultObj = new ResultMessage(1, "排序分类完成");
    out.print(resultObj);
  }
  
  @RequestMapping({"addResource"})
  @Action(description="添加资源")
  public ModelAndView addResource(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = getAutoView();
    List<SubSystem> subSystemList = this.subSystemService.getAll();
    Long currentSystemId = SubSystemUtil.getCurrentSystemId(request);
    mv.addObject("subSystemList", subSystemList).addObject("currentSystemId", currentSystemId);
    return mv;
  }
  
  @RequestMapping("upd")
	@Action(description="分配角色资源")
	public void upd(HttpServletRequest request,HttpServletResponse response) throws IOException {
		final Long systemId=RequestUtil.getLong(request, "systemId",0);
		final Long roleId=RequestUtil.getLong(request, "roleId",0);
		
		 Long[] resIds=null;
		if(request.getParameter("resIds")!=null&&!request.getParameter("resIds").equals("")){
			resIds=RequestUtil.getLongAryByStr(request, "resIds");
		}
		final Long[] tempResIds=resIds;
		ResultMessage resultMessage=new ResultMessage(ResultMessage.Success,"角色资源分配成功");
		writeResultMessage(response.getWriter(), resultMessage);
		try {
			roleResourcesService.update( systemId, roleId, resIds);
			//log.setLevel(Level.DEBUG);
			String tempSystemId = subsystemidJoiningQueue();
			jmsSend(systemId, roleId, tempResIds, tempSystemId);
			/*SysMsgLog msgLog = new SysMsgLog();
			msgLog.setId(UniqueIdUtil.genId());
			msgLog.setSendrersonal(systemId+"");
			msgLog.setOperation("资源角色分配");
			msgLog.setReceiverersonal(tempSystemId);
			msgLog.setSendcontent(Arrays.asList(tempResIds).toString());
			sysMsgLogService.add(msgLog);*/
		}catch(JmsException e2){
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"角色资源分配消息失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e2);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"角色资源分配失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		} 
		}

/**
 * @return
 */
public String subsystemidJoiningQueue() {
	List<SubSystem> all = subSystemService.getAll();
	 String tempSystemId="";
	for (int i = 0; i < all.size(); i++) {
		 SubSystem subSystem = all.get(i);
		 long systemId2 = subSystem.getSystemId();

		 if(i==0){
			 tempSystemId=systemId2+",";
		 }else if(all.size()==i+1){
			 tempSystemId+=systemId2;
		 }else{
			 tempSystemId+=systemId2+",";
		 }
	}
	  if(log.isInfoEnabled()){
		    log.info(" -----queuetempSystemId-------"+tempSystemId+"}"); 
	  }
	return tempSystemId;
}

	/**
	 * @param systemId
	 * @param roleId
	 * @param tempResIds
	 * @param tempSystemId
	 * @throws JmsException
	 */
	private void jmsSend(final Long systemId, final Long roleId, final Long[] tempResIds, String tempSystemId)
			throws JmsException {
		JSONObject json = new JSONObject();
		Map<String, Object> OperationMarked = new HashMap<String, Object>();
		OperationMarked.put("操作说明", "分配角色资源：1,分配角色资源：3");
		OperationMarked.put("operationSatus", 1);// 操作标示
		json.put("OperationMarked", OperationMarked);// 操作标示：分配角色资源：1,分配角色资源：3
		json.put("systemId", systemId);
		json.put("roleId", roleId);
		json.put("resIds", tempResIds);
		new JMSRunableThread("resourcerole_update", json.toString());
	     /*	jmsTemplate.send(tempSystemId + "", new MessageCreator() {
			public MapMessage createMessage(Session session) throws JMSException {
				MapMessage mapMsg = session.createMapMessage();
				JSONObject json = new JSONObject();
				Map<String, Object> OperationMarked = new HashMap<String, Object>();
				OperationMarked.put("操作说明", "分配角色资源：1,分配角色资源：3");
				OperationMarked.put("operationSatus", 1);// 操作标示
				json.put("OperationMarked", OperationMarked);// 操作标示：分配角色资源：1,分配角色资源：3
				json.put("systemId", systemId);
				json.put("roleId", roleId);
				json.put("resIds", tempResIds);
				if (log.isDebugEnabled()) {
					log.debug("debug ------操作标示：分配角色资源：1,分配角色资源：3：{ }-------" + json.toString() + "}");
				}
				mapMsg.setString("resourcerole_update", json.toString());
				mapMsg.setStringProperty("resourcerole_update_Property", "1");// 分配角色：1
				return mapMsg;
			}
		});*/
	}
	
	
	
	  
}
  
  

  
  
  
  
  
  
  
  

