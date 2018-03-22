package com.casic.subsysInterface.model;




import java.util.Date;

import com.hotent.core.model.BaseModel;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * 对象功能:接口地址
 * 开发公司:航天智造
 */
public class InterfaceUrlBean extends BaseModel
{
	// id
	protected Long  id;
	// 子系统id
	protected Long  subId;
	// 子系统名
	protected String  subSystemName;
	// 接口分类id
	protected Long  interfaceClassifyId;
	// 接口分类名
	protected String  interfaceClassifyName;
	// type(增、删、改)
	protected int  type;
	// 创建时间
	protected Date  createTime;
	
	protected String url;
	
	protected String subIndexUrl;
	
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
		.append(this.subId)
		.append(this.subSystemName)
		.append(this.interfaceClassifyId) 
		.append(this.interfaceClassifyName) 
		.append(this.type) 
		.append(this.createTime) 
		.append(this.url)
		.append(this.subIndexUrl)
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
		.append("subId", this.subId) 
		.append("subSystemName", this.subSystemName) 
		.append("interfaceClassifyId",this.interfaceClassifyId)
		.append("interfaceClassifyName",this.interfaceClassifyName)
		.append("type",this.type)
		.append("createTime",this.createTime) 
		.append("url",this.url) 
		.append("subIndexUrl",this.subIndexUrl) 
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

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public Long getInterfaceClassifyId() {
		return interfaceClassifyId;
	}

	public void setInterfaceClassifyId(Long interfaceClassifyId) {
		this.interfaceClassifyId = interfaceClassifyId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getSubSystemName() {
		return subSystemName;
	}

	public void setSubSystemName(String subSystemName) {
		this.subSystemName = subSystemName;
	}

	public String getInterfaceClassifyName() {
		return interfaceClassifyName;
	}

	public void setInterfaceClassifyName(String interfaceClassifyName) {
		this.interfaceClassifyName = interfaceClassifyName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSubIndexUrl() {
		return subIndexUrl;
	}

	public void setSubIndexUrl(String subIndexUrl) {
		this.subIndexUrl = subIndexUrl;
	}
	
	
	
}