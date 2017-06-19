/**
 * 用于业务管理高级检索
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	var datepick = require("datepick-init");
	
	var init = function() {
		init_hint();
		$("#list_button_advSearch").click(function(){
			if($("#startDate1").val() == "--不限--"){
				$("#startDate").val("");
			}else {
				$("#startDate").val($("#startDate1").val());
			}
			if($("#endDate1").val() == "--不限--"){
				$("#endDate").val("");
			}else {
				$("#endDate").val($("#endDate1").val());
			}
			if($("#applicantDeadline11").val() == "--不限--"){
				$("#applicantDeadline1").val("");
			}else {
				$("#applicantDeadline1").val($("#applicantDeadline11").val());
			}
			if($("#applicantDeadline12").val() == "--不限--"){
				$("#applicantDeadline2").val("");
			}else {
				$("#applicantDeadline2").val($("#applicantDeadline12").val());
			}
			if($("#deptInstDeadline11").val() == "--不限--"){
				$("#deptInstDeadline1").val("");
			}else {
				$("#deptInstDeadline1").val($("#deptInstDeadline11").val());
			}
			if($("#deptInstDeadline12").val() == "--不限--"){
				$("#deptInstDeadline2").val("");
			}else {
				$("#deptInstDeadline2").val($("#deptInstDeadline12").val());
			}
			if($("#univDeadline11").val() == "--不限--"){
				$("#univDeadline1").val("");
			}else {
				$("#univDeadline1").val($("#univDeadline11").val());
			}
			if($("#univDeadline12").val() == "--不限--"){
				$("#univDeadline2").val("");
			}else {
				$("#univDeadline2").val($("#univDeadline12").val());
			}
			if($("#provDeadline11").val() == "--不限--"){
				$("#provDeadline1").val("");
			}else {
				$("#provDeadline1").val($("#provDeadline11").val());
			}
			if($("#provDeadline12").val() == "--不限--"){
				$("#provDeadline2").val("");
			}else {
				$("#provDeadline2").val($("#provDeadline12").val());
			}
			if($("#reviewDeadline11").val() == "--不限--"){
				$("#reviewDeadline1").val("");
			}else {
				$("#reviewDeadline1").val($("#reviewDeadline11").val());
			}
			if($("#reviewDeadline12").val() == "--不限--"){
				$("#reviewDeadline2").val("");
			}else {
				$("#reviewDeadline2").val($("#reviewDeadline12").val());
			}
		});
	};
	
	exports.valid = function(){
		$("#advSearch").validate({
			errorElement: "span",
			event: "blur",
			rules:{
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parents("td").next("td") );
			}
		});
	};
	
	exports.init = function() {
		datepick.init();// 初始化日期选择器
		init();
	};
});
