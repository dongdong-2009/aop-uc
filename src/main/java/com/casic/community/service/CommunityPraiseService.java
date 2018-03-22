package com.casic.community.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.community.dao.PraiseDao;
import com.casic.community.model.PraiseBean;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
@Service
public class CommunityPraiseService extends BaseService<PraiseBean>{

	@Resource
	private PraiseDao praiseDao;
	
	@Override
	protected IEntityDao<PraiseBean, Long> getEntityDao() {
		
		return praiseDao;
	}

	public int updatePraise(PraiseBean praiseBean) {
		
		return praiseDao.updatePraise(praiseBean);
	}

	public List<PraiseBean> getAllCountsByReplyId(String replyId) {
		
		return praiseDao.getBySqlKey("com.casic.community.model.PraiseBean.getAllCountsByReplyId", replyId);
	}

	public int addPraise(PraiseBean praiseBean) {
	
		return praiseDao.addPraise(praiseBean);
	}

	

}
