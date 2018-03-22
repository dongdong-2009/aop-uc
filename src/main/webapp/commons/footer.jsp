<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<style>
.clound_footer_web span {
margin-right:10px;
}
</style>
<script type="text/javascript">
$(function(){
	var systemId  =  '${systemId}';
	if(systemId==""||systemId==null){
		systemId = 100;
	} 
	/* if(systemId==100){
		var div = document.getElementById("clound_footer");
		div.style.display="block";
	}else */
	if(systemId==10000054556562){
		var div = document.getElementById("gzgyy_footer");
		div.style.display="block";
	}else if(systemId==10000062500576){
		var div = document.getElementById("jxgyy_footer");
		div.style.display="block";
	}else if(systemId==10000050010085){
		var div = document.getElementById("scjm_footer");
		div.style.display="block";
	}
	else{
		var gz = getCookieName("saas");
		if(gz == "gz"){
			var div = document.getElementById("gzgyy_footer");
			div.style.display="block";
		}else{
			var div = document.getElementById("clound_footer");
			div.style.display="block";
		}
	}
}) 


function getCookieName(cookie_name){
    var allcookies = document.cookie;
    var cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度
 
    // 如果找到了索引，就代表cookie存在，
    // 反之，就说明不存在。
    if (cookie_pos != -1)
    {
        // 把cookie_pos放在值的开始，只要给值加1即可。
        cookie_pos += cookie_name.length + 1;      //这里容易出问题，所以请大家参考的时候自己好好研究一下
        var cookie_end = allcookies.indexOf(";", cookie_pos);
 
        if (cookie_end == -1)
        {
            cookie_end = allcookies.length;
        }
 
        var value = unescape(allcookies.substring(cookie_pos, cookie_end));         //这里就可以得到你想要的cookie的值了。。。
    }
    return value;
}
</script> 
<div class="clound_footer" id="clound_footer" style="display: none" >
            <!-- <div class="clound_footer_abount">
                <a href="javascript:void(0)">法律声明</a>
                <a href="javascript:void(0)">关于我们</a>
                <a href="javascript:void(0)">人才招聘</a>
                <a href="javascript:void(0)">友情链接</a>
                <a href="javascript:void(0)" class="footer_link">联系我们</a>
            </div>
            <div class="clound_footer_magz">
                <span>客服电话(周一至周五8:30-17:30)</span>
                <span>航天云网：400-857-6688</span>
                <span>云资源中心：400-062-1553</span>
                <span>专有云：4000680022</span>
            </div> -->
            <div class="clound_footer_web">
                <span>Copyright © 2015, All Rights Reserved　京ICP备05067351号-2</span>
                <span class="footLink">法律声明 |</span>
                <span class="footLink">站点地图 |</span>
                <span class="footLink">诚聘英才</span>
            </div>
  </div>
<div class="clound_footer" id="gzgyy_footer" style="display: none">
            <div class="clound_footer_web">
                <span>Copyright © 2016贵州工业云,All Rights Reserved | 备案号: 黔ICP备16002195号-1</span>
            </div>
  </div>
  <div class="clound_footer" id="jxgyy_footer" style="display: none">
            <div class="clound_footer_web">
                <span>Copyright2018 版权所有 江西航天云网科技有限公司 备案序号：赣ICP备案16001899号-5</span>
            </div>
  </div>
<div class="clound_footer" id="scjm_footer" style="display: none">
            <div class="clound_footer_web">
                <span>Copyright@2016四川军民融合,All Rights Reserved 蜀ICP备16029625号-1</span>
            </div>
  </div>