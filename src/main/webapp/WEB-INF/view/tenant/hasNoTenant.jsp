<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<html>
<head>
<title>编辑企业信息</title>
<%@include file="/commons/include/form.jsp"%>

<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>

<script type="text/javascript">
	$(function() {
		layer.alert('请先增加企业信息！', {
			skin : 'layui-layer-molv' //样式类名
			,
			closeBtn : 0
		});

	});
</script>
</head>
<body>
</body>
</html>
