var liger=null;
$(function(){
  $("#btntable").bind("click",function(){
	  var begincreatetime=$("input[name='Q_begincreatetime_DL']").val();
	  var endcreatetime=$("input[name='Q_endcreatetime_DG']").val();
	  var url= CTX+"/invitited/echarts.ht?begincreatetime="+begincreatetime+"&endcreatetime="+endcreatetime;
	  liger=$.ligerDialog.open({ 
			url:url, 
			height: 600,
			width: 850, 
			title :'图形展示', 
			name : "frameDialog_",
			allowClose: true
		});
	  
  });	
	//导出excel
  $("#export").click(function(){
	  var currentPage=$("input[name='currentPage']").val();
	  var pageSize=$("input[name='oldPageSize']").val();
	  $("#downFile").attr("src",CTX+"/invitited/exportExcel.ht?currentPage="+currentPage+"&pageSize="+pageSize);
	  
	  
  })
});



$(window.parent.document).find("#mainiframe").load(function(){
	
	var mainheight = 800;
	 $(this).height(mainheight);
});