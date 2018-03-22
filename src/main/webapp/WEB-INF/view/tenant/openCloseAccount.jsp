
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
	<%-- <script type="text/javascript" src="${ctx}/js/uc/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/cloudDialogUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/uploadPreview.js"></script> --%>
 
	<!-- tablegird -->
	<%-- <link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/style.css" rel="stylesheet" type="text/css" /> --%>
	<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	.enterprise_baseInfor_navLeft ul li a.current {
    background: #dfdfdf;
    color: #323232;
}
	
	
	</style>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
    <script type="text/javascript">
        $(function (){
            $("#navtab1").ligerTab({ dblClickToClose: false });  
            
            var accountStatsus="${info.accountStatsus}";
            //alert(accountStatsus);
    		if(accountStatsus!='1'){
    			layer.open({
    				  title: '提示信息',
    				  closeBtn:0,
    				  skin: 'layui-layer-molv',
    				  shade:[0.6,'#000'],
    				  content: '请先完善实名认证信息，再进行开户管理',
    				  shift:1, //0-6的动画形式，-1不开启
    				  btn:['去认证'],
    				  yes:function(index){
    					  $(window.parent.document).find("#ulleft a").each(function(index){
    							var str=this.innerHTML;
    							if(str=='实名认证')
    							{
    							$(window.parent.document).find("#ulleft a").removeClass("current");
    								$(this).addClass("current");

    							}
    							 
    						 });
    					  window.location.href="${ctx}/tenant/certiInformation.ht"; 
    					  layer.close(index);
    					}
    				 
    			});     
    			
    		}
            
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
	        	
	        	/* if(clientProperty==0){
	        		if(branchaccountname!=branchname){
	        			
	        			layer.alert("户名和姓名不一致");
	        			return;
	        		}
	        		
	        	} */
	        	
	        	var clientProperty="${branch.clientProperty}";
	    		var branchaccountname=$("#branchaccountname").val();
	    		var branchname=$("#branchname").val();
	    		var orgCode=$("#orgCode").val();
	    		var businessLicense=$("#businessLicense").val();
	    		var taxId=$("#taxId").val();
	        	
	        	if(clientProperty==1){
	        		
	        		if(orgCode==null||orgCode==""){
	        			layer.alert("组织机构代码必填");
	        			return;
	        		}
	        		if(businessLicense==null||businessLicense==""){
	        			layer.alert("营业执照必填");
	        			return;
	        		}
	        		if(taxId==null||taxId==""){
	        			layer.alert("税务登记号必填");
	        			return;
	        		}
	        	}
	        	
	        	
	        	
	        	
	        	
	        	
				//去掉所有的html标记
				if(validForm()){
					//var code = $("#code").val();
					$("#credentialsType").removeAttr("disabled");
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
		/* var dd = null;
		function openImageDialog(type){
			dd = type;
			$.cloudDialog.imageDialog({contextPath:"${ctx}",isSingle:true});
		}
		function imageDialogCallback(data){
			if(data.length > 0){
				_callbackImageUploadSuccess(data[0].filePath,dd);
			}
		} */
		
		//上传图片回调函数
		/* function _callbackImageUploadSuccess(path,type){
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
		} */
		
		//检查是否为字母或者数字
		/* function checknum(value) {
            var Regx = /^[A-Za-z0-9]*$/;
            if (Regx.test(value)) {
                return true;
            }
            else {
                return false;
            }
        } */
		
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
					layer.alert('开销户信息保存成功！', {
				    	skin: 'layui-layer-molv' //样式类名
				    ,closeBtn: 0
					},function(){
						window.location.reload();
					});  
				}
			} else {
				layer.alert('开销户信息保存成功,中金接口调用失败',{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0},function(){
					
					window.location.reload();
				});     
			}
		}
	 
		/* function shoudiv(){
			var ck = $("#Check_info");
			if(ck.is(':checked')==true){
				$("#isture_check").show();
			}else{
				$("#isture_check").hide();
				
			}
		} */
		
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
		
		
		
		
	</script>
	
	<style type="text/css">
	.openAccount_titie_width {
    width: 120px;
	}
	
	
	</style>
	
	
