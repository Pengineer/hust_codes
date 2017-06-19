/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var listEntrust = require('javascript/projectFund/list');
	var nameSpace = "projectFund/entrust";
	
	exports.init = function() {
		listEntrust.init(nameSpace);//公共部分
		listEntrust.graExport(nameSpace);
	};
});