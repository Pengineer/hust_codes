define(function(require, exports, module) {
	var list = require('javascript/list');
	var nameSpace = "dm/universityVariation";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"addUrl":nameSpace+"/toAdd.action",
			"delUrl":nameSpace+"/delete.action",
			"delParam":"entityIds",
			"viewUrl":nameSpace+"/toView.action",
			"viewParam":"entityId",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6}
		});
	};
});