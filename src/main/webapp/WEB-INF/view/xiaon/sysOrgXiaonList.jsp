<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.hotent.core.util.ContextUtil" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>客服管理</title>
<style type="text/css">
table.gridtable {
	text-align:center;
	font-family: verdana,arial,sans-serif;
	font-size:14px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable th {
	width:80px;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	width:80px;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}

.tipBox{
	border: 1px solid #ddd;
    border-radius: 10px;
    height: 115px;
    margin-bottom: 10px;
    font-size: 14px;
    color: #666;
}
span{
	 color: #666;
}
</style>
<body>
	<div class="tipBox">	
		<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>
		<div style="margin-left: 25px">	
		<span >功能简介：商户在线客服功能,是航天云网平台为入驻企业提供的在线实时沟通及留言功能，开通在线客服功能后,客户可以通过商品、能力、需求单、采购单的详情页入口与企业进行在线沟通。企业可以通过IM客户端，实时接收和回复用户的咨询信息。</span>
	 	</div>
	 	<div style="margin-left: 25px">
	 	<span >开通条件：企业需要先通过航天云网实名认证后，可以申请开通在线客服功能。</span>
	 	</div>
	 	<div style="margin-left: 25px">
	 	<span>计费规则：基础版商户在线客服功能，对商户免费开放。每个企业可以创建1个主账户和4个子账户;如需开通更多子账户，请联系航天云网客服400-857-6688。</span>
	 	</div>
	</div>	
	<div class="panel-top" style="margin-top: 15px">
		<div class="tbar-title">
			<span class="tbar-label">在线客服工具账号信息</span>
		</div>		 
		<div style="margin-top: 8px">
			<table class="gridtable">
				<tr>
					<th>登录账号</th>
					<th>用户名</th>
					<th>密码</th>
					<th>可创建客服数</th>
					<th style="width: 120px">开通状态</th>
					<th>服务状态</th>
					<th>开通日期</th>
					<th style="width: 150px">操作</th>
				</tr>
				<tr>
					<td>${sysOrgXiaon.account }</td>
					<td>${sysOrgXiaon.siteid }</td>
					<td>${sysOrgXiaon.pass }</td>
					<td style="width: 120px">${sysOrgXiaon.kfnum }</td>
					<td>${sysOrgXiaon.onlineType }</td>
					<td>${sysOrgXiaon.onlineService }</td>
					<td style="width: 150px">${sysOrgXiaon.ktTime }</td>
					<td style="width: 150px">
					<c:if test="${sysOrgXiaon.service==0 }">
					<a href="${ctx}/xiaon/saveServes.ht?siteid=${sysOrgXiaon.siteid}&service=1">离线</a>
					</c:if>
					<c:if test="${sysOrgXiaon.service==1 }">
					<a href="${ctx}/xiaon/saveServes.ht?siteid=${sysOrgXiaon.siteid}&service=0">上线</a>
					</c:if>
					&nbsp;&nbsp;<a href="http://download.ntalker.com/install/t2ddown/Xiaoneng_6.24.1603281122.exe">下载</a></td>
				</tr>
			</table>		
		</div>
</body>
<script type="text/javascript">
function help(){
	window.open("http://b2b.casicloud.com/pages/cloud6.0/yzz/info/jsp/operateManual.jsp?name=商户客服");
}
</script>
</html>

