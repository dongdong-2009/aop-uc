<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%-- <%@include file="/commons/global.jsp"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="fileCtx" value="https://oby0yx23h.qnssl.com"/>
<c:set var="fileCtx1" value="https://oby0yx23h.qnssl.com/"/>
<c:set var="CTX" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="ap" uri="/appleTag"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>编辑企业信息</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/uc/CustomValid.js"></script>
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
	<link href="${ctx}/styles/uc/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/style.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/scrollable.js"></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	<!-- 上传图片 -->
	<script type="text/javascript" src="${ctx}/js/upload/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/upload/cloudDialogUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/upload/uploadPreview.js"></script>

	<!-- ueditor -->
	<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.all.js"></script>
	<script type="text/javascript" href="${ctx}/ueditor142/lang/zh-cn/zh-cn.js" ></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/addCustomizeButton2.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.zoom.js"></script>
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


	/* styles unrelated to zoom */
		* {margin:0; padding:0; }
		#msg p { position:absolute; top:3px; right:28px; color:#555; font:bold 13px/1 sans-serif;} 
		
		/* optional icon style */
		.zoomIcon { 
			width:33px; 
			height:33px; 
			position:absolute; 
			top:0;
			right:0;
			background:url(${ctx}/resources/css/images/icon.png);
		}

		/* these styles are for the demo, and are not required for the plugin */
		.zoom {
			display:inline-block;
			position:relative;
		}

		.zoom img {
			display: block;
		}

		.zoom img::selection { background-color: transparent; }

		.zoom img:hover { cursor: url(${ctx}/resources/css/images/grab.cur), default; }
		.zoom img:active { cursor: url(${ctx}/resources/css/images/grabbed.cur), default; }
</style>

<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>

<script type="text/javascript">
		
$(function(){
	  $(".api_rightCon_tit").each(function(index){
	        $(this).toggle(
	          function(){
	        	  $(this).parent().parent().find(".api_right_style").attr("style","display:none");
	        	  $(this).parent().parent().find(".triangle").removeClass('open');
	              $(this).siblings(".api_right_style").attr("style","display:block");
	              $(this).find('.triangle').addClass('open');
	           },
	           function(){
	        	   if($(this).find('.triangle').hasClass('open')){
	        		  $(this).siblings(".api_right_style").attr("style","display:none");
	 	              $(this).find('.triangle').removeClass('open');
	        	   }else{
	        		  $(this).parent().parent().find(".api_right_style").attr("style","display:none");
	 	        	  $(this).parent().parent().find(".triangle").removeClass('open');
	        		  $(this).siblings(".api_right_style").attr("style","display:block");
	 	              $(this).find('.triangle').addClass('open'); 
	        	   }
	           })
	  	})
	})
	
</script>


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
	
	var wpd = $(window.parent.document);
	//alert(wpd);
	if(wpd){
		$(window.parent.document).find("#mainiframe").load(function(){
	    	 var mainheight = 800;
		    	$(this).height(mainheight);
		     })
	    }
	/*  $(window.parent.document).find("#mainiframe").load(function(){
	    	 var mainheight = 800;
	    	$(this).height(mainheight);
	     }); */
</script>


	<script type="text/javascript">
	
		$(function() {
			var currencyFunds=$("#currencyFunds").val();
			if(currencyFunds){
			     $("select[name='currencyFunds']").val(currencyFunds);
			}
			var sysOrgInfoId=$("#sysOrgInfoId").val();
			$(".sysId").click(function(){
				if($(this).text()=='显示'){
					$(this).prev("span").text(sysOrgInfoId);
					$(this).text('隐藏');
				}else{
					$(this).prev("span").html("******");
					$(this).text('显示');
				}
			});
			/* if(parent.layer){
				
			var index =  parent.layer.getFrameIndex(window.name);
				parent.layer.iframeAuto(index);
			
			} */
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
	        $("#typeIn").click(function(){
				 var invitedCode=$("#invititedCode").val();
				 //alert(invitedCode);
				 if(invitedCode=="" || invitedCode==null){
					 layer.alert("请输入邀请码");
					 return ;
				 }
				 var sysOrgInfoId="${info.sysOrgInfoId}";
				 $.ajax({
						type : 'POST',
						url : "${ctx}/tenant/updateInvitedCode.ht",
						data : {
							invitedCode : invitedCode,
							sysOrgInfoId : sysOrgInfoId
						},
						success : function(dataMap) {
							if (dataMap && dataMap.flag == "1") {
								layer.alert('邀请码对应企业不存在');
							} else if (dataMap.flag == "2") {
								/* layer.alert("邀请码补录成功",function(){
									window.location.href="${ctx}/tenant/baseInfo.ht";
								}); */
								layer.alert("邀请码补录成功");
							} else {
								layer.alert("邀请码补录失败");
							}
						},
						error : function(dataMap) {
							layer.alert("邀请码补录失败");
						}
					});
				 
	        });
	        
	     
	        $("#sub").click(function(){
		          firstClick(frm);
		        });
		     $("#sub").dblclick(function(){
		          firstClick(frm);
		     });
	        function firstClick(frm){
				//去掉所有的html标
	        	var str = UE.getEditor('editor').getContent();
				if(validForm()){
					$('#info').val(str);
					var aa=$('#info').val();
					if($("#logoPicView").find("img").length==0){
						layer.alert('请添加企业logo！');
						return;
					}
					//校验公司经营地址
					//校验省份
					var province1=$("#province1").val();
					var city1=$("#city1").val();
					var county1=$("#county1").val();
					   var type1 = document.getElementById("type").value;  
					    
					     if(type1==""||type1==null){
								layer.alert('请选择企业类型',{
								    skin: 'layui-layer-molv' //样式类名
								        ,closeBtn: 0
								    });
								return;
							}
					     
					     
					if(province1=='香港特别行政区'||province1=='澳门特别行政区'||province1=='台湾省'||province1=="北京市"||province1=="上海市"||province1=="重庆市"||province1=="天津市"){
						
					}else{
						if(province1==null||province1==''){
							layer.alert("请选择省份");
							return;
						}
						if(city1.indexOf("辖")>-1){
							
						}else{
							if(city1==null||city1==''){
								layer.alert("请选择城市");
								return;
							}
							 if(county1==null||county1==''){
								layer.alert("请选择地区");
								return;
							} 
						}
					}
					
					if(frm.valid()){
						
						if(aa.length > 10000){
							layer.alert('企业简介输入字数过多！',{
							    skin: 'layui-layer-molv' //样式类名
							        ,closeBtn: 0
							    });
							return;
						}
				
						var a=document.getElementById("quhaohomephone").value;
			        	var b=document.getElementById("homephone").value;		    
			        	var c=""+a+"-"+b+"";
			        	$("#allhomephone").val(c);
			        	var a=document.getElementById("quhaofax").value;
			        	var b=document.getElementById("fax").value;
			        	$("#allfax").val(a+"-"+b);
						frm.setHtmlData();
						frm.ajaxForm(options);
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
						},function(){
							
							window.location.reload();
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
						if(iv == salse2)
							s="selected='selected'";
						$('#sellArea2').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
		});
		
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
	<script type="text/javascript">
	$(function(){
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
		
		
		
	var state=$("#state").val();
	if(state=='0'){
		
		var str='';
		str+='<div class="register_mainCon_list">';
        str+='<span class="register_mainCon_tit1"><span class="register2_red">&nbsp;</span>';
        str+='<span class="register_mainCon_con"  style="border:none;" ></span></span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;font-size:16px;" id="sysId" ><img src="${ctx}/resources/images/notName.png" width="25px" height="25px"> &nbsp;&nbsp;&nbsp;您还未进行实名认证，请尽快认证，获得更好服务</span>';
        str+='<span class="  <div class="tipinfo"></div>';
        str+='</div>';
        str+='<div class="register_mainCon_list1" style="margin-top:0;">';
        str+='<span class="register_mainCon_tit"></span>';
        str+='<input type="button" class="register_mainCon_btn" value="去认证" id="sub1" style="width:160px;"/>';
        str+='<div class="tipinfo"></div>';
        str+='</div>';
		$("#msg").empty();
		$("#msg").html(str);
	}
		
	
	$("#sub1").click(function(){
		
		//window.open("http://localhost:8080/home.ht?menu=smrz");//本地环境
		
		//window.open("http://b2b.casicloud.com/home.ht?menu=smrz");//测试环境
	window.open("http://core.casicloud.com/home.ht?menu=smrz");//生产环境
	});
	});
	

	
	
	</script>
	
	<script type="text/javascript">
	function myfunction(){ 
	 var selectId = document.getElementById("type2.1"); 
	 var se= selectId.options[0];
     var selectValue = se.value; 
     $("#type2 option").each(function(){ //遍历全部option
         var txt = $(this).text(); //获取option的内容
    
         if(txt ==selectValue ) //如果不是“全部”
	     this.selected = true
     });
     
     var selectId = document.getElementById("type1.1");  
     var selectValue = selectId.options[0].value; 
     $("#type1 option").each(function(){ //遍历全部option
         var txt = $(this).text(); //获取option的内容
    
         if(txt ==selectValue ) //如果不是“全部”
	     this.selected = true
     });
     
}
</script>


<script type="text/javascript">
var CTX="${pageContext.request.contextPath}";
 function checkCode1(){
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
					$("input[name='invititedCode']").parent("div").find("font").replaceWith('<font color="red"><label for="invititedCode"  class="error" >您输入的公司不存在</label></font>');
				}
			},
			error : function(data){
				layer.alert("邀请码查询失败");
			}
		});
	}
	
} 
/*  var invititedCodeLength=0;
//为公司名框绑定触发事件
	$('#invititedCode').bind('input propertychange', function() {
		invititedCodeLength=$("#invititedCode").val().length;
	});
//查询企业号
function checkFocusCode(obj){console.info(obj)
	$(obj).addClass("item-focus");
	$(obj).removeClass("error");
	if(invititedCodeLength>0){
		$(obj).parent("div").find("font").children("label").html('');
	}
} */



