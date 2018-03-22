
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
function confirmDel(orgId){
	   layer.confirm('确定驳回吗？', {
		   btn: ['确定','取消'] //按钮
		 }, function(index){
			 ajaxDel(orgId,1);
			 layer.close(index);
		 }, function(index){
			 layer.close(index);
	  });
}



function ajaxDel(orgId,state){
	   $.ajax({
			type : 'post',
			dataType : 'json',
			async : false,
			data:{
				state : state,
				sysOrgInfoId : orgId
			},
			url : '${ctx}/tenant/depTenant.ht',
			success : function(dics){
				if(dics){
					window.location.reload();
				}else{
					layer.alert("审核失败！");
				}
			}
	   });
}

function batchPass(state){
	//alert(state);
	var sysOrgInfoIds=new Array();
	var row = $.getSelectedRows($('#InfoItem'));
	//console.info(row);
	//alert(row.length);
	if(row==null||row.length==0){
 		$.ligerMessageBox.alert('提示信息','请选择需要审核的企业');
		return false;
 	}
	for(var i=0;i<row.length;i++){
		//alert(row[i].sysOrgInfoId);
		sysOrgInfoIds.push(row[i].sysOrgInfoId);
	}
	//alert(sysOrgInfoIds);
	var url="${ctx}/tenant/batchPass.ht?sysOrgInfoIds="+sysOrgInfoIds;
	 $.ajax({
			type : 'post',
			dataType : 'json',
			async : false,
			data:{
				state : state
			},
			url : url,
			success : function(dics){
				if(dics){
					window.location.href="${ctx}/tenant/audit.ht";
				}else{
					layer.alert("审核失败！");
				}
			}
	   });
}; 

function batchReject(){
	var sysOrgInfoIds=new Array();
	var row = $.getSelectedRows($('#InfoItem'));
	//console.info(row);
	//alert(row.length);
	if(row==null||row.length==0){
 		$.ligerMessageBox.alert('提示信息','请选择需要驳回的企业');
		return false;
 	}
	for(var i=0;i<row.length;i++){
		//alert(row[i].sysOrgInfoId);
		sysOrgInfoIds.push(row[i].sysOrgInfoId);
	}
	
	layer.confirm('确定驳回吗？', {
		   btn: ['确定','取消'] //按钮
		 }, function(index){
			 $.ajax({
					type : 'post',
					dataType : 'json',
					async : false,
					data:{
						state : 1
					},
					url : "${ctx}/tenant/batchReject.ht?sysOrgInfoIds="+sysOrgInfoIds,
					success : function(dics){
						if(dics){
							window.location.href="${ctx}/tenant/audit.ht";
						}else{
							layer.alert("驳回失败！");
						}
					}
			   });
			 layer.close(index);
		 }, function(index){
			 layer.close(index);
	  });
	
}; 
	

$(window.parent.document).find("#mainiframe").load(function(){
	
	var mainheight = 800;
	 $(this).height(mainheight);
});
var yer=0;
$("#btnSynchronize").live("click",function(){
	var tenantIds=new Array();
	var row = $.getSelectedRows($('#InfoItem'));
	if(row==null||row.length==0){
 		$.ligerMessageBox.alert('提示信息','请选择需要同步的企业');
		return false;
 	}
	for(var i=0;i<row.length;i++){
		tenantIds.push(row[i].sysOrgInfoId);
	}
	 yer=layer.open({ 
		 	type: 2,
		    title: '子系统信息',
		    shadeClose: false,
		    fix: true, 
		    icon: 1,
		    shade: 0.6,
		    offset:50,
		    area: ['1000px', '700px'],
		    content:"${ctx}/subSystem/synchronizeForTenant.ht?tenantIds="+tenantIds //iframe的url
	});
});
function closeWin(){
	layer.close(yer);
	window.location.href="${ctx}/tenant/audit.ht";
	
}
$(function(){
	var temid=$("input[name='temid']").val();
	if(temid != ""){
		$("#hide").hide();
	}	
})
</script>

