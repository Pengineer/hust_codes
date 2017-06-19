define(function(require, exports, module) {
	var cookie = require('cookie');
	require('pop');
	require('pop-self');

	//由于多维统计分析中的统计指标和统计数据不能为相同轴，所以如果统计指标使用了某轴的某维度，则统计数据就不能选这个了
	//检测年度范围
	function checkYear() {
		if($("#statistic_startYear").val() > $("#statistic_endYear").val()) {
			$("#statistic_endYear").val($("#statistic_startYear").val());
		}
	}
	//检测学科门类
	function checkDiscipline(data) {
		if(data) {
			$("#statistic_index_discipline").removeAttr("checked");
			$("#statistic_index_discipline").attr("disabled", "disabled");
			$("#disciplineShow").show();
		} else {
			$("#statistic_index_discipline").removeAttr("disabled");
			$("#statistic_discipline").val("");
			$("#disciplineDiv").html("");
			$("#disciplineShow").hide();
		}
	}
	//检测高校
	function checkUniversity(data) {
		if(data) {
			$("#statistic_index_university").removeAttr("checked");
			$("#statistic_index_university").attr("disabled", "disabled");
			$("#universityShow").show();
		} else {
			$("#statistic_index_university").removeAttr("disabled");
			$("#statistic_university").val("");
			$("#universityDiv").html("");
			$("#universityShow").hide();
		}
	}
	//检测项目类型
	function checkProjectType(data) {
		if(data) {
			$("#statistic_index_projectType").removeAttr("checked");
			$("#statistic_index_projectType").attr("disabled", "disabled");
			$("#projectTypeShow").show();
		} else {
			$("#statistic_index_projectType").removeAttr("disabled");
			$("#statistic_projectType").val("");
			$("#projectTypeShow").hide();
		}
	}
	//检测项目子类
	function checkSubType(data) {
		if(data) {
			$("#statistic_index_subType").removeAttr("checked");
			$("#statistic_index_subType").attr("disabled", "disabled");
			$("#subTypeShow").show();
		} else {
			$("#statistic_index_subType").removeAttr("disabled");
			$("#statistic_subType").val("");
			$("#subTypeShow").hide();
		}
	}
	//检测项目区域
	function checkProjectArea(data) {
		if(data) {
			$("#statistic_index_projectArea").removeAttr("checked");
			$("#statistic_index_projectArea").attr("disabled", "disabled");
			$("#projectAreaShow").show();
		} else {
			$("#statistic_index_projectArea").removeAttr("disabled");
			$("#statistic_projectArea").val("");
			$("#projectAreaShow").hide();
		}
	}
	//检测项目省份
	function checkProvince(data) {
		if(data) {
			$("#statistic_index_province").removeAttr("checked");
			$("#statistic_index_province").attr("disabled", "disabled");
			$("#provinceShow").show();
		} else {
			$("#statistic_index_province").removeAttr("disabled");
			$("#statistic_province").val("");
			$("#provinceShow").hide();
		}
	}
	//检测项目成果类型
	function checkProductType(data) {
		if(data) {
			$("#statistic_index_productType").removeAttr("checked");
			$("#statistic_index_productType").attr("disabled", "disabled");
			$("#productTypeshow").show();
		} else {
			$("#statistic_index_productType").removeAttr("disabled");
			$("#statistic_index_productType").val("");
			$("#productTypeshow").hide();
			$("#statistic_productType").val('');
			var typelist = document.getElementsByName("productType");
			for(var i = 0; i < typelist.length; i++) {
				typelist[i].checked = false;
			}
		}
	}
	//检测项目结项评审类型
	function checkEvaluType(data) {
		if(data) {
			$("#statistic_index_evaluType").removeAttr("checked");
			$("#statistic_index_evaluType").attr("disabled", "disabled");
			$("#evaluTypeShow").show();
		} else {
			$("#statistic_index_evaluType").removeAttr("disabled");
			$("#statistic_evaluType").val("");
			$("#evaluTypeShow").hide();
		}
	}
	//检测依托高校类型
	function checkUniversityType(data) {
		if(data) {
			$("#statistic_index_universityType").removeAttr("checked");
			$("#statistic_index_universityType").attr("disabled", "disabled");
			$("#universityTypeShow").show();
		} else {
			$("#statistic_index_universityType").removeAttr("disabled");
			$("#statistic_universityType").val("");
			$("#universityTypeShow").hide();
		}
	}
	//检测高校举办者
	function checkUniversityOrganizer(data) {
		if(data) {
			$("#universityOrganizerShow").show();
		} else {
			$("#statistic_universityOrganizer").val("");
			$("#universityOrganizerShow").hide();
		}
	}
	//检测结项状态
	function checkEndStatus(data) {
		if(data) {
			$("#endStatusShow").show();
		} else {
			$("#statistic_endStatus").val("");
			$("#endStatusShow").hide();
		}
	}
	//拼接项目类型字串
	function generateProductType(){
		var typelist = document.getElementsByName("productType");
		var result = '';
		var flag = 0;
		for(var i = 0; i < typelist.length; i++) {
			if(typelist[i].checked && flag == 0) {
				result += typelist[i].id;
				flag = 1;
			} else if(typelist[i].checked && flag == 1) {
				result += '; ' + typelist[i].id;
			}
		}
		$("#statistic_productType").val(result);
	}
	
	//初始化
	exports.init = function() {
		//高校切片弹窗
		$("#selectUniversity").click(function(){
			popSelect({
				type : 3,
				inData : $("#statistic_university").val(),
				callBack : function(result){
					doWithXX(result.data.name, "statistic_university", "universityDiv");
				}
			});
		});
		
		//学科门类切片弹窗
		$("#selectDiscipline").click(function(){
			popSelect({
				type : 19,
				inData : $("#statistic_discipline").val(),
				callBack : function(result){
					doWithXX(result, "statistic_discipline", "disciplineDiv");
				}
			});
		});
		
//		//统计数据radio点击事件，如果统计数据选中的是项目年度，则隐藏项目年度tr
//		$("input[name='statistic_data'][type='radio']").click(function(){
//			if ($(this).attr("id") === 'statistic_data_projectYear') {	
//				$("#tr_projectYear").css("display", "none");
//			} else {
//				$("#tr_projectYear").css("display", "");
//			}
//		});
//		//页面初始加载时，如果统计数据选中的是项目年度，则隐藏项目年度tr
//		$(function() {
//			if ($("#statistic_data_projectYear").attr("checked")) {
//				$("#tr_projectYear").css("display", "none");
//			}
//		});
		
		//页面初始化
		$("#statistic_university").val("");
		$("#universityDiv").html("");
		$("#selectUniversityCheck").attr("checked","");
		$("#statistic_index_university").removeAttr("disabled");
		
		$("#statistic_discipline").val("");
		$("#disciplineDiv").html("");
		$("#selectDisciplineCheck").attr("checked","");
		$("#statistic_index_discipline").removeAttr("disabled");
		
		$("#selectProjectTypeCheck").attr("checked","");
		$("#statistic_projectType").val('');
		$("#statistic_index_projectType").removeAttr("disabled");
		
		$("#selectSubTypeCheck").attr("checked","");
		$("#statistic_subType").val('');
		$("#statistic_index_subType").removeAttr("disabled");
		
		$("#selectProjectAreaCheck").attr("checked","");
		$("#statistic_projectArea").val('');
		$("#statistic_index_projectArea").removeAttr("disabled");
		
		$("#selecetProductTypeCheck").attr("checked","");
		$("#statistic_productType").val('');
		$("#statistic_index_productType").removeAttr("disabled");
		
		$("#selectProvinceCheck").attr("checked","");
		$("#statistic_province").val('');
		$("#statistic_index_province").removeAttr("disabled");
		
		$("#selectEvaluTypeCheck").attr("checked","");
		$("#statistic_evaluType").val('');
		$("#statistic_index_evaluType").removeAttr("disabled");
		
		$("#selectUniversityTypeCheck").attr("checked","");
		$("#statistic_universityType").val('');
		$("#statistic_index_universityType").removeAttr("disabled");
		
		$("#selectEndStatusCheck").attr("checked","");
		$("#statistic_endStatus").val('');
	};
	
	exports.initClick = function() {
		window.checkYear = function() {checkYear()};
		window.checkDiscipline = function(data) {checkDiscipline(data)};
		window.checkUniversity = function(data) {checkUniversity(data)};
		window.checkProjectType = function(data) {checkProjectType(data)};
		window.checkSubType = function(data) {checkSubType(data)};
		window.checkProjectArea = function(data) {checkProjectArea(data)};
		window.checkProvince = function(data) {checkProvince(data)};
		window.checkProductType = function(data) {checkProductType(data)};
		window.checkEvaluType = function(data) {checkEvaluType(data)};
		window.checkUniversityType = function(data) {checkUniversityType(data)};
		window.checkUniversityOrganizer = function(data) {checkUniversityOrganizer(data)};
		window.checkEndStatus = function(data) {checkEndStatus(data)};
		window.generateProductType = function() {generateProductType()};
	}
});
