<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<html>
<head>
	<title>审核企业信息</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/Validform_v5.3.1.js"></script>
		
	<!--[if lt IE 9]>
	<script type="text/javascript" src="${ctx}/pages/cloud3.0/js/cloud/json2.js"></script>
	<![endif]-->
	 
	<!-- tablegird -->
	<%-- <link href="${ctx}/styles/uc/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/style.css" rel="stylesheet" type="text/css" /> --%>
		<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/scrollable.js"></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	
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


</style>


<script>
$(function(){
	$('#industry').change(function(){
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : '${ctx}/tenant/getCascadeJsonData.ht',
			data : {value : $(this).val() },
			success : function(dics){
				var rows=dics;$('#industry2').html('');
				for(var i=0; i<rows.length; i++){
					var s = '';$('#industry2').append('<option ' + s + 'value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					}
				$('#industry2').trigger('change');
				}
			});
		});
	})
</script>


	<script type="text/javascript">
	
		$(function() {
			var sysOrgInfoId=$("#sysOrgInfoId").val();
			$("#sysId").click(function(){
				if($(this).text()=='显示'){
					$(this).prev("span").text(sysOrgInfoId);
					$(this).text('隐藏');
				}else{
					$(this).prev("span").html("******");
					$(this).text('显示');
				}
			});
			
			var index =  parent.layer.getFrameIndex(window.name);
			parent.layer.iframeAuto(index);
			
			var cks = $("#ck_mod").val();
			var arrs = new Array();
			arrs = cks.split(",");
			
			for(var i = 0;i<arrs.length;i++){
				if(arrs[i] == 1 ){
					$("#model1").attr("checked", true);
				}else if(arrs[i] == 2 ){
					$("#model2").attr("checked", true);
				}else if(arrs[i] == 3){
					$("#model3").attr("checked", true);
				}else if(arrs[i] == 4){
					$("#model4").attr("checked", true);
				}else{
					$("#model0").attr("checked", true);
				}
				
				
			}
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#infoForm').form();
			
			/* $(".btn_nav #sub").click(function(){
	          firstClick(frm);
	        });
	        $(".btn_nav #sub").dblclick(function(){
	          firstClick(frm);
	        }); */
	        
	        $("#sub").click(function(){
		          firstClick(frm);
		        });
		        $("#sub").dblclick(function(){
		          firstClick(frm);
		        });
	        function firstClick(frm){
				//去掉所有的html标记
				if(validForm()){
					frm.setHtmlData();
					frm.ajaxForm(options);
					if(frm.valid()){
						layer.msg('正在提交请稍候。。。', {icon: 26,time: 100000,shade : [0.5 , '#000' , true]});
						form.submit();
						firstClick = secondClick;
					}else{
						layer.alert('输入信息错误，请检查输入正确后再提交', {
						    icon: 2,
						    skin: 'layer-ext-moon' 
						});
						return;
					}
				}else{
					layer.alert('请将必填信息填充完整再提交', {
					    icon: 2,
					    skin: 'layer-ext-moon' 
					});
					return;
				}
	        }
			function secondClick(){
				layer.alert('单据已经提交，请勿重复提交', {
				    icon: 2,
				    skin: 'layer-ext-moon' 
				});
				return false;
			}
			
			function showResponse(responseText) {
				var obj = new com.hotent.form.ResultMessage(responseText);
				layer.closeAll();
				if (obj.isSuccess()) {
						layer.alert('企业基本信息更新成功！', {
					    	skin: 'layui-layer-molv' //样式类名
					    ,closeBtn: 0
						}); 
				} else {
					layer.alert('企业信息保存失败：'+obj.getMessage(),{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});     
				}
			}
			
			//行业
			var indus = $('#industry').val();
			var ind2 = $('#ind2').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : indus },
				success : function(dics){
					var rows=dics;
					$('#industry2').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind2)
							s='selected';
						$('#industry2').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			
			//省份
			var induse = $('#country').val();
			var ind3 = $('#ind3').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : induse },
				success : function(dics){
					var rows=dics;
					$('#province').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind3)
							s="selected='selected'";
						$('#province').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			//城市
			var indus = $('#ind3').val();
			var ind4 = $('#ind4').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : indus },
				success : function(dics){
					var rows=dics;
					$('#city').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind4)
							s="selected='selected'";
						$('#city').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			
			//销售区域
			var salse1 = $('#sellArea').val();
			var salse2 = $('#inpu2').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : salse1 },
				success : function(dics){
					var rows=dics;
					$('#sellArea2').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind4)
							s="selected='selected'";
						$('#sellArea2').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			
			$("#wizard").scrollable({
				onSeek: function(event,i){
					$("#status li").removeClass("active").eq(i).addClass("active");
				},
				onBeforeSeek:function(event,i){
					if(i==1){
						var user = $("#user").val();
						if(user==""){
							layer.alert('请输入用户名！', {
							    icon: 2,
							    skin: 'layer-ext-moon' 
							});
							return false;
						}
						var pass = $("#pass").val();
						var pass1 = $("#pass1").val();
						if(pass==""){
						    layer.alert('请输入密码！', {
							    icon: 2,
							    skin: 'layer-ext-moon' 
							});
							return false;
						}
						if(pass1 != pass){
							  layer.alert('两次密码不一致！', {
								    icon: 2,
								    skin: 'layer-ext-moon' 
								});
							return false;
						}
					}
				}
			});
		});
		
		//检查是否为字母或者数字
		function checknum(value) {
            var Regx = /^[A-Za-z0-9]*$/;
            if (Regx.test(value)) {
                return true;
            }
            else {
                return false;
            }
        }
	 	
	 
		function shoudiv(){
			var ck = $("#Check_info");
			if(ck.is(':checked')==true){
				$("#isture_check").show();
			}else{
				$("#isture_check").hide();
				
			}
		}
		
		function validForm(){
			var flag = true;
			$(".row em").parent().next().find("input").each(function(){
				var thisVal = $(this).val();
				var names = $(this).attr('name');
				if(names!="info" && typeof(names) != "undefined" && names!="logo" ){
					if(thisVal==""||thisVal=="必填"){
						flag = false;
						$(this).after($("<label class='error'>该项为必填项</label>"));
						$(this).next().next().remove();
					}else{
						$(this).next().remove();
					}
				}				
			});
			return flag;
		}
		
		var rtn=$("#form").form({
			 	//扩展验证规则 追加到已有的规则中
			 	rules:[{
			 		//规则名称
			 		name:"ajaxName",
			 		//判断方法  返回 true 或false
			 		rule: function(v, b) {
						var flag = false;
						var gets = $("#" + b).val();
						$.ajax({//验证唯一性
							type : 'post',
							url : '${ctx}/tenant/nameValid.ht',
							data : {name : gets},
						    async: false,
							success : function(istf){
								if(istf=='true'){
									flag =  true;
								}else{
									flag = false;
								}
							},failure : function(){	flag = false; }
						});	
						return flag;
					},
			 		//错误的提示信息
			 		msg:"名称已被占用"
			 	
			 	}],
			 	//显示的错误信息样式 element 当前验证的元素，msg：错误信息
			 	errorPlacement: function(element, msg) {
					var errorId = $(element).attr("tipId");
					if (errorId) {
						$('#' + errorId).find('label.error').remove();
						$('#' + errorId).append($('<label class="error">' + msg
								+ '</label>'));
						return;
					}
					var parent =$(element).parent();
					
					//判断parent宽度如果大于element宽度的100，slide tip,如果小于把父元素撑开
					if($(parent).outerWidth() > $(element).outerWidth() + 100){//slide tip
						parent.find('label.error').remove();
						parent.append($('<label class="error">' + msg + '</label>'));
					}else{
						$(parent).width($(element).outerWidth() + 80);
						parent.find('label.error').remove();
						parent.append($('<label class="error">' + msg + '</label>'));
					}
					//element的focus时候去掉提示
					$(element).focus(function(){
						parent.find('div.validatebox-tip').remove();
					});
			 	},
			 	//成功后的样式 element 当前验证的元素
			 	success:function(element){
			 	},
			 	excludes:":hidden"
	 	}).valid();
		
		
		
		function fanhui(){
			//返回上一个页面并刷新
			location.replace(document.referrer);
		}

	</script>

