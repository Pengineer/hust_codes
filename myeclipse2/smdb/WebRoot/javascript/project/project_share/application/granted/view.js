/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var timeValidate = require('javascript/project/project_share/validate');
	var viewProject = require('javascript/project/project_share/view');
	
	//查看立项信息
	var readGranted = function(projectType, showProjectDetails) {
		viewProject.viewProject("project/" + projectType + "/application/granted/view.action", showProjectDetails);
	};
	
	 //准备添加立项计划申请
    var toAddGraPlanPop = function(projectType, showProjectDetails) {
     	if($("#graStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	popProjectOperation({
 			title: "添加立项申请",
 			src : 'project/' + projectType + '/application/granted/toAddGra.action?projectid=' + $("#projectid").val(),
 			callBack : function(layer){
 				$("#update").val(1);
 				readGranted(projectType, showProjectDetails);
 				layer.destroy();
 			}
 		});
     	return false;
    };
    //准备修改立项计划申请
    var toModifyMidApplyPop = function(projectType, showProjectDetails) {
     	if($("#graStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	popProjectOperation({
     		title: "修改立项申请",
     		src : 'project/' + projectType + '/application/granted/toModifyGra.action?projectid=' + $("#projectid").val(),
     		callBack : function(layer){
     			$("#update").val(1);
     			readGranted(projectType, showProjectDetails);
     			layer.destroy();
     		}
     	});
    };
    
    
    //删除立项计划申请
    var deleteGraPlanPop = function(projectType, showProjectDetails) {
 		$.ajax({
 			url: "project/" + projectType + "/application/granted/deleteGra.action",
 			type: "post",
 			data: "projectid=" + $("#projectid").val(),
 			dataType: "json",
 			success: function(result){
 				if (result.errorInfo == null || result.errorInfo == "") {
 					$("#update").val(1);
 					readGranted(projectType, showProjectDetails);
 				}
 				else {
 					alert(result.errorInfo);
 				}
 			}	
 		});
    };
    
    //提交立项申请
    var submitGraApply = function(projectType, showProjectDetails) {
     	if($("#graStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
//     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
//     	if(flag){
     		if( !confirm('提交后无法修改，是否确认提交？')){
     			return;
     		}
     		$.ajax({
     			url: "project/" + projectType + "/application/granted/submit.action",
     			type: "post",
     			data: "projectid=" + $("#projectid").val(),
     			dataType: "json",
     			success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readGranted(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	
     		});
//     	}
    };
    
    //添加立项审核
    var addGraAuditPop = function(projectType, showProjectDetails) {
     	if($("#graStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var accountType = $("#accountType").val();
//     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
//     	if(flag){
     		//存在立项成果待审
     		popProjectOperation({
     			title : "添加审核意见",
     			src : "project/" + projectType + "/application/granted/applyAudit/toAdd.action?projectid=" + $("#projectid").val(),
     			callBack : function(dis){
     				doSubmitGraAudit(dis, projectType, showProjectDetails);
     			}
     		});
//     	}else{
//     		return false;
//     	}
    };
    
    //查看立项审核详情
    var viewGraAuditPop = function(id, data, projectType) {
     	popProjectOperation({
     		title : "查看审核详细意见",
     		src : 'project/' + "general" + '/application/granted/applyAudit/view.action?grantedId=' + id + '&vtp=' + data
     	});
    };
    
  //修改立项审核
    var modifyGraAuditPop = function(projectType, showProjectDetails) {
     	if($("#graStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
 		var accountType = $("#accountType").val();
 		var id = document.getElementsByName('projectid')[0].value;
 		popProjectOperation({
 			title : "修改审核意见",
 			src : "project/" + projectType + "/application/granted/applyAudit/toModify.action?projectid=" + id,
 			callBack : function(dis){
 				doSubmitGraAudit(dis, projectType, showProjectDetails);
 			}
 		});
    };
    
    //提交审核
    var submitGraAudit = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
 		if( !confirm('确认提交此审核意见？'))
 			return;
 		$("#midForm").ajaxSubmit({ 
 			url: "project/" + projectType + "/application/granted/applyAudit/submit.action",
 	       	success: function(result){
 				if (result.errorInfo == null || result.errorInfo == "") {
 					$("#update").val(1);
 					readGranted(projectType, showProjectDetails);
 				}
 				else {
 					alert(result.errorInfo);
 				}
 			}	 
 		});
    };
    
    //准备录入立项计划信息
    var addGraResultPop = function(projectType, showProjectDetails){
     	popProjectOperation({
     		title : "录入立项计划信息",
     		src : "project/" + projectType + "/application/granted/toAddResult.action?projectid=" + $("#projectid").val(),
     		callBack : function(dis){
     			doSubmitGraResult(dis, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };
    
    //准备修改立项计划信息
    var modifyGraResultPop = function(projectType, showProjectDetails){
     	popProjectOperation({
     		title : "修改立项计划信息",
     		src : 'project/' + projectType + '/application/granted/toModifyResult.action?projectid=' + $("#projectid").val(),
     		callBack : function(dis){
     			doSubmitGraResult(dis, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };
    
    //提交中检结果
    var submitGraResult = function(projectType, showProjectDetails){
     	if( !confirm('提交后无法修改，是否确认提交？')){
     		return;
     	}
     	$.ajax({
     		url: "project/" + projectType + "/application/granted/submitResult.action",
     		type: "post",
     		data: "projectid=" + $("#projectid").val(),
     		dataType: "json",
     		success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readGranted(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	
     	});
    };

    //向后台添加或修改中检结果
    var doSubmitGraResult = function(dis, projectType, showProjectDetails){
     	url = "";
     	if(dis.type == 1){
     		url = "project/" + projectType + "/application/granted/addResult.action";
     	}else if(dis.type == 2){
     		url = "project/" + projectType + "/application/granted/modifyResult.action";
     	}
     	$.ajax({
     		url: url,
     		type: "post",
     		data: "projectid=" + $("#projectid").val() + "&graResult=" + dis.graResult + "&graImportedStatus=" + dis.graStatus + 
     			  "&graDate=" + dis.graDate + "&graImportedOpinion=" + dis.graImportedOpinion + "&graOpinionFeedback=" + dis.graOpinionFeedback + "&bookFee=" + dis.bookFee + "&bookNote=" + dis.bookNote + 
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
																			"&totalFee=" + dis.totalFee + "&feeNote=" + dis.feeNote,
     		dataType: "json",
     		success:  function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readGranted(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	 
     	});
    };
    
    
    //ajax提交立项的审核意见
    var doSubmitGraAudit = function(data, projectType, showProjectDetails) {
     	var xx = document.getElementsByName('graAuditOpinion')[0];
     	var yy = document.getElementsByName('graAuditStatus')[0];
     	var yyy = document.getElementsByName('graAuditResult')[0];
     	var zz = document.getElementById('midFormProjectid');
     	var tmp = document.getElementById('projectid');
     	xx.value = data.auditOpinion;
     	yy.value = data.auditStatus;
     	yyy.value = data.auditResult;
     	zz.value = tmp.value;
     	$('#graAuditOpinionFeedback').val(data.graAuditOpinionFeedback);
     	var type = data.type;
     	var url = "";
     	if(type == 1){
     		url = "project/" + projectType + "/application/granted/applyAudit/add.action";
     	}else if(type == 2){
     		url = "project/" + projectType + "/application/granted/applyAudit/modify.action";
     	}
     	$("#GraForm").ajaxSubmit({ 
     		url: url,
           	success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readGranted(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	 
     	});
    };
    
	
	module.exports = {
		readGranted: function(projectType, showProjectDetails){readGranted(projectType, showProjectDetails)},
		toAddGraPlanPop: function(projectType, showProjectDetails){toAddGraPlanPop(projectType, showProjectDetails)},
		toModifyMidApplyPop: function(projectType, showProjectDetails){toModifyMidApplyPop(projectType, showProjectDetails)},
		submitGraApply: function(projectType, showProjectDetails){submitGraApply(projectType, showProjectDetails)},
		addGraAuditPop: function(projectType, showProjectDetails){addGraAuditPop(projectType, showProjectDetails)},
		viewGraAuditPop: function(projectType, showProjectDetails){viewGraAuditPop(projectType, showProjectDetails)},
		modifyGraAuditPop: function(projectType, showProjectDetails){modifyGraAuditPop(projectType, showProjectDetails)},
		submitGraAudit:function(projectType, showProjectDetails){submitGraAudit(projectType, showProjectDetails)},
		deleteGraPlanPop:function(projectType, showProjectDetails){deleteGraPlanPop(projectType, showProjectDetails)},
		addGraResultPop:function(projectType, showProjectDetails){addGraResultPop(projectType, showProjectDetails)},
		modifyGraResultPop:function(projectType, showProjectDetails){modifyGraResultPop(projectType, showProjectDetails)},
		submitGraResult:function(projectType, showProjectDetails){submitGraResult(projectType, showProjectDetails)}
	};
});
