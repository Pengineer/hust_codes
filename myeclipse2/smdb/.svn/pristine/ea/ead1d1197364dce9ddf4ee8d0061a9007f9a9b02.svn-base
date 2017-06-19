define(function(require, exports, module) {
	var list = require('javascript/list');
	require('pop-init');
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"selectStudent",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2},
			"listType":1
		});
		
		$("input[type='radio']", $("#list_container")).live("click", function(){
			thisPopLayer.outData = {
				data : {
					"id" : this.value,
					"name" : this.alt,
					"personId" : this.lang,
					"sname" : this.title
				}
			};
		});
	};
});