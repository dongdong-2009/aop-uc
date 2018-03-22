jQuery.validator.addMethod("mobile", function (value, element) {
	    var mobile = /^1[3|4|5|7|8]\d{9}$/;
		return this.optional(element) || (mobile.test(value));
}, "请输入正确的手机号");
jQuery.validator.addMethod("email", function (value, element) {
	var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return this.optional(element) || (email.test(value));
}, "请输入合法的邮箱地址");
$(function(){
	
	validate("invititedForm");
});
function validate(id){
	var form = $("#" + id);
	form.validate({
		onfocusout:function(eles){
			$(eles).valid();
		},
		rules: {
			mobile:{
				required: true,
				mobile:true
			},
			email:{
				required: true,
				email : true
			}
		
		
        },
        messages:{
        	mobile:{
        		required: "请输入手机号"
        	},
        	email:{
				required: "请输入邮箱地址"
			}
			
        },
        debug: true,
        errorPlacement: function(error, element){
        	var placement = $(element.parent("td").find("font"));
        	error.appendTo( placement ); 
        }
	});
	
}

//验证图片验证码
function checkBlurVerify(){
	var verifycode_img=$("#verifycode_img").val();
	result=$("#invititedForm").validate().element($("#invititedForm").find('input[name="verifycode_img"]'));
	if(result){
		if((verifycode_img.toUpperCase())==code){
			$("#verifycode_img").parent("td").parent("tr").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
		}else
		   {
			$("#verifycode_img").parent("td").parent("tr").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u338.png">输入错误</font>');
		}
		
	}
}
function checkFocusVerify(obj){
	if(verifycode_imgLength>0){
		$(obj).parent("div").find("font").html('');
	}
}
var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
		 'S','T','U','V','W','X','Y','Z');//随机数
		 var verifycode_imgLength=0;
		 
			$(function() {
				//产生图片验证码
				$("#verifycode_img").val('');
					code='';
				         codeLength=4;
						 for(var i = 0; i < codeLength; i++) {//循环操作
							var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
						
							code += random[index];//根据索引取得随机数加到code上
						}
					$("#code").val(code);
					
					$('#verifycode_img').bind('input propertychange', function() {
						verifycode_imgLength=$("#verifycode_img").val().length;
					});
			
			
				$("a.save").click(function() {
					var result=false;
					result=$("#invititedForm").valid();
					var verifycode_img=$("#verifycode_img").val();
					if((verifycode_img.toUpperCase())!=code){
						layer.alert("验证码错误");
						return;
					}
					if(result){
						$.ajax({
							type: "post",
							dataType:"json",
							async:false,
							url:CTX+"/invitited/welcome.ht",
							data:$("#invititedForm").serialize(),
							success:function(responseText){
								layer.alert(responseText,function(){
									window.location.reload();
								});
							}
						});
						
					}else{
						layer.alert("请完善信息",{
						    skin: 'layui-layer-molv' //样式类名
						        ,closeBtn: 0
						    });
					}
					
				});
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
