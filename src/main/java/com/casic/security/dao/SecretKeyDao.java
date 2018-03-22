package com.casic.security.dao;

import org.springframework.stereotype.Repository;

import com.casic.security.model.SecretKeyBean;
import com.hotent.core.db.BaseDao;

@Repository
public class SecretKeyDao extends BaseDao<SecretKeyBean>{

	@Override
	public Class<SecretKeyBean> getEntityClass() {
		// TODO Auto-generated method stub
		return SecretKeyBean.class;
	}
	
	//根据sysId得到秘钥
	public String getSecretKeyBySysId(Long sysId){
		Object secretKey=this.getSqlSessionTemplate().selectOne("com.casic.security.model.SecretKeyBean.getSecretKeyBySysId", sysId);
		if(secretKey!=null){
			return secretKey.toString();
		}else{
			
			return "";
		}
	}

}
