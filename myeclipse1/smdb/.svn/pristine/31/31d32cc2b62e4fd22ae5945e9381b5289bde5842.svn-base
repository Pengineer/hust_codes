define(function(require, exports, module) {
	var editEndRevResult = require('javascript/project/project_share/endinspection/review/edit_result');
	
	var projectType = "general";
	
	//初始化onclick事件
	exports.init = function() {
		editEndRevResult.init();
		$(".j_submitOrSaveAddEndReviewResult").live("click",function(){
			var data = $(this).attr("data");
			editEndRevResult.submitOrSaveAddEndReviewResult(data, projectType);
		});
		$(".j_submitOrSaveModifyEndReviewResult").live("click",function(){
			var data = $(this).attr("data");
			editEndRevResult.submitOrSaveModifyEndReviewResult(data, projectType);
		});
		
		
		
//		window.submitOrSaveModifyEndReviewResult = function(data){editEndRevResult.submitOrSaveModifyEndReviewResult(data, projectType)};
//		window.submitOrSaveAddEndReviewResult = function(data){editEndRevResult.submitOrSaveAddEndReviewResult(data, projectType)};
	};
});