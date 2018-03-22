<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<html>
<head>
	<title>编辑企业信息</title>
	<%@include file="/commons/include/form.jsp" %>
    <link rel="stylesheet" href="${ctx}/zTree_v3/css/demo.css" type="text/css">  
    <link rel="stylesheet" href="${ctx}/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script> 
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script> 
    <script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.exedit-3.5.min.js"></script>  
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
<script type="text/javascript">
var rolTree;    //角色树var rolTree;    //角色树
var expandDepth =2; 
var	manager;
	var frm=null;
$(function(){
	frm=$('#userForm').form();
	var options={};
	 if(showResponse){
		options.success=showResponse;
	 }
	$("a.save").click(function(){
        firstClick(frm,options);
    });
	 $("#rolAdd").click(function(){
			btnAddRow('rolTree');
		});
		$("#rolDel").click(function(){
			btnDelRow();
		});
	loadrolTree();
})

function firstClick(frm,options){
	
	var rolItem = $("#rolItem tbody tr").html();
	if(!rolItem){
		$.ligerMessageBox.error("提示信息","请给用户选择一个角色");
		return;
	}
 
          
          try{ 
        	  	//manager= $.ligerDialog.waitting('正在保存中,请稍候...');
		        /* frm.setData();
		        frm.ajaxForm(options);
		       // $("#tijiao")[0].click()
		       
		       frm.submit(); */
        	  	$.ajax({           
       		     cache: false,
       		     type: "POST", 
       		     url:"${ctx}/tenant/saveNewRole.ht",
       		     data : frm.serialize(),
       		     dataType : 'json',
       		     async: false,
       		     error: function(request) {
       		    	 $.ligerMessageBox.warn("提示信息","网络异常，请联系管理人员");
       		     },  
       		      success: function(data) { 
       		    	 // alert(JSON.stringify(data));
       			    	 if (data.result==1) {
       							$.ligerMessageBox.confirm('提示信息',data.message+',是否继续?',function(rtn){
       								if(rtn){
       									//rtn = true
       									window.location.reload();//刷新页面
       								}else{
       									window.parent.closeWin();
       								}
       							});	      							
       						} else {
       							$.ligerMessageBox.error("提示信息",data.message);
       			       }
       		   }
       		});     	  	
		       
          }catch(e){
				$.ligerMessageBox.error("提示信息",e.message);
    			return;
			}
    	
		
	
}





 //生成角色树      		
	  function loadrolTree() {
		var setting = {       				    					
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
				selectedMulti: true,
				expandSpeed: "fast"
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "p", "N": "s" }
			},
			callback:{onCheck:roleOnCheck,
				onDblClick: rolTreeOnDblClick}
		 	
		   };
		   
			//var url="${ctx}/tenant/getData.ht";
			var systemId = $("#systemId").val();
			var orgopenId = $("#orgopenId").val();
			//展示所有权限
			///var url="${ctx}/tenant/getTreeByTenant.ht?systemId="+systemId+"&orgopenId="+orgopenId;
			//仅展示对应管理员权限
			var url="${ctx}/tenant/getTreeByTenantByOrgId.ht?systemId="+systemId+"&orgopenId="+orgopenId;
			$.post(url,function(result){
				posTree=$.fn.zTree.init($("#rolTree"), setting,result);
				if(expandDepth!=0)
				{
					posTree.expandAll(false);
					var nodes = posTree.getNodesByFilter(function(node){
						return (node.level < expandDepth);
					});
					if(nodes.length>0){
						for(var i=0;i<nodes.length;i++){
							posTree.expandNode(nodes[i],true,false);
						}
					}
				}else posTree.expandAll(true);
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
					 // roleId=n.roleId+"_"+n.roleType;
					  roleId=n.roleId;
					  var roleName=n.roleName;
					  var systemName = n.systemName;
					  /* if (n.subSystem==null ||n.subSysten=="")
					  {
	    		       sysName=" ";
	    		       } 
					  else{
					  sysName=n.subSystem.sysName;
					  }*/
					  
					  var  systemId=n.systemId;
					  rolAddHtml(roleId,roleName,systemId,systemName);
				  }
	    	 });
	    }
   }
	
	//角色树左键双击
	 function rolTreeOnDblClick(event, treeId, treeNode){   
		 var roleId=treeNode.roleId;
		 if(roleId < 0) {
			 return false;
		 }
		 
		 var roleName=treeNode.roleName;
		 var sysName=treeNode.systemName;
		 var systemId=treeNode.systemId;
		 if(treeNode.subSystem!=null&&treeNode.subSystem!=""){
			 sysName=treeNode.subSystem.sysName;
			 /* sysName=treeNode.systemName;
			 systemId=treeNode.systemId; */
		 }
		 //var systemId=treeNode.systemId;
		 
		
		 
		 
		 
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
		 tr+="</td>";
		 tr+="<td style='text-align: center;'>";
		 tr+="<a href='#' onclick='delrow(\""+roleId+"\")' class='link del'>移除</a>";
		 tr+="</td>";
		 tr+="</tr>";
	   
		 $("#rolItem").append(tr);
		
	 }	
	 
	 
	 
	//当选中时触发的函数 角色树
	   function roleOnCheck(e,treeId,treeNode){
       var a;
       if(treeNode.checked){
    	   a=true;
    	   treeNode.checked=a;
		   }else{
			   a=false;
			   treeNode.checked=a;
		   }
       
  
      
    	   getRoleChildren(treeNode,a);
         
     
       }

	   function getRoleChildrenNodes(node,a) {
		   var zTree=$.fn.zTree.getZTreeObj('rolTree');
		   if(node.checked){
			   node.checked=a;
		   }else{
			   node.checked=a;
		   }
		   zTree.updateNode(node);  
	   }
		
	  
	
	 function getRoleChildren(treeNode,a){
	 // ids.push(treeNode.id);
	 if (treeNode.isParent){
	  getRoleChildrenNodes(treeNode,a);
		 for(var obj in treeNode.children)
		 {
		  getRoleChildrenNodes(treeNode.children[obj],a);
		  getRoleChildren(treeNode.children[obj],a);
     }
	   }
	
	 }
	 
	 
	 function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			manager.close();
			if (obj.isSuccess()) {
				$.ligerMessageBox.success("提示信息", "用户信息保存成功",function(){
					this.close();
					window.parent.closeWin();
				});
			} else {
				$.ligerMessageBox.error("提示信息","保存失败");
			}
		}
	 
