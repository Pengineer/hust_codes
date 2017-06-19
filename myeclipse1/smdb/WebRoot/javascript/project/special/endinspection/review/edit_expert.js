define(function(require, exports, module) {
	var editEndRevExpert= require('javascript/project/project_share/endinspection/review/edit_expert');
	
	var projectType = "special";
	
	//初始化onclick事件
	exports.init = function() {
		editEndRevExpert.init();
		
		$(".j_addReviewExpert").live("click",function(){
			var endId = $("#endId").val();
			editEndRevExpert.addReviewExpert(endId, projectType);
		});
	};
});