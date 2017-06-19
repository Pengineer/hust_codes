/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspectionReview = require('javascript/project/project_share/endinspection/review/view');
	var viewSpecial = require('javascript/project/special/view');
	
	var projectType = "special";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_addEndReviewExpert").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.addEndReviewExpert(data, projectType, viewSpecial.showProjectDetails);
		});
		$(".addEndReviewResultPop").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewSpecial.showProjectDetails);
		});
		$(".modifyEndReviewResultPop").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".submitEndReviewResult").live("click", function(){
			var data = $(this).attr("endinspectionId");
			viewEndinspectionReview.submitEndReviewResult(data, projectType, viewSpecial.showProjectDetails);
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
			viewEndinspectionReview.addEndReviewPop(id, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_submitEndReview").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReview(id, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_addEndGroupReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_modifyEndGroupReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndGroupReviewPop(id, entityId, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_submitEndGroupReview").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndGroupReview(id, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_addEndReviewAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_modifyEndReviewAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewSpecial.showProjectDetails);
		});
		
		$(".j_submitEndReviewAuditResult").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewSpecial.showProjectDetails);
		});
		$(".j_modifyEndReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			var entityId = $(this).attr("entityId");
			viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewSpecial.showProjectDetails);
		});
		$(".j_viewEndReviewPop").live("click", function(){
			var entityId = $(this).attr("endId");
			viewEndinspectionReview.viewEndReviewPop(entityId, projectType);
		});
		
//		window.addEndReviewResultPop = function(data){viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewSpecial.showProjectDetails)};//公共部分
//		window.modifyEndReviewResultPop = function(data){viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewSpecial.showProjectDetails)};
//		window.submitEndReviewResult = function(data){viewEndinspectionReview.submitEndReviewResult(data, projectType, viewSpecial.showProjectDetails)};
//		window.addEndReviewAuditPop = function(id){viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewSpecial.showProjectDetails)};
//		window.modifyEndReviewAuditPop = function(id){viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewSpecial.showProjectDetails)};
		window.submitEndReviewAuditResult = function(id){viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewSpecial.showProjectDetails)};
//		window.viewEndReviewAuditPop = function(id){viewEndinspectionReview.viewEndReviewAuditPop(id, projectType)};
//		window.addEndReviewPop = function(id){viewEndinspectionReview.addEndReviewPop(id, projectType, viewSpecial.showProjectDetails)};
//		window.modifyEndReviewPop = function(id, entityId){viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewSpecial.showProjectDetails)};//公共部分
//		window.submitEndReview = function(id){viewEndinspectionReview.submitEndReview(id, projectType, viewSpecial.showProjectDetails)};
//		window.viewEndReviewPop = function(){viewEndinspectionReview.viewEndReviewPop(entityId, projectType)};
//		window.addEndGroupReviewPop = function(id){viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewSpecial.showProjectDetails)};
		
		
		window.modifyEndGroupReviewPop = function(id){viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewSpecial.showProjectDetails)};
		
		
		window.submitEndGroupReview = function(id){viewEndinspectionReview.submitEndGroupReview(id, projectType, viewSpecial.showProjectDetails)};
//		window.viewEndGroupReviewPop = function(entityId){viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType)};
	};
});