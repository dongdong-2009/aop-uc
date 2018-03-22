<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script id="allmobilize" charset="utf-8" src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script><meta http-equiv="Cache-Control" content="no-siteapp" /><link rel="alternate" media="handheld" href="#" />
<meta charset="utf-8" />
<title>航天云网注册主页</title>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/pages/reg/css/register.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/reg/css/reset.css"/> --%>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<%-- <link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" /> --%>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/pages/js/jquery/jquery.validate.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/placeholderfriend.js"></script>

<script type="text/javascript">
	function verify(){
		 //var validCode=true;
		 
		  var mobileV = /^1[3|4|5|7|8]\d{9}$/;
		var mobile = $("#mobile").attr("value");
		var code=$("#code").val();
		////alert(code)
		if(mobile == null || mobile == ''){
			//alert("请先输入手机号");
			layer.alert('请先输入手机号');
			return;
		}
		///alert(mobileV.test(mobile))
		if(!mobileV.test(mobile)){
			layer.alert('请先输入正确手机号');
			return;
		}
		var verifycod=$("#verifycode_img").val();
		if(verifycod == null ||verifycod == ''){
			layer.alert('请先输入图片验证码');
			return ;
		}else{
			if((verifycod.toUpperCase())!=code){
				layer.alert('图片验证码验证失败');
				$("#verifycode_img").val('')
				return ;
			}
			
		}
		//layer.msg('正在下发验证码。。。', {icon: 16,time: 3000,shade : [0.5 , '#000' , true]});
		$.ajax({
			type : 'POST',
			url : "${ctx}/code/ajaxSendVerifyCode.ht",
			data : {mobile : mobile},
			success : function(dataMap) {
				if(dataMap && dataMap.flag=="1"){
					//alert("验证码下发成功，注意查收");
					layer.alert('验证码下发成功，注意查收');
					var sendObj = $('input#verifyBtn');
					start_sms_button(sendObj);
				}else if(dataMap.flag=="2"){
					layer.alert("该手机号已被注册");
				}else{
					layer.alert("验证码下发失败");
				}
			},
			error : function(dataMap){
				layer.alert("验证码下发失败");
			}
		});
	}


	function start_sms_button(obj){
	    var count = 1 ;
	    var sum = 60;
	    obj.attr('disabled',true); 
	    var i = setInterval(function(){
	      if(count > sum){
	        obj.attr('disabled',false);
	        obj.val('发送验证码');
	        clearInterval(i);
	      }else{
	        obj.val('重发剩余'+parseInt(sum - count)+'秒');
	      }
	      count++;
	    },1000);
	  }

	//////////////////////////////////////
	
	
					var code ; //在全局定义验证码 
					var codeLength = 4;//验证码的长度
					 var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
							 'S','T','U','V','W','X','Y','Z');//随机数
			 		$(function(){
			 			$("#verifycode_img").val('');
			 			code='';
						codeLength=4;
								 for(var i = 0; i < codeLength; i++) {//循环操作
									var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
								
									code += random[index];//根据索引取得随机数加到code上
								}
								//alert(code)
						
								$("#code").val(code);
					}); 
					
			//产生验证码
		function createCode(){
				 code = ''; 
				 for(var i = 0; i < codeLength; i++) {//循环操作
					var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
					code += random[index];//根据索引取得随机数加到code上
				}
				// alert(code)
				$("#code").val(code);
			}
	
	
</script>	
<!-- Bootstrap -->
<%-- <link href="${ctx}/pages/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/css/bootstrap-theme.css" rel="stylesheet"/>
<link href="${ctx}/pages/css/bootstrap.css" rel="stylesheet"/>
<link href="${ctx}/pages/css/bootstrap-theme.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/css/index.css" rel="stylesheet"/> --%>
		<style type="text/css">
		/* .form-list1 p{
			height: 22px; 
			padding-top: 11px; 
			padding-bottom: 10px;
		}
		.form-list-input1:focus{ 
			border-color:#eb5454;
		}
		p.error{
			color:#eb5454; 
		}
.register_new {
  width: 1160px;
  margin: 0 auto;
  height: 600px;
  border: 1px solid #eee;
  background: #Fff;
}
.register_left {
  float: left;
  width: 750px;
  margin-top: 50px;
}
.form-list1 {
  padding-left: 20px;
  padding-bottom: 20px;
  width: 720px;
  height: 70px;
}
.form-list1 .form-list-input1 {
  width: 320px;
  padding:0 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  height: 40px;
  line-height:40px;
  font-family: "Microsoft YaHei";
  float: left;
}
.form-list1 label {
  font-size: 12px !important;
  font-family: "Microsoft YaHei";
  color: #656565;
  width: 105px;
  display: block;
  float: left;
  padding-top: 10px;
  text-align: right;
  padding-right: 20px;
}

#code
			{
				font-family:Arial;
				font-style:italic;
				font-weight:bold;
				border:0;
				letter-spacing:2px;
				color:blue;
			} */
			
			
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
		
