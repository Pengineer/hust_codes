/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list');
	var project = require('javascript/project/project_share/project');
	
	exports.init = function(nameSpace) {
		$(function() {
			listProject.initListButton(nameSpace);
			list.pageShow({
				"dealWith" :project.director,
				"nameSpace":nameSpace,
				"selectedTab":"anninspection",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11","sortcolumn12","sortcolumn13","sortcolumn14","sortcolumn15"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11,"sortcolumn12":12,"sortcolumn13":13,"sortcolumn14":14,"sortcolumn15":15}
			});
		});
	};
});

