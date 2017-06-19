define(function(require, exports, module) {
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/management/recruit/validate');
	require("pop");
	require("pop-self");
	
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

		$("#prev").click(function(){
			step_controller.prev();
		});

		$("#next").click(function(){
			var age_min = $("#age_min").val();
			var age_max = $("#age_max").val();
			$("#age").val(age_min+"-"+age_max);
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
			popSelectTemplate({
				title: "选择模板文件",
				inData : {"data":{"id" : [], "name" : []}},
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