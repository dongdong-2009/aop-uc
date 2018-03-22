<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>注册成功</title>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>

 <link href="${ctx}/pages/css/bootstrap.min.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/bootstrap-theme.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/bootstrap.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/bootstrap-theme.min.css" rel="stylesheet"/>
 <link href="${ctx}/pages/css/index.css" rel="stylesheet"/>


<script type="text/javascript">

</script>
<style>
.wrapper {
  width: 980px;
  margin: 0 auto;
}
.rmainbgi {
  border: 1px solid #C9D0DF;
  padding: 0 7px;
}

.userform_left {
  margin: 80px 0 20px 250px;
  width: 540px;
  width: 240px\8;
  line-height: 30px;
}
.clearfix {
  display: block;
}
.button{
    background-color: green;
    color: white;
    list-style: none;
}
.forMycompany{
	width:140px;
	height:36px;
	line-height:36px;
	text-align:center;
	background-color:#09c;
	color:#fff;
	font-weight:bold;
	display:inline-block;
}

.forMycompany a:hover{
	width:140px;
	height:36px;
	line-height:36px;
	text-align:center;
	background-color:#09c;
	color:#fff;
	font-weight:bold;
	display:inline-block;
}
</style>
</head>
<body>
<!-- <div class="clear10"></div> -->
		<div class="Mainboxi wrapper clearfix">
			<div class="rmainconi clearfix">				
				<div class="rmainbgi" style="  height: 400px;">
						
						<div class="maincon">
								<c:if test="${not empty resultMessage}">${resultMessage}</c:if>
							<c:if test="${empty resultMessage}">
								<div  class="userform userform_left" style="height:225px;">
									<form action="" method="post">
										<table>
											<tr>
												<td align="left"><img src="${ctx}/pages/images/success.jpg" width="62" height="49" /></td>
												<td colspan="2">恭喜您成功创建企业:<br/>
								            	企业号为 : ${info.sysOrgInfoId}<br/>
								            	登录名: ${account} <br/>
								            	密码: 未改变<br/>
								            	注册信息已经发送至手机  &nbsp;${mobile},请注意查收。
								            	</td>	
											</tr>
											<br/>
											<tr style="text-align: center;">
												<td align="left" class="buton">
													<a class="forMycompany" href="javascript:certified('${info.sysOrgInfoId}');" >立即认证企业信息</a>
												</td>
												<td class="buton">
													<a class="forMycompany" href="javascript:closeWin();">关闭</a>
												</td>
										</table>
									</form>
								</div>
							</c:if>
						</div>
					</div>
					<div class="bgbti"></div>
			</div>
		</div>

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
<script type="text/javascript">
  var certified=function(id){
	  parent.certified(id);
  }
  var closeWin=function(){
	  
	  parent.loginout();
	  
  }
</script>
</html>
