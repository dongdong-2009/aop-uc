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
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.zoom.js"></script>
	<!-- 上传图片 -->
	<script type="text/javascript" src="${ctx}/js/uc/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/cloudDialogUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/uc/uploadPreview.js"></script>
 
	
	<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/main.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor142/ueditor.all.js"></script>
<script type="text/javascript" href="${ctx}/ueditor142/lang/zh-cn/zh-cn.js" ></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	<style type="text/css">
	   .register_mainCon_con{
	     height: 30px;
	     width: 190px;
	     font-size: 14px;
	   }
	.register_mainCon_list{
	     line-height:20px;
	    font-size: 14px;
	     width: 557px;
	     float: left;
	}
	#bigimage img {padding:5px; background:#fff; border:1px solid #e3e3e3; }
	#bigimage { position:absolute; display:none; }
	/* .register_mainCon_btn{
	  margin-top: 100px;
	} */
	
	.openAccount_titie_width{
	 width: 132px;
	}
	.appleSelect{
	  width: 120px;
	}
	
	.register_mainCon_btn {
    width: 240px;
    height: 40px;
    font-size: 18px;
    cursor: pointer;
    background: #ff771d;
    color: #fff;
    border: none;
    line-height: 40px;
    text-align: center;
    border-radius: 5px;
    margin-top: 100px;
}

	.register_mainCon_btn1 {
    width: 240px;
    height: 40px;
    font-size: 18px;
    cursor: pointer;
    background: #ff771d;
    color: #fff;
    border: none;
    line-height: 40px;
    text-align: center;
    border-radius: 5px;
    margin-left: 200px;
}
	
	div#saveOrSubmit { display: inline;width:1200px; }
	
	.tipBox{
border: solid 1px #ddd;
height:90px;
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
  
  .layui-layer-iframe{
    margin-top: 410px;
  }
  .al{
  color: #007bc4;
  }

  
	</style>
    <script type="text/javascript">
    
    var ue=null;
        $(function (){
            $("#navtab1").ligerTab({ dblClickToClose: false });                 
        });
        
    
    </script>
	<script type="text/javascript">
	
		var manager = null;
		
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#infoForm').form();
			$("#sub").click(function(){
	            firstClick(frm,options);
	        });
			$("#sub2").click(function(){
	            firstClick1(frm,options);
	        });
			
	        $("#sub").dblclick(function(){
	            firstClick(frm,options);
	        });
	        
	        $("#draft").click(function(){
	        	firstClickDraft(frm,options);
	        	/* layer.msg('正在保存中,请稍候...', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
				frm[0].action="saveDraft.ht";
				//$("#infoForm").action="saveDraft.ht";
				$("#infoForm").submit(); */
	        });
	        function firstClickDraft(frm,options){
	        	if($("#frsjh").val()=="必填"){
	        		$("#frsjh").val(null);
	        	}
	        	if($("#abbreviationName").val()=="必填"){
	        		$("#abbreviationName").val(null);
	        	}
	        	
	        	if($("#gszch").val()=="必填"){
	        		$("#gszch").val(null);
	        	}
	        	if($("#code").val()=="必填"){
	        		$("#code").val(null);
	        	}
	        	if($("#nsrsbh").val()=="必填"){
	        		$("#nsrsbh").val(null);
	        	}
	        	if($("#incorporator").val()=="必填"){
	        		$("#incorporator").val(null);
	        	}
	        	if($("#frsfzhm").val()=="必填"){
	        		$("#frsfzhm").val(null);
	        	}
	        	if($("#yyzz").val()=="必填"){
	        		$("#yyzz").val(null);
	        	}
	        	if($("#creditCode").val()=="必填"){
	        		$("#creditCode").val(null);
	        	}
	        	if($("#email").val()=="必填"){
	        		$("#email").val(null);
	        	}
	        	if($("#connecter").val()=="必填"){
	        		$("#connecter").val(null);
	        	}
	        	if($("#tel").val()=="必填"){
	        		$("#tel").val(null);
	        	}
	        	if($("#homephone").val()=="必填"){
	        		$("#homephone").val(null);
	        	}
	        	if($("#fax").val()=="必填"){
	        		$("#fax").val(null);
	        	}
	        	if($("#regCapital").val()=="必填"){
	        		$("#regCapital").val(null);
	        	}
	        	if($("#product").val()=="必填"){
	        		$("#product").val(null);
	        	}
	        	$("#info").val(ue.getContent());
	        	frm.setHtmlData();
				frm.ajaxForm(options);
				layer.msg('正在保存中,请稍候...', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
				frm[0].action="saveDraft.ht";
				$("#infoForm").submit();
	        	
	        }
	        
	        function firstClick(frm,options){
				//去掉所有的html标记
	        	var xieyi=$("#xieyi");
				if(!xieyi.attr("checked")){
					layer.alert("请勾选协议!!");
					return;
				}
				if(validForm()){
					var code = $("#code").val();
					var content=ue.getContent();
					if(content==null||content==""){
						layer.alert("请输入企业详情");
						return ;
					}
					if(content.length>5000){
						content.substring(0,5000);
					}
					$("#info").val(content);
					$("input[name='accountStatsus']").val('1');//将实名认证改为已认证
					$("input[name='credentialsNumber']").val($.trim($("input[name='frsfzhm']").val()));
					$("input[name='orgCode']").val($.trim($("#code").val()));
					$("input[name='businessLicense']").val($.trim($("#yyzz").val()));
					$("input[name='taxId']").val($.trim($("#nsrsbh").val()));
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
	        function firstClick1(frm,options){
				//去掉所有的html标记
					var code = $("#code").val();
					$("#info").val(ue.getContent());
					frm.setHtmlData();
					frm.ajaxForm(options);
					if(frm.valid()){
						layer.msg('正在保存中,请稍候...', {icon: 16,time: 100000,shade : [0.5 , '#000' , true]});
						frm[0].action="saveInfo.ht";
						form.submit();
						firstClick1 = secondClick;
					}else{
						layer.alert("信息填写不正确");
					}
					//str = str.replace(/<[^>]+>/g,"");
					
			
				
	        }
			function secondClick(){
				layer.alert("单据已经提交，请勿重复提交");
				return false;
			}
			
			//行业
			var indus = $('#industry').val();
			var ind2 = $('#ind2').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : indus },
				success : function(dics){
					var rows=dics;
					$('#industry2').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind2)
							s="selected='selected'";
						$('#industry2').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			
			//城市
			var induse = $('#province').val();
			var ind3 = $('#ind3').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : induse },
				success : function(dics){
					var rows=dics;
					$('#city').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind3)
							s="selected='selected'";
						$('#city').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			//县级
			var indus = $('#ind3').val();
			var ind4 = $('#ind4').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : indus },
				success : function(dics){
					var rows=dics;
					$('#county').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind4)
							s="selected='selected'";
						$('#county').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			//城市
			var induse1 = $('#province1').val();
			var ind5 = $('#ind5').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : induse1 },
				success : function(dics){
					var rows=dics;
					$('#city1').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind5)
							s="selected='selected'";
						$('#city1').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			//县级
			var indus2 = $('#ind5').val();
			var ind6 = $('#ind6').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/tenant/getCascadeJsonData.ht',
				data : {value : indus2 },
				success : function(dics){
					var rows=dics;
					$('#county1').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind6)
							s="selected='selected'";
						$('#county1').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
		});
		//上传图片	
		var dd = null;
		function openImageDialog(type){
			dd = type;
			$.cloudDialog.imageDialog({contextPath:"${ctx}",isSingle:true});
		}
		function imageDialogCallback(data){
			if(data.length > 0){
				_callbackImageUploadSuccess(data[0].filePath,dd);
			}
		}
		
		//上传图片回调函数
		function _callbackImageUploadSuccess(path,type){
			if(type=='0'){//上传导航栏
				$("#yyzzPic").val(path);
				var item = $('<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/' + path + ' "><img src="${fileCtx}/' + path + '" width="80" height="84" onmouseover="bigpic()"/></a>');
				$(".yyzzPic-pic").empty();
				$(".yyzzPic-pic").append(item);
			}else if(type == '1'){
				//说明有值，为替换状态
				if($("#frPic").val() != ""){
					var oldPath = $("#frPic").val().split(",")[0];
					$("#frPic").val($("#frPic").val().replace(oldPath,path))
				}else{
					$("#frPic").val(path);
				}
				var item = $('<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/' + path + '"><img src="${fileCtx}/' + path + '" width="80" height="84" onmouseover="bigpic()"/></a>');
			 	$("#frPicDiv img:eq(0)").remove();
				$("#frPicDiv").prepend(item);	
				$("#addfrPic2").show();
			}else if(type == '2'){
				//说明有值，为替换状态
				if($("#frPic").val().indexOf(",")>0 ){
					var oldPath = $("#frPic").val().split(",")[1];
					$("#frPic").val($("#frPic").val().replace(oldPath,path));
					var item = $('<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/' + path + '"><img src="${fileCtx}/' + path + '" width="80" height="84" onmouseover="bigpic()"/></a>');
				 	$("#frPicDiv img:eq(1)").remove();
					$("#frPicDiv").append(item);
				}else if($("#frPic").val()!="" ){
					var realPath = $("#frPic").val()+","+path;
					$("#frPic").val(realPath);
				 	var item = $('<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/' + path + '"><img src="${fileCtx}/' + path + '" width="80" height="84" onmouseover="bigpic()"/></a>');
				 	$("#frPicDiv img:eq(1)").remove();
					$("#frPicDiv").append(item);
				}
				else{
					layer.alert("请先上传身份证正面");
				}
			}
			else 
				if(type == '3'){
					$("#codePic").val(path);
					var item = $('<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/' + path + '"><img src="${fileCtx}/' + path + '" width="80" height="84" onmouseover="bigpic()"/></a>');
					$(".codePic-pic").empty();
					$(".codePic-pic").append(item);
				}
				else
				if(type == '4'){
					$("#nsrsbhPic").val(path);
					var item = $('<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/' + path + '"><img src="${fileCtx}/' + path + '" width="80" height="84" onmouseover="bigpic()"/></a>');
					$(".nsrsbhPic-pic").empty();
					$(".nsrsbhPic-pic").append(item);
				}
			
			
		}
		
		//检查是否为字母或者数字
		function checknum(value) {
            var Regx = /^[A-Za-z0-9]*$/;
            if (Regx.test(value)) {
                return true;
            }
            else {
                return false;
            }
        }
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			layer.closeAll();
			if (obj.isSuccess()) {
				obj=obj.getMessage();
				obj=$.parseJSON(obj);
				if(obj.flag=='audit'){
					
					audit(obj);
				}
				else{
					layer.alert(obj.Msg, {
				    	skin: 'layui-layer-molv' //样式类名
				    ,closeBtn: 0},function(){
						window.location.reload();
					});  
				}
				
			} else {
                if(obj.flag=='audit'){
					
					audit(obj);
				}
                else{
                	
                	
				   layer.alert('企业信息保存失败：'+obj.Msg,{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});     
                }
			}
		}
	 
		function shoudiv(){
			var ck = $("#Check_info");
			if(ck.is(':checked')==true){
				$("#isture_check").show();
			}else{
				$("#isture_check").hide();
				
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
			
		//校验省份
		var province1=$("#province1").val();
		var city1=$("#city1").val();
		var county1=$("#county1").val();
		if(province1=='香港特别行政区'||province1=='澳门特别行政区'||province1=='台湾省'||province1=="北京市"){
			
		}else{
			if(province1==null||province1==''){
				flag=false;
				layer.alert("请选择省份");
			}
			if(city1.indexOf("辖")>-1){
				
			}else{
				if(city1==null||city1==''){
					flag=false;
					layer.alert("请选择城市");
				}
				if(county1==null||county1==''){
					flag=false;
					layer.alert("请选择地区");
				}
			}
		}
		var province=$("#province").val();
		var city=$("#city").val();
		var county=$("#county").val();
        if(province=='香港特别行政区'||province=='澳门特别行政区'||province=='台湾省'||province=="北京市"){
			
		}else{
			if(province==null||province==''){
				flag=false;
				layer.alert("请选择省份");
			}
             if(city.indexOf("辖")>-1){
				
			}else{
				if(city==null||city==''){
					flag=false;
					layer.alert("请选择城市");
				}
				if(county==null||county==''){
					flag=false;
					layer.alert("请选择地区");
				}
			}
		}	
			if($("#codePic").val()==null||$("#codePic").val()==''){
				flag = false;
				layer.alert('请上传组织机构代码证扫描件或照片');
			}
			if($("#nsrsbhPic").val()==null||$("#nsrsbhPic").val()==''){
				flag = false;
				layer.alert('请上传税务登记证扫描件或照片');
			}
			if($("#yyzzPic").val()==null||$("#yyzzPic").val()==''){
				flag = false;
				layer.alert('请上传营业执照');
			}
			if($("#frPic").val()==null||$("#frPic").val()==''){
				flag = false;
				layer.alert('请上传法人身份照片');
			}
			if($("#frPic").val()!=null&&$("#frPic").val()!=''){
				if(!($("#frPic").val().indexOf(",")>-1)){
					flag = false;
					layer.alert('请上传法人身份照片');
				}
			}
			return flag;
		}
		
		
	var audit=function(obj){
		layer.open({
		    type: 2,
		    title: '认证信息',
		    shadeClose: false,
		    fix: true, 
		    shade: 0.6,
		    offset:50,
		    area: ['960px', '600px'],
		    content:"${ctx}/tenant/certifiedFeedback.ht?id="+obj.info.sysOrgInfoId+"&code="+obj.map.code+"&message="+encodeURI(encodeURI(obj.map.message))+"&status="+obj.status //iframe的url
		}); 
		
	}
	
	/* function bigpic(e){
		if(e.target){
			$("body").append('<p id="bigimage"><img src="'+ this.href + '" alt="" /></p>');
			$(this).find('img').stop().fadeTo('slow',0.5);
			widthJudge(e);    //调用宽度判断函数 
	        $("#bigimage").fadeIn('fast');
		}else{
			$(this).find('img').stop().fadeTo('slow',1); 
	         $("#bigimage").remove(); 
		}
	} */
	
	function bigpic(){
		var x = 22; 
	     var y = 20; 
	     $("a.smallimage").hover(function(e){ 
	             $("body").append('<p id="bigimage"><img src="'+ this.rel + '" alt="" /></p>'); 
	             $(this).find('img').stop().fadeTo('slow',0.5); 
	         widthJudge(e);    //调用宽度判断函数 
	         $("#bigimage").fadeIn('fast'); 
	     },function(){ 
	         $(this).find('img').stop().fadeTo('slow',1); 
	         $("#bigimage").remove(); 
	     });  
	     $("a.smallimage").mousemove(function(e){ 
	         widthJudge(e);    //调用宽度判断函数 
	     });  
	     function widthJudge(e){ 
	         var marginRight = document.documentElement.clientWidth - e.pageX; 
	         var imageWidth = $("#bigimage").width(); 
	         if(marginRight < imageWidth) 
	         { 
	             x = imageWidth + 22; 
	             $("#bigimage").css({top:(e.pageY - y ) + 'px',left:(e.pageX - x ) + 'px'}); 
	         }else{ 
	             x = 22; 
	             $("#bigimage").css({top:(e.pageY - y ) + 'px',left:(e.pageX + x ) + 'px'}); 
	         }; 
	     }
		
	}
	      
	 
	 $(window.parent.document).find("#mainiframe").load(function(){
	    	 var mainheight = $(document).height() + 30;
	    	$(this).height(mainheight);
	     });

	</script>
</head>
<body >
	
<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>实名认证是航天云网针对普通会员推出的免费认证服务，只要您是一家正规的企业，提交的资料真实有效，即可进行实名认证。</span></p>
 <p><span>认证成功后，将获得实名认证标志，更好获得买家信任。</span></p>
</div>
	
	<form id="infoForm" method="post" action="certified.ht"  class="signupForm2">
	         <input name="state" type="hidden" value="${info.state}">
	         <input name="accountStatsus" type="hidden">
	          <div id="wizard"  type="main">
	          <input type="hidden" name="openId" id="openId" value="${info.openId}" />
								<input type="hidden" name="sysOrgInfoId" id="sysOrgInfoId" value="${info.sysOrgInfoId}" />
								
									<div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>企业名称</span>
				                        <span clas="register_mainCon_con">${info.name}</span>
				                        <input id="name" name="name" type="hidden" value="${info.name}"/>
				                        <div class="tipinfo"></div>
				                     </div>
									<div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>简称</span>
				                        <input class="register_mainCon_con" id="abbreviationName" name="abbreviationName" maxlength="18" placeholder="请输入简称" maxlength="15" type="text" class="inputText" value="${info.abbreviationName}" validate="{required:true,specialChar:true}" />
				                        <div class="tipinfo"></div>
				                     </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>工商注册号</span>
				                        <input class="register_mainCon_con" id="gszch" name="gszch" maxlength="18" placeholder="2-15个数字,字母" maxlength="15" type="text" class="inputText" value="${info.gszch}" validate="{required:true,qiyexx:true}" />
				                        <div class="tipinfo"></div>
				                    </div>

									<div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>组织机构代码</span>
				                        <input class="register_mainCon_con" name="code" type="text"   value="${info.code}" id="code" maxlength="18" placeholder="2-15个数字,字母" validate="{required:true,qiyexx:true}"/>
				                        <div class="tipinfo"></div>
				                    </div>
									
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>纳税人识别号</span>
				                        <input name="nsrsbh" type="text" class="register_mainCon_con" value="${info.nsrsbh}" id="nsrsbh" placeholder="2-15个数字,字母" maxlength="18" value="${info.nsrsbh}"  validate="{required:true,qiyexx:true}"/>
				                        <div class="tipinfo"></div>
				                    </div>	
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>法人代表</span>
				                        <input name="incorporator" type="text" value="${info.incorporator}" class="register_mainCon_con" maxlength="15" id="incorporator" validate="{required:true}"
											value="${info.incorporator}" />
				                        <div class="tipinfo"></div>
				                    </div>
									
									  <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>法人身份证</span>
				                        <input name="frsfzhm" type="text" value="${info.frsfzhm}" placeholder="15或18位数字" class="register_mainCon_con" maxlength="18" id="frsfzhm" validate="{required:true,idCard:true}"
											value="${info.frsfzhm}" />
				                        <div class="tipinfo"></div>
				                    </div>
									
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>法人手机号</span>
				                        <input name="frsjh" type="text" value="${info.frsjh}" class="register_mainCon_con" maxlength="11" id="frsjh" validate="{required:true,phone:true}"
											value="${info.frsjh}" />
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>营业执照注册号</span>
				                        <input name="yyzz" type="text" class="register_mainCon_con" placeholder="请输入数字或字母"  id="yyzz" validate="{required:true,qiyexx:true}"
											value="${info.yyzz}" />
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>统一社会信用代码</span>
				                        <input name="creditCode" type="text" value="${info.creditCode}" class="register_mainCon_con" placeholder="请输入数字或字母" id="creditCode" validate="{required:true,qiyexx:true}"
											value="${info.creditCode}" />
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>企业类型</span>
				                        <ap:selectDB name="type" id="type"
										where="typeId=10000005420003" optionValue="itemValue"
										optionText="itemName" table="SYS_DIC"
										selectedValue="${info.type }">
									</ap:selectDB>
				                        <div class="tipinfo"></div>
				                    </div>
									 <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>经营地址</span>
				                              <ap:ajaxSelect srcElement="country" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="province1"/>
												<ap:selectDB name="province1" id="province1" defaultText="==请选择==" defaultValue=""
													where="itemKey='province'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.province1}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="province1" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="city1" />
												<ap:selectDB name="city1" id="city1" 
													where="itemKey='city'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.city1}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="city1" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="county1"/>
												<ap:selectDB name="county1" id="county1" 
													where="itemKey='county'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.county1}">
												</ap:selectDB>
											<input id="ind5" type="hidden" value="${info.city1}">
											<input id="ind6" type="hidden" value="${info.county1}">
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>员工人数</span>
				                       <ap:selectDB name="scale" id="scale" where="typeId=10000005680006" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.scale}">
										</ap:selectDB>
										 <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width">邮编</span>
				                       <input class="text" id="postcode" name="postcode" value="${info.postcode}" maxlength="20" type="text" class="inputText" validate="{required:false,maxlength:20,specialChar:true}"/>
				                       <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>企业邮箱</span>
				                        <input type="text" name="email" value="${info.email}" id="email" maxlength="60" style="width:200px;" class="inputText" validate="{required:true,email:true}"/>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>联系人</span>
				                        <input name="connecter" type="text" class="text" value="${info.connecter }" maxlength="32" id="connecter" validate="{required:true,maxlength:32}" />
				                         <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>手机号码</span>
				                       <input type="text" name="tel" class="text" id="tel" maxlength="11" value="${info.tel}" validate="{required:true,phone:true}"/>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>公司固话</span>
				                      <input type="text" value="${info.homephone}" name="homephone" id="homephone" class="text"  maxlength="32"  validate="{required:true,tel:true,maxlength:32}"/>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>公司传真</span>
				                        <input name="fax" type="text" class="text" value="${info.fax}" id="fax" maxlength="32" validate="{required:true,maxlength:32}" />
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>注册地址</span>
				                       <ap:ajaxSelect srcElement="country" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="province"/>
												<ap:selectDB name="province" id="province" defaultText="==请选择==" defaultValue=""
													where="itemKey='province'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.province}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="province" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="city"/>
												<ap:selectDB name="city" id="city" 
													where="itemKey='city'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.city}">
												</ap:selectDB>
												<ap:ajaxSelect srcElement="city" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="county"/>
												<ap:selectDB name="county" id="county" 
													where="itemKey='county'" optionValue="itemValue"
													optionText="itemName" table="SYS_DIC"
													selectedValue="${info.county}">
												</ap:selectDB>
											<input id="ind3" type="hidden" value="${info.city}">
											<input id="ind4" type="hidden" value="${info.county}">
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>注册资本(万元)</span>
				                       <input name="regCapital" type="text" class="text" maxlength="64" id="regCapital" validate="{required:true,maxlength:64,specialChar:true}"
											value="${info.regCapital}" />
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>注册资金币种</span>
				                       <%-- <input name="currencyFunds" type="text" class="text" maxlength="64" id="currencyFunds" validate="{required:true,maxlength:64,specialChar:true}"
											value="${info.currencyFunds}" /> --%>
										   <!-- <select name="currencyFunds">
						                       <option value="1">人民币</option>
						                       <option value="2">美元</option>
						                       <option value="0">其他</option>
					                       </select> -->
					                       
					                       <c:if test="${info.currencyFunds==null || info.currencyFunds=='' }">
						                       	<select name="currencyFunds">
							                       <option value="1">人民币</option>
							                       <option value="2">美元</option>
							                       <option value="0">其他</option>
						                       </select>
					                       
					                       </c:if>
					                       <c:if test="${info.currencyFunds=='1'}">
						                       	<select name="currencyFunds">
							                       <option value="1" selected="selected">人民币</option>
							                       <option value="2">美元</option>
							                       <option value="0">其他</option>
						                       </select>
					                       
					                       </c:if>
					                       <c:if test="${info.currencyFunds=='2' }">
						                       	<select name="currencyFunds">
							                       <option value="1">人民币</option>
							                       <option value="2" selected="selected">美元</option>
							                       <option value="0">其他</option>
						                       </select>
					                       
					                       </c:if>
					                       <c:if test="${info.currencyFunds=='0' }">
						                       	<select name="currencyFunds">
							                       <option value="1">人民币</option>
							                       <option value="2" >美元</option>
							                       <option value="0" selected="selected">其他</option>
						                       </select>
					                       
					                       </c:if>
					                       
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>经营模式</span>
				                       <select name="model">
				                       <option value="1">生产型</option>
				                       <option value="2">贸易型</option>
				                       <option value="3">服务型</option>
				                       <option value="4">研发型</option>
				                       <option value="0">其他类型</option>
				                       </select>
				                        <div class="tipinfo"></div>
				                    </div>
				                    
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>主营行业</span>
				                     	<ap:selectDB name="industry" id="industry" where="parentId=10000003470025"  optionValue="itemValue" defaultText="==请选择==" defaultValue=""
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.industry}">
										</ap:selectDB>
										<ap:ajaxSelect srcElement="industry" url="${ctx}/tenant/getCascadeJsonData.ht" targetElement="industry2"/>
										<ap:selectDB name="industry2" id="industry2" 
											where="1!=1" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.industry2}">
										</ap:selectDB>
										<%-- <input id="ind1" type="hidden" value="${info.industry}"> --%>
										<input id="ind2" type="hidden" value="${info.industry2}">
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>主营产品</span>
				                      <input class="text" id="product" name="product" value="${info.product }" maxlength="56" type="text" class="inputText" validate="{required:true,maxlength:256,specialChar:true}" />
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list">
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>年销售额</span>
				                      <ap:selectDB name="turnover" id="turnover" where="typeId=10000005640295" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.turnover}">
											</ap:selectDB>
				                        <div class="tipinfo"></div>
				                    </div>
				                     <div class="register_mainCon_list" style="width:100%">
				                        <span class="register_mainCon_tit openAccount_titie_width" style="float: left;"><span class="register2_red">*</span>企业简介</span>
				                      <input id="info" name="info" type="hidden" value='${info.info}'/>
				                      <script class="postbox" type="text/plain"  name="html"  id="editor" position="center" style="width:90%;padding-left: 140px;">
                                      </script>
				                        <div class="tipinfo"></div>
				                    </div>
				                    
				                    <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="codePic" id="codePic" value="${info.codePic}"/>
									 	<div class="codePic-pic" >
									 	<c:if test="${not empty info.codePic}" >
									 		<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/${info.codePic}"><img src="${fileCtx}/${info.codePic}" width="80" height="84" onmouseover="bigpic()"/></a>
									 	</c:if>
									 	</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>组织机构代码证扫描件或照片</span> 
				                        <a href="javascript:openImageDialog(3);" class="authentication_pic1" id="addProductPic1" js_tukubtn="true">上传组织机构代码证扫描件</a>
				                        <div class="tipinfo"></div>
				                        <div class="authentication_tip">建议上传小于400x480像素的图片</div>
				                    </div>	
				                    <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="nsrsbhPic" id="nsrsbhPic" value="${info.nsrsbhPic}"/>
									 	<div class="nsrsbhPic-pic" >
									 	<c:if test="${not empty info.nsrsbhPic}" >
									 		<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/${info.nsrsbhPic}"><img src="${fileCtx}/${info.nsrsbhPic}" width="80" height="84" onmouseover="bigpic()"/></a>
									 	</c:if>
									 	</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>税务登记证扫描件或照片</span> 
				                        <a href="javascript:openImageDialog(4);" class="authentication_pic1" id="addProductPic2" js_tukubtn="true">上传税务登记证扫描件或照片</a>
				                        <div class="tipinfo"></div>
				                        <div class="authentication_tip">建议上传小于400x480像素的图片</div>
				                    </div>	
									 <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="yyzzPic" id="yyzzPic" value="${info.yyzzPic}"/>
									 	<div class="yyzzPic-pic" id="yyzzPic">
									 	<c:if test="${not empty info.yyzzPic}" >
									 	<span class='zoom' id='ex1'>
									 		<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}/${info.yyzzPic}"><img src="${fileCtx}/${info.yyzzPic}" width="80" height="84" onmouseover="bigpic()"/></a>
									 	</span>
									 	</c:if>
									 	</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>营业执照</span> 
				                        <a href="javascript:openImageDialog(0);" class="authentication_pic1" id="addProductPic" js_tukubtn="true">上传营业执照图片</a>
				                        <div class="tipinfo"></div>
				                        <div class="authentication_tip">建议上传小于400x480像素的图片</div>
				                    </div>			
									 <div class="register_mainCon_list" style="margin-bottom:15px;height:162px;">
									 	<input type="hidden" name="frPic" id="frPic"  value="${info.frPic}"/>
									 	<div class="frPicDiv-pic" id="frPicDiv">
									 			<c:forEach items="${info.frPic}" var="it" varStatus="i">
												 	<a class="smallimage" href="javascript:void(0)" rel="${fileCtx}${it}"><img src="${fileCtx}${it}" width="80" height="84" onmouseover="bigpic()"/></a>
												</c:forEach>
												</div>
				                        <span class="register_mainCon_tit openAccount_titie_width"><span class="register2_red">*</span>法人身份证照</span> 
				                        <a href="javascript:openImageDialog(1);" class="authentication_pic1 authentication_pic2" id="addfrPic1" js_tukubtn="true">上传法人身份证照正面</a>
										<a href="javascript:openImageDialog(2);" class="authentication_pic1 authentication_pic2" id="addfrPic2" js_tukubtn="true">上传法人身份证照反面</a>
				                        <div class="tipinfo"></div>
				                        <div class="authentication_tip">建议上传小于400x480像素的图片</div>
				                    </div>
				                    
				                    
				                    <div class="register_mainCon_list" style="margin-left: 140px;">
				                        <span class="register_mainCon_tit openAccount_titie_width" ></span>
				                        <input type="checkbox" id="xieyi"  />我已阅读企业认证协议&nbsp;&nbsp;<a href="${ctx}/tenant/paymentxieyi.ht" target="_blank" class="al">《企业认证协议》</a>
				                        <div class="tipinfo"></div>
				                    </div>
				                    
									<div class="register_mainCon_list authentication_btn_mar" id="saveOrSubmit">
				                       <span class="register_mainCon_tit openAccount_titie_width"></span>
				                        <input type="button" class="register_mainCon_btn" value="提交" id="sub"/>
				                        <input type="button" class="register_mainCon_btn1" value="保存" id="draft" />
				                        <div class="tipinfo"></div>
				                    </div>
									<!-- <div class="register_mainCon_list authentication_btn_mar" >
				                       <span class="register_mainCon_tit openAccount_titie_width"></span>
				                        <input type="button" class="register_mainCon_btn" value="保存" id="sub2" />
				                        <div class="tipinfo"></div>
				                    </div> -->
				                    <c:if test="${not empty info.axnStatus}">
				                    <input type="hidden" name="axnStatus" value="${info.axnStatus}">
				                    </c:if>
				                    
				                    <input type="hidden" name="orgid"  value="${info.sysOrgInfoId}" />
			                        <input type="hidden" name="sysid"  value="uc" />
						            <input type="hidden" name="clientProperty"  value="1" />
						            <input type="hidden" name="branchaccountname"  value="${info.name}" />
						           <%--  <input type="text" id="incorporator"  value="${info.incorporator}" /> --%>
						            <input type="hidden" name="credentialsType"  value="A" />
						            <input type="hidden" name="credentialsNumber"  value="${info.frsfzhm}" />
						             <input type="hidden" name="orgCode"  value="${info.code}" />
						             <input type="hidden" name="businessLicense"  value="${info.yyzz}"  />
						             <input type="hidden" name="taxId"  value="${info.nsrsbh}" />
			<c:choose>
				<c:when
					test="${not empty branch.id and (branch.accstate=='1' or branch.accstate=='3' or branch.accstate=='4' )}">
					
						<input type="hidden" name="businessFlag"
							value="2" />
				</c:when>
				<c:otherwise>
					<input type="hidden" name="businessFlag"
							value="1" />
				</c:otherwise>
			</c:choose>
             <input type="hidden" name="branchId"  value="${branch.id}"/>
             <input type="hidden" name="accountType1"   value="1"/>
             <input type="hidden" name="accstate" value="${branch.accstate}">
		     <input type="hidden" name="flag" value="1">
		</div>
	      
	  
	             
						
		
	</form>
	<!-- </div>
	</div> -->
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

