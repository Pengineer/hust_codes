define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('form');
	var datepick = require("datepick-init");

	var submitUniversityRename = function(){
		$("#form_universityVariation").submit();
	}
	
	exports.init = function() {
		window.submitUniversityRename = function(){submitUniversityRename()};
		datepick.init();
	};

});
