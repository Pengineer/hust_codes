/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	var listPost = require('javascript/projectFund/list');
	var nameSpace = "projectFund/post";
	
	exports.init = function() {
		listPost.init(nameSpace);//公共部分
		listPost.graExport(nameSpace);
	};
});