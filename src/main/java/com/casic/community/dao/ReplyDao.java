package com.casic.community.dao;

import org.springframework.stereotype.Repository;

import com.casic.community.model.ReplyBean;
import com.hotent.core.db.BaseDao;

@Repository
public class ReplyDao extends  BaseDao<ReplyBean>{

	@Override
	public Class<ReplyBean> getEntityClass() {
		
		return ReplyBean.class;
	}

	public int addReply(ReplyBean replyBean) {
		
		return this.getSqlSessionTemplate().insert("com.casic.community.model.ReplyBean.add", replyBean);
	}

	

}
