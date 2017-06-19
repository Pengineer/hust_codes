/**
 * 用于公示数据列表
 * @author 余潜玉
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var listApplication = require('javascript/award/moesocial/application/list')
	var viewMoesocial = require('javascript/award/moesocial/view')
	var viewApplication = require('javascript/award/moesocial/application/view')
	
	var nameSpace = "award/moesocial/application/publicity";
	
	var getEntityId = function(ob){//获取entityId
		// 获取复选框对象数组
	    var sel_box = document.getElementsByName(ob);
	    var entityId="";
	    for (var i = 0; i < sel_box.length; i ++) {
	        if (sel_box[i].checked) {
				if(entityId !="")  entityId +=";"+sel_box[i].value;
				else	entityId +=sel_box[i].value;
	        }
	    }
	    return entityId;
	};
	//获取所有届次
	exports.initClick = function(){
		window.loadSession = function(id){listApplication.loadSession(id)};
	};
	
	exports.init = function() {
		$(function() {
			var accountType = $("#accountType").val();
			var selectedTab = "";
			if(accountType == "ADMINISTRATOR" || accountType == "MINISTRY" ){
				selectedTab = "review";
			}else{
				selectedTab = "application";
			} 
			list.pageShow({
				"nameSpace":"award/moesocial/application/publicity",
				"selectedTab":selectedTab,
				"viewParam":{"listflag":$("#listflag").val()},
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn10","sortcolumn19","sortcolumn25","sortcolumn27"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn10":10,"sortcolumn19":19,"sortcolumn25":25,"sortcolumn27":27}
			});
			listApplication.init();
			//奖励审核
			$("#awarded_audits").live("click", function() {
				var cnt = count("entityIds");
				var entityId = getEntityId("entityIds");
				if (cnt == 0) {
					alert("请选择审核的奖励项！");
				} else 
				if (confirm("您确定要审核这些公示数据吗？")) {
					$("#pagenumber").attr("value", $("#list_goto").val());
					popAwardOperation({
						title : "奖励审核",
						src : "award/moesocial/application/publicityAudit/toAdd.action?audflag=1&&entityId="+entityId,
						callBack : function(dis, type){
							viewMoesocial.doSubmitAwardedAudit(dis, 1, viewApplication.showDetails);
						}
					});
					return false;
				}
			});
	
			//届次更新，则列表更新
			$("#session1").bind("change", function() {
				$("#search").attr("action", "award/moesocial/application/publicity/simpleSearch.action");
				$("#search").submit();
				return false;
			});
			listApplication.loadSession('session1');
		});
	};
});
