define(function(require, exports, module){
	var nameSpace = "taskConfig/";
	function showConfigTask(){
		$.ajax({
			url: nameSpace + "gainTaskConfigList.action",
			data: "",
			dataType: "json",
			success: function(result){
				for (var i = 0; i < result.taskConfigInfos.length; i++) {
					result.taskConfigInfos[i].push(i);
				}
				$("#taskConfigInfos").hide();
				$("#taskConfigInfos").html(TrimPath.processDOMTemplate("taskConfigInfos_template", result));
				setOddEvenLine("taskConfigInfos", 0);//设置奇偶行效果
				$("#check").live("click", function() { // 全选
					checkAll(this.checked, "entityIds");
				});
				$("#taskConfigInfos").show();
			}
		});
	}//任务显示函数
	
	$(".link1").live("click", function(){
		var taskConfigId = $(this).attr("id");
		window.location.href = nameSpace + "toViewTaskConfigInfo.action?taskConfigId=" + taskConfigId;
	})
	
	
	exports.init = function(){
		showConfigTask();
	}
})