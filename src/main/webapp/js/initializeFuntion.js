/**
 * 期初开账过滤业务单据
 * @return
 */
function initializeFilter(){
	var flag = false;
	$.ajax({
			url: __ctx + "/cloud/warehouse/cloudWarehouseInitialize/isPermit.ht",  
			type: 'POST',
			dataType: 'json',
			async: false,
			success: function(data){
				flag = data;
			},
			error: function(data){
				//$.ligerMessageBox.warn("提示信息","期初建账没有完成.<br/><font color='red'>如果需要开单请先建账。</font><br/><br/><a href='${ctx}/cloud/warehouse/cloudWarehouseInitialize/list.ht'>点击进入期初建账页面</a>");
				$.ligerMessageBox.warn("提示信息","期初建账没有完成.<br/><font color='red'>如需开单,请联系管理员先建账。</font>");
			}
		});
	return flag;
}

/**
 * 月结过滤业务单据
 **/
function monthlockFilter(e){
	//获取处理的DOM节点
	var flag = false;
	$.ajax({
			url: __ctx + "/cloud/warehouse/cloudWarehouseInitialize/isLock.ht",  
			type: 'POST',
			dataType: 'json',
			async: false,
			success: function(data){
				flag = data;
			},
			error: function(data){
				$.ligerMessageBox.warn("提示信息","操作的单据失败.");
			}
		});
	return flag;
}


/**
 * 当DOM加载完成后，执行其中的函数
 */
$(document).ready(function(){
	$("a.add").click(function() {
		var flag = initializeFilter();
		if(!flag){
			$.ligerMessageBox.warn("提示信息","期初建账没有完成.<br/><font color='red'>如果需要开单请先建账。</font><br/><br/><a href= '"+__ctx+"/cloud/warehouse/cloudWarehouseInitialize/list.ht'>点击进入期初建账页面</a>");
		}
		return flag;
	});
	
	$("a.monthlock").click(function(e) {
		var flag = monthlockFilter(e);
		if(!flag){
			$.ligerMessageBox.warn("提示信息","操作的单据已经月结.");
		}
		e.preventDefault(); 
		return flag;
	});
});
