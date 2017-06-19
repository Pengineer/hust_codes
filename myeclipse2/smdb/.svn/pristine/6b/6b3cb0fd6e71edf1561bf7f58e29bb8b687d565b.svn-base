/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspectionReview = require('javascript/project/project_share/endinspection/review/view');
	var viewGeneral = require('javascript/project/general/view');
	
	var projectType = "general";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_addEndReviewExpert").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.addEndReviewExpert(data, projectType, viewGeneral.showProjectDetails);
		});
		$(".addEndReviewResultPop").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewGeneral.showProjectDetails);
		});
		$(".modifyEndReviewResultPop").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".submitEndReviewResult").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.submitEndReviewResult(data, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_viewEndGroupReviewPop").live("click", function(){
			var entityId = $(this).attr("endId");
			viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType);
		});
		$(".j_viewEndReviewAuditPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.viewEndReviewAuditPop(id, projectType);
		});
		
		$(".j_addEndReviewPop").live("click", function(){
			 var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewPop(id, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_submitEndReview").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReview(id, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_addEndGroupReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_modifyEndGroupReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndGroupReviewPop(id, entityId, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_submitEndGroupReview").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndGroupReview(id, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_addEndReviewAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_modifyEndReviewAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewGeneral.showProjectDetails);
		});
		
		$(".j_submitEndReviewAuditResult").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyEndReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			var entityId = $(this).attr("entityId");
			viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_viewEndReviewPop").live("click", function(){
			var entityId = $(this).attr("endId");
			viewEndinspectionReview.viewEndReviewPop(entityId, projectType);
		});
		
//		window.addEndReviewResultPop = function(data){viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.modifyEndReviewResultPop = function(data){viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewGeneral.showProjectDetails)};
//		window.submitEndReviewResult = function(data){viewEndinspectionReview.submitEndReviewResult(data, projectType, viewGeneral.showProjectDetails)};
//		window.addEndReviewAuditPop = function(id){viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewGeneral.showProjectDetails)};
//		window.modifyEndReviewAuditPop = function(id){viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewGeneral.showProjectDetails)};
		window.submitEndReviewAuditResult = function(id){viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewGeneral.showProjectDetails)};
//		window.viewEndReviewAuditPop = function(id){viewEndinspectionReview.viewEndReviewAuditPop(id, projectType)};
//		window.addEndReviewPop = function(id){viewEndinspectionReview.addEndReviewPop(id, projectType, viewGeneral.showProjectDetails)};
//		window.modifyEndReviewPop = function(id, entityId){viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.submitEndReview = function(id){viewEndinspectionReview.submitEndReview(id, projectType, viewGeneral.showProjectDetails)};
//		window.viewEndReviewPop = function(){viewEndinspectionReview.viewEndReviewPop(entityId, projectType)};
//		window.addEndGroupReviewPop = function(id){viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewGeneral.showProjectDetails)};
		
		
		window.modifyEndGroupReviewPop = function(id){viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewGeneral.showProjectDetails)};
		
		
		window.submitEndGroupReview = function(id){viewEndinspectionReview.submitEndGroupReview(id, projectType, viewGeneral.showProjectDetails)};
//		window.viewEndGroupReviewPop = function(entityId){viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType)};
	};
});