</script>
<style type="text/css">

.register_mainCon_list {
    line-height: 30px;
    margin-bottom: 15px;
    font-size: 14px;
}
.register_mainCon_tit {
    width: 128px;
    text-align: right;
    display: inline-block;
    margin-left: -40px;
    color: #333;
    line-height: 30px;
}
.register_pattern {
    display: inline-block;
    width: 608px;
    height: 40px;
    line-height: 40px;
    color: #999;
    margin-left: -60px;
}
.register2_judge {
    display: inline-block;
    width: 60px;
    height: 40px;
    line-height: 1px;
    padding-left: 8px;
    color: #999;
}
form .label {
    width: 128px;
    float: left;
    margin-left: -180px;
    text-align: right;
    position: relative;
    _display: inline;
    font-size: 14px;
    font-weight:normal;
    color: #333;
}
form .cell1 {
    float: left;
    margin-left: -36px;
    margin-top: -10px;
    margin-bottom: 5px;
}
form .cell {
    float: left;
    margin-left: -36px;
    margin-bottom: 5px;
}
.contact-form a, .corp-form a {
    color: #1169c2;
    padding-right: 30px;
}

.contact-form, .corp-form {
    padding-bottom: 10px;
}
.api_rightCon_tit { padding-top: 12px;padding-bottom: 12px; margin-top: -5px; text-indent: 30px; position: relative; cursor: pointer; border-bottom: 1px solid #999;font-size: 16px; margin-bottom: 20px;}
.api_rightCon_tit .triangle { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: transparent transparent transparent #666; position: absolute; left: 13px; top: 20px;}
.api_rightCon_tit .triangle.open { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: #666 transparent transparent transparent; top: 20px; left: 10px; }
.api_rightCon_tit:hover{background:#eee;}
.api_right_text{
	height:auto;
	border:0px solid #e5e5e5;
	font-size:12px;}
/* 
.api_right_text{
	height:auto;
	border:0px solid #e5e5e5;
	text-indent: 28px;
	font-size:12px;} */
.register_pattern {
    display: inline-block;
    width: 608px;
    height: 40px;
    line-height: 40px;
    color: #999;
    margin-left: 0px;
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


.register_mainCon_con1 {
    border: 1px solid #e5e5e5;
    color: #343434;
    font-size: 16px;
    height: 38px;
    line-height: 38px;
    padding-left: 10px;
    width: 200px;
}

.register_mainCon_btn1 {
    background: #ff771d none repeat scroll 0 0;
    border: medium none;
    border-radius: 5px;
    color: #fff;
    cursor: pointer;
    font-size: 14px;
    height: 30px;
    text-align: center;
    width: 50px;
}
  span.register_mainCon_con{
   border: none;
 }
 
.register_mainCon_tit1 {
    width: 128px;
    text-align: right;
    display: inline-block;
    margin-left: -10px;
    color: #333;
    line-height: 30px;
}
.register_mainCon_list1 {
    margin-top: 180px;
    margin-left: 200px;
    width: 200px;
    line-height: 30px;
    float: left;
    margin-bottom: 15px;
    font-size: 14px;
}

.register_mainCon_btn {
    width: 160px;
    height: 40px;
    font-size: 18px;
    cursor: pointer;
    background: #ff771d;
    color: #fff;
    border: none;
    line-height: 40px;
    text-align: center;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -ms-border-radius: 5px;
    -moz-border-radius: 5px;
    -o-border-radius: 5px;
}
</style>
</head>
<body  onload="myfunction()">
<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>在中国大陆注册并有效存续的独立企业法人或事业单位，维护如下资料。所填写的企业信息必须真实准确，其中*表示该项必填。</span></p>
</div>
	<form id="infoForm" method="post" action="saveTenantBase.ht">
		<div id="wizard"  type="main">
					 <h3></h3>
					 		<input type="hidden" name="openId" id="openId" value="${info.openId}">
					         <input type="hidden" name="sysOrgInfoId" id="sysOrgInfoId" value="${info.sysOrgInfoId}">
							<input type="hidden" name="flaglogo" value="${info.flaglogo}" />
		<div class="api_rightCon">
	        <div class="api_rightCon_tit">
		        <i class="triangle"></i>
		       		 基本信息
	        </div>
        	<div class="api_right_text api_right_style" style="display: none;">
        	   <div  title="基本信息"  style="height:260px; overflow:auto;" >
        					<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业号</span>
		                        <span class="register_mainCon_con"  style="border:none;" >******</span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" id="sysId" class="sysId">显示</span>
		                        <div class="tipinfo"></div>
		                    </div>
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业名称</span>
		                        <span class="register_mainCon_con">${info.name}</span>
		                        <input  id="name" name="name"  type="hidden" value="${info.name}"/>
		                        <div class="tipinfo"></div>
		                    </div>
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit">注册邀请码</span>
		                       <c:if test="${ empty info.invititedCode}"><!--onfocus="checkFocusCode(this)" -->
		                        <input type="text" class="register_mainCon_con" placeholder="请输入邀请码" name="invititedCode" id="invititedCode" onblur="checkCode1();" onfocus="checkFocusCode(this)" />
		                        <font color="red">若有企业邀请码请填写</font>
		                       </c:if>
		                       
		                       <c:if test="${ not empty info.invititedCode}">
		                           <span class="register_mainCon_con">${info.invititedCode}</span>
		                       </c:if>
		                       
		                        <div class="tipinfo"></div>
		                    </div>
		           <div class="register_mainCon_list" style="margin-bottom:48px;">
                        <div class="register1_list_left">
                                                       
                        </div>
                      
                       
                    </div>  
		                    
		                    
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业简称</span>
		                        <input class="register_mainCon_con" id="abbreviationName" name="abbreviationName" maxlength="18" placeholder="请输入简称" maxlength="15" type="text" class="inputText" value="${info.abbreviationName}" validate="{required:true,specialChar:true}" />
		                        <div class="tipinfo"></div>
		                    </div>
		                    <div class="row">
										<div class="label"><span class="register2_red">*</span>企业logo</div>
										<div class="cell1">
											<input type="hidden" name="logo" id="logo" value="${info.logo }"/>
											<input type="hidden" name="state" id="state" value="${info.state  }"/>
											<div class="addproduct-pic" id="logoPicView">
												<c:if test="${not empty info.logo}" >
													<img src="${fileCtx}/${info.logo}" width="80" height="84" />
												</c:if>
											</div>
											<a href="javascript:openImageDialog('logo');" class="btn-ctrl" 
											id="addProductPic" js_tukubtn="true">添加企业logo</a>
											<span style="color: red;">&nbsp;&nbsp;&nbsp;(建议上传小于400*480像素的图片)</span>
										</div>
									</div>
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>主营行业</span>
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
		                        <div class="tipinfo"></div>
		                    </div>
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red"></span>经营范围</span>
										<ap:selectDB name="manageRange" id="manageRange" where="typeId=10000005510001" optionValue="itemValue"
												optionText="itemName" table="SYS_DIC"
												selectedValue="${info.manageRange}">
										</ap:selectDB>
							</div>
				               <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>联系人姓名</span>
		                        <input name="connecter" type="text" class="register_mainCon_con" id="connecter" maxlength="32"  validate="{required:true,maxlength:32}" value="${info.connecter}" />
		                        <div class="tipinfo"></div>
		                    </div>
				            <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>联系人手机</span>
		                        <input type="text"  name="tel" class="register_mainCon_con" maxlength="11"  validate="{required:true,phone:true}" value="${info.tel}"/>
		                        <div class="tipinfo"></div>
		                    </div>
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业固定电话</span>
		                       
		                        <input type="text"  class="register_mainCon_con" maxlength="5" style="width:50px"  validate="{required:true,digits:true,rangelength:[3,5]}" id="quhaohomephone" value="${fn:substringBefore(info.homephone,'-')}">-
		                        <input type="text"  class="register_mainCon_con"  maxlength="8"  style="width:283px" validate="{required:true,tel:true}" id="homephone" value="${fn:contains(info.homephone,'-')?fn:substringAfter(info.homephone,'-'):(info.homephone)}"/>
		                        <input type="hidden" name="homephone" id="allhomephone">
		                        <div class="tipinfo"></div>
		                    </div>
							 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业邮箱</span>
		                        <input type="text" name="email"  style="width:200px;" maxlength="32" class="register_mainCon_con" validate="{required:true,email:true,maxlength:32}" value="${info.email}"/>
		                        <div class="tipinfo"></div>
		                     </div>
							 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit"><span class="register2_red">*</span>公司传真</span>
				                         <input type="text" class="register_mainCon_con" maxlength="5" style="width:50px" id="quhaofax" validate="{required:true,maxlength:32,digits:true,rangelength:[3,5]}"   value="${fn:substringBefore(info.fax,'-')}">-
				                        <input  type="text" class="register_mainCon_con" value="${fn:contains(info.fax,'-')?fn:substringAfter(info.fax,'-'):info.fax}" id="fax" style="width:283px" maxlength="8" validate="{required:true,maxlength:8,fax:true}" />
				                        <input name="fax" type="hidden" id="allfax">
				                        <div class="tipinfo"></div>
				                    </div>
							  <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit">邮编</span>
				                       <input class="register_mainCon_con" id="postcode" name="postcode" value="${info.postcode}" maxlength="20" type="text" class="inputText" validate="{required:false,maxlength:20,specialChar:true}"/>
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
						        		<input type="radio" name="isPublic" checked="checked" class="radio" value="1" />&nbsp;是
						        		</p>
								      <p class="register2_judge">
										<input type="radio" name="isPublic" class="radio" value="0"/>&nbsp;否
										</p>
								    </c:if>
								     <div class="tipinfo"></div>
		                    </div>
        	</div>
        	</div>
        </div>
							
					
		<div class="api_rightCon">
	        <div class="api_rightCon_tit">
		        <i class="triangle"></i>
		       		 企业详细信息
	        </div>
        	<div class="api_right_text api_right_style" style="display: none;">
					<div  title="企业详细信息"  style="height:260px; overflow:auto;">
						       <div class="register_mainCon_list">
			                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业号</span>
			                        <span class="register_mainCon_con"  style="border:none;" >******</span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" id="sysId" class="sysId">显示</span>
			                        <div class="tipinfo"></div>
			                    </div>
							    <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业名称</span>
					                        <span class="register_mainCon_con">${info.name}</span>
					                        <div class="tipinfo"></div>
					              </div>
					          <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>企业类型</span>
		                        
		                        
		                     <%--     <ap:selectDB name="type1" id="type1"
										where="a.parentId=b.parentId and a.itemValue='test1.2.1'" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										>
								</ap:selectDB> --%>
		                         <ap:selectDB name="" id="type1" defaultText="==请选择==" defaultValue=""
									where="parentId=10000005420003" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC">
								</ap:selectDB><span style="display: none;">
								
								<ap:selectDB name="" id="type1.1"
									where=" dicId=(select parentId from SYS_DIC where  dicId=(select parentId from SYS_DIC where itemValue='${info.type }' and parentId < 1000))" optionValue="itemName"
										optionText="itemName" table="SYS_DIC" >
								</ap:selectDB></span>
		                        
		                        <ap:ajaxSelect srcElement="type1" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="type2"/>
		                        
		                        
		                        
		                        
		                        
		                        
		                         <ap:selectDB name="" id="type2"
									where="parentId= (select parentId from SYS_DIC where  dicId=(select parentId from SYS_DIC where itemValue='${info.type }' and parentId < 1000))" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC">
								</ap:selectDB><span style="display: none;">
								<ap:selectDB name="" id="type2.1"
									where=" dicId=(select parentId from SYS_DIC where itemValue='${info.type } ' and parentId < 1000)" optionValue="itemName"
										optionText="itemName" table="SYS_DIC" >
								</ap:selectDB></span>
		                        
		                          <ap:ajaxSelect srcElement="type2" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="type"/>
		                        
		                        <ap:selectDB name="type" id="type"
										where="parentId=(select parentId from SYS_DIC where itemValue='${info.type } ' and parentId < 1000 )" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.type }">
									</ap:selectDB>
		                        <div class="tipinfo"></div>
		                       </div>
					          <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>经营模式</span>
		                        <p class="register_pattern">
		                        	<input type="hidden" id="ck_mod" value="${info.model }">
		                            <label><input type="checkbox" name="model" id="model1" class="ck_model" value="1"/>生产型</label> 
		                            <label><input type="checkbox" name="model" id="model2" class="ck_model" value="2"/>贸易型</label> 
		                            <label><input type="checkbox" name="model" id="model3" class="ck_model" value="3"/>服务型</label> 
		                            <label><input type="checkbox" name="model" id="model4" class="ck_model" value="4"/>研发型 </label>
		                            <label><input type="checkbox" name="model" validate="{required:true}" tipId="errorA" id="model0" class="ck_model" value="0"/>其他类型</label>
		                        </p>
		                        <div class="tipinfo"></div>
		                       </div>
					           <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>主营产品</span>
		                        <input class="register_mainCon_con" id="product" name="product"  type="text" validate="{required:true,maxlength:60}" value="${info.product}"/>
		                        <div class="tipinfo"></div>
		                    </div>
					           <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>经营地址</span>
				                             
												<ap:selectDB name="province1" id="province1" defaultText="==请选择==" defaultValue=""
													where="itemKey='province'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedText="${info.province1}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="province1" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="city1"/>
												<ap:selectDB name="city1" id="city1" defaultText="==请选择==" defaultValue=""
													where="itemKey='city'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedText="${info.city1}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="city1" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="county1"/>
												<ap:selectDB name="county1" id="county1" defaultText="==请选择==" defaultValue=""
													where="itemKey='county'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedText="${info.county1}">
												</ap:selectDB>
											<input id="ind5" type="hidden" value="${info.city1}">
											<input id="ind6" type="hidden" value="${info.county1}">
				                        <div class="tipinfo"></div>
				                    </div>
					         <div class="register_mainCon_list" style="width: 500px;">
		                        <span class="register_mainCon_tit"><span class="register2_red"></span>&nbsp;</span>
		                        <input class="text" id="address1" name="address1"  type="text" validate="{required:true,maxlength:256}" value="${info.address1}"/>
		                        <div class="tipinfo"></div>
		                      </div>
					            <div class="row">
										<div class="label">
											企业简介
										</div>
										<div class="cell" style="width:100%">
											 <input id="info" name="info" type="hidden" value="${info.info}"></input>
											<script class="postbox" type="text/plain"  name="html"  id="editor" position="center" style="overflow:auto; width:99%;">
												
										</script>
										</div>
									</div>
					             	<div class="row">
										<div class="label">
											企业注册时间
										</div>
										<div class="cell">
											<input type="text" id="registertime" name="registertime" value="<fmt:formatDate value='${info.registertime}' pattern='yyyy-MM-dd'/>" class="inputText date " validate="{date:true}" />
										</div>
									</div>
					            <div class="row">
										<div class="label">
											企业所在地
										</div>
										<div class="cell">
											<ap:selectDB name="country" id="country" where="parentId=10000000180001" optionValue="itemValue"
												optionText="itemName" table="SYS_DIC"
												selectedValue="${info.country}">
											</ap:selectDB>
											<ap:ajaxSelect srcElement="country" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="province"/>
											<ap:selectDB name="province" id="province" defaultText="==请选择==" defaultValue=""
												where="itemKey='province'" optionValue="itemValue"
												optionText="itemName" table="SYS_DIC"
												selectedValue="${info.province}">
											</ap:selectDB>
											<ap:ajaxSelect srcElement="province" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="city"/>
											<ap:selectDB name="city" id="city" defaultText="==请选择==" defaultValue=""
												where="itemKey='city'" optionValue="itemValue"
												optionText="itemName" table="SYS_DIC"
												selectedValue="${info.city}">
											</ap:selectDB>
											<input id="ind3" type="hidden" value="${info.province}">
											<input id="ind4" type="hidden" value="${info.city}">
										</div>
									</div>
					         <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red"></span>&nbsp;</span>
		                       <input class="text" id="address" name="address" value="${info.address}" maxlength="128" type="text" class="inputText" validate="{required:false,maxlength:128}"/>
		                        <div class="tipinfo"></div>
		                      </div>
					           <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>注册资本</span>
		                       <input name="regCapital" type="text" class="text" maxlength="64" id="regCapital" validate="{required:false,maxlength:64}"
											value="${info.regCapital}" />
		                        <div class="tipinfo"></div>
		                    </div>
					                          <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>注册资金币种</span>
					                          <select name="currencyFunds">
							                       <option value="1">人民币</option>
							                       <option value="2">美元</option>
							                       <option value="0">其他</option>
						                       </select>
				                        <div class="tipinfo"></div>
				                        <input id="currencyFunds" value="${info.currencyFunds}" type="hidden">
				                    </div>
					         <div class="register_mainCon_list">
		                       <span class="register_mainCon_tit"><span class="register2_red"></span>企业品牌</span>
		                     <input name="brand" type="text" class="text" id="brand" maxlength="64" validate="{required:false,maxlength:64}"
											value="${info.brand}" />
		                        <div class="tipinfo"></div>
		                    </div>
					           <div class="register_mainCon_list">
		                       <span class="register_mainCon_tit"><span class="register2_red"></span>公司网址</span>
		                    	<input class="text" id="website" name="website" value="${info.website}" maxlength="64" type="text" class="inputText" validate="{required:false,url:true,maxlength:64}"/>
		                        <div class="tipinfo"></div>
		                    </div>
					           <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit"><span class="register2_red">*</span>年销售额</span>
				                      <ap:selectDB name="turnover" id="turnover" where="typeId=10000005640295" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.turnover}" >
											</ap:selectDB>
				                        <div class="tipinfo"></div>
				               </div>
				                
					            <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit"><span class="register2_red">*</span>员工人数</span>
				                       <ap:selectDB name="scale" id="scale" where="typeId=10000005680006" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.scale}">
										</ap:selectDB>
										 <div class="tipinfo"></div>
				                    </div>
					          	<div class="row">
										<div class="label">
											企业占地面积
										</div>
										<div class="cell">
											<input name="area" type="text" class="text" id="area" maxlength="64" validate="{required:false,maxlength:64,number:true}" 
											value="${info.area}" />
										</div>
									</div>
					          <div class="row">
										<div class="label">
											主要客户群体
										</div>
										<div class="cell">
											<input name="clients" type="text" class="text" id="clients" maxlength="64" validate="{required:false,maxlength:64}" 
											value="${info.clients}" />
										</div>
									</div>
					          <div class="row">
										<div class="label">
											主要销售区域
										</div>
										<div class="cell">
											<ap:selectDB name="sellArea" id="sellArea" where="parentId=10000005610078" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.sellArea}">
											</ap:selectDB>
											<ap:ajaxSelect srcElement="sellArea" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="sellArea2"/>
											<ap:ajaxSelect srcElement="" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement=""/>
											<ap:selectDB name="sellArea2" id="sellArea2" 
												where="itemKey='sellArea2' and parentId != 10000005610078" optionValue="itemValue"
												optionText="itemName" table="SYS_DIC"
												selectedValue="${info.sellArea2}">
											</ap:selectDB>
											<input type="hidden" id="inpu2" value="${info.sellArea2}">
										</div>
								</div>
									<div class="row">
										<div class="label">
											企业二维码
										</div>
										<div class="cell">
											<input type="hidden" name="QRcode" id="QRcode" value="${info.QRcode }"/>
											<div class="addQRcode-pic" id="QRcodeView">
												<c:if test="${not empty info.QRcode}" >
													<img src="${fileCtx}/${info.QRcode}" width="80" height="84" />
												</c:if>
											</div>
											<!-- <a href="javascript:openImageDialog('QRcode');" class="btn-ctrl" -->
											<a href="javascript:openImageDialog('QRcode');" class="btn-ctrl"
													id="addProductPic" js_tukubtn="true">添加企业二维码</a>
											<span style="color: red;">&nbsp;&nbsp;&nbsp;(建议上传小于400*480像素的图片)</span>
										</div>
									</div>
									  <div class="row">
										<div class="label">
											质量管理体系
										</div>
										<div class="cell">
											<input name="qualityControl" type="text" class="text" maxlength="64" id="qualityControl" validate="{required:false,maxlength:64}"
											value="${info.qualityControl}" />
										</div>
									</div>
									  <div class="row">
										<div class="label">
											年出口额
										</div>
										<div class="cell">
											<input name="exportFore" type="text" class="text" id="exportFore" maxlength="64" validate="{required:false,maxlength:64}"
											value="${info.exportFore}" />
										</div>
									</div>
									  <div class="row">
										<div class="label">
											年进口额
										</div>
										<div class="cell">
											<input name="importFore" type="text" class="text" id="importFore" maxlength="64" validate="{required:false,maxlength:64}"
											value="${info.importFore}" />
										</div>
									</div>
									  	<div class="row">
										<div class="label">
											企业导航图片
										</div>
										<div class="cell">
											<input type="hidden" id="showimage" name="showimage" value="${info.showimage}"/>
											<div class="addqydhtp-pic" id="pic_showImage">
												<c:if test="${not empty info.showimage}">
													<img src="${fileCtx}/${info.showimage}" width="260" height="80" />
												</c:if>
											</div>
											<!-- <a href="javascript:openImageDialog('dhpic');" class="btn-ctrl" -->
											<a href="javascript:openImageDialog('showimage');" class="btn-ctrl"
													id="add_dhPic" js_tukubtn="true">添加导航图片</a>
											<a href="javascript:deleteIcon('dhpic');" class="btn-ctrl"
													id="add_dhPic" js_tukubtn="true">删除导航图片</a>
											<span style="color: red;">&nbsp;&nbsp;&nbsp;(建议上传小于400*480像素的图片)</span>
										</div>
									</div>
						</div>
						</div>
						</div>
				
				<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle"></i>
				       		实名信息
			        </div>
			        <div class="api_right_text api_right_style" style="display: none;">     
		                    <input id="accountStatsus" value="${info.accountStatsus}" type="hidden">
		               <div title="实名信息" style="height:400px; overflow:auto;" id="msg">
		                  <div class="register_mainCon_list">
			                        <span class="register_mainCon_tit1"><span >&nbsp;</span>&nbsp;</span>
			                        <c:choose>
			                            <c:when test="${info.state eq 2}">
			                                  <c:if test="${info.accountStatsus eq 1}">
			                                       <span class="register_mainCon_con"  style="border:none;" ></span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" ><img src="${ctx}/resources/images/wait.png" width="25px" height="25px"> &nbsp;&nbsp;&nbsp;您的实名认证信息已提交,请耐心等待审核结果</span>
			                                  </c:if>
			                                  <c:if test="${empty info.accountStatsus}">
			                                       <span class="register_mainCon_con"  style="border:none;" ></span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" ><img src="${ctx}/resources/images/notName.png" width="25px" height="25px"> &nbsp;&nbsp;&nbsp;您还未进行实名认证，请尽快认证，获得更好服务</span>
			                                  </c:if>
			                            </c:when>
			                             <c:when test="${info.state eq 1}">
			                              <span class="register_mainCon_con"  style="border:none;" ></span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" ><img src="${ctx}/resources/images/notPass.png" width="25px" height="25px"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${reason}</span>
			                            </c:when>
			                             <c:when test="${info.state eq 6}">
			                              <span class="register_mainCon_con"  style="border:none;" ></span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" ><img src="${ctx}/resources/images/dongjie.png" width="25px" height="25px"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的信息已冻结，请耐心等待审核</span>
			                            </c:when>
			                        </c:choose>
			                        
			                        <div class="tipinfo"></div>
			                    </div>
						       <div class="register_mainCon_list">
			                        <span class="register_mainCon_tit1"><span class="register2_red">*</span>企业号</span>
			                        <span class="register_mainCon_con"  style="border:none;" >******</span>&nbsp;&nbsp;&nbsp;<span style="cursor:pointer;" id="sysId" class="sysId">显示</span>
			                        <div class="tipinfo"></div>
			                    </div>
							    <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red">*</span>企业名称</span>
					                        <span class="register_mainCon_con">${info.name}</span>
					                        <div class="tipinfo"></div>
					              </div>
					           <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span>是否三证合一</span>
					                        <span class="register_mainCon_con"><c:if test="${info.isThreeInOne eq 1}">是</c:if><c:if test="${info.isThreeInOne eq 0}">否</c:if></span>
					                        <div class="tipinfo"></div>
					              </div>
					           <div class="register_mainCon_list" <c:if test="${info.isThreeInOne eq '1'}">style="display:none;"</c:if> >
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span>工商注册号</span>
					                        <span class="register_mainCon_con">${info.gszch}</span>
					                        <div class="tipinfo"></div>
					              </div>
					            <div class="register_mainCon_list" <c:if test="${info.isThreeInOne eq '1'}">style="display:none;"</c:if>>
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span>组织机构代码</span>
					                        <span class="register_mainCon_con">${info.code}</span>
					                        <div class="tipinfo"></div>
					              </div>
					            <div class="register_mainCon_list" <c:if test="${info.isThreeInOne eq '1'}">style="display:none;"</c:if>>
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span> 纳税人识别号</span>
					                        <span class="register_mainCon_con">${info.nsrsbh}</span>
					                        <div class="tipinfo"></div>
					              </div>
					            <div class="register_mainCon_list" <c:if test="${info.isThreeInOne eq '1'}">style="display:none;"</c:if>>
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span> 营业执照注册</span>
					                        <span class="register_mainCon_con">${info.yyzz}</span>
					                        <div class="tipinfo"></div>
					              </div>
					            <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span> 统一社会信用代码</span>
					                        <span class="register_mainCon_con">${info.creditCode}</span>
					                        <div class="tipinfo"></div>
					              </div>
					             <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span> 法人代表姓名</span>
					                        <span class="register_mainCon_con"><ap:textTip value="${info.incorporator}" max="1" more="..."/></span>
					                        <div class="tipinfo"></div>
					              </div>
					             <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span> 法人身份证号</span>
					                       <span class="register_mainCon_con"><ap:textTip value="${info.frsfzhm}" max="3" more="..."/> ${fn:substring(info.frsfzhm,(fn:length(info.frsfzhm)-3),-1)}</span>
					                        <div class="tipinfo"></div>
					              </div>
					             <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span>法人手机号</span>
					                        <span class="register_mainCon_con">${info.frsjh}</span>
					                        <div class="tipinfo"></div>
					              </div>
					             <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span> 申请联系人</span>
					                        <span class="register_mainCon_con">${info.connecter}</span>
					                        <div class="tipinfo"></div>
					              </div>
					             <div class="register_mainCon_list">
					                        <span class="register_mainCon_tit1"><span class="register2_red"></span>联系人手机号</span>
					                        <span class="register_mainCon_con">${info.tel}</span>
					                        <div class="tipinfo"></div>
					              </div>
					             <div class="register_mainCon_list1" style="margin-top:-15px;<c:if test='${info.isThreeInOne eq 1}'> display:none;</c:if>">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>税务登记</span> 
									 	<div class="nsrsbhPic-pic" style="width:150px;height:150px;margin-left:100px;">
									 	<c:if test="${not empty info.nsrsbhPic}" >
									 	<span class='zoom' >
									 		<img src="${fileCtx}/${info.nsrsbhPic}" width="150px" height="150px" onerror="this.onerror=null;this.src='${fileCtx1}/${info.nsrsbhPic}'"/>
									 	</span>
									 	</c:if>
									 	</div>
				                    </div>	
					           <div class="register_mainCon_list1" style="margin-top:-15px;<c:if test='${info.isThreeInOne eq 1}'>display:none;</c:if>">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>组织机构代码证</span> 
									 	<div class="codePic-pic" style="width:150px;height:150px;margin-left:100px;">
									 	<c:if test="${not empty info.codePic}" >
									 	 <span class='zoom' >
									 		<img src="${fileCtx}/${info.codePic}" width="150px" height="150px" onerror="this.onerror=null;this.src='${fileCtx1}/${info.codePic}'"/>
									 	 </span>
									 	</c:if>
									 	</div>
				               </div>
					           <div class="register_mainCon_list1" style="margin-top:-20px;">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>营业执照</span> 
									 	<div class="yyzzPic-pic" id="yyzzPic" style="width:150px;height:150px;margin-left:100px;">
									 	<c:if test="${not empty info.yyzzPic}" >
									 	<span class='zoom' >
									 		<img src="${fileCtx}/${info.yyzzPic}" width="150px" height="150px"  onerror="this.onerror=null;this.src='${fileCtx1}/${info.yyzzPic}'"/>
									 	</span>
									 	</c:if>
									 	</div>
				               </div>	
									 <div class="register_mainCon_list1" style="margin-top:-20px;">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>法人身份证照</span> 
									 	<div class="frPicDiv-pic" id="frPicDiv" style="width:310px;height:150px;margin-left:100px;">
									 			<c:forEach items="${info.frPic}" var="it" varStatus="i">
									 				<span class='zoom' >
												 	<img src="${fileCtx}${it}" width="150px" height="150px"  onerror="this.onerror=null;this.src='${fileCtx1}/${it}'"/>
												 	</span>
												</c:forEach>
												</div>
				                    </div>
					      <c:if test="${info.state eq 5 or info.state eq 1}">
				           <div class="register_mainCon_list1" style="margin-top:0;">
		                        <span class="register_mainCon_tit"></span>
		                        <input type="button" class="register_mainCon_btn"  value="去修改" id="sub1" />
		                        <div class="tipinfo"></div>
		                    </div>
		                  </c:if>
		               
		               
		               
		               
		               </div>
		               </div>
			     </div>
		      <div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle"></i>
				       		 资质荣誉
			        </div>
        				<div class="api_right_text api_right_style" style="display: none;">     
		               <div title="资质荣誉" style="height:260px; overflow:auto;">
								<table class="table-grid table-list" border="0" cellpadding="1" cellspacing="1" id="aptitude" formType="window" type="sub">
									<tr>
										<td colspan="7">
											<div class="group" align="left">
									   			<a class="link" href="javascript:addForm();">添加</a>
								    		</div>
								    		<div align="center">
											企业资质认证信息表
								    		</div>
							    		</td>
									</tr>
									<tr>
										<th>所属企业</th>
										<th>证书分类</th>
										<th>发证机构</th>
										<th>生效日期</th>
										<th>截止日期</th>
										<th>证书扫描件</th>
										<th>操作</th>
									</tr>
									<c:forEach items="${aptitudeList}" var="aptitudeItem" varStatus="status">
									    <tr type="subdata">
										    <td style="text-align: center" title="${info.name}" name="name">
										    	<ap:textTip value="${info.name}" max="8" more=".."/>
									    	</td>
										    <td style="text-align: center" name="cateType">${aptitudeItem.cateType}</td>
										    <td style="text-align: center" title="${aptitudeItem.cateOrg}" name="cateOrg">
										  	 	<ap:textTip value="${aptitudeItem.cateOrg}" max="8" more=".."/>
										    </td>
											<td style="text-align: center" name="inureDate"><fmt:formatDate value='${aptitudeItem.inureDate}' pattern='yyyy-MM-dd'/></td>								
										    <td style="text-align: center" name="endDate">${aptitudeItem.endDate}</td>
										    <td style="text-align: center" name="catePic">
											    <c:if test="${ not empty aptitudeItem.catePic}">
											    	<img src="${fileCtx}/${aptitudeItem.catePic} " width="80" height="84" />
											    </c:if>
										    </td>
										    <td style="text-align: center">
										    	<a href="javascript:void(0)" onclick="deleteTr(this)" class="link">删除</a>
										    </td>
											<input type="hidden" name="infoId" value="${aptitudeItem.infoId}"/>
											<input type="hidden" name="cateType" value="${aptitudeItem.cateType}"/>
											<input type="hidden" name="cateOrg" value="${aptitudeItem.cateOrg}"/>
										    <input type="hidden" name="inureDate" value="<fmt:formatDate value='${aptitudeItem.inureDate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
											<input type="hidden" name="endDate" value="${aptitudeItem.endDate}"/>
											<input type="hidden" name="catePic" value="${aptitudeItem.catePic}"/>
									    </tr>
									</c:forEach>
									<tr type="append">
								    	<td style="text-align: center" name="name"></td>
								    	<td style="text-align: center" name="cateType"></td>
								    	<td style="text-align: center" name="cateOrg"></td>
										<td style="text-align: center" name="inureDate"></td>								
								    	<td style="text-align: center" name="endDate"></td>
								    	<td style="text-align: center" name="catePic"></td>
								    	<td style="text-align: center">
								    		<a href="javascript:void(0)" onclick="deleteTr(this)" class="link">删除</a>
								    	</td>
								    	<input type="hidden" name="infoId" value=""/>
								    	<input type="hidden" name="cateType" value=""/>
								    	<input type="hidden" name="cateOrg" value=""/>
								    	<input type="hidden" name="inureDate" value="" class="inputText date"/>
								    	<input type="hidden" name="endDate" value=""/>
								    	<input type="hidden" name="catePic" value=""/>
							 		</tr>
							    </table>
							</div>     
					</div>     
			</div>    
			                 <div class="register_mainCon_list1">
		                        <span class="register_mainCon_tit"></span>
		                        <input type="button" class="register_mainCon_btn" value="提交" id="sub" />
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
	//上传图片	
	var dd = null;
	function openImageDialog(type){
		dd = type;
		$.cloudDialog.imageDialog({contextPath:"${ctx}"},type);
	} 
	
	/* function openImageDialog(type){
		alert(type);
		$.cloudDialog.imageDialog({contextPath:"${ctx}"},type);
	} */
	/* function imageDialogCallback(data){
		if(data.length > 0){
			_callbackImageUploadSuccess(data[0].filePath,dd);
		}
	} */
	function imageDialogCallback(data){
		for(index in data){
			_callbackImageUploadSuccess(data[index].filePath,data[index].fileName,dd);
		}
	}

	function _callbackImageUploadSuccess(path,name,type){
		
		if($(".proPicture").length<5){
			var item = '<div style="float:left;margin-right: 2px;">';
			item += "<img src='${fileCtx}/"+path+"' width='80' height='84' /></br>";
			item += "<input type='hidden' value='"+path+"' class='proPicture' name='"+type+"' />";
			item += "<input type='button' value='删除' onclick='deleteImg(this)' />";
			item += "</div>";
			if(type=='logo'){//上传企业logo
			 	$(".addproduct-pic").empty();
				$("#logoPicView").append(item);
			}else if(type=='QRcode'){
			 	$(".addQRcode-pic").empty();
				$("#QRcodeView").append(item);
			}else if(type=='qyzz'){//上传资质
			 	$(".qyzzView_pic").empty();
				$("#qyzzView").append(item);
			}else if(type=='showimage'){//上传导航栏
				$(".addqydhtp-pic").empty();
				$("#pic_showImage").append(item);
			}else if(type=='0'){//上传导航栏
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
			}else{
				var item = '<img src="${fileCtx}/'+path+'"/>';
				UE.getEditor('editor').setContent(item, true);	
			}
			/* parent.getIframeHeight(); */
		}else{
			layer.alert("最多只能上传5张图片",{icon:2});
		}

	}
	
	
	function deleteImg(obj){
		$(obj).parent().remove();
	}
	
	//上传图片回调函数
	/* function _callbackImageUploadSuccess(path,type){
		if(type=='logo'){//上传企业logo
			$("#logo").val(path);
		 	var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
		 	$(".addproduct-pic").empty();
			$("#logoPicView").append(item);
		}else if(type=='QRcode'){
			$("#QRcode").val(path);
		 	var item = $('<img src="${fileCtx}/' + path + '" width="80" height="84" />');
		 	$(".addQRcode-pic").empty();
			$("#QRcodeView").append(item);
		}else if(type=='qyzz'){//上传资质
			$("#catePic").val(path);
		 	var item = $('<img src="${fileCtx}/' + path +'" width="80" height="84" />');
		 	$(".qyzzView_pic").empty();
			$("#qyzzView").append(item);
		}else if(type=='dhpic'){//上传导航栏
			$("#showimage").val(path);
			var item = $('<img src="${fileCtx}/' + path + '" width="260" height="84" />');
			$(".addqydhtp-pic").empty();
			$("#pic_showImage").append(item);
		}else if(type=='0'){//上传导航栏
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
		}else{
			var item = '<img src="${fileCtx}/'+path+'"/>';
			UE.getEditor('editor').setContent(item, true);	
		}
	} */
	
	//删除导航图片
	function deleteIcon(){
		var imgUrl=$("#showimage").val();
		if(imgUrl!=null&&imgUrl!=''){
			$("#showimage").val('');
		$(".addqydhtp-pic").empty();
		layer.alert('删除导航图片成功',{
		    skin: 'layui-layer-molv' //样式类名
		        ,closeBtn: 0
		    });
		}
	}
	
	var ue = UE.getEditor('editor',
		 {initialFrameHeight:280,
		toolbars: [
		           [
		               //'anchor', //锚点
		               'undo', //撤销
		               'redo', //重做
		               'bold', //加粗
		               'indent', //首行缩进
		               //'snapscreen', //截图
		               'italic', //斜体
		               'underline', //下划线
		               'strikethrough', //删除线
		               'fontborder', //字符边框
		               'subscript', //下标			               
		               'superscript', //上标
		               'formatmatch', //格式刷
		               //'source', //源代码
		               'blockquote', //引用
		               'pasteplain', //纯文本粘贴模式
		               'selectall', //全选
		               'print', //打印
		               'preview', //预览
		               'horizontal', //分隔线
		               'removeformat', //清除格式
		               'time', //时间
		               'date', //日期			               
		               'inserttable', //插入表格
		               'edittable', //表格属性
		               'edittd', //单元格属性
		               'insertrow', //前插入行
		               'insertcol', //前插入列
		               'mergeright', //右合并单元格
		               'mergedown', //下合并单元格
		               'deleterow', //删除行
		               'deletecol', //删除列
		               'splittorows', //拆分成行
		               'splittocols', //拆分成列
		               'splittocells', //完全拆分单元格
		               'deletecaption', //删除表格标题
		               'inserttitle', //插入标题
		               'mergecells', //合并多个单元格
		               'deletetable', //删除表格			               
		               'insertparagraphbeforetable', //"表格前插入行" 
		               'imagenone', //默认
		               'imageleft', //左浮动
		               'imageright', //右浮动
		               'edittip ', //编辑提示
		               //'insertcode', //代码语言
		               'fontfamily', //字体
		               'fontsize', //字号
		               //'paragraph', //段落格式			               			               
		               'link', //超链接
		               'unlink', //取消链接
		               'emotion', //表情
		               'spechars', //特殊字符
		               'searchreplace', //查询替换
		               //'map', //Baidu地图
		               //'gmap', //Google地图
		               'justifyleft', //居左对齐
		               'justifyright', //居右对齐
		               'justifycenter', //居中对齐
		               'justifyjustify', //两端对齐
		               'forecolor', //字体颜色
		               'backcolor', //背景色
		               'insertorderedlist', //有序列表
		               'insertunorderedlist', //无序列表
		               'pagebreak', //分页			               
		               //'fullscreen', //全屏
		               'directionalityltr', //从左向右输入
		               'directionalityrtl', //从右向左输入
		               'rowspacingtop', //段前距
		               'rowspacingbottom', //段后距
		               'lineheight', //行间距
		               //'insertframe', //插入Iframe			               
		              // 'attachment', //附件
		               //'imagecenter', //居中
		               //'wordimage', //图片转存    
		               'customstyle', //自定义标题
		               'autotypeset', //自动排版
		               //'webapp', //百度应用
		               'touppercase', //字母大写
		               'tolowercase', //字母小写
		              // 'background', //背景
		               'template', //模板
		              // 'scrawl', //涂鸦
		               //'music', //音乐			               
		               //'drafts', // 从草稿箱加载
		              // 'charts', // 图表
		              'cleardoc', //清空文档
		           ]
		       ],labelMap:{
		            'insertimage':'上传图片'
		        }
		
	});
	var content = $("#info").val();
	ue.ready(function(){
		ue.setContent(content);    
	})
	
	function addForm(){
		var name = '${info.name}';
		layer.open({
			type:2,
		    title: '选择证书',
		    fix: true, 
		    area: ['550px', '600px'],
		    content: ["${ctx}/tenant/formList.ht?name="+ encodeURI(encodeURI(name)),"no"], //iframe的url
		    btn :'确定',
		    yes: function(index){
		    	//校验表单信息
		    	if(!$("iframe").contents().find('#aptitudeForm').form().valid()){
		    		 layer.alert("请完善信息!",{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});
		    		return false;
		    	}
		    	if($("iframe").contents().find("#catePic").val()==null||$("iframe").contents().find("#catePic").val()==''){
		    		layer.alert("请上传证书扫描件!",{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});
		    		return false;
		    	}
		    	
		    	var values = [];
		    	$("iframe").contents().find('#aptitudeForm input').each(function(){
		    		values.push($(this).val());
		    	});
		    	$("#aptitude").append("<tr type='subdata'>"
	    				+" <td name='name' title='"+values[0]+"' style='text-align: center'>"+subStr(values[0])+"</td>"
	    				+"<td name='cateType' style='text-align: center'>"+values[1]+"</td>"
	    				+" <td name='cateOrg' title='"+values[2]+"' style='text-align: center'>"+subStr(values[2])+"</td>"
	    				+" <td name='inureDate' style='text-align: center'>"+values[3]+"</td>"
	    				+" <td name='endDate' style='text-align: center'>"+values[4]+"</td>"
	    				+"<td name='catePic' style='text-align: center'>"+(values[6]==""?"":"<img width='80' height='84' src='${fileCtx}"+values[6]+"'>")+"</td>"
   						+"<td style='text-align: center'> <a href='javascript:void(0)' onclick='deleteTr(this)' class='link'>删除</a> </td> "
   						+"<input type='hidden' value='"+values[0]+"' name='infoId'>"
   						+"<input type='hidden' value='"+values[1]+"' name='cateType'>"
   						+"<input type='hidden' value='"+values[2]+"' name='cateOrg'>"
   						+"<input type='hidden' value='"+values[3]+"' name='inureDate'>"
   						+"<input type='hidden' value='"+values[4]+"' name='endDate'>"
   						+"<input type='hidden' value='"+values[6]+"' name='catePic'>"
	    				+"</tr>");
		    	layer.close(index);
		    }
		});  
	}
	
	function deleteTr(obj){
		layer.confirm('确认删除吗?', {icon: 3, title:'提示',skin: 'layui-layer-molv' ,closeBtn: 0}, function(index){
		   	$(obj).closest("tr").remove();
		    layer.close(index);
		});
	}
	
	$(function(){
		$("#msg img").each(function(){
			if(this.complete){
				$('.zoom').zoom();
			}
		})
		
	});
</script>
</body>
</html>
