define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/unit/validate');
	
	var setting = new Setting({
		
		id: "financialInfo",
		
		buttons: ['prev','finish','cancel'],
		
		out_check: function(){
			return $("#form_agency").valid();
		},
		
		on_in_opt: function(){
			validate.valid_financialInfo();
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
