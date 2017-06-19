/*
*移动端常规及定制统计分析展现图
*@徐江伟  2013-11-01
*/
$(function(){
	var jsonMapData = $("#jsonMapData").val();//获取后天传来的JSON格式数据
	var myData = JSON.parse(jsonMapData);//将JSON格式的数据转化为javascript可以识别的数据（对象）
//	alert(jsonMapData);
	
	/*
	 * 判断垂直柱状图还是水平柱状图
	*/
	var horizontal = false;//标识，用于控制是否水平显示柱状图
	if(myData.chartType == "horizontalColumn"){
		horizontal = true;
		myData.chartType = "column";
	}else if(myData.chartType == "verticalColumn"){
		horizontal = false;
		myData.chartType = "column";
	}else{
		horizontal = false;
	};
	
//	var statistic_parm = ['项目类型=所有项目','统计指标=学科门类','统计数据=申报数','项目年度=2005年 至 2013年','高校名称=三峡大学','排序列=申报数(降序)'];
//	alert(statistic_parm.length);
//	alert(statistic_parm);
//	alert(statistic_parm[1]);
	var listData = myData.dataList;
	var listTitle = myData.listTitle;
	var statistic_parm = myData.statistic_parm;
//	alert(statistic_parm);
	iniTableList(listData,listTitle,statistic_parm);
	
	/*
	 *判断是否为饼状图 
	*/
	if(myData.chartType == "pie"){
		//如果是饼状图，则调用iniHighchartPie函数，传进对应参数，将图表初始化
		iniHighchartPie(myData.vList,myData.chartType,myData.chartTitle);
	}else{
		//如果不是饼状图，则调用iniHighchart函数，传进对应参数，将图表初始化
		iniHighchart(myData.xList,myData.yList,myData.chartType,myData.chartTitle,horizontal);
	}
});

/*
*初始化列表函数
*/
function iniTableList(listData,listTitle,statistic_parm){
	var titleString = "<h3>" + listTitle + "</h3>";//创建标题
	
	//创建定制统计分析的统计条件列表
	var customListStr = "";
	if((typeof (statistic_parm) != undefined) && statistic_parm){
		customListStr = "<table class='listTable'><tbody>";
		for(var k=0; k<statistic_parm.length;k++){
			var item = statistic_parm[k].split("=");
			customListStr += "<tr><td>" + item[0] + "</td>" + "<td>" + item[1] + "</td></tr>";
		}
		customListStr += "</tbody></table>";
	}
	
	//创建列表
	var listString = "<table class='listTable'>";
	//创建表头
	for(var i in listData[0]){
		if(i == 0){
			listString += "<tr>";
		};
		listString += "<th>" + listData[0][i] + "</th>";
		if(i == listData[0].length){
			listString += "</tr>";
		};
	};
	//创建表的内容
	for(var i=1; i<=listData.length-1; i++){
		listString += "<tr>";
		for(var j=0; j<=listData[i].length-1; j++){
			listString += "<td>" + listData[i][j] + "</td>";
		};
		listString += "</tr>";
	};
	
	//将表头、条件列表和数据列表添加到页面
	$("#listHoder").append(titleString);
	$("#listHoder").append(customListStr);
	$("#listHoder").append(listString);
};

/*
*初始化饼状图列表highcart函数
*/
function iniHighchartPie(vList,chartType,chartTitle){
	$('#highchartHolder').highcharts({
		 title: {
	            text: chartTitle,
	            margin: 10
	     },
        tooltip: {
    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
        },
        credits: {
            enabled: false
        },
    	plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        credits: {
            enabled: false
        },
        series:[{
            type: chartType,
            data: vList
        }]
	});
};

/*
*初始化水平或者垂直柱状图、折线图highchart函数
*/
function iniHighchart(xValue,yValue,chartType,chartTitle,horizontal){
    $('#highchartHolder').highcharts({
        chart: {
            type: chartType,
//            width: 100%,
            inverted: horizontal
        },
        tooltip: {
            formatter: function() {
            	//判断是否为百分数，百分数则以%形式显示，否则直接显示数据
            	if(this.y > 0 && this.y < 1){
            		return '<b>'+ this.x + '</b>:' +
                    '  <b>'+ (this.y*100).toFixed(2) +'%</b>';
            	}else{
            		return '<b>'+ this.x + '</b>:' + '  <b>'+ this.y +'</b>';
            	}
            }
        },
        credits: {
            enabled: false
        },
        legend: {
        	enabled: false
        },
        title: {
        	text: chartTitle,
            margin: 10
        },
        xAxis: {
            categories: xValue,
            title: {
            	enabled: true
            },
            labels: {
                rotation: -45
            }
        },
        yAxis: {
            title: {
            	text: null
            }
        },
        series: [{
            data: yValue
        }]
    });
};