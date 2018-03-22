package com.casic.xiaon.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.xiaon.model.SysOrgXiaonGroup;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;

/**
 * 对象功能:sys_org_xiaon_pwd Dao对象
 * 开发公司:航天科工集团
 * 开发人员:LZY
 * 创建时间:2016-07-27
 */

@Repository
public class SysOrgXiaonGroupDao extends BaseDao<SysOrgXiaonGroup>{

	@Override
	public Class<?> getEntityClass() {
		return SysOrgXiaonGroup.class;
	}

	public SysOrgXiaonGroup getByIds(Long id) {
		return this.getUnique("getByIds",id);
	}
	
	public List<SysOrgXiaonGroup> getAll() {
		return this.getBySqlKey("getAll");
	}
	
	public List<SysOrgXiaonGroup> getAdd() {
		return this.getBySqlKey("add");
	}
	
	public List<SysOrgXiaonGroup> getUpdate(SysOrgXiaonGroup sysOrgXiaon) {
		return this.getBySqlKey("update",sysOrgXiaon);
	}

	public SysOrgXiaonGroup getUserId(Long id) {
		return this.getUnique("getUserId", id);
	}
	
	public List<SysOrgXiaonGroup> getType(QueryFilter queryFilter) {
		return this.getBySqlKey("getType",queryFilter);
	}
}
