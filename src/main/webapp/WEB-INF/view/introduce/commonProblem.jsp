<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户指南</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" language="javascript">
$(function(){
	$(".api_left ul li").removeClass("active");
	$(".api_left ul li:first").addClass("active");
	$(".api_right div.api_items").show();
	$(".api_left ul li").each(function(index){
		
	  $(this).click(function(){
		  $(".api_right div.api_items").show();
		 $(".api_right div.api_items:lt("+index+")").hide();
		 $(this).addClass("active").siblings("li").removeClass("active");
		 })
	  })
	})
	
	function encryptDatum(){
	var unencryptedData=$("#unencryptDatum").val();
	if($("#unencryptDatum").val()==""||$("#unencryptDatum").val()=="this is a example"){
		layer.alert("请输入未加密数据");
		return ;
	}
	if($("#secretKey").val()==""){
		layer.alert("请输入秘钥");
		return ;
	}
	var secretKey=$("#secretKey").val();
	var url="${ctx}/encryptData.ht";
	$.ajax({
		url:url,
		data:{unencryptedData:unencryptedData,
			secretKey:secretKey},
		success:function(data){
			$("#encryptDatum1").val(data);
		}
		
	})
	}
	
	function clearData(){
		$("#unencryptDatum").val("");
	}
	function clearData1(){
		$("#secretKey").val("");
	}
