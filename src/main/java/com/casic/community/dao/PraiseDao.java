package com.casic.community.dao;

import org.springframework.stereotype.Repository;

import com.casic.community.model.PraiseBean;
import com.casic.community.model.ReplyBean;
import com.hotent.core.db.BaseDao;

@Repository
public class PraiseDao extends   BaseDao<PraiseBean>{

	@Override
	public Class<PraiseBean> getEntityClass() {
		
		return PraiseBean.class;
	}

	public int updatePraise(PraiseBean praiseBean) {
		
		return this.getSqlSessionTemplate().update("com.casic.community.model.PraiseBean.updatePraise", praiseBean);
	}

	public int addPraise(PraiseBean praiseBean) {
		
		return this.getSqlSessionTemplate().insert("com.casic.community.model.PraiseBean.add", praiseBean);
	}

	

	
}