</style>
<script type="text/javascript">
ue= UE.getEditor('editor',
		 {initialFrameHeight:280,
		toolbars: [
		           [
		               //'anchor', //锚点
		               'undo', //撤销
		               'redo', //重做
		               'bold', //加粗
		               'indent', //首行缩进
		               //'snapscreen', //截图
		               'italic', //斜体
		               'underline', //下划线
		               'strikethrough', //删除线
		               'fontborder', //字符边框
		               'subscript', //下标			               
		               'superscript', //上标
		               'formatmatch', //格式刷
		               //'source', //源代码
		               'blockquote', //引用
		               'pasteplain', //纯文本粘贴模式
		               'selectall', //全选
		               'print', //打印
		               'preview', //预览
		               'horizontal', //分隔线
		               'removeformat', //清除格式
		               'time', //时间
		               'date', //日期			               
		               'inserttable', //插入表格
		               'edittable', //表格属性
		               'edittd', //单元格属性
		               'insertrow', //前插入行
		               'insertcol', //前插入列
		               'mergeright', //右合并单元格
		               'mergedown', //下合并单元格
		               'deleterow', //删除行
		               'deletecol', //删除列
		               'splittorows', //拆分成行
		               'splittocols', //拆分成列
		               'splittocells', //完全拆分单元格
		               'deletecaption', //删除表格标题
		               'inserttitle', //插入标题
		               'mergecells', //合并多个单元格
		               'deletetable', //删除表格			               
		               'insertparagraphbeforetable', //"表格前插入行" 
		               'imagenone', //默认
		               'imageleft', //左浮动
		               'imageright', //右浮动
		               'edittip ', //编辑提示
		               //'insertcode', //代码语言
		               'fontfamily', //字体
		               'fontsize', //字号
		               //'paragraph', //段落格式			               			               
		               'link', //超链接
		               'unlink', //取消链接
		               'emotion', //表情
		               'spechars', //特殊字符
		               'searchreplace', //查询替换
		               //'map', //Baidu地图
		               //'gmap', //Google地图
		               'justifyleft', //居左对齐
		               'justifyright', //居右对齐
		               'justifycenter', //居中对齐
		               'justifyjustify', //两端对齐
		               'forecolor', //字体颜色
		               'backcolor', //背景色
		               'insertorderedlist', //有序列表
		               'insertunorderedlist', //无序列表
		               'pagebreak', //分页			               
		               //'fullscreen', //全屏
		               'directionalityltr', //从左向右输入
		               'directionalityrtl', //从右向左输入
		               'rowspacingtop', //段前距
		               'rowspacingbottom', //段后距
		               'lineheight', //行间距
		               //'insertframe', //插入Iframe			               
		              // 'attachment', //附件
		               //'imagecenter', //居中
		               //'wordimage', //图片转存    
		               'customstyle', //自定义标题
		               'autotypeset', //自动排版
		               //'webapp', //百度应用
		               'touppercase', //字母大写
		               'tolowercase', //字母小写
		              // 'background', //背景
		               'template', //模板
		              // 'scrawl', //涂鸦
		               //'music', //音乐			               
		               //'drafts', // 从草稿箱加载
		              // 'charts', // 图表
		              'cleardoc', //清空文档
		           ]
		       ],labelMap:{
		            'insertimage':'上传图片'
		        }
		
	});
	var content = $("#info").val();
ue.ready(function(){
		ue.setContent(content);    
	})


</script>
<script>
	/* 	$(document).ready(function(){
			$('#ex1').zoom();
			
		}); */
		
		
	</script>
</body>
</html>
