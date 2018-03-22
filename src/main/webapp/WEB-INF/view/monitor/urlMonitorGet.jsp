
<%--
	time:2015-10-14 09:55:16
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>订单详情</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript" src="${ctx}/js/urlMonitor/urlMonitorGet.js"></script>
<script type="text/javascript">
	
	function fanhui(){
		//返回上一个页面并刷新
		location.replace(document.referrer);
	}
</script>
</head>
<style type="text/css">
	.go-field-title h3 {
	    border-left: 5px solid #ff6600;
	    font-family: "microsoft yahei";
	    font-size: 18px;
	    line-height: 1.2;
	    margin: 14px 0;
	    padding-left: 10px;
	}
	.go-field-title {
	    border-bottom: 1px dashed #ddd;
	    margin: 0 10px 15px;
	    padding: 5px 15px;
	}
	
	.table-detail th{
		 width:20%;
	}
	.table-detail td{
		 width:80%;
	}
</style>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">URI监控详情</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="javascript:fanhui()">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
 
			<tr>
				<th>URI</th>
				<td>${urlBean.url}</td>
			</tr>
			
			<tr>
				<th>当天请求次数</th>
				<td>
				 <c:choose>
				     <c:when test="${urlBean.currentTimes != 0 }">
				      <a href="javascript:void(0)" onclick="linkDetail('${urlBean.url}','now')">${urlBean.currentTimes}</a>
				     </c:when>
				     <c:otherwise>
				        ${urlBean.currentTimes}
				     </c:otherwise>
				 </c:choose>  
				</td>
			</tr>
			
			<tr>
				<th>当天请求时间(毫秒)</th>
				<td>${urlBean.currentSeconds}</td>
			</tr>
			<tr>
				<th>总共请求次数</th>
				<td><a href="javascript:void(0)" onclick="linkDetail('${urlBean.url}','all')" style="text-decoration: none;">${urlBean.accessTotal}</a>
				 &nbsp;&nbsp;<a style="text-decoration: none;" href="javascript:void(0)" onclick="getEcharts('${urlBean.url}');">图形分析</a>
				</td>
			</tr>
			
			<tr>
				<th>总共请求时间(毫秒)</th>
				<td>${urlBean.accessTime}</td>
			</tr>
			
			<tr>
				<th>最后访问地址</th>
				<td>10.142.15.235</td>
			</tr>
			
			<tr>
				<th>最后访问时间</th>
				<td>${urlBean.endTime}</td>
			</tr>	
			<!-- 
			<tr>
				<th>失败次数</th>
				<td>0</td>
			</tr>	 -->		
		</table>
		
		</div>
		
	</div>
	
	<div style="height:100px; line-height:0px; font-size:0px; clear:both;overflow:hidden; display:block;"></div>
</body>
</html>

