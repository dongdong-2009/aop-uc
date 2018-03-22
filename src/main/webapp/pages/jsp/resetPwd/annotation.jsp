<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<style type="text/css">

body {
  font: 12px/1.5 Arial, Helvetica, SimSun, san-serif;
  color: #333;
}

.yyindex_content {
  width: 300px;
  margin: 0 auto;
  font-size: 16px;
}

.content p{
	margin-top:10px;
	width: 100%;
	line-height:40px;
}
.content{
	margin-top:40px;
	text-align: center;
}
.forMycompany{
	width:140px;
	height:36px;
	line-height:36px;
	text-align:center;
	text-decoration:none;
	background-color:#ff9935;
	color:#fff;
	font-weight:bold;
	display:inline-block;
}

</style>
<script type="text/javascript">
	var seconds = 3;//记数时间 
	var handle;//事件柄 
	
	//开始记数器 
	function startTimer() { 
	   handle = setInterval("timer()",1000); 
	} 
	
	
	//计数器 
	function timer() { 
		 seconds -= 1; 
		 document.getElementById("time").innerHTML = " <font color='red'>" + seconds + "</font>秒后跳转至登录页面"; 
		 if (seconds == 0) { 
			 parent.window.location.href="${ctx}/user/doLogin.ht";
			 var iframeIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
			 parent.layer.close(iframeIndex);
		 }  
	} 
	
	//1秒后显示计数器 
	//setTimeout("startTimer()",1000);    
  
  function LoginPage(){
	// parent.closeWin();
	  parent.window.location.href="${ctx}/user/doLogin.ht";
  }

		  parent.window.location.href="${ctx}/user/doLogin.ht";
	  }
</script>
</head>
<body>
   <div class="yyindex_content">
  		<div class="content">
			密码重置成功! <span id="time"><font color="red">3</font>秒后跳转至登录页面</span></p>
			<p >
				<a class="forMycompany" href="javascript:void(0)"  onclick="LoginPage();">立即登录</a>
			</p>
  		</div>
   </div>
</body>
</html>