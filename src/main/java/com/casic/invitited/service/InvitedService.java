package com.casic.invitited.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.invitited.dao.InvitiedDao;
import com.casic.invitited.model.InvitiedEntity;
import com.casic.tenant.model.TenantInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
@Service
public class InvitedService extends BaseService<InvitiedEntity>{
    
	@Resource
	private InvitiedDao invitiedDao;
	
	@Override
	protected IEntityDao<InvitiedEntity, Long> getEntityDao() {
		
		return invitiedDao;
	}

	public InvitiedEntity loadData(String tenantId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tenantId", tenantId);
		List<InvitiedEntity> list=invitiedDao.getList("loadData", map);
		if(!list.isEmpty()){
		return list.get(0);
		}else{
			return null;
		}
	}

	public InvitiedEntity loadDataByTenantId(String tenantId,
			String begincreatetime, String endcreatetime) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tenantId", tenantId);
		map.put("begincreatetime", begincreatetime);
		map.put("endcreatetime", endcreatetime);
		List<InvitiedEntity> list=invitiedDao.getList("loadData", map);
		if(!list.isEmpty()){
		return list.get(0);
		}else{
			return null;
		}
	
	}

}
