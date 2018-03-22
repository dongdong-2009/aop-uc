package com.hotent.platform.dao.system;

import com.hotent.core.db.BaseDao;
import com.hotent.core.mybatis.BaseMyBatisDao.SqlSessionTemplate;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.ResourcesUrlExt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ResourcesDao
  extends BaseDao<Resources>
{
  public Class getEntityClass()
  {
    return Resources.class;
  }
  
  public List<Resources> getByParentId(long parentId)
  {
    return getBySqlKey("getByParentId", Long.valueOf(parentId));
  }
  
  public List<Resources> getByParentId1(long parentId)
  {
    return getBySqlKey("getByParentId1", Long.valueOf(parentId));
  }
  
  public List<Resources> getBySystemId(long systemId)
  {
    return getBySqlKey("getBySystemId", Long.valueOf(systemId));
  }
  
  public List<Resources> getBySystemIdAndIsHidden(long systemId)
  {
    return getBySqlKey("getBySystemIdAndIsHidden", Long.valueOf(systemId));
  }
  
  public List<Resources> getNormMenu(Long systemId, Long userId)
  {
    Map<String, Long> p = new HashMap();
    p.put("systemId", systemId);
    p.put("userId", userId);
    return getBySqlKey("getNormMenu", p);
  }
  
  public List<Resources> getNormMenuByRole(long systemId, String roles)
  {
    Map<String, Object> p = new HashMap();
    p.put("systemId", Long.valueOf(systemId));
    p.put("rolealias", roles);
    return getBySqlKey("getNormMenuByRole", p);
  }
  
  public List<Resources> getCloudMenuByRole(long systemId, String roles, Long parentId)
  {
    Map<String, Object> p = new HashMap();
    p.put("systemId", Long.valueOf(systemId));
    p.put("rolealias", roles);
    p.put("parentId", parentId);
    p.put("tenantId", ContextUtil.getCurrentTenantId());
    List<Resources> resources = getBySqlKey("getCloudSaasMenuByRole", p);
    if (resources.isEmpty()) {
      resources = getBySqlKey("getCloudMenuByRole", p);
    }
    return resources;
  }
  
  public List<Resources> getSuperMenu(Long systemId)
  {
    return getBySqlKey("getSuperMenu", systemId);
  }
  
  public List<ResourcesUrlExt> getDefaultUrlAndRoleBySystemId(long systemId)
  {
    String stament = getIbatisMapperNamespace() + ".getDefaultUrlAndRoleBySystemId";
    return getSqlSessionTemplate().selectList(stament, Long.valueOf(systemId));
  }
  
  public List<ResourcesUrlExt> getFunctionAndRoleBySystemId(long systemId)
  {
    String stament = getIbatisMapperNamespace() + ".getFunctionAndRoleBySystemId";
    return getSqlSessionTemplate().selectList(stament, Long.valueOf(systemId));
  }
  
  public Integer isAliasExists(Long systemId, String alias)
  {
    Map<String, Object> params = new HashMap();
    params.put("alias", alias);
    params.put("systemId", systemId);
    return (Integer)getOne("isAliasExists", params);
  }
  
  public Integer isAliasExistsForUpd(Long systemId, Long resId, String alias)
  {
    Map<String, Object> params = new HashMap();
    params.put("alias", alias);
    params.put("systemId", systemId);
    params.put("resId", resId);
    return (Integer)getOne("isAliasExistsForUpd", params);
  }
  
  public List<Resources> getByUrl(String url)
  {
    return getBySqlKey("getByUrl", url);
  }
  
  public List<Resources> getBySystemIdAndParentId(long systemId, long parentId)
  {
    Map<String, Object> params = new HashMap();
    params.put("parentId", Long.valueOf(parentId));
    params.put("systemId", Long.valueOf(systemId));
    return getBySqlKey("getBySystemIdAndParentId", params);
  }
  
  public void updSn(Long resId, long sn)
  {
    Map<String, Object> map = new HashMap();
    map.put("resId", resId);
    map.put("sn", Long.valueOf(sn));
    update("updSn", map);
  }
  
  public void updIsHidden(String path)
  {
    update("updateIsHidden", path);
  }

public List<Resources> getNormMenuByRoleAndResourceName(long systemId,
		String roles, String name) {
	Map<String, Object> p = new HashMap();
    p.put("systemId", Long.valueOf(systemId));
    p.put("rolealias", roles);
    p.put("resName", name);
    return getBySqlKey("getNormMenuByRoleAndResourceName", p);
}
}
