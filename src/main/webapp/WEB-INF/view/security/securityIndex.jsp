
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
$(function(){
	
$(window.parent.document).find("#mainiframe").load(function(){
	var mainheight = 800;
	 $(this).height(mainheight);
});
});
</script>
<style type="text/css">
body {
  margin:0px;
  background-image:none;
  position:static;
  left:auto;
  width:1381px;
  margin-left:0;
  margin-right:0;
  text-align:left;
}
#base {
  position:absolute;
  z-index:0;
}
#u1656_img {
  position:absolute;
  left:0px;
  top:0px;
  width:1381px;
  height:827px;
}
#u1656 {
  position:absolute;
  left:0px;
  top:0px;
  width:1381px;
  height:827px;
}
#u1657 {
  position:absolute;
  left:2px;
  top:406px;
  width:1377px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1658_div {
  position:absolute;
  left:0px;
  top:0px;
  width:94px;
  height:22px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-family:'Arial Negreta', 'Arial Normal', 'Arial';
  font-weight:700;
  font-style:normal;
  font-size:18px;
}
#u1658 {
  position:absolute;
  left:0px;
  top:0px;
  width:94px;
  height:22px;
  font-family:'Arial Negreta', 'Arial Normal', 'Arial';
  font-weight:700;
  font-style:normal;
  font-size:18px;
}
#u1659 {
  position:absolute;
  left:0px;
  top:0px;
  width:94px;
  word-wrap:break-word;
}
#u1660_div {
  position:absolute;
  left:0px;
  top:0px;
  width:1126px;
  height:34px;
  background:inherit;
  background-color:rgba(242, 242, 242, 1);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
}
#u1660 {
  position:absolute;
  left:0px;
  top:40px;
  width:1126px;
  height:34px;
}
#u1661 {
  position:absolute;
  left:2px;
  top:9px;
  width:1122px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1662_div {
  position:absolute;
  left:0px;
  top:0px;
  width:84px;
  height:19px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:16px;
  color:#336699;
}
#u1662 {
  position:absolute;
  left:0px;
  top:47px;
  width:84px;
  height:19px;
  font-size:16px;
  color:#336699;
}
#u1663 {
  position:absolute;
  left:0px;
  top:0px;
  width:84px;
  word-wrap:break-word;
}
#u1664_img {
  position:absolute;
  left:0px;
  top:0px;
  width:802px;
  height:94px;
}
#u1664 {
  position:absolute;
  left:50px;
  top:105px;
  width:802px;
  height:94px;
}
#u1665 {
  position:absolute;
  left:2px;
  top:39px;
  width:798px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1666_div {
  position:absolute;
  left:0px;
  top:0px;
  width:73px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1666 {
  position:absolute;
  left:70px;
  top:220px;
  width:73px;
  height:15px;
  font-size:12px;
}
#u1667 {
  position:absolute;
  left:0px;
  top:0px;
  width:73px;
  white-space:nowrap;
}
#u1668_div {
  position:absolute;
  left:0px;
  top:0px;
  width:61px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1668 {
  position:absolute;
  left:273px;
  top:346px;
  width:61px;
  height:15px;
  font-size:12px;
}
#u1669 {
  position:absolute;
  left:0px;
  top:0px;
  width:61px;
  white-space:nowrap;
}
#u1670_div {
  position:absolute;
  left:0px;
  top:0px;
  width:61px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1670 {
  position:absolute;
  left:271px;
  top:372px;
  width:61px;
  height:15px;
  font-size:12px;
}
#u1671 {
  position:absolute;
  left:0px;
  top:0px;
  width:61px;
  white-space:nowrap;
}
#u1672_div {
  position:absolute;
  left:0px;
  top:0px;
  width:98px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1672 {
  position:absolute;
  left:355px;
  top:320px;
  width:98px;
  height:15px;
  font-size:12px;
}
#u1673 {
  position:absolute;
  left:0px;
  top:0px;
  width:98px;
  white-space:nowrap;
}
#u1674_div {
  position:absolute;
  left:0px;
  top:0px;
  width:78px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1674 {
  position:absolute;
  left:354px;
  top:346px;
  width:78px;
  height:15px;
  font-size:12px;
}
#u1675 {
  position:absolute;
  left:0px;
  top:0px;
  width:78px;
  white-space:nowrap;
}
#u1676_div {
  position:absolute;
  left:0px;
  top:0px;
  width:103px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1676 {
  position:absolute;
  left:354px;
  top:372px;
  width:103px;
  height:15px;
  font-size:12px;
}
#u1677 {
  position:absolute;
  left:0px;
  top:0px;
  width:103px;
  white-space:nowrap;
}
#u1678_div {
  position:absolute;
  left:0px;
  top:0px;
  width:1126px;
  height:34px;
  background:inherit;
  background-color:rgba(242, 242, 242, 1);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
}
#u1678 {
  position:absolute;
  left:0px;
  top:370px;
  width:1126px;
  height:34px;
}
#u1679 {
  position:absolute;
  left:2px;
  top:9px;
  width:1122px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1680_div {
  position:absolute;
  left:0px;
  top:0px;
  width:84px;
  height:19px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:16px;
  color:#336699;
}
#u1680 {
  position:absolute;
  left:0px;
  top:375px;
  width:84px;
  height:19px;
  font-size:16px;
  color:#336699;
}
#u1681 {
  position:absolute;
  left:0px;
  top:0px;
  width:84px;
  word-wrap:break-word;
}
#u1682_div {
  position:absolute;
  left:0px;
  top:0px;
  width:715px;
  height:77px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
}
#u1682 {
  position:absolute;
  left:50px;
  top:700px;
  width:715px;
  height:77px;
}
#u1683 {
  position:absolute;
  left:0px;
  top:0px;
  width:715px;
  word-wrap:break-word;
}
#u1684_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
  color:#0000FF;
}
#u1684 {
  position:absolute;
  left:513px;
  top:350px;
  width:49px;
  height:15px;
  font-size:12px;
  color:#0000FF;
}
#u1685 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1686_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
  color:#0000FF;
}
#u1686 {
  position:absolute;
  left:513px;
  top:377px;
  width:49px;
  height:15px;
  font-size:12px;
  color:#0000FF;
}
#u1687 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1688_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
  color:#0000FF;
}
#u1688 {
  position:absolute;
  left:587px;
  top:350px;
  width:49px;
  height:15px;
  font-size:12px;
  color:#0000FF;
}
#u1689 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1690_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
  color:#0000FF;
}
#u1690 {
  position:absolute;
  left:587px;
  top:377px;
  width:49px;
  height:15px;
  font-size:12px;
  color:#0000FF;
}
#u1691 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1692_img {
  position:absolute;
  left:-1px;
  top:-1px;
  width:849px;
  height:5px;
}
#u1692 {
  position:absolute;
  left:0px;
  top:565px;
  width:846px;
  height:2px;
}
#u1693 {
  position:absolute;
  left:2px;
  top:-7px;
  width:842px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1694_img {
  position:absolute;
  left:-1px;
  top:-1px;
  width:849px;
  height:5px;
}
#u1694 {
  position:absolute;
  left:0px;
  top:490px;
  width:846px;
  height:2px;
}
#u1695 {
  position:absolute;
  left:2px;
  top:-7px;
  width:842px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1696_img {
  position:absolute;
  left:-1px;
  top:-1px;
  width:849px;
  height:5px;
}
#u1696 {
  position:absolute;
  left:0px;
  top:650px;
  width:846px;
  height:2px;
}
#u1697 {
  position:absolute;
  left:2px;
  top:-7px;
  width:842px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1698_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1698 {
  position:absolute;
  left:96px;
  top:452px;
  width:49px;
  height:15px;
  font-size:12px;
}
#u1699 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1700_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1700 {
  position:absolute;
  left:96px;
  top:525px;
  width:49px;
  height:15px;
  font-size:12px;
}
#u1701 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1702_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1702 {
  position:absolute;
  left:96px;
  top:605px;
  width:49px;
  height:15px;
  font-size:12px;
}
#u1703 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1704_div {
  position:absolute;
  left:0px;
  top:0px;
  width:528px;
  height:30px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1704 {
  position:absolute;
  left:196px;
  top:452px;
  width:528px;
  height:30px;
  font-size:12px;
}
#u1705 {
  position:absolute;
  left:0px;
  top:0px;
  width:528px;
  word-wrap:break-word;
}
#u1706_div {
  position:absolute;
  left:0px;
  top:0px;
  width:547px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1706 {
  position:absolute;
  left:196px;
  top:525px;
  width:547px;
  height:15px;
  font-size:12px;
}
#u1707 {
  position:absolute;
  left:0px;
  top:0px;
  width:547px;
  word-wrap:break-word;
}
#u1708_div {
  position:absolute;
  left:0px;
  top:0px;
  width:512px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
}
#u1708 {
  position:absolute;
  left:196px;
  top:605px;
  width:512px;
  height:15px;
  font-size:12px;
}
#u1709 {
  position:absolute;
  left:0px;
  top:0px;
  width:512px;
  word-wrap:break-word;
}
#u1710_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
  color:#0000FF;
}
#u1710 {
  position:absolute;
  left:770px;
  top:452px;
  width:49px;
  height:15px;
  font-size:12px;
  color:#0000FF;
}
#u1711 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1712_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
  color:#0000FF;
}
#u1712 {
  position:absolute;
  left: 770px;
  top:525px;
  width:49px;
  height:15px;
  font-size:12px;
  color:#0000FF;
}
#u1713 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1714_div {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  height:15px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  font-size:12px;
  color:#0000FF;
}
#u1714 {
  position:absolute;
  left:770px;
  top:605px;
  width:49px;
  height:15px;
  font-size:12px;
  color:#0000FF;
}
#u1715 {
  position:absolute;
  left:0px;
  top:0px;
  width:49px;
  white-space:nowrap;
}
#u1716_img {
  position:absolute;
  left:0px;
  top:0px;
  width:19px;
  height:19px;
}
#u1716 {
  position:absolute;
  left:937px;
  top:398px;
  width:19px;
  height:19px;
}
#u1717 {
  position:absolute;
  left:2px;
  top:2px;
  width:15px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1718_div {
  position:absolute;
  left:0px;
  top:0px;
  width:57px;
  height:16px;
  background:inherit;
  background-color:rgba(255, 255, 255, 0);
  border:none;
  border-radius:0px;
  -moz-box-shadow:none;
  -webkit-box-shadow:none;
  box-shadow:none;
  color:#FF0000;
}
#u1718 {
  position:absolute;
  left:956px;
  top:399px;
  width:57px;
  height:16px;
  color:#FF0000;
}
#u1719 {
  position:absolute;
  left:0px;
  top:0px;
  width:57px;
  white-space:nowrap;
}
#u1720_img {
  position:absolute;
  left:0px;
  top:0px;
  /* width:67px; */
  height:20px;
}
#u1720 {
  position:absolute;
  left:0px;
  top:525px;
  width:67px;
  height:24px;
}
#u1721 {
  position:absolute;
  left:2px;
  top:4px;
  width:63px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1722_img {
  position:absolute;
  left:0px;
  top:0px;
 /*  width:64px; */
  height:20px;
}
#u1722 {
  position:absolute;
  left:0px;
  top:605px;
  width:64px;
  height:24px;
}
#u1723 {
  position:absolute;
  left:2px;
  top:4px;
  width:60px;
  visibility:hidden;
  word-wrap:break-word;
}
#u1724_img {
  position:absolute;
  left:0px;
  top:0px;
  /* width:62px; */
  height:20px;
}
#u1724 {
  position:absolute;
  left:0px;
  top:450px;
  width:62px;
  height:23px;
}
#u1725 {
  position:absolute;
  left:2px;
  top:4px;
  width:58px;
  visibility:hidden;
  word-wrap:break-word;
}
#haveset{
margin-left: 25px;
}
#havebind{
margin-left: 25px;
}
#havevalidate{
margin-left: 25px;
}

