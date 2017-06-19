define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/management/recruit/validate');
	
	var setting = new Setting({
		id: "detail",
		out_check: function(){
			return $("#form_job").valid();
		},
		on_in_opt: function(){
			validate.valid_detail();
		}
	});
	
	var init = function(){
		// 子页面初始化接口
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});