package com.casic.subsysInterface.dao;

import org.springframework.stereotype.Repository;

import com.casic.subsysInterface.model.InterfaceClassifyBean;
import com.hotent.core.db.BaseDao;

@Repository
public class SubSystemInterfaceDao extends BaseDao<InterfaceClassifyBean>{

	@Override
	public Class<InterfaceClassifyBean> getEntityClass() {
		// TODO Auto-generated method stub
		return InterfaceClassifyBean.class;
	}

}
