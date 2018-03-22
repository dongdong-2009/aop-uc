package com.casic.tenant.service;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.tenant.dao.BranchBeanDao;
import com.casic.tenant.model.BranchBean;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;

/**
 *<pre>
 * 对象功能:branch Service类
 * 开发公司:航天科工
 * 开发人员:msq
 * 创建时间:2016-04-05 10:35:43
 *</pre>
 */
@Service
public class BranchBeanService extends BaseService<BranchBean>
{
	@Resource
	private BranchBeanDao dao;
	
	
	
	public BranchBeanService()
	{
	}
	
	@Override
	protected IEntityDao<BranchBean, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<BranchBean> getByOrgid(Long  orgid){
		return dao.getByOrgid(orgid);
	}

	public List<BranchBean> getAllOrgIds() {
		return dao.getAllOrgIds();
	}

	/**
	 * 查看账户是否存在，存在更新，不存在添加
	 * @param branchBean
	 */
	public void updateBranch(BranchBean branchBean) {
		BranchBean bean = dao.getById(branchBean.getId());
		if(bean == null){
			dao.add(branchBean);
		}else{
			dao.update(branchBean);
		}
	}
	
	/**
	 * 查看账户是否存在，存在更新，不存在添加
	 * @param branchBean
	 */
	public void updateOpenCloseAccount(BranchBean branchBean) {
		BranchBean bean = dao.getById(branchBean.getId());
		if(bean == null){
			dao.addOpenCloseAccount(branchBean);
		}else{
			dao.updateOpenCloseAccount(branchBean);
		}
	}

	public void updateBranchAccount(BranchBean branchBean) {
		BranchBean bean = dao.getById(branchBean.getId());
		dao.updateBranchAccount(branchBean);
		
	}

	public void updateStatus(Long id, String status) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("stlstate", status);
		dao.getBySqlKey("updateStatus", params);
		
	}
	
	public void updateOpenCloseAccstate(Long id, String accstate) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("accstate", accstate);
		dao.getBySqlKey("updateOpenCloseAccstate", params);
		
	}
}
