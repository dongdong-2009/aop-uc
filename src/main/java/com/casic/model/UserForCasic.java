package com.casic.model;
/**
 * 为航天云网提供快速注册服务
 * @author ypchenga
 *
 */
public class UserForCasic {
	@NotNullAnnotation
	private String account;// 会员用户名
	@NotNullAnnotation
	private String mobile;// 手机号
	@NotNullAnnotation
	private String password;//用户密码
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserForCasic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserForCasic(String account, String mobile, String password) {
		super();
		this.account = account;
		this.mobile = mobile;
		this.password = password;
	}
	
	
}
