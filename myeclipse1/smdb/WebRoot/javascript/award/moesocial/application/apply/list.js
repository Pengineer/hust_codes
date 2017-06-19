/**
 * 用于奖励申请数据列表
 * @author 余潜玉
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var listApplication = require('javascript/award/moesocial/application/list')
	var viewMoesocial = require('javascript/award/moesocial/view');
	var viewApplication = require('javascript/award/moesocial/application/view');
	
	var nameSpace = "award/moesocial/application/apply";
	var loadSession = function(id){
		listApplication.loadSession(id);
	};
	exports.loadSession = function(id){
		loadSession(id);
	};
	
	exports.init = function() {
		$(function() {
			list.pageShow({
				"nameSpace":"award/moesocial/application/apply",
				"selectedTab":"application",
				"viewParam":{"listflag":$("#listflag").val()},
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn12","sortcolumn13","sortcolumn14","sortcolumn15","sortcolumn16","sortcolumn17","sortcolumn18","sortcolumn19","sortcolumn21","sortcolumn22","sortcolumn23","sortcolumn24","sortcolumn25"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn12":12,"sortcolumn13":13,"sortcolumn14":14,"sortcolumn15":15,"sortcolumn16":16,"sortcolumn17":17,"sortcolumn18":18,"sortcolumn19":19,"sortcolumn21":21,"sortcolumn22":22,"sortcolumn23":23,"sortcolumn24":24,"sortcolumn25":25}
			});
			//添加奖励申请
			$("#list_add_award").live("click", function() {
				window.location.href = basePath + "award/moesocial/application/apply/toAdd.action?listflag="+$("#listflag").val();
				return false;
			});
			//批量审核
			$("#audit_applies").live("click", function() {
				var cnt = count("entityIds");
				if (cnt == 0) {
					alert("请选择要审核的奖励申请！");
				} else 
				if (confirm("您确定要审核这些奖励申请吗？")) {
					$("#pagenumber").attr("value", $("#list_goto").val());
					popAwardOperation({
						title : "审核",
						src : "award/moesocial/application/applyAudit/toAdd.action?audflag=1",
						callBack : function(dis, type){
							viewMoesocial.doSubmitAudit(dis, 1, viewApplication.showDetails);
						}
					});
					return false;
				}
			});
			//批量评审审核
			$("#review_audits").live("click", function() {
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
			//申请届次
			listApplication.loadSession('session1');
		});
	};
});