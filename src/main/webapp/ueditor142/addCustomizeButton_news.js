/*!
 * UEditor
 * version: ueditor
 * build: Fri May 16 2014 14:14:42 GMT+0800 (中国标准时间)
 */

(function(){

	UE.registerUI('insertimage', function(editor, uiName) {
	    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
	    //创建一个button
	    var btn = new UE.ui.Button({
	        //按钮的名字
	        name: uiName,
	        //提示
	        title: '上传图片',
	        //添加额外样式，指定icon图标，这里默认使用一个重复的icon
	        cssRules: 'background-position: -726px -77px;',
	        //点击时执行的命令
	        onclick: function() {
	        	$.cloudDialog.imageDialog({content:[__ctx+'/pub/image/toCloudUpload.ht','no'],_callback:"parent.ueEditorImgCallback(jsonArr)",widths:"960"});
	        }
	    });
	    //当点到编辑内容上时，按钮要做的状态反射
	    editor.addListener('selectionchange', function() {
	        var state = editor.queryCommandState(uiName);
	        if (state == -1) {
	            btn.setDisabled(true);
	            btn.setChecked(false);
	        } else {
	            btn.setDisabled(false);
	            btn.setChecked(state);
	        }
	    });
	    //因为你是添加button,所以需要返回这个button
	    return btn;
	});
})();


function ueEditorImgCallback(data){
	for(index in data){
		_callbackImageUploadSuccess(data[index].filePath,data[index].fileName);
	}
}


