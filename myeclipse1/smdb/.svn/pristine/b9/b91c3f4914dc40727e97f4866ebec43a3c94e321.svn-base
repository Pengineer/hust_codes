/**
 * @author 雷达、肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list');
	var project = require('javascript/project/project_share/project');
	
	var viewApplicant = function(){
		$(function() {
			$(".view_applicant").live("click", function() {
				popPerson(this.id, 7);
				return false;
			});
		});
	};
	
	//打印、导出结项一览表
	var printAndExport = function(nameSpace){
		$(["list_print", "list_export"]).each(function(index){
			$("#" + this).live("click", function(){
				var getPara = function(obj){
					return "?univ=" + obj["univ"] + "&univId=" + obj["univId"] + "&subType=" + 
						obj["subType"] + "&startYear=" + obj["startYear"] + "&endYear=" + obj["endYear"] +
						"&printType=" + obj["printType"] + "&type=" + (index+1);
				};
				var args = 
					[{
						title : "打印结项一览表",
						src : nameSpace + "/printOverView.action?type=" + (index+1),
						callBack : function(result){
							var ver = Math.random();
							var ifr_window = window.frames["print" + ver];
							printIframe = $("<iframe src='' name='print" + ver +"'></iframe>");
							printIframe.css({
								filter:"Alpha(Opacity=0)",
								opacity: "0",
								scrolling : "no",
								vspale : "0",
								height: "0px",
								width : "98%",
								border : "0px",
								frameBorder : "0px"
							});
							printIframe.attr("src", nameSpace + "/confirmPrintOverView.action" + getPara(result));
							$("body").append(printIframe);
							ifr_window = window.frames["print" + ver];
							var isLoaded = false;
							$(ifr_window).load(function(){
								isLoaded = true;
							});
							if(confirm("您确定要打印结项一览表吗？\n\n建议使用FireFox7.0以上浏览器\n打印前请进行必要页面设置：\n  纸张大小：A4\n  打印方向：横向\n  页眉页脚：空白\n  页边距（上下左右）：0")){
								if(isLoaded){
									ifr_window.focus();
									ifr_window.print();
								}
								$(ifr_window).load(function(){
									ifr_window.focus();
									ifr_window.print();
								});
							} else {
								printIframe.remove();
							}
						}
					},
					{
						title : "导出结项一览表",
						src : nameSpace + "/printOverView.action?type=" + (index+1),
						callBack : function(result){
							//window.location.href = basePath + "project/general/endinspection/apply/confirmPrintOverView.action" + getPara(result);
							var url = basePath + nameSpace + "/confirmPrintOverView.action" + getPara(result);
							exportData(url, result["startDate"], result["endDate"]);
					}
					}];
				popProjectOperation(args[index]);
			});
		});
	};
	
	exports.init = function(nameSpace) {
		$(function() {
			listProject.initListButton(nameSpace);
			list.pageShow({
				"dealWith":project.director,
				"nameSpace":nameSpace,
				"selectedTab":"endinspection",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11","sortcolumn12","sortcolumn13","sortcolumn14","sortcolumn15","sortcolumn16","sortcolumn17"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11,"sortcolumn12":12,"sortcolumn13":13,"sortcolumn14":14,"sortcolumn15":15,"sortcolumn16":16,"sortcolumn17":17}
			});
		});
	};
	exports.viewApplicant = function(){
		viewApplicant();
	}
	exports.printAndExport = function(nameSpace){
		printAndExport(nameSpace);
	};
});

