package com.hotent.platform.model.system;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.hotent.core.model.BaseModel;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.webservice.api.util.adapter.GrantedAuthorityAdapter;

/**
 * 对象功能:用户表 Model对象 开发公司:北京航天软件有限公司 开发人员:hotent 创建时间:2011-11-03 16:02:45
 */
public class SysUser extends BaseModel implements ISysUser{
	public final static String SEARCH_BY_ROL = "1";// 从角色
	public final static String SEARCH_BY_ORG = "2";// 从组织
	public final static String SEARCH_BY_POS = "3";// 从岗位
	public final static String SEARCH_BY_ONL = "4";// 从在线

	/**
	 * 账号未锁定
	 */
	public final static Short UN_LOCKED = 0;
	/**
	 * 账号被锁定
	 */
	public final static Short LOCKED = 1;

	/**
	 * 账号未期
	 */
	public final static Short UN_EXPIRED = 0;

	/**
	 * 账号过期
	 */
	public final static Short EXPIRED = 1;

	/**
	 * 账号激活
	 */
	public final static Short STATUS_OK = 1;

	/**
	 * 账号禁用
	 */
	public final static Short STATUS_NO = 0;
	/**
	 * 账号删除
	 */
	public final static Short STATUS_Del = -1;
	
	/**
	 * 数据来自系统添加
	 */
	public final static Short FROMTYPE_DB=0;
	/**
	 * 数据来自AD同步,并在系统中未被设置
	 */
	public final static Short FROMTYPE_AD=1;
	/**
	 * 数据来自AD同步,并在系统中被设置过
	 */
	public final static Short FROMTYPE_AD_SET=2;

	// userOrgId
	protected Long userOrgId;
	// userId
	protected Long userId;
	// 姓名
	protected String fullname;
	// 帐号
	protected String account;
	// 短帐号
	protected String  shortAccount;
	// 密码
	protected String password;
	// 是否过期
	protected Short isExpired;
	// 是否锁定
	protected Short isLock;
	// 创建时间
	protected java.util.Date createtime;
	// 状态
	protected Short status;
	// 邮箱
	protected String email;
	// 手机
	protected String mobile;
	// 电话
	protected String phone;

	// 性别
	protected String sex;
	// 照片
	protected String picture;

	// 类型
	protected String retype;
	
	//是否绑定手机，0为未绑定，1为绑定
	private String isMobailTrue;
	
	//是否绑定邮箱，0为未绑定，1为绑定
	private String isEmailTrue;
	
	private Long total;
	
	private Long fromSysId;

	private long isCharge;
	
	private long updateTimes;//账号修改次数

	
	public Long getFromSysId() {
		return fromSysId;
	}

	public void setFromSysId(Long fromSysId) {
		this.fromSysId = fromSysId;
	}

