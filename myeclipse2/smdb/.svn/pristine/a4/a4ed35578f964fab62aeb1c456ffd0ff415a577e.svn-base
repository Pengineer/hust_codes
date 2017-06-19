define(function(require, exports, module) {
	var editEndRev = require('javascript/project/project_share/endinspection/review/edit');
	
	var projectType = "instp";
	
	//初始化onclick事件
	exports.init = function() {
		
		$(".j_submitOrSaveAddEndReview").live("click", function(){
			var data = $(this).attr("data");
			editEndRev.submitOrSaveAddEndReview(data, thisPopLayer);
		});
		$(".j_submitOrSaveAddEndGroupReview").live("click",function(){
			var data = $(this).attr("data");
			editEndRev.submitOrSaveAddEndGroupReview(data, thisPopLayer);
		});
		$(".j_submitOrSaveModifyEndReview").live("click",function(){
			var data = $(this).attr("data");
			editEndRev.submitOrSaveModifyEndReview(data, thisPopLayer);
		});
		$(".j_submitOrSaveModifyEndGroupReview").live("click",function(){
			var data = $(this).attr("data");
			editEndRev.submitOrSaveModifyEndGroupReview(data, thisPopLayer);
		});
		
		
		
//		window.submitOrSaveAddEndReview = function(data, layer){editEndRev.submitOrSaveAddEndReview(data, layer)};
//		window.submitOrSaveModifyEndReview = function(data, layer){editEndRev.submitOrSaveModifyEndReview(data, layer)};
		window.alertGroupOpinion = function(endId){editEndRev.alertGroupOpinion(endId, projectType)};
//		window.submitOrSaveAddEndGroupReview = function(data, layer){editEndRev.submitOrSaveAddEndGroupReview(data, layer)};
//		window.submitOrSaveModifyEndGroupReview = function(data, layer){editEndRev.submitOrSaveModifyEndGroupReview(data, layer)};
		editEndRev.init();
	};
	
	exports.initGroupOpinion = function(projectType){
		editEndRev.initGroupOpinion(projectType);
	};
});