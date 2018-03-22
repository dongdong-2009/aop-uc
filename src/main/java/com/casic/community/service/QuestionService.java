package com.casic.community.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.community.dao.QuestionDao;
import com.casic.community.model.QuestionBean;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class QuestionService extends BaseService<QuestionBean>{
	
	@Resource
	private QuestionDao questionDao;

	@Override
	protected IEntityDao<QuestionBean, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return questionDao;
	}
	
	public List<QuestionBean> getAllQuestionByPage(int cur,int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cur", cur);
		params.put("size", size);
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		list = questionDao.getBySqlKey("getAllQuestionByPage", params);
		return list;
	}
	
	public List<QuestionBean> getSolvedQuestionByPage(int cur,int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cur", cur);
		params.put("size", size);
		params.put("status", "1");
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		list = questionDao.getBySqlKey("getSolvedQuestionByPage", params);
		return list;
	}
	
	public List<QuestionBean> getUnsolvedQuestionByPage(int cur,int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cur", cur);
		params.put("size", size);
		params.put("status", "0");
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		list = questionDao.getBySqlKey("getUnsolvedQuestionByPage", params);
		return list;
	}
	
	public List<QuestionBean> getMyQuestionByPage(int cur,int size,Long currentUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cur", cur);
		params.put("size", size);
		params.put("currentUserId", currentUserId);
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		list = questionDao.getBySqlKey("getMyQuestionByPage", params);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public int getAllCount() {
		int size = 0;
		List<Integer> list = new ArrayList<Integer>();
		list = questionDao.getSqlSessionTemplate().selectList("com.casic.community.model.QuestionBean.getAllCount");
		if(list!=null){
			size = list.get(0);
		}
		return size;
	}
	
	public int getSolvedCount() {
		int size = 0;
		List<Integer> list = new ArrayList<Integer>();
		list = questionDao.getSqlSessionTemplate().selectList("com.casic.community.model.QuestionBean.getSolvedCount");
		if(list!=null){
			size = list.get(0);
		}
		return size;
	}
	
	public int getUnsolvedCount() {
		int size = 0;
		List<Integer> list = new ArrayList<Integer>();
		list = questionDao.getSqlSessionTemplate().selectList("com.casic.community.model.QuestionBean.getUnsolvedCount");
		if(list!=null){
			size = list.get(0);
		}
		return size;
	}
	
	public int getMyCount(Long currentUserId) {
		int size = 0;
		List<Integer> list = new ArrayList<Integer>();
		list = questionDao.getSqlSessionTemplate().selectList("com.casic.community.model.QuestionBean.getMyCount");
		if(list!=null){
			size = list.get(0);
		}
		return size;
	}
	
	public int updateStatus(Long questionId){
		
		int flag = questionDao.getSqlSessionTemplate().update("com.casic.community.model.QuestionBean.updateStatus",questionId);
		return flag;
		
	}
	
}
