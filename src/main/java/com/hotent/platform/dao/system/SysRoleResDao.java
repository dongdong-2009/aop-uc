package com.hotent.platform.dao.system;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysRoleRes;
/**
 *<pre>
 * 对象功能:角色资源映射 Dao类
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-13 10:48:10
 *</pre>
 */
@Repository
public class SysRoleResDao extends BaseDao<SysRoleRes>
{
	@Override
	public Class<?> getEntityClass()
	{
		return SysRoleRes.class;
	}

	public List<SysRoleRes> getSysRoleResByRoleId(Long roleid) {
		// TODO Auto-generated method stub
		return getBySqlKey("getSysRoleResByRoleId", roleid);
	}

}