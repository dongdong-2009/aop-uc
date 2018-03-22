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
        <div class="api_rightTit">企业增加</div>
        	<h6 class="api_right_describe">增加企业信息</h6>
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
	        	<td>企业数据(需要通过秘钥加密)</td>
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
    "SYS_ORG_INFO_ID": "id",
    "EMAIL": "邮箱",
    "NAME": "公司名称",
    "INDUSTRY": "主营行业",
    "SCALE": "公司规模",
    "ADDRESS": "公司地址",
    "POSTCODE": "公司邮编",
    "CONNECTER": "联系人",
    "TEL": "手机号",
    "FAX": "传真号",
    "HOMEPHONE": "固定电话",
    "LOGO": "公司LOGO",
    "INDUSTRY2": "主营行业",
    "INFO": "公司简介",
    "COUNTRY": "国家",
    "PROVINCE": "省",
    "CITY": "市",
    "CODE": "组织机构代码",
    "TYPE": "公司类型",
    "MODEL": "经营类型",
    "PRODUCT": "主营产品",
    "WEBSITE": "公司网址",
    "IS_PUBLIC": "是否公开",
    "REGISTERTIME": "注册时间",
    "FLAGLOGO": "所属国别",
    "STATE": "",
    "MANAGE_RANGE": "经营范围",
    "REG_PROVE": "注册证明",
    "SELL_AREA": "销售区域",
    "BRAND": "企业品牌",
    "EMPLOYEES": "员工人数",
    "AREA": "占地面积",
    "CLIENTS": "主要客户群体",
    "TURNOVER": "年营业额",
    "EXPORT_FORE": "年出口额",
    "IMPORT_FORE": "年进口额",
    "QUALITY_CONTROL": "质量管理体系",
    "REG_CAPITAL": "注册资本",
    "REG_ADD": "注册地点",
    "INCORPORATOR": "法人",
    "OPEN_BANK": "开户银行",
    "OPEN_ACCOUNT": "开户账户",
    "SHOWIMAGE": "企业首页展示图片",
    "SELL_AREA2": "",
    "CREATETIME": "",
    "Setid": "套餐ID",
    "recommendedEnt": "推荐企业",
    "QRcode": "企业二维码",
    "orgType": "所属组织类型",
    "typeId": "类型ID",
    "typeName": "类型名称",
    "manageType": "经营类型",
    "specialMaterial": "是否特殊物资",
    "Gszch": "工商注册号",
    "Nsrsbh": "纳税人识别号",
    "Frsfzhm": "法人身份证号码",
    "Frsjh": "法人手机号",
    "yyzzPic": "营业执照图片",
    "axnStatus": "爱信诺认证",
    "ylStatus": "银联状态",
    "frPic": "法人身份证照片",
    "openId": "唯一标识",
    "invititedCode": "企业邀请码",
    "fromSysId": "子系统标识"
	}
}</textarea>
        	</div>
	        
	        
	        
	        
        </div>
    </div>



</body>
</html>