</head>
<body>
	<form id="infoForm" method="post" action="saveTenantBase.ht">
		<div id="wizard"  type="main">
				
					 <h3></h3>
					         <input type="hidden" name="sysOrgInfoId" id="sysOrgInfoId" value="${info.sysOrgInfoId}">
							<input type="hidden" name="flaglogo" value="${info.flaglogo}" />
							<input type="hidden" name="state" value="0" />
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">企业号</span>
		                           <input class="register_mainCon_con" id="name" name="name"  type="text" class="inputText"  value="${info.name}" readonly="readonly"/>  
		                        <div class="tipinfo"></div>
		                    </div>
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">公司名称</span>
		                        <input class="register_mainCon_con" id="name" name="name"  type="text" class="inputText" value="${info.name}" readonly="readonly"/> 
		                        <div class="tipinfo"></div>
		                    </div>
							
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">企业类型</span>
		                        <ap:selectDB name="type" id="type"
										where="typeId=10000005420003" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.type }">
									</ap:selectDB>
		                        <div class="tipinfo"></div>
		                    </div>
								
							
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">经营模式</span>
		                        <p class="register_pattern">
		                        	<input type="hidden" id="ck_mod" >
		                            <label><input type="checkbox" name="model" id="model1" class="ck_model" value="1"/>生产型</label> 
		                            <label><input type="checkbox" name="model" id="model2" class="ck_model" value="2"/>贸易型</label> 
		                            <label><input type="checkbox" name="model" id="model3" class="ck_model" value="3"/>服务型</label> 
		                            <label><input type="checkbox" name="model" id="model4" class="ck_model" value="4"/>研发型 </label>
		                            <label><input type="checkbox" name="model"  id="model0" class="ck_model" value="0"/>其他类型</label>
		                        </p>
		                        <div class="tipinfo"></div>
		                    </div>
								
						
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">主营产品</span>
		                        <input class="register_mainCon_con" id="product" name="product"  type="text"  value="${info.product}" readonly="readonly"/>
		                        <div class="tipinfo"></div>
		                    </div>
							
						
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">主营行业</span>
		                        <ap:selectDB name="industry" id="industry" where="parentId=10000003470025" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.industry}">
									</ap:selectDB>
									<%-- <ap:ajaxSelect srcElement="industry" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="industry2"/> --%>
									<ap:selectDB name="industry2" id="industry2" 
										where="1!=1" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.industry2}">
									</ap:selectDB>
		                        <div class="tipinfo"></div>
		                    </div>
								
						
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">企业邮箱</span>
		                        <input type="text" name="email"  style="width:200px;" maxlength="32" class="register_mainCon_con"  value="${info.email}" readonly="readonly"/>
		                        <div class="tipinfo"></div>
		                    </div>
						
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">公开联系方式</span>
		                        
		                        <c:if test="${info.isPublic==1}">
		                         <p class="register2_judge">
										<input type="radio" name="isPublic" checked="checked" class="radio" value="1" />&nbsp;是
										</p>
										<p class="register2_judge">
										<input type="radio" name="isPublic" class="radioa" value="0"/>&nbsp;否
										</p>
								    </c:if>
							        <c:if test="${info.isPublic==0}">
							        <p class="register2_judge">
						        		<input type="radio" name="isPublic" class="radio" value="1" />&nbsp;是
						        		</p>
						        		 <p class="register2_judge">
										<input type="radio" name="isPublic" checked="checked" class="radio" value="0"/>&nbsp;否
										</p>
								    </c:if>
								    <c:if test="${empty info.isPublic}">
								      <p class="register2_judge">
										<input type="radio" name="isPublic" class="radio" value="0"/>&nbsp;否
										</p>
										 <p class="register2_judge">
						        		<input type="radio" name="isPublic" checked="checked" class="radio" value="1" />&nbsp;是
						        		</p>
								    </c:if>
								     <div class="tipinfo"></div>
		                    </div>
							
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">企业联系人</span>
		                        <input name="connecter" type="text" class="register_mainCon_con" id="connecter"   value="${info.connecter}" readonly="readonly"/>
		                        <div class="tipinfo"></div>
		                    </div>
									
						
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">手机号</span>
		                        <input type="text"  name="tel" class="register_mainCon_con"    value="${info.tel}" readonly="readonly"/>
		                        <div class="tipinfo"></div>
		                    </div>
		                    
		                     <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">固定电话</span>
		                        <input type="text" name="homephone" class="register_mainCon_con"   value="${info.homephone}" readonly="readonly"/>
		                        <div class="tipinfo"></div>
		                    </div>
						
			                
			                 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"></span>
		                       <input type="button" class="register_mainCon_btn" value="返回" onclick="fanhui()" style="width: 100px;"/>
		                        <div class="tipinfo"></div>
		                    </div>
					
			
		</div>
</form>
<script type="text/javascript">
	
	function subStr(loginName){
		 if(loginName!=null && loginName != null){
	    	if(loginName.length>7){
	    		loginName = loginName.trim();
	    		loginName = loginName.substring(0,5)+"...";
	    	}
	    }
		 return loginName;
	}
	
</script>
</body>
</html>
