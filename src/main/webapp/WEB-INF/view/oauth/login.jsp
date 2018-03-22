<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/include/get.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录页面</title>

</head>
<body>
<div class="clear10"></div>

<div id="all">
	<!--登录框-->
	<div class="bggraybox" style="margin:30px 0">
		<form action="${ctx}/oauth/authorize.ht" method="post">
		<h2>登录</h2>
		<input type="hidden" name="redirect_uri" value="${redirect_uri}"/>
		<input type="hidden" name="client_id" value="${client_id}"/>
		<input type="hidden" name="response_type" value="${response_type}"/>
		<input type="hidden" name="state" value="${state}"/>
		<input type="hidden" name="display" value="${display}"/>
		<div  class="userform userform_left" style="height:225px;">
			
			<table style="white-space:nowrap;">
				
				<tr>
					<td align="left"><font>用户名：</font></td>
					<td><input name="account" type="text" class="text" id="account" value="" /></td>
				</tr>
				<tr>
					<td align="left"><font>密码：</font></td>
					<td><input name="password" type="password" class="text" id="password" value="" /></td>
				</tr>
				
				<tr>
					<td><input type="checkbox" name="scope" id="scope" value="getUser.ht" checked="checked" disabled="disabled">获得您的昵称、头像、性别</td>
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