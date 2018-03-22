package com.casic.tenant.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.casic.util.StringUtil;
import com.hotent.core.model.BaseModel;
import com.hotent.platform.model.system.SysOrgInfo;
/**
 * 对象功能:sys_org_info Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:ziapple
 * 创建时间:2013-04-17 19:28:39
 */
public class TenantInfo extends SysOrgInfo
{
		// ID
		protected Long  sysOrgInfoId;
		// 公司邮箱
		protected String  email;
		// 公司名称
		protected String  name;
		// 主营行业
		protected String  industry;
		// 公司规模
		protected String  scale;
		// 公司地址
		protected String  address;
		// 公司邮编
		protected String  postcode;
		// 联系人
		protected String  connecter;
		// 手机号
		protected String  tel;
		// 传真号
		protected String  fax;
		// 固定电话
		protected String  homephone;
		// 公司LOGO
		protected String  logo;
		// 主营行业
		protected String  industry2;
		// 公司简介
		protected String  info;
		// 国家
		protected String  country;
		// 省
		protected String  province;
		// 市
		protected String  city;
		// 组织机构代码
		protected String  code;
		// 公司类型
		protected String  type;
		// 经营类型
		protected String  model;
		// 主营产品
		protected String  product;
		// 公司网址
		protected String  website;
		// 是否公开
		protected String  isPublic;
		// 注册时间
		protected java.util.Date  registertime;
		// 年检时间
		protected java.util.Date  createtime;
		// 国别
		protected String  flaglogo;
		// STATE 新注册为0，审核通过为2，审核未通过为1
		protected int state;
		// 经营范围
		protected String  manageRange;
		// 注册证明
		protected String  regProve;
		// 销售区域
		protected String  sellArea;
		// 企业品牌
		protected String  brand;
		// 员工人数
		protected Long  employees;
		// 占地面积
		protected Long  area;
		// 主要客户群体
		protected String  clients;
		// 年营业额
		protected String  turnover;
		// 年出口额
		protected String  exportFore;
		// 年进口额
		protected String  importFore;
		// 质量管理体系
		protected String  qualityControl;
		// 注册资本
		protected String  regCapital;
		// 注册地点
		protected String  regAdd;
		// 法人
		protected String  incorporator;
		// 开户银行
		protected String  openBank;
		// 开户账户
		protected String  openAccount;
		// 企业首页展示图片
		protected String  showimage;
		protected String sellArea2;
		// 套餐ID
		protected Long  setid;
		
		protected String recommendedEnt;
		//企业二维码
		protected String QRcode;
		
		//所属组织类型
		protected String orgType;
		
		//类型ID
		protected long typeId;

		//类型名称
		protected String typeName;
		
		//经营类型
		private String manageType;
		
		//工商注册号
		private String gszch;
		
		//纳税人识别号
		private String nsrsbh;
		
		//法人身份证号
		private String frsfzhm;
		
		//法人手机号
		private String frsjh;
		
		//营业执照图片
		private String yyzzPic;
		
		//爱信诺认证状态，0，未审，1待审，2通过，3未通过
		private long axnStatus;
		//银联状态，0，未审，1待审，2通过，3未通过
		private long ylStatus;
		//法人身份证照片
		private String frPic;
		
		private String openId;
		//企业邀请码
		private String  invititedCode;

		private String abbreviationName;//企业简称
		
		private String codePic;//组织机构代码证件
		
		private String nsrsbhPic;//税务登记照片
		
		private String yyzz;//营业执照
		
		private String creditCode;//统一社会信用代码
		
		
		private String currencyFunds;//资金币种
		
		
		//作为企业的经营地址
		private String county;//区
		private String province1;
		private String city1;
		private String county1;
		protected String address1;//经营地址
		
		private String accountStatsus;//账户实名认证的状态  0未认证 1已认证
		
		protected java.util.Date accountTime;//实名认证时间
		
		private Long total;
		
		private String uopenId;//用户openId
		
		private Long operator;//操作人Id
		
		
		protected String isThreeInOne;//是否三证合一 0否1是
		

		private String fromSysId;//子系统标识fromSysId=systemId
		
		private Date buluTime;		
		
		
		
		
		
		

	

		public String getFromSysId() {
			return fromSysId;
		}

		public void setFromSysId(String fromSysId) {
			this.fromSysId = fromSysId;
		}

