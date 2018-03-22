//【新闻自动滚动】
window.onload=(function(){
	
	if( $('#notice2').size()==0 ) return false;
	var timer=null;
	function autoscroll(obj){
		$(obj).find("ol:first").animate({
			marginTop:"-34px"
		},500,function(){
			$(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
		});
	}
	
	document.getElementById('notice2').onmouseover=function(){
		window.clearInterval(timer);
	}
	
	document.getElementById('notice2').onmouseout=function(){
		timer=setInterval(function(){autoscroll("#notice2")},1500);
	}
	
	timer=setInterval(function(){autoscroll("#notice2")},1500);
})

$(function(){
	var timer=0;
	var timer2=0;
	//会员信息展示
	$('#username').hover(function(){
		timer=setTimeout(function(){$('#userinfobox').show()},300);
	},function(){
		window.clearTimeout(timer);
	})
	$('#userinfobox,#head').mouseleave(function(){
		$('#userinfobox').hide();
	})
	
	//发消息事件绑定
	$('#textcontent').keydown(function(e){
		var e = e?e:window.event;
		if(e.ctrlKey && 13==e.keyCode){
			alert('你输入的内容是\n'+$(this).val())
		   this.form.submit();
		}
	})
	
	
	//生产能力
	$('.tag_list .tag').mouseover(function(){
		$(this).addClass('on');
	})
	$('.tag_list .tag').mouseleave(function(){
		$(this).removeClass('on');
	})
	
})

