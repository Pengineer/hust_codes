define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
	var datepick = require("datepick-init");
	var validateApplication = require("javascript/award/moesocial/application/validate")
	var listApply = require('javascript/award/moesocial/application/apply/list')
	var listApplication = require("javascript/award/moesocial/application/list")
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var initPop = function(){
		var discipline = document.getElementById("discipline").value;
		doWithXX(discipline, "discipline", "disptr");
		//选择学科门类
		$("#select_disciplineType_btn").click(function(){
			$("#disptr").parent("td").next("td").html("");
			popSelect({
				type : 11,
				inData : $("#discipline").val(),
				callBack : function(result){
					doWithXX(result, "discipline", "disptr");
				}
			});
		});
	};
	
	var leaf = function() {
		init_hint();
		$("#list_button_advSearch").click(function(){
			if($("#startDate1").val() == "--不限--"){
				$("#startDate").val("");
			}else {
				$("#startDate").val($("#startDate1").val());
			}
			if($("#endDate1").val() == "--不限--"){
				$("#endDate").val("");
			}else {
				$("#endDate").val($("#endDate1").val());
			}
		});
		//申请届次
		listApply.loadSession('session3');
		$("#session3").val($("#session3Value").val());
		listApply.loadSession('session2');
		$("#session2").val($("#session2Value").val());
	};

	exports.init = function() {
		leaf();//初始化时间“不限”的提示
		initPop();//初始化弹层
		datepick.init();// 初始化日期选择器
		validateApplication.advSearch();//高级检索校验
	};
});
