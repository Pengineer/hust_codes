/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewApplication = require('javascript/project/project_share/application/apply/view');
	var viewGeneral = require('javascript/project/general/view');
	
	var projectType = "general";
	
	exports.initClick = function() {
		$(".j_modifyAppApply").live('click',function(){//申请修改
			viewApplication.modifyAppApply(projectType);
		});
		$(".j_submitAppApply").live('click',function(){//申请提交
			viewApplication.submitAppApply(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_deleteAppApply").live('click',function(){//申请删除
			viewApplication.deleteAppApply(projectType);
		});
		$(".j_addAppAuditPop").live('click',function(){//审核添加
			viewApplication.addAppAuditPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyAppAuditPop").live('click',function(){//审核修改
			viewApplication.modifyAppAuditPop(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAppAudit").live('click',function(){//审核提交
			viewApplication.submitAppAudit(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_backAppApply").live('click',function(){//审核退回
			viewApplication.backAppApply(projectType, viewGeneral.showProjectDetails);
		});
		$(".j_viewAppAuditPop").live('click',function(){//审核查看
			var id = $(this).attr("appId");
			var data = $(this).attr("alt");
			viewApplication.viewAppAuditPop(id, data, projectType);
		});
//		window.modifyAppApply = function(){viewApplication.modifyAppApply(projectType)};
//		window.submitAppApply = function(){viewApplication.submitAppApply(projectType, viewGeneral.showProjectDetails)};
//		window.deleteAppApply = function(){viewApplication.deleteAppApply(projectType)};
//		window.addAppAuditPop = function(){viewApplication.addAppAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.modifyAppAuditPop = function(){viewApplication.modifyAppAuditPop(projectType, viewGeneral.showProjectDetails)};
//		window.viewAppAuditPop = function(id, data){viewApplication.viewAppAuditPop(id, data, projectType)};
//		window.submitAppAudit = function(){viewApplication.submitAppAudit(projectType, viewGeneral.showProjectDetails)};
//		window.backAppApply = function(){viewApplication.backAppApply(projectType, viewGeneral.showProjectDetails)};//公共部分
		window.uploadAppPop = function(appId){viewApplication.uploadAppPop(appId, projectType, viewGeneral.showProjectDetails)};
	};
}); 


	