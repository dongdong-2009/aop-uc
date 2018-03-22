(function() {
	var params = {};
	var _maq = _maq || []; _maq.push(['system', 'UC']);
	// Document对象数据 
	if(document)
	{
		params.domain = document.domain || '';
		params.url = document.URL || '';
		params.title = encodeURI(document.title) || '';
		params.referrer = document.referrer || '';
	} // Window对象数据
	if (window && window.screen) {
		params.sh = window.screen.height || 0;
		params.sw = window.screen.width || 0;
		params.cd = window.screen.colorDepth || 0;
	} // navigator对象数据
	if (navigator) {
		params.lang = navigator.language || '';
	} // 解析_maq配置
	if (_maq) {
		for ( var i in _maq) {
			switch (_maq[i][0]) {
			case 'system':
				params.system = _maq[i][1];
				break;
			default:
				break;
			}
		}
	} // 拼接参数串
	var args = '';
	for ( var i in params) {
		if (args != '') {
			args += '&';
		}
		args += i + '=' + encodeURIComponent(params[i]);
	}
	// 通过Image对象请求后端脚本
	//var img = new Image(1, 1);
	//img.src = 'http://analytics.codinglabs.org/1.gif?' + args;
	var img = new Image(1, 1);
	img.style.display="none";
    img.src = 'http://xin.caicloud.com:1027/log/save.ht?' + args;
    document.body.appendChild(img);
    /*var url = 'http://172.17.70.238:1027/xin.casicloud.com/log/save.ht?'+args;
	$.ajax({
		url:url,
		async:false
		});*/
})();