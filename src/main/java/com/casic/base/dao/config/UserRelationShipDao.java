package com.casic.base.dao.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.casic.base.model.config.UserRelationShip;
/**
 *<pre>
 * 对象功能:sys_userRelationship_mapping Dao类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-30 13:57:25
 *</pre>
 */
@Repository
public class UserRelationShipDao extends BaseDao<UserRelationShip>
{
	@Override
	public Class<?> getEntityClass()
	{
		return UserRelationShip.class;
	}

	public UserRelationShip loadUserRelationShipByUserId(Map<String, Object> map) {
		List<UserRelationShip> list=this.getSqlSessionTemplate().selectList("com.casic.base.model.config.UserRelationShip.loadUserRelationShipByUserIdAndTenantId", map);
	    if(list!=null&&list.size()>0){
			return list.get(0);
	     }
		return null;
	}
	public UserRelationShip getBypartnerUserId(String partnerUserId){
		return this.getUnique("getshipbypartnerUserId",partnerUserId);
	}
	
	public List<UserRelationShip> getShipBypartnerUserIdSystemId(String partnerUserId,String systemId){
		Map<String,String> map = new HashMap<String,String>();
		map.put("partnerUserId", partnerUserId);
		map.put("systemId", systemId);
		return this.getBySqlKey("getShipBypartnerUserIdSystemId",map);
	}
	
	//新增的方法获得list列表
	public List<UserRelationShip> getBypartnerUserIdList(String partnerUserId){
		return this.getBySqlKey("getshipbypartnerUserIdList",partnerUserId);
	}
	public UserRelationShip getByAccount(String account){
		return this.getUnique("getshipbyAccount", account);
	}
	
	public void addShip(UserRelationShip newShip){
		this.update("addship", newShip);
	}
	 public void Unbind(UserRelationShip delShip){
		 this.delBySqlKey("deleteByAccountAndPartnerUserId", delShip);
	 }
	 public UserRelationShip getByAccountAndPartenId(UserRelationShip delShip){
		 return this.getUnique("loadUserRelationShipByUserIdAndTenantId", delShip);
	 }
}