
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">

	$("#btnChooseSubSystem").live("click",function(){
		var tenantIds="${tenantIds}";
		//alert(tenantIds);
		var systemIds=new Array();
		var row = $.getSelectedRows($('#synchronizeForTenantItem'));
		if(row==null||row.length==0){
	 		$.ligerMessageBox.alert('提示信息','请选择系统');
			return false;
	 	}
		for(var i=0;i<row.length;i++){
			systemIds.push(row[i].id);
		}
		var url="${ctx}/user/tenantDataToSystem.ht?tenantIds=${tenantIds}&systemIds="+systemIds;
		$.ajax({
			type : 'POST',
			url : url,
			dataType: "json",
			success : function(data) {
				if(data!=null && data.result=='1'){
					layer.alert("企业同步成功",function(index){
						layer.close(index);
						parent.closeWin();
					});
				}
				else{
					layer.alert("企业同步失败",function(index){
						layer.close(index);
						parent.closeWin();
					});
				}
			},
			error : function(data){
				layer.alert("企业同步失败",function(){
				layer.close(index);
				parent.closeWin();	
				});
			}
		});
	});
</script>

<style type="text/css">
	#btnChooseSubSystem:hover{
		text-decoration:underline;
	}
</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">子系统列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<!-- <div class="group"><input type="button" value="确定" onclick="chooseSubSystem()"/></div> -->
					<div class="group"><a id="btnChooseSubSystem" style="cursor:pointer;color: #336699;margin-top:1px;">确定</a></div>
				</div>
			</div>
			<!-- <input type="button" value="确定" onclick="chooseSubSystem()"/> -->
			<div class="panel-search">
				<form id="searchForm" method="post" action="synchronizeForTenant.ht">
					<div class="row">
						<span class="label">名称:</span><input type="text" name="Q_sysName_SL"  class="inputText" style="width:10%;" value="${param['Q_sysName_SL']}"/>
						<span class="label">别名:</span><input type="text" name="Q_alias_SL"  class="inputText" style="width:10%;" value="${param['Q_alias_SL']}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="tenantList" id="synchronizeForTenantItem" requestURI="synchronizeForTenant.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input type="checkbox" class="pk" name="id" value="${synchronizeForTenantItem.systemId}">
					</display:column>
					<display:column property="sysName" title="名称" sortable="true" sortName="sysName" ></display:column>
					<display:column property="alias" title="别名" sortable="true" sortName="alias" ></display:column>
					<display:column property="defaultUrl" title="首页地址" sortable="true" sortName="defaultUrl" ></display:column>
				</display:table>
				<hotent:paging tableId="synchronizeForTenantItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


