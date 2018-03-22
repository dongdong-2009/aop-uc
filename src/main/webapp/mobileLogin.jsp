<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/commons/include/getCtx.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>BPM X3业务管理移动平台</title>
    <script src="${ctx}/js/senchaTouch/sencha-touch-all.gzjs"></script>
    <script src="${ctx}/js/senchaTouch/extend/htContainer.js"></script>
    <script src="${ctx}/js/senchaTouch/extend/htMsgBox.js"></script>
    <link rel="stylesheet" href="${ctx}/js/senchaTouch/resources/css/sencha-touch.css" />
    <link rel="stylesheet" href="${ctx}/styles/default/css/mobile.css" />
    <script type="text/javascript" src="${ctx}/js/hotent/platform/mobile/Util.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/mobile/MenuConfig.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/mobile/Init.js"></script>
    <script type="text/javascript">
        if (!Ext.browser.is.WebKit) {
            alert("The current browser is unsupported.\n\nSupported browsers:\n" +
                "Google Chrome\n" +
                "Apple Safari\n" +
                "Mobile Safari (iOS)\n" +
                "Android Browser\n" +
                "BlackBerry Browser"
            );
        }
    </script>
</head>
<body></body>
</html>