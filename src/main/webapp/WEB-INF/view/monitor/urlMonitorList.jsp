
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子系统接口分类管理</title>
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
				<span class="tbar-label">监控管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">URI:</span><input type="text" name="Q_url_SL"  class="inputText" style="width:10%;" value="${param['Q_url_SL']}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="urlBeanList" id="UrlBeanItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${UrlBeanItem.id}">
					</display:column>
					<display:column  title="URI" sortable="true" sortName="url" >
					
					<a href="${ctx}/urlMonitor/get.ht?url=${UrlBeanItem.url}">${UrlBeanItem.url}</a>
					</display:column>
					<display:column property="subSysName" title="系统名" sortable="true" sortName="subSysName" ></display:column>
					<display:column property="accessTotal" title="请求次数" sortable="true" sortName="accessTotal" ></display:column>
					<display:column property="accessTime" title="请求时间(毫秒)" sortable="true" sortName="accessTime" ></display:column>
					
				</display:table>
				<%-- <hotent:paging tableId="UrlBeanItem"/> --%>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


