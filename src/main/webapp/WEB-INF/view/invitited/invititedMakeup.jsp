<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/include/get.jsp" %>
<html>
<head>
<meta charset="utf-8">
<title>邀请补录</title>
<link href="${ctx}/js/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/layer/skin/layer.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/default/css/web.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/invitited/invititedMakeup.js" ></script>
<script type="text/javascript" src="${ctx}/js/invitited/myInvititedList.js"></script> 
<script type="text/javascript">
 var CTX="${pageContext.request.contextPath}";

</script>
</head>
<style type="text/css">
*{margin:0;padding:0;}
body{font-size:14px;font-family:"微软雅黑";padding:20px;}
.MainBox{width:100%;position: relative;}
/*标题样式 start*/
.pageTitle{padding:8px 20px;position: relative;text-align:left;border-bottom: 1px solid #E2DFDF;}
 .MainBox ul{padding:5px 0px 10px 10px;;border-bottom: 4px solid #ff6600;height:20px;margin-left:10px;margin-bottom:0px;}
  .MainBox ul li{ list-style:none;float:left;height:20px;line-height:20px;border:1px solid #E4D8D8;border-bottom:0px;padding:5px 20px;margin-left:10px;border-radius:4px;cursor:pointer;}
.MainBox ul .active{background:#ff6600;color:#fff;}
.pageContent{width:80%;padding:40px;margin-top:40px;margin-left:40px;background: #fff;display: none;}
.pageContent .table_title{color:#333333;padding-left: 50px;font-size: 30px;border-bottom:1px solid #fff;}
.pageContent .newadd{margin:10px 0px;}
.chen_table{width:95%; border-collapse:collapse;margin:0 auto;color:#333;margin-top:5px;}
/*th具有加粗*/
.chen_table th,td{height:34px;padding-left:10px;border:1px solid #ccc;text-align:center;}
.chen_table td a{float:left;padding-left:7px;}

/* fortm 表单*/
.formgroup{width:100%;padding:5px 20px;}
.clearfix{ clear:both;}
.formgroup .label-text{float: left;width: 7em;text-align: right;margin-right: 15px;line-height: 36px;}
.formgroup .input-text{float: left;}
.formgroup .i-text{height: 24px; line-height: 24px;padding:0 5px;width: 218px;px;color:#636363;}
.formgroup .mb{padding:0px 5px;}
.formgroup  .red{padding-left: 5px;color:red;}
.egister_mainCon_btn {
				     width: 80px;height: 40px; font-size: 18px; cursor: pointer; background: #ff771d;
				     color: #fff;border: none; line-height: 40px; text-align: center; border-radius: 5px;
				     -webkit-border-radius: 5px; -ms-border-radius: 5px; -moz-border-radius: 5px; -o-border-radius: 5px;
				      margin-left: 300px;
				    }
.clickBtn{
  background: #a3c0e8;
}
  span.tbar-label {
    border-left: 5px solid #ff6600;
    font-family: "microsoft yahei";
    font-size: 16px;
    line-height: 1.2;
    margin: 14px 0;
    padding-left: 10px;
   }
   td span{
    color: #666;
   }
   textarea {
	resize: none;
 }
th td{
  width: 15%
}
 .timerdiv{
    width: 95%;
    padding: 20px;
    background: #fff;
    background: inherit;
    background-color:#fff;
    box-sizing: border-box;
    border-width: 1px;
    border-radius: 0px;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    box-shadow: none;
   }

   .group {
    margin-right: 2px;
    margin-left: 0px;
    padding-left: 0px;
    float: right;
  }
  .l-bar-separator {
    float: right;
    height: 18px;
    border-left: 1px solid #9AC6FF;
    border-right: 1px solid white;
    margin: 2px;
 }
#main{
   margin-left: 75px;
   text-align: center;
   height:400px;

}



.zr-element{
 cursor: pointer;
}

	.tipBox{
border: solid 1px #ddd;
height:70px;
border-radius:10px;
margin-bottom: 10px;
font-size: 14px;
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

.register_mainCon_list {
    line-height: 30px;
    margin-bottom: 25px;
    font-size: 14px;
}
.register_mainCon_btn {
    width: 100px;
    height: 40px;
    font-size: 18px;
    cursor: pointer;
    background: #ff771d;
    color: #fff;
    border: none;
    line-height: 40px;
    text-align: center;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -ms-border-radius: 5px;
    -moz-border-radius: 5px;
    -o-border-radius: 5px;
    margin-left:360px;
}
</style>

<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
<script type="text/javascript">
$(window.parent.document).find("#mainiframe").load(function(){
	var mainheight = 800;
	 $(this).height(mainheight);
});
$(function(){
	  $("#myNewList").click(function(){
	  $("div.timerdiv").hide();
	  $(".pageContent").show();
      });
	  $("#makeup").click(function(){
	  $(".pageContent").hide();
	  $("div.timerdiv").show();
      });
});



</script>
<body>

<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>如您已经邀请企业入驻航天云网，在会员注册时没有填写您的邀请码，请在此处补录.</span></p>
</div>

	<div class="pageTitle">
	           <span class="tbar-label">
	           <a  href="javascript:void(0)" id="myNewList" style="cursor:pointer;color: #336699;margin-top:1px;">邀请补录</a><!--/invitited/myNewList.ht  -->
               </span>
	          <!--  <span class="tbar-label">
	            <a  href="javascript:void(0)" id="makeup" style="cursor:pointer;color: #336699;margin-top:1px;">邀请补录</a>
               </span> -->
    </div>

  <div  class="timerdiv" style="display:none;">
     <form id="searchForm" method="post" action=""  >
        <input type="hidden" name="sysOrgInfoId" value="${tenantInfo.sysOrgInfoId}" >
        <input type="hidden" id="currentName" value="${tenantInfo.name}" >
		<div class="formgroup clearfix">
      <div class="label-text"><span class="red"></span>公司名称：</div>
      <%-- <c:if test="${empty invitedInfo }"> --%>
      <div class="input-text" id="div1"><input type="text" name="name" id="name" class="i-text" >
    <%--   </c:if> --%>
    <%--   <c:if test="${not empty invitedInfo}">
      <div class="input-text" style="margin-top:7px;">
      <span>${invitedInfo.name}</span>
       <input type="hidden" name="name" id="name" value="${invitedInfo.name}"/> </c:if>--%>
      <font color="red"></font></div> 
      </div> 
      <div class="formgroup clearfix">
      <div class="label-text"><span class="red"></span>企业号：</div>
      <div class="input-text" style="margin-top:7px;"> 
    <%--   <span>${tenantInfo.invititedCode}</span> --%>
      <input type="text" name="invititedCode" id="invititedCode"  readonly="readonly" class="i-text"><font color="red"></font>
     <%--  <input type="hidden" name="invititedCode" id="invititedCode" value="${tenantInfo.invititedCode}"><font color="red"></font> --%>
      </div>
      </div>
   <%--    <c:if test="${empty invitedInfo}"> --%>
      <div class="formgroup clearfix" >
      <span style="width: 100px;padding-left: 110px;" > 
      <input type="button" value="保存" id="sub" class="egister_mainCon_btn">
      </span>
    </div>
	<%-- </c:if> --%>
	
 </form> 
 </div>


   
   
     <div class="pageContent"style="display:block;margin-left: 0px;margin-top: 0px;padding-top: 0px;padding-left: 0px;padding-bottom: 0px;padding-right: 0px;">
		<!-- <div class="panel-top">
			<div class="l-bar-separator"></div>
				<div class="toolBar">
					<div class="group" style="display:inline"></div>
				</div>
			</div> -->
	  <div class="toolBar" style="width:200px;display:inline">
	<!-- <a class="link export" id="export" style="margin-right: 880px;padding-left: 20px;width: 25px;display: block;">导出</a><a  type="button" id="makeup" style="cursor:pointer;color: #336699;margin-top:1px;margin-right: 793px;padding-left: 60px;width: 78px;display: block;">邀请补录+</a> -->
        <a  id="export" class="link export" style="cursor: pointer;color: #336699;height: 20px;font-size: 16px;" >导出</a>
        <button id="makeup" style="margin-left: 10px;background-color: white;border-color: white;">邀请补录+</button>
     </div>
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			     <display:table name="tenantInfoList" id="tenantInfoItem" requestURI="${ctx}/invitited/makeup.ht" sort="external" cellpadding="0" cellspacing="0" class="table-grid">
					<display:column  title="序号"  >${tenantInfoItem_rowNum}</display:column>
					<display:column property="name" title="企业名称" sortable="true" sortName="name" ></display:column>
					<display:column  title="账户类型" sortable="true"  >注册会员</display:column>
					<display:column  title="创建日期" sortable="true" sortName="createtime" >
						<fmt:formatDate value="${tenantInfoItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
				 </display:table>
				<hotent:paging tableId="tenantInfoItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<iframe style="display:none;" id="downFile"></iframe>
</body>
</html>
