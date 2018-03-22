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
<link href="${ctx}/js/layer/skin/layer.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/validate/jquery.validate.extend.js"></script>

<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript">
 var CTX="${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${ctx}/js/tenant/tenantAddressEditInfo.js"></script>
</head>
<style type="text/css">
*{margin:0;padding:0;}
body{font-size:14px;font-family:"微软雅黑";padding:20px;}
.MainBox{width:100%;position: relative;}
/*标题样式 start*/
.pageTitle{padding:8px 20px;position: relative;text-align:left;border-bottom: 1px solid #E2DFDF;}
 .MainBox ul{padding:5px 0px 10px 10px;;border-bottom: 4px solid #ff6600;height:20px;margin-left:10px;margin-bottom:0px;}
  .MainBox ul li{ list-style:none;float:left;height:20px;line-height:20px;border:1px solid #E4D8D8;border-bottom:0px;padding:5px 20px;margin-left:10px;border-radius:4px;cursor:pointer;}
.MainBox ul .active{background:#ff6600;color:#fff;}
.pageContent{width:95%;;;padding:20px;background: #fff;}
.pageContent .table_title{color:#ff6600; padding-left: 22px;}
.pageContent .newadd{margin:10px 0px;}
.chen_table{width:95%; border-collapse:collapse;margin:0 auto;color:#333;margin-top:5px;}
/*th具有加粗*/
.chen_table th,td{height:34px;padding-left:10px;border:1px solid #ccc;text-align:center;}
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
  span.tbar-label {
    border-left: 5px solid #ff6600;
    font-family: "microsoft yahei";
    font-size: 16px;
    line-height: 1.2;
    margin: 14px 0;
    padding-left: 10px;
   }
   td span{
    color: #666;
   }
   textarea {
	resize: none;
 }
th td{
  width: 15%
}

.tipBox{
border: solid 1px #ddd;
height:130px;
border-radius:10px;
margin-bottom:10px;
}

#tip{
height: 20px;
margin-left: 10px;
margin-top: 10px;
}
#warmtips{
margin-left: 5px;
line-height: 30px;
}

.tipBox p span{
margin-left: 35px;
line-height: 20px;

}
</style>

<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
<script type="text/javascript">
$(window.parent.document).find("#mainiframe").load(function(){
	var mainheight = 1000;
	 $(this).height(mainheight);
});

</script>
<body>
<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>1、最多可设置6个发货地址。</span></p>
 <p><span>2、发布供需需求时，必须选择某一已设置的收发货地址。</span></p>
 <p><span>3、系统自动保存您使用过的收发货地址信息。</span></p>
<p><span>4、您可以设置一个常用地址，作为默认收发货地址，常用地址设置后可删除或修改。</span></p>
</div>
<div class="MainBox">
	<div class="pageTitle">
	             <span class="tbar-label">
                                                    收发货地址
                 </span>
	</div>
<ul>
  <li class="active" onclick="fun(this)">收货地址</li>
  <li  onclick="fun(this)">发货地址</li>
</ul>
  <div class="pageContent">
  <div class=" table_title">已保存地址</div>
<table class="chen_table">
  <thead>
    <tr>
      <th style="width:15%;">收货人</th>
      <th style="width:15%;">所在地区</th>
      <th style="width:15%;">街道地址</th>
      <th style="width:15%;">邮编</th>
      <th style="width:15%;">手机</th>
      <th style="width:25%;">操作</th>
    </tr>  
  </thead>
  <tbody id="person">
  <c:forEach var="orderAddressInfo" items="${orderAddressInfo}">
      <tr>
         <td>${orderAddressInfo.contact}</td>
         <c:choose>
            <c:when test="${orderAddressInfo.province eq orderAddressInfo.city}">
               <td>${orderAddressInfo.city}${orderAddressInfo.country}</td>
            </c:when>
            <c:otherwise>
               <td>${orderAddressInfo.province}${orderAddressInfo.city}${orderAddressInfo.country}</td>
            </c:otherwise>
         </c:choose>
         <td><div style="width:100px;word-break:break-all;">${orderAddressInfo.addresDetail}</div></td>
         <td>${orderAddressInfo.postcode}</td>
         <td>${orderAddressInfo.telephone}</td>
         <c:choose>
           <c:when test="${orderAddressInfo.isDefault=='1'}">
              <td id="${orderAddressInfo.id}"><span>默认地址</span><a href="javascript:editAddress('${orderAddressInfo.id }');">修改</a><a href="javascript:delAddress('${orderAddressInfo.id }');">删除</a><a href="javascript:linkDetail('${orderAddressInfo.id }');">详情</a></td>
           </c:when>
           <c:otherwise>
              <td id="${orderAddressInfo.id}"><a href="javascript:updateIsDefault('${orderAddressInfo.id}',this);">设为默认地址</a><a href="javascript:editAddress('${orderAddressInfo.id }');">修改</a><a href="javascript:delAddress('${orderAddressInfo.id }');">删除</a><a href="javascript:linkDetail('${orderAddressInfo.id }');">详情</a></td>
           </c:otherwise>
        </c:choose>
      </tr>
  
  
  </c:forEach>
  </tbody> 
  <tfoot></tfoot>
</table>
 <div class="table_title newadd">新增发货地址</div>
 <form method="post" action="" class="formgroup" id="address_form">
    <input type="hidden" id="isReceviced" name="isReceviced">
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>联系人姓名：</div>
      <div class="input-text"> <input type="text" name="contact" id="contact" class="i-text"/><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>所在地区：</div>
      <div class="input-text"> 
        <select name="nation">
         <option value="中国">中国</option>
        </select>      
      </div>
      <div class="input-text mb">国家</div>
      <div class="input-text"> 
         <select id="s_province" name="province" onchange="clearaddress(this)"></select>      
      </div>
      <div class="input-text mb">省</div>
      <div class="input-text"> 
          <select id="s_city" name="city" onchange="clearaddress(this)"></select>      
      </div>
      <div class="input-text mb">市</div>
      <div class="input-text"> 
          <select id="s_county" name="country" onchange="clearaddress(this)"></select>     
      </div>
      <div class="input-text mb">区</div>
      <input type="hidden" id="show" >
      <input type="hidden" name="address" id="address" placeholder="请填写具体地址" class="order_chh_textd" style="float:left;" onchange="clearaddress(this)"/><span id="address_span" class="order_chh_star" style="font-size: 15px;float:left;display:inline-block;"></span>
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
      <div class="input-text"><textarea  name="addresDetail" id="addresDetail" class="i-text" style="height:65px;width:300px;"></textarea><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>邮政编码：</div>
      <div class="input-text"> <input type="text" name="postcode" id="postcode" class="i-text"/><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text">电话：</div>
      <div class="input-text"> <input type="text" name="mobile" id="mobile" class="i-text"/><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
      <div class="label-text"><span class="red">*</span>手机号码：</div>
      <div class="input-text"> <input type="text" name="telephone" id="telephone" class="i-text"/><font color="red"></font></div>
    </div>
    <div class="formgroup clearfix">
        <div class="label-text"></div>
         <div class="input-text"></div>
    </div>
 </form>
     <div class="formgroup clearfix">
      
     
      <span    style="width: 100px;padding-left: 110px;" onclick="submit()"> 
      <input type="button" value="提交" class="egister_mainCon_btn" >
      </span>

    </div>
</div>
</div>
</body>
</html>
