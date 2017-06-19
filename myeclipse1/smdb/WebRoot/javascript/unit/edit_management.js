define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/unit/validate');
	
	var setting = new Setting({
		
		id: "managementInfo",
		
		buttons: ['prev','next','save','cancel'],
		
		out_check: function(){
			return $("#form_agency").valid();
		},
		
		on_in_opt: function(){
			validate.valid_managementInfo();
		},
		on_submit_opt:function(){
			$("#tr_subjection_addr").remove();
			$("#agency-saddr-table tr").each(function(key, value){
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
		$("#add-agency-saddr").click(function(){
			var flag = true;
			addRow("agency-saddr-table", "tr_subjection_addr");
			$(":checkbox[name*='isDefault']",$("#agency-saddr-table")).each(function(){
				if($(this).val() == "1"){
					flag = false;
				}
			});
			if(flag) $(":checkbox[name*='isDefault']",$("#agency-saddr-table")).eq(0).attr("checked","true").val("1");
			prevsAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-saddr-table")).val("1") || {};
		});
		prevsAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-saddr-table")).val("1") || {};
		$(":checkbox[name*='isDefault']",$("#agency-saddr-table")).live("change",function(){				
			if(prevsAddrCheckbox.attr("name") !== $(this).attr("name")) {
				prevsAddrCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevsAddrCheckbox = $(this);
			}				
		});
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
