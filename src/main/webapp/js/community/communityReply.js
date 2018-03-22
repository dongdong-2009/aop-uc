var ue=null;
$(function(){
	 ue = UE.getEditor('container',{
		toolbars: [
		           [
		               'undo', //撤销
		               'redo', //重做
		               'bold', //加粗
		               'indent', //首行缩进
		               'italic', //斜体
		               'underline', //下划线
		               'strikethrough', //删除线
		               'superscript', //上标
		               'formatmatch', //格式刷
		               'selectall', //全选
		               'print', //打印
		               'preview', //预览
		               'cleardoc', //清空文档
		               'insertcode', //代码语言
		               'fontfamily', //字体
		               'fontsize', //字号
		               'paragraph', //段落格式
		               'edittable', //表格属性
		               'edittd', //单元格属性
		               'emotion', //表情
		               'touppercase', //字母大写
		               'tolowercase', //字母小写
		           ]
		       ],labelMap:{
		            'insertimage':'上传图片'
		        }
		
	});	
	 
 $("#btn").bind("click",function(){
	   save();
	 
	 });
 
 loadData();
 
});

//上传图片	
function _callbackImageUploadSuccess(path,name){
	UE.getEditor('container').execCommand( 'insertimage', {
	     src:fileCtx + path
	});
}
function openImageDialog(){
	$.cloudDialog.imageDialog({contextPath:__ctx});
}
function imageDialogCallback(data){
	for(index in data){
		_callbackImageUploadSuccessOther(data[index].filePath,data[index].fileName);
	}
}
function _callbackImageUploadSuccessOther(path,name){
	var item = '<div style="float:left;margin-right: 2px;">';
		item += "<img src='${fileCtx}/"+path+"' width='80' height='84' /></br>";
		item += "<input type='hidden' value='"+path+"' name='proPicture' class='proPicture'/>";
		item += "<input type='button' value='删除' onclick='deleteImg(this)' />";
		item += "</div>";
	$("#picView").append(item);
}

function deleteImg(obj){
	$(obj).parent().remove();
}

function fanhui(){
	window.location.href=__ctx+"/community.ht";
}

var Lastname='';
var save=function(){
	//判断用户是否登录
	 var userId=$("input[name='replyId']").val();
	 var createById=$("input[name='createById']").val();
	 var html=ue.getContent();
	 if(Lastname!=''){
		 var lastReply='<p>@'+Lastname+':</p>';
		 if(html==lastReply){
		 layer.alert("您的回答过于简陋!");
		 return;
		 }
		 
	 }
	 
	 if(userId==null||userId==""){
		 
	   layer.alert("请先登录");
		return;
		
	 }

	 
	
		if(html==''){
			layer.alert("详细信息不能为空!");
			return;
		} 
		var questionId=$("#questionId").val();
		   $("#content").val(html);
		   //提交解答开始
		 $.ajax({
				type : 'POST',
				url : __ctx+"/reply/replyQuestion.ht",
				data : $("#replyForm").serialize(),
				async:false,
				success : function(data) {
					if(data.result==1){
						ue.setContent('');
						loadData();
						
						
					}else{
						layer.alert("回复失败");
					}
				},
				error : function(data){
					alert("系统故障");
				}
			});
	
	
}
var pageSize = 2;
function loadData(curr){
	var questionId=$("#questionId").val();
    //以下将以jquery.ajax为例，演示一个异步分页
    $.getJSON(__ctx+"/reply/getAllReply.ht", {
        page: curr || 1,
        pageSize: pageSize,
        questionId:questionId
    },
    function (res) { //从第1页开始请求。返回的json格式可以任意定义
        laypage({
            cont: 'pager', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: Math.ceil(res.total/pageSize),//通过后台拿到的总页数
            curr: curr || 1,
            skin:'#009E94',
            //first: '首页', //若不显示，设置false即可
            //last: '尾页', //若不显示，设置false即可
            //prev: '<', //若不显示，设置false即可
            //next: '>', //若不显示，设置false即可
            jump: function (obj,first) { //触发分页后的回调
                 if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                	 loadData(obj.curr);
                 }
            }
        });
        $("#jiedaCount").text(res.total);
         $('#jieda').html(PackagData(res));
    });

}

