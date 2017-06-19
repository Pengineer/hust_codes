/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listEndReview = require('javascript/project/project_share/endinspection/review/list');
	var nameSpace = "project/post/endinspection/review";
	
	var downloadfile = function(){
		$(function() {
			$(".download_post_3").live("click", function() {
				var validateUrl = "project/post/endinspection/apply/validateFile.action?entityId="+this.id+"&filepath="+this.name;
				var successUrl = "project/post/endinspection/apply/downloadApply.action?filepath="+this.name;
				downloadFile(validateUrl, successUrl);
				return false;
			});
		});
	};
	
	exports.init = function() {
		downloadfile();
		listEndReview.init(nameSpace);//公共部分
		listEndReview.viewApplicant();//查看申请人
		listEndReview.printAndExport(nameSpace);//打印、导出结项一览表
	};
});