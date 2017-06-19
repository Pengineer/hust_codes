define(function(require, exports, module) {
	var editEndRevResult = require('javascript/project/project_share/endinspection/review/edit_result');
	var datepick = require("datepick-init");
	
	var projectType = "post";
	
	//编辑结项成果
	var editProductType = function(){
		popEdit({
			type : 1,
			isApplyNoevaluation : 0,
			inData : $("[name='endProductInfo']").val(),
			callBack : function(result){
				$("[name='endProductInfo']").val(result.data.productType);
				$("#endProductInfo").html(result.data.productType);
			}
		});
		return false;
	};

	//显示最终审核意见（反馈给项目负责人）
	var showOpinionFeedbackOrNot = function(data){
		if(data == 1){
			$("#opinion_feedback").show();
		}else{
			$("#opinion_feedback").hide();
		}
	};
	
	//初始化onclick事件
	exports.init = function() {
		datepick.init();
		editEndRevResult.init();
		$(".j_editProductType").live("click", function(){
			editProductType();
		});
		$(".j_showOpinionFeedbackOrNot").live("click", function(){
			var data = $("input[name='reviewResult'][type='radio']:checked").val();
			showOpinionFeedbackOrNot(data);
		});
		$(".j_submitOrSaveAddEndReviewResult").live("click", function(){
			var data = $(this).attr("data");
			editEndRevResult.submitOrSaveAddEndReviewResult(data, projectType);
		});
		$(".j_submitOrSaveModifyEndReviewResult").live("click", function(){
			var data = $(this).attr("data");
			editEndRevResult.submitOrSaveModifyEndReviewResult(data, projectType);
		});
		
//		window.submitOrSaveModifyEndReviewResult = function(data){editEndRevResult.submitOrSaveModifyEndReviewResult(data, projectType)};
		window.submitOrSaveAddEndReviewResult = function(data){editEndRevResult.submitOrSaveAddEndReviewResult(data, projectType)};
//		window.editProductType = function(){editProductType()};
//		window.showOpinionFeedbackOrNot = function(data){showOpinionFeedbackOrNot(data)};
	};
});