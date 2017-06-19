define(function(require, exports, module) {
	require('javascript/step_tools');
	
	//初始化
	exports.init = function() {
		
		//导出firstYear（如2012）年结项人员在secondYear（如2013）年的项目申请情况
		$("#exportApplyInEnd").click(function(){
			var firstYear = $("#firstYear").val();
			var secondYear = $("#secondYear").val();
			if(firstYear == ''){
				alert("请输入起始年份！");
				return false;
			}
			if(secondYear == ''){
				alert("请输入终止年份！");
				return false;
			}
			var url = "statistic/analysis/project/exportApplyInEnd.action";
			var parms = "?firstYear=" + firstYear + "&secondYear=" + secondYear;
			location.href = url + parms;
		});
	};
});