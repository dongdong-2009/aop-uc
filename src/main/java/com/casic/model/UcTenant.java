package com.casic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户企业批量注册接口
 * @author ypchenga
 *
 */
public class UcTenant {
    private String systemId;//子系统来源
    @NotNullAnnotation
    private String tname;//企业名称
    @NotNullAnnotation
    private String mobile;//管理人员手机号
    private String email;//企业邮箱
    @NotNullAnnotation
    private String industry;//企业行业
    @NotNullAnnotation
    private String province;//企业省
    @NotNullAnnotation
    private String city;//企业市
    @NotNullAnnotation
    private String connecter;//联系人
    @NotNullAnnotation
    private String address;//联系地址
    private String fax;//企业传真
    private String invitedCode;//邀请码
    private String topenId;//企业openId
    private String uopenId;//用户openid
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getConnecter() {
		return connecter;
	}
	public void setConnecter(String connecter) {
		this.connecter = connecter;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getInvitedCode() {
		return invitedCode;
	}
	public void setInvitedCode(String invitedCode) {
		this.invitedCode = invitedCode;
	}
	public String getTopenId() {
		return topenId;
	}
	public void setTopenId(String topenId) {
		this.topenId = topenId;
	}
	public String getUopenId() {
		return uopenId;
	}
	public void setUopenId(String uopenId) {
		this.uopenId = uopenId;
	}
    
	
	
	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("12321");
		list.add("3423");
		list.add("23432");
		list.add("12321");
		System.out.println(list);
		System.out.println(list.toString());
	}
    
    
    
}
