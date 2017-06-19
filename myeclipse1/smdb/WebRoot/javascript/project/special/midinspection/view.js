/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewMidinspection = require('javascript/project/project_share/midinspection/view');
	var viewSpecial = require('javascript/project/special/view');
	
	var projectType = "special";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_toAddMidApplyPop").live('click',function(){//申请添加
			viewMidinspection.toAddMidApplyPop(projectType, viewSpecial.showProjectDetails);
		});
		$(".j_toModifyMidApplyPop").live('click',function(){//申请修改
			viewMidinspection.toModifyMidApplyPop(projectType, viewSpecial.showProjectDetails)
		});
		$(".j_submitMidApply").live('click',function(){//申请提交
			viewMidinspection.submitMidApply(projectType, viewSpecial.showProjectDetails)
		});
		$(".j_deleteMidApply").live('click',function(){//申请删除
			viewMidinspection.deleteMidApply(projectType, viewSpecial.showProjectDetails)
		});
		$(".j_addMidAuditPop").live('click',function(){//审核添加
			viewMidinspection.addMidAuditPop(projectType, viewSpecial.showProjectDetails)
		});
		$(".j_modifyMidAuditPop").live('click',function(){//审核修改
			viewMidinspection.modifyMidAuditPop(projectType, viewSpecial.showProjectDetails)
		});
		$(".j_submitMidAudit").live('click',function(){//审核提交
			viewMidinspection.submitMidAudit(projectType, viewSpecial.showProjectDetails)
		});
		$(".j_backMidApply").live('click',function(){//审核退回
			viewMidinspection.backMidApply(projectType, viewSpecial.showProjectDetails)
		});
		$(".j_viewMidAuditPop").live('click',function(){//审核查看
			var id = $(this).attr("midId");
			var data = $(this).attr("alt");
			viewMidinspection.viewMidAuditPop(id, data, projectType);
		});
		$(".j_downloadMidTemplate").live('click',function(){//下载中检申请书模板
			viewMidinspection.downloadMidTemplate(projectType);
		});
		$(".j_uploadMidPop").live('click',function(){//上传中检申请书（教育部录入）
			var id = $(this).attr("midId");
			viewMidinspection.uploadMidPop(id, projectType, viewSpecial.showProjectDetails);
		});
		$(".j_addMidResultPop").live('click',function(){//录入添加
			viewMidinspection.addMidResultPop(projectType, viewSpecial.showProjectDetails);
		});
		$(".j_modifyMidResultPop").live('click',function(){//录入修改
			viewMidinspection.modifyMidResultPop(projectType, viewSpecial.showProjectDetails);
		});
		$(".j_submitMidResult").live('click',function(){//录入提交
			viewMidinspection.submitMidResult(projectType, viewSpecial.showProjectDetails);
		});
//		window.toAddMidApplyPop = function(){viewMidinspection.toAddMidApplyPop(projectType, viewSpecial.showProjectDetails)};//公共部分
//		window.toModifyMidApplyPop = function(){viewMidinspection.toModifyMidApplyPop(projectType, viewSpecial.showProjectDetails)};
//		window.submitMidApply = function(){viewMidinspection.submitMidApply(projectType, viewSpecial.showProjectDetails)};
//		window.deleteMidApply = function(){viewMidinspection.deleteMidApply(projectType, viewSpecial.showProjectDetails)};
//		window.addMidAuditPop = function(){viewMidinspection.addMidAuditPop(projectType, viewSpecial.showProjectDetails)};
//		window.modifyMidAuditPop = function(){viewMidinspection.modifyMidAuditPop(projectType, viewSpecial.showProjectDetails)};
//		window.viewMidAuditPop = function(id, data){viewMidinspection.viewMidAuditPop(id, data, projectType)};
//		window.submitMidAudit = function(){viewMidinspection.submitMidAudit(projectType, viewSpecial.showProjectDetails)};
//		window.backMidApply = function(){viewMidinspection.backMidApply(projectType, viewSpecial.showProjectDetails)};//公共部分
//		window.addMidResultPop = function(){viewMidinspection.addMidResultPop(projectType, viewSpecial.showProjectDetails)};
//		window.modifyMidResultPop = function(){viewMidinspection.modifyMidResultPop(projectType, viewSpecial.showProjectDetails)};
//		window.submitMidResult = function(){viewMidinspection.submitMidResult(projectType, viewSpecial.showProjectDetails)};
//		window.uploadMidPop = function(midId){viewMidinspection.uploadMidPop(midId, projectType, viewSpecial.showProjectDetails)};
//		window.downloadMidTemplate = function(){viewMidinspection.downloadMidTemplate(projectType)};
	};
});