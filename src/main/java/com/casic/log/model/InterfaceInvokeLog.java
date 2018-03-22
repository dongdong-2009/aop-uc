package com.casic.log.model;

public class InterfaceInvokeLog {

		// 日志Id
		protected Long id;
		// 执行时间
		protected java.util.Date executeTime;
		// 执行人ID
		protected Long executorId;
		// IP来源
		protected String fromIp;
		// 执行方法
		protected String executeMethod;
		// 请求URL
		protected String requestUrl;
		// 请求参数
		protected String requestParams;
		//回调结果
		protected String responseResult;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public java.util.Date getExecuteTime() {
			return executeTime;
		}
		public void setExecuteTime(java.util.Date executeTime) {
			this.executeTime = executeTime;
		}
		public Long getExecutorId() {
			return executorId;
		}
		public void setExecutorId(Long executorId) {
			this.executorId = executorId;
		}
		public String getFromIp() {
			return fromIp;
		}
		public void setFromIp(String fromIp) {
			this.fromIp = fromIp;
		}
		public String getExecuteMethod() {
			return executeMethod;
		}
		public void setExecuteMethod(String executeMethod) {
			this.executeMethod = executeMethod;
		}
		public String getRequestUrl() {
			return requestUrl;
		}
		public void setRequestUrl(String requestUrl) {
			this.requestUrl = requestUrl;
		}
		public String getRequestParams() {
			return requestParams;
		}
		public void setRequestParams(String requestParams) {
			this.requestParams = requestParams;
		}
		public String getResponseResult() {
			return responseResult;
		}
		public void setResponseResult(String responseResult) {
			this.responseResult = responseResult;
		}
		
		
		
		
}
