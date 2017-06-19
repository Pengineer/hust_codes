/**
 * @author liujia
 * @description
 */
define(function(require, exports, module) {
	var nameSpace = "dataProcessing/"
	exports.init = function() {
		$("#cancel").live("click", function() {
			top.PopLayer.instances[1].destroy();
		})
		var parameters = top.PopLayer.instances[1].inData.parameters;
		var sourceName = top.PopLayer.instances[1].inData.sourceName;
		var typeName = top.PopLayer.instances[1].inData.typeName;

		$("#confirm").live("click", function() {
			if ($(":checked").length) {
				if ($(":checked").attr("id") == "createTask") {
					var taskName = "";
					do {
						taskName = prompt("请输入任务名(不能为空）：");
					} while (!taskName);


					$.ajax({
						url:"taskConfig/createTask.action",
						data: "parameters=" + parameters + "&taskName=" + taskName + "&className=sourctToMidTableAction" + "&methodName=xmlImporter",
						type: "post",
						success: function(json) {
							alert("任务成功创建！");
							top.PopLayer.instances[1].callBack("create");
						}
					});
				} else {
					if (confirm("确定立即执行任务？")) {
						$("#selectAction").hide();
						$("#tips").show();
						$.ajax({
							url: nameSpace + "executeImportXmlInfoToDB.action",
							data: "parameters=" + parameters + "&sourceName=" + sourceName + "&typeName=" + typeName,
							type: "post",
							success: function(json) {
								alert("任务执行成功！");
								top.PopLayer.instances[1].callBack("exe");
							}

						});
					}
				}
			} else {
				alert("请选择！");
			}
		})
	}
})