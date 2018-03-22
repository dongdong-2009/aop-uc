package com.hotent.platform.model.system;

import com.hotent.core.model.BaseModel;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SysOrgInfo
  extends BaseModel
{
  protected Long sysOrgInfoId;
  protected String email;
  protected String name;
  protected String industry;
  protected String industry2;
  protected String scale;
  protected String address;
  protected String postcode;
  protected String connecter;
  protected String province;
  protected String city;
  public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getProvince() {
	return province;
}

public void setProvince(String province) {
	this.province = province;
}

  protected String tel;
  protected String fax;
  protected String homephone;
  protected String country;
  protected String flaglogo;
  protected int state;
  protected Date registtime;
  private Long setid;
  private String recommendedEnt;
  private String manageType;
  private String openId;
  private String invititedCode;//企业邀请码
  protected String  systemId;//子系统标识fromSysId=systemId
  protected String mark;//来源标志

  
  
  
  
 public String getMark() {
	return mark;
 }

 public void setMark(String mark) {
	this.mark = mark;
 }

public String getSystemId() {
	return systemId;
 }

public void setSystemId(String systemId) {
	this.systemId = systemId;
}

public String getInvititedCode() {
		return invititedCode;
	}
	
	public void setInvititedCode(String invititedCode) {
		this.invititedCode = invititedCode;
	}

public String getOpenId()
  {
    return this.openId;
  }
  
  public void setOpenId(String openId)
  {
    this.openId = openId;
  }
  
  public void setSysOrgInfoId(Long sysOrgInfoId)
  {
    this.sysOrgInfoId = sysOrgInfoId;
  }
  
  public Long getSysOrgInfoId()
  {
    return this.sysOrgInfoId;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setIndustry(String industry)
  {
    this.industry = industry;
  }
  
  public String getIndustry()
  {
    return this.industry;
  }
  
  public String getIndustry2()
  {
    return this.industry2;
  }
  
  public void setIndustry2(String industry2)
  {
    this.industry2 = industry2;
  }
  
  public void setScale(String scale)
  {
    this.scale = scale;
  }
  
  public String getScale()
  {
    return this.scale;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setPostcode(String postcode)
  {
    this.postcode = postcode;
  }
  
  public String getPostcode()
  {
    return this.postcode;
  }
  
  public void setConnecter(String connecter)
  {
    this.connecter = connecter;
  }
  
  public String getConnecter()
  {
    return this.connecter;
  }
  
  public void setTel(String tel)
  {
    this.tel = tel;
  }
  
  public String getTel()
  {
    return this.tel;
  }
  
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setHomephone(String homephone)
  {
    this.homephone = homephone;
  }
  
  public String getHomephone()
  {
    return this.homephone;
  }
  
  public String getCountry()
  {
    return this.country;
  }
  
  public void setCountry(String country)
  {
    this.country = country;
  }
  
  public String getFlaglogo()
  {
    return this.flaglogo;
  }
  
  public void setFlaglogo(String flaglogo)
  {
    this.flaglogo = flaglogo;
  }
  
  public Date getRegisttime()
  {
    return this.registtime;
  }
  
  public void setRegisttime(Date registtime)
  {
    this.registtime = registtime;
  }
  
  public int getState()
  {
    return this.state;
  }
  
  public void setState(int state)
  {
    this.state = state;
  }
  
  public Long getSetid()
  {
    return this.setid;
  }
  
  public void setSetid(Long setid)
  {
    this.setid = setid;
  }
  
  public String getRecommendedEnt()
  {
    return this.recommendedEnt;
  }
  
  public void setRecommendedEnt(String recommendedEnt)
  {
    this.recommendedEnt = recommendedEnt;
  }
  
  public String getManageType()
  {
    return this.manageType;
  }
  
  public void setManageType(String manageType)
  {
    this.manageType = manageType;
  }
  
  public boolean equals(Object object)
  {
    if (!(object instanceof SysOrgInfo)) {
      return false;
    }
    SysOrgInfo rhs = (SysOrgInfo)object;
    return new EqualsBuilder().append(this.sysOrgInfoId, rhs.sysOrgInfoId).append(this.email, rhs.email).append(this.name, rhs.name).append(this.industry, rhs.industry).append(this.scale, rhs.scale).append(this.address, rhs.address).append(this.postcode, rhs.postcode).append(this.connecter, rhs.connecter).append(this.tel, rhs.tel).append(this.fax, rhs.fax).append(this.homephone, rhs.homephone).append(this.country, rhs.country).append(this.flaglogo, rhs.flaglogo).append(this.state, rhs.state).append(this.openId, rhs.openId).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder(-82280557, -700257973).append(this.sysOrgInfoId).append(this.email).append(this.name).append(this.industry).append(this.scale).append(this.address).append(this.postcode).append(this.connecter).append(this.tel).append(this.fax).append(this.homephone).append(this.country).append(this.flaglogo).append(this.state).append(this.openId).toHashCode();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("sysOrgInfoId", this.sysOrgInfoId).append("email", this.email).append("name", this.name).append("industry", this.industry).append("scale", this.scale).append("address", this.address).append("postcode", this.postcode).append("connecter", this.connecter).append("tel", this.tel).append("fax", this.fax).append("homephone", this.homephone).append("state", this.state).append("openId", this.openId).toString();
  }
}
