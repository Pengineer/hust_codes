define(function(require, exports, module) {
	var editEndRevExpert= require('javascript/project/project_share/application/review/edit_expert');
	
	var projectType = "post";
	
	//初始化onclick事件
	exports.init = function() {
		editEndRevExpert.init();
		
		$(".j_addReviewExpert").live("click",function(){
			var appId = $("#appId").val();
			editEndRevExpert.addReviewExpert(appId, projectType);
		});
	};
});