package com.casic.xiaon.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;


/**
 * 对象功能:sys_org_xiaon_pwd Model对象
 * 开发公司:航天科工集团
 * 开发人员:LZY
 * 创建时间:20160530
 */
public class SysOrgXiaon extends BaseModel {

	// 主键
	private Long id;
	// 商户id
	private String siteid;
	// 商户名称
	private String name;
	// 商户密码
	private String pass;
	// 商户主账号
	private String account;
	// 可创建的客服账号数量
	private String kfnum;
	// 0:启用 1：禁用
	private Long type;
	// 企业名称
	private String username;
	// 联系人
	private String connecter;
	// 电话
	private String tel;
	// 邮箱
	private String email;
	// 创建时间
	private Date createTime;
	// 最后修改时间
	private Date deadlineTime;
	//服务    0：在线    1：离线
	private String service;
	
	
	public String getOnlineService(){
		String s=null;
		if("0".equals(service)){
			s="在线";
			return s;
		}else if("1".equals(service)){
			s="离线";
			return s;
		}
		return s;
	}
	
	public String getOnlineType(){
		String s=null;
		if(0==type){
			s="启用";
			return s;
		}else if(1==type){
			s="禁用";
			return s;
		}
		return s;
	}
	public String getKtTime(){
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd");
		String formattime = null;
		if(createTime != null){
		formattime = time.format(createTime);
		}		
		return formattime;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(Date deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getKfnum() {
		return kfnum;
	}

	public void setKfnum(String kfnum) {
		this.kfnum = kfnum;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConnecter() {
		return connecter;
	}

	public void setConnecter(String connecter) {
		this.connecter = connecter;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("siteid", this.siteid).append("name", this.name)
				.append("pass", this.pass).append("account", this.account)
				.append("kfnum", this.kfnum).append("type", this.type)
				.append("createtime", this.createtime).append("service",this.service)
				.append("deadlinetime", this.deadlineTime).toString();
	}

}
