/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var viewApplicationReview = require('javascript/project/project_share/application/review/view');
	var viewGeneral = require('javascript/project/general/view');
	
	var projectType = "general";
	
	//初始化onclick事件
	exports.initClick = function() {
		$(".j_addAppReviewResultPop").live('click',function(){//添加录入评审
			var id = $(this).attr("appId");
			viewApplicationReview.addAppReviewResultPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_addAppReviewExpert").live('click',function(){//添加录入评审专家
			var id = $(this).attr("appId");
			viewApplicationReview.addAppReviewExpert(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyAppReviewResultPop").live('click',function(){//修改录入评审
			var id = $(this).attr("appId");
			viewApplicationReview.modifyAppReviewResultPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAppReviewResult").live('click',function(){//提交录入评审
			var id = $(this).attr("appId");
			viewApplicationReview.submitAppReviewResult(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_addAppReviewPop").live('click',function(){//添加专家评审
			var id = $(this).attr("appId");
			viewApplicationReview.addAppReviewPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyAppReviewPop").live('click',function(){//修改专家评审
			var appId = $(this).attr("appId");
			var appRevId = $(this).attr("appRevId");
			viewApplicationReview.modifyAppReviewPop(appId, appRevId, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAppReview").live('click',function(){//提交专家评审
			var id = $(this).attr("appId");
			viewApplicationReview.submitAppReview(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_viewAppReviewPop").live('click',function(){//查看专家评审详情
			var appRevId = $(this).attr("appRevId");
			viewApplicationReview.viewAppReviewPop(appRevId, projectType);
		});
		$(".j_addAppGroupReviewPop").live('click',function(){//添加小组评审
			var id = $(this).attr("appId");
			viewApplicationReview.addAppGroupReviewPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyAppGroupReviewPop").live('click',function(){//修改小组评审
			var id = $(this).attr("appId");
			viewApplicationReview.modifyAppGroupReviewPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAppGroupReview").live('click',function(){//提交小组评审
			var id = $(this).attr("appId");
			viewApplicationReview.submitAppGroupReview(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_viewAppGroupReviewPop").live('click',function(){//查看小组评审详情
			var id = $(this).attr("appId");
			viewApplicationReview.viewAppGroupReviewPop(id, projectType);
		});
		$(".j_addAppReviewAuditPop").live('click',function(){//添加评审审核
			var id = $(this).attr("appId");
			viewApplicationReview.addAppReviewAuditPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_modifyAppReviewAuditPop").live('click',function(){//修改评审审核
			var id = $(this).attr("appId");
			viewApplicationReview.modifyAppReviewAuditPop(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_submitAppReviewAuditResult").live('click',function(){//提交评审审核
			var id = $(this).attr("appId");
			viewApplicationReview.submitAppReviewAuditResult(id, projectType, viewGeneral.showProjectDetails);
		});
		$(".j_viewAppReviewAuditPop").live('click',function(){//查看评审审核详情
			var id = $(this).attr("appId");
			viewApplicationReview.viewAppReviewAuditPop(id, projectType);
		});
//		window.addAppReviewResultPop = function(data){viewApplicationReview.addAppReviewResultPop(data, projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.modifyAppReviewResultPop = function(data){viewApplicationReview.modifyAppReviewResultPop(data, projectType, viewGeneral.showProjectDetails)};
//		window.submitAppReviewResult = function(data){viewApplicationReview.submitAppReviewResult(data, projectType, viewGeneral.showProjectDetails)};
//		window.addAppReviewAuditPop = function(id){viewApplicationReview.addAppReviewAuditPop(id, projectType, viewGeneral.showProjectDetails)};
//		window.modifyAppReviewAuditPop = function(id){viewApplicationReview.modifyAppReviewAuditPop(id, projectType, viewGeneral.showProjectDetails)};
//		window.submitAppReviewAuditResult = function(id){viewApplicationReview.submitAppReviewAuditResult(id, projectType, viewGeneral.showProjectDetails)};
//		window.viewAppReviewAuditPop = function(id){viewApplicationReview.viewAppReviewAuditPop(id, projectType)};
//		window.addAppReviewPop = function(id){viewApplicationReview.addAppReviewPop(id, projectType, viewGeneral.showProjectDetails)};
//		window.modifyAppReviewPop = function(id, entityId){viewApplicationReview.modifyAppReviewPop(id, entityId, projectType, viewGeneral.showProjectDetails)};//公共部分
//		window.submitAppReview = function(id){viewApplicationReview.submitAppReview(id, projectType, viewGeneral.showProjectDetails)};
//		window.viewAppReviewPop = function(appRevId){viewApplicationReview.viewAppReviewPop(appRevId, projectType)};
//		window.addAppGroupReviewPop = function(id){viewApplicationReview.addAppGroupReviewPop(id, projectType, viewGeneral.showProjectDetails)};
//		window.modifyAppGroupReviewPop = function(id){viewApplicationReview.modifyAppGroupReviewPop(id, projectType, viewGeneral.showProjectDetails)};
//		window.submitAppGroupReview = function(id){viewApplicationReview.submitAppGroupReview(id, projectType, viewGeneral.showProjectDetails)};
//		window.viewAppGroupReviewPop = function(id){viewApplicationReview.viewAppGroupReviewPop(id, projectType)};
	};
});