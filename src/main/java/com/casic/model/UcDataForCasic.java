package com.casic.model;

public class UcDataForCasic {
	private String systemId;
	private UserForCasic user;
	private TenantForCasic tenant;
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public UserForCasic getUser() {
		return user;
	}
	public void setUser(UserForCasic user) {
		this.user = user;
	}
	public TenantForCasic getTenant() {
		return tenant;
	}
	public void setTenant(TenantForCasic tenant) {
		this.tenant = tenant;
	}
	

}
