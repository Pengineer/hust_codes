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
//			$("#view_task").html(TrimPath.processDOMTemplate("view_task", data));
//			$("#view_task").css("visibility", "visible");
			Template_tool.populate(data);
		});
	}

	exports.init = function() {
		Template_tool.init();
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
							$.get(nameSpace + "/importer.action?projectType=" + $("#search_projectType").find("option:selected").val() + "&methodName=" + $("#search_methodName").find("option:selected").val());
							getData = setInterval(showData, 5000);
						} else {
							alert(result.errorInfo);
						}
					}
				});
			}
		});
	};

});