define(function(require, exports, module) {
	var nameSpace = "taskConfig/";
	var validate = require("javascript/dataProcessing/taskConfig/validate");
	window.taskList = []; // 子任务堆栈
	validate.valid();
	$(":checkbox[name='auto_time_config']").each(function() {
		$(this).change(function() {
			if ($(this).attr('checked')) {
				$(":checkbox[name='auto_time_config']").removeAttr('checked').parent().parent().find("select,input[type='text']").attr("disabled", "disabled");
				$(this).attr('checked', 'checked').parent().parent().find("select,input[type='text']").removeAttr("disabled");
				auto_time_config = $(this).attr("id");
			} else if ($(this).attr("id") === auto_time_config) {
				$(this).attr('checked', 'checked');
			}
			if ($(this).attr("checked") && $(this).attr("id") === '_interval') {
				$("input[name='interval']").rules("add", "required");
			} else {
				$("input[name='interval']").rules("remove", "required");
			}
		});

	});

	$("#add-task").click(function() {
		var node = $("#tr_task_template").clone().attr("id", "").appendTo($("#task-table tbody")).fadeIn();
		node.find("select").attr("name", "taskList" + taskList.length).rules("add", "required");
		taskList.push(node);
		node.find(".order").text(taskList.length);
		console.log(taskList);
	});

	function order() {
		for (var i = 0; i < taskList.length; i++) {
			taskList[i].find(".order").text(i + 1);
		}
	} // 重组子元素顺序
	$(".delete_row").live("click", function() {
		var index = $(this).parent().parent().find(".order").text() - 1;
		taskList.splice(index, 1)[0].fadeOut(function() {
			(this).remove();
		})
		order();
	});

	$("input[name='taskType']").change(function() {
		if ($("input[name='taskType']:checked").val() === "1") {
			$(".auto_task_config").fadeIn();
		} else {
			$(".auto_task_config").fadeOut();
		}
	});

	$("#confirm").click(function() {
		if ($("#template").valid()) {
			if (confirm("确定要创建事务？")) {
				var taskConfigName = $("#taskConfigName").val();
				var parameters = "";
				for (var i = 0; i < taskList.length; i++) {
					parameters += taskList[i].find("select").val() + ";"
				}
				parameters = parameters.replace(/;$/, "");
				if ($("input[name='taskType']:checked").val() === "0") { //手动创建
					$.ajax({
						url: nameSpace + "createManualTaskConfig.action",
						data: {
							"taskConfigName": taskConfigName,
							"parameters": parameters
						},
						type: "post",
						dataType: "json",
						success: function() {
							alert("事务创建成功!");
							window.location.href = nameSpace + "toTaskConfigListView.action";
						}
					});

				} else { //自动创建
					var executeTime = $("#executeTimeDay").val() + ":" +
						$("#executeTimeHour").val() + ":" + $("#executeTimeMin").val() + "0:0";
					var interval = $("input[name='interval']").val();
					if (auto_time_config == "_interval") {
						executeTime = "";
					} else {
						interval = "";
					}
					$.ajax({
						url: nameSpace + "createAutoTaskConfig.action",
						data: {
							"executeTime": executeTime,
							"parameters": parameters,
							"taskConfigName": taskConfigName,
							"interval": interval
						},
						type: "post",
						//					dataType: "json",
						success: function(results) {
							//TODO
							alert("事务创建成功!");
							window.location.href = nameSpace + "toTaskConfigListView.action";
						}
					});
				}
			}
		}
	});
	exports.init = function() {
		window.auto_time_config = "_time";
		$.ajax({ //请求列表数据
			url: nameSpace + "gainTaskList.action",
			data: "",
			dataType: "json",
			type: "post",
			success: function(result) {
				console.log(result);
				var node = "";
				for (var i = 0; i < result.taskInfos.length; i++) {
					node += "<option value = '" + result.taskInfos[i][0] + "'>" + result.taskInfos[i][1] + "</option>";
				}
				$(node).appendTo($("#tr_task_template .taskList"));
			}
		});
	}
})