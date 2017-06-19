define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	var validate = require('javascript/system/mail/validate');
	var nameSpace = "inbox";
	
	exports.init = function() {
		var sendType = $("#sendType").val();
		if (sendType == 1) {//单播或多播
			$("#unicast").show();
			$("#multicast").hide();
		} else if (sendType == 2) {//广播
			$("#unicast").hide();
			$("#multicast").show();
		} 
		validate.valid();
		$("#sendType").change(function(){
			var sendType = $("#sendType").val();
			if (sendType == 1) {//单播或多播
				$("#unicast").show();
				$("#multicast").hide();
			} else if (sendType == 2) {//广播
				$("#unicast").hide();
				$("#multicast").show();
			} 
		});
	};
});

