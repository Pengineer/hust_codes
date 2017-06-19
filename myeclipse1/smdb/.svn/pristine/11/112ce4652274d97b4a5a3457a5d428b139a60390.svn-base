/**
 * @author 刘雅琴、肖雅
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list')
	var timeValidate = require('javascript/project/project_share/validate');
	var project = require('javascript/project/project_share/project')
	
	var add = function(nameSpace){
		$(function() {
			$("#list_add").die("click").live("click", function() {
				if($("#appStatus").val()== 0){
					alert("该业务已停止，无法进行此操作！");
					return false;
				}
				var flag = timeValidate.timeValidate($("#deadline").val());
				if(flag){
					var url = basePath + nameSpace + "/toAdd.action"
					if(listProject.viewParam != null && listProject.viewParam.listflag != undefined){
						url += "&listflag=" + viewParam.listflag;
					}
					window.location.href = url;
				}else{
					return false;
				}
			});
		});
	};
	
	exports.init = function(nameSpace) {
		$(function() {
			listProject.initListButton(nameSpace);
			list.pageShow({
				"dealWith" :project.director,
				"nameSpace":nameSpace,
				"selectedTab":"application",
				"addUrl":nameSpace + "/toAdd.action",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11","sortcolumn12","sortcolumn13","sortcolumn14","sortcolumn15","sortcolumn16","sortcolumn17"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11,"sortcolumn12":12,"sortcolumn13":13,"sortcolumn14":14,"sortcolumn15":15,"sortcolumn16":16,"sortcolumn17":17}
			});
		});
	};
	exports.add = function(nameSpace) {
		add(nameSpace);
	};
});

