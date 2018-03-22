<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>用户角色映射表管理</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx }/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
	function dlgCallBack(userIds, fullnames) {
		if (userIds.length > 0) {
			var form = new com.hotent.form.Form();
			form.creatForm("form", "${ctx}/platform/system/userRole/add.ht");
			form.addFormEl("roleId", "${roleId}");
			form.addFormEl("userIds", userIds);
 	 		form.submit();
		}
	};
	var yer=0;
	$("div.group > a.link.add").live("click",function(){
		
	/* 	var roleIds=new Array();
		var row = $.getSelectedRows($('#userRoleItem'));
		if(row==null||row.length==0){
	 		$.ligerMessageBox.alert('提示信息','请选择角色');
			return false;
	 	}
		for(var i=0;i<row.length;i++){
			roleIds.push(row[i].roleId);
		} */
		 yer=layer.open({ 
			 	type: 2,
			    title: '用户信息',
			    shadeClose: true,
			    fix: true, 
			    icon: 1,
			   // shade: 0.6,
			    offset:40,
			    area: ['1100px', '660px'],
			    content:"${ctx}/platform/system/sysUser/dialog.ht?roleId="+${roleId} //iframe的url
		});
	});
	function closeWin(){
		layer.close(yer);
		//window.location.href="${ctx}/tenant/audit.ht";	
		window.location.href="${ctx}/platform/system/userRole/edit.ht";	
	}
	  /* function add() {
		UserDialog({
			callback : dlgCallBack,
			isSingle : false
		});
	}   */
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">分配用户</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<!-- <div class="l-bar-separator"></div>
					<div class="group">
						<a class="link add" href="javascript:void(0);">选择用户</a>onclick="add()"
					</div> -->
					<!--<div class="l-bar-separator"></div>
					 <div class="group">
						<a class="link del" action="del.ht">删除</a>
					</div> -->
					<div class="l-bar-separator"></div>
					<div class="group">
					<a href="javascript:window.location.href = document.referrer;" class="link back" style="cursor:pointer;color: #336699;margin-top:1px;">返回</a>
						<%-- <a class="link back"
							href="${ctx }/platform/system/sysRole/list.ht">返回</a> --%>
					</div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="edit.ht?roleId=${roleId}">
						<div class="row">
							<span class="label">姓名:</span><input type="text" name="Q_fullname_SL"  class="inputText" value="${param['Q_fullname_SL']}"/>
							<span class="label">帐号:</span><input type="text" name="Q_account_SL"  class="inputText" value="${param['Q_account_SL']}"/>					
						</div>
						
				</form>
			</div>
		</div>
		<div class="panel-body">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
			<display:table name="userRoleList" id="userRoleItem"
				requestURI="edit.ht" sort="external" cellpadding="1"
				cellspacing="1" export="false" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox" class="pk" name="userRoleId" value="${userRoleItem.userRoleId}">
				</display:column>

				<display:column property="fullname" title="用户名称" sortable="true" sortName="fullname"></display:column>
				<display:column property="account" title="帐号" sortable="true" sortName="account"></display:column>

			<%-- 	<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?userRoleId=${userRoleItem.userRoleId}" class="link del">删除</a>
				</display:column> --%>
			</display:table>
			<hotent:paging tableId="userRoleItem"/>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


