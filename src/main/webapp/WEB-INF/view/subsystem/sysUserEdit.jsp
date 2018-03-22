<%--
	time:2011-11-28 10:17:09
	desc:edit the 用户表
--%>
<%@page language="java" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 用户表</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysUser"></script>
	<link rel="stylesheet" href="${ctx}/js/tree/v35/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.exedit-3.5.min.js"></script> 
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js" ></script>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerWindow.js" ></script>
   <script type="text/javascript"  src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
   <script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
   <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.js"></script>
   <%-- <script type="text/javascript" src="${ctx}/js/layer/layer.js"></script> --%>
  <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script> 
  
  
  <!-- 上传图片 -->
<script type="text/javascript" src="${ctx}/js/upload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/upload/cloudDialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/upload/uploadPreview.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>	
  
	<script type="text/javascript">
	var orgTree;    //组织树
	var posTree;    //岗位树
	var rolTree;    //角色树
	var h;
	var expandDepth =2; 
	var action="${action}";
	var message1="${message1}";
	var returnUrl="${returnUrl}";
	
	var	manager;
	var frm=null;
    $(function ()
    { 
    	
    	var pic=$("#picture").val();
		if(pic!=null&&pic!=''){
			$("#personPic").attr("src","${fileCtx}/"+pic);
		}
    	h=$('body').height();
    	$("#tabMyInfo").ligerTab({         	
            	//height:h-80
          });
    	function showRequest(formData, jqForm, options)
    	{ 
			return true;
		} 					
    	 if(${sysUser.userId==null}){
			valid(showRequest,showResponse,1);
		}else{
			valid(showRequest,showResponse);
		} 
    	frm=$('#sysUserForm');
		$("a.save").click(function() {
		/* 	if($("#account").val()==null||$("#account").val()==''){
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
			} */
			///console.info($("input [name='roleId']"));
		/* 	if($("input [name='roleId']")==null){
				layer.alert("角色不能为空！");
				return false;
			}  */
			firstClick(frm);
			
			//$('#sysUserForm').submit(); 
		});
		
		function firstClick(frm){
	          try{ 
	        	  	$.ajax({           
	        		     cache: false,
	        		     type: "POST", 
	        		     url:"${ctx}/platform/subSystem/sysUser/userRoleEdit.ht",
	        		     data : frm.serialize(),
	        		     dataType : 'json',
	        		     async: false,
	        		     error: function(request) {
	        		    	if(request.status=="500"){
	        		    		   layer.open({
	        		    			  type: 1, 
	        		    			  area: ['600px', '400px'],
	        		    			  content: request.responseText //这里content是一个普通的String
	        		    			});
	        		    	}else{
	        		    		 $.ligerMessageBox.warn("提示信息","网络异常，请联系管理人员");
	        		    	};
	        		     },  
	        		      success: function(data) { 
	        		    	 // alert(data.result+data.message);
	        			    	 if (data.result==1) {
	        							$.ligerMessageBox.confirm('提示信息',data.message+',是否继续?',function(rtn){
	        								if(rtn){
	        									window.close();
	        								
	        									location.reload();
	        								}else{
	        									this.close();
	        									window.location.replace("${ctx}/platform/subSystem/sysUser/list.ht")//返回并重新加载
	        									//window.location.href="${ctx}/platform/subSystem/sysUser/list.ht";
	        								}
	        							});	      							
	        						} else {
	        							$.ligerMessageBox.error("提示信息",data.message);
	        			       }
	        		   }
	        		});     	  	
	          }catch(e){
					$.ligerMessageBox.warn("提示信息",e.message);
	    			return;
				}
	}

		
	/* 	
		$.post(url,data,function(result){
			var obj=new com.hotent.form.ResultMessage(result);
			if(obj.isSuccess()){
				$.ligerMessageBox.confirm('提示信息','角色资源分配成功,是否继续?',function(rtn){
					if(!rtn){
						window.close();
					}
				});
			}else{
				$.ligerMessageBox.warn('角色资源分配出错!');
			}
		})
		 */
		
		
		
		//*****************************
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
		
		
		
		
//************************
		$("#orgAdd").click(function(){
			btnAddRow('orgTree');
		});
		$("#orgDel").click(function(){
			btnDelRow();
		});
		$("#posAdd").click(function(){
			btnAddRow('posTree');
		});
		$("#posDel").click(function(){
			btnDelRow();
		});
		$("#rolAdd").click(function(){
			btnAddRow('rolTree');
		});
		$("#rolDel").click(function(){
			btnDelRow();
		});
		$("#demensionId").change(function(){
			//loadorgTree();
		});
		$("#treeReFresh").click(function() {
			//loadorgTree();
		});

		$("#treeExpand").click(function() {
			orgTree = $.fn.zTree.getZTreeObj("orgTree");
			var treeNodes = orgTree.transformToArray(orgTree.getNodes());
			for(var i=1;i<treeNodes.length;i++){
				if(treeNodes[i].children){
					orgTree.expandNode(treeNodes[i], true, false, false);
				}
			}
		});
		$("#treeCollapse").click(function() {
			orgTree.expandAll(false);
		});
    	if("grade"==action){
    		loadorgGradeTree();
    	}else{
    		//loadorgTree();
    	}
    	//loadposTree();
    	loadrolTree();
    });
    
	function initPersonPic(path){
		var image = new Image();
		image.src = "${ctx}/" + path;		
		if (image.complete) {
			ifPicOk(image,path);
	         return; 
	    }
		image.onload = function () {
			ifPicOk(image,path);
			return;
       };
       setTimeout(function(){
    	   if(!image.readyState||/^uninitialized$/.test(image.readyState))
    		   $.ligerMessageBox.warn("提示","获取照片超时，您所设置的照片不存在或已被删除!");
       },3000);
	};
	
	function ifPicOk(img,path){
		if(img.width<=0||img.height<=0){
			 $.ligerMessageBox.warn("提示","您所设置的照片未能正常显示!");
			 return;
		}
		if(img.width>400||img.height>480){
			$.ligerMessageBox.warn("提示","您所设置的照片超出了尺寸上限(400×480)!");
			return;
		}
		$("#picture").val(path);
		$("#personPic").attr("src","${ctx}/" + path);
	};
	
	//添加个人照片
	function picCallBack(fileIds,fileNames,filePaths){
		var arrPath;
		if(filePaths.isEmpty()) return;
		
		arrPath=filePaths.split(",");
		if(/\w+.(png|gif|jpg)/gi.test(arrPath[0]))
			initPersonPic(arrPath[0]);
		else
			$.ligerMessageBox.warn("提示","请选择图片类型文件!");
				
	};
	function addPic(){
		FlexUploadDialog({isSingle:true,callback:picCallBack});
	};
	
	/* //生成组织树      		
	function loadorgTree(){
		var demId=$("#demensionId").val();
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
				url:__ctx+"/platform/system/sysOrg/getTreeData.ht?demId="+demId,
				autoParam:["orgId","orgSupId"]
			},
			view: {
				selectedMulti: true
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			callback:{
				onDblClick: orgTreeOnDblClick,
				onAsyncSuccess: orgTreeOnAsyncSuccess
			}
		};
		orgTree=$.fn.zTree.init($("#orgTree"), setting);
	}; */
	//判断是否为子结点,以改变图标	
	function orgTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		if(treeNode){
	  	 	var children=treeNode.children;
		  	 if(children.length==0){
		  		treeNode.isParent=true;
		  		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		  		orgTree.updateNode(treeNode);
		  	 }
		}
	};	
	
	function loadorgGradeTree(){
		var setting = {
				
				data: {
					key : {
						name: "orgName",
						title: "orgName"
					}
				},
				view : {
					selectedMulti : false
				},
				onRightClick: false,
				onClick:false,
				check: {
					enable: true,
					chkboxType: { "Y": "p", "N": "s" }
				},
				callback:{onDblClick: orgTreeOnDblClick}
			};
		
		   var orgUrl=__ctx + "/platform/system/grade/getOrgJsonByUserId.ht";
		   //一次性加载
		   $.post(orgUrl,function(result){
			   var zNodes=eval("(" +result +")");
			   orgTree=$.fn.zTree.init($("#orgTree"), setting,zNodes);
			   if(expandDepth!=0)
				{
					orgTree.expandAll(false);
					var nodes = orgTree.getNodesByFilter(function(node){
						return (node.level < expandDepth);
					});
					if(nodes.length>0){
						for(var i=0;i<nodes.length;i++){
							orgTree.expandNode(nodes[i],true,false);
						}
					}
				}else orgTree.expandAll(true);
		   });		
	};	
    	
