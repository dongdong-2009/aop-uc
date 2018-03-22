<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script id="allmobilize" charset="utf-8" src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" href="#" />
<meta charset="utf-8" />
<title>找回密码</title>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
 <link href="${ctx}/styles/uc/v2.0/top.css" rel="stylesheet" type="text/css" />
 
 
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/cloudFooterheader.css" />
 <script type="text/javascript" src="${ctx}/js/hotent/uc/CustomValid.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery.validate.js"></script>

<script type="text/javascript">
	  var mobileV = /^1[3|4|5|7|8]\d{9}$/;
	  var mailV = /^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$/;
	  var fasongcishu = 10;
	  function verify(){
			var mailmobile = $("#mailmobile").attr("value");
			if(mailmobile == null || mailmobile == ''){
				layer.alert('请输入手机号或邮箱');
				return;
			}
			if(!mobileV.test(mailmobile) && !mailV.test(mailmobile)){
				layer.alert('请输入正确手机号或邮箱');
				return;
			}
			var code=$("#code").val();
			var verifycod=$("#verifycode_img").val();
			if(verifycod == null ||verifycod == ''){
				layer.alert('请先输入图片验证码');
				return ;
			}else{
				if((verifycod.toUpperCase())!=code){
					layer.alert('图片验证码验证失败');
					createCode();
					$("#verifycode_img").val('')
					return ;
				}
			}
			//layer.msg('正在下发验证码。。。', {icon: 16,time: 3000,shade : [0.5 , '#000' , true]});
			if(mobileV.test(mailmobile)){
				$.ajax({
					type : 'POST',
					url : "${ctx}/code/ajaxCheckSendNumber.ht",
					data : {mobile : mailmobile},
					success:function(mCount){
						if(mCount>10){
							//layer.alert("该手机获取验证码过于频繁，请一小时后重试");
							//$("#verifyBtn").setAttribute("disabled", true);
							$("#mobileCount").show();
							$("#mobileCount").text("该手机获取验证码过于频繁，请一小时后重试");
							document.getElementById("verifyBtn").setAttribute("disabled", true);
						}else{
							var msg = "该手机在最近一小时内还可以获取"+(fasongcishu-mCount)+"次验证码,请尽快完成验证";
							$.ajax({
								type : 'POST',
								url : "${ctx}/code/ajaxSendVerifyCodeFind.ht",
								data : {
									mobile : mailmobile,
									forgot : 1
								},
								success : function(dataMap) {
									if(dataMap && dataMap.flag=="1"){
										layer.alert('验证码下发成功，注意查收');
										createCode();
										$(".register1_imgCode").next().empty();
										var sendObj = $('input#verifyBtn');
										start_sms_button(sendObj);
										$("#mobileCount").show();
										$("#mobileCount").text(msg);
									}else if(dataMap.flag=="2"){
										layer.alert("该手机号未注册");
									}else{
										/* layer.alert("验证码下发失败"); */
										layer.alert("发送验证码频率过快，请于1个小时后再修改");
									}
								},
								error : function(dataMap){
									layer.alert("验证码下发失败");
								}
							});
						}
					}});
			
			}else{
				$.ajax({
					type : 'POST',
					url : "${ctx}/code/ajaxCheckSendNumber.ht",
					data : {mobile : mailmobile},
					success:function(mCount){
						if(mCount>10){
							$("#mobileCount").show();
							$("#mobileCount").text("该邮箱获取验证码过于频繁，请一小时后重试");
							document.getElementById("verifyBtn").setAttribute("disabled", true);
						}else{
							var msg = "该邮箱在最近一小时内还可以获取"+(fasongcishu-mCount)+"次验证码,请尽快完成验证";
							$.ajax({
								
								type : 'POST',
								url : "${ctx}/code/sendEmailVerifyCodeFind.ht",
								data : {
									email : mailmobile,
									forgot : 1
								},
								success : function(dataMap) {
									if(dataMap && dataMap.flag=="1"){
										layer.alert('验证码下发成功，注意查收');
										createCode();
										$(".register1_imgCode").next().empty();
										var sendObj = $('input#verifyBtn');
										start_sms_button(sendObj);
										$("#mobileCount").show();
										$("#mobileCount").text(msg);
									}else if(dataMap.flag=="2"){
										layer.alert("该邮箱未注册");
									}else{
										layer.alert("验证码下发失败");
									}
								},
								error : function(dataMap){
									layer.alert("验证码下发失败");
								}
							});
						}
						
					}
						})
				
			}
		}
	  
	function start_sms_button(obj){
	    var count = 1 ;
	    var sum = 60;
	    obj.attr('disabled',true); 
	    var i = setInterval(function(){
	      if(count > sum){
	        obj.attr('disabled',false);
	        obj.val('发送验证码');
	        //$("#sendCode").remove();
	        clearInterval(i);
	      }else{
	        obj.val(parseInt(sum - count)+'秒后可重发');
	      /*   if($("#sendCode").html()==null){
	        	obj.parent().append("<span id='sendCode' style='font-size:14px;'>发送成功</span>");
	        } */
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
				// $("#verifycode_img").val("");
				// $(".register1_imgCode").next().empty();
				 code = ''; 
				 for(var i = 0; i < codeLength; i++) {//循环操作
					var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
					code += random[index];//根据索引取得随机数加到code上
				}
				$("#code").val(code);
			}
			
		  
			//验证图片验证码
			$(function(){
				$("#verifyBtn").attr('disabled',false);
				/* $("#verifycode_img").attr('placeholder','图片验证码'); */
			});
	
			//配置错误提示的节点，默认为label，这里配置成 span （errorElement:'span'）
			$.validator.setDefaults({
				errorElement:'span'
			});
		          
			$.extend($.validator.messages, {
				required: '不能为空',
				minlength: $.validator.format("密码格式位数不正确,请重新输入"),
				maxlength: $.validator.format("密码格式位数不正确,请重新输入"),
				equalTo: "请再次输入相同的值"
			});
			
			var CTX="${pageContext.request.contextPath}";
			//验证图片验证码
			function checkBlurVerify1(){
			  
				$("#verifycode_img").removeClass("item-focus");
				var verifycode_img=$("#verifycode_img").val();
				result=$("#loginForm").validate().element($("#loginForm").find('input[name="verifycode_img"]'));
				if(result){
					if((verifycode_img.toUpperCase())==code){
						$("#verifycode_img").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
					}else if((verifycode_img.toUpperCase())==''){
						$("#verifycode_img").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode_img" class="error"><img src='+CTX+'"/resources/css/images/u338.png">请输入验证码</label></font>');
					}
					else 
					   {
						$("#verifycode_img").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode_img" class="error"><img src='+CTX+'"/resources/css/images/u338.png">输入错误,请点击验证码刷新</label></font>');
					}
					
				}
			}
	
</script>

<style type="text/css">
.findPwd_title {
    width: 100%;
    border-bottom: 1px solid #ddd;
    height: 50px;
    margin-top:50px;
    }
.findPwd {
	border-left: 5px solid #ff771d;
    font-family: "Microsoft YaHei";
    color: #656565;
    font-size: 24px;
    width: 580px;
    float: left;
    padding: 5px 0 0 10px;
    margin-left: 400px;}
.findPwd_title a{
	color: #666;
    text-decoration: none;
	line-height: 60px;}
.form-list1 {
    padding-left: 20px;
    padding-bottom: 20px;
    width: 720px;
    height: 50px;
    font-size: 16px;}
.login_intro {
	margin-left:620px;}
.form-list{
	padding-left:20px;
	padding-bottom:20px;}
.form-list .form-list-input{
	width:230px;
	padding:10px;
	border-radius:5px;
	border:1px solid #ddd;
	height:22px;
	font-family:"Microsoft YaHei";}
.register_footer{
	width:1058px;
	margin:0 auto;
	height: 80px;
	padding-left:78px;
	margin-top: 150px;}
.findPwd_intro{
	padding-top:45px;
	padding-left:500px;}
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
.btn-login1{
	width:160px;
	height:30px;
	background:#ff771d;
	color:#fff;
	border-radius:6px;
	cursor:pointer;
	font-size:14px;
	padding:10px 0 0 130px;
	margin-left:80px;}
.error{
	color:red;
}
 body {
    color: #666;
    font-family: Microsoft YaHei;
    font-size: 14px;}
#code{
	font-family:Arial;
	font-style:italic;
	font-weight:bold;
	border:0;
	letter-spacing:2px;
	color:blue;} 
	
.clound_footer{
	width:1230px; 
	height:78px; 
	color:#707070; 
	text-align: center;
	margin-top: 120px;
	margin-left: 220px;}
			
</style> 
</head>
	 <body>
	  <%@ include file="/commons/findPwdTop.jsp"%>
        <div class="findPwd_title">
		     <div class="findPwd">找回密码</div>
		</div>
		<div class="findPwd_intro">
		     <form id="loginForm" >
      			  <div class="form-list1">
				    <label class="findPwd_mainCon_tit"><span style="color:red">*</span>手机号/邮箱：</label>
				    <input type="text" id="mailmobile" name="mailmobile" maxlength="60"  class="findPwd_mainCon_con" mailmobile ="true"  required data-rule-remote="${ctx}/user/isParamExist.ht?mobileRandom=${mobileRandom}" data-msg-remote="该邮箱或手机号未绑定,请重新输入"/>
				  	<span id="cannotbenone"></span>
				  </div> 
			  	<div class="form-list1">
				   <label class="findPwd_mainCon_tit"><span style="color:red">*</span>图片验证码：</label>
				  <input type="text"  style="width: 180px" id="verifycode_img"  name="verifycode_img"  onblur="checkBlurVerify1()" maxlength="4" class="findPwd_mainCon_con"  /> &nbsp;&nbsp;<input type = "button" id="code" onclick="createCode()" style="width: 70px;height: 38px"/>
				 <font color="red"></font>
				 </div> 
				 <div class="form-list1">
				   <label class="findPwd_mainCon_tit"><span style="color:red">*</span>手机号/邮箱验证码：</label>
				   <input type="text"  style="width: 180px"  id="verifycode" name="verifycode" maxlength="4" required class="findPwd_mainCon_con"  /> &nbsp;&nbsp;<input type="button" style="height: 38px;border-radius:5px;padding:0 5px;cursor: pointer;" name="verifyBtn" id="verifyBtn" value="发送验证码" onclick="verify();"/>
				  <!--  <span style= "display:block;text-align: center;" id="mobileCount"></span> -->
				 	 <div style="padding: 5px 0 5px 179px;margin-top: 0px;display:none;" id="mobileCount">
				 	 </div>
				 </div>
				  <div class="form-list1">
				   <label class="findPwd_mainCon_tit"><span style="color:red">*</span>新密码：</label>
				   <input type="password" onkeyup="this.value=this.value.replace(/[^\w]/g,'')" onpaste="this.value=this.value.replace(/[^\w]/g,'')" name="password" maxlength="16" minlength="6"  class="findPwd_mainCon_con" required id="password" />
				 </div>
				  <div class="form-list1">
				   <label class="findPwd_mainCon_tit"><span style="color:red">*</span>确认新密码：</label>
				   <input id= "repeatPassword" type="password" onkeyup="this.value=this.value.replace(/[^\w]/g,'')" onpaste="this.value=this.value.replace(/[^\w]/g,'')" name="repeatPassword" maxlength="16" minlength="6"  class="findPwd_mainCon_con" required equalTo="#password" />
				 </div>
				   <input  type="hidden" id="identification" name="identification" value="${identification}"/>
				  <div class="form-list" > 
			  		<input type="button" class="btn-login1" title="重置密码" value="重置密码" style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 170px; width: 200px; height: 40px;"/>
				  </div>
				 <div class="form-list">
				 	<div style="width:50px;float:left;font-size:14px;margin-left:40px;">提示：</div>
				 	<div style="width:370px;float:left;font-size:14px;">
					 	<p>如您已经加入企业，也可以联系本企业管理员重置账户密码</p>
					 	<p>企业管理员忘记密码时，无法通过手机号或邮箱找回，请联系航天云网客服</p>
					</div>
				 </div>
	          </form>
		 </div>
         <%-- <jsp:include page="/commons/regFooter.jsp" /> --%>
         <div style="float: left">
			<%@include file="/commons/footer.jsp" %>
		</div>
	</body>
<!-- 	<script>
	var onburCount=0;
	function myrefresh(obj){
		var value1=obj.value;
		var regex = /^1[3,5,7,8]\d{9}$/;
	    var flag = regex.test($.trim(value1));
		if(!flag ){
			return false;
		}
		onburCount++;
		if(onburCount > 3){
			   location.reload();
			   return;
	     }
		
	}
	</script> -->

	<script type="text/javascript">
		$(function(){
			var frm=$('#loginForm').form();
			$(".btn-login1").click(function(){
					firstClick();
			})
			$(".btn-login1").dblclick(function(){
					firstClick();
			})
		 function firstClick(){
			var mailmobile = $("#mailmobile").attr("value");
			if(mailmobile == null || mailmobile == ''){
				layer.alert('请输入手机号或邮箱');
				return;
			}
			if(!mobileV.test(mailmobile) && !mailV.test(mailmobile)){
				layer.alert('请输入正确手机号或邮箱');
				/* $("#mailmobile").attr("border-color","red"); */
				return;
			}
			/* var code=$("#code").val();
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
			} */
			var verifycode = $("#verifycode").val();
			if(verifycode == null ||verifycode == ''){
				layer.alert('请先输入手机或邮箱验证码');
				return ;
			}
			var password = $("#password").val();
			if(password == null ||password == ''){
				layer.alert('密码不能为空');
				return ;
			}else{
				if(password.length<6 || password.length>16){
					layer.alert('密码长度在6-16字符之间');
					return ;
				}
			}
			 var repeatPassword = $("#repeatPassword").val();
			if(repeatPassword == null ||repeatPassword == ''){
				layer.alert('确认新密码不能为空');
				return ;
			}else{
				if(repeatPassword.length<6 || repeatPassword.length>16){
					layer.alert('确认新密码长度在6-16字符之间');
					return ;
				}
			} 
			if(repeatPassword != password){
				layer.alert('密码与确认新密码不一致');
				return ;
			}
			if(frm.valid()){
				layer.msg('正在保存中,请稍候...', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
				$.ajax({
					type : 'POST',
					url : "${ctx}/user/resetPwd.ht",
					data : {
						mailmobile : mailmobile,
						password : $("#password").val(),
						verifycode : $("#verifycode").val(),
						identification : $("#identification").val()
					},
					success : function(resultMessage) {
						
						if(resultMessage && resultMessage.indexOf("成功") > 0){
							firstClick = secondClick;
							 layer.open({
								//type : 2,
								title : '操作成功',
								//shadeClose : false,
								//fix : true,
								//shade : 0.6,
								content:'重置密码成功'
								//area : [ '425px', '210px' ]
							}); 
							
						}else{
							layer.alert(resultMessage);
						}
					},
					error : function(dataMap){
						firstClick = secondClick;
						layer.alert("密码重置失败");
					}
				});
			}
		}
		
		function secondClick(){
			layer.alert("单据已经提交，请勿重复提交");
			return false;
		}
		 $("#loginForm").validate(); 
	});
	
	$.validator.setDefaults({
		
		submitHandler: function(form) {
			layer.msg('正在提交请稍候。。。', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
			form.submit();
		}
	}); 
	
	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		layer.closeAll();
		if (obj.isSuccess()) {
			if(obj.data.resultMessage.indexOf('成功')>0){
				layer.alert('重置密码成功！3秒后跳转至登陆页面', {
			    	skin: 'layui-layer-molv' //样式类名
			    ,closeBtn: 0
				},function(){
					window.location.href="${ctx}/CAS/casLogout.ht";
				});  
			}
		} else {
			layer.alert('重置密码失败：'+obj.getMessage(),{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});     
		}
	}
 

	//邮箱 
	jQuery.validator.addMethod("mailmobile", function (value, element) {
		var mail = /^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$/;
		var mobile = /^1[3|4|5|7|8]\d{9}$/;
		return this.optional(element) || (mail.test(value))||this.optional(element) || (mobile.test(value));
	}, "手机或邮箱格式不正确,请重新输入");
	
		
</script>
</html>
