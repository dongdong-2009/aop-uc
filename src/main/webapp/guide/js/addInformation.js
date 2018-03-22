var oUL=document.getElementById("oUL")
	var txt=document.getElementById("chattxt");
	var btn=document.getElementById("chatbtn");
	
	document.onkeypress=function(event){
		var event=event||window.event;
		var code=event.keyCode||event.which
		if(code==13){
			fn()
		}
	}
	btn.onclick=function(){
		fn()
	}

function fn(){
		
			var str=txt.value;
			var li=document.createElement("li");
			oUL.appendChild(li);
			li.innerHTML=str;
			txt.value="";
			li.scrollIntoView();

	}