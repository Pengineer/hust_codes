/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var project = require('javascript/project/project_share/project');
	var timeValidate = require('javascript/project/project_share/validate');
	var viewProject = require('javascript/project/project_share/view');
	
	//查看中检
	var readMid = function(projectType, showProjectDetails) {
		viewProject.viewProject("project/" + projectType + "/midinspection/apply/view.action", showProjectDetails);
	};
	 
	//下载中检文件
    var downloadMidTemplate = function(projectType) {
	 	validateUrl = 'project/' + projectType + '/midinspection/apply/validateTemplate.action?projectid='+$("#projectid").val();
	 	successUrl = 'project/' + projectType + '/midinspection/apply/downloadTemplate.action';
	 	downloadFile(validateUrl, successUrl);
	 	return false;
	};
     
   //准备添加中检申请
    var toAddMidApplyPop = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	popProjectOperation({
 			title: "添加中检申请",
 			src : 'project/' + projectType + '/midinspection/apply/toAdd.action?projectid=' + $("#projectid").val(),
 			callBack : function(layer){
 				$("#update").val(1);
 				readMid(projectType, showProjectDetails);
 				layer.destroy();
 			}
 		});
     	return false;
    };
     
   //准备修改中检申请
    var toModifyMidApplyPop = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	popProjectOperation({
     		title: "修改中检申请",
     		src : 'project/' + projectType + '/midinspection/apply/toModify.action?projectid=' + $("#projectid").val(),
     		callBack : function(layer){
     			$("#update").val(1);
     			readMid(projectType, showProjectDetails);
     			layer.destroy();
     		}
     	});
    };

     //提交中检申请
    var submitMidApply = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
     	if(flag){
     		if( !confirm('提交后无法修改且不能再添加中检成果，是否确认提交？')){
     			return;
     		}
     		$.ajax({
     			url: "project/" + projectType + "/midinspection/apply/submit.action",
     			type: "post",
     			data: "projectid=" + $("#projectid").val(),
     			dataType: "json",
     			success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readMid(projectType, showProjectDetails);
     				}
     				else {
     					alert(result.errorInfo);
     				}
     			}	
     		});
     	}
    };
     
   //删除中检申请
    var deleteMidApply = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
     	if(flag){
     		if( !confirm('确认删除此中检申请？'))
     			return;
     		$.ajax({
     			url: "project/" + projectType + "/midinspection/apply/delete.action",
     			type: "post",
     			data: "entityIds=" + $("#projectid").val(),
     			dataType: "json",
     			success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readMid(projectType, showProjectDetails);
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

     //添加中检审核
    var addMidAuditPop = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var accountType = $("#accountType").val();
     	var midProAudAlr = $("#midProAudAlr").val();
     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
     	if(flag){
     		//存在中检成果待审
     		if($("#canAuditMidProduct").val() == 'true') {
     			var auditflag = true;
     			if(accountType != "MINISTRY") {//初审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_mid_0")).each(function() {
     					if($(this).next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			} else {//终审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_mid_0")).each(function() {
     					if($(this).next().next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			}
     			if(!auditflag) {
     				alert("请先审核完中检成果, 否则不能进行此操作！");
     				return;
     			}
     		}
     		var id = document.getElementsByName('projectid')[0].value;
     		popProjectOperation({
     			title : "添加审核意见",
     			src : "project/" + projectType + "/midinspection/applyAudit/toAdd.action?projectid=" + id,
     			callBack : function(dis){
     				doSubmitMidAudit(dis, projectType, showProjectDetails);
     			}
     		});
     	}else{
     		return false;
     	}
    };

     //修改中检审核
    var modifyMidAuditPop = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
     	if(flag){
     		var accountType = $("#accountType").val();
     		var midProAudAlr = $("#midProAudAlr").val();
     		//存在中检成果待审
     		if($("#canAuditMidProduct").val() == 'true') {
     			var auditflag = true;
     			if(accountType != "MINISTRY") {//初审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_mid_0")).each(function() {
     					if($(this).next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			} else {//终审
     				$("input[type='checkbox'][name='entityIds']", $("#list_table_mid_0")).each(function() {
     					if($(this).next().next().val() == 0) {//待审
     						auditflag = false; return;
     					}
     				});
     			}
     			if(!auditflag) {
     				alert("请先审核完中检成果, 否则不能进行此操作！");
     				return;
     			}
     		}
     		var id = document.getElementsByName('projectid')[0].value;
     		popProjectOperation({
     			title : "修改审核意见",
     			src : "project/" + projectType + "/midinspection/applyAudit/toModify.action?projectid=" + id,
     			callBack : function(dis){
     				doSubmitMidAudit(dis, projectType, showProjectDetails);
     			}
     		});
     	}else{
     		return false;
     	}
    };
     //查看中检审核详情
    var viewMidAuditPop = function(id, data, projectType) {
     	popProjectOperation({
     		title : "查看审核详细意见",
     		src : 'project/' + projectType + '/midinspection/applyAudit/view.action?midId=' + id + '&vtp=' + data
     	});
    };
     //提交审核
    var submitMidAudit = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
     	if(flag){
     		if( !confirm('确认提交此审核意见？'))
     			return;
     		$("#midForm").ajaxSubmit({ 
     			url: "project/" + projectType + "/midinspection/applyAudit/submit.action",
     	       	success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readMid(projectType, showProjectDetails);
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
    
    //退回中检
    var backMidApply = function(projectType, showProjectDetails) {
     	if($("#midStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineMid", $("#midinspection")).val());
     	if(flag){
     		if( !confirm('确认退回此中检申请？'))
     			return;
     		$("#midForm").ajaxSubmit({ 
     			url: "project/" + projectType + "/midinspection/applyAudit/back.action",
     	        success: function(result){
     				if (result.errorInfo == null || result.errorInfo == "") {
     					$("#update").val(1);
     					readMid(projectType, showProjectDetails);
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
     //准备录入中检结果信息
    var addMidResultPop = function(projectType, showProjectDetails){
     	popProjectOperation({
     		title : "录入中检结果信息",
     		src : "project/" + projectType + "/midinspection/apply/toAddResult.action?projectid=" + $("#projectid").val(),
     		callBack : function(dis){
     			doSubmitMidResult(dis, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };

     //准备修改中检结果信息
    var modifyMidResultPop = function(projectType, showProjectDetails){
     	popProjectOperation({
     		title : "修改中检结果信息",
     		src : 'project/' + projectType + '/midinspection/apply/toModifyResult.action?projectid=' + $("#projectid").val(),
     		callBack : function(dis){
     			doSubmitMidResult(dis, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };

     //提交中检结果
    var submitMidResult = function(projectType, showProjectDetails){
     	if( !confirm('提交后无法修改，是否确认提交？')){
     		return;
     	}
     	$.ajax({
     		url: "project/" + projectType + "/midinspection/apply/submitResult.action",
     		type: "post",
     		data: "projectid=" + $("#projectid").val(),
     		dataType: "json",
     		success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readMid(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	
     	});
    };
     //ajax提交中检的审核意见
    var doSubmitMidAudit = function(data, projectType, showProjectDetails) {
     	var xx = document.getElementsByName('midAuditOpinion')[0];
     	var yy = document.getElementsByName('midAuditStatus')[0];
     	var yyy = document.getElementsByName('midAuditResult')[0];
     	var zz = document.getElementById('midFormProjectid');
     	var tmp = document.getElementById('projectid');
     	xx.value = data.auditOpinion;
     	yy.value = data.auditStatus;
     	yyy.value = data.auditResult;
     	zz.value = tmp.value;
     	$('#midAuditOpinionFeedback').val(data.midAuditOpinionFeedback);
     	var type = data.type;
     	var url = "";
     	if(type == 1){
     		url = "project/" + projectType + "/midinspection/applyAudit/add.action";
     	}else if(type == 2){
     		url = "project/" + projectType + "/midinspection/applyAudit/modify.action";
     	}
     	$("#midForm").ajaxSubmit({ 
     		url: url,
           	success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readMid(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	 
     	});
    };
    
    //向后台添加或修改中检结果
    var doSubmitMidResult = function(dis, projectType, showProjectDetails){
     	url = "";
     	if(dis.type == 1){
     		url = "project/" + projectType + "/midinspection/apply/addResult.action";
     	}else if(dis.type == 2){
     		url = "project/" + projectType + "/midinspection/apply/modifyResult.action";
     	}
     	$.ajax({
     		url: url,
     		type: "post",
     		data: "projectid=" + $("#projectid").val() + "&fileIds=" + dis.fileIds + "&midResult=" + dis.midResult + "&midImportedStatus=" + dis.midStatus + 
     			  "&midDate=" + dis.midDate + "&midImportedOpinion=" + dis.midImportedOpinion + "&midOpinionFeedback=" + dis.midOpinionFeedback + "&bookFee=" + dis.bookFee + "&bookNote=" + dis.bookNote + 
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
     				readMid(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	 
     	});
    };

     //准备上传中检申请书
    var uploadMidPop = function(midId, projectType, showProjectDetails){
     	popProjectOperation({
     		title : "上传电子文档",
     		src : 'project/' + projectType + '/midinspection/apply/toUploadFileResult.action?midId=' + midId,
     		inData : {"midId" : midId},
     		callBack : function(dis, type){
     			doSubmitUploadMidResult(dis, type, projectType, showProjectDetails);
     		}
     	});
     	return false;
    };

    var doSubmitUploadMidResult = function (dis, type, projectType, showProjectDetails){
     	url = "project/" + projectType + "/midinspection/apply/uploadFileResult.action?projectid=" + $("#projectid").val();
     	$("#midFileId").val(dis.midFileId);
     	$("#midId").val(dis.midId);
     	$("#uploadMidFile_form").ajaxSubmit({ 
     		url: url,
             success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readMid(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	  
     	});
    };
    var switchMidPublish = function(projectType) {
    	$.ajax({
			url:"project/" + projectType + "/midinspection/apply/switchPublish.action",
			data:{
				midResultPublish:$("input[name='midinspection.finalAuditResultPublish']").val(),
				midId:$("input[name='midinspection.id']").val()
			},
			dataType:"json",
			type:"post",
			success:function(){
				if($(".j_switchMidPublish").val() == "发布") {
					$("#midinspection_finalAuditResultPublishStatus").text("已发布");
					$(".j_switchMidPublish").val("取消发布");
				}
				else {
					$("#midinspection_finalAuditResultPublishStatus").text("未发布");
					$(".j_switchMidPublish").val("发布");
				}
				$("input[name='midinspection.finalAuditResultPublish']").val(1 - parseInt($("input[name='midinspection.finalAuditResultPublish']").val()));
			}
		});
    };
    module.exports = {
    	 toAddMidApplyPop: function(projectType, showProjectDetails){toAddMidApplyPop(projectType, showProjectDetails)},
     	 toModifyMidApplyPop: function(projectType, showProjectDetails){toModifyMidApplyPop(projectType, showProjectDetails)}, 
     	 submitMidApply: function(projectType, showProjectDetails){submitMidApply(projectType, showProjectDetails)},
     	 deleteMidApply: function(projectType, showProjectDetails){deleteMidApply(projectType, showProjectDetails)},
     	 addMidAuditPop: function(projectType, showProjectDetails){addMidAuditPop(projectType, showProjectDetails)},
     	 modifyMidAuditPop: function(projectType, showProjectDetails){modifyMidAuditPop(projectType, showProjectDetails)},
     	 viewMidAuditPop: function(id, data, projectType){viewMidAuditPop(id, data, projectType)},
     	 submitMidAudit: function(projectType, showProjectDetails){submitMidAudit(projectType, showProjectDetails)},
     	 backMidApply: function(projectType, showProjectDetails){backMidApply(projectType, showProjectDetails)},
     	 addMidResultPop: function(projectType, showProjectDetails){addMidResultPop(projectType, showProjectDetails)},
     	 modifyMidResultPop: function(projectType, showProjectDetails){modifyMidResultPop(projectType, showProjectDetails)},
     	 submitMidResult: function(projectType, showProjectDetails){submitMidResult(projectType, showProjectDetails)},
     	 uploadMidPop: function(midId, projectType, showProjectDetails){uploadMidPop(midId, projectType, showProjectDetails)},
     	 downloadMidTemplate: function(projectType){downloadMidTemplate(projectType)},
     	 switchMidPublish: function(projectType){switchMidPublish(projectType);}
    };
});
