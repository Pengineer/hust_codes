define(function(require, exports, module) {
	require('pop-init');
	var list = require('javascript/list');
	var nameSpace = "other/nssf/granted";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11","sortcolumn12","sortcolumn13","sortcolumn14","sortcolumn15","sortcolumn16","sortcolumn17","sortcolumn18","sortcolumn19","sortcolumn20","sortcolumn21","sortcolumn22","sortcolumn23","sortcolumn24","sortcolumn25","sortcolumn26","sortcolumn27","sortcolumn28"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11,"sortcolumn12":12,"sortcolumn13":13,"sortcolumn14":14,"sortcolumn15":15,"sortcolumn16":16,"sortcolumn17":17,"sortcolumn18":18,"sortcolumn19":19,"sortcolumn20":20,"sortcolumn21":21,"sortcolumn22":22,"sortcolumn23":23,"sortcolumn24":24,"sortcolumn25":25,"sortcolumn26":26,"sortcolumn27":27,"sortcolumn28":28}
		});

		$("#list_update").live("click", function() {
			new top.PopLayer({
				"title" : "爬虫任务",
				"src" : basePath + "other/nssf/granted/toViewTask.action",
				"document" : top.document
			});
			return false;
		});
	};
});