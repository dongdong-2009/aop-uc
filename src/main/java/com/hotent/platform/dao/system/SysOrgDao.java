package com.hotent.platform.dao.system;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.model.system.SysOrg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class SysOrgDao
  extends BaseDao<ISysOrg>
{
  public Class getEntityClass()
  {
    return SysOrg.class;
  }
  
  public List<ISysOrg> getOrgByOrgId(QueryFilter queryFilter)
  {
    return getBySqlKey("getOrgByOrgId", queryFilter);
  }
  
  public List<ISysOrg> getOrgByDemId(Long demId)
  {
    return getBySqlKey("getOrgByDemId", demId);
  }
  
  public void updSn(Long orgId, long sn)
  {
    Map params = new HashMap();
    params.put("orgId", orgId);
    params.put("sn", Long.valueOf(sn));
    update("updSn", params);
  }
  
  public Long getOneByuserOrgId(Long userOrgId)
  {
    return (Long)getOne("getOneByuserOrgId", userOrgId);
  }
  
  public List<ISysOrg> getOrgsByUserId(Long userId)
  {
    return getBySqlKey("getOrgsByUserId", userId);
  }
  
  public List<ISysOrg> getOrgsByDemIdOrAll(Long demId)
  {
    Map<String, Object> params = new HashMap();
    if (demId.longValue() != 0L) {
      params.put("demId", demId);
    }
    return getBySqlKey("getOrgsByDemIdOrAll", params);
  }
  
  public List<ISysOrg> getByUserIdAndDemId(Long userId, Long demId)
  {
    Map<String, Object> m = new HashMap();
    m.put("userId", userId);
    m.put("demId", demId);
    return getBySqlKey("getByUserIdAndDemId", m);
  }
  
  public List<ISysOrg> getByDepth(Integer depth)
  {
    Map<String, Object> params = new HashMap();
    params.put("depth", depth);
    return getBySqlKey("getByDepth", params);
  }
  
  public List<ISysOrg> getByOrgPath(String path)
  {
    Map<String, String> params = new HashMap();
    params.put("path", StringUtil.isNotEmpty(path) ? path + "%" : "");
    return getBySqlKey("getByOrgPath", params);
  }
  
  public List<ISysOrg> getByFromType(short type)
  {
    Map<String, Object> params = new HashMap();
    params.put("fromType", Short.valueOf(type));
    return getBySqlKey("getByFromType", params);
  }
  
  public void delByPath(String path)
  {
    Map<String, Object> params = new HashMap();
    params.put("path", StringUtil.isNotEmpty(path) ? path + "%" : "");
    delBySqlKey("delByPath", params);
  }
  
  public ISysOrg getPrimaryOrgByUserId(Long userId)
  {
    return (ISysOrg)getUnique("getPrimaryOrgByUserId", userId);
  }
  
  public List<ISysOrg> getOrgByIds(String orgIds)
  {
    Map<String, Object> params = new HashMap();
    params.put("orgIds", orgIds);
    List<ISysOrg> list = getBySqlKey("getOrgByIds", params);
    return list;
  }
  
  public List<ISysOrg> getOrgByOrgSupId(Long orgSupId)
  {
    Map<String, Object> params = new HashMap();
    params.put("orgSupId", orgSupId);
    List<ISysOrg> list = getBySqlKey("getOrgByOrgSupId", params);
    return list;
  }
  
  public List<ISysOrg> getOrgByOrgSupId(Long orgSupId, Long compId)
  {
    Map<String, Object> params = new HashMap();
    params.put("orgSupId", orgSupId);
    params.put("sn", compId);
    List<ISysOrg> list = getBySqlKey("getOrgByOrgSupIdAndSn", params);
    return list;
  }
  
  public ISysOrg getOrgByNameAndCompany(String orgName, Long compId)
  {
    Map<String, Object> params = new HashMap();
    params.put("orgName", orgName);
    params.put("sn", compId);
    return (ISysOrg)getUnique("getOrgByNameAndCompany", params);
  }
  
  public List<ISysOrg> getManageOrgByTenantId(Long tenantId)
  {
    return getBySqlKey("getManageOrgByTenantId", tenantId);
  }

}
