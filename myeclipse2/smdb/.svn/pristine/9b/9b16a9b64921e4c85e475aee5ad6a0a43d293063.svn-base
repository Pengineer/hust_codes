define(function(require, exports, module) {
	var view = require('javascript/view');
	
	var nameSpace = "dm/universityVariation";
	
	var init_view = function(nameSpace) {
		$(function() {
			view.show(nameSpace, showDetails);
			view.add(nameSpace);
			view.mod(nameSpace);
			view.del(nameSpace, "您确定要删除此高校更名吗？");
			view.prev(nameSpace, showDetails);
			view.next(nameSpace, showDetails);
			view.back(nameSpace);// 所有查看页面公共部分
		});
	};
	
	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.universityVariation.id);
			$("#entityIds").attr("value", result.universityVariation.id);
			$("#view_container").hide();
			$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
			$("#view_container").show();
		}else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	exports.init = function() {
		init_view(nameSpace);
	};
	
});

