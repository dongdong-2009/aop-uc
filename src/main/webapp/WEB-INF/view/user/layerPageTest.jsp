<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/layer/skin/layer.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/layer/skin/laypage.css" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/laypage.js"></script>
<script type="text/javascript">
//好像很实用的样子，后端的同学再也不用写分页逻辑了。
$(function(){
	demo();
 
	
	
});
function demo(curr) {
    var pageSize = 10;
 
    //以下将以jquery.ajax为例，演示一个异步分页
    $.getJSON('${ctx}/user/ajax_list.ht', {
        page: curr || 1,
        pageSize: pageSize
    },
    function (res) { //从第1页开始请求。返回的json格式可以任意定义
        laypage({
            cont: 'dd', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: Math.ceil(res[0].total/pageSize),//通过后台拿到的总页数
            curr: curr || 1,
            skin:'#429842',
            //first: '首页', //若不显示，设置false即可
            //last: '尾页', //若不显示，设置false即可
            //prev: '<', //若不显示，设置false即可
            //next: '>', //若不显示，设置false即可
            jump: function (obj,first) { //触发分页后的回调
                 if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                     demo(obj.curr);
                 }
            }
        });
         $('#tbody').html(PackagData(res));
    });
}
function PackagData(res){
	var content="";
	    $.each(res,function(i,o){
	        content+="<tr><td>";
	        content+=o.userId;
	        content+="</td><td>";
	        content+=o.fullname;
	        content+="</td></tr>";
	         
	    });
	    return content;
	}
</script>
</head>

<body>
<table id="Result" cellspacing="0" cellpadding="0" border="1">
            <tr>
                <th>id</th>
                <th>名称</th>
            </tr>
            <tbody id="tbody">
            </tbody>
        </table>
    <div id="dd"></div>
</body>
</html>