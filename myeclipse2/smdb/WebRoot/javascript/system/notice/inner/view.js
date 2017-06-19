define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "notice/inner";

	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.notice.id);
			$("#entityIds").attr("value", result.notice.id);
			if (result.notice.attachmentName != null && result.notice.attachmentName != "") {
				result.notice.attachmentName = result.notice.attachmentName.split("; ");
			}
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
		view.show(nameSpace, showDetails);// 公用方法
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此通知吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);
		view.download(nameSpace);
	};
});
