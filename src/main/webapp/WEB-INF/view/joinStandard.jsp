<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>子系统接入规范</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript">
$(function(){
	$(".api_left ul li").each(function(index){
	  $(this).click(function(){
		 $(".api_right div.api_items").hide();
		 $(".api_right div.api_items").eq(index).slideDown();
		 })
	  })
	})
</script>
<style type="text/css">
.api_bread { height: 38px; overflow: hidden; line-height: 38px; color: #323232; padding: 0 5px; margin-left: 90px;}
.api_left { float: left; display: inline; width: 220px; min-height: 344px; overflow: hidden; background: #f5f5f5; margin-left: 90px;}
.api_right { margin-left: 320px; min-height: 700px; }
</style>
</head>
<body>
<%@ include file="/commons/top.jsp" %>
<%@ include file="/commons/navigator.jsp"  %>

<div class="api_box clearfix">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="joinStandard.ht">接入规范</a></div>
    <div class="api_left">
    	<h2>所有规范</h2>
        <ul>
        	<li><a href="javascript:void(0);">用户规范</a></li>
            <li><a href="javascript:void(0);">企业规范</a></li>
            <li><a href="javascript:void(0);">认证规范</a></li>
            <li><a href="javascript:void(0);">开户规范</a></li>
            <li><a href="javascript:void(0);">收发货规范</a></li>
            <li><a href="javascript:void(0);">密码规范</a></li>
            <li><a href="javascript:void(0);">openId规范</a></li>
        </ul>
    </div>
    <div class="api_right">
    	<div class="api_items" style="display:block;">
        	<h2>用户规范</h2>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                	<!-- <td width="37%">API列表</td>
                    <td width="10%">类型</td>
                    <td width="53%">描述</td> -->
                    <td width="47%">类型</td>
                    <td width="53%">描述</td>
                </tr>
                <!-- <tr>
                	<td>enterprise/register</td>
					<td><span class="api_item_red">收费</span></td>
                    <td>获取一个产品的信息</td>
                </tr> -->
                <tr>
					<td><a href="userStandard.ht?type=1"><span class="api_item_green">增加</span></a></td>
                    <td>增加用户信息</td>
                </tr>
                <tr>
					<td><a href="userStandard.ht?type=2"><span class="api_item_green">修改</span></a></td>
                    <td>修改用户信息</td>
                </tr>
                <tr>
					<td><a href="userStandard.ht?type=3"><span class="api_item_green">删除</span></a></td>
                    <td>删除用户信息</td>
                </tr>
            </table>
        </div>
        
        <div class="api_items">
        	<h2>企业规范</h2>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                    <td width="47%">类型</td>
                    <td width="53%">描述</td>
                </tr>
                 <tr>
					<td><a href="enterpriseStandard.ht?type=1"><span class="api_item_green">增加</span></a></td>
                    <td>增加企业信息</td>
                </tr>
                <tr>
					<td><a href="enterpriseStandard.ht?type=2"><span class="api_item_green">修改</span></a></td>
                    <td>修改企业信息</td>
                </tr>
                <tr>
					<td><a href="enterpriseStandard.ht?type=3"><span class="api_item_green">删除</span></a></td>
                    <td>删除企业信息</td>
                </tr>
            </table>
        </div>
        
        <div class="api_items">
        	<h2>认证规范</h2>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                    <td width="47%">类型</td>
                    <td width="53%">描述</td>
                </tr>
                <tr>
					<td><a href="authenticationStandard.ht?type=1"><span class="api_item_green">增加</span></a></td>
                    <td>增加认证信息</td>
                </tr>
                <tr>
					<td><a href="authenticationStandard.ht?type=2"><span class="api_item_green">修改</span></a></td>
                    <td>修改认证信息</td>
                </tr>
                <tr>
					<td><a href="authenticationStandard.ht?type=3"><span class="api_item_green">删除</span></a></td>
                    <td>删除认证信息</td>
                </tr>
            </table>
        </div>
        
        <div class="api_items">
        	<h2>开户规范</h2>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                    <td width="47%">类型</td>
                    <td width="53%">描述</td>
                </tr>
                 <tr>
					<td><a href="branchStandard.ht?type=1"><span class="api_item_green">增加</span></a></td>
                    <td>增加开户信息</td>
                </tr>
                <tr>
					<td><a href="branchStandard.ht?type=2"><span class="api_item_green">修改</span></a></td>
                    <td>修改开户信息</td>
                </tr>
                <!-- <tr>
					<td><a href="branchStandard.ht?type=3"><span class="api_item_green">删除</span></a></td>
                    <td>删除开户信息</td>
                </tr> -->
            </table>
        </div>
        
        <div class="api_items">
        	<h2>收发货规范</h2>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                    <td width="47%">类型</td>
                    <td width="53%">描述</td>
                </tr>
                <tr>
					<td><a href="goodsStandard.ht?type=1"><span class="api_item_green">增加</span></a></td>
                    <td>增加收发货信息</td>
                </tr>
                <tr>
					<td><a href="goodsStandard.ht?type=2"><span class="api_item_green">修改</span></a></td>
                    <td>修改收发货信息</td>
                </tr>
                <!-- <tr>
					<td><a href="goodsStandard.ht?type=3"><span class="api_item_green">删除</span></a></td>
                    <td>删除收发货信息</td>
                </tr> -->
            </table>
        </div>
        
        <div class="api_items">
        	<h2>密码规范</h2>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                    <td width="47%">类型</td>
                    <td width="53%">描述</td>
                </tr>
                <tr>
					<td><a href="passwordStandard.ht?type=1"><span class="api_item_green">增加</span></a></td>
                    <td>添加密码</td>
                </tr>
                <tr>
					<td><a href="passwordStandard.ht?type=2"><span class="api_item_green">修改</span></a></td>
                    <td>修改密码</td>
                </tr>
                <tr>
					<td><a href="passwordStandard.ht?type=3"><span class="api_item_green">删除</span></a></td>
                    <td>删除密码</td>
                </tr>
            </table>
        </div>
        
        <div class="api_items">
        	<h2>openId规范</h2>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                    <td width="47%">类型</td>
                    <td width="53%">描述</td>
                </tr>
                <tr>
					<td><a href="openIdStandard.ht?type=1"><span class="api_item_green">增加</span></a></td>
                    <td>添加openId</td>
                </tr>
                <tr>
					<td><a href="openIdStandard.ht?type=2"><span class="api_item_green">修改</span></a></td>
                    <td>修改openId</td>
                </tr>
                <tr>
					<td><a href="openIdStandard.ht?type=3"><span class="api_item_green">删除</span></a></td>
                    <td>删除openId</td>
                </tr>
            </table>
        </div>
        
    </div>
</div>


<!--casicloud footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--casicloud footer end-->

<script type="text/javascript" src="${ctx}/js/navigator.js"></script>
</body>
</html>