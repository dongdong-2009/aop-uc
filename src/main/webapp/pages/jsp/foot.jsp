<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- <meta name="viewport" content="width=1190px, initial-scale=1.0"> -->
<link rel="stylesheet" type="text/css" href="${ctx}/pages/css/sidebar.css"/>
<!-- footer 开始 -->

<div class="side-bar"> 
	<a href="#" class="icon-qq">返回顶部</a>
	<a href="http://imall.casicloud.com/im/casicindex.jsp" target="_blank" class="icon-chat">在线客服
	<div class="chat-tips" style="width:80px;">
	<i></i>
	<div style="width: 100%; float: left; font-size: 14px; text-align: center; height: 0px;color: rebeccapurple;">在线客服</div>
	<%-- <div style="width:100%;float:left;">
	<img style="width:80px;height:80px;" src="${ctx}/pages/cloud4.0/images/index/online.png" alt="在线客服">
	</div> --%>
	
	</div></a> 
	<a href="${ctx}/cloud/pub/yunFeedback/htcloud/create.ht" class="icon-mail">mail
	<div class="mail-tips" style="width:80px;">
	<i></i>
	<div style="width: 100%; float: left; font-size: 14px; text-align: center; height: 0px;color: rebeccapurple;">客户留言</div>
	</div>
	</a>
</div>
<footer class="index-footer">
	<div class="jumbotron mt10" style="background-color: #263142;">
		<div class="container">
			<div class="col-xs-12">
				<div class="col-xs-2 footer01">
					<h1 style="font-size:24px !important;">
						关于我们
					</h1>
					<ul>
						<li><a target="_blank" href="${ctx}/aboutus_htcloud.jsp">航天云网平台</a></li>
						<li><a target="_blank" href="${ctx}/aboutusJX.jsp">江西航天云网平台</a></li>
					</ul>
				</div>
				<div class="col-xs-2 footer01">
					<h1 style="font-size:24px !important;">
						快速链接
					</h1>
					<ul>
						<li><a target="_blank" href="http://inno.casicloud.com/innoCasicloud/common/home.do">创新创业</a></li>
						<li><a target="_blank" href="${ctx}/industryMall/hall/industryIndex.ht">工业品商城</a></li>
						<li><a target="_blank" href="${ctx}/cosimcloud/index.ht">云制造平台</a></li>
						<li><a target="_blank" href="http://182.50.3.20:8080/Education/main">培训平台</a></li>
					</ul>
				</div>
				<div class="col-xs-2 footer01">
					<h1 style="font-size:24px !important;">
						服务支持
					</h1>
					<ul>
					
						<li><a target="_blank" href="${ctx}/pages/cloud6.0/yzz/info/jsp/yunList.jsp">培训视频</a></li>
						<li><a target="_blank" href="${ctx}/pages/cloud6.0/yzz/info/jsp/operateManual.jsp">帮助手册</a></li>
						<li><a target="_blank" href="http://imall.casicloud.com/im/casicindex.jsp">客服中心</a></li>
						<li><a target="_blank" href="${ctx}/pages/cloud6.0/yzz/info/jsp/operate/shbz.jsp">售后保障</a></li>
			<%-- 			<li><a target="_blank" href="${ctx }/CAS/casLogout.ht">用户登录</a></li>
						<li><a target="_blank" href="${ctx}/reg_cloud.ht">用户注册</a></li> --%>
					</ul>
				</div>
				<div class="col-xs-2 footer01" style="width: 220px;">
					<h1 style="font-size:24px !important;">
						联系我们
					</h1>
					<ul>
						<li><span>4008576688</span></li>
						<li>地址：北京市海淀区阜成路</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 8号院主办公楼</li>
					</ul>
				</div>
				<div class="col-xs-2 footer01">
					<h1 style="font-size:24px !important;">
						云网主站
					</h1>
					<ul>
						<li style="float:left;"><img src="${ctx}/pages/images/2wm1.jpg" width="110" height="110"></li>
					</ul>
				</div>
				<div class="col-xs-2 footer01">
					<h1 style="font-size:24px !important;">
						微信公众号
					</h1>
					<ul>
						<li><img src="${ctx}/pages/images/2wm2.jpg" width="110" height="110"></li>
					</ul>
				</div>
			</div>
			<div class="col-xs-12 footer02">Copyright ©2015 All Rights Reserved&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;京ICP备05067351号-2&nbsp;&nbsp;&nbsp;京公网安备1101082014254</div>
		</div>
	</div>
</footer>
<!-- footer 结束 -->
<!--底部 end-->
		<script type="text/javascript">
	var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
	document.write(
			unescape(
					"%3Cspan id='cnzz_stat_icon_1255172414'%3E%3C/span%3E%3Cscript src='" 
					+ cnzz_protocol
					+ "s95.cnzz.com/z_stat.php%3Fid%3D1255172414%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));
	</script>
<style>
.footer01{
	padding-top:50px !important;
	*padding-top:25px !important;
}
a:hover {
  color: #fff;
  text-decoration: none;
}
a{
  color: #fff;
  text-decoration: none;
}
.footer01 ul{
	padding-top:20px !important;
	*padding-top:10px !important;
}
/* .footer01 h1 a {
  color: #FFF;
  font-size: 24px;
  font-weight: bold;
}*/
.col-xs-2 {
  width: 15.66666667%;
}
</style>