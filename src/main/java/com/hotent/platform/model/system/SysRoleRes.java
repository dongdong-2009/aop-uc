package com.hotent.platform.model.system;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:角色资源映射 Model对象
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-13 10:48:10
 */
public class SysRoleRes extends BaseModel
{
	// 角色资源Id
	protected Long  roleresid;
	// roleId
	protected Long  roleid;
	// 资源主键
	protected Long  resid;
	// 系统ID
	protected Long  systemid;
	public void setRoleresid(Long roleresid) 
	{
		this.roleresid = roleresid;
	}
	/**
	 * 返回 角色资源Id
	 * @return
	 */
	public Long getRoleresid() 
	{
		return this.roleresid;
	}
	public void setRoleid(Long roleid) 
	{
		this.roleid = roleid;
	}
	/**
	 * 返回 roleId
	 * @return
	 */
	public Long getRoleid() 
	{
		return this.roleid;
	}
	public void setResid(Long resid) 
	{
		this.resid = resid;
	}
	/**
	 * 返回 资源主键
	 * @return
	 */
	public Long getResid() 
	{
		return this.resid;
	}
	public void setSystemid(Long systemid) 
	{
		this.systemid = systemid;
	}
	/**
	 * 返回 系统ID
	 * @return
	 */
	public Long getSystemid() 
	{
		return this.systemid;
	}


   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysRoleRes)) 
		{
			return false;
		}
		SysRoleRes rhs = (SysRoleRes) object;
		return new EqualsBuilder()
		.append(this.roleresid, rhs.roleresid)
		.append(this.roleid, rhs.roleid)
		.append(this.resid, rhs.resid)
		.append(this.systemid, rhs.systemid)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.roleresid) 
		.append(this.roleid) 
		.append(this.resid) 
		.append(this.systemid) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("roleresid", this.roleresid) 
		.append("roleid", this.roleid) 
		.append("resid", this.resid) 
		.append("systemid", this.systemid) 
		.toString();
	}
   
  

}