<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>子系统接入规范</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
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
$(function(){
	  $(".api_rightCon_tit").each(function(index){
	        $(this).toggle(
	           function(){
	              $(this).siblings(".api_right_style").slideUp();
	              $(this).find('.triangle').removeClass('open');
	           },
	           function(){
	              $(this).siblings(".api_right_style").slideDown();
	              $(this).find('.triangle').addClass('open');
	           })
	  	})
	})
</script>
<style>
.api_right { 
	margin-left: 5px; 
	min-height: 700px; }
.api_rightTit { 
	font-size: 22px; 
	height: 60px; }
.api_right_describe { margin-bottom: 20px; }	
.api_right_text{
	height:auto;
	border:0px solid #e5e5e5;
	text-indent: 28px;
	font-size:12px;}

.api_rightCon_tit { padding-top: 12px;padding-bottom: 12px; margin-top: 25px; text-indent: 30px; position: relative; cursor: pointer; border-bottom: 1px solid #999;font-size: 20px; margin-bottom: 20px;}
.api_rightCon_tit .triangle { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: transparent transparent transparent #666; position: absolute; left: 13px; top: 20px;}
.api_rightCon_tit .triangle.open { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: #666 transparent transparent transparent; top: 20px; left: 10px; }
.api_rightCon_tit:hover{background:#eee;}

.api_right_simple_param{height:600px;background:#eee;font-size:12px;text-align:left;}
.api_right_style table tr td { background: #fff; border: solid 1px #dcdcdc; padding: 6px 10px; line-height: 25px; font-size: 12px; }
.api_right_style table tr.api_item_bt td { background: #666; color: #fff; line-height: 17px;}
</style>
</head>
<body>

    <div class="api_right">
        <div class="api_rightTit">开户增加</div>
        	<h6 class="api_right_describe">增加开户信息</h6>
        <div class="api_rightCon">
	        <div class="api_rightCon_tit">
		        <i class="triangle open"></i>
		       		 请求方式
	        </div>
        	<p class="api_right_text api_right_style">Post</p>
        </div>
        <div class="api_rightCon">
	        <div class="api_rightCon_tit">
		        <i class="triangle open"></i>
		       		 请求参数
	        </div>
	        <div class="api_right_style">
	        <table border="0" cellpadding="0" cellspacing="0" width="100%">
	        <tr class="api_item_bt">
	        	<td width="15%">名称</td>
	        	<td width="10%">类型</td>
	        	<td width="10%">是否必须</td>
	        	<td width="65%">描述</td>
	        </tr>
	        <tr>
	        	<td>systemId</td>
	        	<td>Long</td>
	        	<td>是</td>
	        	<td>子系统id</td>
	        </tr>
	        <tr>
	        	<td>data</td>
	        	<td>String</td>
	        	<td>是</td>
	        	<td>开户数据(需要通过秘钥加密)</td>
	        </tr>
	        </table>
	        </div>
	        <div class="api_rightCon">
		        <div class="api_rightCon_tit">
			        <i class="triangle open"></i>
			       		 请求参数示例
		        </div>
		        <textarea class="api_right_simple_param api_right_style" style="width:100%;display:inline-block;">{
systemId:子系统id,
data: {
    "id": "id",
    "merchants": "商户号",
    "branchName": "分支机构名称",
    "bankAccount": "开户行账号",
    "bankFullName": "开户行名称（全称）",
    "branchAccountName": "分支机构账户名称",
    "city": "账户所在城市（直辖市、地级市）",
    "operatorDesign": "操作员号",
    "orgId": "企业id",
    "note": "备注",
    "province": "所在省",
    "accountType": "账户类型",
    "branchAbbr": "开户行简称",
    "channelId": "支付渠道",
    "state": ""
	}
}</textarea>
        	</div>
	        
	        
	        
	        
        </div>
    </div>



</body>
</html>