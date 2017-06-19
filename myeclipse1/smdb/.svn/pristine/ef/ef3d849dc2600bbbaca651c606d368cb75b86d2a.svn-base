define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');

	var setting = new Setting({

		id: "bank",

		on_submit_opt: function(){
			$("#tr_bank").remove();
			$("#table_bank tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
			$(":checkbox[name*='isDefault']").each(function(){
				var name = $(this).attr("name"),
					selector = ":hidden[name='" + name + "']"
				$(selector).val($(this).val());
				$(this).remove();
			});
		},

		out_check: function(){
			return $("#form_person").valid();
		},

		on_in_opt: function(){
			validate.valid_bank();
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$(":input[name*='isDefault']").each(function(){
			var $checkBox = $(":checkbox",$(this).parent());
			$checkBox.attr({
				"name": $(this).attr("name"),
				"value": $(this).val()
			});
			if($(this).val() == "1") $checkBox.attr("checked","true");
		});
		
		$("#add_bank").click(function(){
			addRow("table_bank", "tr_bank");
		});

		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});

		$(".exist_date").datepick({alignment: "left", onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, autoSize: true});
		prevCheckbox = $(":checked[name*='isDefault']").val("1") || {};
		$(":checkbox[name*='isDefault']").live("change",function(){				
			if(prevCheckbox.attr("name") !== $(this).attr("name")) {
				prevCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevCheckbox = $(this);
			}				
		});
		$(":checkbox[name*='isDefault']").live("click",function(){
			$(this).attr("checked","true").val("1");
		})
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
