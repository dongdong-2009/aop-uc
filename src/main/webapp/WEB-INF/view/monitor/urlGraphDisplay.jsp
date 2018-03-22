<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>图表展示</title>
<script type="text/javascript">
var CTX="${pageContext.request.contextPath}";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<link href="${ctx}/FushionCharts/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/FushionCharts/css/prettify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/FushionCharts/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/FushionCharts/js/FusionCharts.js"></script>
<script type="text/javascript" src="${ctx}/FushionCharts/js/prettify.js"></script>
<script type="text/javascript" src="${ctx}/FushionCharts/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/FushionCharts/js/lib.js" ></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
 <script type="text/javascript" src="${ctx}/FushionCharts/js/glow.js" ></script>
</head>
 <body>
      <input type="hidden" id="url" value="${url}"/>
        <h3 class="chart-title">图表展示</h3>
        <p>&nbsp;</p>
        <div id="chartdiv" align="center">Chart will load here</div>
		  <p>&nbsp;</p>
        <br/>
        <!--  <div class="qua-button-holder"></div>

        <div class="show-code-block"></div> -->
    </body>
</html>