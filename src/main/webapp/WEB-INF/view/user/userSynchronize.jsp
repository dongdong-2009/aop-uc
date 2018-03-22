
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/user/timerago.js"></script>
<script type="text/javascript">
  jQuery(function(){
	  var allowDelStatus = "${param.Q_mark_SN }";
		var optionValues=$("#allowDelStatus").find("option");
		for(var i=0;i<optionValues.length;i++){
			if(optionValues[i].value==allowDelStatus){
				$(optionValues[i]).attr("selected","selected");
			}
		}
  });
  $(window.parent.document).find("#mainiframe").load(function(){
		
		var mainheight = 800;
		 $(this).height(mainheight);
	});
</script>
<script type="text/javascript">

var options = {};
var dd = {}
if (showResponse) {
	options.success = showResponse;
}

function showResponse(responseText) {
	var obj = new com.hotent.form.ResultMessage(responseText);
	dd.close();
	if (obj.isSuccess()) {
		//"导入成功！"
	layer.alert(obj.getMessage(),{icon:1, closeBtn: 0,offset: '40%'},function(){
		$("#port").attr("disabled",false);
		window.location.href="${ctx}/user/synchronize.ht";
	})
	} else {
		var length=obj.getMessage().length;
		if(length>100){
			layer.open({
				title :'导入失败',
			    content: obj.getMessage(),
			    closeBtn: 0,
			    icon :2,
			    area: ['980px', '700px'],
			    btn: ['返回重新导入','取消']
			,yes: function(index, layero){ //或者使用btn1
				  layer.close(index);
				  $("#port").attr("disabled",false);
				  window.location.href="${ctx}/user/synchronize.ht";
			  },cancel: function(index){ //或者使用btn2
				  layer.close(index);
				  $("#port").attr("disabled",false);
				  window.location.href="${ctx}/user/synchronize.ht";
			  }
				
			});
	
				
		}else
		{
			$("#port").attr("disabled",false);
			 $('#comForm')[0].reset();
		   $.ligerMessageBox.error("提示信息", obj.getMessage());
		}
	}
}

 
	
	
	$("#btnSynchronize").live("click",function(){
		var userIds=new Array();
		var row = $.getSelectedRows($('#userSynchronizeItem'));
	
		if(row==null||row.length==0){
	 		$.ligerMessageBox.alert('提示信息','请选择需要同步的用户');
			return false;
	 	}
		for(var i=0;i<row.length;i++){
		
			userIds.push(row[i].id);
		}
		
		var url="${ctx}/subSystem/synchronizeForSystem.ht?userIds="+userIds;
		$.ligerDialog.open({ 
			url:url, 
			height: 700,
			width: 850, 
			title :'子系统列表', 
			name : "frameDialog_",
			allowClose: true
		});
	});
  //模板下载
	function downLoad(){
	    //使用js创建一个a标签
	   
		$("#downFile").attr("src","${ctx}/user/downLoad.ht?svrPath=template/comPany.xls");
	}
  //excel导入
  function importCompany(){
	        var uId=$("#uId").val();
	        if(uId==null||uId==''){
	        	$.ligerMessageBox.alert("提示信息","当前session已失效,请重新登录!");
	        	return ;
	        }
	  
			var frm = $('#comForm').form();
			var file = $('#comForm').find("input[name='excel']").val();
			var tem=file.substring(file.length-3,file.length);
			
			
			if(file==""){
				$.ligerMessageBox.alert("提示信息","请选择文件！");
				return;
			}
			
			if(tem!="xls"){
				$.ligerMessageBox.alert("提示信息","请选择扩展名为.xls的文件！");
				return;
			}
			
			dd = $.ligerDialog.waitting('正在导入,请等待...');			
			frm.ajaxForm(options);
			frm.submit();
			$("#port").attr("disabled",true);
	
  }
  $(function(){
	  $("input[type='file']").bind('input propertychange', function() {
		  $("#port").attr("disabled",false);
		});
  });
  $(function(){
	  var uId=$("input[name='temid']").val();
	  if(uId != ""){
		  $("#hid").hide();
	  }
  })
  
  $(function(){
	//导出excel
  $("#export").click(function(){
	  var currentPage=$("input[name='currentPage']").val();
	  var pageSize=$("input[name='oldPageSize']").val();
	  $("#downFile").attr("src",CTX+"/user/exportExcel.ht?currentPage="+currentPage+"&pageSize="+pageSize);
	  
  })
});
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
				<span class="tbar-label">用户同步列表</span>
				<input type="hidden" id="uId" value="${userId}">
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div id="hid">
					<div class="l-bar-separator"></div>
					<!-- <div class="group"><input type="button" value="同步" onclick="userSynchronize()"/></div> -->
					<div class="group"><a id="btnSynchronize" style="cursor:pointer;color: #336699;margin-top:1px;">同步</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="export" style="cursor:pointer;color: #336699;margin-top:1px;">导出Excel</a></div>
					
					</div>
				    <div class="Rgroup" style="float:right">
                       <form id="comForm" action="${ctx}/user/importCompany.ht" method="post" enctype="multipart/form-data">
					企业批量导入：<input type="file" name="excel"> <input type="button" onclick="importCompany()" value="导入" id="port"> 
					    <span onclick="downLoad();" style="cursor:pointer;color:#336699">模板下载</span>
				      </form>
  
