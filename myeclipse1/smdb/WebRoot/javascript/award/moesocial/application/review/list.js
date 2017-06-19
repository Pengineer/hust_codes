/**
 * 用于专家评审审核
 * @author 余潜玉
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var listApplication = require('javascript/award/moesocial/application/list');
	var viewApplication = require('javascript/award/moesocial/application/view');
	
	var nameSpace = "award/moesocial/application/review";
	
	exports.init = function() {
		$(function() {
			list.pageShow({
				"nameSpace":"award/moesocial/application/review",
				"selectedTab":"review",
				"viewParam":{"listflag":$("#listflag").val()},
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn11","sortcolumn17","sortcolumn28","sortcolumn29"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn11":11,"sortcolumn17":17,"sortcolumn28":28,"sortcolumn29":29}
			});
			
			$("#review_audits").live("click", function() {//批量评审审核
				var cnt = count("entityIds");
				if (cnt == 0) {
					alert("请选择要审核的奖励评审！");
				}else if (confirm("您确定要审核这些奖励评审吗？")) {
					$("#pagenumber").attr("value", $("#list_goto").val());
					popAwardOperation({
						title : "评审结果审核",
						src : "award/moesocial/application/reviewAudit/toAdd.action?audflag=1",
						callBack : function(dis, type){
							viewMoesocial.doSubmitAudit(dis, 3, viewApplication.showDetails);
						}
					});
					return false;
				}
			});
			
			listApplication.init();
			//届次更新，则列表更新
			$("#session1").bind("change", function() {
				$("#search").attr("action", nameSpace + "/simpleSearch.action");
				$("#search").submit();
				return false;
			});
			listApplication.loadSession('session1');
		});
	};
});











