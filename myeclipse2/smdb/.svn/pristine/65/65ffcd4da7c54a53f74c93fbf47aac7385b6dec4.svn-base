/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewAnninspection = require('javascript/project/project_share/anninspection/view');
	var viewGeneral = require('javascript/project/general/view');
	
	var projectType = "general";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_toAddAnnApplyPop").live('click',function(){//申请添加
			viewAnninspection.toAddAnnApplyPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_toModifyAnnApplyPop").live('click',function(){//申请修改
			viewAnninspection.toModifyAnnApplyPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAnnApply").live('click',function(){//申请提交
			viewAnninspection.submitAnnApply(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_deleteAnnApply").live('click',function(){//申请删除
			viewAnninspection.deleteAnnApply(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_addAnnAuditPop").live('click',function(){//审核添加
			viewAnninspection.addAnnAuditPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyAnnAuditPop").live('click',function(){//审核修改
			viewAnninspection.modifyAnnAuditPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAnnAudit").live('click',function(){//审核提交
			viewAnninspection.submitAnnAudit(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_backAnnApply").live('click',function(){//审核退回
			viewAnninspection.backAnnApply(projectType, viewGeneral.showProjectDetails);
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
			viewAnninspection.uploadAnnPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_addAnnResultPop").live('click',function(){//录入添加
			viewAnninspection.addAnnResultPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyAnnResultPop").live('click',function(){//录入修改
			viewAnninspection.modifyAnnResultPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAnnResult").live('click',function(){//录入提交
			viewAnninspection.submitAnnResult(projectType, viewGeneral.showProjectDetails);
		});
		
//		window.toAddAnnApplyPop = function(){viewAnninspection.toAddAnnApplyPop(projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.toModifyAnnApplyPop = function(){viewAnninspection.toModifyAnnApplyPop(projectType, viewGeneral.showProjectDetails)};
//		window.submitAnnApply = function(){viewAnninspection.submitAnnApply(projectType, viewGeneral.showProjectDetails)};
//		window.deleteAnnApply = function(){viewAnninspection.deleteAnnApply(projectType, viewGeneral.showProjectDetails)};
//		window.addAnnAuditPop = function(){viewAnninspection.addAnnAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.modifyAnnAuditPop = function(){viewAnninspection.modifyAnnAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.viewAnnAuditPop = function(id, data){viewAnninspection.viewAnnAuditPop(id, data, projectType)};
//		window.submitAnnAudit = function(){viewAnninspection.submitAnnAudit(projectType, viewGeneral.showProjectDetails)};
//		window.backAnnApply = function(){viewAnninspection.backAnnApply(projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.addAnnResultPop = function(){viewAnninspection.addAnnResultPop(projectType, viewGeneral.showProjectDetails)};
//		window.modifyAnnResultPop = function(){viewAnninspection.modifyAnnResultPop(projectType, viewGeneral.showProjectDetails)};
//		window.submitAnnResult = function(){viewAnninspection.submitAnnResult(projectType, viewGeneral.showProjectDetails)};
//		window.uploadAnnPop = function(AnnId){viewAnninspection.uploadAnnPop(AnnId, projectType, viewGeneral.showProjectDetails)};
//		window.downloadAnnTemplate = function(){viewAnninspection.downloadAnnTemplate(projectType)};
	};
});