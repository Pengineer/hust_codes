/*
 * @author: Liujia
 */
define(function(require,exports,module){
	var list = require('javascript/list');
	var nameSpace = "funding";
	var fundingListId = top.PopLayer.instances[top.PopLayer.id].inData.fundingListId;
	var agencyId = top.PopLayer.instances[top.PopLayer.id].inData.agencyId || "";
	exports.init = function(){
		$("#search").attr({
			"action":"funding/funding/list.action?fundingListId=" + fundingListId +"&agencyId=" + agencyId
		});	
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3},
			"dealWith": function(){
				$("#list_button_query").unbind("click");
				$("#list_button_query").click(function(){
					var keyword = $("#keyword").val().trim();
					$("#keyword").val(keyword);
					var url = nameSpace + "/simpleSearch.action?" + "agencyId=" + agencyId + "&fundingListId=" + fundingListId;
					$("#search").attr("action", url);
					if($("#checkedIds").length > 0){//检索时要判断是否有checkedIds，如果有要清空
						$("#checkedIds").attr("value", "");
					};
					list.getData();
					return false;
				});
			},
			"dealWithBefore":function(listjson, json){
				listjson.fundingType = json.fundingType;
			}
		});
	}
	
})