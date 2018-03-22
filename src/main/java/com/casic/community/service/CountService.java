package com.casic.community.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.community.dao.CountDao;
import com.casic.community.model.VisitorCountBean;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class CountService extends BaseService<VisitorCountBean>{
	
	@Resource
	private CountDao countDao;

	@Override
	protected IEntityDao<VisitorCountBean, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return countDao;
	}
	
	public void updateReadCountById(Long passageId){
		countDao.updateReadCountById(passageId);
	}
	
	public void updateReplyCountById(Long passageId,Integer responseCount){
		countDao.updateReplyCountById(passageId,responseCount);
	}
	
	public VisitorCountBean findByPassageId(Long passageId){
		return countDao.findByPassageId(passageId);
	}
	
	
}
