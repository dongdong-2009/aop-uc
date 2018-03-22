<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<html>
<head>
	<title>编辑企业信息</title>
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

/* form .row {*width:700px;}
#wizard {font-size:12px;height:500px;margin-top:10px;margin-left:20px;overflow:hidden; position:relative;-moz-border-radius:5px;-webkit-border-radius:5px;}
#wizard .items{width:20000px; clear:both;position:absolute;}
#wizard #status{height:35px;background:#123;padding-left:25px !important;}
#status li{float:left;color:#fff;padding:10px 30px;}
#status li.active{background-color:#369;font-weight:normal;}
.input{width:240px; height:18px; margin:10px auto; line-height:20px; border:1px solid #d3d3d3; padding:2px}
.page{padding:15px 30px;width:900px;height:500px; overflow-x:hidden; overflow-y:auto;float:left; position:relative;}
.page h3 em{font-size:12px; font-weight:500; font-style:normal}
.page p{line-height:24px;}
.page p label{font-size:14px; display:block;}
.btn_nav{height:36px; line-height:36px;  padding:60px 0 100px;}
.prev,.next{width:100px; height:32px; line-height:32px; background:url(btn_bg.gif) repeat-x bottom; border:1px solid #d3d3d3; cursor:pointer;
  color:white; border: none; border-radius:4px;}
	
label.error {
  padding-left: 15px;
}
input.prev {
  float: left;
  background-color:#09c;
}
input.next {
  float: right;
  margin-right:80px; 
  display:inline;
  background-color:#0a8;
}

a {
    color: #666;
    text-decoration: none;
}
form .label {
  width: 120px;
  float: left;
  margin-left: -145px;
  font-weight: bold;
  text-align: right;
  position: relative;
  _display: inline;
}
.page h3 {
  height: 5px;
  font-size: 16px;
  border-bottom: 1px dotted #ccc;
  margin-bottom: 5px;
  padding-bottom: 5px;
}
。blank0 { height:0px; line-height:0px; display:block; overflow:hidden; clear:both; font-size:0;}
。blank150 { height:150px; line-height:0px; display:block; overflow:hidden; clear:both; font-size:0;} */
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
		
	</script>

</head>
<body>
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
                <div class="register_mainTit_left">企业账号注册</div>
                <div class="register_mainTit_right"><a href="${ctx}/user/reg_cloud.ht">切换成个人账号注册</a></div>
            </div>
