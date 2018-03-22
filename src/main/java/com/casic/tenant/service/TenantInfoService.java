package com.casic.tenant.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.casic.invitited.model.InvitiedEntity;
import com.casic.tenant.dao.UcAptitudeDao;
import com.casic.tenant.dao.TenantInfoDao;
import com.casic.tenant.model.Aptitude;
import com.casic.tenant.model.TenantInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;
import com.ixinnuo.credit.ws.model.RequestData;
import com.ixinnuo.credit.ws.model.ResponseData;
import com.ixinnuo.credit.ws.util.IxinnuoUtil;

/***
 * 企业相关service
 * @author think
 * 2016 07 05
 */
@Service
public class TenantInfoService extends BaseService<TenantInfo>{
	
	@Resource
	private TenantInfoDao dao;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private UcAptitudeDao ucaptitudeDao;

	@Override
	protected IEntityDao<TenantInfo, Long> getEntityDao() {
		return dao;
	}
	
	/**
	 * 企业注册
	 * @param info
	 * @param pwd
	 * @param aryRoleId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public TenantInfo registerSysOrg(TenantInfo info,String pwd,Long[] aryRoleId,ISysUser user) throws Exception{
		Map map = sysUserService.registerSysOrg(info, user.getPassword());
		TenantInfo tenonf = (TenantInfo)map.get("sysOrgInfo");
		
		Long entId = tenonf.getSysOrgInfoId();
		info.setSysOrgInfoId(entId);
		//updateAll(info);
		updateAllInfo(info);
		
		user.setOrgId(entId);
		user.setOrgName(tenonf.getName());
		user.setOrgSn(entId);
		user.setAccount(tenonf.getSysOrgInfoId()+"_"+user.getFullname());
		
		sysUserService.update(user);
		
		String sqlUpdateOrg = "update sys_user_org  set orgId = '"+tenonf.getSysOrgInfoId()+"' where userId = '"+user.getUserId()+"'";
		jdbcTemplate.update(sqlUpdateOrg);
		
		String sysName = tenonf.getSysOrgInfoId()+"_system";
		String EptPassword = EncryptUtil.encryptSha256(pwd);
		String sqlUpdateUser = "update sys_user  set password = '"+EptPassword+"' where account = '"+sysName+"'";
		jdbcTemplate.update(sqlUpdateUser);
		
		userRoleService.saveUserRole(user.getUserId(), aryRoleId);
		SecurityUtil.removeUserRoleCache(user.getUserId());
		String sqlUpdate = "update sys_user_extence  set state = 1  where user_id = '"+user.getUserId()+"'";
		int result = jdbcTemplate.update(sqlUpdate);
		
		if(result == 0){
			String sql = "insert into sys_user_extence  set state = 1 ,user_id = '"+user.getUserId()+"'";
			jdbcTemplate.execute(sql);
		}
		return tenonf;
		
	}
	
	/**
	 * 企业注册
	 * @param info
	 * @param pwd
	 * @param aryRoleId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	/*public Long registerSysOrgNoUser(TenantInfo info,String pwd) throws Exception{
		Map map = sysUserService.registerSysOrg(info, pwd);
		TenantInfo tenonf = (TenantInfo)map.get("sysOrgInfo");
		Long entId = tenonf.getSysOrgInfoId();
		return entId;
		
	}*/
	
	public Map<String,Object> registerSysOrgNoUser(TenantInfo info,String relMes) throws Exception{
		Map<String,Object> map = sysUserService.registerSysOrg(info, relMes);
		//TenantInfo tenonf = (TenantInfo)map.get("sysOrgInfo");
		return map;
		
	}
	
	
	public void updateAll(TenantInfo info) throws Exception{
		//根据国家填写国旗
		info.setFlaglogo(sysUserService.getFlagLogoByCountry(info.getCountry()));
		update(info);
		delByPk(info.getSysOrgInfoId());
		addSubList(info);
		String sql = "update sys_org set orgName = '"+info.getName()+"',orgDesc='"+info.getName()+"' where orgId = "+ info.getSysOrgInfoId();
		jdbcTemplate.update(sql);
	}
	
