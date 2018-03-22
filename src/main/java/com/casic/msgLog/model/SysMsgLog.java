package com.casic.msgLog.model;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:sys_msg_log Model对象
 * 开发公司:航天科工集团
 * 开发人员:张旭
 * 创建时间:2017-11-27 18:08:38
 */
public class SysMsgLog extends BaseModel
{
	// id
	protected Long  id;
	// operation
	protected String  operation;
	// sendRersonal
	protected String  sendrersonal;
	// receiveRersonal
	protected String  receiverersonal;
	// sendTime
	protected java.util.Date  sendtime;
	// sendContent
	protected String  sendcontent;
	// remark1
	protected String  remark1;
	// remark2
	protected String  remark2;
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
	public void setOperation(String operation) 
	{
		this.operation = operation;
	}
	/**
	 * 返回 operation
	 * @return
	 */
	public String getOperation() 
	{
		return this.operation;
	}
	public void setSendrersonal(String sendrersonal) 
	{
		this.sendrersonal = sendrersonal;
	}
	/**
	 * 返回 sendRersonal
	 * @return
	 */
	public String getSendrersonal() 
	{
		return this.sendrersonal;
	}
	public void setReceiverersonal(String receiverersonal) 
	{
		this.receiverersonal = receiverersonal;
	}
	/**
	 * 返回 receiveRersonal
	 * @return
	 */
	public String getReceiverersonal() 
	{
		return this.receiverersonal;
	}
	public void setSendtime(java.util.Date sendtime) 
	{
		this.sendtime = sendtime;
	}
	/**
	 * 返回 sendTime
	 * @return
	 */
	public java.util.Date getSendtime() 
	{
		return this.sendtime;
	}
	public void setSendcontent(String sendcontent) 
	{
		this.sendcontent = sendcontent;
	}
	/**
	 * 返回 sendContent
	 * @return
	 */
	public String getSendcontent() 
	{
		return this.sendcontent;
	}
	public void setRemark1(String remark1) 
	{
		this.remark1 = remark1;
	}
	/**
	 * 返回 remark1
	 * @return
	 */
	public String getRemark1() 
	{
		return this.remark1;
	}
	public void setRemark2(String remark2) 
	{
		this.remark2 = remark2;
	}
	/**
	 * 返回 remark2
	 * @return
	 */
	public String getRemark2() 
	{
		return this.remark2;
	}


   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysMsgLog)) 
		{
			return false;
		}
		SysMsgLog rhs = (SysMsgLog) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.operation, rhs.operation)
		.append(this.sendrersonal, rhs.sendrersonal)
		.append(this.receiverersonal, rhs.receiverersonal)
		.append(this.sendtime, rhs.sendtime)
		.append(this.sendcontent, rhs.sendcontent)
		.append(this.remark1, rhs.remark1)
		.append(this.remark2, rhs.remark2)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.operation) 
		.append(this.sendrersonal) 
		.append(this.receiverersonal) 
		.append(this.sendtime) 
		.append(this.sendcontent) 
		.append(this.remark1) 
		.append(this.remark2) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("operation", this.operation) 
		.append("sendrersonal", this.sendrersonal) 
		.append("receiverersonal", this.receiverersonal) 
		.append("sendtime", this.sendtime) 
		.append("sendcontent", this.sendcontent) 
		.append("remark1", this.remark1) 
		.append("remark2", this.remark2) 
		.toString();
	}
   
  

}