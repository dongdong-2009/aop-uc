<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- <script id="allmobilize" charset="utf-8"
	src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script> -->
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" href="#" />
<meta charset="utf-8" />
<title>航天云网注册主页</title>
<%@ include file="/commons/include/form.jsp" %>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet"
	type="text/css" />
<%-- <link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" /> --%>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/layer/layer.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/jquery/jquery.validate.js"></script>
<%-- <script language="JavaScript" type="text/javascript"
	src="${ctx}/js/jquery/placeholderfriend.js"></script> --%>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>

<script type="text/javascript">
	function verify() {

		var mobileV = /^1[3|4|5|7|8]\d{9}$/;
		var mobile = $("#mobile").attr("value");
		var code = $("#code").val();
		////alert(code)
		if (mobile == null || mobile == '') {
			layer.alert('请先输入手机号');
			return;
		}
		if (!mobileV.test(mobile)) {
			layer.alert('请先输入正确手机号');
			return;
		}
		var verifycod = $("#verifycode_img").val();
		if (verifycod == null || verifycod == '') {
			layer.alert('请先输入图片验证码');
			return;
		} else {
			if ((verifycod.toUpperCase()) != code) {
				layer.alert('图片验证码验证失败');
				$("#verifycode_img").val('')
				return;
			}

		}
		//layer.msg('正在下发验证码。。。', {icon: 16,time: 3000,shade : [0.5 , '#000' , true]});
		$.ajax({
			type : 'POST',
			url : "${ctx}/code/ajaxSendVerifyCode.ht",
			data : {
				mobile : mobile
			},
			success : function(dataMap) {
				if (dataMap && dataMap.flag == "1") {
					//alert("验证码下发成功，注意查收");
					layer.alert('验证码下发成功，注意查收');
					var sendObj = $('input#verifyBtn');
					start_sms_button(sendObj);
				} else if (dataMap.flag == "2") {
					layer.alert("该手机号已被注册");
				} else {
					layer.alert("验证码下发失败");
				}
			},
			error : function(dataMap) {
				layer.alert("验证码下发失败");
			}
		});
	}

	function start_sms_button(obj) {
		var count = 1;
		var sum = 60;
		obj.attr('disabled', true);
		var i = setInterval(function() {
			if (count > sum) {
				obj.attr('disabled', false);
				obj.val('发送验证码');
				clearInterval(i);
			} else {
				obj.val('重发剩余' + parseInt(sum - count) + '秒');
			}
			count++;
		}, 1000);
	}

	//////////////////////////////////////

	var code; //在全局定义验证码 
	var codeLength = 4;//验证码的长度
	var random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');//随机数
	$(function() {
		$("#verifycode_img").val('');
		code = '';
		codeLength = 4;
		for (var i = 0; i < codeLength; i++) {//循环操作
			var index = Math.floor(Math.random() * 36);//取得随机数的索引（0~35）

			code += random[index];//根据索引取得随机数加到code上
		}
		//alert(code)

		$("#code").val(code);
		
		
		
		//新增加的form提交代码
		var options={};
		if(showResponse){
			options.success=showResponse;
		}
		var frm=$('#updateUserForm').form();
		$("#sub").click(function(){
			var verifycode = $("#verifycode").val();
			//alert(verifycode+"\n"+$("#mobile").val());
			if (verifycode == null || verifycode == '') {
				layer.alert("请输入验证码");
				return false;
			}
			var verifycode_img = $("#verifycode_img").val();
			if (verifycode_img == null || verifycode_img == '') {
				layer.alert('请先输入图片验证码');
				return false;
			}
			if ((verifycode_img.toUpperCase()) != code) {
				layer.alert('图片验证码验证失败');
				$("#verifycode_img").val('');
				return false;
			}
            firstClick(frm,options);
        });
        $("#sub").dblclick(function(){
            firstClick(frm,options);
        });
        function firstClick(frm,options){
			//去掉所有的html标记
			if(validForm()){
				var code = $("#code").val();
				frm.setHtmlData();
				frm.ajaxForm(options);
				if(frm.valid()){
					layer.msg('正在保存中,请稍候...', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
					form.submit();
					firstClick = secondClick;
				}else{
					layer.alert("信息填写不正确");
				}
				//str = str.replace(/<[^>]+>/g,"");
				
			}else{
				layer.alert("请将必填信息填充完整再提交",{
				    skin: 'layui-layer-molv' //样式类名
				        ,closeBtn: 0
				    });
				return;
			}
			
        }
		function secondClick(){
			layer.alert("单据已经提交，请勿重复提交");
			return false;
		}
	});
			
			
	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		layer.closeAll();
		if (obj.isSuccess()) {
			if(obj.data.message.indexOf('变化')>0){
				layer.alert('由于您修改了企业认证信息，工作人员会在3-5个工作日内对您提供的最新信息进行审核。'+
						'如果审核成功系统会保存您提供的最新信息，如果审核失败系统会还原您之前认证过的信息。'+
						'如需要人工帮助请联系客服电话 010-88611981', {
			    	skin: 'layui-layer-molv' //样式类名
			    ,closeBtn: 0
				},function(){
					window.location.reload();
				});  
			}else{
				layer.alert('用户更新成功！', {
			    	skin: 'layui-layer-molv' //样式类名
			    ,closeBtn: 0
				},function(){
					$("#verifycode").val('');
					window.location.href='${ctx}/user/userManage.ht';
				});  
			}
		} else {
			layer.alert('用户更新失败：'+obj.getMessage(),{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});     
		}
	}
	
	function validForm(){
		var flag = true;
		$(".row em").parent().next().find("input").each(function(){
			var thisVal = $(this).val();
			var names = $(this).attr('name');
			if(names!="info" && typeof(names) != "undefined" && names!="logo" && names!="yyzzPic"  && names!="frPic"){
				if(thisVal==""||thisVal=="必填"){
					flag = false;
					$(this).after($("<label class='error'>该项为必填项</label>"));
					$(this).next().next().remove();
				}
			}				
		});
		return flag;
	}

	//产生验证码
	function createCode() {
		code = '';
		for (var i = 0; i < codeLength; i++) {//循环操作
			var index = Math.floor(Math.random() * 36);//取得随机数的索引（0~35）
			code += random[index];//根据索引取得随机数加到code上
		}
		// alert(code)
		$("#code").val(code);
	}
	
