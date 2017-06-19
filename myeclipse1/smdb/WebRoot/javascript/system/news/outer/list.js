define(function(require, exports, module) {
	var list = require('javascript/list');
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"news/outer",
			"sortColumnId":["sortcolumn0","sortcolumn1"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1},
		});
	};
});
