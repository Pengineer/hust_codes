/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	var list = require('javascript/list');
	
	var nameSpace = "business";
	
	var init = function() {
		$(function() {
			list.pageShow({
				"nameSpace":nameSpace,
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8}
			});
			$("#mainFlag").bind("change", function() {
				$("#search").attr("action", nameSpace + "/simpleSearch.action");
				$("#search").submit();
				return false;
			});
		});
	};
	
	exports.init = function(nameSpace) {
		init();
	};
});

