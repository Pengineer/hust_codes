define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"role",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2}
		});
		$(".link2").live("click", function() {
			popAccount(this.id);
			return false;
		});
		$("#accountType").bind("change", function() {
			$("#search").attr("action", "role/simpleSearch.action");
			$("#search").submit();
			return false;
		});
	};
});
