<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>注册结果</title>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>

 <link href="${ctx}/pages/css/bootstrap.min.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/bootstrap-theme.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/bootstrap.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/bootstrap-theme.min.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/index.css" rel="stylesheet"/>


<script type="text/javascript">

</script>
<style>
.wrapper {
  width: 1190px;
  margin: 0 auto;
}
.rmainbgi {
  border: 1px solid #C9D0DF;
  padding: 0 7px;
}

.userform_left {
  margin: 40px 0 20px 250px;
  width: 540px;
  width: 240px\8;
}
.clearfix {
  display: block;
}

</style>
</head>
<body>
<%@include file="/pages/jsp/top.jsp" %>

<!-- <div class="clear10"></div> -->
		<div class="Mainboxi wrapper clearfix">
			<div class="rmainconi clearfix">				
				<div class="rmainbgi" style="  height: 600px;">
						
						<div class="maincon">
								<c:if test="${not empty resultMessage}">
								<div  class="userform userform_left" style="height:225px;">
									<form action="" method="post">
										<table>
											<tr>
<%-- 												<td align="left"><img src="${ctx}/images/fail.jpg" width="62" height="49" /></td>
 --%>												<td align="center">抱歉!注册失败。<br/>
 														失败原因:${resultMessage}<br/>
								            	</td>	
											</tr>
												
										</table>
									</form>
								</div>
								</c:if>
							<c:if test="${empty resultMessage}">
								<div  class="userform userform_left" style="height:225px;">
									<form action="" method="post">
										<table>
											<tr>
												<td align="left"><img src="${ctx}/pages/images/success.jpg" width="62" height="49" /></td>
												<td>恭喜您注册成功！您可以使用手机号或邮箱登录平台,请牢记以下信息:<br/>
								            	用户名 : ${userName}<br/>
								            	手机号: ${mobile}<br/>
								            	邮箱: ${email}<br/>
								            	密码 : ${dePassword}<br/>
								            	注册信息已经发送至您的邮箱，为了保证您的账号安全，请立即
								            	<c:if test="${not empty mailLoginUrl}">
								            		<a href="${mailLoginUrl}" target="_blank">登录邮箱</a>
								            	</c:if>
								            	<c:if test="${empty mailLoginUrl}">
								            		登录邮箱
								            	</c:if>
								            	保存您的信息！<br/>
								            	
								            	</td>	
											</tr>
											<tr>
												<td align="left"></td>
												
												<td><a id="forMycompany" href="${url}?openId=${openId}" class="link09" style="color:#8FD1FF">点击返回系统</a>&nbsp;&nbsp;&nbsp;&nbsp; 
												</td>
												</tr>
												
										</table>
									</form>
								</div>
							</c:if>
						</div>
					</div>
					<div class="bgbti"></div>
			</div>
		</div>
<%@include file="/pages/jsp/foot.jsp" %>


</body>
<style>
.userform_left {
  margin: 40px 0 20px 250px;
  width: 540px;
  width: 240px\8;
}

.userform td {
  padding: 8px 10px;
}

</style>
</html>