</style>

<script type="text/javascript">
	window.HT_UID='${userId}';
	window.HT_OID='${orgId}';
</script>
<script type="text/javascript" src="https://stat.htres.cn/log.js?sid=myhome"></script>
</head>
<body>
	<div>
      <!-- Unnamed (Rectangle) -->
      <div id="u1658" class="ax_default label">
        <div id="u1658_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1659" class="text" style="visibility: visible;">
          <p><span>安全设置</span></p>
        </div>
      </div>

      <!-- Unnamed (Rectangle) -->
      <div id="u1660" class="ax_default box_1">
        <div id="u1660_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1661" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (Rectangle) -->
      <div id="u1662" class="ax_default label">
        <div id="u1662_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1663" class="text" style="visibility: visible;">
          <p><span>基础信息</span></p>
        </div>
      </div>

      <!-- Unnamed (Image) -->
      <div id="u1664" class="ax_default _图片">
        <img id="u1664_img" class="img " src="${ctx}/pages/images/securitywarn.png"/>
        <!-- Unnamed () -->
        <div id="u1665" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (Rectangle) -->
      <div id="u1666" class="ax_default label">
        <div id="u1666_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1667" class="text" style="visibility: visible;">
          <p><span>会员用户名：</span>
          <span style="margin-left: 20px">${user.account}</span></p>
          <br>
          <p style="margin-left: 12px"><span>手机号码：</span>
          <span style="margin-left: 20px">${user.mobile}</span>
          <c:if test="${user.isMobailTrue==1}">
          <a style="left: 410px;top: 35px;position: absolute;text-decoration: none" href="${ctx}/security/changeMobile.ht">更换手机</a>
          </c:if>
          <c:if test="${user.isMobailTrue==0 || user.isMobailTrue==null}">
          <a style="left: 410px;top: 35px;position: absolute;text-decoration: none" href="${ctx}/security/bindingMobile.ht">绑定手机</a>
          </c:if>
          </p>
          <br>
          <p style="margin-left: 12px"><span>电子邮箱：</span>
          <span style="margin-left: 20px">${user.email}</span>
          <c:if test="${user.isEmailTrue==1}">
           <a style="left: 410px;top: 67px;position: absolute;text-decoration: none" href="${ctx}/security/changeEmail.ht" >更换邮箱</a>
          </c:if>
          <c:if test="${user.isEmailTrue==0 || user.isEmailTrue==null}">
          <a style="left: 410px;top: 67px;position: absolute;text-decoration: none" href="${ctx}/security/validatingEmail.ht">验证邮箱</a>
          </c:if>
          </p>
        </div>
      </div>


      <!-- Unnamed (Rectangle) -->
      <div id="u1678" class="ax_default box_1">
        <div id="u1678_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1679" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (Rectangle) -->
      <div id="u1680" class="ax_default label">
        <div id="u1680_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1681" class="text" style="visibility: visible;">
          <p><span>安全服务</span></p>
        </div>
      </div>
 
     <!--  <div id="u1682" class="ax_default _文本段落">
        <div id="u1682_div" class=""></div>
        <div id="u1683" class="text" style="visibility: visible;">
          <p><span style="color:#FF0000;">页面说明：</span><span>如果邮箱或者手机已经验证，那么此处显示“修改邮箱”或者“修改手机”，如果邮箱或者手机未验证那么此处显示“验证邮箱”或者“绑定手机”，点击相关链接弹出验证修改页面。</span></p><p><span>绑定手机和验证邮箱为初次操作。</span></p>
        </div>
      </div>  -->

      <!-- Unnamed (Horizontal Line) -->
      <div id="u1694" class="ax_default line">
        <img id="u1694_img" class="img " src="${ctx}/pages/images/line.png"/>
        <!-- Unnamed () -->
        <div id="u1695" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (Rectangle) -->
     
     <div>
      <div id="u1698" class="ax_default label">
        <div id="u1698_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1699" class="text" style="visibility: visible;">
          <p><span>登录密码</span></p>
        </div>
      </div>

     
      <div id="u1704" class="ax_default label">
        <div id="u1704_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1705" class="text" style="visibility: visible;">
          <p><span>安全性高的密码可以使账号更安全。建议您定期更换密码，密码长度为6~16位，允许大小写字母、数字、下划线。</span></p>
        </div>
      </div>

      <!-- Unnamed (Rectangle) -->
      <div id="u1710" class="ax_default label">
        <div id="u1710_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1711" class="text" style="visibility: visible;">
          <p><span><a style="position: absolute;text-decoration: none" href="${ctx}/security/updatePwd.ht">修改密码</a></span></p>
        </div>
      </div>

      <!-- Unnamed (Image) -->
      <div id="u1724" class="ax_default _图片">
      <%--   <img id="u1724_img" class="img " src="${ctx}/pages/images/haveset.png"/> --%>
       	<!--   已设置 -->
       	  <img id="u1724_img" class="img " src="${ctx}/pages/images/haveset2.png"/>
       	  <span id="haveset">已设置</span>  
        <!-- Unnamed () -->
        <div id="u1725" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>
      </div>
      
      
       <!-- Unnamed (Image) -->
      <div id="u1720" class="ax_default _图片">
      	<c:if test="${user.isMobailTrue==1 }">
        <%-- <img id="u1720_img" class="img " src="${ctx}/pages/images/havebind.png"/> --%>
        <img id="u1720_img" class="img " src="${ctx}/pages/images/havebind1.png"/>
       		<span id="havebind">已绑定</span>  
        </c:if>
        <c:if test="${user.isMobailTrue==0 || user.isMobailTrue==null}">
        	<img id="u1720_img" class="img " src="${ctx}/pages/images/unbind.png"/>
       		<span id="havebind">未绑定</span> 
        </c:if>
        <!-- Unnamed () -->
        <div id="u1721" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>
      
       <div id="u1700" class="ax_default label">
        <div id="u1700_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1701" class="text" style="visibility: visible;">
          <p><span>手机绑定</span></p>
        </div>
      </div>
      
       <div id="u1706" class="ax_default label">
        <div id="u1706_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1707" class="text" style="visibility: visible;">
          <p><span>绑定手机后，您即可享受航天云网丰富的手机服务，如接收注册验证码找回密码等功能。</span></p>
        </div>
      </div>
      
      
      <div id="u1712" class="ax_default label">
        <div id="u1712_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1713" class="text" style="visibility: visible;">
          <%-- <p><span><a style="position: absolute;text-decoration: none" href="${ctx}/security/changeMobile.ht">更换手机</a></span></p> --%>
          <c:if test="${user.isMobailTrue==1 }">
          <p><span><a style="position: absolute;text-decoration: none" href="${ctx}/security/changeMobile.ht">更换手机</a></span></p>
          </c:if>
          <c:if test="${user.isMobailTrue==0 || user.isMobailTrue==null ||user.isMobailTrue==''}">
          <p><span><a style="position: absolute;text-decoration: none" href="${ctx}/security/bindingMobile.ht">绑定手机</a></span></p>
          </c:if>
        </div>
      </div>
      
       <div id="u1692" class="ax_default line">
        <img id="u1692_img" class="img " src="${ctx}/pages/images/line.png"/>
        <!-- Unnamed () -->
        <div id="u1693" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>
      
       <div id="u1722" class="ax_default _图片">
       <c:if test="${user.isEmailTrue==0 || user.isEmailTrue==null||user.isEmailTrue==''}">
       <%--  <img id="u1722_img" class="img " src="${ctx}/pages/images/novalidate.png"/> --%>
       <img id="u1722_img" class="img " src="${ctx}/pages/images/unbind.png"/>
       		<span id="havevalidate">未验证</span>
        </c:if>
        <c:if test="${user.isEmailTrue==1}">
        	<img id="u1722_img" class="img " src="${ctx}/pages/images/havevalidate.png"/>
       		<span id="havevalidate">已验证</span>
        </c:if>
        <!-- Unnamed () -->
        <div id="u1723" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>
      
      
      
       <div id="u1702" class="ax_default label">
        <div id="u1702_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1703" class="text" style="visibility: visible;">
          <p><span>邮箱验证</span></p>
        </div>
      </div>
      
      
       <div id="u1714" class="ax_default label">
        <div id="u1714_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1715" class="text" style="visibility: visible;">
          <%-- <p><span><a style="position: absolute;text-decoration: none" href="${ctx}/security/validatingEmail.ht">验证邮箱</a></span></p> --%>
          <c:if test="${user.isEmailTrue==1 }">
          <p><span><a style="position: absolute;text-decoration: none" href="${ctx}/security/changeEmail.ht">更换邮箱</a></span></p>
          </c:if>
          <c:if test="${user.isEmailTrue==0 || user.isEmailTrue==null }">
          <p><span><a style="position: absolute;text-decoration: none" href="${ctx}/security/validatingEmail.ht">验证邮箱</a></span></p>
          </c:if>
        </div>
      </div>
      
       <div id="u1708" class="ax_default label">
        <div id="u1708_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u1709" class="text" style="visibility: visible;">
          <p><span>是您验证身份的方式之一，也可以实时接收到航天云网的最新动态信息。</span></p>
        </div>
      </div>
      
      
      
        <div id="u1696" class="ax_default line">
        <img id="u1696_img" class="img " src="${ctx}/pages/images/line.png"/>
        <!-- Unnamed () -->
        <div id="u1697" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>
		
	</div>
</body>
</html>


