/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspection = require('javascript/project/project_share/endinspection/apply/view');
	var viewKey = require('javascript/project/key/view');
	
	var projectType = "key";
	
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
		$(".j_downloadEndTemplate").live("click", function(){
			viewEndinspection.downloadEndTemplate(projectType);	
		});
		$(".j_toAddEndApplyPop").live("click", function(){
			viewEndinspection.toAddEndApplyPop(projectType, viewKey.showProjectDetails);	
		});
		$(".j_addEndResultPop").live("click", function(){
			viewEndinspection.addEndResultPop(projectType, viewKey.showProjectDetails);	
		});
		$(".j_modifyEndResultPop").live("click", function(){
			var modifyFlag = $(this).attr("data");
			modifyEndResultPop(modifyFlag, projectType, viewKey.showProjectDetails);	
		});
		$(".j_submitEndResult").live("click", function(){
			viewEndinspection.submitEndResult(projectType, viewKey.showProjectDetails);	
		});
		$(".j_uploadEndPop").live("click", function(){
			var endId = $(this).attr("endId");
			viewEndinspection.uploadEndPop(endId, projectType, viewKey.showProjectDetails);	
		});
		$(".j_toModifyEndApplyPop").live("click", function(){
			viewEndinspection.toModifyEndApplyPop(projectType, viewKey.showProjectDetails);	
		});
		$(".j_submitEndApply").live("click", function(){
			viewEndinspection.submitEndApply(projectType, viewKey.showProjectDetails);	
		});
		$(".j_deleteEndApply").live("click", function(){
			viewEndinspection.deleteEndApply(projectType, viewKey.showProjectDetails);	
		});
		$(".j_addEndAuditPop").live("click", function(){
			viewEndinspection.addEndAuditPop(projectType, viewKey.showProjectDetails);	
		});
		$(".j_backEndApply").live("click", function(){
			viewEndinspection.backEndApply(projectType, viewKey.showProjectDetails);	
		});
		$(".j_viewEndAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			var data = $(this).attr("data");
			viewEndinspection.viewEndAuditPop(id, data, projectType);	
		});
		$(".j_modifyEndAuditPop").live("click", function(){
			viewEndinspection.modifyEndAuditPop(projectType, viewKey.showProjectDetails);	
		});
		$(".j_submitEndAudit").live("click", function(){
			viewEndinspection.submitEndAudit(projectType, viewKey.showProjectDetails);	
		});
		
		
		
		
		
		
//		window.toAddEndApplyPop = function(){viewEndinspection.toAddEndApplyPop(projectType, viewKey.showProjectDetails)};
//		window.toModifyEndApplyPop = function(){viewEndinspection.toModifyEndApplyPop(projectType, viewKey.showProjectDetails)};
//		window.submitEndApply = function(){viewEndinspection.submitEndApply(projectType, viewKey.showProjectDetails)};
//		window.deleteEndApply = function(){viewEndinspection.deleteEndApply(projectType, viewKey.showProjectDetails)};
//		window.addEndAuditPop = function(){viewEndinspection.addEndAuditPop(projectType, viewKey.showProjectDetails)};
//		window.modifyEndAuditPop = function(){viewEndinspection.modifyEndAuditPop(projectType, viewKey.showProjectDetails)};
//		window.viewEndAuditPop = function(id, data){viewEndinspection.viewEndAuditPop(id, data, projectType)};
//		window.submitEndAudit = function(){viewEndinspection.submitEndAudit(projectType, viewKey.showProjectDetails)};
//		window.backEndApply = function(){viewEndinspection.backEndApply(projectType, viewKey.showProjectDetails)};
//		window.addEndResultPop = function(){viewEndinspection.addEndResultPop(projectType, viewKey.showProjectDetails)};
//		window.modifyEndResultPop = function(modifyFlag){modifyEndResultPop(modifyFlag, projectType, viewKey.showProjectDetails)};
//		window.submitEndResult = function(){viewEndinspection.submitEndResult(projectType, viewKey.showProjectDetails)};
//		window.uploadEndPop = function(endId){viewEndinspection.uploadEndPop(endId, projectType, viewKey.showProjectDetails)};
//		window.downloadEndTemplate = function(){viewEndinspection.downloadEndTemplate(projectType)};
		window.printCertificate = function(elem){printCertificate(elem, projectType, viewKey.showProjectDetails)};
	};
}); 


	