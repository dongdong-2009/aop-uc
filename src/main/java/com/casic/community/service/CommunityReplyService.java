package com.casic.community.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.community.dao.ReplyDao;
import com.casic.community.model.PraiseBean;
import com.casic.community.model.ReplyBean;
import com.fr.third.org.hsqldb.lib.HashMap;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class CommunityReplyService extends BaseService<ReplyBean>{
	
	@Resource
	private ReplyDao replyDao;

	@Override
	protected IEntityDao<ReplyBean, Long> getEntityDao() {
		
		return replyDao;
	}

	public int addReply(ReplyBean replyBean) {
		
		return replyDao.addReply(replyBean);
	}

	public int updateCountById(String replyId, String count) {
		Map<String,Object> map=new java.util.HashMap<String, Object>();
		map.put("id", replyId);
		map.put("count", count);
		return replyDao.update("com.casic.community.model.ReplyBean.updateCountById", map);
	}

	
	
}
