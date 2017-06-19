/**
 * 用于打印变更
 * @author 肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('jquery-ui');
	var datepick = require("datepick-init");
	
	var init = function(){
		datepick.init();
		
		$("input[type='radio'][name='univ']").click(function(){
			if($(this).val() == 0){
				$(this).parent().parent().next().hide();
			} else {
				$(this).parent().parent().next().show();
			}
		});
		
		$("#select_university_btn").click(function(){
			popSelect({
				entityId : $("#subjectionId").val(),
				type : 3,
				title : "选择高校",
				inData : {"id" : $("#univId").val(), "name" : $("#univName").html()},
				callBack : function(result){
					$("#univId").val(result.data.id);
					$("#univName").html(result.data.name);
				}
			});
		});
		
		$("#submit").click(function(){
			if($("input[type='radio'][name='univ']:checked").val() == 1){
				if($.trim($("#univId").val()) == ""){
					alert("请选择高校！");
					return;
				}
			}
			if($.trim($("#startDate").val()) == "" || $.trim($("#endDate").val()) == ""){
				alert("请选择变更时间！");
				return;
			}
			if($.trim($("#startYear").val()) == "" || $.trim($("#endYear").val()) == ""){
				alert("请选择项目年度！");
				return;
			}
			thisPopLayer.callBack({
				univ : $("input[type='radio'][name='univ']:checked").val(),
				univId : $("#univId").val(),
				subType : $("#subType").val(),
				projectStatus : $("#projectStatus").val(),
				startDate : $("#startDate").val(),
				endDate : $("#endDate").val(),
				startYear : $("#startYear").val(),
				endYear : $("#endYear").val(),
			});
		});
	};
	
	exports.init = function() {
		init();
	};
});