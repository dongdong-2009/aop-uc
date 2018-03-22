package com.casic.url.model;

import com.hotent.core.model.BaseModel;

public class UrlBean extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	// id
	protected Long  id;
	
	protected String  url;
	
	//时间保存成字符串精确到毫秒
	protected String  startTime;
	
	protected String  endTime;
	
	//来源地址
	protected String  fromUrl;
	//是否调用成功
	protected int  isSuccess;
	//失败原因
	protected String  failReason;
	
	//子系统id
	protected String  subSysId;
	
	//子系统id
	protected String  subSysName;
	
	//访问时间
	protected String  accessTime;
	//访问时间
	protected String  accessTotal;
	
	protected String  remark1;
	
	protected String  remark2;
	
	protected String  remark3;
    //当天访问次数
	private Long  currentTimes;
	//当天访问的总毫秒数
	private String currentSeconds;
	//成功次数
	private Long successTotal;
	
	private Long total;//总次数
	
	
	
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getSuccessTotal() {
		return successTotal;
	}

	public void setSuccessTotal(Long successTotal) {
		this.successTotal = successTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	public String getSubSysId() {
		return subSysId;
	}

	public void setSubSysId(String subSysId) {
		this.subSysId = subSysId;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getAccessTotal() {
		return accessTotal;
	}

	public void setAccessTotal(String accessTotal) {
		this.accessTotal = accessTotal;
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

	public String getSubSysName() {
		return subSysName;
	}

	public void setSubSysName(String subSysName) {
		this.subSysName = subSysName;
	}

	public Long getCurrentTimes() {
		return currentTimes;
	}

	public void setCurrentTimes(Long currentTimes) {
		this.currentTimes = currentTimes;
	}

	public String getCurrentSeconds() {
		return currentSeconds;
	}

	public void setCurrentSeconds(String currentSeconds) {
		this.currentSeconds = currentSeconds;
	}
	
	
	
}
