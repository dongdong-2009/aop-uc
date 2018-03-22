package com.casic.community.model;

import java.util.List;

import com.hotent.core.model.BaseModel;

/***
 * 社区回复bean
 * @author 张小鑫
 *
 */
public class ReplyBean extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	protected Long  id;
	
	protected String questionId;//问题的Id
	
	protected String  createById;//问题创建人的Id
	
	protected String  createByName;//问题的名称
	
	protected String  createTime;//回复时间
	
	protected String  content;//回复内容
	
	protected String  replyId;//回复人的Id
	
	protected String replyName;//回复人名称;
	
	protected String  remark1;
	
	protected String  remark2;
	
	protected String  remark3;
	
	protected String timeAgo;
	
	private String status;//是否点赞(数据库中实际不存在) 
    
	private String count;//点赞总数
	
	private List<PraiseBean> praiseBeans;
	
	private Long praiseId;//当前登录用户的点赞id
	
	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
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

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<PraiseBean> getPraiseBeans() {
		return praiseBeans;
	}

	public void setPraiseBeans(List<PraiseBean> praiseBeans) {
		this.praiseBeans = praiseBeans;
	}

	public Long getPraiseId() {
		return praiseId;
	}

	public void setPraiseId(Long praiseId) {
		this.praiseId = praiseId;
	}

	
	
	

}
