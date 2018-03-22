package com.casic.xiaon.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.xiaon.dao.SysOrgXiaonDao;
import com.casic.xiaon.model.SysOrgXiaon;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;


/**
 * 对象功能:sys_org_xiaon_pwd Service对象
 * 开发公司:航天科工集团
 * 开发人员:LZY
 * 创建时间:20160530
 */
@Service
public class SysOrgXiaonService extends BaseService<SysOrgXiaon>{

	@Resource
	private SysOrgXiaonDao dao;

	@Override
	protected IEntityDao<SysOrgXiaon, Long> getEntityDao() {
		return dao;
	}
	
	public SysOrgXiaon getById(Long id) {
		return dao.getById(id);
	}
	
	public List<SysOrgXiaon> getAll() {
		return dao.getAll();
	}
	
	public List<SysOrgXiaon> getAdd() {
		return dao.getAdd();
	}
	
	public List<SysOrgXiaon> gUpdate(SysOrgXiaon sysOrgXiaon) {
		return dao.getUpdate(sysOrgXiaon);
	}

	public SysOrgXiaon getByUserId(Long id) {
		return dao.getUserId(id);
	}

	public List<SysOrgXiaon> getType(QueryFilter queryFilter) {
		return dao.getType(queryFilter);
	}

}
