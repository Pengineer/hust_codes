define(function(require, exports, module) {
	require('pop-init');
	var list = require('javascript/list');
	var nameSpace = "other/nsfc/granted";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11}
		});

		$("#list_update").live("click", function() {
			new top.PopLayer({
				"title" : "爬虫任务",
				"src" : basePath + "other/nsfc/granted/toViewTask.action",
				"document" : top.document
			});
			return false;
		});
	};
});