		public String getUopenId() {
			return uopenId;
		}

		public void setUopenId(String uopenId) {
			this.uopenId = uopenId;
		}

		public Long getTotal() {
			return total;
		}

		public void setTotal(Long total) {
			this.total = total;
		}

		public java.util.Date getAccountTime() {
			return accountTime;
		}

		public void setAccountTime(java.util.Date accountTime) {
			this.accountTime = accountTime;
		}

		public String getAccountStatsus() {
			return accountStatsus;
		}

		public void setAccountStatsus(String accountStatsus) {
			this.accountStatsus = accountStatsus;
		}

		public String getProvince1() {
			return province1;
		}

		public void setProvince1(String province1) {
			this.province1 = province1;
		}

		public String getCity1() {
			return city1;
		}

		public void setCity1(String city1) {
			this.city1 = city1;
		}

		public String getCounty1() {
			return county1;
		}

		public void setCounty1(String county1) {
			this.county1 = county1;
		}

		public String getCurrencyFunds() {
			return currencyFunds;
		}

		public void setCurrencyFunds(String currencyFunds) {
			this.currencyFunds = currencyFunds;
		}

		public String getCounty() {
			return county;
		}

		public void setCounty(String county) {
			this.county = county;
		}

		public String getCreditCode() {
			return creditCode;
		}

		public void setCreditCode(String creditCode) {
			this.creditCode = creditCode;
		}

		public String getYyzz() {
			return yyzz;
		}

		public void setYyzz(String yyzz) {
			this.yyzz = yyzz;
		}

		public String getNsrsbhPic() {
			return nsrsbhPic;
		}

		public void setNsrsbhPic(String nsrsbhPic) {
			this.nsrsbhPic = nsrsbhPic;
		}

		public String getCodePic() {
			return codePic;
		}

		public void setCodePic(String codePic) {
			this.codePic = codePic;
		}

		public String getAbbreviationName() {
			return abbreviationName;
		}

		public void setAbbreviationName(String abbreviationName) {
			this.abbreviationName = abbreviationName;
		}

		public String getInvititedCode() {
			return invititedCode;
		}
		
