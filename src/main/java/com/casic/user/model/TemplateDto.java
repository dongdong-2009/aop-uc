package com.casic.user.model;

import java.io.Serializable;
import java.util.Date;

public class TemplateDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String typeId;
	
	private String templateCode;
	
	private String templateValue;
	
	private String opportunity;
	
	private String creater;
	
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateValue() {
		return templateValue;
	}

	public void setTemplateValue(String templateValue) {
		this.templateValue = templateValue;
	}

	public String getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(String opportunity) {
		this.opportunity = opportunity;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TemplateDto [id=" + id + ", typeId=" + typeId + ", templateCode=" + templateCode + ", templateValue="
				+ templateValue + ", opportunity=" + opportunity + ", creater=" + creater + ", createTime=" + createTime
				+ "]";
	}
	
}
