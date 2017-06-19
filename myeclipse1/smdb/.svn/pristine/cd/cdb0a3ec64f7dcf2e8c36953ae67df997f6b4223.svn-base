/**
 * highCharts画图
 * @param json
 * @returns
 */
//垂直柱图
var initHighchartsColumn = function(json) {
	$('#container').highcharts({
		chart : {
			type : 'column'
		},
		title : {
			text : null
		},
		xAxis : {
			categories : json.lTitle
		},
		yAxis : {
			min : 0,
			title : {
				text : null
			}
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			/*series : {
				pointWidth : 30
			},*/
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : [ {
			name : '总数',
			data : json.lData
		// 图表1y轴数据
		} ]
	});
};

//水平柱图
var initHighchartsBar = function(json) {
	$('#container').highcharts({
		chart : {
			type : 'bar'
		},
		title : {
			text : null
		},
		xAxis : {
			categories : json.lTitle
		},
		yAxis : {
			min : 0,
			title : {
				text : null
			}
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			bar : {
				dataLabels : {
					enabled : true
				}
			}
		},
		series : [ {
			name : '总数',
			data : json.lData
		// 图表1y轴数据
		} ]
	});
};


// 饼状图
var initHighchartsPlot = function(json) {
	var numbers =json.numbers;
	$('#container').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
			},
		title : {
			text : null
		},
		tooltip : {
			pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f} %'
				}
			}
		},
		series : [ {
			type: 'pie',
			name : '比例',
			data : [['访问系统功能模块次数', json.number], ['访问系统功能模块次数', numbers[0]], ['访问系统安全模块次数', numbers[1]], ['访问人员数据模块次数', numbers[2]], 
			        ['访问机构数据模块次数', numbers[3]], ['访问项目数据模块次数', numbers[4]], ['访问成果数据模块次数', numbers[5]], ['访问奖励数据模块次数', numbers[6]]]
		} ]
	});
};

//折线图
var initHighcharts = function(json) {
	$('#container').highcharts({
		title : {
			text : null
		},
		xAxis : {
			categories : json.lTitle
		},
		yAxis : {
			title : {
				text : null
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		series : [ {
			name : '总数',
			data : json.lData
		// 图表1y轴数据
		} ]
	});
};