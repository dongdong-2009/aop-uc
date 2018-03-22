package com.casic.base.dao.config;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.base.model.config.OrgRelationShip;
import com.casic.xiaon.model.SysOrgXiaon;
/**
 *<pre>
 * 对象功能:sys_orgRelationShip_mapping Dao类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-30 14:02:38
 *</pre>
 */
@Repository
public class OrgRelationShipDao extends BaseDao<OrgRelationShip>
{
	@Override
	public Class<?> getEntityClass()
	{
		return OrgRelationShip.class;
	}
	public OrgRelationShip getByAccount(String partnerId) {
		return this.getUnique("getshipbypartnerid",partnerId);
	}
	
}