define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"mail",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
		$(".link2").live("click", function() {
			popAccount(this.id);
			return false;
		});
	};
});