</script>
  <style type="text/css">
      .api_left ul li:hover, .api_left ul li.active {
    color: #0089cd;
    background-color: #ddedf4;
    text-decoration: none;
    }
    .api2_rightTit {
    color: #333;
    line-height: 34px;
    font-size: 28px;
    border-bottom: 1px solid #ddd;
    padding-bottom:12px;
    margin-bottom: 15px;
    }
    .api2_rightCon {
    margin-bottom: 30px;
    }
    .api2_rightCon_ti {
    font-size: 16px;
    text-indent: 2em;
    }
    .api2_rightCon p {
    line-height: 39px;
}
.encryptData{font-size:18px;margin-left: 20px;line-height: 40px;}
.encryptBtn{margin-left: 10px;margin-top: 20px;}
textarea{ border:1px solid #01ACD1;width: 500px;height: 200px;margin-left: 20px;}
input{ border:1px solid #01ACD1;}

.api_bread { height: 38px; overflow: hidden; line-height: 38px; color: #323232; padding: 0 5px; margin-left: 90px;}
.api_left { float: left; display: inline; width: 220px; min-height: 344px; overflow: hidden; background: #f5f5f5; margin-left: 90px;}
.api_right { margin-left: 320px; min-height: 700px; }

</style>
</head>
<body>
<%@ include file="/commons/top.jsp" %>
<%@ include file="/commons/navigator.jsp"  %>

<div class="api_box clearfix">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="commonProblems.ht">常见问题</a></div>
    <div class="api_left">
    	<h2>所有规范</h2>
        <ul>
            <li><a href="javascript:void(0);">注册子系统</a></li>
            <li><a href="javascript:void(0);">统一注册</a></li>
            <li><a href="javascript:void(0);">统一登录</a></li>
            <li><a href="javascript:void(0);">统一维护</a></li>
            <li><a href="javascript:void(0);">秘钥加密</a></li>
        </ul>
    </div>
    <div class="api_right">
        <div class="api_items">
        	<div class="api2_rightTit">1.注册子系统</div>
            <div class="api2_rightCon">
               <%--  <p class="api2_rightCon_ti">
                                           注册子系统需要向运营的管理人员提供将要被注册的子系统的名称 ,访问地址和首页地址,运营管理人员登录用户中心后,根据提供的相应信息,在子系统管理菜单下,点击添加操作即可完成对子系统的注册工作.
               
                </p> 
                <div style="width:100%;height:411px;background:url('${ctx}/styles/uc/images/uc_zcz.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div> --%>
              	  待完善
            </div>
        </div>
        
        <div class="api_items">
        	<div class="api2_rightTit">2.统一注册</div>
            <div class="api2_rightCon">
               <%--  <p class="api2_rightCon_ti">
                                               子系统调用用户中心统一注册接口时,需要拼上子系统的系统Id,这个标识将作为系统用户的注册来源。统一注册在用户中心分为三步。
                                               第一步 ：填写手机号和注册邀请码。(手机号必须唯一,注册邀请码即已注册用户的企业号)
                </p> 
                <div style="width:100%;height:386px;background:url('${ctx}/styles/uc/images/uc_zc1.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div>
                 <p class="api2_rightCon_ti"> 
                                               第二步 ：填写会员用户名,用户密码,公司名称以及公司联系人。(用户名和公司名称必须唯一)
                </p> 
                <div style="width:100%;height:498px;background:url('${ctx}/styles/uc/images/uc_zc2.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div>
                 <p class="api2_rightCon_ti"> 
                                               第三步 ：注册成功,跳转到统一登录界面.
                </p> 
                <div style="width:100%;height:372px;background:url('${ctx}/styles/uc/images/regSuccess.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div> --%>
        	待完善
        </div>
        </div>
        <div class="api_items">
        	<div class="api2_rightTit">3.统一登录</div>
            <div class="api2_rightCon">
               <%--  <p class="api2_rightCon_ti">
                                                     用户统一登录时有两种登录方式,第一种通过手机号登录,第二种通过企业号的登录方式.
                </p> 
                <p class="api2_rightCon_ti">
                                                      第一种：用户通过手机号方式进行登录.
                </p> 
                <div style="width:100%;height:461px;background:url('${ctx}/styles/uc/images/uc_dl.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div>
                <p class="api2_rightCon_ti">
                                                      第二种：用户通过企业号方式进行登录.
                </p> 
                <div style="width:100%;height:399px;background:url('${ctx}/styles/uc/images/uc_dl2.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div> --%>
            	待完善
            </div>
        </div>
        
        <div class="api_items">
        	<div class="api2_rightTit">4.统一维护</div>
            <div class="api2_rightCon">
                <%-- <p class="api2_rightCon_ti">
                                            已经在用户中心完成注册的企业用户,可以在用户中心后台对自己的企业信息进行维护,用户登录后台时需要传入在注册用户时产生的openId。
                                           系统 统一的维护包含有8大子模块，分别为：账号管理,基本信息,认证信息,开户信息,组织信息,统计信息,安全设置,收发货地址.                          
                </p> 
                <div style="width:100%;height:715px;background:url('${ctx}/styles/uc/images/uc_wh.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div> --%>
           		待完善
            </div>
        </div>
        
        <div class="api_items">
        	<div class="api2_rightTit">5.秘钥加密</div>
            <div class="api2_rightCon">
               	<!--  <label class="encryptData">明文：</label><input id="unencryptDatum" type="text" onfocus="clearData();" name="unencryptDatum" value="请输入数据"><br> -->
               	 <label class="encryptData">明文：</label><br>
               	 <textarea name='unencryptmessage' id='unencryptDatum' onfocus="clearData();" value="this is a example" >this is a example</textarea><br>
               	 <label class="encryptData">秘钥：</label><input id="secretKey" type="text" onfocus="clearData1();" name="secretKey"><input class="encryptBtn" type="button" value="生成密文" onclick="encryptDatum();"><br>
               	 <!-- <label class="encryptData">密文：</label><input id="encryptDatum1" type="text" name="encryptDatum"><br> -->
               	 <label class="encryptData">密文：</label><br>
               	 <textarea name='encryptmessage' id='encryptDatum1'></textarea><br>
            </div>
        </div>
    </div>
</div>


<!--casicloud footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--casicloud footer end-->

<script type="text/javascript" src="${ctx}/js/navigator.js"></script>
</body>
</html>