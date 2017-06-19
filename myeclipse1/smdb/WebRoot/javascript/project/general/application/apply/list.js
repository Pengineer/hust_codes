/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listApplication = require('javascript/project/project_share/application/apply/list');
	var nameSpace = "project/general/application/apply";
	
	exports.init = function() {
		listApplication.init(nameSpace);//公共部分
		listApplication.add(nameSpace);
		$("#list_check").live("click", function() {
			var url = basePath + "/project/general/application/applyStrict" + "/toList.action";
			window.location.href = url;
		});
		$("#firstAudit").live("click", function() {
			var url = basePath + "project/general/application/firstAudit/toFirstAudit.action";
			window.location.href = url;
		});
	};
});