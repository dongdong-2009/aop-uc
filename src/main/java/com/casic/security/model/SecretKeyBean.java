package com.casic.security.model;

import java.util.Date;

import com.hotent.core.model.BaseModel;

public class SecretKeyBean extends BaseModel{

	protected static final long serialVersionUID = 1L;
	
	protected Long  id;
	
	protected String secretKey;
	
	protected String createById;
	
	protected String createByName;
	
	protected Date date;
	
	protected String subSystemId;
	
	protected String subSystemName;
	
	protected String remark1;
	
	protected String remark2;
	
	protected String remark3;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubSystemId() {
		return subSystemId;
	}

	public void setSubSystemId(String subSystemId) {
		this.subSystemId = subSystemId;
	}
	
	public String getSubSystemName() {
		return subSystemName;
	}

	public void setSubSystemName(String subSystemName) {
		this.subSystemName = subSystemName;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
}
