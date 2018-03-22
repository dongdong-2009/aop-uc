//扩展手机验证的插件
//手机验证规则  
jQuery.validator.addMethod("mobile", function (value, element) {
	    var mobile = /^1[3|4|5|7|8]\d{9}$/;
		return this.optional(element) || (mobile.test(value));
}, "手机格式不正确,请重新输入");
var mobileLength=0;
var verifycode_imgLength=0;
var duanxincodeLength=0;
var invititedCodeLength=0;
var sms_tishi="";
var clickCount=0;

var fasongcishu = 10;

$(function(){
	validate("loginForm");
	//产生图片验证码
	$("#verifycode_img").val('');
		code='';
	         codeLength=4;
			 for(var i = 0; i < codeLength; i++) {//循环操作
				var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
			
				code += random[index];//根据索引取得随机数加到code上
			}
		$("#code").val(code);
		
		//为密码框绑定触发事件
		$('#verifycode_img').bind('input propertychange', function() {
			verifycode_imgLength=$("#verifycode_img").val().length;
		});
		//为公司名框绑定触发事件
		$('#duanxincode').bind('input propertychange', function() {
			duanxincodeLength=$("#duanxincode").val().length;
		});
		//为公司名框绑定触发事件
		$('#invititedCode').bind('input propertychange', function() {
			invititedCodeLength=$("#invititedCode").val().length;
		});
});
var code ; //在全局定义验证码 
var codeLength = 4;//验证码的长度
var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
		 'S','T','U','V','W','X','Y','Z');//随机数
function validate(id){
	var form = $("#" + id);
	var rtn = form.validate({
		onfocusout:function(eles){
			$(eles).valid();
		},
		rules: {
			mobile:{
				required: true,
				mobile:true,
				remote:  {
					url: CTX + '/user/checkPhoneRepeat.ht',
					type: "post",
					dataType: "json",
					async : false ,
					data: { 
						"mobile":function(){
					          return $("#mobile").val();
					          },
					    "mobileRandom":$("#mobileRandom").val()
					}
				}
			},
			verifycode_img:{
				required: true
			},
			verifycode:{
				required: true
			}
        },
        messages:{
        	mobile:{
        		required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入常用手机号',
        		mobile:'<img src="'+CTX+'/resources/css/images/u338.png"/>手机格式不正确,请重新输入',
        		remote:'<img src="'+CTX+'/resources/css/images/u338.png"/>此手机号已被绑定,<a href="javascript:Login();">登录</a>或选择其他的账号'
        	},
        	verifycode_img:{
				required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入验证码'
			},
			verifycode:{
				required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入验证码'
			}
        },
        debug: true,
        errorPlacement: function(error, element){
        	var placement = $(element.parent("div").find("font"));
        	error.appendTo( placement ); 
        }
	});
}
//产生验证码
function createCode(){
		 // $("#verifycode_img").val("");
		 // $(".register1_imgCode").next().empty();
		 code = ''; 
		 for(var i = 0; i < codeLength; i++) {//循环操作
			var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
			code += random[index];//根据索引取得随机数加到code上
		}
		// alert(code)
		$("#code").val(code);
	}
function change2person(){
	var systemId=$("#systemId").val();
	var returnUrl=$("#returnUrl").val();
	var serviceUrl=$("#serviceUrl").val();
	if(systemId != null && systemId != ""){
		window.location.href="reg_personal_cloud.ht?systemId="+systemId+"&returnUrl="+returnUrl+"&serviceUrl="+serviceUrl;
	}else{
		window.location.href="reg_personal_cloud.ht"
	}
}

