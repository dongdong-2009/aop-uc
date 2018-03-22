package com.hotent.platform.auth;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysRoleDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.dao.system.SysUserOrgDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SecurityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

public class AuthenticateImpl
  implements IAuthenticate
{
  @Resource
  private SysUserDao sysUserDao;
  @Resource
  private SysRoleDao sysRoleDao;
  @Resource
  private SysOrgDao sysOrgDao;
  @Resource
  private SysUserOrgDao sysUserOrgDao;

  
  @Resource(name="systemproperties")
  private Properties systemproperties;
  
  public boolean isAccountExist(String account)
  {
    return this.sysUserDao.isAccountExist(account);
  }
  
  public boolean isAccountInCompanyExist(String shortAccount, Long orgSn)
  {
    return this.sysUserDao.isAccountInCompanyExist(shortAccount, orgSn);
  }
  
  public boolean isAccountExistForUpd(Long userId, String account)
  {
    return this.sysUserDao.isAccountExistForUpd(userId, account);
  }
  
  public ISysUser getUserByUserId(Long userId)
  {
    return (ISysUser)this.sysUserDao.getById(userId);
  }
  
  public ISysUser getUserByAccount(String account)
  {
    return this.sysUserDao.getByAccount(account);
  }
  
  public ISysUser getUserByOpenId(String openId)
  {
    return this.sysUserDao.getUserByOpenId(openId);
  }
  
  public List<ISysUser> getAllUser()
  {
    return this.sysUserDao.getAll();
  }
  
  public ISysOrg getOrgByOrgId(Long orgId)
  {
    return (ISysOrg)this.sysOrgDao.getById(orgId);
  }
  
  public List<ISysOrg> getOrgByDemId(Long demId)
  {
    return this.sysOrgDao.getOrgsByDemIdOrAll(demId);
  }
  
  public List<ISysOrg> getOrgByPath(String path)
  {
    return this.sysOrgDao.getByOrgPath(path);
  }
  
  public void delOrgByPath(String path)
  {
    this.sysOrgDao.delByPath(path);
  }
  
  public ISysOrg getPrimaryOrgByUserId(Long userId)
  {
    return this.sysOrgDao.getPrimaryOrgByUserId(userId);
  }
  
  public List<ISysUser> getUsersInOrg(Long orgId)
  {
    return this.sysUserDao.getByOrgId(orgId);
  }
  
  public ISysRole getRoleByRoleId(Long roleId)
  {
    return (ISysRole)this.sysRoleDao.getById(roleId);
  }
  
  public List<ISysRole> getRoleBySystemId(Long systemId)
  {
    return this.sysRoleDao.getBySystemId(systemId);
  }
  
  public boolean isExistRoleAlias(String roleAlias)
  {
    return this.sysRoleDao.isExistRoleAlias(roleAlias);
  }
  
  public boolean isExistRoleAliasForUpd(String roleAlias, Long roleId)
  {
    return this.sysRoleDao.isExistRoleAliasForUpd(roleAlias, roleId);
  }
  
  public List<ISysUser> getUserInRole(Long roleId)
  {
    return this.sysUserDao.getByRoleId(roleId);
  }
  
  public List<ISysRole> getAllRoles()
  {
    return this.sysRoleDao.getAll();
  }
  
  public List<ISysRole> getAllRoles(Long tenantId)
  {
    return this.sysRoleDao.getSaasAll(tenantId);
  }
  
  public void changePassword(Long userId, String newPwd)
  {
    this.sysUserDao.updPwd(userId, newPwd);
  }
  
  public List<ISysOrg> getOrgsUserIn(Long userId)
  {
    return this.sysOrgDao.getOrgsByUserId(userId);
  }
  
  public List<ISysRole> getRolesUserHas(Long userId)
  {
    return this.sysRoleDao.getByUserId(userId);
  }
  
  public List<ISysUser> queryUser(QueryFilter queryFilter)
  {
    return this.sysUserDao.getUserByQuery(queryFilter);
  }
  
  public List<ISysUser> queryUserByOrgId(QueryFilter queryFilter)
  {
    return this.sysUserDao.getUserByOrgId(queryFilter);
  }
  
  public List<ISysUser> queryUserNoOrg(QueryFilter queryFilter)
  {
    return this.sysUserDao.getUserNoOrg(queryFilter);
  }
  
  public List<ISysUser> queryUserByPosPath(QueryFilter queryFilter)
  {
    return this.sysUserDao.getDistinctUserByPosPath(queryFilter);
  }
  
  public List<ISysUser> queryUserByOrgPath(QueryFilter queryFilter)
  {
    return this.sysUserDao.getDistinctUserByOrgPath(queryFilter);
  }
  
  public List<ISysUser> queryUserByRoleId(QueryFilter queryFilter)
  {
    return this.sysUserDao.getUserByRoleId(queryFilter);
  }
  
  public List<ISysUser> getUserByOrgIdAndOrgSn(QueryFilter queryFilter)
  {
    return this.sysUserDao.getUserByOrgIdAndOrgSn(queryFilter);
  }
  
  public List<ISysUser> getUserByEnterprise(QueryFilter queryFilter)
  {
    return this.sysUserDao.getUserByEnterprise(queryFilter);
  }
  
  public List<ISysUser> getUserByOrgSn(QueryFilter queryFilter)
  {
	  return this.sysUserDao.getUserByOrgSn(queryFilter);
  }
  
  public List<ISysRole> queryRole(QueryFilter queryFilter)
  {
    return this.sysRoleDao.getRole(queryFilter);
  }
  
  public List<ISysOrg> queryOrg(QueryFilter queryFilter)
  {
    return this.sysOrgDao.getOrgByOrgId(queryFilter);
  }
  
  public List<ISysUser> queryUserByUserParam(Map<String, String> userParam)
  {
    return this.sysUserDao.getByUserOrParam(userParam);
  }
  
  public List<ISysUser> queryUserByOrgParam(Map<String, String> userParam)
  {
    return this.sysUserDao.getByOrgOrParam(userParam);
  }
  
  public List<ISysUser> getUserUpLowPost(Map<String, Object> p)
  {
    return this.sysUserDao.getUpLowPost(p);
  }
  
  public List<ISysUser> getUserUpLowOrg(Map<String, Object> p)
  {
    return this.sysUserDao.getUpLowOrg(p);
  }
  
  public List<ISysOrg> getOrgByUserIdAndDemId(Long userId, Long demId)
  {
    return this.sysOrgDao.getByUserIdAndDemId(userId, demId);
  }
  
  public List<ISysUser> getUserByIdSet(Set userIds)
  {
    return this.sysUserDao.getByIdSet(userIds);
  }
  
  public ISysUser getUserByMail(String address)
  {
    return this.sysUserDao.getByMail(address);
  }
  
  public void updUserStatus(Long userId, Short status, Short isLock)
  {
    this.sysUserDao.updStatus(userId, status, isLock);
  }
  
  public List<ISysUser> getUserByFromType(Short type)
  {
    return this.sysUserDao.getByFromType(type.shortValue());
  }
  
  public ISysOrg getNewSysOrg()
  {
    return new SysOrg();
  }
  
  public ISysUser getNewSysUser()
  {
    return new SysUser();
  }
  
  public ISysRole getNewSysRole()
  {
    return new SysRole();
  }
  
  public void add(Object entity)
  {
    String className = entity.getClass().getName();
    if (BeanUtils.isInherit(entity.getClass(), ISysUser.class)) {
      this.sysUserDao.add((ISysUser)entity);
    }
    if (BeanUtils.isInherit(entity.getClass(), ISysOrg.class)) {
      this.sysOrgDao.add((ISysOrg)entity);
    }
    if (BeanUtils.isInherit(entity.getClass(), ISysRole.class)) {
      this.sysRoleDao.add((ISysRole)entity);
    }
  }
  
  public void update(Object entity)
  {
    String className = entity.getClass().getName();
    if (BeanUtils.isInherit(entity.getClass(), ISysUser.class)) {
      this.sysUserDao.update((ISysUser)entity);
    }
    if (BeanUtils.isInherit(entity.getClass(), ISysOrg.class)) {
      this.sysOrgDao.update((ISysOrg)entity);
    }
    if (BeanUtils.isInherit(entity.getClass(), ISysRole.class)) {
      this.sysRoleDao.update((ISysRole)entity);
    }
  }
  
  public void delById(Class clazz, Long id)
  {
    if (BeanUtils.isInherit(clazz, ISysUser.class))
    {
      this.sysUserDao.delById(id);
      
      SecurityUtil.removeUserRoleCache(id);
    }
    if (BeanUtils.isInherit(clazz, ISysOrg.class)) {
      this.sysOrgDao.delById(id);
    }
    if (BeanUtils.isInherit(clazz, ISysRole.class)) {
      this.sysRoleDao.delById(id);
    }
  }
  
  public void delByIds(Class clazz, Long[] ids)
  {
    if (BeanUtils.isEmpty(ids)) {
      return;
    }
    for (Long id : ids) {
      delById(clazz, id);
    }
  }
  
  public List<ISysOrg> getAllOrgs()
  {
    return this.sysOrgDao.getAll();
  }
  
  public List<ISysRole> getRoleBySystemId(String systemId, String roleName)
  {
    return this.sysRoleDao.getRoleBySystemId(new Long(systemId), roleName);
  }
  
  public void updSn(Long orgId, long sn)
  {
    this.sysOrgDao.updSn(orgId, sn);
  }
  
  public List<ISysOrg> getOrgByOrgSupId(Long orgSupId)
  {
    return this.sysOrgDao.getOrgByOrgSupId(orgSupId);
  }
  
  public List<ISysOrg> getOrgByOrgSupId(Long orgSupId, Long compId)
  {
    return this.sysOrgDao.getOrgByOrgSupId(orgSupId, compId);
  }
  
  public ISysUser getSysUserByOrgIdAndAccount(Long orgId, String shortAccount)
  {
    Map<String, Object> params = new HashMap();
    params.put("orgId", orgId);
    params.put("shortAccount", shortAccount);
    List<ISysUser> sysUserList = this.sysUserDao.getBySqlKey("getSysUserByOrgIdAndAccount", params);
    if (sysUserList.size() > 0) {
      return (ISysUser)sysUserList.get(0);
    }
    return null;
  }
  
  public ISysUser getSysUserByOrgSnAndAccount(Long orgSn, String shortAccount)
  {
    Map<String, Object> params = new HashMap();
    params.put("orgSn", orgSn);
    params.put("shortAccount", shortAccount);
    List<ISysUser> sysUserList = this.sysUserDao.getBySqlKey("getSysUserByOrgSnAndAccount", params);
    if (sysUserList.size() > 0) {
      return (ISysUser)sysUserList.get(0);
    }
    return null;
  }
  
  public ISysUser getSysUserByOrgSnOrOrgCodeAndAccount(Long orgSn, String shortAccount)
  {
    Map<String, Object> params = new HashMap();
    params.put("orgSn", orgSn);
    params.put("orgCode", orgSn.toString());
    params.put("shortAccount", shortAccount);
    List<ISysUser> sysUserList = this.sysUserDao.getBySqlKey("getSysUserByOrgSnOrOrgCodeAndAccount", params);
    if (sysUserList.size() > 0) {
      return (ISysUser)sysUserList.get(0);
    }
    return null;
  }
  
  public List<ISysUser> getByCompAndRoles(Long compId, List<Long> roleIds)
  {
    return this.sysUserDao.getByCompAndRoles(compId, roleIds);
  }
  
  public List<ISysUser> getCompDefaultUser(Long compId)
  {
    String enterpriseAdminRoleAlias = this.systemproperties.getProperty("enterpriseAdminRoleAlias");
    ISysRole role = this.sysRoleDao.getByAlias(enterpriseAdminRoleAlias);
    if (role == null) {
      return new ArrayList();
    }
    List<Long> roleIds = new ArrayList();
    roleIds.add(role.getRoleId());
    List<ISysUser> sysUsers = this.sysUserDao.getByCompAndRoles(compId, roleIds);
    return sysUsers;
  }
  
  public ISysOrg getOrgByOrgSn(Long orgSn)
  {
    List<ISysOrg> sysOrgList = this.sysOrgDao.getBySqlKey("getOrgBySn", orgSn);
    if (sysOrgList.size() > 0) {
      return (ISysOrg)sysOrgList.get(0);
    }
    return null;
  }
  
  public SysOrg getOrgBySn(Long sn)
  {
    List<ISysOrg> sysOrgList = this.sysOrgDao.getBySqlKey("getOrgBySn", sn);
    if (sysOrgList.size() > 0) {
      return (SysOrg)sysOrgList.get(0);
    }
    return null;
  }

	@Override
	public void delOrgByIds(Long id,Long userId) {
		//先删除下级部门，再删除该部门
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		List<ISysOrg> sysOrgs=sysOrgDao.getBySqlKey("getBySupId", map);
		//删除所有下级部门的信息
		
		if(!sysOrgs.isEmpty()){
			for (ISysOrg iSysOrg : sysOrgs) {
				//先删除中间表的状态信息
				Map<String, Object> map1=new HashMap<String, Object>();
				map1.put("orgId", iSysOrg.getOrgId());
				map1.put("userId", userId);
				sysUserOrgDao.delBySqlKey("delByUserIdAndOrgId", map1);
				//再删除字表
				sysOrgDao.delById(iSysOrg.getOrgId());
			}
			
			
		}
		//删除自生信息
		
		sysOrgDao.delById(id);
	}

	@Override
	public List<ISysUser> querySynchronizeList(QueryFilter queryFilter) {
		
		return sysUserDao.querySynchronizeList(queryFilter);
	}

	@Override
	public String getPasswordByUserIdAndAccount(Map<String, Object> map) {
		
		return sysUserDao.getPasswordByUserIdAndAccount(map);
	}

	/* (non-Javadoc)
	 * @see com.hotent.platform.auth.IAuthenticate#queryUserBySystemId(com.hotent.core.web.query.QueryFilter)
	 */
	@Override
	public List<ISysUser> queryUserBySystemId(QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return sysUserDao.queryUserBySystemId(queryFilter);
	}
}
