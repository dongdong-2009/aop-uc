<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>注册账号2</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"></META>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"></META>
<META HTTP-EQUIV="expires" CONTENT="0"></META>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/cloudReset.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/cloudFooterheader.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/cloudStyle.css" />
<style type="text/css">
input.item-focus {
    border: 1px solid #3aa2e4
  }
  
  input.error {
    border-color: #e4393c;
    color: #e4393c;
}

</style>
<script type="text/javascript">
var CTX="${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/user/reg_cloud_step2.js"></script>
</head>
<body>
 <%@ include file="/commons/regPersonTop.jsp" %>
        <div class="register2">
            <div class="register2_outer">         	
                <div class="register2_top">
                <a href="javascript:void(0)" onclick="change2tean()" class="register1_top_login">切换成企业注册</a>&nbsp&nbsp&nbsp
                </div>
                <div class="register2_step">
                    <span>验证手机号</span>
                    <span>设置账户信息</span>
                    <span>注册成功</span>
                </div>
                <form action="" id="loginForm" method="post">
                <input type="hidden" id="systemId" value="${systemId}" name="systemId"/>
                <input type="hidden" id="returnUrl" value="${returnUrl}"/>
                <input type="hidden" id="serviceUrl" value="${serviceUrl}"/>
                <input type="hidden" id="user" value="${user}"/>
                 <input type="hidden"  name="mobile" value="${mobile}"/>   
                    <div class="register2_list">
                        <div class="register2_list_left">
                            <span class="register2_red">*</span>
                            会员用户名：
                        </div>
                        <input type="text" class="register2_list_right" placeholder="请输入会员账号" name="account" id="account"/>
                        <font color="red"></font>
                    </div>
                    <div class="register2_list">
                        <div class="register2_list_left">
                            <span class="register2_red">*</span>
                            密码：
                        </div>
                        <input type="password" onpaste="return false" class="register2_list_right" placeholder="请输入密码" name="password" id="password" />
                        <font color="red"></font>
                    </div>
                    <div class="register2_list">
                        <div class="register2_list_left">
                            <span class="register2_red">*</span>
                            确认密码：
                        </div>
                        <input type="password" onpaste="return false" class="register2_list_right" placeholder="请再次输入你的密码" name="repeatpassword" id="repeatpassword"/>
                        <font color="red"></font>
                    </div>
                        <div class="register2_list">
                        <div class="register2_list_left">
                            <span class="register2_red">*</span>
                                    姓名：
                        </div>
                        <input type="text" class="register2_list_right" placeholder="请输入用户姓名" name="fullname" id="fullname"/>
                        <font color="red"></font>
                    </div>       
           
                    <div class="register2_list" style="margin-bottom:48px;">
                        <div class="register2_list_left" style="vertical-align:sub;">
                            <input type="checkbox" style="margin-right:4px;width:14px;height:55px;" name="check_box" id="check_box"/>
                        </div>
                        <div class="register2_read">
                            创建网站账号的同时，我同意：遵守<a href="${ctx}/user/xieyi.ht" class="register2_agreenment" target="_blank">《航天云网用户注册协议》</a>和
                            <a href="${ctx}/user/loginXieYi.ht" class="register2_agreenment" target="_blank">《航天云网登录服务协议》</a>
                        </div>
                    </div>

                    <div class="register2_list">
                        <div class="register2_list_left">
                         <input type="hidden" name="identification" value="${identification}"/>
                        <input  type="hidden"  id="AccountRandom" name="AccountRandom"  value="${AccountRandom}"/>
                        </div>
                        <input type="button" class="register2_list_btn" value="立即注册" onclick="persave();" id="btn"/>
                    </div>
                </form>
                
            </div>
        </div>
      <%@ include file="/commons/footer.jsp" %>
</body>
</html>