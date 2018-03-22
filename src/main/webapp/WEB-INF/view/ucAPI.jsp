<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心API</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/css/ucAPI.css" />
<script type="text/javascript">
  var CTX="${pageContext.request.contextPath}";
</script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/introduce/ucAPI.js"></script>


</head>
<body>
<%@ include file="/commons/top.jsp" %>
<%@ include file="/commons/navigator.jsp"  %>

<div class="api_box clearfix">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="ucAPI.ht">用户中心API</a></div>
    <div class="api_left">
    	<h2>所有类目</h2>
        <ul>
        	<li class="active"><a href="javascript:void(0);">子系统调用API</a></li>
        	<li ><a href="javascript:void(0);">在线Post和Get</a></li>
        </ul>
    </div>
    <div class="api_right">
    	<div class="api_items" style="display:block;">
        	<h2>子系统调用API</h2>
        	<h6>子系统需注册成会员，并注册子系统才可使用该接口</h6>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            	<tr class="api_item_bt">
                	<!-- <td width="37%">API列表</td>
                    <td width="10%">类型</td>
                    <td width="53%">描述</td> -->
                    <td width="47%">API列表</td>
                    <td width="53%">描述</td>
                </tr>
                <!-- <tr>
                	<td>enterprise/register</td>
					<td><span class="api_item_red">收费</span></td>
                    <td>获取一个产品的信息</td>
                </tr> -->
                <tr>
					<td><a href="ucAPIIntroduction1.ht">/api/registerNoBack</a></td>
					<td>用于子系统传递注册用户数据</td>
				</tr>
				<tr>
					<td><a href="ucAPIIntroduction2.ht">/api/getOrgInfoByOid</a></td>
					<td>通过子系统的唯一标识获取该企业的数据</td>
				</tr>
				<tr>
					<td><a href="ucAPIIntroduction3.ht">/api/getOrgInfoByOrgId</a></td>
					<td>通过企业ID获得该企业的数据信息</td>
				</tr>
				<tr>	
					<td><a href="ucAPIIntroduction4.ht">/api/getUserInfoByAcc</a></td>
					<td>子系统通过用户账户获得用户信息</td>
				</tr>
				<tr>	
					<td><a href="ucAPIIntroduction5.ht">/api/getUserInfoByOid</a></td>
					<td>子系统通过用户的唯一标识来获取用户数据</td>
				</tr>
				<tr>	
					<td><a href="ucAPIIntroduction6.ht">/api/getRoleByUser</a></td>
					<td>子系统通过用户的ID获取该用户下的角色信息</td>
				</tr>
				<tr>	
					<td><a href="ucAPIIntroduction7.ht">/api/getRuleByRole</a></td>
					<td>子系统通过角色ID获取该角色下的权限信息</td>
				</tr>
				<tr>	
					<td><a href="ucAPIIntroduction8.ht">/api/getUserByRole</a></td>
					<td>子系统通过角色ID获取该角色下的用户信息</td>
				</tr>
				<tr>	
					<td><a href="ucAPIIntroduction9.ht">/api/isAdmin</a></td>
					<td>子系统通过用户ID判断此用户是否是管理员</td>
				</tr>
				<tr>	
					<td><a href="ucAPIIntroduction10.ht">/api/getOrgByOrgId</a></td>
					<td>子系统通过企业ID获得该企业的组织关系</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntroduction11.ht">/api/getOrgInfoAndManagerByOrgId</a></td>
					<td>子系统通过企业ID获得该企业的信息并返回该企业的一个管理员信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntroduction12.ht">/api/getBranchByOri</a></td>
					<td>子系统通过企业ID获得该企业的开户信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro1.ht">/api/getSysRoleBySysId</a></td>
					<td>根据子系统Id查询子系统所具有的角色信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro2.ht">/api/getSysResBySysId</a></td>
					<td>根据子系统Id获得该子系统下的所有资源信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro3.ht">/api/addResForRole</a></td>
					<td>给角色分配资源权限</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro4.ht">/api/getResRolById</a></td>
					<td>根据Id获取用户资源的详情</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro5.ht">/api/updateResoById</a></td>
					<td>修改角色下的资源信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro6.ht">/api/getRessByRole</a></td>
					<td>根据角色查询该角色下的所有的资源信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro7.ht">/api/delRes</a></td>
					<td>删除角色下的资源信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro8.ht">/api/getResByParentId</a></td>
					<td>根据父ID查询该父ID下的资源信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro9.ht">/api/getResById</a></td>
					<td>通过资源ID获取资源信息</td>                 
                </tr>
                <tr>	
					<td><a href="ucAPIIntro10.ht">/api/isOpen</a></td>
					<td>启用、禁用资源</td>                 
                </tr>
            </table>
        </div>
        
    	<div class="api_items">
    	  <div style="width:90%;">
        	<h3>GET和POST测试(支持需要登录的接口调用:高级功能-&gt;填写cookie)</h3>
        	<br>
        	接口:<textarea rows="2" class="form-control" name='url' id="url"></textarea>
           <span style="color:#ccc;font-size:12px">例：http://coolaf.com/m?a=xx&amp;b=xx；get参数直接加在url后就行</span>
           <br>
           <div id="disparms">
               post参数:<textarea rows="2" class="form-control" name="parms" id="parms"></textarea>
               <span style="color:#ccc;font-size:12px">参数例：a=b&amp;c=d&amp;f=e,get和head方式除外</span>
               <button type="button" id="highbtn" class="btn btn-success" style="width:20%;height:20px;font-size:10px;padding:0px 0px;float:right;margin-top:3px;margin-right:10px">显示高级功能</button>
           </div>
           <div>
				<select name="seltype" id="seltype" class="form-control" style="margin-top:14px;float:left;width:49%">
				<option value="post">POST</option>
				<option value="get">GET</option>
				</select>
				<select name="code" id="code" class="form-control" style="margin-top:10px;margin-left:10px;float:left;width:49%">
				<option value="utf8">UTF-8 --接口输出的编码</option>
				<option value="gbk">GBk --接口输出的编码</option>
				</select>
				<br>
			</div>
           <button type="button" id="btn" style="width:72%;height:40px;margin-top:5px;margin-button:20px">提交</button>
           <button type="button" id="httptest" style="width:13%;height:40px;margin-top:5px;margin-button:20px">测试示例</button>
           <button type="button" id="clearform" style="width:13%;height:40px;margin-top:5px;margin-button:20px">清空表单</button>
            <div style="width:90%;min-height:200px;margin-right:20px;margin-top:20px">
				<ul class="nav nav-tabs" role="tablist">
				  <li role="presentation" class="active" id="rspdis" ><a href="javascript:void(0);">Response</a></li>
				 <!--  <li role="presentation" id="headdis"><a href="javascript:void(0);">Headers</a></li> -->
				</ul>
				
				<pre id="getresult" style="min-height:360px;word-break:break-all;background-color:#F5F5F5;border: 1px solid #ccc"></pre>
				<p id="getresultheader" style="display:none;min-height:360px"></p>
             </div>

          </div>
       </div> 
        
        
        
        
    </div>
</div>


<!--casicloud footer begin-->
<%@ include file="/commons/footer.jsp" %>

<!--casicloud footer end-->

<script type="text/javascript" src="${ctx}/js/navigator.js"></script>
</body>
</html>