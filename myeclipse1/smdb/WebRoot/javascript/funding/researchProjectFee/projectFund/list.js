define(function(require, exports, module) {
	var list = require('javascript/list'),
		nameSpace = "funding/projectFund";
	exports.init = function(){
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn":5,"sortcolumn":6},
			"dealWith": function(){}
		});
	}
})