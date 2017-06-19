/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspection = require('javascript/project/project_share/endinspection/apply/view');
	var viewGeneral = require('javascript/project/general/view');
	
	var projectType = "general";
	
	//准备修改结项结果信息
	var modifyEndResultPop = function(modifyFlag, projectType, showProjectDetails){
		popProjectOperation({
			title : "修改结项结果信息",
			src : 'project/' + projectType + '/endinspection/apply/toModifyResult.action?projectid=' + $("#projectid").val() + '&modifyFlag=' + modifyFlag + '&midPending=' + $("#midPending").val() + '&varPending=' + $("#varPending").val(),
			callBack : function(dis, type){
				viewEndinspection.doSubmitEndResult(dis, projectType, showProjectDetails);
			}
		});
		return false;
	};
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".downloadEndTemplate").live("click",function(){
			viewEndinspection.downloadEndTemplate(projectType);
		});
		
		$(".j_toAddEndApplyPop").live("click",function(){
			viewEndinspection.toAddEndApplyPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_addEndResultPop").live("click",function(){
			viewEndinspection.addEndResultPop(projectType, viewGeneral.showProjectDetails);
		});
		
		$(".modifyEndResultPop").live("click",function(){
			var modifyFlag = $(this).attr("modifyFlag");
			modifyEndResultPop(modifyFlag, projectType, viewGeneral.showProjectDetails);
		});
		$(".submitEndResult").live("click", function(){
			viewEndinspection.submitEndResult(projectType, viewGeneral.showProjectDetails);
		});
		
		$(".uploadEndPop").live("click", function(){
			var endId =  $(this).attr("endinspectionId");
			viewEndinspection.uploadEndPop(endId, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_viewEndAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			var data = $(this).attr("alt");
			viewEndinspection.viewEndAuditPop(id, data, projectType);
		});
		
		$(".j_toModifyEndApplyPop").live("click",function(){
			viewEndinspection.toModifyEndApplyPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitEndApply").live("click",function(){
			viewEndinspection.submitEndApply(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_deleteEndApply").live("click",function(){
			viewEndinspection.deleteEndApply(projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_addEndAuditPop").live("click", function(){
			viewEndinspection.addEndAuditPop(projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_backEndApply").live("click",function(){
			viewEndinspection.backEndApply(projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_modifyEndAuditPop").live("click",function(){
			viewEndinspection.modifyEndAuditPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitEndAudit").live("click",function(){
			viewEndinspection.submitEndAudit(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_switchEndPublish").live('click', function(){//切换发布
			viewEndinspection.switchEndPublish(projectType);
		});
		
//		window.toAddEndApplyPop = function(){viewEndinspection.toAddEndApplyPop(projectType, viewGeneral.showProjectDetails)};
//		window.toModifyEndApplyPop = function(){viewEndinspection.toModifyEndApplyPop(projectType, viewGeneral.showProjectDetails)};
//		window.submitEndApply = function(){viewEndinspection.submitEndApply(projectType, viewGeneral.showProjectDetails)};
//		window.deleteEndApply = function(){viewEndinspection.deleteEndApply(projectType, viewGeneral.showProjectDetails)};
//		window.addEndAuditPop = function(){viewEndinspection.addEndAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.modifyEndAuditPop = function(){viewEndinspection.modifyEndAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.viewEndAuditPop = function(id, data){viewEndinspection.viewEndAuditPop(id, data, projectType)};
//		window.submitEndAudit = function(){viewEndinspection.submitEndAudit(projectType, viewGeneral.showProjectDetails)};
//		window.backEndApply = function(){viewEndinspection.backEndApply(projectType, viewGeneral.showProjectDetails)};
//		window.addEndResultPop = function(){viewEndinspection.addEndResultPop(projectType, viewGeneral.showProjectDetails)};
//		window.modifyEndResultPop = function(modifyFlag){modifyEndResultPop(modifyFlag, projectType, viewGeneral.showProjectDetails)};
//		window.submitEndResult = function(){viewEndinspection.submitEndResult(projectType, viewGeneral.showProjectDetails)};
//		window.uploadEndPop = function(endId){viewEndinspection.uploadEndPop(endId, projectType, viewGeneral.showProjectDetails)};
//		window.downloadEndTemplate = function(){viewEndinspection.downloadEndTemplate(projectType)};
		window.printCertificate = function(elem){viewEndinspection.printCertificate(elem, projectType, viewGeneral.showProjectDetails)};
	};
}); 


	