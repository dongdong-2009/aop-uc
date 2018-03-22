package com.casic.xiaon.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.xiaon.dao.SysOrgXiaonGroupDao;
import com.casic.xiaon.model.SysOrgXiaonGroup;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;


/**
 * 对象功能:sys_org_xiaon_pwd Service对象
 * 开发公司:航天科工集团
 * 开发人员:LZY
 * 创建时间:2016-07-27
 */
@Service
public class SysOrgXiaonGroupService extends BaseService<SysOrgXiaonGroup>{

	@Resource
	private SysOrgXiaonGroupDao dao;

	@Override
	protected IEntityDao<SysOrgXiaonGroup, Long> getEntityDao() {
		return dao;
	}
	
	public SysOrgXiaonGroup getById(Long id) {
		return dao.getById(id);
	}
	
	public List<SysOrgXiaonGroup> getAll() {
		return dao.getAll();
	}
	
	public List<SysOrgXiaonGroup> getAdd() {
		return dao.getAdd();
	}
	
	public List<SysOrgXiaonGroup> getUpdate(SysOrgXiaonGroup sysOrgXiaon) {
		return dao.getUpdate(sysOrgXiaon);
	}

	public SysOrgXiaonGroup getByUserId(Long id) {
		return dao.getUserId(id);
	}

	public List<SysOrgXiaonGroup> getType(QueryFilter queryFilter) {
		return dao.getType(queryFilter);
	}

}
