package com.casic.tenant.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.casic.tenant.model.RejectHistory;
import com.hotent.core.db.BaseDao;

@Repository
public class SysOrgRejectHistoryDao extends BaseDao<RejectHistory> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Class<?> getEntityClass() {
		return RejectHistory.class;
	}
	//插入历史记录
	public void insertRejectHistory(RejectHistory rejectHistory) {
		// TODO Auto-generated method stub
		//this.getSqlSessionTemplate().insert("addRejectHistory", rejectHistory);
		this.getBySqlKey("addRejectHistory", rejectHistory);
	}

}
