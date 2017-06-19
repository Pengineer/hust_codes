define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	/**
	 * 审核留言
	 */
	function auditMessage(nameSpace) {
		var cnt = count("entityIds");// 判断是否有留言被选中
		if (cnt == 0) {
			alert("请选择要审核的留言！");
		} else {
			popMessageAudit({
				callBack : function(result){
					$("#pagenumber").val($("#list_pagenumber").val());// 记录当前页码，以便回到此页面
					if (result && result.auditStatus){
						$("#auditStatus").val(result.auditStatus);
						$("#list").attr("action", nameSpace + "/audit.action").submit();
					}
				}
			});
		}
	};
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"message/inner",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6}
		});
		$("#list_audit").live("click", function() {
			auditMessage(nameSpace);
			return false;
		});// 绑定启用功能
	};
});
