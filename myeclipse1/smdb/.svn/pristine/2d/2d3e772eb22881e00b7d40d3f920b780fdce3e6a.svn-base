/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var viewProject = require('javascript/project/project_share/view');
	var project = require('javascript/project/project_share/project');
	var timeValidate = require('javascript/project/project_share/validate');
	
	var viewPerson = function(id){
		popPerson(id, 7);
		return false;
	};
	
	var viewUniversity = function(id){
		popAgency(id, 1);
		return false;
	};
	
	var viewDepartment = function(id){
		popDept(id, 2);
		return false;
	};
	
	var viewInstitute = function(id){
		popInst(id, 3);
		return false;
	}
	
	var viewFee = function(id, projectType){
		popProjectOperation({
			title: "查看详细信息",
			src : "project/" + projectType + "/variation/apply/viewFee.action?varId="+id
		});
		return false;
	};
	
	var viewOther = function(id, projectType){
		popProjectOperation({
			title: "查看详细信息",
			src : "project/" + projectType + "/variation/apply/viewOther.action?varId="+id
		});
		return false;
	};
	
	//查看变更
	var viewVar = function(projectType, showProjectDetails) {
		 viewProject.viewProject("project/" + projectType + "/variation/apply/view.action", showProjectDetails);
	 };
	 
	//下载变更文件
     var downloadVarTemplate = function(projectType) {
	 	validateUrl = 'project/' + projectType + '/variation/apply/validateTemplate.action?projectid='+$("#projectid").val();
	 	successUrl = 'project/' + projectType + '/variation/apply/downloadTemplate.action';
	 	downloadFile(validateUrl, successUrl);
	 	return false;
	 };
     
   //准备添加变更申请
     var addVar = function(projectType) {
     	if($("#varStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
    	if(flag){
    		var proId = $("#projectid").val();
    		var entityId = $("#entityId").val();
    		var listType = $("#listType").val();
    		var selectedTab = $("#selectedTab").val();
    		window.location.href = basePath + 'project/' + projectType + '/variation/apply/toAdd.action?projectid=' + proId + '&entityId=' + entityId + '&listType=' + listType + '&selectedTab=' + selectedTab;
    		return false;
    	}else{
    		return false;
    	}
     };
		
   //准备修改变更申请
     var modVar = function(varId, projectType) {
    	if($("#varStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
		if(flag){
			var proId = $("#projectid").val();
			var listType = $("#listType").val();
			var selectedTab = $("#selectedTab").val();
			window.location.href = basePath + 'project/' + projectType + '/variation/apply/toModify.action?varId='+varId+'&projectid='+proId+'&listType='+listType+'&selectedTab='+selectedTab;
			return false;
		}else{
			return false;
		}
     };

     //提交变更申请
     var subVar = function(varId, projectType, showProjectDetails) {
    	if($("#varStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
		if(flag){
			var proId = document.getElementById('projectid').value;
			if(confirm("提交后不能修改，您确定要提交吗？")) {
				varSubmitform('project/' + projectType + '/variation/apply/submit.action?varId='+varId + '&projectid=' + proId, projectType, showProjectDetails);
			}
			return false;
		}else{
			return false;
		}
     };
     
   //删除变更申请
     var delVar = function(varId, projectType, showProjectDetails) {
    	if($("#varStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
		if(flag){
			var proId = document.getElementById('projectid').value;
			if(confirm("您确定要删除吗？")) {
				varSubmitform('project/' + projectType + '/variation/apply/delete.action?varId='+varId + '&entityIds=' + proId, projectType, showProjectDetails);
			}
			return false;
		}else{
			return false;
		}
     };
     
   //添加录入变更
     var addVarResultPop = function(projectType, showProjectDetails) {
     	var projectid = document.getElementById('projectid').value;
     	popProjectOperation({
     		title: "录入变更结果信息",
     		src : 'project/' + projectType + '/variation/apply/toAddResult.action?projectid=' + projectid,
     		inData:{
     			"universityId": $(".universityId").attr("id"),
     			"departmentOrInstitute": $("#departmentOrInstitute").val()
     		},
     		callBack : function(layer){
     			$("#update").val(1);
     			viewVar(projectType, showProjectDetails);
     			layer.destroy();
     		}
     	});
     	return false;
     };
     
   //修改录入变更
     var modifyVarResultPop = function(varId, projectType, showProjectDetails) {
     	var projectid = document.getElementById('projectid').value;
     	popProjectOperation({
     		title: "修改变更结果信息",
     		src : 'project/' + projectType + '/variation/apply/toModifyResult.action?varId=' + varId + "&projectid=" + projectid,
     		inData:{
     			"universityId": $(".universityId").attr("id"),
     			"departmentOrInstitute": $("#departmentOrInstitute").val()
     		},
     		callBack : function(layer){
     			$("#update").val(1);
     			viewVar(projectType, showProjectDetails);
     			layer.destroy();
     		}
     	});
     };
     
     //添加变更审核
     var addVarAuditPop = function (varId, projectType, showProjectDetails){
     	if($("#varStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
     	if(flag){
     		if($("#endPassAlready").val() == 1) {
     			alert("该项目已结项，无法进行此操作！");
     			return;
     		}
     		popProjectOperation({
     			title:"添加审核",
     			src : "project/" + projectType + "/variation/applyAudit/toAdd.action?varId="+varId,
     			callBack : function(dis){
     				doSubmitVarAudit(dis, projectType, showProjectDetails);
     			}
     		});
     	}else{
     		return false;
     	}
     };
     
     //修改变更审核
     var modVarAuditPop = function(varId, projectType, showProjectDetails){
     	if($("#varStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
     	if(flag){
     		if($("#endPassAlready").val() == 1) {
     			alert("该项目已结项，无法进行此操作！");
     			return;
     		}
     		popProjectOperation({
     			title:"修改审核",
     			src : "project/" + projectType + "/variation/applyAudit/toModify.action?varId="+varId,
     			callBack : function(dis){
     				doSubmitVarAudit(dis, projectType, showProjectDetails);
     			}
     		});
     	}else{
     		return false;
     	}
     };
     
   //提交变更审核
     var subVarAudit = function(varId, projectType, showProjectDetails){
     	if($("#varStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
     	if(flag){
     		if($("#endPassAlready").val() == 1) {
     			alert("该项目已结项，无法进行此操作！");
     			return;
     		}
     		if (confirm("提交后不能修改，您确定要提交吗？")) 
     			varSubmitform("project/" + projectType + "/variation/applyAudit/submit.action?varId="+varId, projectType, showProjectDetails);
     		return false;
     	}else{
     		return false;
     	}
     };
     
     //退回变更审核
     var backVarAudit = function(varId, projectType, showProjectDetails){
     	if($("#varStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineVar", $("#variation")).val());
     	if(flag){
     		if($("#endPassAlready").val() == 1) {
     			alert("该项目已结项，无法进行此操作！");
     			return;
     		}
     		if (confirm("您确定要退回吗？")) 
     			varSubmitform("project/"+ projectType +"/variation/applyAudit/back.action?varId="+varId, projectType, showProjectDetails);
     		return false;
     	}else{
     		return false;
     	}
     };
     
     var viewVarAuditPop = function(varId, auditViewFlag, projectType){
		popProjectOperation({
			title:"查看详情",
			src : "project/"+ projectType +"/variation/applyAudit/view.action?varId="+varId+"&auditViewFlag="+auditViewFlag
		});
	 };

     /**
      * 提交申请
      * @param {Object} url
      */
     var varSubmitform = function(url, projectType, showProjectDetails){
     	$("#var_form").attr("action", url);
     	$("#var_form").ajaxSubmit({
     		success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				viewVar(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}
     	});
     };
     
   //ajax提交审核意见
     var doSubmitVarAudit = function(data, projectType, showProjectDetails){
     	$('#varAuditStatus').val(data.auditStatus);
     	$('#varAuditResult').val(data.auditResult);
     	$('#varAuditOpinion').val(data.auditOpinion);
     	$('#varAuditOpinionFeedback').val(data.varAuditOpinionFeedback);
     	$('#varAuditSelectIssue').val(data.varAuditSelectIssue);
     	$('#varAuditDate').val(data.varDate);
     	
     	var varId = data.varId;
     	var type = data.type;
     	var url = "";
     	if(type == 1){
     		url = "project/" + projectType + "/variation/applyAudit/add.action?varId="+varId;
     	}else if(type == 2){
     		url = "project/" + projectType + "/variation/applyAudit/modify.action?varId="+varId
     	}
     	$("#var_audit_form").ajaxSubmit({
     		url: url,
     		success:function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				viewVar(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}
     	});
     };
     
   //准备上传变更申请书
     var uploadVarPop = function(variId, projectType, showProjectDetails){
     	popProjectOperation({
     		title : "上传电子文档",
     		src : 'project/' + projectType + '/variation/apply/toUploadFileResult.action?variId=' + variId,
     		inData : {"variId" : variId},
     		callBack : function(dis, type){
     			doSubmitUploadVarResult(dis, type, projectType, showProjectDetails);
     		}
     	});
     	return false;
     };
     
     var doSubmitUploadVarResult = function(dis, type, projectType, showProjectDetails){
		url = "project/" + projectType + "/variation/apply/uploadFileResult.action?projectid=" + $("#projectid").val();
		$("#variId").val(dis.variId);
		$("#varFileId").val(dis.varFileId);
		$("#uploadVarFile_form").ajaxSubmit({ 
			url: url,
	        success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					viewVar(projectType, showProjectDetails);
				}
				else {
					alert(result.errorInfo);
				}
			}	  
		});
	 };
	 var switchVarPublish = function(projectType) {
		 $.ajax({
				url:"project/" + projectType + "/variation/apply/switchPublish.action",
				data:{
					varResultPublish:$("input[name='variation.finalAuditResultPublish']").val(),
					varId:$("input[name='variation.id']").val()
				},
				dataType:"json",
				type:"post",
				success:function(){
					if($(".j_switchVarPublish").val() == "发布") {
						$("#variation_finalAuditResultPublishStatus").text("已发布 ");
						$(".j_switchVarPublish").val("取消发布");
					}
					else {
						$("#variation_finalAuditResultPublishStatus").text("未发布 ");
						$(".j_switchVarPublish").val("发布");
					}
					$("input[name='variation.finalAuditResultPublish']").val(1 - parseInt($("input[name='variation.finalAuditResultPublish']").val()));
				}
			});
	 }
     module.exports = {
    	 viewPerson: function(id){viewPerson(id)},
    	 viewUniversity: function(id){viewUniversity(id)},
    	 viewDepartment: function(id){viewDepartment(id)},
    	 viewInstitute: function(id){viewInstitute(id)},
    	 
    	 viewFee: function(id, projectType){viewFee(id, projectType)},
    	 viewOther: function(id, projectType){viewOther(id, projectType)},
    	 addVar: function(projectType){addVar(projectType)},
     	 modVar: function(varId, projectType){modVar(varId, projectType)}, 
     	 subVar: function(varId, projectType, showProjectDetails){subVar(varId, projectType, showProjectDetails)},
     	 delVar: function(varId, projectType, showProjectDetails){delVar(varId, projectType, showProjectDetails)},
     	 addVarAuditPop: function(varId, projectType, showProjectDetails){addVarAuditPop(varId, projectType, showProjectDetails)},
     	 modVarAuditPop: function(varId, projectType, showProjectDetails){modVarAuditPop(varId, projectType, showProjectDetails)},
     	 viewVarAuditPop: function(varId, auditViewFlag, projectType){viewVarAuditPop(varId, auditViewFlag, projectType)},
     	 subVarAudit: function(varId, projectType, showProjectDetails){subVarAudit(varId, projectType, showProjectDetails)},
     	 backVarAudit: function(varId, projectType, showProjectDetails){backVarAudit(varId, projectType, showProjectDetails)},
     	 addVarResultPop: function(projectType, showProjectDetails){addVarResultPop(projectType, showProjectDetails)},
     	 modifyVarResultPop: function(varId, projectType, showProjectDetails){modifyVarResultPop(varId, projectType, showProjectDetails)},
     	 uploadVarPop: function(varId, projectType, showProjectDetails){uploadVarPop(varId, projectType, showProjectDetails)},
     	 downloadVarTemplate: function(projectType){downloadVarTemplate(projectType)},
     	 varSubmitform: function(url, projectType, showProjectDetails){varSubmitform(url, projectType, showProjectDetails)},
     	 switchVarPublish: function(projectType){switchVarPublish(projectType);}
     };
});
