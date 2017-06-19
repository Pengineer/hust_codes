/*
 * 2014.03.07  创建文件   生成高校专家使用情况图表
*/
$(function(){
	//取出页面后台传来的参数值
	var chartInitData = $('#chartData').val();
	var chartInitDataAve = $('#chartDataAve').val();
	var chartInitDataStd = $('#chartDataStd').val();
	
	
	
	//对平均值和图表数据进行预处理
	var afterProcessAveData = preprocessAveData(chartInitDataAve);
	var afterProcessData = preprocessChartData(chartInitData);
	
	//将学校专家匹配情况和学校专家使用情况平均值放到对应数组里
	var universityAveData = [afterProcessAveData[5],afterProcessAveData[6],afterProcessAveData[7],afterProcessAveData[8],afterProcessAveData[9]];
	var expertAveData = [afterProcessAveData[0],afterProcessAveData[1],afterProcessAveData[2],afterProcessAveData[3],afterProcessAveData[4]];
	
	//创建数组，将每一年的专家及学校使用情况放到对应的数组里
	var expertData2010 = afterProcessData[0],expertData2011 = afterProcessData[1], expertData2012 = afterProcessData[2], expertData2013 = afterProcessData[3], expertData2014 = afterProcessData[4];
	var universityUseData2010 = afterProcessData[5],universityUseData2011 = afterProcessData[6], universityUseData2012 = afterProcessData[7], universityUseData2013 = afterProcessData[8], universityUseData2014 = afterProcessData[9];
	
	//取出页面放置图表的holder
	var expertHighchartHolder = $('#expertHighchartHolder');
	var universityHighchartHolder = $('#universityHighchartHolder');
	
	//对图表进行初始化
	iniHighchart(expertHighchartHolder,expertAveData,'高校专家配对情况',expertData2010,expertData2011,expertData2012,expertData2013,expertData2014);
	iniHighchart(universityHighchartHolder,universityAveData,'高校专家使用情况',universityUseData2010,universityUseData2011,universityUseData2012,universityUseData2013,universityUseData2014);
});


/*
 * 处理平均值
*/
var preprocessAveData = function(chartInitDataAve){
	var chartInitDataAveStrArray = chartInitDataAve.substring(0,chartInitDataAve.length-1).substring(1).split(',');
	var chartAfterDataAveStrArray =[];
	for(var i=1; i<chartInitDataAveStrArray.length;i++){
		chartInitDataAveStrArray[i] = parseFloat(chartInitDataAveStrArray[i]);
		if(isNaN(chartInitDataAveStrArray[i])){
			chartInitDataAveStrArray[i] = 0;
		}
		chartAfterDataAveStrArray[i-1] = parseFloat(chartInitDataAveStrArray[i]);
	};
	return chartAfterDataAveStrArray;
};





