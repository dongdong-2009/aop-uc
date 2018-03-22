package com.casic.log.dao;

import org.springframework.stereotype.Repository;

import com.casic.log.model.InterfaceInvokeLog;
import com.hotent.core.db.BaseDao;

@Repository
public class InterfaceInvokeLogDao  extends BaseDao<InterfaceInvokeLog>{

	@Override
	public Class<InterfaceInvokeLog> getEntityClass() {
		
		return InterfaceInvokeLog.class;
	}

}
