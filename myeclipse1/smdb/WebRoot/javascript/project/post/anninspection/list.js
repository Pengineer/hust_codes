/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listAnninspection = require('javascript/project/project_share/anninspection/list');
	var nameSpace = "project/post/anninspection/apply";
	
	exports.init = function() {
		listAnninspection.init(nameSpace);//公共部分
	};
});