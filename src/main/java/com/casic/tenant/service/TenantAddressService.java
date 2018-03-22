package com.casic.tenant.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.casic.tenant.dao.TenantAddressDao;
import com.casic.tenant.dao.UcAptitudeDao;
import com.casic.tenant.model.Aptitude;
import com.casic.tenant.model.TenantAddress;
import com.casic.tenant.model.TenantInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

/***
 * 企业相关service
 * @author think
 * 2016 07 05
 */
@Service
public class TenantAddressService extends BaseService<TenantAddress>{
	
	@Resource
	private TenantAddressDao dao;

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	

	@Override
	protected IEntityDao<TenantAddress, Long> getEntityDao() {
		return dao;
	}



	public Integer addTenantAddress(TenantAddress tenantAddress) {
		
		return dao.addTenantAddress(tenantAddress);
		
	}



	public Integer updateTenantAddress(TenantAddress tenantAddress) {
	
		return dao.updateTenantAddress(tenantAddress);
	}



	public Integer updateTenantAddressUnisDefault(Long id, Long currentUserId,String isReceviced) {
		dao.updateTenantAddressisDefault(id, currentUserId,isReceviced);
		return dao.updateTenantAddressUnisDefault(id,currentUserId,isReceviced);
		
	}



	public List<TenantAddress> getAllTenantAddress(Long currentUserId) {
	
		return dao.getAllTenantAddress(currentUserId);
	}



	public List<TenantAddress> getTenantAddressByType(Long currentUserId,
			String isReceviced) {
		
		return dao.getTenantAddressByType(currentUserId,isReceviced);
	}



	public Integer delAddress(Long id, Long userId) {
		
		return dao.delAddress(id,userId);
		
	}



	public TenantAddress getAddressById(Long id, Long userId) {
		
		return dao.getAddressById(id,userId);
	}



	
	

}
