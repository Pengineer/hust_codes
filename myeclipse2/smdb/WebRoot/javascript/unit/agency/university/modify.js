define(function(require, exports, module) {
	var unit = require('javascript/unit/unit');
	var modifyUnit = require('javascript/unit/modify');
	
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/unitService');
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/unit/validate');
	require('javascript/step_tools');
	
	var edit_basic = require('javascript/unit/edit_basic');
	var edit_contact = require('javascript/unit/edit_contact');
	var edit_management = require('javascript/unit/edit_management');
	var edit_financial = require('javascript/unit/edit_financial');

	exports.init = function() {
		basic_setting = edit_basic.setting;
		contact_setting = edit_contact.setting;
		management_setting = edit_management.setting;
		financial_setting = edit_financial.setting;
		
		unit.init();
		modifyUnit.init();
		validate.validAgency();
		datepick.init();

		edit_basic.init();
		edit_contact.init();
		edit_management.init();
		edit_financial.init();
		
		var selectedProvince = $("#province").val();
		//页面初始化时下拉框显示市
		if (selectedProvince && selectedProvince !='-1'){
			unitService.getSystemOptionById(selectedProvince, unit.displayCity);
		}
		
		basic_setting.buttons = ['next', 'finish', 'cancel'];

		contact_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		management_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		financial_setting.buttons = ['prev', 'finish', 'cancel'];
		
		//academic_setting.buttons = ['prev', 'finish', 'cancel'];
		
		step_controller = new Step_controller();
		
		step_controller.after_move_opt = function(){
			var flag = false;
			for (step in step_controller.steps){
				var $curLi = $("li[name=" + step_controller.steps[step].id + "]");
				if (step_controller.steps[step].id != step_controller.state){
					$("li[name=" + step_controller.steps[step].id + "]").attr("class", "proc step_e");
				} else {
					$("li[name=" + step_controller.steps[step].id + "]").attr("class", "proc step_d");
				}
			}
		}

		step_controller.add_step(basic_setting);
		step_controller.add_step(contact_setting);
		step_controller.add_step(management_setting);
		step_controller.add_step(financial_setting);
		//step_controller.add_step(academic_setting);
		
		step_controller.init();

		// 绑定分布功能按钮
		$("li.proc").click(function(){
			step_controller.move_to($(this).attr('name'));
		});
		$("#prev").click(function(){
			step_controller.prev();
		});
		$("#next").click(function(){
			step_controller.next();
		});
		$("#finish").click(function(){
			unit.doSave('Agency');
			step_controller.submit();
		});
		$("#cancel").click(function(){
			history.go(-1);
		});
		
		$("#info").show();
	};
});
