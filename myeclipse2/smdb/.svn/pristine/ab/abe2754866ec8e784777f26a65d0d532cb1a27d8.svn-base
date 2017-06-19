define(function(require, exports, module) {
	var datepick = require("datepick-init");
	var validate = require('javascript/security/account/validate');
	
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
			if($("#expireDate3").val() == "--不限--"){
				$("#expireDate1").val("");
			}else {
				$("#expireDate1").val($("#expireDate3").val());
				var expireDate1 = $("#expireDate3").val().split('-').join(',');
			}
			if($("#expireDate4").val() == "--不限--"){
				$("#expireDate2").val("");
			}else {
				$("#expireDate2").val($("#expireDate4").val());
				var expireDate2 = $("#expireDate4").val().split('-').join(',');
			}
			if(date1 >= date2 || expireDate1>= expireDate2){
				alert("起始时间必须小于结束时间！");
				return false;
			}
		});
	};
	
	exports.init = function() {
		validate.valid();// 校验
		datepick.init();// 初始化日期选择器
		init();
	};
});
