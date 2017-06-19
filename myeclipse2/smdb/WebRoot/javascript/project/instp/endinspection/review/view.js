/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspectionReview = require('javascript/project/project_share/endinspection/review/view');
	var viewInstp = require('javascript/project/instp/view');
	
	var projectType = "instp";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_addEndReviewExpert").live("click", function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewExpert(data, projectType, viewInstp.showProjectDetails)
		});
		$(".j_addEndReviewResultPop").live("click",function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_modifyEndReviewResultPop").live("click",function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitEndReviewResult").live("click",function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.submitEndReviewResult(data, projectType, viewInstp.showProjectDetails);
		});
		$(".j_viewEndGroupReviewPop").live("click",function(){
			var entityId = $(this).attr("endId");
			viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType);
		});
		$(".j_viewEndReviewAuditPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.viewEndReviewAuditPop(id, projectType);
		});
		$(".j_addEndReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewPop(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitEndReview").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReview(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_addEndGroupReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_modifyEndGroupReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitEndGroupReview").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndGroupReview(id, projectType, viewInstp.showProjectDetails);
		});
		
		$(".j_addEndReviewAuditPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_modifyEndReviewAuditPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitEndReviewAuditResult").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_viewEndReviewPop").live("click",function(){
			var entityId = $(this).attr("entityId");
			viewEndinspectionReview.viewEndReviewPop(entityId, projectType);
		});
		
		$(".j_modifyEndReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			var entityId = $(this).attr("entityId");
			viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewInstp.showProjectDetails);
		});
		
		
		
//		window.addEndReviewResultPop = function(data){viewEndinspectionReview.addEndReviewResultPop(data, projectType, viewInstp.showProjectDetails)};//公共部分
//		window.modifyEndReviewResultPop = function(data){viewEndinspectionReview.modifyEndReviewResultPop(data, projectType, viewInstp.showProjectDetails)};
//		window.submitEndReviewResult = function(data){viewEndinspectionReview.submitEndReviewResult(data, projectType, viewInstp.showProjectDetails)};
//		window.addEndReviewAuditPop = function(id){viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewInstp.showProjectDetails)};
//		window.modifyEndReviewAuditPop = function(id){viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewInstp.showProjectDetails)};
//		window.submitEndReviewAuditResult = function(id){viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewInstp.showProjectDetails)};
//		window.viewEndReviewAuditPop = function(id){viewEndinspectionReview.viewEndReviewAuditPop(id, projectType)};
//		window.addEndReviewPop = function(id){viewEndinspectionReview.addEndReviewPop(id, projectType, viewInstp.showProjectDetails)};
//		window.modifyEndReviewPop = function(id, entityId){viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewInstp.showProjectDetails)};//公共部分
//		window.submitEndReview = function(id){viewEndinspectionReview.submitEndReview(id, projectType, viewInstp.showProjectDetails)};
//		window.viewEndReviewPop = function(){viewEndinspectionReview.viewEndReviewPop(entityId, projectType)};
//		window.addEndGroupReviewPop = function(id){viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewInstp.showProjectDetails)};
//		window.modifyEndGroupReviewPop = function(id){viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewInstp.showProjectDetails)};
//		window.submitEndGroupReview = function(id){viewEndinspectionReview.submitEndGroupReview(id, projectType, viewInstp.showProjectDetails)};
//		window.viewEndGroupReviewPop = function(entityId){viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType)};
		window.addEndGroupReviewPop = function(id){viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewInstp.showProjectDetails)};
		window.modifyEndGroupReviewPop = function(id){viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewInstp.showProjectDetails)};
		window.submitEndGroupReview = function(id){viewEndinspectionReview.submitEndGroupReview(id, projectType, viewInstp.showProjectDetails)};
		window.viewEndGroupReviewPop = function(entityId){viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType)};
	};
});