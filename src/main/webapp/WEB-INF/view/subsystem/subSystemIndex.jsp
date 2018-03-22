<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<html>
<head>
	<title>运营中心</title>
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
	<%-- <script type="text/javascript" src="${ctx}/js/upload/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/upload/cloudDialogUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/upload/uploadPreview.js"></script> --%>
 
	<!-- tablegird -->
	<%-- <link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/style.css" rel="stylesheet" type="text/css" /> --%>
	<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
    <script type="text/javascript">
      
    </script>
	<script type="text/javascript">
		var bl=${bl };
		
		$(function(){
			var id=$("#usersystemId").val()
			if(id != "" && id != null){
				$("#mainiframe")[0].src="${ctx}/user/synchronize.ht?temid="+id+"&usersystemId="+id;
				//$("#mainiframe")[0].src="${ctx}/platform/subSystem/sysUser/list.ht?usersystemId="+id;
			}else{
				if(bl){
					$("#mainiframe")[0].src="${ctx}/platform/subSystem/sysUser/list.ht";
				}
			}
			
		})
		
		$(function() {
			$("#ulleft a").click(function(){
				 $("#ulleft a").removeClass("current");
				 $(this).addClass("current");
				var str = $(this)[0].innerText;
				var usersystemId=$("#usersystemId").val()
				var url ="";
				if(str=="子系统管理"){
					url = "${ctx}/subSystem/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="接口分类管理"){
					url = "${ctx}/interface/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="接口地址管理"){
					url = "${ctx}/interfaceUrl/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="监控中心"){
					url = "${ctx}/urlMonitor/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="企业用户管理"){
					if(usersystemId != "" && usersystemId != null){
						url = "${ctx}/user/synchronize.ht?temid="+usersystemId;
					}else{
						url = "${ctx}/user/synchronize.ht";
					}
					
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="秘钥管理"){
					url = "${ctx}/secretKey/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="邀请统计"){
					url = "${ctx}/invitited/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="企业审核"){
					if(usersystemId != "" && usersystemId != null){
						url = "${ctx}/tenant/audit.ht?temid="+usersystemId;
					}else{
						url = "${ctx}/tenant/audit.ht";
					}
					
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="菜单管理"){
					//url = "${ctx}/resource/index.ht";
				url = "${ctx}/platform/system/resources/tree.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="角色管理"){
					//url = "${ctx}/resource/roleManager.ht";
					url = "${ctx}/platform/system/sysRole/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(str=="用户管理"){
				     url = "${ctx}/platform/subSystem/sysUser/list.ht";
					//$("#mainiframe")[0].height="800px";
				}
				if(url!="" && usersystemId==""){
					$("#mainiframe")[0].src = url;
				}else if(usersystemId != "" && usersystemId != null&&url.indexOf("?")>0){
					$("#mainiframe")[0].src = url+"&usersystemId="+usersystemId;
				}else if(usersystemId != "" && usersystemId != null&&url.indexOf("?")<0){
					$("#mainiframe")[0].src = url+"?usersystemId="+usersystemId;
				}
				
			})
			
			
		});
		
		$("#mainiframe").load(function(){
			 var mainheight = $(this).contents().find("body").height() + 30;
			 $(this).height(mainheight);
		});
		
		
		
		function iFrameHeight(){


			
			var ifm= document.getElementById("mainiframe");   
			var subWeb = document.frames ? document.frames["mainiframe"].document : ifm.contentDocument;   
			if(ifm != null && subWeb != null) {
			   ifm.height = subWeb.body.scrollHeight;
			   ifm.width = subWeb.body.scrollWidth;
			   
			}   
		  
		}

		
	</script>
	
	<style type="text/css">
		body, html {
    height: 100%;
    width: 100%;
}
	</style>
</head>
<body>
	<%-- <%@include file="/pages/jsp/uctop.jsp" %> --%>
	     <input type="hidden" id="systemId" name="systemId" value="${info.systemId}"/>
	     <input type="hidden" id="usersystemId" value="${user.fromSysId}"/>
	  <%@ include file="/commons/top.jsp"  %>
		
         <div class="register_main" style="position:relative;">
            <div class="enterprise_baseInfor_navLeft">
                <ul id="ulleft">
                   
                   <li id="kl" <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);"  class="current" >企业用户管理</a></li>
                   <c:choose>
                      <%--    <c:when test="${ empty user.fromSysId}"> --%>
                         <c:when test="${flag}">
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);">子系统管理</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);" >接口分类管理</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);" >接口地址管理</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);" >监控中心</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);" >秘钥管理</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);" >邀请统计</a></li>
                    <li <c:if test="${bl and flag }">style="display: none"</c:if>><a href="javascript:void(0);" >企业审核</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="${ctx}/druid/" target="_blank">统计信息</a></li>
                         </c:when>
                   <%--       <c:otherwise>
                    <li id="kl" <c:if test="${!bl }">style="display: none"</c:if>><a href="javascript:void(0);" >企业用户管理</a></li>
                    <li <c:if test="${!bl }">style="display: none"</c:if>><a href="javascript:void(0);" >接口分类管理</a></li>
                    <li <c:if test="${!bl }">style="display: none"</c:if>><a href="javascript:void(0);" >接口地址管理</a></li>
                    <li <c:if test="${!bl }">style="display: none"</c:if>><a href="javascript:void(0);" >监控中心</a></li>
                    <li <c:if test="${!bl }">style="display: none"</c:if>><a href="javascript:void(0);" >秘钥管理</a></li>
                    <li <c:if test="${!bl }">style="display: none"</c:if>><a href="javascript:void(0);" >邀请统计</a></li>
                    <li <c:if test="${!bl or flag }">style="display: none"</c:if>><a href="javascript:void(0);" >企业审核</a></li>
                    <li <c:if test="${!bl }">style="display: none"</c:if>><a href="${ctx}/druid/" target="_blank">统计信息</a></li>
                         </c:otherwise> --%>
                   </c:choose>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);">用户管理</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);" >角色管理</a></li>
                    <li <c:if test="${bl }">style="display: none"</c:if>><a href="javascript:void(0);" >菜单管理</a></li>
                    
                </ul>
            </div>
            <div class="register_mainCon" style="padding-left:316px;padding-top:36px;">
           <%--  <iframe id="mainiframe" src="${ctx}/subSystem/list.ht" width="1400px" --%>
            <iframe id="mainiframe" src="${ctx}/user/synchronize.ht" width="1400px"
				height="800px"   style="margin-left: -250px; float: left;min-height: 800px;"
				frameborder="0" scrolling="no" ></iframe>
			</div>
			<div style="float: left">
			<%@include file="/commons/footer.jsp" %>
			</div>
	</div>
<style type="text/css">

.enterprise_baseInfor_navLeft{
	left:-100px;
}

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
</style>

</body>
</html>
