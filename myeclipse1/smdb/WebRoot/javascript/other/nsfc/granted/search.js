define(function(require, exports, module) {
	var datepick = require("datepick-init");
	
	var init = function() {
		init_hint();
		$("#list_button_advSearch").click(function(){
			if($("#startDate1").val() == "--不限--"){
				$("#grantedStartDate").val("");
			}else {
				$("#grantedStartDate").val($("#startDate1").val());
			}
			if($("#endDate1").val() == "--不限--"){
				$("#grantedEndDate").val("");
			}else {
				$("#grantedEndDate").val($("#endDate1").val());
			}
			
			if($("#startDate2").val() == "--不限--"){
				$("#endStartDate").val("");
			}else {
				$("#endStartDate").val($("#startDate2").val());
			}
			if($("#endDate2").val() == "--不限--"){
				$("#endEndDate").val("");
			}else {
				$("#endEndDate").val($("#endDate2").val());
			}
		});
	};
	
	exports.init = function() {
		datepick.init();// 初始化日期选择器
		init();
	};
});

