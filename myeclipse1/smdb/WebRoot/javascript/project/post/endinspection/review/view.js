/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewEndinspectionReview = require('javascript/project/project_share/endinspection/review/view');
	var viewPost = require('javascript/project/post/view');
	var viewEndApp = require('javascript/project/project_share/endinspection/apply/view');
	
	var projectType = "post";
	
	//录入鉴定信息
	var addEndReviewResultPop = function(endId, reviewFlag){
		popProjectOperation({
			title : "录入鉴定信息",
			src : 'project/post/endinspection/review/toAddResult.action?projectid=' + $("#projectid").val() + '&reviewFlag=' + reviewFlag + '&endId=' + endId,
			callBack : function(layer){
				$("#update").val(1);
				viewEndApp.readEnd(projectType, viewPost.showProjectDetails);
				layer.destroy();
			}
		});
		return false;
	};
	
	//修改录入鉴定
	var modifyEndReviewResultPop = function(data, reviewFlag){
		popProjectOperation({
		title : "修改鉴定意见",
		src : 'project/post/endinspection/review/toModifyResult.action?endId=' + data + '&projectid=' + $("#projectid").val() + '&reviewFlag=' + reviewFlag,
		callBack : function(layer){
			viewEndApp.readEnd(projectType, viewPost.showProjectDetails);
			layer.destroy();
			}
		});
		return false;
	};
	
	//提交录入鉴定
	var submitEndReviewResult = function(data, reviewFlag){
		if($("#varPending").val() == 1){
			alert("该项目已通过鉴定，无法修改鉴定！");
			return;
		}
		var url = 'project/post/endinspection/review/submitResult.action';
		if( !confirm('提交后无法修改，是否确认提交？'))
			return;
		$.ajax({
			url: url,
			type: "post",
			data: "endId=" + data + "&projectid=" + $("#projectid").val() + '&reviewFlag=' + reviewFlag,
			dataType: "json",
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					viewEndApp.readEnd(projectType, viewPost.showProjectDetails);
				}else {
					alert(result.errorInfo);
				}
			}	
		});
	};
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_addEndReviewExpert").live("click", function(){
			var data = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewExpert(data, projectType, viewPost.showProjectDetails)
		});
		$(".j_addEndReviewResultPop").live("click",function(){
			var endId = $(this).attr("endId");
			var reviewFlag = $(this).attr("reviewFlag");
			addEndReviewResultPop(endId, reviewFlag);
		});
		
		$(".j_modifyEndReviewResultPop").live("click",function(){
			var data = $(this).attr("endId");
			var reviewFlag = $(this).attr("reviewFlag");
			modifyEndReviewResultPop(data, reviewFlag);
		});
		
		$(".j_submitEndReviewResult").live("click",function(){
			var data = $(this).attr("endId");
			var reviewFlag = $(this).attr("reviewFlag");
			submitEndReviewResult(data, reviewFlag);
		});
		
		$(".j_addEndReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndReviewPop(id, projectType, viewPost.showProjectDetails);
		});
		$(".j_submitEndReview").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.submitEndReview(id, projectType, viewPost.showProjectDetails);
		});
		$(".j_addEndGroupReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewPost.showProjectDetails);
		});
		$(".j_modifyEndGroupReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewPost.showProjectDetails);
		});
		$(".j_viewEndGroupReviewPop").live("click",function(){
			var id = $(this).attr("endId");
			viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType);
		});
		$(".j_addEndReviewAuditPop").live("click",function(){
			var id =$(this).attr("endId");
			viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewPost.showProjectDetails);
		});
		$(".j_viewEndReviewAuditPop").live("click",function(){
			var id =$(this).attr("endId");
			viewEndinspectionReview.viewEndReviewAuditPop(id, projectType);
		});
		$(".j_modifyEndReviewAuditPop").live("click",function(){
			var id =$(this).attr("endId");
			viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewPost.showProjectDetails);
		});
		$(".j_submitEndReviewAuditResult").live("click",function(){
			var id =$(this).attr("endId");
			viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewPost.showProjectDetails);
		});
		$(".j_viewEndReviewPop").live("click",function(){
			var entityId =$(this).attr("endId");
			viewEndinspectionReview.viewEndReviewPop(entityId, projectType);
		});
		$(".j_modifyEndReviewPop").live("click",function(){
			var id =$(this).attr("endId");
			var entityId = $(this).attr("entityId");
			viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewPost.showProjectDetails);
		});
		
		$(".j_submitEndGroupReview").live("click",function(){
			var id =$(this).attr("endId");
			viewEndinspectionReview.submitEndGroupReview(id, projectType, viewPost.showProjectDetails);
		});
		
		
		
		
		
		
//		window.addEndReviewResultPop = function(endId, reviewFlag){addEndReviewResultPop(endId, reviewFlag)};
//		window.modifyEndReviewResultPop = function(data, reviewFlag){modifyEndReviewResultPop(data, reviewFlag)};
//		window.submitEndReviewResult = function(data, reviewFlag){submitEndReviewResult(data, reviewFlag)};
//		window.addEndReviewAuditPop = function(id){viewEndinspectionReview.addEndReviewAuditPop(id, projectType, viewPost.showProjectDetails)};
//		window.modifyEndReviewAuditPop = function(id){viewEndinspectionReview.modifyEndReviewAuditPop(id, projectType, viewPost.showProjectDetails)};
//		window.submitEndReviewAuditResult = function(id){viewEndinspectionReview.submitEndReviewAuditResult(id, projectType, viewPost.showProjectDetails)};
//		window.viewEndReviewAuditPop = function(id){viewEndinspectionReview.viewEndReviewAuditPop(id, projectType)};
//		window.addEndReviewPop = function(id){viewEndinspectionReview.addEndReviewPop(id, projectType, viewPost.showProjectDetails)};
//		window.modifyEndReviewPop = function(id, entityId){viewEndinspectionReview.modifyEndReviewPop(id, entityId, projectType, viewPost.showProjectDetails)};//公共部分
		window.submitEndReview = function(id){viewEndinspectionReview.submitEndReview(id, projectType, viewPost.showProjectDetails)};
//		window.viewEndReviewPop = function(entityId){viewEndinspectionReview.viewEndReviewPop(entityId, projectType)};
		window.addEndGroupReviewPop = function(id){viewEndinspectionReview.addEndGroupReviewPop(id, projectType, viewPost.showProjectDetails)};
		window.modifyEndGroupReviewPop = function(id){viewEndinspectionReview.modifyEndGroupReviewPop(id, projectType, viewPost.showProjectDetails)};
//		window.submitEndGroupReview = function(id){viewEndinspectionReview.submitEndGroupReview(id, projectType, viewPost.showProjectDetails)};
//		window.viewEndGroupReviewPop = function(entityId){viewEndinspectionReview.viewEndGroupReviewPop(entityId, projectType)};
	};
});