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
		 $("#res_content").attr("src","${ctx}/userStandardAdd.ht");
		 $("#userStandardAdd").addClass("active");
	 }else if(type=="2"){
		 $(".api_left ul li").each(function(){
       		$(this).removeClass("active");
		 });
		 $("#res_content").attr("src","${ctx}/userStandardUpdate.ht");
		 $("#userStandardUpdate").addClass("active");
		 
	 }else if(type=="3"){
		 $(".api_left ul li").each(function(){
       		$(this).removeClass("active");
	 	});
		 $("#res_content").attr("src","${ctx}/userStandardDelete.ht");
		 $("#userStandardDelete").addClass("active");
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
        		return "${ctx}/userStandardAdd.ht";
        	}else if(name=="修改"){
        		return "${ctx}/userStandardUpdate.ht";
        	}else if(name=="删除"){
        		return '${ctx}/userStandardDelete.ht';
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
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="joinStandard.ht">接入规范</a> &gt; <a href="userStandard.ht">用户规范</a> </div>
    <div class="api_left">
    	<h2>用户规范</h2>
        <ul>
        	<li id="userStandardAdd" class="active"><a href="javascript:void(0);">增加</a></li>
            <li id="userStandardUpdate"><a href="javascript:void(0);">修改</a></li>
            <li id="userStandardDelete"><a href="javascript:void(0);">删除</a></li>
        </ul>
    </div>
   <div class="api_right" style="overflow:hidden;margin-bottom: 20px;">
       <iframe src="/userStandardAdd.ht" frameborder="0" scrolling="no" width="100%" style="height: 1100px;" id="res_content"></iframe>
    </div>
</div>


<!--casicloud footer begin-->
<%@ include file="/commons/footer.jsp" %>
 
  
<!--casicloud footer end-->


</body>
</html>