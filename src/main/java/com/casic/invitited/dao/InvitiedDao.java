package com.casic.invitited.dao;

import org.springframework.stereotype.Repository;

import com.casic.invitited.model.InvitiedEntity;
import com.hotent.core.db.BaseDao;
@Repository
public class InvitiedDao extends BaseDao<InvitiedEntity>{

	@Override
	public Class<InvitiedEntity> getEntityClass() {
		
		return InvitiedEntity.class;
	}

}
