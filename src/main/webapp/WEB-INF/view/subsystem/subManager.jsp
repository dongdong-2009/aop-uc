<%--
	time:2011-11-21 12:22:07
--%>
<%@page language="java" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="fileCtx" value="http://img.cosimcloud.com/"/>
<html>
<head>
<title>子系统管理员</title>
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
				if(${flag}){
					
					if($("#systemId").val()==null||$("#systemId").val()==''){
						layer.alert("子系统不能为空");
						return;
					} 		
				}
				
				
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
					<%--<c:choose>
					 <c:when test="${sysUser.userId==0||sysUser.userId==null}">
							添加子系统管理员
						</c:when> 
						<c:otherwise>--%>
						 <c:choose>
						    <c:when test="${not empty systemId }">
						             修改子系统管理员
						    </c:when>
						      <c:otherwise>
						               保存
						    </c:otherwise>      
						 </c:choose>
							
					<%-- 	</c:otherwise>
					</c:choose> --%>
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">
						
						 <c:choose>
						    <c:when test="${flag}">
						               保存子系统管理员
						    </c:when>
						      <c:otherwise>
						              保存
						    </c:otherwise>      
						 </c:choose>
						</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="${returnUrl}">返回</a><%-- //href="${ctx }/user/management.ht" --%>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="subSystemForm" method="post" action="/platform/subSystem/sysUser/saveSubManager.ht">
				<div  tabid="userdetail" icon="${ctx}/styles/default/images/resicon/user.gif">
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
						<td rowspan="<c:if test="${not empty sysUser.userId}">12</c:if><c:if test="${empty sysUser.userId}">12</c:if>" align="center" width="26%">
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
					    <th style="width:200px;"><span class="required" style="color: red;">*</span>
					    	 <c:choose>
						    <c:when test="${flag }">
						                 子系统管理员用户名:
						    </c:when>
						      <c:otherwise>
						               用户名:
						    </c:otherwise>      
						 </c:choose>
					     </th>
					    <c:if test="${empty sysUser.userId}">
						<td class="required" style="width:600px;"><input type="text" id="account" name="account" required value="${sysUser.account}" style="width:240px !important" data-rule-remote="${ctx}/user/checkAccountRepeat.ht" data-msg-remote="该用户名已经存在,请重新输入" /></td>
						</c:if>
						<c:if test="${not empty sysUser.userId}">
						<td class="required" style="width:600px;"><input type="text" id="account" name="account" required value="${sysUser.account}" style="width:240px !important" readonly="readonly"/></td>
						</c:if>
					</tr>
					<c:if test="${flag}"> 				
				   	<tr>
							<th width="20%"><span class="required" style="color: red;">*</span>子系统名称: </th>
							<td>
								
								<select name="fromSysId" id="systemId" class="select" style="width: 34%;">
									<c:choose>
									<c:when test="${flag}">
										
										    <option value="">--请选择子系统--</option>
											<c:forEach var="subsystem" items="${subsystemList }">
												<option value="${subsystem.systemId}" <c:if test="${sysUser.fromSysId eq subsystem.systemId }"> selected="selected"</c:if> >${subsystem.sysName}</option>
											</c:forEach>
										
									</c:when>
									<%-- <c:otherwise>
										<input type="hidden" name="fromSysId" value="${systemId }" />
										<c:forEach var="subsystem" items="${subsystemList }">
											 <c:if test="${sysRole.systemId==subsystem.systemId }">${subsystem.sysName }</c:if>
										</c:forEach>
									</c:otherwise>  --%>
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
						     <span class="required" style="color: red;" >*</span>密   码: </th>
						     <td class="required" style="width:600px;"><input type="password" id="password" name="password" maxlength="16" minlength="6" required  style="width:240px !important" /></td>
						   
						   </c:otherwise>
						</c:choose>
						
					</tr>
					<tr>
					    <th style="width:200px;"><span style="color: red;" class="required">*</span>用户姓名: </th>
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
					   <th style="width:200px;"><span style="color: red;" class="required">*</span>邮箱地址: </th>
					   <td class="required" style="width:600px;"><input type="text" id="email" name="email"  value="${sysUser.email}" email="true" style="width:240px !important"  required ${sysUser.isEmailTrue eq "1"?"readonly='readonly' onclick=\"tishi(this,\'a\');\" style='cursor:pointer;'":''}/></td>
					</tr>
					
					<tr>
						<th style="width:200px;"><span style="color: red;" class="required">*</span>手机号码: </th>
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
					<%-- <tr>
									<th>是否锁定: </th>
									<td >								
										<select name="isLock" class="select" style="width:245px !important" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
											<option value="<%=SysUser.UN_LOCKED %>" <c:if test="${sysUser.isLock==0}">selected</c:if> >未锁定</option>
											<option value="<%=SysUser.LOCKED %>" <c:if test="${sysUser.isLock==1}">selected</c:if> >已锁定</option>
										</select>	
									</td>				  
								</tr>
								
								<tr>
								    <th>是否过期: </th>
									<td >
										<select name="isExpired" class="select" style="width:245px !important" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
											<option value="<%=SysUser.UN_EXPIRED %>" <c:if test="${sysUser.isExpired==0}">selected</c:if> >未过期</option>
											<option value="<%=SysUser.EXPIRED %>" <c:if test="${sysUser.isExpired==1}">selected</c:if> >已过期</option>
										</select>
									</td>
								</tr>
								
								<tr>
								   <th>当前状态: </th>
									<td>
										<select name="status"  class="select" style="width:245px !important" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
											<option value="<%=SysUser.STATUS_OK %>" <c:if test="${sysUser.status==1}">selected</c:if> >激活</option>
											<option value="<%=SysUser.STATUS_NO %>" <c:if test="${sysUser.status==0}">selected</c:if> >禁用</option>
											<option value="<%=SysUser.STATUS_Del %>" <c:if test="${sysUser.status==-1}">selected</c:if>>删除</option>
										</select>
									</td>								
								</tr>				 --%>								
				</table>
				<input type="hidden" name="orgId" value="${sysUser.orgId}" />
				<input type="hidden" name="userId" value="${sysUser.userId}" id="userId"/>
				<input type="hidden"  value="${sysUser.password}" id="pass"/>
				</div>
				
			</form>
		</div>
	</div>
</body>
</html>

