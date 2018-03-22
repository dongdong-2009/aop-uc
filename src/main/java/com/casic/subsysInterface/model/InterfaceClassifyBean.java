package com.casic.subsysInterface.model;




import java.util.Date;

import com.hotent.core.model.BaseModel;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * 对象功能:接口分类
 * 开发公司:航天智造
 */
public class InterfaceClassifyBean extends BaseModel
{
	// id
	protected Long  id;
	// 分类名
	protected String  name;
	// 英文名
	protected String  ename;
	// 创建时间
	protected Date  createTime;
	
	protected String remark1;
	
	protected String remark2;
	
	protected String remark3;
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.ename) 
		.append(this.createTime) 
		.append(this.remark1) 
		.append(this.remark2) 
		.append(this.remark3) 
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
		.append("ename",this.ename) 
		.append("createTime",this.createTime) 
		.append("remark1",this.remark1) 
		.append("remark2",this.remark2) 
		.append("remark3",this.remark3) 
		.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
  
}