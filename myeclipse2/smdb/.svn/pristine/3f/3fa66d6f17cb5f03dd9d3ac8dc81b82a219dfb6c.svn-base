define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	var nameSpace = "system/monitor/visitor";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4"],
			"sortColumnValue":{"sortcolumn0":1,"sortcolumn1":2,"sortcolumn2":3,"sortcolumn3":6,"sortcolumn4":7}
		});
		
		$("#list_evict").live("click", function() {
			var cnt = count("entityIds");
			if (cnt == 0) {
				alert("请选择要下线的访客！");
			} else {
				if (confirm("您确定要这些访客下线吗？")) {
					$("#type").attr("value", 1);
					$("#pagenumber").attr("value", $("#list_goto").val());
					$("#list").attr("action", nameSpace + "/evict.action?flag=1");
					$("#list").submit();
				}
			}
			return false;
		});
		
		$("#list_evict_all").live("click", function() {
			if (confirm("您确定要全部的访客下线吗？")) {
				$("#list").attr("action", nameSpace + "/evict.action?flag=2");
				$("#list").submit();
			}
			return false;
		});
		
	};
});
