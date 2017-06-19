define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "inBox";
	exports.init = function() {
		list.pageShow({
			"nameSpace":"inBox",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7}
		});
		
		//根据收信箱、发信箱的选择，则列表更新
		$("#session1").bind("change", function() {
			$("#search").attr("action",nameSpace + "/simpleSearch.action");
			$("#search").submit();
			return false;
		});
	};
});
