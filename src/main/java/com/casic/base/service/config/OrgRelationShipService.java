package com.casic.base.service.config;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.base.model.config.OrgRelationShip;
import com.casic.base.dao.config.OrgRelationShipDao;

/**
 *<pre>
 * 对象功能:sys_orgRelationShip_mapping Service类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-30 14:02:38
 *</pre>
 */
@Service
public class OrgRelationShipService extends BaseService<OrgRelationShip>
{
	@Resource
	private OrgRelationShipDao dao;

	
	public OrgRelationShipService()
	{
	}
	
	@Override
	protected IEntityDao<OrgRelationShip, Long> getEntityDao() 
	{
		return dao;
	}
	
	public OrgRelationShip getShipByAccount(String partnerId){
		return dao.getByAccount(partnerId);
		 
	}
}