</div>
				</div>
			</div>
			<!-- <input type="button" value="同步" onclick="userSynchronize()"/> -->
			<div class="panel-search">
				<form id="searchForm" method="post" action="synchronize.ht">
					<input name="temid" value="${systemid }" type="hidden"/>
					<div class="row">
						<span class="label">企业名称:</span><input type="text" name="Q_orgName_SL"  class="inputText" style="width:10%;" value="${param['Q_orgName_SL']}"/>
						<span class="label">账号:</span><input type="text" name="Q_account_SL"  class="inputText" style="width:10%;" value="${param['Q_account_SL']}"/>
						<span class="label">手机:</span><input type="text" name="Q_mobile_SL"  class="inputText" style="width:10%;" value="${param['Q_mobile_SL']}"/>
						<span class="label">邮箱:</span><input type="text" name="Q_email_SL"  class="inputText" style="width:10%;" value="${param['Q_email_SL']}"/>
						<span class="label">注册时间:</span><input  name="Q_begincreatetime_DL"  class="inputText date" style="width:10%;" value="${param['Q_begincreatetime_DL']}"/>
						<span class="label">至</span><input  name="Q_endcreatetime_DG" class="inputText date" style="width:10%;" value="${param['Q_endcreatetime_DG']}"/>
						<select name="Q_mark_SN" class="select" style="width:8%;" id="allowDelStatus">
							<option value="">注册来源</option>
							<option value="1" <c:if test="${param['Q_mark_SN'] == 1}">selected</c:if>>平台注册</option>
							<option value="2" <c:if test="${(empty param['Q_mark_SN']) or param['Q_mark_SN'] == 0}">selected</c:if>>批量导入</option>
						</select>
					</div>
				</form>
			</div>
		</div>
		
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="userSynchronizeList" id="userSynchronizeItem" requestURI="synchronize.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input id="userId" type="checkbox" class="pk" name="id" value="${userSynchronizeItem.userId}">
					</display:column>
					<%-- <display:column property="userId" title="用户ID" sortable="true" sortName="userId" style="display:none"></display:column> --%>
					<display:column property="orgName" title="企业名称" sortable="true" sortName="orgName" ></display:column>
					<display:column property="account" title="账户" sortable="true" sortName="account" ></display:column>
					<display:column property="connecter" title="联系人" sortable="true" sortName="connecter" ></display:column>
					<display:column property="mobile" title="手机" sortable="true" sortName="mobile" ></display:column>
					<display:column property="email" title="邮箱" sortable="true" sortName="email" ></display:column>
					<display:column  title="注册时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${userSynchronizeItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
				</display:table>
				<hotent:paging tableId="userSynchronizeItem"/>
			
		</div><!-- end of panel-body -->	
					
	</div> <!-- end of panel -->
	
	<iframe style="display:none;" id="downFile"></iframe>
</body>
</html>


