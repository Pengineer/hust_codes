/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var viewProject = require('javascript/project/project_share/view');
	var project = require('javascript/project/project_share/project');
	var timeValidate = require('javascript/project/project_share/validate');
	
	//查看申请
	var readApply = function(projectType, showProjectDetails) {
		viewProject.viewProject("project/" + projectType + "/application/apply/view.action", showProjectDetails);
	};
	 
     
   //准备修改申请申请
	var modifyAppApply = function(projectType) {
		if($("#appStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineApp", $("#application")).val());
		if(flag){
			var entityId = $("#entityId").val();
			window.location.href = basePath + "project/" + projectType + "/application/apply/toModify.action?entityId="+entityId;
			return false;
		}
	};

    //提交申请申请
	var submitAppApply = function(projectType, showProjectDetails) {
		if($("#appStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadlineApp", $("#application")).val());
		if(flag){
			if( !confirm('提交后无法修改申请信息，是否确认提交？')){
				return;
			}
			$.ajax({
				url: "project/" + projectType + "/application/apply/submit.action",
				type: "post",
				data: "entityId=" + $("#entityId").val(),
				dataType: "json",
				success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						readApply(projectType, showProjectDetails);
					}
					else {
						alert(result.errorInfo);
					}
				}	
			});
		}
	};
	
	//删除申请申请
	var deleteAppApply = function(projectType) {
	 	if($("#appStatus").val()== 0){
	 		alert("该业务已停止，无法进行此操作！");
	 		return false;
	 	}
	 	var flag = timeValidate.timeValidate($("#deadlineApp", $("#application")).val());
	 	if(flag){
	 		if (confirm("确认删除此项目申请申请？")) {
				var info=[{
						"url": "project/" + projectType + "/application/apply/delete.action?entityIds="+ $("#entityId").val(),
						"successUrl": "project/" + projectType + "/application/apply/toList.action?listType=1&update=1"
					}];
				$("#view_" + projectType).attr("action", info[0]["url"]);
				$("#view_" + projectType).ajaxSubmit({
					success:function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							window.location.href = basePath + info[0]["successUrl"];
							return false;
						} else {
							alert(result.errorInfo);
						}
					}
				});
			}
	 		return false;
		}
	};

     //添加申请审核
	var addAppAuditPop = function(projectType, showProjectDetails) {
	 	if($("#appStatus").val()== 0){
	 		alert("该业务已停止，无法进行此操作！");
	 		return false;
	 	}
	 	var flag = timeValidate.timeValidate($("#deadlineApp", $("#application")).val());
		if(flag){
			var accountType = $("#accountType").val();
			var id = document.getElementsByName('entityId')[0].value;
			popProjectOperation({
				title : "添加申请审核意见",
				src : 'project/' + projectType + '/application/applyAudit/toAdd.action?entityId=' + id,
				callBack : function(result){
					doSubmitAppAudit(result, projectType, showProjectDetails);
				}
			});
		}else{
			return false;
		}
	};

     //修改申请审核
	var modifyAppAuditPop = function(projectType, showProjectDetails) {
	 	if($("#appStatus").val()== 0){
	 		alert("该业务已停止，无法进行此操作！");
	 		return false;
	 	}
	 	var flag = timeValidate.timeValidate($("#deadlineApp", $("#application")).val());
	 	if(flag){
			var accountType = $("#accountType").val();
			var id = document.getElementsByName('entityId')[0].value;
			popProjectOperation({
				title : "修改审核意见",
				src : "project/" + projectType + "/application/applyAudit/toModify.action?entityId=" + id,
				callBack : function(dis){
					doSubmitAppAudit(dis, projectType, showProjectDetails);
				}
			});
		}else{
			return false;
		}
	};
	
     //查看申请审核详情
 	var viewAppAuditPop = function(id, data, projectType) {
     	popProjectOperation({
     		title : "查看审核详细意见",
     		src : 'project/' + projectType + '/application/applyAudit/view.action?entityId=' + id + '&vtp=' + data
     	});
    };
     //提交审核
    var submitAppAudit = function(projectType, showProjectDetails) {
     	if($("#appStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineApp", $("#application")).val());
     	if(flag){
     		if( !confirm('确认提交此审核意见？'))
     			return;
     		$("#appForm").ajaxSubmit({ 
    			url: "project/" + projectType + "/application/applyAudit/submit.action",
    			success: function(result){
    				if (result.errorInfo == null || result.errorInfo == "") {
    					$("#update").val(1);
    					readApply(projectType, showProjectDetails);
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
     //退回申请
     var backAppApply = function(projectType, showProjectDetails) {
     	if($("#appStatus").val()== 0){
     		alert("该业务已停止，无法进行此操作！");
     		return false;
     	}
     	var flag = timeValidate.timeValidate($("#deadlineApp", $("#application")).val());
     	if(flag){
     		if( !confirm('确认退回此申请申请？'))
     			return;
     		$("#appForm").ajaxSubmit({ 
    			url: "project/" + projectType + "/application/applyAudit/back.action",
    	        success: function(result){
    				if (result.errorInfo == null || result.errorInfo == "") {
    					$("#update").val(1);
    					readApply(projectType, showProjectDetails);
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

     //ajax提交申请的审核意见
     var doSubmitAppAudit = function(dis, projectType, showProjectDetails) {
		$("[name='appAuditOpinion']").val(dis.appAuditOpinion);
		$("[name='appAuditStatus']").val(dis.appAuditStatus);
		$("[name='appAuditResult']").val(dis.appAuditResult);
		$('#appAuditOpinionFeedback').val(dis.appAuditOpinionFeedback);
		var type = dis.type;
		var url = "";
		if(type == 1){
			url = "project/" + projectType + "/application/applyAudit/add.action?entityId=" + $("#entityId").val();
		}else if(type == 2){
			url = "project/" + projectType + "/application/applyAudit/modify.action?entityId=" + $("#entityId").val();
		}
		$("#appForm").ajaxSubmit({ 
			url: url,
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#update").val(1);
					readApply(projectType, showProjectDetails);
				}
				else {
					alert(result.errorInfo);
				}
			}
		});
     };
     
     //准备上传申请申请书
     var uploadAppPop = function(appId, projectType, showProjectDetails){
     	popProjectOperation({
     		title : "上传电子文档",
     		src : 'project/' + projectType + '/application/apply/toUploadFileResult.action?entityId=' + $("#entityId").val(),
     		inData : {"appId" : appId},
     		callBack : function(dis, type){
     			doSubmitUploadAppResult(dis, type, projectType, showProjectDetails);
     		}
     	});
     	return false;
     };

     var doSubmitUploadAppResult = function(dis, type, projectType, showProjectDetails){
     	url = "project/" + projectType + "/application/apply/uploadFileResult.action?projectid=" + $("#projectid").val();
     	$("#appFileId").val(dis.appFileId);
     	$("#uploadAppFile_form").ajaxSubmit({ 
     		url: url,
             success: function(result){
     			if (result.errorInfo == null || result.errorInfo == "") {
     				$("#update").val(1);
     				readApply(projectType, showProjectDetails);
     			}
     			else {
     				alert(result.errorInfo);
     			}
     		}	  
     	});
    };
    var switchAppPublish = function(projectType) { // 切换发布
    	$.ajax({
			url:"project/" + projectType + "/application/apply/switchPublish.action",
			data:{
				appResultPublish:$("input[name='application.finalAuditResultPublish']").val(),
				entityId:$("input[name='application.id']").val()
			},
			dataType:"json",
			type:"post",
			success:function(){
				if($(".j_switchAppPublish").val() == "发布") {
					$("#application_finalAuditResultPublishStatus").text("已发布");
					$(".j_switchAppPublish").val("取消发布");
				}
				else {
					$("#application_finalAuditResultPublishStatus").text("未发布");
					$(".j_switchAppPublish").val("发布");
				}
				$("input[name='application.finalAuditResultPublish']").val(1 - parseInt($("input[name='application.finalAuditResultPublish']").val()));
			}
		});
    };
    
	module.exports = {
		modifyAppApply: function(projectType){modifyAppApply(projectType)}, 
		submitAppApply: function(projectType, showProjectDetails){submitAppApply(projectType, showProjectDetails)},
		deleteAppApply: function(projectType){deleteAppApply(projectType)},
		addAppAuditPop: function(projectType, showProjectDetails){addAppAuditPop(projectType, showProjectDetails)},
		modifyAppAuditPop: function(projectType, showProjectDetails){modifyAppAuditPop(projectType, showProjectDetails)},
		viewAppAuditPop: function(id, data, projectType){viewAppAuditPop(id, data, projectType)},
		submitAppAudit: function(projectType, showProjectDetails){submitAppAudit(projectType, showProjectDetails)},
		backAppApply: function(projectType, showProjectDetails){backAppApply(projectType, showProjectDetails)},
		uploadAppPop: function(appId, projectType, showProjectDetails){uploadAppPop(appId, projectType, showProjectDetails)},
		readApply: function(projectType, showProjectDetails){readApply(projectType, showProjectDetails)},
		switchAppPublish : function(projectType){switchAppPublish(projectType);}
	};
});
