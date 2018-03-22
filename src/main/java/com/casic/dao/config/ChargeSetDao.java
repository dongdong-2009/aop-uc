package com.casic.dao.config;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.model.ChargeSet;
import com.hotent.core.db.BaseDao;
/**
 *<pre>
 * 对象功能:cloud_charge_set Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-10-22 10:25:06
 *</pre>
 */
@Repository
public class ChargeSetDao extends BaseDao<ChargeSet>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ChargeSet.class;
	}

}
