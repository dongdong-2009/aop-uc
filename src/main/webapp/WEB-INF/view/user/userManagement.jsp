
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/user/timerago.js"></script>


<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>

<script type="text/javascript">
  jQuery(function(){
	  
 
  $(window.parent.document).find("#mainiframe").load(function(){
		var mainheight = 800;
		 $(this).height(mainheight);
	});
  
  
  });
	var  liger=null;
 var assign=function(id,userId){
	 
	 
		var url="${ctx}/tenant/assignRole.ht?openId="+id+"&userId="+userId;
		liger=$.ligerDialog.open({ 
			url:url, 
			height: 700,
			width: 850, 
			title :'角色分配', 
			name : "frameDialog_",
			allowClose: true
		});
	  
  
  }
 
 var closeWin=function(){
	 liger.close();
	 
 }
 
 var assignDepartMent=function(id,userId){
		var url="${ctx}/tenant/assignDepartMent.ht?openId="+id+"&userId="+userId;
		liger=$.ligerDialog.open({ 
			url:url, 
			height: 700,
			width: 850, 
			title :'部门分配', 
			name : "frameDialog_",
			allowClose: true
		});
	 
 }
 
 //驳回
 var disAgree=function(userId){
	 layer.confirm('确定驳回吗?', {icon: 3, title:'提示'}, function(index){
	 bohui(userId);
      layer.close(index);
   }); 
 }
 var bohui=function(userId){
	 $.ajax({           
	     cache: false,
	     type: "POST", 
	     url:"${ctx}/user/rejectUser.ht",
	     data : {userId : userId},
	     dataType : 'json',
	     async: false,
	     error: function(request) {  
	    	 layer.alert("网络异常，请联系管理人员"); 
	     },  
	      success: function(data) {  
	    	 if(data.result==1){
		    	 layer.alert(data.message, {
				    	skin: 'layui-layer-molv' //样式类名
						    ,closeBtn: 0
							},function(){
		    		 window.location.reload();
		    	 });
		       }
	      else{
	    	  layer.alert(data.message, {
			    	skin: 'layui-layer-molv' //样式类名
					    ,closeBtn: 0
						},function(){
		    		 window.location.reload();
		    	 });
	      }
	   }
	});
 }
 
 var agree=function(userId){
	 
	 $.ajax({
			type : 'post',
			dataType : 'json',
			url : '${ctx}/user/passUser.ht',
			data : {
				userId:userId		
			},
			success:function(data){
				if(data.result==1){
			    	 layer.alert(data.message,{
					    	skin: 'layui-layer-molv' //样式类名
							    ,closeBtn: 0
								},function(){
			    		 window.location.reload();
			    	 });
			       } else{
				    	  layer.alert(data.message,{
						    	skin: 'layui-layer-molv' //样式类名
								    ,closeBtn: 0
									},function(){
					    		 window.location.reload();
					    	 });
				      }
		     
				
				
			},
			 error: function(request) {  
			     	layer.alert("网络异常，请联系管理人员"); 
			     }
	 });
 }
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">用户管理列表</span><span id="num" class="${userNum }"></span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<!-- <div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="management.ht">
					<div class="row">
						<span class="label">姓名:</span><input type="text" name="Q_fullname_SL"  class="inputText" style="width:10%;" value="${param['Q_fullname_SL']}"/>
						<span class="label">用户名:</span><input type="text" name="Q_account_SL"  class="inputText" style="width:10%;" value="${param['Q_account_SL']}"/>
						<span class="label">手机:</span><input type="text" name="Q_mobile_SL"  class="inputText" style="width:10%;" value="${param['Q_mobile_SL']}"/>
						<span class="label">邮箱:</span><input type="text" name="Q_email_SL"  class="inputText" style="width:10%;" value="${param['Q_email_SL']}"/>
						<span class="label">创建时间:</span><input  name="Q_begincreatetime_DL"  class="inputText date" style="width:10%;" value="${param['Q_begincreatetime_DL']}"/>
						<span class="label">至</span><input  name="Q_endcreatetime_DG" class="inputText date" style="width:10%;" value="${param['Q_endcreatetime_DG']}"/>
					</div>
				</form>
			</div>
		</div>
		
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="userManagementList" id="userManagementItem" requestURI="management.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input id="userId" type="checkbox" class="pk" name="id" value="${userManagementItem.userId}">
					</display:column>
					<display:column property="fullname" title="姓名" sortable="true" sortName="fullname" ></display:column>
					<display:column property="account" title="用户名" sortable="true" sortName="account" ></display:column>
					<display:column property="mobile" title="手机" sortable="true" sortName="mobile" ></display:column>
					<display:column property="email" title="邮箱" sortable="true" sortName="email" ></display:column>
					<display:column  title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${userManagementItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
					<display:column  title="操作" media="html" style="width:200px;;text-align:center">
					<c:if test="${userManagementItem.isCharge ne 1}">
					
					   <c:choose>
					   <c:when test="${userManagementItem.isIndividual eq '1' and userManagementItem.state eq '0'}">
					     <a href="javascript:void(0)" onclick="disAgree('${userManagementItem.userId}');" class="link notAgree">驳回</a>
					     <a href="javascript:agree('${userManagementItem.userId}');" class="link deploy">同意</a>
					   </c:when>
					   <c:otherwise>
						   <a href="javascript:void(0)" id='${ userManagementItem_rowNum}' onclick="assign('${userManagementItem.openId}','${userManagementItem.userId}');" class="link auth">角色分配</a>
						   <a href="javascript:assignDepartMent('${userManagementItem.openId}','${userManagementItem.userId}');" class="link org">部门分配</a>
						   <a href="del.ht?id=${userManagementItem.userId}" class="link del">删除</a>
					   </c:otherwise>
					 </c:choose>
					</c:if>
					<!-- 不能修改自己的权限 -->
				   <%--  <c:if test="${userManagementItem.isCharge eq 1 and userManagementItem.account ne user.account}"> --%>
				    <!-- 可以修改自己的权限 -->
				    <c:if test="${userManagementItem.isCharge eq 1 }">
				    		<!-- 如果是管理员，去掉角色分配和删除按钮，这里没有删除按钮就只注掉了角色分配按钮 -->
					         <%-- <a href="javascript:void(0)" id='${ userManagementItem_rowNum}' onclick="assign('${userManagementItem.openId}','${userManagementItem.userId}');" class="link auth">角色分配</a> --%>
							 <a href="javascript:assignDepartMent('${userManagementItem.openId}','${userManagementItem.userId}');" class="link org">部门分配</a>
				    </c:if>
					</display:column> 
					
					
				</display:table>
				<hotent:paging tableId="userManagementItem"/>
			
		</div><!-- end of panel-body -->	
					
	</div> <!-- end of panel -->
	
</body>
</html>


