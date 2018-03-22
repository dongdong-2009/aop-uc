package com.hotent.platform.model.system;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:用户角色映射表 Model对象
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-10 16:50:40
 */
public class SysUserRole extends BaseModel
{
	// 用户角色Id
	protected Long  userroleid;
	// 角色ID
	protected Long  roleid;
	// 用户ID
	protected Long  userid;
	public void setUserroleid(Long userroleid) 
	{
		this.userroleid = userroleid;
	}
	/**
	 * 返回 用户角色Id
	 * @return
	 */
	public Long getUserroleid() 
	{
		return this.userroleid;
	}
	public void setRoleid(Long roleid) 
	{
		this.roleid = roleid;
	}
	/**
	 * 返回 角色ID
	 * @return
	 */
	public Long getRoleid() 
	{
		return this.roleid;
	}
	public void setUserid(Long userid) 
	{
		this.userid = userid;
	}
	/**
	 * 返回 用户ID
	 * @return
	 */
	public Long getUserid() 
	{
		return this.userid;
	}


   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysUserRole)) 
		{
			return false;
		}
		SysUserRole rhs = (SysUserRole) object;
		return new EqualsBuilder()
		.append(this.userroleid, rhs.userroleid)
		.append(this.roleid, rhs.roleid)
		.append(this.userid, rhs.userid)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.userroleid) 
		.append(this.roleid) 
		.append(this.userid) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("userroleid", this.userroleid) 
		.append("roleid", this.roleid) 
		.append("userid", this.userid) 
		.toString();
	}
   
  

}