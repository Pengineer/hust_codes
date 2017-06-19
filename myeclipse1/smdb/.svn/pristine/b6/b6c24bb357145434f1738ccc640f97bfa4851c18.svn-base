/**
 * @author liujia
 * @description 从数据源到中间表前台配置页面
 */
define(function(require, exports, module) {
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/dataProcessing/fromSourceToMid/validate');
	require("pop");
	require("pop-self");

	require('javascript/step_tools');
	var edit_basic1 = require('javascript/dataProcessing/fromSourceToMid/edit_basic1');
	var edit_basic2 = require('javascript/dataProcessing/fromSourceToMid/edit_basic2');
	var nameSpace = "dataProcessing/"

	function popSetStatus(args) {
		new top.PopLayer({
			"title": "选择操作",
			"src": args.src,
			"document": top.document,
			"inData": args.inData,
			"callBack": args.callBack
		});
	}//二次封装的弹出层工厂函数

	exports.init = function() {
		top.dataProcessing = {}; //全局变量用于存储共享变量。
		var basic1_setting = edit_basic1.setting;
		var basic2_setting = edit_basic2.setting;
		edit_basic1.init();
		edit_basic2.init();

		basic1_setting.buttons = ['next', 'cancel'];
//		basic2_setting.buttons = ['next', 'prev', 'cancel'];

		step_controller = new Step_controller();

		step_controller.after_move_opt = function() {
			var flag = false;
			for (step in step_controller.steps) {
				var $curLi = $("li[name=" + step_controller.steps[step].id + "]");
				if (step_controller.steps[step].id == step_controller.state) {
					flag = true;
					$curLi.attr("class", "proc step_d");
				} else if (!flag) {
					$curLi.attr("class", "proc step_e");
				} else {
					$curLi.attr("class", "proc step_f");
				}
			}
		};

		step_controller.add_step(basic1_setting);
		step_controller.add_step(basic2_setting);
		step_controller.init();

		$("#prev").click(function() {
			step_controller.prev();
		});

		$("#next").click(function() {
			step_controller.next();
		});

		$("#confirm").click(function() {
			step_controller.next();
		});

		$("#retry").click(function() {
			location.reload();
		});

		$("#cancel").click(function() {
			history.go(-1);
		});

		$("#finish").click(function() {});

		$("#info").show();

	}
})