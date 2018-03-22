package com.casic.url.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.casic.url.dao.UrlMonitorDao;
import com.casic.url.model.UrlBean;
import com.casic.util.DateUtil;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;

@Service
public class UrlMonitorService extends BaseService<UrlBean>{

	
	@Resource
	private UrlMonitorDao urlMonitorDao;
	
	@Override
	protected IEntityDao<UrlBean, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return urlMonitorDao;
	}
	
	
	public List<UrlBean> getAllUrls(){
		List<UrlBean> urls = urlMonitorDao.getBySqlKey("getAllUrls");
		return urls;
	}
	
	
	public UrlBean getByUrl(String url){
		UrlBean urlBean = new UrlBean();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", url);
		List<UrlBean> urls = urlMonitorDao.getBySqlKey("getByUrl", params);
		if(urls!=null){
			urlBean = urls.get(0);
		}
		return urlBean;
	}


	public List<UrlBean> getAllUrlDetails(QueryFilter filter) {
		
		return urlMonitorDao.getBySqlKey("getAllUrlDetails", filter);
	}


	public UrlBean loadDataByUrl(String url) {
		UrlBean urlBean = new UrlBean();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", url);
		List<UrlBean> urls = urlMonitorDao.getBySqlKey("loadDataByUrl", params);
		if(urls!=null){
			urlBean = urls.get(0);
		}
		return urlBean;
	}
     
	
	public UrlBean getMonitor(UrlBean urlMonitor,InterfaceUrlBean urlBean){
		    String startTime=DateUtil.getCurrentDate();
		    long id = UniqueIdUtil.genId();
			urlMonitor.setId(id);
			urlMonitor.setUrl(urlBean.getSubIndexUrl()+urlBean.getUrl());
		
			urlMonitor.setStartTime(startTime);
			urlMonitor.setSubSysId(urlBean.getSubId()+"");
			return urlMonitor;
	}
}
