package com.hotent.platform.dao.system;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysUserRole;
/**
 *<pre>
 * 对象功能:用户角色映射表 Dao类
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-10 16:50:40
 *</pre>
 */
@Repository
public class SysUserRoleDao extends BaseDao<SysUserRole>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SysUserRole.class;
	}

	public List<SysUserRole> getSysUserRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		return getBySqlKey("getSysUserRoleByUserId", userId);
	}

}