define(function(require, exports, module) {
	var view = require('javascript/view');
	
	var nameSpace = "right";
	
	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.right.id);
			$("#entityIds").attr("value", result.right.id);
			$("#view_container").hide();
			$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
			$("#view_container").show();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	exports.init = function() {
		view.show(nameSpace, showDetails);
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此权限吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);
	};
});