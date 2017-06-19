/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list');
	var project = require('javascript/project/project_share/project');
	
	var midExport = function(nameSpace){
		$(function() {
			$("#list_export").live("click", function(){
				var url = basePath + nameSpace + "/confirmExportOverView.action";
				var startDate= $("#startDate").val();
				var endDate= $("#endDate").val();
				exportData1(url, startDate, endDate);
			});
		});
	};

	
	function exportData1(url, startTime, endTime) {
		if(startTime == "") {
			startTime = "2010-01-01";
		}
		if(endTime =="") {
			endTime = "2022-07-01";
		}
		$.ajax({
			url: "validate/validateExport.action",
			type: "post",
			data: {"startTime" : startTime, "endTime" : endTime},
			dataType: "json",
			success: function(result){
				if (result.exportForbidden) {
					alert("早期数据已自动归档，暂能导出" + result.exportYear + "年1月1号至今的最新数据！");
					return false;
				} else if(result.exportNotValid) {
					if(confirm("早期数据已自动归档，暂能导出" + result.exportYear + "年1月1号至今的最新数据。是否继续？")) {
						url += "?startDate=" + result.exportStartTime + "&endDate=" + endTime; 
						location.href = url;
					}
				} else {
					url += "?startDate=" + startTime + "&endDate=" + endTime; 
					location.href = url;
				}
			}
		});
	}
	exports.init = function(nameSpace) {
		$(function() {
			listProject.initListButton(nameSpace);
			list.pageShow({
				"dealWith" :project.director,
				"nameSpace":nameSpace,
				"selectedTab":"midinspection",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11","sortcolumn12","sortcolumn13","sortcolumn14","sortcolumn15"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11,"sortcolumn12":12,"sortcolumn13":13,"sortcolumn14":14,"sortcolumn15":15}
			});
		});
	};
	//导出中检一览表
	exports.midExport = function(nameSpace) {
		midExport(nameSpace);
	};
});

