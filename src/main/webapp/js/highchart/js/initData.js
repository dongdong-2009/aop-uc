$(function(){
	//给时间控件赋初值
	var yesterday= getYestoday(new Date());
	$("#startTime").val(yesterday);
	$("#endTime").val(new Date().format("yyyy-MM-dd"));

	$("#submit").click(function(){
		var timegap = 6;
		var starttime = $("#startTime").val();
		var endtime = $("#endTime").val();
		var state=checkTime(starttime,endtime,timegap);
		if(!state){
			return false;
		}
	});
	
});
