package com.casic.security.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.security.dao.SecretKeyDao;
import com.casic.security.model.SecretKeyBean;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class SecretKeyService extends BaseService<SecretKeyBean>{
	
	@Resource
	private SecretKeyDao secretKeyDao;

	@Override
	protected IEntityDao<SecretKeyBean, Long> getEntityDao() {
		return secretKeyDao;
	}
	
	//根据sysId得到秘钥
	public String getSecretKeyBySysId(Long sysId){
		return secretKeyDao.getSecretKeyBySysId(sysId);
	}

}
