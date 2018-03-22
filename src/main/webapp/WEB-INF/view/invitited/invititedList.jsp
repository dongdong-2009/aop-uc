
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>邀请统计</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
$(window.parent.document).find("#mainiframe").load(function(){
	
	var mainheight = 800;
	 $(this).height(mainheight);
});
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">邀请统计管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">邀请码:</span><input type="text" name="Q_invititedCode_SL"  class="inputText" style="width:10%;" value="${param['Q_invititedCode_SL']}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="tenantInfoList" id="tenantInfoItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<%-- <display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${tenantInfoItem.id}">
					</display:column> --%>
					<display:column  title="序号" sortable="true" >${tenantInfoItem_rowNum}</display:column>
					<display:column property="name" title="企业名称" sortable="true" sortName="name" ></display:column>
					<display:column  title="账户类型" sortable="true"  >注册会员</display:column>
					<display:column  title="创建日期" sortable="true" sortName="createtime" >
						<fmt:formatDate value="${tenantInfoItem.createtime}" pattern="yyyy-MM-dd :hh:mm:ss"/>
					</display:column>
					
				</display:table>
				<hotent:paging tableId="tenantInfoItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


