<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript"
	src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
</head>
<BODY bgcolor="#FFFFFF" onload = "showTimer()" background="${pageContext.request.contextPath }/images/back1.jpg"> 
<Form name="form1" method="POST">
<table border="0" width="100%" id="table1" height="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center">
		<table border="0" width="60%" id="table2" cellspacing="0" cellpadding="0">
			<tr>
				<td width="49" height="45"><img border="0" src="${pageContext.request.contextPath }/pages/images/noPower.jpg" width="300" height="300"></td>
				<td style="word-break:break-all" align="center">
					<font face="黑体" size="4" color="red">
						<b>sesion超时！系统将在5秒中内跳转到登录页面</b>
                	</font>
                </td>
			</tr>
			<tr>
				<td width="39" height="34"></td>
				<td style="word-break:break-all" align="center">
					<font face="黑体" size="3" color="red">
						<div id ="timer" style="color:#999;font-size:20pt;text-align:center"></div>
                	</font>
                </td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</Form>
</BODY>
<script>
var i=6;
var t;
function showTimer(){
 if(i==0){//如果秒数为0的话,清除t,防止一直调用函数,对于反应慢的机器可能实现不了跳转到的效果，所以要清除掉 setInterval()
  parent.toLogout();
  window.clearInterval(t);

  }else{
  i = i - 1 ;
  // 秒数减少并插入 timer 层中
  document.getElementById("timer").innerHTML= i+"秒";
  }
}
// 每隔一秒钟调用一次函数 showTimer()
t = window.setInterval(showTimer,1000);
</script>
</html>