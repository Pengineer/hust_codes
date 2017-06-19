/**
 * 关联分析
 */
define(function(require, exports, module) {
	require('tool/d3/d3.v3');
	
	var nameSpace = "dataMining/association";
	
	// 初始化
	exports.init = function() {
		//请求URL
		var url = nameSpace + "/analyze.action";
		
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
		
		/**
		 * 请求获取数据，并进行图形展示
		 */
		d3.json(url, function(error, graph) {
			//如果没有数据，直接返回
			if(graph.nodes.length == 0){
				return false;
			}
			
			// 添加TOP K列表
			addList(graph.dataList);
			
			//加载数据
			force.nodes(graph.nodes)
				.links(graph.links)
				.start();
		
			//定义line连接线
			var link = svg.selectAll(".link")
		      	.data(graph.links).enter()
		      	.append("line").attr("class", "link")
		      	.style("stroke-width", function(d) { return Math.sqrt(d.value); })
		      	.style("stroke", "#999").style("stroke-opacity", "0.6");
			
			//给line添加title提示
			link.append("title").text(function(d) { return "权值：" + d.value; });
	      
			//定义节点
			var node = svg.selectAll(".node")
	  			.data(graph.nodes).enter()
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
			node.append("title").text(function(d) { return d.name + "\n共[" + d.value + "]条关联"; });
			
			//给节点添加文本显示
			node.append("text").attr("id",function(d) { return "t_" + getIdName(d.name) })
		  		.attr("x", "5").attr("dy", ".35em")
		  		.style("fill", "black").style("font-size", "12px")
		  		.text(function(d) { return d.name; });
			
			//更新节点的坐标信息
			var updateNode = function() {
				this.attr("transform", function(d) {
					return "translate(" + d.x + "," + d.y + ")";
				});

			}
			
			//tick标记事件
		  	force.on("tick", function() {
		  		//更新连接线
		    	link.attr("x1", function(d) { return d.source.x; })
		        	.attr("y1", function(d) { return d.source.y; })
		       	 	.attr("x2", function(d) { return d.target.x; })
		       	 	.attr("y2", function(d) { return d.target.y; });
		    	
		    	//更新节点
		    	node.call(updateNode);
		  	});
			
			//导出
			$("#view_export").live("click", function() {
				if (!confirm("您确定要导出此结果吗？")) return false;
				window.location.href = basePath + nameSpace + "/exportExcel.action";
				return false;
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
		});

		/**
		 * 添加列表数据
		 */
		function addList(jsonDataList){
			var datas = jsonDataList;//取数组
			var number = datas.length - 1;
			$(".title_statistic").append("TOP " + number + " 结果");
			var content = '';
			for(var i = 0; i < datas.length; i++){
				if(i == 0){
					content += '<tr class="first">';
				}else if(i % 2 == 0){
					content += '<tr class="even">';
				}else{
					content += '<tr>';
				}
				var data = datas[i];
				content += "<td>" + ((i==0) ? data[0] : i) + "</td>";
				for(var j = 1; j < data.length; j++){
					content += "<td>" + data[j] + "</td>";
				}
				content += "</tr>";
			}
			$("#dataList").html(content);
		}
	};
});