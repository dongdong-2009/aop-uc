//用户名
	jQuery.validator.addMethod("account", function (value, element) {
		var account = /^[0-9a-zA-Z_]{1,}$/;
		return this.optional(element) || (account.test(value));
	}, "用户名格式不对");

//密码
	jQuery.validator.addMethod("pass", function (value, element) {
		var pass = /^[A-Za-z0-9]+$/;
		return this.optional(element) || (pass.test(value));
	}, "密码格式不对");
//公司名称
	jQuery.validator.addMethod("comName", function (value, element) {
		var comName = /^[A-Za-z0-9\s\u4e00-\u9fa5\(\)\（\）]+$/;
		//  /^[A-Za-z0-9]+$/
		//  /^[A-Za-z0-9\u4e00-\u9fa5\s\(\)\（\）]+$/
		return this.optional(element) || (comName.test(value));
	}, "公司名称格式不正确");
	
var accountLength=0;
var passwordLength=0;
var repetpasswordLength=0;
var nameLength=0;
var connecterLength=0;
var timeoutid = null; 
var clickCount = 0; 
$(function(){
	
	validate("loginForm");
	

	//为公司名框绑定触发事件
	$('#connecter').bind('input propertychange', function() {
		connecterLength=$("#connecter").val().length;
	});

	$("#fullname").focus(function(){
		$(this).addClass("item-focus");
		 if($(this).parent("div").find("font > img")){
	    		$(this).parent("div").find("font > img").remove();
	    	}
	});
	$("#fullname").blur(function(){
		$(this).removeClass("item-focus");
		 var result=false;
  		 result=$("#loginForm").validate().element($("#loginForm").find('input[name="fullname"]'));
  		 if(result){
  	    	//验证通过
  			$(this).parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
  	     }else{
  	    	 if($(this).parent("div").find("font > img")){
  	    		$(this).parent("div").find("font > img").remove();
  	    	 }
  	     }
	})
});
var checkAccountRepeat=0;
function validate(id){
	
	$("#account").on('blur',function (){
		//console.info(this);
/*		var value1=this.value;
		var regex = new RegExp("^.{4}$");
	           //验证有4位的数字
	    var flag = regex.test($.trim(value1));*/
		var account = $("#account").attr("value");
		if(account == null || account == ''){
			return false;
		}
		$.ajax({
			url: CTX + '/user/checkAccountRepeatString.ht',
			type: "post",
			dataType: "json",
			data: { 
				"mobile":$("#mobile").val(),
			    "AccountRandom":$("#AccountRandom").val()
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
	
	var form = $("#" + id);
	form.validate({
		onfocusout:function(eles){
			$(eles).valid();
		},
		rules: {
			fullname:{
				required: true,
				rangelength:[2,30]

			},
			account:{
				required: true,
				account:true,
				rangelength:[4,30],
				remote:  {
					url: CTX + '/user/checkAccountRepeat.ht',
					type: "post",
					dataType: "json",
					async : false ,
					data: { 
						"account":function(){
					          return $("#account").val();
					          },
					  "AccountRandom":$("#AccountRandom").val()
				         
					}
				}
			},
			password:{
				required: true,
				pass:true,
				rangelength:[6,16]
				
			},
			repeatpassword:{
				required: true,
				pass:true,
				equalTo:"#password"
				
			},
			name:{
				required: true,
				comName:true,
				rangelength:[4,30],
				remote:  {
					url: CTX + '/user/checkOrgNameRepeat.ht',
					type: "post",
					dataType: "json",
					async : false ,
					data: { 
						name:function(){
					          return $("#name").val();
					          }   
					}
				}
			},
			connecter:{
				required: true,
				comName:true,
				rangelength:[2,8]
			}
			
			
        },
        messages:{
        	fullname:{
        		required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入昵称',
        		rangelength:'<img src="'+CTX+'/resources/css/images/u338.png"/>用户姓名的长度必须在2到30位之间'
        	},
        	account:{
        		required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入会员用户名',
        		account:'<img src="'+CTX+'/resources/css/images/u338.png"/>会员名格式不正确，请重新输入',
        		rangelength:'<img src="'+CTX+'/resources/css/images/u338.png"/>会员用户名的长度必须在4到30位之间',
        		remote:'<img src="'+CTX+'/resources/css/images/u338.png"/>会员名已经存在,你可以点此<a href="javascript:Login();">登录</a>'
        	},
        	password:{
				required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入密码',
				pass:'<img src="'+CTX+'/resources/css/images/u338.png"/>密码格式不正确',
				rangelength:"请输入一个6到16之间的字符串"
			},
			repeatpassword:{
				required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入密码',
				pass:'<img src="'+CTX+'/resources/css/images/u338.png"/>密码格式不正确',
				equalTo:"请再次输入相同的值"
			},
			name:{
				required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入公司名称',
				comName:'<img src="'+CTX+'/resources/css/images/u338.png"/>公司名称格式不正确',
				rangelength:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入字符量在4到30之间',
				remote:"该企业名称已存在,请更换"
			},
			connecter:{
				required:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入联系人',
				comName:'<img src="'+CTX+'/resources/css/images/u338.png"/>公司名称格式不正确',
				rangelength:'<img src="'+CTX+'/resources/css/images/u338.png"/>请输入一个字符串长度在2到8位之间'
			}
        },
        debug: true,
        onkeyup:null,
        errorPlacement: function(error, element){
        	var placement = $(element.parent("div").find("font"));
        	error.appendTo( placement ); 
        }
	});
	
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

function change2person(){
	var systemId=$("#systemId").val();
	var returnUrl=$("#returnUrl").val();
	if(systemId != null && systemId != ""){
		window.location.href="reg_personal_cloud.ht?systemId="+systemId+"&returnUrl="+returnUrl;
	}else{
		window.location.href="reg_personal_cloud.ht"
	}
}

function save(){
	clickCount++;
	if(clickCount==3){
		layer.alert("请刷新再试",function(){
			location.reload();
		});
		return;
	}
	var systemId=$("#systemId").val();
	var serviceUrl=$("#serviceUrl").val();
	var reuslt=false;
	result=$("#loginForm").valid();
	var check_box=$("#check_box").is(":checked");
	if(!check_box){
		layer.alert("请勾选协议");
		return ;
	}
	if(result){
		$("#btn").attr("disabled","disabled").css("background","#dbdbdb");
		var account=$("#account").val();
		var user=$("#user").val();
		var mobile=$("input[name='mobile']")[0].value;
		var password=$("input[name='password']")[0].value;
		var arrValue=new Array();
		arrValue.push(account+":"+mobile+":"+password);
		$("#relMes").val(JSON.stringify(arrValue));
		$.ajax({
			type : 'POST',
			url : CTX+"/user/saveUserAndOrgInfo.ht",
			dataType: "json",
			data : $("#loginForm").serialize(),
			success : function(data) {
				if(data.result=='1'){
					var params=new Array();
					params.push({name:"systemId",value:systemId});
					params.push({name:"account",value:account}),
					params.push({name:"serviceUrl",value:serviceUrl}),
					Post(CTX+"/user/regSuccess.ht",params);
				}
				else{
					layer.alert("用户注册失败");
					window.location.reload();
				}
			},
			error : function(data){
				layer.alert("网络问题，注册失败");
				window.location.reload();
			}
		});
		
		
		
	}
	
}

// 保存个人用户的是注册
function persave(){
	var systemId=$("#systemId").val();
	var returnUrl=$("#returnUrl").val();
	var serviceUrl=$("#serviceUrl").val();
	var reuslt=false;
	result=$("#loginForm").valid();
	var check_box=$("#check_box").is(":checked");
	if(!check_box){
		layer.alert("请勾选协议");
		return ;
	}
	if(result){
		$("#btn").attr("disabled","disabled").css("background","#dbdbdb");
		var account=$("#account").val();
		var mobile=$("input[name='mobile']")[0].value;
		var password=$("input[name='password']")[0].value;
		
		$.ajax({
			type : 'POST',
			url : CTX+"/user/reg_personal_saveuser.ht",
			dataType: "json",
			data : $("#loginForm").serialize(),
			success : function(data) {
				if(data.result=='1'){
					var params=new Array();
					params.push({name:"systemId",value:systemId});
					params.push({name:"account",value:account});
					params.push({name:"returnUrl",value:returnUrl});
					params.push({name:"serviceUrl",value:serviceUrl});
					Post(CTX+"/user/perregSuccess.ht",params);
				}
				else{
					layer.alert("用户注册失败");
					window.location.reload();
				}
			},
			error : function(data){
				layer.alert("用户注册失败");
				window.location.reload();
			}
		});		
	}	
}



//为公司名添加验证
function checkFocusName(obj){
	//alert(nameLength)
	if(nameLength==0){
		$(obj).parent("div").find("font").replaceWith('<font color="blue"><label for="name" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">请输入公司名称</label></font>');
	}
	if(nameLength>0){
		$(obj).parent("div").find("font").replaceWith('<font color="blue"><label for="name" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">公司名称长度在4-30之间</label></font>');
	}
}
function addStyle(obj){
	$(obj).addClass("item-focus");
}
function checkBlurName(){
	$(obj).removeClass("item-focus");
	setTimeout(clear,500);
	var fontHtml=$("#name").parent("div").find("font").html();
	$("#name").parent("div").find("font").replaceWith('<font color="red">'+fontHtml+'</font>');
	var result=false;
	result=$("#loginForm").validate().element($("#loginForm").find('input[id="name"]'));
	if(result){
		//验证通过
		$("#name").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
	}
}
function changeStyle(obj){
	if(nameLength==0){
	  clear();
	 }
	 $(obj).parent("div").find("font")[0].color='red';
	 $("#loginForm").validate().element($("#loginForm").find('input[id="name"]'));
}
function appendSome(obj){
	var nameVlaue=$("#name").val();
	if(!(nameVlaue.indexOf("有限公司")>-1)&&!(nameVlaue.indexOf("个体经营")>-1)&&!(nameVlaue.indexOf("集团")>-1)&&!(nameVlaue.indexOf("有限责任公司")>-1))
	{
		if(nameVlaue!=''&&nameVlaue!=null)
			timeoutid=setTimeout(append(obj,nameVlaue.length),500);
	}
	
}
//为联系人添加验证
function checkFocusConnctor(obj){
	$(obj).addClass("item-focus");
	if(connecterLength==0){
		$(obj).parent("div").find("font").replaceWith('<font color="blue"><label for="connecter" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">请输入联系人</label></font>');
	}
	if(connecterLength>0){
		$(obj).parent("div").find("font").html('');
	}
	
}
function checkBlurConnector(){
	$("#connecter").removeClass("item-focus");
	var fontHtml=$("#connecter").parent("div").find("font").html();
	$("#connecter").parent("div").find("font").replaceWith('<font color="red">'+fontHtml+'</font>');
	var result=false;
	result=$("#loginForm").validate().element($("#loginForm").find('input[name="connecter"]'));
	if(result){
		//验证通过
		$("#connecter").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
	}
	
}


function Login(){
	var systemId=$("#systemId").val();
	if(systemId=="null"){
		systemId="100";
	}
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
var append=function(obj,nameLength){
	//找到ul标签
	var content='';
	content+='<li data-val="'+obj.value+'" onclick="resetValue(this)">'+obj.value+'有限公司</li>';
	content+='<li data-val="'+obj.value+'" onclick="resetValue(this)">'+obj.value+'个体经营</li>';
	content+='<li data-val="'+obj.value+'" onclick="resetValue(this)">'+obj.value+'集团</li>';
	content+='<li data-val="'+obj.value+'" onclick="resetValue(this)">'+obj.value+'有限责任公司</li>';
	var $div=$("div.selbox");
	if(nameLength>0){
	 $div.find("ul").empty();
	 $div.show();
	 $div.find("ul").append(content);
	}else{
	 $div.find("ul").empty();
	 $div.hide();
	}
	
		
}
var clear=function(){
	var $div=$("div.selbox");
	  $div.find("ul").empty();
	  $div.hide();
}
var resetValue=function(obj){
	$("#name").val(obj.innerText);
	clearTimeout(timeoutid);
	clear();
	if(obj.value){
	  nameLength=obj.value.replace(/[^\x00-\xff]/g, "**").length;
	}
	$("#name").focus();
}

//为公司名添加验证
function checkFocusName(obj){
	//alert(nameLength)
	if(nameLength==0){
		$(obj).parent("div").find("font").replaceWith('<font color="blue"><label for="name" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">请输入公司名称</label></font>');
	}
	if(nameLength>0){
		$(obj).parent("div").find("font").replaceWith('<font color="blue"><label for="name" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">公司名称长度在4-30之间</label></font>');
	}
}
function checkBlurName(){
	setTimeout(clear,500);
	var fontHtml=$("#name").parent("div").find("font").html();
	$("#name").parent("div").find("font").replaceWith('<font color="red">'+fontHtml+'</font>');
	var result=false;
	result=$("#loginForm").validate().element($("#loginForm").find('input[id="name"]'));
	if(result){
		//验证通过
		$("#name").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
	}
}

function check(obj){
	if(obj.value!=null){
	  nameLength=obj.value.replace(/[^\x00-\xff]/g, "**").length;
	}
	
	
}
//校验会员用户名
window.onload=function(){
	var account=$("#account");
	var oTxtAccount=account[0];
	var password=$("#password");
	var oTxtPassword=password[0];
	var repeatpassword=$("#repeatpassword");
	var oTxtRepeatpassword=repeatpassword[0];
	var name=$("#name");
	var oTxtName=name[0];
	oTxtAccount.onfocus=function(){
		account.addClass("item-focus");
		if(accountLength==0){
			account.parent("div").find("font").replaceWith('<font color="blue"><label for="account" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">请输入会员用户名</label></font>');
		}
		if(accountLength>0){
			account.parent("div").find("font").replaceWith('<font color="blue"><label for="account" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">4-30位字符,仅支持字母,数字,下划线组合</label></font>');
		}	
	}
	oTxtAccount.onblur=function(){
		account.removeClass("item-focus");
		var fontHtml=$("#account").parent("div").find("font").html();
		account.parent("div").find("font").replaceWith('<font color="red">'+fontHtml+'</font>');
		var result=false;
		result=$("#loginForm").validate().element($("#loginForm").find('input[name="account"]'));
		if(result){
	    	//验证通过
			 var reg_number = /^[0-9]+$/; // 判断是否为数字的正则表达式
			 if(reg_number.test(oTxtAccount.value)){
				 account.addClass("error");
				 account.parent("div").find("font").replaceWith('<font color="red">会员用户名不能设置为纯数字格式,请您更换</font>');
			 }else{
				 account.removeClass("error");
	    	     $("#account").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
			 }
	    }
	}
	oTxtAccount.onkeydown=function(){
		//计算文本框的长度
		accountLength=calLength(oTxtAccount);
	}
	oTxtAccount.onkeyup=function(){
		//计算文本框的长度
		accountLength=calLength(oTxtAccount);
		account.parent("div").find("font")[0].color='red';
		$("#loginForm").validate().element($("#loginForm").find('input[name="account"]'));
		
	}
	oTxtPassword.onfocus=function(){
		password.addClass("item-focus");
		if(passwordLength==0){
			password.parent("div").find("font").replaceWith('<font color="blue"><label for="password" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">请设置登录密码</label></font>');
		}
		if(passwordLength>0){
			password.parent("div").find("font").html('');
		}
		
	}
	oTxtPassword.onblur=function(){
		password.removeClass("item-focus");
		var fontHtml=$("#password").parent("div").find("font").html();
		password.parent("div").find("font").replaceWith('<font color="red">'+fontHtml+'</font>');
		var result=false;
		result=$("#loginForm").validate().element($("#loginForm").find('input[name="password"]'));
		if(result){
	    	//验证通过
			
	    	$("#password").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
	    }
	}
	oTxtPassword.onkeyup=function(){
		//计算文本框的长度
		passwordLength=calLength(oTxtPassword);
		password.parent("div").find("font")[0].color='red';
  		$("#loginForm").validate().element($("#loginForm").find('input[name="password"]'));
	}
      oTxtPassword.onkeydown=function(){
    	//计算文本框的长度
    	 passwordLength=calLength(oTxtPassword);
  		
	}
      oTxtRepeatpassword.onfocus=function(){
    	  repeatpassword.addClass("item-focus");
    	  if(repetpasswordLength==0){
    		  repeatpassword.parent("div").find("font").replaceWith('<font color="blue"><label for="repeatpassword" class="error"><img src='+CTX+'"/resources/css/images/changeP_img03.png">请设置登录密码</label></font>');
  		}
  		if(repetpasswordLength>0){
  			repeatpassword.parent("div").find("font").html('');
  		}
      }
      oTxtRepeatpassword.onblur=function(){
    	  repeatpassword.removeClass("item-focus");
    	  var result=false;
  		 result=$("#loginForm").validate().element($("#loginForm").find('input[name="repeatpassword"]'));
  		
  		 if(result){
  	    	//验证通过
  			repeatpassword.parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
  	     }else{
  	    	repeatpassword.parent("div").find("font")[0].color='red';
  	     }
    	  
      }
      oTxtRepeatpassword.onkeyup=function(){
  		//计算文本框的长度
    	 repetpasswordLength=calLength(oTxtRepeatpassword);
    	 repeatpassword.parent("div").find("font")[0].color='red';
    		$("#loginForm").validate().element($("#loginForm").find('input[name="repeatpassword"]'));
  	}
      oTxtRepeatpassword.onkeydown=function(){
      	//计算文本框的长度
    	  repetpasswordLength=calLength(oTxtRepeatpassword);
    		
  	}
      
     
      
      
}
//计算文字长度
function calLength(obj){
	return  obj.value.length;
}