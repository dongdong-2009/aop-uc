
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
				<span class="tbar-label">子系统接口分类管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">地址:</span><input type="text" name="Q_url_SL"  class="inputText" style="width:10%;" value="${param['Q_url_SL']}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="interfaceUrlBeanList" id="InterfaceUrlBeanItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${InterfaceUrlBeanItem.id}">
					</display:column>
					<display:column property="subSystemName" title="系统名称" sortable="true" sortName="subSystemName" ></display:column>
					<display:column property="url" title="地址" sortable="true" sortName="url" ></display:column>
					<display:column property="interfaceClassifyName" title="接口分类" sortable="true" sortName="interfaceClassifyName" ></display:column>
					<display:column  title="操作类型" sortable="true" sortName="typeName" >
						<c:if test="${InterfaceUrlBeanItem.type==1}">
							增加
						</c:if>
						<c:if test="${InterfaceUrlBeanItem.type==2}">
							修改
						</c:if>
						<c:if test="${InterfaceUrlBeanItem.type==3}">
							删除
						</c:if>
						<c:if test="${InterfaceUrlBeanItem.type==4}">
							查询
						</c:if>
					</display:column>
					<display:column  title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${InterfaceUrlBeanItem.createTime}" pattern="yyyy-MM-dd"/>
					</display:column>
					<display:column title="管理" media="html" style="width:200px;;text-align:center">
						<a href="${ctx}/interfaceUrl/edit.ht?id=${InterfaceUrlBeanItem.id}" class="link edit">编辑</a>
						<a href="${ctx}/interfaceUrl/del.ht?id=${InterfaceUrlBeanItem.id}" class="link del">删除</a>
					</display:column>
				</display:table>
				<hotent:paging tableId="InterfaceUrlBeanItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


