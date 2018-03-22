<%--
	time:2011-11-21 12:22:07
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="fileCtx" value="http://img.cosimcloud.com/"/>
<html>
<head>
<title>修改用户管理</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=subSystem"></script>
<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerWindow.js"></script>
<script type="text/javascript" src="${ctx }/js/hotent/platform/system/IconDialog.js"></script>
<script type="text/javascript" src="${ctx }/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.js"></script>

<!-- 上传图片 -->
<script type="text/javascript" src="${ctx}/js/upload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/upload/cloudDialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/upload/uploadPreview.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>	
<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
<script type="text/javascript">
		var isPhoneRepeat = false;
		$(function() {
			
			var pic=$("#picture").val();
			if(pic!=null&&pic!=''){
				$("#personPic").attr("src","${fileCtx}/"+pic);
			}
			
			 function showRequest(formData, jqForm, options) { 
				return true;
			} 
			if(${sysUser.userId==0||sysUser.userId==null}){
				valid(showRequest,showResponse,1);
			}else{
				valid(showRequest,showResponse);
			} 
			$("a.save").click(function() {
				/* if($("#shortAccount").val()==null||$("#shortAccount").val()==''){
					layer.alert("账号不能为空");
					return;
				} */
				if($("#account").val()==null||$("#account").val()==''){
					layer.alert("用户名不能为空");
					return;
				}
				var userId=$("#userId").val();
				var password=$("#password").val();
				var pass=$("#pass").val();
				if(userId!=null&&userId!=''){
				   
				}else{
					if(password==null||password==''){
						layer.alert("密码不能为空");
						return;
					}
				}
				if($("#fullname").val()==null||$("#fullname").val()==''){
					layer.alert("用户姓名不能为空");
					return;
				}
				if($("#email").val()==null||$("#email").val()==''){
					layer.alert("邮箱不能为空");
					return;
				}
				if($("#mobile").val()==null||$("#mobile").val()==''){
					layer.alert("手机号码不能为空");
					return;
				}
				if(isPhoneRepeat){
					layer.alert("手机号码已被注册");
					return;
				} 
				$('#subSystemForm').submit(); 
				
			});
			
			
		});

		function selectIcon(){
			IconDialog({callback:function(src){
				$("#logo").val(src);
				$("#logoImg").attr("src",src);
				$("#logoImg").show();
			},params:"iconType=1"})
		};
		
		
		$.extend($.validator.messages, {
			email: "邮箱格式不正确,请重新输入",
			minlength: $.validator.format("密码格式位数不正确,请重新输入"),
			maxlength: $.validator.format("密码格式位数不正确,请重新输入"),
			required:"不能为空"
		});
		
		jQuery.validator.addMethod("mobile", function (value, element) {
			var mobile = /^1[3|4|5|7|8]\d{9}$/;
			return this.optional(element) || (mobile.test(value));
		}, "手机格式不正确,请重新输入");
		
		jQuery.validator.addMethod("email", function (value, element) {
			var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			return this.optional(element) || (email.test(value));
		}, "请输入合法的邮箱地址");
		
		function openImageDialog(){
			$.cloudDialog.imageDialog({contextPath:"${ctx}",isSingle:true});
		}
		function imageDialogCallback(data){
			if(data.length > 0){
				_callbackImageUploadSuccess(data[0].filePath);
			}
		}
		
		function _callbackImageUploadSuccess(path){
			$("#picture").val(path);
			$("#personPic").attr("src","${fileCtx}/" + path);
		};
		
		
		function check(obj){
			var value='${sysUser.mobile}';
			var value2=obj.value;
			if(value2!=value){
				$(obj).attr("data-rule-remote","${ctx}/user/checkPhoneRepeat.ht");
				$(obj).attr("data-msg-remote","该手机号已经存在,请重新输入");
			}
		}
		
		function tishi(obj,type){
			if(type=='a'){
				layer.tips("邮箱已验证，更换请到安全设置中修改", obj,{
					tips:3,
					time:4e3
				}
				);
			}else{
					layer.tips("手机已绑定，更换请到安全设置中修改", obj,{
						tips:3,
						time:4e3
					}
					);
			}
		}
	</script>
	
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label"> 
				<c:choose>
						<c:when test="${sysUser.userId==0||sysUser.userId==null}">
							添加用户
						</c:when>
						<c:otherwise>
							修改用户
						</c:otherwise>
					</c:choose>
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="${ctx }/user/management.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="subSystemForm" method="post" action="save.ht">
				<div title="基本信息" tabid="userdetail" icon="${ctx}/styles/default/images/resicon/user.gif">
				<%-- <table class="table-detail" cellpadding="0" cellspacing="0"
					border="0">
					<tr>
						<th width="20%">名称: <span class="required">*</span></th>
						<td><input type="text" id="fullname" name="fullname" required value="${sysUser.fullname}" class="inputText" /></td>
					</tr>
					<tr>
						<th width="20%">账户:<span class="required">*</span></th>
						<td><input type="text" id="account" name="account" required value="${sysUser.account}" class="inputText" /></td>
					</tr>
					
                   	<tr>
						<th width="20%">密码:<span class="required">*</span></th>
						<td><input type="text" id="password" name="password" required maxlength="16" minlength="6"  value="${sysUser.password}" class="inputText" /></td>
					</tr>
					<tr>
						<th width="20%">手机:<span class="required">*</span></th>
						<td><input type="text" id="mobile" name="mobile" required mobile="true" value="${sysUser.mobile}" class="inputText" data-rule-remote="${ctx}/user/checkPhoneRepeat.ht" data-msg-remote="该手机号已存在,请重新输入" /></td>
					</tr>
					<tr>
						<th width="20%">邮箱:<span class="required">*</span></th>
						<td><input type="text" id="email" name="email" required email="true" value="${sysUser.email}" class="inputText" data-rule-remote="${ctx}/user/checkEmailRepeat.ht" data-msg-remote="该邮箱已存在,请重新输入"/></td>
					</tr>
				</table> --%>

           		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td rowspan="<c:if test="${not empty sysUser.userId}">9</c:if><c:if test="${empty sysUser.userId}">10</c:if>" align="center" width="26%">
						<div style="float:top !important;background: none;height: 24px;padding:0px 6px 0px 112px;">
							<a class="link uploadPhoto" href="#" onclick="openImageDialog();">上传照片</a>
						</div>
						<div class="person_pic_div">
							<p>
							<c:choose>
							  <c:when test="${empty sysUser.picture}">
							    <img id="personPic" src="${fileCtx}/${pictureLoad}" onerror="this.src='${ctx}/commons/image/default_image_male.jpg'"  alt="个人相片"  width="230px" height="378px"/ >
							  </c:when>
							  <c:otherwise>
							      <img id="personPic" src="${fileCtx}/${sysUser.picture}"   alt="个人相片"  width="230px" height="378px"/ >
							  </c:otherwise>
							  </c:choose>
							</p>
							<input type="hidden" id="picture" name="picture" value="${sysUser.picture}" />
						</div>
						</td>
						<%-- <th width="18%"><span class="required">*</span>账   号: </th>
						<c:if test="${empty sysUser.userId}">
							<td><input type="hidden" id="shortAccount" name="shortAccount" value="${sysUser.shortAccount}"/></td>
						</c:if>
						<c:if test="${empty sysUser.userId}">
							<td><input type="text"   id="shortAccount" name="shortAccount" required value="${sysUser.shortAccount}" style="width:240px !important" /></td>
						</c:if>
						
						<c:if test="${not empty sysUser.userId}">
							<td ><input type="text" id="shortAccount" name="shortAccount" required value="${sysUser.shortAccount}" style="width:240px !important" /></td>
						</c:if> --%> 
					</tr>	
					 <tr>
					    <th style="width:200px;"><span class="required" style="color: red;">*</span>用户账  号: </th>
					    <c:if test="${empty sysUser.userId}">
						<td class="required" style="width:600px;"><input type="text" id="account" name="account" required value="${sysUser.account}" style="width:240px !important" data-rule-remote="${ctx}/user/checkAccountRepeat.ht" data-msg-remote="该用户名已经存在,请重新输入" /></td>
						</c:if>
						<c:if test="${not empty sysUser.userId}">
						<td class="required" style="width:600px;"><input type="text" id="account" name="account" required value="${sysUser.account}" style="width:240px !important" readonly="readonly"/></td>
						</c:if>
					</tr>						
				   	<c:if test="${currentUser.account eq 'confadmin'}"> 				
				   	<tr>
							<th width="20%"><span class="required" style="color: red;">*</span>子系统名称: </th>
							<td>
								<select name="fromSysId" id="systemId" class="select" style="width: 40%;">
									<c:choose>
									<c:when test="${flag}">
									         <option value="">--请选择子系统--</option>
											<c:forEach var="subsystem" items="${subsystemList }">
												<option value="${subsystem.systemId}" <c:if test="${subsystem.systemId eq systemId }"> selected="selected"</c:if> >${subsystem.sysName}</option>
											</c:forEach>
									 </c:when>
									 <c:otherwise>
									        <option value="">--请选择子系统--</option>
											<c:forEach var="subsystem" items="${subsystemList }">
												<option value="${subsystem.systemId}" <c:if test="${sysUser.fromSysId eq subsystem.systemId }"> selected="selected"</c:if> >${subsystem.sysName}</option>
											</c:forEach>
									</c:otherwise>  
									</c:choose>
							</select>
							</td>
					</tr>	
					</c:if>															
					<tr >
						<th style="width:200px;">
						<c:if test="${not empty sysUser.userId}">
						  
						</c:if>
						<c:choose>
						   <c:when test="${not empty sysUser.userId}">
						     <span style="color: red;" ></span>密   码: </th>
						      <td  style="width:600px;"><input type="password" id="password" name="password" maxlength="16" minlength="6"  style="width:240px !important" /></td>
						   </c:when>
						   <c:otherwise>
						     <span class="required" style="color: red;">*</span>密   码: </th>
						     <td class="required" style="width:600px;"><input type="password" id="password" name="password" maxlength="16" minlength="6" required  style="width:240px !important" /></td>
						   
						   </c:otherwise>
						</c:choose>
						
					</tr>
					<tr>
					    <th style="width:200px;"><span class="required" style="color: red;">*</span>用户姓名: </th>
						<td class="required" style="width:600px;"><input type="text" id="fullname" name="fullname" required  value="${sysUser.fullname}" style="width:240px !important" /></td>
					</tr>
					
					<tr>
						<th>用户性别: </th>
						<td>
						<select name="sex" class="select" style="width:245px !important">
								<option value="1" <c:if test="${sysUser.sex==1}">selected</c:if> >男</option>
								<option value="0" <c:if test="${sysUser.sex==0}">selected</c:if> >女</option>
						</select>						
						</td>
					</tr>		
					<tr>
					   <th style="width:200px;"><span class="required">*</span>邮箱地址: </th>
					   <td class="required" style="width:600px;"><input type="text" id="email" name="email"  value="${sysUser.email}" email="true" style="width:240px !important"  required ${sysUser.isEmailTrue eq "1"?"readonly='readonly' onclick=\"tishi(this,\'a\');\" style='cursor:pointer;'":''}/></td>
					</tr>
					
					<tr>
						<th style="width:200px;"><span class="required">*</span>手机号码: </th>
						<%-- <td class="required" style="width:600px;"><input type="text" id="mobile" name="mobile"  required value="${sysUser.mobile}" mobile="true" style="width:240px !important" /></td>	 --%>					   
						<c:if test="${empty sysUser.userId}">
						<td class="required" style="width:600px;"><input type="text" id="mobile" name="mobile"  required value="${sysUser.mobile}" mobile="true" style="width:240px !important"  data-rule-remote="${ctx}/user/checkPhoneRepeat.ht" data-msg-remote="该手机号已经存在,请重新输入" /></td>
						</c:if>
						<c:if test="${not empty sysUser.userId}">
						<td class="required" style="width:600px;"><input type="text" id="mobile" name="mobile"  required value="${sysUser.mobile}" mobile="true" style="width:240px !important" ${sysUser.isMobailTrue eq "1"?"readonly='readonly' onclick=\"tishi(this,\'b\');\" style='cursor:pointer;'":"onclick='check(this);'"}/></td>
						</c:if>
					</tr>
					
					<tr>
					    <th style="width:200px;">固 定 电   话: </th>
						<td style="width:600px;"><input type="text" id="phone" name="phone"  value="${sysUser.phone}"  style="width:240px !important" /></td>
					</tr>
											
				</table>
				<input type="hidden" name="orgId" value="${sysUser.orgId}" />
				<input type="hidden" name="userId" value="${sysUser.userId}" id="userId"/>
				<input type="hidden"  value="${sysUser.password}" id="pass"/>
				</div>
				
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
	
	//修改添加子账号信息时手机号可能重复bug
	  $(function(){
		  var originNumber = $("#mobile").val();//获取到手机号码，用来判断是新建用户还是修改用户，如果不为空就是修改用户
		  var CTX="${pageContext.request.contextPath}";
		  
		  //添加子账号信息时判断是否手机号是否已被注册
		  $("#mobile").blur(function (){
				var value1=this.value;
				if($.trim(value1).length==0){
					return false;
				}
		        var regex = /^1[3|4|5|7|8]\d{9}$/;
			    var flag = regex.test($.trim(value1));
				if(!flag ){
				return false;
				} 
				
				var mobile = document.getElementById("mobile");
		        var _parent = mobile.parentNode;
		        var _childs = _parent.children;
		        var _childs = _parent.children;
		        for(var i=0; i<_childs.length; i++){
		            if(_childs[i].tagName.toLowerCase() == "label"){
		                //_parent.remove(_childs[i]);
		                _childs[i].innerHTML = "";;
		            }
		        }
		        
		        //判断是否修改了手机号
		        var currNumber = $("#mobile").val();//获取当前手机号
		        if(originNumber == currNumber){
		        	isPhoneRepeat = false;
		        	return ;
		        }
				$.ajax({
					url:CTX + '/user/isPhoneRepeat.ht',
					type: "post",
					dataType: "json",
					data: { 
						"mobile":$("#mobile").val()
					},
					success : function(istf) {
						if(istf==true){
					        label = _parent.appendChild(document.createElement('label'));
					        label.setAttribute("for", "mobile");
					        label.setAttribute("class", "error");
					        label.innerHTML = "此手机号码已被注册，请换其它手机号！";
					        isPhoneRepeat = true;
						}else{
							console.log("该手机号可以注册");
							isPhoneRepeat = false;
						}
					}
				
				});
			});
	  });
	</script>
</body>
</html>

