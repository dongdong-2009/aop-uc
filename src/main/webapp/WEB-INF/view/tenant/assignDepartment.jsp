<%@page language="java" pageEncoding="UTF-8"%>
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
var orgTree;    //组织树
var expandDepth =2; 
var	manager;
var frm=null;
$(function(){
	frm=$("#orgForm").form();
	var options={};
	 if(showResponse){
		options.success=showResponse;
	 }
	$("a.save").click(function(){
        firstClick(frm,options);
    });
	 $("#orgAdd").click(function(){
			btnAddRow('orgTree');
		});
		$("#rolDel").click(function(){
			btnDelRow();
		});
	   loadOrgTree();
	
	$("#demensionId").change(function(){
		loadOrgTree();
	});
	
	$("#treeReFresh").click(function(){
		loadOrgTree();
	});
	$("#treeExpand").click(function(){
	var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		for(var i=1;i<nodes.length;i++){
			if(nodes[0].isParent){
			treeObj.expandAll(true);
			}
		}
	});
	
	$("#treeCollapse").click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("orgTree"); 
		treeObj.expandAll(false);
	});
});


function firstClick(frm,options){
	
	var orgItem = $("#orgItem tbody tr").html();
	if(!orgItem){
		$.ligerMessageBox.error("提示信息","请给用户选择一个组织");
		return;
	}
 
          
          try{ 
        	  	manager= $.ligerDialog.waitting('正在保存中,请稍候...');
		        frm.setData();
		        frm.ajaxForm(options);
		       frm.submit();
          }catch(e){
				$.ligerMessageBox.error("提示信息",e.message);
    			return;
			}
    	
		
	
}


 //生成组织树     		
	  function loadOrgTree() {
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
					rootPId: null
				}
			},
			async: {
				enable: true,
				url: "${ctx}/tenant/getTreeData.ht?demId="+demId,
			    autoParam:["orgId","orgSupId"]
			},
			view: {
				selectedMulti: true,
				showTitle: true
				
			},
			onRightClick: false,
			onClick:false,
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }
			},
			callback:{onCheck:orgCheck,
				onDblClick: orgTreeOnDblClick}
		 	
		   };
		   
		orgTree=$.fn.zTree.init($("#orgTree"), setting);
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
       var i=0;
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
	        		i++;
	        		if(i==1){
	        		  orgAddHtml(orgId,orgSupId,orgName);
	        		}else{
	        			$.ligerMessageBox.warn("请最多添加一个部门");
	        		}
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
					  roleName=n.roleName;
					  if (n.subSystem==null ||n.subSystem=="")
					  {
	    		       sysName=" ";
	    		       }
					  else{
					  sysName=n.subSystem.sysName;
					  }
					  systemId=n.systemId;
					  rolAddHtml(roleId,roleName,systemId,sysName);
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
         if($("#orgItem tbody tr").length>0){
        	 $.ligerMessageBox.warn("请最多添加一个部门");
        	 return ;
         }
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
	 
	 
	 
	//当选中时触发的函数 组织树
	   function orgCheck(e,treeId,treeNode){
       var a;
       if(treeNode.checked){
    	   a=true;
    	   treeNode.checked=a;
		   }else{
			   a=false;
			   treeNode.checked=a;
		   }

    	  // getRoleChildren(treeNode,a);
         
     
       }

	   function getRoleChildrenNodes(node,a) {
		   var zTree=$.fn.zTree.getZTreeObj('orgTree');
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
			var message=obj.data.message;
			manager.close();
			if (obj.isSuccess()) {
				$.ligerMessageBox.success("提示信息", "用户信息保存成功",function(){
					this.close();
					window.parent.closeWin();
				});
			} else {
				$.ligerMessageBox.error("提示信息",message);
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

a.link:hover {
    transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1) 0s;
    background-color: #f5f5f5;
    color: #fff;
    border-color: #3bd;
    background-color: #3bd;
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
				</div>
			</div>
	</div>
 <form id="orgForm" method="post" action="${ctx}/tenant/saveOrg.ht" >
 <input type="hidden" name="userId" value="${userId}">
 <div title="组织选择" tabid="orgdetail" icon="${ctx}/styles/default/images/icon/home.png">		         	         
		         
			          <table align="center"  cellpadding="0" cellspacing="0" class="table-grid table-list">
					    <tr>
				        <td width="28%" valign="top"  style="padding-left:2px !important;">
						<div class="tbar-title">
								<span class="tbar-label">所有组织</span> 
				        </div>
						<div class="panel-body" style="height:480px;overflow-y:auto;border:1px solid #6F8DC6;">	 
			<div style="display: none">
			<div style="width: 100%;">
				<select id="demensionId" style="width: 99.8% !important;">
					<c:forEach var="dem" items="${demensionList}">
						<option value="${dem.demId}" <c:if test="${dem.demId==1}">selected="selected"</c:if> >${dem.demName}</option>
					</c:forEach>
				</select>
			</div>
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
	  </form>
			<div style="height:140px; line-height:0px; font-size:0px; clear:both;overflow:hidden; display:block;"></div>
</div>
</body>
</html>