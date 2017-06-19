define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/unit/validate');
	
	var setting = new Setting({
		
		id: "contactInfo",
		
		buttons: ['prev','next','finish','cancel'],
		
		out_check: function(){
			return $("#form_agency").valid();
		},
		
		on_in_opt: function(){
			validate.valid_agency_contactInfo();
		},
		on_submit_opt:function(){
			$("#tr_common_addr").remove();
			$("#agency-caddr-table tr").each(function(key, value){
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
		$("#add-agency-caddr").click(function(){
			var flag = true;
			addRow("agency-caddr-table", "tr_common_addr");
			$(":checkbox[name*='isDefault']",$("#agency-caddr-table")).each(function(){
				if($(this).val() == "1"){
					flag = false;
				}
			});
			if(flag) $(":checkbox[name*='isDefault']",$("#agency-caddr-table")).eq(0).attr("checked","true").val("1");
			prevcAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-caddr-table")).val("1") || {};
		});

		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});

		prevcAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-caddr-table")).val("1") || {};
		$(":checkbox[name*='isDefault']",$("#agency-caddr-table")).live("change",function(){				
			if(prevcAddrCheckbox.attr("name") !== $(this).attr("name")) {
				prevcAddrCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevcAddrCheckbox = $(this);
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
