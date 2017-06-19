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
		
		$("#save").click(function(){
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