/* 	//生成岗位树      		
	  function loadposTree() {
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
						url:__ctx+"/platform/system/position/getChildTreeData.ht",
						autoParam:["posId","parentId"],
						dataFilter: filter
					},
					view: {
						selectedMulti: true
					},
					onRightClick: false,
					onClick:false,
					check: {
						enable: true,
						chkboxType: { "Y": "", "N": "" }
					},
					callback:{
						onDblClick: posTreeOnDblClick,
						onAsyncSuccess: zTreeOnAsyncSuccess
					}
			};
		    posTree = $.fn.zTree.init($("#posTree"), setting);
		
	};	
	 */
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
	
	//过滤节点,默认为父节点,以改变图标	
	function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				if(!childNodes[i].isParent){
					alert(childNodes[i].posName);
					childNodes[i].isParent = true;
				}
			}
			return childNodes;
	};
    
	
	 //生成角色树      		
	  function loadrolTree() {
		var setting = { 
		/* 	edit: {
					enable: true,
					renameTitle: "编辑角色",
					showRenameBtn: true,
					showRemoveBtn: true,
					removeTitle: "删除角色"
				}, */
		     data: {
				key : {
					
					name: "roleName",
					title: "roleName"
				},
			
				simpleData: {
					enable: true,
					idKey: "roleId",
					pIdKey: "systemId",
					rootPId: null
				}
			},
			view: {
				selectedMulti: true
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			callback:{onDblClick: rolTreeOnDblClick}
		 	
		   };
		   
			var url="${ctx}/platform/system/sysRole/getTreeData.ht?tenantId=${orgsn}&fromSysId=${fromSysId}";
			//var url="${ctx}/tenant/getTreeByTenant.ht?systemId="+100;
			$.post(url,function(result){
				//alert(JSON.stringify(result));
				rolTree=$.fn.zTree.init($("#rolTree"), setting,result);
				if(expandDepth!=0)
				{
					rolTree.expandAll(false);
					var nodes = rolTree.getNodesByFilter(function(node){
						return (node.level < expandDepth);
					});
					if(nodes.length>0){
						for(var i=0;i<nodes.length;i++){
							rolTree.expandNode(nodes[i],false,false);
						}
					}
				}else rolTree.expandAll(true);
			});
	};	
	
	
	 function btnDelRow() {
		 var $aryId = $("input[type='checkbox'][class='pk']:checked");
		 var len=$aryId.length;
		 if(len==0){
			 $.ligerMessageBox.warn("你还没选择任何记录!");
			 return;
		 }
		 else{			
			 $aryId.each(function(i){
					var obj=$(this);
					delrow(obj.val());
			 });
		 }
	 }
	 
	 function delrow(id)//删除行,用于删除暂时选择的行
	 {
		 $("#"+id).remove();
	 }

	
	
	
	//树按添加按钮
	function btnAddRow(treeName) {
		var treeObj = $.fn.zTree.getZTreeObj(treeName);
        var nodes = treeObj.getCheckedNodes(true);
        if(nodes==null||nodes=="")
        {
        	 $.ligerMessageBox.warn("你还没选择任何节点!");
			 return;
        }
		if(treeName.indexOf("org")!=-1) {
			var orgId="";
			var orgSupId="";
			var orgName="";	
			var userName="";
	        $.each(nodes,function(i,n){	
	        	orgId=n.orgId;
	        	orgSupId=n.orgSupId;
	        	orgName=n.orgName;
	        	if(n.isRoot==0){
	        		orgAddHtml(orgId,orgSupId,orgName);
	        	}
	        	
			});
	    }
	    else if(treeName.indexOf("pos")!=-1){
	    	 var posId="";
			 var posName="";
			 var posDesc="";
			 var parentId="";
		     $.each(nodes,function(i,n){
		    	  posId=n.posId;
				  posName=n.posName;
				  parentId=n.parentId;
			 	  posAddHtml(posId,posName,parentId);
		     });
	    }
	    else if(treeName.indexOf("rol")!=-1){
	    	 $.each(nodes,function(i,n){
				  var roleId=n.roleId;
				  if(roleId>0){
					  roleId=n.roleId;
					  var roleName=n.roleName;
					  var systemName = n.systemName;
					/*   if (n.subSystem==null ||n.subSysten=="")
					  {
	    		       sysName=" ";
	    		       }
					  else{
					  sysName=n.subSystem.sysName;
					  } */
					  var systemId=n.systemId;
					  rolAddHtml(roleId,roleName,systemId,systemName);
				  }
	    	 });
	    }
    }
	 //组织树左键双击
	 function orgTreeOnDblClick(event, treeId, treeNode)
	 {   
		 var orgId=treeNode.orgId;
		 var orgSupId=treeNode.orgSupId;
		 var orgName=treeNode.orgName;
		 if(treeNode.isRoot==0){
			 orgAddHtml(orgId,orgSupId,orgName);
		 }
	 };
	 
	 
	 function orgAddHtml(orgId,orgSupId,orgName){
		
		 if(orgSupId==-1) return;
		 var obj=$("#" +orgId); 
         if(obj.length>0) return;
         
		 var tr="<tr  id='"+orgId+"' style='cursor:pointer'>";
		 tr+="<td style='text-align: center;'>";
		 tr+=""+orgName+"<input  type='hidden' name='orgIds' value='"+orgId+"'>";
		 tr+="</td>";		
		 
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='radio' name='orgIdPrimary' value='"+orgId+"' />";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='checkbox' name='chargeOrgId' value='"+orgId+"' />";
		 tr+="</td>";
	
		 
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='#' onclick='delrow(\""+orgId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 
		 tr+="</tr>";
	     
		 $("#orgItem").append(tr);
		 
	 }
	 
	 
	//岗位树左键双击
	 function posTreeOnDblClick(event, treeId, treeNode){   
		 var posId=treeNode.posId;
		 var posName=treeNode.posName;
		 var posDesc=treeNode.posDesc;
		 var parentId=treeNode.parentId;
		 posAddHtml(posId,posName,parentId);
		 
	 };
	 
	 function posAddHtml(posId,posName,parentId){
		 if(parentId==-1) return;
		 var obj=$("#" +posId);
		 if(obj.length>0) return;
		 var tr="<tr  id='"+posId+"' style='cursor:pointer'>";
		 tr+="<td style='text-align: center;'>";
		 tr+=posName +"<input type='hidden' name='posId' value='"+posId+"'>";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<input type='radio' name='posIdPrimary' value='"+posId+"' />";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='#' onclick='delrow(\""+posId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 tr+="</tr>";
	    
		 $("#posItem").append(tr);
		 
	 }
	 

	 
	 
	 
	 
	 
	 
	//角色树左键双击
	 function rolTreeOnDblClick(event, treeId, treeNode){   
		 var roleId=treeNode.roleId;
		 var roleName=treeNode.roleName;
		 if(roleId < 0) {
			 return false;
		 }
		 
		 /* var sysName = " ";
		  if(treeNode.subSystem!=null&&treeNode.subSystem!=""){
			 sysName=treeNode.subSystem.sysName;
		 } */
		 var sysName=treeNode.systemName
		 var systemId=treeNode.systemId;
		 rolAddHtml(roleId,roleName,systemId,sysName);
	 };
	 
	 function rolAddHtml(roleId,roleName,systemId,sysName){
		// if( systemId==0) return;
		 var obj=$("#" +roleId);
		 if(obj.length>0) return;
		
		 var tr="<tr id='"+roleId+"' style='cursor:pointer'>";
		 tr+="<td style='text-align: center;'>";
		 tr+=""+roleName+"<input type='hidden' name='roleId' value='"+ roleId +"'>";
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+=""+sysName;
		/*  tr+="<input type='hidden' name='systemId' value='"+systemId+"' />"; */
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='javascript:void(0);' onclick='delrow(\""+roleId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 tr+="</tr>";
	   
		 $("#rolItem").append(tr);
		
	 }	 
	
	 
	 
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
	</script>
<style type="text/css">
	html{height:100%}
	body {overflow:auto;}

</style>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				<c:if test="${sysUser.userId==null }">添加用户信息</c:if>
				<c:if test="${sysUser.userId!=null }">编辑【${sysUser.fullname}】用户信息</c:if>  
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><%-- <a class="link back" href="${returnUrl}">返回</a> --%>
				          <a href="javascript:window.location.href = document.referrer;" class="link back" style="cursor:pointer;color: #336699;margin-top:1px;">返回</a>
					</div>
				</div>
			</div>
		</div>
	   <form id="sysUserForm"  method="post" action="/platform/subSystem/sysUser/userEdit.ht">
						
            <div  id="tabMyInfo" class="panel-nav" style="overflow:hidden; position:relative;">  
	          <%--  <div title="基本信息"  tabid="userdetail" icon="${ctx}/styles/default/images/resicon/user.gif">
			         
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
									<td rowspan="<c:if test="${not empty sysUser.userId}">9</c:if><c:if test="${empty sysUser.userId}">10</c:if>" align="center" width="26%">
									<div style="float:top !important;background: none;height: 24px;padding:0px 6px 0px 112px;"><a class="link uploadPhoto" href="#" onclick="addPic();">上传照片</a></div>
									<div class="person_pic_div">
										<p><img id="personPic" src="${ctx}/${pictureLoad}"  alt="个人相片" /></p>
									</div> 
									</td>
									
									<th width="18%">帐   号: <span class="required">*</span></th>
									<td ><c:if test="${bySelf==1}"><input type="hidden" name="bySelf" value="1"></c:if><input type="text" <c:if test="${bySelf==1}">disabled="disabled"</c:if> id="account"   readonly="readonly"      name="account" value="${sysUser.account}" style="width:240px !important" class="inputText"/></td>
								</tr>						
																				
								<tr style="<c:if test="${not empty sysUser.userId}">display:none</c:if>">
									<th>密   码: <span class="required">*</span></th>
									<td><input type="password" id="password" name="password" value="${sysUser.password}" style="width:240px !important" class="inputText"/></td>
								</tr>
								
								<tr>
								    <th>用户姓名: </th>
									<td ><input type="text" id="fullname" name="fullname" value="${sysUser.fullname}" style="width:240px !important" class="inputText"/></td>
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
								</tr>						
								<tr>
								   <th>邮箱地址: </th>
								   <td ><input type="text" id="email" name="email" value="${sysUser.email}" style="width:240px !important" class="inputText"/></td>
								</tr>
								
								<tr>
									<th>手   机: </th>
									<td ><input type="text" id="mobile" name="mobile" value="${sysUser.mobile}" style="width:240px !important" class="inputText"/></td>						   
								</tr>
								
								<tr>
								    <th>电   话: </th>
									<td ><input type="text" id="phone" name="phone" value="${sysUser.phone}"  style="width:240px !important" class="inputText"/></td>
								</tr>
														
							</table>
							<input type="hidden" name="userId" value="${sysUser.userId}" />
							<input type="hidden" name="orgId" value="${sysUser.orgId}" />
							<input type="hidden" id="picture" name="picture" value="${sysUser.picture}" />
					
	           </div> --%>
	           		        <input type="hidden" name="userId" value="${sysUser.userId}" />
							<input type="hidden" name="orgSn" value="${sysUser.orgSn}" />
							<input type="hidden" id="picture" name="picture" value="${sysUser.picture}" />
	            <c:if test="${bySelf!=1}">
	           <%-- <div title="组织选择" tabid="orgdetail" icon="${ctx}/styles/default/images/icon/home.png">		         	         
		         
			          <table align="center"  cellpadding="0" cellspacing="0" class="table-grid table-list">
					    <tr>
				        <td width="28%" valign="top"  style="padding-left:2px !important;">
						<div class="tbar-title">
								<span class="tbar-label">所有组织</span> 
				        </div>
						<div class="panel-body" style="height:520px;overflow-y:auto;border:1px solid #6F8DC6;">	 
			<div style="width: 100%;">
				<select id="demensionId" style="width: 99.8% !important;">
					<c:forEach var="dem" items="${demensionList}">
						<option value="${dem.demId}" <c:if test="${dem.demId==1}">selected="selected"</c:if> >${dem.demName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="tree-toolbar" id="pToolbar">
				<div class="toolBar"
					style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap">
					<div class="group">
						<a class="link reload" id="treeReFresh">刷新</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link expand" id="treeExpand">展开</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link collapse" id="treeCollapse">收起</a>
					</div>
				</div>
			</div> 		  
				            <ul id="orgTree" class="ztree" style="width:200px;margin:-2; padding:-2;" >         
				            </ul>    
						</div>
						</td>
						<td width="3%" valign="middle"  style="padding-left:2px !important;">
						<input type="button" id="orgAdd" value="添加>>" />
						<br/>
						<br/>
						<br/>
						</td>
					    <td valign="top" style="padding-left:2px !important;">
			           <div class="tbar-title">
								<span class="tbar-label">已选组织</span>
				         </div>
						<div style="overflow-y:auto;border:1px solid #6F8DC6;">
						      <table id="orgItem" class="table-grid table-list"  cellpadding="1" cellspacing="1">
						   		<thead>
						   			
						   			<th style="text-align:center !important;">组织名称</th>
						    		<th style="text-align:center !important;">是否主组织</th>
						    		<th style="text-align:center !important;">主要负责人</th>
						    		
						    		<th style="text-align:center !important;">操作</th>
						    	</thead>
						    	<c:forEach items="${orgList}" var="orgItem">
						    		<tr trName="${orgItem.orgName}"  id="${orgItem.orgId}" style='cursor:pointer'>
							    		
							    		<td style="text-align: center;">
						    				${orgItem.orgName}<input type="hidden" name="orgIds" value="${orgItem.orgId}">					    				
						    			</td>
						    			<td style="text-align: center;">					    			
						    			 <input type="radio" name="orgIdPrimary" value="${orgItem.orgId}" <c:if test='${orgItem.isPrimary==1}'>checked</c:if> />
						    			</td>
						    			<td style="text-align: center;">					    			
						    			 	<input type="checkbox" name="chargeOrgId" value="${orgItem.orgId}"  <c:if test='${orgItem.isCharge==1}'>checked</c:if>> 
						    			</td>
						    			
						    			<td style="text-align: center;">
						    			 <a href="#" onclick="delrow('${orgItem.orgId}')" class="link del">移除</a>
						    			</td>
						    		</tr>
						    	</c:forEach>
						   	 </table>
						</div>
			            </td>
			            </tr>
					 </table>
					
	        </div>
	        <div title="岗位选择" tabid="posdetail" icon="${ctx}/styles/default/images/nav-sales.png">
	        	
			         <table align="center"  cellpadding="0" cellspacing="0" class="table-grid">
					   <tr>
				       <td width="28%" valign="top" style="padding-left:2px !important;">
				        <div class="tbar-title">
								<span class="tbar-label">所有岗位</span>
						</div>				         
						<div class="panel-body" style="height:520px;overflow-y:auto;border:1px solid #6F8DC6;">	 
							<div id="posTree" class="ztree" style="width:200px;margin:-2; padding:-2;" >         
				            </div>
						</div>
						</td>
						<td width="3%" valign="middle"  style="padding-left:2px !important;">
						<input type="button" id="posAdd" value="添加&gt;&gt;" />
						<br/>
						<br/>
						<br/>
						</td>
					    <td valign="top" style="padding-left:2px !important;">
					   <div class="tbar-title">
							<span class="tbar-label">已选岗位</span>
						</div>	
						<div style="overflow-y:auto;border:1px solid #6F8DC6;">
						<table id="posItem" class="table-grid"  cellpadding="1" cellspacing="1">
						   		<thead>					   			
						   			<th style="text-align:center !important;">岗位名称</th>
						   			<th style="text-align:center !important;">是否主岗位</th>
						    		<th style="text-align:center !important;">操作</th>
						    	</thead>
						    	<c:forEach items="${posList}" var="posItem">
						    		<tr  id="${posItem.posId}" style='cursor:pointer'>						    		
							    		<td style="text-align: center;">
						    				${posItem.posName}<input type="hidden" name="posId" value="${posItem.posId}">
						    			</td>
						    			<td style="text-align: center;">					    			
						    			 <input type="radio" name="posIdPrimary" value="${posItem.posId}" <c:if test='${posItem.isPrimary==1}'>checked</c:if> />
						    			</td>
						    			
						    			<td style="text-align: center;">
						    			 <a href="#" onclick="delrow('${posItem.posId}')" class="link del">移除</a>
						    			</td>
						    		</tr>
						    	</c:forEach>
						   	 </table>
						</div>
			            </td>
			            </tr>
					 </table>
				 
	         </div> --%>
	         <div  title="角色选择" tabid="roldetail" icon="${ctx}/styles/default/images/resicon/customer.png">
		       
			        <table align="center"  cellpadding="0" cellspacing="0" class="table-grid">
					   <tr>
				       <td width="28%" valign="top" style="padding-left:2px !important;">
				        <div class="tbar-title">
							 <span class="tbar-label">所有角色</span>
						</div>	
						<div class="panel-body" style="height:540px;overflow:scroll;border:1px solid #6F8DC6;">	
				    	    <div id="rolTree" class="ztree" style="width:200px;margin:-2; padding:-2;" >         
				            </div>
				        </div>
						</td>
						<td width="3%" valign="middle"  style="padding-left:2px !important;">
						<input type="button" id="rolAdd" value="添加>>" />
						<br/>
						<br/>
						<br/>
						</td>
					    <td valign="top" style="padding-left:2px !important;">
					   <div class="tbar-title">
							 <span class="tbar-label">已选角色</span>
						</div>	
						<div style="overflow:scroll;border:1px solid #6F8DC6;height:540px;">
						  <table id="rolItem" class="table-grid"  cellpadding="1" cellspacing="1">
						   		<thead>					   			
						   			<th style="text-align:center !important;">角色名称</th>
						    		<th style="text-align:center !important;">子系统名称</th>
						    		<th style="text-align:center !important;">操作</th>
						    	</thead>
						    	<c:forEach items="${roleList}" var="rolItem">
						    		<tr trName="${rolItem.roleName}" id="${rolItem.roleId}" style="cursor:pointer">
							    		<td style="text-align: center;">
						    				${rolItem.roleName}<input type="hidden" name="roleId" value="${rolItem.roleId}">
						    			</td>
						    			<td style="text-align: center;">
						    			    ${rolItem.systemName}
						    			</td>
						    			<td style="text-align: center;">
						    			 <a href="javascript:void(0);" onclick="delrow('${rolItem.roleId}')"  title='刪除角色'  class="link del"><!-- 移除 --></a>
						    			</td>
						    		</tr>
						    	<%-- </c:forEach>
						    		<c:forEach items="${systemIdList}" var="subSystem">
						    		<tr id="${subSystem.systemId}" style="cursor:pointer">
							    		<td style="text-align: center;">
						    				<input type="text" name="systemId" value="${subSystem.systemId}">
						    			</td>
						    	    </tr>--%>
						    	</c:forEach> 
						   	 </table>
						</div>
			            </td>
			            </tr>
					 </table>
			</div>								
	      	</c:if>
	      </div>      
	  </form>
</div>
</body>
</html>
