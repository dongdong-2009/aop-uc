<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script id="allmobilize" charset="utf-8" src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" href="#" />
<meta charset="utf-8" />
<title>安全设置</title>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
 <link href="${ctx}/styles/uc/v2.0/top.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
 <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery.validate.js"></script>

<style type="text/css">
.changeMobile_title {
    width: 100%;
    border-bottom: 1px solid #ddd;
    height: 50px;}
.changeMobile {
	border-left: 5px solid #ff771d;
    font-family: "Microsoft YaHei";
    color: #656565;
    font-size: 24px;
    width: 580px;
    float: left;
    padding: 5px 0 0 10px;}
.changeMobile_intro{
	padding-top:60px;
	padding-left:100px;}
.btn-confirm{
	width:60px;
	height:30px;
	background:#ff771d;
	color:#fff;
	border-radius:6px;
	cursor:pointer;
	font-size:14px;
	padding:10px 0 0 130px;
	margin-left:80px;}	
.btn-return{
	width:60px;
	height:30px;
	background:#ff771d;
	color:#fff;
	border-radius:6px;
	cursor:pointer;
	font-size:14px;
	padding:10px 0 0 130px;
	margin-left:80px;}	
.form-list1 {
    padding-left: 20px;
    padding-bottom: 20px;
    width: 720px;
    height: 50px;
    font-size: 16px;}
.form-list{
	padding-left:20px;
	padding-bottom:20px;
	margin-top: 50px;}
.form-list .form-list-input{
	width:230px;
	padding:10px;
	border-radius:5px;
	border:1px solid #ddd;
	height:22px;
	font-family:"Microsoft YaHei";}
.findPwd_mainCon_tit{
	width: 160px;
	text-align: right;
	display: inline-block;
	margin-right: 15px;
	color:#333;}
.findPwd_mainCon_con{
	width:270px;
	padding-left:10px;
	height:38px;
	border:1px solid #e5e5e5;
	font-size: 16px;
	color:#343434;
	line-height: 38px;}
.error{
	color:red;
}
 body {
    color: #666;
    font-family: Microsoft YaHei;
    font-size: 14px;}

</style>
</head>
	 <body>
	   
        <!-- <div class="top">
			<div class="block">
			<a class="logo" href="#"><span class="decorate"></span></a>
			<div class="tit">安全设置</div>
			</div>
		</div> -->
        <div class="changeMobile_title">
		     <div class="changeMobile">修改密码</div>
		</div>
		<div class="changeMobile_intro">
		     <form id="loginForm" action="${ctx}/user/updatePwd.ht" method="post" >
		     	<div class="form-list1">
				    <span class="findPwd_mainCon_tit">用户名</span>
				    <input type="text" id="username" name="username" class="findPwd_mainCon_con" value="${user.fullname }" 	readonly="readonly"/>
				  </div>
      			  <div class="form-list1">
      			  	<span class="findPwd_mainCon_tit"><span style="color:red">*</span>原密码</span>
      			  	<input type="password" onkeyup="this.value=this.value.replace(/[^\w]/g,'')" onpaste="this.value=this.value.replace(/[^\w]/g,'')" name="password" maxlength="16" minlength="6"  class="findPwd_mainCon_con" required id="password" />
				  </div> 
				 <div class="form-list1">
				   <span class="findPwd_mainCon_tit"><span style="color:red">*</span>新密码</span>
				   <input type="password" onkeyup="this.value=this.value.replace(/[^\w]/g,'')" onpaste="this.value=this.value.replace(/[^\w]/g,'')" maxlength="16" minlength="6"  class="findPwd_mainCon_con" required id="newpassword" name="newpassword" />
				 </div>
				 <div class="form-list1">
				   <span class="findPwd_mainCon_tit"><span style="color:red">*</span>确认新密码</span>
				   <input type="password" onkeyup="this.value=this.value.replace(/[^\w]/g,'')" onpaste="this.value=this.value.replace(/[^\w]/g,'')" maxlength="16" minlength="6"  class="findPwd_mainCon_con" required id="repeatpassword" name="repeatpassword" equalTo="#newpassword" />
				 </div>
				  <div class="form-list" > 
			  		<input type="button" id="confirm" class="btn-confirm" value="确定" style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 170px; width: 80px; height: 40px;"/>
			  		<input type="button" class="btn-return" value="返回" onclick="clickReturn()" style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 50px; width: 80px; height: 40px;"/>
				  </div>
	          </form>
		 </div>
	</body>
	
	<script type="text/javascript">
	
	 $("#loginForm").validate(); 
		function clickReturn(){
			window.location.href="${ctx}/security/index.ht";
		}		
		
		$(function(){
			
			$("#confirm").click(function(){
				var newpassword=$("#newpassword").val();
				var repeatpassword=$("#repeatpassword").val();
				var password = $("#password").val();
				if(password == null ||password == ''){
					layer.alert('请输入原密码');
					return ;
				}else{
					if(password.length<6 || password.length>16){
						layer.alert('密码长度在6-16字符之间');
						return ;
					}
				}
				if(newpassword == null ||newpassword == ''){
					layer.alert('请输入新密码');
					return ;
				}else{
					if(newpassword.length<6 || newpassword.length>16){
						layer.alert('密码长度在6-16字符之间');
						return ;
					}
				}
				if(newpassword == password){
					layer.alert("新密码不能与原密码相同");
					return false;
				}
				if(repeatpassword == null ||repeatpassword == ''){
					layer.alert('请确认新密码');
					return ;
				}else{
					if(repeatpassword.length<6 || repeatpassword.length>16){
						layer.alert('密码长度在6-16字符之间');
						return ;
					}
				}
				if(newpassword!=repeatpassword){
					layer.alert("两次密码不一致");
					return false;
				}
				$.ajax({
				    type: 'POST',
				    url: "${ctx}/user/updatePwd.ht",
				    data: $("#loginForm").serialize() ,
				    dataType: "json",
				    success: function(data){
				    	if(data.message=='修改密码成功'){
					    	layer.alert(data.message,function (){
					    		location.href="${ctx}/security/index.ht";
					    	});
				    	}else{
				    		layer.alert(data.message);
				    	}
				    }
				});
				
			})
			
		
		});
	
		$.extend($.validator.messages, {
			required: '不能为空',
			minlength: $.validator.format("密码格式位数不正确,请重新输入"),
			maxlength: $.validator.format("密码格式位数不正确,请重新输入"),
			equalTo: "两次密码不一致"
		});
</script>
</html>
