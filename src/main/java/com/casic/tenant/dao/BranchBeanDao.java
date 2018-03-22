package com.casic.tenant.dao;




import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.tenant.model.BranchBean;
import com.hotent.core.db.BaseDao;
/**
 *<pre>
 * 对象功能:branch Dao类
 * 开发公司:航天科工
 * 开发人员:msq
 * 创建时间:2016-04-05 10:35:43
 *</pre>
 */
@Repository
public class BranchBeanDao extends BaseDao<BranchBean>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BranchBean.class;
	}
	
	public List<BranchBean> getByOrgid(Long  orgid){
		return (List<BranchBean>) this.getSqlSessionTemplate().selectList("com.casic.tenant.model.BranchBean.getByOrgid", orgid);
	}

	public List<BranchBean> getAllOrgIds() {
		return (List<BranchBean>)this.getSqlSessionTemplate().selectList("getAllOrgIds");
	}
	
	public void addOpenCloseAccount(BranchBean branchBean){
		this.getSqlSessionTemplate().insert("com.casic.tenant.model.BranchBean.addOpenCloseAccount", branchBean);
	}
	
	public void updateOpenCloseAccount(BranchBean branchBean){
		
		this.getSqlSessionTemplate().update("com.casic.tenant.model.BranchBean.updateOpenCloseAccount", branchBean);
		
	}

	public void updateBranchAccount(BranchBean branchBean) {
	
		 this.getSqlSessionTemplate().update("updateBranchAccount", branchBean);
	}

}