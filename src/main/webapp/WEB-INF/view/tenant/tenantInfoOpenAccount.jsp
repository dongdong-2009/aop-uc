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
		
	<!-- 上传图片 -->
	<script type="text/javascript" src="${ctx}/js/uc/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/cloudDialogUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/uploadPreview.js"></script>
 
	<!-- tablegird -->
	<%-- <link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/style.css" rel="stylesheet" type="text/css" /> --%>
	<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
    <script type="text/javascript">
        $(function (){
            $("#navtab1").ligerTab({ dblClickToClose: false });                 
        });
    </script>
	<script type="text/javascript">
	
		var manager = null;
		
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#infoForm').form();
			$("#sub").click(function(){
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
		//上传图片	
		var dd = null;
		function openImageDialog(type){
			dd = type;
			$.cloudDialog.imageDialog({contextPath:"${ctx}",isSingle:true});
		}
		function imageDialogCallback(data){
			if(data.length > 0){
				_callbackImageUploadSuccess(data[0].filePath,dd);
			}
		}
		
		//上传图片回调函数
		function _callbackImageUploadSuccess(path,type){
			if(type=='0'){//上传导航栏
				$("#yyzzPic").val(path);
				var item = $('<img src="${fileCtx}/' + path + '" width="260" height="84" />');
				$(".yyzzPic-pic").empty();
				$(".yyzzPic-pic").append(item);
			}else if(type == '1'){
				//说明有值，为替换状态
				if($("#frPic").val() != ""){
					var oldPath = $("#frPic").val().split(",")[0];
					$("#frPic").val($("#frPic").val().replace(oldPath,path))
				}else{
					$("#frPic").val(path);
				}
				var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
			 	$("#frPicDiv img:eq(0)").remove();
				$("#frPicDiv").prepend(item);	
				$("#addfrPic2").show();
			}else if(type == '2'){
				//说明有值，为替换状态
				if($("#frPic").val().indexOf(",")>0 ){
					var oldPath = $("#frPic").val().split(",")[1];
					$("#frPic").val($("#frPic").val().replace(oldPath,path));
					var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
				 	$("#frPicDiv img:eq(1)").remove();
					$("#frPicDiv").append(item);
				}else if($("#frPic").val()!="" ){
					var realPath = $("#frPic").val()+","+path;
					$("#frPic").val(realPath);
				 	var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
				 	$("#frPicDiv img:eq(1)").remove();
					$("#frPicDiv").append(item);
				}else{
					layer.alert("请先上传身份证正面");
				}
			}
		}
		
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
					layer.alert('开户信息保存成功！', {
				    	skin: 'layui-layer-molv' //样式类名
				    ,closeBtn: 0
					},function(){
						window.location.reload();
					});  
				}
			} else {
				layer.alert('企业信息保存失败：'+obj.getMessage(),{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});     
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
		$(window.parent.document).find("#mainiframe").load(function(){
			
			var mainheight = 800;
			 $(this).height(mainheight);
		});
		
		$(function(){
			//城市
			var induse = $('#province').val();
			var ind3 = $('#ind3').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : induse },
				success : function(dics){
					var rows=dics;
					$('#city').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind3)
							s="selected='selected'";
						$('#city').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			
		});
		
		
		
		
		
	</script>
