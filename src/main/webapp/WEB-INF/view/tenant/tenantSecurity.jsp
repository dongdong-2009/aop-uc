<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<html>
<head>
	<title>编辑企业信息</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/Validform_v5.3.1.js"></script>
		
	<!--[if lt IE 9]>
	<script type="text/javascript" src="${ctx}/pages/cloud3.0/js/cloud/json2.js"></script>
	<![endif]-->
	 
	<!-- tablegird -->
	<%-- <link href="${ctx}/styles/uc/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/style.css" rel="stylesheet" type="text/css" /> --%>
		<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/scrollable.js"></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	
<style type="text/css">

/* form .row {*width:700px;}
#wizard {font-size:12px;height:500px;margin-top:10px;margin-left:20px;overflow:hidden; position:relative;-moz-border-radius:5px;-webkit-border-radius:5px;}
#wizard .items{width:20000px; clear:both;position:absolute;}
#wizard #status{height:35px;background:#123;padding-left:25px !important;}
#status li{float:left;color:#fff;padding:10px 30px;}
#status li.active{background-color:#369;font-weight:normal;}
.input{width:240px; height:18px; margin:10px auto; line-height:20px; border:1px solid #d3d3d3; padding:2px}
.page{padding:15px 30px;width:900px;height:500px; overflow-x:hidden; overflow-y:auto;float:left; position:relative;}
.page h3 em{font-size:12px; font-weight:500; font-style:normal}
.page p{line-height:24px;}
.page p label{font-size:14px; display:block;}
.btn_nav{height:36px; line-height:36px;  padding:60px 0 100px;}
.prev,.next{width:100px; height:32px; line-height:32px; background:url(btn_bg.gif) repeat-x bottom; border:1px solid #d3d3d3; cursor:pointer;
  color:white; border: none; border-radius:4px;}
	
label.error {
  padding-left: 15px;
}
input.prev {
  float: left;
  background-color:#09c;
}
input.next {
  float: right;
  margin-right:80px; 
  display:inline;
  background-color:#0a8;
}

a {
    color: #666;
    text-decoration: none;
}
form .label {
  width: 120px;
  float: left;
  margin-left: -145px;
  font-weight: bold;
  text-align: right;
  position: relative;
  _display: inline;
}
.page h3 {
  height: 5px;
  font-size: 16px;
  border-bottom: 1px dotted #ccc;
  margin-bottom: 5px;
  padding-bottom: 5px;
}
。blank0 { height:0px; line-height:0px; display:block; overflow:hidden; clear:both; font-size:0;}
。blank150 { height:150px; line-height:0px; display:block; overflow:hidden; clear:both; font-size:0;} */
a {
    color: #666;
    text-decoration: none;
}
body {
    color: #666;
    font-family: Microsoft YaHei;
    font-size: 14px;
}

</style>
	<script type="text/javascript">
	
	$(function(){
		
		$("#sub").click(function(){
			var newpassword=$("#newpassword").val();
			var repeatpassword=$("#repeatpassword").val();
			var password = $("#password").val();
			
			if(newpassword.trim()==""||repeatpassword.trim()==""||password.trim()==""){
				$.ligerMessageBox.alert('提示信息',"请输入所有必填字段");
				return false;
			}
			
			
			if(newpassword == password){
				$.ligerMessageBox.alert('提示信息',"新密码不能与原始密码相同");
				return false;
			}
			if(newpassword!=repeatpassword){
				$.ligerMessageBox.alert('提示信息',"两次输入的密码不一致");
				return false;
			}
			$.ajax({
			    type: 'POST',
			    url: "${ctx}/user/updatePwd.ht",
			    data: $("#infoForm").serialize() ,
			    dataType: "json",
			    success: function(data){
			    	$.ligerMessageBox.alert('提示信息',data.message);
			    }

			});
			
		})
		
	
	});
	
	
		
	</script>

</head>
<body>
	<form id="infoForm" method="post" action="${ctx}/user/updatePwd.ht">
		<div id="wizard"  type="main">
		
							<div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>当前密码</span>
		                        <input class="register_mainCon_con" id="password" name="password"  type="password" class="inputText" maxlength="18" "/>
		                        <div class="tipinfo"></div>
		                    </div>
		                    
		                    
		                    <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>新的密码</span>
		                        <input class="register_mainCon_con" id="newpassword" name="newpassword"  type="password" class="inputText" maxlength="18" "/>
		                        <div class="tipinfo"></div>
		                    </div>
		                    
		                    
		                     <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"><span class="register2_red">*</span>确认新密码</span>
		                        <input class="register_mainCon_con" id="repeatpassword" name="repeatpassword"  type="password" class="inputText" maxlength="18" "/>
		                        <div class="tipinfo"></div>
		                    </div>
		                    
						
		                  
			                 <div class="register_mainCon_list">
		                        <span class="register_mainCon_tit"></span>
		                        <input type="button" class="register_mainCon_btn" value="提交" id="sub" />
		                        <div class="tipinfo"></div>
		                    </div>
					
			
		</div>
</form>
<script type="text/javascript">
	
	function subStr(loginName){
		 if(loginName!=null && loginName != null){
	    	if(loginName.length>7){
	    		loginName = loginName.trim();
	    		loginName = loginName.substring(0,5)+"...";
	    	}
	    }
		 return loginName;
	}
	
</script>
</body>
</html>
