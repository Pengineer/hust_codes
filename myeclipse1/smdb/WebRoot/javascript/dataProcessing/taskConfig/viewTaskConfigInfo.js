define(function(require, exports, module){
	var nameSpace = "taskConfig/";
	function showDetail(){
		var taskConfigId = $("#taskConfigId").val();
		$.ajax({
			url: nameSpace + "gainTaskConfigInfo.action",
			data:{
				"taskConfigId": taskConfigId
			},
			type: "post",
			dataType: "json",
			success: function(result){
				$("#view_container").hide();
				$("#view_container").html(TrimPath.processDOMTemplate("view_template",result));
				$("#view_container").show();
			}
		});
	}
	$("#view_back").click(function(){
		history.go(-1);
	});
	$("#view_del").click(function(){
		var taskConfigId = $("#taskConfigId").val();
		if(confirm("确定删除所选事务？")) {
			$.ajax({
				url: nameSpace + "deleteTaskConfig.action",
				data: {
					"taskConfigId":taskConfigId
				},
				type: "post",	
				dataType: "json",
				success: function(result){
					alert(result.results);
					window.location.href = nameSpace + "toTaskConfigListView.action";//删除之后返回任务列表
				}
				
			})
		}
	});
	exports.init = function(){
		showDetail();
	}
})