package com.casic.dao;

import com.casic.model.UserData;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysUser;

public class UserDataDao extends BaseDao<UserData> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return UserData.class;
	}
	

}
