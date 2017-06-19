/**
 * 结项评审
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var project = require('javascript/project/project_share/project');
	var timeValidate = require('javascript/project/project_share/validate');
	var viewProject = require('javascript/project/project_share/view');
	var viewEndApp = require('javascript/project/project_share/endinspection/apply/view');
	
	/*---------------用于录入结项评审--------------------*/
	//录入结项评审
	var addEndReviewResultPop = function(data, projectType, showProjectDetails) {
		var id = document.getElementsByName('projectid')[0].value;
		popProjectOperation({
			title : "录入结项评审意见",
			src : 'project/' + projectType + '/endinspection/review/toAddResult.action?projectid=' + id + '&endId=' + data,
			callBack : function(layer){
				$("#update").val(1);
				viewEndApp.readEnd(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	};
	
	//选择专家
	var addEndReviewExpert = function(data, projectType, showProjectDetails) {
		popProjectOperation({
			title : "选择结项评审专家",
			src : 'project/' + projectType + '/endinspection/review/toAddRevExpert.action?endId=' + data,
			callBack : function(layer){
//				$("#update").val(1);
//				viewEndApp.readEnd(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	};
	
	//修改录入评审
	var modifyEndReviewResultPop = function(data, projectType, showProjectDetails) {
		popProjectOperation({
			title : "修改结项评审意见",
			src : 'project/' + projectType + '/endinspection/review/toModifyResult.action?endId=' + data,
			callBack : function(layer){
				$("#update").val(1);
				viewEndApp.readEnd(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	};
	
	//提交录入评审
	var submitEndReviewResult = function(data, projectType, showProjectDetails){
		var url = 'project/' + projectType + '/endinspection/review/submitResult.action';
		if( !confirm('提交后无法修改，是否确认提交？'))
			return;
		$.ajax({
			url: url,
			type: "post",
			data: "endId=" + data,
			dataType: "json",
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewEndApp.readEnd(projectType, showProjectDetails);
				}
				else {
					alert(result.errorInfo);
				}
			}	
		});
	};
	/*--------------------结束--------------------*/
     
	//添加评审审核
	var addEndReviewAuditPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "添加结项评审审核意见",
			src : 'project/' + projectType + '/endinspection/reviewAudit/toAdd.action?endId=' + id,
			callBack : function(dis, endId, type){
				doSubmitEndReviewAudit(dis, endId, type, projectType, showProjectDetails);
			}
		});
	};
	
	//修改评审审核
	var modifyEndReviewAuditPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "修改结项评审审核意见",
			src : 'project/' + projectType + '/endinspection/reviewAudit/toModify.action?endId=' + id,
			callBack : function(dis, endId, type){
				doSubmitEndReviewAudit(dis, endId, type, projectType, showProjectDetails);
			}
		});
	};
	
	//提交评审审核
	var submitEndReviewAuditResult = function(id, projectType, showProjectDetails){
		var url = 'project/' + projectType + '/endinspection/reviewAudit/submit.action?endId=' + id;
		if (confirm("提交后确定结果，您确定要提交吗？"))
		$("#review_endform").attr("action", url);
		$("#review_endform").ajaxSubmit({
			success:function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewEndApp.readEnd(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	//查看评审审核
	var viewEndReviewAuditPop = function(id, projectType){
		popProjectOperation({
			title : "查看结项评审审核意见",
			src : 'project/' + projectType + '/endinspection/reviewAudit/view.action?endId=' + id
		});
	};
	//添加专家评审弹出层
	var addEndReviewPop = function(id, projectType, showProjectDetails){
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineEndRev", $("#endinspection")).val());
		if(flag){
			popProjectOperation({
				title : "添加结项评审意见",
				src : 'project/' + projectType + '/endinspection/review/toAdd.action?endId=' + id,
				callBack : function(dis, type, endId){
					doSubmitEndReview(dis, type, endId, projectType, showProjectDetails);
				}
			});
		}
	};
	
	//修改专家评审弹出层
	var modifyEndReviewPop = function(id, entityId, projectType, showProjectDetails){
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineEndRev", $("#endinspection")).val());
		if(flag){
			popProjectOperation({
				title : "添加结项评审意见",
				src : 'project/' + projectType + '/endinspection/review/toModify.action?endId=' + id + '&entityId=' + entityId,
				callBack : function(dis, type, endId){
					doSubmitEndReview(dis, type, endId, projectType, showProjectDetails);
				}
			});
		}
	};
	
	//查看页面ajax提交专家评审信息
	var submitEndReview = function(id, projectType, showProjectDetails){
		if($("#endStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineEndRev", $("#endinspection")).val());
		if(flag){
			var url = 'project/' + projectType + '/endinspection/review/submit.action?endId=' + id + '&entityId=' + id;
			if (confirm("提交后不能修改，您确定要提交吗？"))
			$("#review_endform").attr("action", url);
			$("#review_endform").ajaxSubmit({
				success:function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						viewEndApp.readEnd(projectType, showProjectDetails);
					} else {
						alert(result.errorInfo);
					}
				}
			});
		}
	};
	
	//查看专家评审
	var viewEndReviewPop = function(entityId, projectType){
		popProjectOperation({
			title : "查看结项评审意见",
			src : 'project/' + projectType + '/endinspection/review/view.action?entityId=' + entityId
		});
	};

	//添加小组评审弹出层
	var addEndGroupReviewPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "添加结项小组评审意见",
			src : 'project/' + projectType + '/endinspection/review/toAddGroup.action?endId=' + id,
			callBack : function(dis, type, endId){
				doSubmitGroupReview(dis, type, endId, projectType, showProjectDetails);
			}
		});
	};
	
	//修改小组评审弹出层
	var modifyEndGroupReviewPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "修改结项小组评审意见",
			src : 'project/' + projectType + '/endinspection/review/toModifyGroup.action?endId=' + id,
			callBack : function(dis, type, endId){
				doSubmitGroupReview(dis, type, endId, projectType, showProjectDetails);
			}
		});
	};
	
	//查看页面ajax提交小组评审信息
	var submitEndGroupReview = function(id, projectType, showProjectDetails){
		var url = 'project/' + projectType + '/endinspection/review/submitGroup.action?endId=' + id;
		if (confirm("提交后不能修改，您确定要提交吗？"))
		$("#review_endform").attr("action", url);
		$("#review_endform").ajaxSubmit({
			success:function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					viewEndApp.readEnd(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	//查看小组评审
	var viewEndGroupReviewPop = function(entityId, projectType){
		popProjectOperation({
			title : "查看结项小组评审意见",
			src : 'project/' + projectType + '/endinspection/review/viewGroup.action?endId=' + entityId
		});
	};
	
	//弹出层页面ajax提交专家评审信息
	var doSubmitEndReview = function (dis, type, id, projectType, showProjectDetails){
		$("#endSubmitStatus").val(dis.submitStatus);
		$("#endInnovationScore").val(dis.innovationScore);
		$("#endScientificScore").val(dis.scientificScore);
		$("#endBenefitScore").val(dis.benefitScore);
		$("#endOpinion").val(dis.opinion);
		$("#endQualitativeOpinion").val(dis.qualitativeOpinion);
		var url;
		if(type == 1)
			url = "project/" + projectType + "/endinspection/review/add.action?endId="+id;
		else if(type == 2)
			url = "project/" + projectType + "/endinspection/review/modify.action?endId="+id;
		$("#review_endform").ajaxSubmit({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					viewEndApp.readEnd(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};

	//弹出层页面ajax提交小组评审信息
	var doSubmitGroupReview = function(dis, type, endId, projectType, showProjectDetails){
		$("#endReviewWay").val(dis.reviewWay);
		$("#endReviewResult").val(dis.reviewResult);
		$("#endReviewStatus").val(dis.reviewStatus);
		$("#endReviewOpinionQualitative").val(dis.reviewOpinionQualitative);
		$("#endReviewOpinion").val(dis.reviewOpinion);
		var url="";
		if(type==1)
			url = "project/" + projectType + "/endinspection/review/addGroup.action?endId="+endId;
		else if(type==2)
			url = "project/" + projectType + "/endinspection/review/modifyGroup.action?endId="+endId;
		$("#group_review_endform").ajaxSubmit({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					viewEndApp.readEnd(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	var doSubmitEndReviewAudit = function(dis, endId, type, projectType, showProjectDetails){
		$("#reviewAuditResultNoevalu").val(dis.reviewAuditResultNoevalu);
		$("#reviewAuditResultEnd").val(dis.reviewAuditResultEnd);
		$("#reviewAuditResultExcelle").val(dis.reviewAuditResultExcelle);
		$("#reviewEndAuditStatus").val(dis.reviewAuditStatus);
		$("#reviewEndAuditOpinion").val(dis.reviewAuditOpinion);
		$("#reviewEndAuditOpinionFeedback").val(dis.reviewAuditOpinionFeedback);
		$("#reviewIsApplyExcellent").val(dis.isApplyExcellent);
		$("#reviewIsApplyNoevaluation").val(dis.isApplyNoevaluation);
		$("input[name=certificate]").val(dis.endCertificate);
		var url="";
		if(type==1)
			url="project/" + projectType + "/endinspection/reviewAudit/add.action?endId="+endId;
		else if(type==2)
			url="project/" + projectType + "/endinspection/reviewAudit/modify.action?endId="+endId;
		$("#review_endAudit_form").ajaxSubmit({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewEndApp.readEnd(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	module.exports = {
		addEndReviewExpert: function(data, projectType, showProjectDetails){addEndReviewExpert(data, projectType, showProjectDetails)},
		addEndReviewResultPop: function(data, projectType, showProjectDetails){addEndReviewResultPop(data, projectType, showProjectDetails)},
		modifyEndReviewResultPop: function(data, projectType, showProjectDetails){modifyEndReviewResultPop(data, projectType, showProjectDetails)}, 
		submitEndReviewResult: function(data, projectType, showProjectDetails){submitEndReviewResult(data, projectType, showProjectDetails)},
		addEndReviewAuditPop: function(id, projectType, showProjectDetails){addEndReviewAuditPop(id, projectType, showProjectDetails)},
		modifyEndReviewAuditPop: function(id, projectType, showProjectDetails){modifyEndReviewAuditPop(id, projectType, showProjectDetails)},
		submitEndReviewAuditResult: function(id, projectType, showProjectDetails){submitEndReviewAuditResult(id, projectType, showProjectDetails)},
		viewEndReviewAuditPop: function(id, projectType){viewEndReviewAuditPop(id, projectType)},
		addEndReviewPop: function(id, projectType, showProjectDetails){addEndReviewPop(id, projectType, showProjectDetails)},
		modifyEndReviewPop: function(id, entityId, projectType, showProjectDetails){modifyEndReviewPop(id, entityId, projectType, showProjectDetails)},
		submitEndReview: function(id, projectType, showProjectDetails){submitEndReview(id, projectType, showProjectDetails)},
		viewEndReviewPop: function(entityId, projectType){viewEndReviewPop(entityId, projectType)},
		addEndGroupReviewPop: function(id, projectType, showProjectDetails){addEndGroupReviewPop(id, projectType, showProjectDetails)},
		modifyEndGroupReviewPop: function(id, projectType, showProjectDetails){modifyEndGroupReviewPop(id, projectType, showProjectDetails)},
		submitEndGroupReview: function(id, projectType, showProjectDetails){submitEndGroupReview(id, projectType, showProjectDetails)},
		viewEndGroupReviewPop: function(entityId, projectType){viewEndGroupReviewPop(entityId, projectType)}
	};
});