	public void updateAllInfo(TenantInfo info) throws Exception{
		//根据国家填写国旗
		info.setFlaglogo(sysUserService.getFlagLogoByCountry(info.getCountry()));
		dao.updateInfo(info);
		delByPk(info.getSysOrgInfoId());
		addSubList(info);
		String sql = "update sys_org set orgName = '"+info.getName()+"',orgDesc='"+info.getName()+"' where orgId = "+ info.getSysOrgInfoId();
		jdbcTemplate.update(sql);
	}
	
	private void delByPk(Long sysOrgInfoId){
		ucaptitudeDao.delBySqlKey("delByFid", sysOrgInfoId);
	}
	
	public void addSubList(TenantInfo info) throws Exception{
		List<Aptitude> aptitudeList=info.getAptitudeList();
		if(BeanUtils.isNotEmpty(aptitudeList)){
			for(Aptitude aptitude:aptitudeList){
				aptitude.setInfoId(info.getSysOrgInfoId());
				aptitude.setId(UniqueIdUtil.genId());
				ucaptitudeDao.add(aptitude);
			}
		}
	}
	
	public List<TenantInfo> getAllInfos(String sqlKey,QueryFilter queryFilter){
		return dao.getAllInfos(sqlKey, queryFilter);
	}

	public Map<String, Object> registerSysOrg(SysOrgInfo info, String password) throws Exception {
		
		Map<String,Object> map = sysUserService.registerSysOrgAndUser(info, password);
		return map;
	}
    /**
     * 
     * @param info
     * @param string
     * @return
     */
	public ResponseData isRealEnterprise(TenantInfo info, String url) {
		RequestData  requestData = new RequestData();
		requestData.setMerchantID("351500001");
		requestData.setRequestSerialNumber(info.getSysOrgInfoId()+"");
		requestData.setInterfaceName("isRealEnterprise");
		requestData.getBusinessParameter().put("gszch", info.getGszch()==null?"001":info.getGszch());
		requestData.getBusinessParameter().put("nsrsbh", info.getNsrsbh()==null?"001":info.getNsrsbh());
		requestData.getBusinessParameter().put("qymc", info.getName());
		requestData.getBusinessParameter().put("frsfzh", info.getFrsfzhm()==null?"001":info.getFrsfzhm());
		requestData.getBusinessParameter().put("frmc", info.getIncorporator()==null?"001":info.getIncorporator());
		requestData.getBusinessParameter().put("frsjh", com.casic.util.StringUtil.isEmpty(info.getFrsjh())?"001":info.getFrsjh()+"");
		requestData.getBusinessParameter().put("url", url);
		ResponseData responseData = null;
		try {
			responseData = IxinnuoUtil.getIxinnuoData(requestData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseData;
	}

	//更新审核状态
	public void updateState(Integer state,Long sysOrgInfoId){
		dao.updateState(state,sysOrgInfoId);
	}
	
	//更新邀请码
	public void updateInvitedCode(String invitedCode,Long sysOrgInfoId){
		dao.updateInvitedCode(invitedCode,sysOrgInfoId);
	}
	
	//更新审核状态
	public void updateDraft(TenantInfo info)  throws Exception{
		dao.updateDraft(info);
	}

	public List<TenantInfo> getAllCounts() {
	
		return dao.getSqlSessionTemplate().selectList("getAllCounts");
	}

	public void updateOpenCloseAccount(TenantInfo info) throws Exception{
		dao.updateOpenCloseAccount(info);
	}

	public Map<String, Object> registerSysOrg(SysOrgInfo info,String password,
			String uopenId) throws Exception {
		Map<String,Object> map = sysUserService.registerSysOrgAndUser(info, password,uopenId);
		return map;
	}

	public List<TenantInfo> checkOrgNameExist(Map<String, Object> map) {
		
		return dao.checkOrgNameExist(map);
	}

	public Map<String,Object> registerSysOrgInfo(SysOrgInfo info, String password,
			Long[] aryRoleId, SysUser user) throws Exception {
		return sysUserService.registerSysOrgInfo(info, password,aryRoleId,user);
	}

	public Map<String, Object> registerSysOrg(SysOrgInfo info, SysUser sysUser) throws Exception {
		Map<String,Object> map = sysUserService.registerSysOrgAndUser(info,sysUser);
		return map;
	}



}
