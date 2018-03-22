/**
 * 2017年11月3日UcMsgSysLogService.java下午6:39:14aop-uc-internetUcMsgSysLogServiceTODO
 */
package com.casic.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.casic.dao.MsgSysLogDao;
import com.casic.model.MsgSysLog;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;

/**
 * 对象功能:系统消息 Service对象
 * 开发公司:航天智造
 * 开发人员:wangshumin
 * 创建时间:2017-11-3 18:50:25
 */
@Service
public class UcMsgSysLogService extends BaseService<MsgSysLog> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hotent.core.service.GenericService#getEntityDao()
	 */

	@Resource
	private MsgSysLogDao MsgSysLogDao;

	@Override
	protected IEntityDao<MsgSysLog, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return MsgSysLogDao;
	}

	public UcMsgSysLogService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/***
	 * 删除：msgsyslog日志
	 * 
	 * @param queryFilter
	 * @return
	 */

	public List<MsgSysLog> delMsgSysLog(QueryFilter queryFilter) {
		return MsgSysLogDao.getBySqlKey("delMsgSysLog", queryFilter);
	}
	
	
	/**
	 * 手动添加msgsyslog日志
	 */
	public void addLog( String messageid,String message_details,Long user_id,String org_info,String message_sending_state){
		// 取到当前的操作用户
		ISysUser curUser = ContextUtil.getCurrentUser();
		// 获取当前操作企业
		SysOrgInfo sysOrgInfo = ContextUtil.getCurrentOrgInfoFromSession();
		try{
			MsgSysLog MsgSysLog = new MsgSysLog();
			MsgSysLog.setMessagelogid(UniqueIdUtil.genId());
			MsgSysLog.setMessageid(messageid);
			MsgSysLog.setMessage_details(message_details);
			//MsgSysLog.setOrg_info(org_info);
			if (curUser != null){
				MsgSysLog.setUserid(curUser.getUserId());
				MsgSysLog.setOperator(curUser.getFullname());
			}
			if(sysOrgInfo != null){
				MsgSysLog.setOrg_info_id(sysOrgInfo.getSysOrgInfoId());
				MsgSysLog.setOrg_info(sysOrgInfo.getName());
			}
			MsgSysLog.setSend_time(new Date());
			MsgSysLog.setUserid(user_id);
			MsgSysLog.setMessage_sending_state(message_sending_state);
			HttpServletRequest request = RequestUtil.getHttpServletRequest();
			if (request != null){
				MsgSysLog.setSender_ip(request.getRemoteAddr());
				MsgSysLog.setRequesturi(request.getRequestURI());
			}
			MsgSysLogDao.add(MsgSysLog);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
