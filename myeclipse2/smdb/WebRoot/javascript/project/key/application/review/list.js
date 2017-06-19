/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listAppReview = require('javascript/project/project_share/application/review/list');
	var nameSpace = "project/key/application/review";
	var downloadfile = function(){
		$(".download_general_3").live("click", function() {
			var validateUrl = "project/key/application/apply/validateFile.action?entityId="+this.id+"&filepath="+this.name;
			var successUrl = "project/key/application/apply/downloadApply.action?entityId="+this.id+"&filepath="+this.name;
			downloadFile(validateUrl, successUrl);
			return false;
		});
	};
	exports.init = function() {
		downloadfile();
		listAppReview.init(nameSpace);//公共部分
		listAppReview.viewApplicant();//查看申请人
		listAppReview.toView(nameSpace);
	};
});