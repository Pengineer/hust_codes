define(function(require, exports, module) {
	var datepick = require("datepick-init");
	require('validate');
	require("pop");
	require("pop-self");
	var nameSpace = "management/recruit/job";
	var validate = require('javascript/management/recruit/validate');
	
	require('javascript/step_tools');
	var edit_basic1 = require('javascript/management/recruit/job/edit_basic1');
	var edit_detail = require('javascript/management/recruit/job/edit_detail');

	exports.init = function() {
		var basic1_setting = edit_basic1.setting;
		var detail_setting = edit_detail.setting;
		
		datepick.init();
		validate.valid();
		
		edit_basic1.init();
		edit_detail.init();
		
		//基本信息
		basic1_setting.buttons = ['next', 'retry', 'cancel'];

		//详细要求
		detail_setting.buttons = ['prev', 'finish', 'retry', 'cancel']; 
		
		$.ajax({
			url: nameSpace+"/view.action",
			data: "entityId="+$("#entityId").val(),
			type: "post",
			dataType: "json",
			success: function(result) { 
				var age=result.age.split("-");
				$("#name").val(result.name);
				$("#number").val(result.number);
				$("#degree").val(result.degree);
				$("#age_min").val(age[0]);
				$("#age_max").val(age[1]);
				$("#contact").val(result.contact);
				$("#endDate").val(result.endDate);
				$("#requirement").val(result.requirement);
				var templateIds =[];
				var templates=[];
				for(var i=0; i<result.templateList.length; i++) {
					templateIds.push(result.templateList[i].id);
					templates.push(result.templateList[i].name);
				}
				$("#selectedTemplateIds").val(templateIds.join("; "));
				$("#selectedTempltes").val(templates.join("; "));
				doWithId(templates.join("; "),templateIds.join("; "),"selectedTemplates","selectedTemplateIds","selected");
			}
			
		})
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
		};

		step_controller.add_step(basic1_setting);
		step_controller.add_step(detail_setting);
		step_controller.init();
		
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

		$("#confirm").click(function(){
			step_controller.next();
		});

		$("#retry").click(function(){
			location.reload();
		});

		$("#cancel").click(function(){
			history.go(-1);
		});

		$("#finish").click(function(){
			step_controller.submit();
		});

		$("#info").show();
		
		//////////////////调用弹层，选择模板/////////////////////
		$("#selectTemplate").live("click", function() {
			var ids;
			var selectedTemplates;
			if($("#selectedTemplateIds").val()) {
				ids=$("#selectedTemplateIds").val().split("; ");
			} else {
				ids=[];
			}
			if($("#selectedTemplates").val()) {
				selectedTemplates=$("#selectedTemplates").val().split("; ");
			} else {
				selectedTemplates=[];
			}
			popSelectTemplate({
				title: "选择模板文件",
				inData : {"data":{"id" : ids, "name" : selectedTemplates}},
				src: "management/recruit/job/selectTemplate.action?listType=3",
				callBack : function(result){
					var templateIds=result.data.id.join("; ");
					var templateNames=result.data.name.join("; ");
					$("#warning").hide();
					doWithId(templateNames,templateIds,"selectedTemplates","selectedTemplateIds","selected");
				}
			});
			return false;
		})
	};

})