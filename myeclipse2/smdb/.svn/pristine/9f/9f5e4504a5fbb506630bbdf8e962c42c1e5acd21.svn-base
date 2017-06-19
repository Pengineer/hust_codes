define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('dwr/interface/associationService');

	var freqChange = function (num0, num1) {
		if ($("input[name='analyzeAngle']:checked").val() == 0) {
			$("input[name='minFrequency']").val(num0);
		} else if ($("input[name='analyzeAngle']:checked").val() == 1) {
			$("input[name='minFrequency']").val(num1);
		}
	};	
	
	var callbackSubType = function (data) {
		$("#analyze_endYear").attr("value", data);
	};
	
	exports.init = function() {
		associationService.getLastYear(callbackSubType);
		$("input[name='analyzeAngle'][value='2']").css("display", "none");
		$("input[name='analyzeAngle'][value='2']").next().css("display", "none");
		$("#analyze_startYear, #analyze_endYear").change(function(){
			if($("#analyze_startYear").val() > $("#analyze_endYear").val()) {
				$("#analyze_endYear").val($("#analyze_startYear").val());
			}
		});
		//根据类型设置初始值
		$("#type").change(function(){
			var val = this.value;
			if(val == 'discipline'){
				freqChange(50, 10);
				$("input[name='analyzeAngle'][value='2']").css("display", "none");
				$("input[name='analyzeAngle'][value='2']").next().css("display", "none");
			}else if(val == 'university'){
				freqChange(120, 20);
				$("input[name='analyzeAngle'][value='2']").css("display", "");
				$("input[name='analyzeAngle'][value='2']").next().css("display", "");
			}else if(val == 'member'){
				freqChange(10, 4);
				$("input[name='analyzeAngle'][value='2']").css("display", "none");
				$("input[name='analyzeAngle'][value='2']").next().css("display", "none");
			}
		});
		
		$("input[name='analyzeAngle']").click(function(){
			var val = this.value;
			if(val == 2){
				$("#minFrequency").css("display", "none");
			}else{
				$("#minFrequency").css("display", "");
			}
		});
		
		$("input[name='analyzeAngle']").change(function () {
			if ($("#type").val() == 'university') {
				freqChange(120, 20);
			} else if ($("#type").val() == 'discipline') {
				freqChange(50, 10);
			} else if ($("#type").val() == 'member') {
				freqChange(10, 4);
			}
		});
	};

});
