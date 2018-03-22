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
<div class="register_top">
            <div class="register_topCon">
                <ul>
                    <li><a href="javascript:void(0);">您好！${name}欢迎访问用户中心</a></li>
                   <!--  <li class="register_topCon_sign">|</li>
                    <li><a href="javascript:void(0);">免费注册</a></li> -->
                    <li style="float:right;padding-left:7px;"><a href="javascript:void(0);">全国站点</a></li>
                    <li style="float:right;"><a href="">切换</a></li>
                    <li class="register_topCon_sign2" style="float:right;">|</li>
                    <li style="float:right;"><a href="">客服中心</a></li>
                    <li class="register_topCon_sign2" style="float:right;">|</li>
                    <li style="float:right;"><a href="">帮助手册</a></li>

                </ul>
            </div>
        </div>
        <div class="register_logo">
            <div class="register_logoCon">
                <div class="register_logoCon_image f_left">
                    <a href="javascript:void(0);">
                        <img src="${ctx}/pages/images/user_register_logo.png" alt="" />
                    </a>
                    
                </div>
                <span class="register_logoName f_left">用户中心</span>
            </div>
        </div>

</body>
</html>