</head>
<body >
	<form id="infoForm" method="post" action="saveOpenAccount.ht"  class="signupForm2">
	<div id="wizard"  type="main">
								<input type="hidden" name="orgid" id="orgid" value="${branch.orgid}" />
									
									
										 <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>账户名称</span>
					                        <input type="text"  name="branchaccountname" class="register_mainCon_con"  maxlength="18"  validate="{required:true}" value="${branch.branchaccountname}"/>
					                        <div class="tipinfo"></div>
					                    </div>
										
										 <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>开户行账号</span>
					                        <input type="text"  name="bankaccount" class="register_mainCon_con"  maxlength="20"  validate="{required:true,digits:true}" class="register_mainCon_con" value="${branch.bankaccount}"/>
					                        <div class="tipinfo"></div>
					                    </div>
										
										<%-- 
										<div class="row">
											<div class="label">
												开户行所在城市：
											</div>
											<div class="cell">
												<ap:selectDB name="country" id="country" where="parentId=10000000180001" optionValue="itemValue" style="display:none;"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.country}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="country" url="${ctx}/cloud/config/tenantInfo/getCascadeJsonData.ht" targetElement="province"/>
												<ap:selectDB name="province" id="province" defaultText="==请选择==" defaultValue=""
													where="itemKey='province'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.province}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="province" url="${ctx}/cloud/config/tenantInfo/getCascadeJsonData.ht" targetElement="city"/>
												<ap:selectDB name="city" id="city" 
													where="itemKey='city'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.city}">
												</ap:selectDB>
											</div>
											<div class="blank0"></div>
										</div> --%>
										
										
										<div id="city_1" class="register_mainCon_list openAccount_city">
					                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>开户行所在地</span>
					                       <!--  <select class="prov " ></select> 
					                        <select class="city " disabled="disabled"></select> -->
					                        
												<ap:ajaxSelect srcElement="country" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="province"/>
												<ap:selectDB name="province" id="province" defaultText="==请选择==" defaultValue=""
													where="itemKey='province'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${branch.province}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="province" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="city"/>
												<ap:selectDB name="city" id="city" 
													where="itemKey='city'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${branch.city}">
												</ap:selectDB>
												<input id="ind3" type="hidden" value="${branch.city}">
					                        <div class="tipinfo"></div>
					                    </div>
										
										
									<%-- 	
										<div class="row">
											<div class="label">
												<em>*</em>开户行全称：
											</div>
											<div class="cell">
												<ap:selectDB name="branchAbbr" id="branchAbbr" where="level=1" optionValue="itemValue"
													optionText="itemName" table="BRANCH_NAME"
													selectedValue="${branch.branchAbbr}">
												</ap:selectDB>
											</div>
											<div class="blank0"></div>
										</div> --%>
										
										
										 <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>开户行全称</span>
					                       <ap:selectDB name="bankfullname" id="bankfullname" where="level=1" optionValue="itemValue"
													optionText="itemName" table="BRANCH_NAME"
													selectedValue="${branch.bankfullname}">
												</ap:selectDB>
					                        <div class="tipinfo"></div>
					                    </div>
										
									<!-- 	
										<div class="row">
											<div class="label">
												<em>*</em>开户行简称：
											</div>
											<div class="cell">
												<input type="text"  name="branchAbbr" placeholder="如农业银行xx支行，请写农业银行" class="text"  maxlength="18"  validate="{required:true}"/>
											</div>
											<div class="blank0"></div>
										</div> -->
										
										
										   <div class="register_mainCon_list">
						                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>开户行简称</span>
						                        <input type="text"  name="branchAbbr" placeholder="如农业银行xx支行，请写农业银行" class="register_mainCon_con"  maxlength="18"  onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''))" validate="{required:true}" value="${branch.branchAbbr}"/>
						                        <div class="tipinfo"></div> 
						                    </div>
										
							
									
									<%--  <c:if test="${not empty branch}">
											<input type="hidden" name="id" value="${branch.id}">
											<div class="row">
												<div class="label">
													<em>*</em>账户名称：
												</div>
												<div class="cell">
													<input type="text"  name="branchaccountname" value="${branch.branchaccountname}" class="text"  maxlength="18"  validate="{required:true}"/>
												</div>
												<div class="blank0"></div>
											</div>
											
											<div class="row">
												<div class="label">
													<em>*</em>开户行账号：
												</div>
												<div class="cell">
													<input type="text"  name="bankaccount" value="${branch.bankaccount}" class="text"  maxlength="50"  validate="{required:true,digits:true}"/>
												</div>
												<div class="blank0"></div>
											</div>
											
											<div class="row">
												<div class="label">
													开户行所在城市：
												</div>
												<div class="cell">
													<ap:selectDB name="country" id="country" where="parentId=10000000180001" optionValue="itemValue" style="display:none;"
														optionText="itemName" table="SYS_DIC"
														selectedValue="${info.country}">
													</ap:selectDB>
													<ap:ajaxSelect srcElement="country" url="${ctx}/cloud/config/tenantInfo/getCascadeJsonData.ht" targetElement="province"/>
													<ap:selectDB name="province" id="province" defaultText="==请选择==" defaultValue=""
														where="itemKey='province'" optionValue="itemValue"
														optionText="itemName" table="SYS_DIC"
														selectedValue="${branch.province}">
													</ap:selectDB>
													<ap:ajaxSelect srcElement="province" url="${ctx}/cloud/config/tenantInfo/getCascadeJsonData.ht" targetElement="city"/>
													<ap:selectDB name="city" id="city" 
														where="itemKey='city'" optionValue="itemValue"
														optionText="itemName" table="SYS_DIC"
														selectedValue="${branch.city}">
													</ap:selectDB>
													<input id="ind3" type="hidden" value="${branch.province}">
													<input id="ind4" type="hidden" value="${branch.city}">
												</div>
												<div class="blank0"></div>
											</div>
											
											<div class="row">
												<div class="label">
													<em>*</em>开户行全称：
												</div>
												<div class="cell">
													<input type="text"  name="bankfullname" value="${branch.bankfullname}" class="text"  maxlength="18"  validate="{required:true}"/>
												</div>
												<div class="blank0"></div>
											</div>
											
											<div class="row">
												<div class="label">
													<em>*</em>开户行简称：
												</div>
												<div class="cell">
													<input type="text"  name="branchAbbr" value="${branch.branchAbbr}" placeholder="如农业银行xx支行，请写农业银行" class="text"  maxlength="18"  validate="{required:true}"/>
												</div>
												<div class="blank0"></div>
											</div>
											
											<div class="row">
												<div class="label">
													<em>*</em>收款方账户类型：
												</div>
												<div class="cell">
												
													<c:remove var="checked"/>
													
											        <c:if test="${branch.accountType == 0}">
											        	<c:set var="checked" value='checked="checked"'></c:set>
												    </c:if>
														<input type="radio" name="accountType" class="radio" ${checked} value="0"/>银行对公账户
														
													<c:remove var="checked"/>
													
											        <c:if test="${branch.accountType == 1}">
											        	<c:set var="checked" value='checked="checked"'></c:set>
												    </c:if>
														<input type="radio"  name="accountType" ${checked} class="radio" value="1" />银行对私账户
												</div>
												<div class="blank0"></div>
											</div>
									</c:if>  --%>
									
									
									
									
									<div class="register_mainCon_list authentication_btn_mar">
				                        <span class="register_mainCon_tit openAccount_titie_width"></span>
				                        <input type="button" class="register_mainCon_btn" value="提交" id="sub"/>
				                        <div class="tipinfo"></div>
				                    </div>
						</div>
						
		
	</form>

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
/* .layui-layer-btn {
    pointer-events: auto;
    text-align: right;
}
.layui-layer-iframe .layui-layer-btn, .layui-layer-page .layui-layer-btn {
    padding-top: 0px;
} */
</style>
</body>
</html>
