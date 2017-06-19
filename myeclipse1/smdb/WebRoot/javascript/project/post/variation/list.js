/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listVariation = require('javascript/project/project_share/variation/list');
	var nameSpace = "project/post/variation/apply";
	
	exports.init = function() {
		listVariation.init(nameSpace);//公共部分
		listVariation.varExport(nameSpace);//导出变更一览表
	};
});