define(function(require, exports, module) {
	var listMidinspectionRequired = require('javascript/project/project_share/midinspection/midinspectionRequired');
	var nameSpace = "project/key/midinspection/apply/midRequired";

	exports.init = function() {
		listMidinspectionRequired.init(nameSpace);//公共部分
		listMidinspectionRequired.midExport(nameSpace);
	};
});