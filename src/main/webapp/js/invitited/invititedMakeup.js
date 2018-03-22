//为公司名称添加验证
jQuery.validator.addMethod("comName", function (value, element) {
		var comName = /^[A-Za-z0-9\u4e00-\u9fa5\(\)\（\）]+$/;
		return this.optional(element) || (comName.test(value));
}, "公司名称格式不正确");
$(function(){
	validate("searchForm");
    $("#name").focus(function(){
    	var result=false;
		 result=$("#searchForm").validate().element($("#searchForm").find('input[name="name"]'));
		
	});
	$("#name").blur(function(){
		var result=false;
		 result=$("#searchForm").validate().element($("#searchForm").find('input[name="name"]'));
		 if(result){
		    validateExist(this.value);
		 }
	});
	$("#sub").click(function(){
		submit();
	});
	

});
function validate(id){
	var form = $("#" + id);
	form.validate({
		onfocusout:function(eles){
			$(eles).valid();
		},
		rules: {
			name:{
				required: true
				
			}
			
        },
        messages:{
			name:{
				required:'请输入公司名称'
			
			}
        },
        debug: true,
        errorPlacement: function(error, element){
        	var placement = $(element.parent("div").find("font"));
        	error.appendTo( placement ); 
        }
	});
	
}
var submit=function(){
	var reuslt=false;
	 result=$("#searchForm").valid();
	 if(result){
	    var name=$("#name").val();
		if(!validateExist(name)){
			return ;
		}
		tijiao();
	 }else{
		 layer.alert("请完善信息!");
	 }
	
}
var validateExist=function(name){
	var result=false;
	var currentName=$("#currentName").val();
	name=$.trim(name);
	if(currentName==name){
		$("#name").next("font").html('<label for="name" class="error">您不能对本企业做补录</label>');
		return ;
	}
	$.ajax({
		type : 'POST',
		url: CTX + '/user/checkOrgNameExist.ht',
		type: "post",
		dataType: "json",
		async : false ,
		data:{"name":name},
		success : function(data) {
			console.log(data);
			if(data.isExist){
				$("#name").next("font").html('');
				$("#invititedCode").prev("span").text(data.currentId);
				$("#invititedCode").val(data.currentId);
				result= true;
			}
			else{
				$("#name").next("font").html('<label for="name" class="error">您填写的企业不存在,请重新输入</label>');
				$("#invititedCode").prev("span").text('');
				$("#invititedCode").val('');
				result= false;
			}
		},
		error : function(data){
			layer.alert("数据异常,请稍后再试!");
			window.location.reload();
		}
	});
	return result;
}
var tijiao=function(){
	$.ajax({
		type : 'POST',
		url : CTX+"/tenant/invitedCodeMakeup.ht",
		dataType: "json",
		data : $("#searchForm").serialize(),
		success : function(data) {
			if(data.result=='1'){
				layer.alert(data.message, {
					skin : 'layui-layer-molv' //样式类名
					,
					closeBtn : 0
				}, function(index) {
					var str='';
					str+='<span>'+$("#name").val()+'</span>';
					str+='<input type="hidden" name="name" id="name" value="'+$("#name").val()+'"/>';
					$("#div1").css("margin-top","7px").empty();
					$("#div1").append(str);
					$("#sub").parent("span").hide();
					layer.close(index);
					window.location.href=CTX +"/invitited/makeup.ht";
				});
			}
			else{
				layer.alert(data.message, {
					skin : 'layui-layer-molv' //样式类名
					,
					closeBtn : 0
				}, function() {
					window.location.reload();
				});
			}
		},
		error : function(data){
			layer.alert("数据异常,请稍后再试!");
			window.location.reload();
		}
	});
}
