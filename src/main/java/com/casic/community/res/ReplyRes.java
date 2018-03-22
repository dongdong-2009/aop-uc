package com.casic.community.res;

import java.util.List;

import com.casic.community.model.ReplyBean;

public class ReplyRes {
	
	private List<ReplyBean> replyBeans;
	private Integer total;
	public List<ReplyBean> getReplyBeans() {
		return replyBeans;
	}
	public void setReplyBeans(List<ReplyBean> replyBeans) {
		this.replyBeans = replyBeans;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
	

}
