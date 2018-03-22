package com.casic.dao;

import com.casic.model.TenantData;
import com.hotent.core.db.BaseDao;

public class TenantDataDao extends BaseDao<TenantData> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return TenantData.class;
	}

}
