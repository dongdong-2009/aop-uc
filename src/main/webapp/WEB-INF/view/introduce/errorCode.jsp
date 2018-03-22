<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误码</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript">
$(function(){
	$(".api_left ul li").removeClass("active");
	$(".api_left ul li:first").addClass("active");
	$(".api_right div.api_items").show();
	$(".api_left ul li").each(function(index){
		
	  $(this).click(function(){
		  $(".api_right div.api_items").show();
		 $(".api_right div.api_items:lt("+index+")").hide();
		 $(this).addClass("active").siblings("li").removeClass("active");
		 })
	  })
	})
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
<div class="aopIndex_nav">
    <div class="gWidth">
        <div class="aopIndex_nav_c clearfix">
            <ul class="f_left aopIndex_nav_l">
                <li><a href="index.ht">首页</a></li>
                <li><a href="userGuide.ht">用户指南</a></li>
                <li><a href="joinStandard.ht" >接入规范</a></li>
                <li><a href="ucAPI.ht">用户中心API</a></li>
                <li ><a href="commonProblems.ht" >常见问题</a></li>
                <li ><a href="errorCode.ht" class="active">错误码</a></li>
                <li ><a href="community.ht" >社区</a></li>
            </ul>
        </div>
    </div>
</div>


<div class="api_box clearfix">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="errorCode.ht">错误码</a></div>
    <div class="api_right">
        <div class="api_items">
        	<h2>错误代码对照表</h2>
            <table border="0" cellpadding="0" width="100%">
              <tbody>
               <tr class="api_item_bt">
               <td width="15%" >错误编码</td>
               <td width="35%" >错误信息</td>
               <td width="50%" >解决方法</td>
               </tr> 
               <tr>
                <td>200</td>
                <td >成功</td>
                <td >成功</td>
               </tr>
               <tr>
                <td>210</td>
                <td>子系统Id不存在</td>
                <td>请传递正确的子系统id</td>
               </tr>
               <tr>
               <tr>
                <td>211</td>
                <td>用户会员名已存在</td>
                <td>更换用户会员名</td>
               </tr>
               <tr>
                <td>212</td>
                <td>用户手机号已存在</td>
                <td>更换手机号</td>
               </tr>
               <tr>
                <td>213</td>
                <td>注册码不存在</td>
                <td>请输入正确的注册邀请码</td>
               </tr>
               <tr>
                <td>214</td>
                <td>用户名或者密码错误</td>
                <td>请检查用户名、密码</td>
               </tr>
               <tr>
                <td>215</td>
                <td>远程服务出错</td>
                <td>请稍后重试</td>
               </tr>
               <tr>
                <td>220</td>
                <td>企业名称已存在</td>
                <td>请更换企业名称</td>
               </tr>
               <tr>
                <td>221</td>
                <td>工商注册号不匹配</td>
                <td>请更换工商注册号</td>
               </tr>
               <tr>
                <td>222</td>
                <td>组织机构代码不匹配</td>
                <td>请更换组织机构代码</td>
               </tr>
               <tr>
                <td>223</td>
                <td>纳税人识别号不匹配</td>
                <td>请更换纳税人识别号</td>
               </tr>
               <tr>
                <td>224</td>
                <td>法人代表不匹配</td>
                <td>请更换法人代表</td>
               </tr>
               <tr>
                <td>225</td>
                <td>法人身份证不匹配</td>
                <td>请更换法人身份证</td>
               </tr>
               <tr>
                <td>226</td>
                <td>法人手机号不匹配</td>
                <td>请更换法人手机号</td>
               </tr>
               <tr>
                <td>227</td>
                <td>身份认证未提交</td>
                <td>请提交身份认证，等待审核</td>
               </tr>
               <tr>
                <td>228</td>
                <td>身份认证未审核</td>
                <td>请等待审核</td>
               </tr>
               <tr>
                <td>229</td>
                <td>身份认证未审核通过</td>
                <td>请修改身份认证信息，重新提交，等待审核</td>
               </tr>
               <tr>
                <td>230</td>
                <td>请求参数data为空</td>
                <td>请求参数data为空，请检查请求参数</td>
               </tr>
               <tr>
                <td>231</td>
                <td>请求参数类型错误</td>
                <td>请传入正确的请求参数类型</td>
               </tr>
               <tr>
                <td>232</td>
                <td>上传图片数量超过限制</td>
                <td>请减少上传图片的数量</td>
               </tr>
               <tr>
                <td>233</td>
                <td>上传图片发生错误</td>
                <td>请刷新重试</td>
               </tr>
               <tr>
                <td>234</td>
                <td>上传图片超过最大限制</td>
                <td>请按要求上传指定大小的图片</td>
               </tr>
               <tr>
                <td>235</td>
                <td>信息不存在</td>
                <td>请传入正确的参数信息</td>
               </tr>
               <tr>
                <td>237</td>
                <td>未拥有权限</td>
                <td>请授权</td>
               </tr>
               <tr>
                <td>500</td>
                <td >未知错误</td>
                <td >未知错误,请稍后再试</td>
               </tr>
               <tr>
                <td>501</td>
                <td >参数key错误</td>
                <td >请输入所需要的key</td>
               </tr>
               <tr>
                <td>502</td>
                <td >用户无角色</td>
                <td >请授予角色</td>
               </tr>
               <tr>
                <td>503</td>
                <td >子系统不存在</td>
                <td >请传入正确的子系统参数</td>
               </tr>
               <tr>
                <td>507</td>
                <td >此用户不是管理员</td>
                <td >请传入正确的管理员Id数据</td>
               </tr>
               <tr>
                <td>509</td>
                <td >该企业未进行中金开户</td>
                <td >请进行中金开户</td>
               </tr>
              </tbody>
            </table>
        </div>
        
        <div class="api_items">
        	<!-- <div class="api2_rightTit">2.统一注册</div> -->
            <div class="api2_rightCon">
               <%--  <p class="api2_rightCon_ti">
                                               子系统调用用户中心统一注册接口时,需要拼上子系统的系统Id,这个标识将作为系统用户的注册来源。统一注册在用户中心分为三步。
                                               第一步 ：填写手机号和注册邀请码。(手机号必须唯一,注册邀请码即已注册用户的企业号)
                </p> 
                <div style="width:100%;height:386px;background:url('${ctx}/styles/uc/images/uc_zc1.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div>
                 <p class="api2_rightCon_ti"> 
                                               第二步 ：填写会员用户名,用户密码,公司名称以及公司联系人。(用户名和公司名称必须唯一)
                </p> 
                <div style="width:100%;height:498px;background:url('${ctx}/styles/uc/images/uc_zc2.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div>
                 <p class="api2_rightCon_ti"> 
                                               第三步 ：注册成功,跳转到统一登录界面.
                </p> 
                <div style="width:100%;height:372px;background:url('${ctx}/styles/uc/images/regSuccess.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div> --%>
        </div>
        </div>
        <div class="api_items">
        	<!-- <div class="api2_rightTit">3.统一登录</div> -->
            <div class="api2_rightCon">
               <%--  <p class="api2_rightCon_ti">
                                                     用户统一登录时有两种登录方式,第一种通过手机号登录,第二种通过企业号的登录方式.
                </p> 
                <p class="api2_rightCon_ti">
                                                      第一种：用户通过手机号方式进行登录.
                </p> 
                <div style="width:100%;height:461px;background:url('${ctx}/styles/uc/images/uc_dl.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div>
                <p class="api2_rightCon_ti">
                                                      第二种：用户通过企业号方式进行登录.
                </p> 
                <div style="width:100%;height:399px;background:url('${ctx}/styles/uc/images/uc_dl2.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div> --%>
            </div>
        </div>
        
        <div class="api_items">
        	<!-- <div class="api2_rightTit">4.统一维护</div> -->
            <div class="api2_rightCon">
                <%-- <p class="api2_rightCon_ti">
                                            已经在用户中心完成注册的企业用户,可以在用户中心后台对自己的企业信息进行维护,用户登录后台时需要传入在注册用户时产生的openId。
                                           系统 统一的维护包含有8大子模块，分别为：账号管理,基本信息,认证信息,开户信息,组织信息,统计信息,安全设置,收发货地址.                          
                </p> 
                <div style="width:100%;height:715px;background:url('${ctx}/styles/uc/images/uc_wh.png') no-repeat center;background-size:100% 100%;margin:20px auto 20px;"></div> --%>
            </div>
        </div>     
    </div>
</div>


<!--casicloud footer begin-->
<%@ include file="/commons/footer.jsp" %>

<!--casicloud footer end-->


</body>
</html>