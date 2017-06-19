define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');

	var setting = new Setting({

		id: "thesis",

		out_check: function(){
			return $("#form_person").valid();
		},

		on_in_opt: function(){
			validate.valid_thesis();
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
