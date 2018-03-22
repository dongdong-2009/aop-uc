package com.casic.invitited.model;

import java.io.Serializable;

import com.casic.tenant.model.TenantInfo;

public class InvitiedEntity implements Serializable{
	

	private static final long serialVersionUID = 1L;

	private Long totalRegistered;//注册总数
	
	private Long certificationNumber;//完成企业认证数

	public Long getTotalRegistered() {
		return totalRegistered;
	}

	public void setTotalRegistered(Long totalRegistered) {
		this.totalRegistered = totalRegistered;
	}

	public Long getCertificationNumber() {
		return certificationNumber;
	}

	public void setCertificationNumber(Long certificationNumber) {
		this.certificationNumber = certificationNumber;
	}

	public InvitiedEntity() {
		super();
	}
	
	
	
	

}
