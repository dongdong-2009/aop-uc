$(function(){
	$(".api_left ul li").each(function(index){
	  $(this).click(function(){
		 $(".api_right div.api_items").hide();
		 $(".api_right div.api_items").eq(index).slideDown();
		 $(this).addClass("active").siblings("li").removeClass("active");
		 });
	  });
	
	 $("#btn").click(function(){
		  var url=$("#url").val();
		  var seltype=$("#seltype").val();
		  var ck=$("#ck").val();
		  var header=$("#header").val();
		  var parms=$("#parms").val();
		  var proxy=$("#proxy").val();
		  var code=$("#code").val();
		  if(url==''){
			layer.alert("地址必须填写",{
			    skin: 'layui-layer-molv' //样式类名
			        ,closeBtn: 0
			    });
			$("#url").focus();
			return;
		}
		$("#getresult").html("正在提交，获取中.....");
		  $.ajax({
			  url:CTX+"/loadData.ht",
			  type:"POST",
			  dataType:"json",
			  data:"url="+encodeURIComponent(encodeURIComponent(url))+"&seltype="+seltype+"&ck="+encodeURIComponent(encodeURIComponent(ck))+"&header="+encodeURIComponent(encodeURIComponent(header))+"&parms="+encodeURIComponent(encodeURIComponent(parms))+"&proxy="+encodeURIComponent(encodeURIComponent(proxy))+"&code="+code,
			  success:function(data){
				 var ss = eval("("+data.message+")");
				if (data.result==1) {
					if(ss.success){
						var message=ss.message;
						if(typeof(message)=='string'){
							//判断是否为json串
							if(message.indexOf("{")>-1){
								var ms=eval("("+message+")");
								if(ms.success){
									 $("#getresult").text(ms.message);
								}else{
									 $("#getresult").text(ms.error);
								}
							}else{
								$("#getresult").text(message);
							}
							
							
						}else{
							$("#getresult").text(ss.message);
						}
					 
					}else{
					 $("#getresult").text("请求出错");
					}
					
				}else{
					$("#getresult").html(ss.message);
					$("#getresultheader").html('');
				}
			  }
		     });
		 });

	
	
	//测试用例
	 $("#httptest").click(function(){
		  var url=$("#url").val("http://coolaf.com/"+"tool/params?g=gtest&g2=gtest2");
		  var ck=$("#ck").val("c=cookie;c1=cookie1;c2=cookie2");
		  var header=$("#header").val("Content-Type:application/x-www-form-urlencoded\nAccept-Language:zh-CN,en-US;q=0.8,en;q=0.6,zh;q=0.4");
		  var parms=$("#parms").val("p=ptest&p=test2");
		});

	 $("#clearform").click(function(){
			$("#url").val("");
		    $("#ck").val("");
		    $("#header").val("Content-Type:application/x-www-form-urlencoded");
		    $("#parms").val("");
		    $("#proxy").val("");
		});
	
	
	
	
	
	
	
	
	
	
	
});
