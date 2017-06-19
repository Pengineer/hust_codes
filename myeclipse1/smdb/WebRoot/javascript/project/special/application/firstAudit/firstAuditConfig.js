define(function(require, exports, module) {
	var list = require('javascript/list');
	var nameSpace = "project/special/application/firstAudit";

	/**
	 * 配置列表检索条件
	 */
	var parmsParse = function() {
		$("#search_year").attr('value', $("#select_year").val());
		$("#keyword").attr('value', "");
		$("#search_searchType").attr('value', "-1");
	}
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
		
		$("#viewResult").live("click", function() {
			parmsParse();
			$("#search").attr("action", nameSpace + "/simpleSearch.action");
			$("#search").submit();
		});
		
		$("#export").live("click", function() {
			window.location.href = basePath + nameSpace + "/confirmExportOverView.action?year=" + $("#search_year").val() + 
						"&keyword=" + $("#keyword").val() + "&searchType=" + $("#search_searchType").find("option:selected").val();
		});
		
		$("#firstAudit").live("click", function() {
			if (confirm("您确定要进行" + $("#select_year").val() + "年一般项目的初审操作吗？继续操作会覆盖之前的初审结果！")) {
				if (parent != null) {
					parent.loading_flag = true;
					setTimeout("parent.showLoading();", parent.loading_lag_time);
				}
				$.ajax({
					url: nameSpace + "/firstAudit.action",
					type: "post",
					data: "year=" + $("#select_year").val(),
					dataType: "json",
					success: function (result) {
						if (parent != null) {
							parent.loading_flag = false;
							parent.hideLoading();
						}
						if (result == undefined || result == null) {// 未知的错误异常
							alert("未知的错误异常");
						} else if (result.firstAuditFlag == "success") {
							alert("初审完成！");
						} else if ($.isEmptyObject(result)) {
							alert("初审未成功！");
						}
					}
				});
			}
		});
	}

});