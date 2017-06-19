/**
 * @author liujia
 * @description 数据入库代码
 */
define(function(require, exports, module) {
	require("pop");
	var view = require('javascript/view');

	function popcreateProgress(args) {
		new top.PopLayer({
			"title": "进度条",
			"src": args.src,
			"document": top.document,
			"inData": args.inData,
			"callBack": args.callBack
		});
	}//进度条构造函数
	
	function showDetail(result){
		$("#systemOption").hide();//隐藏入库操作界面
		$("#totalImportNum").html(result.totalImportNum);
		$("#totalNum").html(result.totalNum);
		//接下来动态生成表格
		result = result.resultDetail;
		var count = 0;
		for(var element in result){
			var table = $("<table>");
			table.attr({
				"id": "tab_" + count,
				"class": "table_statistic",
				"width":"100%"
			});
			table.css({
				"text-align":"center"
			});
			
			var head = $("<tr>");
			head.addClass("table_title_tr");
			
			var td = $("<td>");
			td.html("序号");
			td.attr({
				"width":"20%"
			});
			td.appendTo(head);
			
			td = $("<td>");
			td.html("项目名称");
			td.attr({
				"width":"40%"
			});
			td.appendTo(head);
			
			td = $("<td>");
			td.html("错误原因");
			td.attr({
				"width":"40%"
			});
			td.appendTo(head);
			
			head.appendTo(table);
			
			var tab = $("<li><a></a></li>");
			$(":only-child",tab).attr({
				"href": "#tab_" + count
			}).html(element);
			tab.appendTo($("#tabs ul"));
			count ++;
			for(var i = 0 ; i < result[element].length; i ++){
				var tr = $("<tr>");

				if(i % 2 == 0){
					tr.attr({
						"class":"even"
					});
				}
				
				var td = $("<td>");
				td.html(i);
				td.appendTo(tr);
				var td = $("<td>");
				td.html(result[element][i][0]);
				td.appendTo(tr);
				var td = $("<td>");
				td.html(result[element][i][1]);
				td.appendTo(tr);
				
				tr.appendTo(table);
			}
			table.appendTo($("#container"));
		}
		view.inittabs();
		$("#importResult").show();
	}//入库详情显示函数

	var start = function(){
		var args = {
			src: "dataProcessing/dataImporter/toProgress.action",
			inData: {
				projectType: $("#search_range_tabs").val(),
				importType: $("#importType").val()
			},
			callBack: showDetail
		}

		$.ajax({
			url: "dataProcessing/dataImporter/startImport.action",
			data: "projectType=" + $("#search_range_tabs").val() + "&importType=" + $("#importType").val() + "&isStarted=1",
			type: "post"
		});

		popcreateProgress(args);

	}//开始入库函数

	exports.init = function(){
		$("#startImport").click(start);
	}

});