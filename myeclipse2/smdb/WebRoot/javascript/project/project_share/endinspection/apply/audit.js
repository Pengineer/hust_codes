/**
 * 用于结项申请审核
 * @author 余潜玉、肖雅
 */
define(function(require, exports, module) {
	require('pop-init');
	require('form');
	require('validate');
	var timeValidate = require('javascript/project/project_share/validate');
	
	var valid = function(){
		$(function() {
			$("#end_audit").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"endAuditResult":{required: true},
					"endNoauditResult":{required: true},
				 	"endExcellentResult":{required: true},
					"endAuditOpinion":{maxlength: 200},
				 	"endAuditOpinionFeedback":{maxlength: 200}
					},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	var initEndAudit = function(){
		var radioValue = $(".r_type:checked").val();
		if(radioValue == 1){//不同意时
			$("#opinion_feedback").show();
		}
	}
	
	//显示最终审核意见（反馈给项目负责人）
	var showOpinionFeedbackOrNot = function(data){
		if(data == 1){
			$("#opinion_feedback").show();
		}else{
			$("#opinion_feedback").hide();
		}
	};
	
	var addEndAudit = function (data, layer){
		saveEndAudit(data, 1, layer);
	};

	var modifyEndAudit = function (data, layer){
		saveEndAudit(data, 2, layer);
	};
	
	//审核是否提交(包括院系，校级，省厅，部级审核)
	var saveEndAudit = function (data, type, layer) {
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			if(!$("#end_audit").valid()){
			return;
			}
			var isApplyNoevaluation = $("#isApplyNoevaluation").val();
			var isApplyExcellent = $("#isApplyExcellent").val();
			var endAuditOpinion = $("[name='endAuditOpinion']:eq(0)").val();
			var endAuditOpinionFeedback = $("[name='endAuditOpinionFeedback']:eq(0)").val();
			var endAuditResult = $("input[name='endAuditResult'][type='radio']:checked").val();
			var endNoauditResult = 0;
			var endExcellentResult = 0;
			if(isApplyNoevaluation == 1){
				endNoauditResult = $("input[name='endNoauditResult'][type='radio']:checked").val();
			}
			if(isApplyExcellent == 1){
				endExcellentResult = $("input[name='endExcellentResult'][type='radio']:checked").val();
			}
			var mainIfm = parent.document.getElementById("main");
			var dis ={
				type: type,
				isApplyNoevaluation: isApplyNoevaluation,
				isApplyExcellent: isApplyExcellent,
				endAuditResult: endAuditResult,
				endNoauditResult: endNoauditResult,
				endExcellentResult: endExcellentResult,
				endAuditStatus: data,
				endAuditOpinion: endAuditOpinion,
				endAuditOpinionFeedback: endAuditOpinionFeedback
			}
			
			layer.callBack(dis);
			layer.destroy();
		}else{
			return false;
		}
	};
	exports.init = function() {
		$(".j_showOpinionFeedbackOrNot").live("click" ,function(){
			var data = $("input[name='endAuditResult'][type='radio']:checked").val();
			alert(data);
			showOpinionFeedbackOrNot(data)
		});
		
		$(".j_addEndAudit").live("click", function(){
			var data = $(this).attr("data");
			addEndAudit(data, thisPopLayer);
		});
		
		$(".j_modifyEndAudit").live("click", function(){
			var data = $(this).attr("data");
			modifyEndAudit(data, thisPopLayer);
		});
		initEndAudit();
		
//		window.addEndAudit = function(data, layer){addEndAudit(data, layer)};
//		window.modifyEndAudit = function(data, layer){modifyEndAudit(data, layer)};
//		window.initEndAudit = function(){initEndAudit()};
//		window.showOpinionFeedbackOrNot = function(data){showOpinionFeedbackOrNot(data)};
	};
	
	exports.valid = function(){
		valid();
	};
});



