/**
 * 关联规则定制分析
 */
define(function(require, exports, module) {
	require('form');
	require('template');
	require('javascript/template_tool');
	
	// 初始化
	exports.init = function() {
		
		Template_tool.init();
		
		$("#form_dm").submit(function(){
			var queryString = $.trim($("textarea[name='queryString']").val()).toLowerCase();
			if (queryString == '' || queryString.indexOf("select") != 0) {
				alert("请输入正确select查询语句！");
				return false;
			}
			if (queryString.indexOf("delete") >= 0 || queryString.indexOf("insert") >= 0 || queryString.indexOf(";") > 0) {
				alert("只支持select查询语句，请输入正确的查询语句！");
				return false;
			}
			if (!/^\d+$/.test($("input[name='minFrequency']").val())) {
				alert("请输入合理的整数！");
				$("input[name='minFrequency']").val(100);
				return false;
			}
			if (!/^0\.\d+$/.test($("input[name='minSupport']").val())) {
				alert("请输入合理的小数！");
				$("input[name='minSupport']").val(0.0003);
				return false;
			}
			if (!/^0\.\d+$/.test($("input[name='minConfidence']").val())) {
				alert("请输入合理的小数！");
				$("input[name='minConfidence']").val(0.3);
				return false;
			}
			$("#form_dm").ajaxSubmit({
				success : function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						Template_tool.populate(result);
	 				} else {
	 					alert(result.errorInfo);
	 				}
				}
			});
			return false;
		});
	};
	
});