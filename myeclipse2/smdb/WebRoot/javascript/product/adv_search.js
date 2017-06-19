define(function(require, exports, module) {
	var datepick = require("datepick-init");
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var initPop = function(){
		var discipline = document.getElementById("dispName").value;
		doWithXX(discipline, "dispName", "disptr");
	};
	var leaf = function() {
		init_hint();//初始化时间"不限"的提示
		$("#list_button_advSearch").click(function(){
			if($("#pubDateId1").val() == "--不限--"){
				$("#pubDate1").val("");
			}else {
				$("#pubDate1").val($("#pubDateId1").val());
			}
			if($("#pubDateId2").val() == "--不限--"){
				$("#pubDate2").val("");
			}else {
				$("#pubDate2").val($("#pubDateId2").val());
			}
			
			if($("#audDateId1").val() == "--不限--"){
				$("#audDate1").val("");
			}else {
				$("#audDate1").val($("#audDateId1").val());
			}
			if($("#audDateId2").val() == "--不限--"){
				$("#audDate2").val("");
			}else {
				$("#audDate2").val($("#audDateId2").val());
			}

		});
		//选择学科门类
		$("#select_disciplineType_btn").click(function(){
			$("#disptr").parent("td").next("td").html("");
			popSelect({
				type : 11,
				inData : $("#dispName").val(),
				callBack : function(result){
					doWithXX(result, "dispName", "disptr");
				}
			});
			return false;
		});
		//高级检索校验
		$("#advSearch").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			}
		});
	};
	
	exports.init = function() {
		datepick.init();// 初始化日期选择器
		initPop();//设置弹出层的原始值
		leaf();
	};
});