package com.casic.auditLog.model.tenantAudit;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:sys_operation_record Model对象
 * 开发公司:航天科工集团
 * 开发人员:chengyupeng
 * 创建时间:2016-12-05 18:26:53
 */
public class tenantInfoAuditLog extends BaseModel
{
	// id
	protected Long  id;
	// operatorId
	protected Long  operatorid;
	// operatorTime
	protected java.util.Date  operatortime;
	// 操作动作,0提交，1通过，2被驳回
	protected String  operatoraction;
	// rejectReson
	protected String  rejectreson;
	// axnStatusReason
	protected String  axnstatusreason;
	//企业id
	protected Long  tenantId;
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
	public void setOperatorid(Long operatorid) 
	{
		this.operatorid = operatorid;
	}
	/**
	 * 返回 operatorId
	 * @return
	 */
	public Long getOperatorid() 
	{
		return this.operatorid;
	}
	public void setOperatortime(java.util.Date operatortime) 
	{
		this.operatortime = operatortime;
	}
	/**
	 * 返回 operatorTime
	 * @return
	 */
	public java.util.Date getOperatortime() 
	{
		return this.operatortime;
	}
	public void setOperatoraction(String operatoraction) 
	{
		this.operatoraction = operatoraction;
	}
	/**
	 * 返回 操作动作,0提交，1通过，2驳回
	 * @return
	 */
	public String getOperatoraction() 
	{
		return this.operatoraction;
	}
	public void setRejectreson(String rejectreson) 
	{
		this.rejectreson = rejectreson;
	}
	/**
	 * 返回 rejectReson
	 * @return
	 */
	public String getRejectreson() 
	{
		return this.rejectreson;
	}
	public void setAxnstatusreason(String axnstatusreason) 
	{
		this.axnstatusreason = axnstatusreason;
	}
	/**
	 * 返回 axnStatusReason
	 * @return
	 */
	public String getAxnstatusreason() 
	{
		return this.axnstatusreason;
	}
   
   	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof tenantInfoAuditLog)) 
		{
			return false;
		}
		tenantInfoAuditLog rhs = (tenantInfoAuditLog) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.operatorid, rhs.operatorid)
		.append(this.operatortime, rhs.operatortime)
		.append(this.operatoraction, rhs.operatoraction)
		.append(this.rejectreson, rhs.rejectreson)
		.append(this.axnstatusreason, rhs.axnstatusreason)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.operatorid) 
		.append(this.operatortime) 
		.append(this.operatoraction) 
		.append(this.rejectreson) 
		.append(this.axnstatusreason) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("operatorid", this.operatorid) 
		.append("operatortime", this.operatortime) 
		.append("operatoraction", this.operatoraction) 
		.append("rejectreson", this.rejectreson) 
		.append("axnstatusreason", this.axnstatusreason) 
		.toString();
	}
   
  

}