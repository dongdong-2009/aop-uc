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
		function searchClassify(){
			var urlShow = "";
		 	urlShow = '${ctx}/interface/list.ht?compType=1';
			classify_win = $.ligerDialog.open({
				url : urlShow,
				height : 500,
				width : 900,
				title : '接口分类选择器',
				name : "frameDialog_"
			}); 
		}
		
		 function loadDocuments(row) {
			var id = row[0].id;
			var name = row[0].名称;
			$("#subSystemName").val(name);
			$("#subId").val(id);
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
						<a class="link back" href="../interfaceUrl/list.ht">返回</a>
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
							value="${interfaceUrlBean.subSystemName}" class="inputText" readonly="readonly"/><button type="button"  onclick="javascript:searchSubSystem()" class="btn btn-warning">选择子系统</button></td>
						
					</tr>
					<tr>
						<th width="20%">接口分类:</th>
						<td><input type="text" id="interfaceClassifyName" name="interfaceClassifyName"
							value="${interfaceUrlBean.interfaceClassifyName}" class="inputText" readonly="readonly"/><button type="button"  onclick="javascript:searchClassify()" class="btn btn-warning">选择接口分类</button></td>
					</tr>
					<tr>
						<th width="20%">接口类型:</th>
						<td>
						<select name="type" >
							<option  value="1" <c:if test='${interfaceUrlBean.type==1}'>selected="selected"</c:if> >增加</option>
							<option  value="2" <c:if test='${interfaceUrlBean.type==2}'>selected="selected"</c:if> >修改</option>
							<option  value="3" <c:if test='${interfaceUrlBean.type==3}'>selected="selected"</c:if> >删除</option>
							<option  value="4" <c:if test='${interfaceUrlBean.type==4}'>selected="selected"</c:if> >查询</option>
						</select>
						</td>
						<%-- <td><input type="text" id="type" name="type"
							value="${interfaceUrlBean.type}" class="inputText" /></td> --%>
					</tr>
					<tr>
						<th width="20%">接口地址:</th>
						<td><input type="text" id="url" name="url"
							value="${interfaceUrlBean.url}" class="inputText" /></td>
					</tr>
				</table>
				<input type="hidden" name="subId" value="${interfaceUrlBean.subId}" id="subId"/>
				<input type="hidden" name="interfaceClassifyId" value="${interfaceUrlBean.interfaceClassifyId}" id="interfaceClassifyId"/>
				<input type="hidden" name="id" value="${interfaceUrlBean.id}" />
			</form>
			
			<%-- <div id=zxtxzq style="display:none;overflow: scroll;">    
           <iframe src="${ctx}/subSystem/list.ht" style="padding-top:8px;width:950px;height:500px;" frameborder="0" scrolling="ture" allowTransparency="ture"></iframe> 
    </div> --%>
		</div>
	</div>
</body>
</html>