<style type="text/css">
#btnSynchronize:hover{
	text-decoration:underline;
}

</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">企业审核列表</span>
			</div>
			<div class="panel-toolbar">
			
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>
				<div class="l-bar-separator"></div>
				<div class="group"><a onclick="batchPass(5)" style="cursor:pointer;color: #336699;margin-top:1px;">批量通过</a></div>	
				<div class="l-bar-separator"></div>
				<div class="group"><a onclick="batchReject()" style="cursor:pointer;color: #336699;margin-top:1px;">批量驳回</a></div>	
				<div id="hide">
				<div class="l-bar-separator"></div>
				<div class="group"><a id="btnSynchronize" style="cursor:pointer;color: #336699;margin-top:1px;">同步</a></div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="audit.ht">
				<input type="hidden" name="temid" value="${temid }"/>
					<div class="row">
						<span class="label">企业号:</span><input type="text" name="Q_sysOrgInfoId_SL"  class="inputText" style="width:10%;" />
						<span class="label">企业名称:</span><input type="text" name="Q_name_SL"  class="inputText" style="width:10%;" />
						<span class="label">手机号码:</span><input type="text" name="Q_tel_SL"  class="inputText" style="width:10%;" />
						<span class="label">注册时间:</span><input  name="Q_begincreatetime_DL"  class="inputText date" style="width:10%;" />
						<span class="label">至</span><input  name="Q_endcreatetime_DG" class="inputText date" style="width:10%;" />
						<c:if test="${flag==0 }">
						<span class="label">系统来源:</span><input type="text" name="Q_systemId_SL"  class="inputText" style="width:10%;" />
						</c:if>
					</div>
				</form>
			</div>
		</div>
		
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="tenantList" id="InfoItem" requestURI="audit.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input id="sysOrgInfoId" type="checkbox" class="pk" name="sysOrgInfoId" value="${InfoItem.sysOrgInfoId}">
					</display:column>
					<display:column property="sysOrgInfoId" title="企业号" sortable="true" sortName="sysOrgInfoId" ></display:column>
					<display:column property="name" title="企业名称" sortable="true" sortName="name" ></display:column>
					<display:column property="connecter" title="联系人" sortable="true" sortName="connecter" ></display:column>
					<display:column property="tel" title="手机号码" sortable="true" sortName="tel" ></display:column>
					<display:column  title="爱信诺状态" sortable="true" sortName="axnStatus" >
					<c:if test="${InfoItem.axnStatus== 2}">
						通过
					</c:if>
					<c:if test="${InfoItem.axnStatus== 0||InfoItem.axnStatus== 1||InfoItem.axnStatus== 3}">
						未通过
					</c:if>
					
					</display:column>
					<display:column  title="企业状态" sortable="true" sortName="state" >
					  <c:if test="${InfoItem.state== 0 or InfoItem.state== 2}">
						待审核
					</c:if>
					<c:if test="${InfoItem.state== 1 }">
						审核未通过
					</c:if>
					<c:if test="${InfoItem.state== 5}">
						审核通过
					</c:if>
					<c:if test="${InfoItem.state== 6}">
						待审核(冻结)
					</c:if>
					
					</display:column>
					<display:column  title="注册时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${InfoItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
					<c:if test="${not empty InfoItem.state}">
					<display:column title="管理" media="html" style="width:200px;;text-align:center">
					<c:if test="${InfoItem.state == 0 || InfoItem.state == 1 || InfoItem.state == 2||InfoItem.state == 6}">
						<a href="javascript:void(0);" class="link deploy" onclick="ajaxDel('${InfoItem.sysOrgInfoId}','5');">通过</a>
						<a href="javascript:void(0);" class="link notAgree" onclick="confirmDel('${InfoItem.sysOrgInfoId}');">驳回</a>
					</c:if>
						<a href="${ctx}/tenant/detail.ht?sysOrgInfoId=${InfoItem.sysOrgInfoId}" class="link detail">明细</a>
					</display:column>
					</c:if>
				</display:table>
				<hotent:paging tableId="InfoItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


