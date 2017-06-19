define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/management/recruit/validate');
	
	var setting = new Setting({

		id: "basic1",
	
		out_check: function(){
			return $("#form_job").valid();
		},

		on_in_opt: function(){
			validate.valid_basic1();
		}
	});
	
	var init = function(){
		
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});