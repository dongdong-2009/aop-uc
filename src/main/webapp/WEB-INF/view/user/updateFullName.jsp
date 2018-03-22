<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- <script id="allmobilize" charset="utf-8"
	src="http://a.yunshipei.com/6a4b3f63883b63133dcb8f2689166293/allmobilize.min.js"></script> -->
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" href="#" />
<meta charset="utf-8" />
<title>航天云网注册主页</title>
<%@ include file="/commons/include/form.jsp" %>
<link href="${ctx}/styles/uc/register.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet"
	type="text/css" />
<%-- <link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" /> --%>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/layer/layer.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${ctx}/js/jquery/jquery.validate.js"></script>
<%-- <script language="JavaScript" type="text/javascript"
	src="${ctx}/js/jquery/placeholderfriend.js"></script> --%>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>

<script type="text/javascript">

	$(function() {
		
		//新增加的form提交代码
		var options={};
		if(showResponse){
			options.success=showResponse;
		}
		var frm=$('#updateUserForm').form();
		$("#sub").click(function(){
            firstClick(frm,options);
        });
        $("#sub").dblclick(function(){
            firstClick(frm,options);
        });
        function firstClick(frm,options){
			//去掉所有的html标记
			if(validForm()){
				var userName=$("#fullname").val();
				if(userName=="" || userName==null){
					layer.alert("会员姓名不能为空！");
					return;
				}
				
				
				frm.setHtmlData();
				frm.ajaxForm(options);
				if(frm.valid()){
					layer.msg('正在保存中,请稍候...', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
					form.submit();
					firstClick = secondClick;
				}else{
					layer.alert("信息填写不正确");
				}
				//str = str.replace(/<[^>]+>/g,"");
				
			}else{
				layer.alert("请将必填信息填充完整再提交",{
				    skin: 'layui-layer-molv' //样式类名
				        ,closeBtn: 0
				    });
				return;
			}
			
        }
		function secondClick(){
			layer.alert("单据已经提交，请勿重复提交");
			return false;
		}
		
		$("#updateName").click(function(){
			
			var userName=$("#fullname").val();
			if(userName.trim()=="" || userName.trim()==null || userName.trim()=="必填"){
				layer.alert("会员姓名不能为空！");
				return;
			}
			if(userName.trim().length<2 ||userName.trim().length>30){
				layer.alert("会员姓名长度在2-30个字符之间！");
				return;
			}
			
			var reg=/^[\u4e00-\u9fa5\w|\.]+$/;
			if (!reg.test(userName)) {
				layer.alert("会员姓名只能包括中文字母数字！");
			       return ;
			    }
			
			 var userId="${user.userId}";
			 $.ajax({
					type : 'POST',
					url : "${ctx}/user/saveFullName.ht",
					data : {
						userFullName : userName,
						userId : userId
					},
					success : function(dataMap) {
						if (dataMap && dataMap.flag == "1") {
							layer.alert('此会员不存在');
						} else if (dataMap.flag == "2") {
							layer.alert("会员姓名更新成功",function(){
								var wpd = $(window.parent.document).find("#userName");
								if(wpd){
									wpd.text("您好，"+userName+"  欢迎来到用户中心！");
									window.location.href="${ctx}/user/userManage.ht";
								}
								//window.parent.location.reload();
							});
						} else {
							layer.alert("会员姓名更新失败");
						}
					},
					error : function(dataMap) {
						layer.alert("会员姓名更新失败");
					}
				});
			
			
		});
		
		
		$("#fanhui").click(function(){
			
			window.location.href="${ctx}/user/userManage.ht";
			
		});
		
		
		
	});
			
			
	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		layer.closeAll();
		if (obj.isSuccess()) {
			if(obj.data.message.indexOf('变化')>0){
				layer.alert('由于您修改了企业认证信息，工作人员会在3-5个工作日内对您提供的最新信息进行审核。'+
						'如果审核成功系统会保存您提供的最新信息，如果审核失败系统会还原您之前认证过的信息。'+
						'如需要人工帮助请联系客服电话 010-88611981', {
			    	skin: 'layui-layer-molv' //样式类名
			    ,closeBtn: 0
				},function(){
					window.location.reload();
				});  
			}else{
				layer.alert('用户更新成功！', {
			    	skin: 'layui-layer-molv' //样式类名
			    ,closeBtn: 0
				},function(){
					$("#verifycode").val('');
					window.location.reload();
				});  
			}
		} else {
			layer.alert('用户更新失败：'+obj.getMessage(),{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});     
		}
	}
	
	function validForm(){
		var flag = true;
		$(".row em").parent().next().find("input").each(function(){
			var thisVal = $(this).val();
			var names = $(this).attr('name');
			if(names!="info" && typeof(names) != "undefined" && names!="logo" && names!="yyzzPic"  && names!="frPic"){
				if(thisVal==""||thisVal=="必填"){
					flag = false;
					$(this).after($("<label class='error'>该项为必填项</label>"));
					$(this).next().next().remove();
				}
			}				
		});
		return flag;
	}

	var wpd = $(window.parent.document).find("#mainiframe");
	if(wpd){
		wpd.load(function(){
				
				var mainheight = 800;
				 $(this).height(mainheight);
			});
	}
	
	
</script>
<style type="text/css">
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

.tipBox p span{
margin-left: 35px;
line-height: 20px;

}

.register_mainCon_list {
    font-size: 16px;
    line-height: 80px;
    margin-bottom: 25px;
}

.register_mainCon_btn {
    background: #ff771d none repeat scroll 0 0;
    border: medium none;
    border-radius: 5px;
    color: #fff;
    cursor: pointer;
    font-size: 18px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    width: 100px;
}

.register_mainCon_btn1 {
    background: #ff771d none repeat scroll 0 0;
    border: medium none;
    border-radius: 5px;
    color: #fff;
    cursor: pointer;
    font-size: 18px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    width: 100px;
    margin-left: 80px;
}
</style>

</head>
<body>

<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>首次登录后，通过手机验证可以修改一次用户名。</span></p>
</div>

	<form id="updateUserForm" action="${ctx}/user/updateFullName.ht" method="post" class="distance">
		<input type="hidden" name="userId" value="${user.userId}"/>

		<div class="register_mainCon_list">
			<span class="register_mainCon_tit">会员姓名</span> 
			<input type="text" name="fullname" id="fullname" maxLength="30" minLength="2" placeholder="2-30个汉字，大小写字母，数字" class="register_mainCon_con" required
			value="${user.fullname}" validate="{required:true}" />
			<div class="tipinfo"></div>
		</div>

		<div class="register_mainCon_list">
			<span class="register_mainCon_tit"></span>
				  <input type="button" class="register_mainCon_btn" value="更新" id="updateName"/>
				  <input type="button" class="register_mainCon_btn1" value="返回" id="fanhui"/>
			<div class="tipinfo"></div>
		</div>
		<input type="hidden" name="url" value="${url}" />
	</form>

</body>


</html>
