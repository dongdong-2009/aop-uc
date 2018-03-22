package com.casic.tenant.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.casic.tenant.model.TenantAddress;
import com.hotent.core.db.BaseDao;
/**
 * 
 * @author ypchenga
 *
 */
@Repository
public class TenantAddressDao extends BaseDao<TenantAddress>{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Class<?> getEntityClass() {
		return TenantAddress.class;
	}

	public Integer addTenantAddress(TenantAddress tenantAddress) {
		return this.getSqlSessionTemplate().insert("com.casic.tenant.model.TenantAddress.addTenantAddress", tenantAddress);
	}

	public Integer updateTenantAddress(TenantAddress tenantAddress) {
	
		return this.getSqlSessionTemplate().update("com.casic.tenant.model.TenantAddress.updateTenantAddress", tenantAddress);
	}

	public Integer updateTenantAddressUnisDefault(Long id, Long currentUserId,String isReceviced) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("userId", currentUserId);
		param.put("isReceviced", isReceviced);
		return this.getSqlSessionTemplate().update("com.casic.tenant.model.TenantAddress.updateTenantAddressUnisDefault", param);
	}

	public Integer updateTenantAddressisDefault(Long id, Long currentUserId,String isReceviced) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("userId", currentUserId);
		param.put("isReceviced", isReceviced);
		
		return this.getSqlSessionTemplate().update("com.casic.tenant.model.TenantAddress.updateTenantAddressisDefault", param);
		
	}

	public List<TenantAddress> getAllTenantAddress(Long currentUserId) {
		
		return this.getSqlSessionTemplate().selectList("com.casic.tenant.model.TenantAddress.getAllTenantAddress", currentUserId);
	}

	public List<TenantAddress> getTenantAddressByType(Long currentUserId,
			String isReceviced) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isReceviced", isReceviced);
		param.put("userId", currentUserId);
		return this.getSqlSessionTemplate().selectList("com.casic.tenant.model.TenantAddress.getTenantAddressByType", param);
	}

	public Integer delAddress(Long id, Long userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("userId", userId);
		return this.getSqlSessionTemplate().delete("com.casic.tenant.model.TenantAddress.delAddress", param);
	}

	public TenantAddress getAddressById(Long id, Long userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("userId", userId);
		return (TenantAddress) this.getSqlSessionTemplate().selectOne("com.casic.tenant.model.TenantAddress.getAddressById", param);
	}
	
	

}
