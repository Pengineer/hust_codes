/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listEndinspection = require('javascript/project/project_share/endinspection/apply/list');
	var nameSpace = "project/entrust/endinspection/apply";
	
	exports.init = function() {
		listEndinspection.init(nameSpace);//公共部分
		listEndinspection.printAndExport(nameSpace);//打印、导出结项一览表
	};
});