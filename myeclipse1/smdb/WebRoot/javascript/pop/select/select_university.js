define(function(require, exports, module) {
	var list = require('javascript/list');
	require('pop-init');
	require('jquery-ui');
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"selectUniversity",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2},
			"listType":1
		});
		
		$.ajax({
			url : "selectUniversity/fetchAutoData.action",
			dataType : "json",
			method : "post",
			success : function(result){
				var json_data = result.autoData;
				$("#keyword").autocomplete({source:json_data});
			}
		});
	};
});