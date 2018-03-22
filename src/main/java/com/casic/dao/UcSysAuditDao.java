package com.casic.dao;

/**
 * 对象功能:UC系统日志 Dao类
 * 开发公司:北京航天制造科技发展有限公司
 * 开发人员:zxx
 * 创建时间:2016 07 06
 */

import org.springframework.stereotype.Repository;

import com.casic.model.UcSysAudit;
import com.hotent.core.db.BaseDao;

@Repository
public class UcSysAuditDao extends BaseDao<UcSysAudit>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return UcSysAudit.class;
	}
}