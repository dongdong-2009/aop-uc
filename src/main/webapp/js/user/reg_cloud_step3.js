var count = 1 ;
var sum = 3;
$(function(){
	var systemId=$("#systemId").val();
	if(systemId==null||systemId==""){
		$(".register").css("display","block");
	}else{
		$(".register").css("display","none");
		interval();
	}
	
});
var interval=function(){
	
	var obj=$("span#timer");
	var i = setInterval(function(){
	      if(count > sum){
	        clearInterval(i);
	        Login();
	      }else{
	        obj.text(parseInt(sum - count));
	      }
	      count++;
	    },1000);
	
}
function Login(){
	var systemId=$("#systemId").val();
	var account=$("#account").val();
	var password=$("#password").val();
	var serviceUrl=$("#serviceUrl").val();
	
var url = "https://cast.casicloud.com/registerLogin?userName="+account+"&password="+password+"&isEncrypt=yes&service="+serviceUrl;
//	var url = "http://172.17.70.247:8080/registerLogin?userName="+account+"&password="+password+"&isEncrypt=yes&service="+serviceUrl;
	
	if("" == serviceUrl || null == serviceUrl ){
		var params=new Array();
		params.push({name:"systemId",value:systemId});
		params.push({name:"userName",value:account});
		params.push({name:"password",value:password});
		Post(CTX+"/user/doLogin.ht",params);
		
	}else{
		window.location.href = url;
	}
	/*var systemId=$("#systemId").val();
	if(systemId==null||systemId==""){
		layer.alert("请传入子系统的Id");
		return;
	}
	if(systemId=="null"){
		systemId="100";
	}
	$.ajax({
		type : 'POST',
		url : CTX+"/user/checkSystemId.ht",
		dataType: "json",
		data : {systemId : systemId},
		success : function(data) {
			if(data.result=='1'){
				//如果存在
				var returnUrl=$("#returnUrl").val();
				var params=new Array();
				params.push({name:"systemId",value:systemId});
				params.push({name:"returnUrl",value:returnUrl});
				Post(CTX+"/user/doLogin.ht",params);
				
			}
			else{
				layer.alert("子系统Id不存在！", {icon: 6,time: 100000,shade : [0.5 , '#000' , true]}, function(){
					window.location.href=CTX+'/user/reg_cloud.ht';
				});
			}
		},
		error : function(data){
			layer.alert("系统故障");
		}
	});*/
	
}

function Post(url,params){
	//创建一个form
	 var temp_form = document.createElement("form");
	 //指定form的跳转action 
	 temp_form.action=url;
	 temp_form.method = "post";
	 temp_form.style.display = "none";
	 //添加参数
	 for(var item in params){
		 //创建若干文本域
		 var opt = document.createElement("input");
		     opt.setAttribute("type", "text");
		     opt.name=params[item].name;
		     opt.value=params[item].value;
		     temp_form.appendChild(opt);
	 }
	 document.body.appendChild(temp_form);
	//提交数据
     temp_form.submit();
}