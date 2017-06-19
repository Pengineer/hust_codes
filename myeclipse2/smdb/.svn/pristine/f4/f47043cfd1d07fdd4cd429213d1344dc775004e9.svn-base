define(function(require, exports, module) {
	var editAppRevAudit = require('javascript/project/project_share/application/review/audit');
	
	var projectType = "instp";
	
	//初始化onclick事件
	exports.init = function() {
		$(".j_addOrModifySave").live('click',function(){//评审审核添加暂存
			editAppRevAudit.submitOrNotAppReviewAudit(2, thisPopLayer, projectType);
		});
		$(".j_addOrModifySubmit").live('click',function(){//评审审核添加提交
			editAppRevAudit.submitOrNotAppReviewAudit(3, thisPopLayer, projectType);
		});
		$(".j_showNumberAndFee").live('click',function(){//是否显示项目立项编号和经费
			var data = $("input[name='reviewAuditResult'][type='radio']:checked").val();
			editAppRevAudit.showNumberAndFee(data);
		});
		editAppRevAudit.initAppReviewAudit();
//		window.submitOrNotAppReviewAudit = function(data, layer){editAppRevAudit.submitOrNotAppReviewAudit(data, layer, projectType)};
//		window.initAppReviewAudit = function(){editAppRevAudit.initAppReviewAudit()};
//		window.showNumberAndFee = function(type){editAppRevAudit.showNumberAndFee(type)};
		editAppRevAudit.valid();
		editAppRevAudit.datepickInit();
	};
});