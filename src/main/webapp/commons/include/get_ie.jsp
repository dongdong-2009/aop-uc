<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="rptctx" value="http://182.50.3.220:8075/WebReport/ReportServer" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />

<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/displaytag_ie.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/calendar/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/ddaccordion.js"></script>
<%@include file="/js/msg.jsp"%>

<!-- 增加Scroll处理,zouping,2014-06-04,在form.jsp里面也加了这一句,没关系,只会加载一次 -->
<script type="text/javascript" src="${ctx}/js/scroll/hScrollPane.js"></script>
<link href="${ctx}/js/scroll/hScrollPane.css" rel="stylesheet" type="text/css" />

<!-- 后台1.0版样式 
<f:link href="Aqua/css/ligerui-all.css"></f:link>
<f:link href="web.css"></f:link>
-->

<!-- 后台2.0版样式 -->
<link href="${ctx}/styles/2.0/css/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/2.0/css/web.css" rel="stylesheet" type="text/css" />