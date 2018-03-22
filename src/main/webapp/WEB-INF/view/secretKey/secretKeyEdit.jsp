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
<script src="${ctx}/js/assets/bootbox.js"></script>
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
		
		
		function searchSubSystem() {
			var urlShow = "";
		 	urlShow = '${ctx}/subSystem/list.ht?compType=1';
		 	subSystem_win = $.ligerDialog.open({
				url : urlShow,
				height : 500,
				width : 900,
				title : '子系统选择器',
				name : "frameDialog_"
			}); 
		}
		function createSecretKey(){
			$.ajax({
				url:"${ctx}/secretKey/createSecretKey.ht",
				success:function(data){
					$("#secretKey").val(data);
				}
			});
		}
		
		 function loadDocuments(row) {
			var id = row[0].id;
			var name = row[0].名称;
			$("#subSystemName").val(name);
			$("#subSystemId").val(id);
			subSystem_win.close();
		} 
		 
		 function loadDocumentsForClassify(row) {
				var id = row[0].id;
				var name = row[0].名称;
				$("#interfaceClassifyName").val(name);
				$("#interfaceClassifyId").val(id);
				classify_win.close();
			} 
		 
		 
	</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label"> <c:choose>
						<c:when test="${secretKeyBean.id==0}">
					添加秘钥
					</c:when>
						<c:otherwise>
					修改秘钥
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
						<a class="link back" href="/secretKey/list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="subSystemForm" method="post" action="save.ht">

				<table class="table-detail" cellpadding="0" cellspacing="0"
					border="0">
					<tr>
						<th width="20%">所属子系统: <span class="required">*</span></th>
						<td><input type="text" id="subSystemName" name="subSystemName"
							value="${secretKeyBean.subSystemName}" class="inputText" readonly="readonly"/><button type="button"  onclick="javascript:searchSubSystem()" class="btn btn-warning">选择子系统</button></td>
						
					</tr>
					<tr>
						<th width="20%">接口分类:</th>
						<td><input type="text" id="secretKey" name="secretKey"
							value="${secretKeyBean.secretKey}" class="inputText" /><button type="button"  onclick="javascript:createSecretKey()" class="btn btn-warning">生成秘钥</button></td>
					</tr>
				</table>
				<input type="hidden" name="subSystemId" value="${secretKeyBean.subSystemId}" id="subSystemId"/>
				<input type="hidden" name="id" value="${secretKeyBean.id}" />
			</form>
			
			<%-- <div id=zxtxzq style="display:none;overflow: scroll;">    
           <iframe src="${ctx}/subSystem/list.ht" style="padding-top:8px;width:950px;height:500px;" frameborder="0" scrolling="ture" allowTransparency="ture"></iframe> 
    </div> --%>
		</div>
	</div>
</body>
</html>

