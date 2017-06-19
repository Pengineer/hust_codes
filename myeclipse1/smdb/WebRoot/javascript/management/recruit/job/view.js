define(function(require, exports, module) {
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	var nameSpace = "management/recruit/job";
	
	var init_view = function(nameSpace) {
		$(function() {
			view.show(nameSpace, showDetails);
		});
	};
	
	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr(("value"),result.jobId);
			$("div[id^='view_container']").hide();
			Template_tool.init();
			Template_tool.populate(result);
			view.inittabs();
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
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此职位吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);// 所有查看页面公共部分
	};
	
});

