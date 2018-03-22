package com.casic.auditLog.dao.tenantAudit;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.casic.auditLog.model.tenantAudit.tenantInfoAuditLog;
/**
 *<pre>
 * 对象功能:sys_operation_record Dao类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-05 18:26:54
 *</pre>
 */
@Repository
public class tenantInfoAuditLogDao extends BaseDao<tenantInfoAuditLog>
{
	@Override
	public Class<?> getEntityClass()
	{
		return tenantInfoAuditLog.class;
	}

	public String getReasonbyTenantId(Long sysOrgInfoId) {
		
		return (String) this.getSqlSessionTemplate().selectOne("com.casic.auditLog.model.tenantAudit.tenantInfoAuditLog.getReasonbyTenantId", sysOrgInfoId);
	}

	public List<tenantInfoAuditLog> getRecordbyTenantId(Long sysOrgInfoId) {
		
		return this.getSqlSessionTemplate().selectList("com.casic.auditLog.model.tenantAudit.tenantInfoAuditLog.getRecordbyTenantId", sysOrgInfoId);
	}

	public String getAxnReasonBybyTenantId(long sysOrgInfoId) {
		return (String) this.getSqlSessionTemplate().selectOne("com.casic.auditLog.model.tenantAudit.tenantInfoAuditLog.getAxnReasonBybyTenantId", sysOrgInfoId);
	}

}