/*
 * 对后台传来的数据进行一个预处理，将每一年的专家及学校专家使用情况都取出来放到对应的数组里
*/
var preprocessChartData = function(chartInitData){
	var data1 = [],data2 = [], data3 = [], data4 = [],data5 = [],data6 = [],data7 = [],data8 = [];
	var chartDataString = chartInitData.substring(1);
	var chartDataString1 = chartDataString.substring(0,chartDataString.length-2);
	var chartDataString2 = chartDataString1.replace(/\[/g,'');
	var chartDataArray = chartDataString2.split('],');
	
	for(var i=0; i < chartDataArray.length;i++){
		var chartDataSubArray =  chartDataArray[i].split(',');
		for(var j=1; j < chartDataSubArray.length;j++){
			chartDataSubArray[j] = parseFloat(chartDataSubArray[j]);
			//将非数字字符替换为0
			if(isNaN(chartDataSubArray[j])){
				chartDataArray[j] = 0;
			};
		};
		data1[i] = chartDataSubArray[1];
		data2[i] = chartDataSubArray[2];
		data3[i] = chartDataSubArray[3];
		data4[i] = chartDataSubArray[4];
		data5[i] = chartDataSubArray[5];
		data6[i] = chartDataSubArray[6];
		data7[i] = chartDataSubArray[7];
		data8[i] = chartDataSubArray[8];
	};
	
	
	/*
	 *对所有数据进行排序 
	*/
	var data11 = data1.sort(function(a,b){return a-b;});
	var data22 = data2.sort(function(a,b){return a-b;});
	var data33 = data3.sort(function(a,b){return a-b;});
	var data44 = data4.sort(function(a,b){return a-b;});
	var data55 = data5.sort(function(a,b){return a-b;});
	var data66 = data6.sort(function(a,b){return a-b;});
	var data77 = data7.sort(function(a,b){return a-b;});
	var data88 = data8.sort(function(a,b){return a-b;});
	var aferProcessData = [data11,data22,data33,data44,data55,data66,data77,data88];
	return aferProcessData;
};

/*
 * 对图表进行初始化
*/
var iniHighchart = function(highchartHolder,aveData,title,data1,data2,data3,data4,data5){
	
	/*
	 *下面将每年的平均值扩充为对应长度，形成图中平均值线的数据 
	*/
	var aveData1 = [],aveData2 = [], aveData3 = [], aveData4 = [], aveData5 = [];
	for(var i = 0 ;i < 5; i++){
		if(i == 0){
			for(var j = 0; j<data1.length; j++){
				aveData1[j] = aveData[i]; 
			}
		}else if (i == 1){
			for(var j = 0; j<data1.length; j++){
				aveData2[j] = aveData[i]; 
			}
		}else if (i == 2){
			for(var j = 0; j<data1.length; j++){
				aveData3[j] = aveData[i]; 
			}
		}else if (i == 3){
			for(var j = 0; j<data1.length; j++){
				aveData4[j] = aveData[i]; 
			}
		}else if(i == 4){
			for(var j = 0; j<data1.length; j++){
				aveData5[j] = aveData[i]; 
			}
		}
		
	};
	Highcharts.setOptions({
		lang: {
            resetZoom: '恢复'
		}
	});
	highchartHolder.highcharts({
		chart: {
            type: 'line',
            zoomType: 'xy',
            resetZoomButton: {
                position: {
                    x: 0,
                    y: -30
                }
            }
        },
        title: {
            text: title
        }, 
        tooltip: {
            formatter: function() {
            	return '<b>数据：' + this.x + '</b>' + '<br>' + '<b>' + this.series.name + '：' + this.y + '</b>';
            }
        },
        credits: {
            enabled: false
        },
        /*legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },*/
        yAxis:{
        	 title: {
            	text: null
            }
        },
        /*yAxis: {
            plotLines: [{
            	color: '#FF0000',
                width: 2,
                value: aveData[0],
                label: {
                	text: '2010年平均值'
                }
            },{
            	color: '#FF00EE',
                width: 2,
                value: aveData[1],
                label: {
                	text: '2011年平均值'
                }
           },{
            	color: '#FFEE00',
                width: 2,
                value: aveData[2],
                label: {
                	text: '2012年平均值'
                }
           },{
            	color: '#BB0000',
                width: 2,
                value: aveData[3],
                label: {
                	text: '2013年平均值'
                }
           }]
        },*/
        plotOptions: {
            series: {
                marker: {
                    radius: 1
                }
            }
        },
        series: [{
            name: '2010年',
            data: data1
        }, {
            name: '2011年',
            data: data2
        }, {
            name: '2012年',
            data: data3
        }, {
            name: '2013年',
            data: data4
        },{
            name: '2014年',
            data: data5
        },{
        	name: '2010年平均值',
        	data: aveData1
        },{
        	name: '2011年平均值',
        	data: aveData2
        },{
        	name: '2012年平均值',
        	data: aveData3
        },{
        	name: '2013年平均值',
        	data: aveData4
        },{
        	name: '2014年平均值',
        	data: aveData5
        }]
    });
}