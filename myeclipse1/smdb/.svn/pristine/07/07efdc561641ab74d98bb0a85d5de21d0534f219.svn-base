/**
 * 用于申请评审审核
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('dwr/interface/projectService');
	require('pop-init');
	require('form');
	require('validate');
	var datepick = require("datepick-init");
	
	var datepickInit = function(){
		datepick.init();
	};
	
	var valid = function(){
		$(function() {
			$("#app_review_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"reviewAuditResult":{required: true},
					"reviewAuditDate":{required: true},
					"reviewAuditOpinion":{maxlength: 2000},
					"reviewAuditOpinionFeedback":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var isNumerUnique = true;
	var showNumberAndFee = function(type){
		if(type==2){//填写项目批准号编号和批准经费
			$(".granted").show();
		}else{
			$(".granted").hide();
		}
	};

	var initAppReviewAudit = function(){
		var reviewAuditResult = $("input[name='reviewAuditResult'][type='radio']:checked").val();
		showNumberAndFee(reviewAuditResult);
	};
	
	//评审审核是否提交
	var submitOrNotAppReviewAudit = function(data, layer, projectType){
		var entityId = $("#entityId").val();
		var number = $("input[name='number']").val();
		if(!$("#app_review_audit").valid()){
			return;
		}
		$("#reviewAuditStatus").val(data);
		var reviewAuditResult = $("input[name='reviewAuditResult'][type='radio']:checked").val();
		if (reviewAuditResult == 2) {
			$(":input[name='number']").rules("add",{required: true});
			valid_number(number, entityId, projectType);
		}else{
			$(":input[name='number']").rules("remove");
		}
		if(!isNumerUnique){
			return;
		}
		submitAppReviewAudit(layer);
	}
	
	var granted = "";
	var valid_number = function(number, entityId, projectType){
		if(projectType == "general"){
			granted = "GeneralGranted";
		}else if(projectType == "instp"){
			granted = "InstpGranted";
		}else if(projectType == "post"){
			granted = "PostGranted";
		}else if(projectType == "key"){
			granted = "KeyGranted";
		}else if(projectType == "entrust"){
			granted = "EntrustGranted";
		}else if(projectType == "special"){
			granted = "SpecialGranted";
		}
		DWREngine.setAsync(false);
		projectService.isGrantedNumberUnique(granted, number, entityId, isGrantedNumberUniqueCallback);
		DWREngine.setAsync(true);
	};
	
	var isGrantedNumberUniqueCallback = function(data){
		var $number = $("#number");
		var $errorTd = $number.parent("td").next("td");
		if(!data){
			$errorTd.html('<span class="error">该批准号已存在</span>');// 写错误信息
			isNumerUnique = false;
		}else{
			$errorTd.html('');// 清错误信息
				isNumerUnique = true;;
		}
	};

	var submitAppReviewAudit = function(layer){
		var reviewAuditStatus = $("#reviewAuditStatus").val();
		if(reviewAuditStatus == 3){
			if (!confirm('提交后确定结果，是否确认提交？')) 
				return;
		}
		var reviewAuditResult = $("input[name='reviewAuditResult'][type='radio']:checked").val();
		var reviewAuditOpinion = $("textarea[name='reviewAuditOpinion']").val();
		var reviewAuditOpinionFeedback = $("textarea[name='reviewAuditOpinionFeedback']").val();
		var reviewAuditDate = $("input[name='reviewAuditDate']").val();
		var number = $("input[name='number']").val();
		var approveFee = $("input[name='approveFee']").val();
		var entityId = $("#entityId").val();
		var number = $("#number").val();
		var type = $("#type").val();
		var dis = {
			"reviewAuditResult":reviewAuditResult,
			"reviewAuditStatus":reviewAuditStatus,
			"reviewAuditOpinion":reviewAuditOpinion,
			"reviewAuditDate":reviewAuditDate,
			"reviewAuditOpinionFeedback":reviewAuditOpinionFeedback,
			"number":number,
			"approveFee":approveFee
		};
		
		layer.callBack(dis, entityId, type);
		layer.destroy();
	};
	
	module.exports = {
		 submitOrNotAppReviewAudit: function(data, layer, projectType){submitOrNotAppReviewAudit(data, layer, projectType)}, 
		 initAppReviewAudit: function(){initAppReviewAudit()},
		 showNumberAndFee: function(type){showNumberAndFee(type)},
		 valid: function(){valid()},
		 datepickInit: function(){datepickInit()}
	};
	
});



