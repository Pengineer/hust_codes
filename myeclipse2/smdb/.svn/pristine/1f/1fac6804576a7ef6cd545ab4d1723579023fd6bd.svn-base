define(function(require, exports, module) {
	require('javascript/step_tools');
	require('datepick');
	require('datepick-zh-CN')($);
	require('tool/jquery.datepick/jquery.datepick-zh-CN')($);
	var validate = require('javascript/person/validate');

	var setting = new Setting({

		id: "abroad",

		on_submit_opt: function(){
			$("#tr_abroad").remove();
			$("#table_abroad tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
		},

		out_check: function(){
			return $("#form_person").valid();
		},

		on_in_opt: function(){
			validate.valid_abroad();
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$("#add_abroad").click(function(){
			addRow("table_abroad", "tr_abroad");
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
