package com.casic.tenant.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.casic.tenant.model.TenantInfo;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysOrgInfo;
/***
 * 企业dao
 * @author think
 * 2015 07 05
 */
@Repository
public class TenantInfoDao extends BaseDao<TenantInfo>{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Class<?> getEntityClass() {
		return TenantInfo.class;
	}
	
	public List<TenantInfo> getAllInfos(String sqlKey,QueryFilter queryFilter){
		return this.getBySqlKey(sqlKey, queryFilter);
	}

	public void updateState(Integer state,Long sysOrgInfoId){
		String sql = "update sys_org_info set state = ? where sys_org_info_id = ?";
		this.jdbcTemplate.update(sql, state,sysOrgInfoId);
	}
	
	public void updateInvitedCode(String invitedCode,Long sysOrgInfoId){
		String sql = "update sys_org_info set invititedCode = ? ,buluTime = now() where sys_org_info_id = ?";
		this.jdbcTemplate.update(sql, invitedCode,sysOrgInfoId);
	}
	
	public void updateInfo(TenantInfo info){
		this.update("updateInfo", info);
	}
	
	public void updateDraft(TenantInfo info){
		this.update("updateDraft", info);
	}
	
	public void updateOpenCloseAccount(TenantInfo info){
		this.getSqlSessionTemplate().update("com.casic.tenant.model.TenantInfo.updateOpenCloseAccount", info);
	}

	public List<TenantInfo> checkOrgNameExist(
			Map<String, Object> map) {
		
		return this.getSqlSessionTemplate().selectList("com.casic.tenant.model.TenantInfo.checkOrgNameExist", map);
	}


	


}
