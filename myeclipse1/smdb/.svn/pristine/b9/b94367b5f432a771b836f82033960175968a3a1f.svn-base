/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspectionReviewReview = require('javascript/project/project_share/endinspection/review/view');
	var viewEntrust = require('javascript/project/entrust/view');
	
	var projectType = "entrust";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_addEndReviewExpert").live("click", function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewExpert(data, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_addEndReviewResultPop").live("click", function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_modifyEndReviewResultPop").live("click", function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_submitEndReviewResult").live("click", function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.submitEndReviewResult(data, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_addEndReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewPop(id, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_viewEndReviewPop").live("click", function(){
			var entityId = $(this).attr("endId");
			viewEndinspectionReview.viewEndReviewPop(entityId, projectType);
		});
		$(".j_modifyEndReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			var entityId = $(this).attr("rendId");
			viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_submitEndReview").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReview(id, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_addEndGroupReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_viewEndGroupReviewPop").live("click", function(){
			var entityId = $(this).attr("endId");
			viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType);
		});
		$(".j_modifyEndGroupReviewPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_submitEndGroupReview").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndGroupReview(id, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_addEndReviewAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_viewEndReviewAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.viewEndReviewAuditPop(id, projectType);
		});
		$(".j_modifyEndReviewAuditPop").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewEntrust.showProjectDetails);
		});
		$(".j_submitEndReviewAuditResult").live("click", function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewEntrust.showProjectDetails);
		});
		
		
		
//		window.addEndReviewResultPop = function(data){viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewEntrust.showProjectDetails)};//公共部分
//		window.modifyEndReviewResultPop = function(data){viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewEntrust.showProjectDetails)};
//		window.submitEndReviewResult = function(data){viewEndinspectionReview.submitEndReviewResult(data, projectType, viewEntrust.showProjectDetails)};
//		window.addEndReviewAuditPop = function(id){viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewEntrust.showProjectDetails)};
//		window.modifyEndReviewAuditPop = function(id){viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewEntrust.showProjectDetails)};
//		window.submitEndReviewAuditResult = function(id){viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewEntrust.showProjectDetails)};
//		window.viewEndReviewAuditPop = function(id){viewEndinspectionReview.viewEndReviewAuditPop(id, projectType)};
//		window.addEndReviewPop = function(id){viewEndinspectionReview.addEndReviewPop(id, projectType, viewEntrust.showProjectDetails)};
//		window.modifyEndReviewPop = function(id, entityId){viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewEntrust.showProjectDetails)};//公共部分
//		window.submitEndReview = function(id){viewEndinspectionReview.submitEndReview(id, projectType, viewEntrust.showProjectDetails)};
		window.viewEndReviewPop = function(){viewEndinspectionReview.viewEndReviewPop(entityId, projectType)};
//		window.addEndGroupReviewPop = function(id){viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewEntrust.showProjectDetails)};
//		window.modifyEndGroupReviewPop = function(id){viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewEntrust.showProjectDetails)};
//		window.submitEndGroupReview = function(id){viewEndinspectionReview.submitEndGroupReview(id, projectType, viewEntrust.showProjectDetails)};
//		window.viewEndGroupReviewPop = function(entityId){viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType)};
	};
});