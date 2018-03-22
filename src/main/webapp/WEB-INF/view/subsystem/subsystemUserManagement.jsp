<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>用户和子系统角色映射管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript">
	function openUserUnder(userid,obj){
		if($(obj).hasClass('disabled')) return false;
		 
		var conf={};				
		var url=__ctx + "/platform/system/userUnder/list.ht?userId="+userid;
		conf.url=url;
		var dialogWidth=550;
		var dialogHeight=450;
		conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
		var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
			+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;				
		var rtn=window.showModalDialog(url,"",winArgs);		
	}
	
	/* $("div.group > f:a[alias=[updateSubsystemManager'].link.update").click(function() {
		if($(this).hasClass('disabled')) return false;
		var action=$(this).attr("action");
		var aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
		var len=aryId.length;
		alert(55);
		if(len==0){
			$.ligerMessageBox.warn("提示信息","还没有选择,请选择一项进行编辑!");
			return false;
		}
		else if(len>1){
			$.ligerMessageBox.warn("提示信息","已经选择了多项,请选择一项进行编辑!");
			return false;
		}
		var name=aryId.attr("name");
		var value=aryId.val();
		var form=new com.hotent.form.Form();
		form.creatForm("form", action);
		form.addFormEl(name, value);
		form.submit();
		
	}); */
	
	</script>
</head>
<body>
<c:set var="SysUser_EXPIRED" value="<%=SysUser.EXPIRED %>" />
<c:set var="SysUser_UN_EXPIRED" value="<%=SysUser.UN_EXPIRED %>"  />

