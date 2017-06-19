/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var listEndReview = require('javascript/project/project_share/endinspection/review/list');
	var nameSpace = "project/entrust/endinspection/review";
	
	var downloadfile = function(){
		$(".download_entrust_3").live("click", function() {
			var validateUrl = "project/entrust/endinspection/apply/validateFile.action?entityId="+this.id+"&filepath="+this.name;
			var successUrl = "project/entrust/endinspection/apply/downloadApply.action?filepath="+this.name;
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