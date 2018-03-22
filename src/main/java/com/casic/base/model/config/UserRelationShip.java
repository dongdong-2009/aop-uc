package com.casic.base.model.config;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:sys_userRelationship_mapping Model对象
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-30 13:57:25
 */
public class UserRelationShip extends BaseModel
{
	// id
	protected Long  id;
	// 合作企业Id
	protected String  partnerId;
	// 合作企业用户Id
	protected String  partnerUserId;
	
	protected Long  userId;
	// 云网绑定的账号
	protected String  account;
	// 申请合作时间
	protected java.util.Date  applyTime;
	// 合作状态0-待合作1----合作成功2---合作失败
	protected String  status;
	// 系统来源
	protected long fromsystemid;
	
	public long getFromsystemid() {
		return fromsystemid;
	}
	public void setFromsystemid(long fromsystemid) {
		this.fromsystemid = fromsystemid;
	}
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
	public void setPartnerUserId(String partnerUserId) 
	{
		this.partnerUserId = partnerUserId;
	}
	/**
	 * 返回 合作企业用户Id
	 * @return
	 */
	public String getPartnerUserId() 
	{
		return this.partnerUserId;
	}
	public void setAccount(String account) 
	{
		this.account = account;
	}
	/**
	 * 返回 云网绑定的账号
	 * @return
	 */
	public String getAccount() 
	{
		return this.account;
	}
	public void setApplyTime(java.util.Date applyTime) 
	{
		this.applyTime = applyTime;
	}
	/**
	 * 返回 申请合作时间
	 * @return
	 */
	public java.util.Date getApplyTime() 
	{
		return this.applyTime;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 合作状态0-待合作1----合作成功2---合作失败
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}

   	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof UserRelationShip)) 
		{
			return false;
		}
		UserRelationShip rhs = (UserRelationShip) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.partnerId, rhs.partnerId)
		.append(this.partnerUserId, rhs.partnerUserId)
		.append(this.account, rhs.account)
		.append(this.applyTime, rhs.applyTime)
		.append(this.status, rhs.status)
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
		.append(this.partnerUserId) 
		.append(this.account) 
		.append(this.applyTime) 
		.append(this.status) 
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
		.append("partnerUserId", this.partnerUserId) 
		.append("account", this.account) 
		.append("applyTime", this.applyTime) 
		.append("status", this.status) 
		.toString();
	}
   
  

}