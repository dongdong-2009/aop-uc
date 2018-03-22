package com.casic.msgLog.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.casic.msgLog.dao.SysMsgLogDao;
import com.casic.msgLog.model.SysMsgLog;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;

/**
 *<pre>
 * 对象功能:sys_msg_log Service类
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-27 18:08:38
 *</pre>
 */
@Service
public class SysMsgLogService extends BaseService<SysMsgLog>
{
	@Resource
	private SysMsgLogDao dao;
	
	
	
	public SysMsgLogService()
	{
	}
	
	@Override
	protected IEntityDao<SysMsgLog, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
