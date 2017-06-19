/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listEndReview = require('javascript/project/project_share/endinspection/review/list');
	var nameSpace = "project/general/endinspection/review";
	
	var downloadfile = function(){
		$(".download_general_3").live("click", function() {
			var validateUrl = "project/general/endinspection/apply/validateFile.action?entityId="+this.id+"&filepath="+this.name;
			var successUrl = "project/general/endinspection/apply/downloadApply.action?entityId="+this.id+"&filepath="+this.name;
			downloadFile(validateUrl, successUrl);
			return false;
		});
	};
	
	exports.init = function() {
		downloadfile();
		listEndReview.init(nameSpace);//公共部分
		listEndReview.viewApplicant();//查看申请人
		listEndReview.printAndExport(nameSpace);//打印、导出结项一览表
	};
});