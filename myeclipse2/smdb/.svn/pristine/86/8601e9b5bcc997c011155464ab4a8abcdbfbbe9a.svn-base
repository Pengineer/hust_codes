define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/personService');
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/person/validate');
	require('javascript/step_tools');
	
	var edit_identifier1 = require('javascript/person/edit_identifier1');
	var edit_basic2 = require('javascript/person/edit_basic2');
	var edit_institute_officer_affiliation = require('javascript/person/edit_institute_officer_affiliation');
	var edit_contact = require('javascript/person/edit_contact');
	var edit_education = require('javascript/person/edit_education');
	var edit_work = require('javascript/person/edit_work');
	var edit_abroad = require('javascript/person/edit_abroad');
	
	exports.init = function() {
		var identifier1_setting = edit_identifier1.setting;
		var basic2_setting = edit_basic2.setting;
		var institute_officer_affiliation_setting = edit_institute_officer_affiliation.setting;
		var contact_setting = edit_contact.setting;
		var education_setting = edit_education.setting;
		var work_setting = edit_work.setting;
		var abroad_setting = edit_abroad.setting;
		
		validate.valid();
		datepick.init();
		
		edit_identifier1.init();
		edit_basic2.init();
		edit_institute_officer_affiliation.init();
		edit_contact.init();
		edit_education.init();
		edit_work.init();
		edit_abroad.init();

		//基本信息
		basic2_setting.buttons = ['next', 'finish', 'cancel'];

		//任职信息
		institute_officer_affiliation_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//联系信息
		contact_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//教育背景
		education_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//工作经历
		work_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//出国（境）经历
		abroad_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

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

		step_controller.add_step(basic2_setting);
		step_controller.add_step(institute_officer_affiliation_setting);
		step_controller.add_step(contact_setting);
		step_controller.add_step(education_setting);
		step_controller.add_step(work_setting);
		step_controller.add_step(abroad_setting);

		step_controller.init();

		////////////////////分割线//////////////////////

		original_idcardNumber = $("[name='person.idcardNumber']").val().trim();

		////////////////////分割线//////////////////////

		$("li.proc").click(function(){
			step_controller.move_to($(this).attr('name'));
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

		$("#finish").click(function(){
			var len = $(".uploadify-queue").children().length;
			if(len>1){
				alert("不能上传多张照片");
			}else{
				step_controller.submit();
			}
		});

		$("#info").show();
		
	};
});
