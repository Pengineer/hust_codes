define(function(require, exports, module) {
	var unit = require('javascript/unit/unit');
	
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/unitService');
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/unit/validate');
	require('javascript/step_tools');
	
	exports.init = function() {
		unit.init();
		validate.validAgency();
		datepick.init();
		
		$("#select_subjection_btn").click(function(){
			popSelect({
				type : 21,
				title : "选择上级管理部门",
				inData : {"id" : $("#subjectionId").val(), "name" : $("#subjectionName").html()},
				callBack : function(result){
					$("#subjectionId").val(result.data.id);
					$("#subjectionName").html(result.data.name);
				}
			});
		});
		
		//基本信息
		var basicInfo_setting = new Setting({
			id: "basicInfo",
			buttons: ['next', 'cancel'],
			out_check: function(){
				return $("#form_agency").valid();
			}
		});
		
		//联系信息
		var contactInfo_setting = new Setting({
			id: "contactInfo",
			buttons: ['prev','next','cancel'],
			out_check: function(){
				return $("#form_agency").valid();
			},
			on_in_opt: function(){
				validate.valid_agency_contactInfo();
			}
		});
		
		//管理部门信息
		var managementInfo_setting = new Setting({
			id: "managementInfo",
			buttons: ['prev','next','cancel'],
			out_check: function(){
				return $("#form_agency").valid();
			},
			on_in_opt: function(){
				validate.valid_managementInfo();
			}
		});
		
		//财务管理部门信息
		var financialInfo_setting = new Setting({
			id: "financialInfo",
			buttons: ['prev','save','cancel'],
			out_check: function(){
				return $("#form_agency").valid();
			},
			on_in_opt: function(){
				validate.valid_financialInfo();
			}
		});
		
		step_controller = new Step_controller();
		
		step_controller.after_move_opt = function(){
			var flag = false;
			for (step in step_controller.steps){
				var $curLi = $("li[name=" + step_controller.steps[step].id + "]");
				if (step_controller.steps[step].id == step_controller.state){
					flag = true;
					$curLi.attr("class", "proc step_d");
				} else if (!flag){
					$curLi.attr("class", "proc step_e");
				} else {
					$curLi.attr("class", "proc step_f");
				}
			}
		}

		step_controller.add_step(basicInfo_setting);
		//step_controller.add_step(contactInfo_setting);
		step_controller.add_step(managementInfo_setting);
		step_controller.add_step(financialInfo_setting);
		step_controller.init();
		
		$("#add_bank").click(function(){
			var flag = true;
			addRow("table_bank", "tr_bank");
			$(":checkbox[name*='isDefault']",$("#table_bank")).each(function(){
				if($(this).val() == "1"){
					flag = false;
				}
			});
			if(flag) $(":checkbox[name*='isDefault']",$("#table_bank")).eq(0).attr("checked","true").val("1");
			prevBankCheckbox = $(":checked[name*='isDefault']",$("#table_bank")).val("1") || {};
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
			prevsAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-caddr-table")).val("1") || {};
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
		
		
		prevBankCheckbox = $(":checked[name*='isDefault']",$("#table_bank")).val("1") || {};
		$(":checkbox[name*='isDefault']",$("#table_bank")).live("change",function(){				
			if(prevBankCheckbox.attr("name") !== $(this).attr("name")) {
				prevBankCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevBankCheckbox = $(this);
			}				
		});
		prevcAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-caddr-table")).val("1") || {};
		$(":checkbox[name*='isDefault']",$("#agency-caddr-table")).live("change",function(){				
			if(prevcAddrCheckbox.attr("name") !== $(this).attr("name")) {
				prevcAddrCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevcAddrCheckbox = $(this);
			}				
		});
		prevsAddrCheckbox = $(":checked[name*='isDefault']",$("#agency-saddr-table")).val("1") || {};
		$(":checkbox[name*='isDefault']",$("#agency-saddr-table")).live("change",function(){				
			if(prevsAddrCheckbox.attr("name") !== $(this).attr("name")) {
				prevsAddrCheckbox.removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevsAddrCheckbox = $(this);
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
		$("#save").click(function(){

			$("#tr_bank").remove();
			$("#table_bank tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
			
			$("#tr_common_addr").remove();
			$("#agency-caddr-table tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
			
			$("#tr_subjection_addr").remove();
			$("#agency-saddr-table tr").each(function(key, value){
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
		
			unit.doSave('Agency');
		});
		
		$("#prev").click(function(){
			step_controller.prev();
		});

		$("#next").click(function(){
			step_controller.next();
		});

		$("#cancel").click(function(){
			history.go(-1);
		});

		$("#info").show();
		
	};
});
