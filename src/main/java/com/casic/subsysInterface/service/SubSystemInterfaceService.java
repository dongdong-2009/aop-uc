package com.casic.subsysInterface.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.subsysInterface.dao.SubSystemInterfaceDao;
import com.casic.subsysInterface.model.InterfaceClassifyBean;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class SubSystemInterfaceService extends BaseService<InterfaceClassifyBean>{

	
	@Resource
	private SubSystemInterfaceDao subSystemInterfaceDao;
	
	@Override
	protected IEntityDao<InterfaceClassifyBean, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return subSystemInterfaceDao;
	}

}
