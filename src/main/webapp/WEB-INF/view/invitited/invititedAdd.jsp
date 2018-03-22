<%--
	time:2011-11-21 12:22:07
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>推荐会员</title>
<link href="${ctx}/styles/default/css/web.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>   
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>
<script type="text/javascript">
var CTX="${pageContext.request.contextPath}";

</script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/invitited/invitiedAdd.js"></script>
	<style type="text/css">
		.register1_imgCode {
	    display: inline-block;
	    height: 30px;
	    line-height: 30px;
	    margin-left: 13px;
	    vertical-align: middle;
	    width: 78px;
	}
	
	#code {
    border: 0 none;
    color: blue;
    font-family: Arial;
    font-style: italic;
    font-weight: bold;
    letter-spacing: 2px;
	}
	</style>
<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
					推荐会员
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">发送邀请</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="invititedForm" method="post" action="${ctx}/invitited/welcome.ht">

				<table class="table-detail" cellpadding="0" cellspacing="0"
					border="0">
					<tr>
						<th width="20%">我的邀请码: </th>
						<td><span class="inputText" style="border:none;">${tenantId}</span>
						<input type="hidden" id="tenantId" name="tenantId"
							value="${tenantId}" />
							<font color="red"></font>
							</td>
						
					</tr>
					<tr>
						<th width="20%">对方手机号:</th>
						<td><input type="text" id="mobile" name="mobile"
							value="" class="inputText" />
						<font color="red"></font>
							</td>
					</tr>
					<tr>
						<th width="20%">对方邮箱:</th>
						<td><input type="text" id="email" name="email"
							value="" class="inputText" />
							<font color="red"></font>
							</td>
						
					</tr>
					<tr>
						<th width="20%">图片验证码:</th>
						<td><input type="text"   class="inputText" id="verifycode_img"  name="verifycode_img" onfocus="checkFocusVerify(this)"  onblur="checkBlurVerify();"/>
						<span class="register1_imgCode">
                           <input type = "button" id="code" onclick="createCode()" style="width: 70px;height: 30px"/>
                        </span>
                        <font color="red"></font>
                        </td>
					</tr>
					
					<tr>
						<th width="20%">邀请内容:</th>
						<td><textarea  id="content" name="content"
						 rows="4" cols="35" style="width: 390px;height: 61px" readonly="readonly">【航天云网】您的邀请验证码是：${tenantId}，如非本人操作，请忽略！</textarea>
						 </td>
					</tr>
				</table>
			</form>
		
		</div>
	</div>
</body>
</html>

