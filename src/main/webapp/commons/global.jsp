<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ap" uri="/appleTag"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- <c:set var="fileCtx" value="http://${ctx}"/> --%>
<%-- <c:set var="fileCtx" value="http://www.casicloud.com/imageServer"/> --%>
<%-- <c:set var="fileCtx" value="http://img.cosimcloud.com/"/> --%>
<c:set var="fileCtx" value="https://oby0yx23h.qnssl.com"/>
<meta name="renderer" content="webkit">
<script type="text/javascript">
 document.domain="casicloud.com";  
</script>
<%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    pageContext.setAttribute("currentDate", sdf.format(new java.util.Date()));
    pageContext.setAttribute("currentDateWithoutTime", sdf.parse(sdf.format(new java.util.Date())));
    pageContext.setAttribute("currentDateWithTime", new java.util.Date());
    /**
    pageContext.setAttribute("tenant", com.hotent.core.util.ContextUtil.getCurrentOrgInfoFromSession());
    **/
%>
