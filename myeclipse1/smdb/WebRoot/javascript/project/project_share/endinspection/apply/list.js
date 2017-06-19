/**
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var list = require('javascript/list');
	var listProject = require('javascript/project/project_share/list');
	var project = require('javascript/project/project_share/project');
	
	//打印、导出结项一览表
	var printAndExport = function(nameSpace) {
		$(function() {
			$("#list_print" ).live("click", function(){
				var index = 0
				var getPara = function(obj){
					return "?univ=" + obj["univ"] + "&univId=" + obj["univId"] + "&subType=" + 
						obj["subType"] + "&projectStatus=" + obj["projectStatus"] + "&startYear=" + obj["startYear"] + "&endYear=" + obj["endYear"] +
						"&printType=" + obj["printType"] + "&type=" + (index+1);
				};
				popProjectOperation({
					title : "打印结项一览表",
					src : nameSpace + "/printOverView.action?type=" + (index+1),
					callBack : function(result){
						var ver = Math.random();//产生一个[0，1)之间的随机数，用作标识，页面可能存在几个打印按钮，每次打印生成不同的iframe 
						var ifr_window = window.frames["print" + ver];//取的是一个完整的DOM模型
						printIframe = $("<iframe src='' name='print" + ver +"'></iframe>");//生成一个iframe窗口
						printIframe.css({
							filter:"Alpha(Opacity=0)",//滤镜
							opacity: "0",//设置透明度
							scrolling : "no", //隐藏滚动条
							vspale : "0",//页边距
							height: "0px",//设置高度
							width : "98%",
							border : "0px",
							frameBorder : "0px"
						});
						printIframe.attr("src", nameSpace + "/confirmPrintOverView.action" + getPara(result));
						$("body").append(printIframe);//将printIframe添加到body所在页面
						ifr_window = window.frames["print" + ver];
						//页面是否加载完成
						var isLoaded = false; 
						$(ifr_window).load(function(){
							isLoaded = true;
						});
						if(confirm("您确定要打印结项一览表吗？\n\n建议使用FireFox7.0以上浏览器\n打印前请进行必要页面设置：\n  纸张大小：A4\n  打印方向：横向\n  页眉页脚：空白\n  页边距（上下左右）：0")){
							//判断调用的时机，如果iframe没加载完则调用打印没效果，即空白页
							//如果在iframe的页面未完全装入的时候,调用iframe的DOM模型,会发生很严重的错误
							if(isLoaded){//已加载完毕，直接进行打印
								ifr_window.focus();//聚焦，让页面成为当前窗口
								ifr_window.print();//打印
							}
							$(ifr_window).load(function(){//等待加载完毕再打印
								ifr_window.focus();//聚焦，让页面成为当前窗口
								ifr_window.print();//打印
							});
						} else {
							printIframe.remove();//移除打印窗口
						}
					}
				});
			});
			$("#list_export").live("click", function(){
//				window.location.href = basePath + nameSpace + "/exportOverView.action";
//				window.location.href = basePath + "validate/validateExport.action";
				
				var url = basePath + nameSpace + "/exportOverView.action";
				var startDate= $(".j_startDate").val();
				var endDate= $(".j_endDate").val();
				exportData1(url, startDate, endDate);
			});
		});
	};
	
	function exportData1(url, startTime, endTime) {
		if(startTime == '--不限--') {
			startTime = "2010-01-01";
		}
		if(endTime == '--不限--') {
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
				"selectedTab":"endinspection",
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10","sortcolumn11","sortcolumn12","sortcolumn13","sortcolumn14","sortcolumn15","sortcolumn16","sortcolumn17"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10,"sortcolumn11":11,"sortcolumn12":12,"sortcolumn13":13,"sortcolumn14":14,"sortcolumn15":15,"sortcolumn16":16,"sortcolumn17":17}
			});
		});
	};
	//打印、导出结项一览表
	exports.printAndExport = function(nameSpace){
		printAndExport(nameSpace);
	}
});

