package com.casic.service.config;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.dao.config.ChargeSetDao;
import com.casic.model.ChargeSet;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;

/**
 *<pre>
 * 对象功能:cloud_charge_set Service类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-10-22 10:25:06
 *</pre>
 */
@Service
public class ChargeSetService extends BaseService<ChargeSet>
{
	@Resource
	private ChargeSetDao dao;
	
	
	
	public ChargeSetService()
	{
	}
	
	@Override
	protected IEntityDao<ChargeSet, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
