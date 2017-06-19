/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listApplication = require('javascript/project/project_share/application/apply/list');
	var nameSpace = "project/instp/application/apply";
	
	exports.init = function() {
		listApplication.init(nameSpace);//公共部分
		listApplication.add(nameSpace);
	};
});