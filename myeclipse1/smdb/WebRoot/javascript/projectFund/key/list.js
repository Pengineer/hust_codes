/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var listKey = require('javascript/projectFund/list');
	var nameSpace = "projectFund/key";
	
	exports.init = function() {
		listKey.init(nameSpace);//公共部分
		listKey.graExport(nameSpace);
	};
});