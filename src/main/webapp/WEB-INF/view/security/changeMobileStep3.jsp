<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script id="allmobilize" charset="utf-8" src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" href="#" />
<meta charset="utf-8" />
<title>安全设置</title>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
 <link href="${ctx}/styles/uc/v2.0/top.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
 <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/jquery/jquery.validate.js"></script>

<style type="text/css">
.changeMobile_title {
    width: 100%;
    border-bottom: 1px solid #ddd;
    height: 50px;
    margin-top:50px;
    }
.changeMobile {
	border-left: 5px solid #ff771d;
    font-family: "Microsoft YaHei";
    color: #656565;
    font-size: 24px;
    width: 580px;
    float: left;
    padding: 5px 0 0 10px;}
.changeMobile_intro{
	padding-top:60px;
	padding-left:100px;}
.btn-confirm{
	width:60px;
	height:30px;
	background:#ff771d;
	color:#fff;
	border-radius:6px;
	cursor:pointer;
	font-size:14px;
	padding:10px 0 0 130px;
	margin-left:80px;}	
.btn-return{
	width:60px;
	height:30px;
	background:#ff771d;
	color:#fff;
	border-radius:6px;
	cursor:pointer;
	font-size:14px;
	padding:10px 0 0 130px;
	margin-left:80px;}	
.form-list1 {
    padding-left: 250px;
    padding-bottom: 20px;
    width: 720px;
    height: 50px;
    font-size: 16px;}
.form-list{
	padding-left:20px;
	padding-bottom:20px;
	margin-top: 50px;}
 body {
    color: #666;
    font-family: Microsoft YaHei;
    font-size: 14px;}

</style>
</head>
	 <body>
	   
        <!-- <div class="top">
			<div class="block">
			<a class="logo" href="#"><span class="decorate"></span></a>
			<div class="tit">安全设置</div>
			</div>
		</div> -->
        <div class="changeMobile_title">
		     <div class="changeMobile">更换手机</div>
		</div>
		<div class="changeMobile_intro">
	          <div class="form-list1">
	          	<img src="${ctx}/pages/images/success.jpg" style="margin: -10px 0px;"></img>更换手机成功
			  </div>
			  <div class="form-list" > 
		  		<input type="button" class="btn-confirm" value="确定" onclick="clickConfirm()" style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 190px; width: 80px; height: 40px;"/>
		  		<input type="button" class="btn-return" value="返回" onclick="clickReturn()" style="border-width: 0px; padding-left: 0px; padding-top: 0px; margin-left: 85px; width: 80px; height: 40px;"/>
			  </div>
		 </div>
	</body>
	
	<script type="text/javascript">
	
		var mobile="${mobile}";
		function clickConfirm(){
			$.ajax({
				type : 'POST',
				url : "${ctx}/security/updateMobile.ht",
				data : {mobile : mobile},
				success : function(resultMessage) {
					if(resultMessage && resultMessage.indexOf("成功") > 0){
						layer.alert("更换手机成功");
						window.location.href="${ctx}/security/index.ht";
					}else{
						layer.alert(resultMessage);
					}
				},
				error : function(dataMap){
					layer.alert("更换手机失败");
				}
			});
		}
	
		function clickReturn(){
			window.location.href="${ctx}/security/changeMobileStep2.ht";
		}
</script>
</html>
