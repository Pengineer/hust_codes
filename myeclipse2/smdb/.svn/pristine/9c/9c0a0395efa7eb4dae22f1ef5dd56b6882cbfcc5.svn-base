define(function(require, exports, module) {
	require('form');
	require('pop');
	require('pop-self');
	var datepick = require("datepick-init");
	var view = require('javascript/view');
	require('tool/highcharts/highcharts');
	require('javascript/comprehensiveAuxiliary/showChart');
	var showDetails = function(result) {// 显示查看内容
		var selectedTab;
		$("#chartIframe").show();
		$("#listSearch2").hide();
		
		if (!(typeof(result.visitList) == "undefined")) {
			selectedTab = "log";
			$(".j_log").show();
			$("#view_visit_container").html(TrimPath.processDOMTemplate("view_visit", result));
			$("#view_visit_container").show();
			setOddEvenLine("view_visit_container", 0);// 设置奇偶行效果
			$("#container").show();
			if (result.chartType == 1) {
				initHighchartsColumn(result);
			} else if (result.chartType == 2) {
				initHighchartsBar(result);
			} else if (result.chartType == 3) {
				initHighchartsPlot(result);
			} else {
				initHighcharts(result);
			}
		} else {
			$("#container").hide();
			$(".j_log").hide();
			$("#view_visit_container").hide();
		}
		if (result.visitList == null) {
			$("#container").hide();
		} else {
			$("#container").show();
		}
		if (!(typeof(result.appList) == "undefined" && typeof(result.graList) == "undefined")) {
			selectedTab = "project";
			$(".j_project").show();
			$("#view_project_container").html(TrimPath.processDOMTemplate("view_project", result));
			$("#view_project_container").show();
			setOddEvenLine("view_project_container", 0);// 设置奇偶行效果
			
			$("#view_project_num_container").html(TrimPath.processDOMTemplate("view_num_project", result));
			$("#view_project_num_container").show();
			
		} else {
			$(".j_project").hide();
			$("#view_project_container").hide();
			$("#view_project_num_container").hide();
		}
		
		if (!(typeof(result.awardList) == "undefined")) {
			selectedTab = "award";
			$(".j_award").show();
			$("#view_award_container").html(TrimPath.processDOMTemplate("view_award", result));
			$("#view_award_container").show();
			setOddEvenLine("view_award_container", 0);// 设置奇偶行效果
		} else {
			$(".j_award").hide();
			$("#view_award_container").hide();
		}
		
		if (!(typeof(result.paperList) == "undefined" && typeof(result.bookList) == "undefined" && typeof(result.consuList) == "undefined" && typeof(result.elecList) == "undefined" )) {
			selectedTab = "product";
			$(".j_product").show();
			$("#view_product_container").html(TrimPath.processDOMTemplate("view_product", result));
			$("#view_product_container").show();
			setOddEvenLine("view_product_container", 0);// 设置奇偶行效果
		} else {
			$(".j_product").hide();
			$("#view_product_container").hide();
		}
		$("#view_parm_container").html(TrimPath.processDOMTemplate("view_parm", result));
		$("#view_parm_container").show();
		view.inittabs(selectedTab, 1);// 初始化以上标签
	};
	
	exports.init = function() {
		init_hint();
		datepick.init();
		
		$("#submit").live("click", function() {
			var query_data = $("input[type='checkbox'][name='query_data']:checked");
			if(query_data.length == 0){alert("查询数据不能为空！"); return false;}
			var personType = $("#personType").val();
			if (personType == -1) {
				alert("请选择人员类型!");
				return false;
			}
			if ($("#entityId").val() == '') {
				alert("请选择社科人员!");
				return false;
			}
			if($("#startDate").val() == "--不限--"){
				$("#startDate").val("");
			}else {
				$("#startDate").val($("#startDate").val());
			}
			if($("#endDate").val() == "--不限--"){
				$("#endDate").val("");
			}else {
				$("#endDate").val($("#endDate").val());
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
			$("#listSearch1").show();
			$("#listSearch2").hide();
			
			return false;
		});
		
		$("#personType").live("click",function(){
			var personType = $("#personType").val();
			if (personType == -1) {
				$("#chose").hide();
			} else {
				$("#chose").show();
			}
			if (personType == 6 || personType == 7 || personType == 8 || personType == -1) {
				$(".j_query_type").show();
			} else {
				$(".j_query_type").hide();
			}
			
		});
		
		$("#personType").live("change",function(){
			$("#entityId").val('');
			$("#entityName").empty();
		});	
		
		$("#selectPerson").click(function(){
			var personType = $("#personType").val();
			var officerType;
			if (personType == 1 || personType == 2 || personType == 3 ) {
				officerType = parseInt(personType) + 1;
			} else if (personType == 4 || personType == 5) {
				officerType = parseInt(personType) + 2;
			} else {
				officerType = 0;
			}
			popSelectPerson({
				type : personType,
				officerType : officerType,
				inData : {"id" : $("#entityId").val(), "name" : $("#entityName").text()},
				callBack : function(result){
					var entityId = result.data.id;
					$("#entityId").val(entityId);
					$("#entityName").empty();
					$("#entityName").append(result.data.name);
				}
			});
		});
		
		$("#query_visit").live("click",function(){
			if($("#query_visit").attr("checked")==true){
				$(".j_time").show();
			} else {
				$(".j_time").hide();
			}
		});
		
		$("#query_project").live("click",function(){
			if($("#query_project").attr("checked")==true){
				$(".j_project_set").show();
			} else {
				$(".j_project_set").hide();
			}
			
		});
		$("#showLineNum").blur(function () {
			if(!($.trim($(this).val())=='')){
				$("input[type='radio'][name='showLineNum']").attr("disabled","true");
				$("#lineNum").attr("value",$.trim($(this).val()));
			} else {
				$("input[type='radio'][name='showLineNum']").attr("disabled",false);
			}
		});
		
		$("#listSearch1").click(function(){
			init_hint();
			$("#config").slideToggle();
			$("#listSearch1").hide();
			$("#listSearch2").show();
		});
		$("#listSearch2").click(function(){
			$("#config").slideToggle();
			$("#listSearch2").hide();
			$("#listSearch1").show();
		});
		
	};
});
