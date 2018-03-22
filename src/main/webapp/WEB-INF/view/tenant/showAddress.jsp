<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta charset="utf-8">
<title>收发货地址</title>
<link href="${ctx}/js/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript">
 var CTX="${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${ctx}/js/tenant/editAddress.js"></script>
</head>
<style type="text/css">
*{margin:0;padding:0;}
body{font-size:14px;font-family:"微软雅黑";padding:20px;}
.MainBox{width:90%;position: relative;margin:0 auto;}
/*标题样式 start*/
.pageTitle{padding:8px 20px;position: relative;text-align:left;border-bottom: 1px solid #E2DFDF;}
 .MainBox ul{padding:5px 0px 10px 10px;;border-bottom: 2px solid red;height:20px;margin-left:10px;margin-bottom:0px;}
  .MainBox ul li{ list-style:none;float:left;height:20px;line-height:20px;border:1px solid #E4D8D8;border-bottom:0px;padding:5px 20px;margin-left:10px;border-radius:4px;}
.MainBox ul .active{background:red;color:#fff;}
.pageContent{width:90%;;;padding:20px;background: #fff;}
.pageContent .table_title{color:red; padding-left: 22px;}
.pageContent .newadd{margin:10px 0px;}
.chen_table{width:95%; border-collapse:collapse;margin:0 auto;color:#666;margin-top:5px;}
/*th具有加粗*/
.chen_table th,td{height:34px;padding-left:10px;border:1px solid #111;text-align:center;}
.chen_table td a{float:left;padding-left:7px;}

/* fortm 表单*/
.formgroup{width:100%;padding:5px 20px;}
.clearfix{ clear:both;}
.formgroup .label-text{float: left;width: 7em;text-align: right;margin-right: 15px;line-height: 36px;}
.formgroup .input-text{float: left;}
.formgroup .i-text{height: 24px; line-height: 24px;padding:0 5px;width: 218px;px;color:#636363;}
.formgroup .mb{padding:0px 5px;}
.formgroup  .red{padding-left: 5px;color:red;}
.egister_mainCon_btn {
				     width: 80px;height: 40px; font-size: 18px; cursor: pointer; background: #ff771d;
				     color: #fff;border: none; line-height: 40px; text-align: center; border-radius: 5px;
				     -webkit-border-radius: 5px; -ms-border-radius: 5px; -moz-border-radius: 5px; -o-border-radius: 5px;
				    }
.clickBtn{
  background: #a3c0e8;
}
</style>

<body>
<div class="MainBox">
  <div class="pageContent">
 <form method="post" action="" class="formgroup" id="address_form">
    <input type="hidden" id="province1"  value="${address.province}">
    <input type="hidden" id="city1"  value="${address.city}">
    <input type="hidden" id="country1"  value="${address.country}">
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>联系人姓名：</div>
      <div class="input-text"> <input type="text" name="contact" id="contact" class="i-text" value="${address.contact}" readonly="readonly"/><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>所在地区：</div>
      <div class="input-text"> 
        <select name="nation" disabled="disabled">
         <option value="中国">中国</option>
        </select>      
      </div>
      <div class="input-text mb">国家</div>
      <div class="input-text"> 
         <select id="s_province" name="province" disabled="disabled"></select>      
      </div>
      <div class="input-text mb">省</div>
      <div class="input-text"> 
          <select id="s_city" name="city" disabled="disabled"></select>      
      </div>
      <div class="input-text mb">市</div>
      <div class="input-text"> 
          <select id="s_county" name="country" disabled="disabled"></select>     
      </div>
      <div class="input-text mb">区</div>
      <input type="hidden" id="show" >
      <span id="address_span" class="order_chh_star" style="font-size: 15px;float:left;display:inline-block;"></span>
                        <script class="resources library" src="${ctx}/js/area.js" type="text/javascript"></script>
						<script type="text/javascript">_init_area();</script>
                        <script type="text/javascript">
                        function gid(id) { return document.getElementById(id); };
                        var Gid  = document.getElementById ;
						var showArea = function(){
							Gid.call(document,'show').innerHTML = "<h3>省" + Gid.call(document,'s_province').value + " - 市" + 	
							Gid.call(document,'s_city').value + " - 县/区" + 
							Gid.call(document,'s_county').value + "</h3>"
							}
						   Gid.call(document,'s_county').setAttribute('onchange','showArea()');
	                </script>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>街道地址：</div>
      <div class="input-text"><textarea  name="addresDetail" id="addresDetail" class="i-text" style="height:65px;width:300px;" readonly="readonly">${address.addresDetail}</textarea><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>邮政编码：</div>
      <div class="input-text"> <input type="text" name="postcode" id="postcode" class="i-text" value="${address.postcode}" readonly="readonly"/><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text">电话：</div>
      <div class="input-text"> <input type="text" name="mobile" id="mobile" class="i-text" value="${address.mobile}" readonly="readonly"/><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>手机号码：</div>
      <div class="input-text"> <input type="text" name="telephone" id="telephone" class="i-text" value="${address.telephone}" readonly="readonly"/><font color="red"></font></div>
    </div>

    
 </form>
</div>
</div>
</body>
</html>
