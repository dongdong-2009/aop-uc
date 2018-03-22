package com.casic.auditLog.service.tenantAudit;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.auditLog.model.tenantAudit.tenantInfoAuditLog;
import com.casic.auditLog.dao.tenantAudit.tenantInfoAuditLogDao;

/**
 *<pre>
 * 对象功能:sys_operation_record Service类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-05 18:26:54
 *</pre>
 */
@Service
public class tenantInfoAuditLogService extends BaseService<tenantInfoAuditLog>
{
	@Resource
	private tenantInfoAuditLogDao dao;
	
	
	
	public tenantInfoAuditLogService()
	{
	}
	
	@Override
	protected IEntityDao<tenantInfoAuditLog, Long> getEntityDao() 
	{
		return dao;
	}

	public String getReasonbyTenantId(Long sysOrgInfoId) {
		
		return dao.getReasonbyTenantId(sysOrgInfoId);
	}

	public List<tenantInfoAuditLog> getRecordbyTenantId(Long sysOrgInfoId) {
		
		return dao.getRecordbyTenantId(sysOrgInfoId);
	}

	public String getAxnReasonBybyTenantId(long sysOrgInfoId) {
		
		return dao.getAxnReasonBybyTenantId(sysOrgInfoId);
	}
	
	
}
