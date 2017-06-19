/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewMidinspection = require('javascript/project/project_share/midinspection/view');
	var viewInstp = require('javascript/project/instp/view');
	
	var projectType = "instp";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_toAddMidApplyPop").live('click',function(){//申请添加
			viewMidinspection.toAddMidApplyPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_toModifyMidApplyPop").live('click',function(){//申请修改
			viewMidinspection.toModifyMidApplyPop(projectType, viewInstp.showProjectDetails)
		});
		$(".j_submitMidApply").live('click',function(){//申请提交
			viewMidinspection.submitMidApply(projectType, viewInstp.showProjectDetails)
		});
		$(".j_deleteMidApply").live('click',function(){//申请删除
			viewMidinspection.deleteMidApply(projectType, viewInstp.showProjectDetails)
		});
		$(".j_addMidAuditPop").live('click',function(){//审核添加
			viewMidinspection.addMidAuditPop(projectType, viewInstp.showProjectDetails)
		});
		$(".j_modifyMidAuditPop").live('click',function(){//审核修改
			viewMidinspection.modifyMidAuditPop(projectType, viewInstp.showProjectDetails)
		});
		$(".j_submitMidAudit").live('click',function(){//审核提交
			viewMidinspection.submitMidAudit(projectType, viewInstp.showProjectDetails)
		});
		$(".j_backMidApply").live('click',function(){//审核退回
			viewMidinspection.backMidApply(projectType, viewInstp.showProjectDetails)
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
			viewMidinspection.uploadMidPop(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_addMidResultPop").live('click',function(){//录入添加
			viewMidinspection.addMidResultPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_modifyMidResultPop").live('click',function(){//录入修改
			viewMidinspection.modifyMidResultPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitMidResult").live('click',function(){//录入提交
			viewMidinspection.submitMidResult(projectType, viewInstp.showProjectDetails);
		});
//		window.toAddMidApplyPop = function(){viewMidinspection.toAddMidApplyPop(projectType, viewInstp.showProjectDetails)};//公共部分
//		window.toModifyMidApplyPop = function(){viewMidinspection.toModifyMidApplyPop(projectType, viewInstp.showProjectDetails)};
//		window.submitMidApply = function(){viewMidinspection.submitMidApply(projectType, viewInstp.showProjectDetails)};
//		window.deleteMidApply = function(){viewMidinspection.deleteMidApply(projectType, viewInstp.showProjectDetails)};
//		window.addMidAuditPop = function(){viewMidinspection.addMidAuditPop(projectType, viewInstp.showProjectDetails)};
//		window.modifyMidAuditPop = function(){viewMidinspection.modifyMidAuditPop(projectType, viewInstp.showProjectDetails)};
//		window.viewMidAuditPop = function(id, data){viewMidinspection.viewMidAuditPop(id, data, projectType)};
//		window.submitMidAudit = function(){viewMidinspection.submitMidAudit(projectType, viewInstp.showProjectDetails)};
//		window.backMidApply = function(){viewMidinspection.backMidApply(projectType, viewInstp.showProjectDetails)};//公共部分
//		window.addMidResultPop = function(){viewMidinspection.addMidResultPop(projectType, viewInstp.showProjectDetails)};
//		window.modifyMidResultPop = function(){viewMidinspection.modifyMidResultPop(projectType, viewInstp.showProjectDetails)};
//		window.submitMidResult = function(){viewMidinspection.submitMidResult(projectType, viewInstp.showProjectDetails)};
//		window.uploadMidPop = function(midId){viewMidinspection.uploadMidPop(midId, projectType, viewInstp.showProjectDetails)};
//		window.downloadMidTemplate = function(){viewMidinspection.downloadMidTemplate(projectType)};
	};
});