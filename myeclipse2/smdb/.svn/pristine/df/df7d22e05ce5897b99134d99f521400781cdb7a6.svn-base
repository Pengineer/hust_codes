/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewApplication = require('javascript/project/project_share/application/apply/view');
	var viewKey = require('javascript/project/key/view');
	
	var projectType = "key";
	
	exports.initClick = function() {
		$(".j_modifyAppApply").live('click',function(){//申请修改
			viewApplication.modifyAppApply(projectType);
		});
		$(".j_submitAppApply").live('click',function(){//申请提交
			viewApplication.submitAppApply(projectType, viewKey.showProjectDetails);
		});
		$(".j_deleteAppApply").live('click',function(){//申请删除
			viewApplication.deleteAppApply(projectType);
		});
		$(".j_addAppAuditPop").live('click',function(){//审核添加
			viewApplication.addAppAuditPop(projectType, viewKey.showProjectDetails);
		});
		$(".j_modifyAppAuditPop").live('click',function(){//审核修改
			viewApplication.modifyAppAuditPop(projectType, viewKey.showProjectDetails);
		});
		$(".j_submitAppAudit").live('click',function(){//审核提交
			viewApplication.submitAppAudit(projectType, viewKey.showProjectDetails);
		});
		$(".j_backAppApply").live('click',function(){//审核退回
			viewApplication.backAppApply(projectType, viewKey.showProjectDetails);
		});
		$(".j_viewAppAuditPop").live('click',function(){//审核查看
			var id = $(this).attr("appId");
			var data = $(this).attr("alt");
			viewApplication.viewAppAuditPop(id, data, projectType);
		});
//		window.modifyAppApply = function(){viewApplication.modifyAppApply(projectType)};
//		window.submitAppApply = function(){viewApplication.submitAppApply(projectType, viewKey.showProjectDetails)};
//		window.deleteAppApply = function(){viewApplication.deleteAppApply(projectType)};
//		window.addAppAuditPop = function(){viewApplication.addAppAuditPop(projectType, viewKey.showProjectDetails)};
//		window.modifyAppAuditPop = function(){viewApplication.modifyAppAuditPop(projectType, viewKey.showProjectDetails)};
//		window.viewAppAuditPop = function(id, data){viewApplication.viewAppAuditPop(id, data, projectType)};
//		window.submitAppAudit = function(){viewApplication.submitAppAudit(projectType, viewKey.showProjectDetails)};
//		window.backAppApply = function(){viewApplication.backAppApply(projectType, viewKey.showProjectDetails)};//公共部分
		window.uploadAppPop = function(appId){viewApplication.uploadAppPop(appId, projectType, viewKey.showProjectDetails)};
	};
}); 


	