</head>
<body >
	<form id="infoForm" method="post" action="saveOpenCloseAccount.ht"  class="signupForm2">
	<div id="wizard"  type="main">
			<input type="hidden" name="orgid" id="orgid" value="${branch.orgid}" />
			<input type="hidden" name="sysid" id="sysid" value="uc" />
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>客户性质</span>
                        	<p class="register2_judge">
							<input type="radio" name="clientProperty" checked="checked" class="radio" value="1" />&nbsp;公司
							</p>
				        <%-- <c:if test="${branch.clientProperty==0}">
				        <p class="register2_judge">
			        		<input type="radio" name="clientProperty" class="radio" value="1" />&nbsp;公司
			        		</p>
			        		 <p class="register2_judge">
							<input type="radio" name="clientProperty" checked="checked" class="radio" value="0"/>&nbsp;个人
							</p>
					    </c:if>
                        <c:if test="${branch.clientProperty==1}">
                        <p class="register2_judge">
							<input type="radio" name="clientProperty" checked="checked" class="radio" value="1" />&nbsp;公司
							</p>
							<p class="register2_judge">
							<input type="radio" name="clientProperty" class="radioa" value="0"/>&nbsp;个人
							</p>
					    </c:if>
					    <c:if test="${empty branch.clientProperty}">
					      <p class="register2_judge">
							<input type="radio" name="clientProperty" class="radio" checked="checked"  value="0"/>&nbsp;个人
							</p>
							 <p class="register2_judge">
			        		<input type="radio" name="clientProperty" class="radio" value="1" />&nbsp;公司
			        		</p>
					    </c:if> --%>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>开户名称</span>
                        <input type="text" id="branchaccountname" name="branchaccountname" class="register_mainCon_con"  maxlength="50"  validate="{required:true}" value="${info.name}" readonly="readonly"/>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>姓名</span>
                        <input type="text" id="incorporator" name="incorporator" class="register_mainCon_con"  maxlength="50" validate="{required:true}" value="${info.incorporator}" readonly="readonly"/>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>证件类型</span>
                        <%-- <c:if test="${branch.credentialsType==null || branch.credentialsType=='' }">
                       	<select name="credentialsType">
	                       <option value="A">身份证</option>
	                       <option value="B">户口簿</option>
	                       <option value="C">军官证</option>
	                       <option value="D">警官证</option>
	                       <option value="E">护照</option>
	                       <option value="F">港澳通行证</option>
	                       <option value="L">其他</option>
                       </select>
                      
                      </c:if>
                      <c:if test="${branch.credentialsType=='A'}">
                       	<select name="credentialsType">
	                        <option value="A" selected="selected">身份证</option>
	                       <option value="B">户口簿</option>
	                       <option value="C">军官证</option>
	                       <option value="D">警官证</option>
	                       <option value="E">护照</option>
	                       <option value="F">港澳通行证</option>
	                       <option value="L">其他</option>
                       </select>
                      
                      </c:if>
                      <c:if test="${branch.credentialsType=='B' }">
                       	<select name="credentialsType">
	                        <option value="A" >身份证</option>
	                       <option value="B" selected="selected">户口簿</option>
	                       <option value="C">军官证</option>
	                       <option value="D">警官证</option>
	                       <option value="E">护照</option>
	                       <option value="F">港澳通行证</option>
	                       <option value="L">其他</option>
                       </select>
                      
                      </c:if>
                      <c:if test="${branch.credentialsType=='C' }">
                       <select name="credentialsType">
	                        <option value="A" >身份证</option>
	                       <option value="B" >户口簿</option>
	                       <option value="C" selected="selected">军官证</option>
	                       <option value="D">警官证</option>
	                       <option value="E">护照</option>
	                       <option value="F">港澳通行证</option>
	                       <option value="L">其他</option>
                       </select>
                      
                      </c:if>
                      <c:if test="${branch.credentialsType=='D' }">
                       <select name="credentialsType">
	                        <option value="A" >身份证</option>
	                       <option value="B" >户口簿</option>
	                       <option value="C" >军官证</option>
	                       <option value="D" selected="selected">警官证</option>
	                       <option value="E">护照</option>
	                       <option value="F">港澳通行证</option>
	                       <option value="L">其他</option>
                       </select>
                      
                      </c:if>
                      <c:if test="${branch.credentialsType=='E' }">
                       <select name="credentialsType">
	                        <option value="A" >身份证</option>
	                       <option value="B" >户口簿</option>
	                       <option value="C" >军官证</option>
	                       <option value="D">警官证</option>
	                       <option value="E" selected="selected">护照</option>
	                       <option value="F">港澳通行证</option>
	                       <option value="L">其他</option>
                       </select>
                      
                      </c:if>
                      <c:if test="${branch.credentialsType=='F' }">
                       <select name="credentialsType">
	                        <option value="A" >身份证</option>
	                       <option value="B" >户口簿</option>
	                       <option value="C" >军官证</option>
	                       <option value="D">警官证</option>
	                       <option value="E" >护照</option>
	                       <option value="F" selected="selected">港澳通行证</option>
	                       <option value="L">其他</option>
                       </select>
                      
                      </c:if>
                      <c:if test="${branch.credentialsType=='L' }">
                       <select name="credentialsType">
	                        <option value="A" >身份证</option>
	                       <option value="B" >户口簿</option>
	                       <option value="C" >军官证</option>
	                       <option value="D">警官证</option>
	                       <option value="E" >护照</option>
	                       <option value="F">港澳通行证</option>
	                       <option value="L" selected="selected">其他</option>
                       </select>
                      
                      </c:if> --%>
                      
                      <select id="credentialsType" name="credentialsType" disabled="disabled">
						<option value="A">身份证</option>
						<!-- <option value="B">户口簿</option>
						<option value="C">军官证</option>
						<option value="D">警官证</option>
						<option value="E">护照</option>
						<option value="F">港澳通行证</option>
						<option value="L">其他</option> -->
					</select>
                      
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>证件号</span>
                        <input type="text"  name="credentialsNumber" class="register_mainCon_con"  maxlength="18"  validate="{required:true}" value="${info.frsfzhm}" readonly="readonly"/>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>组织机构代码证</span>
                        <input type="text" id="orgCode" name="orgCode" class="register_mainCon_con" maxlength="30"   validate="{required:true,qiyexx:true}" value="${info.code}" readonly="readonly"/>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>营业执照</span>
                        <input type="text" id="businessLicense" name="businessLicense" class="register_mainCon_con"  maxlength="30"  validate="{required:true,qiyexx:true}" value="${info.yyzz}" readonly="readonly"/>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>税务登记号</span>
                        <input type="text" id="taxId" name="taxId" class="register_mainCon_con" maxlength="30"   validate="{required:true,qiyexx:true}" value="${info.nsrsbh}" readonly="readonly"/>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>业务功能标示</span>
                      <%--   <c:if test="${empty branch.id }">
                        	<p class="register2_judge">
			        		<input type="radio" name="businessFlag" class="radio" value="1" checked="checked" />&nbsp;开户
			        		</p>
                        </c:if>
                        <c:if test="${not empty branch.id and branch.accstate=='1'}">
                        	<p class="register2_judge">
							<input type="radio" name="businessFlag" class="radio" checked="checked" value="2"/>&nbsp;修改
							</p>
                        </c:if> --%>
                        
                         <c:choose>
				    <c:when test="${not empty branch.id and (branch.accstate=='1' or branch.accstate=='3' or branch.accstate=='4' )}">
				       <p class="register2_judge">
							<input type="radio" name="businessFlag" class="radio" checked="checked" value="2"/>&nbsp;修改
							</p>
				    </c:when>
	                 <c:otherwise>
	                  <p class="register2_judge">
		        		<input type="radio" name="businessFlag" class="radio" value="1" checked="checked" />&nbsp;开户
		        		</p>
	                 </c:otherwise>
	                 </c:choose>
                        
                        <input type="hidden" name="id"  value="${branch.id}"/>
                        
                        
                        
                       <%-- <c:if test="${branch.businessFlag==1}">
				        <p class="register2_judge">
			        		<input type="radio" name="businessFlag" class="radio" value="1" checked="checked" />&nbsp;开户
			        		</p>
			        		 <p class="register2_judge">
							<input type="radio" name="businessFlag" class="radio" value="2"/>&nbsp;修改
							</p>
					    </c:if>
                        <c:if test="${branch.businessFlag==2}">
                        <p class="register2_judge">
							<input type="radio" name="businessFlag" class="radio" value="1" />&nbsp;开户
							</p>
							<p class="register2_judge">
							<input type="radio" name="businessFlag" class="radioa" checked="checked" value="2"/>&nbsp;修改
							</p>
					    </c:if>
					    <c:if test="${empty branch.businessFlag}">
					      <p class="register2_judge">
							<input type="radio" name="businessFlag" class="radio" checked="checked"  value="1"/>&nbsp;开户
							</p>
							 <p class="register2_judge">
			        		<input type="radio" name="businessFlag" class="radio" value="2" />&nbsp;修改
			        		</p>
					    </c:if> --%>
                        <div class="tipinfo"></div>
                    	</div> 
	                   <div class="register_mainCon_list">
                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>账户类型</span>
			        		 <p class="register2_judge">
							<input type="radio" name="accountType1" class="radio" checked="checked"  value="1"/>&nbsp;客户
							</p>
                        <div class="tipinfo"></div>
                    	</div> 
	                    
	                    
	                    
	                    
	                    
					
				
				<div class="register_mainCon_list authentication_btn_mar">
                       <span class="register_mainCon_tit openAccount_titie_width"></span>
                              <c:choose>
				    <c:when test="${not empty branch.id and (branch.accstate=='1' or branch.accstate=='3' or branch.accstate=='4' )}">
				       <p class="register2_judge">
							<input type="button" class="register_mainCon_btn" value="变更" id="sub"/>
							</p>
				    </c:when>
	                 <c:otherwise>
	                  <p class="register2_judge">
		        		<input type="button" class="register_mainCon_btn" value="开户" id="sub"/>
		        		</p>
	                 </c:otherwise>
	                 </c:choose>
                       
                       
                       <div class="tipinfo"></div>
                   </div>
	</div>
	
		<input type="hidden" name="accstate" value="${branch.accstate}">
		<input type="hidden" name="flag" value="1">
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
</style>
</body>
</html>
