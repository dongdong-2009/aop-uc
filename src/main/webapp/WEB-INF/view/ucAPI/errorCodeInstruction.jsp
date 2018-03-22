<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心API</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript">
$(function(){
	$(".api_left ul li").each(function(index){
	  $(this).click(function(){
		 $(".api_right div.api_items").hide();
		 $(".api_right div.api_items").eq(index).slideDown();
		 })
	  })
	})
$(function(){
	  $(".api_rightCon_tit").each(function(index){
	        $(this).toggle(
	           function(){
	              $(this).siblings(".api_right_style").slideUp();
	              $(this).find('.triangle').removeClass('open');
	           },
	           function(){
	              $(this).siblings(".api_right_style").slideDown();
	              $(this).find('.triangle').addClass('open');
	           })
	  	})
	})	
	

</script>
<style type="text/css">
.api_left ul li:hover, .api_left ul li.active { 
	color: #0089cd; 
	background-color: #ddedf4; 
	text-decoration: none;}
.api_left ul li:hover a, .api_left ul li.active a { color: #0089cd; }
.api_rightTit { font-size: 22px; height: 60px; }
.api_rightCon_tit { padding-top: 12px;padding-bottom: 12px; margin-top: 25px; text-indent: 30px; position: relative; cursor: pointer; border-bottom: 1px solid #999;font-size: 20px; margin-bottom: 20px;}
.api_rightCon_tit .triangle { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: transparent transparent transparent #666; position: absolute; left: 13px; top: 20px;}
.api_rightCon_tit .triangle.open { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: #666 transparent transparent transparent; top: 20px; left: 10px; }
.api_rightCon_tit:hover{background:#eee;}
.api_right_simple_param{height:100px;background:#eee;font-size:12px;text-align:left;}
.api_right_simple_param_1{height:300px;background:#eee;font-size:12px;text-align:left;}
</style>
</head>
<body>
<%@ include file="/commons/top.jsp" %>
<%@ include file="/commons/navigator.jsp"  %>

<div class="api_box clearfix">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="ucAPI.ht">用户中心API</a> &gt; <a href="ucAPIIntroduction1.ht">子系统调用API</a> </div>
    <div class="api_left">
    	<h2>子系统调用API</h2>
        <ul>
        	<li class="active"><a href="javascript:void(0);">/api/registerNoBack</a></li>
        </ul>
    </div>
   <div class="api_right">
      <div class="api_items" style="display:block;">
      			<h2>错误代码对照表</h2>
      			 <div class="api_rightCon">
			        <!-- <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 请求参数
			        </div> -->
			        <div class="api_right_style">
				        <table border="0" cellpadding="0" cellspacing="0" width="100%">
					        <tr class="api_item_bt">
					        	<td width="15%">错误编码</td>
					        	<td width="30%">错误信息</td>
					        	<td width="55%">解决方法</td>
					        </tr>
					        <tr>
					        	<td>200</td>
					        	<td>成功</td>
					        	<td>成功</td>
					        </tr>
					        <tr>
					        	<td>500</td>
					        	<td>请传入子系统唯一标识</td>
					        	<td>传入systemId</td>
					        </tr>
					        <tr>
					        	<td>501</td>
					        	<td>请传入子系统所需参数</td>
					        	<td>传入data</td>
					        </tr>
					        <tr>
					        	<td>502</td>
					        	<td>未查询到子系统</td>
					        	<td>传入正确的systemId</td>
					        </tr>
					        <tr>
					        	<td>503</td>
					        	<td>用户保存失败</td>
					        	<td>用户保存失败</td>
					        </tr>
					        <tr>
					        	<td>504</td>
					        	<td>系统无对应秘钥，请联系管理员</td>
					        	<td>生成秘钥</td>
					        </tr>
				        </table>
			        </div>
		        </div>
      		
        </div>
    </div>
</div>


<!--casicloud footer begin-->
<div class="footer">
  <div class="block">
    <div class="box">
      <ul class="ul1">
        <li class="num">01</li>
        <li class="t">关于我们</li>
        <li><a href="#">航天云网平台</a></li>
        <li><a href="#">江西航天云网平台</a></li>
      </ul>
      <ul class="ul2">
        <li class="num">02</li>
        <li class="t">快速链接</li>
        <li><a href="#">创新创业</a></li>
        <li><a href="#">工业品商城</a></li>
        <li><a href="#">云制造平台</a></li>
        <li><a href="#">培训平台</a></li>
      </ul>
      <ul class="ul3">
        <li class="num">03</li>
        <li class="t">服务支持</li>
        <li><a href="#">培训视频</a></li>
        <li><a href="#">帮助手册</a></li>
        <li><a href="#">客服中心</a></li>
        <li><a href="#">售后保障</a></li>
      </ul>
      <ul class="ul4">
        <li class="num">04</li>
        <li class="t">快速链接</li>
        <li>电话：4008576688 <br />
          北京市海锭区阜成路8号院<br />
          主办公楼</li>
      </ul>
      <ul class="ul5">
        <li class="num">05</li>
        <li class="t"> <span class="s1"> <img src="${ctx}/styles/uc/images/j26.jpg" />
          <h2>云网主站</h2>
          </span> <span class="s2"> <img src="${ctx}/styles/uc/images/j26.jpg" />
          <h2>微信公众号</h2>
          </span> </li>
      </ul>
    </div>
  </div>
  <div class="site">
    <div class="block">
      <div class="l">Copyright@2016航天云网,All Rights Reserved</div>
      京ICP备16012914号-12    京公网安备1101082014254
      <div class="r"><a target="_blank" href="javascript:void(0);">网站建设</a>：<a target="_blank" href="javascript:void(0);">北京天智科技</a></div>
    </div>
  </div>
</div>
<!--casicloud footer end-->

<script type="text/javascript" src="${ctx}/js/navigator.js"></script>
</body>
</html>