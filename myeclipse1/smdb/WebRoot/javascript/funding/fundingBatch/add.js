define(function(require, exports, module) {
	var datepick = require("datepick-init");
	var nameSpace = "funding/fundingBatch";
	$("#cancel").click(function(){
		history.go(-1);
	});
	exports.init = function() {
		datepick.init();
	};
	
});

