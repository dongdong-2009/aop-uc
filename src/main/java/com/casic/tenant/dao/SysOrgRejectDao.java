package com.casic.tenant.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.casic.tenant.model.Reject;
import com.hotent.core.db.BaseDao;
@Repository
public class SysOrgRejectDao extends BaseDao<Reject> {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return Reject.class;
	}
	/**
	 * 通过企业Id获得该企业的驳回对象
	 * */
	public List<Reject> getByOrgtenId(String sysId) {
		List<Reject> rejects = this.getBySqlKey("getByOrgtenId", Long.parseLong(sysId));		
//		List<Reject> rejects = this.getSqlSessionTemplate().selectList("getByOrgtenId",Long.parseLong(sysId));
		return rejects;
	}
	public boolean updateOrInsert(Reject reject, boolean uporin) {
		boolean flag=true;
		try {
			if(uporin){//更新表
				this.update("updateReject", reject);			
			}else{//插入表
//				this.getSqlSessionTemplate().insert("insertReject", reject);
				this.getBySqlKey("insertReject", reject);
			}
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
}
