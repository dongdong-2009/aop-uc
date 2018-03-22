package com.casic.model;

public class UcDataParam {
	private String systemId;
    private UserData user;
    private TenantData tenant;
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public UserData getUser() {
		return user;
	}
	public void setUser(UserData user) {
		this.user = user;
	}
	public TenantData getTenant() {
		return tenant;
	}
	public void setTenant(TenantData tenant) {
		this.tenant = tenant;
	}
	
    
    
}
