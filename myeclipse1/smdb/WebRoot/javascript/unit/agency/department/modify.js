define(function(require, exports, module) {
	require('javascript/step_tools');
	require('datepick-init');
	var unit = require('javascript/unit/unit.js');
	var modify = require('javascript/unit/modify.js');
	var validate = require('javascript/unit/validate.js');
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

	
	exports.init = function(){
		unit.init();
		modify.init();
		validate.validDepartment();
		window.doSave = function(objectName) {
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
			unit.doSave(objectName);
		};
	}
})