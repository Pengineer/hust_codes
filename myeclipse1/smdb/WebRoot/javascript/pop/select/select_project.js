define(function(require, exports, module) {
	var list = require('javascript/list');
	require('pop-init');
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"selectProject",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4},
			"listType":1
		});
	};
});