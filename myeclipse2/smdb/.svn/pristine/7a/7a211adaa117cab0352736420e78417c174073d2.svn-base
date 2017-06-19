define(function(require, exports, module) {
	require('javascript/step_tools');
	require('datepick');
	require('datepick-zh-CN')($);
	require('tool/jquery.datepick/jquery.datepick-zh-CN')($);
	var validate = require('javascript/person/validate');

	var setting = new Setting({

		id: "work",

		out_check : function(){
			return $("#form_person").valid();
		},

		on_in_opt: function(){
			validate.valid_work();
		},

		on_submit_opt : function(){
			$("#tr_work").remove();
			$("#table_work tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
		}

	});
	
	var init = function(){
		$("#add_work").click(function(){
			addRow("table_work", "tr_work");
			validate.valid_work();
		});

		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});

		$(".exist_date").datepick({alignment: "left", onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, autoSize: true});
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
