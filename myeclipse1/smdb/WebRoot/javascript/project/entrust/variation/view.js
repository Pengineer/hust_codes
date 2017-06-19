/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewVariation = require('javascript/project/project_share/variation/view');
	var viewEntrust = require('javascript/project/entrust/view');
	
	var projectType = "entrust";
	
	//提交录入变更
    var submitVarResultPop = function(varId, projectType, showProjectDetails){
    	if (confirm('提交后无法修改，是否确认提交？')) {
    		var projectid = document.getElementById('projectid').value;
    		varSubmitform('project/' + projectType + '/variation/apply/submitResult.action?varId='+varId+"&projectid=" + projectid);
    	}
    }
    
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_addVar").live('click',function(){
			viewVariation.addVar(projectType);
		});
		$(".j_addVarResultPop").live('click',function(){
			viewVariation.addVarResultPop(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_modifyVarResultPop").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.modifyVarResultPop(varId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_submitVarResultPop").live('click',function(){
			var varId = $(this).attr("itemIds");
			submitVarResultPop(varId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_viewUniversity").live('click',function(){
			var id = $(this).attr("univId");
			viewVariation.viewUniversity(id);
		});
		$(".j_viewDepartment").live('click',function(){
			var id = $(this).attr("depId");
			viewVariation.viewDepartment(id);
		});
		$(".j_viewInstitute").live('click',function(){
			var id = $(this).attr("instId");
			viewVariation.viewInstitute(id);
		});
		$(".j_viewOther").live('click',function(){
			var id = $(this).attr("itemIds");
			viewVariation.viewOther(id, projectType);
		});
		$(".j_viewFee").live('click',function(){
			var id = $(this).attr("feeId");
			viewVariation.viewFee(id, projectType);
		});
		$(".j_modVar").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.modVar(varId, projectType);
		});
		$(".j_subVar").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.subVar(varId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_delVar").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.delVar(varId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_addVarAuditPop").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.addVarAuditPop(varId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_backVarAudit").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.backVarAudit(varId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_viewVarAuditPop").live('click',function(){
			var varId = $(this).attr("itemIds");
			var auditViewFlag = $(this).attr("auditViewFlag");
			viewVariation.viewVarAuditPop(varId, auditViewFlag, projectType);
		});
		$(".j_modVarAuditPop").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.modVarAuditPop(varId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_subVarAudit").live('click',function(){
			var varId = $(this).attr("itemIds");
			viewVariation.subVarAudit(varId, projectType, viewEntrust.showProjectDetails);
		});
		
		window.viewPerson = function(id){viewVariation.viewPerson(id)};//公共部分
//		window.viewUniversity = function(id){viewVariation.viewUniversity(id)};
//		window.viewDepartment = function(id){viewVariation.viewDepartment(id)};
//		window.viewInstitute = function(id){viewVariation.viewInstitute(id)};
//		window.viewOther = function(id){viewVariation.viewOther(id, projectType)};
//		window.addVar = function(){viewVariation.addVar(projectType)};
//		window.modVar = function(varId){viewVariation.modVar(varId, projectType)};
//		window.subVar = function(varId){viewVariation.subVar(varId, projectType, viewEntrust.showProjectDetails)};
//		window.delVar = function(varId){viewVariation.delVar(varId, projectType, viewEntrust.showProjectDetails)};//公共部分
//		window.addVarAuditPop = function(varId){viewVariation.addVarAuditPop(varId, projectType, viewEntrust.showProjectDetails)};
//		window.modVarAuditPop = function(varId){viewVariation.modVarAuditPop(varId, projectType, viewEntrust.showProjectDetails)};
//		window.viewVarAuditPop = function(varId, auditViewFlag){viewVariation.viewVarAuditPop(varId, auditViewFlag, projectType)};
//		window.subVarAudit = function(varId){viewVariation.subVarAudit(varId, projectType, viewEntrust.showProjectDetails)};
//		window.backVarAudit = function(varId){viewVariation.backVarAudit(varId, projectType, viewEntrust.showProjectDetails)};
//		window.addVarResultPop = function(){viewVariation.addVarResultPop(projectType, viewEntrust.showProjectDetails)};
//		window.modifyVarResultPop = function(varId){viewVariation.modifyVarResultPop(varId, projectType, viewEntrust.showProjectDetails)};
//		window.submitVarResultPop = function(varId){submitVarResultPop(varId, projectType, viewEntrust.showProjectDetails)};
		window.uploadVarPop = function(varId){viewVariation.uploadVarPop(varId, projectType, viewEntrust.showProjectDetails)};
		window.downloadVarTemplate = function(){viewVariation.downloadVarTemplate(projectType)};
	};
});