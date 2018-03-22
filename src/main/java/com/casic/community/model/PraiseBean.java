package com.casic.community.model;

import com.hotent.core.model.BaseModel;

/**
 * 
 * @author ypchenga
 *
 */
public class PraiseBean extends BaseModel {
    private static final long serialVersionUID = 1L;
	
	protected Long  id;
	
	protected Long replyId;//回复Id
	
	protected String userId;//点赞人
	
	protected String status;//是否点过赞0未点
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}



	
	
	
	
}
