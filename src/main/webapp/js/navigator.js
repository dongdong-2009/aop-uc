var currentURL=window.location.href;
	if(currentURL.indexOf("index")>0){
		$("#dd").find("li").each(function(){
			if($(this).find("a").attr("href").indexOf("index")>0){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			}else{
				$(this).find("a").removeClass("active");
			}
			
		});
	}else if(currentURL.indexOf("userGuide")>0){
		$("#dd").find("li").each(function(){
			if($(this).find("a").attr("href").indexOf("userGuide")>0){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			}else{
				$(this).find("a").removeClass("active");
			}
			
		});
	}else if(currentURL.indexOf("joinStandard")>0){
		$("#dd").find("li").each(function(){
			if($(this).find("a").attr("href").indexOf("joinStandard")>0){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			}else{
				$(this).find("a").removeClass("active");
			}
			
		});
	}else if(currentURL.indexOf("ucAPI")>0){
		$("#dd").find("li").each(function(){
			if($(this).find("a").attr("href").indexOf("ucAPI")>0){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			}else{
				$(this).find("a").removeClass("active");
			}
			
		});
	}else if(currentURL.indexOf("commonProblems")>0){
		$("#dd").find("li").each(function(){
			if($(this).find("a").attr("href").indexOf("commonProblems")>0){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			}else{
				$(this).find("a").removeClass("active");
			}
			
		});
	}else if(currentURL.indexOf("errorCode")>0){
		$("#dd").find("li").each(function(){
			if($(this).find("a").attr("href").indexOf("errorCode")>0){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			}else{
				$(this).find("a").removeClass("active");
			}
			
		});
	}else if(currentURL.indexOf("community")>0){
		$("#dd").find("li").each(function(){
			if($(this).find("a").attr("href").indexOf("community")>0){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			}else{
				$(this).find("a").removeClass("active");
			}
			
		});
	}