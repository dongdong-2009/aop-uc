package com.casic.model;

import java.util.Date;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:系统消息 Model对象
 * 开发公司:航天智造
 * 开发人员:wangshumin
 * 创建时间:2017-11-3 18:33:25
 */
@SuppressWarnings("serial")
public class MsgSysLog extends BaseModel {
	protected String messageid;//msgid
	protected Long messagelogid;//日志id
	protected java.util.Date send_time;//发送时间
	protected String sender_ip;//发送者ip
	protected String operator;//操作人
	protected String requesturi;//
	protected String message_sending_state;//消息发送状态
	protected String message_details;//消息明细{JSON字符串}
	protected String org_info;// 获取当前操作企业
	protected Long user_id;//对应用户表
	protected Long userid;//对应用户表userid
	protected Long org_info_id;//对应企业表
	
	public Long getMessagelogid() {
		return messagelogid;
	}

	public void setMessagelogid(Long messagelogid) {
		this.messagelogid = messagelogid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getOrg_info() {
		return org_info;
	}
	
	public String getRequesturi() {
		return requesturi;
	}

	public void setRequesturi(String requesturi) {
		this.requesturi = requesturi;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setOrg_info(String org_info) {
		this.org_info = org_info;
	}
	public MsgSysLog() {
		super();
	}


	public String getMessageid() {
		return messageid;
	}

	public java.util.Date getSend_time() {
		return send_time;
	}
	public void setSend_time(java.util.Date send_time) {
		this.send_time = send_time;
	}
	public String getSender_ip() {
		return sender_ip;
	}
	public void setSender_ip(String sender_ip) {
		this.sender_ip = sender_ip;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getMessage_sending_state() {
		return message_sending_state;
	}
	public void setMessage_sending_state(String message_sending_state) {
		this.message_sending_state = message_sending_state;
	}
	public String getMessage_details() {
		return message_details;
	}
	public void setMessage_details(String message_details) {
		this.message_details = message_details;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getOrg_info_id() {
		return org_info_id;
	}
	public void setOrg_info_id(Long org_info_id) {
		this.org_info_id = org_info_id;
	}

	@Override
	public String toString() {
		return "MsgSysLog [messageid=" + messageid + ", messagelogid=" + messagelogid + ", send_time=" + send_time
				+ ", sender_ip=" + sender_ip + ", operator=" + operator + ", requesturi=" + requesturi
				+ ", message_sending_state=" + message_sending_state + ", message_details=" + message_details
				+ ", org_info=" + org_info + ", user_id=" + user_id + ", userid=" + userid + ", org_info_id="
				+ org_info_id + "]";
	}
	
	
	
}
