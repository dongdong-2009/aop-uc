<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>  -->
    <title>用户中心</title>
    <link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
    <script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
</head>
<body>
     <%--  <%@ include file="/commons/global.jsp"%> --%>
     <input type="hidden" id="systemId" name="systemId" value="${info.systemId}"/>
      <%@ include file="/commons/top.jsp"  %>
      <%@ include file="/commons/navigator.jsp"  %>
    <div class="aopIndex_cont">
        <div class="aopIndex_bigImg">
           <%--  <img src="${ctx}/styles/uc/images/aopIndex_bigImg.jpg" alt=""/> --%>
            <img src="${ctx}/styles/uc/images/uc_d.jpg" alt=""/>
        </div>
        <div class="aopIndex_cont1 ">
            <div class="gWidth">
                <div class="aopIndex_cont_title">
                    <div class="aopIndex_cont_title_hd">
                        <h2>UC介绍</h2>
                    </div>
                    <div class="aopIndex_cont_title_bd">

                        <div class="aopIndex_cont_title_icon">
                            <span class="icon_title1"></span>
                        </div>
                        <div class="aopIndex_cont_title_xian"></div>
                    </div>
                </div>
                <div class="aopIndex_cont_content">
                    <div class="aopIndex_cont_content_hd">
                        <!-- <p> 覆盖用户/企业管理、供需对接交易等航天云网核心业务。</p>
                        <p>提供对基础类数据（用户、企业等）及业务类数据（需求、能力、订单等）的操作接口。 </p>
                        <p>支持HTTP、Java SDK等多种二次开发形式。</p> -->
                        <p>
                        	UC 的中文意思就是“用户中心”，其中的 U 代表 User，C代表 Center。</p>
<p>UC 是今后航天云网旗下各个产品之间信息直接传递的一个桥梁。通过 UC，可以无缝整合航天系列产品，</p>
<p>甚至更多的第三方应用，实现用户的一站式登录和企业信息、个人信息等资源的统一管理。
                        </p>
                    </div>
                    <div class="aopIndex_cont_content_bd">
                        <h2>功能介绍</h2>
                    </div>
                    <div class="aopIndex_cont_content_ft">
                        <ul class="clearfix">
                            <li>
                                <a href="javascript:void(0);"><img src="${ctx}/styles/uc/images/tyzc.png" alt=""/></a>
                                <p>为航天云网以及各子系统提供统一的注册入口，用户中心可以将用户、企业数据同步到各子系统</p>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="${ctx}/styles/uc/images/tydl.png" alt=""/></a>
                                <p>航天云网以及各子系统采用统一的登录方式，一点登录到处可用，方便系统登录管理</p>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="${ctx}/styles/uc/images/tywh.png" alt=""/></a>
                                <p>各子系统的核心数据由用户中心统一管理，用户中心可将用户、企业、认证信息等数据的操作同步到各子系统</p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="aopIndex_cont2">
            <div class="gWidth">
                <div class="aopIndex_cont_title">
                    <div class="aopIndex_cont_title_hd">
                        <h2>已接入系统</h2>
                        <span>systems </span>
                    </div>
                    <div class="aopIndex_cont_title_bd">

                        <div class="aopIndex_cont_title_icon">
                            <span class="icon_title2"></span>
                        </div>
                        <div class="aopIndex_cont_title_xian"></div>
                    </div>
                </div>
                <div class="aopIndex_cont_content">
                    <div class="aopIndex_cont_content_c">
                        <ul class="clearfix">
                            <li><a href="javascript:void(0);">
                                    <span class="aopIndex_cont_content_chd">
                                    <strong>航天云网</strong>
                                        <em class="icon_cont1"></em>
                                    </span>
                                     <span class="aopIndex_cont_content_cbd">
                                        <i></i>
                                        <em>以“信息互通、资源共享、能力协同、开放合作、互利共赢”为核心理念，以“互联网+智能制造”为发展方向</em>
                                    </span>
                                </a>
                            </li>
                            <li class=""><a href="javascript:void(0);">
                                    <span class="aopIndex_cont_content_chd">
                                        <strong>贵州工业云</strong>
                                        <em class="icon_cont2"></em>
                                    </span>
                                    <span class="aopIndex_cont_content_cbd">
                                        <i></i>
                                        <em> 以智能制造、工业互联网为突破口,打造“政府的好助手、企业的云超市、工业的互联网”</em>
                                    </span>
                                </a>
                            </li>
                            <li><a href="javascript:void(0);">
                                    <span class="aopIndex_cont_content_chd">
                                        <strong>横沥模具产业云专区</strong>
                                        <em class="icon_cont3"></em>
                                    </span>
                                    <span class="aopIndex_cont_content_cbd">
                                        <i></i>
                                        <em>供需对接、品牌展示、双创服务，高品质模具生产</em>
                                    </span>
                                </a>
                            </li>
                            <li><a href="javascript:void(0);">
                                    <span class="aopIndex_cont_content_chd">
                                        <strong>康居</strong>
                                        <em class="icon_cont4"></em>
                                    </span>
                                    <span class="aopIndex_cont_content_cbd">
                                        <i></i>
                                        <em>让沟通更便捷、让生产更简单、让一切触手可及</em>
                                    </span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>


            </div>
        </div>
        <div class="aopIndex_cont3">
            <div class="gWidth">
                <div class="aopIndex_cont_title">
                    <div class="aopIndex_cont_title_hd">
                        <h2>接入流程</h2>
                        <span>Access flow</span>
                    </div>
                    <div class="aopIndex_cont_title_bd">
                        <div class="aopIndex_cont_title_icon">
                            <span class="icon_title3"></span>
                        </div>
                        <div class="aopIndex_cont_title_xian"></div>
                    </div>
                </div>
                <div class="aopIndex_cont_content">
                    <div class="aopIndex_cont_content_flow">
                       <%--  <img src="${ctx}/styles/uc/images/aopIndex_013.png" alt=""/> --%>
                        <div style="height:49px;width: 100%;">
  							<ul style="margin:0px; border-bottom:2px solid #dfdfdf;width:100%;line-height:49px; font-size:20px;height:49px;font-weight: bold;">
    							<li style="float:left;margin-left:18px"><span style="color:#a8d4e5;font-size:40px;font-family: inherit;">01</span>&nbsp;注册子系统</li>
								<li style="float:left;margin-left:160px"><span style="color:#a8d4e5;font-size:40px;font-family: inherit;">02</span>&nbsp;统一注册</li>
								<li style="float:left;margin-left:180px"><span style="color:#a8d4e5;font-size:40px;font-family: inherit;">03</span>&nbsp;统一登录</li>
								<li style="float:left;margin-left:180px"><span style="color:#a8d4e5;font-size:40px;font-family: inherit;">04</span>&nbsp;统一维护</li>
      
  							</ul>


						</div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    
<!--casicloud footer begin-->
<%@ include file="/commons/footer.jsp"  %>
<!--casicloud footer end-->    
    <script type="text/javascript">

            $(function(){
                $(".aopIndex_cont_content_c").find("li").hover(function(){
                    $(this).addClass("active").siblings().removeClass("active");
                },function(){
                    $(this).removeClass("active");
                })

            })

    </script>
<script type="text/javascript" src="${ctx}/js/navigator.js"></script>

</body>
</html>