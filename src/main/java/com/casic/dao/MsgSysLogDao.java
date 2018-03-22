/**
 * 2017年11月3日MsgSysLogDao.java下午6:44:27aop-uc-internetMsgSysLogDaoTODO
 */
package com.casic.dao;

import org.springframework.stereotype.Repository;

import com.casic.model.MsgSysLog;
import com.hotent.core.db.BaseDao;
/**
 * 对象功能:系统消息 MsgSysLogDao对象
 * 开发公司:航天智造
 * 开发人员:wangshumin
 * 创建时间:2017-11-3 18:50:25
 */
@Repository
public class MsgSysLogDao extends BaseDao<MsgSysLog> {

	/* (non-Javadoc)
	 * @see com.hotent.core.db.GenericDao#getEntityClass()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return MsgSysLog.class;
	}

}
