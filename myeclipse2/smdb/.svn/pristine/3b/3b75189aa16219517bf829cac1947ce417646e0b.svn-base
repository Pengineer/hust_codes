define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	var validate = require('javascript/system/mail/validate');
	var nameSpace = "mail";
	
	exports.init = function() {
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 5,
				fileSizeLimit : '10MB',
				fileTypeDesc : '附件'
			});
		});
		validate.valid();
		$("textarea[name=mail.sendTo]").hide();
		$("input#recieverType-16").click(function(){
			if (this.checked == true){
				$("textarea[name=mail.sendTo]").show();
			} else {
				$("textarea[name=mail.sendTo]").hide();
			}
		});
		$("textarea[name=mail.sendTo]").click(function(){
			if (this.style.color == 'gray'){
				this.style.color = 'black';
				this.value = "";
			}
		});
	};
});

