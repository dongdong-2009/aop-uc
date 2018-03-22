package com.casic.tenant.model;

import java.util.Date;

import com.hotent.core.model.BaseModel;

public class Reject extends BaseModel{
	
	private Long id;//主键
	private Long userId;//驳回者
	private Long tenantId;//被驳回企业id
	private String reason;//驳回原因
	private Date datetime;//驳回日期
	private Integer num;//审核次数       包括通过与驳回
	private String isPass;//是否驳回       1驳回   2通过
	private String state;//传参数使用
	
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}		
}
