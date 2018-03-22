<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<html>
<head>
	<title>通行证信息</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
		
	<!-- 上传图片 -->
	<script type="text/javascript" src="${ctx}/js/uc/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/cloudDialogUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/uploadPreview.js"></script>
 
	<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
     <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
	
	<link rel="stylesheet" type="text/css" href="${ctx}/guide/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/guide/css/businessSubscibe.css"/>
	<script src="${ctx}/guide/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
	
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	
	
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}/js/introduce/tool.js"></script>
    <script type="text/javascript">

    </script>
    
    <style type="text/css">
			.layui-layer{
				background-color: transparent;
				box-shadow:none;
				border-radius: 0;
			}
		</style>
	<script type="text/javascript">
		   window.HT_UID='${userId}';
		   window.HT_OID='${orgId}';
	</script>
    <script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
 
    
	<script type="text/javascript">
	
		
		$(function() {
		    var defurl="";
			var menu = $("#menu").val();
			if(menu==null||menu==''){
			}
			else{
			$("#ulleft a").removeClass("current");
			$("#"+menu).addClass("current");
				if(menu=="zhgl"){
					defurl = "${ctx}/user/userManage.ht";
				}
				
				if(menu=="jsxx"){
					defurl = "${ctx}/tenant/getBranchAccount.ht";
				}
				if(menu=="jsbd"){
					defurl = "${ctx}/tenant/settlementAccount.ht"
				}
				if(menu=="zxkf"){
					defurl = "${ctx}/xiaon/onLineService.ht";
				}
				if(menu=="smrz"){
					defurl = "${ctx}/tenant/certiInformation.ht";
				}
				if(menu=="qyxx"){
					defurl = "${ctx}/tenant/baseInfo.ht";
				}
				
				if(menu=="zzxx"){
					defurl = "${ctx}/tenant/orgInfo.ht";
				}
				if(menu=="sfhdz"){
					defurl = "${ctx}/tenant/addressEdit.ht";
				}
				if(menu=="aqsz"){
					defurl = "${ctx}/security/index.ht";
				}
				if(menu=="zzhgl"){
					defurl = "${ctx}/user/management.ht";
				
					 $(function(){
						
						/*位置*/
						var left = $("#zzhgl").offset().left;
						var top = $("#zzhgl").offset().top;
						
						 layer.closeAll();
						
		                 layer.open({
		                    type: 1,
		                    title:false,
		                    closeBtn: 0,
		                    area: ['600px', '600px'],
		                 //   offset: ['145px', '360px'],
		                    offset: [top-469, left+31],
		                    shadeClose: false,
		                    move: false,
		                    scrollbar: true,
		                    skin:'register-skin',
		                    content: $('.busOrder_layer_box1')
		                });
		                 
		                 

					$('.layertest1').on("click" , function(event){
						 /* 读取数据 */
						 //var num=$("table tbody tr").length;
						var num=$("#mainiframe").contents().find("#num")[0].className;
						
						$(".busOrder_layer_box2 a").addClass('img'+num);
						 
						var left1=$("#mainiframe").offset().left;
						var top1=$("#mainiframe").offset().top;
						var left=$("#mainiframe").contents().find("#1").position().left;
						var top=$("#mainiframe").contents().find("#1").position().top;
						event.preventDefault();
						layer.closeAll();
						 layer.open({
							type: 1,
							title:false,
							closeBtn: 0,
							area: ['600px', '600px'],
							offset: [top1+top-97+30+5, left1+left-505+20],
						//	offset: [top, left],
							shadeClose: false,
							move: false,
							scrollbar: true,
							skin:'register-skin',
							content: $('.busOrder_layer_box2')
						})
					});
					
					$('.layertest2').on("click" , function(event){
						var ob=$("#mainiframe").contents().find("#1");
						ob.click();	
						var left1=$("#mainiframe").offset().left;
						var top1=$("#mainiframe").offset().top;
						var left=$("#mainiframe").contents().find("#1").position().left;
						var top=$("#mainiframe").contents().find("#1").position().top;
						event.preventDefault();
						layer.closeAll();
					
						layer.open({
							type: 1,
							title:false,
							closeBtn: 0,
							area: ['600px', '600px'],
							offset: [top1+top-40+145-120, left1+left-724-15-60],
						//	offset: [300, 700],
							shadeClose: false,
							move: false,
							scrollbar: true,
							skin:'register-skin',
							content: $('.busOrder_layer_box3')
						})
					});
					$('.layertest3').on("click" , function(event){
					//	var ob=$('#mainiframe').contents().find('#frameDialog_').contents().find("#rolTree_18_check");
						//ob.click();
						event.preventDefault();
						layer.closeAll();
					});
		        });
					
					
					
					
				}
				if(menu=="yqtj"){
					defurl = "${ctx}/invitited/myList.ht";
				}
				if(menu=="tjhy"){
					defurl = "${ctx}/invitited/add.ht";
				}
				if(menu=="kxhxx"){
					defurl = "${ctx}/tenant/openCloseAccount.ht";
				}
				if(defurl!=""){
					$("#mainiframe")[0].src = defurl;
				}
			}
			
			$("#ulleft a").click(function(){
				 $("#ulleft a").removeClass("current");
				 $(this).addClass("current");
				var str = $(this)[0].innerText;
				var url ="";
				if(str=="账号管理"){
					url = "${ctx}/user/userManage.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="开户信息"){
					url = "${ctx}/tenant/openAccount.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="中金开户"){
					url = "${ctx}/tenant/getBranchAccount.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="中金绑卡"){
					url = "${ctx}/tenant/settlementAccount.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="在线客服"){
					url = "${ctx}/xiaon/onLineService.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="实名认证"){
					url = "${ctx}/tenant/certiInformation.ht";
					//$("#mainiframe")[0].style.width='1200px';
				}
				if(str=="企业信息"){
					url = "${ctx}/tenant/baseInfo.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				/* if(str=="密保管理"){
					url = "${ctx}/tenant/security.ht";
				} */
				if(str=="组织信息"){
					url = "${ctx}/tenant/orgInfo.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="收发货地址"){
					url = "${ctx}/tenant/addressEdit.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="安全设置"){
					url = "${ctx}/security/index.ht";
					//$("#mainiframe")[0].style.width='980px';
				}
				if(str=="子账号管理"){
					url = "${ctx}/user/management.ht";
					//$("#mainiframe")[0].style.width='1200px';
				}
				if(str=="邀请统计"){
					url = "${ctx}/invitited/myList.ht";
					//$("#mainiframe")[0].style.width='1200px';
				}
				if(str=="推荐会员"){
					url = "${ctx}/invitited/add.ht";
					//$("#mainiframe")[0].style.width='1200px';
				}
				if(str=="邀请补录"){
					url = "${ctx}/invitited/makeup.ht";
					//$("#mainiframe")[0].style.width='1200px';
				}
				
				if(url!=""){
					$("#mainiframe")[0].src = url;
				}
				
			})
			
			
			var isEmailTrue="${user.isEmailTrue}";
			var isMobileTrue="${user.isMobailTrue}";
			var c = getCookie("UserName");
			if (c != null) {
				return;
				}else{
					if(isEmailTrue!="1"&&isMobileTrue!="1"){
						layer.confirm("<font color='red'>尊敬的用户您好，您尚未对您的账号进行手机和邮箱验证，验证手机或邮箱后，可用于登录和密码找回，为了保障您的账号安全，请及时验证！</font>",
							{
						title:'提示',
					    btn:['立刻验证'],    //btn: ['立即验证','下次再说'], 
					    shade: false //不显示遮罩
					    }, function(index){
						//window.location.href="${ctx}/security/index.ht";
						$("#ulleft a").removeClass("current");
						$("#ulleft a").each(function(){
								var str=this.innerHTML;
								if(str=='安全设置')
								{
									$(this).addClass("current");
								}
							});
						$("#mainiframe")[0].src = "${ctx}/security/index.ht";
					    layer.close(index);
						},function(index){
							//window.location.href="${ctx}/tenant/maintain.ht";
							//window.location.href="${ctx}/tenant/maintain.ht?systemId="+${systemId};
							var today = new Date();
							var expires = new Date();
							expires.setTime(today.getTime() + 1000*60*60*24);
							//expires.setTime(today.getTime() + 1000*2);
							//setCookie("cookievalue", name, expires);
							addCookie("UserName","cs",1);
						    layer.close(index);
						});
						}
				if(isEmailTrue=="1"&&isMobileTrue!="1"){
						layer.confirm("<font color='red'>尊敬的用户您好，您尚未进行手机验证，为了保障您的账号安全性，请您及时验证。</font>",
							{
						title:'提示',
					    btn:['立刻验证','下次再说'],    //btn: ['立即验证','下次再说'], //按钮
					    shade: false //不显示遮罩
					    }, function(index){
						//window.location.href="${ctx}/security/index.ht";
						$("#ulleft a").removeClass("current");
						$("#ulleft a").each(function(){
								var str=this.innerHTML;
								if(str=='安全设置')
								{
									$(this).addClass("current");
								}
							});
						$("#mainiframe")[0].src = "${ctx}/security/index.ht";
					    layer.close(index);
						},function(index){
							//window.location.href="${ctx}/tenant/maintain.ht";
							//window.location.href="${ctx}/tenant/maintain.ht?systemId="+${systemId};
							var today = new Date();
							var expires = new Date();
							expires.setTime(today.getTime() + 1000*60*60*24);
							//expires.setTime(today.getTime() + 1000*2);
							//setCookie("cookievalue", name, expires);
							addCookie("UserName","cs",1);
						    layer.close(index);
						});
						}
				if(isEmailTrue!="1"&&isMobileTrue=="1"){
					layer.confirm("<font color='red'>尊敬的用户您好，您尚未对您的账号进行邮箱验证，验证邮箱后，可用于登录和密码找回，为了保障您的账号安全，请及时验证！</font>",
						{
					title:'提示',
				    btn:['立刻验证','下次再说'],   //btn: ['立即验证','下次再说'], //按钮
				    shade: false //不显示遮罩
				    }, function(index){
					//window.location.href="${ctx}/security/index.ht";
					$("#ulleft a").removeClass("current");
					$("#ulleft a").each(function(){
							var str=this.innerHTML;
							if(str=='安全设置')
							{
								$(this).addClass("current");
							}
						});
					$("#mainiframe")[0].src = "${ctx}/security/index.ht";
				    layer.close(index);
					},function(index){
						//window.location.href="${ctx}/tenant/maintain.ht";
						//window.location.href="${ctx}/tenant/maintain.ht?systemId="+${systemId};
						var today = new Date();
						var expires = new Date();
						expires.setTime(today.getTime() + 1000*60*60*24);
						//expires.setTime(today.getTime() + 1000*2);
						//setCookie("cookievalue", name, expires);
						addCookie("UserName","cs",1);
					    layer.close(index);
					});
					}
				
			
			
			
				}
			
		});
		
		function iFrameHeight(){
			var ifm= document.getElementById("mainiframe");   
			var subWeb = document.frames ? document.frames["mainiframe"].document : ifm.contentDocument;   
			if(ifm != null && subWeb != null) {
			   ifm.height = subWeb.body.scrollHeight;
			   ifm.width = subWeb.body.scrollWidth;
			}   
		  
		}
		$("#mainiframe").load(function(){
			 var mainheight = $(this).contents().find("body").height() + 30;
			 $(this).height(mainheight);
		});
		
	</script>
	<script type="text/javascript">
	
	function setCookie(name, value, expire) {   
	  window.document.cookie = name + "=" + escape(value) + ((expire == null) ? "" : ("; expires=" + expire.toGMTString()));
	}
	
	function getCookie(Name) {   
	   var findcookie = Name + "=";
	   if (window.document.cookie.length > 0) { // if there are any cookies
	     offset = window.document.cookie.indexOf(findcookie);
	  if (offset != -1) { // if cookie exists
	       offset += findcookie.length;          // set index of beginning of value
	    end = window.document.cookie.indexOf(";", offset)          // set index of end of cookie value
	      end = window.document.cookie.length;
	    if (end == -1)
	    return unescape(window.document.cookie.substring(offset, end));
	     }
	   }
	   return null;
	}
	
	
	$(function(){
		$(".myinvited").click(function(){
			var $this=$(this);
			if($this.find("i")[0].className=='triangle open'){
				$(this).find("i").removeClass("triangle open");
				$(this).find("i").addClass("triangle");
				 $(".index_style").slideUp('slow');
			}else{
				$(this).find("i").removeClass("triangle");
				$(this).find("i").addClass("triangle open");
				 $(".index_style").slideDown('slow');
			}
		});
		
	});
	
	//中金
	$(function(){
		$(".zjopen").click(function(){
			var $this=$(this);
			if($this.find("i")[0].className=='tree open'){
				$(this).find("i").removeClass("tree open");
				$(this).find("i").addClass("tree");
				 $("#indexstyle").slideUp('slow');
			}else{
				$(this).find("i").removeClass("tree");
				$(this).find("i").addClass("tree open");
				 $("#indexstyle").slideDown('slow');
			}
		});
		
	});
	</script>
	
