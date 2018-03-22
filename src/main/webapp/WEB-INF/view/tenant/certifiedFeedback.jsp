<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>企业认证反馈</title>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>

 <link href="${ctx}/pages/css/bootstrap.min.css" rel="stylesheet">
 <link href="${ctx}/pages/css/bootstrap-theme.css" rel="stylesheet">
 <link href="${ctx}/pages/css/bootstrap.css" rel="stylesheet">
 <link href="${ctx}/pages/css/bootstrap-theme.min.css" rel="stylesheet">
 <link href="${ctx}/pages/css/index.css" rel="stylesheet">



</script>
<script>
var modify=function(){
	
	window.top.location.reload();
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index); 
	
}
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

.clearfix {
  display: block;
}
.button{
    background-color: #00A987;
    color: white;
    list-style: none;
}
.linkA{
	font-size:16px;
	font-weight:bold;
	color:#38AB6C;
}
.link09{
	width:175px;
	height:36px;
	line-height:36px;
	text-align:center;
	background-color:rgb(0, 170, 136);
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
							<c:if test="${not empty code && code=='0'}">
								<div  class="userform userform_left" style="height:225px;">
									<form action="" method="post">
										<table>
											<tr>
												<td align="left" style="width: 62px;"><img src="${ctx}/images/success.jpg" width="62" height="49" /></td>
												<td style="font-size:18px;font-weight:bold;">您已提交成功，企业认证信息审核中...</td>
											</tr>
											<tr><td></td><td>现在您可以</td></tr>
											<%-- <tr style="text-align: center;">
												<td align="left" class="buton">
													<a id="forMycompany" href="${ctx}/cloud/audit/tenantInfo/openBusiness.ht?entId=${info.sysOrgInfoId}" class="link09" style="width:140px;height:36px;line-height:36px;text-align:center;background-color:#09c;color:#fff;display:inline-block;">开通商户</a>
												</td>
												
											</tr> --%>
										</table>
									</form>
								</div>
							</c:if>
							<c:if test="${empty code || code=='1'}">
								<div  class="userform userform_left" style="height:225px;">
									<form action="" method="post">
										<table>
											<tr>
												<td align="left" style="width: 62px; !important"><img src="${ctx}/images/tanhao.jpg" width="62" height="49" /></td>
												<td style="font-size:18px;font-weight:bold;">很抱歉，
													<c:if test="${status == '1'}">
														输入数据错误，保存失败
													</c:if>
													<c:if test="${empty code}">
														认证参数错误
													</c:if>
													<c:if test="${not empty code && code=='1'}">
														${message}
													</c:if>
												
												请检查更新认证信息！</td>
												
											</tr>
											<tr><td><br /></td></tr>
											<tr style="text-align: center;">
												<td align="left" class="buton">
													<a id="forMycompany" href="javascript:modify();" class="link09" >修改认证信息</a>
												</td>
												<%-- <td style="width: 100%;">
													<a href="${ctx}/cloud/audit/tenantInfo/manualReview.ht?entId=${info.sysOrgInfoId}" class="link09">信息无误，申请人工审核</a>
												</td> --%>
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
</body>
<style>
.userform_left {
    margin: 0 auto;
    width: 580px;
    width: 240px\8;
    padding-top: 100px;
}

.userform td {
  padding: 8px 10px;
}

</style>
</html>
