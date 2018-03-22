package com.casic.model;

public class TenantForCasic {
	@NotNullAnnotation
	private String tenantName; //公司名称
	@NotNullAnnotation
	private String connecter; //联系人
    private String email;//企业邮箱
    private String yyzz;//营业执照
    private String yyzzPic;//营业执照图片
    private String province;//企业省
    private String city;//企业的市
    private String info;//企业的简介信息
    
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getYyzz() {
		return yyzz;
	}

	public void setYyzz(String yyzz) {
		this.yyzz = yyzz;
	}

	public String getYyzzPic() {
		return yyzzPic;
	}

	public void setYyzzPic(String yyzzPic) {
		this.yyzzPic = yyzzPic;
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

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getConnecter() {
		return connecter;
	}

	public void setConnecter(String connecter) {
		this.connecter = connecter;
	}
	public TenantForCasic() {
		super();
	}
	public TenantForCasic(String tenantName, String connecter) {
		super();
		this.tenantName = tenantName;
		this.connecter = connecter;
	}

	public TenantForCasic(String tenantName, String connecter, String email,
			String yyzz, String yyzzPic, String province, String city,
			String info) {
		super();
		this.tenantName = tenantName;
		this.connecter = connecter;
		this.email = email;
		this.yyzz = yyzz;
		this.yyzzPic = yyzzPic;
		this.province = province;
		this.city = city;
		this.info = info;
	}
	
	
	
	
}
