package com.casic.model;


import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_charge_set Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-10-22 12:45:53
 */
public class ChargeSet extends BaseModel
{
	// 套餐ID
	protected Long  id;
	// 套餐名称
	protected String  name;
	// 起始时间
	protected java.util.Date  starttime;
	// 结束时间
	protected java.util.Date  endtime;
 
	// 用户数限制
	protected String  usernum;
	// 套餐说明
	protected String  remark;
	// 资费标准
	protected String  standard;
	// 套餐类型1按用户2按功能3-按商友
	protected String  type;
	
	// 正常，作废
		protected String  state;
		
		
	public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 套餐ID
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 套餐名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setStarttime(java.util.Date starttime) 
	{
		this.starttime = starttime;
	}
	
 
 
 
	/**
	 * 返回 起始时间
	 * @return
	 */
	public java.util.Date getStarttime() 
	{
		return this.starttime;
	}
	public void setEndtime(java.util.Date endtime) 
	{
		this.endtime = endtime;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	public java.util.Date getEndtime() 
	{
		return this.endtime;
	}
	public void setUsernum(String usernum) 
	{
		this.usernum = usernum;
	}
	/**
	 * 返回 用户数限制
	 * @return
	 */
	public String getUsernum() 
	{
		return this.usernum;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 套餐说明
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}
	public void setStandard(String standard) 
	{
		this.standard = standard;
	}
	/**
	 * 返回 资费标准
	 * @return
	 */
	public String getStandard() 
	{
		return this.standard;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 套餐类型1按用户2按功能3-按商友
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}


   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ChargeSet)) 
		{
			return false;
		}
		ChargeSet rhs = (ChargeSet) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.starttime, rhs.starttime)
		.append(this.endtime, rhs.endtime)
		.append(this.usernum, rhs.usernum)
		.append(this.remark, rhs.remark)
		.append(this.standard, rhs.standard)
		.append(this.type, rhs.type)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.starttime) 
		.append(this.endtime) 
		.append(this.usernum) 
		.append(this.remark) 
		.append(this.standard) 
		.append(this.type) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("starttime", this.starttime) 
		.append("endtime", this.endtime) 
		.append("usernum", this.usernum) 
		.append("remark", this.remark) 
		.append("standard", this.standard) 
		.append("type", this.type) 
		.toString();
	}
   
  

}
