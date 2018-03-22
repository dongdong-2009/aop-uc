package com.hotent.core.util;

import com.hotent.core.cache.ICache;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysPaurService;
import com.hotent.platform.service.system.SysUserService;

import java.io.PrintStream;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtil
{
  private static Logger logger = LoggerFactory.getLogger(ContextUtil.class);
  private static ThreadLocal<ISysUser> curUser = new ThreadLocal();
  private static ThreadLocal<ISysOrg> curOrg = new ThreadLocal();
  private static ThreadLocal<SysOrgInfo> curOrgInfo = new ThreadLocal();
  public static final String CurrentOrg = "CurrentOrg_";
  public static final String CurrentOrgInfo = "CurrentOrgInfo_";
  public static final String CurrentMenuResource = "CurrentMenuResource_";
  private static ThreadLocal<Resources> curResources = new ThreadLocal();
  
  

  
  public static ISysUser getCurrentUser()
  {
    if (curUser.get() != null)
    {
      ISysUser user = (ISysUser)curUser.get();
      return user;
    }
    ISysUser sysUser = null;
    SecurityContext securityContext = SecurityContextHolder.getContext();
    if (securityContext != null)
    {
      Authentication auth = securityContext.getAuthentication();
      if (auth != null)
      {
        Object principal = auth.getPrincipal();
        if ((principal instanceof ISysUser)) {
          sysUser = (ISysUser)principal;
        }
      }
    }
    return sysUser;
  }
  
  public static Long getCurrentUserId()
  {
    ISysUser curUser = getCurrentUser();
    if (curUser != null) {
    	
      return curUser.getUserId();
    }
    return null;
  }
  
  public static Long getCurrentTenantId()
  {
    SysOrgInfo tenant = getCurrentOrgInfoFromSession();
    if (tenant != null) {
      return tenant.getSysOrgInfoId();
    }
    return null;
  }
  
  public static void setCurrentUserAccount(String account)
  {
    SysUserService sysUserService = (SysUserService)AppUtil.getBean("sysUserService");
    if (!com.casic.util.StringUtil.isEmpty(account)) {
		ISysUser sysUser = sysUserService.getByAccount(account);
		curUser.set(sysUser);
	}
  }
  
  public static void setCurrentUser(ISysUser sysUser)
  {
    curUser.set(sysUser);
  }
  
  public static void setCurrentOrg(Long orgId)
  {
    SysOrgService sysOrgService = (SysOrgService)AppUtil.getBean("sysOrgService");
    ISysOrg sysOrg = sysOrgService.getById(orgId);
    HttpServletRequest request = RequestUtil.getHttpServletRequest();
    HttpServletResponse response = RequestUtil.getHttpServletResponse();
    HttpSession session = request.getSession();
    saveSessionCookie(sysOrg, request, response, session);
  }
  
  public static ISysOrg getCurrentOrgFromSession()
  {
    HttpServletRequest request = RequestUtil.getHttpServletRequest();
    HttpServletResponse response = RequestUtil.getHttpServletResponse();
    HttpSession session = request.getSession();
    Long userId = getCurrentUserId();
    SysOrgService sysOrgService = (SysOrgService)AppUtil.getBean("sysOrgService");
    
    ISysOrg sysOrg = (ISysOrg)session.getAttribute("CurrentOrg_");
    if (sysOrg == null) {
      sysOrg = sysOrgService.getDefaultOrgByUserId(userId);
    }
    if (sysOrg != null) {
    	
      setCurrentOrg(sysOrg);
    }
    return sysOrg;
  }
  
  public static SysOrgInfo getCurrentOrgInfoFromSession()
  {
		System.out.println(Thread.currentThread().getId()+"-----");
    HttpServletRequest request = RequestUtil.getHttpServletRequest();
    if(request==null){
    	System.out.println(Thread.currentThread().getId()+"-----");
    }
    HttpServletResponse response = RequestUtil.getHttpServletResponse();
    System.out.println("request*****"+request);
    HttpSession session = request.getSession();
    
    SysOrgInfo sysOrgInfo = (SysOrgInfo)session.getAttribute("CurrentOrgInfo_");
    if (sysOrgInfo == null)
    {
      ISysOrg sysOrg = getCurrentOrgFromSession();
      if (sysOrg != null)
      {
        SysOrgInfoService sysOrgInfoService = (SysOrgInfoService)AppUtil.getBean("sysOrgInfoService");
      sysOrgInfo = (SysOrgInfo)sysOrgInfoService.getById(sysOrg.getSn());
       /// sysOrgInfo = (SysOrgInfo)sysOrgInfoService.getById(sysOrg.getOrgId());
      }
    }
    if (sysOrgInfo != null)
    {
      session.setAttribute("CurrentOrgInfo_", sysOrgInfo);
      setCurrentOrgInfo(sysOrgInfo);
    }
    System.out.println("用户未登录或者组织不存在");
    



    return sysOrgInfo;
  }
  
  public static ISysOrg getCurrentOrg()
  {
    ISysOrg sysOrg = (ISysOrg)curOrg.get();
    return sysOrg;
  }
  
  public static Long getCurrentOrgId()
  {
    ISysOrg sysOrg = getCurrentOrg();
    if (sysOrg != null) {
      return sysOrg.getOrgId();
    }
    return null;
  }
  
  public static String getCurrentUserSkin()
  {
    String skinStyle = "default";
    HttpServletRequest request = RequestUtil.getHttpServletRequest();
    HttpSession session = request.getSession();
    String skin = (String)session.getAttribute("skinStyle");
    if (StringUtil.isNotEmpty(skin)) {
      return skin;
    }
    SysPaurService sysPaurService = (SysPaurService)AppUtil.getBean("sysPaurService");
    Long userId = getCurrentUserId();
    skinStyle = sysPaurService.getCurrentUserSkin(userId);
    session.setAttribute("skinStyle", skinStyle);
    return skinStyle;
  }
  
  public static void setCurrentOrg(ISysOrg sysOrg)
  {
    if (sysOrg == null) {
      return;
    }
    curOrg.set(sysOrg);
   

    ICache iCache = (ICache)AppUtil.getBean(ICache.class);
    Long userId = getCurrentUserId();
    if (userId == null) {
      return;
    }
    String userKey = "CurrentOrg_" + userId;
    iCache.add("userId", userId);
    iCache.add("orgInfoId", sysOrg.getOrgId());
    iCache.add(userKey, sysOrg);
  }
  
  public static void setCurrentOrgInfo(SysOrgInfo sysOrgInfo)
  {
    if (sysOrgInfo == null) {
      return;
    }
    curOrgInfo.set(sysOrgInfo);
  
  }
  
  public static List<Resources> getCurrentMenuResources(Long systemId)
  {
    HttpServletRequest request = RequestUtil.getHttpServletRequest();
    HttpServletResponse response = RequestUtil.getHttpServletResponse();
    HttpSession session = request.getSession();
    ResourcesService resourcesService = (ResourcesService)AppUtil.getBean(ResourcesService.class);
    SubSystemService subSystemService = (SubSystemService)AppUtil.getBean(SubSystemService.class);
    SubSystem currentSystem = (SubSystem)subSystemService.getById(systemId);
    List<Resources> menuResources = (List)session.getAttribute("CurrentMenuResource_");
    if (menuResources == null) {
      menuResources = resourcesService.getCloudMenuWithCascade(currentSystem, getCurrentUser());
    }
    if (menuResources != null) {
      session.setAttribute("CurrentMenuResource_", menuResources);
    }
    return menuResources;
  }
  
  public static List<Resources> getCurrentMenuResources()
  {
    HttpServletRequest request = RequestUtil.getHttpServletRequest();
    HttpServletResponse response = RequestUtil.getHttpServletResponse();
    HttpSession session = request.getSession();
    ResourcesService resourcesService = (ResourcesService)AppUtil.getBean(ResourcesService.class);
    SubSystemService subSystemService = (SubSystemService)AppUtil.getBean(SubSystemService.class);
    SubSystem currentSystem = (SubSystem)subSystemService.getById(Long.valueOf(1L));
    
    List<Resources> menuResources = (List)session.getAttribute("CurrentMenuResource_");
    if (menuResources == null) {
      menuResources = resourcesService.getCloudMenuWithCascade(currentSystem, getCurrentUser());
    }
    if (menuResources != null) {
      session.setAttribute("CurrentMenuResource_", menuResources);
    }
    return menuResources;
  }
  
  public static void removeMenuResources(HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.removeAttribute("CurrentMenuResource_");
    }
  }
  
  public static void cleanCurrentOrg()
  {
    curOrg.remove();
  }
  
  public static void cleanCurUser()
  {
    curUser.remove();
  }
  
  private static void saveSessionCookie(ISysOrg sysOrg, HttpServletRequest request, HttpServletResponse response, HttpSession session)
  {
    session.setAttribute("CurrentOrg_", sysOrg);
    
    Long orgId = sysOrg.getOrgId();
    
    CookieUitl.addCookie("CurrentOrg_", orgId.toString(), request, response);
  }
  
  public static void removeCurrentUser(HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.removeAttribute("CurrentOrg_");
    }
    CookieUitl.delCookie("CurrentOrg_", request, response);
  }
  
  public static void removeCurrentOrg(HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.removeAttribute("CurrentOrg_");
    }
    CookieUitl.delCookie("CurrentOrg_", request, response);
  }
  
  public static void clearAll()
  {
    curUser.remove();
    curOrg.remove();
    curResources.remove();
    
    RequestUtil.clearHttpReqResponse();
    TaskThreadService.clearAll();
    TaskUserAssignService.clearAll();
    MessageUtil.clean();
  }
}
