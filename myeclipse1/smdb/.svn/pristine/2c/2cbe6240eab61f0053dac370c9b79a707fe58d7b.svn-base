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
		on_submit_opt: function(){
			$("#tr_bank").remove();
			$("#table_bank tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
			$("#tr_finance_addr").remove();
			$("#agency-faddr-table tr").each(function(key, value){
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
		on_in_opt: function(){
			validate.valid_financialInfo();
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
		$("#add-agency-faddr").click(function(){
			var flag = true;
			addRow("agency-faddr-table", "tr_finance_addr");
			$(":checkbox[name*='isDefault']",$("#agency-faddr-table")).each(function(){
				if($(this).val() == "1"){
					flag = false;
				}
			});
			if(flag) $(":checkbox[name*='isDefault']",$("#agency-faddr-table")).eq(0).attr("checked","true").val("1");
			prevsAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-faddr-table")).val("1") || {};
		});

		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});
		
		prevCheckbox = $(":checked[name*='isDefault']").val("1") || {};
		$(":checkbox[name*='isDefault']").live("change",function(){				
			if(prevCheckbox.attr("name") !== $(this).attr("name")) {
				prevCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevCheckbox = $(this);
			}				
		});
		prevfAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-faddr-table")).val("1") || {};
		$(":checkbox[name*='isDefault']",$("#agency-faddr-table")).live("change",function(){				
			if(prevfAddrCheckbox.attr("name") !== $(this).attr("name")) {
				prevfAddrCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevfAddrCheckbox = $(this);
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
