/**
 * @author 雷达、肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list')
	
	var viewApplicant = function(){
		$(function() {
			$(".view_applicant").live("click", function() {
				popPerson(this.id, 7);
				return false;
			});
		});
	};
	
	var toView = function(nameSpace){
		$(function() {
			$(".link1").die("click").live("click", function(){
				var url = basePath + nameSpace + "/toViewReview.action?entityId=" + this.id + "&pageNumber=" + $("#list_pagenumber").val();
				url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
				url += "&selectedTab=application";
				window.location.href = url;
				return false;
			});	
		});
	};
	
	exports.init = function(nameSpace) {
		$(function() {
			listProject.initListButton(nameSpace);
			list.pageShow({
				"nameSpace":nameSpace,
				"selectedTab":"application",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9}
			});
		});
	};
	exports.viewApplicant = function(){
		viewApplicant();
	}
	exports.toView = function(nameSpace){
		toView(nameSpace);
	}
});

