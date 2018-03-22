$(document).ready(function() {
	//测试
	//var Qiniu_UploadUrl = "http://up-z1.qiniu.com";
	 
	//生产
	 var Qiniu_UploadUrl = "http://up.qiniu.com";
	 
	 var token;
	 var key;
	 var token1;
	 var key1;
	 var fileName;
	 //生产
	 var imageUrl = "https://oby0yx23h.qnssl.com/";
	 
	//测试
	//var imageUrl = "https://oc4ts9ttb.qnssl.com/";
	 
	$("#uploadImage").click(function() {
		layer.msg('正在上传，请稍后...',{icon: 16,time: 1000000});
		token = $("#token").val();
        if ($("#uploadFile")[0].files.length > 0 && token != "") {
            key1=$("#key").val();
            key=$("#key").val();
            GetName($("#uploadFile")[0].value);
        	Qiniu_upload($("#uploadFile")[0].files[0], token, key);
        } else {
            console && console.log("form input error");
        }
	});
	var GetName = function(path){
		 var pos1 = path.lastIndexOf("\\");
		 fileName = path.substring(pos1 + 1);
	}
	 var Qiniu_upload = function(f, token, key) {
         var xhr = new XMLHttpRequest();
         xhr.open('POST', Qiniu_UploadUrl, true);
         var formData, startDate;
         formData = new FormData();
         if (key !== null && key !== undefined) formData.append('key', 'image/carousel/'+key);
         formData.append('token', token);
         formData.append('file', f);
         xhr.send(formData);
         xhr.onreadystatechange = function(response) {
             if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {
        	    layer.msg('上传图片成功',{icon: 1,time: 1500});
     			$(".layui-layer").css({'left':'30%','top':'45%','right':'30%'});
     			//alert("success:"+data);
     			layer.closeAll('loading'); //关闭加载层
     			$("#previewButton").removeClass("bg-main");
     			$("#previewButton").addClass("bg-gray");
     			$("#previewButton #uploadFile").attr("disabled", "disabled"); 
     			$("#manageImage").hide();
     			$("#manageImageInvalid").hide();
     			$("#continueUpload").show();//显示是否继续上传按钮
     			$("#dataCache").append("<span>"+"{'fileName':'"+fileName+"','filePath':'image;carousel;"+key+"'}</span>");
     			//显示到已上传图片区域
     			var src = imageUrl+'image/carousel/'+key;
     			$("#alreadyUploadedImage img:eq("+indexNum+")").parent().removeClass("icon-file-image-o");
     			$("#alreadyUploadedImage img:eq("+indexNum+")").parent().removeClass("defaultImg-small");
     			$("#alreadyUploadedImage img:eq("+indexNum+")").parent().append('<span class="badge cancleBtn" style="padding:3px 4px;right:-9px;display:inline;" onclick="removeImg(this);"></span>');
     			$("#alreadyUploadedImage img:eq("+indexNum+")").attr("src",src);
     			//如果是ie默认让图片透明
     			if (navigator.userAgent.indexOf("MSIE") > -1) {
     				$("#alreadyUploadedImage img:eq("+indexNum+")").css({'opacity':"1"});
     			}
             } else if (xhr.status != 200 && xhr.responseText) {
             }
         };
     };
})