<div class="register_mainCon" >
	<form id="infoForm" method="post" action="saveSucess.ht">
		<div id="wizard"  type="main">
			<!-- <ul id="status">
				<li class="active"><strong>1.</strong>企业基本资料</li>
			</ul> -->
				
					 <h3></h3>
							<input type="hidden" name="flaglogo" value="${info.flaglogo}" />
							<input type="hidden" name="state" value="0" />
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>公司名称</span>
		                        <input class="register_mainCon_con" id="name" name="name"  type="text" class="inputText" maxlength="18" validate="{required:true,maxlength:128,ajaxName:'name'}"/>
		                        <div class="tipinfo"></div>
		                    </div>
								
							<!-- <div class="row pt0">
								<div class="note">
									请用中文完整填写在工商局注册的公司名称。
									<br />
									例如：上海西芝信息技术有限公司；
								</div>
								<div class="blank0"></div>
							</div> -->
							<%-- <div class="row">
								<div class="label">
									<em>*</em>企业类型：
								</div>
								<div class="cell">
									<ap:selectDB name="type" id="type"
										where="typeId=10000005420003" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.type }">
									</ap:selectDB>
								</div>
								<div class="blank0"></div>
							</div> --%>
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业类型</span>
		                        <ap:selectDB name="type" id="type"
										where="typeId=10000005420003" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.type }">
									</ap:selectDB>
		                        <div class="tipinfo"></div>
		                    </div>
								
							<!-- <div class="row">
								<div class="label">
									<em>*</em>经营模式：
								</div>
								<div id="business_mode_div" class="cell">
									<input type="hidden" id="ck_mod" >
									<label for="type_6">
										<input type="checkbox" name="model" id="model1" class="ck_model" value="1"/>生产型
									</label>
									<label for="type_7">
										<input type="checkbox" name="model" id="model2" class="ck_model" value="2"/>贸易型
									</label>
									<label for="type_8">
										<input type="checkbox" name="model" id="model3" class="ck_model" value="3"/>服务型
									</label>
									<label for="type_9">
										<input type="checkbox" name="model" id="model4" class="ck_model" value="4"/>研发型
									</label>
									<label for="type_10">
										<input type="checkbox" name="model" validate="{required:true}" tipId="errorA" id="model0" class="ck_model" value="0"/>其他类型
									</label>
									<label id="errorA"></label>
								</div>
								<div class="blank0"></div>
							</div> -->
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>经营模式</span>
		                        <p class="register_pattern">
		                        	<input type="hidden" id="ck_mod" >
		                            <label><input type="checkbox" name="model" id="model1" class="ck_model" value="1"/>生产型</label> 
		                            <label><input type="checkbox" name="model" id="model2" class="ck_model" value="2"/>贸易型</label> 
		                            <label><input type="checkbox" name="model" id="model3" class="ck_model" value="3"/>服务型</label> 
		                            <label><input type="checkbox" name="model" id="model4" class="ck_model" value="4"/>研发型 </label>
		                            <label><input type="checkbox" name="model" validate="{required:true}" tipId="errorA" id="model0" class="ck_model" value="0"/>其他类型</label>
		                        </p>
		                        <div class="tipinfo"></div>
		                    </div>
								
							<!-- <div class="row">
								<div class="label">
									<em>*</em>主营产品：
								</div>
								<div class="cell">
									<input class="text" id="product" name="product"  type="text" class="inputText" validate="{required:true,maxlength:256}" />
								</div>
								<div class="blank0"></div>
							</div> -->
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>主营产品</span>
		                        <input class="register_mainCon_con" id="product" name="product"  type="text" validate="{required:true,maxlength:256}" />
		                        <div class="tipinfo"></div>
		                    </div>
							
								
							<%-- <div class="row">
								
								<div class="cell">
									<ap:selectDB name="industry" id="industry" where="parentId=10000003470025" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.industry}">
									</ap:selectDB>
									<ap:ajaxSelect srcElement="industry" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="industry2"/>
									<ap:selectDB name="industry2" id="industry2" 
										where="1!=1" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.industry2}">
									</ap:selectDB>
									<input id="ind2" type="hidden" value="${info.industry2}">
								</div>
								<div class="blank0"></div>
							</div> --%>
							
							
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>主营行业</span>
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
									<input id="ind2" type="hidden" value="${info.industry2}">
		                        <div class="tipinfo"></div>
		                    </div>
								
							<!-- <div class="row">
								<div class="label">
										<em>*</em>企业邮箱：
								</div>
								<div class="cell">
									<input type="text" name="email"  style="width:200px;" maxlength="32" class="inputText" validate="{required:true,email:true,maxlength:32}"/>
								</div>
								<div class="blank0"></div>
							</div> -->
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业邮箱</span>
		                        <input type="text" name="email"  style="width:200px;" maxlength="32" class="register_mainCon_con" validate="{required:true,email:true,maxlength:32}"/>
		                        <div class="tipinfo"></div>
		                    </div>
										
							<%-- <div class="row">
								<div class="label">
									公开联系方式：
								</div>
								<div class="cell">
							        <c:if test="${info.isPublic==1}">
										<input type="radio" name="isPublic" checked="checked" class="radio" value="1" />是
										<input type="radio" name="isPublic" class="radioa" value="0"/>否
								    </c:if>
							        <c:if test="${info.isPublic==0}">
						        		<input type="radio" name="isPublic" class="radio" value="1" />是
										<input type="radio" name="isPublic" checked="checked" class="radio" value="0"/>否
								    </c:if>
								    <c:if test="${empty info.isPublic}">
										<input type="radio" name="isPublic" class="radio" value="0"/>否
						        		<input type="radio" name="isPublic" checked="checked" class="radio" value="1" />是
								    </c:if>
								</div>
								<div class="blank0"></div>
							</div> --%>
							
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
									
							<!-- <div class="row">
								<div class="label">
									<em>*</em>企业联系人：
								</div>
								<div class="cell">
									<input name="connecter" type="text" class="text" id="connecter" maxlength="32"  validate="{required:true,maxlength:32}" />
								</div>
								<div class="blank0"></div>
							</div> -->
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业联系人</span>
		                        <input name="connecter" type="text" class="register_mainCon_con" id="connecter" maxlength="32"  validate="{required:true,maxlength:32}" />
		                        <div class="tipinfo"></div>
		                    </div>
									
							<!-- <div class="row">
								<div class="label">
									<em>*</em>手机号：
								</div>
								<div class="cell">
									<input type="text"  name="tel" class="text" maxlength="11"  validate="{required:true,phone:true}"/>
								</div>
								<div class="blank0"></div>
							</div> -->
							
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>手机号</span>
		                        <input type="text"  name="tel" class="register_mainCon_con" maxlength="11"  validate="{required:true,phone:true}"/>
		                        <div class="tipinfo"></div>
		                    </div>
		                    
		                     <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>固定电话</span>
		                        <input type="text" name="homephone" class="register_mainCon_con"  maxlength="18"  validate="{required:true,tel:true}"/>
		                        <div class="tipinfo"></div>
		                    </div>
							<!-- 
							<div class="row">
								<div class="label">
								<em>*</em>固定电话：
								</div>
								<div class="cell">
									<input type="text" 
											name="homephone" class="text"  maxlength="18"  validate="{required:true,tel:true}"/>
								</div>
								<div class="blank0"></div>
							</div> -->
								<!-- <div class="blank0"></div>
							<div class="btn_nav">
			                  <input type="button" class="next right" id="sub" value="立即创建"/>
			                </div> -->
			                
			                 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"></span>
		                        <input type="button" class="register_mainCon_btn" value="提交" id="sub" />
		                        <div class="tipinfo"></div>
		                    </div>
					
			
		</div>
</form>
</div>
</div>
<div class="register_footer">
            <div class="register_footerCon">
                <span class="register_copy">Copyright © 2015, All Rights Reserved　京ICP备05067351号-2</span><span style="margin-left:18px;"><a href="javascript:void(0);">法律声明</a></span><span style="margin-left:11px;margin-right:5px;">|</span><span><a href="javascript:void(0);">站点地图</a></span><span style="margin-left:11px;margin-right:5px;">|</span><span><a href="javascript:void(0);">诚聘精英</a></span>
            </div>
        </div>
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
