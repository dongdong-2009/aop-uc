//手机验证规则  
jQuery.validator.addMethod("stringCheck", function(value, element) {       
	    return this.optional(element) || /^[\u4e00-\u9fa5\w|\.]+$/.test(value);       
	}, "请输入汉字或字母或下划线");   
jQuery.validator.addMethod("specialCharFilter", function(value, element) { 
	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\]<>?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	
	return this.optional(element) || (!pattern.test(value));       
}, "请输入正确的字符");   
jQuery.validator.addMethod("tel", function(value, element) { 
	var tel = /^(0\d{2,3}\-)?\d{5,8}$/;
	
	return this.optional(element) || (tel.test(value));       
}, "请输入正确的电话号码");   


jQuery.validator.addMethod("mobile", function (value, element) {
	    var mobile = /^1[3|4|5|7|8]\d{9}$/;
		return this.optional(element) || (mobile.test(value));
}, "请输入正确的手机号");
jQuery.validator.addMethod("zipCode", function(value, element) {
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "邮政编码格式错误");
var isReceviced=1;
var yer=null;
function fun(obj){
    $(obj).parent("ul").find("li").each(function(index){
	      if(this==obj){
		    $(this).addClass("active").siblings("li").removeClass("active");
		  }
		  if($(obj).text()=='收货地址'){
			  $("#address_form")[0].reset();
			  isReceviced=1;
			  $(".chen_table thead tr").find("th:eq(0)").text("收货人");
		   $(".table_title.newadd").text("新增收货地址");
		  }else{
			  $("#address_form")[0].reset();
			  isReceviced=0;
			  $(".chen_table thead tr").find("th:eq(0)").text("发货人");
			  
		    $(".table_title.newadd").text("新增发货地址");
		  }
	  });
    fun1(isReceviced);
}

  function fun1(isReceviced){
	  $.ajax({
			type: "post",
			dataType:"json",
			async:false,
			url:CTX+"/tenantAddress/getTenantAddressByType.ht",
			data:{
				"isReceviced":isReceviced
			},
			success:function(responseText){
				if (responseText.result==1) {
					var obj = JSON.parse(responseText.message);
					var str='';
					for(var i in obj){
					str+='<tr>';
					str+='<td>'+obj[i].contact+'</td>';
					if(obj[i].province==obj[i].city){
					str+='<td>'+ obj[i].city+''+ obj[i].country+'</td>';
					}else{
					str+='<td>'+obj[i].province+''+ obj[i].city+''+ obj[i].country+'</td>';
					}
					str+='<td><div style="width:100px;word-break:break-all;">'+obj[i].addresDetail+'</div></td>';
					str+='<td>'+obj[i].postcode+'</td>';
					str+='<td>'+obj[i].telephone+'</td>';	
					if(obj[i].isDefault=='1'){
					  str+='<td id='+obj[i].id+'><span>默认地址</span><a href="javascript:editAddress(\''+obj[i].id+'\');">修改</a><a href="javascript:delAddress(\''+obj[i].id+'\');">删除</a><a href="javascript:linkDetail(\''+obj[i].id+'\');">详情</a></td>';
					}
					else{
					  str+='<td id='+obj[i].id+'><a href="javascript:updateIsDefault(\''+obj[i].id+'\',this);">设为默认地址</a><a href="javascript:editAddress(\''+obj[i].id+'\');">修改</a><a href="javascript:delAddress(\''+obj[i].id+'\');">删除</a><a href="javascript:linkDetail(\''+obj[i].id+'\');">详情</a></td>';
					}
					str+='</tr>';
				 }
			       $("#person").html(str);
					
				} else {
					$.ligerMessageBox.error("提示信息",responseText.message);
				}
			}
		});
  }

   function clearaddress(obj){
		var province=$("#s_province").val();
		var city=$("#s_city").val();
		var county=$("#s_county").val();
		$("#address").val(province+","+city+","+county);
	}
$(function(){
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
				maxlength:"请输入一个不超过30个字符的字符串"
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
		if(isReceviced==1){
		 layer.alert("请填写收货地址！");
		}else{
	     layer.alert("请填写发货地址！");
		}
	} 
	if(result){
		//判断省、市、县选择状态
		if(validation("address_form")){
			var index=$("#person tr").length;
			if(index>=6){
				layer.alert("最多可以保存6条地址信息",{
				    skin: 'layui-layer-molv' //样式类名
				        ,closeBtn: 0
				    });
				return;
			}
			//规定显示的行数
			
			$("#isReceviced").val(isReceviced);
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
						str+='<td>'+obj.addresDetail+'</td>';
						str+='<td>'+obj.postcode+'</td>';
						str+='<td>'+obj.telephone+'</td>';	
						if(obj.isDefault=='1'){
						  str+='<td id='+obj.id+'><span>默认地址</span><a href="javascript:editAddress(\''+obj.id+'\');">修改</a><a href="javascript:delAddress(\''+obj.id+'\');">删除</a><a href="javascript:linkDetail(\''+obj.id+'\');">详情</a></td>';
						}
						else{
						  str+='<td id='+obj.id+'><a href="javascript:updateIsDefault(\''+obj.id+'\',this);">设为默认地址</a><a href="javascript:editAddress(\''+obj.id+'\');">修改</a><a href="javascript:delAddress(\''+obj.id+'\');">删除</a><a href="javascript:linkDetail(\''+obj.id+'\');">详情</a></td>';
						}
						str+='</tr>';
				       var length=$("#person tr").length;
				       if(length==0){
				    	   $("#person").html(str);
				       }
				       else{
				         $("#person tr:eq("+(length-1)+")").after(str);
				       }
						$("#address_form")[0].reset();
					} else {
						$.ligerMessageBox.error("提示信息",responseText.message);
					}
				}
			});
		}
	}
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
//修改默认地址
function updateIsDefault(id,obj){
	$.ajax({
		type:'post',
		url:CTX+"/tenantAddress/updateIsDefault.ht",
		data:{'id': id,
			  'isReceviced':isReceviced
		},
		dataType:'json',
		success:function(responseText){
			if (responseText.result==1) {
				var p = $("#person tr").find("td").find("span");
				var pId = p.parent("td").attr("id");
				p.replaceWith('<a href="javascript:updateIsDefault(\''+pId+'\',this);">设为默认地址</a>');
				$("#"+id).find("a:eq(0)").replaceWith('<span>默认地址</span>');
			} else {
				$.ligerMessageBox.error("提示信息",responseText.message);
			}
		}
	});
	
}
//删除地址信息
function delAddress(id){
	$.ligerMessageBox.confirm("提示信息", "确定要删除吗?", function(rtn) {
		if(rtn){
		$.ajax({
			type:'post',
			url:CTX+"/tenantAddress/delAddress.ht",
			data:{'id': id},
			dataType:'json',
			success:function(responseText){
				if (responseText.result==1) {
					$("#"+id).parent("tr").remove();
				} else {
					$.ligerMessageBox.error("提示信息",responseText.message);
				}
			}
		});
		}else{
			this.close();
		}
	});
	
}

function editAddress(id){
 yer=layer.open({
	    type: 2,
	    title: '修改地址信息',
	    shadeClose: false,
	    fix: true, 
	    icon: 1,
	    shade: 0.6,
	    offset:50,
	    area: ['800px', '450px'],
	    content:CTX+"/tenantAddress/editAddress.ht?id="+id //iframe的url
	}); 
	
}

function editTenantAddress(address_Id,str){
	 layer.close(yer);
	 $("#"+address_Id).parent("tr").replaceWith(str);
}

function linkDetail(id){
	yer=layer.open({
	    type: 2,
	    title: '修改地址信息',
	    shadeClose: false,
	    fix: true, 
	    shade: 0.6,
	    offset:50,
	    area: ['800px', '450px'],
	    content:CTX+"/tenantAddress/showAddress.ht?id="+id //iframe的url
	}); 
}