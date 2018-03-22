<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ap" uri="/appleTag"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用戶中心</title>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="register_footer">
            <div class="register_footerCon">
                <span class="register_copy">Copyright © 2015, All Rights Reserved　京ICP备05067351号-2</span><span style="margin-left:18px;"><a href="javascript:void(0);">法律声明</a></span><span style="margin-left:11px;margin-right:5px;">|</span><span><a href="javascript:void(0);">站点地图</a></span><span style="margin-left:11px;margin-right:5px;">|</span><span><a href="javascript:void(0);">诚聘精英</a></span>
            </div>
        </div>

</body>
</html>