		public void setInvititedCode(String invititedCode) {
			this.invititedCode = invititedCode;
		}
		
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getFrPic() {
			return frPic;
		}
		public void setFrPic(String frPic) {
			this.frPic = frPic;
		}
		public String getFrsjh() {
			return frsjh;
		}
		public void setFrsjh(String frsjh) {
			this.frsjh = frsjh;
		}
		public String getYyzzPic() {
			return yyzzPic;
		}
		public void setYyzzPic(String yyzzPic) {
			this.yyzzPic = yyzzPic;
		}
		public long getAxnStatus() {
			return axnStatus;
		}
		public void setAxnStatus(long axnStatus) {
			this.axnStatus = axnStatus;
		}
		public long getYlStatus() {
			return ylStatus;
		}
		public void setYlStatus(long ylStatus) {
			this.ylStatus = ylStatus;
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
		public String getFrsfzhm() {
			return frsfzhm;
		}
		public void setFrsfzhm(String frsfzhm) {
			this.frsfzhm = frsfzhm;
		}
		/**
		 * 返回经营类型
		 * @return
		 */
		public String getManageType() {
			return manageType;
		}
		public void setManageType(String manageType) {
			this.manageType = manageType;
		}
		
		/**
		 * 返回所属组织类型
		 * @return
		 */
		public String getOrgType() {
			return orgType;
		}
		public void setOrgType(String orgType) {
			this.orgType = orgType;
		}
		/**
		 * 返回类型ID
		 * @return
		 */
		public long getTypeId() {
			return typeId;
		}
		public void setTypeId(long typeId) {
			this.typeId = typeId;
		}
		/**
		 * 返回类型名称
		 * @return
		 */
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		
		
		/*
		 * 企业二维码的get和set方法
		 */
		public String getQRcode() {
			return QRcode;
		}
		public void setQRcode(String qRcode) {
			QRcode = qRcode;
		}

		//返回资质认证List
		protected List<Aptitude> aptitudeList=new ArrayList<Aptitude>();
		
		
		
		
		public String getRecommendedEnt() {
			return recommendedEnt;
		}
		public void setRecommendedEnt(String recommendedEnt) {
			this.recommendedEnt = recommendedEnt;
		}
		public Long getSetid() {
			return setid;
		}
		public void setSetid(Long setid) {
			this.setid = setid;
		}
		public java.util.Date getCreatetime() {
			return createtime;
		}
		public void setCreatetime(java.util.Date createtime) {
			this.createtime = createtime;
		}
		public String getSellArea2() {
			return sellArea2;
		}
		public void setSellArea2(String sellArea2) {
			this.sellArea2 = sellArea2;
		}
		public void setAptitudeList(List<Aptitude> aptitudeList) 
		{
			this.aptitudeList = aptitudeList;
		}
		/**
		 * 返回 认证资质 列表
		 * @return
		 */
		public List<Aptitude> getAptitudeList() 
		{
			return this.aptitudeList;
		}

		
		public void setSysOrgInfoId(Long sysOrgInfoId) 
		{
			this.sysOrgInfoId = sysOrgInfoId;
		}
		/**
		 * 返回 ID
		 * @return
		 */
		public Long getSysOrgInfoId() 
		{
			return this.sysOrgInfoId;
		}
		public void setEmail(String email) 
		{
			this.email = email;
		}
		/**
		 * 返回 公司邮箱
		 * @return
		 */
		public String getEmail() 
		{
			return this.email;
		}
		public void setName(String name) 
		{
			this.name = name;
		}
		/**
		 * 返回 公司名称
		 * @return
		 */
		public String getName() 
		{
			return this.name;
		}
		public void setIndustry(String industry) 
		{
			this.industry = industry;
		}
		/**
		 * 返回 主营行业
		 * @return
		 */
		public String getIndustry() 
		{
			return this.industry;
		}
		public void setScale(String scale) 
		{
			this.scale = scale;
		}
		/**
		 * 返回 公司规模
		 * @return
		 */
		public String getScale() 
		{
			return this.scale;
		}
		public void setAddress(String address) 
		{
			this.address = address;
		}
		/**
		 * 返回 公司地址
		 * @return
		 */
		public String getAddress() 
		{
			return this.address;
		}
		public void setPostcode(String postcode) 
		{
			this.postcode = postcode;
		}
		/**
		 * 返回 公司邮编
		 * @return
		 */
		public String getPostcode() 
		{
			return this.postcode;
		}
		public void setConnecter(String connecter) 
		{
			this.connecter = connecter;
		}
		/**
		 * 返回 联系人
		 * @return
		 */
		public String getConnecter() 
		{
			return this.connecter;
		}
		public void setTel(String tel) 
		{
			this.tel = tel;
		}
		/**
		 * 返回 手机号
		 * @return
		 */
		public String getTel() 
		{
			return this.tel;
		}
		public void setFax(String fax) 
		{
			this.fax = fax;
		}
		/**
		 * 返回 传真号
		 * @return
		 */
		public String getFax() 
		{
			return this.fax;
		}
		public void setHomephone(String homephone) 
		{
			this.homephone = homephone;
		}
		/**
		 * 返回 固定电话
		 * @return
		 */
		public String getHomephone() 
		{
			return this.homephone;
		}
		public void setLogo(String logo) 
		{
			this.logo = logo;
		}
		/**
		 * 返回 公司LOGO
		 * @return
		 */
		public String getLogo() 
		{
			return this.logo;
		}
		public void setIndustry2(String industry2) 
		{
			this.industry2 = industry2;
		}
		/**
		 * 返回 主营行业
		 * @return
		 */
		public String getIndustry2() 
		{
			return this.industry2;
		}
		public void setInfo(String info) 
		{
			this.info = info;
		}
		/**
		 * 返回 公司简介
		 * @return
		 */
		public String getInfo() 
		{
			return this.info;
		}
		public void setCountry(String country) 
		{
			this.country = country;
		}
		/**
		 * 返回 国家
		 * @return
		 */
		public String getCountry() 
		{
			return this.country;
		}
		public void setProvince(String province) 
		{
			this.province = province;
		}
		/**
		 * 返回 省
		 * @return
		 */
		public String getProvince() 
		{
			return this.province;
		}
		public void setCity(String city) 
		{
			this.city = city;
		}
		/**
		 * 返回 市
		 * @return
		 */
		public String getCity() 
		{
			return this.city;
		}
		public void setCode(String code) 
		{
			this.code = code;
		}
		/**
		 * 返回 组织机构代码
		 * @return
		 */
		public String getCode() 
		{
			return this.code;
		}
		public void setType(String type) 
		{
			this.type = type;
		}
		/**
		 * 返回 公司类型
		 * @return
		 */
		public String getType() 
		{
			return this.type;
		}
		public void setModel(String model) 
		{
			this.model = model;
		}
		/**
		 * 返回 经营类型
		 * @return
		 */
		public String getModel() 
		{
			return this.model;
		}
		public void setProduct(String product) 
		{
			this.product = product;
		}
		/**
		 * 返回 主营产品
		 * @return
		 */
		public String getProduct() 
		{
			return this.product;
		}
		public void setWebsite(String website) 
		{
			this.website = website;
		}
		/**
		 * 返回 公司网址
		 * @return
		 */
		public String getWebsite() 
		{
			return this.website;
		}
		public void setIsPublic(String isPublic) 
		{
			this.isPublic = isPublic;
		}
		/**
		 * 返回 是否公开
		 * @return
		 */
		public String getIsPublic() 
		{
			return this.isPublic;
		}
		public void setRegistertime(java.util.Date registertime) 
		{
			this.registertime = registertime;
		}
		/**
		 * 返回 注册时间
		 * @return
		 */
		public java.util.Date getRegistertime() 
		{
			return this.registertime;
		}
		public void setFlaglogo(String flaglogo) 
		{
			this.flaglogo = flaglogo;
		}
		/**
		 * 返回 认证/资质
		 * @return
		 */
		public String getFlaglogo() 
		{
			return this.flaglogo;
		}
		public void setState(int state) 
		{
			this.state = state;
		}
		/**
		 * 返回 STATE
		 * @return
		 */
		public int getState() 
		{
			return this.state;
		}
		public void setManageRange(String manageRange) 
		{
			this.manageRange = manageRange;
		}
		/**
		 * 返回 经验营范围
		 * @return
		 */
		public String getManageRange() 
		{
			return this.manageRange;
		}
		public void setRegProve(String regProve) 
		{
			this.regProve = regProve;
		}
		/**
		 * 返回 注册证明
		 * @return
		 */
		public String getRegProve() 
		{
			return this.regProve;
		}
		public void setSellArea(String sellArea) 
		{
			this.sellArea = sellArea;
		}
		/**
		 * 返回 销售区域
		 * @return
		 */
		public String getSellArea() 
		{
			return this.sellArea;
		}
		public void setBrand(String brand) 
		{
			this.brand = brand;
		}
		/**
		 * 返回 企业品牌
		 * @return
		 */
		public String getBrand() 
		{
			return this.brand;
		}
		public void setEmployees(Long employees) 
		{
			this.employees = employees;
		}
		/**
		 * 返回 员工人数
		 * @return
		 */
		public Long getEmployees() 
		{
			return this.employees;
		}
		public void setArea(Long area) 
		{
			this.area = area;
		}
		/**
		 * 返回 占地面积
		 * @return
		 */
		public Long getArea() 
		{
			return this.area;
		}
		public void setClients(String clients) 
		{
			this.clients = clients;
		}
		/**
		 * 返回 主要客户群体
		 * @return
		 */
		public String getClients() 
		{
			return this.clients;
		}
		public void setTurnover(String turnover) 
		{
			this.turnover = turnover;
		}
		/**
		 * 返回 年营业额
		 * @return
		 */
		public String getTurnover() 
		{
			return this.turnover;
		}
		public void setExportFore(String exportFore) 
		{
			this.exportFore = exportFore;
		}
		/**
		 * 返回 年出口额
		 * @return
		 */
		public String getExportFore() 
		{
			return this.exportFore;
		}
		public void setImportFore(String importFore) 
		{
			this.importFore = importFore;
		}
		/**
		 * 返回 年进口额
		 * @return
		 */
		public String getImportFore() 
		{
			return this.importFore;
		}
		public void setQualityControl(String qualityControl) 
		{
			this.qualityControl = qualityControl;
		}
		/**
		 * 返回 质量管理体系
		 * @return
		 */
		public String getQualityControl() 
		{
			return this.qualityControl;
		}
		public void setRegCapital(String regCapital) 
		{
			this.regCapital = regCapital;
		}
		/**
		 * 返回 注册资本
		 * @return
		 */
		public String getRegCapital() 
		{
			return this.regCapital;
		}
		public void setRegAdd(String regAdd) 
		{
			this.regAdd = regAdd;
		}
		/**
		 * 返回 注册地点
		 * @return
		 */
		public String getRegAdd() 
		{
			return this.regAdd;
		}
		public void setIncorporator(String incorporator) 
		{
			this.incorporator = incorporator;
		}
		/**
		 * 返回 法人
		 * @return
		 */
		public String getIncorporator() 
		{
			return this.incorporator;
		}
		public void setOpenBank(String openBank) 
		{
			this.openBank = openBank;
		}
		/**
		 * 返回 开户银行
		 * @return
		 */
		public String getOpenBank() 
		{
			return this.openBank;
		}
		public void setOpenAccount(String openAccount) 
		{
			this.openAccount = openAccount;
		}
		/**
		 * 返回 开户账户
		 * @return
		 */
		public String getOpenAccount() 
		{
			return this.openAccount;
		}
		public void setShowimage(String showimage) 
		{
			this.showimage = showimage;
		}
		/**
		 * 返回 企业首页展示图片
		 * @return
		 */
		public String getShowimage() 
		{
			return this.showimage;
		}

	   	public String getAddress1() {
			return address1;
		}

		public void setAddress1(String address1) {
			this.address1 = address1;
		}

		public Long getOperator() {
			return operator;
		}

		public void setOperator(Long operator) {
			this.operator = operator;
		}

		/**
		 * @see java.lang.Object#equals(Object)
		 */
		public boolean equals(Object object) 
		{
			if (!(object instanceof TenantInfo)) 
			{
				return false;
			}
			TenantInfo rhs = (TenantInfo) object;
			return new EqualsBuilder()
			.append(this.sysOrgInfoId, rhs.sysOrgInfoId)
			.append(this.email, rhs.email)
			.append(this.name, rhs.name)
			.append(this.industry, rhs.industry)
			.append(this.scale, rhs.scale)
			.append(this.address, rhs.address)
			.append(this.postcode, rhs.postcode)
			.append(this.connecter, rhs.connecter)
			.append(this.tel, rhs.tel)
			.append(this.fax, rhs.fax)
			.append(this.homephone, rhs.homephone)
			.append(this.logo, rhs.logo)
			.append(this.industry2, rhs.industry2)
			.append(this.info, rhs.info)
			.append(this.country, rhs.country)
			.append(this.province, rhs.province)
			.append(this.city, rhs.city)
			.append(this.code, rhs.code)
			.append(this.type, rhs.type)
			.append(this.model, rhs.model)
			.append(this.product, rhs.product)
			.append(this.website, rhs.website)
			.append(this.isPublic, rhs.isPublic)
			.append(this.registertime, rhs.registertime)
			.append(this.flaglogo, rhs.flaglogo)
			.append(this.state, rhs.state)
			.append(this.manageRange, rhs.manageRange)
			.append(this.regProve, rhs.regProve)
			.append(this.sellArea, rhs.sellArea)
			.append(this.brand, rhs.brand)
			.append(this.employees, rhs.employees)
			.append(this.area, rhs.area)
			.append(this.clients, rhs.clients)
			.append(this.turnover, rhs.turnover)
			.append(this.exportFore, rhs.exportFore)
			.append(this.importFore, rhs.importFore)
			.append(this.qualityControl, rhs.qualityControl)
			.append(this.regCapital, rhs.regCapital)
			.append(this.regAdd, rhs.regAdd)
			.append(this.incorporator, rhs.incorporator)
			.append(this.openBank, rhs.openBank)
			.append(this.openAccount, rhs.openAccount)
			.append(this.showimage, rhs.showimage)
			.append(this.sellArea2, rhs.sellArea2)
			.append(this.orgType, rhs.orgType)
			.append(this.typeId, rhs.typeId)
			.append(this.typeName, rhs.typeName)
			.append(this.openId, rhs.openId)
			.append(this.invititedCode, rhs.invititedCode)
			.append(this.systemId, rhs.systemId)
			.append(this.buluTime, rhs.buluTime)
			.isEquals();
			
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() 
		{
			return new HashCodeBuilder(-82280557, -700257973)
			.append(this.sysOrgInfoId) 
			.append(this.email) 
			.append(this.name) 
			.append(this.industry) 
			.append(this.scale) 
			.append(this.address) 
			.append(this.postcode) 
			.append(this.connecter) 
			.append(this.tel) 
			.append(this.fax) 
			.append(this.homephone) 
			.append(this.logo) 
			.append(this.industry2) 
			.append(this.info) 
			.append(this.country) 
			.append(this.province) 
			.append(this.city) 
			.append(this.code) 
			.append(this.type) 
			.append(this.model) 
			.append(this.product) 
			.append(this.website) 
			.append(this.isPublic) 
			.append(this.registertime) 
			.append(this.flaglogo) 
			.append(this.state) 
			.append(this.manageRange) 
			.append(this.regProve) 
			.append(this.sellArea) 
			.append(this.brand) 
			.append(this.employees) 
			.append(this.area) 
			.append(this.clients) 
			.append(this.turnover) 
			.append(this.exportFore) 
			.append(this.importFore) 
			.append(this.qualityControl) 
			.append(this.regCapital) 
			.append(this.regAdd) 
			.append(this.incorporator) 
			.append(this.openBank) 
			.append(this.openAccount) 
			.append(this.showimage)
			.append(this.sellArea2)
			.append(this.orgType)
			.append(this.typeId)
			.append(this.typeName)
			.append(this.openId)
			.append(this.buluTime)
			.toHashCode();
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() 
		{
			return new ToStringBuilder(this)
			.append("sysOrgInfoId", this.sysOrgInfoId) 
			.append("email", this.email) 
			.append("name", this.name) 
			.append("industry", this.industry) 
			.append("scale", this.scale) 
			.append("address", this.address) 
			.append("postcode", this.postcode) 
			.append("connecter", this.connecter) 
			.append("tel", this.tel) 
			.append("fax", this.fax) 
			.append("homephone", this.homephone) 
			.append("logo", this.logo) 
			.append("industry2", this.industry2) 
			.append("info", this.info) 
			.append("country", this.country) 
			.append("province", this.province) 
			.append("city", this.city) 
			.append("code", this.code) 
			.append("type", this.type) 
			.append("model", this.model) 
			.append("product", this.product) 
			.append("website", this.website) 
			.append("isPublic", this.isPublic) 
			.append("registertime", this.registertime) 
			.append("flaglogo", this.flaglogo) 
			.append("state", this.state) 
			.append("manageRange", this.manageRange) 
			.append("regProve", this.regProve) 
			.append("sellArea", this.sellArea) 
			.append("brand", this.brand) 
			.append("employees", this.employees) 
			.append("area", this.area) 
			.append("clients", this.clients) 
			.append("turnover", this.turnover) 
			.append("exportFore", this.exportFore) 
			.append("importFore", this.importFore) 
			.append("qualityControl", this.qualityControl) 
			.append("regCapital", this.regCapital) 
			.append("regAdd", this.regAdd) 
			.append("incorporator", this.incorporator) 
			.append("openBank", this.openBank) 
			.append("openAccount", this.openAccount) 
			.append("showimage", this.showimage)
			.append("sellArea2",this.sellArea2)
			.append("orgType",this.orgType)
			.append("typeId",this.typeId)
			.append("typeName",this.typeName)
			.append("invititedCode",this.invititedCode)
			.append("systemId",this.systemId)
			.append("buluTime",this.buluTime)
			.toString();
		}
	   public Date getBuluTime() {
			return buluTime;
		}

		public void setBuluTime(Date buluTime) {
			this.buluTime = buluTime;
		}

	/**
	    * 比较六个属性来判断是否需要爱心诺认证
	    * @param tenantInfo
	    * @return
	    */
      public boolean compare(Object object){
    	  if (!(object instanceof TenantInfo)) 
			{
				return false;
			}
    	  TenantInfo rhs = (TenantInfo) object;
    	  
    	  return new EqualsBuilder()
    	  .append(this.sysOrgInfoId, rhs.sysOrgInfoId)
    	  .append(this.name, rhs.name)
    	  .append(this.gszch, rhs.gszch)
    	  .append(this.nsrsbh, rhs.nsrsbh)
    	  .append(this.frsfzhm, rhs.frsfzhm)
    	  .append(this.incorporator, rhs.incorporator)
    	  .append(this.frsjh, rhs.frsjh)
    	  .isEquals();
    	
       }

	public String getIsThreeInOne() {
		return isThreeInOne;
	}

	public void setIsThreeInOne(String isThreeInOne) {
		this.isThreeInOne = isThreeInOne;
	}
     
     

}
