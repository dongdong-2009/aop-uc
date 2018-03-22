package com.hotent.platform.service.system;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.SysRoleResDao;
import com.hotent.platform.model.system.SysRoleRes;

/**
 *<pre>
 * 对象功能:角色资源映射 Service类
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-13 10:48:10
 *</pre>
 */
@Service
public class SysRoleResService extends BaseService<SysRoleRes>
{
	@Resource
	private SysRoleResDao dao;
	
	
	
	public SysRoleResService()
	{
	}
	
	@Override
	protected IEntityDao<SysRoleRes, Long> getEntityDao() 
	{
		return dao;
	}

	public List<SysRoleRes> getSysRoleResByRoleId(Long roleid) {
		// TODO Auto-generated method stub
		return dao.getSysRoleResByRoleId(roleid);
	}

	
	
}
