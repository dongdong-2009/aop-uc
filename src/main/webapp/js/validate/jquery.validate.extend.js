var invalidRegex = /^[^<>;]{0,}$/;
+ function(jQuery , undefined){
	//防止xss
	jQuery.validator.addMethod("invalidMethod", function(value, element) {
		var pass = false;
		//var regex = /^[\u4E00-\u9FA5a-zA-Z0-9_\-@]{0,}$/;
		if(invalidRegex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		//console.log(inputName+" pass="+pass+" tipName="+tipName);
		return this.optional(element) || pass;
	}, ('不能输入符号<>;'));
	
	//网址url
	jQuery.validator.addMethod("urlMethod", function(value, element) {
		var pass = false;
		var regex = /^(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?$/;
		if(regex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		//console.log(inputName+" pass="+pass+" tipName="+tipName);
		return this.optional(element) || pass;
	}, ('请输入正确的url，如http://www.1.cn'));
	
	
	//网址url char
	jQuery.validator.addMethod("charMethod", function(value, element) {
		var pass = false;
		var regex = /^[a-zA-Z0-9_\-]{0,}$/;
		if(regex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		//console.log(inputName+" pass="+pass+" tipName="+tipName);
		return this.optional(element) || pass;
	}, ('只能输入字母 数组 减号 下划线'));
	
	
	
	
	//用户名验证方法
	jQuery.validator.addMethod("userNameMethod", function(value, element) {
		var pass = false;
		var regex = /^[a-zA-Z0-9_\-]{6,20}$/;
		if(regex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		//console.log(inputName+" pass="+pass+" tipName="+tipName);
		return this.optional(element) || pass;
	}, ('长度6~20,可用字母、数字、减号、下划线,区分大小写'));
	
	//下拉框必选
	jQuery.validator.addMethod("addRequireSelectMethod", function(value, element, options) {
		var select = false;
		if(value!="-1" && value!="" && value!=null) {
			select = true;
		}
		//console.info("下拉框 select="+select+"= value="+value+"=" );
		return this.optional(element) || select;
	}, jQuery.format('请选择下拉框中的选项'));
	
	//验证身份证或其它证件
	jQuery.validator.addMethod("credentialsNumberMethod", function(value, element, options) {
		var pass = false;
		//var credentialsTypeVal = document.getElementsByName('credentialsType')[0].value;
		var credentialsTypeVal = $(element).parents("form").find("*[name='credentialsType']").val();
		//alert(credentialsTypeVal);
		var regex = /^(\d{18,18}|\d{15,15}|\d{17,17}[xX]{1})$/;
		if("00000400"==credentialsTypeVal ) {
			if(regex.test(value)){
				//如果类型是身份证 并且验证通过
				pass = true;
			}
		} else {
			pass = true;
		}
		return this.optional(element) || pass;
	}, jQuery.format('请输入正确的证件号码'));
	
	//验证邮箱
	jQuery.validator.addMethod("emailMethod", function(value, element) {
		var pass = false;
		var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if(regex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		return this.optional(element) || pass;
	}, ('请输入正确的邮箱'));
	
	//验证手机号
	jQuery.validator.addMethod("mobilephoneMethod", function(value, element) {
		var pass = false;
		var regex = /^1[3|4|7|5|8]\d{9}$/;
		if(regex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		return this.optional(element) || pass;
	}, ('请输入正确的移动电话'));
	
	
	//验证固定电话
	jQuery.validator.addMethod("landlinephoneMethod", function(value, element) {
		var pass = false;
		var regex = /^(\d{3,4})-(\d{7,8})$/;
		if(regex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		return this.optional(element) || pass;
	}, ('请输入正确的固定电话，格式：区号-号码'));

	//验证邮政编码
	jQuery.validator.addMethod("postcodeMethod", function(value, element) {
		var pass = false;
		var regex = /^[1-9][0-9]{5}$/;
		if(regex.test(value)) {
			pass = true;
		} else {
			pass = false;
		}
		return this.optional(element) || pass;
	}, ('请输入正确邮政编码'));
}(jQuery);