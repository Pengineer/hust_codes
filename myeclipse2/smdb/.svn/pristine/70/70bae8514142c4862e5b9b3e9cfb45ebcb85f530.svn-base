define(function(require, exports, module) {
	require('tool/d3/d3.v3');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	// 初始化
	exports.init = function(nameSpace) {
		
		// 弹出层项目查看：根据项目申报ID查看项目
		$(".linkP").live("click", function() {
			var projectType = $("#type").val(); //例如：general
			var analyzeAngle = $("#analyzeAngle").val();;
			if(analyzeAngle == 0){
				popProject(this.id, projectType + "ByAppId");
			}else{
				popProject(this.id, projectType);
			}
			
			return false;
		});
		
		//请求URL
		var url = nameSpace + "/hotspot.action";
		
		/**
		 * 请求获取数据，并进行图形展示
		 * root就是请求返回的数据
		 */
		d3.json(url, function(error, root) {
			//如果没有数据，直接返回
			if(root.children.length == 0){
				return false;
			}
			
			// 添加列表
			addList(root.list);
			
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
				.data(bubble.nodes(classes(root))
				.filter(function(d) {return !d.children;}))
				.enter().append("g").attr("class", "node")
				.attr("transform", function(d) {return "translate(" + d.x + "," + d.y + ")";})
				.style("fill", function(d) { return color(d.className); })
				.on("mouseover", function(d) {d3.select(this).style("fill", function(d) { return "red"; }); showToolTip("主题：" + d.className+"<br>研究"+d.value +"项",d.x+d3.mouse(this)[0]+50,d.y+d3.mouse(this)[1],true);})//添加鼠标事件
		        .on("mouseout", function(d) {d3.select(this).style("fill", function(d) { return color(d.className); }); showToolTip(" ",0,0,false);})//添加鼠标事件
		        .on("mousemove", function(d) {tooltipDivID.css({top:d.y+d3.mouse(this)[1],left:d.x+d3.mouse(this)[0]+50});})	//添加鼠标事件	
		        .on("click", function(d) { click(d.className); });	//添加点击事件
			
			//给节点添加圆			
			node.append("circle")
				.attr("r", function(d) { return d.r; });
			
			//文字描述
			node.append("text")
				.attr("dy", ".3em")
				.style("text-anchor", "middle").style("fill","black")
				.text(function(d) { return d.className.substring(0, d.r / 3); });

//			//显示提示
//			node.append("title")
//		      	.text(function(d) { return d.className + ": " + format(d.value); });
			
//			d3.select(self.frameElement).style("height", diameter + "px");
		});

		/**
		 * 解析返回的json数据，生成节点
		 */
		function classes(root) {
			var classes = [];
			//递归的解析数据
			function recurse(name, node) {
			    if (node.children) node.children.forEach(function(child) { recurse(node.name, child); });
			    else classes.push({packageName: name, className: node.name, value: node.size});
			}

			recurse(null, root);
			return {children: classes};
		}
		
		/**
		 * 单击事件，更新列表数据
		 */
		function click(keyword) {
			$("#keyword").val(keyword);
			$("#search").attr("action", nameSpace + "/simpleSearch.action");
			$("#search").submit();
		}
		
		/**
		 * 自定义提示显示方式
		 */
		function showToolTip(pMessage, pX, pY, pShow)
		{
		  if (typeof(tooltipDivID)=="undefined")
		  {
		       tooltipDivID =$('<div id="msgToolTipDiv" style="position:absolute;display:block;z-index:10000;border:2px solid black;background-color:rgba(0,0,0,0.8);margin:auto;padding:3px 5px 3px 5px;color:white;font-size:12px;font-family:arial;border-radius: 5px;vertical-align: middle;text-align: center;min-width:50px;overflow:auto;"></div>');

				$("#graphHolder").append(tooltipDivID);
		  }
		  if (!pShow) { tooltipDivID.hide(); return;}
		  tooltipDivID.html(pMessage);
		  tooltipDivID.css({top:pY,left:pX});
		  tooltipDivID.show();
		}
		
		/**
		 * 添加列表数据
		 */
		function addList(jsonDataList){
			var datas = jsonDataList;//取数组
			var number = datas.length - 1;
			$(".title_statistic").append("热点分析 TOP " + number + " 结果");
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