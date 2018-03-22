package com.casic.xiaon.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 对象功能:sys_org_xiaon_pwd Dao对象
 * 开发公司:航天科工集团
 * 开发人员:LZY
 * 创建时间:2016-07-27
 */

public class SysOrgXiaonGroup {
	
	private Long id;
	private String name;
	//接待组ID
	private String settingId;
	//0：显示   1：不显示
	private int type;		
	//排序号
	private int sorting;	
	private Date addTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSettingId() {
		return settingId;
	}
	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSorting() {
		return sorting;
	}
	public void setSorting(int sorting) {
		this.sorting = sorting;
	}
	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("settingId", this.settingId)
				.append("type", this.type).append("sorting",this.sorting)
				.append("addTime",this.addTime).toString();
	}
	

}
