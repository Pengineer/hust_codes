define(function(require, exports, module) {
	require('form');
	require('pop');
	require('pop-self');
	var view = require('javascript/view');
	require('tool/highcharts/highcharts');
	require('javascript/comprehensiveAuxiliary/showChart');
	
	var nameSpace = "auxiliary/unit";
	
	var showDetails = function(result) {// 显示查看内容
		$("#chartIframe").show();
		
		$("#totlaUniversity").empty();
		$("#totlaUniversity").append(result.totlaUniversity);
		$("#totlaMoeUniversity").empty();
		$("#totlaMoeUniversity").append(result.totlaMoeUniversity);
		$("#totlaLocUniversity").empty();
		$("#totlaLocUniversity").append(result.totlaLocUniversity);
		
		$("#provinceName").empty();
		$("#provinceName").append($("#entityNameValue").val());
		
		$("#view_person_container").html(TrimPath.processDOMTemplate("view_person", result));
		$("#view_person_container").show();
		setOddEvenLine("view_person_container", 0);// 设置奇偶行效果
		
		$("#view_project_container").html(TrimPath.processDOMTemplate("view_project", result));
		$("#view_project_container").show();
		setOddEvenLine("view_project_container", 0);// 设置奇偶行效果
		
		$("#view_product_container").html(TrimPath.processDOMTemplate("view_product", result));
		$("#view_product_container").show();
		setOddEvenLine("view_product_container", 0);// 设置奇偶行效果
		
		$("#view_award_container").html(TrimPath.processDOMTemplate("view_award", result));
		$("#view_award_container").show();
		setOddEvenLine("view_award_container", 0);// 设置奇偶行效果
		
		$("#view_score_container").show();
		$("#view_score_container").html(TrimPath.processDOMTemplate("view_score", result));
		setOddEvenLine("view_score_container", 0);// 设置奇偶行效果
		
		view.inittabs();// 初始化以上标签
		
		//画图
		if (result.chartType == 1) {
			initHighchartsColumnUnit(result);
			/*initHighchartsColumn(1,result.lTitle, result.lData);
			initHighchartsColumn(2,result.lProjTitle, result.lProjData);
			initHighchartsColumn(3,result.lProdTitle, result.lProdData);
			initHighchartsColumn(4,result.lAwarTitle, result.lAwarData);*/
		} else if (result.chartType == 2) {
			initHighchartsBar(result);
		} else if (result.chartType == 3) {
			initHighcharts(result);
		}
		
	};
	
	//垂直柱图
	var initHighchartsColumnUnit = function(json) {
		$('#container').highcharts({
			chart : {
				type : 'column'
			},
			title : {
				text : null
			},
			xAxis : {
				categories : json.lSocTitle
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
				name : '总分',
				data : json.lScoData
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
				categories : json.lSocTitle
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
				name : '总分',
				data : json.lScoData
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
				categories : json.lSocTitle
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
				name : '总分',
				data : json.lScoData
			// 图表1y轴数据
			} ]
		});
	};
	
	exports.init = function() {
		view.show(nameSpace, showDetails);// 公用方法
		var entityNameValue = $("#entityNameValue").val();
		if (entityNameValue != "") {
			$("#entityName").empty();
			$("#entityName").append(entityNameValue);
		}
		
		$("#submit").live("click", function() {
			if ($("#entityId").val() == '') {
				alert("请选择省级机构!");
				return false;
			}
			var sum = 0;
			$(".keyword").each(function() {
				sum += +this.value;
			});
			if ((sum) != 1) {
				alert("所有的权重和要等于1 ！");
				return false;
			}
			$("#form_stat").ajaxSubmit({
				success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						showDetails(result);
						return false;
					} else {
						alert(result.errorInfo);
					}
				}
			});
			$("#config").slideToggle(1000);
			return false;
		});
		
		$("#selectProvince").click(function(){
			popSelect({
				type : 2,
				label : 0,
				officerType : 0,
				inData : {"id" : $("#entityId").val(), "name" : $("#entityName").text()},
				callBack : function(result){
					var entityId = result.data.id;
					$("#entityId").val(entityId);
					$("#entityName").empty();
					$("#entityName").append(result.data.name);
					$("#entityNameValue").val(result.data.name);
					$.post("auxiliary/unit/getData.action?entityId="+entityId);
				}
			});
		});
		
		$("#listSearch1").click(function(){
			init_hint();
			$("#config").slideToggle(800);
			$("#listSearch1").hide();
			$("#listSearch2").show();
		});
		
		$("#listSearch2").click(function(){
			$("#config").slideToggle(500);
			$("#listSearch2").hide();
			$("#listSearch1").show();
		});
		
	};
});
