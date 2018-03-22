<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta charset="utf-8">
<title>我的邀请</title>
<link href="${ctx}/js/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/layer/skin/layer.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/default/css/web.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/calendar/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript" src="${ctx}/js/invitited/myNewInvititedList.js" ></script>
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
   .panel-search{
    padding-left: 40px;
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

.panel-search {
    min-width: 450px;
    border: 1px #efefef solid;
    padding-left: 20px;
    padding-top: 3px;
    padding-bottom: 3px;
    margin: 0 0 0 0;
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

</script>
<body>

<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>邀请我的伙伴加入航天云网，体验工业4.0。邀请码：xxxx。</span></p>
</div>
<div class="MainBox">
	<div class="pageTitle">
	             <span class="tbar-label">
                                                    邀请统计
                 </span>
	</div>
  <div  class="timerdiv">
     <div class="panel-search">
				   <form id="searchForm" method="post" action="">
				        <span class="label">创建时间:</span><input id="productiondate"  name="Q_begincreatetime_DL"  class="inputText " style="width:10%;" value="${param['Q_begincreatetime_DL']}" onfocus="WdatePicker({maxDate:'%y-%M-%d'})"/>
						<span class="label">至</span><input  name="Q_endcreatetime_DG" class="inputText " style="width:10%;" value="${param['Q_endcreatetime_DG']}" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'productiondate\',{d:1})}'})"/>
				        <div class="group"><a class="link search" id="btnCount">统计</a></div>
				   </form>
		</div>
  </div>
   <div class="pageContent" >
     <div class=" table_title" align="center">企业邀请统计注册结果</div>
     <div style="width: 800px;" id="main" >
        
     </div>
   </div>
</div>
</div>

</body>
</html>
