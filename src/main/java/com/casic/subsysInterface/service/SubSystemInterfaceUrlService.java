package com.casic.subsysInterface.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.subsysInterface.dao.SubSystemInterfaceUrlDao;
import com.casic.subsysInterface.model.InterfaceUrlBean;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class SubSystemInterfaceUrlService extends BaseService<InterfaceUrlBean>{

	
	@Resource
	private SubSystemInterfaceUrlDao subSystemInterfaceUrlDao;
	
	@Override
	protected IEntityDao<InterfaceUrlBean, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return subSystemInterfaceUrlDao;
	}
	
	
	
	public List<InterfaceUrlBean> getAllUrlByType(int type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		List<InterfaceUrlBean> urls = subSystemInterfaceUrlDao.getBySqlKey("getAllUrlByType", params);
		return urls;
	}
	
	public List<InterfaceUrlBean> getAllUrlByTypeAndClassify(int type,String ename) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("ename", ename);
		List<InterfaceUrlBean> urls = subSystemInterfaceUrlDao.getBySqlKey("getAllUrlByTypeAndClassify", params);
		return urls;
	}
	
	public List<InterfaceUrlBean> getAllUrlWithTypeAndClassify(int type,String ename,Long systemId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("ename", ename);
		params.put("systemId", systemId);
		List<InterfaceUrlBean> urls = subSystemInterfaceUrlDao.getBySqlKey("getAllUrlWithTypeAndClassify", params);
		return urls;
	}
	
	/**
	 * 添加根据当前系统名称获得url
	 * @param type
	 * @param ename
	 * @param currentSystem
	 * @return
	 */
	public List<InterfaceUrlBean> getAllUrlByTypeAndClassifyWithSys(int type,String ename,String currentSystem) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("ename", ename);
		params.put("currentSystem", currentSystem);
		List<InterfaceUrlBean> urls = subSystemInterfaceUrlDao.getBySqlKey("getAllUrlByTypeAndClassifyWithSys", params);
		return urls;
	}

}
