define(function(require, exports, module) {
	var editAppRev = require('javascript/project/project_share/application/review/edit');
	
	var projectType = "general";
	var nameSpace = "project/general/application/review";
	
	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#view_container").hide();
			$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
			$("#view_container").show();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	var show = function(nameSpace, showDetails, viewType){
		viewType = viewType || "";
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		};
		$.ajax({
			url: nameSpace + "/getGroupRev.action",
			type: "post",
			data: "entityId=" + $("#entityId").val() + "&viewType=" + viewType,//type选填
			dataType: "json",
			success: showDetails//在具体的查看页面js实现
		});
	};
	//初始化onclick事件
	exports.init = function() {
		show(nameSpace, showDetails);// 公用方法
		editAppRev.init();
		editAppRev.initGroupOpinion(projectType);
		$(".j_addSave").live('click',function(){//添加专家评审暂存
			editAppRev.submitOrSaveAddAppReview(2, thisPopLayer);
		});
		$(".j_addSubmit").live('click',function(){//添加专家评审提交
			editAppRev.submitOrSaveAddAppReview(3, thisPopLayer);
		});
		$(".j_modifySave").live('click',function(){//添加专家评审暂存
			editAppRev.submitOrSaveModifyAppReview(2, thisPopLayer);
		});
		$(".j_modifySubmit").live('click',function(){//添加专家评审提交
			editAppRev.submitOrSaveModifyAppReview(3, thisPopLayer);
		});
		$(".j_addSave").live('click',function(){//添加专家评审暂存
			editAppRev.submitOrSaveAddAppReview(2, thisPopLayer);
		});
		$(".j_addSubmit").live('click',function(){//添加专家评审提交
			editAppRev.submitOrSaveAddAppReview(3, thisPopLayer);
		});
		$(".j_modifySave").live('click',function(){//修改专家评审暂存
			editAppRev.submitOrSaveModifyAppReview(2, thisPopLayer);
		});
		$(".j_modifySubmit").live('click',function(){//修改专家评审提交
			editAppRev.submitOrSaveModifyAppReview(3, thisPopLayer);
		});
		$(".j_addGroupSave").live('click',function(){//添加小组评审暂存
			editAppRev.submitOrSaveAddAppGroupReview(2, thisPopLayer);
		});
		$(".j_addGroupSubmit").live('click',function(){//添加小组评审提交
			editAppRev.submitOrSaveAddAppGroupReview(3, thisPopLayer);
		});
		$(".j_modifyGroupSave").live('click',function(){//修改小组评审暂存
			editAppRev.submitOrSaveModifyAppGroupReview(2, thisPopLayer);
		});
		$(".j_modifyGroupSubmit").live('click',function(){//修改小组评审提交
			editAppRev.submitOrSaveModifyAppGroupReview(3, thisPopLayer);
		});
//		window.submitOrSaveAddAppReview = function(data, layer){editAppRev.submitOrSaveAddAppReview(data, layer)};
//		window.submitOrSaveModifyAppReview = function(data, layer){editAppRev.submitOrSaveModifyAppReview(data, layer)};
//		window.alertGroupOpinion = function(entityId){editAppRev.alertGroupOpinion(entityId, projectType)};
//		window.submitOrSaveAddAppGroupReview = function(data, layer){editAppRev.submitOrSaveAddAppGroupReview(data, layer)};
//		window.submitOrSaveModifyAppGroupReview = function(data, layer){editAppRev.submitOrSaveModifyAppGroupReview(data, layer)};
	};
	
//	exports.initGroupOpinion = function(projectType){
//		editAppRev.initGroupOpinion(projectType);
//	};
});