<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户指南</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/laypage/laypage.js"></script>
<script language="JavaScript" type="text/javascript" src="${ctx}/js/layer/layer.js"></script>

<script type="text/javascript">
var url='${ctx}/question/getAllQuestionByPage.ht';
var currentUser="${user}";
function questionType(type){
	if(type==0){
		url='${ctx}/question/getUnsolvedQuestionByPage.ht';
		demo();
	}else if(type==1){
		url='${ctx}/question/getSolvedQuestionByPage.ht';
		demo();
	}else if(type==2){
		if(null==currentUser||""==currentUser){
			layer.alert("您尚未登录！");
			return;
		}
		url='${ctx}/question/getMyQuestionByPage.ht';
		demo();
	}else if(type==3){
		url='${ctx}/question/getAllQuestionByPage.ht';
		demo();
	}
}

</script>


<script type="text/javascript" language="javascript">
//以下将以jquery.ajax为例，演示一个异步分页
function demo(curr){
    $.getJSON(url, {
        page: curr || 1 //向服务端传的参数，此处只是演示
    }, function(res){
    	var html="";
    	var pages = res.pages;//总页数
    	var list = res.list;
    	for (var i=0;i<list.length;i++)
    	{
    		var obj =list[i];
    		if(obj.status==0){
    			var li = 
    		/* "<li><a onclick='readCountTotal(this,"+obj.id+");' href='#'>"+obj.title+"</a><cite>"+obj.createByName+"</cite><i>"+obj.createTime+"</i><em>29答/<span name='readCount'>"+obj.readCount+"</span>阅</em></li>" */
				"<li><span class='jie-status'>求解</span><a href='${ctx}/reply/communityReply.ht?questionId="+obj.id+"'>"+obj.title+"</a><cite>"+obj.createByName+"</cite><i>"+obj.createTime+"</i><em>"+obj.replyCount+"答/"+obj.readCount+"阅</em></li>" 
    		}else if(obj.status==1){
    			var li=
    			"<li><span class='jie-status jie-status-ok'>已解</span><a href='${ctx}/reply/communityReply.ht?questionId="+obj.id+"'>"+obj.title+"</a><cite>"+obj.createByName+"</cite><i>"+obj.createTime+"</i><em>"+obj.replyCount+"答/"+obj.readCount+"阅</em></li>" 
    		}
			html = html+li;
    	}
    	$("#jie-row li").remove();
    	$("#jie-row").append(html);
         //此处仅仅是为了演示变化的内容
        //var demoContent = (new Date().getTime()/Math.random()/1000)|0;
        //document.getElementById('view1').innerHTML = "11111111" + demoContent; 
        //显示分页
        laypage({
            cont: 'view1', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: pages, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            skin: '#009E94',
            jump: function(obj, first){ //触发分页后的回调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                    demo(obj.curr);
                }
            }
        });
    });
};
//运行

</script>


  <style type="text/css">
      .api_left ul li:hover, .api_left ul li.active {
    color: #0089cd;
    background-color: #ddedf4;
    text-decoration: none;
    }
    .api2_rightTit {
    color: #333;
    line-height: 34px;
    font-size: 28px;
    border-bottom: 1px solid #ddd;
    padding-bottom:12px;
    margin-bottom: 15px;
    }
    .api2_rightCon {
    margin-bottom: 30px;
    }
    .api2_rightCon_ti {
    font-size: 16px;
    text-indent: 2em;
    }
    .api2_rightCon p {
    line-height: 39px;
   }
   tbody tr td{
      text-align: center;
      vertical-align: middle;
   }
   
   .api_bread {
    height: 38px;
    overflow: hidden;
    line-height: 38px;
    color: #323232;
    padding: 0 90px;
  }
  
  .api_right {
    margin-left: 90px;
    min-height: 700px;
   }
   
   
   html body {
    overflow-x: hidden;
}
body {
    color: #333;
    font-family: "Avenir Next",Avenir,"Helvetica Neue",Helvetica,"Lantinghei SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",STHeiti,"WenQuanYi Micro Hei",SimSun,sans-serif;
    font-feature-settings: "kern";
    font-kerning: auto;
    font-language-override: normal;
    font-size: 14px;
    font-size-adjust: none;
    font-stretch: normal;
    font-style: normal;
    font-synthesis: weight style;
    font-variant: normal;
    font-weight: normal;
    line-height: normal;
    text-rendering: geometricprecision;
}
   
   
.jie-row li {
    border-bottom: 1px dotted #e9e9e9;
   /*  font-size: 0; */
    margin-bottom: 15px;
    margin-left: 90px;
    padding-bottom: 15px;
    position: relative;
}

.jie-row li a {
    font-size: 14px;
    padding-right: 25px;
}

.jie-row li * {
    display: inline-block;
    font-size: 12px;
    line-height: 24px;
    vertical-align: top;
}
a {
    color: #444;
}


.jie-row li i, .jie-row li em, .jie-row li cite {
    color: #999;
    font-size: 12px;
}
.jie-row li cite {
    padding-right: 25px;
}

i, cite, em, var, address, dfn {
    font-style: normal;
}


.jie-row li em {
    position: absolute;
    right: 0;
    top: 0;
}

