<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/global.jsp"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
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
	<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
		
	<!--[if lt IE 9]>
	<script type="text/javascript" src="${ctx}/pages/cloud3.0/js/cloud/json2.js"></script>
	<![endif]-->
	 
	<!-- tablegird -->
	<%-- <link href="${ctx}/styles/uc/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/style.css" rel="stylesheet" type="text/css" /> --%>
		<link href="${ctx}/styles/uc/register.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/uc/user_reset.css" rel="stylesheet" type="text/css" />
	
	<link rel="stylesheet" href="${ctx}/js/tree/v35/zTreeStyle.css" type="text/css" />
	
	<link href="${ctx}/styles/ligerUI/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/scrollable.js"></script>
	<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
	
<style type="text/css">
.organization_rightIntro {
    padding-left: 0px;
    padding-top: 0px;
}
.organization_leftBtn ul li input {
    margin-top: 5px;
}

.tipBox{
border: solid 1px #ddd;
height:70px;
border-radius:10px;
margin-bottom:10px;
font-size:14px;
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
</style>

<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
	<script type="text/javascript">
	var orgTree; //树
	var menu;
	var menu_root;
	var height;
	var expandDepth = 2; 
	
	
	
	
	
	
	
	 $(function(){
		//菜单
		getMenu();
		
		loadTree(1);
		 $("#treeReFresh").click(function() {
				var demensionId = 1;
				loadTree(demensionId);
			});

			$("#treeExpand").click(function() {
				/* orgTree = $.fn.zTree.getZTreeObj("orgTree");
				var treeNodes = orgTree.transformToArray(orgTree.getNodes());
				for(var i=1;i<treeNodes.length;i++){
					if(treeNodes[i].children){
						orgTree.expandNode(treeNodes[i], true, false, false);
					}
				} */
				orgTree.expandAll(true);
			});
			$("#treeCollapse").click(function() {
				orgTree.expandAll(false);
			});
		 
		 
         $(".enterprise_baseInfor_navLeft li a").each(function(index){
             $(this).click(function(){
                 $(".enterprise_baseInfor_navLeft li a").removeClass("current");
                 $(this).addClass("current");

             });
         })
         $('.register_mainCon_list input').change(function(){
             $('.enterprise_baseInfor_btn').css('background-color' , '#ff771d');
         });
         $('.organization_toggle').each(function(){
             $(this).toggle(function(){
                 $(this).addClass('organization_leftCon_coin2');
                 $(this).parent().find('.organization_leftCon_con').hide();

             },function(){
                 $(this).removeClass('organization_leftCon_coin2');
                 $(this).parent().find('.organization_leftCon_con').show();
             })
         })
         $('.organization_rightTitBtn_list li input').each(function(){
             $(this).click(function(){
                 $('.organization_rightTitBtn_list li input').removeClass('organization_active');
                 $(this).addClass('organization_active');
             })
         })
         
     })
     
    function loadTree(demId){
		 var setting = {
					data: {
						key : {
							name: "orgName",
						},
						simpleData: {
							enable: true,
							idKey: "orgId",
							pIdKey: "orgSupId",
							rootPId: 0
						}
					},
					// 拖动
					edit : {
						enable : true,
						showRemoveBtn : false,
						showRenameBtn : false,
						drag : {
							prev : true,
							inner : true,
							next : true,
							isMove : true,
							isCopy : true
						}
					},
					view : {
						selectedMulti : false
					},
					async: {
						enable: true,
						url:"${ctx}/tenant/getTreeData.ht?demId="+demId,
						autoParam:["orgId","orgSupId"],
						dataFilter: filter
					},
					callback:{
						onClick : zTreeOnLeftClick,
						onRightClick : zTreeOnRightClick,
						beforeDrop : beforeDrop,
						onDrop : onDrop,
						onAsyncSuccess: zTreeOnAsyncSuccess
					}
					
				};
		 orgTree= $.fn.zTree.init($("#orgTree"), setting, null);
	 }
     
     
	function addNode() {
		var treeNode=getSelectNode();
		var orgId = treeNode.orgId;
		var demId = treeNode.demId;
		var url = "edit.ht?orgId=" + orgId + "&demId=" + demId + "&action=add";
		$("#viewFrame").attr("src", url);
	};

	//编辑节点
	function editNode() {
		var treeNode=getSelectNode();
		var orgId = treeNode.orgId;//如果是新增时它就变成父节点Id	      
		var demId = treeNode.demId;
		var url = "edit.ht?orgId=" + orgId + "&demId=" + demId + "&flag=upd";
		$("#viewFrame").attr("src", url);
	};

	function delNode() {
		var treeNode=getSelectNode();
		var callback = function(rtn) {
			if (!rtn) return;
			var params = "orgId=" + treeNode.orgId;
			$.post("orgdel.ht", params, function() {
				orgTree.removeNode(treeNode);
			});
		};
		$.ligerMessageBox.confirm('提示信息', "确认要删除此组织吗，其下组织也将被删除？", callback);

	};
     
	
	function getSelectNode(){
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = orgTree.getSelectedNodes();
		var treeNode = nodes[0];
		return treeNode;
	}
	
	//排序
	function sortNode(){
		var treeNode=getSelectNode();
		var orgId = treeNode.orgId;
		var demId = treeNode.demId;
		var url=__ctx +'/cloud/config/enterprise/org/sortList.ht?orgId='+orgId+"&demId="+demId;
		var winArgs="dialogWidth:600px;dialogHeight:300px";
	 	url=url.getNewUrl();
	 	var rtn=window.showModalDialog(url,"",winArgs);
	 	var demensionId = $("#demensionId").val();
		loadTree(demensionId);
	}
     
     //判断是否为子结点,以改变图标	
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		if(treeNode){
	  	 	var children=treeNode.children;
		  	 if(children.length==0){
		  		treeNode.isParent=true;
		  		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		  		orgTree.updateNode(treeNode);
		  	 }
		}
	};
     
     
     //拖放 后动作
	function onDrop(event, treeId, treeNodes, targetNode, moveType) {
		if (targetNode == null || targetNode == undefined)	return;
		var targetId = targetNode.orgId;
		var dragId = treeNodes[0].orgId;
		var url = __ctx + "/platform/system/sysOrg/move.ht";
		var params = {
			targetId : targetId,
			dragId : dragId,
			moveType : moveType
		};

		$.post(url, params, function(result) {
			var demensionId = $("#demensionId").val();
			loadTree(demensionId);
		});
	}
     
     
     //拖放 前准备
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {

		if (!treeNodes)
			return false;
		if (targetNode.isRoot == 1)
			return false;
		return true;
	};
     
     
     //左击事件
	function zTreeOnLeftClick(event, treeId, treeNode) {
		var isRoot = treeNode.isRoot;
		if (isRoot == 1) {
			return;
		}
		var orgId = treeNode.orgId;
		$("#viewFrame").attr("src", "get.ht?orgId=" + orgId);
	};

	/**
	 * 右击事件
	 */
	function zTreeOnRightClick(e, treeId, treeNode) {
		orgTree.selectNode(treeNode);
		if (treeNode.isRoot == 1) {//根节点时，把删除和编辑隐藏掉
			menu_root.show({
				top : e.pageY,
				left : e.pageX
			});
		} else {
			menu.show({
				top : e.pageY,
				left : e.pageX
			});
		}
	};
     
     
     //过滤节点
	function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				var node = childNodes[i];
				if (node.isRoot == 1) {
					node.icon = __ctx + "/styles/default/images/icon/root.png";
				} else {
					if (node.ownUser == null || node.ownUser.length < 1) {
						node.orgName += "[未]";
					}
// 					setIcon(node);
				}
			}
			return childNodes;
	};
	
	
	//右键菜单
	function getMenu() {
		debugger;
		menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 100,
			items:
				[ {
					text : '增加',
					click : addNode
					//alias:'addEnterpriseOrg'
				}, {
					text : '编辑',
					click : editNode
					//alias:'editEnterpriseOrg'
				}, 
				/*
				{
					text : '参数属性',
					click : 'orgParam',
					alias:''
				},
				*/
				/* {
					text : '排序',
					click : sortNode
				}, */ {
					text : '删除',
					click : delNode
				}/* , {
					text : '刷新',
					click : refreshNode
				} */]
				  
		});

		menu_root = $.ligerMenu({
			top : 100,
			left : 100,
			width : 100,
			items : [ {
				text : '增加',
				click : addNode
			}/* ,{
				text : '排序',
				click : sortNode
			}  */]
		});
	};
		
	
	$(window.parent.document).find("#mainiframe").load(function(){
		
		var mainheight = 800;
		 $(this).height(mainheight);
	});
	</script>

