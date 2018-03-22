package com.hotent.platform.auth;

import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface IAuthenticate
{
  public abstract ISysUser getNewSysUser();
  
  public abstract ISysOrg getNewSysOrg();
  
  public abstract ISysRole getNewSysRole();
  
  public abstract void add(Object paramObject);
  
  public abstract void update(Object paramObject);
  
  public abstract void delById(Class paramClass, Long paramLong);
  
  public abstract void delByIds(Class paramClass, Long[] paramArrayOfLong);
  
  public abstract boolean isAccountExist(String paramString);
  
  public abstract boolean isAccountInCompanyExist(String paramString, Long paramLong);
  
  public abstract boolean isAccountExistForUpd(Long paramLong, String paramString);
  
  public abstract ISysUser getUserByUserId(Long paramLong);
  
  public abstract List<ISysUser> getUserByOrgIdAndOrgSn(QueryFilter paramQueryFilter);
  
  public abstract ISysUser getUserByAccount(String paramString);
  
  public abstract ISysUser getUserByOpenId(String paramString);
  
  public abstract List<ISysUser> getAllUser();
  
  public abstract ISysOrg getOrgByOrgId(Long paramLong);
  
  public abstract List<ISysOrg> getOrgByDemId(Long paramLong);
  
  public abstract List<ISysOrg> getOrgByPath(String paramString);
  
  public abstract void delOrgByPath(String paramString);
  
  public abstract ISysOrg getPrimaryOrgByUserId(Long paramLong);
  
  public abstract List<ISysUser> getUsersInOrg(Long paramLong);
  
  public abstract ISysRole getRoleByRoleId(Long paramLong);
  
  public abstract List<ISysRole> getRoleBySystemId(Long paramLong);
  
  public abstract List<ISysRole> getRoleBySystemId(String paramString1, String paramString2);
  
  public abstract boolean isExistRoleAlias(String paramString);
  
  public abstract boolean isExistRoleAliasForUpd(String paramString, Long paramLong);
  
  public abstract List<ISysUser> getUserInRole(Long paramLong);
  
  public abstract List<ISysRole> getAllRoles();
  
  public abstract List<ISysRole> getAllRoles(Long paramLong);
  
  public abstract void changePassword(Long paramLong, String paramString);
  
  public abstract List<ISysOrg> getOrgsUserIn(Long paramLong);
  
  public abstract List<ISysOrg> getAllOrgs();
  
  public abstract List<ISysRole> getRolesUserHas(Long paramLong);
  
  public abstract List<ISysUser> queryUser(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> queryUserByOrgId(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> queryUserNoOrg(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> queryUserByPosPath(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> queryUserByOrgPath(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> queryUserByRoleId(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> getUserByEnterprise(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> getUserByOrgSn(QueryFilter paramQueryFilter);
  
  public abstract List<ISysRole> queryRole(QueryFilter paramQueryFilter);
  
  public abstract List<ISysOrg> queryOrg(QueryFilter paramQueryFilter);
  
  public abstract List<ISysUser> queryUserByUserParam(Map<String, String> paramMap);
  
  public abstract List<ISysUser> queryUserByOrgParam(Map<String, String> paramMap);
  
  public abstract List<ISysUser> getUserUpLowPost(Map<String, Object> paramMap);
  
  public abstract List<ISysUser> getUserUpLowOrg(Map<String, Object> paramMap);
  
  public abstract List<ISysOrg> getOrgByUserIdAndDemId(Long paramLong1, Long paramLong2);
  
  public abstract List<ISysUser> getUserByIdSet(Set paramSet);
  
  public abstract ISysUser getUserByMail(String paramString);
  
  public abstract void updUserStatus(Long paramLong, Short paramShort1, Short paramShort2);
  
  public abstract List<ISysUser> getUserByFromType(Short paramShort);
  
  public abstract void updSn(Long paramLong, long paramLong1);
  
  public abstract List<ISysOrg> getOrgByOrgSupId(Long paramLong);
  
  public abstract List<ISysOrg> getOrgByOrgSupId(Long paramLong1, Long paramLong2);
  
  public abstract ISysOrg getOrgByOrgSn(Long paramLong);
  
  public abstract ISysUser getSysUserByOrgIdAndAccount(Long paramLong, String paramString);
  
  public abstract ISysUser getSysUserByOrgSnAndAccount(Long paramLong, String paramString);
  
  public abstract ISysUser getSysUserByOrgSnOrOrgCodeAndAccount(Long paramLong, String paramString);
  
  public abstract List<ISysUser> getByCompAndRoles(Long paramLong, List<Long> paramList);
  
  public abstract List<ISysUser> getCompDefaultUser(Long paramLong);
  
  public abstract SysOrg getOrgBySn(Long paramLong);

public abstract void delOrgByIds(Long id,Long userId);

public abstract List<ISysUser> querySynchronizeList(QueryFilter queryFilter);

public abstract String getPasswordByUserIdAndAccount(Map<String, Object> map);

/**
 * @param queryFilter
 * @return
 */
public abstract List<ISysUser> queryUserBySystemId(QueryFilter queryFilter);

}
