define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');

	var setting = new Setting({

		id: "education",

		out_check: function(){
			return $("#form_person").valid();
		},

		on_in_opt: function(){
			validate.valid_education();
		},

		on_submit_opt: function(){
			$("#tr_education").remove();
			$("#table_education tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$("#add_education").click(function(){
			addRow("table_education", "tr_education");
			validate.valid_education();
		});

		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
