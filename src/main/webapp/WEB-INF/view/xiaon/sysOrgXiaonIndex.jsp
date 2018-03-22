<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.hotent.core.util.ContextUtil" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>

<title></title>
<%@include file="/commons/include/form.jsp" %>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<style type="text/css">
.l-dialog {
    font-size: 12px;
    margin: 0;
    padding: 0;
    display:block;
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
.tipBox{
	border: 1px solid #ddd;
    border-radius: 10px;
    height: 115px;
    margin-bottom: 10px;
    font-size: 14px;
    margin-top: 5px;
}
html {
    color: #666;
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
	});
	
   var state=$("#state").val();
		if(state!='5'){
			layer.open({
				  title: '提示信息',
				  closeBtn:0,
				  skin: 'layui-layer-molv',
				  shade:[0.6,'#000'],
				  content: "请先实名认证,再进行在线客服",
				  shift:1, //0-6的动画形式，-1不开启
				  btn:['去认证'],
				  yes:function(index){
					  window.open('http://b2b.casicloud.com/home.ht?menu=smrz');
					  layer.close(index);
					}				 
			});     			
		}
	});
</script>
</head>
<body>
	<div class="tipBox">	
		<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>
		<div style="margin-left: 25px">	
		<span >功能简介：商户在线客服功能,是航天云网平台为入驻企业提供的在线实时沟通及留言功能，开通在线客服功能后,客户可以通过商品、能力、需求单、采购单的详情页入口与企业进行在线沟通。企业可以通过IM客户端，实时接收和回复用户的咨询信息。</span>
	 	</div>
	 	<div style="margin-left: 25px">
	 	<span >开通条件：企业需要先通过航天云网实名认证后，可以申请开通在线客服功能。</span>
	 	</div>
	 	<div style="margin-left: 25px">
	 	<span>计费规则：基础版商户在线客服功能，对商户免费开放。每个企业可以创建1个主账户和4个子账户;如需开通更多子账户，请联系航天云网客服400-857-6688。</span>
	 	</div>
	</div>
	<div style="margin-left: 40px">
		<form id="searchForm" method="post">
			<table style="border-spacing: 10px; border-collapse:separate; line-height: 25px;font-size: 14px;text-align: left">
				<input id="state" value="${state}" type="hidden">
				<input type="hidden" name="userId" id="userId" value="<%=ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()%>">
				<tr>
					<td><div style="text-align: right">企业名称:</div></td>
					<td><%=ContextUtil.getCurrentOrgInfoFromSession().getName()%><input type="hidden" style="border:none;" name="username" id="username" value="<%=ContextUtil.getCurrentOrgInfoFromSession().getName()%>"/></td>
				</tr>
				<tr>
					<td>
						<div style="text-align: right">实名认证:</div>
					</td>					
					<td>
						${res}
					</td>
				</tr>
				<tr>
					<td><div style="text-align: right"><span style="color:red">*</span>联系人:</div></td>
					<td><input type="text" name="connecter" id="connecter" onblur="vacon();"/><span id="con"></span></td>				
				</tr>
				<tr>
					<td><div style="text-align: right"><span style="color:red">*</span>联系电话:</div></td>
					<td><input type="text" name="tel" id="tel" onblur="vatel();"/><span id="te"></span></td>
				</tr>
				<tr>
					<td><div style="text-align: right"><span style="color:red">*</span>邮箱:</div></td>
					<td><input type="text" name="email" id="email"  onblur="vaemail();"/><span id="em"></span></td>
				</tr>
			
				<tr>
					<td id="hide" colspan="2">
						<div style="text-align: center;">
							<span id="bten" class='form_button' onclick="submitAdd()" style="cursor:pointer">提交申请</span>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	 
<script type="text/javascript">
function vatel(){  
	 reg=/^1[3|4|7|5|8]\d{9}$/;//正则表达式  
	 var tel=$("#tel").val();     
	 if(!(reg.test(tel))){
		 layer.alert	("请输入正确的手机号");
		 return;
		 }
	 } 
//检查输入的数据是不是邮箱格式 
function vaemail(){
        var email = $("#email").val();    
        //获取email控件对象         
        var reg =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;//正则表达式       
        if (!reg.test(email)) {                 
       	 layer.alert	("邮箱格式错误，请重新输入！");                                    
       	 return;                    
       }        
   }    
function vacon(){
	 var con=$("#connecter").val();
	 if(con==""){
		 layer.alert	("联系人不能为空！")
		 return;
	 }
}

var orgId = '<%=ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()%>';
function submitAdd() {
	if($("#connecter").val()==""){
		layer.alert	("请填写联系人！")
		return;
	}
	if($("#tel").val()==""){
		layer.alert	("请填写手机号码！")
		return;
	}
	if($("#email").val()==""){
		layer.alert	("请填写邮箱！")
		return;
	}
	
	$.ajax({
		async : false,
		cache : false,
		type : 'post',
		dataType : 'json',
		url : '${ctx}/xiaon/addInfo.ht?id='+ orgId,
		data : {
			userId : $("#userId").val(),
			username : $("#username").val(),
			connecter : $('#connecter').val(),
			tel : $('#tel').val(),
			email : $('#email').val(),
			type : 0
		},
		success : function(data) {
			if(data.doMsg == 1){	
				
				$("#connecter").hide()
				$("#con").html(data.name)
				$("#tel").hide()
				$("#te").html(data.tel)
				$("#email").hide()
				$("#em").html(data.email)
				var htm = "<div style='text-align:center;margin-top: 20px'> ";
				htm +="<p style='font-size: 14px;color:red'>恭喜您!商户在线客服功能开通成功，点击“返回”</p> "
					+"<p style='font-size: 14px;color:red'>可在列表中查看在线客服的账号、密码、使用帮助以 </p> "+
					"<p style='font-size: 14px;color:red'>及IM客户端的下载地址，感谢您的使用!</p>"
					+"<button type='button' class='form_button' value='返回我的客服' "  
					+"onclick='returnList()' "
					+"class='form_button' style='height:35px;'>返回我的客服</button> </div>";
				$("#hide").html(htm);
			}else
				if(data.doMsg == 3){
					layer.alert(data.msg)					
				}			
				else
			{
					layer.alert(data.msg,function(index){
					layer.close(index);
					});
				
			}
		},
		error : function() {
			$.ligerMessageBox.warn("提示信息","数据填写错误！");
		}
	});
}
function returnList() {
	window.location.href="${ctx}/xiaon/xiaonList.ht?id=" + orgId;
}
</script>
</body>
</html>

