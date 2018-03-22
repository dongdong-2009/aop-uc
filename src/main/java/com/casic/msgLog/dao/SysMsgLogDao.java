package com.casic.msgLog.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.casic.msgLog.model.SysMsgLog;
import com.hotent.core.db.BaseDao;
/**
 *<pre>
 * 对象功能:sys_msg_log Dao类
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-27 18:08:38
 *</pre>
 */
@Repository
public class SysMsgLogDao extends BaseDao<SysMsgLog>
{
	@Override  
	public Class<?> getEntityClass()
	{
		return SysMsgLog.class;
	}

}