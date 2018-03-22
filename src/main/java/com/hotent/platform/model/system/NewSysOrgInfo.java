package com.hotent.platform.model.system;

import java.io.Serializable;
import java.util.Date;

public class NewSysOrgInfo implements Serializable{

	private Long sysOrgInfoId;
	
	private String name;
	
	private String info;
	
	private String createtime;
	
	private String updatetime;

	public Long getSysOrgInfoId() {
		return sysOrgInfoId;
	}

	public void setSysOrgInfoId(Long sysOrgInfoId) {
		this.sysOrgInfoId = sysOrgInfoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
}
