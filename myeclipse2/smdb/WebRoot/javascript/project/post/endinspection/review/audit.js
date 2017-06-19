define(function(require, exports, module) {
	var editEndRevAudit = require('javascript/project/project_share/endinspection/review/audit');
	
	var projectType = "post";
	
	//初始化onclick事件
	exports.init = function() {
		$(".j_showNumber").live("click", function(){
			var type = $("input[name='reviewAuditResultEnd'][type='radio']:checked").val();
			editEndRevAudit.showNumber(type);
		});
		$(".j_submitOrNotEndReviewAudit").live("click", function(){
			var data = $(this).attr("data");
			editEndRevAudit.submitOrNotEndReviewAudit(data, thisPopLayer, projectType);
		});
		editEndRevAudit.valid();
		editEndRevAudit.initEndReviewAudit();
//		window.submitOrNotEndReviewAudit = function(data, layer){editEndRevAudit.submitOrNotEndReviewAudit(data, layer, projectType)};
//		window.initEndReviewAudit = function(){editEndRevAudit.initEndReviewAudit()};
//		window.showNumber = function(type){editEndRevAudit.showNumber(type)};
	};
});