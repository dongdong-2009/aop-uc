package com.casic.model;
/**
 * 用来封装企业信息
 * @author ypchenga
 *
 */
public class TenantData {
	@NotNullAnnotation
	private String tenantId;//云平台企业id 必填
	@NotNullAnnotation
	private String tenantName; //企业名 必填
	private String address;// 公司地址, /通信地址 
	@NotNullAnnotation
    private String connecter;//"联系人",/单位联系人
	@NotNullAnnotation
    private String tel;//"手机号",/单位联系人手机
    private String city;//"市",/归属地市
	@NotNullAnnotation
    private String code;//组织机构代码
    private String registertime;//注册时间
    private String regCapital;//注册资本
    private String incorporator;//"法人",/单位法人
    private String gszch;//工商注册号
    private String nsrsbh;//"纳税人识别号",/税务登记号(可以为空)
	private String creditCode;//统一社会信用编码
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConnecter() {
		return connecter;
	}
	public void setConnecter(String connecter) {
		this.connecter = connecter;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRegistertime() {
		return registertime;
	}
	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}
	public String getRegCapital() {
		return regCapital;
	}
	public void setRegCapital(String regCapital) {
		this.regCapital = regCapital;
	}
	public String getIncorporator() {
		return incorporator;
	}
	public void setIncorporator(String incorporator) {
		this.incorporator = incorporator;
	}
	public String getGszch() {
		return gszch;
	}
	public void setGszch(String gszch) {
		this.gszch = gszch;
	}
	public String getNsrsbh() {
		return nsrsbh;
	}
	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
    
    
    
}