function change2tean(){
	var systemId=$("#systemId").val();
	var returnUrl=$("#returnUrl").val();
	if(systemId != null && systemId != ""){
		window.location.href="reg_cloud.ht?systemId="+systemId+"&returnUrl="+returnUrl;
	}else{
		window.location.href="reg_cloud.ht"
	}
}
//发送短信验证码
function verify(){
	    var result = false;
		result=$("#loginForm").validate().element($("#loginForm").find('input[name="mobile"]'));
		var verifycod=$("#verifycode_img").val();
		if(verifycod == null ||verifycod == ''){
			layer.alert('请先输入图片验证码');
			$("#verifycode_img").focus();
			return ;
		}else{
			if((verifycod.toUpperCase())!=code){
				layer.alert('图片验证码验证失败');
				$("#verifycode_img").val('')
				$("#verifycode_img").focus();
				return ;
			}
			
		}
		if(result){
			var mobile = $("#mobile").val();
			$.ajax({
				type : 'POST',
				url : CTX + "/code/ajaxCheckSendNumber.ht",
				data : {mobile : mobile},
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
							url : CTX + "/code/ajaxSendVerifyCodeDataMap.ht",
							data : {mobile : mobile},
							success : function(dataMap) {
								if(dataMap && dataMap.flag=="1"){
									layer.alert('验证码下发成功，注意查收');
									//createCode();
									$(".register1_imgCode").next().empty();
									var sendObj = $('input#verifyBtn');
									start_sms_button(sendObj);
									$("#mobileCount").show();
									$("#mobileCount").text(msg);
									$("#mobileCodeValue").val(dataMap.codeValue);
									$("#mobileNo").val(dataMap.mobile);
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
        clearInterval(i);
      }else{
        obj.val('重发剩余'+parseInt(sum - count)+'秒');
      }
      count++;
    },1000);
  }
function submit(){
  var Verify =	checkBlurVerify();//图片验证码错误不让提交
   if(!Verify){
	  return Verify;
   }
	var systemId=$("#systemId").val();
	var serviceUrl=$("#serviceUrl").val();
	var result=false;
	var flag=false;
	result=$("#loginForm").valid();
	flag=isVerifycodeTrue();
	if(!flag &&$.trim($("#mobileCodeValue").val()).length==0 ){
		layer.alert("重新获取短信验证码！");
		return false;
	}
	if(flag&&result){
	var invititedCode=$("input[name='invititedCode']").val();
	if(invititedCode!=null&&invititedCode!=""){
		$.ajax({
			type : 'POST',
			url : CTX+"/user/checkOrgId.ht",
			dataType: "json",
			data : {invititedCode : invititedCode},
			success : function(data) {
				if(data){
					//如果存在
					result=true;
					
				}
				else{
					layer.alert("该邀请码不存在");
					result=false;
				}
			},
			error : function(data){
				layer.alert("网络异常，请稍后再试");
			}
		});
	}else{
		result=true;
	}
	}else{
		if(!flag){
			layer.alert(sms_tishi);
		}else{
		  layer.alert("请完善信息");
		}
		return;
	}
	if(result){
		$("#btn").attr("disabled","disabled").css("background","#dbdbdb");
		var params=new Array();
		params.push({name:"mobile",value:$("#mobile").val()});
		params.push({name:"invititedCode",value:invititedCode});
		params.push({name:"systemId",value:systemId});
		params.push({name:"serviceUrl",value:serviceUrl});
		Post(CTX+"/user/reg_cloud_2.ht",params);
	}
}

function submits(){	
	var systemId=$("#systemId").val();
	var serviceUrl=$("#serviceUrl").val();
	var result=false;
	var flag=false;
	result=$("#loginForm").valid();
	flag=isVerifycodeTrue();
	if(flag&&result){
		if(result){
			$("#btn").attr("disabled","disabled").css("background","#dbdbdb");
			var params=new Array();
			params.push({name:"mobile",value:$("#mobile").val()});
			params.push({name:"systemId",value:systemId});
			params.push({name:"serviceUrl",value:serviceUrl});
			Post(CTX+"/user/reg_personal_2.ht",params);
		}		
	}else{
		if(!flag){
			layer.alert(sms_tishi);
			return;
		}else{
		  layer.alert("请完善信息");
		  return;
		}
	}	
}

//校验手机验证码
function isVerifycodeTrue(){
	
	sms_tishi="";
	var result=true;
	var thisVal = $("#duanxincode").val();
	var mobile = $("#mobile").val();
	if($.trim($("#mobileCodeValue").val()).length==0){
		$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode" class="error"><img src='+CTX+'"/resources/css/images/u338.png">获取新的验证码！</label></font>');
		sms_tishi='获取新的验证码';
		return false; 
	}
	if(thisVal == ''){
		sms_tishi='验证码不能为空';
		result=false;
		return false;
	}
	$.ajax({
		type : 'POST',
		url : CTX+"/user/isChenk.ht",
		data : {
			mobile : mobile,
			verifycode : thisVal
			
		},
		async:false,
		success : function(data) {
			if(data == 'fail'){
				sms_tishi='验证码验证错误！';
				result=false;
				return;
			}
			/*else if(data =="ok"){
				result=true;
			}else{
				layer.alert(data,function(){
					 location.reload();
				 });
			}*/
			else{
				result=true;
			}
		},
		error : function(data){
			sms_tishi='系统异常！';
			result=false;
		}
	});
	return result;
}


function checkFocusVerify(obj){
	$(obj).addClass("item-focus");
	if(verifycode_imgLength>0){
		$(obj).parent("div").find("font").html('');
	}
}
//验证图片验证码
function checkBlurVerify(){
	var result1=true;
	$("#verifycode_img").removeClass("item-focus");
	var verifycode_img=$("#verifycode_img").val();
	result=$("#loginForm").validate().element($("#loginForm").find('input[name="verifycode_img"]'));
	if(result){
		if((verifycode_img.toUpperCase())==code){
			$("#verifycode_img").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
		}else
		   {
			$("#verifycode_img").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode_img" class="error"><img src='+CTX+'"/resources/css/images/u338.png">输入错误,请点击验证码刷新</label></font>');
			result1 = false;
		   }
		
	}
	return result1;
}
//验证短信验证码


function checkFocusVerifyCode(obj){
	$(obj).addClass("item-focus");
	if(duanxincodeLength>0){
		$(obj).parent("div").find("font").html('');
	}
}
var isChenkCount=0;
function checkVerifycode(obj){
	var value1=obj.value;
	var regex = new RegExp("^.{4}$");
           //验证有4位的数字
    var flag = regex.test($.trim(value1));
	if(!flag ){
		return false;
	}
	
	if($.trim($("#mobileCodeValue").val()).length==0 ){
		$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode" class="error"><img src='+CTX+'"/resources/css/images/u338.png">获取新的验证码！</label></font>');
		   return false;  
	}
	$("#duanxincode").removeClass("item-focus");
	var result=false;
	result=$("#loginForm").validate().element($("#loginForm").find('input[name="mobile"]'));
	var mobile=$("#mobile").val();
	var duanxincode=$("#duanxincode").val();
	if(result&&duanxincode!=""){
		$.ajax({
			type : 'POST',
			url : CTX+"/user/isChenkString.ht",
			data : {
				mobile : mobile,
				verifycode : duanxincode,
				mobileRandom : $("#mobileRandom").val(),
				pageIsChenkCount : isChenkCount,
				mobileCodeValue:$("#mobileCodeValue").val(),
		        mobileNo:$("#mobileNo").val()
			},
			async:false,
			success : function(data) {
				if(data == 'ok'){
					$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
				}
				else if(data == 'fail'){
					$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode" class="error"><img src='+CTX+'"/resources/css/images/u338.png">输入错误,请重新获取短信信息</label></font>');
				}
				else{
					layer.alert(data,function(){
						 location.reload();
					 });
				}
			},
			error : function(data){
				layer.alert('验证码验证报错！');
				result=false;
			}
		});
		
		
	}
}
//查询企业号
function checkFocusCode(obj){
	$(obj).addClass("item-focus");
	$(obj).removeClass("error");
	if(invititedCodeLength>0){
		$(obj).parent("div").find("font").html('');
	}
}

function checkCode(){
	$("#invititedCode").removeClass("item-focus");
	var invititedCode=$("input[name='invititedCode']").val();
	if(invititedCode!=null&&invititedCode!=""){
		$.ajax({
			type : 'POST',
			url : CTX+"/user/checkOrgId.ht",
			dataType: "json",
			data : {invititedCode : invititedCode},
			success : function(data) {
				if(data.result=='1'){
					//如果存在
					var result=eval('(' + data.message+ ')');
					
					
					$("input[name='invititedCode']").parent("div").find("font").replaceWith('<font color="black"><img src="'+CTX+'/resources/css/images/u344.png">'+result[0].name+'</font>');
				}
				else{
					$("#invititedCode").addClass("error");
					$("input[name='invititedCode']").parent("div").find("font").replaceWith('<font color="red"><label for="invititedCode" class="error"><img src='+CTX+'"/resources/css/images/u338.png">您输入的公司不存在</label></font>');
				}
			},
			error : function(data){
				layer.alert("邀请码查询失败");
			}
		});
	}
	
}

function Login(){
	var systemId=$("#systemId").val();
	var user=$("#user").val();
	var returnUrl=window.location.href;
	if(user==null||user==''){
		
		var params=new Array();
		params.push({name:"systemId",value:systemId});
		params.push({name:"returnUrl",value:returnUrl});
		Post(CTX+"/user/doLogin.ht",params);
	}else{
		
		layer.alert("您已经登录，无需再次登录");
		return;
	}
	
	

	
	//window.location.href=CTX+"/user/doLogin.ht?systemId="+systemId+"&returnUrl="+returnUrl;
}
function Post(url,params){
	//创建一个form
	 var temp_form = document.createElement("form");
	 //指定form的跳转action 
	 temp_form.action=url;
	 temp_form.method = "post";
	 temp_form.style.display = "none";
	 //添加参数
	 for(var item in params){
		 //创建若干文本域
		 var opt = document.createElement("input");
		     opt.setAttribute("type", "text");
		     opt.name=params[item].name;
		     opt.value=params[item].value;
		     temp_form.appendChild(opt);
	 }
	 document.body.appendChild(temp_form);
	//提交数据
     temp_form.submit();
}

//js以post方式提交表单数据

//表单验证

window.onload=function(){
	var mobile=$("#mobile");
	var oTxtMobile=mobile[0];
	oTxtMobile.onfocus=function(){
		mobile.addClass("item-focus");
		if(mobileLength==0){
			mobile.parent("div").find("font").replaceWith('<font color="blue"><label for="mobile" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">不绑定任何收费，便于与客户联系</label></font>');
		}
		 if(mobileLength>0){
			 mobile.parent("div").find("font").replaceWith('<font color="gray"><label for="mobile" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">手机号必须以1开头11位数字</label></font>');
		}
	}
	oTxtMobile.onblur=function(){
		mobile.removeClass("item-focus");
		var fontHtml=mobile.parent("div").find("font").html();
		mobile.parent("div").find("font").replaceWith('<font color="red">'+fontHtml+'</font>');
		var result=false;
		result=$("#loginForm").validate().element($("#loginForm").find('input[name="mobile"]'));
		if(result){
	    	//验证通过
			mobile.parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
	    }
		
	}
	
	oTxtMobile.onkeydown=function(){
		//计算文本框的长度
		mobileLength=calLength(oTxtMobile);
	}
	oTxtMobile.onkeyup=function(){
		//计算文本框的长度
		mobileLength=calLength(oTxtMobile);
		mobile.parent("div").find("font")[0].color='red';
		$("#loginForm").validate().element($("#loginForm").find('input[name="mobile"]'));
		
	}
	
}
//计算文字长度
function calLength(obj){
	return  obj.value.length;
}