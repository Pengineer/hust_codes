// 高校和院系的学术信息，主要包括博士点和重点学科
define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/unit/validate');
	
	var setting = new Setting({

		id: "academicInfo",

		out_check: function(){
			return $("#form_agency").valid();
		},

		on_in_opt: function(){
			validate.valid_doctorial();
			validate.valid_discipline();
		},

		on_submit_opt: function(){
			
			$("#tr_doctorial").remove();
			$("#table_doctorial tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
			
			$("#tr_discipline").remove();
			$("#table_discipline tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$("#add_doctorial").click(function(){
			addRow("table_doctorial", "tr_doctorial");
			validate.valid_doctorial();
		});

		$("#add_discipline").click(function(){
			addRow("table_discipline", "tr_discipline");
			validate.valid_discipline();
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
