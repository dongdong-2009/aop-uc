<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hotent.core.util.ContextUtil" language="java"%>
<%@ page import="com.casic.util.PropertiesUtils" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="atx" value="${pageContext.request.localPort}"/>
<c:set var="btx" value="${pageContext.request.serverName}"/>
<c:set var="dtx" value="${pageContext.request.scheme}"/>
<script type="text/javascript" language="javascript" src="../js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/log.js"></script>
<script type="text/javascript">
$(function(){
	
	var systemId  =  '${systemId}';
	if(systemId==""||systemId==null){
		systemId = 100;
	}  
	if(systemId==10000054556562){
		$("#newLogo").addClass('gzgyy');
		$("#href").attr("href","http://share.gz-icloud.com.cn");
	}else if(systemId==10000065110011){
		$("#newLogo").addClass('hbgyy');
		$("#href").attr("href","http://hb.casicloud.com");
	}else if(systemId==10000064690722){
		$("#newLogo").addClass('jxgyy');
		$("#href").attr("href","http://www.jxicloud.com");
	}else if(systemId==10000050058950){
		$("#newLogo").addClass('scjm');
		$("#href").attr("href","http://www.scjmrh.org");
	}
	else{
		var gz = getCookieName("saas");
		if(gz=="gz"){
			$("#newLogo").addClass('gzgyy');
			$("#href").attr("href","http://share.gz-icloud.com.cn");
		}
		$("#newLogo").addClass('htyw');
		$("#href").attr("href","http://www.casicloud.com");
	}
})

</script>
<script type="text/javascript">

function getCookieName(cookie_name){
    var allcookies = document.cookie;
    var cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度
 
    // 如果找到了索引，就代表cookie存在，
    // 反之，就说明不存在。
    if (cookie_pos != -1)
    {
        // 把cookie_pos放在值的开始，只要给值加1即可。
        cookie_pos += cookie_name.length + 1;      //这里容易出问题，所以请大家参考的时候自己好好研究一下
        var cookie_end = allcookies.indexOf(";", cookie_pos);
 
        if (cookie_end == -1)
        {
            cookie_end = allcookies.length;
        }
 
        var value = unescape(allcookies.substring(cookie_pos, cookie_end));         //这里就可以得到你想要的cookie的值了。。。
    }
    return value;
}



var domain = '<%=PropertiesUtils.getProperty("platform.url")%>';//本地
var casLogin ='<%=PropertiesUtils.getProperty("cas.url")%>/login?service=';//本地
 

function toLogin(){
	//alert(location.protocol+"//"+location.hostname+":"+location.port+"/j_spring_cas_security_check"+"?spring-security-redirect="+location.pathname); 
	var pathName=window.location.pathname;
	var href=window.location.href;
	var url = (encodeURIComponent(location.protocol+"//"+location.hostname+":"+location.port+"/j_spring_cas_security_check"+"?spring-security-redirect="+pathName));
			window.location.href = casLogin+url;
	
}
function toLogout(systemId){
	// alert(location.protocol+"//"+location.hostname+":"+location.port+"/j_spring_cas_security_check"+"?spring-security-redirect="+location.pathname); 
	var href=window.location.href;
	
	if(systemId == 10000054556562){
		window.location.href = domain+"/cas/logout.ht?url=http://share.gz-icloud.com.cn";
	}else if(systemId == 10000065110011){
		window.location.href = domain+"/cas/logout.ht?url=http://hb.casicloud.com";
	}else if(systemId == 10000064690722){
		window.location.href = domain+"/cas/logout.ht?url=http://www.jxicloud.com";
	}else if(systemId == 10000050058950){
		window.location.href = domain+"/cas/logout.ht?url=http://www.scjmrh.org/sc-cmio/cloud/homepage/goIndex.ht";
	}
	else{
		window.location.href = domain+"/cas/logout.ht?url="+href;
	}

	
}
</script>
<!--

//-->
<style>
.welcome{
line-height: 97px;
text-align: right;
color: #474747;
margin-right: 100px;
}
</style>
<div class="clound_header_box" id="clound_logo">
            <div class="clound_header">
                 <a href="" id="href">
               	 	<h1  id="newLogo">
                   		 <span>航天云网</span>
                	</h1>
    	         </a>
                <i class="f_left"></i>
                <span class="f_left clound_header_safe">用户中心</span>
                <div class="welcome">
                <c:if test="<%=ContextUtil.getCurrentUser()==null%>">
				<span>您好，欢迎来到用户中心！</span>
				<a href="#" onclick="toLogin()">登录</a> 
				</c:if>
				
				<c:if test="<%=ContextUtil.getCurrentUser()!=null%>">
				<span>您好，<%=ContextUtil.getCurrentUser().getFullname()%> 欢迎来到用户中心！</span>
				<a href="#"  onclick="toLogout(${systemId})">注销</a>
				</c:if>
				<!--登录之后-->
            	</div>
            </div>
        </div>
<%-- <div class="clound_header_box" id="gzgyy_logo" style="display: none">
            <div class="clound_header">
                <h1 class="clound_header_gzgyy_logo f_left">
                    <span>贵州工业云</span>
                </h1>
                <i class="f_left"></i>
                <span class="f_left clound_header_safe">用户中心</span>
                <div class="welcome">
                  <c:if test="<%=ContextUtil.getCurrentUser()==null%>">
				<span>您好，欢迎来到用户中心！</span>
				<a href="#" onclick="toLogin()">登录</a> 
				</c:if>
				
				<c:if test="<%=ContextUtil.getCurrentUser()!=null%>">
				<span>您好，<%=ContextUtil.getCurrentUser().getFullname()%> 欢迎来到用户中心！</span>
				<a href="#"  onclick="toLogout()">注销</a>
				</c:if>
				<!--登录之后-->
            	</div>
            </div>
        </div> --%>