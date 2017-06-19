define(function(require, exports, module) {
	var datepick = require("datepick-init");
	
	var init = function() {
		init_hint();
		$("#list_button_advSearch").click(function(){
//			alert("clear");
			if($("#startDate1").val() == "--不限--"){
				$("#startDate").val("");
			}else {
				$("#startDate").val($("#startDate1").val());
				var date1 = $("#startDate1").val().split('-').join(',');
			}
			if($("#endDate1").val() == "--不限--"){
				$("#endDate").val("");
			}else {
				$("#endDate").val($("#endDate1").val());
				var date2 = $("#endDate1").val().split('-').join(',');
			}
			if(date1 >= date2){
				alert("起始时间必须小于结束时间！");
				return false;
			}
		});
	};
	
	exports.init = function() {
		datepick.init();// 初始化日期选择器
		init();
	};
});