var fanhui=function(){
	
	window.parent.closeWin();
}	 
	
</script>
<style type="text/css">
	html{height:100%}
	body {overflow:auto;}
a.link.save:hover {
    transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1) 0s;
    background-color: #f5f5f5;
    color: blue;
    border-color: #e33;

}
a.link.back:hover {
    transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1) 0s;
    background-color: #f5f5f5;
    color: blue;
    border-color: #e33;

}
a.link.del:hover {
    transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1) 0s;
    background-color: #f5f5f5;
    color: blue;
    border-color: #e33;

}
</style>
</head>
<body>
<div class="panel">
		<div class="panel-top">
		
		<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="javascript:fanhui();">关闭</a></div>
					<div class="l-bar-separator"></div>
				</div>
			</div>
	</div>
 <form id="userForm" method="post" action="${ctx}/tenant/saveNewRole.ht" >
 <input type="hidden" name="userId" value="${userId}">
  <input type="hidden" name="systemId" value="${systemId}" id="systemId">
   <input type="hidden" name="openId" value="${openId}" id="openId">
      <input type="hidden" name="orgopenId" value="${orgopenId}" id="orgopenId">
      <input type="hidden" name="flag" value="${flag}">
 <div tabid="roldetail" icon="${ctx}/styles/default/images/resicon/customer.png">
			        <table align="center"  cellpadding="0" cellspacing="0" class="table-grid">
					   <tr>
				       <td width="28%" valign="top" style="padding-left:2px !important;">
				        <div class="tbar-title">
							 <span class="tbar-label">所有角色</span>
						</div>	
						<div class="panel-body" style="height:500px;overflow:scroll;border:1px solid #6F8DC6;">	
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
						<div style="overflow:scroll;border:1px solid #6F8DC6; height:500px;">
						  <table id="rolItem" class="table-grid"  cellpadding="1" cellspacing="1">
						   		<thead>					   			
						   			<th style="text-align:center !important;">角色名称</th>
						    		<th style="text-align:center !important;">子系统名称</th>
						    		<th style="text-align:center !important;">操作</th>
						    	</thead>
						    	<c:forEach items="${roleList}" var="rolItem">
						    			<tr trName="${rolItem.roleName}" id="${rolItem.roleId}_${rolItem.fullname}" style="cursor:pointer">
							    		<td style="text-align: center;">
						    				${rolItem.roleName}
						    				<input type="hidden" name="roleId" value="${rolItem.roleId}_${rolItem.fullname}">
						    			</td>
						    			<td style="text-align: center;">
						    			    ${rolItem.systemName}
						    			</td>
						    			<td style="text-align: center;">
						    			 <a href="javascript:void(0);" onclick="delrow('${rolItem.roleId}_${rolItem.fullname}')"   title='刪除角色' class="link del"><!--  移除 --></a>
						    			</td>
						    			</tr>
						    		
						    	</c:forEach>
						   	 </table>
						</div>
			            </td>
			            </tr>
					 </table>
					
		</div>	
	  </form>
			<div style="height:140px; line-height:0px; font-size:0px; clear:both;overflow:hidden; display:block;"></div>
</div>
</body>
</html>