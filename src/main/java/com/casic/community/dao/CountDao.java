package com.casic.community.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.casic.community.model.VisitorCountBean;
import com.hotent.core.db.BaseDao;

@Repository
public class CountDao extends  BaseDao<VisitorCountBean>{

	@Override
	public Class<VisitorCountBean> getEntityClass() {
		// TODO Auto-generated method stub
		return VisitorCountBean.class;
	}
	
	public void updateReadCountById(Long passageId){
		this.getSqlSessionTemplate().update("com.casic.community.model.VisitorCountBean.updateReadCountById", passageId);
	}
	
	public void updateReplyCountById(Long passageId,Integer responseCount){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", passageId);
		param.put("replyCount", responseCount);
		this.getSqlSessionTemplate().update("com.casic.community.model.VisitorCountBean.updateReplyCountById", param);
	}
	
	public VisitorCountBean findByPassageId(Long passageId){
		return (VisitorCountBean)this.getSqlSessionTemplate().selectOne("com.casic.community.model.VisitorCountBean.findByPassageId", passageId);
	}

}
