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
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.zoom.js"></script>
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
	
	.register_mainCon_btn{
	  margin-top: 100px;
	}
	
	.openAccount_titie_width{
	 width: 132px;
	}
	.appleSelect{
	  width: 100px;
	}
	
	
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
			$("#sub2").click(function(){
	            firstClick1(frm,options);
	        });
			
	        $("#sub").dblclick(function(){
	            firstClick(frm,options);
	        });
	        function firstClick(frm,options){
				//去掉所有的html标记
				if(validForm()){
					var code = $("#code").val();
					$("#info").val(ue.getContent());
					$("input[name='accountStatsus']").val('1');//将实名认证改为已认证
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
	        function firstClick1(frm,options){
				//去掉所有的html标记
				
					var code = $("#code").val();
					$("#info").val(ue.getContent());
					frm.setHtmlData();
					frm.ajaxForm(options);
					if(frm.valid()){
						layer.msg('正在保存中,请稍候...', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
						frm[0].action="saveInfo.ht";
						form.submit();
						firstClick1 = secondClick;
					}else{
						layer.alert("信息填写不正确");
					}
					//str = str.replace(/<[^>]+>/g,"");
					
			
				
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
			if (obj.isSuccess()) {
				obj=obj.getMessage();
				obj=$.parseJSON(obj);
				if(obj.flag=='audit'){
					audit(obj);
				}
				else{
					layer.alert(obj.Msg, {
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
	
	
	
	 $(window.parent.document).find("#mainiframe").load(function(){
	    	 var mainheight = $(document).height() + 30;
	    	$(this).height(mainheight);
	     });

	</script>
</head>
<body >
	
	<form id="infoForm" method="post" action="certified.ht"  class="signupForm2">
	         <input name="state" type="hidden" value="${info.state}">
	         <input name="accountStatsus" type="hidden">
	          <div id="wizard"  type="main">
				 <div class="row">
										<div class="label">
											主要销售区域：
										</div>
										<div class="cell">
											<ap:selectDB name="sellArea" id="sellArea" where="parentId=10000005610078" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.sellArea}">
											</ap:selectDB>
											<ap:ajaxSelect srcElement="sellArea" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="sellArea2"/>
											<ap:selectDB name="sellArea2" id="sellArea2" 
												where="itemKey='sell_area' and parentId != 10000005610078" optionValue="itemValue"
												optionText="itemName" table="SYS_DIC"
												selectedValue="${info.sellArea2}">
											</ap:selectDB>
											<input type="hidden" id="inpu2" value="${info.sellArea2}">
										</div>
							</div>				
	  
	             
				</div>
						
		
	</form>
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


</body>
</html>
