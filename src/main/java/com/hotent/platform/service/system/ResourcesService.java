package com.hotent.platform.service.system;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.ResourcesDao;
import com.hotent.platform.dao.system.ResourcesUrlDao;
import com.hotent.platform.dao.system.RoleResourcesDao;
import com.hotent.platform.dao.system.SubSystemDao;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.ResourcesUrl;
import com.hotent.platform.model.system.RoleResources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SystemConst;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class ResourcesService
  extends BaseService<Resources>
{
  @Resource
  private ResourcesDao resourcesDao;
  @Resource
  private ResourcesUrlDao resourcesUrlDao;
  @Resource
  private SubSystemDao subSystemDao;
  @Resource
  private RoleResourcesDao roleResourcesDao;
  
  protected IEntityDao<Resources, Long> getEntityDao()
  {
    return this.resourcesDao;
  }
  
  public Long addRes(Resources resources, String[] aryName, String[] aryUrl)
    throws Exception
  {
    Long resId = Long.valueOf(UniqueIdUtil.genId());
    resources.setResId(resId);
    String path = "";
    Long parentId = resources.getParentId();
    Resources parentRes = (Resources)this.resourcesDao.getById(parentId);
    if (BeanUtils.isNotEmpty(parentRes))
    {
      if (StringUtil.isNotEmpty(parentRes.getPath())) {
        path = parentRes.getPath() + ":" + resId;
      }
    }
    else {
      path = resId.toString();
    }
    resources.setPath(path);
    this.resourcesDao.add(resources);
    if (BeanUtils.isEmpty(aryName)) {
      return resId;
    }
    for (int i = 0; i < aryName.length; i++)
    {
      String url = aryUrl[i];
      if (!StringUtil.isEmpty(url))
      {
        ResourcesUrl resouceUrl = new ResourcesUrl();
        resouceUrl.setResId(resId);
        resouceUrl.setResUrlId(Long.valueOf(UniqueIdUtil.genId()));
        resouceUrl.setName(aryName[i]);
        resouceUrl.setUrl(url);
        this.resourcesUrlDao.add(resouceUrl);
      }
    }
    return resId;
  }
  
  public void updRes(Resources resources, String[] aryName, String[] aryUrl)
    throws Exception
  {
    Long resId = resources.getResId();
    String path = "";
    Long parentId = resources.getParentId();
    Resources parentRes = (Resources)this.resourcesDao.getById(parentId);
    if (BeanUtils.isNotEmpty(parentRes))
    {
      if (StringUtil.isNotEmpty(parentRes.getPath())) {
        path = parentRes.getPath() + ":" + resId;
      }
    }
    else {
      path = resId.toString();
    }
    resources.setPath(path);
    this.resourcesDao.update(resources);
    
    this.resourcesUrlDao.delByResId(resId.longValue());
    if (BeanUtils.isEmpty(aryName)) {
      return;
    }
    for (int i = 0; i < aryName.length; i++)
    {
      String url = aryUrl[i];
      if (!StringUtil.isEmpty(url))
      {
        ResourcesUrl resouceUrl = new ResourcesUrl();
        resouceUrl.setResId(resId);
        resouceUrl.setResUrlId(Long.valueOf(UniqueIdUtil.genId()));
        resouceUrl.setName(aryName[i]);
        resouceUrl.setUrl(url);
        this.resourcesUrlDao.add(resouceUrl);
      }
    }
  }
  
  public List<Resources> getBySystemId(long systemId)
  {
    List<Resources> resourcesList = this.resourcesDao.getBySystemId(systemId);
    
    return resourcesList;
  }
  
  public List<Resources> getBySystemIdAndIsHidden(long systemId)
  {
    List<Resources> resourcesList = this.resourcesDao.getBySystemIdAndIsHidden(systemId);
    
    return resourcesList;
  }
  
  public Resources getParentResourcesByParentId(long systemId, long parentId)
  {
    Resources parent = (Resources)this.resourcesDao.getById(Long.valueOf(parentId));
    if (parent != null) {
      return parent;
    }
    SubSystem sys = (SubSystem)this.subSystemDao.getById(Long.valueOf(systemId));
    
    parent = new Resources();
    parent.setResId(Long.valueOf(0L));
    parent.setParentId(Long.valueOf(-1L));
    parent.setSn(Integer.valueOf(0));
    parent.setSystemId(Long.valueOf(systemId));
    
    parent.setAlias(sys.getAlias());
    
    parent.setIsDisplayInMenu(Resources.IS_DISPLAY_IN_MENU_Y);
    parent.setIsFolder(Resources.IS_FOLDER_Y);
    parent.setIsOpen(Resources.IS_OPEN_Y);
    parent.setResName(sys.getSysName());
    
    return parent;
  }
  
  private List<Resources> getChildsByResId(Long resId, List<Resources> allRes)
  {
    List<Resources> rtnList = new ArrayList();
    for (Iterator<Resources> it = allRes.iterator(); it.hasNext();)
    {
      Resources res = (Resources)it.next();
      if (res.getParentId().equals(resId))
      {
        rtnList.add(res);
        recursiveChilds(res.getResId(), rtnList, allRes);
        it.remove();
      }
    }
    return rtnList;
  }
  
  private void recursiveChilds(Long resId, List<Resources> rtnList, List<Resources> allRes)
  {
    for (Iterator<Resources> it = allRes.iterator(); it.hasNext();)
    {
      Resources res = (Resources)it.next();
      if (res.getParentId().equals(resId))
      {
        rtnList.add(res);
        recursiveChilds(res.getResId(), rtnList, allRes);
        it.remove();
      }
    }
  }
  
  public void delById(Long resId)
  {
    Resources res = (Resources)this.resourcesDao.getById(resId);
    List<Resources> allRes = this.resourcesDao.getBySystemId(res.getSystemId().longValue());
    List<Resources> allChilds = getChildsByResId(resId, allRes);
    for (Iterator<Resources> it = allChilds.iterator(); it.hasNext();)
    {
      Resources resources = (Resources)it.next();
      Long childId = resources.getResId();
      
      this.resourcesUrlDao.delByResId(childId.longValue());
      
      this.roleResourcesDao.delByResId(childId);
      
      this.resourcesDao.delById(childId);
    }
    this.resourcesUrlDao.delByResId(resId.longValue());
    
    this.roleResourcesDao.delByResId(resId);
    
    this.resourcesDao.delById(resId);
  }
  
  public List<Resources> getBySysRolResChecked(Long systemId, Long roleId)
  {
    List<Resources> resourcesList = this.resourcesDao.getBySystemIdAndIsHidden(systemId.longValue());
    List<RoleResources> roleResourcesList = this.roleResourcesDao.getBySysAndRole(systemId, roleId);
    
    Set<Long> set = new HashSet();
    if (BeanUtils.isNotEmpty(roleResourcesList)) {
      for (RoleResources rores : roleResourcesList) {
        set.add(rores.getResId());
      }
    }
    if (BeanUtils.isNotEmpty(resourcesList)) {
      for (Resources res : resourcesList) {
        if (set.contains(res.getResId())) {
          res.setChecked("true");
        } else {
          res.setChecked("false");
        }
      }
    }
    return resourcesList;
  }
  
  public List<Resources> getSysMenu(SubSystem sys, ISysUser user)
  {
    Long systemId = Long.valueOf(sys.getSystemId());
    Collection<GrantedAuthority> auths = user.getAuthorities();
    List<Resources> resourcesList = new ArrayList();
    if ((auths != null) && (auths.size() > 0) && (auths.contains(SystemConst.ROLE_GRANT_SUPER)))
    {
      resourcesList = this.resourcesDao.getSuperMenu(systemId);
    }
    else
    {
      String roles = "";
      for (GrantedAuthority role : auths)
      {
        roles = roles + "'" + role.getAuthority() + "'";
        roles = roles + ",";
      }
      int index = roles.lastIndexOf(",");
      if (index >= 0) {
        roles = roles.substring(0, index);
      }
      if (StringUtil.isEmpty(roles)) {
        roles = "''";
      }
      resourcesList = this.resourcesDao.getNormMenuByRole(systemId.longValue(), roles);
    }
    short isLocal = sys.getIsLocal() == null ? 1 : sys.getIsLocal().shortValue();
    if (isLocal == SubSystem.isLocal_N) {
      for (Resources res : resourcesList) {
        res.setDefaultUrl(sys.getDefaultUrl() + res.getDefaultUrl());
      }
    }
    return resourcesList;
  }
  
  public List<Resources> getCloudMenu(SubSystem sys, ISysUser user, Long parentId)
  {
    Long systemId = Long.valueOf(sys.getSystemId());
    Collection<GrantedAuthority> auths = user.getAuthorities();
    List<Resources> resourcesList = new ArrayList();
    
    String roles = "";
    for (GrantedAuthority role : auths)
    {
      roles = roles + "'" + role.getAuthority() + "'";
      roles = roles + ",";
    }
    int index = roles.lastIndexOf(",");
    if (index >= 0) {
      roles = roles.substring(0, index);
    }
    if (StringUtil.isEmpty(roles)) {
      roles = "''";
    }
    resourcesList = this.resourcesDao.getCloudMenuByRole(systemId.longValue(), roles, parentId);
    return resourcesList;
  }
  
  public List<Resources> getCloudTopMenu(SubSystem sys, ISysUser user)
  {
    List<Resources> topResourcesList = new ArrayList();
    List<Resources> resourcesList = getCloudMenu(sys, user, null);
    for (Resources resources : resourcesList) {
      if (resources.getParentId().longValue() == 0L) {
        topResourcesList.add(resources);
      }
    }
    return topResourcesList;
  }
  
  public List<Resources> getCloudMenuWithCascade(SubSystem sys, ISysUser user)
  {
    List<Resources> cloudMenuList = new ArrayList();
    
    List<Resources> resourcesList = getCloudMenu(sys, user, null);
    for (Iterator i$ = resourcesList.iterator(); i$.hasNext();)
    {
    	Resources resources = (Resources)i$.next();
      if (resources.getParentId().longValue() == 0L) {
        cloudMenuList.add(resources);
      } else {
        for (Resources parent : resourcesList) {
          if (resources.getParentId().longValue() == parent.getResId().longValue()) {
            parent.getChildren().add(resources);
          }
        }
      }
    }
    Resources resources;
    return cloudMenuList;
  }
  
  public List<Resources> getCloudMenuByParentId(SubSystem sys, ISysUser user, Long parentId)
  {
    List<Resources> resourcesList = getCloudMenu(sys, user, parentId);
    for (Resources resources : resourcesList)
    {
      List<Resources> children = getCloudMenu(sys, user, resources.getResId());
      resources.setChildren(children);
    }
    return resourcesList;
  }
  
  public List<Resources> getChildrenByParentId(Long parentId, boolean includeGrand)
  {
    List<Resources> resourcesList = this.resourcesDao.getByParentId(parentId.longValue());
    if (includeGrand) {
      for (Resources resources : resourcesList)
      {
        List<Resources> subResourcesList = this.resourcesDao.getByParentId(resources.getResId().longValue());
        resources.setChildren(subResourcesList);
      }
    }
    return resourcesList;
  }
  
  public Integer isAliasExists(Resources resources)
  {
    Long systemId = resources.getSystemId();
    String alias = resources.getAlias();
    return this.resourcesDao.isAliasExists(systemId, alias);
  }
  
  public Integer isAliasExistsForUpd(Resources resources)
  {
    Long systemId = resources.getSystemId();
    String alias = resources.getAlias();
    Long resId = resources.getResId();
    return this.resourcesDao.isAliasExistsForUpd(systemId, resId, alias);
  }
  
  public Resources getByUrl(String url)
  {
    List<Resources> list = this.resourcesDao.getByUrl(url);
    if (list.size() != 0) {
      return (Resources)list.get(0);
    }
    return null;
  }
  
  public List<Resources> getByParentId(Long id)
  {
    return this.resourcesDao.getByParentId(id.longValue());
  }
  
  public List<Resources> getByParentId1(Long id)
  {
    return this.resourcesDao.getByParentId1(id.longValue());
  }
  
  public void move(Long sourceId, Long targetId, String moveType)
  {
    if ("inner".equalsIgnoreCase(moveType))
    {
      Resources target = (Resources)this.resourcesDao.getById(targetId);
      Resources source = (Resources)this.resourcesDao.getById(sourceId);
      
      source.setParentId(target.getResId());
      if (StringUtil.isNotEmpty(target.getPath())) {
        source.setPath(target.getPath() + ":" + sourceId);
      } else {
        source.setPath(sourceId.toString());
      }
      this.resourcesDao.update(source);
    }
    else
    {
      Resources target = (Resources)this.resourcesDao.getById(targetId);
      int sn = target.getSn().intValue();
      Resources parentRes = (Resources)this.resourcesDao.getById(target.getParentId());
      Resources source = (Resources)this.resourcesDao.getById(sourceId);
      if (StringUtil.isNotEmpty(parentRes.getPath())) {
        source.setPath(parentRes.getPath() + ":" + sourceId);
      } else {
        source.setPath(sourceId.toString());
      }
      source.setParentId(target.getParentId());
      if ("prev".equals(moveType))
      {
        source.setSn(Integer.valueOf(sn));
        target.setSn(Integer.valueOf(sn + 1));
        this.resourcesDao.update(source);
        this.resourcesDao.update(target);
      }
      else
      {
        source.setSn(Integer.valueOf(sn + 1));
        this.resourcesDao.update(source);
      }
    }
  }
  
  public static void addIconCtxPath(List<Resources> list, String ctxPath)
  {
    for (Iterator<Resources> it = list.iterator(); it.hasNext();)
    {
      Resources res = (Resources)it.next();
      String icon = res.getIcon();
      if (StringUtil.isNotEmpty(icon)) {
        res.setIcon(ctxPath + icon);
      }
    }
  }
  
  public String exportXml(long resId)
  {
    String strXml = "";
    Document doc = DocumentHelper.createDocument();
    Element root = doc.addElement("items");
    addElement(root, resId);
    strXml = doc.asXML();
    return strXml;
  }
  
  private void addElement(Element root, long resId)
  {
    List<Resources> list = getByParentId(Long.valueOf(resId));
    if (BeanUtils.isNotEmpty(list)) {
      for (Resources res : list)
      {
        Element element = root.addElement("item");
        setAttribute(res, element);
        addElement(element, res.getResId().longValue());
      }
    }
  }
  
  public void setAttribute(Resources res, Element element)
  {
    String url = res.getDefaultUrl();
    if ((url != null) && (!url.equals(""))) {
      element.addAttribute("defaultUrl", res.getDefaultUrl());
    }
    element.addAttribute("name", res.getResName());
    element.addAttribute("isDisplayMenu", res.getIsDisplayInMenu().toString());
    element.addAttribute("isFolder", res.getIsFolder().toString());
    element.addAttribute("isOpen", res.getIsOpen().toString());
    element.addAttribute("icon", res.getIcon());
    element.addAttribute("sn", res.getSn().toString());
  }
  
  public void importXml(InputStream inputStream, long resId, long systemId)
  {
    Document doc = Dom4jUtil.loadXml(inputStream);
    Element root = doc.getRootElement();
    addResource(root, resId, systemId);
  }
  
  private void addResource(Element root, long resId, long systemId)
  {
    List<Element> list = root.elements();
    if (BeanUtils.isNotEmpty(list)) {
      for (Element element : list)
      {
        long id = UniqueIdUtil.genId();
        Resources res = new Resources();
        res.setResId(Long.valueOf(id));
        res.setResName(element.attributeValue("name"));
        res.setIsDisplayInMenu(Short.valueOf(Short.parseShort(element.attributeValue("isDisplayMenu"))));
        res.setIsFolder(Short.valueOf(Short.parseShort(element.attributeValue("isFolder"))));
        res.setIsOpen(Short.valueOf(Short.parseShort(element.attributeValue("isOpen"))));
        res.setIcon(element.attributeValue("icon"));
        String url = element.attributeValue("defaultUrl");
        if (StringUtil.isNotEmpty(url)) {
          res.setDefaultUrl(url);
        }
        res.setParentId(Long.valueOf(resId));
        res.setSystemId(Long.valueOf(systemId));
        add(res);
        addResource(element, id, systemId);
      }
    }
  }
  
  public void updSn(Long resId, long sn)
  {
    this.resourcesDao.updSn(resId, sn);
  }
  
  public void updateIsHidden(Resources resources)
  {
    resources.setIsHidden(Integer.valueOf(1));
    update(resources);
    

    List<Resources> list = getByParentId1(resources.getResId());
    if (list.size() > 0) {
      for (Resources res : list) {
        updateIsHidden(res);
      }
    }
  }

public List<Resources> getSysMenuAndResourceName(SubSystem sys,
		ISysUser user, String name) {
	Long systemId = Long.valueOf(sys.getSystemId());
    Collection<GrantedAuthority> auths = user.getAuthorities();
    List<Resources> resourcesList = new ArrayList();
    if ((auths != null) && (auths.size() > 0) && (auths.contains(SystemConst.ROLE_GRANT_SUPER)))
    {
      resourcesList = this.resourcesDao.getSuperMenu(systemId);
    }
    else
    {
      String roles = "";
      for (GrantedAuthority role : auths)
      {
        roles = roles + "'" + role.getAuthority() + "'";
        roles = roles + ",";
      }
      int index = roles.lastIndexOf(",");
      if (index >= 0) {
        roles = roles.substring(0, index);
      }
      if (StringUtil.isEmpty(roles)) {
        roles = "''";
      }
      resourcesList = this.resourcesDao.getNormMenuByRoleAndResourceName(systemId.longValue(), roles,name);
    }
    short isLocal = sys.getIsLocal() == null ? 1 : sys.getIsLocal().shortValue();
    if (isLocal == SubSystem.isLocal_N) {
      for (Resources res : resourcesList) {
        res.setDefaultUrl(sys.getDefaultUrl() + res.getDefaultUrl());
      }
    }
    return resourcesList;
 }
}
