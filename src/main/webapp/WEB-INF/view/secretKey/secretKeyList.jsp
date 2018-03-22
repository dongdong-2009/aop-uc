
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子系统接口分类管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	function selectItem(){
	 	var row = $.getSelectedRows($('#InterfaceClassifyBeanItem'));
	 	if(row==null||row.length==0){
	 		$.ligerMessageBox.alert('提示信息','请选择');
			return false;
	 	}else{
	 		window.parent.loadDocumentsForClassify(row);
	 	}
	}
	
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
				<span class="tbar-label">秘钥管理</span>
			</div>
			<div class="panel-toolbar">
			<c:if test="${empty compType}">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
				</c:if>
				<c:if test="${not empty compType}">
				<div class="toolBar">
					<div class="group"><a href="#" id="ok" onclick="selectItem()" class="link add">选择</a></div>
				</div>
				</c:if>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">系统名称:</span><input type="text" name="Q_subSystemName_SL"  class="inputText" style="width:10%;" value="${param['Q_subSystemName_SL']}"/>
						<span class="label">秘钥:</span><input type="text" name="Q_secretKey_SL"  class="inputText" style="width:10%;" value="${param['Q_secretKey_SL']}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="secretKeyBeanList" id="SecretKeyBeanItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${SecretKeyBeanItem.id}">
					</display:column>
					<display:column property="subSystemName" title="系统名" sortable="true" sortName="subSystemName" ></display:column>
					<display:column property="secretKey" title="秘钥" sortable="true" sortName="secretKey" ></display:column>
					<display:column  title="创建时间" sortable="true" sortName="date">
						<fmt:formatDate value="${SecretKeyBeanItem.date}" pattern="yyyy-MM-dd"/>
					</display:column>
					
					<display:column title="管理" media="html" style="width:200px;;text-align:center">
						<a href="${ctx}/secretKey/edit.ht?id=${SecretKeyBeanItem.id}" class="link edit">编辑</a>
						<a href="${ctx}/secretKey/del.ht?id=${SecretKeyBeanItem.id}" class="link del">删除</a>
					</display:column>
				</display:table>
				<hotent:paging tableId="SecretKeyBeanItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


