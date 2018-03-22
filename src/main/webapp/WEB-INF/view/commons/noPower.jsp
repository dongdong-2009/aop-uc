<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>  -->
<title>用户中心</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript"
	src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
</head>
<body>
     <input type="hidden" id="systemId" name="systemId" value="${info.systemId}"/>
	<%@ include file="/commons/top.jsp"  %>
	
	<div id="img" style="text-align:center;margin-top: 50px;">
		<img alt="你没有访问权限" src="/pages/images/noPower.jpg">
	</div>
	
	<%@ include file="/commons/footer.jsp"%>

</body>
</html>