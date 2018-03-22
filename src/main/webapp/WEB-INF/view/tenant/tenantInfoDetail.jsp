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
 
	
	<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.all.js"></script>
<script type="text/javascript" href="${ctx}/ueditor142/lang/zh-cn/zh-cn.js" ></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	<style type="text/css">
	   .register_mainCon_con{
	     height: 30px;
	     width: 190px;
	     font-size: 14px;
	   }
	.register_mainCon_list{
	     line-height:20px;
	    font-size: 14px;
	     width: 520px;
	     float: left;
	}
	#bigimage { position:absolute; display:none; }
	.register_mainCon_btn{
	  margin-top: 100px;
	  margin-left: 300px;
	}
	
	.openAccount_titie_width{
	 width: 132px;
	}
	.appleSelect{
	  width: 100px;
	}
	#bigimage img {padding:5px; background:#fff; border:1px solid #e3e3e3; }
	</style>
    <script type="text/javascript">
    
    var ue=null;
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
					$("#info").val(ue.getContent());
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
							s="selected='selected'";
						$('#industry2').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			
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
			//县级
			var indus = $('#ind3').val();
			var ind4 = $('#ind4').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : indus },
				success : function(dics){
					var rows=dics;
					$('#county').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind4)
							s="selected='selected'";
						$('#county').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			//城市
			var induse1 = $('#province1').val();
			var ind5 = $('#ind5').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : induse1 },
				success : function(dics){
					var rows=dics;
					$('#city1').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind5)
							s="selected='selected'";
						$('#city1').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			//县级
			var indus2 = $('#ind5').val();
			var ind6 = $('#ind6').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : indus2 },
				success : function(dics){
					var rows=dics;
					$('#county1').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind6)
							s="selected='selected'";
						$('#county1').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
		});
		//上传图片	
		var dd = null;
		function openImageDialog(type){
			dd = type;
			$.cloudDialog.imageDialog({contextPath:"${ctx}",isSingle:true});
		}
		function imageDialogCallback(data){
			debugger;
			if(data.length > 0){
				_callbackImageUploadSuccess(data[0].filePath,dd);
			}
		}
		
		//上传图片回调函数
		function _callbackImageUploadSuccess(path,type){
			if(type=='0'){//上传导航栏
				$("#yyzzPic").val(path);
				var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
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
				}
				else{
					layer.alert("请先上传身份证正面");
				}
			}
			else 
				if(type == '3'){
					$("#codePic").val(path);
					var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
					$(".codePic-pic").empty();
					$(".codePic-pic").append(item);
				}
				else
				if(type == '4'){
					$("#nsrsbhPic").val(path);
					var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
					$(".nsrsbhPic-pic").empty();
					$(".nsrsbhPic-pic").append(item);
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
			debugger;
			if (obj.isSuccess()) {
				obj=obj.getMessage();
				obj=$.parseJSON(obj);
				if(obj.flag=='audit'){
					
					audit(obj);
				}
				else{
					layer.alert('企业信息修改成功！', {
				    	skin: 'layui-layer-molv' //样式类名
				    ,closeBtn: 0},function(){
						window.location.reload();
					});  
				}
				
			} else {
                if(obj.flag=='audit'){
					
					audit(obj);
				}
                else{
                	
                	
				   layer.alert('企业信息保存失败：'+obj.Msg,{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});     
                }
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
			if($("#codePic").val()==null||$("#codePic").val()==''){
				flag = false;
				layer.alert('请上传组织机构代码证扫描件或照片');
			}
			if($("#nsrsbhPic").val()==null||$("#nsrsbhPic").val()==''){
				flag = false;
				layer.alert('请上传税务登记证扫描件或照片');
			}
			if($("#yyzzPic").val()==null||$("#yyzzPic").val()==''){
				flag = false;
				layer.alert('请上传营业执照');
			}
			if($("#frPic").val()==null||$("#frPic").val()==''){
				flag = false;
				layer.alert('请上传法人身份照片');
			}
			return flag;
		}
		
		
	var audit=function(obj){
		layer.open({
		    type: 2,
		    title: '认证信息',
		    shadeClose: false,
		    fix: true, 
		    closeBtn: true,
		    shade: 0.6,
		    offset:50,
		    area: ['960px', '600px'],
		    content:"${ctx}/tenant/certifiedFeedback.ht?id="+obj.info.sysOrgInfoId+"&code="+obj.map.code+"&message="+encodeURI(encodeURI(obj.map.message))+"&status="+obj.status //iframe的url
		}); 
		
	}
	
	/* $(function(){
		var incoab=$("#incoab").val();
		var nameab=$("#nameab").val();
		var gszchab=$("#gszchab").val();
		var nsrsbhab=$("#nsrsbhab").val();
		var frsfzhmab=$("#frsfzhmab").val();
		var frsjhab=$("#frsjhab").val();
		if(incoab=='red'){
			//法人代表
			$("#incorporator").addClass("register2_red")
		}
		if(nameab=='red'){
			//公司名字
			$("#name").addClass("register2_red")
		}
		if(gszchab=='red'){
			//工商注册号
			$("#gszch").addClass("register2_red")
		}
		if(nsrsbhab=='red'){
			//纳税人识别号
			$("#nsrsbh").addClass("register2_red")
		}
		if(frsfzhmab=='red'){
			//法人身份证号
			$("#frsfzhm").addClass("register2_red")
		}
		if(frsjhab=='red'){
			//发热手机号
			$("#frsjh").addClass("register2_red")
		}
	})	 */
	 $(window.parent.document).find("#mainiframe").load(function(){
	
    	 var mainheight = $(document).height() + 30;
    	$(this).height(mainheight);
     });

	</script>
</head>
<body >
<!-- 
	<div>
      	<input type="hidden" id="incoab" value="${incorporator}">
      	<input type="hidden" id="nameab" value="${name}">
      	<input type="hidden" id="gszchab" value="${gszch}">
      	<input type="hidden" id="nsrsbhab" value="${nsrsbh}">
      	<input type="hidden" id="frsfzhmab" value="${frsfzhm}">
      	<input type="hidden" id="frsjhab" value="${frsjh}">
    </div>
-->
	<form id="infoForm" method="post" action="certified.ht"  class="signupForm2">
	         <input name="state" type="hidden" value="${info.state}">
	          <div id="wizard"  type="main">
								<input type="hidden" name="sysOrgInfoId" id="sysOrgInfoId" value="${info.sysOrgInfoId}" />
								
									<div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>企业名称</span>
				                        <input class="register_mainCon_con" id="name" name="name" maxlength="18" placeholder="2-15个数字,字母" maxlength="15" type="text" class="inputText" value="${info.name}"  readonly="readonly"/>
				                        <div class="tipinfo"></div>
				                     </div>
									<div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>简称</span>
				                        <input class="register_mainCon_con" id="abbreviationName" name="abbreviationName" maxlength="18" placeholder="2-15个数字,字母" maxlength="15" type="text" class="inputText" value="${info.abbreviationName}"  readonly="readonly" />
				                        <div class="tipinfo"></div>
				                     </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>工商注册号</span>
				                        <input class="register_mainCon_con" id="gszch" name="gszch" maxlength="18" placeholder="2-15个数字,字母" maxlength="15" type="text" class="inputText" value="${info.gszch}" readonly="readonly" />
				                        <div class="tipinfo"></div>
				                    </div>

									<div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>组织机构代码</span>
				                        <input class="register_mainCon_con" name="code" type="text"   value="${info.code}" id="code"  placeholder="2-15个数字,字母" readonly="readonly"/>
				                        <div class="tipinfo"></div>
				                    </div>
									
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>纳税人识别号</span>
				                        <input name="nsrsbh" type="text" class="register_mainCon_con" value="${info.nsrsbh}" id="nsrsbh" placeholder="2-15个数字,字母" maxlength="18"  value="${info.nsrsbh}"  readonly="readonly"/>
				                        <div class="tipinfo"></div>
				                    </div>	
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>法人代表</span>
				                        <input name="incorporator" type="text" value="${info.incorporator}" class="register_mainCon_con" maxlength="15" id="incorporator" readonly="readonly"
											value="${info.incorporator}" />
				                        <div class="tipinfo"></div>
				                    </div>
									
									  <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>法人身份证</span>
				                        <input name="frsfzhm" type="text" value="${info.frsfzhm}" placeholder="15或18位数字" class="register_mainCon_con" maxlength="18" id="frsfzhm" readonly="readonly"
											value="${info.frsfzhm}" />
				                        <div class="tipinfo"></div>
				                    </div>
									
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>法人手机号</span>
				                        <input name="frsjh" type="text" value="${info.frsjh}" class="register_mainCon_con" maxlength="11" id="frsjh" readonly="readonly"
											value="${info.frsjh}" />
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>营业执照注册号</span>
				                        <input name="yyzz" type="text" class="register_mainCon_con"  id="yyzz" readonly="readonly"
											value="${info.yyzz}" />
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>统一社会信用代码</span>
				                        <input name="creditCode" type="text" value="${info.creditCode}" class="register_mainCon_con"  id="creditCode" readonly="readonly"
											value="${info.creditCode}" />
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>企业类型</span>
				                        <ap:selectDB name="type" id="type"
										where="typeId=10000005420003" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.type }">
									</ap:selectDB>
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>经营地址</span>
				                              <ap:ajaxSelect srcElement="country" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="province1"/>
												<ap:selectDB name="province1" id="province1" defaultText="==请选择==" defaultValue=""
													where="itemKey='province'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.province1}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="province1" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="city1"/>
												<ap:selectDB name="city1" id="city1" 
													where="itemKey='city1'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.city1}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="city1" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="county1"/>
												<ap:selectDB name="county1" id="county1" 
													where="itemKey='county1'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.county1}">
												</ap:selectDB>
											<input id="ind5" type="hidden" value="${info.city1}">
											<input id="ind6" type="hidden" value="${info.county1}">
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>员工人数</span>
				                       <ap:selectDB name="scale" id="scale" where="typeId=10000005680006" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.scale}">
										</ap:selectDB>
										 <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width">邮编</span>
				                       <input class="text" id="postcode" name="postcode" value="${info.postcode}" maxlength="20" type="text" class="inputText" readonly="readonly"/>
				                       <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>企业邮箱</span>
				                        <input type="text" name="email" value="${info.email}" maxlength="60" style="width:200px;" class="inputText" readonly="readonly"/>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>联系人</span>
				                        <input name="connecter" type="text" class="text" value="${info.connecter }" maxlength="32" id="connecter" readonly="readonly" />
				                         <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>手机号码</span>
				                       <input type="text" name="tel" class="text" maxlength="11" value="${info.tel}" readonly="readonly"/>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>公司固话</span>
				                      <input type="text" value="${info.homephone}" name="homephone" class="text"  maxlength="32"  readonly="readonly"/>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>公司传真</span>
				                        <input name="fax" type="text" class="text" value="${info.fax}" id="fax" maxlength="32" readonly="readonly"/>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>注册地址</span>
				                       <ap:ajaxSelect srcElement="country" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="province"/>
												<ap:selectDB name="province" id="province" defaultText="==请选择==" defaultValue=""
													where="itemKey='province'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.province}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="province" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="city"/>
												<ap:selectDB name="city" id="city" 
													where="itemKey='city'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.city}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="city" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="county"/>
												<ap:selectDB name="county" id="county" 
													where="itemKey='county'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.county}">
												</ap:selectDB>
											<input id="ind3" type="hidden" value="${info.city}">
											<input id="ind4" type="hidden" value="${info.county}">
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>注册资本(万元)</span>
				                       <input name="regCapital" type="text" class="text" maxlength="64" id="regCapital" readonly="readonly"
											value="${info.regCapital}" />
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>注册资金币种</span>
				                       <input name="currencyFunds" type="text" class="text" maxlength="64" id="currencyFunds" readonly="readonly"
											value="${info.currencyFunds}" />
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>经营模式</span>
				                       <select name="model">
				                       <option value="1">生产型</option>
				                       <option value="2">贸易型</option>
				                       <option value="3">服务型</option>
				                       <option value="4">研发型</option>
				                       <option value="0">其他类型</option>
				                       </select>
				                        <div class="tipinfo"></div>
				                    </div>
				                    
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>主营行业</span>
				                     	<ap:selectDB name="industry" id="industry" where="parentId=10000003470025"  optionValue="itemValue" defaultText="==请选择==" defaultValue=""
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.industry}">
										</ap:selectDB>
										<ap:ajaxSelect srcElement="industry" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="industry2"/>
										<ap:selectDB name="industry2" id="industry2" 
											where="1!=1" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.industry2}">
										</ap:selectDB>
										<%-- <input id="ind1" type="hidden" value="${info.industry}"> --%>
										<input id="ind2" type="hidden" value="${info.industry2}">
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>主营产品</span>
				                      <input class="text" id="product" name="product" value="${info.product }" maxlength="56" type="text" class="inputText" validate="{required:true,maxlength:256}" />
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>年销售额</span>
				                      <ap:selectDB name="turnover" id="turnover" where="typeId=10000005640295" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.turnover}">
											</ap:selectDB>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list" style="width:80%">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>企业简介</span>
				                      <input id="info" name="info" type="hidden" value="${info.info}"/>
				                      <script class="postbox" type="text/plain"  name="html"  id="editor" position="center" style="width:99%;padding-left: 115px;">
                                      </script>
				                        <div class="tipinfo"></div>
				                    </div>
				                    
				                    <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="codePic" id="codePic" value="${info.codePic}"/>
									 	<div class="codePic-pic" >
									 	<c:if test="${not empty info.codePic}" >
									 		<a class="smallimage" href="${fileCtx}/${info.codePic}" rel="${fileCtx}/${info.codePic}" target="_blank"><img src="${fileCtx}/${info.codePic}" width="80" height="84"/></a>
									 	</c:if>
									 	</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>组织机构代码证扫描件或照片</span> 
				                        
				                    </div>	
				                    <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="nsrsbhPic" id="nsrsbhPic" value="${info.nsrsbhPic}"/>
									 	<div class="nsrsbhPic-pic" >
									 	<c:if test="${not empty info.nsrsbhPic}" >
									 		<a class="smallimage" href="${fileCtx}/${info.nsrsbhPic}" rel="${fileCtx}/${info.nsrsbhPic}" target="_blank"><img src="${fileCtx}/${info.nsrsbhPic}" width="80" height="84"/></a>
									 	</c:if>
									 	</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>税务登记证扫描件或照片</span> 
				                      
				                    </div>	
									 <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="yyzzPic" id="yyzzPic" value="${info.yyzzPic}"/>
									 	<div class="yyzzPic-pic" id="yyzzPic">
									 	<c:if test="${not empty info.yyzzPic}" >
									 		<a class="smallimage" href="${fileCtx}/${info.yyzzPic}" rel="${fileCtx}/${info.yyzzPic}" target="_blank"><img src="${fileCtx}/${info.yyzzPic}" width="80" height="84"/></a>
									 	</c:if>
									 	</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>营业执照</span> 
				                       
				                        
				                    </div>			
									 <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="frPic" id="frPic"  value="${info.frPic}"/>
									 	<div class="frPicDiv-pic" id="frPicDiv">
									 			<c:forEach items="${info.frPic}" var="it" varStatus="i">
												 	<a class="smallimage" href="${fileCtx}${it}" rel="${fileCtx}${it}" target="_blank"><img src="${fileCtx}${it}" width="80" height="84"/></a>
												</c:forEach>
												</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red"></span>法人身份证照</span> 
				                    </div>
									<div class="register_mainCon_list authentication_btn_mar" >
				                       <span class="register_mainCon_tit openAccount_titie_width"></span>
				                        <input type="button" class="register_mainCon_btn" value="返回" onclick="fanhui();" id="fanhui1" />
				                        <div class="tipinfo"></div>
				                    </div>
									
				                    <c:if test="${not empty info.axnStatus}">
				                    <input type="hidden" name="axnStatus" value="${info.axnStatus}">
				                    </c:if>				                    
						</div>								
	</form>
	<img href="" id="tong">
	<!-- </div>
	</div> -->
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
<script type="text/javascript">
	 ue= UE.getEditor('editor',{initialFrameHeight:280});
		var content = $("#info").val();
	 ue.ready(function(){
			ue.setContent(content);    
		})

   function fanhui(){
			//返回上一个页面并刷新
			//var i1 = parent.window.frames["mainiframe"];
		   // i1.onload();
			
			window.location.href="${ctx}/tenant/audit.ht";
		}
	 $(function(){    
	     var x = 22; 
	     var y = 20; 
	     $("a.smallimage").hover(function(e){ 
	             $("body").append('<p id="bigimage"><img src="'+ this.rel + '" alt="" /></p>'); 
	             $(this).find('img').stop().fadeTo('slow',0.5); 
	         widthJudge(e);    //调用宽度判断函数 
	         $("#bigimage").fadeIn('fast'); 
	     },function(){ 
	         $(this).find('img').stop().fadeTo('slow',1); 
	         $("#bigimage").remove(); 
	     });  
	     $("a.smallimage").mousemove(function(e){ 
	         widthJudge(e);    //调用宽度判断函数 
	     });  
	     function widthJudge(e){ 
	         var marginRight = document.documentElement.clientWidth - e.pageX; 
	         var imageWidth = $("#bigimage").width(); 
	         if(marginRight < imageWidth) 
	         { 
	             x = imageWidth + 22; 
	             $("#bigimage").css({top:(e.pageY - y ) + 'px',left:(e.pageX - x ) + 'px'}); 
	         }else{ 
	             x = 22; 
	             $("#bigimage").css({top:(e.pageY - y ) + 'px',left:(e.pageX + x ) + 'px'}); 
	         }; 
	     } 
	 });  
	
</script>
</body>
</html>
