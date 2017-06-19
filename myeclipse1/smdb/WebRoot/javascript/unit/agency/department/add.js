define(function(require, exports, module){
	require('javascript/step_tools');
	require('datepick-init');
	exports.init = function(){
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
	}

})