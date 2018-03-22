package com.casic.tenant.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.tenant.model.Aptitude;
import com.hotent.core.db.BaseDao;
/**
 *<pre>
 * 对象功能:sys_org_info_aptitude Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:ziapple
 * 创建时间:2013-05-06 16:34:54
 *</pre>
 */
@Repository
public class UcAptitudeDao extends BaseDao<Aptitude>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Aptitude.class;
	}
	
	public List<Aptitude> getAptitudeList(String sqlKey,Long params){
		return this.getBySqlKey(sqlKey, params);
		
	}
	
	public void delByMainId(String sqlKey,Long params){
		this.delBySqlKey(sqlKey, params);
	}

}
