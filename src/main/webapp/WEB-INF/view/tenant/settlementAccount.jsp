<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<html>
<head>
<title>中金开户</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript">
var CTX="${pageContext.request.contextPath}";
</script>
	
	
<!-- 上传图片 -->
<script type="text/javascript" src="${ctx}/js/uc/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/uc/cloudDialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/uc/uploadPreview.js"></script>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
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
.enterprise_baseInfor_navLeft ul li a.current {
    background: #dfdfdf;
    color: #323232;
}
.fonts{
	font-size: 14px
}
.register_mainCon_btn{
	width:180px;
	height:40px;
	font-size:18px;
	cursor:pointer;
	background:#ff771d;
	color:#fff;
	border:none;
	line-height: 40px;
	text-align: center;
	border-radius:5px;
	-webkit-border-radius:5px;
	-ms-border-radius:5px;
	-moz-border-radius:5px;
	-o-border-radius:5px;
}
.register_mainCon_btn_h{
	width:180px;
	height:40px;
	font-size:14px;
	cursor:pointer;
	background:#888888;
	color:#fff;
	border:none;
	line-height: 40px;
	text-align: center;
	border-radius:5px;
	-webkit-border-radius:5px;
	-ms-border-radius:5px;
	-moz-border-radius:5px;
	-o-border-radius:5px;
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
a{
 color: #7babcf;
}
.appleSelect{
	  width: 366px;
	  height: 38px;
	  font-size:14px
	}
</style>
<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
var code ; //在全局定义验证码 
var codeLength = 4;//验证码的长度
var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
		 'S','T','U','V','W','X','Y','Z');//随机数
		var fasongcishu = 10;
		var duanxincodeLength=0;
	$(function() {
		$("#navtab1").ligerTab({
			dblClickToClose : false
		});
		var yc=$("#yc").val()
		var st=$("#infost").val();
		var ts="";var btn="";var href="";var fg="";
		if(st == '6'){
			ts="由于您的开户信息已修改，暂不能使用此功能，请耐心等待审核结果";
			btn="我知道了";
			
			href="http://b2b.casicloud.com/home.ht?menu=smrz";
			 //window.open("http://core.casicloud.com/home.ht?menu=smrz");//生产环境
			fg="实名认证"
		}else{
			ts="请先中金开户,再进行中金绑卡";
			btn="去开户";
			href="${ctx}/tenant/getBranchAccount.ht";
			fg="中金开户"
		}
		//未进行中金开户，或者未通过企业审核
	 	if(yc=='0'|| st != '5'){
			layer.open({
				  title: '提示信息',
				  closeBtn:0,
				  skin: 'layui-layer-molv',
				  shade:[0.6,'#000'],
				  content: ts,
				  shift:1, //0-6的动画形式，-1不开启
				  btn:[btn],
				  yes:function(index){
						//已绑定(冻结)	
						if(yc=='1'){
						   $("#sub").removeClass("register_mainCon_btn");
						   $("#sub").addClass("register_mainCon_btn_h");
					       window.open(href);//测试环境
						}else{
							  $(window.parent.document).find("#ulleft a").each(function(index){
									var str=this.innerHTML;
									if(str==fg)
									{
									   $(window.parent.document).find("#ulleft a").removeClass("current");
									   $(this).addClass("current");
									}						 
								 });
								window.location.href=href;
						}
					  layer.close(index);
					}				 
			});     
			return;
		}
		if(yc=='3'){
			//已绑定		
			$("#sub").val("已绑定"); //qxg
			$("#sub").attr("disabled","disabled");
			$("#qxg").show();
			// $("#banka").attr("readonly","readonly");
			$("#sub").removeClass("register_mainCon_btn");
			$("#sub").addClass("register_mainCon_btn_h");
		}				
	});
	var manager = null;
	$(function() {
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#infoForm').form();
		$("#sub").click(function() {
			firstClick(frm, options);
		});
		$("#sub").dblclick(function(){
			firstClick(frm, options);
		});
		$("#qxg").click(function() {
			firstClick(frm, options);
		});
		$("#qxg").dblclick(function() {
			firstClick(frm, options);
		});
		function firstClick(frm, options) {
			//去掉所有的html标记
			var xieyi=$("#xieyi");
			if(!xieyi.attr("checked")){
				layer.alert("请勾选协议!!");
				return;
			}

			var yzm = $("#duanxincode").val();
			if(yzm==null||yzm==""){
				layer.alert("请填写手机验证码!!");
				return;
			}
			var flag=false;
			flag=isVerifycodeTrue();
			if(!flag){
				layer.alert("请重新获取短信验证码！");
				$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode" class="error">验证码验证失败，请获取新的验证码！</label></font>');
				return false;
			}
			
			if (validForm()) {
				var code = $("#code").val();
				if($("#provCode").val()=='==请选择=='||$("#provCode").val()==""){
					layer.alert('请选择开户地所在省');
					return;
				}
				if($("#cityCode").val()=='==请选择=='||$("#cityCode").val()==""){
					layer.alert('请选择开户地所在城市');
					return;
				}
			  if($("#bwname option:selected").text())
			   $("#branchname").val($("#bwname option:selected").text());
				var bankfullname=$("#bankId").find("option:selected").text();
				$("input[name='bankfullname']").val(bankfullname);
				var province=$("#provCode").find("option:selected").text();
				$("#province").val(province);
				var city=$("#cityCode").find("option:selected").text();
				$("#city").val(city);
				$("#credentialsType").removeAttr("disabled");
				frm.setHtmlData();
				frm.ajaxForm(options);
				if (frm.valid()) {
					layer.msg('正在保存中,请稍候...', {
						icon : 16,
						time : 100000,
						shade : [ 0.5, '#000', true ]
					});
					frm.ajaxSubmit(function(data){
						if(data.code==1){
							layer.alert(data.msg);
							$("#banka").attr("readonly","readonly");
							$("#sub").val("已绑定");
							$("#sub").attr("disabled","disabled");
							$("#sub").removeClass("register_mainCon_btn")
							$("#sub").addClass("register_mainCon_btn_h")
						}else{
							layer.alert(data.msg);
						}
					})
					firstClick = secondClick;
				} else {
					layer.alert("信息填写不正确");
				}
			} else {
				layer.alert("请将必填信息填充完整再提交", {
					skin : 'layui-layer-molv' //样式类名
					,
					closeBtn : 0
				});
				return;
			}
		}
		function secondClick() {
			layer.alert("单据已经提交，请勿重复提交");
			return false;
		}
	});	
	
	//发送短信验证码
	function verify(){
		    var result = false;
			result=$("#infoForm").validate().element($("#infoForm").find('input[name="mobile"]'));
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
										createCode();
										$(".register1_imgCode").next().empty();
										var sendObj = $('input#verifyBtn');
										start_sms_button(sendObj);
										$("#mobileCount").show();
										$("#mobileCount").text(msg);
										$("#mobileCodeValue").val(dataMap.codeValue);
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
	
	
	//校验手机验证码
	function isVerifycodeTrue(){
		
		sms_tishi="";
		var result=true;
		var thisVal = $("#duanxincode").val();
		var mobile = $("#mobile").val();
		if($.trim($("#mobileCodeValue").val()).length==0){
			$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode" class="error">获取新的验证码！</label></font>');
			sms_tishi='请重新获取新的验证码';
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
	
	//检查是否为字母或者数字
	function checknum(value) {
		var Regx = /^[A-Za-z0-9]*$/;
		if (Regx.test(value)) {
			return true;
		} else {
			return false;
		}
	}

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		layer.closeAll();
		if (obj.isSuccess()) {
			
			if (obj.data.message.indexOf('变化') > 0) {
				layer.alert('由于您修改了企业认证信息，工作人员会在3-5个工作日内对您提供的最新信息进行审核。'
						+ '如果审核成功系统会保存您提供的最新信息，如果审核失败系统会还原您之前认证过的信息。'
						+ '如需要人工帮助请联系客服电话 010-88611981', {
					skin : 'layui-layer-molv' //样式类名
					,
					closeBtn : 0
				}, function() {

					window.location.reload();
				});
			} else {
				layer.alert(obj.getMessage(), {
					skin : 'layui-layer-molv' //样式类名
					,
					closeBtn : 0
				}, function() {
					window.location.reload();
				});
			}
		} else {
			layer.alert('开户信息保存成功 ,' + obj.getMessage(), {
				icon : 2,
				skin : 'layui-layer-molv',
				closeBtn : 0
			},function(){
				
				window.location.reload();
			});
		}
	}

	function validForm() {
		var flag = true;
		$(".row em").parent().next().find("input").each(
				function() {
					var thisVal = $(this).val();
					var names = $(this).attr('name');
					if (names != "info" && typeof (names) != "undefined"
							&& names != "logo" && names != "yyzzPic"
							&& names != "frPic") {
						if (thisVal == "" || thisVal == "必填") {
							flag = false;
							$(this).after(
									$("<label class='error'>该项为必填项</label>"));
							$(this).next().next().remove();
						}
					}
				});
		return flag;
	}
	$(window.parent.document).find("#mainiframe").load(function() {

		var mainheight = 800;
		$(this).height(mainheight);
	});

	var flg=false;
	$(function(){
		var va=$("#bankId").find("option:selected")[0];
		   bankChange(va);
		
	});
 	var bankChange=function(obj){
		var branchName=$("#branchname").val();
	 $.ajax({
			type : 'post',
			dataType : 'json',
			url : '${ctx}/user/getBankId.ht',
			data : {
				value:obj.value		
			},
			success:function(data){
				var rows = data;
				var opt = '';
				$('#bwname').html('');
				if(rows.length==0){
					$("#bwname").attr("class='register_mainCon_tit openAccount_titie_width'")
					$("#bwname").append('<option ' + s + ' value="'+0+'">'
							 +'请选择其他银行'+'</option>')
							return;
				}								
				for (var i = 0; i < rows.length; i++) {
					var s = '';
					var iv = rows[i].itemName + '';
				 	if (iv == branchName)
						s = "selected='selected'";
					$('#bwname').append(
							'<option ' + s + ' value="' +  rows[i].itemValue + '">'
									+ rows[i].itemName + '</option>');
					}
				}
			})
 }
	var change=function(obj){
		//根据编号查询省份的Id
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : '${ctx}/tenant/getProvinceId.ht',
			data : {
				value : obj.value
			},
			success : function(dics) {
				var rows = dics;
				$('#cityCode').html('');
				var opt = '';
				for (var i = 0; i < rows.length; i++) {
					var s = '';
					var iv = rows[i].itemValue + '';
				/* 	if (iv == ind3)
						s = "selected='selected'"; */
					$('#cityCode').append(
							'<option ' + s + ' value="' +  rows[i].itemValue + '">'
									+ rows[i].itemName + '</option>');
				}
				changeCity();
			}
		});
	}	
	function checkFocusVerifyCode(obj){
		$(obj).addClass("item-focus");
		if(duanxincodeLength>0){
			$(obj).parent("div").find("font").html('');
		}
	}
	/* function checkVerifycode(){
		$("#duanxincode").removeClass("item-focus");
		var result=false;
		result=$("#infoForm").validate().element($("#infoForm").find('input[name="mobile"]'));
		var mobile=$("#mobile").val();
		var duanxincode=$("#duanxincode").val();
		if(result&&duanxincode!=""){
			$.ajax({
				type : 'POST',
				url : CTX+"/user/isChenk.ht",
				data : {
					mobile : mobile,
					verifycode : duanxincode
				},
				async:false,
				success : function(data) {
					if(data == 'ok'){
						$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><img src="'+CTX+'/resources/css/images/u344.png"></font>');
					}
					else{
						$("#duanxincode").parent("div").find("font").replaceWith('<font color="red"><label for="verifycode" class="error">验证码输入错误，请重新获取</label></font>');
					}
				},
				error : function(data){
					layer.alert('验证码验证报错！');
					result=false;
				}
			});
			
			
		}
	} */
	
	
	
	function changeCity(){
		var cityIdValue = $("#cityCode").val();
		var bankIdValue = $("#bankId").val();
		var branchName=$("#branchname").val();
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : '${ctx}/user/getCityIdBankId.ht',
			data : {
				cityId:cityIdValue,	
				bankId:bankIdValue	
			},
			success:function(data){
				var rows = data;
				var opt = '';
				$('#bwname').html('');
				if(rows.length==0){
					$("#bwname").attr("class='register_mainCon_tit openAccount_titie_width'")
					$("#bwname").append('<option ' + s + ' value="'+0+'">'
							 +'请选择其他银行'+'</option>')
							return;
				}								
				for (var i = 0; i < rows.length; i++) {
					var s = '';
					var iv = rows[i].itemName + '';
				 	if (iv == branchName)
						s = "selected='selected'";
					$('#bwname').append(
							'<option ' + s + ' value="' +  rows[i].itemValue + '">'
									+ rows[i].itemName + '</option>');
					}
				}
			})
		
	}
