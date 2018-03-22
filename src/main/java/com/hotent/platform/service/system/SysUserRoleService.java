package com.hotent.platform.service.system;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.SysUserRoleDao;
import com.hotent.platform.model.system.SysUserRole;

/**
 *<pre>
 * 对象功能:用户角色映射表 Service类
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-10 16:50:40
 *</pre>
 */
@Service
public class SysUserRoleService extends BaseService<SysUserRole>
{
	@Resource
	private SysUserRoleDao dao;
	
	
	
	public SysUserRoleService()
	{
	}
	
	@Override
	protected IEntityDao<SysUserRole, Long> getEntityDao() 
	{
		return dao;
	}

	public List<SysUserRole> getSysUserRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		return dao.getSysUserRoleByUserId(userId);
	}


	
	
}
