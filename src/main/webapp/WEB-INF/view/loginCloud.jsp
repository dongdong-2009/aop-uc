<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/include/get.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录页面</title>
<script src="${ctx}/js/jquery/plugins/jquery.cookie.js"></script>
<script type="text/javascript">
	if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
		  top.location='<%=request.getContextPath()%>/loginCloud.ht';
	}
	
	$(function(){
		$('#orgSn').val($.cookie('orgSn'));
		$('#shortAccount').val($.cookie('shortAccount'));
		//$('#password').val($.cookie('password'));
	})
</script>
</head>
<body>
<div class="clear10"></div>

<div id="all">
	<!--登录框-->
	<div class="bggraybox" style="margin:30px 0">
		<form action="${ctx}/loginCloudPost.ht" method="post">
		<h2>登录</h2>
		<form action="" method="post">
		<div  class="userform userform_left" style="height:225px;">
			<c:if test="${not empty errMsg}">  	
  				<span class="Validform_checktip Validform_wrong">${errMsg}</span>
  			</c:if>
			<table style="white-space:nowrap;">
				<tr>
					<td align="left"><font>企业账号：</font></td>
					<td><input name="orgSn" type="text" class="text" id="orgSn" value="" /></td>	
				</tr>
				<tr>
					<td align="left"><font><fmt:message key="test.app"/>用户名：</font></td>
					<td><input name="shortAccount" type="text" class="text" id="shortAccount" value="" /></td>
				</tr>
				<tr>
					<td align="left"><font>密码：</font></td>
					<td><input name="password" type="password" class="text" id="password" value="" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
					<label style="color:#666; font-size:12px"><input name="autoLogin" type="checkbox" value="1" />&nbsp;记住账户名</label>
					<label style="color:#666; font-size:12px">
					<!-- 
					<input name="_spring_security_remember_me" type="checkbox" value="1" />&nbsp;自动登录(2周之内自动登录)</label></td>
					 -->
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<input name="input" type="image" class="btn" id="button" value="登  录" src="skins/btn_login.jpg"/>
					</td>
				</tr>
			</table>
		</div>
		</form>
		<div class="clear"></div>
	</div>
	<!--登录框结束-->
	</div>
	<!-- 底部版权区  开始 -->
	<div style="bottom:0px;width:100%;">
	</div>
	<!-- 底部版权区  结束 -->
</body>
</html>