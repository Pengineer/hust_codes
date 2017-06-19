define(function(require, exports, module) {
	var listMidinspectionRequired = require('javascript/project/project_share/midinspection/midinspectionRequired');
	var nameSpace = "project/special/midinspection/apply/midRequired";

	exports.init = function() {
		listMidinspectionRequired.init(nameSpace);//公共部分
		listMidinspectionRequired.midExport(nameSpace);
	};
});