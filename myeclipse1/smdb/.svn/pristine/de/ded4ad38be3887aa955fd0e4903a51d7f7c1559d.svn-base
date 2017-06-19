/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewMidinspection = require('javascript/project/project_share/midinspection/view');
	var viewGeneral = require('javascript/project/general/view');
	
	var projectType = "general";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_toAddMidApplyPop").live('click',function(){//申请添加
			viewMidinspection.toAddMidApplyPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_toModifyMidApplyPop").live('click',function(){//申请修改
			viewMidinspection.toModifyMidApplyPop(projectType, viewGeneral.showProjectDetails)
		});
		$(".j_submitMidApply").live('click',function(){//申请提交
			viewMidinspection.submitMidApply(projectType, viewGeneral.showProjectDetails)
		});
		$(".j_deleteMidApply").live('click',function(){//申请删除
			viewMidinspection.deleteMidApply(projectType, viewGeneral.showProjectDetails)
		});
		$(".j_addMidAuditPop").live('click',function(){//审核添加
			viewMidinspection.addMidAuditPop(projectType, viewGeneral.showProjectDetails)
		});
		$(".j_modifyMidAuditPop").live('click',function(){//审核修改
			viewMidinspection.modifyMidAuditPop(projectType, viewGeneral.showProjectDetails)
		});
		$(".j_submitMidAudit").live('click',function(){//审核提交
			viewMidinspection.submitMidAudit(projectType, viewGeneral.showProjectDetails)
		});
		$(".j_backMidApply").live('click',function(){//审核退回
			viewMidinspection.backMidApply(projectType, viewGeneral.showProjectDetails)
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
			viewMidinspection.uploadMidPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_addMidResultPop").live('click',function(){//录入添加
			viewMidinspection.addMidResultPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyMidResultPop").live('click',function(){//录入修改
			viewMidinspection.modifyMidResultPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitMidResult").live('click',function(){//录入提交
			viewMidinspection.submitMidResult(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_switchMidPublish").live('click', function(){//切换发布
			viewMidinspection.switchMidPublish(projectType);
		});
//		window.toAddMidApplyPop = function(){viewMidinspection.toAddMidApplyPop(projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.toModifyMidApplyPop = function(){viewMidinspection.toModifyMidApplyPop(projectType, viewGeneral.showProjectDetails)};
//		window.submitMidApply = function(){viewMidinspection.submitMidApply(projectType, viewGeneral.showProjectDetails)};
//		window.deleteMidApply = function(){viewMidinspection.deleteMidApply(projectType, viewGeneral.showProjectDetails)};
//		window.addMidAuditPop = function(){viewMidinspection.addMidAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.modifyMidAuditPop = function(){viewMidinspection.modifyMidAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.viewMidAuditPop = function(id, data){viewMidinspection.viewMidAuditPop(id, data, projectType)};
//		window.submitMidAudit = function(){viewMidinspection.submitMidAudit(projectType, viewGeneral.showProjectDetails)};
//		window.backMidApply = function(){viewMidinspection.backMidApply(projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.addMidResultPop = function(){viewMidinspection.addMidResultPop(projectType, viewGeneral.showProjectDetails)};
//		window.modifyMidResultPop = function(){viewMidinspection.modifyMidResultPop(projectType, viewGeneral.showProjectDetails)};
//		window.submitMidResult = function(){viewMidinspection.submitMidResult(projectType, viewGeneral.showProjectDetails)};
//		window.uploadMidPop = function(midId){viewMidinspection.uploadMidPop(midId, projectType, viewGeneral.showProjectDetails)};
//		window.downloadMidTemplate = function(){viewMidinspection.downloadMidTemplate(projectType)};
	};
});