define(function(require, exports, module) {
	var nameSpace = "system/interfaces/sinossClient";
	require('javascript/template_tool');
	require('form');
	var getData;

	var showData = function() {
		$.get("system/interfaces/sinossClient/getCurrentImporter.action", function(data){
			if (data.result != null) {
				clearInterval(getData);
				alert("数据入库完成");
			}
			Template_tool.populate(data);
		});
	}

	exports.init = function() {
		Template_tool.init();
		$("#search_methodName").bind("change", function() {
			if($("#search_methodName").val() == "getApplyProject") {
				$("#search_projectType").append('<option value="special">专项任务</option>');
			} else  {
				$("#search_projectType option[value='special']").remove();
			}
		});
		$("#submit").live("click", function() {
			if(confirm("是否确定同步数据？")) {
				if (parent != null) {
					parent.loading_flag = true;
					setTimeout("parent.showLoading();", parent.loading_lag_time);
				}
				$.ajax({
					url: nameSpace + "/fileExist.action",
					type: "post",
					data: "projectType=" + $("#search_projectType").find("option:selected").val() + "&methodName=" + $("#search_methodName").find("option:selected").val(),
					dataType: "json",
					success: function (result) {
						if (parent != null) {
							parent.loading_flag = false;
							parent.hideLoading();
						}
						if (result.errorInfo == null || result.errorInfo == "") {
							getData = setInterval(showData, 3000);
							$.ajax({
								url: nameSpace + "/importer.action?projectType=" + $("#search_projectType").find("option:selected").val() + "&methodName=" + $("#search_methodName").find("option:selected").val(),
								type: "get",
								dataType: "json",
								success : function (data) {
									if (data.importError != null && data.importError != "undefined") {
										alert(data.importError);
										clearInterval(getData);
									}
								}
							});				
						} else {
							alert(result.errorInfo);
						}
					}
				});
			}
		});
	};

});