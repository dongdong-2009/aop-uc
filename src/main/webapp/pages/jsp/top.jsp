<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hotent.core.util.ContextUtil" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="atx" value="${pageContext.request.localPort}"/>
<c:set var="btx" value="${pageContext.request.serverName}"/>
<c:set var="dtx" value="${pageContext.request.scheme}"/>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<!-- <script id="allmobilize" charset="utf-8" src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script> -->
<!-- <meta http-equiv="Cache-Control" content="no-siteapp" /><link rel="alternate" media="handheld" href="#" /> -->
<!-- 顶部 快捷栏 开始 -->
<script type="text/javascript">
var html="";
function fullSearch(obj){
	var searchVal = $(".form-search-bar").val();
	if(searchVal=="- 搜索关键字 -"){
		$(".form-search-bar").val('');
	}
	$(obj).closest("form").submit();
}
function changeArea(obj){
	var value = $(obj).val();
	switch(value){
	case 'nationwide':
		location = "${ctx}/index.ht";
		break;
	case 'jiangxi':
		location = "${ctx}/jiangxiIndex.ht";
		break;
	}
}
/* function openUpdateNotice(){
	layer.open({
	    type: 1,
	    title: false,
	    closeBtn:1,
	    area: '691px',
	    skin: 'layui-layer-nobg', //没有背景色
	    shadeClose: true,
	    content: $('#noticeImg')
	});
} */
/* 获取cas认证信息 */
/*  $(function(){
	//alert("${dtx}://${btx}:${atx}${ctx}/CAS/ajaxCasLogin.ht");
	var urlStr = encodeURIComponent("${dtx}://${btx}:${atx}${ctx}/CAS/ajaxCasLogin.ht");
	var responseData = $.ajax({
		type:'post',
		async:false,
		url:"http://10.142.14.88:1010/login?service="+urlStr,
		dataType : "jsonp",
		jsonpCallback:"success_jsonpCallback",
		success:function(data){
			if(data != ""){
	    	 	$("#loginMsg").empty();
	    	 	html= '您好,<a href="${ctx}/home.ht" title="'+data[0]+'">'+subStr(data[0])+'</a>欢迎访问航天云网&nbsp;&nbsp;&nbsp;<a href="${ctx}/CAS/casLogout.ht">注销</a>';
	    		$("#loginMsg").html(html);
	    		$("#bodyLoginMsg").empty();
	    		var html2= '<button type="button" class="btn btn-info" style="width:190px;" onclick="location=\'${ctx}/home.ht\'">我的云网</button></br>'+
	           				 '<a href="${ctx}/CAS/casLogout.ht">注销</a>';
				$("#bodyLoginMsg").append(html2);
	    	}
		}
	});
	if(html==""){
		html ='<span style="color:#fff;padding-right:10px;">欢迎访问航天云网</span><a href="javascript:doLogin();">登录</a><span>&nbsp;|&nbsp;</span><a href="${ctx}/reg_cloud.ht">免费注册</a>';
		$("#loginMsg").html(html);
	}
});  */
function subStr(loginName){
	 if(loginName!=null && loginName != null){
   	if(loginName.length>10){
   		loginName = loginName.trim();
   		loginName = loginName.substring(0,8)+"..";
   	}
   }
	 return loginName;
}

jQuery(function(){
	jQuery("#orderPic").hover(function(e){
		jQuery("#bigPic").css({"display":"block"});
	},function(e){
		jQuery("#bigPic").css({"display":"none"}); 
	});
	jQuery("#orderPic1").hover(function(e){
		jQuery("#bigPic1").css({"display":"block"});
		
	},function(e){
		jQuery("#bigPic1").css({"display":"none"}); 
	});
 	jQuery("#myCasicloud").hover(function(e){
		jQuery("#myCasicloud_box").css({"display":"block"});
	},function(e){
		jQuery("#myCasicloud_box").css({"display":"none"}); 
	}); 
	jQuery("#serviceSupport").hover(function(e){
		jQuery("#serviceSupport_box").css({"display":"block"});
	},function(e){
		jQuery("#serviceSupport_box").css({"display":"none"}); 
	});
	jQuery("#language").hover(function(e){
		jQuery("#language_box").css({"display":"block"});
	},function(e){
		jQuery("#language_box").css({"display":"none"}); 
	});
	var val = jQuery("#loginMsg li a:first").text();
	var loginName = subStr(val);
	jQuery("#loginMsg li a:first").text(loginName);
});



