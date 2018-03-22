
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
	/* function chooseSubSystem(){
		//alert("选择");
		var userIds="${userIds}";
		//alert(userIds);
		var systemIds=new Array();
		var row = $.getSelectedRows($('#synchronizeForSystemItem'));
		if(row==null||row.length==0){
	 		$.ligerMessageBox.alert('提示信息','请选择系统');
			return false;
	 	}
		for(var i=0;i<row.length;i++){
			systemIds.push(row[i].id);
		}
		//alert(systemIds);
		window.location.href="${ctx}/subSystem/userDataToSystem.ht?userIds=${userIds}&systemIds="+systemIds;
		
	} */

	$("#btnChooseSubSystem").live("click",function(){
		var userIds="${userIds}";
		//alert(userIds);
		var systemIds=new Array();
		var row = $.getSelectedRows($('#synchronizeForSystemItem'));
		if(row==null||row.length==0){
	 		$.ligerMessageBox.alert('提示信息','请选择系统');
			return false;
	 	}
		for(var i=0;i<row.length;i++){
			systemIds.push(row[i].id);
		}
		//alert(systemIds);
		fun(userIds,systemIds);
	});
	
	var fun=function(userIds,systemIds){
		$.ajax({
    		type : 'POST',
    		url : "${ctx}/subSystem/userDataToSystem.ht?userIds="+userIds+"&systemIds="+systemIds,
    		dataType: "json",
    		success : function(data) {
    			if(data.result=='1'){
    				layer.alert("同步成功");
    			}
    			else{
    				layer.alert("数据加载失败");
    			}
    		},
    		error : function(data){
    			layer.alert("数据加载失败");
    		}
    	});
	}
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
				<form id="searchForm" method="post" action="synchronizeForSystem.ht">
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
			    <display:table name="synchronizeForSystemList" id="synchronizeForSystemItem" requestURI="synchronizeForSystem.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input type="checkbox" class="pk" name="id" value="${synchronizeForSystemItem.systemId}">
					</display:column>
					<display:column property="sysName" title="名称" sortable="true" sortName="sysName" ></display:column>
					<display:column property="alias" title="别名" sortable="true" sortName="alias" ></display:column>
					<display:column property="defaultUrl" title="首页地址" sortable="true" sortName="defaultUrl" ></display:column>
				</display:table>
				<hotent:paging tableId="synchronizeForSystemItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


