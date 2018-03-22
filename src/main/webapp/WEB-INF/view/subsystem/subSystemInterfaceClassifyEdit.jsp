<%--
	time:2011-11-21 12:22:07
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>修改子系统管理</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript"
	src="${ctx}/servlet/ValidJs?form=subSystem"></script>
<script type="text/javascript"
	src="${ctx }/js/lg/plugins/ligerWindow.js"></script>
<script type="text/javascript"
	src="${ctx }/js/hotent/platform/system/IconDialog.js"></script>
<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			if(${interfaceClassifyBean.id==0}){
				valid(showRequest,showResponse,1);
			}else{
				valid(showRequest,showResponse);
			}
			$("a.save").click(function() {
				$('#subSystemForm').submit(); 
				
			});
		});

		function selectIcon(){
			IconDialog({callback:function(src){
				$("#logo").val(src);
				$("#logoImg").attr("src",src);
				$("#logoImg").show();
			},params:"iconType=1"})
		};
	</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label"> <c:choose>
						<c:when test="${interfaceClassifyBean.id==0}">
					添加子系统
					</c:when>
						<c:otherwise>
					修改子系统
					</c:otherwise>
					</c:choose>
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="../interface/list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="subSystemForm" method="post" action="save.ht">

				<table class="table-detail" cellpadding="0" cellspacing="0"
					border="0">
					<tr>
						<th width="20%">名称: <span class="required">*</span></th>
						<td><input type="text" id="name" name="name"
							value="${interfaceClassifyBean.name}" class="inputText" /></td>
					</tr>
					<tr>
						<th width="20%">别名:</th>
						<td><input type="text" id="ename" name="ename"
							value="${interfaceClassifyBean.ename}" class="inputText" /></td>
					</tr>
				</table>

				<input type="hidden" name="id" value="${interfaceClassifyBean.id}" />
			</form>
		</div>
	</div>
</body>
</html>

