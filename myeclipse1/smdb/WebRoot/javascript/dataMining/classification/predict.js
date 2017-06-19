/**
 * 关联分析
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/highcharts/highcharts');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "dataMining/classification";
	
	// 初始化
	exports.init = function() {

		// 异步获取图表数据
		$.ajax({
			url: nameSpace + "/predict.action",
			data: "predictType=" + $("#predictType").val() + "&predictYear=" + $("#predictYear").val() + "&toDataBase=" + $("#toDataBase").val(),
 			type: "post",
 			dataType: "json",
 			success: function(result) {
 				if (result.errorInfo == null || result.errorInfo == "") {
 					initHighcharts(result);
 				} else {
 					alert(result.errorInfo);
 				}
 				return false;
 			}	
		});

		// 列表初始化
		list.pageShow({
			"nameSpace": nameSpace,
			"sortColumnId": ["sortcolumn0", "sortcolumn1", "sortcolumn2"],
			"sortColumnValue": {"sortcolumn0": 0, "sortcolumn1": 1, "sortcolumn2": 2}
		});
		
		// 弹出层项目查看：根据项目申请ID查看项目
		$(".linkP").live("click", function() {
			//根据预测类型组装项目类型 general_end -> generalByAppId
			var predictType = $("#predictType").val(); //例如：general_end
			var projectType = predictType.substring(0, predictType.length - 4) + "ByAppId";//由general_end修改为generalByAppId
			popProject(this.id, projectType);
			return false;
		});
	};
	
	// 绘制图形
	function initHighcharts(json) {
//		Highcharts.setOptions({
//			lang: {
//				resetZoom : '还原'
//			}
//		});
		
		$('#graphHolder').highcharts({
			chart: {
				zoomType: 'xy',
                type: 'line'				//图表类型
            },
            title: {
                text: json.title,			//图表标题
                x: -20 //center
            },
            subtitle: {
                text: json.subTitle,		//图表子标题
                x: -20
            },
            xAxis: {
                categories: json.categories	//x轴分类坐标
            },
            yAxis: {
                title: {
                    text: json.yAxisTitle	//y轴标题
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '项'			//提示框，显示信息后缀
            },
            legend: {						//图表说明
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            plotOptions: {
                series: {
                    lineWidth: 1,
                    point: {
                        events: {
                            'click': function() {
                                if (this.series.data.length > 1) alert(this.series.data);
                            }
                        }
                    }
                }
            },
            series: [{						//图表数据
                name: json.line1Name,		//图表1名称
                data: json.line1Data		//图表1y轴数据
            }, {
                name: json.line2Name,		//图表2名称
                data: json.line2Data		//图表2y轴数据
            }]
        });
	}
});