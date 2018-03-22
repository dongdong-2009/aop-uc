package com.casic.tenant.dto;

import java.util.Date;

import com.casic.tenant.model.BranchBean;
import com.casic.util.DateUtil;
import com.hotent.core.util.UniqueIdUtil;

public class BranchDto {
	
	private Long branchId;
	private Long  orgid;
	private String sysid;
	//客户性质
	private String clientProperty;
	
	private String branchaccountname;
	
	private String credentialsType;
	private String credentialsNumber;
	private String orgCode;

	private String businessLicense;
	private String taxId;
	//业务表示
	private String businessFlag;
	private String accountType1;
	private String accstate;
	private String flag;
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getClientProperty() {
		return clientProperty;
	}
	public void setClientProperty(String clientProperty) {
		this.clientProperty = clientProperty;
	}
	public String getBranchaccountname() {
		return branchaccountname;
	}
	public void setBranchaccountname(String branchaccountname) {
		this.branchaccountname = branchaccountname;
	}
	public String getCredentialsType() {
		return credentialsType;
	}
	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}
	public String getCredentialsNumber() {
		return credentialsNumber;
	}
	public void setCredentialsNumber(String credentialsNumber) {
		this.credentialsNumber = credentialsNumber;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getBusinessFlag() {
		return businessFlag;
	}
	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}
	public String getAccountType1() {
		return accountType1;
	}
	public void setAccountType1(String accountType1) {
		this.accountType1 = accountType1;
	}
	public String getAccstate() {
		return accstate;
	}
	public void setAccstate(String accstate) {
		this.accstate = accstate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public BranchDto() {
		super();
	}
	
	
	
	
	
	
	
	
	

}
