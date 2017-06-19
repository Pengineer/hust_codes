define(function(require, exports, module) {
	var cookie = require('cookie');
	var list = require('javascript/list');
	require('javascript/statistic/tool/flash_ver_detection');
	require('javascript/swf/swfobject');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var init = function(nameSpace){
		//添加
		$("#view_add").bind("click", function() {
			$("#view_list").attr("action", nameSpace + "/toAdd.action").submit();
			return false;
		});
		//修改
		$("#view_mod").bind("click", function() {
			$("#view_list").attr("action", nameSpace + "/toModify.action").submit();
			return false;
		});
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
			del(nameSpace);
			return false;
		});
		//下载
		$("#view_del").bind("click", function() {
			del(nameSpace);
			return false;
		});
		//返回
		$("#view_back").bind("click", function() {
			window.location.href = basePath + nameSpace + "/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
			return false;
		});
		
		//返回
		$("#view_back1").bind("click", function() {
			//window.location.href = basePath + nameSpace + "/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
			history.back();
			return false;
		});
		
		//常规导出
		$("#common_export").live("click", function() {
			if (!confirm("您确定要导出此统计结果吗？")) return false;
			window.location.href = basePath + "statistic/common/exportExcel.action";
			return false;
		});
		
		//定制导出
		$("#custom_export").live("click", function() {
			if (!confirm("您确定要导出此统计结果吗？")) return false;
			window.location.href = basePath + "statistic/custom/exportExcel.action";
			return false;
		});
		
		//导入常规统计
		$("#view_common").live("click", function() {
			var url = "statistic/custom/";
			var statisticType = $("#statisticType").val();
			if(statisticType.indexOf("project") > 0){
				url += "project";
			}else if(statisticType.indexOf("product") > 0){
				url += "product";
			}else{
				url += statisticType;
			}
			popTitleCreate({
				src : basePath + url + "/titleCreate.action",
				callBack : function(result){
					
				}
			});
		});
		
		//设为首页显示
		$("#view_isHomeShow").bind("click", function() {
			if ($("#isHomeShow").val() == 0) {
				if (!confirm("您确定要将此生成图设为首页显示吗？")) return;
				var isHomeShow = 1;
				
			} else {
				if (!confirm("您确定取消将此生成图设为首页显示吗？")) return;
				var isHomeShow = 0;
			}
			$.ajax({
				url: nameSpace+"/configHomeShow.action",
				type: "post",
				data: "entityId=" + $("#entityId").val() + "&isHomeShow=" + isHomeShow,
				dataType: "json",
				success: function(result) {
					if (result.errorInfo == null || result.errorInfo == "") {
						alert("设置成功!");
						if (isHomeShow == 1) {
							$("#isHomeShow").attr("value", 1);
							$("#view_isHomeShow").children().eq(0).attr("value", "首页取消");
						} else {
							$("#isHomeShow").attr("value", 0);
							$("#view_isHomeShow").children().eq(0).attr("value", "首页显示");
						}
						return false;
					} else {
						alert(result.errorInfo);
					}
				}
			});
		});
		
		/**
		 * 表格钻取
		 */
		$(".drill_down").click(function(){
			var cell = this;
			$.ajax({
				url: "statistic/drillDown.action",
				type: "post",
				data: {//Ajax要提交的数据
						"entityId" : $("#entityId").val(),
						"cell_row" : parseInt(cell.rel) - 1,
						"cell_column" : cell.rev,
						"encryptedMdxQueryString" : $("#encryptedMdxQueryString").val(),
						"statisticType" : $("#statisticType").val()
					},
				dataType: "json",
				success: function(result) {
					if (result.errorInfo == null || result.errorInfo == "") {
						if(result.headList){headList= result.headList;}
						if(result.drillRow && result.drillColumn){drillRow = result.drillRow;drillColumn = result.drillColumn;};
						//添加表格标题
						if(drillRow && drillColumn){
							$("#drill_head").html(drillRow + "" + drillColumn);
						};//表格标题
						
						//添加表格head
						if(headList){ 
							var headTr = '<td width="30" align="center">序号</td>'; 
							for(var i in headList){
								headTr += "<td align='center'>" + headList[i] + "</td>";
							};//标题列表
							$("#head").html(headTr);	
						}
						
						//请求钻取的数据
						$("#search").attr("action", "statistic/list.action");
						$("#search").submit();
						//显示钻取结果div
						$("#drillDiv").show();
					} else {
						alert(result.errorInfo);
					}
				}
			});
			$("#show_img").show();
			return false;
		});
		
		/**
		 * 收起、隐藏钻取表格
		 */
		$("#show_img").mouseover(function(){
			$(this).css("cursor", "pointer");
		}).click(function(){
			var imgs = ["image/close.gif", "image/open.gif"],
				src = ($(this).attr("src") == imgs[0]) ? (function(){
					$("#list_container").slideUp();
					return imgs[1];
				})() : (function(){
					$("#list_container").slideDown();
					return imgs[0];
				})();
			$(this).attr("src",src);
		});
		
		/**
		 * 级别钻取
		 */
		$(".drill_level").click(function(){
			var imgs = ["image/statistic/drill_plus.gif", "image/statistic/drill_minus.gif"],
				src = $(this).find("img").attr("src"),
				//判定钻取图标是展开、收缩
				newsrc = (src == imgs[0]) ? 
		            (function($this){
		            	$.ajax({//发起钻取请求
							url: "statistic/drillLevel.action",
							type: "post",
							data: {
								"cell_row" :  parseInt($this.attr("rel")) - 1,
								"encryptedMdxQueryString" : $("#encryptedMdxQueryString").val(),
								"statisticType" : $("#statisticType").val()},
							dataType: "json",
							success: function(result) {
								if (result.errorInfo == null || result.errorInfo == "") {
									var data = result.dataList;
									//在钻取数据下面添加一个显示DIV
									for(var i = 1; i < $this.parent("td").parent("tr").children("td").length; i++){
										$this.parent("td").parent("tr").children("td").eq(i)
											.append("<div></div>");
									}
									//将获取后的数据依次显示到DIV中
									for(var i = 0; i < data.length; i++){
										var height = 0;
										for(var j = 0; j < data[i].length; j++){
											var con = $this.parent("td").parent("tr").children("td").eq(j+1);
											$this.parent("td").parent("tr").children("td").eq(j+1).find("div")
												.append("<span style='padding-left: 20px;" + (function(){
													return (j != 0) ? "line-height:" + height : "";
												})() +"'>" + (function(){
													return (j == 0) ? (i+1) + "&nbsp;&nbsp;" : "";
												})() + data[i][j] +"</span>").append("<br/>");
											height = (j == 0) ? con.find("div").find("span")
									                  .eq(i).css("height"): height;
										};
									};
								} else {
									alert(result.errorInfo);
								}
							}
						});
		            	return imgs[1];
					})($(this)) : 
					(function($this){
						for(var i =1; i < $this.parent("td").parent("tr").children("td").length; i++){
							$this.parent("td").parent("tr").children("td").eq(i)
								.find("div").remove();
						}
						return imgs[0];
					})($(this));
			$(this).find("img").attr("src", newsrc);
			return false;
		});
		
		/**
		 * 奇偶行效果
		 */
		/*(function(){
			var count = -1;
			$(".table_statistic tr").each(function(){
				if($.trim($(this).html()) == ""){
					$(this).remove();
				} else{
					count++;
					if(count == 0){
						$(this).addClass("first");
					} else if(count % 2 == 0){
						$(this).addClass("even");
					}
				};
			});
		})();*/
		
		/**
		 * 自适应单元格宽度
		 */
		(function(){
			$(".table_statistic tr:first").children().each(function(index){
				if(index == 0) {$(this).width("45");}
				else if(index == 1) {$(this).width("140");}
			});
			$(".table_statistic").width('100%');
		})();
	};

	/**
	 * 用HighChart显示统计图
	 * @author liujia 2014-10-29
	 */
	function showHighChart() {
		var config = $("#chartConfig").val();//统计图配置
	    var configs = config.split(';');//多个图表配置用分号隔开
	     for(var i = 0; i < configs.length; i++) {
	            var tmp = configs[i].trim().split(':');
	            var type = tmp[0].trim();//统计图类型
	            var mdxid = $("#MDXQueryId").val();//常规统计分析id
	            if(mdxid != null && mdxid != '') {//常规统计分析
	            	var keyword = "MDXQueryId=" + mdxid + "&chartType=" + type + "&chartShowIndexes=" + tmp[1]
	            } else {
	            	var keyword = "encryptedMdxQueryString=" + $("#encryptedMdxQueryString").val() + "&statisticType=" + $("#statisticType").val() + "&chartType=" + type + "&chartShowIndexes=" + tmp[1];
	            }
	            var height = 300;//统计图高度
	            var chartid = 'chart' + i;
	            var myElement = document.createElement('div');//插入统计图的div
	            myElement.id = chartid;
	            var e = document.createElement('div');
	            var xchart = document.getElementById('chart');
	            xchart.appendChild(myElement);
	            xchart.appendChild(e);
	            $.ajax({
	            	url: "statistic/showChart.action",
	            	data: keyword,
	            	dateType: "json",
	    			type: "post",
	            	success: function(json){
	            		 chart = new Highcharts.Chart({                   //图表展示容器，与div的id保持一致
	            	            chart: {
	            	                type: json.type,
	            	                renderTo: chartid,
	            	                height:400
	            	            },
	            	            title: {
	            	                text: json.title || ""     //指定图表标题
	            	            },
	            	            subtitle: {
	            	                text: json.subtitle || ""
	            	            },
	            	            legend: {
	            	            	enabled: false
	            	            },
	            	            plotOptions: {
	            	                line: {
	            	                    dataLabels: {
	            	                        format: json.isPercent == "1" ? '{y:.2f}%' : ''
	            	                    }
	            	                },
	            	                 pie: {
	            	                    allowPointSelect: true, //启用饼图点击动画效果
	            	                    cursor: 'pointer'
	            	                },
	            	                series: {
	            	                    dashStyle: "solid",
	            	                    dataLabels: {
	            	                        enabled: $.cookie("showDataLabels") === "false" ? false : true
	            	                    }
	            	                }
	            	            },
	            	            yAxis: {
	            	                title: {
	            	                    text:json.yAxis ? json.yAxis.title : ""
	            	                },
	            	                min: json.yAxis ? json.yAxis.min : 0,
	            	                max: json.yAxis ? json.yAxis.max : 0,
	            	                tickInterval: json.yAxis ? json.yAxis.step : 1,
	            	                labels: {
	            	                	formatter: function() {
	            	                		if(json.isPercent == "1") return this.value + '%';
	            	                		else return this.value;
					                	}
	            	               }
	            	               
	            	            },
	            	            xAxis: {
	            	                title: {
	            	                    text: json.xAxis ? json.xAxis.title : ""
	            	                },
	            	                labels: {
	            	                    rotation: -45 //x轴文字逆时针旋转45°
	            	                },
	            	                categories: json.xAxis ? json.xAxis.categories : ""
	            	            },
	            	           tooltip: {
	            	                formatter: function () {
	            	                    return this.point.options.tip; // 设置鼠标悬浮显示的内容
	            	             }
	            	            },
	            	            series:[{
	            	                data:json.data //设置数据
	            	            }]
	            	               
	            	        });
	            	}
	            	
	            });
	    }
	}

	/**
	 * 删除统计项
	 */
	function del(nameSpace){
		if (!confirm("您确定要删除此条统计分析吗？")) return;
		$.ajax({
			url: nameSpace+"/delete.action",
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
	};

	/**
	 * 生成统计图
	 * @param {Object} id flash的id
	 * @author leida 2011-11-04
	 */
	function generateImage(id) {
		$.ajax({
			url: "statistic/generateImage.action",
			type: "post",
			data: "imageBase64Str=" + document.getElementById(id).get_img_binary(),
			dataType: "json",
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					var successUrl = 'statistic/downloadImage.action';
					window.location.href = basePath + successUrl;
				}
				else {
					alert(result.errorInfo);
				}
			}	
		});
	}

	/**
	 * open flash chart的默认保存图片函数接口
	 */
	function save_image() {
		generateImage('chart0');
	}
	
	/**
	 * 子页面iframe高度自适应
	 */
	function SetParentIfrmHeight(id){
	    var pIframe = window.parent.document.getElementById(id);
	    if (document.getElementById){
            if (pIframe && !window.opera){
                if(pIframe.contentDocument && pIframe.contentDocument.body.offsetHeight)
                    pIframe.height = pIframe.contentDocument.body.offsetHeight + 420;
                else if(pIframe.Document && pIframe.Document.body.scrollHeight)
                    pIframe.height = pIframe.Document.body.scrollHeight + 420;
            }
	    }
	}
	
	/**
	 * 父页面iframe高度自适应
	 */
	function SetPParentIframeHeight(id){
	    var ppIframe = window.parent.parent.document.getElementById(id);
	    if (document.getElementById){
            if (ppIframe && !window.opera){
                if(ppIframe.contentDocument && ppIframe.contentDocument.body.offsetHeight)
                    ppIframe.height = ppIframe.contentDocument.body.offsetHeight;
                else if(ppIframe.Document && ppIframe.Document.body.scrollHeight)
                    ppIframe.height = ppIframe.Document.body.scrollHeight;
            }
	    }
	}
	
	exports.init = function() {
		init("statistic/common");
		showHighChart();
		SetParentIfrmHeight("sChart");
		SetPParentIframeHeight("main");
		list.pageShow({"nameSpace": "statistic"});
	};
});
