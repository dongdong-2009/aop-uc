package com.casic.log.model.config;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:sys_ah_record Model对象
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2017-01-05 19:42:23
 */
public class AhRecord extends BaseModel
{
	// id
	protected Long  id;
	// 调用时间
	protected java.util.Date  calltime;
	// 是否成功0否
	protected String  issuccess;
	// 调用地址
	protected String  addr;
	// failReason
	protected String  failreason;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 id
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setCalltime(java.util.Date calltime) 
	{
		this.calltime = calltime;
	}
	/**
	 * 返回 调用时间
	 * @return
	 */
	public java.util.Date getCalltime() 
	{
		return this.calltime;
	}
	public void setIssuccess(String issuccess) 
	{
		this.issuccess = issuccess;
	}
	/**
	 * 返回 是否成功0否
	 * @return
	 */
	public String getIssuccess() 
	{
		return this.issuccess;
	}
	public void setAddr(String addr) 
	{
		this.addr = addr;
	}
	/**
	 * 返回 调用地址
	 * @return
	 */
	public String getAddr() 
	{
		return this.addr;
	}
	public void setFailreason(String failreason) 
	{
		this.failreason = failreason;
	}
	/**
	 * 返回 failReason
	 * @return
	 */
	public String getFailreason() 
	{
		return this.failreason;
	}


   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AhRecord)) 
		{
			return false;
		}
		AhRecord rhs = (AhRecord) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.calltime, rhs.calltime)
		.append(this.issuccess, rhs.issuccess)
		.append(this.addr, rhs.addr)
		.append(this.failreason, rhs.failreason)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.calltime) 
		.append(this.issuccess) 
		.append(this.addr) 
		.append(this.failreason) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("calltime", this.calltime) 
		.append("issuccess", this.issuccess) 
		.append("addr", this.addr) 
		.append("failreason", this.failreason) 
		.toString();
	}
   
  

}