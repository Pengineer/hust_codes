/**
 * 用于结项评审审核
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('dwr/interface/projectService');
	require('pop-init');
	require('form');
	require('validate');
	
	var valid = function(){
		$(function() {
			$("#end_review_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"reviewAuditResultEnd":{required: true},
					"reviewAuditResultNoevalu":{required: true},
				 	"reviewAuditResultExcelle":{required: true},
					"reviewAuditOpinion":{maxlength: 200},
					"reviewAuditOpinionFeedback":{maxlength: 200},
					"certificate":{required: true, maxlength: 40}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var isNumerUnique = true;
	var showNumber = function(type){
		if(type==2){//填写结项证书编号
			$("#number_info").show();
		}else{
			$("#number_info").hide();
		}
	};

	var initEndReviewAudit = function(){
		var reviewAuditResultEnd = $("input[name='reviewAuditResultEnd'][type='radio']:checked").val();
		showNumber(reviewAuditResultEnd);
	};
	
	//评审审核是否提交
	var submitOrNotEndReviewAudit = function(data, layer, projectType){
		var endId = $("#endId").val();
		var endCertificate = $("#endCertificate").val();
		if(!$("#end_review_audit").valid()){
			return;
		}
		$("#reviewAuditStatus").val(data);
		var reviewAuditResultEnd = $("input[name='reviewAuditResultEnd'][type='radio']:checked").val();
		if (reviewAuditResultEnd == 2) {
			valid_number(endCertificate, endId, projectType);
		}
		if(!isNumerUnique){
			return;
		}
		submitEndReviewAudit(layer);
	}
	
	var endinspection = "";
	var valid_number = function(endCertificate, endId, projectType){
		if(projectType == "general"){
			endinspection = "GeneralEndinspection";
		}else if(projectType == "instp"){
			endinspection = "InstpEndinspection";
		}else if(projectType == "post"){
			endinspection = "PostEndinspection";
		}else if(projectType == "key"){
			endinspection = "KeyEndinspection";
		}else if(projectType == "entrust"){
			endinspection = "EntrustEndinspection";
		}
		DWREngine.setAsync(false);
		projectService.isEndNumberUnique(endinspection, endCertificate, endId, isEndNumberUniqueCallback);
		DWREngine.setAsync(true);
	};
	
	var isEndNumberUniqueCallback = function(data){
		var $endCertificate = $("#endCertificate");
		var $errorTd = $endCertificate.parent("td").next("td");
		if(!data){
			$errorTd.html('<span class="error">该证书编号已存在</span>');// 写错误信息
			isNumerUnique = false;
		}else{
			$errorTd.html('');// 清错误信息
				isNumerUnique = true;;
		}
	};

	var submitEndReviewAudit = function(layer){
		var reviewAuditStatus = $("#reviewAuditStatus").val();
		if(reviewAuditStatus == 3){
			if (!confirm('提交后确定结果，是否确认提交？')) 
				return;
		}
		var mainIfm = parent.document.getElementById("main");
		var reviewAuditResultEnd = $("input[name='reviewAuditResultEnd'][type='radio']:checked").val();
		var reviewAuditOpinion = document.getElementsByName('reviewAuditOpinion')[0].value;
		var reviewAuditOpinionFeedback = document.getElementsByName('reviewAuditOpinionFeedback')[0].value;
		var endId = document.getElementById('endId').value;
		var endCertificate = $("#endCertificate").val();
		var isApplyNoevaluation = $("#isApplyNoevaluation").val();
		var isApplyExcellent = $("#isApplyExcellent").val();
		var reviewAuditResultNoevalu = 0;
		var reviewAuditResultExcelle = 0;
		if(isApplyNoevaluation == 1){
			reviewAuditResultNoevalu =$("input[name='reviewAuditResultNoevalu'][type='radio']:checked").val();
		}
		if(isApplyExcellent == 1){
			 reviewAuditResultExcelle =$("input[name='reviewAuditResultExcelle'][type='radio']:checked").val(); 
		}
		var type = $("#type").val();
		var dis = {
			"reviewAuditResultNoevalu":reviewAuditResultNoevalu,
			"reviewAuditResultEnd":reviewAuditResultEnd,
			"reviewAuditResultExcelle":reviewAuditResultExcelle,
			"reviewAuditStatus":reviewAuditStatus,
			"reviewAuditOpinion":reviewAuditOpinion,
			"reviewAuditOpinionFeedback":reviewAuditOpinionFeedback,
			"endCertificate":endCertificate,
			"isApplyNoevaluation":isApplyNoevaluation,
			"isApplyExcellent":isApplyExcellent
		};
		
		layer.callBack(dis, endId, type);
		layer.destroy();
	};
	
	module.exports = {
		 submitOrNotEndReviewAudit: function(data, layer, projectType){submitOrNotEndReviewAudit(data, layer, projectType)}, 
		 initEndReviewAudit: function(){initEndReviewAudit()},
		 showNumber: function(type){showNumber(type)},
		 valid: function(){valid()}
	};
	
});



