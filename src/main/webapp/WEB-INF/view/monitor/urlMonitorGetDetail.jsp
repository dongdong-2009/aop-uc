
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>监控管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">子系统url监管页面</span>
			</div>
		</div>
		<div class="panel-body">
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="urlBeanList" id="urlBeanItem" requestURI="getDetail.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${urlBeanItem.id}">
					</display:column>
					<display:column property="subSysName" title="系统名称" sortable="true" sortName="subSysName" ></display:column>
					<display:column property="url" title="地址" sortable="true" sortName="url" ></display:column>
					<display:column  title="请求是否成功" sortable="true" sortName="isSuccess" >
						<c:if test="${urlBeanItem.isSuccess==1}">
							成功
						</c:if>
						<c:if test="${urlBeanItem.isSuccess==0}">
						          <font color="red">失败</font>
						</c:if>
					</display:column>
					<display:column property="accessTime" title="请求时间(毫秒)" sortable="true" sortName="accessTime" ></display:column>
				</display:table>
				<hotent:paging tableId="UrlBeanItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