</head>
<body>

<div class="tipBox">

<img id="tip" src="${ctx}/pages/images/tip.png"/><span id="warmtips">温馨提示：</span>

 <p><span>您可对本企业组织机构进行查看、编辑、增加、等维护管理。</span></p>
</div>




            <div class="register_mainCon organization_leftTop">
                <div class="organization_outer">
                    <div class="organization_left f_left">
                        <div class="organization_leftTit">组织机构管理</div>
                        <div class="organization_leftBtn">
                            <ul>
                                <li><input type="button" value="刷新" id="treeReFresh"/></li>
                                <li><input type="button" value="展开" id="treeExpand"/></li>
                                <li><input type="button" value="收起" id="treeCollapse"/></li>
                            </ul>
                        </div>
                        <div class="organization_leftCon">
                            <div  id="orgTree" class="ztree">
                               <!--  <span class="organization_toggle organization_leftCon_coin1"></span>
                                <div class="organization_leftCon_tit">北京五棵松发展公司</div>
                                <ul class="organization_leftCon_con">
                                    <li>采购部</li>
                                    <li>采购部</li>
                                    <li>采购部</li>
                                </ul> -->
                            </div>
                            
                        </div>
                    </div>
                    <div class="organization_right f_left">
                        <div class="organization_rightTit">组织架构信息</div>
                        <%-- <div class="organization_rightTitBtn">
                            <ul class="organization_rightTitBtn_list">
                                <li><span style="vertical-align: middle;"><img src="${ctx}/pages/images/organization_bg4.png" height="10" width="10" alt="" /></span>&nbsp;<input type="button" value="添加下级组织" /></li>
                                <li><input type="button" value="编辑" class="organization_active" /></li>
                                <li><input type="button" value="删除" /></li>
                                <li><input type="button" value="排序" /></li>

                            </ul>
                        </div> --%>
                        <div class="organization_rightIntro">
                            <!-- div class="organization_rightIntro_tit">组织简介</div> -->
                            <iframe id="viewFrame" src="get.ht" frameborder="0" width="100%" height="350px"></iframe>
                        </div>
                    </div>
                </div>
                
            </div>
      
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
