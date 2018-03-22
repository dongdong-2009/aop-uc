package com.casic.log.service.config;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.log.model.config.AhRecord;
import com.casic.log.dao.config.AhRecordDao;

/**
 *<pre>
 * 对象功能:sys_ah_record Service类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2017-01-05 19:42:23
 *</pre>
 */
@Service
public class AhRecordService extends BaseService<AhRecord>
{
	@Resource
	private AhRecordDao dao;
	
	
	
	public AhRecordService()
	{
	}
	
	@Override
	protected IEntityDao<AhRecord, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
