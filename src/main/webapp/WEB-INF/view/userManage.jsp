<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- <script id="allmobilize" charset="utf-8"
	src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script> -->
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" href="#" />
<meta charset="utf-8" />
<title>航天云网注册主页</title>
<%@ include file="/commons/include/form.jsp" %>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet"
	type="text/css" />
<%-- <link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" /> --%>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/layer/layer.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/jquery/jquery.validate.js"></script>
<%-- <script language="JavaScript" type="text/javascript"
	src="${ctx}/js/jquery/placeholderfriend.js"></script> --%>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>




<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
<script type="text/javascript">
var updateAccount=function(){
	
	window.location.href="${ctx}/user/updateAccount.ht";
}

var updateFullName=function(){
	
	window.location.href="${ctx}/user/updateFullName.ht";
}
	
$(window.parent.document).find("#mainiframe").load(function(){
		
		var mainheight = 800;
		 $(this).height(mainheight);
	});
	
	
	function shezhi(){
		$(window.parent.document).find("#ulleft a").each(function(){
			var str=this.innerHTML;
			if(str=='安全设置')
			{
			   $(window.parent.document).find("#ulleft a").removeClass("current");
				$(this).addClass("current");
			}
		});
		window.location.href="${ctx}/security/index.ht";
	}
	
	$(function(){
		$("#create").click(function(){
			 createSysOrgInfo();
		});
		$("#join").click(function(){
			 joinCompany();
		});
	});
	var yer=0;
	var createSysOrgInfo=function(){
		yer=layer.open({
		    type: 2,
		    title: '导航提示',
		    shadeClose: false,
		    fix: true, 
		    shade: 0.6,
		    offset:50,
		    area: ['980px', '700px'],
		    content: "${ctx}/tenant/editNew.ht", //iframe的url
		    cancel:function(index){
		    	 layer.close(index);
		    	 parent.window.location.reload();
		    	}
		}); 
	}
	var joinCompany=function(){
		yer=layer.open({
		    type: 2,
		    title: '加入企业',
		    shadeClose: false,
		    fix: true, 
		    shade: 0.6,
		    offset:50,
		    area: ['880px', '500px'],
		    content: "${ctx}/tenant/joinCompany.ht"//iframe的url
		   
		}); 
	}
	
	
	var certified=function(id){
		layer.close(yer);
		//window.open("http://core.casicloud.com/home.ht?menu=smrz&id="+id)
		window.open("http://b2b.casicloud.com/home.ht?menu=smrz&id="+id)
		//parent.window.location.href="${ctx}/tenant/maintain.ht?menu=smrz";
	}
	var loginout=function(){
		debugger;
		layer.close(yer);
		parent.window.location.reload();
	}
	
	var closeWin=function(){

		layer.close(yer);
		$("#create").attr("disabled","disabled").css("background","#dbdbdb");
	}
</script>
<style type="text/css">
a {
	color: #666;
	text-decoration: none;
}

body {
	color: #666;
	font-family: Microsoft YaHei;
	font-size: 14px;
}

#code {
	font-family: Arial;
	font-style: italic;
	font-weight: bold;
	border: 0;
	letter-spacing: 2px;
	color: blue;
}

.tipBox{
border: solid 1px #ddd;
height:70px;
border-radius:10px;
margin-bottom:10px;
}

#tip{
height: 20px;
margin-left: 10px;
margin-top: 10px;
}
#warmtips{
margin-left: 5px;
line-height: 30px;
}

.tipBox p span{
margin-left: 35px;
line-height: 20px;

}
  .register_mainCon_list{
    font-size: 14px;
  }
.tipinfo img {
    width: 20px;
    height: 20px;
    position: relative;
    top: 5px;
    }
  .register_mainCon_btn {
    width: 100px;
    height: 40px;
    font-size: 18px;
    cursor: pointer;
    background: #ff771d;
    color: #fff;
    border: none;
    margin-top: 30px;
    margin-left: 35px;
    line-height: 40px;
    text-align: center;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -ms-border-radius: 5px;
    -moz-border-radius: 5px;
    -o-border-radius: 5px;
}
</style>

</head>
<body>

<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>首次登录后，通过手机验证可以修改一次用户名。</span></p>
</div>

	<form  class="distance">
		<input type="hidden" name="userId" value="${user.userId}"/>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">用户名</span> 
			 ${user.account} 
			<div class="tipinfo"> 
			<c:choose>
			  <c:when test="${(sysOrgInfo.mark eq 2) and (user.updateTimes lt 1)}">
			    <a href="javascript:updateAccount()">
			     <img src="${ctx}/images/updatefullName.png"/>
			    </a>
			  </c:when>
			  <c:otherwise>
			   <a href="javascript:void(0)">
			     <img src="${ctx}/pages/images/haveset2.png"/>
			    </a>
			  </c:otherwise>
			</c:choose>
			<span style="color: red" >(用户名可以登录平台，不可修改)</span>
			</div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">会员姓名</span> 
			 ${user.fullname} 
			<div class="tipinfo"> 
			 <a href="javascript:updateFullName()">
			     <img src="${ctx}/images/updatefullName.png"/>
			 </a>
			</div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">企业名称</span> 
		    ${sysOrgInfo.name }
			<div class="tipinfo"></div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">手机号码</span> 
			 ${user.mobile} 
			<div class="tipinfo">
			<c:choose>
			   <c:when test="${user.isMobailTrue == '1'}">
			   <a href="javascript:void(0)"> 
			   <img src="${ctx}/pages/images/havebind1.png" />
			   </a>
			   </c:when>
			   <c:otherwise>
			    <a href="javascript:void(0)" onclick="shezhi()"> 
			      <img src="${ctx}/images/unbind.png" />
			     </a>
			   </c:otherwise>
			</c:choose>		
			<span style="color: red" >(手机号可以登录平台，可以修改绑定)</span>	
			</div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">邮箱号码</span> 
			 ${user.email} 
			<div class="tipinfo"> 
			<c:choose>
			   <c:when test="${user.isEmailTrue == '1'}">
			   <a href="javascript:void(0)"> 
			    <img src="${ctx}/pages/images/havevalidate.png" />
			   </a>
			   </c:when>
			   <c:otherwise>
			    <a href="javascript:void(0)" onclick="shezhi()"> 
			      <img src="${ctx}/images/unEmail.png" />
			     </a>
			   </c:otherwise>
			</c:choose>
			<span style="color: red" >(邮箱可以登录平台，可以修改绑定)</span>
			
			<a href="javascript:void(0)"></a></div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">注册时间</span> 
			<fmt:formatDate value="${user.createtime}" type="date" pattern="yyyy-MM-dd"/>  
			<div class="tipinfo"> 
		</div>
		  <c:if test="${state eq 0 or isNew ne 2}">
			<div class="register_mainCon_list" >
				<input class="register_mainCon_btn" 
					id="create" type="button" value="创建企业 "  >
				<div class="tipinfo"> </div>
			</div>
		
			<div class="register_mainCon_list" style="margin-top:-20px;" >
				<input class="register_mainCon_btn" 
					id="join" value="加入企业">
				<div class="tipinfo"> </div>
			</div>
	  </c:if>
		<input type="hidden" name="url" value="${url}" />
	</form>

</body>


</html>