$(window.parent.document).find("#mainiframe").load(function(){
		
		var mainheight = 800;
		 $(this).height(mainheight);
	});
</script>
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

#code {
	font-family: Arial;
	font-style: italic;
	font-weight: bold;
	border: 0;
	letter-spacing: 2px;
	color: blue;
}

.tipBox{
border: solid 1px #ddd;
height:70px;
border-radius:10px;
margin-bottom:10px;
}

#tip{
height: 20px;
margin-left: 10px;
margin-top: 10px;
}
#warmtips{
margin-left: 5px;
line-height: 30px;
}

.tipBox p span{
margin-left: 35px;
line-height: 20px;

}
</style>

</head>
<body>

<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>首次登录后，通过手机验证可以修改一次用户名。</span></p>
</div>

	<form id="updateUserForm" action="${ctx}/user/updateUser.ht"
		method="post" class="distance">
		<input type="hidden" name="userId" value="${user.userId}"/>

		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">用户名</span> <input type="text"
				name="account" maxlength="30" minlength="2"
				placeholder="2-30个汉字，大小写字母，数字" class="register_mainCon_con" required
				data-rule-remote="${ctx}/user/checkUserNameRepeat.ht"
				data-msg-remote="该用户名已被使用" value="${user.account}" validate="{required:true}" />
			<div class="tipinfo"></div>
		</div>





		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">手机号码</span> 
			<c:choose>
			   <c:when test="${user.isMobailTrue =='1'}">
			      
			   <input type="text"
				id="mobile" name="mobile" maxlength="11" placeholder="11位数字"
				class="register_mainCon_con" required mobile="true"
				 value="${user.mobile}" readonly="readonly" />
			   </c:when>
			   <c:otherwise>
			      <input type="text"
				id="mobile" name="mobile" maxlength="11" placeholder="11位数字"
				class="register_mainCon_con" required mobile="true"
				data-msg-mobile="请输入正确手机号码"
				data-rule-remote="${ctx}/user/checkPhoneRepeat.ht"
				data-msg-remote="该手机号已被注册" value="${user.mobile}" validate="{required:true}" />
			   </c:otherwise>
			</c:choose>
			
			<div class="tipinfo"></div>
		</div>


		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">图片验证码</span> <input type="text"
				style="width: 100px" id="verifycode_img" id="verifycode"
				name="verifycode_img" maxlength="4" placeholder="图片验证码"
				class="register_mainCon_con" id="verifycode" /> &nbsp;&nbsp;<input
				type="button" id="code" onclick="createCode()"
				style="width: 70px; height: 38px" validate="{required:true}"/>
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
			<input type="text" style="width: 100px" id="verifycode"
				id="verifycode" name="verifycode" maxlength="4" placeholder="4位数字"
				class="register_mainCon_con" /> &nbsp;&nbsp;<input type="button"
				style="height: 38px; border-radius: 5px; padding: 0 5px;"
				name="verifyBtn" id="verifyBtn" value="发送验证码" onclick="verify();" validate="{required:true}" />
			<div class="tipinfo"></div>
		</div>


		


	<!-- 	<div class="register_mainCon_list">
			<span class="register_mainCon_tit">密码</span> <input type="password"
				onpaste="return false" name="password" maxlength="16" minlength="6"
				placeholder="6-16位的大小写字母，数字，下划线" class="register_mainCon_con"
				required id="password" />
			<div class="tipinfo"></div>
		</div> -->

		

		<!-- <div class="register_mainCon_list">
			<span class="register_mainCon_tit">确认密码</span> <input type="password"
				onpaste="return false" name="repeatPassword" maxlength="16"
				minlength="6" placeholder="6-16位大小写字母，数字，下划线"
				class="register_mainCon_con" required equalTo="#password" />
			<div class="tipinfo"></div>
		</div> -->

		<div class="register_mainCon_list">
			<span class="register_mainCon_tit"></span>
			<!-- <button class="register_mainCon_btn" title="提 交" value="提 交"
				style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 0px; width: 370px; height: 40px;"
				id="sub">提 交</button> -->
				  <input type="button" class="register_mainCon_btn" value="提交" id="sub"/>
			<div class="tipinfo"></div>
		</div>
		<input type="hidden" name="url" value="${url}" />
	</form>

</body>


</html>
