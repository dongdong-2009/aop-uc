<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@ include file="/commons/include/form.jsp" %>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中金开户</title>
<script type="text/javascript">
document.domain="casicloud.com";
</script>
<style>
a {
	color: #666;
	text-decoration: none;
}

body {
	color: #666;
	font-family: Microsoft YaHei;
	font-size: 14px;
}

#code {
	font-family: Arial;
	font-style: italic;
	font-weight: bold;
	border: 0;
	letter-spacing: 2px;
	color: blue;
}
.form_button {
    background-color: #e56826;
    border: 0 none;
    border-radius: 3px;
    color: #fff;
    display: inline-block;
    line-height: 38px;
   	margin: 20px 8px;
    text-align: center;
    width: 120px;
}
.form_buttonhui {
    background-color: #888888;
    border: 0 none;
    border-radius: 3px;
    color: #fff;
    display: inline-block;
    line-height: 38px;
   	margin: 20px 8px;
    text-align: center;
    width: 120px;
}
.tipBox{
border: solid 1px #ddd;
height:70px;
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

.register_mainCon_list{
    font-size: 14px;
}
.tipinfo img {
    width: 20px;
    height: 20px;
    position: relative;
    top: 5px;
    }
    
.frist{
	margin-top: 20px;
	margin-left: 30px;

}
</style>
<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
<script type="text/javascript">
$(function() {
  $(window.parent.document).find("#mainiframe").load(function(){
		var mainheight = 800;
		 $(this).height(mainheight);
	})
	
	var orgInfoaccstate=$("#orgInfoaccstate").val();
	if(orgInfoaccstate!='1'){
		layer.open({
			  title: '提示信息',
			  closeBtn:0,
			  skin: 'layui-layer-molv',
			  shade:[0.6,'#000'],
			  content: "请先实名认证,再进行中金开户",
			  shift:1, //0-6的动画形式，-1不开启
			  btn:['去认证'],
			  yes:function(index){
				var win= window.open("http://b2b.casicloud.com/home.ht?menu=smrz");//测试环境
				//var win=window.open("http://172.17.11.38:8080/tenant/certiInformation.ht");
				  //window.open("http://core.casicloud.com/home.ht?menu=smrz");//生产环境
				  layer.close(index);
				}				 
		});
		return;
	}
	var state=$("#orgInfoState").val()
	if(state=='6'){		
		$("#sub").html("已冻结");
		$("#sub").attr("onclick","");
		$("#sub").removeClass("form_button")
		$("#sub").addClass("form_buttonhui")
		$("#span").html("您的实名认证信息已修改，请审核通过后再开户！")
		return;
	}
	if(state!='5'){
		$("#sub").html("一键开户");
		$("#sub").attr("onclick","");
		$("#sub").removeClass("form_button")
		$("#sub").addClass("form_buttonhui")
		$("#span").html("您的实名认证信息还未通过审核，请审核通过后再开户！")
		return;
	}
	var cod=$("#cod").val();
	 if(cod !=null || cod !='' && state!='6'){
		 $("#span").html(cod);
	 }	
	var yc=$("#yc").val()
//	if(yc=='3'){
		//账户冻结		
//		$("#sub").html("已冻结");
//		$("#sub").attr("onclick","");
//		$("#sub").removeClass("form_button")
//		$("#sub").addClass("form_buttonhui")
//	}
	if(yc=='1'){
		//已开户
		$("#sub").html("已开户");
		$("#sub").attr("onclick","");
		$("#sub").removeClass("form_button")
		$("#sub").addClass("form_buttonhui")
	}
});

function callback(){
	window.location.reload();
}
</script>
</head>
<body>
<div class="frist">
	<!-- 
	<div class="tipBox">
		<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>
 		<p><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;只有拥有中金账户的用户才可以进行中金绑卡，。</span></p>
	</div>
 	-->
	<form  class="distance">
		
		<input type="hidden" id="cod" value=${cod }>
		
		<input type="hidden" id="orgInfoState" value="${orginfo.state}">
		<input type="hidden" id="orgInfoaccstate" value="${orginfo.accountStatsus}"/>
		<input type="hidden" id="orgInfo" value="${orginfo.sysOrgInfoId }" />
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">公司名称:</span> 
			 ${orginfo.name } 
			 <div class="tipinfo"></div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">开户名称:</span> 
			 ${orginfo.name }
			 <div class="tipinfo"></div> 
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">营业执照:</span> 
		    ${orginfo.yyzz }
			<div class="tipinfo"></div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">税务登记号:</span> 
			 ${orginfo.nsrsbh }
			<div class="tipinfo"></div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">法人姓名:</span> 
			 ${orginfo.incorporator }
			<div class="tipinfo"></div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">证件类型:</span> 
			 ${type }
			<div class="tipinfo"></div>
		</div>
		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">证件号:</span> 
			 ${orginfo.frsfzhm }
			<div class="tipinfo"></div>
		</div>
		<div style="margin-left: 50px;margin-top: 50px;">
			<span id="sub" class='form_button' onclick="openAcc()" style="cursor:pointer;">${msg }</span>
			<span id="span" style="font-size: 12px ;"></span>
		</div>		
			<input type="hidden" id="yc" value="${flag }"/>			
	</form>
</div>
</body>
<script type="text/javascript">
function openAcc(){
	var orgId=$("#orgInfo").val();
	$.ajax({
		type : 'post',
		dataType : 'json',
		url : '${ctx}/tenant/valiBranchAndRealNameState.ht',
		data : {
			id:orgId
		},
		success : function(data) {
			if(data.flag=='0'){
				
				layer.alert(data.msg,function(){
					window.location.reload();
				});			
			}			
			if(data.flag=='2'){
				layer.alert("开户成功",function(){
					window.location.reload();
					$("#sub").html(data.msg);
					$("#sub").attr("onclick","");
					$("#sub").removeClass("form_button");
					$("#sub").addClass("form_buttonhui");	
				})
							
			}			
		}	
	});
}
</script>

</html>