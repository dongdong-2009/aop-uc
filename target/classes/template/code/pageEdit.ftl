<#assign class=table.variable.class>
<#assign tabcomment=table.tableDesc>
<#assign classVar=table.variable.classVar>
<#assign package=table.variable.package>
<#assign fieldList=table.fieldList>
<#assign subtables=table.subTableList>

<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 ${tabcomment}</title>
	<%@include file="/commons/include/customForm.jsp" %>
	<#if subtables?size!=0>
	<script type="text/javascript" src="<#noparse>${ctx}</#noparse>/js/hotent/subform.js" ></script>
	</#if>
	<#if defKey?exists>
	<script type="text/javascript" src="<#noparse>${ctx}</#noparse>/js/hotent/platform/bpm/TaskImageUserDialog.js"></script>
	<script type="text/javascript">
		var taskId="<#noparse>${taskId}</#noparse>";
		$(function() {
			$("a.save").click(function() {
				$("#${classVar}Form").attr("action","save.ht");
				submitForm();
			});
			
			$("a.run").click(function(){
				$("#${classVar}Form").attr("action","run.ht");
				submitForm();
			});
		
		});
		
		function submitForm(){
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#${classVar}Form').form();
			$('#${classVar}Form').ajaxForm(options);
			if(frm.valid()){
				$('#${classVar}Form').submit();
			}
		}
		
		//显示流程图
		function showTaskUserDlg(){
			TaskImageUserDialog({taskId:taskId});
		}
		//显示审批历史
		function showTaskOpinions(){
			var winArgs="dialogWidth=800px;dialogHeight=600px;help=1;status=1;scroll=1;center=1;resizable:1";				
			var url='<#noparse>${ctx}</#noparse>/platform/bpm/taskOpinion/list.ht?actInstId=<#noparse>${</#noparse>task.processInstanceId}';
			url=url.getNewUrl();
			window.showModalDialog(url,"",winArgs);
		}
		
		
		function completeTask(type){
			$("#${classVar}Form").attr("action","doNext.ht?taskId="+taskId+"&voteAgree="+type);
			submitForm();
		}
		
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.success("成功", obj.getMessage(), function(rtn) {
					if(rtn==false)return;
					if(window.opener){
						window.opener.location.reload();
						window.close();
					}else{
						this.close();
						window.location.href="<#noparse>${ctx}</#noparse>/platform/demo/${classVar}/list.ht"
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
	</script>
	<#else>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=<#noparse>$('#</#noparse>${classVar}Form').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					$('#${classVar}Form').submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "<#noparse>${ctx}</#noparse>/${system}/${package}/${classVar}/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
	</script>
	</#if>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
		    	<#if defKey?exists>
		    	<c:when test="<#noparse>${</#noparse>not empty taskId}">
		    	<span class="tbar-label">办理任务</span>
		    	</c:when>
		    	<c:otherwise>
		    	<c:choose>
		    	</#if>
			    <c:when test="<#noparse>${not empty </#noparse>${classVar}Item.id}">
			        <span class="tbar-label">编辑${table.tableDesc}</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加${table.tableDesc}</span>
			    </c:otherwise>	
			    <#if defKey?exists>
			    </c:choose>
			    </c:otherwise>
			    </#if>		   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
			<#if defKey?exists>
				<c:if test="<#noparse>${</#noparse>empty ${classVar}Item.flowRunId}">
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link run" >启动流程</a></div>
				</c:if>
				<c:choose>
					<c:when test="<#noparse>${</#noparse>not empty taskId}">
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link agree" onclick="completeTask(1)" >办理</a></div>
					<c:if test="<#noparse>${</#noparse>isAllowBack}">
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnReject" class="link reject"  onclick="completeTask(3)" >驳回</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnRejectToStart" class="link rejectToStart" onclick="completeTask(4)">驳回到发起人</a></div>
					</c:if>
					<c:if test="<#noparse>${</#noparse>isSignTask}">
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnNotAgree" class="link notAgree" onclick="completeTask(2)" >反对</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnAbandon" class="link abandon" onclick="completeTask(0)" >弃权</a></div>
					</c:if>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link setting" onclick="showTaskUserDlg()">流程执行示意图</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link search" onclick="showTaskOpinions()">审批历史</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link print" onclick="window.print();">打印</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link close" onclick="window.close()" >关闭</a></div>
					</c:when>
					<c:otherwise>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="<#noparse>${</#noparse>returnUrl}" >返回</a></div>
					</c:otherwise>
				</c:choose>
			<#else>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</#if>
			</div>
		</div>
	</div>
	<div class="panel-body" type="custform">
		<form id="${classVar}Form" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<#list fieldList as field>
					<#if field.valueFrom != 2 && field.isHidden == 0>
				<tr>
				<th width="20%">${field.fieldDesc}: <#if (field.isRequired==1) > <span class="required">*</span></#if></th>
				<td>
					<@input field=field table=table />
				</td>
				</tr>
					<#else>
					<input type="hidden" name="${field.fieldName}" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"/>
					</#if>
				</#list>
			</table>
			<#if subtables?size!=0>
			<#list subtables as subtable>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" type="sub" formType="page" id="${subtable.variable.classVar}">
				<tr>
					<td colspan="${subtable.fieldList?size-1}">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加${subtable.tableDesc}</a>
			    		</div>
		    		</td>
				</tr>
				<tr>
					<#list subtable.fieldList as field>
					<#if field.valueFrom!=2 && field.isHidden==0>
					<th>${field.fieldDesc}</th>
					</#if>
					</#list>
					<th>操作</th>
				</tr>
				<c:forEach items="<#noparse>${</#noparse>${subtable.variable.classVar}List}" var="${subtable.variable.classVar}Item" varStatus="i">
				    <tr type="subdata">
				        <#list subtable.fieldList as field>	
				        	<#if field.valueFrom != 2 && field.isHidden == 0>
						<td>
								<@input field=field table=subtable />
						</td>
						    <#else>
						    <input type="hidden" name="${subtable.variable.classVar}List[<#noparse>${i.index}</#noparse>]${field.fieldName}" value="<#noparse>${</#noparse>${subtable.variable.classVar}Item.${field.fieldName}}"/>
							</#if>											
					    </#list>
					    <input type="hidden" name="${subtable.variable.classVar}List[<#noparse>${i.index}</#noparse>].id" value="<#noparse>${</#noparse>${subtable.variable.classVar}Item.id}" />
					    <td style="text-align: center"><a href="#" class="link del">删除</a></td>
				    </tr>
				</c:forEach>
				<tr type="append">
			        <#list subtable.fieldList as field>												
				    <#if field.valueFrom != 2 && field.isHidden == 0>
					<td>
						<@input field=field table=subtable />
					</td>
					<#else>
					<input type="hidden" name="${field.fieldName}" value="<#noparse>${</#noparse>${subtable.variable.classVar}Item.${field.fieldName}}"/>
					</#if>	
				    </#list>
				    <td style="text-align: center"><a href="#" class="link del">删除</a></td>
			    </tr>
		    </table>
			</#list>
			</#if>
			<input type="hidden" name="id" value="<#noparse>${</#noparse>${classVar}Item.id<#noparse>}</#noparse>" />					
			<#if defKey?exists>
			<input type="hidden" name="flowRunId" value="<#noparse>${</#noparse>${classVar}Item.flowRunId}" />	
			</#if>
		</form>
	</div>
</div>
</body>
</html>
