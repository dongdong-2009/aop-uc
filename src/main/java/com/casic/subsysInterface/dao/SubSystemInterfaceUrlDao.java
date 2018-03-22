package com.casic.subsysInterface.dao;

import org.springframework.stereotype.Repository;

import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.hotent.core.db.BaseDao;

@Repository
public class SubSystemInterfaceUrlDao extends BaseDao<InterfaceUrlBean>{

	@Override
	public Class<InterfaceUrlBean> getEntityClass() {
		// TODO Auto-generated method stub
		return InterfaceUrlBean.class;
	}

}
