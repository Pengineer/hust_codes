$(function () {  
	var name = $('#name').val();
	var total = parseInt($('#total').val(),10);
	var  cpuSeries = $('#cpuSeries').val().split(" ");
	var  memorySeries = $('#memorySeries').val().split(" ");
	
       $('#cpuRate').highcharts({
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function() {                       	
                        var series = this.series[0];
                        setInterval(function() {
                        	$.get('sm/cluster/monitor/tomcat/update/'+name,function(r){
                    			var x = (new Date()).getTime(), // current time
                                y = r.data.p.cpu;
                           		series.addPoint([x, y], true, true);
                    		});                          
                        }, 5000);
                    }
                }
            },
            title: {
                text: 'CPU使用情况'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: '占用率'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: name,
                data: (function() {
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
                    for (i = 1-total; i <= 0; i++) {
                        data.push({
                            x: time + i * 5000,
                            y: parseFloat(cpuSeries[19+i])
                        });
                    }
                    return data;
                })()
            }]
        });   
        //内存使用率
        
        $('#memoryRate').highcharts({
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function() {                       	
                        var sery = this.series[0];
                        setInterval(function() {
                        	$.get('sm/cluster/monitor/tomcat/update/'+name,function(r){
                    			var x = (new Date()).getTime(), // current time
                                y = r.data.p.memoryRate;
                           		sery.addPoint([x, y], true, true);
                    		});    
                        	
                        }, 5000);
                    }
                }
            },
            title: {
                text: '内存使用情况'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: '占用率'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: name,
                data: (function() {
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
                    for (i = 1-total; i <= 0; i++) {
                        data.push({
                            x: time + i * 5000,
                            y: parseFloat(memorySeries[19+i])
                        });
                    }
                    return data;
                })()
            }]
        });   

});