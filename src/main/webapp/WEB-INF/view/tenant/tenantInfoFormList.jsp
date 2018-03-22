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
	<script type="text/javascript" src="${ctx}/js/hotent/uc/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
		
	<!-- 上传图片 -->
	<script type="text/javascript" src="${ctx}/js/upload/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/upload/cloudDialogUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/upload/uploadPreview.js"></script>
 
<style type="text/css">
	.layui-layer-btn {
	    pointer-events: auto;
	    text-align: right;
	}
.layui-layer-iframe .layui-layer-btn, .layui-layer-page .layui-layer-btn {
    padding-top: 0px;
}
</style>
	<script type="text/javascript">
	/* //上传图片	
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
				$("#catePic").val("/"+path);
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
	
	$(function(){
		$("#cateType").live('change',function(){
			$(this).next().val($(this).val());
		});
		$("#aptitudeForm").form();
		
		$("#endDate1").click(function(){
			if(this.checked){
				$(this).prev("input[type='text']").removeClass("date");
				$(this).prev("input[type='text']").removeAttr("validate");
				$(this).prev("input[type='text']").attr('readonly','readonly');
				$(this).prev("input[type='text']").val(this.value);
			}else{
				if(!$(this).prev("input[type='text']").hasClass("date"))
				$(this).prev("input[type='text']").addClass("date");
				$(this).prev("input[type='text']").removeAttr("readonly");
				$(this).prev("input[type='text']").val('');
				$(this).prev("input[type='text']").attr("validate","{required:true,date:true}");
			}
			
		});
	})
	
	</script>
</head>
<body>
	<form id="aptitudeForm" method="post">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr style="display: none;">
				<th width="20%">所属企业22: </th>
				<td><input type="text" name="name" value="${name }" readonly="readonly" class="inputText" /></td>
			</tr> 
			<tr>
				<th width="20%">证书分类: </th>
				<td>
					<ap:selectDB name="cateType" id="cateType" 
						where="parentId=10000005960001" optionValue="itemValue"
						optionText="itemName" table="SYS_DIC"
						selectedValue="">
					</ap:selectDB>
					<input type="hidden" name="cateType" class="inputText" value="企业基本资格证书"/>
				</td>
			</tr>
			<tr>
				<th width="20%">发证机构: </th>
				<td><input type="text" name="cateOrg" maxlength="64" class="inputText" validate="{required:true,maxlength:64}"/></td>
			</tr>
			<tr>
				<th width="20%">生效日期: </th>
				<td class="">
					<div class="cell">
						<input type="text" name="inureDate" class="inputText date" validate="{required:true,date:true}"/>
					</div>
				</td>
			</tr>
			
			<tr>
				<th width="20%">截止日期: </th>
				<td>
				  <div class="cell">
						<input type="text" name="endDate" class="inputText date" validate="{required:true,date:true}"/>
				        <input type="checkbox" id="endDate1" value="长期有效" readonly="readonly" class="inputText" />长期有效
					</div>
				</td>
			</tr>
			<tr height="120px;">
				<th width="20%">证书扫描件: </th>
				<td>
					<!-- <input type="file" name="catePic" id="catePic" value=""/> -->
					<input type="hidden" readonly="readonly" id="catePic" value=""/>
					<a href="javascript:openImageDialog('qyzz');" class="btn-ctrl"
							id="addProductPic" js_tukubtn="true">添加企业资质</a>
					<div class="qyzzView_pic" id="qyzzView"></div>
					<div class="prductNameTip">建议上传100*100像素的图片，大小不超过2M</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
