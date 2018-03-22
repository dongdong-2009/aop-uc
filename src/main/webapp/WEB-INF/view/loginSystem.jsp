<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/include/get.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录页面</title>
<script type="text/javascript">
		if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
			  top.location='<%=request.getContextPath()%>/loginCloud.ht';
		}
</script>
</head>
<body>
<div class="clear10"></div>
<div id="all">
	<!--登录框-->
	<div class="bggraybox">
		<form action="${ctx}/loginSystemPost.ht" method="post">
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
					<td align="left"><font>用户名：</font></td>
					<td><input name="shortAccount" type="text" class="text" id="shortAccount" value="" /></td>
				</tr>
				<tr>
					<td align="left"><font>密码：</font></td>
					<td><input name="password" type="password" class="text" id="password" value="" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
					<label style="color:#666; font-size:12px"><input name="input" type="checkbox" value="" />&nbsp;记住账户名</label>
					<label style="color:#666; font-size:12px">
					<input name="input" type="checkbox" value="" />&nbsp;自动登录</label></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input name="button" type="submit" id="button" value="登  录" /></td>
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