</style>

	</head>
	 <body>
	      <!--logon top begin-->
		<div class="register_top">
            <div class="register_topCon">
                <ul>
                    <li><a href="javascript:void(0);">登陆</a></li>
                    <li class="register_topCon_sign">|</li>
                    <li><a href="javascript:void(0);">免费注册</a></li>
                    <li style="float:right;padding-left:7px;"><a href="javascript:void(0);">全国站点</a></li>
                    <li style="float:right;"><a href="">切换</a></li>
                    <li class="register_topCon_sign2" style="float:right;">|</li>
                    <li style="float:right;"><a href="">客服中心</a></li>
                    <li class="register_topCon_sign2" style="float:right;">|</li>
                    <li style="float:right;"><a href="">帮助手册</a></li>

                </ul>
            </div>
        </div>
        
        
         <div class="register_logo">
            <div class="register_logoCon">
                <div class="register_logoCon_image f_left">
                    <a href="javascript:void(0);">
                        <img src="${ctx}/pages/images/user_register_logo.png" alt="" />
                    </a>
                    
                </div>
                <span class="register_logoName f_left">用户中心</span> 
            </div>
        </div>
        
        <div class="register_main">
            <div class="register_mainTit">
                <a href="javascript:void(0);"></a>
                <div class="register_mainTit_left">个人账户注册</div>
                <div class="register_mainTit_right"><a href="${ctx}/tenant/editNew1.ht">切换成企业账户注册</a></div>
            </div>
            <div class="register_mainCon">
		 		<form id="loginForm" action="${ctx}/user/regPostCloud_v2.ht" method="post" class="distance">
			     <%-- <div class="form-list1">
				     <label>用户名</label><input type="text"  name="userName" maxlength="30" minlength="2" placeholder="2-30个汉字，大小写字母，数字" class="form-list-input1" required data-rule-remote="${ctx}/cloud/console/checkUserNameRepeat.ht" data-msg-remote="该用户名已被使用"/>
					
				 </div> --%>
				 
				  <div class="register_mainCon_list">
                        <span class="register_mainCon_tit">用户名</span>
                        <input type="text"  name="userName" maxlength="30" minlength="2" placeholder="2-30个汉字，大小写字母，数字" class="register_mainCon_con" required data-rule-remote="${ctx}/cloud/console/checkUserNameRepeat.ht" data-msg-remote="该用户名已被使用"/>
                        <div class="tipinfo"></div>
                    </div>
				 
				<%--  <div class="form-list1">

				   <label>注册邮箱</label><input type="text"  name="email" maxlength="60" placeholder="请输入邮箱地址" class="form-list-input1" required email="true" data-msg-email="请输入正确的email地址" data-rule-remote="${ctx}/cloud/console/checkEmailRepeat.ht" data-msg-remote="该邮箱已被注册"/>

				   	
				 </div>  --%>
				 
				  <div class="register_mainCon_list">
                        <span class="register_mainCon_tit">注册邮箱</span>
                        <input type="text"  name="email" maxlength="60" placeholder="请输入邮箱地址" class="register_mainCon_con" required email="true" data-msg-email="请输入正确的email地址" data-rule-remote="${ctx}/cloud/console/checkEmailRepeat.ht" data-msg-remote="该邮箱已被注册"/>
                        <div class="tipinfo"></div>
                    </div>
				 
				 
				<%--  <div class="form-list1">
				   <label>手机号码</label><input type="text"  id="mobile" name="mobile" maxlength="11" placeholder="11位数字" class="form-list-input1" required mobile="true" data-msg-mobile="请输入正确手机号码"  data-rule-remote="${ctx}/cloud/console/checkPhoneRepeat.ht" data-msg-remote="该手机号已被注册"/>
				 </div> --%>
				 
				  <div class="register_mainCon_list">
                        <span class="register_mainCon_tit">手机号码</span>
                        <input type="text"  id="mobile" name="mobile" maxlength="11" placeholder="11位数字" class="register_mainCon_con" required mobile="true" data-msg-mobile="请输入正确手机号码"  data-rule-remote="${ctx}/cloud/console/checkPhoneRepeat.ht" data-msg-remote="该手机号已被注册"/>
                        <div class="tipinfo"></div>
                    </div>
				 
			<!--   <div class="form-list1">
				   <label>图片验证码</label>
				  <input type="text"  style="width: 100px" id="verifycode_img" id="verifycode" name="verifycode_img" maxlength="4" placeholder="图片验证码" class="form-list-input1"  id = "verifycode"/> &nbsp;&nbsp;<input type = "button" id="code" onclick="createCode()" style="width: 70px;height: 38px"/>
				 </div>  -->
				 
				 <div class="register_mainCon_list">
                        <span class="register_mainCon_tit">图片验证码</span>
                        <input type="text"  style="width: 100px" id="verifycode_img" id="verifycode" name="verifycode_img" maxlength="4" placeholder="图片验证码" class="register_mainCon_con"  id = "verifycode"/> &nbsp;&nbsp;<input type = "button" id="code" onclick="createCode()" style="width: 70px;height: 38px"/>
                        <div class="tipinfo"></div>
                    </div>
				 
			     
				<!--  <div class="form-list1">
				   <label>验证码</label>
				   <input type="text"  style="width: 100px" id="verifycode" id="verifycode" name="verifycode" maxlength="4" placeholder="4位数字" class="form-list-input1"  /> &nbsp;&nbsp;<input type="button" style="height: 38px;border-radius:5px;padding:0 5px;" name="verifyBtn" id="verifyBtn" value="发送验证码" onclick="verify();"/>
				   
				 </div> -->
				 
				  <div class="register_mainCon_list register_pisition">
                        <span class="register_mainCon_tit">验证码</span>
                        <!-- <input type="text" id="code" name="code" class="register_mainCon_con " placeholder="4位数字" />
                        <input class="register_sendCode" type="button" value="发送验证码" /> -->
                         <input type="text"  style="width: 100px" id="verifycode" id="verifycode" name="verifycode" maxlength="4" placeholder="4位数字" class="register_mainCon_con"  /> &nbsp;&nbsp;<input type="button" style="height: 38px;border-radius:5px;padding:0 5px;" name="verifyBtn" id="verifyBtn" value="发送验证码" onclick="verify();"/>
                        <div class="tipinfo"></div>
                    </div>
				 
				 
				<!--   <div class="form-list1">
				   <label>密码</label><input type="password" onpaste="return false" name="password" maxlength="16" minlength="6"  placeholder="6-16位的大小写字母，数字，下划线" class="form-list-input1" required id="password" />
				   
				 </div> -->
				 
				 
				  <div class="register_mainCon_list">
                        <span class="register_mainCon_tit">密码</span>
                        <input type="password" onpaste="return false" name="password" maxlength="16" minlength="6"  placeholder="6-16位的大小写字母，数字，下划线" class="register_mainCon_con" required id="password" />
                        <div class="tipinfo"></div>
                    </div>
                    
				<!--   <div class="form-list1">
				   <label>确认密码</label><input type="password" onpaste="return false" name="repeatPassword" maxlength="16" minlength="6" placeholder="6-16位大小写字母，数字，下划线" class="form-list-input1" required equalTo="#password"/>
				 </div> -->
				 
				  <div class="register_mainCon_list">
                        <span class="register_mainCon_tit">确认密码</span>
                        <input type="password" onpaste="return false" name="repeatPassword" maxlength="16" minlength="6" placeholder="6-16位大小写字母，数字，下划线" class="register_mainCon_con" required equalTo="#password"/>
                        <div class="tipinfo"></div>
                    </div>
				 
				<!--  <div class="form-list1"> 
				     <div class="btn-login1" style="margin-left: 85px; width: 160px; padding-left: 110px;">注  册</div>
				    <button class="btn-login1"  title="提 交"  value="提 交" style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 105px; width: 270px; height: 40px;" id="button">提 交</button>
				    
				 </div> -->
				 <div class="register_mainCon_list">
                        <span class="register_mainCon_tit"></span>
                         <button class="register_mainCon_btn"  title="提 交"  value="提 交" style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 0px; width: 370px; height: 40px;" id="button">提 交</button>
                        <div class="tipinfo"></div>
                    </div>
				 <input type="hidden"  name ="url" value="${url}"/>
				</form>
			 </div>
			 
		 </div>
		 <div class="register_footer">
            <div class="register_footerCon">
                <span class="register_copy">Copyright © 2015, All Rights Reserved　京ICP备05067351号-2</span><span style="margin-left:18px;"><a href="javascript:void(0);">法律声明</a></span><span style="margin-left:11px;margin-right:5px;">|</span><span><a href="javascript:void(0);">站点地图</a></span><span style="margin-left:11px;margin-right:5px;">|</span><span><a href="javascript:void(0);">诚聘精英</a></span>
            </div>
        </div>
	</body>
	
	<script type="text/javascript">
	
	$(function(){
		$("#button").click(function(){
			var verifycode=$("#verifycode").val();
			//alert(verifycode+"\n"+$("#mobile").val());
			if(verifycode == null ||verifycode=='' ){
				layer.alert("请输入验证码");
				return false;
			}
			var verifycode_img=$("#verifycode_img").val();
			if(verifycode_img == null ||verifycode_img == ''){
				layer.alert('请先输入图片验证码');
				return false;
			}
			if((verifycode_img.toUpperCase())!=code){
					layer.alert('图片验证码验证失败');
					$("#verifycode_img").val('');
					return false;
				}
			
			 /* else{
				alert(1)
				$.ajax({
				   type : 'POST',
				   dataType:"json",
					url : "${ctx}/isChenk.ht",
				   data : {
						    mobile : $("#mobile").val()
						    
						
					},
					success : function(dataMap) {
						alert(dataMap)
					},
					error : function(dataMap){
						layer.alert("验证码下发失败");
					}
				});
				
			}  */
			
			$("#loginForm").submit();
		});
		
	});
	
	
	
	
	$.validator.setDefaults({
		
		submitHandler: function(form) {
			//alert(1)
			layer.msg('正在提交请稍候。。。', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
			form.submit();
		}
	}); 
	
	$().ready(function(){
		/* $(".btn-login1").click(function(){
			if($("#loginForm").valid()){
				layer.msg('正在提交请稍候。。。', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
				$("#loginForm").submit();
			}
		}) */
		/*  $("#loginForm").validate({
			submitHandler: function() {
			}
		});  */
		
		 $("#loginForm").validate(); 
	});

	function subFrom(form){
		$(form).submit(); 
	}
	//配置错误提示的节点，默认为label，这里配置成 p （errorElement:'p'）
	$.validator.setDefaults({
		errorElement:'p'
	});

	//配置通用的默认提示语
	$.extend($.validator.messages, {
		required: '必填',
	    equalTo: "请再次输入相同的值"
	});

	//邮箱 
	jQuery.validator.addMethod("mail", function (value, element) {
		var mail = /^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$/;
		return this.optional(element) || (mail.test(value));
	}, "邮箱格式不对");
	
	//手机验证规则  
	jQuery.validator.addMethod("mobile", function (value, element) {
	    var mobile = /^1[3|4|5|7|8]\d{9}$/;
		return this.optional(element) || (mobile.test(value));
	}, "手机格式不对");

	//验证当前值和目标val的值相等 相等返回为 false
	jQuery.validator.addMethod("equalTo2",function(value, element){
	    var returnVal = true;
	    var id = $(element).attr("data-rule-equalto2");
	    var targetVal = $(id).val();
	    if(value === targetVal){
	        returnVal = false;
	    }
	    return returnVal;
	},"不能和原始密码相同");
	function changeArea(obj){
		var value = $(obj).val();
		switch(value){
		case 'nationwide':
			loation = "${ctx}/index.ht";
			break;
		case 'jiangxi':
			location = "${ctx}/jiangxiIndex.ht";
			break;
		}
	}
		
</script>
</html>
