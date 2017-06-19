define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/personService');
	require('dwr/interface/personExtService');
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/person/validate');
	require('javascript/step_tools');
	
	var edit_identifier1 = require('javascript/person/edit_identifier1');
	var edit_basic2 = require('javascript/person/edit_basic2');
	var edit_teacher_affiliation_modify = require('javascript/person/edit_teacher_affiliation_modify');
	var edit_contact = require('javascript/person/edit_contact');
	var edit_academic = require('javascript/person/edit_academic');
	var edit_education = require('javascript/person/edit_education');
	var edit_work = require('javascript/person/edit_work');
	var edit_abroad = require('javascript/person/edit_abroad');
	var edit_bank = require('javascript/person/edit_bank');
	
	exports.init = function() {
		// 初始化各标签对象
		var identifier1_setting = edit_identifier1.setting;
		var basic2_setting = edit_basic2.setting;
		var teacher_affiliation_modify_setting = edit_teacher_affiliation_modify.setting;
		var contact_setting = edit_contact.setting;
		var academic_setting = edit_academic.setting;
		var education_setting = edit_education.setting;
		var work_setting = edit_work.setting;
		var abroad_setting = edit_abroad.setting;
		var bank_setting = edit_bank.setting;
		
		validate.valid();
		datepick.init();
		
		// 调用标签内部方法
		edit_identifier1.init();
		edit_basic2.init();
		edit_teacher_affiliation_modify.init();
		edit_contact.init();
		edit_academic.init();
		edit_education.init();
		edit_work.init();
		edit_abroad.init();
		edit_bank.init();

		// 定义各标签按钮
		//基本信息
		basic2_setting.buttons = ['next', 'finish', 'cancel'];

		//任职信息
		teacher_affiliation_modify_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//联系信息
		contact_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//学术信息
		academic_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//教育背景
		education_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//工作经历
		work_setting.buttons = ['prev', 'next', 'finish', 'cancel'];

		//出国（境）经历
		abroad_setting.buttons = ['prev', 'next', 'finish', 'cancel'];
		
		//银行信息
		bank_setting.buttons = ['prev', 'finish', 'cancel'];

		// 初始化调度对象
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

		// 将各标签加入调度对象
		step_controller.add_step(basic2_setting);
		step_controller.add_step(teacher_affiliation_modify_setting);
		step_controller.add_step(contact_setting);
		step_controller.add_step(academic_setting);
		step_controller.add_step(education_setting);
		step_controller.add_step(work_setting);
		step_controller.add_step(abroad_setting);
		step_controller.add_step(bank_setting);

		step_controller.init();

		////////////////////分割线//////////////////////

		original_idcardNumber = $("[name='person.idcardNumber']").val().trim();

		////////////////////分割线//////////////////////

		// 绑定编辑页面按钮事件
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
		
		/**
		 * 根据已选择的专业职称获取对应职称子类的列表
		 * @return
		 */
		var selectSubType = function(){
			var id = $("#title").val();
			if(id && id != "" && id != -1){
				personExtService.getCodeNameMapByParentId(id, callbackSubType);
			}else{
				DWRUtil.removeAllOptions("subTitle");
				DWRUtil.addOptions('subTitle',[{name:'请选择二级职称',id:'-1'}],'id','name');
			}
		};

		var callbackSubType = function(data) {
			DWRUtil.removeAllOptions("subTitle");
			DWRUtil.addOptions('subTitle',[{name:'请选择二级职称',id:'-1'}],'id','name');
			DWRUtil.addOptions('subTitle',data);
		};
		
		/**
		 * 根据专业职称一级菜单的初始值来初始化二级职称菜单
		 */
		var displaySubType = function(data){
			DWRUtil.removeAllOptions("subTitle");
			DWRUtil.addOptions('subTitle', [{name:'请选择业务子类',id:''}],'id','name');
			DWRUtil.addOptions('subTitle', data);
			DWRUtil.setValue('subTitle', $("#subTitleId").val());
		};
		
		//初始化二级职称下拉列表
		var selectId = $("#title").val();
		if (selectId && selectId !='-1'){
			personExtService.getCodeNameMapByParentId(selectId, displaySubType);
		}
				
		//监听专业职称下拉列表变化事件
		$(".j_selectSubType").live('change',function(){
			selectSubType();
		});
		
		
		
	};
});
