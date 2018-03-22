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
.form-list1 {
    padding-left: 250px;
    /* padding-bottom: 20px; */
    width: 720px;
    height: 30px;
    font-size: 16px;}
.form-success {
    padding-left: 190px;
    padding-bottom: 20px;
    width: 720px;
    height: 50px;
    margin-top: 30px;
    font-size: 16px;}
 body {
    color: #666;
    font-family: Microsoft YaHei;
    font-size: 14px;}

 a{
 cursor:pointer;
    }
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
		     <div class="changeMobile">绑定手机</div>
		</div>
		<div class="changeMobile_intro">
	          <div class="form-list1">
	          	<img src="${ctx}/pages/images/success.jpg" style="margin: -10px 0px;"></img>绑定成功
			  </div>
			  <div style="font-size:14px;margin-left: 250px;margin-top: 30px;">页面将在<span id="time"><font color="red">5</font>秒内关闭</div>
			  <div class="form-success">
	          	如果页面没有自动关闭，<a style="color:blue;position: absolute;text-decoration: none" href="${ctx}/security/index.ht">请点击这里</a>
			  </div>
		 </div>
	</body>
	
<script type="text/javascript">
	var seconds = 5;//记数时间 
	var handle;//事件柄 
	//开始记数器 
	function startTimer() { 
	   handle = setInterval("timer()",1000); 
	}
	//计数器 
	function timer() { 
		 seconds -= 1; 
		 document.getElementById("time").innerHTML = " <font color='red'>" + seconds + "</font>秒后页面关闭"; 
		 if (seconds == 0) { 
			 window.location.href="${ctx}/security/index.ht"
		 }  
	}
	
	//1秒后显示计数器 
	setTimeout("startTimer()",1000);  
	
	//添加遮罩
	/* $(function(){  
		var docHeight = $(document).height(); //获取窗口高度  
		$('body').append('<div id="overlay"></div>');  
		$('#overlay').height(docHeight).css({  
			//'opacity': .9, //透明度  
		    'position': 'absolute',  
			'top': 0,  
			'left': 0,  
			//'background-color': 'grey',  
			'width': '100%',  
			'z-index': 5000 //保证这个悬浮层位于其它内容之上  
		 });  
		 setTimeout(function(){$('#overlay').fadeOut('slow')}, 5000); //设置5秒后覆盖层自动淡出  
	});  */

</script>
</html>
