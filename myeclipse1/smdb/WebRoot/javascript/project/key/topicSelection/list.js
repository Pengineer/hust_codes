/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list');
	var timeValidate = require('javascript/project/project_share/validate');
	var project = require('javascript/project/project_share/project');
	
	var nameSpace = "project/key/topicSelection/apply";
	
	var topsAdd = function(){
		$(function() {
			$("#list_add").die("click").live("click", function() {
				if($("#topsStatus").val()== 0){
					alert("该业务已停止，无法进行此操作！");
					return false;
				}
				var flag = timeValidate.timeValidate($("#deadline").val());
				if(flag){
					var url = basePath + nameSpace + "/toAdd.action"
					if(list.viewParam != null && list.viewParam.listflag != undefined){
						url += "&listflag=" + list.viewParam.listflag;
					}
					window.location.href = url;
				}else{
					return false;
				}
			});
		});
	};
	
	var topsDelete = function() {
		$(function() {
			$("#list_delete").die("click").live("click", function() {
				var cnt = count("topsIds");
				if (cnt == 0) {
					alert("请选择要删除的记录！");
				} else {
					if (confirm("您确定要删除选中的记录吗？")) {
						$("#type").attr("value", 1);
						$("#pagenumber").attr("value", $("#list_goto").val());
						$("#list").attr("action", nameSpace + "/delete.action");
						$("#list").submit();
					}
				}
				return false;
			});
		});
	};
	
	var toView = function() {
		$(function() {
			$(".link1").die("click").live("click", function(){
				var url = basePath + nameSpace + "/toView.action?topsId=" + this.id;
				url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
//			url += (list.selectedTab != null) ? "&selectedTab=" + list.selectedTab : "";
				url += "&selectedTab=topicSelection";
				if(list.viewParam != null && list.viewParam.listflag != undefined){
					url += "&listflag=" + list.viewParam.listflag;
				}
				window.location.href = url;
				return false;
			});
		});
	};
	
	exports.init = function() {
		$(function() {
			listProject.initListButton(nameSpace);
			list.pageShow({
				"dealWith" :project.director,
				"nameSpace":nameSpace,
				"selectedTab":"topicSelection",
				"addUrl":nameSpace + "/toAdd.action",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11","sortcolumn12","sortcolumn13"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11,"sortcolumn12":12,"sortcolumn13":13}
			});
		});
		topsAdd();
		topsDelete();
		toView();
	};
	
});