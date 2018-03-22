<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">



$(function(){
	/* var systemId  =  ${systemId}
	if(systemId==100){
		var div = document.getElementById("clound_logo");
		div.style.display="block";
	}else if(systemId==10000054556562){
		var div = document.getElementById("gzgyy_logo");
		div.style.display="block";
	}else{
		var div = document.getElementById("clound_logo");
		div.style.display="block";
	}  */
	
	var systemId  =  '${systemId}';
	//alert(systemId)
	if(systemId==""||systemId==null){
		systemId = 100;
	}  
	if(systemId==10000054556562){
		$("#newLogo").addClass('gzgyy');
		$("#href").attr("href","http://share.gz-icloud.com.cn");
	}else if(systemId==10000061305128){
		$("#newLogo").addClass('jyw');
		//$("#href").attr("href","http://share.gz-icloud.com.cn");
	}else if(systemId==10000061305130){
		$("#newLogo").addClass('hbgyy');
		$("#href").attr("href","http://hb.casicloud.com");
	}else if(systemId==10000062500576){
		$("#newLogo").addClass('jxgyy');
		$("#href").attr("href","http://www.jxicloud.com");
	}else if(systemId==10000050010085){
		$("#newLogo").addClass('scjm');
		$("#href").attr("href","http://www.scjmrh.org");
	}
	else{
		var gz = getCookieName("saas");
		if(gz == "gz"){
			$("#newLogo").addClass('gzgyy');
			$("#href").attr("href","http://share.gz-icloud.com.cn");
		}else{
			$("#newLogo").addClass('htyw');
			$("#href").attr("href","http://www.casicloud.com");
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
<div class="clound_header_box">
            <div class="clound_header">
               <a href="" id="href">
               	 <h1  id="newLogo">
                   	 <span>航天云网</span>
                </h1>
    	           </a>
                <i class="f_left"></i>
                <span class="f_left clound_header_safe">企业注册</span>
            </div>
        </div>
<!-- <div class="clound_header_box" id="gzgyy_logo" style="display: none">
            <div class="clound_header">
            	<a href="http://share.gz-icloud.com.cn/">
            		<h1 class="clound_header_gzgyy_logo f_left">
                    	<span>贵州工业云</span>
               	 	</h1>
            	</a>
                <i class="f_left"></i>
                <span class="f_left clound_header_safe">企业注册</span>
            </div>
        </div> -->