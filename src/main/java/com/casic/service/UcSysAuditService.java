package com.casic.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.casic.dao.UcSysAuditDao;
import com.casic.model.UcSysAudit;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;

/**
 * 对象功能:系统日志 Service类 
 */
@Service
public class UcSysAuditService extends BaseService<UcSysAudit> {
	@Resource
	private UcSysAuditDao dao;

	public UcSysAuditService() {
	}

	@Override
	protected IEntityDao<UcSysAudit, Long> getEntityDao() {
		return dao;
	}

	/***
	 * 删除：系统日志
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<UcSysAudit> delAdmin(QueryFilter queryFilter) {
		return dao.getBySqlKey("delAudit", queryFilter);
	}

	/**
	 * 手动添加用户操作日志
	 */
	public void addLog(String optName,String method,String content,String state){
		// 取到当前的操作用户
		ISysUser curUser = ContextUtil.getCurrentUser();
		// 获取当前操作企业
		SysOrgInfo sysOrgInfo = ContextUtil.getCurrentOrgInfoFromSession();
		
		try{
			UcSysAudit sysAudit = new UcSysAudit();
			sysAudit.setAuditId(UniqueIdUtil.genId());
			sysAudit.setOpName(optName);
			if (curUser != null){
				sysAudit.setExecutorId(curUser.getUserId());
				sysAudit.setExecutor(curUser.getFullname());
			}
			if(sysOrgInfo != null){
				sysAudit.setTenantId(sysOrgInfo.getSysOrgInfoId());
				sysAudit.setTenantName(sysOrgInfo.getName());
			}
			sysAudit.setExeTime(new Date());
			sysAudit.setExeMethod(method);
			sysAudit.setResultState(state);
			HttpServletRequest request = RequestUtil.getHttpServletRequest();
			if (request != null){
				sysAudit.setFromIp(request.getRemoteAddr());
				sysAudit.setRequestURI(request.getRequestURI());
				sysAudit.setReqParams(content);
			}
			dao.add(sysAudit);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
