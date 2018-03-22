package com.casic.base.service.config;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.system.SysUser;
import com.casic.base.model.config.OrgRelationShip;
import com.casic.base.model.config.UserRelationShip;
import com.casic.base.dao.config.UserRelationShipDao;
import com.casic.model.TenantData;
import com.casic.model.UserData;
import com.casic.tenant.model.TenantInfo;

/**
 *<pre>
 * 对象功能:sys_userRelationship_mapping Service类
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-30 13:57:25
 *</pre>
 */
@Service
public class UserRelationShipService extends BaseService<UserRelationShip>
{
	@Resource
	private UserRelationShipDao dao;
	
	@Resource
	private OrgRelationShipService orgRelationShipService;
	
	
	
	public UserRelationShipService()
	{
	}
	
	@Override
	protected IEntityDao<UserRelationShip, Long> getEntityDao() 
	{
		return dao;
	}

	public UserRelationShip loadUserRelationShipByUserId(String userId,
			String tenantId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("tenantId", tenantId);
		return dao.loadUserRelationShipByUserId(map);
	}

	public void saveUserRelationAndOrgRelation(UserRelationShip userRelationShip,String account,Long userId,TenantData tenantData,UserData userData,TenantInfo info) {
		userRelationShip=new UserRelationShip();
		userRelationShip.setAccount(account);
		userRelationShip.setApplyTime(new Date());
		userRelationShip.setId(UniqueIdUtil.genId());
		userRelationShip.setStatus("1");
		userRelationShip.setUserId(userId);
		userRelationShip.setPartnerId(tenantData.getTenantId());
		userRelationShip.setPartnerUserId(userData.getUserId());
		dao.add(userRelationShip);
		OrgRelationShip orgRelationShip=new OrgRelationShip();
		orgRelationShip.setId(UniqueIdUtil.genId());
		orgRelationShip.setApplyTime(new Date());
		orgRelationShip.setEntpId(info.getSysOrgInfoId());
		orgRelationShip.setPartnerId(tenantData.getTenantId());
		orgRelationShip.setStatus("1");
		orgRelationShipService.add(orgRelationShip);
	}

	public void saveUserRelationShip(UserRelationShip userRelationShip, String account, SysUser sysUser,
			TenantData tenantData, UserData userData) {
		 userRelationShip=new UserRelationShip();
		 userRelationShip.setAccount(account);
		 userRelationShip.setApplyTime(new Date());
		 userRelationShip.setId(UniqueIdUtil.genId());
		 userRelationShip.setStatus("1");
		 userRelationShip.setUserId(sysUser.getUserId());
		 userRelationShip.setPartnerId(tenantData.getTenantId());
		 userRelationShip.setPartnerUserId(userData.getUserId());
		 dao.add(userRelationShip);		
	}

	public UserRelationShip getShipBypartnerUserId(String partnerUserId){
		return dao.getBypartnerUserId(partnerUserId);
	}
	
	//根据系统id和用户id获得关系
	public List<UserRelationShip> getShipBypartnerUserIdSystemId(String partnerUserId,String systemId){
		return dao.getShipBypartnerUserIdSystemId(partnerUserId,systemId);
	}
	
	//新增的方法获得list列表
	public List<UserRelationShip> getShipBypartnerUserIdList(String partnerUserId){
		return dao.getBypartnerUserIdList(partnerUserId);
	}
	public UserRelationShip getShipByAccount(String account){
		return dao.getByAccount(account);
	}
	
	public void addNewShip(UserRelationShip newShip){
		dao.addShip(newShip);
	}
	public void Unbind(UserRelationShip delShip){
		dao.Unbind(delShip);
	}
	public UserRelationShip getShipByAccountAndPartenId(String stuid,String account){
		UserRelationShip ship=new UserRelationShip();
		ship.setPartnerId(stuid);
		ship.setAccount(account);
		return dao.getByAccountAndPartenId(ship);
	}
}
