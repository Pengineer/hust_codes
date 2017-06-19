/**
 * @author 刘雅琴、肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list');
	var project = require('javascript/project/project_share/project');
	
	//导出立项一览表
	/*var graExport = function(nameSpace){
		$(function() {
			$("#list_export").live("click", function(){
				var getPara = function(obj){
					return "?univ=" + obj["univ"] + "&univId=" + obj["univId"] + "&subType=" + 
							obj["subType"] + "&projectStatus=" + obj["projectStatus"] + "&startYear=" + obj["startYear"] + "&endYear=" + obj["endYear"];
				};
				popProjectOperation({
					title : "导出立项一览表",
					src : nameSpace + "/exportOverView.action" ,
					callBack : function(result){
						window.location.href = basePath + nameSpace + "/confirmExportOverView.action" + getPara(result);
//						var url = basePath + nameSpace + "/confirmExportOverView.action" + getPara(result);
//						exportData(url);
						this.destroy();
					}
				});
			});
		});
	};*/
	
	//将高级检索和导出功能合二为一
	var graExport = function(nameSpace){
		$(function() {
			$("#list_export").live("click", function(){
				window.location.href = basePath + nameSpace + "/confirmExportOverView.action";
//				var expFlag = $("#expFlag").val();
//				if(expFlag == 1){
//					window.location.href = basePath + nameSpace + "/confirmExportOverView.action";
//				} else {
//					if (confirm("您还未输入任何高级检索条件，是否继续导出？")) {
//						window.location.href = basePath + nameSpace + "/confirmExportOverView.action";
//					}
//				}
			});
		});
	};
	
	
	exports.init = function(nameSpace) {
		$(function() {
			listProject.initListButton(nameSpace);
			list.pageShow({
				"dealWith" :project.director,
				"nameSpace":nameSpace,
				"selectedTab":"granted",
				"addUrl":nameSpace + "/toAdd.action",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8}
			});
		});
	};
	exports.graExport = function(nameSpace) {
		graExport(nameSpace);
	};
});

