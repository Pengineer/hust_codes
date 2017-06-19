/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var listInstp = require('javascript/projectFund/list');
	var nameSpace = "projectFund/instp";
	
	exports.init = function() {
		listInstp.init(nameSpace);//公共部分
		listInstp.graExport(nameSpace);
	};
});