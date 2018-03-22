package com.casic.tenant.model;




import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:开户信息
 * 开发公司:航天科工
 */
public class BranchBean extends BaseModel
{
	// id
	protected Long  id;
	// 商户号
	protected String  merchants;
	// 分支机构名称
	protected String  branchname;
	// 开户行账号
	protected String  bankaccount;
	// 开户行名称（全称）
	protected String  bankfullname;
	// 分支机构账户名称
	protected String  branchaccountname;
	// 账户所在城市（直辖市、地级市）
	protected String  city;
	// 操作员号
	protected String  operatordesign;
	//备注
	protected String  note;
	// 账户所在省
	protected String  province;
	// 企业id
	protected Long  orgid;
	//账户类型，0代表为对公账户，为1为对私账户
	protected String  accountType;
	//开户行简称
	protected String  branchAbbr;
	//支付渠道
	protected String  channelId;
	//支付渠道
	protected String  state;
	//开户结算状态
	protected String accstate;//0:待审核 1:审核通过 2:审核失败 3:待修改审核 4:待删除审核 5:删除成功 6:冻结'
	
	protected String stlstate;//0:待审核 1:审核通过 2:审核失败 3:待修改审核 4:待删除审核 5:删除成功 6:冻结'
	protected String  identityType;//开户证件类型
	
	
	protected String provCode;//省编
	protected String cityCode;
	
	//客户性质
	protected String clientProperty;
	//证件类型
	protected String credentialsType;
	//证件号
	protected String credentialsNumber;
	//业务功能标示  1开户2修改
	protected String businessFlag;
	//账户类型  1客户
	protected String accountType1;
	//账户状态  0:待审核 1:审核通过 2:审核失败 3:待修改审核 4:待删除审核 5:删除成功 6:冻结'
	
	private String flag;
	
	
	
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getClientProperty() {
		return clientProperty;
	}
	public void setClientProperty(String clientProperty) {
		this.clientProperty = clientProperty;
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
	
	
	protected String bankId;//银行编号;
	
	protected String cdNo;//银行编号;
	protected String openbkcd;//分支行清算行号
	
	protected String fcflg;//业务功能表示1绑定2变更
	
	public String getAccstate() {
		return accstate;
	}
	public void setAccstate(String accstate) {
		this.accstate = accstate;
	}
	public String getStlstate() {
		return stlstate;
	}
	public void setStlstate(String stlstate) {
		this.stlstate = stlstate;
	}
	public String getFcflg() {
		return fcflg;
	}
	public void setFcflg(String fcflg) {
		this.fcflg = fcflg;
	}
	public String getOpenbkcd() {
		return openbkcd;
	}
	public void setOpenbkcd(String openbkcd) {
		this.openbkcd = openbkcd;
	}
	public String getCdNo() {
		return cdNo;
	}
	public void setCdNo(String cdNo) {
		this.cdNo = cdNo;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBranchAbbr() {
		return branchAbbr;
	}
	public void setBranchAbbr(String branchAbbr) {
		this.branchAbbr = branchAbbr;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 id
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setMerchants(String merchants) 
	{
		this.merchants = merchants;
	}
	/**
	 * 返回 商户号
	 * @return
	 */
	public String getMerchants() 
	{
		return this.merchants;
	}
	public void setBranchname(String branchname) 
	{
		this.branchname = branchname;
	}
	/**
	 * 返回 分支机构名称
	 * @return
	 */
	public String getBranchname() 
	{
		return this.branchname;
	}
	public void setBankaccount(String bankaccount) 
	{
		this.bankaccount = bankaccount;
	}
	/**
	 * 返回 开户行账号
	 * @return
	 */
	public String getBankaccount() 
	{
		return this.bankaccount;
	}
	public void setBankfullname(String bankfullname) 
	{
		this.bankfullname = bankfullname;
	}
	/**
	 * 返回 开户行名称（全称）
	 * @return
	 */
	public String getBankfullname() 
	{
		return this.bankfullname;
	}
	public void setBranchaccountname(String branchaccountname) 
	{
		this.branchaccountname = branchaccountname;
	}
	/**
	 * 返回 分支机构账户名称
	 * @return
	 */
	public String getBranchaccountname() 
	{
		return this.branchaccountname;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}
	/**
	 * 返回 账户所在城市（直辖市、地级市）
	 * @return
	 */
	public String getCity() 
	{
		return this.city;
	}
	public void setOperatordesign(String operatordesign) 
	{
		this.operatordesign = operatordesign;
	}
	/**
	 * 返回 操作员号
	 * @return
	 */
	public String getOperatordesign() 
	{
		return this.operatordesign;
	}
	public void setOrgid(Long orgid) 
	{
		this.orgid = orgid;
	}
	/**
	 * 返回 企业id
	 * @return
	 */
	public Long getOrgid() 
	{
		return this.orgid;
	}


   
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BranchBean)) 
		{
			return false;
		}
		BranchBean rhs = (BranchBean) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.merchants, rhs.merchants)
		.append(this.branchname, rhs.branchname)
		.append(this.bankaccount, rhs.bankaccount)
		.append(this.bankfullname, rhs.bankfullname)
		.append(this.branchaccountname, rhs.branchaccountname)
		.append(this.city, rhs.city)
		.append(this.operatordesign, rhs.operatordesign)
		.append(this.orgid, rhs.orgid)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.merchants) 
		.append(this.branchname) 
		.append(this.bankaccount) 
		.append(this.bankfullname) 
		.append(this.branchaccountname) 
		.append(this.city) 
		.append(this.operatordesign) 
		.append(this.orgid) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("merchants", this.merchants) 
		.append("branchname", this.branchname) 
		.append("bankaccount", this.bankaccount) 
		.append("bankfullname", this.bankfullname) 
		.append("branchaccountname", this.branchaccountname) 
		.append("city", this.city) 
		.append("operatordesign", this.operatordesign) 
		.append("orgid", this.orgid) 
		.toString();
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
   
  

}