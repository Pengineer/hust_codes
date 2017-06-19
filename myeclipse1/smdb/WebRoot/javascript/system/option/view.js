define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/ztree/js/jquery.ztree.core-3.3');
	require('tool/ztree/js/jquery.ztree.excheck-3.3');
	require('jquery');
	
	function show_detail(id) {
//		$.post("system/option/toList.action", {"id" : id});
		$("#search").attr("action", "system/option/simpleSearch.action?id=" + id);
		$("#search").submit();
	};
	var zTreeObj;
  	// zTree 的参数配置，（setting 配置详解）
 	var setting = {
 			view: {
				selectedMulti: false
			},
 			async : {
				enable : true,
				url : "system/option/queryNodesTitleByParentId.action",
				autoParam:["id"]
			},
			callback: {
				onClick: onClick
			}
  	};
    				
 	//所选节点
 	function onClick(){
 		var selectedNode = zTreeObj.getSelectedNodes();
 		show_detail(selectedNode[0].id);
 	}
 	
	exports.init = function(nameSpace) {
		list.pageShow({
			"nameSpace":"system/option",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
		zTreeObj = $.fn.zTree.init($("#system_option_tree"), setting);
	};
});
