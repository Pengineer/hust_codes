define(function(require, exports, module) {
	var list = require('javascript/list');
	var nameSpace = 'right';
	exports.init = function() {
		list.pageShow({
			"nameSpace":"right",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3}
		});
		$("#list_export").live("click", function(){
			window.location.href = basePath + nameSpace + "/confirmExportOverView.action";
		});
	};
});