package com.hotent.platform.service.system;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysOrgInfoDao;
import com.hotent.platform.dao.system.SysOrgTypeDao;
import com.hotent.platform.dao.system.SysUserOrgDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.model.system.SysUserOrg;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class SysOrgService
{
  @Resource
  private SysUserOrgDao sysUserOrgDao;
  @Resource
  private SysOrgTypeDao sysOrgTypeDao;
  @Resource
  private IAuthenticate iAuthenticate;
  @Resource
  private SysOrgDao sysOrgDao;
  @Resource
  private SysOrgInfoDao sysOrgInfoDao;
  
  public void add(ISysOrg org)
  {
    this.iAuthenticate.add(org);
  }
  
  public void delByIds(Long[] ids)
  {
    this.iAuthenticate.delByIds(ISysOrg.class, ids);
  }
  
  public void update(ISysOrg org)
  {
    this.iAuthenticate.update(org);
  }
  
  public ISysOrg getById(Long id)
  {
    return this.iAuthenticate.getOrgByOrgId(id);
  }
  
  public List<ISysOrg> getAll()
  {
    return this.iAuthenticate.getAllOrgs();
  }
  
  public List<ISysOrg> getAll(QueryFilter queryFilter)
  {
    return this.iAuthenticate.queryOrg(queryFilter);
  }
  
  public List<ISysOrg> getOrgByOrgId(QueryFilter queryFilter)
  {
    return this.iAuthenticate.queryOrg(queryFilter);
  }
  
  public List<ISysOrg> getOrgsByDemIdOrAll(Long demId)
  {
    return this.iAuthenticate.getOrgByDemId(demId);
  }
  
  public Map getOrgMapByDemId(Long demId)
  {
    String userNameStr = "";
    String userNameCharge = "";
    Map<Long, ISysOrg> orgMap = new HashMap();
    List<ISysOrg> list = this.iAuthenticate.getOrgByDemId(demId);
    for (ISysOrg sysOrg : list)
    {
      List<SysUserOrg> userlist = this.sysUserOrgDao.getByOrgId(sysOrg.getOrgId());
      for (SysUserOrg userOrg : userlist)
      {
        if (userNameStr.isEmpty()) {
          userNameStr = userOrg.getUserName();
        } else {
          userNameStr = userNameStr + "," + userOrg.getUserName();
        }
        String isCharge = "";
        if (BeanUtils.isNotEmpty(userOrg.getIsCharge())) {
          isCharge = userOrg.getIsCharge().toString();
        }
        if (SysUserOrg.CHARRGE_YES.equals(isCharge)) {
          if (userNameCharge.isEmpty()) {
            userNameCharge = userOrg.getUserName();
          } else {
            userNameCharge = userNameCharge + "," + userOrg.getUserName();
          }
        }
      }
      sysOrg.setOwnUserName(userNameCharge);
      if ((sysOrg.getOrgSupId() != null) && (sysOrg.getOrgSupId().longValue() != 0L)) {
        orgMap.put(sysOrg.getOrgId(), sysOrg);
      }
    }
    return orgMap;
  }
  
  public void delById(Long id,Long userId)
  {
    ISysOrg sysOrg = this.iAuthenticate.getOrgByOrgId(id);
    if(StringUtil.isNotEmpty(sysOrg.getPath())){
      this.iAuthenticate.delOrgByPath(sysOrg.getPath());
    }else{
    	this.iAuthenticate.delOrgByIds(id,userId);
    }
    	
  }
  
  public List<ISysOrg> getOrgsByUserId(Long userId)
  {
    return this.iAuthenticate.getOrgsUserIn(userId);
  }
  
  public String getOrgIdsByUserId(Long userId)
  {
    StringBuffer sb = new StringBuffer();
    List<ISysOrg> orgList = this.iAuthenticate.getOrgsUserIn(userId);
    for (ISysOrg org : orgList) {
      sb.append(org.getOrgId() + ",");
    }
    if (orgList.size() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }
  
  private Map<Long, List<ISysOrg>> coverTreeData(Long rootId, List<ISysOrg> instList)
  {
    Map<Long, List<ISysOrg>> dataMap = new HashMap();
    dataMap.put(Long.valueOf(rootId.longValue()), new ArrayList());
    if ((instList != null) && (instList.size() > 0)) {
      for (ISysOrg sysOrg : instList)
      {
        long parentId = sysOrg.getOrgSupId().longValue();
        if (dataMap.get(Long.valueOf(parentId)) == null) {
          dataMap.put(Long.valueOf(parentId), new ArrayList());
        }
        ((List)dataMap.get(Long.valueOf(parentId))).add(sysOrg);
      }
    }
    return dataMap;
  }
  
  public List<ISysOrg> coverTreeList(Long rootId, List<ISysOrg> instList)
  {
    Map<Long, List<ISysOrg>> dataMap = coverTreeData(rootId, instList);
    
    List<ISysOrg> list = new ArrayList();
    
    list.addAll(getChildList(rootId, dataMap));
    
    return list;
  }
  
  private List<ISysOrg> getChildList(Long parentId, Map<Long, List<ISysOrg>> dataMap)
  {
    List<ISysOrg> list = new ArrayList();
    
    List<ISysOrg> orgList = (List)dataMap.get(Long.valueOf(parentId.longValue()));
    if ((orgList != null) && (orgList.size() > 0)) {
      for (ISysOrg sysOrg : orgList)
      {
        list.add(sysOrg);
        List<ISysOrg> childList = getChildList(sysOrg.getOrgId(), dataMap);
        list.addAll(childList);
      }
    }
    return list;
  }
  
  public List<ISysOrg> getByUserIdAndDemId(Long userId, Long demId)
  {
    return this.iAuthenticate.getOrgByUserIdAndDemId(userId, demId);
  }
  
  public void move(Long targetId, Long dragId, String moveType)
  {
    ISysOrg target = this.iAuthenticate.getOrgByOrgId(targetId);
    ISysOrg dragged = this.iAuthenticate.getOrgByOrgId(dragId);
    if (!target.getDemId().equals(dragged.getDemId())) {
      return;
    }
    String nodePath = dragged.getPath();
    
    List<ISysOrg> list = this.iAuthenticate.getOrgByPath(nodePath);
    for (ISysOrg org : list)
    {
      if (("prev".equals(moveType)) || ("next".equals(moveType)))
      {
        String targetPath = target.getPath();
        String parentPath = targetPath.endsWith(".") ? targetPath.substring(0, targetPath.length() - 1) : targetPath;
        
        parentPath = parentPath.substring(0, parentPath.lastIndexOf(".") + 1);
        if (org.getOrgId().equals(dragId))
        {
          org.setOrgSupId(target.getOrgSupId());
          org.setPath(parentPath + dragId + ".");
        }
        else
        {
          String path = org.getPath();
          String tmpPath = parentPath + dragId + "." + path.replaceAll(nodePath, "");
          org.setPath(tmpPath);
        }
        if ("prev".equals(moveType)) {
          org.setSn(Long.valueOf(target.getSn().longValue() - 1L));
        } else {
          org.setSn(Long.valueOf(target.getSn().longValue() + 1L));
        }
      }
      else if (org.getOrgId().equals(dragId))
      {
        org.setOrgSupId(targetId);
        
        org.setPath(target.getPath() + org.getOrgId() + ".");
      }
      else
      {
        String path = org.getPath();
        
        String tmpPath = path.replaceAll(nodePath, "");
        
        String targetPath = target.getPath();
        
        String tmp = targetPath + dragged.getOrgId() + "." + tmpPath;
        org.setPath(tmp);
      }
      this.iAuthenticate.update(org);
    }
  }
  
  public static void main(String[] args)
  {
    String path = "1.2.3.";
    path = path.endsWith(".") ? path.substring(0, path.length() - 1) : path;
    String subPath = path.substring(0, path.lastIndexOf(".") + 1);
    System.out.println(subPath);
  }
  
  public void addOrg(ISysOrg sysOrg)
    throws Exception
  {
    this.iAuthenticate.add(sysOrg);
    
    String ownerId = sysOrg.getOwnUser();
    if (StringUtil.isEmpty(ownerId)) {
      return;
    }
    String[] aryUserId = ownerId.split(",");
    for (int i = 0; i < aryUserId.length; i++)
    {
      String userId = aryUserId[i];
      if (!StringUtil.isEmpty(userId))
      {
        Long lUserId = Long.valueOf(Long.parseLong(userId));
        SysUserOrg sysUserOrg = new SysUserOrg();
        sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
        sysUserOrg.setOrgId(sysOrg.getOrgId());
        sysUserOrg.setIsCharge(SysUserOrg.CHARRGE_YES);
        sysUserOrg.setUserId(lUserId);
        this.sysUserOrgDao.updNotPrimaryByUserId(lUserId);
        sysUserOrg.setIsPrimary(Short.valueOf((short)1));
        this.sysUserOrgDao.add(sysUserOrg);
      }
    }
  }
  
  public void updOrg(ISysOrg sysOrg)
    throws Exception
  {
    Long orgId = sysOrg.getOrgId();
    
    this.iAuthenticate.update(sysOrg);
  }
  
  public ISysOrg getPrimaryOrgByUserId(Long userId)
  {
    return this.iAuthenticate.getPrimaryOrgByUserId(userId);
  }
  
  public List<ISysOrg> getByOrgPath(String path)
  {
    return this.iAuthenticate.getOrgByPath(path);
  }
  
  public ISysOrg getParentWithType(ISysOrg sysOrg)
  {
    Long parentOrgId = sysOrg.getOrgSupId();
    if (parentOrgId.equals(Long.valueOf(Long.parseLong("1")))) {
      return null;
    }
    ISysOrg parentOrg = this.iAuthenticate.getOrgByOrgId(parentOrgId);
    if (parentOrg == null) {
      return null;
    }
    if ((parentOrg.getOrgType() != null) && (this.sysOrgTypeDao.getById(parentOrg.getOrgType()) != null)) {
      return parentOrg;
    }
    parentOrg = getParentWithType(parentOrg);
    
    return parentOrg;
  }
  
  public ISysOrg getParentWithTypeLevel(ISysOrg sysOrg, SysOrgType sysOrgType)
  {
    ISysOrg parentOrg = getParentWithType(sysOrg);
    if (parentOrg == null) {
      return parentOrg;
    }
    SysOrgType currentSysOrgType = (SysOrgType)this.sysOrgTypeDao.getById(parentOrg.getOrgType());
    if ((currentSysOrgType != null) && (sysOrgType.getLevels().longValue() < currentSysOrgType.getLevels().longValue())) {
      parentOrg = getParentWithTypeLevel(parentOrg, sysOrgType);
    }
    return parentOrg;
  }
  
  public ISysOrg getDefaultOrgByUserId(Long userId)
  {
    List<SysUserOrg> list = this.sysUserOrgDao.getOrgByUserId(userId);
    Long orgId = Long.valueOf(0L);
    if (BeanUtils.isEmpty(list)) {
      return null;
    }
    if (list.size() == 1)
    {
      orgId = ((SysUserOrg)list.get(0)).getOrgId();
    }
    else
    {
      for (SysUserOrg userOrg : list) {
        if (userOrg.getIsPrimary().shortValue() == 1)
        {
          orgId = userOrg.getOrgId();
          break;
        }
      }
      if (orgId.longValue() == 0L) {
        orgId = ((SysUserOrg)list.get(0)).getOrgId();
      }
    }
    return this.iAuthenticate.getOrgByOrgId(orgId);
  }
  
  public void updSn(Long orgId, long sn)
  {
    this.iAuthenticate.updSn(orgId, sn);
  }
  
  public List<ISysOrg> getOrgByOrgSupId(Long orgSupId)
  {
    List<ISysOrg> list = this.iAuthenticate.getOrgByOrgSupId(orgSupId);
    return list;
  }
  
  public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId)
  {
    List<ISysOrg> ChildList = this.iAuthenticate.getOrgByOrgSupId(orgSupId);
    Properties prop = (Properties)AppUtil.getBean("configproperties");
    int level = Integer.parseInt(prop.getProperty("orgExpandLevel", "0"));
    int childSize = ChildList.size();
    if (level == 0) {
      for (int i = 0; i < childSize; i++)
      {
        List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(((ISysOrg)ChildList.get(i)).getOrgId());
        ChildList.addAll(MoreList);
      }
    }
    if (level > 1)
    {
      level--;
      for (int i = 0; i < childSize; i++)
      {
        List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(((ISysOrg)ChildList.get(i)).getOrgId(), level);
        ChildList.addAll(MoreList);
      }
    }
    return ChildList;
  }
  
  public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId, Long compId)
  {
    List<ISysOrg> ChildList = this.iAuthenticate.getOrgByOrgSupId(orgSupId, compId);
    Properties prop = (Properties)AppUtil.getBean("configproperties");
    int level = Integer.parseInt(prop.getProperty("orgExpandLevel", "0"));
    int childSize = ChildList.size();
    if (level == 0) {
      for (int i = 0; i < childSize; i++)
      {
        List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(((ISysOrg)ChildList.get(i)).getOrgId(), compId);
        ChildList.addAll(MoreList);
      }
    }
    if (level > 1)
    {
      level--;
      for (int i = 0; i < childSize; i++)
      {
        List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(((ISysOrg)ChildList.get(i)).getOrgId(), level, compId);
        ChildList.addAll(MoreList);
      }
    }
    return ChildList;
  }
  
  public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId, int level)
  {
    List<ISysOrg> ChildList = new ArrayList();
    if (level > 0)
    {
      ChildList = this.iAuthenticate.getOrgByOrgSupId(orgSupId);
      level--;
      int childSize = ChildList.size();
      for (int i = 0; i < childSize; i++)
      {
        List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(((ISysOrg)ChildList.get(i)).getOrgId(), level);
        ChildList.addAll(MoreList);
      }
    }
    return ChildList;
  }
  
  public List<ISysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId, int level, Long compId)
  {
    List<ISysOrg> ChildList = new ArrayList();
    if (level > 0)
    {
      ChildList = this.iAuthenticate.getOrgByOrgSupId(orgSupId);
      level--;
      int childSize = ChildList.size();
      for (int i = 0; i < childSize; i++)
      {
        List<ISysOrg> MoreList = getOrgByOrgSupIdAndLevel(((ISysOrg)ChildList.get(i)).getOrgId(), level);
        ChildList.addAll(MoreList);
      }
    }
    return ChildList;
  }
  
  public SysOrg getOrgBySn(Long sn)
  {
    return this.iAuthenticate.getOrgBySn(sn);
  }
  
  public SysOrg getOrgByNameAndCompany(String orgName, Long compId)
  {
    return (SysOrg)this.sysOrgDao.getOrgByNameAndCompany(orgName, compId);
  }

//根据openId查询企业信息
public SysOrgInfo getOrgByOId(String openId) {
	SysOrgInfo orginfo = (SysOrgInfo) sysOrgInfoDao.getOne("getByOpenid", openId);
	return orginfo;
}

public List<SysOrgInfo> getOrgInfoBySysid(String systemId) {
	// TODO Auto-generated method stub
	return null;
}


}
