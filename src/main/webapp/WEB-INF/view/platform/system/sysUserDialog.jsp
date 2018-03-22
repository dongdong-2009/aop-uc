<%@page pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>选择用户 </title>
	<%@include file="/commons/include/form.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/tree/v35/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" 	src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	<script type="text/javascript">
		var isSingle=${isSingle};
		var rol_Tree=null; 
		var org_Tree=null; 
		var pos_Tree=null; 
		var onl_Tree=null; 
		var accordion = null;
		//树展开层数
		var expandDepth = 1; 
		$(function(){
			//布局
			$("#defLayout").ligerLayout({
				 leftWidth: 200,
				 rightWidth: 140,
				 bottomHeight :40,
				 height: '100%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 onHeightChanged: heightChanged,
				 minLeftWidth:200,
				 allowLeftResize:false
			});
			
			var height = $(".l-layout-center").height();
			$("#leftMemu").ligerAccordion({ height: height-28, speed: null });
		    accordion = $("#leftMemu").ligerGetAccordionManager();
		    
			//load_Rol_Tree();
		    load_Org_Tree();
		    //load_Pos_Tree();
		    //load_Onl_Tree();
		    
		    heightChanged();
		    
		 //   handleSelects();
		});
		function heightChanged(options){
			$(".l-layout-right").css({width: "278px", top: "0px", height: "579px",left: "821px"});
			$(".l-layout-center").css({width: "630px",top: "0px",left: "205px",height: "579px"});
			$(".l-layout-bottom").css({width: "103%"});
			//$(".panel-search").css({width: "100%"});
			if(options){   
			    if (accordion && options.middleHeight - 28 > 0){
			    	$("#SEARCH_BY_ROL").height(options.middleHeight - 183);
				  //  $("#SEARCH_BY_ORG").height(options.middleHeight - 163);
				    $("#SEARCH_BY_POS").height(options.middleHeight - 140);
				    $("#SEARCH_BY_ONL").height(options.middleHeight -163);
			        accordion.setHeight(options.middleHeight - 28);
			    }
			}else{
			    var height = $(".l-layout-center").height();
				$("#SEARCH_BY_ROL").height(height - 183);
			  //  $("#SEARCH_BY_ORG").height(height - 163);
			    $("#SEARCH_BY_POS").height(height - 140);
			    $("#SEARCH_BY_ONL").height(height - 163);
		    }
		}
		
		function setCenterTitle(title){
			
			$("#centerTitle").empty();
			$("#centerTitle").append(title);
			
		};
		
		/**
		function load_Pos_Tree(){
			var setting = {
					data: {
						key : {
							
							name: "posName",
							title: "posName"
						},
					
						simpleData: {
							enable: true,
							idKey: "posId",
							pIdKey: "parentId",
							rootPId: -1
						}
					},
					async: {
						enable: true,
						url:"${ctx}/platform/system/position/getChildTreeData.ht",
						autoParam:["posId","parentId"]
					},
					callback:{
						onClick: function(event, treeId, treeNode){
									var url="${ctx}/platform/system/sysUser/selector.ht";
									var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_POS%>&nodePath="+treeNode.nodePath;
									$("#userListFrame").attr("src",url+p);
									setCenterTitle("按岗位查找:"+treeNode.posName);
								},
						onAsyncSuccess: zTreeOnAsyncSuccess
					}
			};
			pos_Tree = $.fn.zTree.init($("#SEARCH_BY_POS"), setting);
		};
		**/
		
		//判断是否为子结点,以改变图标	
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if(treeNode){
		  	 var children=treeNode.children;
			  	 if(children.length==0){
			  		treeNode.isParent=true;
			  		pos_Tree = $.fn.zTree.getZTreeObj("SEARCH_BY_POS");
			  		pos_Tree.updateNode(treeNode);
			  	 }
			}
		};
		
		function load_Org_Tree(){
			var value=$("#dem").val();
			var setting = {
					data: {
						key : {
							
							name: "orgName",
							title: "orgName"
						},
					
						simpleData: {
							enable: true,
							idKey: "orgId",
							pIdKey: "orgSupId",
							rootPId: -1
						}
					},
					async: {
						enable: true,
						url:"${ctx}/platform/system/sysOrg/getTreeDataByCompany.ht?demId="+value,
						autoParam:["orgId","orgSupId"]
					},
					callback:{
						onClick: function(event, treeId, treeNode){
							var url="${ctx}/platform/system/sysUser/selector.ht";
							var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ORG%>&path="+treeNode.path;
							$("#userListFrame").attr("src",url+p);
							setCenterTitle("按组织查找:"+treeNode.orgName);
						},
						onAsyncSuccess: orgTreeOnAsyncSuccess
					}
					
				};
				org_Tree=$.fn.zTree.init($("#SEARCH_BY_ORG"), setting);
		};
		//判断是否为子结点,以改变图标	
		function orgTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if(treeNode){
		  	 	var children=treeNode.children;
			  	 if(children.length==0){
			  		treeNode.isParent=true;
			  		org_Tree = $.fn.zTree.getZTreeObj("SEARCH_BY_ORG");
			  		org_Tree.updateNode(treeNode);
			  	 }
			}
		};
		function load_Rol_Tree(){
			var systemId=$("#systemId").val();
			var roleName=$("#Q_roleName_SL").val();
			var setting = {
		    		data: {
						key : {
							name: "roleName",
							title: "roleName"
						},
						simpleData: {
							enable: true,
							idKey: "roleId",
							rootPId: -1
						}
					},
		    		callback: {
						onClick: function(event, treeId, treeNode){
						var url="${ctx}/platform/system/sysUser/selector.ht";
						var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ROL%>&roleId=" + treeNode.roleId;
							$("#userListFrame").attr("src", url + p);
							setCenterTitle("按角色查找:" + treeNode.roleName);
						}
					}
				};
				var url="${ctx}/platform/system/sysRole/getAll.ht";
				var para= {systemId : systemId,Q_roleName_SL : roleName};
				$.post(url,para,function(result){
					rol_Tree = $.fn.zTree.init($("#SEARCH_BY_ROL"), setting,result);
					if(expandDepth!=0)
					{
						rol_Tree.expandAll(false);
						var nodes = rol_Tree.getNodesByFilter(function(node){
							return (node.level < expandDepth);
						});
						if(nodes.length>0){
							for(var i=0;i<nodes.length;i++){
								rol_Tree.expandNode(nodes[i],true,false);
							}
						}
					}else rol_Tree.expandAll(true);
				});
			};

			
			function load_Onl_Tree(){
				var value=$("#onl").val();
				var setting = {
			    		data: {
							key : {
								name: "orgName",
								title: "orgName"
							},
							simpleData: {
								enable: true,
								idKey: "orgId",
								pIdKey : "orgSupId",
								rootPId: -1
							}
						},
			    		callback: {
							onClick: function(event, treeId, treeNode){
								var url="${ctx}/platform/system/sysUser/selector.ht";
								var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ONL%>&path="+treeNode.path;
								$("#userListFrame").attr("src",url+p);
								setCenterTitle("按组织查找:"+treeNode.orgName);
							}
						}
			    };
				
				var url= "${ctx}/platform/system/sysOrg/getTreeOnlineData.ht";
				var para="demId=" + value;
				$.post(url,para,function(result){
					org_Tree = $.fn.zTree.init($("#SEARCH_BY_ONL"), setting,result);
					if(expandDepth!=0)
					{
						org_Tree.expandAll(false);
						var nodes = org_Tree.getNodesByFilter(function(node){
							return (node.level < expandDepth);
						});
						if(nodes.length>0){
							for(var i=0;i<nodes.length;i++){
								org_Tree.expandNode(nodes[i],true,false);
							}
						}
					}else org_Tree.expandAll(true);
				});
				
			};
				
				
			function dellAll() {
				$("#sysUserList").empty();
			};
			function del(obj) {
				var tr = $(obj).parents("tr");
				$(tr).remove();
			};
			
			function add(data) {
				
				var aryTmp=data.split("#");
				var userId=aryTmp[0];
				var len= $("#user_" + userId).length;
				if(len>0) return;
				
				var aryData=['<tr id="user_'+userId+'">',
					'<td>',
					'<input type="hidden" class="pk" name="userData" value="'+data +'"> ',
					aryTmp[1],
					'</td>',
					'<td><a onclick="javascript:del(this);" class="link del" >删除</a> </td>',
					'</tr>'];
				$("#sysUserList").append(aryData.join(""));
			};
		
			function selectMulti(obj) {
				if ($(obj).attr("checked") == "checked"){
					var data = $(obj).val();
					add(data);
				}	
			};
		
			function selectAll(obj) {
				var state = $(obj).attr("checked");
				var rtn=state == undefined?false:true;
				checkAll(rtn);
			};
		
			function checkAll(checked) {
				$("#userListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
					$(this).attr("checked", checked);
					if (checked) {
						var data = $(this).val();
						add(data);
					}
				});
			};
		
			function closeWin1(){
				//当你在iframe页面关闭自身时
				//var index = parent.layer.getFrameIndex("layui-layer-iframe1"); //先得到当前iframe层的索引
				layer.close(layer.index); //它获取的始终是最新弹出的某个层，值是由layer内部动态递增计算的
				//parent.layer.closeAll('iframe'); //关闭所有的iframe层
				//parent.layer.close(index); //再执行关闭   
				//window.location.href="${ctx}/tenant/audit.ht";
		        window.parent.location.reload();
			}
			function selectUser(){
				var chIds;
				if(isSingle==true){
					chIds = $('#userListFrame').contents().find(":input[name='userData'][checked]");
				
				}else{
					chIds = $("#sysUserList").find(":input[name='userData']");
				}
				
				var aryuserIds=new Array();
				var aryfullnames=new Array();
				var aryemails=new Array();
				var arymobiles=new Array();
				
				$.each(chIds,function(i,ch){
					var aryTmp=$(ch).val().split("#");
					aryuserIds.push(aryTmp[0]);
					aryfullnames.push(aryTmp[1]);
					aryemails.push(aryTmp[2]);
					arymobiles.push(aryTmp[3]);
					
				});
				
				/* 
				 */
				var roleid = ${roleId};
				var obj={userIds:aryuserIds.join(","),fullnames:aryfullnames.join(","),
						emails:aryemails.join(","),mobiles:arymobiles.join(",")};
				
				//window.returnValue=obj;
				
				//window.close();
				//询问框
				if(aryuserIds.length == 0){
				/* 	layer.msg('选择用户', {
					    time: 20000, //20s后自动关闭
					    btn: ['明白了', '知道了']
					  }); */
					layer.msg('选择用户', {icon: 2}, function(){
						 return;
					});
					  
				}else{
					$.ajax({
						type : 'post',
						dataType : 'json',
						async : false,
						data:obj,
						url : "${ctx}/platform/system/userRole/add.ht?roleId="+${roleId},
				        complete: function (XMLHttpRequest, textStatus) {
							    this; // 调用本次AJAX请求时传递的options参数
							 // console.info(this);
							    if(XMLHttpRequest.status == 200&&XMLHttpRequest.statusText == "OK" ){
							    	 //console.info(XMLHttpRequest);
										layer.confirm('保存成功！是否继续选择？', {
											  btn: ['是','否'] //按钮
											}, function(){
												layer.close(layer.index); //它获取的始终是最新弹出的某个层，值是由layer内部动态递增计算的
												window.location.reload();
											}, function(){
												window.parent.location.reload();
												 //window.location.href='${ctx}/platform/system/userRole/edit.ht';
											     //closeWin1();
											});
										
									}else{
										layer.alert("保存失败！");
									}
							    }
				         });
					
			
				/* layer.alert("用户选择完成",function(){
					//var index = parent.layer.getFrameIndex("layui-layer-iframe1"); //先得到当前iframe层的索引
					layer.close(layer.index); //它获取的始终是最新弹出的某个层，值是由layer内部动态递增计算的
					//parent.closeWin();	
				}); */
			}
			}
			/* var handleSelects=function(){
				var    selectUsers    =    window.dialogArguments ;
				if(selectUsers.selectUserIds && selectUsers.selectUserNames){
					var ids=selectUsers.selectUserIds.split(","); 
					var names=selectUsers.selectUserNames.split(","); 
					for(var i=0;i<ids.length;i++){
						add(ids[i]+"#"+names[i]+"##");
					}
				}
			}		 */
	</script>

