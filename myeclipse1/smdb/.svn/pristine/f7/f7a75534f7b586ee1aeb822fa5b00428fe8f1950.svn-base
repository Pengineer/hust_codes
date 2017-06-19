define(function(require, exports, module) {
	var editAppRevResult = require('javascript/project/project_share/application/review/edit_result');
	
	var projectType = "special";
	
	//初始化onclick事件
	exports.init = function() {
		editAppRevResult.init();
		$(".j_addResultSave").live('click',function(){//录入评审添加暂存
			editAppRevResult.submitOrSaveAddAppReviewResult(2, projectType);
		});
		$(".j_addResultSubmit").live('click',function(){//录入评审添加提交
			editAppRevResult.submitOrSaveAddAppReviewResult(3, projectType);
		});
		$(".j_modifyResultSave").live('click',function(){//录入评审修改暂存
			editAppRevResult.submitOrSaveModifyAppReviewResult(2, projectType);
		});
		$(".j_modifyResultSubmit").live('click',function(){//录入评审修改提交
			editAppRevResult.submitOrSaveModifyAppReviewResult(3, projectType);
		});
		
//		window.submitOrSaveAddAppReviewResult = function(data){editAppRevResult.submitOrSaveAddAppReviewResult(data, projectType)};
//		window.submitOrSaveModifyAppReviewResult = function(data){editAppRevResult.submitOrSaveModifyAppReviewResult(data, projectType)};
	};
});