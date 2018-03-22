package com.casic.community.model;

import com.hotent.core.model.BaseModel;

/***
 * 数量统计
 *
 */
public class VisitorCountBean extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	protected Long  id;
	
	protected Long passageId;
	
	protected Long readCount;
	
	protected Long replyCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

	public Long getPassageId() {
		return passageId;
	}

	public void setPassageId(Long passageId) {
		this.passageId = passageId;
	}

	public Long getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}
	
}
