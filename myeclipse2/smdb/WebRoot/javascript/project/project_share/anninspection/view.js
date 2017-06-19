/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var project = require('javascript/project/project_share/project');
	var timeValidate = require('javascript/project/project_share/validate');
	var viewProject = require('javascript/project/project_share/view');
	
	//查看年检
	var readAnn = function(projectType, showProjectDetails) {
		viewProject.viewProject("project/" + projectType + "/anninspection/apply/view.action", showProjectDetails);
	};
	 
	//下载年检文件
    var downloadAnnTemplate = function(projectType) {
	 	validateUrl = 'project/' + projectType + '/anninspection/apply/validateTemplate.action?projectid='+$("#projectid").val();
	 	successUrl = 'project/' + projectType + '/anninspection/apply/downloadTemplate.action';
	 	downloadFile(validateUrl, successUrl);
	 	return false;
	};
     
   //准备添加年检申请
    var toAddAnnApplyPop = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	popProjectOperation({
 			title: "添加年检申请",
 			src : 'project/' + projectType + '/anninspection/apply/toAdd.action?projectid=' + $("#projectid").val(),
 			callBack : function(layer){
 				$("#update").val(1);
 				readAnn(projectType, showProjectDetails);
 				layer.destroy();
 			}
 		});
     	return false;
    };
     
   //准备修改年检申请
    var toModifyAnnApplyPop = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	popProjectOperation({
     		title: "修改年检申请",
     		src : 'project/' + projectType + '/anninspection/apply/toModify.action?projectid=' + $("#projectid").val(),
     		callBack : function(layer){
     			$("#update").val(1);
     			readAnn(projectType, showProjectDetails);
     			layer.destroy();
     		}
     	});
    };

     //提交年检申请
    var submitAnnApply = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineAnn", $("#anninspection")).val());
     	if(flag){
     		if( !confirm('提交后无法修改且不能再添加年检成果，是否确认提交？')){
     			return;
     		}
     		$.ajax({
     			url: "project/" + projectType + "/anninspection/apply/submit.action",
     			type: "post",
     			data: "projectid=" + $("#projectid").val(),
     			dataType: "json",
     			success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readAnn(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	
     		});
     	}
    };
     
   //删除年检申请
    var deleteAnnApply = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineAnn", $("#anninspection")).val());
     	if(flag){
     		if( !confirm('确认删除此年检申请？'))
     			return;
     		$.ajax({
     			url: "project/" + projectType + "/anninspection/apply/delete.action",
     			type: "post",
     			data: "entityIds=" + $("#projectid").val(),
     			dataType: "json",
     			success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readAnn(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	
     		});
     	}else{
     		return false;
     	}
    };

     //添加年检审核
    var addAnnAuditPop = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var accountType = $("#accountType").val();
     	var annProAudAlr = $("#annProAudAlr").val();
     	var flag = timeValidate.timeValidate($("#deadlineAnn", $("#anninspection")).val());
     	if(flag){
     		//存在年检成果待审
     		if($("#canAuditAnnProduct").val() == 'true') {
     			var auditflag = true;
     			if(accountType != "MINISTRY") {//初审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_ann_0")).each(function() {
     					if($(this).next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			} else {//终审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_ann_0")).each(function() {
     					if($(this).next().next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			}
     			if(!auditflag) {
     				alert("请先审核完年检成果, 否则不能进行此操作！");
     				return;
     			}
     		}
     		var id = document.getElementsByName('projectid')[0].value;
     		popProjectOperation({
     			title : "添加审核意见",
     			src : "project/" + projectType + "/anninspection/applyAudit/toAdd.action?projectid=" + id,
     			callBack : function(dis){
     				doSubmitAnnAudit(dis, projectType, showProjectDetails);
     			}
     		});
     	}else{
     		return false;
     	}
    };

     //修改年检审核
    var modifyAnnAuditPop = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineAnn", $("#anninspection")).val());
     	if(flag){
     		var accountType = $("#accountType").val();
     		var annProAudAlr = $("#annProAudAlr").val();
     		//存在年检成果待审
     		if($("#canAuditAnnProduct").val() == 'true') {
     			var auditflag = true;
     			if(accountType != "MINISTRY") {//初审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_ann_0")).each(function() {
     					if($(this).next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			} else {//终审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_ann_0")).each(function() {
     					if($(this).next().next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			}
     			if(!auditflag) {
     				alert("请先审核完年检成果, 否则不能进行此操作！");
     				return;
     			}
     		}
     		var id = document.getElementsByName('projectid')[0].value;
     		popProjectOperation({
     			title : "修改审核意见",
     			src : "project/" + projectType + "/anninspection/applyAudit/toModify.action?projectid=" + id,
     			callBack : function(dis){
     				doSubmitAnnAudit(dis, projectType, showProjectDetails);
     			}
     		});
     	}else{
     		return false;
     	}
    };
     //查看年检审核详情
    var viewAnnAuditPop = function(id, data, projectType) {
     	popProjectOperation({
     		title : "查看审核详细意见",
     		src : 'project/' + projectType + '/anninspection/applyAudit/view.action?annId=' + id + '&vtp=' + data
     	});
    };
     //提交审核
    var submitAnnAudit = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineAnn", $("#anninspection")).val());
     	if(flag){
     		if( !confirm('确认提交此审核意见？'))
     			return;
     		$("#annForm").ajaxSubmit({ 
     			url: "project/" + projectType + "/anninspection/applyAudit/submit.action",
     	       	success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readAnn(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	 
     		});
     	}else{
     		return false;
     	}
    };
    
    //退回年检
    var backAnnApply = function(projectType, showProjectDetails) {
     	if($("#annStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineAnn", $("#anninspection")).val());
     	if(flag){
     		if( !confirm('确认退回此年检申请？'))
     			return;
     		$("#annForm").ajaxSubmit({ 
     			url: "project/" + projectType + "/anninspection/applyAudit/back.action",
     	        success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readAnn(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	  
     		});
     	}else{
     		return false;
     	}
    };
     //准备录入年检结果信息
    var addAnnResultPop = function(projectType, showProjectDetails){
     	popProjectOperation({
     		title : "录入年检结果信息",
     		src : "project/" + projectType + "/anninspection/apply/toAddResult.action?projectid=" + $("#projectid").val(),
     		callBack : function(dis){
     			doSubmitAnnResult(dis, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };

     //准备修改年检结果信息
    var modifyAnnResultPop = function(projectType, showProjectDetails){
     	popProjectOperation({
     		title : "修改年检结果信息",
     		src : 'project/' + projectType + '/anninspection/apply/toModifyResult.action?projectid=' + $("#projectid").val(),
     		callBack : function(dis){
     			doSubmitAnnResult(dis, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };

     //提交年检结果
    var submitAnnResult = function(projectType, showProjectDetails){
     	if( !confirm('提交后无法修改，是否确认提交？')){
     		return;
     	}
     	$.ajax({
     		url: "project/" + projectType + "/anninspection/apply/submitResult.action",
     		type: "post",
     		data: "projectid=" + $("#projectid").val(),
     		dataType: "json",
     		success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readAnn(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	
     	});
    };
     //ajax提交年检的审核意见
    var doSubmitAnnAudit = function(data, projectType, showProjectDetails) {
     	var xx = document.getElementsByName('annAuditOpinion')[0];
     	var yy = document.getElementsByName('annAuditStatus')[0];
     	var yyy = document.getElementsByName('annAuditResult')[0];
     	var zz = document.getElementById('annFormProjectid');
     	var tmp = document.getElementById('projectid');
     	xx.value = data.auditOpinion;
     	yy.value = data.auditStatus;
     	yyy.value = data.auditResult;
     	zz.value = tmp.value;
     	$('#annAuditOpinionFeedback').val(data.annAuditOpinionFeedback);
     	var type = data.type;
     	var url = "";
     	if(type == 1){
     		url = "project/" + projectType + "/anninspection/applyAudit/add.action";
     	}else if(type == 2){
     		url = "project/" + projectType + "/anninspection/applyAudit/modify.action";
     	}
     	$("#annForm").ajaxSubmit({ 
     		url: url,
           	success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readAnn(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	 
     	});
    };
    
    //向后台添加或修改年检结果
    var doSubmitAnnResult = function(dis, projectType, showProjectDetails){
     	url = "";
     	if(dis.type == 1){
     		url = "project/" + projectType + "/anninspection/apply/addResult.action";
     	}else if(dis.type == 2){
     		url = "project/" + projectType + "/anninspection/apply/modifyResult.action";
     	}
     	$.ajax({
     		url: url,
     		type: "post",
     		data: "projectid=" + $("#projectid").val() + "&fileIds=" + dis.fileIds + "&annResult=" + dis.annResult + "&annYear=" + dis.annYear + "&annImportedStatus=" + dis.annStatus + 
     			  "&annDate=" + dis.annDate + "&annImportedOpinion=" + dis.annImportedOpinion + "&annOpinionFeedback=" + dis.annOpinionFeedback + "&bookFee=" + dis.bookFee + "&bookNote=" + dis.bookNote + 
																			"&dataFee=" + dis.dataFee + "&dataNote=" + dis.dataNote +
																			"&travelFee=" + dis.travelFee + "&travelNote=" + dis.travelNote +
																			"&conferenceFee=" + dis.conferenceFee + "&conferenceNote=" + dis.conferenceNote +
																			"&internationalFee=" + dis.internationalFee + "&internationalNote=" + dis.internationalNote +
																			"&deviceFee=" + dis.deviceFee + "&deviceNote=" + dis.deviceNote +
																			"&consultationFee=" + dis.consultationFee + "&consultationNote=" + dis.consultationNote +
																			"&laborFee=" + dis.laborFee + "&laborNote=" + dis.laborNote +
																			"&printFee=" + dis.printFee + "&printNote=" + dis.printNote +
																			"&indirectFee=" +dis.indirectFee + "&indirectNote=" + dis.indirectNote +
																			"&otherFeeD=" + dis.otherFeeD + "&otherNote=" + dis.otherNote +
																			"&totalFee=" + dis.totalFee + "&fundedFee=" + dis.fundedFee + "&feeNote=" + dis.feeNote + "&surplusFee=" + dis.surplusFee,
     		dataType: "json",
     		success:  function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readAnn(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	 
     	});
    };

     //准备上传年检申请书
    var uploadAnnPop = function(annId, projectType, showProjectDetails){
     	popProjectOperation({
     		title : "上传电子文档",
     		src : 'project/' + projectType + '/anninspection/apply/toUploadFileResult.action?annId=' + annId,
     		inData : {"annId" : annId},
     		callBack : function(dis, type){
     			doSubmitUploadAnnResult(dis, type, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };

    var doSubmitUploadAnnResult = function (dis, type, projectType, showProjectDetails){
     	url = "project/" + projectType + "/anninspection/apply/uploadFileResult.action?projectid=" + $("#projectid").val();
     	$("#annFileId").val(dis.annFileId);
     	$("#annId").val(dis.annId);
     	$("#uploadAnnFile_form").ajaxSubmit({ 
     		url: url,
             success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readAnn(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	  
     	});
    };
    
    module.exports = {
    	 toAddAnnApplyPop: function(projectType, showProjectDetails){toAddAnnApplyPop(projectType, showProjectDetails)},
     	 toModifyAnnApplyPop: function(projectType, showProjectDetails){toModifyAnnApplyPop(projectType, showProjectDetails)}, 
     	 submitAnnApply: function(projectType, showProjectDetails){submitAnnApply(projectType, showProjectDetails)},
     	 deleteAnnApply: function(projectType, showProjectDetails){deleteAnnApply(projectType, showProjectDetails)},
     	 addAnnAuditPop: function(projectType, showProjectDetails){addAnnAuditPop(projectType, showProjectDetails)},
     	 modifyAnnAuditPop: function(projectType, showProjectDetails){modifyAnnAuditPop(projectType, showProjectDetails)},
     	 viewAnnAuditPop: function(id, data, projectType){viewAnnAuditPop(id, data, projectType)},
     	 submitAnnAudit: function(projectType, showProjectDetails){submitAnnAudit(projectType, showProjectDetails)},
     	 backAnnApply: function(projectType, showProjectDetails){backAnnApply(projectType, showProjectDetails)},
     	 addAnnResultPop: function(projectType, showProjectDetails){addAnnResultPop(projectType, showProjectDetails)},
     	 modifyAnnResultPop: function(projectType, showProjectDetails){modifyAnnResultPop(projectType, showProjectDetails)},
     	 submitAnnResult: function(projectType, showProjectDetails){submitAnnResult(projectType, showProjectDetails)},
     	 uploadAnnPop: function(annId, projectType, showProjectDetails){uploadAnnPop(annId, projectType, showProjectDetails)},
     	 downloadAnnTemplate: function(projectType){downloadAnnTemplate(projectType)}
    };
});
