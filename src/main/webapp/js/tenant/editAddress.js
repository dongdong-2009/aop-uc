//手机验证规则  
jQuery.validator.addMethod("stringCheck", function(value, element) {       
	    return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);       
	}, "请输入汉字或字母或下划线"); 
jQuery.validator.addMethod("specialCharFilter", function(value, element) { 
	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\]<>?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	
	return this.optional(element) || (!pattern.test(value));       
}, "请输入正确的字符"); 
jQuery.validator.addMethod("tel", function(value, element) { 
	var tel = /^(0\d{2,3}\-)?\d{5,8}$/;
	
	return this.optional(element) || (tel.test(value));       
}, "请输入正确的电话号码");  
//手机验证规则  
jQuery.validator.addMethod("mobile", function (value, element) {
	    var mobile = /^1[3|4|5|7|8]\d{9}$/;
		return this.optional(element) || (mobile.test(value));
}, "请输入正确的手机号");
jQuery.validator.addMethod("zipCode", function(value, element) {
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "邮政编码格式错误");
$(function(){
	var province1=$("#province1").val();
	var city1=$("#city1").val();
	var country1=$("#country1").val();
	$("#s_province option[value="+province1+"]").attr("selected",true);
	change(1);
	$("#s_city option[value="+city1+"]").attr("selected",true);
	change(2);
	$("#s_county option[value="+country1+"]").attr("selected",true);
	validate("address_form");
});
function validate(id){
	var form = $("#" + id);
	form.validate({
		onfocusout:function(eles){
			$(eles).valid();
		},
		rules: {
			contact:{
				required: true,
				specialCharFilter:true
			},
			addresDetail:{
				required: true,
				maxlength : 30
			},
			postcode:{
				required:true,
				zipCode:true
				
			},
			mobile:{
				tel:true
			},
			telephone:{
				required: true,
				mobile:true
				
				
			}
		
		
        },
        messages:{
        	contact:{
        		required: "请输入联系人"
        	},
        	addresDetail:{
				required: "请输入地址详情",
				maxlength:"请输入字符数少于30"
			},
			postcode:{
				required: "请输入邮政编码"
			},
			mobile:{
				
			},
			telephone:{
				required: "请输入手机号"
			}
        },
        debug: true,
        errorPlacement: function(error, element){
        	var placement = $(element.parent("div").find("font"));
        	error.appendTo( placement ); 
        }
	});
	
}



function submit(){
	var result=false;
	result=$("#address_form").valid();
	if(($("#s_province").val()=='省份'||$("#s_city").val()=='地级市'||$("#s_county").val()=='市、县级市')){
		result=false;
		layer.alert("请填写收货地址！");
	} 
	if(result){
		if(validation("address_form")){
			$.ajax({
				type: "post",
				dataType:"json",
				async:false,
				url:CTX+"/tenantAddress/save.ht",
				data:$("#address_form").serialize(),
				success:function(responseText){
				//	$("span.egister_mainCon_btn").addClass("clickBtn").attr("onclick",null);
					if (responseText.result==1) {
						var obj = JSON.parse(responseText.message);
						var str='';
						str+='<tr>';
						str+='<td>'+obj.contact+'</td>';
						if(obj.province==obj.city){
						str+='<td>'+obj.city+''+ obj.country+'</td>';
						}
						else{
						str+='<td>'+obj.province+''+ obj.city+''+ obj.country+'</td>';
						}
						str+='<td><div style="width:100px;word-break:break-all;">'+obj.addresDetail+'</div></td>';
						str+='<td>'+obj.postcode+'</td>';
						str+='<td>'+obj.telephone+'</td>';	
						if(obj.isDefault=='1'){
						  str+='<td id='+obj.id+'><span>默认地址</span><a href="javascript:editAddress(\''+obj.id+'\');">修改</a><a href="javascript:delAddress(\''+obj.id+'\');">删除</a><a href="javascript:linkDetail(\''+obj.id+'\');">详情</a></td>';
						}
						else{
						  str+='<td id='+obj.id+'><a href="javascript:updateIsDefault(\''+obj.id+'\',this);">设为默认地址</a><a href="javascript:editAddress(\''+obj.id+'\');">修改</a><a href="javascript:delAddress(\''+obj.id+'\');">删除</a><a href="javascript:linkDetail(\''+obj.id+'\');">详情</a></td>';
						}
						str+='</tr>';
						var address_Id=$("#address_Id").val();
				      if(address_Id!=null)
				    	  {
				    	    window.parent.editTenantAddress(address_Id,str);
				    	  }
						
					} else {
						$.ligerMessageBox.error("提示信息",responseText.message);
					}
				}
			});
		}
	}
}
function clearaddress(obj){
	var province=$("#s_province").val();
	var city=$("#s_city").val();
	var county=$("#s_county").val();
	$("#address").val(province+","+city+","+county);
}

function validation(id){
	var result=true;
	//验证电话
	var form=$("#"+id);
	//电话号码验证   
   var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	var mobile=$("#mobile").val();
	if(mobile!=null&&mobile!=''){
		if(!tel.test(mobile)){
			result=false;
			layer.alert("请输入正确的电话号码");
			$("#mobile").focus();
		}
		
	}
	//验证所在地区
	//验证所在地区
	var s_province=$("#s_province").val();
	var s_city=$("#s_city").val();
	var s_county=$("#s_county").val();
	if(s_province==null||s_province==""||s_province=="省份"){
		result=false;
		layer.alert("请选择省份");
		
	}
	if(s_city==null||s_city==""||s_city=="地级市"){
		result=false;
		layer.alert("请选择地级市");
		
	}
	if(s_county==null||s_county==""||s_county=="市、县级市"){
		result=false;
		layer.alert("请选择市、县级市");
		
	}
	var address=s_province+","+s_city+","+s_county;
	if(address==null||address==""){
		result=false;
		layer.alert("请选择所在地区");
		
	}
	return result;
}