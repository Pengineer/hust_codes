define(function(require,exports,module){
	require('pop-init');
	require('jquery-ui');
	var list = require('javascript/list');
	var nameSpace = "management/recruit/template";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,	
			"sortColumnId":["sortcolumn0","sortcolumn1"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1},
			"listType": 2,
			"dealWith": function(){
				$(".date").each(function(){
					var date = $(this).html().substring(0,10);
					$(this).html(date);
				});
			}
		});
	};
});