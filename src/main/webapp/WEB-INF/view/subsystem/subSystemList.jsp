
<%@include file="/commons/include/html_doctype.html" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	function ImportXml(systemId){
		var url=__ctx +"/platform/system/subSystem/import.ht";
		
		var conf=$.extend({},{dialogWidth:550 ,dialogHeight:200,help:0,status:0,scroll:0,center:1});
		var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
			+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
		
		url=url.getNewUrl();
		var rtn=window.showModalDialog(url,{systemId:systemId},winArgs);
	}
	
	function selectItem(){
	 	var row = $.getSelectedRows($('#subSystemItem'));
	 	if(row==null||row.length==0){
	 		$.ligerMessageBox.alert('提示信息','请选择');
			return false;
	 	}else{
	 		window.parent.loadDocuments(row);
	 	}
	}

	
	$(window.parent.document).find("#mainiframe").load(function(){
		
		var mainheight = 800;
		 $(this).height(mainheight);
	});
	
	

</script>

<script type="text/javascript">
	$(function(){
		var allowDelStatus = "${param.Q_allowDel_SN }";
		var optionValues=$("#allowDelStatus").find("option");
		for(var i=0;i<optionValues.length;i++){
			if(optionValues[i].value==allowDelStatus){
				$(optionValues[i]).attr("selected","selected");
			}
		}
		
		var isActiveStatus = "${param.Q_isActive_SN }";
		var statusValues=$("#isActiveStatus").find("option");
		for(var i=0;i<statusValues.length;i++){
			if(statusValues[i].value==isActiveStatus){
				$(statusValues[i]).attr("selected","selected");
			}
		}
	})

	
	//alert('${param['Q_sysName_SL']}');
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">子系统管理列表</span>
			</div>
			<div class="panel-toolbar">
			<c:if test="${empty compType}">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<!-- <div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
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
						<span class="label">系统标志:</span><input type="text" name="Q_systemId_SL"  class="inputText" style="width:10%;" value="${param['Q_systemId_SL']}"/>
						<span class="label">名称:</span><input type="text" name="Q_sysName_SL"  class="inputText" style="width:10%;" value="${param['Q_sysName_SL']}"/>
						<span class="label">别名:</span><input type="text" name="Q_alias_SL"  class="inputText" style="width:10%;" value="${param['Q_alias_SL']}"/>
						<span class="label">创建时间:</span><input  name="Q_begincreatetime_DL"  class="inputText date" style="width:10%;" value="${param['Q_begincreatetime_DL']}"/>
						<span class="label">至</span><input  name="Q_endcreatetime_DG" class="inputText date" style="width:10%;" value="${param['Q_endcreatetime_DG']}"/>
						<%-- <span class="label">允许删除:</span>
						<select name="Q_allowDel_SN" class="select" style="width:8%;" id="allowDelStatus">
							<option value="">全部</option>
							<option value="1" <c:if test="${param['Q_allowDel_SN'] == 1}">selected</c:if>>是</option>
							<option value="0" <c:if test="${param['Q_allowDel_SN'] == 0}">selected</c:if>>否</option>
						</select>
						<span class="label">是否激活:</span>
						<select name="Q_isActive_SN" class="select" style="width:8%;" id="isActiveStatus">
							<option value="">全部</option>
							<option value="1" <c:if test="${param['Q_isActive_SN'] == 1}">selected</c:if>>是</option>
							<option value="0" <c:if test="${param['Q_isActive_SN'] == 0}">selected</c:if>>否</option>
						</select> --%>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="subSystemList" id="subSystemItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${subSystemItem.systemId}">
					</display:column>
					<display:column property="systemId" title="系统标识" sortable="true" sortName="systemId" ></display:column>
					<display:column property="sysName" title="名称" sortable="true" sortName="sysName" ></display:column>
					<display:column property="alias" title="别名" sortable="true" sortName="alias" ></display:column>
					
					<%-- <display:column title="系统图标" sortName="logo">${param['Q_sysName_SL']
						<img alt="" src="${ctx }/${subSystemItem.logo}" >
					</display:column> --%>
					
					
					<display:column property="defaultUrl" title="首页地址" sortable="true" sortName="defaultUrl" ></display:column>
					<display:column  title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${subSystemItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
					
					<!-- 让屏蔽允许删除、是否激活、本地系统 -->
					<%-- <display:column title="允许删除" sortable="true" sortName="allowDel" style="text-align:center">
						<c:choose>
							<c:when test="${subSystemItem.allowDel eq 1}"><span class="green">是</span></c:when>
							<c:when test="${subSystemItem.allowDel eq 0}"><span class="red">否</span></c:when>
							<c:otherwise><span class="red">未设定</span></c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="是否激活" sortable="true" sortName="isActive" style="text-align:center">
						<c:choose>
							<c:when test="${subSystemItem.isActive eq 1}"><span class="green">是</span></c:when>
							<c:when test="${subSystemItem.isActive eq 0}"><span class="red">否</span></c:when>
							<c:otherwise><span class="red">未设定</span></c:otherwise>
						</c:choose>
					</display:column> --%>
					
					<display:column title="是否展示" sortable="true" sortName="isShow" style="text-align:center">
						<c:choose>
							<c:when test="${subSystemItem.isShow eq 1}"><span class="green">是</span></c:when>
							<c:when test="${subSystemItem.isShow eq 0}"><span class="red">否</span></c:when>
							<c:otherwise><span class="red">未设定</span></c:otherwise>
						</c:choose>
					</display:column>
					
					
					<!-- 让屏蔽允许删除、是否激活、本地系统 -->
					<%-- <display:column title="本地系统" sortable="true" sortName="isLocal" style="text-align:center">
						<c:choose>
							<c:when test="${subSystemItem.isLocal eq 0}"><span class="red">否</span></c:when>
							<c:otherwise><span class="green">是</span></c:otherwise>
						</c:choose>
					</display:column> --%>
					<!-- 同步信息 -->
					<display:column title="同步信息" sortable="true" sortName="sync" style="text-align:center">
						<c:choose>
							<c:when test="${subSystemItem.sync eq 0}"><span class="red">不同步</span></c:when>
							<c:when test="${subSystemItem.sync eq 1}"><span class="green">企业用户</span></c:when>
							<c:when test="${subSystemItem.sync eq 2}"><span class="green">企业用户角色</span></c:when>
						</c:choose>
					</display:column>
					<c:if test="${empty compType}">
					<display:column title="管理" media="html" style="width:200px;;text-align:center">
					<!-- 测试让去掉删除按钮 -->
						<%-- <c:choose>
							<c:when test="${subSystemItem.allowDel==1 }">
								<f:a alias="delSubsystem" href="del.ht?id=${subSystemItem.systemId}" css="link del">删除</f:a>
							</c:when>
							<c:otherwise>
								<a href="#" class="link del disabled">删除</a>
							</c:otherwise>
						</c:choose> --%>
						<a href="edit.ht?id=${subSystemItem.systemId}" class="link edit">编辑</a>
						<%-- <a href="exportXml.ht?systemId=${subSystemItem.systemId}" class="link download">导出</a>
						<a href="#" onclick="ImportXml(${subSystemItem.systemId})" class="link upload">导入</a> --%>
					</display:column>
					</c:if>
					
				</display:table>
				<hotent:paging tableId="subSystemItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


