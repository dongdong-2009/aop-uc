<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"></META>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"></META> 
<META HTTP-EQUIV="expires" CONTENT="0"></META>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>注册账号</title>
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
<script type="text/javascript" src="${ctx}/js/user/reg_cloud_step1.js"></script>
<style type="text/css">	
a {
    color: #666;
    text-decoration: none;
}
body {
    color: #666;
    font-family: Microsoft YaHei;
    font-size: 14px;
}
#code
	{
				font-family:Arial;
				font-style:italic;
				font-weight:bold;
				border:0;
				letter-spacing:2px;
				color:blue;
		}	
		
 input.item-focus {
    border: 1px solid #3aa2e4
  }
  
  input.error {
    border-color: #e4393c;
    color: #e4393c;
}
</style>

	</head>
	 <body>
	   <jsp:include page="/commons/regPersonTop.jsp" />
        <div class="register1">
            <div class="register1_outer"> 	
                <div class="register1_top">
                <a href="javascript:void(0)" onclick="change2tean()" class="register1_top_login">切换成企业注册</a>&nbsp&nbsp&nbsp
                </div>
                <div class="register1_step">
                    <span>验证手机号</span>
                    <span>设置账户信息</span>
                    <span>注册成功</span>
                </div>
                <form action="" id="loginForm" method="post">
                    <input type="hidden" id="systemId" value="${systemId}"/>
                    <input type="hidden" id="returnUrl" value="${returnUrl}"/>
                    <input type="hidden" id="serviceUrl" value="${serviceUrl}"/>
                    <input type="hidden" id="user" value="${user}"/>
                    <div class="register1_list">
                        <div class="register1_list_left">
                            <span class="register1_red">*</span>
                            手机：
                        </div>
                        <input type="text" class="register1_list_right " placeholder="请输入手机号" id="mobile" name="mobile"  />
                        <font color="red"></font>
                        
                    </div>
                    <div class="register1_list">
                        <div class="register1_list_left">
                            <span class="register1_red">*</span>
                            图形验证码：
                        </div>
                        <input type="text" class="register1_list_right" placeholder="请输入图形验证码" maxlength="4" id="verifycode_img"  name="verifycode_img" onfocus="checkFocusVerify(this)"  onblur="checkBlurVerify();"/>
                        <span class="register1_imgCode">
                           <input type = "button" id="code" onclick="createCode()" style="width: 70px;height: 38px"/>
                        </span>
                         <font color="red"></font>
                    </div>
                    <div class="register1_list">
                        <div class="register1_list_left">
                            <span class="register1_red">*</span>
                            短信验证码：
                        </div>
                        <input type="text" class="register1_list_right" placeholder="请输入手机验证码"  id="duanxincode" name="verifycode" maxlength="4"  onblur="checkVerifycode(this);" onfocus="checkFocusVerifyCode(this);"/>
                        <input type="button" class="register1_sendCode" value="发送验证码" onclick="verify();" id="verifyBtn"/>
                        <font color="red"></font>
                    </div>
                    <div style="padding: 5px 0 5px 305px;display:none;margin-top: -17px;" id="mobileCount">
                    </div>
                     <input type="hidden" name="televerifycode" value="${televerifycode}"/>
                        <input  type="hidden"  id="mobileRandom" value="${mobileRandom}"/>
                        <input  type="hidden"  id="mobileCodeValue" value="${mobileCodeValue}"/>
                    <div class="register1_list" style="margin-top: 30px">
                        <div class="register1_list_left">  
                        </div>
                        <span onclick="submits()"><input type="button" class="register1_list_btn" value="下一步" id="btn" /></span>
                    </div>
                </form>
                
            </div>
        </div>
      <jsp:include page="/commons/footer.jsp" />
        <script>
	/* var refreshCount=0; */
	$("#mobile").blur(function (){
		var value1=this.value;
		if($.trim(value1).length==0){
			return false;
		}
        var regex = /^1[3|4|5|7|8]\d{9}$/;
	    var flag = regex.test($.trim(value1));
		if(!flag ){
		return false;
		} 

		$.ajax({
			url: CTX + '/user/checkPhoneRepeatString.ht',
			type: "post",
			dataType: "json",
			//async : false ,
			data: { 
				"mobile":$("#mobile").val(),
			    "mobileRandom":$("#mobileRandom").val()
			},
			success : function(istf) {
				if(istf!="true"&&istf!="false"){
					 layer.alert(istf,function(){
						 location.reload();
					 });
				}
			  }
	       });
	});
	</script> 
	</body>
	
</html>
