package com.casic.log.dao.config;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.log.model.config.AhRecord;
/**
 *<pre>
 * 对象功能:sys_ah_record Dao类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2017-01-05 19:42:23
 *</pre>
 */
@Repository
public class AhRecordDao extends BaseDao<AhRecord>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AhRecord.class;
	}

}