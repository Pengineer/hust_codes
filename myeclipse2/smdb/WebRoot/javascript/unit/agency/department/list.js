define(function(require, exports, module) {
	var listUnit = require('javascript/unit/list');
	
	exports.init = function() {
		listUnit.init("unit/department");
		listUnit.merge(2, "unit/department");
	};
});