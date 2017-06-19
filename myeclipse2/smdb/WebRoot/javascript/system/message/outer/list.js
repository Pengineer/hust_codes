define(function(require, exports, module) {
	var list = require('javascript/list');

	exports.init = function() {
		list.pageShow({
			"nameSpace":"message/outer",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2}
		});
	};
});