<c:set var="SysUser_LOCKED" value="<%=SysUser.LOCKED %>"/>
<c:set var="SysUser_UN_LOCKED" value="<%=SysUser.UN_LOCKED %>"/>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">用户管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<f:a alias="searchUser" css="link search" id="btnSearch">查询</f:a>
					</div>
					<div class="l-bar-separator"></div>
				 	<div class="group">
						<f:a alias="updateSubsystemManager" css="link update" action="/platform/subSystem/sysUser/editSubManager.ht">编辑</f:a>
					</div>
					<div class="l-bar-separator"></div>
					<%--  <c:when test="${ empty 0 }"> --%>
		           <%--   <div class="group">
						<f:a alias="addSubsystemManager" css="link add manager" href="/platform/subSystem/sysUser/editSubManager.ht">添加管理员</f:a>
					</div>  --%>
				<%-- 	 </c:when> --%>
				 	
					<%--<div class="l-bar-separator"></div> 
					 <div class="group">
						<f:a alias="delUser" css="link del" action="del.ht">删除</f:a>
					</div>
					<div class="l-bar-separator"></div> --%>
				</div>	
			</div>
			<div class="panel-search">
					<form id="searchForm" method="post" action="list.ht">
							<div class="row">
								<span class="label">姓名:</span><input type="text" name="Q_fullname_SL"  class="inputText" style="width:9%" value="${param['Q_fullname_SL']}"/>					
								<span class="label">账号:</span><input type="text" name="Q_account_SL"  class="inputText" style="width:9%" value="${param['Q_account_SL']}"/>					
								<span class="label">创建时间从:</span><input type="text" id="Q_begincreatetime_DL" name="Q_begincreatetime_DL"  class="inputText Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endcreatetime_DG\');}'})" style="width:9%" value="${param['Q_begincreatetime_DL']}"/>
								<span class="label">至</span><input type="text" id="Q_endcreatetime_DG" name="Q_endcreatetime_DG"  class="inputText Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_begincreatetime_DL\');}'})" style="width:9%" value="${param['Q_endcreatetime_DG']}"/>
								<%-- <span class="label">是否过期:</span>	
								<select name="Q_isExpired_S" class="select" style="width:6%;" value="${param['Q_isExpired_S']}">
									<option value="">--选择--</option>
								<option value="${SysUser_EXPIRED}"  <c:if test="${param['Q_isExpired_S'] == SysUser_EXPIRED }">selected</c:if>>是</option>
								<option value="${SysUser_UN_EXPIRED}" <c:if test="${param['Q_isExpired_S'] == SysUser_UN_EXPIRED }">selected</c:if>>否</option>
								</select>
								<span class="label">是否锁定:</span>
								<select name="Q_isLock_S" class="select" style="width:6%;" value="${param['Q_isLock_S']}">
									<option value="">--选择--</option>
								<option value="${SysUser_LOCKED}"  <c:if test="${param['Q_isLock_S'] == SysUser_LOCKED }">selected</c:if>>是</option>
								<option value="${SysUser_UN_LOCKED}" <c:if test="${param['Q_isLock_S'] == SysUser_UN_LOCKED }">selected</c:if>>否</option>
								</select>
								<span class="label">状态:</span>
								<select name="Q_status_S" class="select" style="width:6%;" value="${param['Q_status_S']}">
									<option value="">--选择--</option>
									<option value="<%=SysUser.STATUS_OK %>">激活</option>
									<option value="<%=SysUser.STATUS_NO %>">禁用</option>
									<option value="<%=SysUser.STATUS_Del %>">删除</option>
								</select>
								<span class="label">来源:</span>
								<select name="Q_fromType_S" class="select" style="width:6%;" value="${param['Q_fromType_S']}">
									<option value="">--选择--</option>
									<option value="<%=SysUser.FROMTYPE_DB %>">系统添加</option>
									<option value="<%=SysUser.FROMTYPE_AD %>">AD同步</option>
								</select> --%>
							</div>
					</form>
			</div>
		</div>
		<div class="panel-body">
			
			 
		
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="sysUserList" id="sysUserItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"  export="true"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;text-align:center;">
						  	<input type="checkbox" class="pk" name="userId" value="${sysUserItem.userId}">
					</display:column>
					<display:column property="fullname" title="姓名" sortable="true" sortName="fullname" style="text-align:left"></display:column>
					<display:column property="account" title="帐号" sortable="true" sortName="account" style="text-align:left"></display:column>
					<display:column  title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${sysUserItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
					<%-- <display:column title="是否过期" sortable="true" sortName="isExpired">
						<c:choose>
							<c:when test="${sysUserItem.isExpired==1}">
								<span class="red">已过期</span>
						   	</c:when>
					       	<c:otherwise>
						    	<span class="green">未过期</span>
						   	</c:otherwise>
						</c:choose>
					</display:column>
	                <display:column title="是否可用" sortable="true" sortName="isLock">
						<c:choose>
							<c:when test="${sysUserItem.isLock==1}">
								<span class="red">已锁定</span>
						   	</c:when>
					       	<c:otherwise>
					       		<span class="green">未锁定</span>
						   	</c:otherwise>
						</c:choose>
					</display:column>
                	<display:column title="状态" sortable="true" sortName="status">
						<c:choose>
							<c:when test="${sysUserItem.status==1}">
								<span class="green">激活</span>
								
						   	</c:when>
						   	<c:when test="${sysUserItem.status==0}">
						   		<span class="red">禁用</span>
								
						   	</c:when>
					       	<c:otherwise>
					       		<span class="red">删除</span>
						        
						   	</c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="数据来源" sortable="true" sortName="fromType">
						<c:choose>
							<c:when test="${sysUserItem.fromType==1}">
								<span class="brown">AD</span>
								
						   	</c:when>
						   	<c:when test="${sysUserItem.fromType==0}">
						   		<span class="green">系统添加</span>
						   	</c:when>
					       	<c:otherwise>
					       		<span class="red">未知</span>
						   	</c:otherwise>
						</c:choose>
					</display:column> --%>
				<%-- 	<display:column title="是否管理员" sortable="true" sortName="isShow" style="text-align:center"> --%>
		<%-- 				<c:choose>
							 <c:when test="${not empty sysUserItem.fromSysId }">
							 <span class="green">
							      <f:a alias="setStatus" css="link manager" href="/tenant/auditSubsystem.ht?userId=${sysUserItem.userId}&fromSysId=${sysUserItem.fromSysId}"> 企业管理</f:a>
							 </span>
							 </c:when>
							 <c:otherwise>
							     <f:a alias="setStatus" css="link commonUser " >职员</f:a>
							 </c:otherwise> 
						</c:choose> --%>
					<%-- 	 <f:a alias="setStatus" css="link commonUser " >职员</f:a>
					</display:column> --%>		
					<display:column title="管理" media="html" style="text-align:center;">
					  <%--   <f:a alias="userUnder" css="link primary" href="#" onclick="openUserUnder('${sysUserItem.userId}',this)">下属管理</f:a> --%>
						<f:a alias="delUser" css="link del" href="/platform/subSystem/sysUser/del.ht?userId=${sysUserItem.userId}&fromSysId=${not empty sysUserItem.fromSysId?sysUserItem.fromSysId:0}">删除</f:a>
						<f:a alias="updateUserInfo" css="link auth" href="/platform/subSystem/sysUser/edit.ht?userId=${sysUserItem.userId}&fromSysId=${ not empty sysUserItem.fromSysId?sysUserItem.fromSysId:0}">用户角色分配</f:a>
						<f:a alias="userInfo" css="link detail" href="/platform/subSystem/sysUser/get.ht?userId=${sysUserItem.userId}&fromSysId=${not  empty sysUserItem.fromSysId?sysUserItem.fromSysId:0}">明细</f:a>
                  <%-- 	<f:a alias="setParams" css="link parameter" href="${ctx}/platform/system/sysUserParam/editByUserId.ht?userId=${sysUserItem.userId}" >参数属性</f:a>
						<f:a alias="resetPwd" css="link resetPwd" href="resetPwdView.ht?userId=${sysUserItem.userId}">重置密码</f:a>
						<f:a alias="setStatus" css="link setting" href="editStatusView.ht?userId=${sysUserItem.userId}">设置状态</f:a> 
						<c:if test="${cookie.origSwitch==null}">
							<f:a alias="switch" css="link switchuser" target="_top" href="${ctx}/j_spring_security_switch_user?j_username=${sysUserItem.account}" >切换用户</f:a>
						</c:if> --%>
					</display:column>
				</display:table>
				<hotent:paging tableId="sysUserItem"/>
			
		</div>
	</div>
</body>
</html>


