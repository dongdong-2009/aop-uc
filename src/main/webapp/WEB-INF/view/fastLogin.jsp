<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>快速登录</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/js/css/CLogin.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/js/css/reset.css"/>
			<script src="${ctx}/js/jquery/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<script type="text/javascript">
		function fastLogin(){
			$("#fastLogin").submit();	
		}
	</script>
	<body>
		<div class="CLogin-capBox">
			<div class="CLogin-cap"></div>	
		</div>
		<div class="clearfix CLogin">
			<div class="CLogin-L">
				<h1 class="CLogin-LCap">快速安全登录</h1>
				<h3 class="CLogin-LTit">请点击云网图标或用户名授权登录。</h3>
				<form action="/user/fastLogin.ht" id="fastLogin">
				<input type="hidden" name="authorizeUrl" value="${authorizeUrl}"></input>
				<input type="hidden" name="personalAccount" value="${account}"></input>
				<input type="hidden" name="personalPassword" value="${password}"></input>
				<a href="javascript:void(0)"  class="CLogin-info">
					<i href="javascript:void(0)" onclick="fastLogin()" class="userImg">
						<img src="${ctx}/js/images/IMG_avatar.png"/>
					</i>
					<p class="userName">${account}</p>
				</a>
			    </form>	
				<div class="formForget rapid">
					<a href="/user/authorize.ht" class="">账号密码登录</a>
					<span>|</span>
					<a href="javascript:void(0)">注册新账号</a>
				</div>
			</div>
			<div class="CLogin-R">
				<p class="CLogin-RHref">INDICS云平台<a href="http://${authorizeUrl}">${authorizeUrl}</a></p>
				<p class="CLogin-Rtit">将获得以下授权：</p>
				<div class="R_list">
					<a href="javascript:void(0)">用户名</a>
					<a href="javascript:void(0)">企业名称</a>
					<a href="javascript:void(0)">邮箱</a>
					<a href="javascript:void(0)">手机号码</a>
					<a href="javascript:void(0)">企业号</a>
					<a href="javascript:void(0)">联系人</a>
				</div>
				<p class="CLogin-RHref RHref2">授权后表明您已同意<a href="/user/openProtocol.ht">《云网登录服务协议》</a></p>
			</div>
		</div>
	</body>
</html>
