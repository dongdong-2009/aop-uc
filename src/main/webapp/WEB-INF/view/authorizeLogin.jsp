<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>个人账号</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/js/css/CLogin.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/js/css/reset.css"/>
		<script src="${ctx}/js/jquery/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="${ctx}/js/authorize/authorize.js"></script>
		<script type="text/javascript">
			var CTX="${pageContext.request.contextPath}";
		</script>
		<script type="text/javascript">
			$(function(){
				$('.CLogin-L .FormTab span').click(function(){
					$(this).addClass('cur').siblings().removeClass('cur');
					$('.CLogin-L .FormCon').eq($(this).index(".CLogin-L .FormTab span")).show().siblings().hide();
				})
			})
		</script>
	</head>
	<body>
		<div class="CLogin-capBox">
			<div class="CLogin-cap"></div>	
		</div>
		<div class="clearfix CLogin">
			<div class="CLogin-L">
				<h1 class="CLogin-LCap">账号密码登录</h1>
				<h3 class="CLogin-LTit">推荐使用<a href="javascript:void(0)">快速安全登录</a>，防止盗号。</h3>
				<div class="CLogin-form">
					<span class="Formtip" style="display: none;" id="errorMsg">授权登录失败请输入正确的用户名和密码！</span>
					<span>${message}</span>
					<div class="FormTab">
						<span class="cur">个人账号登录</span>
						<span class="FormTab2">企业账户登录</span>
					</div>
					<form action="/user/authorizeLogin.ht" id="authorizeForm">
							<input type="hidden" name="authorizeUrl" value="${authorizeUrl}">
					<div>
						<div class="FormCon">
							<i class="FormCon_Close"></i>
							<input type="text" name="personalAccount" placeholder="手机/邮箱/用户名"/>
							<input type="password" name="personalPassword" placeholder="密码"/>
						</div>
						<div class="FormCon" style="display: none;">
							<!-- 此为非空情况下显示 -->
							<!--<i class="FormCon_Close"></i>
							<i class="FormCon_Close Close2"></i>-->
							<input type="text" name="orgId" placeholder="企业号"/>
							<input type="text" name="orgName" placeholder="用户名"/>
							<input type="password" name="orgPassword" placeholder="密码"/>
						</div>
					</div>
					<a href="javascript:void(0)" class="formBtn" id="authorize" onclick="authorizeLogin()">授权并登录</a>
					</form>
					<div class="formForget">
						<a href="javascript:void(0)" class="">忘记密码?</a>
						<span>|</span>
						<a href="javascript:void(0)">注册新账号</a>
					</div>
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