</script>
</head>
<body>
<div class="tipBox">
<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>
 <p><span>中金绑卡主要是针对卖家能够正常收到款项（买家用户可以不填）。</span></p>
</div>
	<form id="infoForm" method="post" action="saveSettlementAccount.ht"
		class="signupForm2">
		<div id="wizard" type="main">
		   <input type="hidden" name="credentialsType" value="A">
		   <input type="hidden" name="credentialsNumber" value="${branch.credentialsNumber}">
			<input type="hidden" name="orgid" id="orgid"
				value="${branch.orgid}" /> <input type="hidden" name="sysid"
				id="sysid" value="uc" />
				<input type="hidden" name="id" value="${branch.id}">
				<input type="hidden" id="infost" value="${info.state }">
				
			<div class="register_mainCon_list">
				<span class="register_mainCon_tit openAccount_titie_width fonts">手机号码:</span> <input type="text"
					class="register_mainCon_con fonts" maxlength="20" 
					validate="{required:true}" class="register_mainCon_con fonts"
					value="${info.tel}" id="mobile" name="mobile"  readonly="readonly" style="border: 0"/>
				<div class="tipinfo"></div>
			</div>	
			
				
			<div class="register_mainCon_list">
				 <span class="register_mainCon_tit openAccount_titie_width fonts">手机验证码:</span>
				    <input type="text" class="register1_list_right" placeholder="请输入手机验证码" maxlength="4"  id="duanxincode" name="verifycode"  onfocus="checkFocusVerifyCode(this);"/>
                        <input type="button" class="register1_sendCode" value="发送验证码" onclick="verify();" id="verifyBtn"/>
                        <font color="red"></font> 
			</div>	
			<div style="padding: 5px 0 5px 125px;display:none;margin-top: -17px;" id="mobileCount">
                   </div>
				
			<div class="register_mainCon_list">
				<span class="register_mainCon_tit openAccount_titie_width fonts"><span
					class="register2_red">*</span>公司名称:</span> <input type="text"
					class="register_mainCon_con fonts" maxlength="20" 
					validate="{required:true}" class="register_mainCon_con fonts"
					value="${info.name}" readonly="readonly" style="border: 0"/>
				<div class="tipinfo"></div>
			</div>
			<div class="register_mainCon_list">
				<span class="register_mainCon_tit openAccount_titie_width fonts"><span
					class="register2_red">*</span>开户名称:</span> <input type="text"
					class="register_mainCon_con fonts" maxlength="20" name="branchaccountname"
					validate="{required:true}" class="register_mainCon_con fonts"
					value="${info.name}" readonly="readonly" style="border: 0"/>
				<div class="tipinfo"></div>
			</div>					
			<div class="register_mainCon_list">
				<span class="register_mainCon_tit openAccount_titie_width fonts"><span
					class="register2_red">*</span>银行账号</span>&nbsp;<input type="text" id="banka"
					name="bankaccount" class="register_mainCon_con fonts" maxlength="20"
					class="register_mainCon_con"
					value="${branch.bankaccount}" />
				<div class="tipinfo"></div>
			</div>
			<div class="register_mainCon_list">
				<span class="register_mainCon_tit openAccount_titie_width fonts"><span
					class="register2_red">*</span>开户银行</span>
				<ap:selectDB name="bankId" id="bankId" where="1=1"
					optionValue="UBANKID" optionText="BANKNAME" table="BF_ZD_SUPPBANK" onChange="bankChange(this)"
					selectedText="${branch.bankfullname}">
				</ap:selectDB>
				<input name="bankfullname" type="hidden" id="bankfullname" value="${branch.bankfullname}">
				<div class="tipinfo"></div>
			</div>		
			<input type="hidden" name="accountType" value="1">
						<div id="city_1" class="register_mainCon_list openAccount_city fonts">
				<span class="register_mainCon_tit openAccount_titie_width"><span
					class="register2_red">*</span>开户行所在地</span>
				<!--  <select class="prov " ></select> 
					                        <select class="city " disabled="disabled"></select> -->	
				<ap:selectDB name="provCode" id="provCode" defaultText="==请选择=="
					defaultValue="" where="1=1" optionValue="PROVCODE"
					optionText="PROVNAME" table="BF_ZD_PROVINCE" onChange="change(this)"
					 selectedText="${branch.province}">
				</ap:selectDB>
				<%-- <ap:ajaxSelect srcElement="PROVCODE" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="CITYCODE"/>
				<ap:selectDB name="CITYCODE" id="CITYCODE" where="PROVID="+$('#PROVCODE').val()
					optionValue="CITYCODE" optionText="CITYNAME" table="BF_ZD_CITY"
					selectedValue="${branch.city}">
				</ap:selectDB> --%>
				<select id="cityCode" name="cityCode" class="appleSelect fonts" onChange="changeCity()" >
				    <c:choose>
				    <c:when test="${not empty branch.province and not empty branch.city}">
				        <option value="${branch.cityCode}">${branch.city}</option>
				    </c:when>
	                 <c:otherwise>
	                   <option value="">==请选择==</option>
	                 </c:otherwise>
	                 </c:choose>
				</select>
				<input id="province" name="province" type="hidden" value="${branch.province}">
				<input id="city" name="city" type="hidden" value="${branch.city}">
				<div class="tipinfo"></div>
			</div>
		<!--  <div class="register_mainCon_list">
				<span class="register_mainCon_tit openAccount_titie_width fonts"><span
					class="register2_red">*</span>银行支行名称</span>
				<select id="bwname"  class="appleSelect fonts" style="display:inline-block;width:365px;height:40px;line-height: 40px;padding-left:8px;">
				   
				</select>
				<input type="hidden"  name="branchname" id="branchname" value="${branch.branchname}">
				<div class="tipinfo"></div>
			</div>-->	
          
            <div class="register_mainCon_list">
				<span class="register_mainCon_tit openAccount_titie_width"><span
					class="register2_red">*</span>银行支行名称</span>
					<select id="bwname" name="openbkcd"  class="appleSelect fonts" style="display:inline-block;width:365px;height:40px;line-height: 40px;padding-left:8px;">
				   
				    </select>
				<%-- <ap:selectDB name="openbkcd" id="openbkcd" where="1=1"
					optionValue="FQHHO2" optionText="FKHMC1" table="BF_ZD_PAYBANK" 
					selectedText="${branch.branchname}">
				</ap:selectDB> --%>
				<input type="hidden" id="openbkcd" value="${branch.openbkcd}">
				<input type="hidden" name="branchname" id="branchname" value="${branch.branchname}">
				<div class="tipinfo"></div>
			</div>
			<c:choose>
			    <c:when test="${branch.stlstate=='1' or branch.stlstate == '3' or branch.stlstate=='4'}">
			        <input type="hidden" name="fcflg" value="2">
			    </c:when>
			    <c:otherwise>
			        <input type="hidden" name="fcflg" value="1">
			    </c:otherwise>
			</c:choose>
			
			 <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width fonts"><span class="register2_red"></span></span>
							<input type="radio" id="xieyi" class="radio fonts"   />同意中金支付协议&nbsp;&nbsp;<a href="${ctx}/user/zjpaymentxieyi.ht
" target="_blank">查看中金支付协议</a>
                        <div class="tipinfo"></div>
             </div>      
			<div class="register_mainCon_list authentication_btn_mar">
				<span class="register_mainCon_tit openAccount_titie_width"></span> 
			       <input type="button" class="register_mainCon_btn fonts" value="去绑定" id="sub" ${(branch.stlstate eq '1' or branch.stlstate eq '3')?'disabled':''}/>				
				   <input type="button"class="register_mainCon_btn fonts" value="去修改" style="display: none" id="qxg"></input>			 
				<div class="tipinfo"></div>
			</div>

		</div>	
		<input type="hidden" id="yc" value="${flag }"/>
		<input type="hidden" id="mobileCodeValue" name="mobileCodeValue" />
		<c:if test="${not empty branch }">	
		<input id="accstate" value="${branch.accstate}" type="hidden">
		<input name="stlstate" value="${branch.stlstate}" type="hidden">
		</c:if>
		<input name="flag" value="1" type="hidden">
	</form>
	
</body>
</html>
