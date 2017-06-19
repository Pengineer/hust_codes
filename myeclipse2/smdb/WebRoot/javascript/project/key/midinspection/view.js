/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewMidinspection = require('javascript/project/project_share/midinspection/view');
	var viewKey = require('javascript/project/key/view');
	
	var projectType = "key";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_toAddMidApplyPop").live('click',function(){//申请添加
			viewMidinspection.toAddMidApplyPop(projectType, viewKey.showProjectDetails);
		});
		$(".j_toModifyMidApplyPop").live('click',function(){//申请修改
			viewMidinspection.toModifyMidApplyPop(projectType, viewKey.showProjectDetails)
		});
		$(".j_submitMidApply").live('click',function(){//申请提交
			viewMidinspection.submitMidApply(projectType, viewKey.showProjectDetails)
		});
		$(".j_deleteMidApply").live('click',function(){//申请删除
			viewMidinspection.deleteMidApply(projectType, viewKey.showProjectDetails)
		});
		$(".j_addMidAuditPop").live('click',function(){//审核添加
			viewMidinspection.addMidAuditPop(projectType, viewKey.showProjectDetails)
		});
		$(".j_modifyMidAuditPop").live('click',function(){//审核修改
			viewMidinspection.modifyMidAuditPop(projectType, viewKey.showProjectDetails)
		});
		$(".j_submitMidAudit").live('click',function(){//审核提交
			viewMidinspection.submitMidAudit(projectType, viewKey.showProjectDetails)
		});
		$(".j_backMidApply").live('click',function(){//审核退回
			viewMidinspection.backMidApply(projectType, viewKey.showProjectDetails)
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
			viewMidinspection.uploadMidPop(id, projectType, viewKey.showProjectDetails);
		});
		$(".j_addMidResultPop").live('click',function(){//录入添加
			viewMidinspection.addMidResultPop(projectType, viewKey.showProjectDetails);
		});
		$(".j_modifyMidResultPop").live('click',function(){//录入修改
			viewMidinspection.modifyMidResultPop(projectType, viewKey.showProjectDetails);
		});
		$(".j_submitMidResult").live('click',function(){//录入提交
			viewMidinspection.submitMidResult(projectType, viewKey.showProjectDetails);
		});
//		window.toAddMidApplyPop = function(){viewMidinspection.toAddMidApplyPop(projectType, viewKey.showProjectDetails)};//公共部分
//		window.toModifyMidApplyPop = function(){viewMidinspection.toModifyMidApplyPop(projectType, viewKey.showProjectDetails)};
//		window.submitMidApply = function(){viewMidinspection.submitMidApply(projectType, viewKey.showProjectDetails)};
//		window.deleteMidApply = function(){viewMidinspection.deleteMidApply(projectType, viewKey.showProjectDetails)};
//		window.addMidAuditPop = function(){viewMidinspection.addMidAuditPop(projectType, viewKey.showProjectDetails)};
//		window.modifyMidAuditPop = function(){viewMidinspection.modifyMidAuditPop(projectType, viewKey.showProjectDetails)};
//		window.viewMidAuditPop = function(id, data){viewMidinspection.viewMidAuditPop(id, data, projectType)};
//		window.submitMidAudit = function(){viewMidinspection.submitMidAudit(projectType, viewKey.showProjectDetails)};
//		window.backMidApply = function(){viewMidinspection.backMidApply(projectType, viewKey.showProjectDetails)};//公共部分
//		window.addMidResultPop = function(){viewMidinspection.addMidResultPop(projectType, viewKey.showProjectDetails)};
//		window.modifyMidResultPop = function(){viewMidinspection.modifyMidResultPop(projectType, viewKey.showProjectDetails)};
//		window.submitMidResult = function(){viewMidinspection.submitMidResult(projectType, viewKey.showProjectDetails)};
//		window.uploadMidPop = function(midId){viewMidinspection.uploadMidPop(midId, projectType, viewKey.showProjectDetails)};
//		window.downloadMidTemplate = function(){viewMidinspection.downloadMidTemplate(projectType)};
	};
});