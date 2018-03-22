package com.casic.xiaon.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.xiaon.model.SysOrgXiaon;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;

/**
 * 对象功能:sys_org_xiaon_pwd Dao对象
 * 开发公司:航天科工集团
 * 开发人员:LZY
 * 创建时间:20160530
 */

@Repository
public class SysOrgXiaonDao extends BaseDao<SysOrgXiaon>{

	@Override
	public Class<?> getEntityClass() {
		return SysOrgXiaon.class;
	}

	public SysOrgXiaon getByIds(Long id) {
		return this.getUnique("getByIds",id);
	}
	
	public List<SysOrgXiaon> getAll() {
		return this.getBySqlKey("getAll");
	}
	
	public List<SysOrgXiaon> getAdd() {
		return this.getBySqlKey("add");
	}
	
	public List<SysOrgXiaon> getUpdate(SysOrgXiaon sysOrgXiaon) {
		return this.getBySqlKey("update",sysOrgXiaon);
	}

	public SysOrgXiaon getUserId(Long id) {
		return this.getUnique("getUserId", id);
	}
	
	public List<SysOrgXiaon> getType(QueryFilter queryFilter) {
		return this.getBySqlKey("getType",queryFilter);
	}
}
