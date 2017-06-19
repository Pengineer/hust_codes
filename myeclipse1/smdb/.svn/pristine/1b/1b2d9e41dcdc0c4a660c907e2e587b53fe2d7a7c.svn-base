define(function(require, exports, module) {
	require('pop-init');
	var datepick = require("datepick-init");
	var validate = require('javascript/security/account/validate');
	
	exports.init = function() {
		datepick.init();
		validate.valid();
		$("#confirm").die('click').live('click', function() {
			thisPopLayer.callBack({
				data : {"date" : $("#datepick").val()}
			});
			thisPopLayer.destroy();
		});
	};
});
