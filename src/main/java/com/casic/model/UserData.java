package com.casic.model;

/**
 * 请求封装用户信息的实体类
 * 
 * @author ypchenga
 *
 */
public class UserData {
	@NotNullAnnotation
	private String userId;// 云平台的用户Id
	@NotNullAnnotation
	private String account;// 用户登陆名
	@NotNullAnnotation
	private String mobile;// 手机号
	private String email;// 邮箱
	@NotNullAnnotation
	private String fullName;// 用户姓名

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
