<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*,java.io.File" pageEncoding="utf-8"%>
<%@include file="/commons/global.jsp"%>

<html>
<head>	
	<title>图片上传</title>	
    <meta http-equiv="X-UA-Compatible" content="IE=edge">	
    <%@ include file="/commons/meta.jsp"%>
	<%@include file="/commons/include/form.jsp" %>
	<%
		String pathSeparator = File.pathSeparator;
	%>
	<link rel="stylesheet" type="text/css" href="${ctx}/pintuer/pintuer.css"/>
	<script language="JavaScript" type="text/javascript" src="${ctx}/pintuer/pintuer.js"></script>
	<script language="JavaScript" type="text/javascript" src="${ctx}/js/upload/uploadPreview.js"></script>
	<!-- 解决ie 6 7 8 下json未定义问题 -->
	<!--[if lt IE 9]>
	<script type="text/javascript" src="${ctx}/pages/cloud3.0/js/cloud/json2.js"></script>
	<![endif]-->
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/layer/skin/layer.css" />
	<script type="text/javascript" src="${ctx}/js/upload/ajaxfileupload.js"></script>
	<base target="_parent" />
	<script>
		$(function(){
			//设置input type = file 透明
			$('#uploadFile').css({'opacity':'0'}); 
			$('input[name=file]').css({'opacity':'0'}); 
			new uploadPreview({ UpBtn: "uploadFile", DivShow: "imgdiv", ImgShow: "imgShow" });
			$("#uploadFile").live("change",function(){
				new uploadPreview({ UpBtn: "uploadFile", DivShow: "imgdiv", ImgShow: "imgShow"});
				$("#imgdiv").removeClass("icon-file-image-o");
				$("#imgdiv").removeClass("defaultImg");
				$("#manageImage").show();
				$("#manageImageInvalid").hide();
			});
			autoGener();
		});
		function addImage(){
			$("#uploadFile").click();
		}
		function cancleUpload(){
			$("#imgShow").removeAttr("src");
			$("#imgdiv").addClass("icon-file-image-o");
			$("#imgdiv").addClass("defaultImg");
			$("#manageImage").hide();
			$("#manageImageInvalid").show();
			$("#continueUpload").hide();//显示是否继续上传按钮
		}
		
		function autoGener(){
			$.ajax({
				type:'post',
				async:false,
				url:"${ctx}/pub/image/getToken.ht",
		        dataType: 'json',
		        contentType:'application/json;charset=UTF-8' ,
				success:function(data){
					key=data.key;
					token=data.uploadToken;
					$("#key").val(key);
					$("#token").val(token);
				}
			})
		}
	</script>
	<script type="text/javascript">
	var maxUploadNum = 4;//最大上传5张图片
	var indexNum = 0;
	//定时器对象
	var uploadProcessTimer = null;

	//获取文件上传进度
	function getFileUploadProcess() {
		/* $.get('/upload/getFileProcessServlet', function(data) {
			$('#fileUploadProcess').html(data);
		}); */
	}
	//确认上传
	function confirmUpload(){
		var jsonArr = new Array();
		var index = 0;
		$("#dataCache span").each(function(){
			var jsonText = $(this).text().replace(/[;]/gi, '/');;
			var jsonObject = eval("(" + jsonText + ")"); 
			jsonArr[index] = jsonObject;
			index++;
		});
		var _callback = '${_callback}';
		//如果指定了回掉函数就用指定的，没有指定就用默认的
		if(_callback == null || _callback == undefined || _callback.length==0){
			parent.imageDialogCallback(jsonArr);
		}else{
			eval(_callback); 
		}
		var iframeIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(iframeIndex);
	}
	</script>
	<style>
		.dataTable {
			margin: 30px 20px 20px 20px;
		}
	
		.dataTable table td{
			padding:5px 5px;
		}
		#appleFile{
			width:100%;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		.imgContainer{
			width:300px;
			height:160px;
			margin-bottom:20px;
			padding:5px;
		}
		.defaultImg{
			color: #ddd;
		    font-size: 100px;
		    text-align: center;
		}
		.defaultImg-small{
			color: #ddd;
		    font-size: 30px;
		    padding-top: 20px !important;
		    text-align: center;
		}
		.container img[src=''],
		.container img:not([src]) {opacity:0;filter: alpha(opacity=0);}
		.file {
		    color:#fff !important;
		    padding:7px 32px;
		    border-radius: 4px;
		    font-size:14px !important;
		    cursor:pointer;
		}
		.file:hover {
		    background:#00aabb;
		}
		.file input {
		    position: absolute;
		    font-size: 50px; 
		    left:-500px;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    filter: alpha(opacity=0);
		    cursor:pointer;
		    height:70px;
		}
		img{
		  filter:alpha(opacity=0);
		}
		.layui-layer { top:50% !important;}
	</style>
</head>
<body>	
<div class="container" style="padding:10px;width:300px; margin-left:99px;display:inline-block;">
	<div class="line" style="margin-bottom:10px;">
	<div style="margin-top:10px;">
		<form method="post" action="http://upload.qiniu.com/" id="fileForm" name="fileForm" enctype="multipart/form-data">
			<a href="javascript:;" class="icon-plus bg-main file" id="previewButton">添加图片
				<input type="file" name="file" id="uploadFile"/>
				<input type="hidden" name="token" id="token">
				<input type="hidden" name="key" id="key">
			</a>
		</form>
	</div>
	</div>
	<div class="line" id="imageContainer">
		<div class="line-big border imgContainer icon-file-image-o defaultImg" id="imgdiv">
			<img id="imgShow" width="290" height="150"/>
		</div>
		<div class="line-big" id="manageImage" style="display:none">
			<div class="x6"><button class="button icon-plus bg-main" style="width:100%" id="uploadImage">上传</button></div>
			<div class="x6"><button class="button icon-times bg-red" style="width:100%" onclick="cancleUpload();">取消</button></div>
		</div>
		<div class="line-big" id="manageImageInvalid">
			<div class="x6"><button class="button icon-plus bg-gray" style="width:100%">上传</button></div>
			<div class="x6"><button class="button icon-times bg-gray" style="width:100%">取消</button></div>
		</div>
		<div class="line-big" id="continueUpload"  style="display:none">
			<div class="x12"><button class="button icon-check bg-main" style="width:100%" onclick="confirmUpload();">确认选择</button></div>
		</div>
	</div>
</div>
<div id="dataCache" style="display:none">

</div>
<script type="text/javascript" src="${ctx}/js/upload/qiniuUploadImage.js"></script>
</body>
</html>