package com.casic.tenant.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.tenant.dao.SysOrgRejectDao;
import com.casic.tenant.dao.SysOrgRejectHistoryDao;
import com.casic.tenant.model.Reject;
import com.casic.tenant.model.RejectHistory;
import com.casic.tenant.model.TenantInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
@Service
public class RejectService extends BaseService<Reject> {
	@Resource
	private SysOrgRejectDao rejectDao;
	@Resource
	private SysOrgRejectHistoryDao rejectHistoryDao;
	@Resource
	private TenantInfoService tenantInfoService;
	
	@Override
	protected IEntityDao<Reject,Long> getEntityDao() {
		// TODO Auto-generated method stub
		return rejectDao;
	}
	/**
	 * 通过企业Id获得该企业的驳回对象
	 * */
	public List<Reject> getByOrgtenId(String sysId) {
		return rejectDao.getByOrgtenId(sysId);
	}
	/**
	 * 更新或者新增驳回
	 * */
	public boolean updateOrInsert(Reject reject, boolean uporin) {
		boolean flag=true;//成功
		try {
			TenantInfo info = tenantInfoService.getById(reject.getTenantId());
			if(info != null){
				info.setState(Integer.parseInt(reject.getState()));
				tenantInfoService.updateState(info.getState(),info.getSysOrgInfoId());
			}else{
				return false;
			}
			//历史记录
			RejectHistory rejectHistory=new RejectHistory();
			rejectHistory.setId(UniqueIdUtil.genId());
			rejectHistory.setNumber(reject.getNum().longValue());
			rejectHistory.setReasoncase(reject.getReason());
			rejectHistory.setTenantId(reject.getTenantId());
			rejectHistoryDao.insertRejectHistory(rejectHistory);
			flag = rejectDao.updateOrInsert(reject,uporin);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

}
