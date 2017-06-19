define(function(require, exports, module) {
	var view = require('javascript/view');
	var viewMoesocial = require('javascript/award/moesocial/view');
	
	// 显示查看内容
	var showDetails = function(result) {
		if (result.errorInfo == null || result.errorInfo == "") {
			image("image");
			result.awardApplication.session = '第' + Num2Chinese(parseInt(result.awardApplication.session)) + '届'
			if (result.award.session != 0) {
				result.award.session = '第' + Num2Chinese(parseInt(result.award.session)) + '届';
			}else{
				result.award.session = null;
			}
			$("#entityId").attr("value", result.awardApplication.id);
			$("#view_choose_bar").html(TrimPath.processDOMTemplate("view_choose_bar_template", result));
			$("#view_basic").html(TrimPath.processDOMTemplate("view_basic_template", result));
			$("#view_application").html(TrimPath.processDOMTemplate("view_application_template", result));
			$("#view_awarded").html(TrimPath.processDOMTemplate("view_awarded_template", result));
			$("#view_review").html(TrimPath.processDOMTemplate("view_review_template", result));
			initImage("image",$(".link_bar").eq(0).attr("id"));
			if(result.awardApplication.applicantSubmitStatus != 3 && (result.awardApplication.createMode != 1 && result.awardApplication.createMode != 2))
				$("#submit").hide();
			else{
				$("#submit").show();
			} 
			if (result.isReviewer > 0 || result.accountType == "ADMINISTRATOR" || result.accountType == "MINISTRY") {//是评审人或部级管理人员或系统管理员
				$("#review_li").show();
			}else {
				$("#review_li").hide();
			}
			view.inittabs($("#selectedTab").val(), 1);
			$("#view_choose_bar").show();
			$("#view_basic").show();
			$("#view_application").show();
			$("#view_awarded").show();
			$("#view_review").show();
			$("#view_content").show();
		}
		else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	exports.init = function(){
		viewMoesocial.initAward();
		viewMoesocial.getNamespace();
		viewMoesocial.prev(showDetails);
		viewMoesocial.next(showDetails);
		viewMoesocial.back();
		viewMoesocial.add();
		viewMoesocial.mod();
		viewMoesocial.del();
		viewMoesocial.readApply(showDetails);
	};
	exports.showDetails = function(result){
		showDetails(result);
	};
});
