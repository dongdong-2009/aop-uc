<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"></meta>
<title>注册成功</title>
<script id="allmobilize" charset="utf-8" src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/cloudReset.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/cloudFooterheader.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/cloudStyle.css" />
<script type="text/javascript">
var CTX="${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/user/reg_cloud_step3.js"></script>
<script type="text/javascript" src="${ctx}/js/user/des.js"></script>
<style type="text/css">
.register{
  
padding-bottom: 15px;
display: none;
}
.register ul{
padding-left: 20px;
padding-top: 10px;
}
.register ul li a{
   float: left;
   padding-left:30px;
   line-height: 40px;
   cursor: auto;
   font-family: serif;
   font-size: 30px;
   color: #4c89c2;
   text-align: center;
}
ul li a:HOVER{
   float: left;
   padding-left:30px;
   line-height: 40px;
   cursor: auto;
   font-family: serif;
   font-size: 30px;
   background: #ddd;
   color: blue;
    text-align: center;
}
.register label{
  font-size: 30px;

}
.registerStep3_tit{
	color: red;

}
.registerStep3_jump{
	margin-top: 20px;
	text-align: center;
}
</style>
</head>
<body>
<%@include file="/commons/regPersonTop.jsp" %>
        <div class="registerStep3">
            <div class="registerStep3_con">
                 <input type="hidden" id="systemId" value="${systemId}"/>
                 <input type="hidden" id="returnUrl" value="${returnUrl}"/>
                 <input type="hidden" id="serviceUrl" value="${serviceUrl}"/>
                  <input type="hidden" id="password" value="${password}"/>
                 <input type="hidden" id="account" value="${account}"/>
                <p class="registerStep3_tit">恭喜您 : 已成功注册为航天云网会员</p>
                <c:if test="${not empty systemId}">
                <div class="registerStep3_jump"><span id="timer">3</span>秒钟后系统自动登录跳转<!-- <a href="javascript:Login();" class="registerStep3_land">登陆</a> --></div>
                </c:if>
            </div>
        <div class="register">
        <!-- <label>请选择登录后跳转的子系统:</label>
         <ul >
            <li><a href="http://www.casicloud.com">航天云网</a></li>
         </ul> -->
        </div>
        </div>
     <%@include file="/commons/regFooter.jsp" %>
<script>
	var key1 = "123459876";
	var key2 = "UserCenter";
	var key3 = "qazwsxedc";
	var password = $("#password").val();
	var newPassword = strEnc(password,key1,key2,key3);
	$("#password").val(newPassword);
</script>
</body>
</html>