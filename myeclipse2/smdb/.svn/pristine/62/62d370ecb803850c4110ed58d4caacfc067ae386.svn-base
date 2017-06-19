define(function(require, exports, module) {
	var unit = require('javascript/unit/unit');
	
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/unitService');
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/unit/validate');
	require('javascript/step_tools');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	function selectDispType(){
		alertSelect({"type":11});
	}
	
	exports.init = function() {
		unit.prepare();//加载学科
		unit.init();
		validate.validInstitute();
		datepick.init();
		
		//弹出层选择依托重点学科
		$("#selectRelyDiscipline").click(function(){
			popSelect({
				type : 12,
				inData : $("#relyDisciplineId").val(),
				callBack : function(result){
					doWithXX(result, "relyDisciplineId", "rdsp");
					$(document.forms[0]).valid();
				}
			});
		});
		
		//弹出层选择负责人
		$("#select_director_btn").click(function(){
			unit.initSelectOfficer("directorId", "directorName");
		});
		
		//弹出层选择联系人
		$("#select_linkman_btn").click(function(){
			unit.initSelectOfficer("linkmanId", "linkmanName");
		});
		
		//弹出层选择上级管理部门
		$("#select_subjection_btn").click(function(){
			popSelect({
				type : 3,
				title : "选择上级管理部门",
				inData : {"id" : $("#subjectionId").val(), "name" : $("#subjectionName").html()},
				callBack : function(result){
					$("#subjectionId").val(result.data.id);
					$("#subjectionName").html(result.data.name);
				}
			});
		});
		
		//弹出层选择学科门类
		$("#selectDisciplineType").click(function(){
			unit.selectDiscilineType();
		});
		
		//标签分布操作
		//基本信息
		var basicInfo_setting = new Setting({
			id: "basicInfo",
			buttons: ['next','save','cancel'],
			out_check: function(){
				return $("#form_institute").valid();
			}
		});
		
		//管理部门信息
		var academicInfo_setting = new Setting({
			id: "academicInfo",
			buttons: ['prev','next','save','cancel'],
			out_check: function(){
				return $("#form_institute").valid();
			},
			on_in_opt: function(){
				validate.valid_academicInfo();
			}
		});
		var resourceInfo_setting = new Setting({
			id: "resourceInfo",
			buttons: ['prev','next','save','cancel'],
			out_check: function(){
				return $("#form_institute").valid();
			},
			on_in_opt: function(){
				validate.valid_resourceInfo();
			}
		});
		var contactInfo_setting = new Setting({
			id: "contactInfo",
			buttons: ['prev','save','cancel'],
			out_check: function(){
				return $("#form_institute").valid();
			},
			on_in_opt: function(){
				validate.valid_institute_contactInfo();
			}
		});
		step_controller = new Step_controller();
		
		step_controller.after_move_opt = function(){
			for (step in step_controller.steps){
//				var $curLi = $("li[name=" + step_controller.steps[step].id + "]");
				if (step_controller.steps[step].id != step_controller.state){
					$("li[name=" + step_controller.steps[step].id + "]").attr("class", "proc step_e");
				} else {
					$("li[name=" + step_controller.steps[step].id + "]").attr("class", "proc step_d");
				}
			}
		};

		step_controller.add_step(basicInfo_setting);
		step_controller.add_step(academicInfo_setting);
		step_controller.add_step(resourceInfo_setting);
		step_controller.add_step(contactInfo_setting);
		step_controller.init();
		
		//修改标签点击响应事件
		$("li.proc").click(function(){
			step_controller.move_to($(this).attr('name'));
		});
		
		$("#save").click(function(){
			unit.doSave('Institute');
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
