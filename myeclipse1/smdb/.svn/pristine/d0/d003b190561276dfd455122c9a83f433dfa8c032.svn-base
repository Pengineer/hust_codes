/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	var list = require('javascript/list');	
	var midExport = function(nameSpace){
		$(function() {
			$("#midRequired_export").live("click", function(){
				var url = basePath + nameSpace + "/exportOverView.action";
				location.href = url;
			});
		});
	};
	exports.init = function(nameSpace) {
		$(function() {
			list.pageShow({
				"nameSpace":nameSpace,
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7}
			});
		});
	};
	//导出中检一览表
	exports.midExport = function(nameSpace) {
		midExport(nameSpace);
	};
});

