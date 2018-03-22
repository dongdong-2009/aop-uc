<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/commons/global.jsp"%> --%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心API</title>
<link href="${ctx}/styles/uc/reset_aop.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/uc/aop_chh.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css/cloudFooterheader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript">
$(function(){
	$(".api_left ul li").each(function(index){
	  $(this).click(function(){
		 $(".api_right div.api_items").hide();
		 $(".api_right div.api_items").eq(index).slideDown();
		 })
	  })
	})
$(function(){
	  $(".api_rightCon_tit").each(function(index){
	        $(this).toggle(
	           function(){
	              $(this).siblings(".api_right_style").slideUp();
	              $(this).find('.triangle').removeClass('open');
	           },
	           function(){
	              $(this).siblings(".api_right_style").slideDown();
	              $(this).find('.triangle').addClass('open');
	           })
	  	})
	})	
	

</script>
<style type="text/css">
.api_left ul li:hover, .api_left ul li.active { 
	color: #0089cd; 
	background-color: #ddedf4; 
	text-decoration: none;}
.api_left ul li:hover a, .api_left ul li.active a { color: #0089cd; }
.api_rightTit { font-size: 22px; height: 60px; }
.api_rightCon_tit { padding-top: 12px;padding-bottom: 12px; margin-top: 25px; text-indent: 30px; position: relative; cursor: pointer; border-bottom: 1px solid #999;font-size: 20px; margin-bottom: 20px;}
.api_rightCon_tit .triangle { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: transparent transparent transparent #666; position: absolute; left: 13px; top: 20px;}
.api_rightCon_tit .triangle.open { width: 0; height: 0; border-style: solid; _border-style: dotted; border-width: 5px; border-color: #666 transparent transparent transparent; top: 20px; left: 10px; }
.api_rightCon_tit:hover{background:#eee;}
.api_right_simple_param{height:100px;background:#eee;font-size:12px;text-align:left;}
.api_right_simple_param_1{height:300px;background:#eee;font-size:12px;text-align:left;}
.api_right_text a:hover{color:#0089cd;}
.api_bread { height: 38px; overflow: hidden; line-height: 38px; color: #323232; padding: 0 5px; margin-left: 90px;}
.api_left { float: left; display: inline; width: 220px; min-height: 344px; overflow: hidden; background: #f5f5f5; margin-left: 90px;}
.api_right { margin-left: 330px; min-height: 700px; }
</style>
</head>
<body>

<%@ include file="/commons/top.jsp" %>
<%@ include file="/commons/navigator.jsp"  %>

<div class="api_box clearfix">
	<div class="api_bread"><a href="index.ht">首页</a> &gt; <a href="ucAPI.ht">用户中心API</a> &gt; <a href="ucAPIIntro4.ht">子系统调用API</a> </div>
    <div class="api_left">
    	<h2>子系统调用API</h2>
        <ul>
        	<li class="active"><a href="javascript:void(0);">/api/getResRolById</a></li>
        </ul>
    </div>
   <div class="api_right">
      <div class="api_items" style="display:block;">
      		<div class="api_rightTit">/api/getResRolById</div>
      			<h6>根据角色资源Id获取该其具有的资源和角色信息</h6>
      			<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 调用URL
			        </div>
	        		<p class="api_right_text api_right_style">/api/getResRolById.ht</p>
	       		 </div>
	      		<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 请求方式
			        </div>
	        		<p class="api_right_text api_right_style">Post</p>
	       		 </div>
      			 <div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 请求参数
			        </div>
			        <div class="api_right_style">
				        <table border="0" cellpadding="0" cellspacing="0" width="100%">
					        <tr class="api_item_bt">
					        	<td width="15%">名称</td>
					        	<td width="10%">类型</td>
					        	<td width="10%">是否必须</td>
					        	<td width="65%">描述</td>
					        </tr>
					        <tr>
					        	<td>systemId</td>
					        	<td>String</td>
					        	<td>是</td>
					        	<td>子系统id</td>
					        </tr>
					        <tr>
					        	<td>data</td>
					        	<td>String</td>
					        	<td>是</td>
					        	<td>参数数据</td>
					        </tr>
				        </table>
			        </div>
		        </div>
      			 <div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 返回参数
			        </div>
			        <div class="api_right_style">
				        <table border="0" cellpadding="0" cellspacing="0" width="100%">
					        <tr class="api_item_bt">
					        	<td width="15%">名称</td>
					        	<td width="10%">类型</td>
					        	<td width="75%">描述</td>
					        </tr>
					        <tr>
					        	<td>success</td>
					        	<td>Boolean</td>
					        	<td>是否成功</td>
					        </tr>
					        <tr>
					        	<td>error</td>
					        	<td>String</td>
					        	<td>错误信息</td>
					        </tr>
					        <tr>
					        	<td>errorCode</td>
					        	<td>String</td>
					        	<td>错误编号</td>
					        </tr>
				        </table>
			        </div>
		        </div>
      			<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 请求参数示例
			        </div>
		        <textarea class="api_right_simple_param_1 api_right_style" style="width:100%;display:inline-block;">{
systemId:子系统id,
data: {
    "roleResId": "资源角色id"  
	}
}</textarea>
        	</div>
      			<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 调用示例
			        </div>
		        <textarea class="api_right_simple_param_1 api_right_style" style="width:100%;display:inline-block;">{
public class TestDemo{
	public static void main(String args[]) {
		String url = "http://aop.casicloud.com/api/getResRolById.ht";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemId", 100);		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("roleResId", "153495");	
		params.put("data", data);
		String result = "";
		try {
			result = JsonPostInvoke(url, params);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println(result);
	}
	public static String JsonPostInvoke(String url, Map<String, Object> params) 
		throws ClientProtocolException, IOException{  
        System.out.println("post方式提交json数据开始......"); 
        JSONObject jsonParam = new JSONObject();  
        // http客户端
        HttpClient httpClient = buildHttpClient(false);
        // post请求
        HttpPost method = new HttpPost(url);  
        if(null != params){
        	for(String key : params.keySet()){
        		jsonParam.put(key, params.get(key));
        	}
        	// 参数实体
        	StringEntity entity = new StringEntity(jsonParam.toString(), CHARSET_UTF8);//解决中文乱码问题    
        	entity.setContentEncoding(CHARSET_UTF8);    
            entity.setContentType(CONTENT_TYPE_JSON_CHARSET_UTF8);    
            method.setEntity(entity);  
        }
        // 执行响应操作
        HttpResponse result = null;
        String data="";
		try {
			result = httpClient.execute(method);
			data= EntityUtils.toString(result.getEntity()); 
		} catch (Exception e) {
			data="请求错误";
			e.printStackTrace();
	}  
       // 请求结束，返回结果  	       
       System.out.println(data);
       System.out.println("post方式提交json数据结束......");
       return data;
	} 
}	
</textarea>
        	</div>
      			<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		 返回结果示例
			        </div>
		        <textarea class="api_right_simple_param api_right_style" style="width:100%;display:inline-block;">
{
"success":true,
"code":"100001",
"msg":资源详情及角色详情
}

</textarea>
        	</div>
      			<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		描述
			        </div>
	        		<p class="api_right_text api_right_style">根据资源角色Id获取该Id下的角色信息和资源信息</p>
	       		 </div>
      			<div class="api_rightCon">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		注意事项
			        </div>
	        		<p class="api_right_text api_right_style">msg获取角色信息和资源信息的集合，使用role从集合中获取角色信息，使用res从集合中获取资源信息</p>
	       		 </div>
      			<div class="api_rightCon" style="margin-bottom:20px;">
			        <div class="api_rightCon_tit">
				        <i class="triangle open"></i>
				       		错误代码
			        </div>
	        		<p class="api_right_text api_right_style">关于错误返回值与错误代码，参见<a href="errorCode.ht">错误代码说明</a></p>
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