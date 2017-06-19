define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	
	function call_back_checkIdcard(_result){
		result = _result;
	}
	
	var setting = new Setting({

		id: "expert_affiliation",

		out_check: function(){
			return $("#form_person").valid();
		},
		
		on_in_opt: function(){
			validate.valid_expert_affiliation();
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

