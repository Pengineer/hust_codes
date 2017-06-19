define(function(require, exports, module) {
	var view = require('javascript/view');
	var nameSpace = "funding/fundingBatch";
	var init_view = function(nameSpace) {
		$(function() {
			view.show(nameSpace, showDetails);
			view.add(nameSpace);
			view.mod(nameSpace);
			view.del(nameSpace);
			view.prev(nameSpace);
			view.back(nameSpace);
		});
	};
	

	var showDetails = function(result) {// 显示查看内容
		$("#view_container").hide();
		$("#view_container").html(TrimPath.processDOMTemplate("view_template", result.fundingBatch));
		$("#view_container").show();
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	exports.init = function() {
		init_view(nameSpace);
	};
	
});

