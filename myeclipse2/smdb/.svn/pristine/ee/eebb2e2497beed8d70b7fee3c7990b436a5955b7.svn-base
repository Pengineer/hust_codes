/**
 * 用于院系，校级，省厅，部级审核以及评审结果审核
 * @author 余潜玉
 */
define(function(require, exports, module) {
	var viewApplication = require('javascript/award/moesocial/application/view');
	require('tool/poplayer/js/pop');
	require('pop-init');
	require('pop-self');
	require('form');
	
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	
	//初始化
	var initAudit = function(){
		var radioValue = $(".r_type:checked").val();
		if(radioValue == 1){//不同意时
			$("#opinion_feedback").show();
		}
	};
	
	//显示最终审核意见（反馈给项目负责人）
	var showOpinionFeedbackOrNot = function(data){
		if(data == 1){
			$("#opinion_feedback").show();
		}else{
			$("#opinion_feedback").hide();
		}
	};
	
	//审核是否提交(包括院系，校级，省厅，部级审核以及评审结果审核)
	var submitOrNot = function(data, type, layer){
//		if(!$("#award_audit").valid()){
//			return;
//		}
		if(data==3) {
			if( !confirm('提交后无法修改，是否确认提交？'))
				return ;
		}
		var mainIfm = parent.document.getElementById("main");
		var auditOpinion = document.getElementsByName('auditOpinion')[0].value;
		var auditOpinionFeedback = document.getElementsByName('auditOpinionFeedback')[0].value;
		var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
		var dis = {
			"auditResult":auditResult,
			"auditStatus":data,
			"auditOpinion":auditOpinion,
			"auditOpinionFeedback":auditOpinionFeedback
		};
		layer.callBack(dis, type);
		layer.destroy();
	};
	
	//添加申报审核
	var submitOrNotAddAudit = function(data, layer){
		submitOrNot(data, 1, layer);
	};
	
	//修改申报审核
	var submitOrNotModifyAudit = function(data, layer){
		submitOrNot(data, 2, layer);
	};
	
	//添加评审审核
	var submitOrNotAddReviewAudit = function(data, layer){
		submitOrNot(data, 3, layer);
	};
	
	//修改评审审核
	var submitOrNotModifyReviewAudit = function(data, layer){
		submitOrNot(data, 4, layer);
	};
	
	exports.initClick = function(){
		$(".j_addSave").live("click",function(){
			submitOrNotAddAudit(2, thisPopLayer);
		});
		
		$(".j_addSubmit").live("click",function(){
			submitOrNotAddAudit(3, thisPopLayer);
		}); 
		
		$(".j_modifySave").live("click",function(){
			submitOrNotModifyAudit(2, thisPopLayer);
		});
		
		$(".j_modifySubmit").live("click",function(){
			submitOrNotModifyAudit(3, thisPopLayer);
		});
		
		$("input[name='auditResult'][type='radio']").click(function(){
			var data = $("input[name='auditResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(data);
		});
		
		$("input[name='auditResult'][type='radio']").click(function(){
			var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(auditResult);
		}); 
		
		$(".j_saveAddReviewAudit").live("click", function() {
			submitOrNotAddReviewAudit(2, thisPopLayer);
		});
		
		$(".j_submitAddReviewAudit").live("click", function() {
			submitOrNotAddReviewAudit(3, thisPopLayer);
		});
		
		$(".j_saveModifyReviewAudit").live("click", function() {
			submitOrNotModifyReviewAudit(2, thisPopLayer);
		});
		
		$(".j_submitModifyReviewAudit").live("click", function() {
			submitOrNotModifyReviewAudit(3, thisPopLayer);
		});
	};
	exports.init = function(){
		$(function() {
			initAudit();
		});
	};
});