<style type="text/css">
body, html {
    height: 100%;
    width: 100%;
}
.layui-layer-btn a {
    height: 30px;
    line-height: 30px;
    margin: 0 8px;
    padding: 0 20px;
    background: #ff771d;
    color: #fff;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;
    text-decoration: none;
}
.layui-layer-btn .layui-layer-btn1 {
    background: #ff771d;
}
 .myinvited .triangle.open {
    width: 0;
    height: 0;
    border-style: solid;
    _border-style: dotted;
    border-width: 5px;
    border-color: #666 transparent transparent transparent;
   position:absolute;
    left: 194px;
    top: 24px;
 }
  .zjopen .tree.open {
    width: 0;
    height: 0;
    border-style: solid;
    _border-style: dotted;
    border-width: 5px;
    border-color: #666 transparent transparent transparent;
   position:absolute;
    left: 194px;
    top: 24px;
 }
 .myinvited  .triangle {
    width: 0;
    height: 0;
    border-style: solid;
    _border-style: dotted;
    border-width: 5px;
    border-color: transparent transparent transparent #666;
    position:absolute;
    left: 194px;
    top: 24px;
 }
 .zjopen  .tree {
    width: 0;
    height: 0;
    border-style: solid;
    _border-style: dotted;
    border-width: 5px;
    border-color: transparent transparent transparent #666;
    position:absolute;
    left: 194px;
    top: 24px;
 }
 i {
    font-style: normal;
 }
 .myinvited{
   border-top: 1px solid #dfdfdf;
   width: 218px;height: 59px;
   text-align: center;
   line-height:59px;
   cursor: pointer;  
   position: relative; 
 }
 .zjopen{
   border-top: 1px solid #dfdfdf;
   width: 218px;height: 59px;
   text-align: center;
   line-height:59px;
   cursor: pointer;  
   position: relative; 
 }
 .index_style{
      border-top:1px solid #dfdfdf;
      display: none;
 }
 .index_style li a {
    display: block;
    text-decoration: none;
    -webkit-transition: all 0.25s ease;
    -o-transition: all 0.25s ease;
    transition: all 0.25s ease;
}
.index_style li{
  height: 40px;

}
.indexstyle{
      border-top:1px solid #dfdfdf;
      display: none;
 }
 .indexstyle li a {
    display: block;
    text-decoration: none;
    -webkit-transition: all 0.25s ease;
    -o-transition: all 0.25s ease;
    transition: all 0.25s ease;
}
.indexstyle li{
  height: 40px;

}
</style>
</head>
<body>
	<%-- <%@include file="/pages/jsp/uctop.jsp" %> --%>
	<input type="hidden" id="menu" name="menu" value="${menu}"/>
	<input type="hidden" id="systemId" name="systemId" value="${systemId}"/>
	<input type="hidden" id="sys_url" name="sys_url" value="${sys_url}"/>
	<%@ include file="/commons/top.jsp"  %>
	
         <div class="register_main" style="position:relative;">
            <div class="enterprise_baseInfor_navLeft">
                <ul id="ulleft">
                
                    <li><a href="javascript:void(0);" class="current" id="zhgl">账号管理</a></li>
                    <c:if test="${isManager}">
                    <li><a href="javascript:void(0);" id="qyxx">企业信息</a></li>
                 <!--    <li><a href="javascript:void(0);" id="smrz">实名认证</a></li> -->
                  <!--   <li><a href="javascript:void(0);" id="kxhxx">开销户信息</a></li> -->
                  	<div class="zjopen" style="background:#F8F8F8">中金管理<i class="tree" > </i></div>
                  	<div class="indexstyle" id="indexstyle">
                    <li style="height: 40px; line-height:40px;"><a href="javascript:void(0);" style="height:40px;" id="jsxx" >中金开户</a></li>
                    <li style="height: 40px; line-height:40px;"><a href="javascript:void(0);"style="height:40px;"  id="jsbd">中金绑卡</a></li>
                    </div >
                  <!--<li><a href="javascript:void(0);" id="jsxx">中金开户</a></li>-->  
                    <li><a href="javascript:void(0);" id="zxkf">在线客服</a></li>
                    <li><a href="javascript:void(0);" id="zzxx">组织信息</a></li>
                    <div class="myinvited" style="background:#F8F8F8">我的邀请<i class="triangle" > </i></div>
                    <div class="index_style" >
                    <li style="height: 40px; line-height:40px;"><a href="javascript:void(0);" style="height:40px; " >邀请统计</a></li>
                    <li style="height: 40px; line-height:40px;"><a href="javascript:void(0);"style="height:40px; "  >推荐会员</a></li>
                    <li style="height: 40px; line-height:40px;"><a href="javascript:void(0);"style="height:40px; "  >邀请补录</a></li>
                    </div >
                    <%-- <li><a href="${ctx}/druid/" target="_blank" >统计信息</a></li> --%>
                    <!-- <li><a href="javascript:void(0);">密保管理</a></li> -->
                    </c:if>
                     <li><a href="javascript:void(0);" id="aqsz">安全设置</a></li>
                    <li><a href="javascript:void(0);" id="sfhdz">收发货地址</a></li>
                     <c:if test="${isManager}">
                    <li >
                    
                       <a href="javascript:void(0);" id="zzhgl">子账号管理</a>
                    
                    </li>
                    </c:if>
                     
                </ul>
            </div>
            <div class="register_mainCon" style="padding-left:316px;padding-top:36px;">
            <iframe id="mainiframe" src="${ctx}/user/userManage.ht" width="1200px"
				height="700px"   style="margin-left: -200px; float: left;"
				frameborder="0" scrolling="no" ></iframe>
			</div>
			<div style="float: left">
			<%@include file="/commons/footer.jsp" %>
			</div>
	</div>
<style type="text/css">

a {
    color: #666;
    text-decoration: none;
}
a:hover{
text-decoration:none;
}
body {
    color: #666;
    font-family: Microsoft YaHei;
    font-size: 14px;
}

.enterprise_baseInfor_navLeft ul li a.current {
    background: #dfdfdf;
    color: #323232;
}
</style>
          
             <div class="busOrder_layer_box1" style="display:none;" >
	            <a href="javascript:void(0);" class="layertest1" >
					<p style="margin-left: 45px;">子账号管理</p>
	            </a>
	        </div>
			<div class="busOrder_layer_box2" style="display:none;">
				<a href="javascript:void(0);" class="layertest2 clearfix">
				</a>
	        </div>
			<div class="busOrder_layer_box3" style="display:none;" style:"background:red;">
	            <a href="javascript:void(0);"  class="layertest3 clearfix">
	            	<!-- <i class="f_left"></i>
	            	<p class="f_left">航天云网-销售经理</p> -->
	            </a>
			</div>
</body>
</html>
