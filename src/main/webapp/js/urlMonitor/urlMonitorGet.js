//查看详情的页面
var dd = null;
function linkDetail(url,flag){
	var url = CTX+"/urlMonitor/getDetail.ht?url="+url+"&flag="+flag;
	dd = $.ligerDialog.open({ 
			url:url, 
			height: 600,
			width: 850, 
			title :'查看详情', 
			name : "frameDialog_",
			allowClose: true
		});
	
}
//查看图片展示
function graphDisplay(url){
	var url = CTX+"/urlMonitor/graphDisplay.ht?url="+url;
	dd = $.ligerDialog.open({ 
			url:url, 
			height: 600,
			width: 850, 
			title :'查看详情', 
			name : "frameDialog_",
			allowClose: true
		});
	
}


//查看echart图片
function getEcharts(url){
	var url = CTX+"/urlMonitor/echarts.ht?url="+url;
	dd = $.ligerDialog.open({ 
			url:url, 
			height: 600,
			width: 850, 
			title :'图形展示', 
			name : "frameDialog_",
			allowClose: true
		});
	
}