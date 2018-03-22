	var zn_hot_sc_temp_arr = new Array();
	var zn_hot_sc_mark = 0;
	
	(function($) {

		$.suggest = function(input, options,temp,modObj,mark) {
	
			var $input = $(input).attr("autocomplete", "off");
			var $results;

			var timeout = false;		// hold timeout ID for suggestion results to appear	
			var prevLength = 0;			// last recorded length of $input.val()
			var cache = [];				// cache MRU list
			var cacheSize = 0;			// size of cache in chars (bytes?)
			
			if($.trim($input.val())=='' || $.trim($input.val())=='中文/拼音') $input.val('中文/拼音').css('color','#aaa');
			if( ! options.attachObject )
				options.attachObject = $(document.createElement("ul")).appendTo('body');

			$results = $(options.attachObject);
			$results.addClass(options.resultsClass);
			
			resetPosition();
			$(window)
				.load(resetPosition)		// just in case user is changing size of page while loading
				.resize(resetPosition);

			$input.blur(function() {
				setTimeout(function() { $results.hide() }, 200);
			});
			
			$input.focus(function(){
				if($.trim($(this).val())=='中文/拼音'){
					$(this).val('').css('color','#000');
				}
				if($.trim($(this).val())==''){
					displayItems('');//显示热门城市列表
				}
			});
			$input.click(function(){
				var q=$.trim($(this).val());
				displayItems(q);
				$(this).select();
			});
						
			// help IE users if possible
			try {
				$results.bgiframe();
			} catch(e) { }

			$input.keyup(processKey);//
			
			function resetPosition() {
				// requires jquery.dimension plugin
				var offset = $input.offset();
				$results.css({
					top: (offset.top + input.offsetHeight) + 'px',
					left: offset.left + 'px'
				});
			}
			
			
			function processKey(e) {
				
				// handling up/down/escape requires results to be visible
				// handling enter/tab requires that AND a result to be selected
				if ((/27$|38$|40$/.test(e.keyCode) && $results.is(':visible')) ||
					(/^13$|^9$/.test(e.keyCode) && getCurrentResult())) {
		            
		            if (e.preventDefault)
		                e.preventDefault();
					if (e.stopPropagation)
		                e.stopPropagation();

	                e.cancelBubble = true;
	                e.returnValue = false;
				
					switch(e.keyCode) {
	
						case 38: // up
							prevResult();
							break;
				
						case 40: // down
							nextResult();
							break;
						case 13: // return
							selectCurrentResult();
							break;
							
						case 27: //	escape
							$results.hide();
							break;
	
					}
					
				} else if ($input.val().length != prevLength) {

					if (timeout) 
						clearTimeout(timeout);
					timeout = setTimeout(suggest, options.delay);
					prevLength = $input.val().length;
					
				}			
					
				
			}
			
			function suggest() {
			
				var q = $.trim($input.val());
				displayItems(q);
			}		
			function displayItems(items) {
				var pId = temp.pId;
				var html = '';
				if (items=='') {//热门城市遍历
					for(h in options.hot_list){
						var id = options.hot_list[h][0];
						var text = options.hot_list[h][1];
						if(text.length > 16){
							text = text.substr(0,15)+"...";
						}
						html+='<li onclick="zn_hot_sc_sel('+mark+',\''+id+'\','+h+');" rel="'
						+id+'"><a  id="hot_sc_a_id_'+id+'" href="#'+h+'"><span>'+'</span>'+text+'</a></li>';
					}
					html='<div class="gray ac_result_tip">请输入中文/拼音</div><ul>'+html+'</ul>';
				}
				else {
					/*if (!items)
					return;
					if (!items.length) {
						$results.hide();
						return;
					}*/
					for (var i = 0; i < options.source.length; i++) {//国内城市匹配
						var reg = new RegExp('^' + items + '.*$', 'im');
						var zwReg = new RegExp('.*' + items + '.*$', 'im');
						var id =options.source[i][0]; 
						var text = options.source[i][1];
						if(text.length > 16){
							text = text.substr(0,15)+"...";
						}
						if (reg.test(options.source[i][0]) || zwReg.test(options.source[i][1]) || reg.test(options.source[i][2]) || reg.test(options.source[i][3])) {
							html += '<li onclick="zn_hot_sc_sel('+mark+',\''+id+'\','
							+i+');" rel="' + options.source[i][0] 
							+ '"><a id="hot_sc_a_id_'+id+'" href="#' + i 
							+ '"><span>' + '</span>' + text + '</a></li>';
						}
					}
					if (html == '') {
						suggest_tip = '<div class="gray ac_result_tip">对不起，找不到：' + items + '</div>';
					}
					else {
						suggest_tip = '<div class="gray ac_result_tip">' + items + '，按拼音排序</div>';
					}
					html = suggest_tip + '<ul>' + html + '</ul>';
				}

				$results.html(html).show();
				$results.children('ul').children('li:first-child').addClass(options.selectClass);
				
				$results.children('ul')
					.children('li')
					.mouseover(function() {
						$results.children('ul').children('li').removeClass(options.selectClass);
						$(this).addClass(options.selectClass);
					});
					//.click(function(e) {
					//	e.preventDefault(); 
					//	e.stopPropagation();
					//	selectCurrentResult();
					//});
			}
						
			function getCurrentResult() {
			
				if (!$results.is(':visible'))
					return false;
			
				var $currentResult = $results.children('ul').children('li.' + options.selectClass);
				if (!$currentResult.length)
					$currentResult = false;
					
				return $currentResult;

			}
			
			function selectCurrentResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult) {
					var text = $currentResult.children('a').html().replace(/<span>.?<\/span>/i,'');
					temp.text = text;
					$input.val(text);
					$results.hide();
					temp.key = $currentResult.children('a').attr('href').replace("#","");
					if(modObj){
						modObj.exeOpt(temp);
					}

					if( $(options.dataContainer) ) {
						$(options.dataContainer).val($currentResult.attr('rel'));
					}
	
					if (options.onSelect) {
						options.onSelect.apply($input[0]);
					}
				}
			
			}
			
			function nextResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.next()
							.addClass(options.selectClass);
				else
					$results.children('ul').children('li:first-child').addClass(options.selectClass);
			
			}
			
			function prevResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.prev()
							.addClass(options.selectClass);
				else
					$results.children('ul').children('li:last-child').addClass(options.selectClass);
			
			}
	
		}
		
		$.fn.suggest = function(source, options, modObj) {
			if (!source)
				return;
		
			options = options || {};
			options.source = source;
			options.hot_list=options.hot_list || [];
			options.delay = options.delay || 0;
			options.resultsClass = options.resultsClass || 'ac_results';
			options.selectClass = options.selectClass || 'ac_over';
			options.matchClass = options.matchClass || 'ac_match';
			options.minchars = options.minchars || 1;
			options.delimiter = options.delimiter || '\n';
			options.onSelect = options.onSelect || false;
			options.dataDelimiter = options.dataDelimiter || '\t';
			options.dataContainer = options.dataContainer || '#SuggestResult';
			options.attachObject = options.attachObject || null;
	
			var temp = {};
			temp.key = "";
			temp.pId = this.selector;
			
			var item = {};
			item.temp = temp;
			item.opt = modObj;
			zn_hot_sc_temp_arr.push(item);
			var  mark= zn_hot_sc_mark++;
			
			this.each(function() {
				new $.suggest(this, options,temp,modObj,mark);
			});
	
			return temp;
			
		};
		
	})(jQuery);
	
	function zn_hot_sc_sel(index,id,key){
		var item = zn_hot_sc_temp_arr[index]; 
		var temp = item.temp;
		var opt = item.opt;
		var pId = temp.pId;
		
		var text = $("#hot_sc_a_id_"+id).html().replace(/<span>.?<\/span>/i,'');
		temp.key = key;
		temp.text = text;
		$(pId).val(text);
		if(opt){
			opt.exeOpt(temp);
		}
	}