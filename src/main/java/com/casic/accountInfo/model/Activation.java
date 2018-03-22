package com.casic.accountInfo.model;

import java.util.Date;

import com.casic.util.OpenIdUtil;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.UniqueIdUtil;

public class Activation {
	/*
	 * newuser.setAccount(newAccount);
				newuser.setFullname(msg.getStUName());
				newuser.setSex("1");
				
				
				long newId=UniqueIdUtil.genId();
				newuser.setUserId(newId);
				newuser.setPassword(EncryptUtil.encryptSha256("123456"));
				newuser.setOrgId(orgship.getEntpId());
				newuser.setOrgSn(orgship.getEntpId());
				newuser.setCreatetime(new Date());
				newuser.setIsLock((short)0);
				newuser.setIsExpired((short)0);
				newuser.setStatus((short)1);
				newuser.setOpenId(OpenIdUtil.getOpenId());
				sysuserservice.add(newuser);
	 * 
	 * 	params.put("topenId", topenId);
				params.put("tsn", tsn);
				params.put("tname", tname);
				params.put("uopenId", uopenId);
				params.put("uname", uname);
				params.put("pwd", pwd);
				params.put("mobile", mobile);
				params.put("email", email);
				params.put("state", state);
				params.put("fullName", fullName);
				params.put("systemId", systemId);
				params.put("fromSysId", fromSysId);
	 */
	private long  orgId ;
	private long userId;
	private String password;
	private String orgName;
	private String account;
	private String mobile;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	private String connecter;
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getConnecter() {
		return connecter;
	}
	public void setConnecter(String connecter) {
		this.connecter = connecter;
	}
	
	
}
