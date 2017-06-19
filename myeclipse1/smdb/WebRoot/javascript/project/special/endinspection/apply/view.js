/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspection = require('javascript/project/project_share/endinspection/apply/view');
	var viewSpecial = require('javascript/project/special/view');
	
	var projectType = "special";
	
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
			viewEndinspection.toAddEndApplyPop(projectType, viewSpecial.showProjectDetails);
		});
		$(".j_addEndResultPop").live("click",function(){
			viewEndinspection.addEndResultPop(projectType, viewSpecial.showProjectDetails);
		});
		
		$(".modifyEndResultPop").live("click",function(){
			var modifyFlag = $(this).attr("modifyFlag");
			modifyEndResultPop(modifyFlag, projectType, viewSpecial.showProjectDetails);
		});
		$(".submitEndResult").live("click", function(){
			viewEndinspection.submitEndResult(projectType, viewSpecial.showProjectDetails);
		});
		
		$(".uploadEndPop").live("click", function(){
			var endId =  $(this).attr("endinspectionId");
			viewEndinspection.uploadEndPop(endId, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_viewEndAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			var data = $(this).attr("alt");
			viewEndinspection.viewEndAuditPop(id, data, projectType);
		});
		
		$(".j_toModifyEndApplyPop").live("click",function(){
			viewEndinspection.toModifyEndApplyPop(projectType, viewSpecial.showProjectDetails);
		});
		$(".j_submitEndApply").live("click",function(){
			viewEndinspection.submitEndApply(projectType, viewSpecial.showProjectDetails);
		});
		$(".j_deleteEndApply").live("click",function(){
			viewEndinspection.deleteEndApply(projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_addEndAuditPop").live("click", function(){
			viewEndinspection.addEndAuditPop(projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_backEndApply").live("click",function(){
			viewEndinspection.backEndApply(projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_modifyEndAuditPop").live("click",function(){
			viewEndinspection.modifyEndAuditPop(projectType, viewSpecial.showProjectDetails);
		});
		$(".j_submitEndAudit").live("click",function(){
			viewEndinspection.submitEndAudit(projectType, viewSpecial.showProjectDetails);
		});

		
//		window.toAddEndApplyPop = function(){viewEndinspection.toAddEndApplyPop(projectType, viewSpecial.showProjectDetails)};
//		window.toModifyEndApplyPop = function(){viewEndinspection.toModifyEndApplyPop(projectType, viewSpecial.showProjectDetails)};
//		window.submitEndApply = function(){viewEndinspection.submitEndApply(projectType, viewSpecial.showProjectDetails)};
//		window.deleteEndApply = function(){viewEndinspection.deleteEndApply(projectType, viewSpecial.showProjectDetails)};
//		window.addEndAuditPop = function(){viewEndinspection.addEndAuditPop(projectType, viewSpecial.showProjectDetails)};
//		window.modifyEndAuditPop = function(){viewEndinspection.modifyEndAuditPop(projectType, viewSpecial.showProjectDetails)};
//		window.viewEndAuditPop = function(id, data){viewEndinspection.viewEndAuditPop(id, data, projectType)};
//		window.submitEndAudit = function(){viewEndinspection.submitEndAudit(projectType, viewSpecial.showProjectDetails)};
//		window.backEndApply = function(){viewEndinspection.backEndApply(projectType, viewSpecial.showProjectDetails)};
//		window.addEndResultPop = function(){viewEndinspection.addEndResultPop(projectType, viewSpecial.showProjectDetails)};
//		window.modifyEndResultPop = function(modifyFlag){modifyEndResultPop(modifyFlag, projectType, viewSpecial.showProjectDetails)};
//		window.submitEndResult = function(){viewEndinspection.submitEndResult(projectType, viewSpecial.showProjectDetails)};
//		window.uploadEndPop = function(endId){viewEndinspection.uploadEndPop(endId, projectType, viewSpecial.showProjectDetails)};
//		window.downloadEndTemplate = function(){viewEndinspection.downloadEndTemplate(projectType)};
		window.printCertificate = function(elem){viewEndinspection.printCertificate(elem, projectType, viewSpecial.showProjectDetails)};
	};
}); 


	