define(function(require, exports, module) {
	var listUnit = require('javascript/unit/list');
	
	exports.init = function() {
		listUnit.init("unit/agency/university");
		listUnit.merge(1, "unit/agency/university");
	};
});