package com.casic.tenant.model;

import com.hotent.core.model.BaseModel;

public class RejectHistory extends BaseModel {
	private Long id;
	private Long tenantId;
	private String reasoncase;
	private Long number;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getReasoncase() {
		return reasoncase;
	}
	public void setReasoncase(String reasoncase) {
		this.reasoncase = reasoncase;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}

}
