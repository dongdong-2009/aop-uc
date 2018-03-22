package com.casic.url.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.url.model.UrlBean;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;

@Repository
public class UrlMonitorDao extends BaseDao<UrlBean>{

	@Override
	public Class<UrlBean> getEntityClass() {
		// TODO Auto-generated method stub
		return UrlBean.class;
	}

	

}
