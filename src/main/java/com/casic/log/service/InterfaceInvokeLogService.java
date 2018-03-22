package com.casic.log.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.log.dao.InterfaceInvokeLogDao;
import com.casic.log.model.InterfaceInvokeLog;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class InterfaceInvokeLogService extends BaseService<InterfaceInvokeLog>{
	
	@Resource
	private InterfaceInvokeLogDao dao;

	@Override
	protected IEntityDao<InterfaceInvokeLog, Long> getEntityDao() {
		return dao;
	}

}