<style type="text/css">
.ztree {
	overflow: auto;
}

.label {
	color: #6F8DC6;
	text-align: right;
	padding-right: 6px;
	padding-left: 0px;
	font-weight: bold;
}
html { overflow-x: hidden; }
</style>
</head>
<body>
	<div id="defLayout">
		<div id="leftMemu" position="left" title="查询条件" style="overflow: auto; float: left;width: 100%;" height="100%" >
			<div height="100%" title="按组织查找" style="overflow: hidden;">
				<table   border="0" width="100%" class="table-detail">
					<tr >
						<td width="30%" nowrap="nowrap"><span class="label">维度:</span>
						</td>
						<td style="width:70%;">
							<select id="dem" name="dem" onchange="load_Org_Tree()">
								<c:forEach var="demen" items="${demensionList}">
									<option  value="${demen.demId}" <c:if test="${demen.demId==1}">selected</c:if>>${ demen.demName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<div id="SEARCH_BY_ORG" class='ztree' style="height: 495px;"></div>
			</div>
			
			<!-- 
			<div title="按岗位查找" style="overflow: hidden;">
				<div id="SEARCH_BY_POS" class='ztree'></div>
			</div>

			<div title="按角色查找" style="overflow: hidden;">
				<div class="tree-title" style="width: 100%;">
					<div class="panel-detail">
						<table border="0" width="100%" class="table-detail">
							<tr>
								<td width="30%" nowrap="nowrap">
									<span class="label">系统:</span>
								</td>
								<td colspan="2">
									<select id="systemId" name="systemId" onchange="load_Rol_Tree()">
										<c:forEach var="sys" items="${subSystemList}">
											<option value="${sys.systemId}">${ sys.sysName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<span class="label">角色:</span>
								</td>
								<td>
									<input id="Q_roleName_SL" name="Q_roleName_SL" type="text" size="10">
								</td>
								<td>
									<a class="link detail" href="javascript:load_Rol_Tree();">&ensp;</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="SEARCH_BY_ROL" class='ztree'></div>
			</div>

			<div title="在线用户" style="overflow: hidden;">
				
				<table border="0" width="100%" class="table-detail">
					<tr >
						<td width="30%" nowrap="nowrap"><span class="label">维度:</span>
						</td>
						<td style="width:70%;">
							<select id="onl" name="onl" onchange="load_Onl_Tree()">
								<c:forEach var="demen" items="${demensionList}">
									<option  value="${demen.demId}" <c:if test="${demen.demId==1}">selected</c:if>>${ demen.demName}</option>
								</c:forEach>
							</select>								
						</td>							
					</tr>
				</table>
				
				<div id="SEARCH_BY_ONL" class='ztree'></div>
			</div>
 			-->
		</div>
		<div position="center">
			<div id="centerTitle" class="l-layout-header">全部用户</div>
			<iframe id="userListFrame" name="userListFrame" height="90%" width="102%" frameborder="0" src="${ctx}/platform/system/sysUser/selector.ht?isSingle=${isSingle}"></iframe>
		</div>
		<c:if test="${isSingle==false}">
			<div position="right"width="100%"title="<a onclick='javascript:dellAll();' class='link del'>清空 </a>"
				style="overflow: auto; float: right;width: 100%;">
				<table width="100%" id="sysUserList" class="table-grid table-list" 	id="0" cellpadding="1" cellspacing="1">
				</table>
			</div>
		</c:if>
		<div position="bottom"  class="bottom" >
			<a href="#" class="button"  onclick="selectUser()" ><span class="icon ok"></span><span >选择</span></a>
			<a href="#" class="button" style="margin-left:10px;"  onclick="closeWin1()"><span class="icon cancel"></span><span >取消</span></a>
		</div>
	</div>
</body>
</html>


