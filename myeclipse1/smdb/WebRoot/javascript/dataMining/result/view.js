/**
 * 关联分析
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/highcharts/highcharts');
	require('tool/d3/d3.v3');
	
	// 初始化
	exports.init = function(nameSpace) {
		//下一条
		$("#view_next").bind("click", function() {
			$("#view_list").attr("action", nameSpace + "/next.action").submit();
			return false;
		});
		//上一条
		$("#view_prev").bind("click", function() {
			$("#view_list").attr("action", nameSpace + "/prev.action").submit();
			return false;
		});
		//删除
		$("#view_del").bind("click", function() {
			if (!confirm("您确定要删除此条挖掘结果吗？")) return;
			$.ajax({
				url: nameSpace + "/delete.action",
				type: "post",
				data: "entityIds=" + $("#entityId").val(),
				dataType: "json",
				success: function(result) {
					if (result.errorInfo == null || result.errorInfo == "") {
						window.location.href = basePath + nameSpace + "/toList.action?update=1&entityId=" + $("#entityId").val();
						return false;
					} else {
						alert(result.errorInfo);
					}
				}
			});
			return false;
		});
		
		//返回
		$("#view_back").bind("click", function() {
			window.location.href = basePath + nameSpace + "/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
			return false;
		});
		
		// 异步获取图表数据
		$.ajax({
			url: nameSpace + "/view.action",
			data: "entityId=" + $("#entityId").val(),
 			type: "post",
 			dataType: "json",
 			success: function(result) {
 				if (result.errorInfo == null || result.errorInfo == "") {
 					if(result.graphType == 'Highcharts'){
 						initHighcharts(result);
 					} else if(result.graphType == 'D3_pack'){
 						initD3_pack(result);
 					} else if(result.graphType == 'D3_force'){
 						initD3_force(result);
 					}
 				} else {
 					alert(result.errorInfo);
 				}
 				return false;
 			}	
		});
		
	};
	
	// 绘制图形
	function initHighcharts(json) {
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
	
	function initD3_pack(json){
		//如果没有数据，直接返回
		if(json.children.length == 0){
			return false;
		}
		
		//bubble图参数初始化
		var width = 990, height = 800;	//画布宽高
		
		var format = d3.format(",d"); 		//格式化
		
		var color = d3.scale.category20c();	//颜色设置
		
		//图形布局
		var bubble = d3.layout.pack()
			.sort(null)
			.size([width, height])
			.padding(1.5);
		
		//定义svg标签，用于存放图形
		var svg = d3.select("#graphHolder").append("svg")	
			.attr("width", width)
			.attr("height", height)
			.attr("class", "bubble")
			.style("background", "#FFFFE1");
		
		//节点定义
		var node = svg.selectAll(".node")
			.data(bubble.nodes(classes(json))
			.filter(function(d) {return !d.children;}))
			.enter().append("g").attr("class", "node")
			.attr("transform", function(d) {return "translate(" + d.x + "," + d.y + ")";})
			.style("fill", function(d) { return color(d.className); })
			.on("mouseover", function(d) {d3.select(this).style("fill", function(d) { return "red"; })})
			.on("mouseout", function(d) {d3.select(this).style("fill", function(d) { return color(d.className); })});
		
		//给节点添加圆			
		node.append("circle")
			.attr("r", function(d) { return d.r; });
		
		//文字描述
		node.append("text")
			.attr("dy", ".3em")
			.style("text-anchor", "middle").style("fill","black")
			.text(function(d) { return d.className.substring(0, d.r / 3); });

		//显示提示
		node.append("title")
	      	.text(function(d) { return d.className + ": 共" + format(d.value) + "项"; });
		
	}
	
	/**
	 * 解析返回的json数据，生成节点
	 */
	function classes(json) {
		var classes = [];
		//递归的解析数据
		function recurse(name, node) {
		    if (node.children) node.children.forEach(function(child) { recurse(node.name, child); });
		    else classes.push({packageName: name, className: node.name, value: node.size});
		}

		recurse(null, json);
		return {children: classes};
	}
	
	function initD3_force(json){

		//如果没有数据，直接返回
		if(json.nodes.length == 0){
			return false;
		}
		
		var width = 990, height = 700;	//画布宽高
		
		var color = d3.scale.category20();//颜色设置
		
		var r1 = 10, r2 = 15;	//圆的半径
		
		var force = d3.layout.force()
//			.gravity(1)				//重心引力
		    .charge(-170)			//不同树之间的间隔（值越小相隔越远）
		    .linkDistance(100)		//连接线的距离（值越大相隔越长）
		    .size([width, height]);
		
		var svg = d3.select("#graphHolder").append("svg")
		    .attr("width", width)
		    .attr("height", height)
		    .style("background", "#FFFFE1");
		
		//加载数据
		force.nodes(json.nodes)
			.links(json.links)
			.start();
	
		//定义line连接线
		var link = svg.selectAll(".link")
	      	.data(json.links).enter()
	      	.append("line").attr("class", "link")
	      	.style("stroke-width", function(d) { return Math.sqrt(d.value); })
			.style("stroke", "#999").style("stroke-opacity", "0.6");
	    
      	link.append("title").text(function(d) { return "权值：" + d.value; });
      
		//定义节点
		var node = svg.selectAll(".node")
  			.data(json.nodes).enter()
  			.append("g").attr("id", function(d) { return d.name }).attr("class", "node")
  			.style("stroke-width", "1.5px");
		
		//给节点添加圆
		node.append("circle").attr("id",function(d) { return "c_"+d.name })
  			.attr("r", r1)	//半径
  			.style("fill", function(d) { return color(d.name); })
  			.style("stroke", "#FFF").style("stroke-width", 2)
  			.on("mouseover", function(d) {d3.select(this).attr("r", r2); d3.selectAll("#t_" + getIdName(d.name)).style("font-size", "18px").style("font-weight", "bold").style("fill", "red");})
  			.on("mouseout", function(d) {d3.select(this).attr("r", r1); d3.selectAll("#t_" + getIdName(d.name)).style("font-size", "12px").style("font-weight", "").style("fill", "black");});

		//给节点添加拖动事件
		node.call(force.drag);
		
		//给节点添加title提示
		node.append("title")
	      	.text(function(d) { return d.name + "\n共[" + d.value + "]条关联"; });
		
		//给节点添加文本显示
		node.append("text").attr("id",function(d) { return "t_" + getIdName(d.name) })
		  	.attr("x", "5").attr("dy", ".35em")
		  	.style("fill", "black").style("font-size", "12px")
		  	.text(function(d) { return d.name; });
						
		//tick标记事件
	  	force.on("tick", function() {
	  		//更新连接线
	    	link.attr("x1", function(d) { return d.source.x; })
	        	.attr("y1", function(d) { return d.source.y; })
	       	 	.attr("x2", function(d) { return d.target.x; })
	       	 	.attr("y2", function(d) { return d.target.y; });
	    	
	    	node.attr("transform", function(d) {return "translate(" + d.x + "," + d.y + ")";});
	    });
	  	
	  	/**
	  	 * 生成text的id名
	  	 */
	  	function getIdName(name){
	  		var endIndex = name.indexOf("/"); 
	  		if(endIndex > 0){
	  			return name.substring(0, endIndex);
	  		}else{
	  			return name;
	  		}
	  	}
	}
});
