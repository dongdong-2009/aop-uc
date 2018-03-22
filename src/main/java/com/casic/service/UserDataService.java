package com.casic.service;

import javax.annotation.Resource;

import com.casic.dao.TenantDataDao;
import com.casic.dao.UserDataDao;
import com.casic.model.TenantData;
import com.casic.model.UserData;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysUser;

public class UserDataService extends BaseService<UserData> {
	@Resource
	private UserDataDao userDataDao;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private TenantDataDao tenantDataDao;
	
	
	@Override
	
	protected IEntityDao<UserData, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return userDataDao;
	}
	/**
	 * 向用户表及用户映射表添加用户信息
	 * @param tenantData 
	 * 
	 * */
	public void addUserAndUserMapper(SysUser user, UserData userData, TenantData tenantData) {
		// TODO Auto-generated method stub
		user.setUserId(UniqueIdUtil.genId());
		sysUserDao.add(user);
		userDataDao.add(userData);
		tenantDataDao.add(tenantData);
	}
	
}
