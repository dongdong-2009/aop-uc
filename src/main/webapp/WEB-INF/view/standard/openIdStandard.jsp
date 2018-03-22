<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>子系统接入规范</title>
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
	 var type='${type}';
	 if(type=="1"){
		 $(".api_left ul li").each(function(){
       		$(this).removeClass("active");
		 });
		 $("#res_content").attr("src","${ctx}/openIdStandardAdd.ht");
		 $("#openIdStandardAdd").addClass("active");
	 }else if(type=="2"){
		 $(".api_left ul li").each(function(){
       		$(this).removeClass("active");
		 });
		 $("#res_content").attr("src","${ctx}/openIdStandardUpdate.ht");
		 $("#openIdStandardUpdate").addClass("active");
		 
	 }else if(type=="3"){
		 $(".api_left ul li").each(function(){
       		$(this).removeClass("active");
	 	});
		 $("#res_content").attr("src","${ctx}/openIdStandardDelete.ht");
		 $("#openIdStandardDelete").addClass("active");
	 }
	 
	 
	 
	$(".api_left li a").click(function() {
        var name = $(this).text().toString();
        var url=getURL(name);
        $("#res_content").attr("src",url); 
        $(".api_left ul li").each(function(){
    			$(this).removeClass("active");
    		});
		   $(this).parent().addClass("active");
   });
})

	function getURL(name){
        	if(name=="增加"){
        		return "${ctx}/openIdStandardAdd.ht";
        	}else if(name=="修改"){
        		return "${ctx}/openIdStandardUpdate.ht";
        	}else if(name=="删除"){
        		return '${ctx}/openIdStandardDelete.ht';
        	}
        }

</script>
<style type="text/css">
.api_left ul li:hover, .api_left ul li.active { 
	color: #0089cd; 
	background-color: #ddedf4; 
	text-decoration: none;}
.api_left ul li:hover a, .api_left ul li.active a { color: #0089cd; }
.api_bread { height: 38px; overflow: hidden; line-height: 38px; color: #323232; padding: 0 5px; margin-left: 90px;}
.api_left { float: left; display: inline; width: 220px; min-height: 344px; overflow: hidden; background: #f5f5f5; margin-left: 90px;}
.api_right { margin-left: 320px; min-height: 700px; }
</style>
</head>
<body>
<%@ include file="/commons/top.jsp" %>
<div class="aopIndex_nav">
    <div class="gWidth">
        <div class="aopIndex_nav_c clearfix">
            <ul class="f_left aopIndex_nav_l">
                <li><a href="index.ht">首页</a></li>
                <li><a href="userGuide.ht" >用户指南</a></li>
                <li><a href="joinStandard.ht" class="active">接入规范</a></li>
                <li><a href="ucAPI.ht">用户中心API</a></li>
                <li><a href="commonProblems.ht">常见问题</a></li>
                <li><a href="errorCode.ht" >错误码</a></li>
                <li><a href="community.ht" >社区</a></li>
            </ul>
        </div>
    </div>
</div>


<div class="api_box clearfix">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="joinStandard.ht">接入规范</a> &gt; <a href="openIdStandard.ht">openId规范</a> </div>
    <div class="api_left">
    	<h2>openId规范</h2>
        <ul>
        	<li id="openIdStandardAdd" class="active"><a href="javascript:void(0);">增加</a></li>
            <li id="openIdStandardUpdate"><a href="javascript:void(0);">修改</a></li>
            <li id="openIdStandardDelete"><a href="javascript:void(0);">删除</a></li>
        </ul>
    </div>
   <div class="api_right" style="overflow:hidden;">
       <iframe src="/openIdStandardAdd.ht" frameborder="0" scrolling="no" width="100%" style="height: 1100px;" id="res_content"></iframe>
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


</body>
</html>