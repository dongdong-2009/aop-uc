package com.casic.base.model.config;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:sys_orgRelationShip_mapping Model对象
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-30 14:02:38
 */
public class OrgRelationShip extends BaseModel
{
	// id
	protected Long  id;
	// 合作企业Id
	protected String  partnerId;
	// 航天云网企业Id
	protected Long  entpId;
	// 合作状态0-待合作  1-合作成功 2--合作失败
	protected String  status;
	// APPLY_TIME
	protected java.util.Date  applyTime;
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
	public void setPartnerId(String partnerId) 
	{
		this.partnerId = partnerId;
	}
	/**
	 * 返回 合作企业Id
	 * @return
	 */
	public String getPartnerId() 
	{
		return this.partnerId;
	}
	public void setEntpId(Long entpId) 
	{
		this.entpId = entpId;
	}
	/**
	 * 返回 航天云网企业Id
	 * @return
	 */
	public Long getEntpId() 
	{
		return this.entpId;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 合作状态0-待合作  1-合作成功 2--合作失败
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setApplyTime(java.util.Date applyTime) 
	{
		this.applyTime = applyTime;
	}
	/**
	 * 返回 APPLY_TIME
	 * @return
	 */
	public java.util.Date getApplyTime() 
	{
		return this.applyTime;
	}


   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof OrgRelationShip)) 
		{
			return false;
		}
		OrgRelationShip rhs = (OrgRelationShip) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.partnerId, rhs.partnerId)
		.append(this.entpId, rhs.entpId)
		.append(this.status, rhs.status)
		.append(this.applyTime, rhs.applyTime)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.partnerId) 
		.append(this.entpId) 
		.append(this.status) 
		.append(this.applyTime) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("partnerId", this.partnerId) 
		.append("entpId", this.entpId) 
		.append("status", this.status) 
		.append("applyTime", this.applyTime) 
		.toString();
	}
   
  

}