jQuery(function(){
	 var url=window.location.href;
	 jQuery('.navbar-nav li a').removeClass();
	 //首页
	 if(url.indexOf('index.ht')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(0).addClass('current');	
	 }
	 //新闻中心
	 if(url.indexOf('newsCenter.ht')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(1).addClass('current');	
	 }
	 //新闻详情
	 if(url.indexOf('getNews.ht')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(1).addClass('current');	
	 }
	 //产品服务
	 if(url.indexOf('product.jsp')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(2).addClass('current');	
	 }
	 //解决方案
	 if(url.indexOf('solve.jsp')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(3).addClass('current');	
	 }
	 //成功案例
	 if(url.indexOf('cases.jsp')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(4).addClass('current');	
	 }
	 //航天专区
	/*  if(url.indexOf('industryIndex.ht')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(5).addClass('current');	
	 } */
	
})

function openIndustry(obj){
	 var url=window.location.href;
	 jQuery('.navbar-nav li a').removeClass();
	 //首页
	 if(url.indexOf('index.ht')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(0).addClass('current');	
		 jQuery('.navbar-nav li a').eq(0).focus();	
	 }
	 //新闻中心
	 if(url.indexOf('newsCenter.ht')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(1).addClass('current');	
		 jQuery('.navbar-nav li a').eq(1).focus();	
	 }
	 //新闻详情
	 if(url.indexOf('getNews.ht')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(1).addClass('current');	
		 jQuery('.navbar-nav li a').eq(1).focus();	
	 }
	 //产品服务
	 if(url.indexOf('product.jsp')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(2).addClass('current');	
		 jQuery('.navbar-nav li a').eq(2).focus();	
	 }
	 //解决方案
	 if(url.indexOf('solve.jsp')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(3).addClass('current');	
		 jQuery('.navbar-nav li a').eq(3).focus();	
	 }
	 //成功案例
	 if(url.indexOf('cases.jsp')!=-1 || url.indexOf('/')==-1){
		 jQuery('.navbar-nav li a').eq(4).addClass('current');	
		 jQuery('.navbar-nav li a').eq(4).focus();	
	 }
	 window.open("${ctx}/industryMall/hall/industryIndex.ht");
}
</script>
<style>
.current{
    text-decoration: none;
    color: #f0442b;
    border-bottom: 3px #f0442b solid;
}
.updateNotice{
	font-weight:bold;
}
.updateNotice:hover{
	color:#ee9f23 !important;
}
.navbar-top li {
  min-width: 30%;
  width: auto;
  float: left;
}
/* .ch-navbar-top-nw a,.ch-navbar-top-nw span { display:inline-block;float:left;} */
.ch-navbar-top-nw a,.ch-navbar-top-nw span { display:inline-block;}
.topov { max-width:110px; text-overflow: ellipsis; white-space: nowrap;}
#serviceSupport_box{display:none;position: absolute;z-index: 9999;left: -10px;top:35px;background-color:#fff;text-align:center;}
#serviceSupport_box .serviceSupport{width:100%;float:left;border:1px solid #ddd;}
#serviceSupport_box .serviceSupport li{font-size:12px;color:#333;font-weight:bold;text-align:center;width:70px;line-height:24px;}
#serviceSupport_box .serviceSupport li a{font-size:12px;color:#555;font-weight:normal;text-align:center;}
#language_box{display:none;position: absolute;z-index: 9999;left: -10px;top:35px;background-color:#fff;text-align:center;}
#language_box .language{width:100%;float:left;}
#language_box .language li{font-size:12px;color:#333;font-weight:bold;text-align:center;width:70px;border:1px solid #ddd;background-color:#fff;}
#language_box .language li a{font-size:12px;color:#555;font-weight:normal;text-align:center;color:#000;}
#myCasicloud_box{display:none;position: absolute;z-index: 9999;left:0px;top:35px;background-color:#fff;text-align:center;width:144px;border:1px solid #ddd;}
#myCasicloud_box .myCasicloud{width:50%;float:left;}
#myCasicloud_box .myCasicloud li{font-size:12px;color:#333;font-weight:bold;text-align:center;width:60px;line-height:24px;margin:0 6px;}
#myCasicloud_box .myCasicloud li a{font-size:12px;color:#555;font-weight:normal;text-align:center;padding:0;}
.navbar-top li span#myCasicloud{color:#fff;}
.navbar-top li span#myCasicloud:hover{color:#ffa800;}
.navbar-top li span#serviceSupport{color:#fff;}
.navbar-top li span#serviceSupport:hover{color:#ffa800;}
.navbar-top li span#language{color:#fff;}
.navbar-top li span#language:hover{color:#ffa800;}
.left-font a:hover{color:#ffa800;tetx-dectation:none;}
.left-font a#orderPic{color:#fff;}
.left-font a#orderPic1{color:#fff;}
.left-font a#orderPic:hover{color:#ffa800;text-decoration:none;}
.left-font a#orderPic1:hover{color:#ffa800;text-decoration:none;}
.navbar-right li a#enter_casicloud{color:#fff;}
.navbar-right a#enter_casicloud:hover{color:#ffa800;text-decoration:none;}

</style>
<img src="/pages/cloud6.0/htcloud/images/notice.png" id="noticeImg" style="display:none;"/>
<div class="navbar-top">
	<div class="container wrap1160">
		<div class="col-xs-6 left-font">
			<div class="navbar-top ch-navbar-top-nw" id="loginMsg" style="width:auto; line-height:35px; float:left;">
			 	<c:if test="<%=ContextUtil.getCurrentUser()==null %>">				
					<span style="color:#fff;padding-right:10px;">欢迎访问航天云网</span><a href="javascript:doLogin();">登录</a><span>&nbsp;|&nbsp;</span><a href="${ctx}/reg_cloud.ht">免费注册</a>
					
				</c:if>
				<!--登录之后-->
				<c:if test="<%=ContextUtil.getCurrentUser()!=null %>">
					<span>您好,</span><a href="${ctx}/home.ht" id="currentName" title="<%=ContextUtil.getCurrentUser().getFullname()%>" class="topov"><%=ContextUtil.getCurrentUser().getFullname()%>"</a><span>&nbsp;&nbsp;欢迎访问航天云网&nbsp;&nbsp;&nbsp;</span><a href="${ctx}/CAS/casLogout.ht">注销</a>
				</c:if> 
			</div>
				<a id="orderPic" href="#" style="float: left;padding-left:10px;position:relative;">
				    云网手机版
				   <div id="bigPic" style="display:none;position: absolute;z-index: 9999;left: -30px;top:35px;background-color:#fff;border:1px solid #ddd;">
					 <img alt="" src="${ctx}/app/liantu_tz.png" style="width: 130px;height: 150px;padding:20px 10px;"> 
				  </div>
				</a>
				<a id="orderPic1" href="#" style="float: left;padding-left:10px;position:relative;">
				 关注云网
				   <div id="bigPic1" style="display:none;position: absolute;z-index: 9999;left: -30px;top:35px;background-color:#fff;border:1px solid #ddd;">
					 <img alt="" src="${ctx}/pages/cloud6.0/htcloud/images/2wm2.jpg" style="width: 130px;height: 150px;padding:20px 10px;"> 
				  </div>
				 <%--  <img alt="" src="${ctx}/saas/nankang/images/lt_ewm.png" style="width: 25px;height: 25px;margin-right:10px;" >扫一扫，下载手机App
				   <!-- 二维码 -->

				<div id="bigPic" style="display: none;position: absolute;z-index: 9999;left: -30px;top:35px;">
					 <img alt="" src="${ctx}/app/liantu_tz.png" style="width: 130px;height: 130px;"> 
				</div> --%>
				<!-- end -->
				</a>　　
				
			
		</div>
		
		
		
		<div class="col-xs-6 right-font">
			<ul class="navbar-top navbar-right">
				<li style="float:right;">
					<span id="myCasicloud" style="display:inline-block;position:relative;">
					   我的云网
					   <div id="myCasicloud_box">
					      <ul class="myCasicloud">
					          <li>买家中心</li>
					          <li><a href="javascript:void(0)" onclick="doLogin('${ctx}/cosimcloud/inquiryAdd.ht',true);">发布需求</a></li>
					          <li style="border-bottom:1px dashed #ddd;"><a href="javascript:void(0)" onclick="doLogin('${ctx}/home.ht?from=gotoInquiryOrder',true);">需求订单</a></li>
					          <li><a href="javascript:void(0)" onclick="doLogin('${ctx}/cosimcloud/industryInquiryAdd.ht',true);">发布询价单</a></li>
					          <li><a href="javascript:void(0)" onclick="doLogin('${ctx}/home.ht?from=gotoProcurementOrder',true);">采购订单</a></li>
					      </ul>
					       <ul class="myCasicloud">
					          <li>卖家中心</li>
					          <li><a href="javascript:void(0)" onclick="doLogin('${ctx }/cosimcloud/abilityPublish.ht',true)">发布能力</a></li>
					          <li style="border-bottom:1px dashed #ddd;"><a href="javascript:void(0)" onclick="doLogin('${ctx}/home.ht?from=gotoAbilityOrder',true);">能力订单</a></li>
					          <li><a href="javascript:void(0)" onclick="doLogin('${ctx}/cosimcloud/industryAbilityPublish.ht',true)">发布商品</a></li>
					           <li><a href="javascript:void(0)" onclick="doLogin('${ctx}/home.ht?from=gotoSaleOrder',true);">销售订单</a></li>
					      </ul>
					   </div>
					</span> | 
					<a href="${ctx}/goToCosic.ht" style="color:#fff;">走进航天科工</a> | 
					<span id="serviceSupport" style="display:inline-block;position:relative;">
					   服务支持
					   <div id="serviceSupport_box">
					      <ul class="serviceSupport">
					          <li><a target="_blank" href="${ctx}/pages/cloud6.0/yzz/info/jsp/operateManual.jsp">用户手册</a></li>
							  <li><a target="_blank" href="${ctx}/pages/cloud6.0/yzz/info/jsp/yunList.jsp">培训视频</a></li>
					          <li><a onclick="xiaon();" target="_blank" style="cursor: pointer;">平台客服</a></li>
							  <li><a target="_blank" href="${ctx}/pages/cloud6.0/yzz/info/jsp/operate/shbz.jsp">售后保障</a></li>
					      </ul>
					   </div>
					</span> | 
					<span id="language" style="display:inline-block;position:relative;">
					   Language
					   <div id="language_box">
					      <ul class="language">
					          <li><a target="_blank" href="http://en.casicloud.com">English</a></li>
					      </ul>
					   </div>
					</span>
					<%-- <a href="${ctx}/pages/cloud6.0/yzz/info/jsp/yunList.jsp">培训视频</a> | 
					<a href="${ctx}/pages/cloud6.0/yzz/info/jsp/operateManual.jsp">帮助手册</a> | 
					<a onclick="nTalk.im_openInPageChat();" target="_blank">客服中心</a> | 
					<a href="${ctx}/cloud/pub/yunFeedback/create.ht">客服中心</a>  | 
					<a onclick="nTalk.im_openInPageChat();" target="_blank">客服中心</a> | 
					<a href="${ctx}/cloud/pub/yunFeedback/create.ht">客服中心</a> |  
					<a href="/cloud/pub/yunFeedback/htcloud/create.ht">客服中心</a> | --%>
					<select id="selectArea" onchange="changeArea(this)" style="border-radius:2px;border:none;color:#333;">
				        <option value="">---选择站点---</option>
				        <!-- <option value="nationwide">全国站点</option> -->
				        <option value="jiangxi">江西站点</option>
			        </select>
				</li>
			</ul>
		</div>
	</div>
	</div>
	<!-- ----end ------------------------- -->
<!-- 顶部 快捷栏 结束 -->

<!-- logo与搜索条 开始 -->
<div class="logo-bar">
	<div class="container wrap1160">
		<div class="col-xs-6 left-font">
			<ul class="logo-bar">
				<img src="${ctx}/pages/images/6.png"  width="320" height="65" alt="Planets" usemap="#planetmap" style="margin-top:12px;" />
				
				<map name="planetmap">
				  <area target="_blank" href="http://www.casic.com.cn/" shape="rect" coords="0,0,110,65"></area>
				  <area href="${ctx}/index.ht" shape="circle" coords="111,0,319,65"></area>
				</map>
			</ul>
		</div>
		<div class="col-xs-6 right-font">
			<ul class="logo-bar navbar-right">
				<li style="width: 485px; float: right; padding-top: 24px;">
					<%-- <form action="${ctx}/cosimcloud/jiangxiSearch.ht"> --%>
					<%-- <form action="${ctx}/cosimcloud/indexSearch.ht">
						<input name="select" onkeydown="if (event.keyCode==13) {}" onblur="if(this.value=='')value='- 搜索关键字 -';" onfocus="if(this.value=='- 搜索关键字 -')value='';" value="- 搜索关键字 -"  type="text" class="form-search-bar">
						<input name="搜索" type="button" value="搜索" class="form-search-btn" onclick="fullSearch(this)">
					</form> --%>
				</li>
			</ul>
		</div>
	</div>
</div>
<!-- logo与搜索条 结束 -->

<!-- nav 开始 -->
<%-- <div class="navbar">
	<div class="container wrap1250" style="width:1250px;">
		<div class="">
			<ul class="nav navbar-nav w1250">
				<li><a href="${ctx}/index.ht">首页</a></li>
				<li><a href="${ctx}/cosimcloud/newsCenter.ht?type=3">新闻中心</a></li>
				<li><a href="${ctx}/pages/cloud6.0/yzz/info/jsp/product.jsp">产品服务</a></li>
				<li><a href="${ctx}/pages/cloud6.0/yzz/info/jsp/solve.jsp">解决方案</a></li>
				<li><a href="${ctx}/pages/cloud6.0/yzz/info/jsp/cases.jsp">成功案例</a></li>
				<li><a onclick="openIndustry(this);" href="javascript:void(0);">航天专区</a></li>
				<li><a target="_blank" href="${ctx}/autonomousControlIndex.ht">自主可控专区</a></li>
			</ul>
		</div>
	</div>
</div> --%>
<!-- nav 结束 -->