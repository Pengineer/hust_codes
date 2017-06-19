/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewAnninspection = require('javascript/project/project_share/anninspection/view');
	var viewInstp = require('javascript/project/instp/view');
	
	var projectType = "instp";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_toAddAnnApplyPop").live('click',function(){//申请添加
			viewAnninspection.toAddAnnApplyPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_toModifyAnnApplyPop").live('click',function(){//申请修改
			viewAnninspection.toModifyAnnApplyPop(projectType, viewInstp.showProjectDetails)
		});
		$(".j_submitAnnApply").live('click',function(){//申请提交
			viewAnninspection.submitAnnApply(projectType, viewInstp.showProjectDetails)
		});
		$(".j_deleteAnnApply").live('click',function(){//申请删除
			viewAnninspection.deleteAnnApply(projectType, viewInstp.showProjectDetails)
		});
		$(".j_addAnnAuditPop").live('click',function(){//审核添加
			viewAnninspection.addAnnAuditPop(projectType, viewInstp.showProjectDetails)
		});
		$(".j_modifyAnnAuditPop").live('click',function(){//审核修改
			viewAnninspection.modifyAnnAuditPop(projectType, viewInstp.showProjectDetails)
		});
		$(".j_submitAnnAudit").live('click',function(){//审核提交
			viewAnninspection.submitAnnAudit(projectType, viewInstp.showProjectDetails)
		});
		$(".j_backAnnApply").live('click',function(){//审核退回
			viewAnninspection.backAnnApply(projectType, viewInstp.showProjectDetails)
		});
		$(".j_viewAnnAuditPop").live('click',function(){//审核查看
			var id = $(this).attr("annId");
			var data = $(this).attr("alt");
			viewAnninspection.viewAnnAuditPop(id, data, projectType);
		});
		$(".j_downloadAnnTemplate").live('click',function(){//下载年检申请书模板
			viewAnninspection.downloadAnnTemplate(projectType);
		});
		$(".j_uploadAnnPop").live('click',function(){//上传年检申请书（教育部录入）
			var id = $(this).attr("annId");
			viewAnninspection.uploadAnnPop(id, projectType, viewInstp.showProjectDetails);
		});
		$(".j_addAnnResultPop").live('click',function(){//录入添加
			viewAnninspection.addAnnResultPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_modifyAnnResultPop").live('click',function(){//录入修改
			viewAnninspection.modifyAnnResultPop(projectType, viewInstp.showProjectDetails);
		});
		$(".j_submitAnnResult").live('click',function(){//录入提交
			viewAnninspection.submitAnnResult(projectType, viewInstp.showProjectDetails);
		});
//		window.toAddAnnApplyPop = function(){viewAnninspection.toAddAnnApplyPop(projectType, viewInstp.showProjectDetails)};//公共部分
//		window.toModifyAnnApplyPop = function(){viewAnninspection.toModifyAnnApplyPop(projectType, viewInstp.showProjectDetails)};
//		window.submitAnnApply = function(){viewAnninspection.submitAnnApply(projectType, viewInstp.showProjectDetails)};
//		window.deleteAnnApply = function(){viewAnninspection.deleteAnnApply(projectType, viewInstp.showProjectDetails)};
//		window.addAnnAuditPop = function(){viewAnninspection.addAnnAuditPop(projectType, viewInstp.showProjectDetails)};
//		window.modifyAnnAuditPop = function(){viewAnninspection.modifyAnnAuditPop(projectType)};
//		window.viewAnnAuditPop = function(id, data){viewAnninspection.viewAnnAuditPop(id, data, projectType)};
//		window.submitAnnAudit = function(){viewAnninspection.submitAnnAudit(projectType, viewInstp.showProjectDetails)};
//		window.backAnnApply = function(){viewAnninspection.backAnnApply(projectType, viewInstp.showProjectDetails)};//公共部分
//		window.addAnnResultPop = function(){viewAnninspection.addAnnResultPop(projectType, viewInstp.showProjectDetails)};
//		window.modifyAnnResultPop = function(){viewAnninspection.modifyAnnResultPop(projectType, viewInstp.showProjectDetails)};
//		window.submitAnnResult = function(){viewAnninspection.submitAnnResult(projectType, viewInstp.showProjectDetails)};
//		window.uploadAnnPop = function(AnnId){viewAnninspection.uploadAnnPop(AnnId, projectType, viewInstp.showProjectDetails)};
//		window.downloadAnnTemplate = function(){viewAnninspection.downloadAnnTemplate(projectType)};
	};
});