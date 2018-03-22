function authorizeLogin(){
	//alert(111)
	$.ajax({
		type : 'POST',
		url : CTX + "/user/ajaxauthorizeLogin.ht",
		data : $('#authorizeForm').serialize(),
		success : function(dataMap) {
			if(dataMap.status ==1){
				var account = dataMap.user.account;
				var name = dataMap.sysOrgInfo.name;
				var email = dataMap.sysOrgInfo.email;
				var mobile = dataMap.user.mobile;
				var sysOrgInfoId = dataMap.sysOrgInfo.sysOrgInfoId;
				var connecter = dataMap.sysOrgInfo.connecter;
				var authorizeUrl = dataMap.authorizeUrl;
				var url = "http://" + authorizeUrl+"?account="+account+"&name="+name+"&email="+email+"&mobile="+mobile+"&sysOrgInfoId="+sysOrgInfoId+"&connecter="+connecter;
				//alert(url);
				window.location.href = url;
				//window.open(url);
				window.close(); //关闭当前页面
			}
			if(dataMap.status == 2){
			$("#errorMsg").show();
			}
		}
	});
}
