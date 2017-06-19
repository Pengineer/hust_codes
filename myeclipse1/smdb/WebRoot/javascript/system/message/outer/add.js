define(function(require, exports, module) {
	require('form');
	var validate = require('javascript/system/message/outer/validate');
	
	exports.init = function() {
		validate.valid();
		
		thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
		
		//取消关闭
		$("#cancel").click(function(){
			thisPopLayer.destroy();
		});
		
		//确定回调
		$("#confirm").click(function(){
			var FCKString = FCKeditorAPI.GetInstance('message.content').GetXHTML();
			if(FCKString == "" || FCKString == null){
				alert("留言正文不得为空！");
			} else{
				if(!$("#form_message").valid()){
					return false;
				} else {
					$("#form_message").ajaxSubmit({
						url: "message/outer/add.action",
						type: "post",
						dataType: "json",
						success: function(result) {
							if(result.errorInfo == null || result.errorInfo == ""){
								alert('感谢您的留言，我们会尽快处理。');
								thisPopLayer.destroy();
							} else {
								alert(result.errorInfo);
							}
						}
					});
				}
			}
		});
	};
});
