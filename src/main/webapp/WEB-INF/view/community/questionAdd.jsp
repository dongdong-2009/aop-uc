<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户指南</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/uc/uclayui.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/laypage/laypage.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" language="javascript">
   
	function save(){
		 var title = $("#title").val();
		 
		    var content = $("#content").val();
		    if(title==""){
		    	layer.alert("请输入问题标题!");
		    	return ;
		    }
		    if(content==""){
		    	layer.alert("请输入问题描述!");
		    	return ;
		    }
		$.ajax({
			type : 'post',
			dataType : 'json',
			data:$("#form").serialize(),
			url : '${ctx}/question/save.ht',
			success : function(res){
				if(res=='发布成功'){
					 layer.confirm('发布成功继续提问吗？', {
						   btn: ['确定','取消'] //按钮
						 }, function(index){
							 $("#form")[0].reset(); 
							 layer.close(index);
						 }, function(index){
							 window.location.href='${ctx}/community.ht';
					  });
				}else{
					layer.alert(res);
				}
			}
	   });
	}
	
	function fanhui1(){
		window.location.href='${ctx}/community.ht';
	}
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
	padding-bottom: 12px;
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

tbody tr td {
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
	font-family: "Avenir Next", Avenir, "Helvetica Neue", Helvetica,
		"Lantinghei SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑",
		STHeiti, "WenQuanYi Micro Hei", SimSun, sans-serif;
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

.main {
    margin: 0 85px;
    min-height: 600px;
    width: 1085px;
}

.layui-form input {
	width: 965px;
	 height: 38px;
}

#fabu {
    background-color: #009E94;
    border: 1px solid #dfdfdf;
    color: #fff;
    font-size: 14px;
    padding: 0 5px;
    width:100px;
}
#fanhui {
    background-color: #009E94;
    border: 1px solid #dfdfdf;
    color: #fff;
    font-size: 14px;
    padding: 0 5px;
    width:100px;
}



</style>
</head>
<body>
	<%@ include file="/commons/top.jsp"%>
	<%@ include file="/commons/navigator.jsp"  %>

	<div class="api_box clearfix" style="height: 750px">
		<div class="api_bread">
			<a href="${ctx}/index.ht">首页</a> &gt; <a href="${ctx}/community.ht">社区</a>
		</div>
		<div class="main layui-clear">
			<h2 class="page-title">发表问题</h2>
			<form id="form" class="layui-form" action="" tips="1"
				method="post">
				<ul class="jie-form">
					<li class="layui-form-li">
						<div class="layui-form-label">
							<label for="LAY_title">标题</label> <input id="title"
								type="text" autocomplete="off" name="title" required="">
						</div>
					</li>
					<li class="layui-form-li">
						<div class="layui-form-label layui-form-area">
							<div class="fly-editbox">
								<textarea id="content" placeholder="请尽量将您的问题表述详细"
									autocomplete="off" name="content" required=""></textarea>
							</div>
							<label for="LAY_desc">描述</label>
						</div>
					</li>
					<!-- <li class="layui-form-li">
						<div class="layui-form-label">
							<label>选择类别</label> <select name="class" required="">
								<option value="layui框架"></option>
								<option value="前端开发"></option>
								<option value="码农异想"></option>
							</select>
							<div class="layui-form-select">
								<div class="layui-form-sltitle">
									<span>layui框架</span> <i class="layui-edge"></i>
								</div>
								<ul class="layui-form-option">
									<li value="layui框架"><a href="javascript:;">layui框架</a></li>
									<li value="前端开发"><a href="javascript:;">前端开发</a></li>
									<li value="码农异想"><a href="javascript:;">码农异想</a></li>
								</ul>
							</div>
						</div>
						<div class="layui-form-label">
							<label>悬赏飞吻</label> <select name="experience" required="">
								<option value="5">5</option>
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select>
							<div class="layui-form-select">
								<div class="layui-form-sltitle">
									<span>5</span> <i class="layui-edge"></i>
								</div>
								<ul class="layui-form-option">
									<li value="5"><a href="javascript:;">5</a></li>
									<li value="20"><a href="javascript:;">20</a></li>
									<li value="50"><a href="javascript:;">50</a></li>
									<li value="100"><a href="javascript:;">100</a></li>
								</ul>
							</div>
						</div>
					</li>
					<li class="layui-form-li">
						<div class="layui-form-label">
							<label style="width: auto;" for="LAY_vercode"> 人类验证： <span
								style="color: #c00;">"1 3 2 4 6 5 7 ___" 请写出"____"处的数字</span>
							</label> <input id="LAY_vercode" type="text" autocomplete="off"
								name="vercode" required="" placeholder="请回答问题，以验证你是正常人类"
								style="width: 260px;">
						</div>
					</li> -->
					<li>
						<!-- <button id="LAY_btn" class="layui-btn" onclick="save()">发布</button> -->
						<input type="button" id="fabu" onclick="save()" value="发布"/>
						<input type="button" id="fanhui" value="返回"  style="background-color:#009E94;color:#fff;margin-left: 100px;" onclick="fanhui1()"/>
					</li>
				</ul>
			</form>
		</div>
	</div>

	<%@ include file="/commons/footer.jsp"%>
	<script type="text/javascript" src="${ctx}/js/navigator.js"></script>
</body>
</html>