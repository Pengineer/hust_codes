/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspection = require('javascript/project/project_share/endinspection/apply/view');
	var viewEntrust = require('javascript/project/entrust/view');
	
	var projectType = "entrust";
	
	//准备修改结项结果信息
	var modifyEndResultPop = function(modifyFlag, projectType, showProjectDetails){
		popProjectOperation({
			title : "修改结项结果信息",
			src : 'project/' + projectType + '/endinspection/apply/toModifyResult.action?projectid=' + $("#projectid").val() + '&modifyFlag=' + modifyFlag + '&varPending=' + $("#varPending").val(),
			callBack : function(dis, type){
				viewEndinspection.doSubmitEndResult(dis, projectType, showProjectDetails);
			}
		});
		return false;
	};
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_downloadEndTemplate").live("click", function(){
			viewEndinspection.downloadEndTemplate(projectType);
		});
		$(".j_toAddEndApplyPop").live("click", function(){
			viewEndinspection.toAddEndApplyPop(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_addEndResultPop").live("click", function(){
			viewEndinspection.addEndResultPop(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_modifyEndResultPop").live("click", function(){
			var modifyFlag = $(this).attr("modifyFlags");
			modifyEndResultPop(modifyFlag, projectType, viewEntrust.showProjectDetails);
		});
		
		$(".j_submitEndResult").live("click", function(){
			viewEndinspection.submitEndResult(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_uploadEndPop").live("click", function(){
			var endId = $(this).attr("endId");
			viewEndinspection.uploadEndPop(endId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_toModifyEndApplyPop").live("click", function(){
			viewEndinspection.toModifyEndApplyPop(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_submitEndApply").live("click", function(){
			viewEndinspection.submitEndApply(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_deleteEndApply").live("click", function(){
			viewEndinspection.deleteEndApply(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_addEndAuditPop").live("click", function(){
			viewEndinspection.addEndAuditPop(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_backEndApply").live("click", function(){
			viewEndinspection.backEndApply(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_modifyEndAuditPop").live("click", function(){
			viewEndinspection.modifyEndAuditPop(projectType, viewEntrust.showProjectDetails);
		});
		$(".j_viewEndAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			var data = $(this).attr("data");
			viewEndinspection.viewEndAuditPop(id, data, projectType);
		});
		$(".j_submitEndAudit").live("click", function(){
			viewEndinspection.submitEndAudit(projectType, viewEntrust.showProjectDetails);
		});
		
		
		
//		window.toAddEndApplyPop = function(){viewEndinspection.toAddEndApplyPop(projectType, viewEntrust.showProjectDetails)};
//		window.toModifyEndApplyPop = function(){viewEndinspection.toModifyEndApplyPop(projectType, viewEntrust.showProjectDetails)};
//		window.submitEndApply = function(){viewEndinspection.submitEndApply(projectType, viewEntrust.showProjectDetails)};
//		window.deleteEndApply = function(){viewEndinspection.deleteEndApply(projectType, viewEntrust.showProjectDetails)};
//		window.addEndAuditPop = function(){viewEndinspection.addEndAuditPop(projectType, viewEntrust.showProjectDetails)};
//		window.modifyEndAuditPop = function(){viewEndinspection.modifyEndAuditPop(projectType, viewEntrust.showProjectDetails)};
//		window.viewEndAuditPop = function(id, data){viewEndinspection.viewEndAuditPop(id, data, projectType)};
//		window.submitEndAudit = function(){viewEndinspection.submitEndAudit(projectType, viewEntrust.showProjectDetails)};
//		window.backEndApply = function(){viewEndinspection.backEndApply(projectType, viewEntrust.showProjectDetails)};
//		window.addEndResultPop = function(){viewEndinspection.addEndResultPop(projectType, viewEntrust.showProjectDetails)};
//		window.modifyEndResultPop = function(modifyFlag){modifyEndResultPop(modifyFlag, projectType, viewEntrust.showProjectDetails)};
//		window.submitEndResult = function(){viewEndinspection.submitEndResult(projectType, viewEntrust.showProjectDetails)};
//		window.uploadEndPop = function(endId){viewEndinspection.uploadEndPop(endId, projectType, viewEntrust.showProjectDetails)};
//		window.downloadEndTemplate = function(){viewEndinspection.downloadEndTemplate(projectType)};
		window.printCertificate = function(elem){printCertificate(elem, projectType, viewEntrust.showProjectDetails)};
	};
}); 


	