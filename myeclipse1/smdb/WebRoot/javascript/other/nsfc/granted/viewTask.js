define(function(require, exports, module) {
	require('javascript/template_tool');
	require('pop-init');
	var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	var showData = function() {
		$.get("other/nsfc/granted/viewTask.action", function(data){
			if(data.finish == 2 && data.runningCount == 0){
				Template_tool.populate(data);
				$("#field_error").html("更新已完成");
				$("#btn_cancel").hide();
				$("#btn_confirm").show();
			} else {
				console.log(data);
				Template_tool.populate(data);
			}
				
		});
	};
	
	var updateData = function() {
		$.ajax({
			url: "other/nsfc/granted/update.action",
			data: "startYear=" + $("#startYear").val() + "&endYear=" + $("#endYear").val(),
			success: function(error){
				if(error){
					$("#field_error").append(error.errorInfo);
				}
			}
		});
		$("#field_error").html("更新中...");
		$("#btn_update").hide();
		setInterval(showData, 2000);
	}
	
	exports.init = function() {
		Template_tool.init();
		
		$("#btn_update").live("click", function(){
			var unfinishedCount = $("#unfinishedCount").html();
			var startYear = $("#startYear").val();
			var endYear = $("#endYear").val();
			if((startYear == -1 || endYear == -1) && unfinishedCount == 0){
				alert("请输入开始年份或者结束年份");
			} else if(startYear > endYear) {
				alert("开始年份不能大于结束年份");
				} else {
					 updateData();
				}
		});
		
		$("#btn_cancel").live("click", function(){
			$.post("other/nsfc/granted/cancel.action");
			thisPopLayer.destroy();
		});
		
		$("#btn_confirm").live("click", function(){
			thisPopLayer.destroy();
		});
		
		$(".select-year").live("click", function(){
			if($("#unfinishedCount").html()>0) {
				alert("存在未完成任务，需完成后才能再次选择年份!");
				$(".select-year").attr("disable", "true");
			}
		})
		
		showData();
	};
});
