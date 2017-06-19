define(function(require, exports, module) {
	var datepick = require("datepick-init");
	
	var init = function() {
		init_hint();
		$("#list_button_advSearch").click(function(){
			if($("#createDate3").val() == "--不限--"){
				$("#createDate1").val("");
			}else {
				$("#createDate1").val($("#createDate3").val());
				var date1 = $("#createDate3").val().split('-').join(',');
			}
			if($("#createDate4").val() == "--不限--"){
				$("#createDate2").val("");
			}else {
				$("#createDate2").val($("#createDate4").val());
				var date2 = $("#createDate4").val().split('-').join(',');
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