function PackagData(res){
	var adoptStatus=$("#status").val();
	//alert(adoptStatus);
	var content="";
	    $.each(res.replyBeans,function(i,o){
	    	 var count=o.count;
		        if(count==''||count==null){
		        	count=0;
		        }
		       var status=o.status;
		        if(status==''||status==null){
		        	status='0';
		        }
		     var praiseId=o.praiseId;
		     if(praiseId==null){
		    	 praiseId='';
		     }
	        content+='<li data-id="'+o.id+'">';
	        content+='<a name="item-1471054517509"></a>';
	        content+='<div class="detail-about detail-about-reply">';
	        content+='<a class="jie-user" href="javascript:void(0);">';
	        content+='<img src='+__ctx+'"/resources/chrome/634032.png" >';
	        content+='<cite><i>'+o.replyName+'</i></cite> </a> ';
	        content+='<div class="detail-hits"> ';
	        content+='<span>'+o.timeAgo+'</span>';
	        content+='</div>';
	        content+='</div>';
	        content+='<div class="detail-body jieda-body">'+o.content+'</div> ';
	        content+='<div class="jieda-reply">';
	        if(status=='0'){
	           content+='<span class="jieda-zan " type="zan" onclick="praise(this,\''+o.replyId+'\',\''+status+'\',\''+o.id+'\',\''+praiseId+'\',\''+count+'\')"> <i class="iconfont icon-zan"></i> <em>'+count+'</em> </span>';
	        }else{
	        	content+='<span class="jieda-zan zanok" type="zan" onclick="praise(this,\''+o.replyId+'\',\''+status+'\',\''+o.id+'\',\''+praiseId+'\',\''+count+'\')"> <i class="iconfont icon-zan"></i> <em>'+count+'</em> </span>';
	        }
	        content+='<span type="reply" onclick="huifu(\''+o.replyName+'\',\''+o.replyId+'\')"> <i class="iconfont icon-svgmoban53"></i> 回复 </span>';
	        if(adoptStatus==0){
	        	content+='<span type="adopt" name="adopt" style="margin-left: 500px;" onclick="adopt(\''+o.replyId+'\')"> 采纳 </span></div>';
	        }
	        content+='</li>';
	        $(".main").css("min-height","1200"); 
	    });
	    return content;
	}

//回复
function huifu(name,id){
	 var html=ue.setContent("@"+name+":");
	 Lastname=name;
}
//采纳
function adopt(replyId){
	var createId=$("#createById").val();
	var questionId=$("#questionId").val();
	var userId=$("#userId").val();
	if(userId==null||userId==""){
		layer.alert("您尚未登录");
		return;
	}
	if(createId!=userId){
		layer.alert("您不能采纳");
		return;
	}
	if(createId==replyId){//评论人
		layer.alert("不要尝试采纳自己");
		return ;
	}
	$.ajax({
		url : __ctx+"/question/updateStatus.ht",
		data:{
			questionId:questionId
		},
		success:function(data){
			if(data.indexOf("成功")>0){
				$("#adopt").find("span").removeClass("jie-status").addClass("jie-status jie-status-ok");
				$("#adopt").find("span").html("已采纳");
				$(".jieda-reply").find("span[name='adopt']").hide();
				layer.alert("采纳成功");
			}else{
				layer.alert("采纳失败");
			}
		}
	})
}
//点赞
var praise=function(obj,replyUserId,status,replyId,praiseId,count){
	var userId=$("input[name='replyId']").val();//回复人
	if(userId==null||userId==''){
		layer.alert("请登录");
		return ;
	}
	if(userId==replyUserId){//评论人
		layer.alert("不要尝试给自己点赞");
		return ;
	}
	count=parseInt(count);
	if(status=='0'){
	//$(obj).removeClass("jieda-zan").addClass("jieda-zan zanok");
	//$(obj).find("em:eq(0)").text('1');
	  $.ajax({
		type : 'POST',
		url : __ctx+"/reply/updatePraise.ht",
		data : {
			userId:userId,
			replyId:replyId,
			status:"1",
			praiseId:praiseId
		},
		async:false,
		success : function(data) {
			if(data.result==1){
				data=eval('('+data.message+')');
				count++;
				$(obj).replaceWith('<span class="jieda-zan zanok" type="zan" onclick="praise(this,\''+replyUserId+'\',\''+data.status+'\',\''+replyId+'\',\''+data.id+'\',\''+count+'\')"> <i class="iconfont icon-zan"></i> <em>'+count+'</em> </span>');
				
			}else{
				layer.alert("点赞失败");
			}
		},
		error : function(data){
			layer.alert("系统故障");
		}
	  });
	}else{
	// $(obj).removeClass("jieda-zan zanok").addClass("jieda-zan");
	 //$(obj).find("em:eq(0)").text('0');
	  $.ajax({
		type : 'POST',
		url : __ctx+"/reply/updatePraise.ht",
		data : {
			userId:userId,
			replyId:replyId,
			status:"0",
			praiseId:praiseId
		},
		async:false,
		success : function(data) {
			if(data.result==1){
				data=eval('('+data.message+')');
				count--;
				$(obj).replaceWith('<span class="jieda-zan " type="zan" onclick="praise(this,\''+replyUserId+'\',\''+data.status+'\',\''+replyId+'\',\''+data.id+'\',\''+count+'\')"> <i class="iconfont icon-zan"></i> <em>'+count+'</em> </span>');
			}else{
				layer.alert("去赞失败");
			}
		},
		error : function(data){
			alert("系统故障");
		}
	  });
	}
}

