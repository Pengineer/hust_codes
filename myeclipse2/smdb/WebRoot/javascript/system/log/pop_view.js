define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-init');
	var datepick = require("datepick-init");
	var validate = require('javascript/security/account/validate');
	
	exports.init = function() {
		datepick.init();
		
		// 根据选择设置默认显示的类型
		var type = $("#logType").val();
		if (type == 0) {
			$("#specifyName").show();
			$("#specifyType").hide();
		} else {
			$("#specifyType").show();
			$("#specifyType").val(type);
			$("#specifyName").hide();
		}
		
		// 绑定单选类别事件
		$("input[type='radio']").bind("click", function() {
			if ($(this).val() == 0) {
				$("#specifyType").show();
				$("#specifyName").hide();
			} else {
				$("#specifyName").show();
				$("#specifyType").hide();
				// 将指定账号名称此类型写到隐藏域
				$("#logType").val(0);
			}
		});
		
		// 将下拉框选中的值写到隐藏域
		$("#specifyType").bind("change", function() {
			$("#logType").val($(this).val());
		});
	};
});
