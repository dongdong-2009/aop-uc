package com.casic.user.model;

import java.io.Serializable;

public class Authorize  implements Serializable{

	/**
	 * 授权登录对象
	 */
	private static final long serialVersionUID = 1L;
	
	private String personalAccount;
	private String personalPassword;
	private String orgId;
	private String orgName;
	private String orgPassword;
	private String authorizeUrl;
	
	
	public String getPersonalAccount() {
		return personalAccount;
	}
	public void setPersonalAccount(String personalAccount) {
		this.personalAccount = personalAccount;
	}
	public String getPersonalPassword() {
		return personalPassword;
	}
	public void setPersonalPassword(String personalPassword) {
		this.personalPassword = personalPassword;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgPassword() {
		return orgPassword;
	}
	public void setOrgPassword(String orgPassword) {
		this.orgPassword = orgPassword;
	}
	public String getAuthorizeUrl() {
		return authorizeUrl;
	}
	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}
	
	
}
