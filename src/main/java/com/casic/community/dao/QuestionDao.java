package com.casic.community.dao;

import org.springframework.stereotype.Repository;

import com.casic.community.model.QuestionBean;
import com.hotent.core.db.BaseDao;

@Repository
public class QuestionDao extends BaseDao<QuestionBean>{

	@Override
	public Class<QuestionBean> getEntityClass() {
		// TODO Auto-generated method stub
		return QuestionBean.class;
	}
	
}