	public long getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(long isCharge) {
		this.isCharge = isCharge;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getIsMobailTrue() {
		return isMobailTrue;
	}

	public void setIsMobailTrue(String isMobailTrue) {
		this.isMobailTrue = isMobailTrue;
	}

	public String getIsEmailTrue() {
		return isEmailTrue;
	}

	public void setIsEmailTrue(String isEmailTrue) {
		this.isEmailTrue = isEmailTrue;
	}

	/**
	 * 所属组织类型，包括经销商
	 */
	protected String orgType;
	//区域负责人
	public static String ORG_TYPE_CUSTOM = "区域";
	//门店负责人
	public static String ORG_TYPE_MARKET = "门店";
	
	/**
	 * 职责ID
	 */
	protected Long typeId;
	
	/**
	 * 职责类型
	 */
	protected String typeName;
	
	/**
	 * 数据来源
	 */
	protected short fromType;
	// orgId
	protected Long  orgId;
	
	// orgSn
	protected Long  orgSn;
	
	// 编码,身份证
	protected String code;
	
	// 映射编码
	protected String refCode;

	//人员密级
	protected Integer securityLevel = 0;
	// 云网通行证
	private Short isApply;
	
	protected String openId ;
	
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getOpenId() {
		return openId;
	}

	public Short getFromType() {
		return fromType;
	}
	
	public void setFromType(Short fromType) {
		this.fromType = fromType;
	}

	public String getRetype() {
		return retype;
	}

	public void setRetype(String retype) {
		this.retype = retype;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	// 企业联系人
	protected String connecter;
	
	// 组织名称
	protected String orgName;
	
	public String getConnecter() {
		return connecter;
	}

	public void setConnecter(String connecter) {
		this.connecter = connecter;
	}

	protected String mark;
	
	protected String IsIndividual;//是否是个人申请
	
	protected String state;//用户审核状态
	
  //注册标注
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Long getUserOrgId() {
		return userOrgId;
	}

	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 返回 userId
	 * 
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * 返回 姓名
	 * 
	 * @return
	 */
	public String getFullname() {
		return fullname;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 返回 帐号
	 * 
	 * @return
	 */
	public String getAccount() {
		return account;
	}

	public String getShortAccount() {
		return shortAccount;
	}

	public void setShortAccount(String shortAccount) {
		this.shortAccount = shortAccount;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回 密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	public void setIsExpired(Short isExpired) {
		this.isExpired = isExpired;
	}

	/**
	 * 返回 是否过期
	 * 
	 * @return
	 */
	public Short getIsExpired() {
		return isExpired;
	}

	public void setIsLock(Short isLock) {
		this.isLock = isLock;
	}

	/**
	 * 返回 是否锁定
	 * 
	 * @return
	 */
	public Short getIsLock() {
		return isLock;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	/**
	 * 返回 状态
	 * 
	 * @return
	 */
	public Short getStatus() {
		return status;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 返回 邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 返回 手机
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 返回 电话
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	public static String getSearchByRol() {
		return SEARCH_BY_ROL;
	}

	public static String getSearchByOrg() {
		return SEARCH_BY_ORG;
	}

	public static String getSearchByPos() {
		return SEARCH_BY_POS;
	}

	public static String getSearchByOnl() {
		return SEARCH_BY_ONL;
	}

	public static Short getUnLocked() {
		return UN_LOCKED;
	}

	public static Short getLocked() {
		return LOCKED;
	}

	public static Short getUnExpired() {
		return UN_EXPIRED;
	}

	public static Short getExpired() {
		return EXPIRED;
	}

	public static Short getStatusOk() {
		return STATUS_OK;
	}

	public static Short getStatusNo() {
		return STATUS_NO;
	}

	public static Short getStatusDel() {
		return STATUS_Del;
	}
	public void setOrgId(Long orgId) 
	{
		this.orgId = orgId;
	}
	/**
	 * 返回 orgId
	 * @return
	 */
	public Long getOrgId() 
	{
		return this.orgId;
	}
	public void setOrgSn(Long orgSn) 
	{
		this.orgSn = orgSn;
	}
	/**
	 * 返回 orgSn
	 * @return
	 */
	public Long getOrgSn() 
	{
		return this.orgSn;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	
	@Override
	public boolean equals(Object rhs) {
		
        if (rhs instanceof SysUser) {
            return this.account.equals(((SysUser) rhs).account);
        }
        return false;
    }
	/**
	 * 返回所属组织机构类型
	 * @return
	 */
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 返回所属类型Id
	 * @return
	 */
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * 返回所属类型名称
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.account.hashCode();

		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public void setFromType(short fromType) {
		this.fromType = fromType;
	}
	
	public Integer getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}

	/**
	 * @see java.lang.Object#toString()
	 * 	protected Long  orgId;
	
		// orgSn
		protected Long  orgSn;
	 */
	public String toString() {
		return new ToStringBuilder(this).append("userId", this.userId).append("fullname", this.fullname).append("account", this.account)
				.append("password", this.password).append("isExpired", this.isExpired).append("isLock", this.isLock).append("createtime", this.createtime)
				.append("status", this.status).append("email", this.email).append("mobile", this.mobile).append("phone", this.phone)
				.append("orgName", this.orgName).append("sex", this.sex).append("picture", this.picture).append("retype", this.retype)
				.append("orgType", this.orgType).append("typeId", this.typeId).append("typeName", this.typeName).append("isMobailTrue", this.isMobailTrue)
				.append("isEmailTrue", this.isEmailTrue).append("orgId",this.orgId).append("orgSn",this.orgSn).append("connecter",this.connecter).toString();
	}
	
	/**
	 * 返回角色。
	 * @return
	 */
	public String getRoles() {
		String str="";
		Collection<GrantedAuthority> roles= getAuthorities();
		for(GrantedAuthority role:roles){
			if("".equals(str)){
				str=role.getAuthority();
			}
			else{
				str+="," + role.getAuthority();
			}
		}
		return str;
	}
	

	/**
	 * 重写UserDetails 的getAuthorities方法。
	 * 
	 * <pre>
	 * 1.首先从缓存中读取。
	 * 2.判断帐号在缓存中是否存在帐号，若存在直接重缓存中读取。
	 * 3.如果不存在则从数据库中读取并加入缓存。
	 * 
	 * 目前角色支持两种方式。
	 * 1.用户和角色的映射。
	 * 2.部门和角色的映射。
	 * 
	 * 两种角色进行合并构成当前用户的角色。
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public @XmlJavaTypeAdapter(GrantedAuthorityAdapter.class)
	Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> rtnList= new ArrayList<GrantedAuthority>();
		SysRoleService sysRoleService = (SysRoleService) AppUtil.getBean(SysRoleService.class);
		ISysOrg curOrg=ContextUtil.getCurrentOrg();
		Long orgId=curOrg==null?0:curOrg.getOrgId();
		Collection<String> totalRoleCol=  sysRoleService.getRolesByUserIdAndOrgId(userId, orgId, orgSn);
		
		if(BeanUtils.isNotEmpty(totalRoleCol)){
			for(String role:totalRoleCol){
				rtnList.add(new GrantedAuthorityImpl(role));
			}
		}
		// 添加超级管理员角色。
		if ("devadmin".equals(this.account)) {
			rtnList.add(SystemConst.ROLE_GRANT_SUPER);
		}
		// 企业管理员
		if ("system".equals(this.shortAccount)) {
			rtnList.add(new GrantedAuthorityImpl("cloud_ROLE_ORGADMIN"));
		}
		return rtnList;
	}

	

	@Override
	public String getUsername() {
		return account;
	}

	@Override
	public boolean isAccountNonExpired() {
	/*	if (isExpired.shortValue() == UN_EXPIRED.shortValue()) {
			return true;
		} else {
			return false;
		}*/
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	/*	if (isLock.shortValue() == UN_LOCKED.shortValue()) {
			return true;
		} else {
			return false;
		}*/
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return status == STATUS_OK ? true : false;

	}

	@Override
	public Short getIsApply() {
		// TODO Auto-generated method stub
		return this.isApply;
	}

	@Override
	public void setIsApply(Short isApply) {
		// TODO Auto-generated method stub
		this.isApply = isApply;
	}

	public long getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(long updateTimes) {
		this.updateTimes = updateTimes;
	}

	public String getIsIndividual() {
		return IsIndividual;
	}

	public void setIsIndividual(String isIndividual) {
		IsIndividual = isIndividual;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
   
	

	
	
	
}