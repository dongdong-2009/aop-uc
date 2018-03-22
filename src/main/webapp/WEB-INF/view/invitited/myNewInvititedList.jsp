
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>邀请统计</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/invitited/myInvititedList.js"></script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">我的邀请</span>
			</div>
			<div class="panel-toolbar">
			<div class="l-bar-separator"></div>
				<div class="toolBar">
					<div class="group"><a class="link export" id="export">导出</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="tenantInfoList" id="tenantInfoItem" requestURI="${ctx}/invitited/myList.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<%-- <display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${tenantInfoItem.id}">
					</display:column> --%>
					<display:column  title="序号"  >${tenantInfoItem_rowNum}</display:column>
					<display:column property="name" title="企业名称" sortable="true" sortName="name" ></display:column>
					<display:column  title="账户类型" sortable="true"  >注册会员</display:column>
					<display:column  title="创建日期" sortable="true" sortName="createtime" >
						<fmt:formatDate value="${tenantInfoItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
					
				</display:table>
				<hotent:paging tableId="tenantInfoItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<iframe style="display:none;" id="downFile"></iframe>
</body>
</html>