.jie-row li .jie-status {
    margin: 0 20px 0 0;
}
.jie-row li span {
    background-color: #dadada;
    color: #fff;
    font-size: 12px;
    height: 24px;
    line-height: 24px;
    margin-right: 10px;
    padding: 0 10px;
}

.jie-row li .jie-status-ok {
    background-color: #8fcda0;
}

.fly-tab {
    margin-bottom: 20px;
     margin-left: 85px;
    position: relative;
}

.fly-tab span {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    border-color: #009e94 -moz-use-text-color #009e94 #009e94;
    border-image: none;
    border-style: solid none solid solid;
    border-width: 1px medium 1px 1px;
    font-size: 0;
}
.fly-tab span, .fly-tab span a {
    display: inline-block;
    vertical-align: top;
}

.fly-tab .tab-this {
    background-color: #009e94;
    color: #fff;
}
.fly-tab span a {
    border-right: 1px solid #009e94;
    font-size: 14px;
    height: 34px;
    line-height: 34px;
    padding: 0 20px;
}


.fly-search {
    display: inline-block;
    margin-left: 10px;
    position: relative;
    vertical-align: top;
}

.icon-sousuo::before {
    content: "";
}
.fly-search .icon-sousuo {
    color: #999;
    cursor: pointer;
    position: absolute;
    right: 10px;
    top: 10px;
}
.iconfont {
    font-family: "iconfont" !important;
    font-size: 16px;
    font-style: normal;
}

.fly-search input {
    border: 1px solid #dfdfdf;
    border-radius: 0;
    height: 34px;
    line-height: 34px;
    padding: 0 30px 0 8px;
}

.jie-add {
    font-size: 14px;
    height: 36px;
    line-height: 36px;
    position: absolute;
    right: 0;
    top: 0;
}
.layui-btn, .layui-form button {
    background-color: #009e94;
    background-image: none;
    border: medium none;
    border-radius: 2px;
    color: #fff;
    cursor: pointer;
    font-size: 14px;
    height: 40px;
    line-height: 40px;
    overflow: hidden;
    padding: 0 20px;
    width: auto;
}

.api_bread {
    height: 38px;
    overflow: hidden;
    line-height: 38px;
    color: #323232;
    padding: 0 90px;
    margin-left: 5px;
}
    </style>
</head>
<body>
<%@ include file="/commons/top.jsp" %>
<%@ include file="/commons/navigator.jsp"  %>

<div class="api_box clearfix" style="height: 750px">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="community.ht">社区</a></div>
    <div class="content" style="margin-right:0;margin-left: 10px;">
    
    	<div class="fly-tab">
			<span>
			<a class="tab-this" href="#" onclick="questionType(3);">全部</a>
			<a href="#" onclick="questionType(0);">未解</a>
			<a href="#" onclick="questionType(1);">已解</a>
			<!-- <a href="#">精帖</a> -->
			<a href="#" onclick="questionType(2);">我的帖</a>
			</span>
			<!-- <form class="fly-search" action="http://cn.bing.com/search">
			<i class="iconfont icon-sousuo"></i>
			<input class="icon_old" type="text" name="q" placeholder="搜索内容，回车跳转" autocomplete="off">
			</form> -->
			<a class="layui-btn jie-add" href="${ctx}/question/add.ht">发布问题</a>
		</div>
    	<ul class="jie-row" id="jie-row">
    		<!-- <li>
				<a href="/jie/3244.html">子系统注册不能进行注册</a>
				<cite>张小鑫</cite>
				<i>2016-07-09</i>
				<em>29答/6071阅</em>
    		</li>
    		<li>
				<a href="/jie/3244.html">子系统注册不能进行注册</a>
				<cite>张小鑫</cite>
				<i>2016-07-09</i>
				<em>29答/6071阅</em>
    		</li>
    		<li>
				<a href="/jie/3244.html">子系统注册不能进行注册</a>
				<cite>张小鑫</cite>
				<i>2016-07-09</i>
				<em>29答/6071阅</em>
    		</li>
    		<li>
				<a href="/jie/3244.html">子系统注册不能进行注册</a>
				<cite>张小鑫</cite>
				<i>2016-07-09</i>
				<em>29答/6071阅</em>
    		</li>
    		<li>
				<a href="/jie/3244.html">子系统注册不能进行注册</a>
				<cite>张小鑫</cite>
				<i>2016-07-09</i>
				<em>29答/6071阅</em>
    		</li>
    		<li>
				<a href="/jie/3244.html">子系统注册不能进行注册</a>
				<cite>张小鑫</cite>
				<i>2016-07-09</i>
				<em>29答/6071阅</em>
    		</li> -->
    	</ul>
    </div>
    <div id="view1" style="margin-left: 420px"></div>
</div>

<%@ include file="/commons/footer.jsp" %>
<script type="text/javascript" src="${ctx}/js/navigator.js"></script>
<script type="text/javascript">

$(".fly-tab span a").click(function(){
	$(".fly-tab span a").each(function(){
		$(this).removeClass("tab-this");
	});
	$(this).addClass("tab-this");
});

$(function(){
	demo();
})

</script>
</body>
</html>