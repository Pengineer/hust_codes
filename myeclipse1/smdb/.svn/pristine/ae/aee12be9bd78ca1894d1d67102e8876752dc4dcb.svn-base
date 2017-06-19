/**
 * 申请评审
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var project = require('javascript/project/project_share/project');
	var timeValidate = require('javascript/project/project_share/validate');
	var viewProject = require('javascript/project/project_share/view');
	var viewAppApp = require('javascript/project/project_share/application/apply/view');
	/*---------------添加评审专家---------------------*/
	//录入申请评审
	var addAppReviewExpert = function(id, projectType, showProjectDetails) {
		popProjectOperation({
			title : "选择申请评审专家",
			src : 'project/' + projectType + '/application/review/toAddRevExpert.action?entityId=' + id,
			callBack : function(layer){
				$("#update").val(1);
				viewAppApp.readApply(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	};
	/*---------------用于录入申请评审--------------------*/
	//录入申请评审
	var addAppReviewResultPop = function(data, projectType, showProjectDetails) {
		popProjectOperation({
			title : "录入申请评审意见",
			src : 'project/' + projectType + '/application/review/toAddResult.action?entityId=' + data,
			callBack : function(layer){
				$("#update").val(1);
				viewAppApp.readApply(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	};
	
	//修改录入评审
	var modifyAppReviewResultPop = function(data, projectType, showProjectDetails) {
		popProjectOperation({
			title : "修改申请评审意见",
			src : 'project/' + projectType + '/application/review/toModifyResult.action?entityId=' + data,
			callBack : function(layer){
				$("#update").val(1);
				viewAppApp.readApply(projectType, showProjectDetails);
				layer.destroy();
			}
		});
	};
	
	//提交录入评审
	var submitAppReviewResult = function(data, projectType, showProjectDetails){
		var url = 'project/' + projectType + '/application/review/submitResult.action';
		if( !confirm('提交后无法修改，是否确认提交？'))
			return;
		$.ajax({
			url: url,
			type: "post",
			data: "entityId=" + data,
			dataType: "json",
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewAppApp.readApply(projectType, showProjectDetails);
				}
				else {
					alert(result.errorInfo);
				}
			}	
		});
	};
	/*--------------------结束--------------------*/
     
	//添加评审审核
	var addAppReviewAuditPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "添加申请评审审核意见",
			src : 'project/' + projectType + '/application/reviewAudit/toAdd.action?entityId=' + id,
			callBack : function(dis, entityId, type){
				doSubmitAppReviewAudit(dis, entityId, type, projectType, showProjectDetails);
			}
		});
	};
	
	//修改评审审核
	var modifyAppReviewAuditPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "修改申请评审审核意见",
			src : 'project/' + projectType + '/application/reviewAudit/toModify.action?entityId=' + id,
			callBack : function(dis, entityId, type){
				doSubmitAppReviewAudit(dis, entityId, type, projectType, showProjectDetails);
			}
		});
	};
	
	//提交评审审核
	var submitAppReviewAuditResult = function(id, projectType, showProjectDetails){
		var url = 'project/' + projectType + '/application/reviewAudit/submit.action?entityId=' + id;
		if (confirm("提交后确定结果，您确定要提交吗？"))
		$("#review_appform").attr("action", url);
		$("#review_appform").ajaxSubmit({
			success:function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewAppApp.readApply(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	//查看评审审核
	var viewAppReviewAuditPop = function(id, projectType){
		popProjectOperation({
			title : "查看申请评审审核意见",
			src : 'project/' + projectType + '/application/reviewAudit/view.action?entityId=' + id
		});
	};
	//添加专家评审弹出层
	var addAppReviewPop = function(id, projectType, showProjectDetails){
		if($("#appStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineAppRev", $("#application")).val());
		if(flag){
			popProjectOperation({
				title : "添加申请评审意见",
				src : 'project/' + projectType + '/application/review/toAdd.action?entityId=' + id,
				callBack : function(dis, type, entityId){
					doSubmitAppReview(dis, type, entityId, projectType, showProjectDetails);
				}
			});
		}
	};
	
	//修改专家评审弹出层
	var modifyAppReviewPop = function(id, appRevId, projectType, showProjectDetails){
		if($("#appStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineAppRev", $("#application")).val());
		if(flag){
			popProjectOperation({
				title : "添加申请评审意见",
				src : 'project/' + projectType + '/application/review/toModify.action?entityId=' + id + '&appRevId=' + appRevId,
				callBack : function(dis, type, entityId){
					doSubmitAppReview(dis, type, entityId, projectType, showProjectDetails);
				}
			});
		}
	};
	
	//查看页面ajax提交专家评审信息
	var submitAppReview = function(id, projectType, showProjectDetails){
		if($("#appStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineAppRev", $("#application")).val());
		if(flag){
			var url = 'project/' + projectType + '/application/review/submit.action?entityId=' + id + '&appRevId=' + id;
			if (confirm("提交后不能修改，您确定要提交吗？"))
			$("#review_appform").attr("action", url);
			$("#review_appform").ajaxSubmit({
				success:function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						viewAppApp.readApply(projectType, showProjectDetails);
					} else {
						alert(result.errorInfo);
					}
				}
			});
		}
	};
	
	//查看专家评审
	var viewAppReviewPop = function(appRevId, projectType){
		popProjectOperation({
			title : "查看申请评审意见",
			src : 'project/' + projectType + '/application/review/view.action?appRevId=' + appRevId
		});
	};

	//添加小组评审弹出层
	var addAppGroupReviewPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "添加申请小组评审意见",
			src : 'project/' + projectType + '/application/review/toAddGroup.action?entityId=' + id,
			callBack : function(dis, type, entityId){
				doSubmitGroupReview(dis, type, entityId, projectType, showProjectDetails);
			}
		});
	};
	
	//修改小组评审弹出层
	var modifyAppGroupReviewPop = function(id, projectType, showProjectDetails){
		popProjectOperation({
			title : "修改申请小组评审意见",
			src : 'project/' + projectType + '/application/review/toModifyGroup.action?entityId=' + id,
			callBack : function(dis, type, entityId){
				doSubmitGroupReview(dis, type, entityId, projectType, showProjectDetails);
			}
		});
	};
	
	//查看页面ajax提交小组评审信息
	var submitAppGroupReview = function(id, projectType, showProjectDetails){
		var url = 'project/' + projectType + '/application/review/submitGroup.action?entityId=' + id;
		if (confirm("提交后不能修改，您确定要提交吗？"))
		$("#review_appform").attr("action", url);
		$("#review_appform").ajaxSubmit({
			success:function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					viewAppApp.readApply(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	//查看小组评审
	var viewAppGroupReviewPop = function(id, projectType){
		popProjectOperation({
			title : "查看申请小组评审意见",
			src : 'project/' + projectType + '/application/review/viewGroup.action?entityId=' + id
		});
	};
	
	//弹出层页面ajax提交专家评审信息
	var doSubmitAppReview = function (dis, type, id, projectType, showProjectDetails){
		$("#appSubmitStatus").val(dis.submitStatus);
		$("#appInnovationScore").val(dis.innovationScore);
		$("#appScientificScore").val(dis.scientificScore);
		$("#appBenefitScore").val(dis.benefitScore);
		$("#appOpinion").val(dis.opinion);
		$("#appQualitativeOpinion").val(dis.qualitativeOpinion);
		var url;
		if(type == 1)
			url = "project/" + projectType + "/application/review/add.action?entityId="+id;
		else if(type == 2)
			url = "project/" + projectType + "/application/review/modify.action?entityId="+id;
		$("#review_appform").ajaxSubmit({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					viewAppApp.readApply(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};

	//弹出层页面ajax提交小组评审信息
	var doSubmitGroupReview = function(dis, type, entityId, projectType, showProjectDetails){
		$("#appReviewWay").val(dis.reviewWay);
		$("#appReviewResult").val(dis.reviewResult);
		$("#appReviewStatus").val(dis.reviewStatus);
		$("#appReviewOpinionQualitative").val(dis.reviewOpinionQualitative);
		$("#appReviewOpinion").val(dis.reviewOpinion);
		var url="";
		if(type==1)
			url = "project/" + projectType + "/application/review/addGroup.action?entityId="+entityId;
		else if(type==2)
			url = "project/" + projectType + "/application/review/modifyGroup.action?entityId="+entityId;
		$("#group_review_appform").ajaxSubmit({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					viewAppApp.readApply(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	var doSubmitAppReviewAudit = function(dis, entityId, type, projectType, showProjectDetails){
		$("#reviewAppAuditStatus").val(dis.reviewAuditStatus);
		$("#reviewAppAuditResult").val(dis.reviewAuditResult);
		$("#reviewAppAuditOpinion").val(dis.reviewAuditOpinion);
		$("#reviewAppAuditOpinionFeedback").val(dis.reviewAuditOpinionFeedback);
		$("input[name=reviewAuditDate]:eq(0)").val(dis.reviewAuditDate);
		$("input[name=number]:eq(0)").val(dis.number);
		$("input[name=approveFee]:eq(0)").val(dis.approveFee);
		var url="";
		if(type==1)
			url="project/" + projectType + "/application/reviewAudit/add.action?entityId="+entityId;
		else if(type==2)
			url="project/" + projectType + "/application/reviewAudit/modify.action?entityId="+entityId;
		$("#review_appAudit_form").ajaxSubmit({
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewAppApp.readApply(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	module.exports = {
		addAppReviewExpert: function(id, projectType, showProjectDetails){addAppReviewExpert(id, projectType, showProjectDetails)},
		addAppReviewResultPop: function(data, projectType, showProjectDetails){addAppReviewResultPop(data, projectType, showProjectDetails)},
		modifyAppReviewResultPop: function(data, projectType, showProjectDetails){modifyAppReviewResultPop(data, projectType, showProjectDetails)}, 
		submitAppReviewResult: function(data, projectType, showProjectDetails){submitAppReviewResult(data, projectType, showProjectDetails)},
		addAppReviewAuditPop: function(id, projectType, showProjectDetails){addAppReviewAuditPop(id, projectType, showProjectDetails)},
		modifyAppReviewAuditPop: function(id, projectType, showProjectDetails){modifyAppReviewAuditPop(id, projectType, showProjectDetails)},
		submitAppReviewAuditResult: function(id, projectType, showProjectDetails){submitAppReviewAuditResult(id, projectType, showProjectDetails)},
		viewAppReviewAuditPop: function(id, projectType){viewAppReviewAuditPop(id, projectType)},
		addAppReviewPop: function(id, projectType, showProjectDetails){addAppReviewPop(id, projectType, showProjectDetails)},
		modifyAppReviewPop: function(id, entityId, projectType, showProjectDetails){modifyAppReviewPop(id, entityId, projectType, showProjectDetails)},
		submitAppReview: function(id, projectType, showProjectDetails){submitAppReview(id, projectType, showProjectDetails)},
		viewAppReviewPop: function(appRevId, projectType){viewAppReviewPop(appRevId, projectType)},
		addAppGroupReviewPop: function(id, projectType, showProjectDetails){addAppGroupReviewPop(id, projectType, showProjectDetails)},
		modifyAppGroupReviewPop: function(id, projectType, showProjectDetails){modifyAppGroupReviewPop(id, projectType, showProjectDetails)},
		submitAppGroupReview: function(id, projectType, showProjectDetails){submitAppGroupReview(id, projectType, showProjectDetails)},
		viewAppGroupReviewPop: function(entityId, projectType){viewAppGroupReviewPop(entityId, projectType)}
	};
});
