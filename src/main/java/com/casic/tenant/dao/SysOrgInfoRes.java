package com.casic.tenant.dao;
/**
 * 该类作为导入结果信息的提示类
 * @author ypchenga
 *
 */
public class SysOrgInfoRes {
	private boolean result;//导入的结果
	private Integer number;//对应于导入行
	private String emailCode;//对应于email的导入结果
	private String nameCode;//对应存放名称的导入结果
	private String industryCode;//行业一的导入结果
	private String industry2Code;//行业2的导入结果
	private String countryCode;//国家的导入结果
	private String provinceCode;//省的导入结果
	private String cityCode;//城市的导入结果
	private String inviteCode;//邀请码的导入结构
	private String addressCode;//地址的导入结果
	private String postcodeCode;//邮编的导入结果
	private String connectorCode;//企业联系人的导入结果
	private String faxCode;//对传真的导入结果
	private String mobileCode;//对手机号的导入结果
	private String systemIdCode;//对系统来源的导入结果
	private String topenIdCode;//对企业openId的导入结果
	private String uopenIdCode;//对用户openId的导入结果
	private int num;//没有导入成功的数量
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getEmailCode() {
		return emailCode;
	}
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
	public String getNameCode() {
		return nameCode;
	}
	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getIndustry2Code() {
		return industry2Code;
	}
	public void setIndustry2Code(String industry2Code) {
		this.industry2Code = industry2Code;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getPostcodeCode() {
		return postcodeCode;
	}
	public void setPostcodeCode(String postcodeCode) {
		this.postcodeCode = postcodeCode;
	}
	public String getConnectorCode() {
		return connectorCode;
	}
	public void setConnectorCode(String connectorCode) {
		this.connectorCode = connectorCode;
	}
	public String getFaxCode() {
		return faxCode;
	}
	public void setFaxCode(String faxCode) {
		this.faxCode = faxCode;
	}
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	
	
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	
	
	
	
	public String getSystemIdCode() {
		return systemIdCode;
	}
	public void setSystemIdCode(String systemIdCode) {
		this.systemIdCode = systemIdCode;
	}
	
	public String getTopenIdCode() {
		return topenIdCode;
	}
	public void setTopenIdCode(String topenIdCode) {
		this.topenIdCode = topenIdCode;
	}
	public String getUopenIdCode() {
		return uopenIdCode;
	}
	public void setUopenIdCode(String uopenIdCode) {
		this.uopenIdCode = uopenIdCode;
	}
	public SysOrgInfoRes() {
		super();
		
	}
	
	
	
	
	
	
	

}

  