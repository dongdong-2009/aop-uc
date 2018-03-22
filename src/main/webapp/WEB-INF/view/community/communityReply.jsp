<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ap" uri="/appleTag"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="fileCtx" value="http://img.cosimcloud.com/"/>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>社区</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/fon.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/local.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/layui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/layer/skin/laypage.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
var __ctx="${pageContext.request.contextPath}";
var fileCtx="http://img.cosimcloud.com/";
</script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/upload/cloudDialogUtil.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/upload/ajaxfileupload.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/upload/uploadPreview.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.all.js"></script>
<script type="text/javascript" href="${ctx}/ueditor142/lang/zh-cn/zh-cn.js" ></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/addCustomizeButton_news.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/laypage.js"></script>
<script type="text/javascript" src="${ctx}/js/community/communityReply.js"></script>

<script type="text/javascript">
	
</script>
</head>
<body>
<%@ include file="/commons/top.jsp" %>
<%@ include file="/commons/navigator.jsp"  %>

 <div class="main layui-clear">
           <div class="content detail">
		     <h1>${questionBean.title}</h1>
			 <div class="detail-about"> 
			 <a class="jie-user" href="javascript:void(0);"> 
			 <img src="${ctx}/resources/chrome/11424.gif" >
			
			 <cite> ${questionBean.createByName} <em>发布于${questionBean.createTime}</em>  
			 </cite> </a> 
			 <div class="detail-hits" data-id="3584" id="adopt">
			 <c:if test="${questionBean.status==0}"> 
			 	<span class="jie-status">未采纳</span> 
			 </c:if>    
			 <c:if test="${questionBean.status==1}"> 
			 	<span class="jie-status jie-status-ok">已采纳</span> 
			 </c:if>    
			 </div> 
			 </div>
		 <div class="detail-body photos"> 
			 ${questionBean.content}
			
		</div> 
		<h2 class="page-title" style="margin-top:0; padding-left:0;">热忱回答<span>（<em id="jiedaCount">12</em>）</span></h2>
		<ul class="jieda photos" id="jieda">  
		      <li data-id="11143"> 
			     <a name="item-1471054517509"></a> 
				 <div class="detail-about detail-about-reply"> 
				 <a class="jie-user" href="/u/784056/"> 
				   <img src="${ctx}/resources/chrome/634032.png" alt="小魔方">
				 <cite> <i>老方</i>   </cite> </a> 
				    <div class="detail-hits"> 
				       <span>3天前</span> 
				    </div>  
			      </div> 
				 <div class="detail-body jieda-body"> 很多人问过这问题, 都没人回答, 我都直接找上站长本人了. 都没给个答复. 所以很负责任的告诉你, 做不到. </div> 
				 <div class="jieda-reply"> <span class="jieda-zan " type="zan"> <i class="iconfont icon-zan"></i> <em>0</em> </span> <span type="reply"> <i class="iconfont icon-svgmoban53"></i> 回复 </span>  </div>
			  </li>
			  <li data-id="11143"> 
			     <a name="item-1471054517509"></a> 
				 <div class="detail-about detail-about-reply"> 
				 <a class="jie-user" href="/u/784056/"> 
				 <img src="${ctx}/resources/chrome/634032.png" alt="老方" layer-index="0"> 
				 <cite> <i>老方</i>   </cite> </a> 
				    <div class="detail-hits"> 
				       <span>3天前</span> 
				    </div>  
			      </div> 
				 <div class="detail-body jieda-body"> 很多人问过这问题, 都没人回答, 我都直接找上站长本人了. 都没给个答复. 所以很负责任的告诉你, 做不到. </div> 
				 <div class="jieda-reply"> <span class="jieda-zan " type="zan"> <i class="iconfont icon-zan"></i> <em>0</em> </span> <span type="reply"> <i class="iconfont icon-svgmoban53"></i> 回复 </span>  </div>
			  </li>
			  <li data-id="11143"> 
			     <a name="item-1471054517509"></a> 
				 <div class="detail-about detail-about-reply"> 
				 <a class="jie-user" href="/u/784056/"> 
				 <img src="${ctx}/resources/chrome/634032.png" alt="老方" layer-index="0"> 
				 <cite> <i>老方</i>   </cite> </a> 
				    <div class="detail-hits"> 
				       <span>3天前</span> 
				    </div>  
			      </div> 
				 <div class="detail-body jieda-body"> 很多人问过这问题, 都没人回答, 我都直接找上站长本人了. 都没给个答复. 所以很负责任的告诉你, 做不到. </div> 
				 <div class="jieda-reply"> <span class="jieda-zan " type="zan"> <i class="iconfont icon-zan"></i> <em>0</em> </span> <span type="reply"> <i class="iconfont icon-svgmoban53"></i> 回复 </span>  </div>
			  </li>
       </ul>
       <div id="pager" style="margin-top: 20px;"></div> 
        <form action="" method="post" class="layui-form" id="replyForm" > 
		         <ul class="layui-form"> 
				      <li class="layui-form-li"> 
					     
						   <script id="container" name="desc" type="text/plain" style="width: 718px;height: 240px;" >
							
    					   </script>
						 
					  </li> 
						  <li> 
						  <input  type="hidden"  name="createById" id="createById" value="${questionBean.createById}"> 
						  <input  type="hidden"  name="replyId" id="userId" value="${user.userId}"> 
						  <input  type="hidden"  name="createByName" value="${questionBean.createByName}"> 
						  <input  type="hidden"  name="questionId"  id="questionId" value="${questionBean.id}">
						  <!-- 获取是否采纳状态 -->
						  <input  type="hidden"  name="status"  id="status" value="${questionBean.status}">
						  <input  type="hidden"  name="content"  id="content" > 
						  <input type="button" value="提交解答" class="layui-btn" style="background-color:#009E94;color:#fff;" id="btn">
						  <input type="button" value="返回" class="layui-btn" style="background-color:#009E94;color:#fff;margin-left: 100px;" onclick="fanhui();">
						  </li> 
				   </ul> 
		</form>  

 </div>
</div>

<%@ include file="/commons/footer.jsp" %>
<script type="text/javascript" src="${ctx}/js/navigator.js"></script>
</body>
</html>