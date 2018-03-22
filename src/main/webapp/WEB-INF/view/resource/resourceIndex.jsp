<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>

<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<html>
<head>
<title>资源管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<%@include file="/commons/include/form.jsp" %>
<link rel="stylesheet" href="${ctx}/zTree_v3/css/demo.css" type="text/css">  
<link rel="stylesheet" href="${ctx}/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/js/lg/base.js"  ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js"  ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"  ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"  ></script>
<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.exedit-3.5.min.js"></script>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css"> 
html,body{padding:0px; margin:0; width:100%;height:100%;overflow:hidden;}  
#pageloading{position:absolute; left:0px; top:0px; background:white url('${ctx}/styles/default/images/loading.gif') no-repeat center; width:100%; height:100%; height:700px; z-index:99999;}
#top{color:White;height: 80px;}
#top a{color:white;}
.l-layout-center, .l-layout-top, .l-layout-bottom {
    position: absolute;
    border: 1px solid white;
    background: white;
    z-index: 10;
    overflow: hidden;
}
</style>
<script type="text/javascript">
var tab = null;
var tree = null;
var ctxPath=__ctx;
  $(function(){
	  //布局
    $("#layoutMain").ligerLayout({topHeight :80, leftWidth: 180, height: '100%', onHeightChanged: heightChanged });
	//取得layout的高度
    var height = $(".l-layout-center").height();	
      //Tab
    $("#framecenter").ligerTab({ height: height});
	//获取tab的引用
    tab = $("#framecenter").ligerGetTabManager();
	
    //隐藏加载对话框
    $("#pageloading").hide();

    //掩藏资源管理菜单选项
    $("#framecenter").find(".l-tab-links").hide();
    
    
   
    loadMenu();
	
	
  });
  
//布局大小改变的时候通知tab，面板改变大小
  function heightChanged(options){
  	$("iframe").each(function(){
  		if($(this).attr("name")!=undefined){
  			$(this).height(options.middleHeight-35);
  		}
  	});
		
  }
  
  
 function loadMenu(){
	var url=$("#url").val(); 
	var resName=$("#resName").val();
	var resId=$("#resId").val();
	var icon=$("#icon").val();
	if(url!=null && url!='' && url!='null'){
    	if(!url.startWith("http",false)) url=ctxPath +url;
    	//扩展了tab方法。
    	addToTab(url,resName,resId,icon);
	}
 }
//添加到tab或者刷新
 function addToTab(url,txt,id,icon){
 	if(tab.isTabItemExist(id)){
 		tab.selectTabItem(id);
 		tab.reload(id);
 	}
 	else{
 		tab.addTabItem({ tabid:id,text:txt,url:url,icon:icon});
 	}
 };
 
 
//firefox下切换tab的高度处置
	$(window).resize(function() {
		initRollButton();
		if(navigator.userAgent.indexOf("Firefox")>0){
	    	setTimeout( 
	    	function(){
              $.each($(".l-tab-content-item"),function(idx,obj) {
              	if($(this).attr("style").indexOf("hidden")>0){
              		$(this).css({"height":"0px"});
              	}
              });
	    	}, 500);
		}
	});
</script>
</head>
<body style="padding:0px;"> 
  <div id="pageloading"></div>
  <div id="layoutMain" style="width:100%">
  
  <input type="hidden" id="url" value="${resource.defaultUrl}">
  <input type="hidden" id="resName" value="${resource.resName}">
  <input type="hidden" id="resId" value="${resource.resId}">
  <input type="hidden" id="icon" value="${resource.icon}">
       <%--  <div position="left" title="<img src='${ctx}/styles/default/images/icon/home.png' >  ${currentSystem.sysName }"> 
	      	<ul id='leftTree' class='ztree' style="overflow:auto;height: 100%;" ></ul>
        </div>	  --%>
        
        <div position="center" id="framecenter"> 
           
        </div> 
    </div>    
</body>
</html>