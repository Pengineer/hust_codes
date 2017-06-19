define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "message/outer";
	
	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.message.id);
			$("#view_container").hide();
			$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
			$("#view_container").show();
		} else {
			alert(result.errorInfo);
		}
	};

	
	exports.init = function() {
		view.show(nameSpace, showDetails);// 公用方法
		view.add(nameSpace);
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);
		$(".message_quoto").live("click", function() {
			window.location.href = basePath + "message/outer/toAdd.action?entityId=" + $(this).attr("alt") + "&status=2";
			return false;
		});
		$(".message_reply").live("click", function() {
			window.location.href = basePath + "message/outer/toAdd.action?entityId=" + $(this).attr("alt") + "&status=1";
			return false;
		});
	};
});
