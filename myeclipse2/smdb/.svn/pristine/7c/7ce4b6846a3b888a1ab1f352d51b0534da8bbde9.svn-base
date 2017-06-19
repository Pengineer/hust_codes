define(function(require, exports, module) {
	var cookie = require('cookie');
	require('pop');
	require('pop-self');

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
		
		$("#statistic_university_name").val("");
		$("#gsjn").html("");
		$("#slecetUniCheck").attr("checked","");
		$("#statistic_data_university").removeAttr("disabled");
	};
	
	exports.initClick = function() {
		window.checkUniversity = function(data) {checkUniversity(data)};
	}
});
