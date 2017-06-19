/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspection = require('javascript/project/project_share/endinspection/apply/view');
	var viewInstp = require('javascript/project/instp/view');
	
	var projectType = "instp";
	
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
		
		$(".j_downloadEndTemplate").live("click",function(){
			viewEndinspection.downloadEndTemplate(projectType);
		});
		
		$(".j_toAddEndApplyPop").live("click",function(){
			viewEndinspection.toAddEndApplyPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_addEndResultPop").live("click",function(){
			viewEndinspection.addEndResultPop(projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_modifyEndResultPop").live("click",function(){
			var modifyFlag = $(this).attr("data");
			modifyEndResultPop(modifyFlag, projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_submitEndResult").live("click",function(){
			viewEndinspection.submitEndResult(projectType, viewInstp.showProjectDetails);
		});
		
		
		$(".j_viewEndAuditPop").live("click",function(){
			var id = $(this).attr("endId");
			var data = $(this).attr("data");
			viewEndinspection.viewEndAuditPop(id, data, projectType);
		});
		
		$(".j_uploadEndPop").live("click",function(){
			var endId = $(this).attr("endId"); 
			viewEndinspection.uploadEndPop(endId, projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_toModifyEndApplyPop").live("click",function(){
			viewEndinspection.toModifyEndApplyPop(projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_submitEndApply").live("click",function(){
			viewEndinspection.submitEndApply(projectType, viewInstp.showProjectDetails);
		});
		$(".j_deleteEndApply").live("click",function(){
			viewEndinspection.deleteEndApply(projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_addEndAuditPop").live("click",function(){
			viewEndinspection.addEndAuditPop(projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_backEndApply").live("click",function(){
			viewEndinspection.backEndApply(projectType, viewInstp.showProjectDetails);
		});
		$(".j_modifyEndAuditPop").live("click",function(){
			viewEndinspection.modifyEndAuditPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitEndAudit").live("click",function(){
			viewEndinspection.submitEndAudit(projectType, viewInstp.showProjectDetails);
		});
		$(".j_switchEndPublish").live('click', function(){//切换发布
			viewEndinspection.switchEndPublish(projectType);
		});
		
		
		
//		window.toAddEndApplyPop = function(){viewEndinspection.toAddEndApplyPop(projectType, viewInstp.showProjectDetails)};
//		window.toModifyEndApplyPop = function(){viewEndinspection.toModifyEndApplyPop(projectType, viewInstp.showProjectDetails)};
//		window.submitEndApply = function(){viewEndinspection.submitEndApply(projectType, viewInstp.showProjectDetails)};
//		window.deleteEndApply = function(){viewEndinspection.deleteEndApply(projectType, viewInstp.showProjectDetails)};
//		window.addEndAuditPop = function(){viewEndinspection.addEndAuditPop(projectType, viewInstp.showProjectDetails)};
//		window.modifyEndAuditPop = function(){viewEndinspection.modifyEndAuditPop(projectType, viewInstp.showProjectDetails)};
//		window.viewEndAuditPop = function(id, data){viewEndinspection.viewEndAuditPop(id, data, projectType)};
//		window.submitEndAudit = function(){viewEndinspection.submitEndAudit(projectType, viewInstp.showProjectDetails)};
//		window.backEndApply = function(){viewEndinspection.backEndApply(projectType, viewInstp.showProjectDetails)};
//		window.addEndResultPop = function(){viewEndinspection.addEndResultPop(projectType, viewInstp.showProjectDetails)};
//		window.modifyEndResultPop = function(modifyFlag){modifyEndResultPop(modifyFlag, projectType, viewInstp.showProjectDetails)};
//		window.submitEndResult = function(){viewEndinspection.submitEndResult(projectType, viewInstp.showProjectDetails)};
//		window.uploadEndPop = function(endId){viewEndinspection.uploadEndPop(endId, projectType, viewInstp.showProjectDetails)};
//		window.downloadEndTemplate = function(){viewEndinspection.downloadEndTemplate(projectType)};
		window.printCertificate = function(elem){printCertificate(elem, projectType, viewInstp.showProjectDetails)};
	};
}); 


	