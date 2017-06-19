define(function(require, exports, module) {
	var cookie = require('cookie');
	require('pop');
	require('pop-self');

	function checkSession() {
		if($("#statistic_start_sesison").val() > $("#statistic_end_sesison").val()) {
			$("#statistic_end_sesison").val($("#statistic_start_sesison").val());
		}
	}

	function checkDiscipline(data) {
		if(data) {
			$("#statistic_data_discipline").removeAttr("checked");
			$("#statistic_data_discipline").attr("disabled", "disabled");
			$("#disciplineShow").show();
		} else {
			$("#statistic_data_discipline").removeAttr("disabled");
			$("#disciplineShow").hide();
		}
	}

	function checkUniversity(data) {
		if(data) {
			$("#statistic_data_university").removeAttr("checked");
			$("#statistic_data_university").attr("disabled", "disabled");
			$("#universityShow").show();
		} else {
			$("#statistic_data_university").removeAttr("disabled");
			$("#universityShow").hide();
		}
	}
	
	exports.init = function() {
		$("#selectUniversity").click(function(){
			popSelect({
				type : 3,
				inData : $("#statistic_university_name").val(),
				callBack : function(result){
					doWithXX(result.data.name, "statistic_university_name", "gsjn");
				}
			});
		});
		$("#selectDiscipline").click(function(){
			popSelect({
				type : 19,
				inData : $("#statistic_discipline").val(),
				callBack : function(result){
					doWithXX(result, "statistic_discipline", "gsjn1");
				}
			});
		});
		$("#statistic_university_name").val("");
		$("#gsjn").html("");
		$("#slecetUniCheck").attr("checked","");
		$("#statistic_data_university").removeAttr("disabled");
		
		$("#statistic_discipline").val("");
		$("#gsjn1").html("");
		$("#slecetDisCheck").attr("checked","");
		$("#statistic_data_discipline").removeAttr("disabled");
	};
	
	exports.initClick = function() {
		window.checkSession = function() {checkSession()};
		window.checkDiscipline = function(data) {checkDiscipline(data)};
		window.checkUniversity = function(data) {checkUniversity(data)};
	}
});
